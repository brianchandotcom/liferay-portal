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

package com.liferay.commerce.model;

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
 * This class is a wrapper for {@link CommercePriceListQualificationTypeRel}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListQualificationTypeRel
 * @generated
 */
@ProviderType
public class CommercePriceListQualificationTypeRelWrapper
	implements CommercePriceListQualificationTypeRel,
		ModelWrapper<CommercePriceListQualificationTypeRel> {
	public CommercePriceListQualificationTypeRelWrapper(
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel) {
		_commercePriceListQualificationTypeRel = commercePriceListQualificationTypeRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommercePriceListQualificationTypeRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommercePriceListQualificationTypeRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("commercePriceListQualificationTypeRelId",
			getCommercePriceListQualificationTypeRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commercePriceListId", getCommercePriceListId());
		attributes.put("commercePriceListQualificationType",
			getCommercePriceListQualificationType());
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

		Long commercePriceListQualificationTypeRelId = (Long)attributes.get(
				"commercePriceListQualificationTypeRelId");

		if (commercePriceListQualificationTypeRelId != null) {
			setCommercePriceListQualificationTypeRelId(commercePriceListQualificationTypeRelId);
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

		String commercePriceListQualificationType = (String)attributes.get(
				"commercePriceListQualificationType");

		if (commercePriceListQualificationType != null) {
			setCommercePriceListQualificationType(commercePriceListQualificationType);
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
		return new CommercePriceListQualificationTypeRelWrapper((CommercePriceListQualificationTypeRel)_commercePriceListQualificationTypeRel.clone());
	}

	@Override
	public int compareTo(
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel) {
		return _commercePriceListQualificationTypeRel.compareTo(commercePriceListQualificationTypeRel);
	}

	/**
	* Returns the commerce price list ID of this commerce price list qualification type rel.
	*
	* @return the commerce price list ID of this commerce price list qualification type rel
	*/
	@Override
	public long getCommercePriceListId() {
		return _commercePriceListQualificationTypeRel.getCommercePriceListId();
	}

	/**
	* Returns the commerce price list qualification type of this commerce price list qualification type rel.
	*
	* @return the commerce price list qualification type of this commerce price list qualification type rel
	*/
	@Override
	public java.lang.String getCommercePriceListQualificationType() {
		return _commercePriceListQualificationTypeRel.getCommercePriceListQualificationType();
	}

	/**
	* Returns the commerce price list qualification type rel ID of this commerce price list qualification type rel.
	*
	* @return the commerce price list qualification type rel ID of this commerce price list qualification type rel
	*/
	@Override
	public long getCommercePriceListQualificationTypeRelId() {
		return _commercePriceListQualificationTypeRel.getCommercePriceListQualificationTypeRelId();
	}

	/**
	* Returns the company ID of this commerce price list qualification type rel.
	*
	* @return the company ID of this commerce price list qualification type rel
	*/
	@Override
	public long getCompanyId() {
		return _commercePriceListQualificationTypeRel.getCompanyId();
	}

	/**
	* Returns the create date of this commerce price list qualification type rel.
	*
	* @return the create date of this commerce price list qualification type rel
	*/
	@Override
	public Date getCreateDate() {
		return _commercePriceListQualificationTypeRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commercePriceListQualificationTypeRel.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce price list qualification type rel.
	*
	* @return the group ID of this commerce price list qualification type rel
	*/
	@Override
	public long getGroupId() {
		return _commercePriceListQualificationTypeRel.getGroupId();
	}

	/**
	* Returns the last publish date of this commerce price list qualification type rel.
	*
	* @return the last publish date of this commerce price list qualification type rel
	*/
	@Override
	public Date getLastPublishDate() {
		return _commercePriceListQualificationTypeRel.getLastPublishDate();
	}

	/**
	* Returns the modified date of this commerce price list qualification type rel.
	*
	* @return the modified date of this commerce price list qualification type rel
	*/
	@Override
	public Date getModifiedDate() {
		return _commercePriceListQualificationTypeRel.getModifiedDate();
	}

	/**
	* Returns the order of this commerce price list qualification type rel.
	*
	* @return the order of this commerce price list qualification type rel
	*/
	@Override
	public int getOrder() {
		return _commercePriceListQualificationTypeRel.getOrder();
	}

	/**
	* Returns the primary key of this commerce price list qualification type rel.
	*
	* @return the primary key of this commerce price list qualification type rel
	*/
	@Override
	public long getPrimaryKey() {
		return _commercePriceListQualificationTypeRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commercePriceListQualificationTypeRel.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this commerce price list qualification type rel.
	*
	* @return the user ID of this commerce price list qualification type rel
	*/
	@Override
	public long getUserId() {
		return _commercePriceListQualificationTypeRel.getUserId();
	}

	/**
	* Returns the user name of this commerce price list qualification type rel.
	*
	* @return the user name of this commerce price list qualification type rel
	*/
	@Override
	public java.lang.String getUserName() {
		return _commercePriceListQualificationTypeRel.getUserName();
	}

	/**
	* Returns the user uuid of this commerce price list qualification type rel.
	*
	* @return the user uuid of this commerce price list qualification type rel
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commercePriceListQualificationTypeRel.getUserUuid();
	}

	/**
	* Returns the uuid of this commerce price list qualification type rel.
	*
	* @return the uuid of this commerce price list qualification type rel
	*/
	@Override
	public java.lang.String getUuid() {
		return _commercePriceListQualificationTypeRel.getUuid();
	}

	@Override
	public int hashCode() {
		return _commercePriceListQualificationTypeRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commercePriceListQualificationTypeRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commercePriceListQualificationTypeRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commercePriceListQualificationTypeRel.isNew();
	}

	@Override
	public void persist() {
		_commercePriceListQualificationTypeRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commercePriceListQualificationTypeRel.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce price list ID of this commerce price list qualification type rel.
	*
	* @param commercePriceListId the commerce price list ID of this commerce price list qualification type rel
	*/
	@Override
	public void setCommercePriceListId(long commercePriceListId) {
		_commercePriceListQualificationTypeRel.setCommercePriceListId(commercePriceListId);
	}

	/**
	* Sets the commerce price list qualification type of this commerce price list qualification type rel.
	*
	* @param commercePriceListQualificationType the commerce price list qualification type of this commerce price list qualification type rel
	*/
	@Override
	public void setCommercePriceListQualificationType(
		java.lang.String commercePriceListQualificationType) {
		_commercePriceListQualificationTypeRel.setCommercePriceListQualificationType(commercePriceListQualificationType);
	}

	/**
	* Sets the commerce price list qualification type rel ID of this commerce price list qualification type rel.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID of this commerce price list qualification type rel
	*/
	@Override
	public void setCommercePriceListQualificationTypeRelId(
		long commercePriceListQualificationTypeRelId) {
		_commercePriceListQualificationTypeRel.setCommercePriceListQualificationTypeRelId(commercePriceListQualificationTypeRelId);
	}

	/**
	* Sets the company ID of this commerce price list qualification type rel.
	*
	* @param companyId the company ID of this commerce price list qualification type rel
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commercePriceListQualificationTypeRel.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce price list qualification type rel.
	*
	* @param createDate the create date of this commerce price list qualification type rel
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commercePriceListQualificationTypeRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commercePriceListQualificationTypeRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commercePriceListQualificationTypeRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commercePriceListQualificationTypeRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce price list qualification type rel.
	*
	* @param groupId the group ID of this commerce price list qualification type rel
	*/
	@Override
	public void setGroupId(long groupId) {
		_commercePriceListQualificationTypeRel.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this commerce price list qualification type rel.
	*
	* @param lastPublishDate the last publish date of this commerce price list qualification type rel
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_commercePriceListQualificationTypeRel.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this commerce price list qualification type rel.
	*
	* @param modifiedDate the modified date of this commerce price list qualification type rel
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commercePriceListQualificationTypeRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commercePriceListQualificationTypeRel.setNew(n);
	}

	/**
	* Sets the order of this commerce price list qualification type rel.
	*
	* @param order the order of this commerce price list qualification type rel
	*/
	@Override
	public void setOrder(int order) {
		_commercePriceListQualificationTypeRel.setOrder(order);
	}

	/**
	* Sets the primary key of this commerce price list qualification type rel.
	*
	* @param primaryKey the primary key of this commerce price list qualification type rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commercePriceListQualificationTypeRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commercePriceListQualificationTypeRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this commerce price list qualification type rel.
	*
	* @param userId the user ID of this commerce price list qualification type rel
	*/
	@Override
	public void setUserId(long userId) {
		_commercePriceListQualificationTypeRel.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce price list qualification type rel.
	*
	* @param userName the user name of this commerce price list qualification type rel
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commercePriceListQualificationTypeRel.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce price list qualification type rel.
	*
	* @param userUuid the user uuid of this commerce price list qualification type rel
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commercePriceListQualificationTypeRel.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this commerce price list qualification type rel.
	*
	* @param uuid the uuid of this commerce price list qualification type rel
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_commercePriceListQualificationTypeRel.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommercePriceListQualificationTypeRel> toCacheModel() {
		return _commercePriceListQualificationTypeRel.toCacheModel();
	}

	@Override
	public CommercePriceListQualificationTypeRel toEscapedModel() {
		return new CommercePriceListQualificationTypeRelWrapper(_commercePriceListQualificationTypeRel.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commercePriceListQualificationTypeRel.toString();
	}

	@Override
	public CommercePriceListQualificationTypeRel toUnescapedModel() {
		return new CommercePriceListQualificationTypeRelWrapper(_commercePriceListQualificationTypeRel.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commercePriceListQualificationTypeRel.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommercePriceListQualificationTypeRelWrapper)) {
			return false;
		}

		CommercePriceListQualificationTypeRelWrapper commercePriceListQualificationTypeRelWrapper =
			(CommercePriceListQualificationTypeRelWrapper)obj;

		if (Objects.equals(_commercePriceListQualificationTypeRel,
					commercePriceListQualificationTypeRelWrapper._commercePriceListQualificationTypeRel)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commercePriceListQualificationTypeRel.getStagedModelType();
	}

	@Override
	public CommercePriceListQualificationTypeRel getWrappedModel() {
		return _commercePriceListQualificationTypeRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commercePriceListQualificationTypeRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commercePriceListQualificationTypeRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commercePriceListQualificationTypeRel.resetOriginalValues();
	}

	private final CommercePriceListQualificationTypeRel _commercePriceListQualificationTypeRel;
}