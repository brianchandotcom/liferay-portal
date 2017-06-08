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

package com.liferay.portal.kernel.route.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link GroupFriendlyURL}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GroupFriendlyURL
 * @generated
 */
@ProviderType
public class GroupFriendlyURLWrapper implements GroupFriendlyURL,
	ModelWrapper<GroupFriendlyURL> {
	public GroupFriendlyURLWrapper(GroupFriendlyURL groupFriendlyURL) {
		_groupFriendlyURL = groupFriendlyURL;
	}

	@Override
	public Class<?> getModelClass() {
		return GroupFriendlyURL.class;
	}

	@Override
	public String getModelClassName() {
		return GroupFriendlyURL.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("groupFriendlyURLId", getGroupFriendlyURLId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("friendlyURL", getFriendlyURL());
		attributes.put("languageId", getLanguageId());
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

		Long groupFriendlyURLId = (Long)attributes.get("groupFriendlyURLId");

		if (groupFriendlyURLId != null) {
			setGroupFriendlyURLId(groupFriendlyURLId);
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

		String friendlyURL = (String)attributes.get("friendlyURL");

		if (friendlyURL != null) {
			setFriendlyURL(friendlyURL);
		}

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public GroupFriendlyURL toEscapedModel() {
		return new GroupFriendlyURLWrapper(_groupFriendlyURL.toEscapedModel());
	}

	@Override
	public GroupFriendlyURL toUnescapedModel() {
		return new GroupFriendlyURLWrapper(_groupFriendlyURL.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _groupFriendlyURL.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _groupFriendlyURL.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _groupFriendlyURL.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _groupFriendlyURL.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<GroupFriendlyURL> toCacheModel() {
		return _groupFriendlyURL.toCacheModel();
	}

	@Override
	public int compareTo(GroupFriendlyURL groupFriendlyURL) {
		return _groupFriendlyURL.compareTo(groupFriendlyURL);
	}

	@Override
	public int hashCode() {
		return _groupFriendlyURL.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _groupFriendlyURL.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new GroupFriendlyURLWrapper((GroupFriendlyURL)_groupFriendlyURL.clone());
	}

	/**
	* Returns the friendly url of this group friendly url.
	*
	* @return the friendly url of this group friendly url
	*/
	@Override
	public java.lang.String getFriendlyURL() {
		return _groupFriendlyURL.getFriendlyURL();
	}

	/**
	* Returns the language ID of this group friendly url.
	*
	* @return the language ID of this group friendly url
	*/
	@Override
	public java.lang.String getLanguageId() {
		return _groupFriendlyURL.getLanguageId();
	}

	/**
	* Returns the user name of this group friendly url.
	*
	* @return the user name of this group friendly url
	*/
	@Override
	public java.lang.String getUserName() {
		return _groupFriendlyURL.getUserName();
	}

	/**
	* Returns the user uuid of this group friendly url.
	*
	* @return the user uuid of this group friendly url
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _groupFriendlyURL.getUserUuid();
	}

	/**
	* Returns the uuid of this group friendly url.
	*
	* @return the uuid of this group friendly url
	*/
	@Override
	public java.lang.String getUuid() {
		return _groupFriendlyURL.getUuid();
	}

	@Override
	public java.lang.String toString() {
		return _groupFriendlyURL.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _groupFriendlyURL.toXmlString();
	}

	/**
	* Returns the create date of this group friendly url.
	*
	* @return the create date of this group friendly url
	*/
	@Override
	public Date getCreateDate() {
		return _groupFriendlyURL.getCreateDate();
	}

	/**
	* Returns the last publish date of this group friendly url.
	*
	* @return the last publish date of this group friendly url
	*/
	@Override
	public Date getLastPublishDate() {
		return _groupFriendlyURL.getLastPublishDate();
	}

	/**
	* Returns the modified date of this group friendly url.
	*
	* @return the modified date of this group friendly url
	*/
	@Override
	public Date getModifiedDate() {
		return _groupFriendlyURL.getModifiedDate();
	}

	/**
	* Returns the company ID of this group friendly url.
	*
	* @return the company ID of this group friendly url
	*/
	@Override
	public long getCompanyId() {
		return _groupFriendlyURL.getCompanyId();
	}

	/**
	* Returns the group friendly url ID of this group friendly url.
	*
	* @return the group friendly url ID of this group friendly url
	*/
	@Override
	public long getGroupFriendlyURLId() {
		return _groupFriendlyURL.getGroupFriendlyURLId();
	}

	/**
	* Returns the group ID of this group friendly url.
	*
	* @return the group ID of this group friendly url
	*/
	@Override
	public long getGroupId() {
		return _groupFriendlyURL.getGroupId();
	}

	/**
	* Returns the mvcc version of this group friendly url.
	*
	* @return the mvcc version of this group friendly url
	*/
	@Override
	public long getMvccVersion() {
		return _groupFriendlyURL.getMvccVersion();
	}

	/**
	* Returns the primary key of this group friendly url.
	*
	* @return the primary key of this group friendly url
	*/
	@Override
	public long getPrimaryKey() {
		return _groupFriendlyURL.getPrimaryKey();
	}

	/**
	* Returns the user ID of this group friendly url.
	*
	* @return the user ID of this group friendly url
	*/
	@Override
	public long getUserId() {
		return _groupFriendlyURL.getUserId();
	}

	@Override
	public void persist() {
		_groupFriendlyURL.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_groupFriendlyURL.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this group friendly url.
	*
	* @param companyId the company ID of this group friendly url
	*/
	@Override
	public void setCompanyId(long companyId) {
		_groupFriendlyURL.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this group friendly url.
	*
	* @param createDate the create date of this group friendly url
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_groupFriendlyURL.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_groupFriendlyURL.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_groupFriendlyURL.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_groupFriendlyURL.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the friendly url of this group friendly url.
	*
	* @param friendlyURL the friendly url of this group friendly url
	*/
	@Override
	public void setFriendlyURL(java.lang.String friendlyURL) {
		_groupFriendlyURL.setFriendlyURL(friendlyURL);
	}

	/**
	* Sets the group friendly url ID of this group friendly url.
	*
	* @param groupFriendlyURLId the group friendly url ID of this group friendly url
	*/
	@Override
	public void setGroupFriendlyURLId(long groupFriendlyURLId) {
		_groupFriendlyURL.setGroupFriendlyURLId(groupFriendlyURLId);
	}

	/**
	* Sets the group ID of this group friendly url.
	*
	* @param groupId the group ID of this group friendly url
	*/
	@Override
	public void setGroupId(long groupId) {
		_groupFriendlyURL.setGroupId(groupId);
	}

	/**
	* Sets the language ID of this group friendly url.
	*
	* @param languageId the language ID of this group friendly url
	*/
	@Override
	public void setLanguageId(java.lang.String languageId) {
		_groupFriendlyURL.setLanguageId(languageId);
	}

	/**
	* Sets the last publish date of this group friendly url.
	*
	* @param lastPublishDate the last publish date of this group friendly url
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_groupFriendlyURL.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this group friendly url.
	*
	* @param modifiedDate the modified date of this group friendly url
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_groupFriendlyURL.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this group friendly url.
	*
	* @param mvccVersion the mvcc version of this group friendly url
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_groupFriendlyURL.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_groupFriendlyURL.setNew(n);
	}

	/**
	* Sets the primary key of this group friendly url.
	*
	* @param primaryKey the primary key of this group friendly url
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_groupFriendlyURL.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_groupFriendlyURL.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this group friendly url.
	*
	* @param userId the user ID of this group friendly url
	*/
	@Override
	public void setUserId(long userId) {
		_groupFriendlyURL.setUserId(userId);
	}

	/**
	* Sets the user name of this group friendly url.
	*
	* @param userName the user name of this group friendly url
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_groupFriendlyURL.setUserName(userName);
	}

	/**
	* Sets the user uuid of this group friendly url.
	*
	* @param userUuid the user uuid of this group friendly url
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_groupFriendlyURL.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this group friendly url.
	*
	* @param uuid the uuid of this group friendly url
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_groupFriendlyURL.setUuid(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof GroupFriendlyURLWrapper)) {
			return false;
		}

		GroupFriendlyURLWrapper groupFriendlyURLWrapper = (GroupFriendlyURLWrapper)obj;

		if (Objects.equals(_groupFriendlyURL,
					groupFriendlyURLWrapper._groupFriendlyURL)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _groupFriendlyURL.getStagedModelType();
	}

	@Override
	public GroupFriendlyURL getWrappedModel() {
		return _groupFriendlyURL;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _groupFriendlyURL.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _groupFriendlyURL.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_groupFriendlyURL.resetOriginalValues();
	}

	private final GroupFriendlyURL _groupFriendlyURL;
}