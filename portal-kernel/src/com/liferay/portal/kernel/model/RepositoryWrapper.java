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

package com.liferay.portal.kernel.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Repository}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Repository
 * @generated
 */
public class RepositoryWrapper
	extends BaseModelWrapper<Repository>
	implements ModelWrapper<Repository>, Repository {

	public RepositoryWrapper(Repository repository) {
		super(repository);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("repositoryId", getRepositoryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("portletId", getPortletId());
		attributes.put("typeSettings", getTypeSettings());
		attributes.put("dlFolderId", getDlFolderId());
		attributes.put("lastPublishDate", getLastPublishDate());

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

		Long repositoryId = (Long)attributes.get("repositoryId");

		if (repositoryId != null) {
			setRepositoryId(repositoryId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String portletId = (String)attributes.get("portletId");

		if (portletId != null) {
			setPortletId(portletId);
		}

		String typeSettings = (String)attributes.get("typeSettings");

		if (typeSettings != null) {
			setTypeSettings(typeSettings);
		}

		Long dlFolderId = (Long)attributes.get("dlFolderId");

		if (dlFolderId != null) {
			setDlFolderId(dlFolderId);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	/**
	 * Returns the fully qualified class name of this repository.
	 *
	 * @return the fully qualified class name of this repository
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this repository.
	 *
	 * @return the class name ID of this repository
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the company ID of this repository.
	 *
	 * @return the company ID of this repository
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this repository.
	 *
	 * @return the create date of this repository
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the description of this repository.
	 *
	 * @return the description of this repository
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the dl folder ID of this repository.
	 *
	 * @return the dl folder ID of this repository
	 */
	@Override
	public long getDlFolderId() {
		return model.getDlFolderId();
	}

	/**
	 * Returns the group ID of this repository.
	 *
	 * @return the group ID of this repository
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this repository.
	 *
	 * @return the last publish date of this repository
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this repository.
	 *
	 * @return the modified date of this repository
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this repository.
	 *
	 * @return the mvcc version of this repository
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this repository.
	 *
	 * @return the name of this repository
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the portlet ID of this repository.
	 *
	 * @return the portlet ID of this repository
	 */
	@Override
	public String getPortletId() {
		return model.getPortletId();
	}

	/**
	 * Returns the primary key of this repository.
	 *
	 * @return the primary key of this repository
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the repository ID of this repository.
	 *
	 * @return the repository ID of this repository
	 */
	@Override
	public long getRepositoryId() {
		return model.getRepositoryId();
	}

	/**
	 * Returns the type settings of this repository.
	 *
	 * @return the type settings of this repository
	 */
	@Override
	public String getTypeSettings() {
		return model.getTypeSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties
		getTypeSettingsProperties() {

		return model.getTypeSettingsProperties();
	}

	/**
	 * Returns the user ID of this repository.
	 *
	 * @return the user ID of this repository
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this repository.
	 *
	 * @return the user name of this repository
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this repository.
	 *
	 * @return the user uuid of this repository
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this repository.
	 *
	 * @return the uuid of this repository
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a repository model instance should use the <code>Repository</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this repository.
	 *
	 * @param classNameId the class name ID of this repository
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the company ID of this repository.
	 *
	 * @param companyId the company ID of this repository
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this repository.
	 *
	 * @param createDate the create date of this repository
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this repository.
	 *
	 * @param description the description of this repository
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the dl folder ID of this repository.
	 *
	 * @param dlFolderId the dl folder ID of this repository
	 */
	@Override
	public void setDlFolderId(long dlFolderId) {
		model.setDlFolderId(dlFolderId);
	}

	/**
	 * Sets the group ID of this repository.
	 *
	 * @param groupId the group ID of this repository
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this repository.
	 *
	 * @param lastPublishDate the last publish date of this repository
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this repository.
	 *
	 * @param modifiedDate the modified date of this repository
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this repository.
	 *
	 * @param mvccVersion the mvcc version of this repository
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this repository.
	 *
	 * @param name the name of this repository
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the portlet ID of this repository.
	 *
	 * @param portletId the portlet ID of this repository
	 */
	@Override
	public void setPortletId(String portletId) {
		model.setPortletId(portletId);
	}

	/**
	 * Sets the primary key of this repository.
	 *
	 * @param primaryKey the primary key of this repository
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the repository ID of this repository.
	 *
	 * @param repositoryId the repository ID of this repository
	 */
	@Override
	public void setRepositoryId(long repositoryId) {
		model.setRepositoryId(repositoryId);
	}

	/**
	 * Sets the type settings of this repository.
	 *
	 * @param typeSettings the type settings of this repository
	 */
	@Override
	public void setTypeSettings(String typeSettings) {
		model.setTypeSettings(typeSettings);
	}

	@Override
	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			typeSettingsProperties) {

		model.setTypeSettingsProperties(typeSettingsProperties);
	}

	/**
	 * Sets the user ID of this repository.
	 *
	 * @param userId the user ID of this repository
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this repository.
	 *
	 * @param userName the user name of this repository
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this repository.
	 *
	 * @param userUuid the user uuid of this repository
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this repository.
	 *
	 * @param uuid the uuid of this repository
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
	protected RepositoryWrapper wrap(Repository repository) {
		return new RepositoryWrapper(repository);
	}

}