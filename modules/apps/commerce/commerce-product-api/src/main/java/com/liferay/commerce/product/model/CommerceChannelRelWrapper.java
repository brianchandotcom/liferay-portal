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

package com.liferay.commerce.product.model;

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
 * This class is a wrapper for {@link CommerceChannelRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceChannelRel
 * @generated
 */
public class CommerceChannelRelWrapper
	implements CommerceChannelRel, ModelWrapper<CommerceChannelRel> {

	public CommerceChannelRelWrapper(CommerceChannelRel commerceChannelRel) {
		_commerceChannelRel = commerceChannelRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceChannelRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceChannelRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceChannelRelId", getCommerceChannelRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("commerceChannelId", getCommerceChannelId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceChannelRelId = (Long)attributes.get(
			"commerceChannelRelId");

		if (commerceChannelRelId != null) {
			setCommerceChannelRelId(commerceChannelRelId);
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

		Long commerceChannelId = (Long)attributes.get("commerceChannelId");

		if (commerceChannelId != null) {
			setCommerceChannelId(commerceChannelId);
		}
	}

	@Override
	public Object clone() {
		return new CommerceChannelRelWrapper(
			(CommerceChannelRel)_commerceChannelRel.clone());
	}

	@Override
	public int compareTo(CommerceChannelRel commerceChannelRel) {
		return _commerceChannelRel.compareTo(commerceChannelRel);
	}

	/**
	 * Returns the fully qualified class name of this commerce channel rel.
	 *
	 * @return the fully qualified class name of this commerce channel rel
	 */
	@Override
	public String getClassName() {
		return _commerceChannelRel.getClassName();
	}

	/**
	 * Returns the class name ID of this commerce channel rel.
	 *
	 * @return the class name ID of this commerce channel rel
	 */
	@Override
	public long getClassNameId() {
		return _commerceChannelRel.getClassNameId();
	}

	/**
	 * Returns the class pk of this commerce channel rel.
	 *
	 * @return the class pk of this commerce channel rel
	 */
	@Override
	public long getClassPK() {
		return _commerceChannelRel.getClassPK();
	}

	@Override
	public CommerceChannel getCommerceChannel()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelRel.getCommerceChannel();
	}

	/**
	 * Returns the commerce channel ID of this commerce channel rel.
	 *
	 * @return the commerce channel ID of this commerce channel rel
	 */
	@Override
	public long getCommerceChannelId() {
		return _commerceChannelRel.getCommerceChannelId();
	}

	/**
	 * Returns the commerce channel rel ID of this commerce channel rel.
	 *
	 * @return the commerce channel rel ID of this commerce channel rel
	 */
	@Override
	public long getCommerceChannelRelId() {
		return _commerceChannelRel.getCommerceChannelRelId();
	}

	/**
	 * Returns the company ID of this commerce channel rel.
	 *
	 * @return the company ID of this commerce channel rel
	 */
	@Override
	public long getCompanyId() {
		return _commerceChannelRel.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce channel rel.
	 *
	 * @return the create date of this commerce channel rel
	 */
	@Override
	public Date getCreateDate() {
		return _commerceChannelRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceChannelRel.getExpandoBridge();
	}

	/**
	 * Returns the modified date of this commerce channel rel.
	 *
	 * @return the modified date of this commerce channel rel
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceChannelRel.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce channel rel.
	 *
	 * @return the primary key of this commerce channel rel
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceChannelRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceChannelRel.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this commerce channel rel.
	 *
	 * @return the user ID of this commerce channel rel
	 */
	@Override
	public long getUserId() {
		return _commerceChannelRel.getUserId();
	}

	/**
	 * Returns the user name of this commerce channel rel.
	 *
	 * @return the user name of this commerce channel rel
	 */
	@Override
	public String getUserName() {
		return _commerceChannelRel.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce channel rel.
	 *
	 * @return the user uuid of this commerce channel rel
	 */
	@Override
	public String getUserUuid() {
		return _commerceChannelRel.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceChannelRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceChannelRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceChannelRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceChannelRel.isNew();
	}

	@Override
	public void persist() {
		_commerceChannelRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceChannelRel.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(String className) {
		_commerceChannelRel.setClassName(className);
	}

	/**
	 * Sets the class name ID of this commerce channel rel.
	 *
	 * @param classNameId the class name ID of this commerce channel rel
	 */
	@Override
	public void setClassNameId(long classNameId) {
		_commerceChannelRel.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this commerce channel rel.
	 *
	 * @param classPK the class pk of this commerce channel rel
	 */
	@Override
	public void setClassPK(long classPK) {
		_commerceChannelRel.setClassPK(classPK);
	}

	/**
	 * Sets the commerce channel ID of this commerce channel rel.
	 *
	 * @param commerceChannelId the commerce channel ID of this commerce channel rel
	 */
	@Override
	public void setCommerceChannelId(long commerceChannelId) {
		_commerceChannelRel.setCommerceChannelId(commerceChannelId);
	}

	/**
	 * Sets the commerce channel rel ID of this commerce channel rel.
	 *
	 * @param commerceChannelRelId the commerce channel rel ID of this commerce channel rel
	 */
	@Override
	public void setCommerceChannelRelId(long commerceChannelRelId) {
		_commerceChannelRel.setCommerceChannelRelId(commerceChannelRelId);
	}

	/**
	 * Sets the company ID of this commerce channel rel.
	 *
	 * @param companyId the company ID of this commerce channel rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceChannelRel.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce channel rel.
	 *
	 * @param createDate the create date of this commerce channel rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceChannelRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceChannelRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceChannelRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceChannelRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the modified date of this commerce channel rel.
	 *
	 * @param modifiedDate the modified date of this commerce channel rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceChannelRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceChannelRel.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce channel rel.
	 *
	 * @param primaryKey the primary key of this commerce channel rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceChannelRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceChannelRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this commerce channel rel.
	 *
	 * @param userId the user ID of this commerce channel rel
	 */
	@Override
	public void setUserId(long userId) {
		_commerceChannelRel.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce channel rel.
	 *
	 * @param userName the user name of this commerce channel rel
	 */
	@Override
	public void setUserName(String userName) {
		_commerceChannelRel.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce channel rel.
	 *
	 * @param userUuid the user uuid of this commerce channel rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceChannelRel.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceChannelRel>
		toCacheModel() {

		return _commerceChannelRel.toCacheModel();
	}

	@Override
	public CommerceChannelRel toEscapedModel() {
		return new CommerceChannelRelWrapper(
			_commerceChannelRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceChannelRel.toString();
	}

	@Override
	public CommerceChannelRel toUnescapedModel() {
		return new CommerceChannelRelWrapper(
			_commerceChannelRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceChannelRel.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceChannelRelWrapper)) {
			return false;
		}

		CommerceChannelRelWrapper commerceChannelRelWrapper =
			(CommerceChannelRelWrapper)object;

		if (Objects.equals(
				_commerceChannelRel,
				commerceChannelRelWrapper._commerceChannelRel)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceChannelRel getWrappedModel() {
		return _commerceChannelRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceChannelRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceChannelRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceChannelRel.resetOriginalValues();
	}

	private final CommerceChannelRel _commerceChannelRel;

}