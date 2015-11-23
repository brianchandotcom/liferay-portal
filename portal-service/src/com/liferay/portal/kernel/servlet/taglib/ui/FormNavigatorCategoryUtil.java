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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author Sergio González
 */
public class FormNavigatorCategoryUtil {

	public static List<FormNavigatorCategory> getFormNavigatorCategories(
		String formNavigatorId) {

		List<FormNavigatorCategory> formNavigatorCategories =
			_instance._formNavigatorCategories.getService(formNavigatorId);

		if (formNavigatorCategories != null) {
			return formNavigatorCategories;
		}

		return Collections.emptyList();
	}

	public static String[] getKeys(String formNavigatorId) {
		List<String> keys = new ArrayList<>();

		List<FormNavigatorCategory> formNavigatorCategories =
			getFormNavigatorCategories(formNavigatorId);

		if (ListUtil.isEmpty(formNavigatorCategories)) {
			return new String[] {""};
		}

		for (FormNavigatorCategory formNavigatorCategory :
				formNavigatorCategories) {

			String key = formNavigatorCategory.getKey();

			if (Validator.isNotNull(key)) {
				keys.add(key);
			}
		}

		return keys.toArray(new String[keys.size()]);
	}

	public static String[] getLabels(String formNavigatorId, Locale locale) {
		List<String> labels = new ArrayList<>();

		List<FormNavigatorCategory> formNavigatorCategories =
			getFormNavigatorCategories(formNavigatorId);

		if (ListUtil.isEmpty(formNavigatorCategories)) {
			return new String[] {""};
		}

		for (FormNavigatorCategory formNavigatorCategory :
				formNavigatorCategories) {

			String label = formNavigatorCategory.getLabel(locale);

			if (Validator.isNotNull(label)) {
				labels.add(label);
			}
		}

		return labels.toArray(new String[labels.size()]);
	}

	private FormNavigatorCategoryUtil() {
		_formNavigatorCategories = ServiceTrackerCollections.openMultiValueMap(
			FormNavigatorCategory.class, null,
			new ServiceReferenceMapper<String, FormNavigatorCategory>() {

				@Override
				public void map(
					ServiceReference<FormNavigatorCategory> serviceReference,
					Emitter<String> emitter) {

					Registry registry = RegistryUtil.getRegistry();

					FormNavigatorCategory formNavigatorCategory =
						registry.getService(serviceReference);

					emitter.emit(formNavigatorCategory.getFormNavigatorId());

					registry.ungetService(serviceReference);
				}

			});
	}

	private static final FormNavigatorCategoryUtil _instance =
		new FormNavigatorCategoryUtil();

	private final ServiceTrackerMap<String, List<FormNavigatorCategory>>
		_formNavigatorCategories;

}