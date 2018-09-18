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

package com.liferay.dynamic.data.mapping.form.field.type.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.internal.util.comparator.DDMFormFieldTypeServiceWrapperDisplayOrderComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDMFormFieldTypeServicesTracker.class)
public class DDMFormFieldTypeServicesTrackerImpl
	implements DDMFormFieldTypeServicesTracker {

	@Override
	public DDMFormFieldRenderer getDDMFormFieldRenderer(String name) {
		return _ddmFormFieldRendererServiceTrackerMap.getService(name);
	}

	@Override
	public DDMFormFieldTemplateContextContributor
		getDDMFormFieldTemplateContextContributor(String name) {

		return _ddmFormFieldTemplateContextContributorServiceTrackerMap.
			getService(name);
	}

	@Override
	public DDMFormFieldType getDDMFormFieldType(String name) {
		ServiceWrapper<DDMFormFieldType> ddmFormFieldTypeServiceWrapper =
			_ddmFormFieldTypeServiceTrackerMap.getService(name);

		if (ddmFormFieldTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No DDM form field type registered with name " + name);
			}

			return null;
		}

		return ddmFormFieldTypeServiceWrapper.getService();
	}

	@Override
	public Set<String> getDDMFormFieldTypeNames() {
		return _ddmFormFieldTypeServiceTrackerMap.keySet();
	}

	@Override
	public Map<String, Object> getDDMFormFieldTypeProperties(String name) {
		ServiceWrapper<DDMFormFieldType> ddmFormFieldTypeServiceWrapper =
			_ddmFormFieldTypeServiceTrackerMap.getService(name);

		if (ddmFormFieldTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No DDM form field type registered with name " + name);
			}

			return null;
		}

		return ddmFormFieldTypeServiceWrapper.getProperties();
	}

	@Override
	public List<DDMFormFieldType> getDDMFormFieldTypes() {
		List<DDMFormFieldType> ddmFormFieldTypes = new ArrayList<>();

		List<ServiceWrapper<DDMFormFieldType>> ddmFormFieldTypeServiceWrappers =
			ListUtil.fromCollection(
				_ddmFormFieldTypeServiceTrackerMap.values());

		Collections.sort(
			ddmFormFieldTypeServiceWrappers,
			_ddmFormFieldTypeServiceWrapperDisplayOrderComparator);

		for (ServiceWrapper<DDMFormFieldType> ddmFormFieldTypeServiceWrapper :
				ddmFormFieldTypeServiceWrappers) {

			ddmFormFieldTypes.add(ddmFormFieldTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(ddmFormFieldTypes);
	}

	@Override
	public List<DDMFormFieldType> getDDMFormFieldTypesByDataDomain(
		String dataDomain) {

		List<DDMFormFieldType> ddmFormFieldTypes = new ArrayList<>();

		List<ServiceWrapper<DDMFormFieldType>> ddmFormFieldTypeServiceWrappers =
			ListUtil.fromCollection(
				_ddmFormFieldTypeServiceTrackerMap.values());

		for (ServiceWrapper<DDMFormFieldType> ddmFormFieldTypeServiceWrapper :
				ddmFormFieldTypeServiceWrappers) {

			String fieldDataDomain = MapUtil.getString(
				ddmFormFieldTypeServiceWrapper.getProperties(),
				"ddm.form.field.type.data.domain");

			if (Objects.equals(fieldDataDomain, dataDomain)) {
				ddmFormFieldTypes.add(
					ddmFormFieldTypeServiceWrapper.getService());
			}
		}

		return Collections.unmodifiableList(ddmFormFieldTypes);
	}

	@Override
	public <T> DDMFormFieldValueAccessor<T> getDDMFormFieldValueAccessor(
		String name) {

		return _ddmFormFieldValueAccessorServiceTrackerMap.getService(name);
	}

	@Override
	public DDMFormFieldValueRenderer getDDMFormFieldValueRenderer(String name) {
		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			_ddmFormFieldValueRendererServiceTrackerMap.getService(name);

		if (ddmFormFieldValueRenderer != null) {
			return ddmFormFieldValueRenderer;
		}

		return _defaultDDMFormFieldValueRenderer;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_ddmFormFieldRendererServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, DDMFormFieldRenderer.class,
				"ddm.form.field.type.name");

		_ddmFormFieldTemplateContextContributorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, DDMFormFieldTemplateContextContributor.class,
				"ddm.form.field.type.name");

		_ddmFormFieldTypeServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, DDMFormFieldType.class,
				"ddm.form.field.type.name",
				ServiceTrackerCustomizerFactory.
					<DDMFormFieldType>serviceWrapper(bundleContext));

		_ddmFormFieldValueAccessorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, DDMFormFieldValueAccessor.class,
				"ddm.form.field.type.name");

		_ddmFormFieldValueRendererServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, DDMFormFieldValueRenderer.class,
				"ddm.form.field.type.name");
	}

	@Deactivate
	protected void deactivate() {
		_ddmFormFieldRendererServiceTrackerMap.close();

		_ddmFormFieldTemplateContextContributorServiceTrackerMap.close();

		_ddmFormFieldTypeServiceTrackerMap.close();

		_ddmFormFieldValueAccessorServiceTrackerMap.close();

		_ddmFormFieldValueRendererServiceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormFieldTypeServicesTrackerImpl.class);

	private ServiceTrackerMap<String, DDMFormFieldRenderer>
		_ddmFormFieldRendererServiceTrackerMap;
	private ServiceTrackerMap<String, DDMFormFieldTemplateContextContributor>
		_ddmFormFieldTemplateContextContributorServiceTrackerMap;
	private ServiceTrackerMap<String, ServiceWrapper<DDMFormFieldType>>
		_ddmFormFieldTypeServiceTrackerMap;
	private final Comparator<ServiceWrapper<DDMFormFieldType>>
		_ddmFormFieldTypeServiceWrapperDisplayOrderComparator =
			new DDMFormFieldTypeServiceWrapperDisplayOrderComparator();
	private ServiceTrackerMap<String, DDMFormFieldValueAccessor>
		_ddmFormFieldValueAccessorServiceTrackerMap;
	private ServiceTrackerMap<String, DDMFormFieldValueRenderer>
		_ddmFormFieldValueRendererServiceTrackerMap;
	private final DefaultDDMFormFieldValueRenderer
		_defaultDDMFormFieldValueRenderer =
			new DefaultDDMFormFieldValueRenderer();

}