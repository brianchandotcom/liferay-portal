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

package com.liferay.dynamic.data.mapping.internal;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.dynamicdatamapping.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalService;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DDMStructureImpl implements DDMStructure {

	public DDMStructureImpl() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			DDMStructureLocalService.class);

		_serviceTracker.open();
	}

	@Override
	public List<DDMFormField> getDDMFormFields(boolean includeTransientFields) {
		List<com.liferay.portlet.dynamicdatamapping.model.DDMFormField>
			fields = _structure.getDDMFormFields(includeTransientFields);

		List<DDMFormField> ddmFormFields = new ArrayList<>();

		try {
			for (com.liferay.portlet.dynamicdatamapping.model.DDMFormField
				field : fields) {

				ddmFormFields.add(
					BeanPropertiesUtil.deepCopyProperties(
						field, DDMFormField.class));
			}
		}
		catch (Exception e) { }

		return ddmFormFields;
	}

	@Override
	public String getFieldType(String fieldName) throws PortalException {
		return _structure.getFieldType(fieldName);
	}

	@Override
	public Map<Locale, String> getNameMap() {
		return _structure.getNameMap();
	}

	@Override
	public long getStructureId() {
		return _structure.getStructureId();
	}

	@Override
	public String getStructureKey() {
		return _structure.getStructureKey();
	}

	@Override
	public String getUuid() {
		return _structure.getUuid();
	}

	@Override
	public void setStructureId(long structureId) {
		_structure = _getDDMStructureLocalService().fetchStructure(structureId);
	}

	private DDMStructureLocalService _getDDMStructureLocalService() {
		return _serviceTracker.getService();
	}

	private final ServiceTracker<DDMStructureLocalService,
		DDMStructureLocalService> _serviceTracker;
	private com.liferay.portlet.dynamicdatamapping.model.DDMStructure
		_structure;

}