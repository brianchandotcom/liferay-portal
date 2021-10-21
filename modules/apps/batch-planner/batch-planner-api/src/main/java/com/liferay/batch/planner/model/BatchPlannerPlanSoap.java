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

package com.liferay.batch.planner.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.batch.planner.service.http.BatchPlannerPlanServiceSoap}.
 *
 * @author Igor Beslic
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class BatchPlannerPlanSoap implements Serializable {

	public static BatchPlannerPlanSoap toSoapModel(BatchPlannerPlan model) {
		BatchPlannerPlanSoap soapModel = new BatchPlannerPlanSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setBatchPlannerPlanId(model.getBatchPlannerPlanId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setActive(model.isActive());
		soapModel.setExport(model.isExport());
		soapModel.setExternalType(model.getExternalType());
		soapModel.setExternalURL(model.getExternalURL());
		soapModel.setInternalClassName(model.getInternalClassName());
		soapModel.setName(model.getName());
		soapModel.setTaskItemDelegateName(model.getTaskItemDelegateName());
		soapModel.setTemplate(model.isTemplate());

		return soapModel;
	}

	public static BatchPlannerPlanSoap[] toSoapModels(
		BatchPlannerPlan[] models) {

		BatchPlannerPlanSoap[] soapModels =
			new BatchPlannerPlanSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static BatchPlannerPlanSoap[][] toSoapModels(
		BatchPlannerPlan[][] models) {

		BatchPlannerPlanSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new BatchPlannerPlanSoap[models.length][models[0].length];
		}
		else {
			soapModels = new BatchPlannerPlanSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static BatchPlannerPlanSoap[] toSoapModels(
		List<BatchPlannerPlan> models) {

		List<BatchPlannerPlanSoap> soapModels =
			new ArrayList<BatchPlannerPlanSoap>(models.size());

		for (BatchPlannerPlan model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new BatchPlannerPlanSoap[soapModels.size()]);
	}

	public BatchPlannerPlanSoap() {
	}

	public long getPrimaryKey() {
		return _batchPlannerPlanId;
	}

	public void setPrimaryKey(long pk) {
		setBatchPlannerPlanId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getBatchPlannerPlanId() {
		return _batchPlannerPlanId;
	}

	public void setBatchPlannerPlanId(long batchPlannerPlanId) {
		_batchPlannerPlanId = batchPlannerPlanId;
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

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public boolean getExport() {
		return _export;
	}

	public boolean isExport() {
		return _export;
	}

	public void setExport(boolean export) {
		_export = export;
	}

	public String getExternalType() {
		return _externalType;
	}

	public void setExternalType(String externalType) {
		_externalType = externalType;
	}

	public String getExternalURL() {
		return _externalURL;
	}

	public void setExternalURL(String externalURL) {
		_externalURL = externalURL;
	}

	public String getInternalClassName() {
		return _internalClassName;
	}

	public void setInternalClassName(String internalClassName) {
		_internalClassName = internalClassName;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getTaskItemDelegateName() {
		return _taskItemDelegateName;
	}

	public void setTaskItemDelegateName(String taskItemDelegateName) {
		_taskItemDelegateName = taskItemDelegateName;
	}

	public boolean getTemplate() {
		return _template;
	}

	public boolean isTemplate() {
		return _template;
	}

	public void setTemplate(boolean template) {
		_template = template;
	}

	private long _mvccVersion;
	private long _batchPlannerPlanId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _active;
	private boolean _export;
	private String _externalType;
	private String _externalURL;
	private String _internalClassName;
	private String _name;
	private String _taskItemDelegateName;
	private boolean _template;

}