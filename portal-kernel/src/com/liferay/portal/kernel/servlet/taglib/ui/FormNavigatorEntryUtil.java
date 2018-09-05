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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * @author Sergio González
 */
public class FormNavigatorEntryUtil {

	public static <T> List<FormNavigatorEntry<T>> getFormNavigatorEntries(
		String formNavigatorId, String categoryKey, User user,
		T formModelBean) {

		List<FormNavigatorEntry<T>> formNavigatorEntries =
			_getFormNavigatorEntries(
				formNavigatorId, categoryKey, formModelBean);

		return filterVisibleFormNavigatorEntries(
			formNavigatorEntries, user, formModelBean);
	}

	public static <T> List<FormNavigatorEntry<T>> getFormNavigatorEntries(
		String formNavigatorId, User user, T formModelBean) {

		List<FormNavigatorEntry<T>> formNavigatorEntries = new ArrayList<>();

		String[] categoryKeys = FormNavigatorCategoryUtil.getKeys(
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

		List<FormNavigatorEntry<T>> filteredFormNavigatorEntries =
			new ArrayList<>();

		for (FormNavigatorEntry<T> formNavigatorEntry : formNavigatorEntries) {
			if (formNavigatorEntry.isVisible(user, formModelBean)) {
				filteredFormNavigatorEntries.add(formNavigatorEntry);
			}
		}

		return filteredFormNavigatorEntries;
	}

	private static <T> Optional<List<FormNavigatorEntry<T>>>
		_getConfigurationFormNavigatorEntries(
			String formNavigatorId, String categoryKey, T formModelBean) {

		FormNavigatorEntryConfigurationHelper
			formNavigatorEntryConfigurationHelper =
				_instance._serviceTracker.getService();

		if (formNavigatorEntryConfigurationHelper == null) {
			return Optional.empty();
		}

		return formNavigatorEntryConfigurationHelper.getFormNavigatorEntries(
			formNavigatorId, categoryKey, formModelBean);
	}

	private static <T> List<FormNavigatorEntry<T>> _getFormNavigatorEntries(
		String formNavigatorId, String categoryKey, T formModelBean) {

		Optional<List<FormNavigatorEntry<T>>> formNavigationEntriesOptional =
			_getConfigurationFormNavigatorEntries(
				formNavigatorId, categoryKey, formModelBean);

		if (formNavigationEntriesOptional.isPresent()) {
			return formNavigationEntriesOptional.get();
		}

		return (List)_instance._formNavigatorEntries.getService(
			_getKey(formNavigatorId, categoryKey));
	}

	private static String _getKey(String formNavigatorId, String categoryKey) {
		return formNavigatorId + StringPool.PERIOD + categoryKey;
	}

	@SuppressWarnings("rawtypes")
	private FormNavigatorEntryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_formNavigatorEntries = ServiceTrackerCollections.openMultiValueMap(
			FormNavigatorEntry.class, null,
			new ServiceReferenceMapper<String, FormNavigatorEntry>() {

				@Override
				public void map(
					ServiceReference<FormNavigatorEntry> serviceReference,
					Emitter<String> emitter) {

					FormNavigatorEntry<?> formNavigatorEntry =
						registry.getService(serviceReference);

					emitter.emit(
						_getKey(
							formNavigatorEntry.getFormNavigatorId(),
							formNavigatorEntry.getCategoryKey()));

					registry.ungetService(serviceReference);
				}

			},
			new PropertyServiceReferenceComparator<FormNavigatorEntry>(
				"form.navigator.entry.order"));

		_serviceTracker = registry.trackServices(
			FormNavigatorEntryConfigurationHelper.class);

		_serviceTracker.open();
	}

	private static final FormNavigatorEntryUtil _instance =
		new FormNavigatorEntryUtil();

	@SuppressWarnings("rawtypes")
	private final ServiceTrackerMap<String, List<FormNavigatorEntry>>
		_formNavigatorEntries;

	private final ServiceTracker
		<FormNavigatorEntryConfigurationHelper,
		 FormNavigatorEntryConfigurationHelper> _serviceTracker;

	/**
	 * @see com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator
	 */
	private class PropertyServiceReferenceComparator<T>
		implements Comparator<ServiceReference<T>>, Serializable {

		public PropertyServiceReferenceComparator(String propertyKey) {
			_propertyKey = propertyKey;
		}

		@Override
		public int compare(
			ServiceReference<T> serviceReference1,
			ServiceReference<T> serviceReference2) {

			if (serviceReference1 == null) {
				if (serviceReference2 == null) {
					return 0;
				}

				return 1;
			}
			else if (serviceReference2 == null) {
				return -1;
			}

			Object propertyValue1 = serviceReference1.getProperty(_propertyKey);
			Object propertyValue2 = serviceReference2.getProperty(_propertyKey);

			if (propertyValue1 == null) {
				if (propertyValue2 == null) {
					return 0;
				}

				return 1;
			}
			else if (propertyValue2 == null) {
				return -1;
			}

			if (!(propertyValue2 instanceof Comparable)) {
				return serviceReference2.compareTo(serviceReference1);
			}

			Comparable<Object> propertyValueComparable2 =
				(Comparable<Object>)propertyValue2;

			return propertyValueComparable2.compareTo(propertyValue1);
		}

		private final String _propertyKey;

	}

}