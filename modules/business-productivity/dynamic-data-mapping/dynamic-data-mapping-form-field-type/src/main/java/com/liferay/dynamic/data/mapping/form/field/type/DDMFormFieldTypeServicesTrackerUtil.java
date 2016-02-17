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

package com.liferay.dynamic.data.mapping.form.field.type;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFieldTypeServicesTrackerUtil {

	public static DDMFormFieldRenderer getDDMFormFieldRenderer(String name) {
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker =
			getDDMFormFieldTypeServicesTracker();

		return ddmFormFieldTypeServicesTracker.getDDMFormFieldRenderer(name);
	}

	public static DDMFormFieldType getDDMFormFieldType(String name) {
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker =
			getDDMFormFieldTypeServicesTracker();

		return ddmFormFieldTypeServicesTracker.getDDMFormFieldType(name);
	}

	public static Set<String> getDDMFormFieldTypeNames() {
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker =
			getDDMFormFieldTypeServicesTracker();

		return ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeNames();
	}

	public static Map<String, Object> getDDMFormFieldTypeProperties(
		String name) {

		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker =
			getDDMFormFieldTypeServicesTracker();

		return ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeProperties(
			name);
	}

	public static List<DDMFormFieldType> getDDMFormFieldTypes() {
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker =
			getDDMFormFieldTypeServicesTracker();

		return ddmFormFieldTypeServicesTracker.getDDMFormFieldTypes();
	}

	public static DDMFormFieldTypeServicesTracker
		getDDMFormFieldTypeServicesTracker() {

		PortalRuntimePermission.checkGetBeanProperty(
			DDMFormFieldTypeServicesTrackerUtil.class);

		return _serviceTracker.getService();
	}

	public static <T> DDMFormFieldValueAccessor<T> getDDMFormFieldValueAccessor(
		String name) {

		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker =
			getDDMFormFieldTypeServicesTracker();

		return ddmFormFieldTypeServicesTracker.getDDMFormFieldValueAccessor(
			name);
	}

	public static DDMFormFieldValueRenderer getDDMFormFieldValueRenderer(
		String name) {

		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker =
			getDDMFormFieldTypeServicesTracker();

		return ddmFormFieldTypeServicesTracker.getDDMFormFieldValueRenderer(
			name);
	}

	private static final ServiceTracker
		<DDMFormFieldTypeServicesTracker, DDMFormFieldTypeServicesTracker>
			_serviceTracker = ServiceTrackerFactory.open(
				FrameworkUtil.getBundle(
					DDMFormFieldTypeServicesTrackerUtil.class),
				DDMFormFieldTypeServicesTracker.class);

}