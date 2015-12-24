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

import com.liferay.portlet.exportimport.lar.StagedModelType;

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
@ProviderType
public class RepositoryWrapper implements Repository, ModelWrapper<Repository> {
	public RepositoryWrapper(Repository repository) {
		_repository = repository;
	}

	@Override
	public Class<?> getModelClass() {
		return Repository.class;
	}

	@Override
	public String getModelClassName() {
		return Repository.class.getName();
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

	@Override
	public java.lang.Object clone() {
		return new RepositoryWrapper((Repository)_repository.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.Repository repository) {
		return _repository.compareTo(repository);
	}

	/**
	* Returns the fully qualified class name of this repository.
	*
	* @return the fully qualified class name of this repository
	*/
	@Override
	public java.lang.String getClassName() {
		return _repository.getClassName();
	}

	/**
	* Returns the class name ID of this repository.
	*
	* @return the class name ID of this repository
	*/
	@Override
	public long getClassNameId() {
		return _repository.getClassNameId();
	}

	/**
	* Returns the company ID of this repository.
	*
	* @return the company ID of this repository
	*/
	@Override
	public long getCompanyId() {
		return _repository.getCompanyId();
	}

	/**
	* Returns the create date of this repository.
	*
	* @return the create date of this repository
	*/
	@Override
	public Date getCreateDate() {
		return _repository.getCreateDate();
	}

	/**
	* Returns the description of this repository.
	*
	* @return the description of this repository
	*/
	@Override
	public java.lang.String getDescription() {
		return _repository.getDescription();
	}

	/**
	* Returns the dl folder ID of this repository.
	*
	* @return the dl folder ID of this repository
	*/
	@Override
	public long getDlFolderId() {
		return _repository.getDlFolderId();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _repository.getExpandoBridge();
	}

	/**
	* Returns the group ID of this repository.
	*
	* @return the group ID of this repository
	*/
	@Override
	public long getGroupId() {
		return _repository.getGroupId();
	}

	/**
	* Returns the last publish date of this repository.
	*
	* @return the last publish date of this repository
	*/
	@Override
	public Date getLastPublishDate() {
		return _repository.getLastPublishDate();
	}

	/**
	* Returns the modified date of this repository.
	*
	* @return the modified date of this repository
	*/
	@Override
	public Date getModifiedDate() {
		return _repository.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this repository.
	*
	* @return the mvcc version of this repository
	*/
	@Override
	public long getMvccVersion() {
		return _repository.getMvccVersion();
	}

	/**
	* Returns the name of this repository.
	*
	* @return the name of this repository
	*/
	@Override
	public java.lang.String getName() {
		return _repository.getName();
	}

	/**
	* Returns the portlet ID of this repository.
	*
	* @return the portlet ID of this repository
	*/
	@Override
	public java.lang.String getPortletId() {
		return _repository.getPortletId();
	}

	/**
	* Returns the primary key of this repository.
	*
	* @return the primary key of this repository
	*/
	@Override
	public long getPrimaryKey() {
		return _repository.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _repository.getPrimaryKeyObj();
	}

	/**
	* Returns the repository ID of this repository.
	*
	* @return the repository ID of this repository
	*/
	@Override
	public long getRepositoryId() {
		return _repository.getRepositoryId();
	}

	/**
	* Returns the type settings of this repository.
	*
	* @return the type settings of this repository
	*/
	@Override
	public java.lang.String getTypeSettings() {
		return _repository.getTypeSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		return _repository.getTypeSettingsProperties();
	}

	/**
	* Returns the user ID of this repository.
	*
	* @return the user ID of this repository
	*/
	@Override
	public long getUserId() {
		return _repository.getUserId();
	}

	/**
	* Returns the user name of this repository.
	*
	* @return the user name of this repository
	*/
	@Override
	public java.lang.String getUserName() {
		return _repository.getUserName();
	}

	/**
	* Returns the user uuid of this repository.
	*
	* @return the user uuid of this repository
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _repository.getUserUuid();
	}

	/**
	* Returns the uuid of this repository.
	*
	* @return the uuid of this repository
	*/
	@Override
	public java.lang.String getUuid() {
		return _repository.getUuid();
	}

	@Override
	public int hashCode() {
		return _repository.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _repository.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _repository.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _repository.isNew();
	}

	@Override
	public void persist() {
		_repository.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_repository.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_repository.setClassName(className);
	}

	/**
	* Sets the class name ID of this repository.
	*
	* @param classNameId the class name ID of this repository
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_repository.setClassNameId(classNameId);
	}

	/**
	* Sets the company ID of this repository.
	*
	* @param companyId the company ID of this repository
	*/
	@Override
	public void setCompanyId(long companyId) {
		_repository.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this repository.
	*
	* @param createDate the create date of this repository
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_repository.setCreateDate(createDate);
	}

	/**
	* Sets the description of this repository.
	*
	* @param description the description of this repository
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_repository.setDescription(description);
	}

	/**
	* Sets the dl folder ID of this repository.
	*
	* @param dlFolderId the dl folder ID of this repository
	*/
	@Override
	public void setDlFolderId(long dlFolderId) {
		_repository.setDlFolderId(dlFolderId);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_repository.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_repository.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_repository.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this repository.
	*
	* @param groupId the group ID of this repository
	*/
	@Override
	public void setGroupId(long groupId) {
		_repository.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this repository.
	*
	* @param lastPublishDate the last publish date of this repository
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_repository.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this repository.
	*
	* @param modifiedDate the modified date of this repository
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_repository.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this repository.
	*
	* @param mvccVersion the mvcc version of this repository
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_repository.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the name of this repository.
	*
	* @param name the name of this repository
	*/
	@Override
	public void setName(java.lang.String name) {
		_repository.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_repository.setNew(n);
	}

	/**
	* Sets the portlet ID of this repository.
	*
	* @param portletId the portlet ID of this repository
	*/
	@Override
	public void setPortletId(java.lang.String portletId) {
		_repository.setPortletId(portletId);
	}

	/**
	* Sets the primary key of this repository.
	*
	* @param primaryKey the primary key of this repository
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_repository.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_repository.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the repository ID of this repository.
	*
	* @param repositoryId the repository ID of this repository
	*/
	@Override
	public void setRepositoryId(long repositoryId) {
		_repository.setRepositoryId(repositoryId);
	}

	/**
	* Sets the type settings of this repository.
	*
	* @param typeSettings the type settings of this repository
	*/
	@Override
	public void setTypeSettings(java.lang.String typeSettings) {
		_repository.setTypeSettings(typeSettings);
	}

	@Override
	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		_repository.setTypeSettingsProperties(typeSettingsProperties);
	}

	/**
	* Sets the user ID of this repository.
	*
	* @param userId the user ID of this repository
	*/
	@Override
	public void setUserId(long userId) {
		_repository.setUserId(userId);
	}

	/**
	* Sets the user name of this repository.
	*
	* @param userName the user name of this repository
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_repository.setUserName(userName);
	}

	/**
	* Sets the user uuid of this repository.
	*
	* @param userUuid the user uuid of this repository
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_repository.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this repository.
	*
	* @param uuid the uuid of this repository
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_repository.setUuid(uuid);
	}

	@Override
	public CacheModel<com.liferay.portal.model.Repository> toCacheModel() {
		return _repository.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.Repository toEscapedModel() {
		return new RepositoryWrapper(_repository.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _repository.toString();
	}

	@Override
	public com.liferay.portal.model.Repository toUnescapedModel() {
		return new RepositoryWrapper(_repository.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _repository.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RepositoryWrapper)) {
			return false;
		}

		RepositoryWrapper repositoryWrapper = (RepositoryWrapper)obj;

		if (Validator.equals(_repository, repositoryWrapper._repository)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _repository.getStagedModelType();
	}

	@Override
	public Repository getWrappedModel() {
		return _repository;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _repository.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _repository.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_repository.resetOriginalValues();
	}

	private final Repository _repository;
}