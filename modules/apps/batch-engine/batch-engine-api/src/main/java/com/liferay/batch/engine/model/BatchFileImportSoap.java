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

import org.osgi.annotation.versioning.ProviderType;

/**
 * This class is used by SOAP remote services.
 *
 * @author Ivica Cardic
 * @generated
 */
@ProviderType
public class BatchFileImportSoap implements Serializable {

	public static BatchFileImportSoap toSoapModel(BatchFileImport model) {
		BatchFileImportSoap soapModel = new BatchFileImportSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setBatchFileImportId(model.getBatchFileImportId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setFileEntryId(model.getFileEntryId());
		soapModel.setBatchJobExecutionId(model.getBatchJobExecutionId());
		soapModel.setDomainName(model.getDomainName());
		soapModel.setVersion(model.getVersion());
		soapModel.setOperation(model.getOperation());
		soapModel.setCallbackURL(model.getCallbackURL());
		soapModel.setColumnNames(model.getColumnNames());
		soapModel.setStatus(model.getStatus());

		return soapModel;
	}

	public static BatchFileImportSoap[] toSoapModels(BatchFileImport[] models) {
		BatchFileImportSoap[] soapModels =
			new BatchFileImportSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static BatchFileImportSoap[][] toSoapModels(
		BatchFileImport[][] models) {

		BatchFileImportSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new BatchFileImportSoap[models.length][models[0].length];
		}
		else {
			soapModels = new BatchFileImportSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static BatchFileImportSoap[] toSoapModels(
		List<BatchFileImport> models) {

		List<BatchFileImportSoap> soapModels =
			new ArrayList<BatchFileImportSoap>(models.size());

		for (BatchFileImport model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new BatchFileImportSoap[soapModels.size()]);
	}

	public BatchFileImportSoap() {
	}

	public long getPrimaryKey() {
		return _batchFileImportId;
	}

	public void setPrimaryKey(long pk) {
		setBatchFileImportId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getBatchFileImportId() {
		return _batchFileImportId;
	}

	public void setBatchFileImportId(long batchFileImportId) {
		_batchFileImportId = batchFileImportId;
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

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
	}

	public long getBatchJobExecutionId() {
		return _batchJobExecutionId;
	}

	public void setBatchJobExecutionId(long batchJobExecutionId) {
		_batchJobExecutionId = batchJobExecutionId;
	}

	public String getDomainName() {
		return _domainName;
	}

	public void setDomainName(String domainName) {
		_domainName = domainName;
	}

	public String getVersion() {
		return _version;
	}

	public void setVersion(String version) {
		_version = version;
	}

	public String getOperation() {
		return _operation;
	}

	public void setOperation(String operation) {
		_operation = operation;
	}

	public String getCallbackURL() {
		return _callbackURL;
	}

	public void setCallbackURL(String callbackURL) {
		_callbackURL = callbackURL;
	}

	public String getColumnNames() {
		return _columnNames;
	}

	public void setColumnNames(String columnNames) {
		_columnNames = columnNames;
	}

	public String getStatus() {
		return _status;
	}

	public void setStatus(String status) {
		_status = status;
	}

	private String _uuid;
	private long _batchFileImportId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _fileEntryId;
	private long _batchJobExecutionId;
	private String _domainName;
	private String _version;
	private String _operation;
	private String _callbackURL;
	private String _columnNames;
	private String _status;

}