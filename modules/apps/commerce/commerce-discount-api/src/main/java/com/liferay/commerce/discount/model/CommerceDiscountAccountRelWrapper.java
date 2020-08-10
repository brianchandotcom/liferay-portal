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
 * This class is a wrapper for {@link CommerceDiscountAccountRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountAccountRel
 * @generated
 */
public class CommerceDiscountAccountRelWrapper
	implements CommerceDiscountAccountRel,
			   ModelWrapper<CommerceDiscountAccountRel> {

	public CommerceDiscountAccountRelWrapper(
		CommerceDiscountAccountRel commerceDiscountAccountRel) {

		_commerceDiscountAccountRel = commerceDiscountAccountRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceDiscountAccountRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceDiscountAccountRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put(
			"commerceDiscountAccountRelId", getCommerceDiscountAccountRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceAccountId", getCommerceAccountId());
		attributes.put("commerceDiscountId", getCommerceDiscountId());
		attributes.put("order", getOrder());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long commerceDiscountAccountRelId = (Long)attributes.get(
			"commerceDiscountAccountRelId");

		if (commerceDiscountAccountRelId != null) {
			setCommerceDiscountAccountRelId(commerceDiscountAccountRelId);
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

		Long commerceAccountId = (Long)attributes.get("commerceAccountId");

		if (commerceAccountId != null) {
			setCommerceAccountId(commerceAccountId);
		}

		Long commerceDiscountId = (Long)attributes.get("commerceDiscountId");

		if (commerceDiscountId != null) {
			setCommerceDiscountId(commerceDiscountId);
		}

		Integer order = (Integer)attributes.get("order");

		if (order != null) {
			setOrder(order);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public Object clone() {
		return new CommerceDiscountAccountRelWrapper(
			(CommerceDiscountAccountRel)_commerceDiscountAccountRel.clone());
	}

	@Override
	public int compareTo(
		CommerceDiscountAccountRel commerceDiscountAccountRel) {

		return _commerceDiscountAccountRel.compareTo(
			commerceDiscountAccountRel);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			getCommerceAccount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDiscountAccountRel.getCommerceAccount();
	}

	/**
	 * Returns the commerce account ID of this commerce discount account rel.
	 *
	 * @return the commerce account ID of this commerce discount account rel
	 */
	@Override
	public long getCommerceAccountId() {
		return _commerceDiscountAccountRel.getCommerceAccountId();
	}

	@Override
	public CommerceDiscount getCommerceDiscount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDiscountAccountRel.getCommerceDiscount();
	}

	/**
	 * Returns the commerce discount account rel ID of this commerce discount account rel.
	 *
	 * @return the commerce discount account rel ID of this commerce discount account rel
	 */
	@Override
	public long getCommerceDiscountAccountRelId() {
		return _commerceDiscountAccountRel.getCommerceDiscountAccountRelId();
	}

	/**
	 * Returns the commerce discount ID of this commerce discount account rel.
	 *
	 * @return the commerce discount ID of this commerce discount account rel
	 */
	@Override
	public long getCommerceDiscountId() {
		return _commerceDiscountAccountRel.getCommerceDiscountId();
	}

	/**
	 * Returns the company ID of this commerce discount account rel.
	 *
	 * @return the company ID of this commerce discount account rel
	 */
	@Override
	public long getCompanyId() {
		return _commerceDiscountAccountRel.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce discount account rel.
	 *
	 * @return the create date of this commerce discount account rel
	 */
	@Override
	public Date getCreateDate() {
		return _commerceDiscountAccountRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceDiscountAccountRel.getExpandoBridge();
	}

	/**
	 * Returns the last publish date of this commerce discount account rel.
	 *
	 * @return the last publish date of this commerce discount account rel
	 */
	@Override
	public Date getLastPublishDate() {
		return _commerceDiscountAccountRel.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this commerce discount account rel.
	 *
	 * @return the modified date of this commerce discount account rel
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceDiscountAccountRel.getModifiedDate();
	}

	/**
	 * Returns the order of this commerce discount account rel.
	 *
	 * @return the order of this commerce discount account rel
	 */
	@Override
	public int getOrder() {
		return _commerceDiscountAccountRel.getOrder();
	}

	/**
	 * Returns the primary key of this commerce discount account rel.
	 *
	 * @return the primary key of this commerce discount account rel
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceDiscountAccountRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceDiscountAccountRel.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this commerce discount account rel.
	 *
	 * @return the user ID of this commerce discount account rel
	 */
	@Override
	public long getUserId() {
		return _commerceDiscountAccountRel.getUserId();
	}

	/**
	 * Returns the user name of this commerce discount account rel.
	 *
	 * @return the user name of this commerce discount account rel
	 */
	@Override
	public String getUserName() {
		return _commerceDiscountAccountRel.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce discount account rel.
	 *
	 * @return the user uuid of this commerce discount account rel
	 */
	@Override
	public String getUserUuid() {
		return _commerceDiscountAccountRel.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce discount account rel.
	 *
	 * @return the uuid of this commerce discount account rel
	 */
	@Override
	public String getUuid() {
		return _commerceDiscountAccountRel.getUuid();
	}

	@Override
	public int hashCode() {
		return _commerceDiscountAccountRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceDiscountAccountRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceDiscountAccountRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceDiscountAccountRel.isNew();
	}

	@Override
	public void persist() {
		_commerceDiscountAccountRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceDiscountAccountRel.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce account ID of this commerce discount account rel.
	 *
	 * @param commerceAccountId the commerce account ID of this commerce discount account rel
	 */
	@Override
	public void setCommerceAccountId(long commerceAccountId) {
		_commerceDiscountAccountRel.setCommerceAccountId(commerceAccountId);
	}

	/**
	 * Sets the commerce discount account rel ID of this commerce discount account rel.
	 *
	 * @param commerceDiscountAccountRelId the commerce discount account rel ID of this commerce discount account rel
	 */
	@Override
	public void setCommerceDiscountAccountRelId(
		long commerceDiscountAccountRelId) {

		_commerceDiscountAccountRel.setCommerceDiscountAccountRelId(
			commerceDiscountAccountRelId);
	}

	/**
	 * Sets the commerce discount ID of this commerce discount account rel.
	 *
	 * @param commerceDiscountId the commerce discount ID of this commerce discount account rel
	 */
	@Override
	public void setCommerceDiscountId(long commerceDiscountId) {
		_commerceDiscountAccountRel.setCommerceDiscountId(commerceDiscountId);
	}

	/**
	 * Sets the company ID of this commerce discount account rel.
	 *
	 * @param companyId the company ID of this commerce discount account rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceDiscountAccountRel.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce discount account rel.
	 *
	 * @param createDate the create date of this commerce discount account rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceDiscountAccountRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceDiscountAccountRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceDiscountAccountRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceDiscountAccountRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the last publish date of this commerce discount account rel.
	 *
	 * @param lastPublishDate the last publish date of this commerce discount account rel
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_commerceDiscountAccountRel.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this commerce discount account rel.
	 *
	 * @param modifiedDate the modified date of this commerce discount account rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceDiscountAccountRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceDiscountAccountRel.setNew(n);
	}

	/**
	 * Sets the order of this commerce discount account rel.
	 *
	 * @param order the order of this commerce discount account rel
	 */
	@Override
	public void setOrder(int order) {
		_commerceDiscountAccountRel.setOrder(order);
	}

	/**
	 * Sets the primary key of this commerce discount account rel.
	 *
	 * @param primaryKey the primary key of this commerce discount account rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceDiscountAccountRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceDiscountAccountRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this commerce discount account rel.
	 *
	 * @param userId the user ID of this commerce discount account rel
	 */
	@Override
	public void setUserId(long userId) {
		_commerceDiscountAccountRel.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce discount account rel.
	 *
	 * @param userName the user name of this commerce discount account rel
	 */
	@Override
	public void setUserName(String userName) {
		_commerceDiscountAccountRel.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce discount account rel.
	 *
	 * @param userUuid the user uuid of this commerce discount account rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceDiscountAccountRel.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce discount account rel.
	 *
	 * @param uuid the uuid of this commerce discount account rel
	 */
	@Override
	public void setUuid(String uuid) {
		_commerceDiscountAccountRel.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommerceDiscountAccountRel> toCacheModel() {

		return _commerceDiscountAccountRel.toCacheModel();
	}

	@Override
	public CommerceDiscountAccountRel toEscapedModel() {
		return new CommerceDiscountAccountRelWrapper(
			_commerceDiscountAccountRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceDiscountAccountRel.toString();
	}

	@Override
	public CommerceDiscountAccountRel toUnescapedModel() {
		return new CommerceDiscountAccountRelWrapper(
			_commerceDiscountAccountRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceDiscountAccountRel.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceDiscountAccountRelWrapper)) {
			return false;
		}

		CommerceDiscountAccountRelWrapper commerceDiscountAccountRelWrapper =
			(CommerceDiscountAccountRelWrapper)object;

		if (Objects.equals(
				_commerceDiscountAccountRel,
				commerceDiscountAccountRelWrapper.
					_commerceDiscountAccountRel)) {

			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commerceDiscountAccountRel.getStagedModelType();
	}

	@Override
	public CommerceDiscountAccountRel getWrappedModel() {
		return _commerceDiscountAccountRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceDiscountAccountRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceDiscountAccountRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceDiscountAccountRel.resetOriginalValues();
	}

	private final CommerceDiscountAccountRel _commerceDiscountAccountRel;

}