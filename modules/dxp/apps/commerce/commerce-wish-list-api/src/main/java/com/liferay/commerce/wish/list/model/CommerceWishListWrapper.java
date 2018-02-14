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

package com.liferay.commerce.wish.list.model;

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
 * This class is a wrapper for {@link CommerceWishList}.
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishList
 * @generated
 */
@ProviderType
public class CommerceWishListWrapper implements CommerceWishList,
	ModelWrapper<CommerceWishList> {
	public CommerceWishListWrapper(CommerceWishList commerceWishList) {
		_commerceWishList = commerceWishList;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceWishList.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceWishList.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("commerceWishListId", getCommerceWishListId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("defaultWishList", getDefaultWishList());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long commerceWishListId = (Long)attributes.get("commerceWishListId");

		if (commerceWishListId != null) {
			setCommerceWishListId(commerceWishListId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Boolean defaultWishList = (Boolean)attributes.get("defaultWishList");

		if (defaultWishList != null) {
			setDefaultWishList(defaultWishList);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceWishListWrapper((CommerceWishList)_commerceWishList.clone());
	}

	@Override
	public int compareTo(CommerceWishList commerceWishList) {
		return _commerceWishList.compareTo(commerceWishList);
	}

	/**
	* Returns the commerce wish list ID of this commerce wish list.
	*
	* @return the commerce wish list ID of this commerce wish list
	*/
	@Override
	public long getCommerceWishListId() {
		return _commerceWishList.getCommerceWishListId();
	}

	/**
	* Returns the company ID of this commerce wish list.
	*
	* @return the company ID of this commerce wish list
	*/
	@Override
	public long getCompanyId() {
		return _commerceWishList.getCompanyId();
	}

	/**
	* Returns the create date of this commerce wish list.
	*
	* @return the create date of this commerce wish list
	*/
	@Override
	public Date getCreateDate() {
		return _commerceWishList.getCreateDate();
	}

	/**
	* Returns the default wish list of this commerce wish list.
	*
	* @return the default wish list of this commerce wish list
	*/
	@Override
	public boolean getDefaultWishList() {
		return _commerceWishList.getDefaultWishList();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceWishList.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce wish list.
	*
	* @return the group ID of this commerce wish list
	*/
	@Override
	public long getGroupId() {
		return _commerceWishList.getGroupId();
	}

	/**
	* Returns the modified date of this commerce wish list.
	*
	* @return the modified date of this commerce wish list
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceWishList.getModifiedDate();
	}

	/**
	* Returns the name of this commerce wish list.
	*
	* @return the name of this commerce wish list
	*/
	@Override
	public java.lang.String getName() {
		return _commerceWishList.getName();
	}

	/**
	* Returns the primary key of this commerce wish list.
	*
	* @return the primary key of this commerce wish list
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceWishList.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceWishList.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this commerce wish list.
	*
	* @return the user ID of this commerce wish list
	*/
	@Override
	public long getUserId() {
		return _commerceWishList.getUserId();
	}

	/**
	* Returns the user name of this commerce wish list.
	*
	* @return the user name of this commerce wish list
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceWishList.getUserName();
	}

	/**
	* Returns the user uuid of this commerce wish list.
	*
	* @return the user uuid of this commerce wish list
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceWishList.getUserUuid();
	}

	/**
	* Returns the uuid of this commerce wish list.
	*
	* @return the uuid of this commerce wish list
	*/
	@Override
	public java.lang.String getUuid() {
		return _commerceWishList.getUuid();
	}

	@Override
	public int hashCode() {
		return _commerceWishList.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceWishList.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this commerce wish list is default wish list.
	*
	* @return <code>true</code> if this commerce wish list is default wish list; <code>false</code> otherwise
	*/
	@Override
	public boolean isDefaultWishList() {
		return _commerceWishList.isDefaultWishList();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceWishList.isEscapedModel();
	}

	@Override
	public boolean isGuestWishList()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWishList.isGuestWishList();
	}

	@Override
	public boolean isNew() {
		return _commerceWishList.isNew();
	}

	@Override
	public void persist() {
		_commerceWishList.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceWishList.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce wish list ID of this commerce wish list.
	*
	* @param commerceWishListId the commerce wish list ID of this commerce wish list
	*/
	@Override
	public void setCommerceWishListId(long commerceWishListId) {
		_commerceWishList.setCommerceWishListId(commerceWishListId);
	}

	/**
	* Sets the company ID of this commerce wish list.
	*
	* @param companyId the company ID of this commerce wish list
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceWishList.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce wish list.
	*
	* @param createDate the create date of this commerce wish list
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceWishList.setCreateDate(createDate);
	}

	/**
	* Sets whether this commerce wish list is default wish list.
	*
	* @param defaultWishList the default wish list of this commerce wish list
	*/
	@Override
	public void setDefaultWishList(boolean defaultWishList) {
		_commerceWishList.setDefaultWishList(defaultWishList);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceWishList.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceWishList.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceWishList.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce wish list.
	*
	* @param groupId the group ID of this commerce wish list
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceWishList.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce wish list.
	*
	* @param modifiedDate the modified date of this commerce wish list
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceWishList.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this commerce wish list.
	*
	* @param name the name of this commerce wish list
	*/
	@Override
	public void setName(java.lang.String name) {
		_commerceWishList.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_commerceWishList.setNew(n);
	}

	/**
	* Sets the primary key of this commerce wish list.
	*
	* @param primaryKey the primary key of this commerce wish list
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceWishList.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceWishList.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this commerce wish list.
	*
	* @param userId the user ID of this commerce wish list
	*/
	@Override
	public void setUserId(long userId) {
		_commerceWishList.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce wish list.
	*
	* @param userName the user name of this commerce wish list
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceWishList.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce wish list.
	*
	* @param userUuid the user uuid of this commerce wish list
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceWishList.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this commerce wish list.
	*
	* @param uuid the uuid of this commerce wish list
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_commerceWishList.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceWishList> toCacheModel() {
		return _commerceWishList.toCacheModel();
	}

	@Override
	public CommerceWishList toEscapedModel() {
		return new CommerceWishListWrapper(_commerceWishList.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceWishList.toString();
	}

	@Override
	public CommerceWishList toUnescapedModel() {
		return new CommerceWishListWrapper(_commerceWishList.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceWishList.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceWishListWrapper)) {
			return false;
		}

		CommerceWishListWrapper commerceWishListWrapper = (CommerceWishListWrapper)obj;

		if (Objects.equals(_commerceWishList,
					commerceWishListWrapper._commerceWishList)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commerceWishList.getStagedModelType();
	}

	@Override
	public CommerceWishList getWrappedModel() {
		return _commerceWishList;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceWishList.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceWishList.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceWishList.resetOriginalValues();
	}

	private final CommerceWishList _commerceWishList;
}