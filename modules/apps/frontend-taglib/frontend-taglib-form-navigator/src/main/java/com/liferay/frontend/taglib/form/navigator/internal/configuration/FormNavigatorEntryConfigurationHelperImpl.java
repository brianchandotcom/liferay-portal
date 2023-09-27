/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.form.navigator.internal.configuration;

import com.liferay.frontend.taglib.form.navigator.constants.FormNavigatorContextConstants;
import com.liferay.frontend.taglib.form.navigator.context.FormNavigatorContextProvider;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntryConfigurationHelper;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = FormNavigatorEntryConfigurationHelper.class)
public class FormNavigatorEntryConfigurationHelperImpl
	implements FormNavigatorEntryConfigurationHelper {

	@Override
	public <T> List<FormNavigatorEntry<T>> getFormNavigatorEntries(
		String formNavigatorId, String categoryKey, T formModelBean) {

		String context = _getContext(formNavigatorId, formModelBean);

		List<String> formNavigatorEntryKeys =
			_formNavigatorEntryConfigurationRetriever.getFormNavigatorEntryKeys(
				formNavigatorId, categoryKey, context);

		return _getFormNavigatorEntries(
			formNavigatorId, formNavigatorEntryKeys);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_formNavigatorEntriesMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext,
			(Class<FormNavigatorEntry<?>>)(Class<?>)FormNavigatorEntry.class,
			null,
			(serviceReference, emitter) -> {
				FormNavigatorEntry<?> formNavigatorEntry =
					bundleContext.getService(serviceReference);

				emitter.emit(
					_getKey(
						formNavigatorEntry.getKey(),
						formNavigatorEntry.getFormNavigatorId()));

				bundleContext.ungetService(serviceReference);
			});

		_formNavigatorContextProviderMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<FormNavigatorContextProvider<?>>)
					(Class<?>)FormNavigatorContextProvider.class,
				FormNavigatorContextConstants.FORM_NAVIGATOR_ID);
	}

	@Deactivate
	protected void deactivate() {
		_formNavigatorEntriesMap.close();
		_formNavigatorContextProviderMap.close();
	}

	private <T> String _getContext(String formNavigatorId, T formModelBean) {
		FormNavigatorContextProvider<T> formNavigatorContextProvider =
			(FormNavigatorContextProvider<T>)
				_formNavigatorContextProviderMap.getService(formNavigatorId);

		if (formNavigatorContextProvider != null) {
			return formNavigatorContextProvider.getContext(formModelBean);
		}

		if (formModelBean == null) {
			return FormNavigatorContextConstants.CONTEXT_ADD;
		}

		return FormNavigatorContextConstants.CONTEXT_UPDATE;
	}

	private <T> List<FormNavigatorEntry<T>> _getFormNavigatorEntries(
		String formNavigatorId, List<String> formNavigatorEntryKeys) {

		if (formNavigatorEntryKeys == null) {
			return null;
		}

		List<FormNavigatorEntry<T>> formNavigatorEntries = new ArrayList<>();

		for (String key : formNavigatorEntryKeys) {
			FormNavigatorEntry<T> formNavigatorEntry = _getFormNavigatorEntry(
				key, formNavigatorId);

			if (formNavigatorEntry != null) {
				formNavigatorEntries.add(formNavigatorEntry);
			}
		}

		return formNavigatorEntries;
	}

	private <T> FormNavigatorEntry<T> _getFormNavigatorEntry(
		String key, String formNavigatorId) {

		FormNavigatorEntry<T> formNavigatorEntry =
			(FormNavigatorEntry<T>)_formNavigatorEntriesMap.getService(
				_getKey(key, formNavigatorId));

		if ((formNavigatorEntry == null) && _log.isWarnEnabled()) {
			_log.warn(
				String.format(
					"There is no form navigator entry for the form '%s' with " +
						"key '%'",
					formNavigatorId, key));
		}

		return formNavigatorEntry;
	}

	private String _getKey(String key, String formNavigatorId) {
		return formNavigatorId + StringPool.PERIOD + key;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FormNavigatorEntryConfigurationHelperImpl.class);

	private ServiceTrackerMap<String, FormNavigatorContextProvider<?>>
		_formNavigatorContextProviderMap;
	private ServiceTrackerMap<String, FormNavigatorEntry<?>>
		_formNavigatorEntriesMap;

	@Reference
	private FormNavigatorEntryConfigurationRetriever
		_formNavigatorEntryConfigurationRetriever;

}