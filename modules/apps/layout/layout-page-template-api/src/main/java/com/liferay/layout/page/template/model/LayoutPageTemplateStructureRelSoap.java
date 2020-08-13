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

package com.liferay.layout.page.template.model;

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
public class LayoutPageTemplateStructureRelSoap implements Serializable {

	public static LayoutPageTemplateStructureRelSoap toSoapModel(
		LayoutPageTemplateStructureRel model) {

		LayoutPageTemplateStructureRelSoap soapModel =
			new LayoutPageTemplateStructureRelSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setLayoutPageTemplateStructureRelId(
			model.getLayoutPageTemplateStructureRelId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setLayoutPageTemplateStructureId(
			model.getLayoutPageTemplateStructureId());
		soapModel.setSegmentsExperienceId(model.getSegmentsExperienceId());
		soapModel.setData(model.getData());

		return soapModel;
	}

	public static LayoutPageTemplateStructureRelSoap[] toSoapModels(
		LayoutPageTemplateStructureRel[] models) {

		LayoutPageTemplateStructureRelSoap[] soapModels =
			new LayoutPageTemplateStructureRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LayoutPageTemplateStructureRelSoap[][] toSoapModels(
		LayoutPageTemplateStructureRel[][] models) {

		LayoutPageTemplateStructureRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LayoutPageTemplateStructureRelSoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new LayoutPageTemplateStructureRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LayoutPageTemplateStructureRelSoap[] toSoapModels(
		List<LayoutPageTemplateStructureRel> models) {

		List<LayoutPageTemplateStructureRelSoap> soapModels =
			new ArrayList<LayoutPageTemplateStructureRelSoap>(models.size());

		for (LayoutPageTemplateStructureRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new LayoutPageTemplateStructureRelSoap[soapModels.size()]);
	}

	public LayoutPageTemplateStructureRelSoap() {
	}

	public long getPrimaryKey() {
		return _layoutPageTemplateStructureRelId;
	}

	public void setPrimaryKey(long pk) {
		setLayoutPageTemplateStructureRelId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getLayoutPageTemplateStructureRelId() {
		return _layoutPageTemplateStructureRelId;
	}

	public void setLayoutPageTemplateStructureRelId(
		long layoutPageTemplateStructureRelId) {

		_layoutPageTemplateStructureRelId = layoutPageTemplateStructureRelId;
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

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
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

	public long getLayoutPageTemplateStructureId() {
		return _layoutPageTemplateStructureId;
	}

	public void setLayoutPageTemplateStructureId(
		long layoutPageTemplateStructureId) {

		_layoutPageTemplateStructureId = layoutPageTemplateStructureId;
	}

	public long getSegmentsExperienceId() {
		return _segmentsExperienceId;
	}

	public void setSegmentsExperienceId(long segmentsExperienceId) {
		_segmentsExperienceId = segmentsExperienceId;
	}

	public String getData() {
		return _data;
	}

	public void setData(String data) {
		_data = data;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _layoutPageTemplateStructureRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _layoutPageTemplateStructureId;
	private long _segmentsExperienceId;
	private String _data;

}