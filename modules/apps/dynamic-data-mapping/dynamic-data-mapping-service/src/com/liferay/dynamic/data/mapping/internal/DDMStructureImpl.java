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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.dynamicdatamapping.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DDMStructureImpl implements DDMStructure {

	public DDMStructureImpl(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure structure) {

		_structure = structure;
	}

	@Override
	public Object clone() {
		DDMStructureImpl structureImpl = new DDMStructureImpl(
			(com.liferay.portlet.dynamicdatamapping.model.DDMStructure)
				_structure.clone());

		return structureImpl;
	}

	@Override
	public long getClassNameId() {
		return _structure.getClassNameId();
	}

	@Override
	public long getCompanyId() {
		return _structure.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		return _structure.getCreateDate();
	}

	@Override
	public DDMForm getDDMForm() {
		return _structure.getDDMForm();
	}

	@Override
	public List<DDMFormField> getDDMFormFields(boolean includeTransientFields) {
		return _structure.getDDMFormFields(includeTransientFields);
	}

	@Override
	public DDMFormLayout getDDMFormLayout() throws PortalException {
		return _structure.getDDMFormLayout();
	}

	@Override
	public String getDefinition() {
		return _structure.getDescription();
	}

	@Override
	public String getDescription() {
		return _structure.getDescription();
	}

	@Override
	public String getDescription(Locale locale) {
		return _structure.getDescription(locale);
	}

	@Override
	public Map<Locale, String> getDescriptionMap() {
		return _structure.getDescriptionMap();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _structure.getExpandoBridge();
	}

	@Override
	public String getFieldType(String fieldName) throws PortalException {
		return _structure.getFieldType(fieldName);
	}

	@Override
	public long getGroupId() {
		return _structure.getGroupId();
	}

	@Override
	public Class<?> getModelClass() {
		return _structure.getModelClass();
	}

	@Override
	public String getModelClassName() {
		return _structure.getModelClassName();
	}

	@Override
	public Date getModifiedDate() {
		return _structure.getModifiedDate();
	}

	@Override
	public String getName() {
		return _structure.getName();
	}

	@Override
	public String getName(Locale locale) {
		return _structure.getName(locale);
	}

	@Override
	public Map<Locale, String> getNameMap() {
		return _structure.getNameMap();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _structure.getPrimaryKeyObj();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _structure.getStagedModelType();
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
	public void setCompanyId(long companyId) {
		_structure.setCompanyId(companyId);
	}

	@Override
	public void setCreateDate(Date createDate) {
		_structure.setCreateDate(createDate);
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_structure.setModifiedDate(modifiedDate);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_structure.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public void setUuid(String uuid) {
		_structure.setUuid(uuid);
	}

	private final com.liferay.portlet.dynamicdatamapping.model.DDMStructure
		_structure;

}