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

package com.liferay.app.builder.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AppBuilderAppVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppVersion
 * @generated
 */
public class AppBuilderAppVersionWrapper
	extends BaseModelWrapper<AppBuilderAppVersion>
	implements AppBuilderAppVersion, ModelWrapper<AppBuilderAppVersion> {

	public AppBuilderAppVersionWrapper(
		AppBuilderAppVersion appBuilderAppVersion) {

		super(appBuilderAppVersion);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("appBuilderAppVersionId", getAppBuilderAppVersionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("appBuilderAppId", getAppBuilderAppId());
		attributes.put("ddlRecordSetId", getDdlRecordSetId());
		attributes.put("ddmStructureId", getDdmStructureId());
		attributes.put("ddmStructureLayoutId", getDdmStructureLayoutId());
		attributes.put("version", getVersion());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long appBuilderAppVersionId = (Long)attributes.get(
			"appBuilderAppVersionId");

		if (appBuilderAppVersionId != null) {
			setAppBuilderAppVersionId(appBuilderAppVersionId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
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

		Long appBuilderAppId = (Long)attributes.get("appBuilderAppId");

		if (appBuilderAppId != null) {
			setAppBuilderAppId(appBuilderAppId);
		}

		Long ddlRecordSetId = (Long)attributes.get("ddlRecordSetId");

		if (ddlRecordSetId != null) {
			setDdlRecordSetId(ddlRecordSetId);
		}

		Long ddmStructureId = (Long)attributes.get("ddmStructureId");

		if (ddmStructureId != null) {
			setDdmStructureId(ddmStructureId);
		}

		Long ddmStructureLayoutId = (Long)attributes.get(
			"ddmStructureLayoutId");

		if (ddmStructureLayoutId != null) {
			setDdmStructureLayoutId(ddmStructureLayoutId);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}
	}

	/**
	 * Returns the app builder app ID of this app builder app version.
	 *
	 * @return the app builder app ID of this app builder app version
	 */
	@Override
	public long getAppBuilderAppId() {
		return model.getAppBuilderAppId();
	}

	/**
	 * Returns the app builder app version ID of this app builder app version.
	 *
	 * @return the app builder app version ID of this app builder app version
	 */
	@Override
	public long getAppBuilderAppVersionId() {
		return model.getAppBuilderAppVersionId();
	}

	/**
	 * Returns the company ID of this app builder app version.
	 *
	 * @return the company ID of this app builder app version
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this app builder app version.
	 *
	 * @return the create date of this app builder app version
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ddl record set ID of this app builder app version.
	 *
	 * @return the ddl record set ID of this app builder app version
	 */
	@Override
	public long getDdlRecordSetId() {
		return model.getDdlRecordSetId();
	}

	/**
	 * Returns the ddm structure ID of this app builder app version.
	 *
	 * @return the ddm structure ID of this app builder app version
	 */
	@Override
	public long getDdmStructureId() {
		return model.getDdmStructureId();
	}

	/**
	 * Returns the ddm structure layout ID of this app builder app version.
	 *
	 * @return the ddm structure layout ID of this app builder app version
	 */
	@Override
	public long getDdmStructureLayoutId() {
		return model.getDdmStructureLayoutId();
	}

	/**
	 * Returns the group ID of this app builder app version.
	 *
	 * @return the group ID of this app builder app version
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this app builder app version.
	 *
	 * @return the modified date of this app builder app version
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this app builder app version.
	 *
	 * @return the primary key of this app builder app version
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this app builder app version.
	 *
	 * @return the user ID of this app builder app version
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this app builder app version.
	 *
	 * @return the user name of this app builder app version
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this app builder app version.
	 *
	 * @return the user uuid of this app builder app version
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this app builder app version.
	 *
	 * @return the uuid of this app builder app version
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the version of this app builder app version.
	 *
	 * @return the version of this app builder app version
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
	 * Sets the app builder app ID of this app builder app version.
	 *
	 * @param appBuilderAppId the app builder app ID of this app builder app version
	 */
	@Override
	public void setAppBuilderAppId(long appBuilderAppId) {
		model.setAppBuilderAppId(appBuilderAppId);
	}

	/**
	 * Sets the app builder app version ID of this app builder app version.
	 *
	 * @param appBuilderAppVersionId the app builder app version ID of this app builder app version
	 */
	@Override
	public void setAppBuilderAppVersionId(long appBuilderAppVersionId) {
		model.setAppBuilderAppVersionId(appBuilderAppVersionId);
	}

	/**
	 * Sets the company ID of this app builder app version.
	 *
	 * @param companyId the company ID of this app builder app version
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this app builder app version.
	 *
	 * @param createDate the create date of this app builder app version
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ddl record set ID of this app builder app version.
	 *
	 * @param ddlRecordSetId the ddl record set ID of this app builder app version
	 */
	@Override
	public void setDdlRecordSetId(long ddlRecordSetId) {
		model.setDdlRecordSetId(ddlRecordSetId);
	}

	/**
	 * Sets the ddm structure ID of this app builder app version.
	 *
	 * @param ddmStructureId the ddm structure ID of this app builder app version
	 */
	@Override
	public void setDdmStructureId(long ddmStructureId) {
		model.setDdmStructureId(ddmStructureId);
	}

	/**
	 * Sets the ddm structure layout ID of this app builder app version.
	 *
	 * @param ddmStructureLayoutId the ddm structure layout ID of this app builder app version
	 */
	@Override
	public void setDdmStructureLayoutId(long ddmStructureLayoutId) {
		model.setDdmStructureLayoutId(ddmStructureLayoutId);
	}

	/**
	 * Sets the group ID of this app builder app version.
	 *
	 * @param groupId the group ID of this app builder app version
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this app builder app version.
	 *
	 * @param modifiedDate the modified date of this app builder app version
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this app builder app version.
	 *
	 * @param primaryKey the primary key of this app builder app version
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this app builder app version.
	 *
	 * @param userId the user ID of this app builder app version
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this app builder app version.
	 *
	 * @param userName the user name of this app builder app version
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this app builder app version.
	 *
	 * @param userUuid the user uuid of this app builder app version
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this app builder app version.
	 *
	 * @param uuid the uuid of this app builder app version
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the version of this app builder app version.
	 *
	 * @param version the version of this app builder app version
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
	protected AppBuilderAppVersionWrapper wrap(
		AppBuilderAppVersion appBuilderAppVersion) {

		return new AppBuilderAppVersionWrapper(appBuilderAppVersion);
	}

}