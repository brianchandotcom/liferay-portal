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

package com.liferay.portal.kernel.servlet.taglib.ui;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Sergio González
 */
public class FormNavigatorEntryUtil {

	public static <T> List<FormNavigatorEntry<T>> getFormNavigatorEntries(
		String formNavigatorId, String categoryKey, User user,
		T formModelBean) {

		@SuppressWarnings("rawtypes")
		List<FormNavigatorEntry<T>> formNavigatorEntries =
			(List)_instance._formNavigatorEntries.getService(
				_getKey(formNavigatorId, categoryKey));

		return filterVisibleFormNavigatorEntries(
			formNavigatorEntries, user, formModelBean);
	}

	public static <T> List<FormNavigatorEntry<T>> getFormNavigatorEntries(
		String formNavigatorId, User user, T formModelBean) {

		List<FormNavigatorEntry<T>> formNavigatorEntries = new ArrayList<>();

		String[] categoryKeys = FormNavigatorCategoryUtil.getKeys(
			formNavigatorId);

		for (String categoryKey : categoryKeys) {

			@SuppressWarnings("rawtypes")
			List<FormNavigatorEntry<T>> curFormNavigatorEntries =
				(List)_instance._formNavigatorEntries.getService(
					_getKey(formNavigatorId, categoryKey));

			if (curFormNavigatorEntries != null) {
				formNavigatorEntries.addAll(curFormNavigatorEntries);
			}
		}

		return filterVisibleFormNavigatorEntries(
			formNavigatorEntries, user, formModelBean);
	}

	public static <T> String[] getKeys(
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

		return keys.toArray(new String[keys.size()]);
	}

	public static <T> String[] getLabels(
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

		return labels.toArray(new String[labels.size()]);
	}

	protected static <T> List<FormNavigatorEntry<T>>
		filterVisibleFormNavigatorEntries(
			List<FormNavigatorEntry<T>> formNavigatorEntries, User user,
			T formModelBean) {

		List<FormNavigatorEntry<T>> filterFormNavigatorEntries =
			new ArrayList<>();

		if (ListUtil.isEmpty(formNavigatorEntries)) {
			return filterFormNavigatorEntries;
		}

		for (FormNavigatorEntry<T> formNavigatorEntry : formNavigatorEntries) {
			if (formNavigatorEntry.isVisible(user, formModelBean)) {
				filterFormNavigatorEntries.add(formNavigatorEntry);
			}
		}

		return filterFormNavigatorEntries;
	}

	private static String _getKey(String formNavigatorId, String categoryKey) {
		return formNavigatorId + StringPool.PERIOD + categoryKey;
	}

	@SuppressWarnings("rawtypes")
	private FormNavigatorEntryUtil() {
		_formNavigatorEntries = ServiceTrackerCollections.openMultiValueMap(
			FormNavigatorEntry.class, null,
			new ServiceReferenceMapper<String, FormNavigatorEntry>() {

				@Override
				public void map(
					ServiceReference<FormNavigatorEntry> serviceReference,
					Emitter<String> emitter) {

					Registry registry = RegistryUtil.getRegistry();

					FormNavigatorEntry<?> formNavigatorEntry =
						registry.getService(serviceReference);

					emitter.emit(
						_getKey(
							formNavigatorEntry.getFormNavigatorId(),
							formNavigatorEntry.getCategoryKey()));

					registry.ungetService(serviceReference);
				}

			});
	}

	private static final FormNavigatorEntryUtil _instance =
		new FormNavigatorEntryUtil();

	@SuppressWarnings("rawtypes")
	private final ServiceTrackerMap<String, List<FormNavigatorEntry>>
		_formNavigatorEntries;

}