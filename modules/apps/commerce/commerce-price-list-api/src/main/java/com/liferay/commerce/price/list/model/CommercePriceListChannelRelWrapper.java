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
 * This class is a wrapper for {@link CommercePriceListChannelRel}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListChannelRel
 * @generated
 */
public class CommercePriceListChannelRelWrapper
	implements CommercePriceListChannelRel,
			   ModelWrapper<CommercePriceListChannelRel> {

	public CommercePriceListChannelRelWrapper(
		CommercePriceListChannelRel commercePriceListChannelRel) {

		_commercePriceListChannelRel = commercePriceListChannelRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommercePriceListChannelRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommercePriceListChannelRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put(
			"CommercePriceListChannelRelId",
			getCommercePriceListChannelRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceChannelId", getCommerceChannelId());
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

		Long CommercePriceListChannelRelId = (Long)attributes.get(
			"CommercePriceListChannelRelId");

		if (CommercePriceListChannelRelId != null) {
			setCommercePriceListChannelRelId(CommercePriceListChannelRelId);
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

		Long commerceChannelId = (Long)attributes.get("commerceChannelId");

		if (commerceChannelId != null) {
			setCommerceChannelId(commerceChannelId);
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
		return new CommercePriceListChannelRelWrapper(
			(CommercePriceListChannelRel)_commercePriceListChannelRel.clone());
	}

	@Override
	public int compareTo(
		CommercePriceListChannelRel commercePriceListChannelRel) {

		return _commercePriceListChannelRel.compareTo(
			commercePriceListChannelRel);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceChannel
			getCommerceChannel()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListChannelRel.getCommerceChannel();
	}

	/**
	 * Returns the commerce channel ID of this commerce price list channel rel.
	 *
	 * @return the commerce channel ID of this commerce price list channel rel
	 */
	@Override
	public long getCommerceChannelId() {
		return _commercePriceListChannelRel.getCommerceChannelId();
	}

	@Override
	public CommercePriceList getCommercePriceList()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListChannelRel.getCommercePriceList();
	}

	/**
	 * Returns the commerce price list channel rel ID of this commerce price list channel rel.
	 *
	 * @return the commerce price list channel rel ID of this commerce price list channel rel
	 */
	@Override
	public long getCommercePriceListChannelRelId() {
		return _commercePriceListChannelRel.getCommercePriceListChannelRelId();
	}

	/**
	 * Returns the commerce price list ID of this commerce price list channel rel.
	 *
	 * @return the commerce price list ID of this commerce price list channel rel
	 */
	@Override
	public long getCommercePriceListId() {
		return _commercePriceListChannelRel.getCommercePriceListId();
	}

	/**
	 * Returns the company ID of this commerce price list channel rel.
	 *
	 * @return the company ID of this commerce price list channel rel
	 */
	@Override
	public long getCompanyId() {
		return _commercePriceListChannelRel.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce price list channel rel.
	 *
	 * @return the create date of this commerce price list channel rel
	 */
	@Override
	public Date getCreateDate() {
		return _commercePriceListChannelRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commercePriceListChannelRel.getExpandoBridge();
	}

	/**
	 * Returns the last publish date of this commerce price list channel rel.
	 *
	 * @return the last publish date of this commerce price list channel rel
	 */
	@Override
	public Date getLastPublishDate() {
		return _commercePriceListChannelRel.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this commerce price list channel rel.
	 *
	 * @return the modified date of this commerce price list channel rel
	 */
	@Override
	public Date getModifiedDate() {
		return _commercePriceListChannelRel.getModifiedDate();
	}

	/**
	 * Returns the order of this commerce price list channel rel.
	 *
	 * @return the order of this commerce price list channel rel
	 */
	@Override
	public int getOrder() {
		return _commercePriceListChannelRel.getOrder();
	}

	/**
	 * Returns the primary key of this commerce price list channel rel.
	 *
	 * @return the primary key of this commerce price list channel rel
	 */
	@Override
	public long getPrimaryKey() {
		return _commercePriceListChannelRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commercePriceListChannelRel.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this commerce price list channel rel.
	 *
	 * @return the user ID of this commerce price list channel rel
	 */
	@Override
	public long getUserId() {
		return _commercePriceListChannelRel.getUserId();
	}

	/**
	 * Returns the user name of this commerce price list channel rel.
	 *
	 * @return the user name of this commerce price list channel rel
	 */
	@Override
	public String getUserName() {
		return _commercePriceListChannelRel.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce price list channel rel.
	 *
	 * @return the user uuid of this commerce price list channel rel
	 */
	@Override
	public String getUserUuid() {
		return _commercePriceListChannelRel.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce price list channel rel.
	 *
	 * @return the uuid of this commerce price list channel rel
	 */
	@Override
	public String getUuid() {
		return _commercePriceListChannelRel.getUuid();
	}

	@Override
	public int hashCode() {
		return _commercePriceListChannelRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commercePriceListChannelRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commercePriceListChannelRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commercePriceListChannelRel.isNew();
	}

	@Override
	public void persist() {
		_commercePriceListChannelRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commercePriceListChannelRel.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce channel ID of this commerce price list channel rel.
	 *
	 * @param commerceChannelId the commerce channel ID of this commerce price list channel rel
	 */
	@Override
	public void setCommerceChannelId(long commerceChannelId) {
		_commercePriceListChannelRel.setCommerceChannelId(commerceChannelId);
	}

	/**
	 * Sets the commerce price list channel rel ID of this commerce price list channel rel.
	 *
	 * @param CommercePriceListChannelRelId the commerce price list channel rel ID of this commerce price list channel rel
	 */
	@Override
	public void setCommercePriceListChannelRelId(
		long CommercePriceListChannelRelId) {

		_commercePriceListChannelRel.setCommercePriceListChannelRelId(
			CommercePriceListChannelRelId);
	}

	/**
	 * Sets the commerce price list ID of this commerce price list channel rel.
	 *
	 * @param commercePriceListId the commerce price list ID of this commerce price list channel rel
	 */
	@Override
	public void setCommercePriceListId(long commercePriceListId) {
		_commercePriceListChannelRel.setCommercePriceListId(
			commercePriceListId);
	}

	/**
	 * Sets the company ID of this commerce price list channel rel.
	 *
	 * @param companyId the company ID of this commerce price list channel rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commercePriceListChannelRel.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce price list channel rel.
	 *
	 * @param createDate the create date of this commerce price list channel rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commercePriceListChannelRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commercePriceListChannelRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commercePriceListChannelRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commercePriceListChannelRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the last publish date of this commerce price list channel rel.
	 *
	 * @param lastPublishDate the last publish date of this commerce price list channel rel
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_commercePriceListChannelRel.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this commerce price list channel rel.
	 *
	 * @param modifiedDate the modified date of this commerce price list channel rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commercePriceListChannelRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commercePriceListChannelRel.setNew(n);
	}

	/**
	 * Sets the order of this commerce price list channel rel.
	 *
	 * @param order the order of this commerce price list channel rel
	 */
	@Override
	public void setOrder(int order) {
		_commercePriceListChannelRel.setOrder(order);
	}

	/**
	 * Sets the primary key of this commerce price list channel rel.
	 *
	 * @param primaryKey the primary key of this commerce price list channel rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commercePriceListChannelRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commercePriceListChannelRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this commerce price list channel rel.
	 *
	 * @param userId the user ID of this commerce price list channel rel
	 */
	@Override
	public void setUserId(long userId) {
		_commercePriceListChannelRel.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce price list channel rel.
	 *
	 * @param userName the user name of this commerce price list channel rel
	 */
	@Override
	public void setUserName(String userName) {
		_commercePriceListChannelRel.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce price list channel rel.
	 *
	 * @param userUuid the user uuid of this commerce price list channel rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commercePriceListChannelRel.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce price list channel rel.
	 *
	 * @param uuid the uuid of this commerce price list channel rel
	 */
	@Override
	public void setUuid(String uuid) {
		_commercePriceListChannelRel.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommercePriceListChannelRel> toCacheModel() {

		return _commercePriceListChannelRel.toCacheModel();
	}

	@Override
	public CommercePriceListChannelRel toEscapedModel() {
		return new CommercePriceListChannelRelWrapper(
			_commercePriceListChannelRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commercePriceListChannelRel.toString();
	}

	@Override
	public CommercePriceListChannelRel toUnescapedModel() {
		return new CommercePriceListChannelRelWrapper(
			_commercePriceListChannelRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commercePriceListChannelRel.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommercePriceListChannelRelWrapper)) {
			return false;
		}

		CommercePriceListChannelRelWrapper commercePriceListChannelRelWrapper =
			(CommercePriceListChannelRelWrapper)object;

		if (Objects.equals(
				_commercePriceListChannelRel,
				commercePriceListChannelRelWrapper.
					_commercePriceListChannelRel)) {

			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commercePriceListChannelRel.getStagedModelType();
	}

	@Override
	public CommercePriceListChannelRel getWrappedModel() {
		return _commercePriceListChannelRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commercePriceListChannelRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commercePriceListChannelRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commercePriceListChannelRel.resetOriginalValues();
	}

	private final CommercePriceListChannelRel _commercePriceListChannelRel;

}