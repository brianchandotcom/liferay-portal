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
 * This class is a wrapper for {@link CommerceAccountGroupCommerceAccountRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceAccountGroupCommerceAccountRel
 * @generated
 */
public class CommerceAccountGroupCommerceAccountRelWrapper
	implements CommerceAccountGroupCommerceAccountRel,
			   ModelWrapper<CommerceAccountGroupCommerceAccountRel> {

	public CommerceAccountGroupCommerceAccountRelWrapper(
		CommerceAccountGroupCommerceAccountRel
			commerceAccountGroupCommerceAccountRel) {

		_commerceAccountGroupCommerceAccountRel =
			commerceAccountGroupCommerceAccountRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceAccountGroupCommerceAccountRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceAccountGroupCommerceAccountRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put(
			"commerceAccountGroupCommerceAccountRelId",
			getCommerceAccountGroupCommerceAccountRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceAccountGroupId", getCommerceAccountGroupId());
		attributes.put("commerceAccountId", getCommerceAccountId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long commerceAccountGroupCommerceAccountRelId = (Long)attributes.get(
			"commerceAccountGroupCommerceAccountRelId");

		if (commerceAccountGroupCommerceAccountRelId != null) {
			setCommerceAccountGroupCommerceAccountRelId(
				commerceAccountGroupCommerceAccountRelId);
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

		Long commerceAccountGroupId = (Long)attributes.get(
			"commerceAccountGroupId");

		if (commerceAccountGroupId != null) {
			setCommerceAccountGroupId(commerceAccountGroupId);
		}

		Long commerceAccountId = (Long)attributes.get("commerceAccountId");

		if (commerceAccountId != null) {
			setCommerceAccountId(commerceAccountId);
		}
	}

	@Override
	public Object clone() {
		return new CommerceAccountGroupCommerceAccountRelWrapper(
			(CommerceAccountGroupCommerceAccountRel)
				_commerceAccountGroupCommerceAccountRel.clone());
	}

	@Override
	public int compareTo(
		CommerceAccountGroupCommerceAccountRel
			commerceAccountGroupCommerceAccountRel) {

		return _commerceAccountGroupCommerceAccountRel.compareTo(
			commerceAccountGroupCommerceAccountRel);
	}

	@Override
	public CommerceAccount getCommerceAccount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupCommerceAccountRel.getCommerceAccount();
	}

	/**
	 * Returns the commerce account group commerce account rel ID of this commerce account group commerce account rel.
	 *
	 * @return the commerce account group commerce account rel ID of this commerce account group commerce account rel
	 */
	@Override
	public long getCommerceAccountGroupCommerceAccountRelId() {
		return _commerceAccountGroupCommerceAccountRel.
			getCommerceAccountGroupCommerceAccountRelId();
	}

	/**
	 * Returns the commerce account group ID of this commerce account group commerce account rel.
	 *
	 * @return the commerce account group ID of this commerce account group commerce account rel
	 */
	@Override
	public long getCommerceAccountGroupId() {
		return _commerceAccountGroupCommerceAccountRel.
			getCommerceAccountGroupId();
	}

	/**
	 * Returns the commerce account ID of this commerce account group commerce account rel.
	 *
	 * @return the commerce account ID of this commerce account group commerce account rel
	 */
	@Override
	public long getCommerceAccountId() {
		return _commerceAccountGroupCommerceAccountRel.getCommerceAccountId();
	}

	/**
	 * Returns the company ID of this commerce account group commerce account rel.
	 *
	 * @return the company ID of this commerce account group commerce account rel
	 */
	@Override
	public long getCompanyId() {
		return _commerceAccountGroupCommerceAccountRel.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce account group commerce account rel.
	 *
	 * @return the create date of this commerce account group commerce account rel
	 */
	@Override
	public Date getCreateDate() {
		return _commerceAccountGroupCommerceAccountRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceAccountGroupCommerceAccountRel.getExpandoBridge();
	}

	/**
	 * Returns the external reference code of this commerce account group commerce account rel.
	 *
	 * @return the external reference code of this commerce account group commerce account rel
	 */
	@Override
	public String getExternalReferenceCode() {
		return _commerceAccountGroupCommerceAccountRel.
			getExternalReferenceCode();
	}

	/**
	 * Returns the modified date of this commerce account group commerce account rel.
	 *
	 * @return the modified date of this commerce account group commerce account rel
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceAccountGroupCommerceAccountRel.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce account group commerce account rel.
	 *
	 * @return the primary key of this commerce account group commerce account rel
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceAccountGroupCommerceAccountRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceAccountGroupCommerceAccountRel.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this commerce account group commerce account rel.
	 *
	 * @return the user ID of this commerce account group commerce account rel
	 */
	@Override
	public long getUserId() {
		return _commerceAccountGroupCommerceAccountRel.getUserId();
	}

	/**
	 * Returns the user name of this commerce account group commerce account rel.
	 *
	 * @return the user name of this commerce account group commerce account rel
	 */
	@Override
	public String getUserName() {
		return _commerceAccountGroupCommerceAccountRel.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce account group commerce account rel.
	 *
	 * @return the user uuid of this commerce account group commerce account rel
	 */
	@Override
	public String getUserUuid() {
		return _commerceAccountGroupCommerceAccountRel.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceAccountGroupCommerceAccountRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceAccountGroupCommerceAccountRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceAccountGroupCommerceAccountRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceAccountGroupCommerceAccountRel.isNew();
	}

	@Override
	public void persist() {
		_commerceAccountGroupCommerceAccountRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceAccountGroupCommerceAccountRel.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce account group commerce account rel ID of this commerce account group commerce account rel.
	 *
	 * @param commerceAccountGroupCommerceAccountRelId the commerce account group commerce account rel ID of this commerce account group commerce account rel
	 */
	@Override
	public void setCommerceAccountGroupCommerceAccountRelId(
		long commerceAccountGroupCommerceAccountRelId) {

		_commerceAccountGroupCommerceAccountRel.
			setCommerceAccountGroupCommerceAccountRelId(
				commerceAccountGroupCommerceAccountRelId);
	}

	/**
	 * Sets the commerce account group ID of this commerce account group commerce account rel.
	 *
	 * @param commerceAccountGroupId the commerce account group ID of this commerce account group commerce account rel
	 */
	@Override
	public void setCommerceAccountGroupId(long commerceAccountGroupId) {
		_commerceAccountGroupCommerceAccountRel.setCommerceAccountGroupId(
			commerceAccountGroupId);
	}

	/**
	 * Sets the commerce account ID of this commerce account group commerce account rel.
	 *
	 * @param commerceAccountId the commerce account ID of this commerce account group commerce account rel
	 */
	@Override
	public void setCommerceAccountId(long commerceAccountId) {
		_commerceAccountGroupCommerceAccountRel.setCommerceAccountId(
			commerceAccountId);
	}

	/**
	 * Sets the company ID of this commerce account group commerce account rel.
	 *
	 * @param companyId the company ID of this commerce account group commerce account rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceAccountGroupCommerceAccountRel.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce account group commerce account rel.
	 *
	 * @param createDate the create date of this commerce account group commerce account rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceAccountGroupCommerceAccountRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceAccountGroupCommerceAccountRel.setExpandoBridgeAttributes(
			baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceAccountGroupCommerceAccountRel.setExpandoBridgeAttributes(
			expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceAccountGroupCommerceAccountRel.setExpandoBridgeAttributes(
			serviceContext);
	}

	/**
	 * Sets the external reference code of this commerce account group commerce account rel.
	 *
	 * @param externalReferenceCode the external reference code of this commerce account group commerce account rel
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		_commerceAccountGroupCommerceAccountRel.setExternalReferenceCode(
			externalReferenceCode);
	}

	/**
	 * Sets the modified date of this commerce account group commerce account rel.
	 *
	 * @param modifiedDate the modified date of this commerce account group commerce account rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceAccountGroupCommerceAccountRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceAccountGroupCommerceAccountRel.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce account group commerce account rel.
	 *
	 * @param primaryKey the primary key of this commerce account group commerce account rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceAccountGroupCommerceAccountRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceAccountGroupCommerceAccountRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this commerce account group commerce account rel.
	 *
	 * @param userId the user ID of this commerce account group commerce account rel
	 */
	@Override
	public void setUserId(long userId) {
		_commerceAccountGroupCommerceAccountRel.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce account group commerce account rel.
	 *
	 * @param userName the user name of this commerce account group commerce account rel
	 */
	@Override
	public void setUserName(String userName) {
		_commerceAccountGroupCommerceAccountRel.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce account group commerce account rel.
	 *
	 * @param userUuid the user uuid of this commerce account group commerce account rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceAccountGroupCommerceAccountRel.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommerceAccountGroupCommerceAccountRel> toCacheModel() {

		return _commerceAccountGroupCommerceAccountRel.toCacheModel();
	}

	@Override
	public CommerceAccountGroupCommerceAccountRel toEscapedModel() {
		return new CommerceAccountGroupCommerceAccountRelWrapper(
			_commerceAccountGroupCommerceAccountRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceAccountGroupCommerceAccountRel.toString();
	}

	@Override
	public CommerceAccountGroupCommerceAccountRel toUnescapedModel() {
		return new CommerceAccountGroupCommerceAccountRelWrapper(
			_commerceAccountGroupCommerceAccountRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceAccountGroupCommerceAccountRel.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof
				CommerceAccountGroupCommerceAccountRelWrapper)) {

			return false;
		}

		CommerceAccountGroupCommerceAccountRelWrapper
			commerceAccountGroupCommerceAccountRelWrapper =
				(CommerceAccountGroupCommerceAccountRelWrapper)object;

		if (Objects.equals(
				_commerceAccountGroupCommerceAccountRel,
				commerceAccountGroupCommerceAccountRelWrapper.
					_commerceAccountGroupCommerceAccountRel)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceAccountGroupCommerceAccountRel getWrappedModel() {
		return _commerceAccountGroupCommerceAccountRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceAccountGroupCommerceAccountRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceAccountGroupCommerceAccountRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceAccountGroupCommerceAccountRel.resetOriginalValues();
	}

	private final CommerceAccountGroupCommerceAccountRel
		_commerceAccountGroupCommerceAccountRel;

}