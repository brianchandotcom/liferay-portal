/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ExportImportConfiguration}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportConfiguration
 * @generated
 */
public class ExportImportConfigurationWrapper
	extends BaseModelWrapper<ExportImportConfiguration>
	implements ExportImportConfiguration,
			   ModelWrapper<ExportImportConfiguration> {

	public ExportImportConfigurationWrapper(
		ExportImportConfiguration exportImportConfiguration) {

		super(exportImportConfiguration);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"exportImportConfigurationId", getExportImportConfigurationId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("type", getType());
		attributes.put("settings", getSettings());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long exportImportConfigurationId = (Long)attributes.get(
			"exportImportConfigurationId");

		if (exportImportConfigurationId != null) {
			setExportImportConfigurationId(exportImportConfigurationId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String settings = (String)attributes.get("settings");

		if (settings != null) {
			setSettings(settings);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	@Override
	public ExportImportConfiguration cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this export import configuration.
	 *
	 * @return the company ID of this export import configuration
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this export import configuration.
	 *
	 * @return the create date of this export import configuration
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the description of this export import configuration.
	 *
	 * @return the description of this export import configuration
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the export import configuration ID of this export import configuration.
	 *
	 * @return the export import configuration ID of this export import configuration
	 */
	@Override
	public long getExportImportConfigurationId() {
		return model.getExportImportConfigurationId();
	}

	/**
	 * Returns the group ID of this export import configuration.
	 *
	 * @return the group ID of this export import configuration
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this export import configuration.
	 *
	 * @return the modified date of this export import configuration
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this export import configuration.
	 *
	 * @return the mvcc version of this export import configuration
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this export import configuration.
	 *
	 * @return the name of this export import configuration
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this export import configuration.
	 *
	 * @return the primary key of this export import configuration
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the settings of this export import configuration.
	 *
	 * @return the settings of this export import configuration
	 */
	@Override
	public String getSettings() {
		return model.getSettings();
	}

	@Override
	public Map<String, Serializable> getSettingsMap() {
		return model.getSettingsMap();
	}

	/**
	 * Returns the status of this export import configuration.
	 *
	 * @return the status of this export import configuration
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this export import configuration.
	 *
	 * @return the status by user ID of this export import configuration
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this export import configuration.
	 *
	 * @return the status by user name of this export import configuration
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this export import configuration.
	 *
	 * @return the status by user uuid of this export import configuration
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this export import configuration.
	 *
	 * @return the status date of this export import configuration
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the class primary key of the trash entry for this export import configuration.
	 *
	 * @return the class primary key of the trash entry for this export import configuration
	 */
	@Override
	public long getTrashEntryClassPK() {
		return model.getTrashEntryClassPK();
	}

	/**
	 * Returns the type of this export import configuration.
	 *
	 * @return the type of this export import configuration
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this export import configuration.
	 *
	 * @return the user ID of this export import configuration
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this export import configuration.
	 *
	 * @return the user name of this export import configuration
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this export import configuration.
	 *
	 * @return the user uuid of this export import configuration
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this export import configuration is approved.
	 *
	 * @return <code>true</code> if this export import configuration is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this export import configuration is denied.
	 *
	 * @return <code>true</code> if this export import configuration is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this export import configuration is a draft.
	 *
	 * @return <code>true</code> if this export import configuration is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this export import configuration is expired.
	 *
	 * @return <code>true</code> if this export import configuration is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this export import configuration is inactive.
	 *
	 * @return <code>true</code> if this export import configuration is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this export import configuration is incomplete.
	 *
	 * @return <code>true</code> if this export import configuration is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this export import configuration is in the Recycle Bin.
	 *
	 * @return <code>true</code> if this export import configuration is in the Recycle Bin; <code>false</code> otherwise
	 */
	@Override
	public boolean isInTrash() {
		return model.isInTrash();
	}

	/**
	 * Returns <code>true</code> if this export import configuration is pending.
	 *
	 * @return <code>true</code> if this export import configuration is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this export import configuration is scheduled.
	 *
	 * @return <code>true</code> if this export import configuration is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this export import configuration.
	 *
	 * @param companyId the company ID of this export import configuration
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this export import configuration.
	 *
	 * @param createDate the create date of this export import configuration
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this export import configuration.
	 *
	 * @param description the description of this export import configuration
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the export import configuration ID of this export import configuration.
	 *
	 * @param exportImportConfigurationId the export import configuration ID of this export import configuration
	 */
	@Override
	public void setExportImportConfigurationId(
		long exportImportConfigurationId) {

		model.setExportImportConfigurationId(exportImportConfigurationId);
	}

	/**
	 * Sets the group ID of this export import configuration.
	 *
	 * @param groupId the group ID of this export import configuration
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this export import configuration.
	 *
	 * @param modifiedDate the modified date of this export import configuration
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this export import configuration.
	 *
	 * @param mvccVersion the mvcc version of this export import configuration
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this export import configuration.
	 *
	 * @param name the name of this export import configuration
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this export import configuration.
	 *
	 * @param primaryKey the primary key of this export import configuration
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the settings of this export import configuration.
	 *
	 * @param settings the settings of this export import configuration
	 */
	@Override
	public void setSettings(String settings) {
		model.setSettings(settings);
	}

	/**
	 * Sets the status of this export import configuration.
	 *
	 * @param status the status of this export import configuration
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this export import configuration.
	 *
	 * @param statusByUserId the status by user ID of this export import configuration
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this export import configuration.
	 *
	 * @param statusByUserName the status by user name of this export import configuration
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this export import configuration.
	 *
	 * @param statusByUserUuid the status by user uuid of this export import configuration
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this export import configuration.
	 *
	 * @param statusDate the status date of this export import configuration
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the type of this export import configuration.
	 *
	 * @param type the type of this export import configuration
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this export import configuration.
	 *
	 * @param userId the user ID of this export import configuration
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this export import configuration.
	 *
	 * @param userName the user name of this export import configuration
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this export import configuration.
	 *
	 * @param userUuid the user uuid of this export import configuration
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected ExportImportConfigurationWrapper wrap(
		ExportImportConfiguration exportImportConfiguration) {

		return new ExportImportConfigurationWrapper(exportImportConfiguration);
	}

}