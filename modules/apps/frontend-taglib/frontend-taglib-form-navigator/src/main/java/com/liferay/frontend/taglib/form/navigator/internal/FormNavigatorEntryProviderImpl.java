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

package com.liferay.frontend.taglib.form.navigator.internal;

import com.liferay.frontend.taglib.form.navigator.FormNavigatorCategoryProvider;
import com.liferay.frontend.taglib.form.navigator.FormNavigatorEntry;
import com.liferay.frontend.taglib.form.navigator.FormNavigatorEntryProvider;
import com.liferay.frontend.taglib.form.navigator.constants.FormNavigatorContextConstants;
import com.liferay.frontend.taglib.form.navigator.context.FormNavigatorContextProvider;
import com.liferay.frontend.taglib.form.navigator.internal.configuration.FormNavigatorEntryConfigurationRetriever;
import com.liferay.frontend.taglib.form.navigator.internal.servlet.taglib.ui.WrapperFormNavigatorEntry;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = FormNavigatorEntryProvider.class)
public class FormNavigatorEntryProviderImpl
	implements FormNavigatorEntryProvider {

	public <T> Optional<List<FormNavigatorEntry<T>>> getFormNavigatorEntries(
		String formNavigatorId, String categoryKey, T formModelBean) {

		String context = _getContext(formNavigatorId, formModelBean);

		Optional<List<String>> formNavigatorEntryKeysOptional =
			_formNavigatorEntryConfigurationRetriever.getFormNavigatorEntryKeys(
				formNavigatorId, categoryKey, context);

		return formNavigatorEntryKeysOptional.map(
			formNavigatorEntryKeys -> _getFormNavigatorEntries(
				formNavigatorId, formNavigatorEntryKeys));
	}

	@Override
	public <T> List<FormNavigatorEntry<T>> getFormNavigatorEntries(
		String formNavigatorId, String categoryKey, User user,
		T formModelBean) {

		List<FormNavigatorEntry<T>> formNavigatorEntries =
			_getFormNavigatorEntries(
				formNavigatorId, categoryKey, formModelBean);

		if (ListUtil.isEmpty(formNavigatorEntries)) {
			return Collections.emptyList();
		}

		return _filterVisibleFormNavigatorEntries(
			formNavigatorEntries, user, formModelBean);
	}

	@Override
	public <T> List<FormNavigatorEntry<T>> getFormNavigatorEntries(
		String formNavigatorId, User user, T formModelBean) {

		List<FormNavigatorEntry<T>> formNavigatorEntries = new ArrayList<>();

		String[] categoryKeys = _formNavigatorCategoryProvider.getKeys(
			formNavigatorId);

		for (String categoryKey : categoryKeys) {
			List<FormNavigatorEntry<T>> curFormNavigatorEntries =
				_getFormNavigatorEntries(
					formNavigatorId, categoryKey, formModelBean);

			if (curFormNavigatorEntries != null) {
				formNavigatorEntries.addAll(curFormNavigatorEntries);
			}
		}

		return _filterVisibleFormNavigatorEntries(
			formNavigatorEntries, user, formModelBean);
	}

	@Override
	public <T> String[] getKeys(
		String formNavigatorId, String categoryKey, User user,
		T formModelBean) {

		List<String> keys = new ArrayList<>();

		List<FormNavigatorEntry<T>> formNavigatorEntries =
			getFormNavigatorEntries(
				formNavigatorId, categoryKey, user, formModelBean);

		for (FormNavigatorEntry<T> formNavigatorEntry : formNavigatorEntries) {
			String key = formNavigatorEntry.getKey();

			if (Validator.isNotNull(key)) {
				keys.add(key);
			}
		}

		return keys.toArray(new String[0]);
	}

	@Override
	public <T> String[] getLabels(
		String formNavigatorId, String categoryKey, User user, T formModelBean,
		Locale locale) {

		List<String> labels = new ArrayList<>();

		List<FormNavigatorEntry<T>> formNavigatorEntries =
			getFormNavigatorEntries(
				formNavigatorId, categoryKey, user, formModelBean);

		for (FormNavigatorEntry<T> formNavigatorEntry : formNavigatorEntries) {
			String label = formNavigatorEntry.getLabel(locale);

			if (Validator.isNotNull(label)) {
				labels.add(label);
			}
		}

		return labels.toArray(new String[0]);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_formNavigatorContextProviderMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<FormNavigatorContextProvider<?>>)
					(Class<?>)FormNavigatorContextProvider.class,
				FormNavigatorContextConstants.FORM_NAVIGATOR_ID);
		_formNavigatorEntries = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, FormNavigatorEntry.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(formNavigatorEntry, emitter) -> emitter.emit(
					_getKey(
						formNavigatorEntry.getFormNavigatorId(),
						formNavigatorEntry.getCategoryKey()))),
			new PropertyServiceReferenceComparator<>(
				"form.navigator.entry.order"));
		_serviceTracker = ServiceTrackerFactory.openWrapperServiceRegistrator(
			bundleContext,
			(Class
				<com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry
					<?>>)
						(Class<?>)
							com.liferay.portal.kernel.servlet.taglib.ui.
								FormNavigatorEntry.class,
			FormNavigatorEntry.class, WrapperFormNavigatorEntry::new,
			"form.navigator.entry.order");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		_formNavigatorContextProviderMap.close();
	}

	private <T> List<FormNavigatorEntry<T>> _filterVisibleFormNavigatorEntries(
		List<FormNavigatorEntry<T>> formNavigatorEntries, User user,
		T formModelBean) {

		List<FormNavigatorEntry<T>> filteredFormNavigatorEntries =
			new ArrayList<>();

		for (FormNavigatorEntry<T> formNavigatorEntry : formNavigatorEntries) {
			if (formNavigatorEntry.isVisible(user, formModelBean)) {
				filteredFormNavigatorEntries.add(formNavigatorEntry);
			}
		}

		return filteredFormNavigatorEntries;
	}

	private <T> Optional<List<FormNavigatorEntry<T>>>
		_getConfigurationFormNavigatorEntries(
			String formNavigatorId, String categoryKey, T formModelBean) {

		return getFormNavigatorEntries(
			formNavigatorId, categoryKey, formModelBean);
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

	private <T> List<FormNavigatorEntry<T>> _getFormNavigatorEntries(
		String formNavigatorId, String categoryKey, T formModelBean) {

		Optional<List<FormNavigatorEntry<T>>> formNavigationEntriesOptional =
			_getConfigurationFormNavigatorEntries(
				formNavigatorId, categoryKey, formModelBean);

		if (formNavigationEntriesOptional.isPresent()) {
			return formNavigationEntriesOptional.get();
		}

		return (List)_formNavigatorEntries.getService(
			_getKey(formNavigatorId, categoryKey));
	}

	private <T> FormNavigatorEntry<T> _getFormNavigatorEntry(
		String key, String formNavigatorId) {

		FormNavigatorEntry<T> formNavigatorEntry =
			(FormNavigatorEntry<T>)_formNavigatorEntries.getService(
				_getKey(key, formNavigatorId));

		if ((formNavigatorEntry == null) && _log.isWarnEnabled()) {
			_log.warn(
				StringBundler.concat(
					"There is no form navigator entry for the form ",
					formNavigatorId, " with key", key));
		}

		return formNavigatorEntry;
	}

	private String _getKey(String formNavigatorId, String categoryKey) {
		return formNavigatorId + StringPool.PERIOD + categoryKey;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FormNavigatorEntryProviderImpl.class);

	@Reference
	private FormNavigatorCategoryProvider _formNavigatorCategoryProvider;

	private ServiceTrackerMap<String, FormNavigatorContextProvider<?>>
		_formNavigatorContextProviderMap;
	private ServiceTrackerMap<String, List<FormNavigatorEntry>>
		_formNavigatorEntries;

	@Reference
	private FormNavigatorEntryConfigurationRetriever
		_formNavigatorEntryConfigurationRetriever;

	private ServiceTracker
		<com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry<?>, ?>
			_serviceTracker;

}