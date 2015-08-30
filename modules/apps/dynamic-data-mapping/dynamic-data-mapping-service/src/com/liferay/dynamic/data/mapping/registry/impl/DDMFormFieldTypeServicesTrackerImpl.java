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

package com.liferay.dynamic.data.mapping.registry.impl;

import com.liferay.dynamic.data.mapping.registry.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldValueRenderer;
import com.liferay.osgi.service.tracker.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true)
public class DDMFormFieldTypeServicesTrackerImpl
	implements DDMFormFieldTypeServicesTracker {

	@Override
	public DDMFormFieldRenderer getDDMFormFieldRenderer(String name) {
		return _ddmFormFieldRendererTracker.getService(name);
	}

	@Override
	public DDMFormFieldType getDDMFormFieldType(String name) {
		ServiceWrapper<DDMFormFieldType> ddmFormFieldTypeServiceWrapper =
			_ddmFormFieldTypeTracker.getService(name);

		if (ddmFormFieldTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No DDMFormFieldType registered with name " + name);
			}

			return null;
		}

		return ddmFormFieldTypeServiceWrapper.getService();
	}

	@Override
	public Set<String> getDDMFormFieldTypeNames() {
		return _ddmFormFieldTypeTracker.keySet();
	}

	@Override
	public Map<String, Object> getDDMFormFieldTypeProperties(String name) {
		ServiceWrapper<DDMFormFieldType> ddmFormFieldTypeServiceWrapper =
			_ddmFormFieldTypeTracker.getService(name);

		if (ddmFormFieldTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No DDMFormFieldType registered with name " + name);
			}

			return null;
		}

		return ddmFormFieldTypeServiceWrapper.getProperties();
	}

	@Override
	public List<DDMFormFieldType> getDDMFormFieldTypes() {
		List<DDMFormFieldType> ddmFormFieldTypes = new ArrayList<>();

		for (ServiceWrapper<DDMFormFieldType> ddmFormFieldTypeServiceWrapper :
				_ddmFormFieldTypeTracker.values()) {

			ddmFormFieldTypes.add(ddmFormFieldTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(ddmFormFieldTypes);
	}

	@Override
	public <T> DDMFormFieldValueAccessor<T> getDDMFormFieldValueAccessor(
		String name) {

		return _ddmFormFieldValueAccessorTracker.getService(name);
	}

	@Override
	public DDMFormFieldValueRenderer getDDMFormFieldValueRenderer(String name) {
		return _ddmFormFieldValueRendererTracker.getService(name);
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_ddmFormFieldRendererTracker = ServiceTrackerMapFactory.singleValueMap(
			bundleContext, DDMFormFieldRenderer.class,
			"ddm.form.field.type.name");

		_ddmFormFieldRendererTracker.open();

		_ddmFormFieldTypeTracker = ServiceTrackerMapFactory.singleValueMap(
			bundleContext, DDMFormFieldType.class, "ddm.form.field.type.name",
			ServiceTrackerCustomizerFactory.<DDMFormFieldType>serviceWrapper(
				bundleContext));

		_ddmFormFieldTypeTracker.open();

		_ddmFormFieldValueAccessorTracker =
			ServiceTrackerMapFactory.singleValueMap(
				bundleContext, DDMFormFieldValueAccessor.class,
				"ddm.form.field.type.name");

		_ddmFormFieldValueRendererTracker.open();

		_ddmFormFieldValueRendererTracker =
			ServiceTrackerMapFactory.singleValueMap(
				bundleContext, DDMFormFieldValueRenderer.class,
				"ddm.form.field.type.name");

		_ddmFormFieldValueRendererTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_ddmFormFieldRendererTracker.close();

		_ddmFormFieldTypeTracker.close();

		_ddmFormFieldValueAccessorTracker.close();

		_ddmFormFieldValueRendererTracker.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormFieldTypeServicesTrackerImpl.class);

	private ServiceTrackerMap<String, DDMFormFieldRenderer>
		_ddmFormFieldRendererTracker;
	private ServiceTrackerMap<String, ServiceWrapper<DDMFormFieldType>>
		_ddmFormFieldTypeTracker;
	private ServiceTrackerMap<String, DDMFormFieldValueAccessor>
		_ddmFormFieldValueAccessorTracker;
	private ServiceTrackerMap<String, DDMFormFieldValueRenderer>
		_ddmFormFieldValueRendererTracker;

}