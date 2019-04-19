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

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <p>
 * This class is a wrapper for {@link BatchFileImport}.
 * </p>
 *
 * @author Ivica Cardic
 * @see BatchFileImport
 * @generated
 */
@ProviderType
public class BatchFileImportWrapper
	extends BaseModelWrapper<BatchFileImport>
	implements BatchFileImport, ModelWrapper<BatchFileImport> {

	public BatchFileImportWrapper(BatchFileImport batchFileImport) {
		super(batchFileImport);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("batchFileImportId", getBatchFileImportId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("batchJobExecutionId", getBatchJobExecutionId());
		attributes.put("domainName", getDomainName());
		attributes.put("version", getVersion());
		attributes.put("operation", getOperation());
		attributes.put("callbackURL", getCallbackURL());
		attributes.put("columnNames", getColumnNames());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long batchFileImportId = (Long)attributes.get("batchFileImportId");

		if (batchFileImportId != null) {
			setBatchFileImportId(batchFileImportId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		Long batchJobExecutionId = (Long)attributes.get("batchJobExecutionId");

		if (batchJobExecutionId != null) {
			setBatchJobExecutionId(batchJobExecutionId);
		}

		String domainName = (String)attributes.get("domainName");

		if (domainName != null) {
			setDomainName(domainName);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		String operation = (String)attributes.get("operation");

		if (operation != null) {
			setOperation(operation);
		}

		String callbackURL = (String)attributes.get("callbackURL");

		if (callbackURL != null) {
			setCallbackURL(callbackURL);
		}

		String columnNames = (String)attributes.get("columnNames");

		if (columnNames != null) {
			setColumnNames(columnNames);
		}

		String status = (String)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	/**
	 * Returns the batch file import ID of this batch file import.
	 *
	 * @return the batch file import ID of this batch file import
	 */
	@Override
	public long getBatchFileImportId() {
		return model.getBatchFileImportId();
	}

	/**
	 * Returns the batch job execution ID of this batch file import.
	 *
	 * @return the batch job execution ID of this batch file import
	 */
	@Override
	public long getBatchJobExecutionId() {
		return model.getBatchJobExecutionId();
	}

	/**
	 * Returns the callback url of this batch file import.
	 *
	 * @return the callback url of this batch file import
	 */
	@Override
	public String getCallbackURL() {
		return model.getCallbackURL();
	}

	/**
	 * Returns the column names of this batch file import.
	 *
	 * @return the column names of this batch file import
	 */
	@Override
	public String getColumnNames() {
		return model.getColumnNames();
	}

	/**
	 * Returns the company ID of this batch file import.
	 *
	 * @return the company ID of this batch file import
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this batch file import.
	 *
	 * @return the create date of this batch file import
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the domain name of this batch file import.
	 *
	 * @return the domain name of this batch file import
	 */
	@Override
	public String getDomainName() {
		return model.getDomainName();
	}

	/**
	 * Returns the file entry ID of this batch file import.
	 *
	 * @return the file entry ID of this batch file import
	 */
	@Override
	public long getFileEntryId() {
		return model.getFileEntryId();
	}

	/**
	 * Returns the modified date of this batch file import.
	 *
	 * @return the modified date of this batch file import
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the operation of this batch file import.
	 *
	 * @return the operation of this batch file import
	 */
	@Override
	public String getOperation() {
		return model.getOperation();
	}

	/**
	 * Returns the primary key of this batch file import.
	 *
	 * @return the primary key of this batch file import
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the status of this batch file import.
	 *
	 * @return the status of this batch file import
	 */
	@Override
	public String getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the uuid of this batch file import.
	 *
	 * @return the uuid of this batch file import
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the version of this batch file import.
	 *
	 * @return the version of this batch file import
	 */
	@Override
	public String getVersion() {
		return model.getVersion();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the batch file import ID of this batch file import.
	 *
	 * @param batchFileImportId the batch file import ID of this batch file import
	 */
	@Override
	public void setBatchFileImportId(long batchFileImportId) {
		model.setBatchFileImportId(batchFileImportId);
	}

	/**
	 * Sets the batch job execution ID of this batch file import.
	 *
	 * @param batchJobExecutionId the batch job execution ID of this batch file import
	 */
	@Override
	public void setBatchJobExecutionId(long batchJobExecutionId) {
		model.setBatchJobExecutionId(batchJobExecutionId);
	}

	/**
	 * Sets the callback url of this batch file import.
	 *
	 * @param callbackURL the callback url of this batch file import
	 */
	@Override
	public void setCallbackURL(String callbackURL) {
		model.setCallbackURL(callbackURL);
	}

	/**
	 * Sets the column names of this batch file import.
	 *
	 * @param columnNames the column names of this batch file import
	 */
	@Override
	public void setColumnNames(String columnNames) {
		model.setColumnNames(columnNames);
	}

	/**
	 * Sets the company ID of this batch file import.
	 *
	 * @param companyId the company ID of this batch file import
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this batch file import.
	 *
	 * @param createDate the create date of this batch file import
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the domain name of this batch file import.
	 *
	 * @param domainName the domain name of this batch file import
	 */
	@Override
	public void setDomainName(String domainName) {
		model.setDomainName(domainName);
	}

	/**
	 * Sets the file entry ID of this batch file import.
	 *
	 * @param fileEntryId the file entry ID of this batch file import
	 */
	@Override
	public void setFileEntryId(long fileEntryId) {
		model.setFileEntryId(fileEntryId);
	}

	/**
	 * Sets the modified date of this batch file import.
	 *
	 * @param modifiedDate the modified date of this batch file import
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the operation of this batch file import.
	 *
	 * @param operation the operation of this batch file import
	 */
	@Override
	public void setOperation(String operation) {
		model.setOperation(operation);
	}

	/**
	 * Sets the primary key of this batch file import.
	 *
	 * @param primaryKey the primary key of this batch file import
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the status of this batch file import.
	 *
	 * @param status the status of this batch file import
	 */
	@Override
	public void setStatus(String status) {
		model.setStatus(status);
	}

	/**
	 * Sets the uuid of this batch file import.
	 *
	 * @param uuid the uuid of this batch file import
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the version of this batch file import.
	 *
	 * @param version the version of this batch file import
	 */
	@Override
	public void setVersion(String version) {
		model.setVersion(version);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected BatchFileImportWrapper wrap(BatchFileImport batchFileImport) {
		return new BatchFileImportWrapper(batchFileImport);
	}

}