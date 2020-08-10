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
 * This class is a wrapper for {@link CommercePriceListDiscountRel}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListDiscountRel
 * @generated
 */
public class CommercePriceListDiscountRelWrapper
	implements CommercePriceListDiscountRel,
			   ModelWrapper<CommercePriceListDiscountRel> {

	public CommercePriceListDiscountRelWrapper(
		CommercePriceListDiscountRel commercePriceListDiscountRel) {

		_commercePriceListDiscountRel = commercePriceListDiscountRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommercePriceListDiscountRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommercePriceListDiscountRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put(
			"commercePriceListDiscountRelId",
			getCommercePriceListDiscountRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceDiscountId", getCommerceDiscountId());
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

		Long commercePriceListDiscountRelId = (Long)attributes.get(
			"commercePriceListDiscountRelId");

		if (commercePriceListDiscountRelId != null) {
			setCommercePriceListDiscountRelId(commercePriceListDiscountRelId);
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
		return new CommercePriceListDiscountRelWrapper(
			(CommercePriceListDiscountRel)
				_commercePriceListDiscountRel.clone());
	}

	@Override
	public int compareTo(
		CommercePriceListDiscountRel commercePriceListDiscountRel) {

		return _commercePriceListDiscountRel.compareTo(
			commercePriceListDiscountRel);
	}

	/**
	 * Returns the commerce discount ID of this commerce price list discount rel.
	 *
	 * @return the commerce discount ID of this commerce price list discount rel
	 */
	@Override
	public long getCommerceDiscountId() {
		return _commercePriceListDiscountRel.getCommerceDiscountId();
	}

	@Override
	public CommercePriceList getCommercePriceList()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListDiscountRel.getCommercePriceList();
	}

	/**
	 * Returns the commerce price list discount rel ID of this commerce price list discount rel.
	 *
	 * @return the commerce price list discount rel ID of this commerce price list discount rel
	 */
	@Override
	public long getCommercePriceListDiscountRelId() {
		return _commercePriceListDiscountRel.
			getCommercePriceListDiscountRelId();
	}

	/**
	 * Returns the commerce price list ID of this commerce price list discount rel.
	 *
	 * @return the commerce price list ID of this commerce price list discount rel
	 */
	@Override
	public long getCommercePriceListId() {
		return _commercePriceListDiscountRel.getCommercePriceListId();
	}

	/**
	 * Returns the company ID of this commerce price list discount rel.
	 *
	 * @return the company ID of this commerce price list discount rel
	 */
	@Override
	public long getCompanyId() {
		return _commercePriceListDiscountRel.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce price list discount rel.
	 *
	 * @return the create date of this commerce price list discount rel
	 */
	@Override
	public Date getCreateDate() {
		return _commercePriceListDiscountRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commercePriceListDiscountRel.getExpandoBridge();
	}

	/**
	 * Returns the last publish date of this commerce price list discount rel.
	 *
	 * @return the last publish date of this commerce price list discount rel
	 */
	@Override
	public Date getLastPublishDate() {
		return _commercePriceListDiscountRel.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this commerce price list discount rel.
	 *
	 * @return the modified date of this commerce price list discount rel
	 */
	@Override
	public Date getModifiedDate() {
		return _commercePriceListDiscountRel.getModifiedDate();
	}

	/**
	 * Returns the order of this commerce price list discount rel.
	 *
	 * @return the order of this commerce price list discount rel
	 */
	@Override
	public int getOrder() {
		return _commercePriceListDiscountRel.getOrder();
	}

	/**
	 * Returns the primary key of this commerce price list discount rel.
	 *
	 * @return the primary key of this commerce price list discount rel
	 */
	@Override
	public long getPrimaryKey() {
		return _commercePriceListDiscountRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commercePriceListDiscountRel.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this commerce price list discount rel.
	 *
	 * @return the user ID of this commerce price list discount rel
	 */
	@Override
	public long getUserId() {
		return _commercePriceListDiscountRel.getUserId();
	}

	/**
	 * Returns the user name of this commerce price list discount rel.
	 *
	 * @return the user name of this commerce price list discount rel
	 */
	@Override
	public String getUserName() {
		return _commercePriceListDiscountRel.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce price list discount rel.
	 *
	 * @return the user uuid of this commerce price list discount rel
	 */
	@Override
	public String getUserUuid() {
		return _commercePriceListDiscountRel.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce price list discount rel.
	 *
	 * @return the uuid of this commerce price list discount rel
	 */
	@Override
	public String getUuid() {
		return _commercePriceListDiscountRel.getUuid();
	}

	@Override
	public int hashCode() {
		return _commercePriceListDiscountRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commercePriceListDiscountRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commercePriceListDiscountRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commercePriceListDiscountRel.isNew();
	}

	@Override
	public void persist() {
		_commercePriceListDiscountRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commercePriceListDiscountRel.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce discount ID of this commerce price list discount rel.
	 *
	 * @param commerceDiscountId the commerce discount ID of this commerce price list discount rel
	 */
	@Override
	public void setCommerceDiscountId(long commerceDiscountId) {
		_commercePriceListDiscountRel.setCommerceDiscountId(commerceDiscountId);
	}

	/**
	 * Sets the commerce price list discount rel ID of this commerce price list discount rel.
	 *
	 * @param commercePriceListDiscountRelId the commerce price list discount rel ID of this commerce price list discount rel
	 */
	@Override
	public void setCommercePriceListDiscountRelId(
		long commercePriceListDiscountRelId) {

		_commercePriceListDiscountRel.setCommercePriceListDiscountRelId(
			commercePriceListDiscountRelId);
	}

	/**
	 * Sets the commerce price list ID of this commerce price list discount rel.
	 *
	 * @param commercePriceListId the commerce price list ID of this commerce price list discount rel
	 */
	@Override
	public void setCommercePriceListId(long commercePriceListId) {
		_commercePriceListDiscountRel.setCommercePriceListId(
			commercePriceListId);
	}

	/**
	 * Sets the company ID of this commerce price list discount rel.
	 *
	 * @param companyId the company ID of this commerce price list discount rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commercePriceListDiscountRel.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce price list discount rel.
	 *
	 * @param createDate the create date of this commerce price list discount rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commercePriceListDiscountRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commercePriceListDiscountRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commercePriceListDiscountRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commercePriceListDiscountRel.setExpandoBridgeAttributes(
			serviceContext);
	}

	/**
	 * Sets the last publish date of this commerce price list discount rel.
	 *
	 * @param lastPublishDate the last publish date of this commerce price list discount rel
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_commercePriceListDiscountRel.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this commerce price list discount rel.
	 *
	 * @param modifiedDate the modified date of this commerce price list discount rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commercePriceListDiscountRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commercePriceListDiscountRel.setNew(n);
	}

	/**
	 * Sets the order of this commerce price list discount rel.
	 *
	 * @param order the order of this commerce price list discount rel
	 */
	@Override
	public void setOrder(int order) {
		_commercePriceListDiscountRel.setOrder(order);
	}

	/**
	 * Sets the primary key of this commerce price list discount rel.
	 *
	 * @param primaryKey the primary key of this commerce price list discount rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commercePriceListDiscountRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commercePriceListDiscountRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this commerce price list discount rel.
	 *
	 * @param userId the user ID of this commerce price list discount rel
	 */
	@Override
	public void setUserId(long userId) {
		_commercePriceListDiscountRel.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce price list discount rel.
	 *
	 * @param userName the user name of this commerce price list discount rel
	 */
	@Override
	public void setUserName(String userName) {
		_commercePriceListDiscountRel.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce price list discount rel.
	 *
	 * @param userUuid the user uuid of this commerce price list discount rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commercePriceListDiscountRel.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce price list discount rel.
	 *
	 * @param uuid the uuid of this commerce price list discount rel
	 */
	@Override
	public void setUuid(String uuid) {
		_commercePriceListDiscountRel.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommercePriceListDiscountRel> toCacheModel() {

		return _commercePriceListDiscountRel.toCacheModel();
	}

	@Override
	public CommercePriceListDiscountRel toEscapedModel() {
		return new CommercePriceListDiscountRelWrapper(
			_commercePriceListDiscountRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commercePriceListDiscountRel.toString();
	}

	@Override
	public CommercePriceListDiscountRel toUnescapedModel() {
		return new CommercePriceListDiscountRelWrapper(
			_commercePriceListDiscountRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commercePriceListDiscountRel.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommercePriceListDiscountRelWrapper)) {
			return false;
		}

		CommercePriceListDiscountRelWrapper
			commercePriceListDiscountRelWrapper =
				(CommercePriceListDiscountRelWrapper)object;

		if (Objects.equals(
				_commercePriceListDiscountRel,
				commercePriceListDiscountRelWrapper.
					_commercePriceListDiscountRel)) {

			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commercePriceListDiscountRel.getStagedModelType();
	}

	@Override
	public CommercePriceListDiscountRel getWrappedModel() {
		return _commercePriceListDiscountRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commercePriceListDiscountRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commercePriceListDiscountRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commercePriceListDiscountRel.resetOriginalValues();
	}

	private final CommercePriceListDiscountRel _commercePriceListDiscountRel;

}