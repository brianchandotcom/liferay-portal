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

package com.liferay.commerce.discount.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CommerceDiscountUserSegmentRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountUserSegmentRel
 * @generated
 */
@ProviderType
public class CommerceDiscountUserSegmentRelWrapper
	implements CommerceDiscountUserSegmentRel,
		ModelWrapper<CommerceDiscountUserSegmentRel> {
	public CommerceDiscountUserSegmentRelWrapper(
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel) {
		_commerceDiscountUserSegmentRel = commerceDiscountUserSegmentRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceDiscountUserSegmentRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceDiscountUserSegmentRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceDiscountUserSegmentRelId",
			getCommerceDiscountUserSegmentRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceDiscountId", getCommerceDiscountId());
		attributes.put("commerceUserSegmentEntryId",
			getCommerceUserSegmentEntryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceDiscountUserSegmentRelId = (Long)attributes.get(
				"commerceDiscountUserSegmentRelId");

		if (commerceDiscountUserSegmentRelId != null) {
			setCommerceDiscountUserSegmentRelId(commerceDiscountUserSegmentRelId);
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

		Long commerceDiscountId = (Long)attributes.get("commerceDiscountId");

		if (commerceDiscountId != null) {
			setCommerceDiscountId(commerceDiscountId);
		}

		Long commerceUserSegmentEntryId = (Long)attributes.get(
				"commerceUserSegmentEntryId");

		if (commerceUserSegmentEntryId != null) {
			setCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceDiscountUserSegmentRelWrapper((CommerceDiscountUserSegmentRel)_commerceDiscountUserSegmentRel.clone());
	}

	@Override
	public int compareTo(
		CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel) {
		return _commerceDiscountUserSegmentRel.compareTo(commerceDiscountUserSegmentRel);
	}

	/**
	* Returns the commerce discount ID of this commerce discount user segment rel.
	*
	* @return the commerce discount ID of this commerce discount user segment rel
	*/
	@Override
	public long getCommerceDiscountId() {
		return _commerceDiscountUserSegmentRel.getCommerceDiscountId();
	}

	/**
	* Returns the commerce discount user segment rel ID of this commerce discount user segment rel.
	*
	* @return the commerce discount user segment rel ID of this commerce discount user segment rel
	*/
	@Override
	public long getCommerceDiscountUserSegmentRelId() {
		return _commerceDiscountUserSegmentRel.getCommerceDiscountUserSegmentRelId();
	}

	/**
	* Returns the commerce user segment entry ID of this commerce discount user segment rel.
	*
	* @return the commerce user segment entry ID of this commerce discount user segment rel
	*/
	@Override
	public long getCommerceUserSegmentEntryId() {
		return _commerceDiscountUserSegmentRel.getCommerceUserSegmentEntryId();
	}

	/**
	* Returns the company ID of this commerce discount user segment rel.
	*
	* @return the company ID of this commerce discount user segment rel
	*/
	@Override
	public long getCompanyId() {
		return _commerceDiscountUserSegmentRel.getCompanyId();
	}

	/**
	* Returns the create date of this commerce discount user segment rel.
	*
	* @return the create date of this commerce discount user segment rel
	*/
	@Override
	public Date getCreateDate() {
		return _commerceDiscountUserSegmentRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceDiscountUserSegmentRel.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce discount user segment rel.
	*
	* @return the group ID of this commerce discount user segment rel
	*/
	@Override
	public long getGroupId() {
		return _commerceDiscountUserSegmentRel.getGroupId();
	}

	/**
	* Returns the modified date of this commerce discount user segment rel.
	*
	* @return the modified date of this commerce discount user segment rel
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceDiscountUserSegmentRel.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce discount user segment rel.
	*
	* @return the primary key of this commerce discount user segment rel
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceDiscountUserSegmentRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceDiscountUserSegmentRel.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this commerce discount user segment rel.
	*
	* @return the user ID of this commerce discount user segment rel
	*/
	@Override
	public long getUserId() {
		return _commerceDiscountUserSegmentRel.getUserId();
	}

	/**
	* Returns the user name of this commerce discount user segment rel.
	*
	* @return the user name of this commerce discount user segment rel
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceDiscountUserSegmentRel.getUserName();
	}

	/**
	* Returns the user uuid of this commerce discount user segment rel.
	*
	* @return the user uuid of this commerce discount user segment rel
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceDiscountUserSegmentRel.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceDiscountUserSegmentRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceDiscountUserSegmentRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceDiscountUserSegmentRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceDiscountUserSegmentRel.isNew();
	}

	@Override
	public void persist() {
		_commerceDiscountUserSegmentRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceDiscountUserSegmentRel.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce discount ID of this commerce discount user segment rel.
	*
	* @param commerceDiscountId the commerce discount ID of this commerce discount user segment rel
	*/
	@Override
	public void setCommerceDiscountId(long commerceDiscountId) {
		_commerceDiscountUserSegmentRel.setCommerceDiscountId(commerceDiscountId);
	}

	/**
	* Sets the commerce discount user segment rel ID of this commerce discount user segment rel.
	*
	* @param commerceDiscountUserSegmentRelId the commerce discount user segment rel ID of this commerce discount user segment rel
	*/
	@Override
	public void setCommerceDiscountUserSegmentRelId(
		long commerceDiscountUserSegmentRelId) {
		_commerceDiscountUserSegmentRel.setCommerceDiscountUserSegmentRelId(commerceDiscountUserSegmentRelId);
	}

	/**
	* Sets the commerce user segment entry ID of this commerce discount user segment rel.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID of this commerce discount user segment rel
	*/
	@Override
	public void setCommerceUserSegmentEntryId(long commerceUserSegmentEntryId) {
		_commerceDiscountUserSegmentRel.setCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	/**
	* Sets the company ID of this commerce discount user segment rel.
	*
	* @param companyId the company ID of this commerce discount user segment rel
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceDiscountUserSegmentRel.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce discount user segment rel.
	*
	* @param createDate the create date of this commerce discount user segment rel
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceDiscountUserSegmentRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceDiscountUserSegmentRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceDiscountUserSegmentRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceDiscountUserSegmentRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce discount user segment rel.
	*
	* @param groupId the group ID of this commerce discount user segment rel
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceDiscountUserSegmentRel.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce discount user segment rel.
	*
	* @param modifiedDate the modified date of this commerce discount user segment rel
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceDiscountUserSegmentRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceDiscountUserSegmentRel.setNew(n);
	}

	/**
	* Sets the primary key of this commerce discount user segment rel.
	*
	* @param primaryKey the primary key of this commerce discount user segment rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceDiscountUserSegmentRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceDiscountUserSegmentRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this commerce discount user segment rel.
	*
	* @param userId the user ID of this commerce discount user segment rel
	*/
	@Override
	public void setUserId(long userId) {
		_commerceDiscountUserSegmentRel.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce discount user segment rel.
	*
	* @param userName the user name of this commerce discount user segment rel
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceDiscountUserSegmentRel.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce discount user segment rel.
	*
	* @param userUuid the user uuid of this commerce discount user segment rel
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceDiscountUserSegmentRel.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceDiscountUserSegmentRel> toCacheModel() {
		return _commerceDiscountUserSegmentRel.toCacheModel();
	}

	@Override
	public CommerceDiscountUserSegmentRel toEscapedModel() {
		return new CommerceDiscountUserSegmentRelWrapper(_commerceDiscountUserSegmentRel.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceDiscountUserSegmentRel.toString();
	}

	@Override
	public CommerceDiscountUserSegmentRel toUnescapedModel() {
		return new CommerceDiscountUserSegmentRelWrapper(_commerceDiscountUserSegmentRel.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceDiscountUserSegmentRel.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceDiscountUserSegmentRelWrapper)) {
			return false;
		}

		CommerceDiscountUserSegmentRelWrapper commerceDiscountUserSegmentRelWrapper =
			(CommerceDiscountUserSegmentRelWrapper)obj;

		if (Objects.equals(_commerceDiscountUserSegmentRel,
					commerceDiscountUserSegmentRelWrapper._commerceDiscountUserSegmentRel)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceDiscountUserSegmentRel getWrappedModel() {
		return _commerceDiscountUserSegmentRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceDiscountUserSegmentRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceDiscountUserSegmentRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceDiscountUserSegmentRel.resetOriginalValues();
	}

	private final CommerceDiscountUserSegmentRel _commerceDiscountUserSegmentRel;
}