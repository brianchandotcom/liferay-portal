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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

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
@ProviderType
public class ExportImportConfigurationWrapper
	implements ExportImportConfiguration,
		ModelWrapper<ExportImportConfiguration> {
	public ExportImportConfigurationWrapper(
		ExportImportConfiguration exportImportConfiguration) {
		_exportImportConfiguration = exportImportConfiguration;
	}

	@Override
	public Class<?> getModelClass() {
		return ExportImportConfiguration.class;
	}

	@Override
	public String getModelClassName() {
		return ExportImportConfiguration.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("exportImportConfigurationId",
			getExportImportConfigurationId());
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

	/**
	* Returns the primary key of this export import configuration.
	*
	* @return the primary key of this export import configuration
	*/
	@Override
	public long getPrimaryKey() {
		return _exportImportConfiguration.getPrimaryKey();
	}

	/**
	* Sets the primary key of this export import configuration.
	*
	* @param primaryKey the primary key of this export import configuration
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_exportImportConfiguration.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the mvcc version of this export import configuration.
	*
	* @return the mvcc version of this export import configuration
	*/
	@Override
	public long getMvccVersion() {
		return _exportImportConfiguration.getMvccVersion();
	}

	/**
	* Sets the mvcc version of this export import configuration.
	*
	* @param mvccVersion the mvcc version of this export import configuration
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_exportImportConfiguration.setMvccVersion(mvccVersion);
	}

	/**
	* Returns the export import configuration ID of this export import configuration.
	*
	* @return the export import configuration ID of this export import configuration
	*/
	@Override
	public long getExportImportConfigurationId() {
		return _exportImportConfiguration.getExportImportConfigurationId();
	}

	/**
	* Sets the export import configuration ID of this export import configuration.
	*
	* @param exportImportConfigurationId the export import configuration ID of this export import configuration
	*/
	@Override
	public void setExportImportConfigurationId(long exportImportConfigurationId) {
		_exportImportConfiguration.setExportImportConfigurationId(exportImportConfigurationId);
	}

	/**
	* Returns the group ID of this export import configuration.
	*
	* @return the group ID of this export import configuration
	*/
	@Override
	public long getGroupId() {
		return _exportImportConfiguration.getGroupId();
	}

	/**
	* Sets the group ID of this export import configuration.
	*
	* @param groupId the group ID of this export import configuration
	*/
	@Override
	public void setGroupId(long groupId) {
		_exportImportConfiguration.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this export import configuration.
	*
	* @return the company ID of this export import configuration
	*/
	@Override
	public long getCompanyId() {
		return _exportImportConfiguration.getCompanyId();
	}

	/**
	* Sets the company ID of this export import configuration.
	*
	* @param companyId the company ID of this export import configuration
	*/
	@Override
	public void setCompanyId(long companyId) {
		_exportImportConfiguration.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this export import configuration.
	*
	* @return the user ID of this export import configuration
	*/
	@Override
	public long getUserId() {
		return _exportImportConfiguration.getUserId();
	}

	/**
	* Sets the user ID of this export import configuration.
	*
	* @param userId the user ID of this export import configuration
	*/
	@Override
	public void setUserId(long userId) {
		_exportImportConfiguration.setUserId(userId);
	}

	/**
	* Returns the user uuid of this export import configuration.
	*
	* @return the user uuid of this export import configuration
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _exportImportConfiguration.getUserUuid();
	}

	/**
	* Sets the user uuid of this export import configuration.
	*
	* @param userUuid the user uuid of this export import configuration
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_exportImportConfiguration.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this export import configuration.
	*
	* @return the user name of this export import configuration
	*/
	@Override
	public java.lang.String getUserName() {
		return _exportImportConfiguration.getUserName();
	}

	/**
	* Sets the user name of this export import configuration.
	*
	* @param userName the user name of this export import configuration
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_exportImportConfiguration.setUserName(userName);
	}

	/**
	* Returns the create date of this export import configuration.
	*
	* @return the create date of this export import configuration
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _exportImportConfiguration.getCreateDate();
	}

	/**
	* Sets the create date of this export import configuration.
	*
	* @param createDate the create date of this export import configuration
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_exportImportConfiguration.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this export import configuration.
	*
	* @return the modified date of this export import configuration
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _exportImportConfiguration.getModifiedDate();
	}

	/**
	* Sets the modified date of this export import configuration.
	*
	* @param modifiedDate the modified date of this export import configuration
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_exportImportConfiguration.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the name of this export import configuration.
	*
	* @return the name of this export import configuration
	*/
	@Override
	public java.lang.String getName() {
		return _exportImportConfiguration.getName();
	}

	/**
	* Sets the name of this export import configuration.
	*
	* @param name the name of this export import configuration
	*/
	@Override
	public void setName(java.lang.String name) {
		_exportImportConfiguration.setName(name);
	}

	/**
	* Returns the description of this export import configuration.
	*
	* @return the description of this export import configuration
	*/
	@Override
	public java.lang.String getDescription() {
		return _exportImportConfiguration.getDescription();
	}

	/**
	* Sets the description of this export import configuration.
	*
	* @param description the description of this export import configuration
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_exportImportConfiguration.setDescription(description);
	}

	/**
	* Returns the type of this export import configuration.
	*
	* @return the type of this export import configuration
	*/
	@Override
	public int getType() {
		return _exportImportConfiguration.getType();
	}

	/**
	* Sets the type of this export import configuration.
	*
	* @param type the type of this export import configuration
	*/
	@Override
	public void setType(int type) {
		_exportImportConfiguration.setType(type);
	}

	/**
	* Returns the settings of this export import configuration.
	*
	* @return the settings of this export import configuration
	*/
	@Override
	public java.lang.String getSettings() {
		return _exportImportConfiguration.getSettings();
	}

	/**
	* Sets the settings of this export import configuration.
	*
	* @param settings the settings of this export import configuration
	*/
	@Override
	public void setSettings(java.lang.String settings) {
		_exportImportConfiguration.setSettings(settings);
	}

	/**
	* Returns the status of this export import configuration.
	*
	* @return the status of this export import configuration
	*/
	@Override
	public int getStatus() {
		return _exportImportConfiguration.getStatus();
	}

	/**
	* Sets the status of this export import configuration.
	*
	* @param status the status of this export import configuration
	*/
	@Override
	public void setStatus(int status) {
		_exportImportConfiguration.setStatus(status);
	}

	/**
	* Returns the status by user ID of this export import configuration.
	*
	* @return the status by user ID of this export import configuration
	*/
	@Override
	public long getStatusByUserId() {
		return _exportImportConfiguration.getStatusByUserId();
	}

	/**
	* Sets the status by user ID of this export import configuration.
	*
	* @param statusByUserId the status by user ID of this export import configuration
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_exportImportConfiguration.setStatusByUserId(statusByUserId);
	}

	/**
	* Returns the status by user uuid of this export import configuration.
	*
	* @return the status by user uuid of this export import configuration
	*/
	@Override
	public java.lang.String getStatusByUserUuid() {
		return _exportImportConfiguration.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this export import configuration.
	*
	* @param statusByUserUuid the status by user uuid of this export import configuration
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_exportImportConfiguration.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Returns the status by user name of this export import configuration.
	*
	* @return the status by user name of this export import configuration
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _exportImportConfiguration.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this export import configuration.
	*
	* @param statusByUserName the status by user name of this export import configuration
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_exportImportConfiguration.setStatusByUserName(statusByUserName);
	}

	/**
	* Returns the status date of this export import configuration.
	*
	* @return the status date of this export import configuration
	*/
	@Override
	public java.util.Date getStatusDate() {
		return _exportImportConfiguration.getStatusDate();
	}

	/**
	* Sets the status date of this export import configuration.
	*
	* @param statusDate the status date of this export import configuration
	*/
	@Override
	public void setStatusDate(java.util.Date statusDate) {
		_exportImportConfiguration.setStatusDate(statusDate);
	}

	/**
	* Returns the trash entry created when this export import configuration was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this export import configuration.
	*
	* @return the trash entry created when this export import configuration was moved to the Recycle Bin
	*/
	@Override
	public com.liferay.portlet.trash.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportConfiguration.getTrashEntry();
	}

	/**
	* Returns the class primary key of the trash entry for this export import configuration.
	*
	* @return the class primary key of the trash entry for this export import configuration
	*/
	@Override
	public long getTrashEntryClassPK() {
		return _exportImportConfiguration.getTrashEntryClassPK();
	}

	/**
	* Returns the trash handler for this export import configuration.
	*
	* @return the trash handler for this export import configuration
	*/
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return _exportImportConfiguration.getTrashHandler();
	}

	/**
	* Returns <code>true</code> if this export import configuration is in the Recycle Bin.
	*
	* @return <code>true</code> if this export import configuration is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrash() {
		return _exportImportConfiguration.isInTrash();
	}

	/**
	* Returns <code>true</code> if the parent of this export import configuration is in the Recycle Bin.
	*
	* @return <code>true</code> if the parent of this export import configuration is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrashContainer() {
		return _exportImportConfiguration.isInTrashContainer();
	}

	@Override
	public boolean isInTrashExplicitly() {
		return _exportImportConfiguration.isInTrashExplicitly();
	}

	@Override
	public boolean isInTrashImplicitly() {
		return _exportImportConfiguration.isInTrashImplicitly();
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #isApproved()}
	*/
	@Deprecated
	@Override
	public boolean getApproved() {
		return _exportImportConfiguration.getApproved();
	}

	/**
	* Returns <code>true</code> if this export import configuration is approved.
	*
	* @return <code>true</code> if this export import configuration is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _exportImportConfiguration.isApproved();
	}

	/**
	* Returns <code>true</code> if this export import configuration is denied.
	*
	* @return <code>true</code> if this export import configuration is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _exportImportConfiguration.isDenied();
	}

	/**
	* Returns <code>true</code> if this export import configuration is a draft.
	*
	* @return <code>true</code> if this export import configuration is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _exportImportConfiguration.isDraft();
	}

	/**
	* Returns <code>true</code> if this export import configuration is expired.
	*
	* @return <code>true</code> if this export import configuration is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _exportImportConfiguration.isExpired();
	}

	/**
	* Returns <code>true</code> if this export import configuration is inactive.
	*
	* @return <code>true</code> if this export import configuration is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _exportImportConfiguration.isInactive();
	}

	/**
	* Returns <code>true</code> if this export import configuration is incomplete.
	*
	* @return <code>true</code> if this export import configuration is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _exportImportConfiguration.isIncomplete();
	}

	/**
	* Returns <code>true</code> if this export import configuration is pending.
	*
	* @return <code>true</code> if this export import configuration is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _exportImportConfiguration.isPending();
	}

	/**
	* Returns <code>true</code> if this export import configuration is scheduled.
	*
	* @return <code>true</code> if this export import configuration is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _exportImportConfiguration.isScheduled();
	}

	@Override
	public boolean isNew() {
		return _exportImportConfiguration.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_exportImportConfiguration.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _exportImportConfiguration.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_exportImportConfiguration.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _exportImportConfiguration.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _exportImportConfiguration.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_exportImportConfiguration.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _exportImportConfiguration.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_exportImportConfiguration.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_exportImportConfiguration.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_exportImportConfiguration.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ExportImportConfigurationWrapper((ExportImportConfiguration)_exportImportConfiguration.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration) {
		return _exportImportConfiguration.compareTo(exportImportConfiguration);
	}

	@Override
	public int hashCode() {
		return _exportImportConfiguration.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.ExportImportConfiguration> toCacheModel() {
		return _exportImportConfiguration.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.ExportImportConfiguration toEscapedModel() {
		return new ExportImportConfigurationWrapper(_exportImportConfiguration.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.ExportImportConfiguration toUnescapedModel() {
		return new ExportImportConfigurationWrapper(_exportImportConfiguration.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _exportImportConfiguration.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _exportImportConfiguration.toXmlString();
	}

	@Override
	public void persist() {
		_exportImportConfiguration.persist();
	}

	@Override
	public java.util.Map<java.lang.String, java.io.Serializable> getSettingsMap() {
		return _exportImportConfiguration.getSettingsMap();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ExportImportConfigurationWrapper)) {
			return false;
		}

		ExportImportConfigurationWrapper exportImportConfigurationWrapper = (ExportImportConfigurationWrapper)obj;

		if (Validator.equals(_exportImportConfiguration,
					exportImportConfigurationWrapper._exportImportConfiguration)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public ExportImportConfiguration getWrappedExportImportConfiguration() {
		return _exportImportConfiguration;
	}

	@Override
	public ExportImportConfiguration getWrappedModel() {
		return _exportImportConfiguration;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _exportImportConfiguration.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _exportImportConfiguration.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_exportImportConfiguration.resetOriginalValues();
	}

	private ExportImportConfiguration _exportImportConfiguration;
}