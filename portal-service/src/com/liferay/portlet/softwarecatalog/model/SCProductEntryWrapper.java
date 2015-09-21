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
 * This class is a wrapper for {@link SCProductEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SCProductEntry
 * @generated
 */
@ProviderType
public class SCProductEntryWrapper implements SCProductEntry,
	ModelWrapper<SCProductEntry> {
	public SCProductEntryWrapper(SCProductEntry scProductEntry) {
		_scProductEntry = scProductEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return SCProductEntry.class;
	}

	@Override
	public String getModelClassName() {
		return SCProductEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("productEntryId", getProductEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("type", getType());
		attributes.put("tags", getTags());
		attributes.put("shortDescription", getShortDescription());
		attributes.put("longDescription", getLongDescription());
		attributes.put("pageURL", getPageURL());
		attributes.put("author", getAuthor());
		attributes.put("repoGroupId", getRepoGroupId());
		attributes.put("repoArtifactId", getRepoArtifactId());
		attributes.put("companyId", getCompanyId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long productEntryId = (Long)attributes.get("productEntryId");

		if (productEntryId != null) {
			setProductEntryId(productEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
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

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String tags = (String)attributes.get("tags");

		if (tags != null) {
			setTags(tags);
		}

		String shortDescription = (String)attributes.get("shortDescription");

		if (shortDescription != null) {
			setShortDescription(shortDescription);
		}

		String longDescription = (String)attributes.get("longDescription");

		if (longDescription != null) {
			setLongDescription(longDescription);
		}

		String pageURL = (String)attributes.get("pageURL");

		if (pageURL != null) {
			setPageURL(pageURL);
		}

		String author = (String)attributes.get("author");

		if (author != null) {
			setAuthor(author);
		}

		String repoGroupId = (String)attributes.get("repoGroupId");

		if (repoGroupId != null) {
			setRepoGroupId(repoGroupId);
		}

		String repoArtifactId = (String)attributes.get("repoArtifactId");

		if (repoArtifactId != null) {
			setRepoArtifactId(repoArtifactId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new SCProductEntryWrapper((SCProductEntry)_scProductEntry.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry) {
		return _scProductEntry.compareTo(scProductEntry);
	}

	/**
	* Returns the author of this s c product entry.
	*
	* @return the author of this s c product entry
	*/
	@Override
	public java.lang.String getAuthor() {
		return _scProductEntry.getAuthor();
	}

	/**
	* Returns the company ID of this s c product entry.
	*
	* @return the company ID of this s c product entry
	*/
	@Override
	public long getCompanyId() {
		return _scProductEntry.getCompanyId();
	}

	/**
	* Returns the create date of this s c product entry.
	*
	* @return the create date of this s c product entry
	*/
	@Override
	public Date getCreateDate() {
		return _scProductEntry.getCreateDate();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _scProductEntry.getExpandoBridge();
	}

	/**
	* Returns the group ID of this s c product entry.
	*
	* @return the group ID of this s c product entry
	*/
	@Override
	public long getGroupId() {
		return _scProductEntry.getGroupId();
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductVersion getLatestVersion() {
		return _scProductEntry.getLatestVersion();
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getLicenses() {
		return _scProductEntry.getLicenses();
	}

	/**
	* Returns the long description of this s c product entry.
	*
	* @return the long description of this s c product entry
	*/
	@Override
	public java.lang.String getLongDescription() {
		return _scProductEntry.getLongDescription();
	}

	/**
	* Returns the modified date of this s c product entry.
	*
	* @return the modified date of this s c product entry
	*/
	@Override
	public Date getModifiedDate() {
		return _scProductEntry.getModifiedDate();
	}

	/**
	* Returns the name of this s c product entry.
	*
	* @return the name of this s c product entry
	*/
	@Override
	public java.lang.String getName() {
		return _scProductEntry.getName();
	}

	/**
	* Returns the page u r l of this s c product entry.
	*
	* @return the page u r l of this s c product entry
	*/
	@Override
	public java.lang.String getPageURL() {
		return _scProductEntry.getPageURL();
	}

	/**
	* Returns the primary key of this s c product entry.
	*
	* @return the primary key of this s c product entry
	*/
	@Override
	public long getPrimaryKey() {
		return _scProductEntry.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _scProductEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the product entry ID of this s c product entry.
	*
	* @return the product entry ID of this s c product entry
	*/
	@Override
	public long getProductEntryId() {
		return _scProductEntry.getProductEntryId();
	}

	/**
	* Returns the repo artifact ID of this s c product entry.
	*
	* @return the repo artifact ID of this s c product entry
	*/
	@Override
	public java.lang.String getRepoArtifactId() {
		return _scProductEntry.getRepoArtifactId();
	}

	/**
	* Returns the repo group ID of this s c product entry.
	*
	* @return the repo group ID of this s c product entry
	*/
	@Override
	public java.lang.String getRepoGroupId() {
		return _scProductEntry.getRepoGroupId();
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductScreenshot> getScreenshots() {
		return _scProductEntry.getScreenshots();
	}

	/**
	* Returns the short description of this s c product entry.
	*
	* @return the short description of this s c product entry
	*/
	@Override
	public java.lang.String getShortDescription() {
		return _scProductEntry.getShortDescription();
	}

	/**
	* Returns the tags of this s c product entry.
	*
	* @return the tags of this s c product entry
	*/
	@Override
	public java.lang.String getTags() {
		return _scProductEntry.getTags();
	}

	/**
	* Returns the type of this s c product entry.
	*
	* @return the type of this s c product entry
	*/
	@Override
	public java.lang.String getType() {
		return _scProductEntry.getType();
	}

	/**
	* Returns the user ID of this s c product entry.
	*
	* @return the user ID of this s c product entry
	*/
	@Override
	public long getUserId() {
		return _scProductEntry.getUserId();
	}

	/**
	* Returns the user name of this s c product entry.
	*
	* @return the user name of this s c product entry
	*/
	@Override
	public java.lang.String getUserName() {
		return _scProductEntry.getUserName();
	}

	/**
	* Returns the user uuid of this s c product entry.
	*
	* @return the user uuid of this s c product entry
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _scProductEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _scProductEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _scProductEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _scProductEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _scProductEntry.isNew();
	}

	@Override
	public void persist() {
		_scProductEntry.persist();
	}

	/**
	* Sets the author of this s c product entry.
	*
	* @param author the author of this s c product entry
	*/
	@Override
	public void setAuthor(java.lang.String author) {
		_scProductEntry.setAuthor(author);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_scProductEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this s c product entry.
	*
	* @param companyId the company ID of this s c product entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_scProductEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this s c product entry.
	*
	* @param createDate the create date of this s c product entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_scProductEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_scProductEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_scProductEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_scProductEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this s c product entry.
	*
	* @param groupId the group ID of this s c product entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_scProductEntry.setGroupId(groupId);
	}

	/**
	* Sets the long description of this s c product entry.
	*
	* @param longDescription the long description of this s c product entry
	*/
	@Override
	public void setLongDescription(java.lang.String longDescription) {
		_scProductEntry.setLongDescription(longDescription);
	}

	/**
	* Sets the modified date of this s c product entry.
	*
	* @param modifiedDate the modified date of this s c product entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_scProductEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this s c product entry.
	*
	* @param name the name of this s c product entry
	*/
	@Override
	public void setName(java.lang.String name) {
		_scProductEntry.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_scProductEntry.setNew(n);
	}

	/**
	* Sets the page u r l of this s c product entry.
	*
	* @param pageURL the page u r l of this s c product entry
	*/
	@Override
	public void setPageURL(java.lang.String pageURL) {
		_scProductEntry.setPageURL(pageURL);
	}

	/**
	* Sets the primary key of this s c product entry.
	*
	* @param primaryKey the primary key of this s c product entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_scProductEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_scProductEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the product entry ID of this s c product entry.
	*
	* @param productEntryId the product entry ID of this s c product entry
	*/
	@Override
	public void setProductEntryId(long productEntryId) {
		_scProductEntry.setProductEntryId(productEntryId);
	}

	/**
	* Sets the repo artifact ID of this s c product entry.
	*
	* @param repoArtifactId the repo artifact ID of this s c product entry
	*/
	@Override
	public void setRepoArtifactId(java.lang.String repoArtifactId) {
		_scProductEntry.setRepoArtifactId(repoArtifactId);
	}

	/**
	* Sets the repo group ID of this s c product entry.
	*
	* @param repoGroupId the repo group ID of this s c product entry
	*/
	@Override
	public void setRepoGroupId(java.lang.String repoGroupId) {
		_scProductEntry.setRepoGroupId(repoGroupId);
	}

	/**
	* Sets the short description of this s c product entry.
	*
	* @param shortDescription the short description of this s c product entry
	*/
	@Override
	public void setShortDescription(java.lang.String shortDescription) {
		_scProductEntry.setShortDescription(shortDescription);
	}

	/**
	* Sets the tags of this s c product entry.
	*
	* @param tags the tags of this s c product entry
	*/
	@Override
	public void setTags(java.lang.String tags) {
		_scProductEntry.setTags(tags);
	}

	/**
	* Sets the type of this s c product entry.
	*
	* @param type the type of this s c product entry
	*/
	@Override
	public void setType(java.lang.String type) {
		_scProductEntry.setType(type);
	}

	/**
	* Sets the user ID of this s c product entry.
	*
	* @param userId the user ID of this s c product entry
	*/
	@Override
	public void setUserId(long userId) {
		_scProductEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this s c product entry.
	*
	* @param userName the user name of this s c product entry
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_scProductEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this s c product entry.
	*
	* @param userUuid the user uuid of this s c product entry
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_scProductEntry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.softwarecatalog.model.SCProductEntry> toCacheModel() {
		return _scProductEntry.toCacheModel();
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductEntry toEscapedModel() {
		return new SCProductEntryWrapper(_scProductEntry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _scProductEntry.toString();
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCProductEntry toUnescapedModel() {
		return new SCProductEntryWrapper(_scProductEntry.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _scProductEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SCProductEntryWrapper)) {
			return false;
		}

		SCProductEntryWrapper scProductEntryWrapper = (SCProductEntryWrapper)obj;

		if (Validator.equals(_scProductEntry,
					scProductEntryWrapper._scProductEntry)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public SCProductEntry getWrappedSCProductEntry() {
		return _scProductEntry;
	}

	@Override
	public SCProductEntry getWrappedModel() {
		return _scProductEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _scProductEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _scProductEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_scProductEntry.resetOriginalValues();
	}

	private final SCProductEntry _scProductEntry;
}