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
 * This class is a wrapper for {@link Website}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Website
 * @generated
 */
public class WebsiteWrapper
	extends BaseModelWrapper<Website>
	implements ModelWrapper<Website>, Website {

	public WebsiteWrapper(Website website) {
		super(website);
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
		attributes.put("primary", isPrimary());
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

	/**
	 * Returns the fully qualified class name of this website.
	 *
	 * @return the fully qualified class name of this website
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this website.
	 *
	 * @return the class name ID of this website
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this website.
	 *
	 * @return the class pk of this website
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this website.
	 *
	 * @return the company ID of this website
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this website.
	 *
	 * @return the create date of this website
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the last publish date of this website.
	 *
	 * @return the last publish date of this website
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this website.
	 *
	 * @return the modified date of this website
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this website.
	 *
	 * @return the mvcc version of this website
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary of this website.
	 *
	 * @return the primary of this website
	 */
	@Override
	public boolean getPrimary() {
		return model.getPrimary();
	}

	/**
	 * Returns the primary key of this website.
	 *
	 * @return the primary key of this website
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public ListType getType()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getType();
	}

	/**
	 * Returns the type ID of this website.
	 *
	 * @return the type ID of this website
	 */
	@Override
	public long getTypeId() {
		return model.getTypeId();
	}

	/**
	 * Returns the url of this website.
	 *
	 * @return the url of this website
	 */
	@Override
	public String getUrl() {
		return model.getUrl();
	}

	/**
	 * Returns the user ID of this website.
	 *
	 * @return the user ID of this website
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this website.
	 *
	 * @return the user name of this website
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this website.
	 *
	 * @return the user uuid of this website
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this website.
	 *
	 * @return the uuid of this website
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the website ID of this website.
	 *
	 * @return the website ID of this website
	 */
	@Override
	public long getWebsiteId() {
		return model.getWebsiteId();
	}

	/**
	 * Returns <code>true</code> if this website is primary.
	 *
	 * @return <code>true</code> if this website is primary; <code>false</code> otherwise
	 */
	@Override
	public boolean isPrimary() {
		return model.isPrimary();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a website model instance should use the <code>Website</code> interface instead.
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
	 * Sets the class name ID of this website.
	 *
	 * @param classNameId the class name ID of this website
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this website.
	 *
	 * @param classPK the class pk of this website
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this website.
	 *
	 * @param companyId the company ID of this website
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this website.
	 *
	 * @param createDate the create date of this website
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the last publish date of this website.
	 *
	 * @param lastPublishDate the last publish date of this website
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this website.
	 *
	 * @param modifiedDate the modified date of this website
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this website.
	 *
	 * @param mvccVersion the mvcc version of this website
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets whether this website is primary.
	 *
	 * @param primary the primary of this website
	 */
	@Override
	public void setPrimary(boolean primary) {
		model.setPrimary(primary);
	}

	/**
	 * Sets the primary key of this website.
	 *
	 * @param primaryKey the primary key of this website
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the type ID of this website.
	 *
	 * @param typeId the type ID of this website
	 */
	@Override
	public void setTypeId(long typeId) {
		model.setTypeId(typeId);
	}

	/**
	 * Sets the url of this website.
	 *
	 * @param url the url of this website
	 */
	@Override
	public void setUrl(String url) {
		model.setUrl(url);
	}

	/**
	 * Sets the user ID of this website.
	 *
	 * @param userId the user ID of this website
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this website.
	 *
	 * @param userName the user name of this website
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this website.
	 *
	 * @param userUuid the user uuid of this website
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this website.
	 *
	 * @param uuid the uuid of this website
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the website ID of this website.
	 *
	 * @param websiteId the website ID of this website
	 */
	@Override
	public void setWebsiteId(long websiteId) {
		model.setWebsiteId(websiteId);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected WebsiteWrapper wrap(Website website) {
		return new WebsiteWrapper(website);
	}

}