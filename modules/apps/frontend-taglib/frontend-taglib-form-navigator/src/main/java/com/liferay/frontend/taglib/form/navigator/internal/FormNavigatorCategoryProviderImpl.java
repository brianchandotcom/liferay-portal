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

import com.liferay.frontend.taglib.form.navigator.FormNavigatorCategory;
import com.liferay.frontend.taglib.form.navigator.FormNavigatorCategoryProvider;
import com.liferay.frontend.taglib.form.navigator.internal.servlet.taglib.ui.WrapperFormNavigatorCategory;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = FormNavigatorCategoryProvider.class)
public class FormNavigatorCategoryProviderImpl
	implements FormNavigatorCategoryProvider {

	@Override
	public List<FormNavigatorCategory> getFormNavigatorCategories(
		String formNavigatorId) {

		List<FormNavigatorCategory> formNavigatorCategories =
			_formNavigatorCategories.getService(formNavigatorId);

		if (formNavigatorCategories != null) {
			return formNavigatorCategories;
		}

		return Collections.emptyList();
	}

	@Override
	public String[] getKeys(String formNavigatorId) {
		List<FormNavigatorCategory> formNavigatorCategories =
			getFormNavigatorCategories(formNavigatorId);

		if (ListUtil.isEmpty(formNavigatorCategories)) {
			return new String[] {""};
		}

		List<String> keys = new ArrayList<>();

		for (FormNavigatorCategory formNavigatorCategory :
				formNavigatorCategories) {

			String key = formNavigatorCategory.getKey();

			if (Validator.isNotNull(key)) {
				keys.add(key);
			}
		}

		return keys.toArray(new String[0]);
	}

	@Override
	public String[] getLabels(String formNavigatorId, Locale locale) {
		List<FormNavigatorCategory> formNavigatorCategories =
			getFormNavigatorCategories(formNavigatorId);

		if (ListUtil.isEmpty(formNavigatorCategories)) {
			return new String[] {""};
		}

		List<String> labels = new ArrayList<>();

		for (FormNavigatorCategory formNavigatorCategory :
				formNavigatorCategories) {

			String label = formNavigatorCategory.getLabel(locale);

			if (Validator.isNotNull(label)) {
				labels.add(label);
			}
		}

		return labels.toArray(new String[0]);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_formNavigatorCategories = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, FormNavigatorCategory.class, null,
			ServiceReferenceMapperFactory.createFromFunction(
				bundleContext, FormNavigatorCategory::getFormNavigatorId),
			new PropertyServiceReferenceComparator<>(
				"form.navigator.category.order"));

		_serviceTracker = ServiceTrackerFactory.openWrapperRegistrator(
			bundleContext,
			com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorCategory.
				class,
			FormNavigatorCategory.class, WrapperFormNavigatorCategory::new,
			"form.navigator.category.order");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		_formNavigatorCategories.close();
	}

	private ServiceTrackerMap<String, List<FormNavigatorCategory>>
		_formNavigatorCategories;
	private ServiceTracker
		<com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorCategory, ?>
			_serviceTracker;

}