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

import com.liferay.frontend.taglib.form.navigator.FormNavigatorCategoryUtil;
import com.liferay.frontend.taglib.form.navigator.FormNavigatorEntry;
import com.liferay.frontend.taglib.form.navigator.FormNavigatorEntryConfigurationHelper;
import com.liferay.frontend.taglib.form.navigator.FormNavigatorEntryUtil;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = FormNavigatorEntryUtil.class)
public class FormNavigatorEntryImpl implements FormNavigatorEntryUtil {

	@Override
	public <T> List<FormNavigatorEntry<T>> getFormNavigatorEntries(
		String formNavigatorId, String categoryKey, User user,
		T formModelBean) {

		List<FormNavigatorEntry<T>> formNavigatorEntries =
			_getFormNavigatorEntries(
				formNavigatorId, categoryKey, formModelBean);

		return filterVisibleFormNavigatorEntries(
			formNavigatorEntries, user, formModelBean);
	}

	@Override
	public <T> List<FormNavigatorEntry<T>> getFormNavigatorEntries(
		String formNavigatorId, User user, T formModelBean) {

		List<FormNavigatorEntry<T>> formNavigatorEntries = new ArrayList<>();

		String[] categoryKeys = _formNavigatorCategoryUtil.getKeys(
			formNavigatorId);

		for (String categoryKey : categoryKeys) {
			List<FormNavigatorEntry<T>> curFormNavigatorEntries =
				_getFormNavigatorEntries(
					formNavigatorId, categoryKey, formModelBean);

			if (curFormNavigatorEntries != null) {
				formNavigatorEntries.addAll(curFormNavigatorEntries);
			}
		}

		return filterVisibleFormNavigatorEntries(
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

	protected static <T> List<FormNavigatorEntry<T>>
		filterVisibleFormNavigatorEntries(
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

	@Activate
	protected void activate(BundleContext bundleContext) {
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
	}

	private <T> Optional<List<FormNavigatorEntry<T>>>
		_getConfigurationFormNavigatorEntries(
			String formNavigatorId, String categoryKey, T formModelBean) {

		return _formNavigatorEntryConfigurationHelper.getFormNavigatorEntries(
			formNavigatorId, categoryKey, formModelBean);
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

	private String _getKey(String formNavigatorId, String categoryKey) {
		return formNavigatorId + StringPool.PERIOD + categoryKey;
	}

	@Reference
	private FormNavigatorCategoryUtil _formNavigatorCategoryUtil;

	private ServiceTrackerMap<String, List<FormNavigatorEntry>>
		_formNavigatorEntries;

	@Reference
	private FormNavigatorEntryConfigurationHelper
		_formNavigatorEntryConfigurationHelper;

}