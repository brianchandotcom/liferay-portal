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
 * This class is used by SOAP remote services, specifically {@link com.liferay.batch.planner.service.http.BatchPlannerLogServiceSoap}.
 *
 * @author Igor Beslic
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class BatchPlannerLogSoap implements Serializable {

	public static BatchPlannerLogSoap toSoapModel(BatchPlannerLog model) {
		BatchPlannerLogSoap soapModel = new BatchPlannerLogSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setBatchPlannerLogId(model.getBatchPlannerLogId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setBatchPlannerPlanId(model.getBatchPlannerPlanId());
		soapModel.setBatchEngineTaskERC(model.getBatchEngineTaskERC());
		soapModel.setDispatchTriggerERC(model.getDispatchTriggerERC());
		soapModel.setSize(model.getSize());
		soapModel.setTotal(model.getTotal());
		soapModel.setStatus(model.getStatus());

		return soapModel;
	}

	public static BatchPlannerLogSoap[] toSoapModels(BatchPlannerLog[] models) {
		BatchPlannerLogSoap[] soapModels =
			new BatchPlannerLogSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static BatchPlannerLogSoap[][] toSoapModels(
		BatchPlannerLog[][] models) {

		BatchPlannerLogSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new BatchPlannerLogSoap[models.length][models[0].length];
		}
		else {
			soapModels = new BatchPlannerLogSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static BatchPlannerLogSoap[] toSoapModels(
		List<BatchPlannerLog> models) {

		List<BatchPlannerLogSoap> soapModels =
			new ArrayList<BatchPlannerLogSoap>(models.size());

		for (BatchPlannerLog model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new BatchPlannerLogSoap[soapModels.size()]);
	}

	public BatchPlannerLogSoap() {
	}

	public long getPrimaryKey() {
		return _batchPlannerLogId;
	}

	public void setPrimaryKey(long pk) {
		setBatchPlannerLogId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getBatchPlannerLogId() {
		return _batchPlannerLogId;
	}

	public void setBatchPlannerLogId(long batchPlannerLogId) {
		_batchPlannerLogId = batchPlannerLogId;
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

	public String getBatchEngineTaskERC() {
		return _batchEngineTaskERC;
	}

	public void setBatchEngineTaskERC(String batchEngineTaskERC) {
		_batchEngineTaskERC = batchEngineTaskERC;
	}

	public String getDispatchTriggerERC() {
		return _dispatchTriggerERC;
	}

	public void setDispatchTriggerERC(String dispatchTriggerERC) {
		_dispatchTriggerERC = dispatchTriggerERC;
	}

	public int getSize() {
		return _size;
	}

	public void setSize(int size) {
		_size = size;
	}

	public int getTotal() {
		return _total;
	}

	public void setTotal(int total) {
		_total = total;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	private long _mvccVersion;
	private long _batchPlannerLogId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _batchPlannerPlanId;
	private String _batchEngineTaskERC;
	private String _dispatchTriggerERC;
	private int _size;
	private int _total;
	private int _status;

}