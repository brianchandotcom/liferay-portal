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

package com.liferay.friendly.url.model;

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
 * This class is a wrapper for {@link FriendlyURL}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURL
 * @generated
 */
@ProviderType
public class FriendlyURLWrapper implements FriendlyURL,
	ModelWrapper<FriendlyURL> {
	public FriendlyURLWrapper(FriendlyURL friendlyURL) {
		_friendlyURL = friendlyURL;
	}

	@Override
	public Class<?> getModelClass() {
		return FriendlyURL.class;
	}

	@Override
	public String getModelClassName() {
		return FriendlyURL.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("friendlyURLId", getFriendlyURLId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("urlTitle", getUrlTitle());
		attributes.put("main", getMain());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long friendlyURLId = (Long)attributes.get("friendlyURLId");

		if (friendlyURLId != null) {
			setFriendlyURLId(friendlyURLId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
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

		String urlTitle = (String)attributes.get("urlTitle");

		if (urlTitle != null) {
			setUrlTitle(urlTitle);
		}

		Boolean main = (Boolean)attributes.get("main");

		if (main != null) {
			setMain(main);
		}
	}

	@Override
	public FriendlyURL toEscapedModel() {
		return new FriendlyURLWrapper(_friendlyURL.toEscapedModel());
	}

	@Override
	public FriendlyURL toUnescapedModel() {
		return new FriendlyURLWrapper(_friendlyURL.toUnescapedModel());
	}

	/**
	* Returns the main of this friendly url.
	*
	* @return the main of this friendly url
	*/
	@Override
	public boolean getMain() {
		return _friendlyURL.getMain();
	}

	@Override
	public boolean isCachedModel() {
		return _friendlyURL.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _friendlyURL.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this friendly url is main.
	*
	* @return <code>true</code> if this friendly url is main; <code>false</code> otherwise
	*/
	@Override
	public boolean isMain() {
		return _friendlyURL.isMain();
	}

	@Override
	public boolean isNew() {
		return _friendlyURL.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _friendlyURL.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<FriendlyURL> toCacheModel() {
		return _friendlyURL.toCacheModel();
	}

	@Override
	public int compareTo(FriendlyURL friendlyURL) {
		return _friendlyURL.compareTo(friendlyURL);
	}

	@Override
	public int hashCode() {
		return _friendlyURL.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _friendlyURL.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new FriendlyURLWrapper((FriendlyURL)_friendlyURL.clone());
	}

	/**
	* Returns the fully qualified class name of this friendly url.
	*
	* @return the fully qualified class name of this friendly url
	*/
	@Override
	public java.lang.String getClassName() {
		return _friendlyURL.getClassName();
	}

	/**
	* Returns the url title of this friendly url.
	*
	* @return the url title of this friendly url
	*/
	@Override
	public java.lang.String getUrlTitle() {
		return _friendlyURL.getUrlTitle();
	}

	/**
	* Returns the uuid of this friendly url.
	*
	* @return the uuid of this friendly url
	*/
	@Override
	public java.lang.String getUuid() {
		return _friendlyURL.getUuid();
	}

	@Override
	public java.lang.String toString() {
		return _friendlyURL.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _friendlyURL.toXmlString();
	}

	/**
	* Returns the create date of this friendly url.
	*
	* @return the create date of this friendly url
	*/
	@Override
	public Date getCreateDate() {
		return _friendlyURL.getCreateDate();
	}

	/**
	* Returns the modified date of this friendly url.
	*
	* @return the modified date of this friendly url
	*/
	@Override
	public Date getModifiedDate() {
		return _friendlyURL.getModifiedDate();
	}

	/**
	* Returns the class name ID of this friendly url.
	*
	* @return the class name ID of this friendly url
	*/
	@Override
	public long getClassNameId() {
		return _friendlyURL.getClassNameId();
	}

	/**
	* Returns the class pk of this friendly url.
	*
	* @return the class pk of this friendly url
	*/
	@Override
	public long getClassPK() {
		return _friendlyURL.getClassPK();
	}

	/**
	* Returns the company ID of this friendly url.
	*
	* @return the company ID of this friendly url
	*/
	@Override
	public long getCompanyId() {
		return _friendlyURL.getCompanyId();
	}

	/**
	* Returns the friendly url ID of this friendly url.
	*
	* @return the friendly url ID of this friendly url
	*/
	@Override
	public long getFriendlyURLId() {
		return _friendlyURL.getFriendlyURLId();
	}

	/**
	* Returns the group ID of this friendly url.
	*
	* @return the group ID of this friendly url
	*/
	@Override
	public long getGroupId() {
		return _friendlyURL.getGroupId();
	}

	/**
	* Returns the primary key of this friendly url.
	*
	* @return the primary key of this friendly url
	*/
	@Override
	public long getPrimaryKey() {
		return _friendlyURL.getPrimaryKey();
	}

	@Override
	public void persist() {
		_friendlyURL.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_friendlyURL.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_friendlyURL.setClassName(className);
	}

	/**
	* Sets the class name ID of this friendly url.
	*
	* @param classNameId the class name ID of this friendly url
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_friendlyURL.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this friendly url.
	*
	* @param classPK the class pk of this friendly url
	*/
	@Override
	public void setClassPK(long classPK) {
		_friendlyURL.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this friendly url.
	*
	* @param companyId the company ID of this friendly url
	*/
	@Override
	public void setCompanyId(long companyId) {
		_friendlyURL.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this friendly url.
	*
	* @param createDate the create date of this friendly url
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_friendlyURL.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_friendlyURL.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_friendlyURL.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_friendlyURL.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the friendly url ID of this friendly url.
	*
	* @param friendlyURLId the friendly url ID of this friendly url
	*/
	@Override
	public void setFriendlyURLId(long friendlyURLId) {
		_friendlyURL.setFriendlyURLId(friendlyURLId);
	}

	/**
	* Sets the group ID of this friendly url.
	*
	* @param groupId the group ID of this friendly url
	*/
	@Override
	public void setGroupId(long groupId) {
		_friendlyURL.setGroupId(groupId);
	}

	/**
	* Sets whether this friendly url is main.
	*
	* @param main the main of this friendly url
	*/
	@Override
	public void setMain(boolean main) {
		_friendlyURL.setMain(main);
	}

	/**
	* Sets the modified date of this friendly url.
	*
	* @param modifiedDate the modified date of this friendly url
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_friendlyURL.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_friendlyURL.setNew(n);
	}

	/**
	* Sets the primary key of this friendly url.
	*
	* @param primaryKey the primary key of this friendly url
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_friendlyURL.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_friendlyURL.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the url title of this friendly url.
	*
	* @param urlTitle the url title of this friendly url
	*/
	@Override
	public void setUrlTitle(java.lang.String urlTitle) {
		_friendlyURL.setUrlTitle(urlTitle);
	}

	/**
	* Sets the uuid of this friendly url.
	*
	* @param uuid the uuid of this friendly url
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_friendlyURL.setUuid(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FriendlyURLWrapper)) {
			return false;
		}

		FriendlyURLWrapper friendlyURLWrapper = (FriendlyURLWrapper)obj;

		if (Objects.equals(_friendlyURL, friendlyURLWrapper._friendlyURL)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _friendlyURL.getStagedModelType();
	}

	@Override
	public FriendlyURL getWrappedModel() {
		return _friendlyURL;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _friendlyURL.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _friendlyURL.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_friendlyURL.resetOriginalValues();
	}

	private final FriendlyURL _friendlyURL;
}