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

package com.liferay.data.engine.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class DEDataDefinitionFieldLinkSoap implements Serializable {

	public static DEDataDefinitionFieldLinkSoap toSoapModel(
		DEDataDefinitionFieldLink model) {

		DEDataDefinitionFieldLinkSoap soapModel =
			new DEDataDefinitionFieldLinkSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setDeDataDefinitionFieldLinkId(
			model.getDeDataDefinitionFieldLinkId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setDdmStructureId(model.getDdmStructureId());
		soapModel.setFieldName(model.getFieldName());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static DEDataDefinitionFieldLinkSoap[] toSoapModels(
		DEDataDefinitionFieldLink[] models) {

		DEDataDefinitionFieldLinkSoap[] soapModels =
			new DEDataDefinitionFieldLinkSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DEDataDefinitionFieldLinkSoap[][] toSoapModels(
		DEDataDefinitionFieldLink[][] models) {

		DEDataDefinitionFieldLinkSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new DEDataDefinitionFieldLinkSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new DEDataDefinitionFieldLinkSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DEDataDefinitionFieldLinkSoap[] toSoapModels(
		List<DEDataDefinitionFieldLink> models) {

		List<DEDataDefinitionFieldLinkSoap> soapModels =
			new ArrayList<DEDataDefinitionFieldLinkSoap>(models.size());

		for (DEDataDefinitionFieldLink model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new DEDataDefinitionFieldLinkSoap[soapModels.size()]);
	}

	public DEDataDefinitionFieldLinkSoap() {
	}

	public long getPrimaryKey() {
		return _deDataDefinitionFieldLinkId;
	}

	public void setPrimaryKey(long pk) {
		setDeDataDefinitionFieldLinkId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getDeDataDefinitionFieldLinkId() {
		return _deDataDefinitionFieldLinkId;
	}

	public void setDeDataDefinitionFieldLinkId(
		long deDataDefinitionFieldLinkId) {

		_deDataDefinitionFieldLinkId = deDataDefinitionFieldLinkId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public long getDdmStructureId() {
		return _ddmStructureId;
	}

	public void setDdmStructureId(long ddmStructureId) {
		_ddmStructureId = ddmStructureId;
	}

	public String getFieldName() {
		return _fieldName;
	}

	public void setFieldName(String fieldName) {
		_fieldName = fieldName;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private String _uuid;
	private long _deDataDefinitionFieldLinkId;
	private long _groupId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private long _ddmStructureId;
	private String _fieldName;
	private Date _lastPublishDate;

}