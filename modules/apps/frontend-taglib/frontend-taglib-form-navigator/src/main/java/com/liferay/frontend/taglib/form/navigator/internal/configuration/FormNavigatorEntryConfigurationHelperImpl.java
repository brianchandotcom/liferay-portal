/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
import java.util.Optional;

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
	public <T> Optional<List<FormNavigatorEntry<T>>> getFormNavigatorEntries(
		String formNavigatorId, String categoryKey, T formModelBean) {

		String context = _getContext(formNavigatorId, formModelBean);

		Optional<List<String>> optionalFormNavigatorEntryKeys =
			_formNavigatorEntryConfigurationRetriever.getFormNavigatorEntryKeys(
				formNavigatorId, categoryKey, context);

		return optionalFormNavigatorEntryKeys.map(
			formNavigatorEntryKeys -> _getFormNavigatorEntries(
				formNavigatorId, formNavigatorEntryKeys));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_formNavigatorEntriesMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, FormNavigatorEntry.class, null,
			(serviceReference, emitter) -> {
				FormNavigatorEntry formNavigatorEntry =
					bundleContext.getService(serviceReference);

				emitter.emit(
					_getKey(
						formNavigatorEntry.getKey(),
						formNavigatorEntry.getFormNavigatorId()));

				bundleContext.ungetService(serviceReference);
			});

		_formNavigatorContextProviderMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, FormNavigatorContextProvider.class,
				FormNavigatorContextConstants.FORM_NAVIGATOR_ID);
	}

	@Deactivate
	protected void deactivate() {
		_formNavigatorEntriesMap.close();
		_formNavigatorContextProviderMap.close();
	}

	private <T> String _getContext(String formNavigatorId, T formModelBean) {
		FormNavigatorContextProvider<T> formNavigatorContextProvider =
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

		FormNavigatorEntry formNavigatorEntry =
			_formNavigatorEntriesMap.getService(_getKey(key, formNavigatorId));

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

	private ServiceTrackerMap<String, FormNavigatorContextProvider>
		_formNavigatorContextProviderMap;
	private ServiceTrackerMap<String, FormNavigatorEntry>
		_formNavigatorEntriesMap;

	@Reference
	private FormNavigatorEntryConfigurationRetriever
		_formNavigatorEntryConfigurationRetriever;

}