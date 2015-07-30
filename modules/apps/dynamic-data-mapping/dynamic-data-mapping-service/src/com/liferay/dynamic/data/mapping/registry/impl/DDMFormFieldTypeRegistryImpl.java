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

package com.liferay.portlet.dynamicdatamapping.registry;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFieldTypeRegistryImpl implements DDMFormFieldTypeRegistry {

	public DDMFormFieldTypeRegistryImpl() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			DDMFormFieldType.class,
			new DDMFormFieldTypeServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	@Override
	public DDMFormFieldType getDDMFormFieldType(String name) {
		return _ddmFormFieldTypesMap.get(name);
	}

	@Override
	public Set<String> getDDMFormFieldTypeNames() {
		return Collections.unmodifiableSet(_ddmFormFieldTypesMap.keySet());
	}

	@Override
	public List<DDMFormFieldType> getDDMFormFieldTypes() {
		List<DDMFormFieldType> ddmFormFieldTypes = ListUtil.fromCollection(
			_ddmFormFieldTypesMap.values());

		return Collections.unmodifiableList(ddmFormFieldTypes);
	}

	private final Map<String, DDMFormFieldType> _ddmFormFieldTypesMap =
		new ConcurrentHashMap<>();
	private final ServiceTracker<DDMFormFieldType, DDMFormFieldType>
		_serviceTracker;

	private class DDMFormFieldTypeServiceTrackerCustomizer
		implements
			ServiceTrackerCustomizer<DDMFormFieldType, DDMFormFieldType> {

		@Override
		public DDMFormFieldType addingService(
			ServiceReference<DDMFormFieldType> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			DDMFormFieldType ddmFormFieldType = registry.getService(
				serviceReference);

			_ddmFormFieldTypesMap.put(
				ddmFormFieldType.getName(), ddmFormFieldType);

			return ddmFormFieldType;
		}

		@Override
		public void modifiedService(
			ServiceReference<DDMFormFieldType> serviceReference,
			DDMFormFieldType ddmFormFieldType) {
		}

		@Override
		public void removedService(
			ServiceReference<DDMFormFieldType> serviceReference,
			DDMFormFieldType ddmFormFieldType) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_ddmFormFieldTypesMap.remove(ddmFormFieldType.getName());
		}

	}

}