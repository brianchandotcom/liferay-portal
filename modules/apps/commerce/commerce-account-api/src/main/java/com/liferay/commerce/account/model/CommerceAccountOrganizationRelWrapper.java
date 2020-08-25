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

package com.liferay.commerce.account.model;

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
 * This class is a wrapper for {@link CommerceAccountOrganizationRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceAccountOrganizationRel
 * @generated
 */
public class CommerceAccountOrganizationRelWrapper
	implements CommerceAccountOrganizationRel,
			   ModelWrapper<CommerceAccountOrganizationRel> {

	public CommerceAccountOrganizationRelWrapper(
		CommerceAccountOrganizationRel commerceAccountOrganizationRel) {

		_commerceAccountOrganizationRel = commerceAccountOrganizationRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceAccountOrganizationRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceAccountOrganizationRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceAccountId", getCommerceAccountId());
		attributes.put("organizationId", getOrganizationId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceAccountId = (Long)attributes.get("commerceAccountId");

		if (commerceAccountId != null) {
			setCommerceAccountId(commerceAccountId);
		}

		Long organizationId = (Long)attributes.get("organizationId");

		if (organizationId != null) {
			setOrganizationId(organizationId);
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
	}

	@Override
	public Object clone() {
		return new CommerceAccountOrganizationRelWrapper(
			(CommerceAccountOrganizationRel)
				_commerceAccountOrganizationRel.clone());
	}

	@Override
	public int compareTo(
		CommerceAccountOrganizationRel commerceAccountOrganizationRel) {

		return _commerceAccountOrganizationRel.compareTo(
			commerceAccountOrganizationRel);
	}

	/**
	 * Returns the commerce account ID of this commerce account organization rel.
	 *
	 * @return the commerce account ID of this commerce account organization rel
	 */
	@Override
	public long getCommerceAccountId() {
		return _commerceAccountOrganizationRel.getCommerceAccountId();
	}

	/**
	 * Returns the company ID of this commerce account organization rel.
	 *
	 * @return the company ID of this commerce account organization rel
	 */
	@Override
	public long getCompanyId() {
		return _commerceAccountOrganizationRel.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce account organization rel.
	 *
	 * @return the create date of this commerce account organization rel
	 */
	@Override
	public Date getCreateDate() {
		return _commerceAccountOrganizationRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceAccountOrganizationRel.getExpandoBridge();
	}

	/**
	 * Returns the modified date of this commerce account organization rel.
	 *
	 * @return the modified date of this commerce account organization rel
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceAccountOrganizationRel.getModifiedDate();
	}

	@Override
	public com.liferay.portal.kernel.model.Organization getOrganization()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountOrganizationRel.getOrganization();
	}

	/**
	 * Returns the organization ID of this commerce account organization rel.
	 *
	 * @return the organization ID of this commerce account organization rel
	 */
	@Override
	public long getOrganizationId() {
		return _commerceAccountOrganizationRel.getOrganizationId();
	}

	/**
	 * Returns the primary key of this commerce account organization rel.
	 *
	 * @return the primary key of this commerce account organization rel
	 */
	@Override
	public com.liferay.commerce.account.service.persistence.
		CommerceAccountOrganizationRelPK getPrimaryKey() {

		return _commerceAccountOrganizationRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceAccountOrganizationRel.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this commerce account organization rel.
	 *
	 * @return the user ID of this commerce account organization rel
	 */
	@Override
	public long getUserId() {
		return _commerceAccountOrganizationRel.getUserId();
	}

	/**
	 * Returns the user name of this commerce account organization rel.
	 *
	 * @return the user name of this commerce account organization rel
	 */
	@Override
	public String getUserName() {
		return _commerceAccountOrganizationRel.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce account organization rel.
	 *
	 * @return the user uuid of this commerce account organization rel
	 */
	@Override
	public String getUserUuid() {
		return _commerceAccountOrganizationRel.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceAccountOrganizationRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceAccountOrganizationRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceAccountOrganizationRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceAccountOrganizationRel.isNew();
	}

	@Override
	public void persist() {
		_commerceAccountOrganizationRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceAccountOrganizationRel.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce account ID of this commerce account organization rel.
	 *
	 * @param commerceAccountId the commerce account ID of this commerce account organization rel
	 */
	@Override
	public void setCommerceAccountId(long commerceAccountId) {
		_commerceAccountOrganizationRel.setCommerceAccountId(commerceAccountId);
	}

	/**
	 * Sets the company ID of this commerce account organization rel.
	 *
	 * @param companyId the company ID of this commerce account organization rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceAccountOrganizationRel.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce account organization rel.
	 *
	 * @param createDate the create date of this commerce account organization rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceAccountOrganizationRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceAccountOrganizationRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceAccountOrganizationRel.setExpandoBridgeAttributes(
			expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceAccountOrganizationRel.setExpandoBridgeAttributes(
			serviceContext);
	}

	/**
	 * Sets the modified date of this commerce account organization rel.
	 *
	 * @param modifiedDate the modified date of this commerce account organization rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceAccountOrganizationRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceAccountOrganizationRel.setNew(n);
	}

	/**
	 * Sets the organization ID of this commerce account organization rel.
	 *
	 * @param organizationId the organization ID of this commerce account organization rel
	 */
	@Override
	public void setOrganizationId(long organizationId) {
		_commerceAccountOrganizationRel.setOrganizationId(organizationId);
	}

	/**
	 * Sets the primary key of this commerce account organization rel.
	 *
	 * @param primaryKey the primary key of this commerce account organization rel
	 */
	@Override
	public void setPrimaryKey(
		com.liferay.commerce.account.service.persistence.
			CommerceAccountOrganizationRelPK primaryKey) {

		_commerceAccountOrganizationRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceAccountOrganizationRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this commerce account organization rel.
	 *
	 * @param userId the user ID of this commerce account organization rel
	 */
	@Override
	public void setUserId(long userId) {
		_commerceAccountOrganizationRel.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce account organization rel.
	 *
	 * @param userName the user name of this commerce account organization rel
	 */
	@Override
	public void setUserName(String userName) {
		_commerceAccountOrganizationRel.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce account organization rel.
	 *
	 * @param userUuid the user uuid of this commerce account organization rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceAccountOrganizationRel.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommerceAccountOrganizationRel> toCacheModel() {

		return _commerceAccountOrganizationRel.toCacheModel();
	}

	@Override
	public CommerceAccountOrganizationRel toEscapedModel() {
		return new CommerceAccountOrganizationRelWrapper(
			_commerceAccountOrganizationRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceAccountOrganizationRel.toString();
	}

	@Override
	public CommerceAccountOrganizationRel toUnescapedModel() {
		return new CommerceAccountOrganizationRelWrapper(
			_commerceAccountOrganizationRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceAccountOrganizationRel.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceAccountOrganizationRelWrapper)) {
			return false;
		}

		CommerceAccountOrganizationRelWrapper
			commerceAccountOrganizationRelWrapper =
				(CommerceAccountOrganizationRelWrapper)object;

		if (Objects.equals(
				_commerceAccountOrganizationRel,
				commerceAccountOrganizationRelWrapper.
					_commerceAccountOrganizationRel)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceAccountOrganizationRel getWrappedModel() {
		return _commerceAccountOrganizationRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceAccountOrganizationRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceAccountOrganizationRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceAccountOrganizationRel.resetOriginalValues();
	}

	private final CommerceAccountOrganizationRel
		_commerceAccountOrganizationRel;

}