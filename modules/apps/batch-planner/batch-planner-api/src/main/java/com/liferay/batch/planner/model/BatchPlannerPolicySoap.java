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
 * This class is used by SOAP remote services, specifically {@link com.liferay.batch.planner.service.http.BatchPlannerPolicyServiceSoap}.
 *
 * @author Igor Beslic
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class BatchPlannerPolicySoap implements Serializable {

	public static BatchPlannerPolicySoap toSoapModel(BatchPlannerPolicy model) {
		BatchPlannerPolicySoap soapModel = new BatchPlannerPolicySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setBatchPlannerPolicyId(model.getBatchPlannerPolicyId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setBatchPlannerPlanId(model.getBatchPlannerPlanId());
		soapModel.setName(model.getName());
		soapModel.setValue(model.getValue());

		return soapModel;
	}

	public static BatchPlannerPolicySoap[] toSoapModels(
		BatchPlannerPolicy[] models) {

		BatchPlannerPolicySoap[] soapModels =
			new BatchPlannerPolicySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static BatchPlannerPolicySoap[][] toSoapModels(
		BatchPlannerPolicy[][] models) {

		BatchPlannerPolicySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new BatchPlannerPolicySoap[models.length][models[0].length];
		}
		else {
			soapModels = new BatchPlannerPolicySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static BatchPlannerPolicySoap[] toSoapModels(
		List<BatchPlannerPolicy> models) {

		List<BatchPlannerPolicySoap> soapModels =
			new ArrayList<BatchPlannerPolicySoap>(models.size());

		for (BatchPlannerPolicy model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new BatchPlannerPolicySoap[soapModels.size()]);
	}

	public BatchPlannerPolicySoap() {
	}

	public long getPrimaryKey() {
		return _batchPlannerPolicyId;
	}

	public void setPrimaryKey(long pk) {
		setBatchPlannerPolicyId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getBatchPlannerPolicyId() {
		return _batchPlannerPolicyId;
	}

	public void setBatchPlannerPolicyId(long batchPlannerPolicyId) {
		_batchPlannerPolicyId = batchPlannerPolicyId;
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

	public long getBatchPlannerPlanId() {
		return _batchPlannerPlanId;
	}

	public void setBatchPlannerPlanId(long batchPlannerPlanId) {
		_batchPlannerPlanId = batchPlannerPlanId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getValue() {
		return _value;
	}

	public void setValue(String value) {
		_value = value;
	}

	private long _mvccVersion;
	private long _batchPlannerPolicyId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _batchPlannerPlanId;
	private String _name;
	private String _value;

}