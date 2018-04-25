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

import aQute.bnd.annotation.ProviderType;

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
 * This class is a wrapper for {@link CommercePriceListUserSegmentEntryRel}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListUserSegmentEntryRel
 * @generated
 */
@ProviderType
public class CommercePriceListUserSegmentEntryRelWrapper
	implements CommercePriceListUserSegmentEntryRel,
		ModelWrapper<CommercePriceListUserSegmentEntryRel> {
	public CommercePriceListUserSegmentEntryRelWrapper(
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel) {
		_commercePriceListUserSegmentEntryRel = commercePriceListUserSegmentEntryRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommercePriceListUserSegmentEntryRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommercePriceListUserSegmentEntryRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("commercePriceListUserSegmentEntryRelId",
			getCommercePriceListUserSegmentEntryRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commercePriceListId", getCommercePriceListId());
		attributes.put("commerceUserSegmentEntryId",
			getCommerceUserSegmentEntryId());
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

		Long commercePriceListUserSegmentEntryRelId = (Long)attributes.get(
				"commercePriceListUserSegmentEntryRelId");

		if (commercePriceListUserSegmentEntryRelId != null) {
			setCommercePriceListUserSegmentEntryRelId(commercePriceListUserSegmentEntryRelId);
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

		Long commercePriceListId = (Long)attributes.get("commercePriceListId");

		if (commercePriceListId != null) {
			setCommercePriceListId(commercePriceListId);
		}

		Long commerceUserSegmentEntryId = (Long)attributes.get(
				"commerceUserSegmentEntryId");

		if (commerceUserSegmentEntryId != null) {
			setCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
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
	public java.lang.Object clone() {
		return new CommercePriceListUserSegmentEntryRelWrapper((CommercePriceListUserSegmentEntryRel)_commercePriceListUserSegmentEntryRel.clone());
	}

	@Override
	public int compareTo(
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel) {
		return _commercePriceListUserSegmentEntryRel.compareTo(commercePriceListUserSegmentEntryRel);
	}

	/**
	* Returns the commerce price list ID of this commerce price list user segment entry rel.
	*
	* @return the commerce price list ID of this commerce price list user segment entry rel
	*/
	@Override
	public long getCommercePriceListId() {
		return _commercePriceListUserSegmentEntryRel.getCommercePriceListId();
	}

	/**
	* Returns the commerce price list user segment entry rel ID of this commerce price list user segment entry rel.
	*
	* @return the commerce price list user segment entry rel ID of this commerce price list user segment entry rel
	*/
	@Override
	public long getCommercePriceListUserSegmentEntryRelId() {
		return _commercePriceListUserSegmentEntryRel.getCommercePriceListUserSegmentEntryRelId();
	}

	/**
	* Returns the commerce user segment entry ID of this commerce price list user segment entry rel.
	*
	* @return the commerce user segment entry ID of this commerce price list user segment entry rel
	*/
	@Override
	public long getCommerceUserSegmentEntryId() {
		return _commercePriceListUserSegmentEntryRel.getCommerceUserSegmentEntryId();
	}

	/**
	* Returns the company ID of this commerce price list user segment entry rel.
	*
	* @return the company ID of this commerce price list user segment entry rel
	*/
	@Override
	public long getCompanyId() {
		return _commercePriceListUserSegmentEntryRel.getCompanyId();
	}

	/**
	* Returns the create date of this commerce price list user segment entry rel.
	*
	* @return the create date of this commerce price list user segment entry rel
	*/
	@Override
	public Date getCreateDate() {
		return _commercePriceListUserSegmentEntryRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commercePriceListUserSegmentEntryRel.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce price list user segment entry rel.
	*
	* @return the group ID of this commerce price list user segment entry rel
	*/
	@Override
	public long getGroupId() {
		return _commercePriceListUserSegmentEntryRel.getGroupId();
	}

	/**
	* Returns the last publish date of this commerce price list user segment entry rel.
	*
	* @return the last publish date of this commerce price list user segment entry rel
	*/
	@Override
	public Date getLastPublishDate() {
		return _commercePriceListUserSegmentEntryRel.getLastPublishDate();
	}

	/**
	* Returns the modified date of this commerce price list user segment entry rel.
	*
	* @return the modified date of this commerce price list user segment entry rel
	*/
	@Override
	public Date getModifiedDate() {
		return _commercePriceListUserSegmentEntryRel.getModifiedDate();
	}

	/**
	* Returns the order of this commerce price list user segment entry rel.
	*
	* @return the order of this commerce price list user segment entry rel
	*/
	@Override
	public int getOrder() {
		return _commercePriceListUserSegmentEntryRel.getOrder();
	}

	/**
	* Returns the primary key of this commerce price list user segment entry rel.
	*
	* @return the primary key of this commerce price list user segment entry rel
	*/
	@Override
	public long getPrimaryKey() {
		return _commercePriceListUserSegmentEntryRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commercePriceListUserSegmentEntryRel.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this commerce price list user segment entry rel.
	*
	* @return the user ID of this commerce price list user segment entry rel
	*/
	@Override
	public long getUserId() {
		return _commercePriceListUserSegmentEntryRel.getUserId();
	}

	/**
	* Returns the user name of this commerce price list user segment entry rel.
	*
	* @return the user name of this commerce price list user segment entry rel
	*/
	@Override
	public java.lang.String getUserName() {
		return _commercePriceListUserSegmentEntryRel.getUserName();
	}

	/**
	* Returns the user uuid of this commerce price list user segment entry rel.
	*
	* @return the user uuid of this commerce price list user segment entry rel
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commercePriceListUserSegmentEntryRel.getUserUuid();
	}

	/**
	* Returns the uuid of this commerce price list user segment entry rel.
	*
	* @return the uuid of this commerce price list user segment entry rel
	*/
	@Override
	public java.lang.String getUuid() {
		return _commercePriceListUserSegmentEntryRel.getUuid();
	}

	@Override
	public int hashCode() {
		return _commercePriceListUserSegmentEntryRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commercePriceListUserSegmentEntryRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commercePriceListUserSegmentEntryRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commercePriceListUserSegmentEntryRel.isNew();
	}

	@Override
	public void persist() {
		_commercePriceListUserSegmentEntryRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commercePriceListUserSegmentEntryRel.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce price list ID of this commerce price list user segment entry rel.
	*
	* @param commercePriceListId the commerce price list ID of this commerce price list user segment entry rel
	*/
	@Override
	public void setCommercePriceListId(long commercePriceListId) {
		_commercePriceListUserSegmentEntryRel.setCommercePriceListId(commercePriceListId);
	}

	/**
	* Sets the commerce price list user segment entry rel ID of this commerce price list user segment entry rel.
	*
	* @param commercePriceListUserSegmentEntryRelId the commerce price list user segment entry rel ID of this commerce price list user segment entry rel
	*/
	@Override
	public void setCommercePriceListUserSegmentEntryRelId(
		long commercePriceListUserSegmentEntryRelId) {
		_commercePriceListUserSegmentEntryRel.setCommercePriceListUserSegmentEntryRelId(commercePriceListUserSegmentEntryRelId);
	}

	/**
	* Sets the commerce user segment entry ID of this commerce price list user segment entry rel.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID of this commerce price list user segment entry rel
	*/
	@Override
	public void setCommerceUserSegmentEntryId(long commerceUserSegmentEntryId) {
		_commercePriceListUserSegmentEntryRel.setCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	/**
	* Sets the company ID of this commerce price list user segment entry rel.
	*
	* @param companyId the company ID of this commerce price list user segment entry rel
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commercePriceListUserSegmentEntryRel.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce price list user segment entry rel.
	*
	* @param createDate the create date of this commerce price list user segment entry rel
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commercePriceListUserSegmentEntryRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commercePriceListUserSegmentEntryRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commercePriceListUserSegmentEntryRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commercePriceListUserSegmentEntryRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce price list user segment entry rel.
	*
	* @param groupId the group ID of this commerce price list user segment entry rel
	*/
	@Override
	public void setGroupId(long groupId) {
		_commercePriceListUserSegmentEntryRel.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this commerce price list user segment entry rel.
	*
	* @param lastPublishDate the last publish date of this commerce price list user segment entry rel
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_commercePriceListUserSegmentEntryRel.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this commerce price list user segment entry rel.
	*
	* @param modifiedDate the modified date of this commerce price list user segment entry rel
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commercePriceListUserSegmentEntryRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commercePriceListUserSegmentEntryRel.setNew(n);
	}

	/**
	* Sets the order of this commerce price list user segment entry rel.
	*
	* @param order the order of this commerce price list user segment entry rel
	*/
	@Override
	public void setOrder(int order) {
		_commercePriceListUserSegmentEntryRel.setOrder(order);
	}

	/**
	* Sets the primary key of this commerce price list user segment entry rel.
	*
	* @param primaryKey the primary key of this commerce price list user segment entry rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commercePriceListUserSegmentEntryRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commercePriceListUserSegmentEntryRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this commerce price list user segment entry rel.
	*
	* @param userId the user ID of this commerce price list user segment entry rel
	*/
	@Override
	public void setUserId(long userId) {
		_commercePriceListUserSegmentEntryRel.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce price list user segment entry rel.
	*
	* @param userName the user name of this commerce price list user segment entry rel
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commercePriceListUserSegmentEntryRel.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce price list user segment entry rel.
	*
	* @param userUuid the user uuid of this commerce price list user segment entry rel
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commercePriceListUserSegmentEntryRel.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this commerce price list user segment entry rel.
	*
	* @param uuid the uuid of this commerce price list user segment entry rel
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_commercePriceListUserSegmentEntryRel.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommercePriceListUserSegmentEntryRel> toCacheModel() {
		return _commercePriceListUserSegmentEntryRel.toCacheModel();
	}

	@Override
	public CommercePriceListUserSegmentEntryRel toEscapedModel() {
		return new CommercePriceListUserSegmentEntryRelWrapper(_commercePriceListUserSegmentEntryRel.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commercePriceListUserSegmentEntryRel.toString();
	}

	@Override
	public CommercePriceListUserSegmentEntryRel toUnescapedModel() {
		return new CommercePriceListUserSegmentEntryRelWrapper(_commercePriceListUserSegmentEntryRel.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commercePriceListUserSegmentEntryRel.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommercePriceListUserSegmentEntryRelWrapper)) {
			return false;
		}

		CommercePriceListUserSegmentEntryRelWrapper commercePriceListUserSegmentEntryRelWrapper =
			(CommercePriceListUserSegmentEntryRelWrapper)obj;

		if (Objects.equals(_commercePriceListUserSegmentEntryRel,
					commercePriceListUserSegmentEntryRelWrapper._commercePriceListUserSegmentEntryRel)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commercePriceListUserSegmentEntryRel.getStagedModelType();
	}

	@Override
	public CommercePriceListUserSegmentEntryRel getWrappedModel() {
		return _commercePriceListUserSegmentEntryRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commercePriceListUserSegmentEntryRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commercePriceListUserSegmentEntryRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commercePriceListUserSegmentEntryRel.resetOriginalValues();
	}

	private final CommercePriceListUserSegmentEntryRel _commercePriceListUserSegmentEntryRel;
}