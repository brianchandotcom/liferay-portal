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
 * This class is a wrapper for {@link CommercePriceListCommerceAccountGroupRel}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListCommerceAccountGroupRel
 * @generated
 */
public class CommercePriceListCommerceAccountGroupRelWrapper
	implements CommercePriceListCommerceAccountGroupRel,
			   ModelWrapper<CommercePriceListCommerceAccountGroupRel> {

	public CommercePriceListCommerceAccountGroupRelWrapper(
		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel) {

		_commercePriceListCommerceAccountGroupRel =
			commercePriceListCommerceAccountGroupRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommercePriceListCommerceAccountGroupRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommercePriceListCommerceAccountGroupRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put(
			"commercePriceListCommerceAccountGroupRelId",
			getCommercePriceListCommerceAccountGroupRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commercePriceListId", getCommercePriceListId());
		attributes.put("commerceAccountGroupId", getCommerceAccountGroupId());
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

		Long commercePriceListCommerceAccountGroupRelId = (Long)attributes.get(
			"commercePriceListCommerceAccountGroupRelId");

		if (commercePriceListCommerceAccountGroupRelId != null) {
			setCommercePriceListCommerceAccountGroupRelId(
				commercePriceListCommerceAccountGroupRelId);
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

		Long commercePriceListId = (Long)attributes.get("commercePriceListId");

		if (commercePriceListId != null) {
			setCommercePriceListId(commercePriceListId);
		}

		Long commerceAccountGroupId = (Long)attributes.get(
			"commerceAccountGroupId");

		if (commerceAccountGroupId != null) {
			setCommerceAccountGroupId(commerceAccountGroupId);
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
		return new CommercePriceListCommerceAccountGroupRelWrapper(
			(CommercePriceListCommerceAccountGroupRel)
				_commercePriceListCommerceAccountGroupRel.clone());
	}

	@Override
	public int compareTo(
		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel) {

		return _commercePriceListCommerceAccountGroupRel.compareTo(
			commercePriceListCommerceAccountGroupRel);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
			getCommerceAccountGroup()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListCommerceAccountGroupRel.
			getCommerceAccountGroup();
	}

	/**
	 * Returns the commerce account group ID of this commerce price list commerce account group rel.
	 *
	 * @return the commerce account group ID of this commerce price list commerce account group rel
	 */
	@Override
	public long getCommerceAccountGroupId() {
		return _commercePriceListCommerceAccountGroupRel.
			getCommerceAccountGroupId();
	}

	@Override
	public CommercePriceList getCommercePriceList()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListCommerceAccountGroupRel.getCommercePriceList();
	}

	/**
	 * Returns the commerce price list commerce account group rel ID of this commerce price list commerce account group rel.
	 *
	 * @return the commerce price list commerce account group rel ID of this commerce price list commerce account group rel
	 */
	@Override
	public long getCommercePriceListCommerceAccountGroupRelId() {
		return _commercePriceListCommerceAccountGroupRel.
			getCommercePriceListCommerceAccountGroupRelId();
	}

	/**
	 * Returns the commerce price list ID of this commerce price list commerce account group rel.
	 *
	 * @return the commerce price list ID of this commerce price list commerce account group rel
	 */
	@Override
	public long getCommercePriceListId() {
		return _commercePriceListCommerceAccountGroupRel.
			getCommercePriceListId();
	}

	/**
	 * Returns the company ID of this commerce price list commerce account group rel.
	 *
	 * @return the company ID of this commerce price list commerce account group rel
	 */
	@Override
	public long getCompanyId() {
		return _commercePriceListCommerceAccountGroupRel.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce price list commerce account group rel.
	 *
	 * @return the create date of this commerce price list commerce account group rel
	 */
	@Override
	public Date getCreateDate() {
		return _commercePriceListCommerceAccountGroupRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commercePriceListCommerceAccountGroupRel.getExpandoBridge();
	}

	/**
	 * Returns the last publish date of this commerce price list commerce account group rel.
	 *
	 * @return the last publish date of this commerce price list commerce account group rel
	 */
	@Override
	public Date getLastPublishDate() {
		return _commercePriceListCommerceAccountGroupRel.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this commerce price list commerce account group rel.
	 *
	 * @return the modified date of this commerce price list commerce account group rel
	 */
	@Override
	public Date getModifiedDate() {
		return _commercePriceListCommerceAccountGroupRel.getModifiedDate();
	}

	/**
	 * Returns the order of this commerce price list commerce account group rel.
	 *
	 * @return the order of this commerce price list commerce account group rel
	 */
	@Override
	public int getOrder() {
		return _commercePriceListCommerceAccountGroupRel.getOrder();
	}

	/**
	 * Returns the primary key of this commerce price list commerce account group rel.
	 *
	 * @return the primary key of this commerce price list commerce account group rel
	 */
	@Override
	public long getPrimaryKey() {
		return _commercePriceListCommerceAccountGroupRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commercePriceListCommerceAccountGroupRel.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this commerce price list commerce account group rel.
	 *
	 * @return the user ID of this commerce price list commerce account group rel
	 */
	@Override
	public long getUserId() {
		return _commercePriceListCommerceAccountGroupRel.getUserId();
	}

	/**
	 * Returns the user name of this commerce price list commerce account group rel.
	 *
	 * @return the user name of this commerce price list commerce account group rel
	 */
	@Override
	public String getUserName() {
		return _commercePriceListCommerceAccountGroupRel.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce price list commerce account group rel.
	 *
	 * @return the user uuid of this commerce price list commerce account group rel
	 */
	@Override
	public String getUserUuid() {
		return _commercePriceListCommerceAccountGroupRel.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce price list commerce account group rel.
	 *
	 * @return the uuid of this commerce price list commerce account group rel
	 */
	@Override
	public String getUuid() {
		return _commercePriceListCommerceAccountGroupRel.getUuid();
	}

	@Override
	public int hashCode() {
		return _commercePriceListCommerceAccountGroupRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commercePriceListCommerceAccountGroupRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commercePriceListCommerceAccountGroupRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commercePriceListCommerceAccountGroupRel.isNew();
	}

	@Override
	public void persist() {
		_commercePriceListCommerceAccountGroupRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commercePriceListCommerceAccountGroupRel.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce account group ID of this commerce price list commerce account group rel.
	 *
	 * @param commerceAccountGroupId the commerce account group ID of this commerce price list commerce account group rel
	 */
	@Override
	public void setCommerceAccountGroupId(long commerceAccountGroupId) {
		_commercePriceListCommerceAccountGroupRel.setCommerceAccountGroupId(
			commerceAccountGroupId);
	}

	/**
	 * Sets the commerce price list commerce account group rel ID of this commerce price list commerce account group rel.
	 *
	 * @param commercePriceListCommerceAccountGroupRelId the commerce price list commerce account group rel ID of this commerce price list commerce account group rel
	 */
	@Override
	public void setCommercePriceListCommerceAccountGroupRelId(
		long commercePriceListCommerceAccountGroupRelId) {

		_commercePriceListCommerceAccountGroupRel.
			setCommercePriceListCommerceAccountGroupRelId(
				commercePriceListCommerceAccountGroupRelId);
	}

	/**
	 * Sets the commerce price list ID of this commerce price list commerce account group rel.
	 *
	 * @param commercePriceListId the commerce price list ID of this commerce price list commerce account group rel
	 */
	@Override
	public void setCommercePriceListId(long commercePriceListId) {
		_commercePriceListCommerceAccountGroupRel.setCommercePriceListId(
			commercePriceListId);
	}

	/**
	 * Sets the company ID of this commerce price list commerce account group rel.
	 *
	 * @param companyId the company ID of this commerce price list commerce account group rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commercePriceListCommerceAccountGroupRel.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce price list commerce account group rel.
	 *
	 * @param createDate the create date of this commerce price list commerce account group rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commercePriceListCommerceAccountGroupRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commercePriceListCommerceAccountGroupRel.setExpandoBridgeAttributes(
			baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commercePriceListCommerceAccountGroupRel.setExpandoBridgeAttributes(
			expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commercePriceListCommerceAccountGroupRel.setExpandoBridgeAttributes(
			serviceContext);
	}

	/**
	 * Sets the last publish date of this commerce price list commerce account group rel.
	 *
	 * @param lastPublishDate the last publish date of this commerce price list commerce account group rel
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_commercePriceListCommerceAccountGroupRel.setLastPublishDate(
			lastPublishDate);
	}

	/**
	 * Sets the modified date of this commerce price list commerce account group rel.
	 *
	 * @param modifiedDate the modified date of this commerce price list commerce account group rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commercePriceListCommerceAccountGroupRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commercePriceListCommerceAccountGroupRel.setNew(n);
	}

	/**
	 * Sets the order of this commerce price list commerce account group rel.
	 *
	 * @param order the order of this commerce price list commerce account group rel
	 */
	@Override
	public void setOrder(int order) {
		_commercePriceListCommerceAccountGroupRel.setOrder(order);
	}

	/**
	 * Sets the primary key of this commerce price list commerce account group rel.
	 *
	 * @param primaryKey the primary key of this commerce price list commerce account group rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commercePriceListCommerceAccountGroupRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commercePriceListCommerceAccountGroupRel.setPrimaryKeyObj(
			primaryKeyObj);
	}

	/**
	 * Sets the user ID of this commerce price list commerce account group rel.
	 *
	 * @param userId the user ID of this commerce price list commerce account group rel
	 */
	@Override
	public void setUserId(long userId) {
		_commercePriceListCommerceAccountGroupRel.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce price list commerce account group rel.
	 *
	 * @param userName the user name of this commerce price list commerce account group rel
	 */
	@Override
	public void setUserName(String userName) {
		_commercePriceListCommerceAccountGroupRel.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce price list commerce account group rel.
	 *
	 * @param userUuid the user uuid of this commerce price list commerce account group rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commercePriceListCommerceAccountGroupRel.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce price list commerce account group rel.
	 *
	 * @param uuid the uuid of this commerce price list commerce account group rel
	 */
	@Override
	public void setUuid(String uuid) {
		_commercePriceListCommerceAccountGroupRel.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommercePriceListCommerceAccountGroupRel> toCacheModel() {

		return _commercePriceListCommerceAccountGroupRel.toCacheModel();
	}

	@Override
	public CommercePriceListCommerceAccountGroupRel toEscapedModel() {
		return new CommercePriceListCommerceAccountGroupRelWrapper(
			_commercePriceListCommerceAccountGroupRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commercePriceListCommerceAccountGroupRel.toString();
	}

	@Override
	public CommercePriceListCommerceAccountGroupRel toUnescapedModel() {
		return new CommercePriceListCommerceAccountGroupRelWrapper(
			_commercePriceListCommerceAccountGroupRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commercePriceListCommerceAccountGroupRel.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof
				CommercePriceListCommerceAccountGroupRelWrapper)) {

			return false;
		}

		CommercePriceListCommerceAccountGroupRelWrapper
			commercePriceListCommerceAccountGroupRelWrapper =
				(CommercePriceListCommerceAccountGroupRelWrapper)object;

		if (Objects.equals(
				_commercePriceListCommerceAccountGroupRel,
				commercePriceListCommerceAccountGroupRelWrapper.
					_commercePriceListCommerceAccountGroupRel)) {

			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commercePriceListCommerceAccountGroupRel.getStagedModelType();
	}

	@Override
	public CommercePriceListCommerceAccountGroupRel getWrappedModel() {
		return _commercePriceListCommerceAccountGroupRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commercePriceListCommerceAccountGroupRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commercePriceListCommerceAccountGroupRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commercePriceListCommerceAccountGroupRel.resetOriginalValues();
	}

	private final CommercePriceListCommerceAccountGroupRel
		_commercePriceListCommerceAccountGroupRel;

}