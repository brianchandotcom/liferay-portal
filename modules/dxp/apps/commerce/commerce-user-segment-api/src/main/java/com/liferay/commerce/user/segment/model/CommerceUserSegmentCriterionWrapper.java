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

package com.liferay.commerce.user.segment.model;

import aQute.bnd.annotation.ProviderType;

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
 * This class is a wrapper for {@link CommerceUserSegmentCriterion}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceUserSegmentCriterion
 * @generated
 */
@ProviderType
public class CommerceUserSegmentCriterionWrapper
	implements CommerceUserSegmentCriterion,
		ModelWrapper<CommerceUserSegmentCriterion> {
	public CommerceUserSegmentCriterionWrapper(
		CommerceUserSegmentCriterion commerceUserSegmentCriterion) {
		_commerceUserSegmentCriterion = commerceUserSegmentCriterion;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceUserSegmentCriterion.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceUserSegmentCriterion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceUserSegmentCriterionId",
			getCommerceUserSegmentCriterionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceUserSegmentEntryId",
			getCommerceUserSegmentEntryId());
		attributes.put("type", getType());
		attributes.put("typeSettings", getTypeSettings());
		attributes.put("priority", getPriority());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceUserSegmentCriterionId = (Long)attributes.get(
				"commerceUserSegmentCriterionId");

		if (commerceUserSegmentCriterionId != null) {
			setCommerceUserSegmentCriterionId(commerceUserSegmentCriterionId);
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

		Long commerceUserSegmentEntryId = (Long)attributes.get(
				"commerceUserSegmentEntryId");

		if (commerceUserSegmentEntryId != null) {
			setCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String typeSettings = (String)attributes.get("typeSettings");

		if (typeSettings != null) {
			setTypeSettings(typeSettings);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}
	}

	@Override
	public Object clone() {
		return new CommerceUserSegmentCriterionWrapper((CommerceUserSegmentCriterion)_commerceUserSegmentCriterion.clone());
	}

	@Override
	public int compareTo(
		CommerceUserSegmentCriterion commerceUserSegmentCriterion) {
		return _commerceUserSegmentCriterion.compareTo(commerceUserSegmentCriterion);
	}

	/**
	* Returns the commerce user segment criterion ID of this commerce user segment criterion.
	*
	* @return the commerce user segment criterion ID of this commerce user segment criterion
	*/
	@Override
	public long getCommerceUserSegmentCriterionId() {
		return _commerceUserSegmentCriterion.getCommerceUserSegmentCriterionId();
	}

	/**
	* Returns the commerce user segment entry ID of this commerce user segment criterion.
	*
	* @return the commerce user segment entry ID of this commerce user segment criterion
	*/
	@Override
	public long getCommerceUserSegmentEntryId() {
		return _commerceUserSegmentCriterion.getCommerceUserSegmentEntryId();
	}

	/**
	* Returns the company ID of this commerce user segment criterion.
	*
	* @return the company ID of this commerce user segment criterion
	*/
	@Override
	public long getCompanyId() {
		return _commerceUserSegmentCriterion.getCompanyId();
	}

	/**
	* Returns the create date of this commerce user segment criterion.
	*
	* @return the create date of this commerce user segment criterion
	*/
	@Override
	public Date getCreateDate() {
		return _commerceUserSegmentCriterion.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceUserSegmentCriterion.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce user segment criterion.
	*
	* @return the group ID of this commerce user segment criterion
	*/
	@Override
	public long getGroupId() {
		return _commerceUserSegmentCriterion.getGroupId();
	}

	/**
	* Returns the modified date of this commerce user segment criterion.
	*
	* @return the modified date of this commerce user segment criterion
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceUserSegmentCriterion.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce user segment criterion.
	*
	* @return the primary key of this commerce user segment criterion
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceUserSegmentCriterion.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceUserSegmentCriterion.getPrimaryKeyObj();
	}

	/**
	* Returns the priority of this commerce user segment criterion.
	*
	* @return the priority of this commerce user segment criterion
	*/
	@Override
	public double getPriority() {
		return _commerceUserSegmentCriterion.getPriority();
	}

	/**
	* Returns the type of this commerce user segment criterion.
	*
	* @return the type of this commerce user segment criterion
	*/
	@Override
	public String getType() {
		return _commerceUserSegmentCriterion.getType();
	}

	/**
	* Returns the type settings of this commerce user segment criterion.
	*
	* @return the type settings of this commerce user segment criterion
	*/
	@Override
	public String getTypeSettings() {
		return _commerceUserSegmentCriterion.getTypeSettings();
	}

	/**
	* Returns the user ID of this commerce user segment criterion.
	*
	* @return the user ID of this commerce user segment criterion
	*/
	@Override
	public long getUserId() {
		return _commerceUserSegmentCriterion.getUserId();
	}

	/**
	* Returns the user name of this commerce user segment criterion.
	*
	* @return the user name of this commerce user segment criterion
	*/
	@Override
	public String getUserName() {
		return _commerceUserSegmentCriterion.getUserName();
	}

	/**
	* Returns the user uuid of this commerce user segment criterion.
	*
	* @return the user uuid of this commerce user segment criterion
	*/
	@Override
	public String getUserUuid() {
		return _commerceUserSegmentCriterion.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceUserSegmentCriterion.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceUserSegmentCriterion.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceUserSegmentCriterion.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceUserSegmentCriterion.isNew();
	}

	@Override
	public void persist() {
		_commerceUserSegmentCriterion.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceUserSegmentCriterion.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce user segment criterion ID of this commerce user segment criterion.
	*
	* @param commerceUserSegmentCriterionId the commerce user segment criterion ID of this commerce user segment criterion
	*/
	@Override
	public void setCommerceUserSegmentCriterionId(
		long commerceUserSegmentCriterionId) {
		_commerceUserSegmentCriterion.setCommerceUserSegmentCriterionId(commerceUserSegmentCriterionId);
	}

	/**
	* Sets the commerce user segment entry ID of this commerce user segment criterion.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID of this commerce user segment criterion
	*/
	@Override
	public void setCommerceUserSegmentEntryId(long commerceUserSegmentEntryId) {
		_commerceUserSegmentCriterion.setCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	/**
	* Sets the company ID of this commerce user segment criterion.
	*
	* @param companyId the company ID of this commerce user segment criterion
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceUserSegmentCriterion.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce user segment criterion.
	*
	* @param createDate the create date of this commerce user segment criterion
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceUserSegmentCriterion.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceUserSegmentCriterion.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceUserSegmentCriterion.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceUserSegmentCriterion.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce user segment criterion.
	*
	* @param groupId the group ID of this commerce user segment criterion
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceUserSegmentCriterion.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce user segment criterion.
	*
	* @param modifiedDate the modified date of this commerce user segment criterion
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceUserSegmentCriterion.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceUserSegmentCriterion.setNew(n);
	}

	/**
	* Sets the primary key of this commerce user segment criterion.
	*
	* @param primaryKey the primary key of this commerce user segment criterion
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceUserSegmentCriterion.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceUserSegmentCriterion.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this commerce user segment criterion.
	*
	* @param priority the priority of this commerce user segment criterion
	*/
	@Override
	public void setPriority(double priority) {
		_commerceUserSegmentCriterion.setPriority(priority);
	}

	/**
	* Sets the type of this commerce user segment criterion.
	*
	* @param type the type of this commerce user segment criterion
	*/
	@Override
	public void setType(String type) {
		_commerceUserSegmentCriterion.setType(type);
	}

	/**
	* Sets the type settings of this commerce user segment criterion.
	*
	* @param typeSettings the type settings of this commerce user segment criterion
	*/
	@Override
	public void setTypeSettings(String typeSettings) {
		_commerceUserSegmentCriterion.setTypeSettings(typeSettings);
	}

	/**
	* Sets the user ID of this commerce user segment criterion.
	*
	* @param userId the user ID of this commerce user segment criterion
	*/
	@Override
	public void setUserId(long userId) {
		_commerceUserSegmentCriterion.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce user segment criterion.
	*
	* @param userName the user name of this commerce user segment criterion
	*/
	@Override
	public void setUserName(String userName) {
		_commerceUserSegmentCriterion.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce user segment criterion.
	*
	* @param userUuid the user uuid of this commerce user segment criterion
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_commerceUserSegmentCriterion.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceUserSegmentCriterion> toCacheModel() {
		return _commerceUserSegmentCriterion.toCacheModel();
	}

	@Override
	public CommerceUserSegmentCriterion toEscapedModel() {
		return new CommerceUserSegmentCriterionWrapper(_commerceUserSegmentCriterion.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceUserSegmentCriterion.toString();
	}

	@Override
	public CommerceUserSegmentCriterion toUnescapedModel() {
		return new CommerceUserSegmentCriterionWrapper(_commerceUserSegmentCriterion.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceUserSegmentCriterion.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceUserSegmentCriterionWrapper)) {
			return false;
		}

		CommerceUserSegmentCriterionWrapper commerceUserSegmentCriterionWrapper = (CommerceUserSegmentCriterionWrapper)obj;

		if (Objects.equals(_commerceUserSegmentCriterion,
					commerceUserSegmentCriterionWrapper._commerceUserSegmentCriterion)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceUserSegmentCriterion getWrappedModel() {
		return _commerceUserSegmentCriterion;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceUserSegmentCriterion.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceUserSegmentCriterion.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceUserSegmentCriterion.resetOriginalValues();
	}

	private final CommerceUserSegmentCriterion _commerceUserSegmentCriterion;
}