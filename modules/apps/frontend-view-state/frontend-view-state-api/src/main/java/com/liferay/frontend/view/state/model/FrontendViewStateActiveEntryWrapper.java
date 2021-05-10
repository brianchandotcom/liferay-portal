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

package com.liferay.frontend.view.state.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link FrontendViewStateActiveEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FrontendViewStateActiveEntry
 * @generated
 */
public class FrontendViewStateActiveEntryWrapper
	extends BaseModelWrapper<FrontendViewStateActiveEntry>
	implements FrontendViewStateActiveEntry,
			   ModelWrapper<FrontendViewStateActiveEntry> {

	public FrontendViewStateActiveEntryWrapper(
		FrontendViewStateActiveEntry frontendViewStateActiveEntry) {

		super(frontendViewStateActiveEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put(
			"frontendViewStateActiveEntryId",
			getFrontendViewStateActiveEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("datasetDisplayId", getDatasetDisplayId());
		attributes.put(
			"frontendViewStateEntryId", getFrontendViewStateEntryId());
		attributes.put("plid", getPlid());
		attributes.put("portletId", getPortletId());

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

		Long frontendViewStateActiveEntryId = (Long)attributes.get(
			"frontendViewStateActiveEntryId");

		if (frontendViewStateActiveEntryId != null) {
			setFrontendViewStateActiveEntryId(frontendViewStateActiveEntryId);
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

		String datasetDisplayId = (String)attributes.get("datasetDisplayId");

		if (datasetDisplayId != null) {
			setDatasetDisplayId(datasetDisplayId);
		}

		Long frontendViewStateEntryId = (Long)attributes.get(
			"frontendViewStateEntryId");

		if (frontendViewStateEntryId != null) {
			setFrontendViewStateEntryId(frontendViewStateEntryId);
		}

		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
		}

		String portletId = (String)attributes.get("portletId");

		if (portletId != null) {
			setPortletId(portletId);
		}
	}

	/**
	 * Returns the company ID of this frontend view state active entry.
	 *
	 * @return the company ID of this frontend view state active entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the container model ID of this frontend view state active entry.
	 *
	 * @return the container model ID of this frontend view state active entry
	 */
	@Override
	public long getContainerModelId() {
		return model.getContainerModelId();
	}

	/**
	 * Returns the container name of this frontend view state active entry.
	 *
	 * @return the container name of this frontend view state active entry
	 */
	@Override
	public String getContainerModelName() {
		return model.getContainerModelName();
	}

	/**
	 * Returns the create date of this frontend view state active entry.
	 *
	 * @return the create date of this frontend view state active entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the dataset display ID of this frontend view state active entry.
	 *
	 * @return the dataset display ID of this frontend view state active entry
	 */
	@Override
	public String getDatasetDisplayId() {
		return model.getDatasetDisplayId();
	}

	/**
	 * Returns the frontend view state active entry ID of this frontend view state active entry.
	 *
	 * @return the frontend view state active entry ID of this frontend view state active entry
	 */
	@Override
	public long getFrontendViewStateActiveEntryId() {
		return model.getFrontendViewStateActiveEntryId();
	}

	/**
	 * Returns the frontend view state entry ID of this frontend view state active entry.
	 *
	 * @return the frontend view state entry ID of this frontend view state active entry
	 */
	@Override
	public long getFrontendViewStateEntryId() {
		return model.getFrontendViewStateEntryId();
	}

	/**
	 * Returns the modified date of this frontend view state active entry.
	 *
	 * @return the modified date of this frontend view state active entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this frontend view state active entry.
	 *
	 * @return the mvcc version of this frontend view state active entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the parent container model ID of this frontend view state active entry.
	 *
	 * @return the parent container model ID of this frontend view state active entry
	 */
	@Override
	public long getParentContainerModelId() {
		return model.getParentContainerModelId();
	}

	/**
	 * Returns the plid of this frontend view state active entry.
	 *
	 * @return the plid of this frontend view state active entry
	 */
	@Override
	public long getPlid() {
		return model.getPlid();
	}

	/**
	 * Returns the portlet ID of this frontend view state active entry.
	 *
	 * @return the portlet ID of this frontend view state active entry
	 */
	@Override
	public String getPortletId() {
		return model.getPortletId();
	}

	/**
	 * Returns the primary key of this frontend view state active entry.
	 *
	 * @return the primary key of this frontend view state active entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this frontend view state active entry.
	 *
	 * @return the user ID of this frontend view state active entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this frontend view state active entry.
	 *
	 * @return the user name of this frontend view state active entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this frontend view state active entry.
	 *
	 * @return the user uuid of this frontend view state active entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this frontend view state active entry.
	 *
	 * @return the uuid of this frontend view state active entry
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
	 * Sets the company ID of this frontend view state active entry.
	 *
	 * @param companyId the company ID of this frontend view state active entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the container model ID of this frontend view state active entry.
	 *
	 * @param containerModelId the container model ID of this frontend view state active entry
	 */
	@Override
	public void setContainerModelId(long containerModelId) {
		model.setContainerModelId(containerModelId);
	}

	/**
	 * Sets the create date of this frontend view state active entry.
	 *
	 * @param createDate the create date of this frontend view state active entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the dataset display ID of this frontend view state active entry.
	 *
	 * @param datasetDisplayId the dataset display ID of this frontend view state active entry
	 */
	@Override
	public void setDatasetDisplayId(String datasetDisplayId) {
		model.setDatasetDisplayId(datasetDisplayId);
	}

	/**
	 * Sets the frontend view state active entry ID of this frontend view state active entry.
	 *
	 * @param frontendViewStateActiveEntryId the frontend view state active entry ID of this frontend view state active entry
	 */
	@Override
	public void setFrontendViewStateActiveEntryId(
		long frontendViewStateActiveEntryId) {

		model.setFrontendViewStateActiveEntryId(frontendViewStateActiveEntryId);
	}

	/**
	 * Sets the frontend view state entry ID of this frontend view state active entry.
	 *
	 * @param frontendViewStateEntryId the frontend view state entry ID of this frontend view state active entry
	 */
	@Override
	public void setFrontendViewStateEntryId(long frontendViewStateEntryId) {
		model.setFrontendViewStateEntryId(frontendViewStateEntryId);
	}

	/**
	 * Sets the modified date of this frontend view state active entry.
	 *
	 * @param modifiedDate the modified date of this frontend view state active entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this frontend view state active entry.
	 *
	 * @param mvccVersion the mvcc version of this frontend view state active entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the parent container model ID of this frontend view state active entry.
	 *
	 * @param parentContainerModelId the parent container model ID of this frontend view state active entry
	 */
	@Override
	public void setParentContainerModelId(long parentContainerModelId) {
		model.setParentContainerModelId(parentContainerModelId);
	}

	/**
	 * Sets the plid of this frontend view state active entry.
	 *
	 * @param plid the plid of this frontend view state active entry
	 */
	@Override
	public void setPlid(long plid) {
		model.setPlid(plid);
	}

	/**
	 * Sets the portlet ID of this frontend view state active entry.
	 *
	 * @param portletId the portlet ID of this frontend view state active entry
	 */
	@Override
	public void setPortletId(String portletId) {
		model.setPortletId(portletId);
	}

	/**
	 * Sets the primary key of this frontend view state active entry.
	 *
	 * @param primaryKey the primary key of this frontend view state active entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this frontend view state active entry.
	 *
	 * @param userId the user ID of this frontend view state active entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this frontend view state active entry.
	 *
	 * @param userName the user name of this frontend view state active entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this frontend view state active entry.
	 *
	 * @param userUuid the user uuid of this frontend view state active entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this frontend view state active entry.
	 *
	 * @param uuid the uuid of this frontend view state active entry
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
	protected FrontendViewStateActiveEntryWrapper wrap(
		FrontendViewStateActiveEntry frontendViewStateActiveEntry) {

		return new FrontendViewStateActiveEntryWrapper(
			frontendViewStateActiveEntry);
	}

}