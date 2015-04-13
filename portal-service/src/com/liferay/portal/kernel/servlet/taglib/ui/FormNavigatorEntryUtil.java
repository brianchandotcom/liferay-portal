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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class FormNavigatorEntryUtil {

	public static List<FormNavigatorEntry> getFormNavigatorEntries(
		String formNavigatorId, HttpServletRequest request) {

		List<FormNavigatorEntry> formNavigatorEntries =
			_instance._formNavigatorEntries.getService(
				_getKey(formNavigatorId, null));

		return filterVisibleFormNavigatorEntries(formNavigatorEntries, request);
	}

	public static List<FormNavigatorEntry> getFormNavigatorEntries(
		String formNavigatorId, String categoryKey,
		HttpServletRequest request) {

		List<FormNavigatorEntry> formNavigatorEntries =
			_instance._formNavigatorEntries.getService(
				_getKey(formNavigatorId, categoryKey));

		return filterVisibleFormNavigatorEntries(formNavigatorEntries, request);
	}

	public static String[] getKeys(
		String formNavigatorId, String categoryKey,
		HttpServletRequest request) {

		List<String> keys = new ArrayList<>();

		List<FormNavigatorEntry> formNavigatorEntries = getFormNavigatorEntries(
			formNavigatorId, categoryKey, request);

		for (FormNavigatorEntry formNavigatorEntry : formNavigatorEntries) {
			String key = formNavigatorEntry.getKey();

			if (Validator.isNotNull(key)) {
				keys.add(key);
			}
		}

		return keys.toArray(new String[keys.size()]);
	}

	public static String[] getLabels(
		String formNavigatorId, String categoryKey,
		HttpServletRequest request) {

		List<String> labels = new ArrayList<>();

		List<FormNavigatorEntry> formNavigatorEntries = getFormNavigatorEntries(
			formNavigatorId, categoryKey, request);

		for (FormNavigatorEntry formNavigatorEntry : formNavigatorEntries) {
			String label = formNavigatorEntry.getLabel();

			if (Validator.isNotNull(label)) {
				labels.add(label);
			}
		}

		return labels.toArray(new String[labels.size()]);
	}

	protected static List<FormNavigatorEntry> filterVisibleFormNavigatorEntries(
		List<FormNavigatorEntry> formNavigatorEntries,
		HttpServletRequest request) {

		List<FormNavigatorEntry> filterFormNavigatorEntries = new ArrayList<>();

		for (FormNavigatorEntry formNavigatorEntry : formNavigatorEntries) {
			if (formNavigatorEntry.isVisible(request)) {
				filterFormNavigatorEntries.add(formNavigatorEntry);
			}
		}

		return filterFormNavigatorEntries;
	}

	private static String _getKey(String formNavigatorId, String categoryKey) {
		if (Validator.isNull(categoryKey)) {
			return formNavigatorId;
		}

		return formNavigatorId + StringPool.PERIOD + categoryKey;
	}

	private FormNavigatorEntryUtil() {
		_formNavigatorEntries = ServiceTrackerCollections.multiValueMap(
			FormNavigatorEntry.class, null,
			new ServiceReferenceMapper<String, FormNavigatorEntry>() {

				@Override
				public void map(
					ServiceReference<FormNavigatorEntry> serviceReference,
					final Emitter<String> emitter) {

					Registry registry = RegistryUtil.getRegistry();

					FormNavigatorEntry formNavigatorEntry = registry.getService(
						serviceReference);

					emitter.emit(
						_getKey(
							formNavigatorEntry.getFormNavigatorId(),
							formNavigatorEntry.getCategoryKey()));
					emitter.emit(
						_getKey(formNavigatorEntry.getFormNavigatorId(), null));

					registry.ungetService(serviceReference);
				}

			});

		_formNavigatorEntries.open();
	}

	private static final FormNavigatorEntryUtil _instance =
		new FormNavigatorEntryUtil();

	private final ServiceTrackerMap<String, List<FormNavigatorEntry>>
		_formNavigatorEntries;

}