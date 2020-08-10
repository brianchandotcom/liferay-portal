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

package com.liferay.commerce.price.list.model;

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
 * This class is a wrapper for {@link CommercePriceListAccountRel}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListAccountRel
 * @generated
 */
public class CommercePriceListAccountRelWrapper
	implements CommercePriceListAccountRel,
			   ModelWrapper<CommercePriceListAccountRel> {

	public CommercePriceListAccountRelWrapper(
		CommercePriceListAccountRel commercePriceListAccountRel) {

		_commercePriceListAccountRel = commercePriceListAccountRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommercePriceListAccountRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommercePriceListAccountRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put(
			"commercePriceListAccountRelId",
			getCommercePriceListAccountRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceAccountId", getCommerceAccountId());
		attributes.put("commercePriceListId", getCommercePriceListId());
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

		Long commercePriceListAccountRelId = (Long)attributes.get(
			"commercePriceListAccountRelId");

		if (commercePriceListAccountRelId != null) {
			setCommercePriceListAccountRelId(commercePriceListAccountRelId);
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

		Long commercePriceListId = (Long)attributes.get("commercePriceListId");

		if (commercePriceListId != null) {
			setCommercePriceListId(commercePriceListId);
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
		return new CommercePriceListAccountRelWrapper(
			(CommercePriceListAccountRel)_commercePriceListAccountRel.clone());
	}

	@Override
	public int compareTo(
		CommercePriceListAccountRel commercePriceListAccountRel) {

		return _commercePriceListAccountRel.compareTo(
			commercePriceListAccountRel);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			getCommerceAccount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListAccountRel.getCommerceAccount();
	}

	/**
	 * Returns the commerce account ID of this commerce price list account rel.
	 *
	 * @return the commerce account ID of this commerce price list account rel
	 */
	@Override
	public long getCommerceAccountId() {
		return _commercePriceListAccountRel.getCommerceAccountId();
	}

	@Override
	public CommercePriceList getCommercePriceList()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListAccountRel.getCommercePriceList();
	}

	/**
	 * Returns the commerce price list account rel ID of this commerce price list account rel.
	 *
	 * @return the commerce price list account rel ID of this commerce price list account rel
	 */
	@Override
	public long getCommercePriceListAccountRelId() {
		return _commercePriceListAccountRel.getCommercePriceListAccountRelId();
	}

	/**
	 * Returns the commerce price list ID of this commerce price list account rel.
	 *
	 * @return the commerce price list ID of this commerce price list account rel
	 */
	@Override
	public long getCommercePriceListId() {
		return _commercePriceListAccountRel.getCommercePriceListId();
	}

	/**
	 * Returns the company ID of this commerce price list account rel.
	 *
	 * @return the company ID of this commerce price list account rel
	 */
	@Override
	public long getCompanyId() {
		return _commercePriceListAccountRel.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce price list account rel.
	 *
	 * @return the create date of this commerce price list account rel
	 */
	@Override
	public Date getCreateDate() {
		return _commercePriceListAccountRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commercePriceListAccountRel.getExpandoBridge();
	}

	/**
	 * Returns the last publish date of this commerce price list account rel.
	 *
	 * @return the last publish date of this commerce price list account rel
	 */
	@Override
	public Date getLastPublishDate() {
		return _commercePriceListAccountRel.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this commerce price list account rel.
	 *
	 * @return the modified date of this commerce price list account rel
	 */
	@Override
	public Date getModifiedDate() {
		return _commercePriceListAccountRel.getModifiedDate();
	}

	/**
	 * Returns the order of this commerce price list account rel.
	 *
	 * @return the order of this commerce price list account rel
	 */
	@Override
	public int getOrder() {
		return _commercePriceListAccountRel.getOrder();
	}

	/**
	 * Returns the primary key of this commerce price list account rel.
	 *
	 * @return the primary key of this commerce price list account rel
	 */
	@Override
	public long getPrimaryKey() {
		return _commercePriceListAccountRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commercePriceListAccountRel.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this commerce price list account rel.
	 *
	 * @return the user ID of this commerce price list account rel
	 */
	@Override
	public long getUserId() {
		return _commercePriceListAccountRel.getUserId();
	}

	/**
	 * Returns the user name of this commerce price list account rel.
	 *
	 * @return the user name of this commerce price list account rel
	 */
	@Override
	public String getUserName() {
		return _commercePriceListAccountRel.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce price list account rel.
	 *
	 * @return the user uuid of this commerce price list account rel
	 */
	@Override
	public String getUserUuid() {
		return _commercePriceListAccountRel.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce price list account rel.
	 *
	 * @return the uuid of this commerce price list account rel
	 */
	@Override
	public String getUuid() {
		return _commercePriceListAccountRel.getUuid();
	}

	@Override
	public int hashCode() {
		return _commercePriceListAccountRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commercePriceListAccountRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commercePriceListAccountRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commercePriceListAccountRel.isNew();
	}

	@Override
	public void persist() {
		_commercePriceListAccountRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commercePriceListAccountRel.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce account ID of this commerce price list account rel.
	 *
	 * @param commerceAccountId the commerce account ID of this commerce price list account rel
	 */
	@Override
	public void setCommerceAccountId(long commerceAccountId) {
		_commercePriceListAccountRel.setCommerceAccountId(commerceAccountId);
	}

	/**
	 * Sets the commerce price list account rel ID of this commerce price list account rel.
	 *
	 * @param commercePriceListAccountRelId the commerce price list account rel ID of this commerce price list account rel
	 */
	@Override
	public void setCommercePriceListAccountRelId(
		long commercePriceListAccountRelId) {

		_commercePriceListAccountRel.setCommercePriceListAccountRelId(
			commercePriceListAccountRelId);
	}

	/**
	 * Sets the commerce price list ID of this commerce price list account rel.
	 *
	 * @param commercePriceListId the commerce price list ID of this commerce price list account rel
	 */
	@Override
	public void setCommercePriceListId(long commercePriceListId) {
		_commercePriceListAccountRel.setCommercePriceListId(
			commercePriceListId);
	}

	/**
	 * Sets the company ID of this commerce price list account rel.
	 *
	 * @param companyId the company ID of this commerce price list account rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commercePriceListAccountRel.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce price list account rel.
	 *
	 * @param createDate the create date of this commerce price list account rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commercePriceListAccountRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commercePriceListAccountRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commercePriceListAccountRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commercePriceListAccountRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the last publish date of this commerce price list account rel.
	 *
	 * @param lastPublishDate the last publish date of this commerce price list account rel
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_commercePriceListAccountRel.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this commerce price list account rel.
	 *
	 * @param modifiedDate the modified date of this commerce price list account rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commercePriceListAccountRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commercePriceListAccountRel.setNew(n);
	}

	/**
	 * Sets the order of this commerce price list account rel.
	 *
	 * @param order the order of this commerce price list account rel
	 */
	@Override
	public void setOrder(int order) {
		_commercePriceListAccountRel.setOrder(order);
	}

	/**
	 * Sets the primary key of this commerce price list account rel.
	 *
	 * @param primaryKey the primary key of this commerce price list account rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commercePriceListAccountRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commercePriceListAccountRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this commerce price list account rel.
	 *
	 * @param userId the user ID of this commerce price list account rel
	 */
	@Override
	public void setUserId(long userId) {
		_commercePriceListAccountRel.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce price list account rel.
	 *
	 * @param userName the user name of this commerce price list account rel
	 */
	@Override
	public void setUserName(String userName) {
		_commercePriceListAccountRel.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce price list account rel.
	 *
	 * @param userUuid the user uuid of this commerce price list account rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commercePriceListAccountRel.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce price list account rel.
	 *
	 * @param uuid the uuid of this commerce price list account rel
	 */
	@Override
	public void setUuid(String uuid) {
		_commercePriceListAccountRel.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommercePriceListAccountRel> toCacheModel() {

		return _commercePriceListAccountRel.toCacheModel();
	}

	@Override
	public CommercePriceListAccountRel toEscapedModel() {
		return new CommercePriceListAccountRelWrapper(
			_commercePriceListAccountRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commercePriceListAccountRel.toString();
	}

	@Override
	public CommercePriceListAccountRel toUnescapedModel() {
		return new CommercePriceListAccountRelWrapper(
			_commercePriceListAccountRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commercePriceListAccountRel.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommercePriceListAccountRelWrapper)) {
			return false;
		}

		CommercePriceListAccountRelWrapper commercePriceListAccountRelWrapper =
			(CommercePriceListAccountRelWrapper)object;

		if (Objects.equals(
				_commercePriceListAccountRel,
				commercePriceListAccountRelWrapper.
					_commercePriceListAccountRel)) {

			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commercePriceListAccountRel.getStagedModelType();
	}

	@Override
	public CommercePriceListAccountRel getWrappedModel() {
		return _commercePriceListAccountRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commercePriceListAccountRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commercePriceListAccountRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commercePriceListAccountRel.resetOriginalValues();
	}

	private final CommercePriceListAccountRel _commercePriceListAccountRel;

}