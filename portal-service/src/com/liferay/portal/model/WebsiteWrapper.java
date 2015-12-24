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
 * This class is a wrapper for {@link Website}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Website
 * @generated
 */
@ProviderType
public class WebsiteWrapper implements Website, ModelWrapper<Website> {
	public WebsiteWrapper(Website website) {
		_website = website;
	}

	@Override
	public Class<?> getModelClass() {
		return Website.class;
	}

	@Override
	public String getModelClassName() {
		return Website.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("websiteId", getWebsiteId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("url", getUrl());
		attributes.put("typeId", getTypeId());
		attributes.put("primary", getPrimary());
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

		Long websiteId = (Long)attributes.get("websiteId");

		if (websiteId != null) {
			setWebsiteId(websiteId);
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

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String url = (String)attributes.get("url");

		if (url != null) {
			setUrl(url);
		}

		Long typeId = (Long)attributes.get("typeId");

		if (typeId != null) {
			setTypeId(typeId);
		}

		Boolean primary = (Boolean)attributes.get("primary");

		if (primary != null) {
			setPrimary(primary);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new WebsiteWrapper((Website)_website.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.Website website) {
		return _website.compareTo(website);
	}

	/**
	* Returns the fully qualified class name of this website.
	*
	* @return the fully qualified class name of this website
	*/
	@Override
	public java.lang.String getClassName() {
		return _website.getClassName();
	}

	/**
	* Returns the class name ID of this website.
	*
	* @return the class name ID of this website
	*/
	@Override
	public long getClassNameId() {
		return _website.getClassNameId();
	}

	/**
	* Returns the class p k of this website.
	*
	* @return the class p k of this website
	*/
	@Override
	public long getClassPK() {
		return _website.getClassPK();
	}

	/**
	* Returns the company ID of this website.
	*
	* @return the company ID of this website
	*/
	@Override
	public long getCompanyId() {
		return _website.getCompanyId();
	}

	/**
	* Returns the create date of this website.
	*
	* @return the create date of this website
	*/
	@Override
	public Date getCreateDate() {
		return _website.getCreateDate();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _website.getExpandoBridge();
	}

	/**
	* Returns the last publish date of this website.
	*
	* @return the last publish date of this website
	*/
	@Override
	public Date getLastPublishDate() {
		return _website.getLastPublishDate();
	}

	/**
	* Returns the modified date of this website.
	*
	* @return the modified date of this website
	*/
	@Override
	public Date getModifiedDate() {
		return _website.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this website.
	*
	* @return the mvcc version of this website
	*/
	@Override
	public long getMvccVersion() {
		return _website.getMvccVersion();
	}

	/**
	* Returns the primary of this website.
	*
	* @return the primary of this website
	*/
	@Override
	public boolean getPrimary() {
		return _website.getPrimary();
	}

	/**
	* Returns the primary key of this website.
	*
	* @return the primary key of this website
	*/
	@Override
	public long getPrimaryKey() {
		return _website.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _website.getPrimaryKeyObj();
	}

	@Override
	public com.liferay.portal.model.ListType getType()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _website.getType();
	}

	/**
	* Returns the type ID of this website.
	*
	* @return the type ID of this website
	*/
	@Override
	public long getTypeId() {
		return _website.getTypeId();
	}

	/**
	* Returns the url of this website.
	*
	* @return the url of this website
	*/
	@Override
	public java.lang.String getUrl() {
		return _website.getUrl();
	}

	/**
	* Returns the user ID of this website.
	*
	* @return the user ID of this website
	*/
	@Override
	public long getUserId() {
		return _website.getUserId();
	}

	/**
	* Returns the user name of this website.
	*
	* @return the user name of this website
	*/
	@Override
	public java.lang.String getUserName() {
		return _website.getUserName();
	}

	/**
	* Returns the user uuid of this website.
	*
	* @return the user uuid of this website
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _website.getUserUuid();
	}

	/**
	* Returns the uuid of this website.
	*
	* @return the uuid of this website
	*/
	@Override
	public java.lang.String getUuid() {
		return _website.getUuid();
	}

	/**
	* Returns the website ID of this website.
	*
	* @return the website ID of this website
	*/
	@Override
	public long getWebsiteId() {
		return _website.getWebsiteId();
	}

	@Override
	public int hashCode() {
		return _website.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _website.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _website.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _website.isNew();
	}

	/**
	* Returns <code>true</code> if this website is primary.
	*
	* @return <code>true</code> if this website is primary; <code>false</code> otherwise
	*/
	@Override
	public boolean isPrimary() {
		return _website.isPrimary();
	}

	@Override
	public void persist() {
		_website.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_website.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_website.setClassName(className);
	}

	/**
	* Sets the class name ID of this website.
	*
	* @param classNameId the class name ID of this website
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_website.setClassNameId(classNameId);
	}

	/**
	* Sets the class p k of this website.
	*
	* @param classPK the class p k of this website
	*/
	@Override
	public void setClassPK(long classPK) {
		_website.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this website.
	*
	* @param companyId the company ID of this website
	*/
	@Override
	public void setCompanyId(long companyId) {
		_website.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this website.
	*
	* @param createDate the create date of this website
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_website.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_website.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_website.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_website.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the last publish date of this website.
	*
	* @param lastPublishDate the last publish date of this website
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_website.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this website.
	*
	* @param modifiedDate the modified date of this website
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_website.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this website.
	*
	* @param mvccVersion the mvcc version of this website
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_website.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_website.setNew(n);
	}

	/**
	* Sets whether this website is primary.
	*
	* @param primary the primary of this website
	*/
	@Override
	public void setPrimary(boolean primary) {
		_website.setPrimary(primary);
	}

	/**
	* Sets the primary key of this website.
	*
	* @param primaryKey the primary key of this website
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_website.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_website.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the type ID of this website.
	*
	* @param typeId the type ID of this website
	*/
	@Override
	public void setTypeId(long typeId) {
		_website.setTypeId(typeId);
	}

	/**
	* Sets the url of this website.
	*
	* @param url the url of this website
	*/
	@Override
	public void setUrl(java.lang.String url) {
		_website.setUrl(url);
	}

	/**
	* Sets the user ID of this website.
	*
	* @param userId the user ID of this website
	*/
	@Override
	public void setUserId(long userId) {
		_website.setUserId(userId);
	}

	/**
	* Sets the user name of this website.
	*
	* @param userName the user name of this website
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_website.setUserName(userName);
	}

	/**
	* Sets the user uuid of this website.
	*
	* @param userUuid the user uuid of this website
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_website.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this website.
	*
	* @param uuid the uuid of this website
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_website.setUuid(uuid);
	}

	/**
	* Sets the website ID of this website.
	*
	* @param websiteId the website ID of this website
	*/
	@Override
	public void setWebsiteId(long websiteId) {
		_website.setWebsiteId(websiteId);
	}

	@Override
	public CacheModel<com.liferay.portal.model.Website> toCacheModel() {
		return _website.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.Website toEscapedModel() {
		return new WebsiteWrapper(_website.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _website.toString();
	}

	@Override
	public com.liferay.portal.model.Website toUnescapedModel() {
		return new WebsiteWrapper(_website.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _website.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof WebsiteWrapper)) {
			return false;
		}

		WebsiteWrapper websiteWrapper = (WebsiteWrapper)obj;

		if (Validator.equals(_website, websiteWrapper._website)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _website.getStagedModelType();
	}

	@Override
	public Website getWrappedModel() {
		return _website;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _website.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _website.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_website.resetOriginalValues();
	}

	private final Website _website;
}