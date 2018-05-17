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

package com.liferay.commerce.notification.model;

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
 * This class is a wrapper for {@link CommerceNotificationTemplateUserSegmentRel}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateUserSegmentRel
 * @generated
 */
@ProviderType
public class CommerceNotificationTemplateUserSegmentRelWrapper
	implements CommerceNotificationTemplateUserSegmentRel,
		ModelWrapper<CommerceNotificationTemplateUserSegmentRel> {
	public CommerceNotificationTemplateUserSegmentRelWrapper(
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel) {
		_commerceNotificationTemplateUserSegmentRel = commerceNotificationTemplateUserSegmentRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceNotificationTemplateUserSegmentRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceNotificationTemplateUserSegmentRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceNotificationTemplateUserSegmentRelId",
			getCommerceNotificationTemplateUserSegmentRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceNotificationTemplateId",
			getCommerceNotificationTemplateId());
		attributes.put("commerceUserSegmentEntryId",
			getCommerceUserSegmentEntryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceNotificationTemplateUserSegmentRelId = (Long)attributes.get(
				"commerceNotificationTemplateUserSegmentRelId");

		if (commerceNotificationTemplateUserSegmentRelId != null) {
			setCommerceNotificationTemplateUserSegmentRelId(commerceNotificationTemplateUserSegmentRelId);
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

		Long commerceNotificationTemplateId = (Long)attributes.get(
				"commerceNotificationTemplateId");

		if (commerceNotificationTemplateId != null) {
			setCommerceNotificationTemplateId(commerceNotificationTemplateId);
		}

		Long commerceUserSegmentEntryId = (Long)attributes.get(
				"commerceUserSegmentEntryId");

		if (commerceUserSegmentEntryId != null) {
			setCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
		}
	}

	@Override
	public Object clone() {
		return new CommerceNotificationTemplateUserSegmentRelWrapper((CommerceNotificationTemplateUserSegmentRel)_commerceNotificationTemplateUserSegmentRel.clone());
	}

	@Override
	public int compareTo(
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel) {
		return _commerceNotificationTemplateUserSegmentRel.compareTo(commerceNotificationTemplateUserSegmentRel);
	}

	/**
	* Returns the commerce notification template ID of this commerce notification template user segment rel.
	*
	* @return the commerce notification template ID of this commerce notification template user segment rel
	*/
	@Override
	public long getCommerceNotificationTemplateId() {
		return _commerceNotificationTemplateUserSegmentRel.getCommerceNotificationTemplateId();
	}

	/**
	* Returns the commerce notification template user segment rel ID of this commerce notification template user segment rel.
	*
	* @return the commerce notification template user segment rel ID of this commerce notification template user segment rel
	*/
	@Override
	public long getCommerceNotificationTemplateUserSegmentRelId() {
		return _commerceNotificationTemplateUserSegmentRel.getCommerceNotificationTemplateUserSegmentRelId();
	}

	/**
	* Returns the commerce user segment entry ID of this commerce notification template user segment rel.
	*
	* @return the commerce user segment entry ID of this commerce notification template user segment rel
	*/
	@Override
	public long getCommerceUserSegmentEntryId() {
		return _commerceNotificationTemplateUserSegmentRel.getCommerceUserSegmentEntryId();
	}

	/**
	* Returns the company ID of this commerce notification template user segment rel.
	*
	* @return the company ID of this commerce notification template user segment rel
	*/
	@Override
	public long getCompanyId() {
		return _commerceNotificationTemplateUserSegmentRel.getCompanyId();
	}

	/**
	* Returns the create date of this commerce notification template user segment rel.
	*
	* @return the create date of this commerce notification template user segment rel
	*/
	@Override
	public Date getCreateDate() {
		return _commerceNotificationTemplateUserSegmentRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceNotificationTemplateUserSegmentRel.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce notification template user segment rel.
	*
	* @return the group ID of this commerce notification template user segment rel
	*/
	@Override
	public long getGroupId() {
		return _commerceNotificationTemplateUserSegmentRel.getGroupId();
	}

	/**
	* Returns the modified date of this commerce notification template user segment rel.
	*
	* @return the modified date of this commerce notification template user segment rel
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceNotificationTemplateUserSegmentRel.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce notification template user segment rel.
	*
	* @return the primary key of this commerce notification template user segment rel
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceNotificationTemplateUserSegmentRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceNotificationTemplateUserSegmentRel.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this commerce notification template user segment rel.
	*
	* @return the user ID of this commerce notification template user segment rel
	*/
	@Override
	public long getUserId() {
		return _commerceNotificationTemplateUserSegmentRel.getUserId();
	}

	/**
	* Returns the user name of this commerce notification template user segment rel.
	*
	* @return the user name of this commerce notification template user segment rel
	*/
	@Override
	public String getUserName() {
		return _commerceNotificationTemplateUserSegmentRel.getUserName();
	}

	/**
	* Returns the user uuid of this commerce notification template user segment rel.
	*
	* @return the user uuid of this commerce notification template user segment rel
	*/
	@Override
	public String getUserUuid() {
		return _commerceNotificationTemplateUserSegmentRel.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceNotificationTemplateUserSegmentRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceNotificationTemplateUserSegmentRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceNotificationTemplateUserSegmentRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceNotificationTemplateUserSegmentRel.isNew();
	}

	@Override
	public void persist() {
		_commerceNotificationTemplateUserSegmentRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceNotificationTemplateUserSegmentRel.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce notification template ID of this commerce notification template user segment rel.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID of this commerce notification template user segment rel
	*/
	@Override
	public void setCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {
		_commerceNotificationTemplateUserSegmentRel.setCommerceNotificationTemplateId(commerceNotificationTemplateId);
	}

	/**
	* Sets the commerce notification template user segment rel ID of this commerce notification template user segment rel.
	*
	* @param commerceNotificationTemplateUserSegmentRelId the commerce notification template user segment rel ID of this commerce notification template user segment rel
	*/
	@Override
	public void setCommerceNotificationTemplateUserSegmentRelId(
		long commerceNotificationTemplateUserSegmentRelId) {
		_commerceNotificationTemplateUserSegmentRel.setCommerceNotificationTemplateUserSegmentRelId(commerceNotificationTemplateUserSegmentRelId);
	}

	/**
	* Sets the commerce user segment entry ID of this commerce notification template user segment rel.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID of this commerce notification template user segment rel
	*/
	@Override
	public void setCommerceUserSegmentEntryId(long commerceUserSegmentEntryId) {
		_commerceNotificationTemplateUserSegmentRel.setCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	/**
	* Sets the company ID of this commerce notification template user segment rel.
	*
	* @param companyId the company ID of this commerce notification template user segment rel
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceNotificationTemplateUserSegmentRel.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce notification template user segment rel.
	*
	* @param createDate the create date of this commerce notification template user segment rel
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceNotificationTemplateUserSegmentRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceNotificationTemplateUserSegmentRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceNotificationTemplateUserSegmentRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceNotificationTemplateUserSegmentRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce notification template user segment rel.
	*
	* @param groupId the group ID of this commerce notification template user segment rel
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceNotificationTemplateUserSegmentRel.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce notification template user segment rel.
	*
	* @param modifiedDate the modified date of this commerce notification template user segment rel
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceNotificationTemplateUserSegmentRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceNotificationTemplateUserSegmentRel.setNew(n);
	}

	/**
	* Sets the primary key of this commerce notification template user segment rel.
	*
	* @param primaryKey the primary key of this commerce notification template user segment rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceNotificationTemplateUserSegmentRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceNotificationTemplateUserSegmentRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this commerce notification template user segment rel.
	*
	* @param userId the user ID of this commerce notification template user segment rel
	*/
	@Override
	public void setUserId(long userId) {
		_commerceNotificationTemplateUserSegmentRel.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce notification template user segment rel.
	*
	* @param userName the user name of this commerce notification template user segment rel
	*/
	@Override
	public void setUserName(String userName) {
		_commerceNotificationTemplateUserSegmentRel.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce notification template user segment rel.
	*
	* @param userUuid the user uuid of this commerce notification template user segment rel
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_commerceNotificationTemplateUserSegmentRel.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceNotificationTemplateUserSegmentRel> toCacheModel() {
		return _commerceNotificationTemplateUserSegmentRel.toCacheModel();
	}

	@Override
	public CommerceNotificationTemplateUserSegmentRel toEscapedModel() {
		return new CommerceNotificationTemplateUserSegmentRelWrapper(_commerceNotificationTemplateUserSegmentRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceNotificationTemplateUserSegmentRel.toString();
	}

	@Override
	public CommerceNotificationTemplateUserSegmentRel toUnescapedModel() {
		return new CommerceNotificationTemplateUserSegmentRelWrapper(_commerceNotificationTemplateUserSegmentRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceNotificationTemplateUserSegmentRel.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceNotificationTemplateUserSegmentRelWrapper)) {
			return false;
		}

		CommerceNotificationTemplateUserSegmentRelWrapper commerceNotificationTemplateUserSegmentRelWrapper =
			(CommerceNotificationTemplateUserSegmentRelWrapper)obj;

		if (Objects.equals(_commerceNotificationTemplateUserSegmentRel,
					commerceNotificationTemplateUserSegmentRelWrapper._commerceNotificationTemplateUserSegmentRel)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceNotificationTemplateUserSegmentRel getWrappedModel() {
		return _commerceNotificationTemplateUserSegmentRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceNotificationTemplateUserSegmentRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceNotificationTemplateUserSegmentRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceNotificationTemplateUserSegmentRel.resetOriginalValues();
	}

	private final CommerceNotificationTemplateUserSegmentRel _commerceNotificationTemplateUserSegmentRel;
}