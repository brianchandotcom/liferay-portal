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

package com.liferay.batch.engine.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Shuyang Zhou
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class BatchEngineImportTaskErrorSoap implements Serializable {

	public static BatchEngineImportTaskErrorSoap toSoapModel(
		BatchEngineImportTaskError model) {

		BatchEngineImportTaskErrorSoap soapModel =
			new BatchEngineImportTaskErrorSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setBatchEngineImportTaskErrorId(
			model.getBatchEngineImportTaskErrorId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setBatchEngineImportTaskId(
			model.getBatchEngineImportTaskId());
		soapModel.setItem(model.getItem());
		soapModel.setItemIndex(model.getItemIndex());
		soapModel.setMessage(model.getMessage());

		return soapModel;
	}

	public static BatchEngineImportTaskErrorSoap[] toSoapModels(
		BatchEngineImportTaskError[] models) {

		BatchEngineImportTaskErrorSoap[] soapModels =
			new BatchEngineImportTaskErrorSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static BatchEngineImportTaskErrorSoap[][] toSoapModels(
		BatchEngineImportTaskError[][] models) {

		BatchEngineImportTaskErrorSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new BatchEngineImportTaskErrorSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new BatchEngineImportTaskErrorSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static BatchEngineImportTaskErrorSoap[] toSoapModels(
		List<BatchEngineImportTaskError> models) {

		List<BatchEngineImportTaskErrorSoap> soapModels =
			new ArrayList<BatchEngineImportTaskErrorSoap>(models.size());

		for (BatchEngineImportTaskError model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new BatchEngineImportTaskErrorSoap[soapModels.size()]);
	}

	public BatchEngineImportTaskErrorSoap() {
	}

	public long getPrimaryKey() {
		return _batchEngineImportTaskErrorId;
	}

	public void setPrimaryKey(long pk) {
		setBatchEngineImportTaskErrorId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getBatchEngineImportTaskErrorId() {
		return _batchEngineImportTaskErrorId;
	}

	public void setBatchEngineImportTaskErrorId(
		long batchEngineImportTaskErrorId) {

		_batchEngineImportTaskErrorId = batchEngineImportTaskErrorId;
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

	public long getBatchEngineImportTaskId() {
		return _batchEngineImportTaskId;
	}

	public void setBatchEngineImportTaskId(long batchEngineImportTaskId) {
		_batchEngineImportTaskId = batchEngineImportTaskId;
	}

	public String getItem() {
		return _item;
	}

	public void setItem(String item) {
		_item = item;
	}

	public int getItemIndex() {
		return _itemIndex;
	}

	public void setItemIndex(int itemIndex) {
		_itemIndex = itemIndex;
	}

	public String getMessage() {
		return _message;
	}

	public void setMessage(String message) {
		_message = message;
	}

	private long _mvccVersion;
	private long _batchEngineImportTaskErrorId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _batchEngineImportTaskId;
	private String _item;
	private int _itemIndex;
	private String _message;

}