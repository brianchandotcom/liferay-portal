/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
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

		Integer typeId = (Integer)attributes.get("typeId");

		if (typeId != null) {
			setTypeId(typeId);
		}

		Boolean primary = (Boolean)attributes.get("primary");

		if (primary != null) {
			setPrimary(primary);
		}
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

	/**
	* Sets the primary key of this website.
	*
	* @param primaryKey the primary key of this website
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_website.setPrimaryKey(primaryKey);
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
	* Sets the uuid of this website.
	*
	* @param uuid the uuid of this website
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_website.setUuid(uuid);
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

	/**
	* Sets the website ID of this website.
	*
	* @param websiteId the website ID of this website
	*/
	@Override
	public void setWebsiteId(long websiteId) {
		_website.setWebsiteId(websiteId);
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
	* Sets the company ID of this website.
	*
	* @param companyId the company ID of this website
	*/
	@Override
	public void setCompanyId(long companyId) {
		_website.setCompanyId(companyId);
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
	* Sets the user ID of this website.
	*
	* @param userId the user ID of this website
	*/
	@Override
	public void setUserId(long userId) {
		_website.setUserId(userId);
	}

	/**
	* Returns the user uuid of this website.
	*
	* @return the user uuid of this website
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _website.getUserUuid();
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
	* Returns the user name of this website.
	*
	* @return the user name of this website
	*/
	@Override
	public java.lang.String getUserName() {
		return _website.getUserName();
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
	* Returns the create date of this website.
	*
	* @return the create date of this website
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _website.getCreateDate();
	}

	/**
	* Sets the create date of this website.
	*
	* @param createDate the create date of this website
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_website.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this website.
	*
	* @return the modified date of this website
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _website.getModifiedDate();
	}

	/**
	* Sets the modified date of this website.
	*
	* @param modifiedDate the modified date of this website
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_website.setModifiedDate(modifiedDate);
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

	@Override
	public void setClassName(java.lang.String className) {
		_website.setClassName(className);
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
	* Sets the class name ID of this website.
	*
	* @param classNameId the class name ID of this website
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_website.setClassNameId(classNameId);
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
	* Sets the class p k of this website.
	*
	* @param classPK the class p k of this website
	*/
	@Override
	public void setClassPK(long classPK) {
		_website.setClassPK(classPK);
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
	* Sets the url of this website.
	*
	* @param url the url of this website
	*/
	@Override
	public void setUrl(java.lang.String url) {
		_website.setUrl(url);
	}

	/**
	* Returns the type ID of this website.
	*
	* @return the type ID of this website
	*/
	@Override
	public int getTypeId() {
		return _website.getTypeId();
	}

	/**
	* Sets the type ID of this website.
	*
	* @param typeId the type ID of this website
	*/
	@Override
	public void setTypeId(int typeId) {
		_website.setTypeId(typeId);
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
	* Returns <code>true</code> if this website is primary.
	*
	* @return <code>true</code> if this website is primary; <code>false</code> otherwise
	*/
	@Override
	public boolean isPrimary() {
		return _website.isPrimary();
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

	@Override
	public boolean isNew() {
		return _website.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_website.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _website.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_website.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _website.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _website.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_website.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _website.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
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

	@Override
	public java.lang.Object clone() {
		return new WebsiteWrapper((Website)_website.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.Website website) {
		return _website.compareTo(website);
	}

	@Override
	public int hashCode() {
		return _website.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.Website> toCacheModel() {
		return _website.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.Website toEscapedModel() {
		return new WebsiteWrapper(_website.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.Website toUnescapedModel() {
		return new WebsiteWrapper(_website.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _website.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _website.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_website.persist();
	}

	@Override
	public com.liferay.portal.model.ListType getType()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _website.getType();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public Website getWrappedWebsite() {
		return _website;
	}

	@Override
	public Website getWrappedModel() {
		return _website;
	}

	@Override
	public void resetOriginalValues() {
		_website.resetOriginalValues();
	}

	private Website _website;
}