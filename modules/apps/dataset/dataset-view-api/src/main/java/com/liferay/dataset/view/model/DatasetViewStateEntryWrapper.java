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

package com.liferay.dataset.view.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DatasetViewStateEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DatasetViewStateEntry
 * @generated
 */
public class DatasetViewStateEntryWrapper
	extends BaseModelWrapper<DatasetViewStateEntry>
	implements DatasetViewStateEntry, ModelWrapper<DatasetViewStateEntry> {

	public DatasetViewStateEntryWrapper(
		DatasetViewStateEntry datasetViewStateEntry) {

		super(datasetViewStateEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("datasetViewStateEntryId", getDatasetViewStateEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("json", getJson());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long datasetViewStateEntryId = (Long)attributes.get(
			"datasetViewStateEntryId");

		if (datasetViewStateEntryId != null) {
			setDatasetViewStateEntryId(datasetViewStateEntryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String json = (String)attributes.get("json");

		if (json != null) {
			setJson(json);
		}
	}

	/**
	 * Returns the company ID of this dataset view state entry.
	 *
	 * @return the company ID of this dataset view state entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the container model ID of this dataset view state entry.
	 *
	 * @return the container model ID of this dataset view state entry
	 */
	@Override
	public long getContainerModelId() {
		return model.getContainerModelId();
	}

	/**
	 * Returns the container name of this dataset view state entry.
	 *
	 * @return the container name of this dataset view state entry
	 */
	@Override
	public String getContainerModelName() {
		return model.getContainerModelName();
	}

	/**
	 * Returns the create date of this dataset view state entry.
	 *
	 * @return the create date of this dataset view state entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the dataset view state entry ID of this dataset view state entry.
	 *
	 * @return the dataset view state entry ID of this dataset view state entry
	 */
	@Override
	public long getDatasetViewStateEntryId() {
		return model.getDatasetViewStateEntryId();
	}

	/**
	 * Returns the json of this dataset view state entry.
	 *
	 * @return the json of this dataset view state entry
	 */
	@Override
	public String getJson() {
		return model.getJson();
	}

	/**
	 * Returns the modified date of this dataset view state entry.
	 *
	 * @return the modified date of this dataset view state entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this dataset view state entry.
	 *
	 * @return the mvcc version of this dataset view state entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the parent container model ID of this dataset view state entry.
	 *
	 * @return the parent container model ID of this dataset view state entry
	 */
	@Override
	public long getParentContainerModelId() {
		return model.getParentContainerModelId();
	}

	/**
	 * Returns the primary key of this dataset view state entry.
	 *
	 * @return the primary key of this dataset view state entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this dataset view state entry.
	 *
	 * @return the user ID of this dataset view state entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this dataset view state entry.
	 *
	 * @return the user name of this dataset view state entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this dataset view state entry.
	 *
	 * @return the user uuid of this dataset view state entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this dataset view state entry.
	 *
	 * @return the uuid of this dataset view state entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this dataset view state entry.
	 *
	 * @param companyId the company ID of this dataset view state entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the container model ID of this dataset view state entry.
	 *
	 * @param containerModelId the container model ID of this dataset view state entry
	 */
	@Override
	public void setContainerModelId(long containerModelId) {
		model.setContainerModelId(containerModelId);
	}

	/**
	 * Sets the create date of this dataset view state entry.
	 *
	 * @param createDate the create date of this dataset view state entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the dataset view state entry ID of this dataset view state entry.
	 *
	 * @param datasetViewStateEntryId the dataset view state entry ID of this dataset view state entry
	 */
	@Override
	public void setDatasetViewStateEntryId(long datasetViewStateEntryId) {
		model.setDatasetViewStateEntryId(datasetViewStateEntryId);
	}

	/**
	 * Sets the json of this dataset view state entry.
	 *
	 * @param json the json of this dataset view state entry
	 */
	@Override
	public void setJson(String json) {
		model.setJson(json);
	}

	/**
	 * Sets the modified date of this dataset view state entry.
	 *
	 * @param modifiedDate the modified date of this dataset view state entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this dataset view state entry.
	 *
	 * @param mvccVersion the mvcc version of this dataset view state entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the parent container model ID of this dataset view state entry.
	 *
	 * @param parentContainerModelId the parent container model ID of this dataset view state entry
	 */
	@Override
	public void setParentContainerModelId(long parentContainerModelId) {
		model.setParentContainerModelId(parentContainerModelId);
	}

	/**
	 * Sets the primary key of this dataset view state entry.
	 *
	 * @param primaryKey the primary key of this dataset view state entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this dataset view state entry.
	 *
	 * @param userId the user ID of this dataset view state entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this dataset view state entry.
	 *
	 * @param userName the user name of this dataset view state entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this dataset view state entry.
	 *
	 * @param userUuid the user uuid of this dataset view state entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this dataset view state entry.
	 *
	 * @param uuid the uuid of this dataset view state entry
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected DatasetViewStateEntryWrapper wrap(
		DatasetViewStateEntry datasetViewStateEntry) {

		return new DatasetViewStateEntryWrapper(datasetViewStateEntry);
	}

}