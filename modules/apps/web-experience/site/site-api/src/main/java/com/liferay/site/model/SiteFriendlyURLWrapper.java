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

package com.liferay.site.model;

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
 * This class is a wrapper for {@link SiteFriendlyURL}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteFriendlyURL
 * @generated
 */
@ProviderType
public class SiteFriendlyURLWrapper implements SiteFriendlyURL,
	ModelWrapper<SiteFriendlyURL> {
	public SiteFriendlyURLWrapper(SiteFriendlyURL siteFriendlyURL) {
		_siteFriendlyURL = siteFriendlyURL;
	}

	@Override
	public Class<?> getModelClass() {
		return SiteFriendlyURL.class;
	}

	@Override
	public String getModelClassName() {
		return SiteFriendlyURL.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("siteFriendlyURLId", getSiteFriendlyURLId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("groupId", getGroupId());
		attributes.put("friendlyURL", getFriendlyURL());
		attributes.put("languageId", getLanguageId());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long siteFriendlyURLId = (Long)attributes.get("siteFriendlyURLId");

		if (siteFriendlyURLId != null) {
			setSiteFriendlyURLId(siteFriendlyURLId);
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

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
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
	public Object clone() {
		return new SiteFriendlyURLWrapper((SiteFriendlyURL)_siteFriendlyURL.clone());
	}

	@Override
	public int compareTo(SiteFriendlyURL siteFriendlyURL) {
		return _siteFriendlyURL.compareTo(siteFriendlyURL);
	}

	/**
	* Returns the company ID of this site friendly url.
	*
	* @return the company ID of this site friendly url
	*/
	@Override
	public long getCompanyId() {
		return _siteFriendlyURL.getCompanyId();
	}

	/**
	* Returns the create date of this site friendly url.
	*
	* @return the create date of this site friendly url
	*/
	@Override
	public Date getCreateDate() {
		return _siteFriendlyURL.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _siteFriendlyURL.getExpandoBridge();
	}

	/**
	* Returns the friendly url of this site friendly url.
	*
	* @return the friendly url of this site friendly url
	*/
	@Override
	public String getFriendlyURL() {
		return _siteFriendlyURL.getFriendlyURL();
	}

	/**
	* Returns the group ID of this site friendly url.
	*
	* @return the group ID of this site friendly url
	*/
	@Override
	public long getGroupId() {
		return _siteFriendlyURL.getGroupId();
	}

	/**
	* Returns the language ID of this site friendly url.
	*
	* @return the language ID of this site friendly url
	*/
	@Override
	public String getLanguageId() {
		return _siteFriendlyURL.getLanguageId();
	}

	/**
	* Returns the last publish date of this site friendly url.
	*
	* @return the last publish date of this site friendly url
	*/
	@Override
	public Date getLastPublishDate() {
		return _siteFriendlyURL.getLastPublishDate();
	}

	/**
	* Returns the modified date of this site friendly url.
	*
	* @return the modified date of this site friendly url
	*/
	@Override
	public Date getModifiedDate() {
		return _siteFriendlyURL.getModifiedDate();
	}

	/**
	* Returns the primary key of this site friendly url.
	*
	* @return the primary key of this site friendly url
	*/
	@Override
	public long getPrimaryKey() {
		return _siteFriendlyURL.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _siteFriendlyURL.getPrimaryKeyObj();
	}

	/**
	* Returns the site friendly url ID of this site friendly url.
	*
	* @return the site friendly url ID of this site friendly url
	*/
	@Override
	public long getSiteFriendlyURLId() {
		return _siteFriendlyURL.getSiteFriendlyURLId();
	}

	/**
	* Returns the user ID of this site friendly url.
	*
	* @return the user ID of this site friendly url
	*/
	@Override
	public long getUserId() {
		return _siteFriendlyURL.getUserId();
	}

	/**
	* Returns the user name of this site friendly url.
	*
	* @return the user name of this site friendly url
	*/
	@Override
	public String getUserName() {
		return _siteFriendlyURL.getUserName();
	}

	/**
	* Returns the user uuid of this site friendly url.
	*
	* @return the user uuid of this site friendly url
	*/
	@Override
	public String getUserUuid() {
		return _siteFriendlyURL.getUserUuid();
	}

	/**
	* Returns the uuid of this site friendly url.
	*
	* @return the uuid of this site friendly url
	*/
	@Override
	public String getUuid() {
		return _siteFriendlyURL.getUuid();
	}

	@Override
	public int hashCode() {
		return _siteFriendlyURL.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _siteFriendlyURL.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _siteFriendlyURL.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _siteFriendlyURL.isNew();
	}

	@Override
	public void persist() {
		_siteFriendlyURL.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_siteFriendlyURL.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this site friendly url.
	*
	* @param companyId the company ID of this site friendly url
	*/
	@Override
	public void setCompanyId(long companyId) {
		_siteFriendlyURL.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this site friendly url.
	*
	* @param createDate the create date of this site friendly url
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_siteFriendlyURL.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_siteFriendlyURL.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_siteFriendlyURL.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_siteFriendlyURL.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the friendly url of this site friendly url.
	*
	* @param friendlyURL the friendly url of this site friendly url
	*/
	@Override
	public void setFriendlyURL(String friendlyURL) {
		_siteFriendlyURL.setFriendlyURL(friendlyURL);
	}

	/**
	* Sets the group ID of this site friendly url.
	*
	* @param groupId the group ID of this site friendly url
	*/
	@Override
	public void setGroupId(long groupId) {
		_siteFriendlyURL.setGroupId(groupId);
	}

	/**
	* Sets the language ID of this site friendly url.
	*
	* @param languageId the language ID of this site friendly url
	*/
	@Override
	public void setLanguageId(String languageId) {
		_siteFriendlyURL.setLanguageId(languageId);
	}

	/**
	* Sets the last publish date of this site friendly url.
	*
	* @param lastPublishDate the last publish date of this site friendly url
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_siteFriendlyURL.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this site friendly url.
	*
	* @param modifiedDate the modified date of this site friendly url
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_siteFriendlyURL.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_siteFriendlyURL.setNew(n);
	}

	/**
	* Sets the primary key of this site friendly url.
	*
	* @param primaryKey the primary key of this site friendly url
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_siteFriendlyURL.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_siteFriendlyURL.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the site friendly url ID of this site friendly url.
	*
	* @param siteFriendlyURLId the site friendly url ID of this site friendly url
	*/
	@Override
	public void setSiteFriendlyURLId(long siteFriendlyURLId) {
		_siteFriendlyURL.setSiteFriendlyURLId(siteFriendlyURLId);
	}

	/**
	* Sets the user ID of this site friendly url.
	*
	* @param userId the user ID of this site friendly url
	*/
	@Override
	public void setUserId(long userId) {
		_siteFriendlyURL.setUserId(userId);
	}

	/**
	* Sets the user name of this site friendly url.
	*
	* @param userName the user name of this site friendly url
	*/
	@Override
	public void setUserName(String userName) {
		_siteFriendlyURL.setUserName(userName);
	}

	/**
	* Sets the user uuid of this site friendly url.
	*
	* @param userUuid the user uuid of this site friendly url
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_siteFriendlyURL.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this site friendly url.
	*
	* @param uuid the uuid of this site friendly url
	*/
	@Override
	public void setUuid(String uuid) {
		_siteFriendlyURL.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<SiteFriendlyURL> toCacheModel() {
		return _siteFriendlyURL.toCacheModel();
	}

	@Override
	public SiteFriendlyURL toEscapedModel() {
		return new SiteFriendlyURLWrapper(_siteFriendlyURL.toEscapedModel());
	}

	@Override
	public String toString() {
		return _siteFriendlyURL.toString();
	}

	@Override
	public SiteFriendlyURL toUnescapedModel() {
		return new SiteFriendlyURLWrapper(_siteFriendlyURL.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _siteFriendlyURL.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SiteFriendlyURLWrapper)) {
			return false;
		}

		SiteFriendlyURLWrapper siteFriendlyURLWrapper = (SiteFriendlyURLWrapper)obj;

		if (Objects.equals(_siteFriendlyURL,
					siteFriendlyURLWrapper._siteFriendlyURL)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _siteFriendlyURL.getStagedModelType();
	}

	@Override
	public SiteFriendlyURL getWrappedModel() {
		return _siteFriendlyURL;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _siteFriendlyURL.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _siteFriendlyURL.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_siteFriendlyURL.resetOriginalValues();
	}

	private final SiteFriendlyURL _siteFriendlyURL;
}