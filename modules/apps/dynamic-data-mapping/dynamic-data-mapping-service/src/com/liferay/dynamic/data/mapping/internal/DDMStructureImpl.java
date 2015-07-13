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
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmTemplate) {

		_ddmStructure = ddmTemplate;
	}

	@Override
	public Object clone() {
		DDMStructureImpl structureImpl = new DDMStructureImpl(
			(com.liferay.portlet.dynamicdatamapping.model.DDMStructure)
				_ddmStructure.clone());

		return structureImpl;
	}

	@Override
	public long getClassNameId() {
		return _ddmStructure.getClassNameId();
	}

	@Override
	public long getCompanyId() {
		return _ddmStructure.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		return _ddmStructure.getCreateDate();
	}

	@Override
	public DDMForm getDDMForm() {
		return _ddmStructure.getDDMForm();
	}

	@Override
	public List<DDMFormField> getDDMFormFields(boolean includeTransientFields) {
		return _ddmStructure.getDDMFormFields(includeTransientFields);
	}

	@Override
	public DDMFormLayout getDDMFormLayout() throws PortalException {
		return _ddmStructure.getDDMFormLayout();
	}

	@Override
	public String getDefinition() {
		return _ddmStructure.getDescription();
	}

	@Override
	public String getDescription() {
		return _ddmStructure.getDescription();
	}

	@Override
	public String getDescription(Locale locale) {
		return _ddmStructure.getDescription(locale);
	}

	@Override
	public Map<Locale, String> getDescriptionMap() {
		return _ddmStructure.getDescriptionMap();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _ddmStructure.getExpandoBridge();
	}

	@Override
	public String getFieldType(String fieldName) throws PortalException {
		return _ddmStructure.getFieldType(fieldName);
	}

	@Override
	public long getGroupId() {
		return _ddmStructure.getGroupId();
	}

	@Override
	public Class<?> getModelClass() {
		return _ddmStructure.getModelClass();
	}

	@Override
	public String getModelClassName() {
		return _ddmStructure.getModelClassName();
	}

	@Override
	public Date getModifiedDate() {
		return _ddmStructure.getModifiedDate();
	}

	@Override
	public String getName() {
		return _ddmStructure.getName();
	}

	@Override
	public String getName(Locale locale) {
		return _ddmStructure.getName(locale);
	}

	@Override
	public Map<Locale, String> getNameMap() {
		return _ddmStructure.getNameMap();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _ddmStructure.getPrimaryKeyObj();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _ddmStructure.getStagedModelType();
	}

	@Override
	public long getStructureId() {
		return _ddmStructure.getStructureId();
	}

	@Override
	public String getStructureKey() {
		return _ddmStructure.getStructureKey();
	}

	@Override
	public String getUuid() {
		return _ddmStructure.getUuid();
	}

	@Override
	public void setCompanyId(long companyId) {
		_ddmStructure.setCompanyId(companyId);
	}

	@Override
	public void setCreateDate(Date createDate) {
		_ddmStructure.setCreateDate(createDate);
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_ddmStructure.setModifiedDate(modifiedDate);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_ddmStructure.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public void setUuid(String uuid) {
		_ddmStructure.setUuid(uuid);
	}

	private final com.liferay.portlet.dynamicdatamapping.model.DDMStructure
		_ddmStructure;

}