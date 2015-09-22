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

package com.liferay.portlet.softwarecatalog.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SCProductVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SCProductVersion
 * @generated
 */
@ProviderType
public class SCProductVersionWrapper implements SCProductVersion,
	ModelWrapper<SCProductVersion> {
	public SCProductVersionWrapper(SCProductVersion scProductVersion) {
		_scProductVersion = scProductVersion;
	}

	@Override
	public Class<?> getModelClass() {
		return SCProductVersion.class;
	}

	@Override
	public String getModelClassName() {
		return SCProductVersion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("productVersionId", getProductVersionId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("productEntryId", getProductEntryId());
		attributes.put("version", getVersion());
		attributes.put("changeLog", getChangeLog());
		attributes.put("downloadPageURL", getDownloadPageURL());
		attributes.put("directDownloadURL", getDirectDownloadURL());
		attributes.put("repoStoreArtifact", getRepoStoreArtifact());
		attributes.put("companyId", getCompanyId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long productVersionId = (Long)attributes.get("productVersionId");

		if (productVersionId != null) {
			setProductVersionId(productVersionId);
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

		Long productEntryId = (Long)attributes.get("productEntryId");

		if (productEntryId != null) {
			setProductEntryId(productEntryId);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		String changeLog = (String)attributes.get("changeLog");

		if (changeLog != null) {
			setChangeLog(changeLog);
		}

		String downloadPageURL = (String)attributes.get("downloadPageURL");

		if (downloadPageURL != null) {
			setDownloadPageURL(downloadPageURL);
		}

		String directDownloadURL = (String)attributes.get("directDownloadURL");

		if (directDownloadURL != null) {
			setDirectDownloadURL(directDownloadURL);
		}

		Boolean repoStoreArtifact = (Boolean)attributes.get("repoStoreArtifact");

		if (repoStoreArtifact != null) {
			setRepoStoreArtifact(repoStoreArtifact);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new SCProductVersionWrapper((SCProductVersion)_scProductVersion.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion) {
		return _scProductVersion.compareTo(scProductVersion);
	}

	/**
	* Returns the change log of this s c product version.
	*
	* @return the change log of this s c product version
	*/
	@Override
	public java.lang.String getChangeLog() {
		return _scProductVersion.getChangeLog();
	}

	/**
	* Returns the company ID of this s c product version.
	*
	* @return the company ID of this s c product version
	*/
	@Override
	public long getCompanyId() {
		return _scProductVersion.getCompanyId();
	}

	/**
	* Returns the create date of this s c product version.
	*
	* @return the create date of this s c product version
	*/
	@Override
	public Date getCreateDate() {
		return _scProductVersion.getCreateDate();
	}

	/**
	* Returns the direct download u r l of this s c product version.
	*
	* @return the direct download u r l of this s c product version
	*/
	@Override
	public java.lang.String getDirectDownloadURL() {
		return _scProductVersion.getDirectDownloadURL();
	}

	/**
	* Returns the download page u r l of this s c product version.
	*
	* @return the download page u r l of this s c product version
	*/
	@Override
	public java.lang.String getDownloadPageURL() {
		return _scProductVersion.getDownloadPageURL();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _scProductVersion.getExpandoBridge();
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions() {
		return _scProductVersion.getFrameworkVersions();
	}

	/**
	* Returns the modified date of this s c product version.
	*
	* @return the modified date of this s c product version
	*/
	@Override
	public Date getModifiedDate() {
		return _scProductVersion.getModifiedDate();
	}

	/**
	* Returns the primary key of this s c product version.
	*
	* @return the primary key of this s c product version
	*/
	@Override
	public long getPrimaryKey() {
		return _scProductVersion.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _scProductVersion.getPrimaryKeyObj();
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductEntry getProductEntry() {
		return _scProductVersion.getProductEntry();
	}

	/**
	* Returns the product entry ID of this s c product version.
	*
	* @return the product entry ID of this s c product version
	*/
	@Override
	public long getProductEntryId() {
		return _scProductVersion.getProductEntryId();
	}

	/**
	* Returns the product version ID of this s c product version.
	*
	* @return the product version ID of this s c product version
	*/
	@Override
	public long getProductVersionId() {
		return _scProductVersion.getProductVersionId();
	}

	/**
	* Returns the repo store artifact of this s c product version.
	*
	* @return the repo store artifact of this s c product version
	*/
	@Override
	public boolean getRepoStoreArtifact() {
		return _scProductVersion.getRepoStoreArtifact();
	}

	/**
	* Returns the user ID of this s c product version.
	*
	* @return the user ID of this s c product version
	*/
	@Override
	public long getUserId() {
		return _scProductVersion.getUserId();
	}

	/**
	* Returns the user name of this s c product version.
	*
	* @return the user name of this s c product version
	*/
	@Override
	public java.lang.String getUserName() {
		return _scProductVersion.getUserName();
	}

	/**
	* Returns the user uuid of this s c product version.
	*
	* @return the user uuid of this s c product version
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _scProductVersion.getUserUuid();
	}

	/**
	* Returns the version of this s c product version.
	*
	* @return the version of this s c product version
	*/
	@Override
	public java.lang.String getVersion() {
		return _scProductVersion.getVersion();
	}

	@Override
	public int hashCode() {
		return _scProductVersion.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _scProductVersion.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _scProductVersion.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _scProductVersion.isNew();
	}

	/**
	* Returns <code>true</code> if this s c product version is repo store artifact.
	*
	* @return <code>true</code> if this s c product version is repo store artifact; <code>false</code> otherwise
	*/
	@Override
	public boolean isRepoStoreArtifact() {
		return _scProductVersion.isRepoStoreArtifact();
	}

	@Override
	public void persist() {
		_scProductVersion.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_scProductVersion.setCachedModel(cachedModel);
	}

	/**
	* Sets the change log of this s c product version.
	*
	* @param changeLog the change log of this s c product version
	*/
	@Override
	public void setChangeLog(java.lang.String changeLog) {
		_scProductVersion.setChangeLog(changeLog);
	}

	/**
	* Sets the company ID of this s c product version.
	*
	* @param companyId the company ID of this s c product version
	*/
	@Override
	public void setCompanyId(long companyId) {
		_scProductVersion.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this s c product version.
	*
	* @param createDate the create date of this s c product version
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_scProductVersion.setCreateDate(createDate);
	}

	/**
	* Sets the direct download u r l of this s c product version.
	*
	* @param directDownloadURL the direct download u r l of this s c product version
	*/
	@Override
	public void setDirectDownloadURL(java.lang.String directDownloadURL) {
		_scProductVersion.setDirectDownloadURL(directDownloadURL);
	}

	/**
	* Sets the download page u r l of this s c product version.
	*
	* @param downloadPageURL the download page u r l of this s c product version
	*/
	@Override
	public void setDownloadPageURL(java.lang.String downloadPageURL) {
		_scProductVersion.setDownloadPageURL(downloadPageURL);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_scProductVersion.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_scProductVersion.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_scProductVersion.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this s c product version.
	*
	* @param modifiedDate the modified date of this s c product version
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_scProductVersion.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_scProductVersion.setNew(n);
	}

	/**
	* Sets the primary key of this s c product version.
	*
	* @param primaryKey the primary key of this s c product version
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_scProductVersion.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_scProductVersion.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the product entry ID of this s c product version.
	*
	* @param productEntryId the product entry ID of this s c product version
	*/
	@Override
	public void setProductEntryId(long productEntryId) {
		_scProductVersion.setProductEntryId(productEntryId);
	}

	/**
	* Sets the product version ID of this s c product version.
	*
	* @param productVersionId the product version ID of this s c product version
	*/
	@Override
	public void setProductVersionId(long productVersionId) {
		_scProductVersion.setProductVersionId(productVersionId);
	}

	/**
	* Sets whether this s c product version is repo store artifact.
	*
	* @param repoStoreArtifact the repo store artifact of this s c product version
	*/
	@Override
	public void setRepoStoreArtifact(boolean repoStoreArtifact) {
		_scProductVersion.setRepoStoreArtifact(repoStoreArtifact);
	}

	/**
	* Sets the user ID of this s c product version.
	*
	* @param userId the user ID of this s c product version
	*/
	@Override
	public void setUserId(long userId) {
		_scProductVersion.setUserId(userId);
	}

	/**
	* Sets the user name of this s c product version.
	*
	* @param userName the user name of this s c product version
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_scProductVersion.setUserName(userName);
	}

	/**
	* Sets the user uuid of this s c product version.
	*
	* @param userUuid the user uuid of this s c product version
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_scProductVersion.setUserUuid(userUuid);
	}

	/**
	* Sets the version of this s c product version.
	*
	* @param version the version of this s c product version
	*/
	@Override
	public void setVersion(java.lang.String version) {
		_scProductVersion.setVersion(version);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.softwarecatalog.model.SCProductVersion> toCacheModel() {
		return _scProductVersion.toCacheModel();
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion toEscapedModel() {
		return new SCProductVersionWrapper(_scProductVersion.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _scProductVersion.toString();
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion toUnescapedModel() {
		return new SCProductVersionWrapper(_scProductVersion.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _scProductVersion.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SCProductVersionWrapper)) {
			return false;
		}

		SCProductVersionWrapper scProductVersionWrapper = (SCProductVersionWrapper)obj;

		if (Validator.equals(_scProductVersion,
					scProductVersionWrapper._scProductVersion)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public SCProductVersion getWrappedSCProductVersion() {
		return _scProductVersion;
	}

	@Override
	public SCProductVersion getWrappedModel() {
		return _scProductVersion;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _scProductVersion.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _scProductVersion.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_scProductVersion.resetOriginalValues();
	}

	private final SCProductVersion _scProductVersion;
}