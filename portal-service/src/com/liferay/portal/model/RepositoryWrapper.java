/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

/**
 * <p>
 * This class is a wrapper for {@link Repository}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Repository
 * @generated
 */
public class RepositoryWrapper implements Repository, ModelWrapper<Repository> {
	public RepositoryWrapper(Repository repository) {
		_repository = repository;
	}

	public Class<?> getModelClass() {
		return Repository.class;
	}

	public String getModelClassName() {
		return Repository.class.getName();
	}

	/**
	* Returns the primary key of this repository.
	*
	* @return the primary key of this repository
	*/
	public long getPrimaryKey() {
		return _repository.getPrimaryKey();
	}

	/**
	* Sets the primary key of this repository.
	*
	* @param primaryKey the primary key of this repository
	*/
	public void setPrimaryKey(long primaryKey) {
		_repository.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the repository ID of this repository.
	*
	* @return the repository ID of this repository
	*/
	public long getRepositoryId() {
		return _repository.getRepositoryId();
	}

	/**
	* Sets the repository ID of this repository.
	*
	* @param repositoryId the repository ID of this repository
	*/
	public void setRepositoryId(long repositoryId) {
		_repository.setRepositoryId(repositoryId);
	}

	/**
	* Returns the group ID of this repository.
	*
	* @return the group ID of this repository
	*/
	public long getGroupId() {
		return _repository.getGroupId();
	}

	/**
	* Sets the group ID of this repository.
	*
	* @param groupId the group ID of this repository
	*/
	public void setGroupId(long groupId) {
		_repository.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this repository.
	*
	* @return the company ID of this repository
	*/
	public long getCompanyId() {
		return _repository.getCompanyId();
	}

	/**
	* Sets the company ID of this repository.
	*
	* @param companyId the company ID of this repository
	*/
	public void setCompanyId(long companyId) {
		_repository.setCompanyId(companyId);
	}

	/**
	* Returns the create date of this repository.
	*
	* @return the create date of this repository
	*/
	public java.util.Date getCreateDate() {
		return _repository.getCreateDate();
	}

	/**
	* Sets the create date of this repository.
	*
	* @param createDate the create date of this repository
	*/
	public void setCreateDate(java.util.Date createDate) {
		_repository.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this repository.
	*
	* @return the modified date of this repository
	*/
	public java.util.Date getModifiedDate() {
		return _repository.getModifiedDate();
	}

	/**
	* Sets the modified date of this repository.
	*
	* @param modifiedDate the modified date of this repository
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_repository.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the fully qualified class name of this repository.
	*
	* @return the fully qualified class name of this repository
	*/
	public java.lang.String getClassName() {
		return _repository.getClassName();
	}

	/**
	* Returns the class name ID of this repository.
	*
	* @return the class name ID of this repository
	*/
	public long getClassNameId() {
		return _repository.getClassNameId();
	}

	/**
	* Sets the class name ID of this repository.
	*
	* @param classNameId the class name ID of this repository
	*/
	public void setClassNameId(long classNameId) {
		_repository.setClassNameId(classNameId);
	}

	/**
	* Returns the name of this repository.
	*
	* @return the name of this repository
	*/
	public java.lang.String getName() {
		return _repository.getName();
	}

	/**
	* Sets the name of this repository.
	*
	* @param name the name of this repository
	*/
	public void setName(java.lang.String name) {
		_repository.setName(name);
	}

	/**
	* Returns the description of this repository.
	*
	* @return the description of this repository
	*/
	public java.lang.String getDescription() {
		return _repository.getDescription();
	}

	/**
	* Sets the description of this repository.
	*
	* @param description the description of this repository
	*/
	public void setDescription(java.lang.String description) {
		_repository.setDescription(description);
	}

	/**
	* Returns the portlet ID of this repository.
	*
	* @return the portlet ID of this repository
	*/
	public java.lang.String getPortletId() {
		return _repository.getPortletId();
	}

	/**
	* Sets the portlet ID of this repository.
	*
	* @param portletId the portlet ID of this repository
	*/
	public void setPortletId(java.lang.String portletId) {
		_repository.setPortletId(portletId);
	}

	/**
	* Returns the type settings of this repository.
	*
	* @return the type settings of this repository
	*/
	public java.lang.String getTypeSettings() {
		return _repository.getTypeSettings();
	}

	/**
	* Sets the type settings of this repository.
	*
	* @param typeSettings the type settings of this repository
	*/
	public void setTypeSettings(java.lang.String typeSettings) {
		_repository.setTypeSettings(typeSettings);
	}

	/**
	* Returns the dl folder ID of this repository.
	*
	* @return the dl folder ID of this repository
	*/
	public long getDlFolderId() {
		return _repository.getDlFolderId();
	}

	/**
	* Sets the dl folder ID of this repository.
	*
	* @param dlFolderId the dl folder ID of this repository
	*/
	public void setDlFolderId(long dlFolderId) {
		_repository.setDlFolderId(dlFolderId);
	}

	public boolean isNew() {
		return _repository.isNew();
	}

	public void setNew(boolean n) {
		_repository.setNew(n);
	}

	public boolean isCachedModel() {
		return _repository.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_repository.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _repository.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _repository.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_repository.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _repository.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_repository.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new RepositoryWrapper((Repository)_repository.clone());
	}

	public int compareTo(com.liferay.portal.model.Repository repository) {
		return _repository.compareTo(repository);
	}

	@Override
	public int hashCode() {
		return _repository.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.Repository> toCacheModel() {
		return _repository.toCacheModel();
	}

	public com.liferay.portal.model.Repository toEscapedModel() {
		return new RepositoryWrapper(_repository.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _repository.toString();
	}

	public java.lang.String toXmlString() {
		return _repository.toXmlString();
	}

	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		return _repository.getTypeSettingsProperties();
	}

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		_repository.setTypeSettingsProperties(typeSettingsProperties);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public Repository getWrappedRepository() {
		return _repository;
	}

	public Repository getWrappedModel() {
		return _repository;
	}

	public void resetOriginalValues() {
		_repository.resetOriginalValues();
	}

	private Repository _repository;
}