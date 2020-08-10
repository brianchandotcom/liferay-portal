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
 * This class is a wrapper for {@link CommerceNotificationTemplateCommerceAccountGroupRel}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateCommerceAccountGroupRel
 * @generated
 */
public class CommerceNotificationTemplateCommerceAccountGroupRelWrapper
	implements CommerceNotificationTemplateCommerceAccountGroupRel,
			   ModelWrapper
				   <CommerceNotificationTemplateCommerceAccountGroupRel> {

	public CommerceNotificationTemplateCommerceAccountGroupRelWrapper(
		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel) {

		_commerceNotificationTemplateCommerceAccountGroupRel =
			commerceNotificationTemplateCommerceAccountGroupRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceNotificationTemplateCommerceAccountGroupRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceNotificationTemplateCommerceAccountGroupRel.class.
			getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"commerceNotificationTemplateCommerceAccountGroupRelId",
			getCommerceNotificationTemplateCommerceAccountGroupRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"commerceNotificationTemplateId",
			getCommerceNotificationTemplateId());
		attributes.put("commerceAccountGroupId", getCommerceAccountGroupId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceNotificationTemplateCommerceAccountGroupRelId =
			(Long)attributes.get(
				"commerceNotificationTemplateCommerceAccountGroupRelId");

		if (commerceNotificationTemplateCommerceAccountGroupRelId != null) {
			setCommerceNotificationTemplateCommerceAccountGroupRelId(
				commerceNotificationTemplateCommerceAccountGroupRelId);
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

		Long commerceAccountGroupId = (Long)attributes.get(
			"commerceAccountGroupId");

		if (commerceAccountGroupId != null) {
			setCommerceAccountGroupId(commerceAccountGroupId);
		}
	}

	@Override
	public Object clone() {
		return new CommerceNotificationTemplateCommerceAccountGroupRelWrapper(
			(CommerceNotificationTemplateCommerceAccountGroupRel)
				_commerceNotificationTemplateCommerceAccountGroupRel.clone());
	}

	@Override
	public int compareTo(
		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel) {

		return _commerceNotificationTemplateCommerceAccountGroupRel.compareTo(
			commerceNotificationTemplateCommerceAccountGroupRel);
	}

	/**
	 * Returns the commerce account group ID of this commerce notification template commerce account group rel.
	 *
	 * @return the commerce account group ID of this commerce notification template commerce account group rel
	 */
	@Override
	public long getCommerceAccountGroupId() {
		return _commerceNotificationTemplateCommerceAccountGroupRel.
			getCommerceAccountGroupId();
	}

	/**
	 * Returns the commerce notification template commerce account group rel ID of this commerce notification template commerce account group rel.
	 *
	 * @return the commerce notification template commerce account group rel ID of this commerce notification template commerce account group rel
	 */
	@Override
	public long getCommerceNotificationTemplateCommerceAccountGroupRelId() {
		return _commerceNotificationTemplateCommerceAccountGroupRel.
			getCommerceNotificationTemplateCommerceAccountGroupRelId();
	}

	/**
	 * Returns the commerce notification template ID of this commerce notification template commerce account group rel.
	 *
	 * @return the commerce notification template ID of this commerce notification template commerce account group rel
	 */
	@Override
	public long getCommerceNotificationTemplateId() {
		return _commerceNotificationTemplateCommerceAccountGroupRel.
			getCommerceNotificationTemplateId();
	}

	/**
	 * Returns the company ID of this commerce notification template commerce account group rel.
	 *
	 * @return the company ID of this commerce notification template commerce account group rel
	 */
	@Override
	public long getCompanyId() {
		return _commerceNotificationTemplateCommerceAccountGroupRel.
			getCompanyId();
	}

	/**
	 * Returns the create date of this commerce notification template commerce account group rel.
	 *
	 * @return the create date of this commerce notification template commerce account group rel
	 */
	@Override
	public Date getCreateDate() {
		return _commerceNotificationTemplateCommerceAccountGroupRel.
			getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceNotificationTemplateCommerceAccountGroupRel.
			getExpandoBridge();
	}

	/**
	 * Returns the group ID of this commerce notification template commerce account group rel.
	 *
	 * @return the group ID of this commerce notification template commerce account group rel
	 */
	@Override
	public long getGroupId() {
		return _commerceNotificationTemplateCommerceAccountGroupRel.
			getGroupId();
	}

	/**
	 * Returns the modified date of this commerce notification template commerce account group rel.
	 *
	 * @return the modified date of this commerce notification template commerce account group rel
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceNotificationTemplateCommerceAccountGroupRel.
			getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce notification template commerce account group rel.
	 *
	 * @return the primary key of this commerce notification template commerce account group rel
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceNotificationTemplateCommerceAccountGroupRel.
			getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceNotificationTemplateCommerceAccountGroupRel.
			getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this commerce notification template commerce account group rel.
	 *
	 * @return the user ID of this commerce notification template commerce account group rel
	 */
	@Override
	public long getUserId() {
		return _commerceNotificationTemplateCommerceAccountGroupRel.getUserId();
	}

	/**
	 * Returns the user name of this commerce notification template commerce account group rel.
	 *
	 * @return the user name of this commerce notification template commerce account group rel
	 */
	@Override
	public String getUserName() {
		return _commerceNotificationTemplateCommerceAccountGroupRel.
			getUserName();
	}

	/**
	 * Returns the user uuid of this commerce notification template commerce account group rel.
	 *
	 * @return the user uuid of this commerce notification template commerce account group rel
	 */
	@Override
	public String getUserUuid() {
		return _commerceNotificationTemplateCommerceAccountGroupRel.
			getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceNotificationTemplateCommerceAccountGroupRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceNotificationTemplateCommerceAccountGroupRel.
			isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceNotificationTemplateCommerceAccountGroupRel.
			isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceNotificationTemplateCommerceAccountGroupRel.isNew();
	}

	@Override
	public void persist() {
		_commerceNotificationTemplateCommerceAccountGroupRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceNotificationTemplateCommerceAccountGroupRel.setCachedModel(
			cachedModel);
	}

	/**
	 * Sets the commerce account group ID of this commerce notification template commerce account group rel.
	 *
	 * @param commerceAccountGroupId the commerce account group ID of this commerce notification template commerce account group rel
	 */
	@Override
	public void setCommerceAccountGroupId(long commerceAccountGroupId) {
		_commerceNotificationTemplateCommerceAccountGroupRel.
			setCommerceAccountGroupId(commerceAccountGroupId);
	}

	/**
	 * Sets the commerce notification template commerce account group rel ID of this commerce notification template commerce account group rel.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRelId the commerce notification template commerce account group rel ID of this commerce notification template commerce account group rel
	 */
	@Override
	public void setCommerceNotificationTemplateCommerceAccountGroupRelId(
		long commerceNotificationTemplateCommerceAccountGroupRelId) {

		_commerceNotificationTemplateCommerceAccountGroupRel.
			setCommerceNotificationTemplateCommerceAccountGroupRelId(
				commerceNotificationTemplateCommerceAccountGroupRelId);
	}

	/**
	 * Sets the commerce notification template ID of this commerce notification template commerce account group rel.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID of this commerce notification template commerce account group rel
	 */
	@Override
	public void setCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {

		_commerceNotificationTemplateCommerceAccountGroupRel.
			setCommerceNotificationTemplateId(commerceNotificationTemplateId);
	}

	/**
	 * Sets the company ID of this commerce notification template commerce account group rel.
	 *
	 * @param companyId the company ID of this commerce notification template commerce account group rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceNotificationTemplateCommerceAccountGroupRel.setCompanyId(
			companyId);
	}

	/**
	 * Sets the create date of this commerce notification template commerce account group rel.
	 *
	 * @param createDate the create date of this commerce notification template commerce account group rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceNotificationTemplateCommerceAccountGroupRel.setCreateDate(
			createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceNotificationTemplateCommerceAccountGroupRel.
			setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceNotificationTemplateCommerceAccountGroupRel.
			setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceNotificationTemplateCommerceAccountGroupRel.
			setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the group ID of this commerce notification template commerce account group rel.
	 *
	 * @param groupId the group ID of this commerce notification template commerce account group rel
	 */
	@Override
	public void setGroupId(long groupId) {
		_commerceNotificationTemplateCommerceAccountGroupRel.setGroupId(
			groupId);
	}

	/**
	 * Sets the modified date of this commerce notification template commerce account group rel.
	 *
	 * @param modifiedDate the modified date of this commerce notification template commerce account group rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceNotificationTemplateCommerceAccountGroupRel.setModifiedDate(
			modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceNotificationTemplateCommerceAccountGroupRel.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce notification template commerce account group rel.
	 *
	 * @param primaryKey the primary key of this commerce notification template commerce account group rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceNotificationTemplateCommerceAccountGroupRel.setPrimaryKey(
			primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceNotificationTemplateCommerceAccountGroupRel.setPrimaryKeyObj(
			primaryKeyObj);
	}

	/**
	 * Sets the user ID of this commerce notification template commerce account group rel.
	 *
	 * @param userId the user ID of this commerce notification template commerce account group rel
	 */
	@Override
	public void setUserId(long userId) {
		_commerceNotificationTemplateCommerceAccountGroupRel.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce notification template commerce account group rel.
	 *
	 * @param userName the user name of this commerce notification template commerce account group rel
	 */
	@Override
	public void setUserName(String userName) {
		_commerceNotificationTemplateCommerceAccountGroupRel.setUserName(
			userName);
	}

	/**
	 * Sets the user uuid of this commerce notification template commerce account group rel.
	 *
	 * @param userUuid the user uuid of this commerce notification template commerce account group rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceNotificationTemplateCommerceAccountGroupRel.setUserUuid(
			userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommerceNotificationTemplateCommerceAccountGroupRel> toCacheModel() {

		return _commerceNotificationTemplateCommerceAccountGroupRel.
			toCacheModel();
	}

	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel
		toEscapedModel() {

		return new CommerceNotificationTemplateCommerceAccountGroupRelWrapper(
			_commerceNotificationTemplateCommerceAccountGroupRel.
				toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceNotificationTemplateCommerceAccountGroupRel.toString();
	}

	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel
		toUnescapedModel() {

		return new CommerceNotificationTemplateCommerceAccountGroupRelWrapper(
			_commerceNotificationTemplateCommerceAccountGroupRel.
				toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceNotificationTemplateCommerceAccountGroupRel.
			toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof
				CommerceNotificationTemplateCommerceAccountGroupRelWrapper)) {

			return false;
		}

		CommerceNotificationTemplateCommerceAccountGroupRelWrapper
			commerceNotificationTemplateCommerceAccountGroupRelWrapper =
				(CommerceNotificationTemplateCommerceAccountGroupRelWrapper)
					object;

		if (Objects.equals(
				_commerceNotificationTemplateCommerceAccountGroupRel,
				commerceNotificationTemplateCommerceAccountGroupRelWrapper.
					_commerceNotificationTemplateCommerceAccountGroupRel)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel
		getWrappedModel() {

		return _commerceNotificationTemplateCommerceAccountGroupRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceNotificationTemplateCommerceAccountGroupRel.
			isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceNotificationTemplateCommerceAccountGroupRel.
			isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceNotificationTemplateCommerceAccountGroupRel.
			resetOriginalValues();
	}

	private final CommerceNotificationTemplateCommerceAccountGroupRel
		_commerceNotificationTemplateCommerceAccountGroupRel;

}