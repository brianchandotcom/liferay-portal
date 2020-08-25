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

package com.liferay.commerce.pricing.model;

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
 * This class is a wrapper for {@link CommercePricingClassCPDefinitionRel}.
 * </p>
 *
 * @author Riccardo Alberti
 * @see CommercePricingClassCPDefinitionRel
 * @generated
 */
public class CommercePricingClassCPDefinitionRelWrapper
	implements CommercePricingClassCPDefinitionRel,
			   ModelWrapper<CommercePricingClassCPDefinitionRel> {

	public CommercePricingClassCPDefinitionRelWrapper(
		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel) {

		_commercePricingClassCPDefinitionRel =
			commercePricingClassCPDefinitionRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommercePricingClassCPDefinitionRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommercePricingClassCPDefinitionRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"CommercePricingClassCPDefinitionRelId",
			getCommercePricingClassCPDefinitionRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commercePricingClassId", getCommercePricingClassId());
		attributes.put("CPDefinitionId", getCPDefinitionId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long CommercePricingClassCPDefinitionRelId = (Long)attributes.get(
			"CommercePricingClassCPDefinitionRelId");

		if (CommercePricingClassCPDefinitionRelId != null) {
			setCommercePricingClassCPDefinitionRelId(
				CommercePricingClassCPDefinitionRelId);
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

		Long commercePricingClassId = (Long)attributes.get(
			"commercePricingClassId");

		if (commercePricingClassId != null) {
			setCommercePricingClassId(commercePricingClassId);
		}

		Long CPDefinitionId = (Long)attributes.get("CPDefinitionId");

		if (CPDefinitionId != null) {
			setCPDefinitionId(CPDefinitionId);
		}
	}

	@Override
	public Object clone() {
		return new CommercePricingClassCPDefinitionRelWrapper(
			(CommercePricingClassCPDefinitionRel)
				_commercePricingClassCPDefinitionRel.clone());
	}

	@Override
	public int compareTo(
		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel) {

		return _commercePricingClassCPDefinitionRel.compareTo(
			commercePricingClassCPDefinitionRel);
	}

	@Override
	public CommercePricingClass getCommercePricingClass()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePricingClassCPDefinitionRel.getCommercePricingClass();
	}

	/**
	 * Returns the commerce pricing class cp definition rel ID of this commerce pricing class cp definition rel.
	 *
	 * @return the commerce pricing class cp definition rel ID of this commerce pricing class cp definition rel
	 */
	@Override
	public long getCommercePricingClassCPDefinitionRelId() {
		return _commercePricingClassCPDefinitionRel.
			getCommercePricingClassCPDefinitionRelId();
	}

	/**
	 * Returns the commerce pricing class ID of this commerce pricing class cp definition rel.
	 *
	 * @return the commerce pricing class ID of this commerce pricing class cp definition rel
	 */
	@Override
	public long getCommercePricingClassId() {
		return _commercePricingClassCPDefinitionRel.getCommercePricingClassId();
	}

	/**
	 * Returns the company ID of this commerce pricing class cp definition rel.
	 *
	 * @return the company ID of this commerce pricing class cp definition rel
	 */
	@Override
	public long getCompanyId() {
		return _commercePricingClassCPDefinitionRel.getCompanyId();
	}

	/**
	 * Returns the cp definition ID of this commerce pricing class cp definition rel.
	 *
	 * @return the cp definition ID of this commerce pricing class cp definition rel
	 */
	@Override
	public long getCPDefinitionId() {
		return _commercePricingClassCPDefinitionRel.getCPDefinitionId();
	}

	/**
	 * Returns the create date of this commerce pricing class cp definition rel.
	 *
	 * @return the create date of this commerce pricing class cp definition rel
	 */
	@Override
	public Date getCreateDate() {
		return _commercePricingClassCPDefinitionRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commercePricingClassCPDefinitionRel.getExpandoBridge();
	}

	/**
	 * Returns the modified date of this commerce pricing class cp definition rel.
	 *
	 * @return the modified date of this commerce pricing class cp definition rel
	 */
	@Override
	public Date getModifiedDate() {
		return _commercePricingClassCPDefinitionRel.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce pricing class cp definition rel.
	 *
	 * @return the primary key of this commerce pricing class cp definition rel
	 */
	@Override
	public long getPrimaryKey() {
		return _commercePricingClassCPDefinitionRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commercePricingClassCPDefinitionRel.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this commerce pricing class cp definition rel.
	 *
	 * @return the user ID of this commerce pricing class cp definition rel
	 */
	@Override
	public long getUserId() {
		return _commercePricingClassCPDefinitionRel.getUserId();
	}

	/**
	 * Returns the user name of this commerce pricing class cp definition rel.
	 *
	 * @return the user name of this commerce pricing class cp definition rel
	 */
	@Override
	public String getUserName() {
		return _commercePricingClassCPDefinitionRel.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce pricing class cp definition rel.
	 *
	 * @return the user uuid of this commerce pricing class cp definition rel
	 */
	@Override
	public String getUserUuid() {
		return _commercePricingClassCPDefinitionRel.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commercePricingClassCPDefinitionRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commercePricingClassCPDefinitionRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commercePricingClassCPDefinitionRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commercePricingClassCPDefinitionRel.isNew();
	}

	@Override
	public void persist() {
		_commercePricingClassCPDefinitionRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commercePricingClassCPDefinitionRel.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce pricing class cp definition rel ID of this commerce pricing class cp definition rel.
	 *
	 * @param CommercePricingClassCPDefinitionRelId the commerce pricing class cp definition rel ID of this commerce pricing class cp definition rel
	 */
	@Override
	public void setCommercePricingClassCPDefinitionRelId(
		long CommercePricingClassCPDefinitionRelId) {

		_commercePricingClassCPDefinitionRel.
			setCommercePricingClassCPDefinitionRelId(
				CommercePricingClassCPDefinitionRelId);
	}

	/**
	 * Sets the commerce pricing class ID of this commerce pricing class cp definition rel.
	 *
	 * @param commercePricingClassId the commerce pricing class ID of this commerce pricing class cp definition rel
	 */
	@Override
	public void setCommercePricingClassId(long commercePricingClassId) {
		_commercePricingClassCPDefinitionRel.setCommercePricingClassId(
			commercePricingClassId);
	}

	/**
	 * Sets the company ID of this commerce pricing class cp definition rel.
	 *
	 * @param companyId the company ID of this commerce pricing class cp definition rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commercePricingClassCPDefinitionRel.setCompanyId(companyId);
	}

	/**
	 * Sets the cp definition ID of this commerce pricing class cp definition rel.
	 *
	 * @param CPDefinitionId the cp definition ID of this commerce pricing class cp definition rel
	 */
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		_commercePricingClassCPDefinitionRel.setCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Sets the create date of this commerce pricing class cp definition rel.
	 *
	 * @param createDate the create date of this commerce pricing class cp definition rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commercePricingClassCPDefinitionRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commercePricingClassCPDefinitionRel.setExpandoBridgeAttributes(
			baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commercePricingClassCPDefinitionRel.setExpandoBridgeAttributes(
			expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commercePricingClassCPDefinitionRel.setExpandoBridgeAttributes(
			serviceContext);
	}

	/**
	 * Sets the modified date of this commerce pricing class cp definition rel.
	 *
	 * @param modifiedDate the modified date of this commerce pricing class cp definition rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commercePricingClassCPDefinitionRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commercePricingClassCPDefinitionRel.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce pricing class cp definition rel.
	 *
	 * @param primaryKey the primary key of this commerce pricing class cp definition rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commercePricingClassCPDefinitionRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commercePricingClassCPDefinitionRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this commerce pricing class cp definition rel.
	 *
	 * @param userId the user ID of this commerce pricing class cp definition rel
	 */
	@Override
	public void setUserId(long userId) {
		_commercePricingClassCPDefinitionRel.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce pricing class cp definition rel.
	 *
	 * @param userName the user name of this commerce pricing class cp definition rel
	 */
	@Override
	public void setUserName(String userName) {
		_commercePricingClassCPDefinitionRel.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce pricing class cp definition rel.
	 *
	 * @param userUuid the user uuid of this commerce pricing class cp definition rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commercePricingClassCPDefinitionRel.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommercePricingClassCPDefinitionRel> toCacheModel() {

		return _commercePricingClassCPDefinitionRel.toCacheModel();
	}

	@Override
	public CommercePricingClassCPDefinitionRel toEscapedModel() {
		return new CommercePricingClassCPDefinitionRelWrapper(
			_commercePricingClassCPDefinitionRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commercePricingClassCPDefinitionRel.toString();
	}

	@Override
	public CommercePricingClassCPDefinitionRel toUnescapedModel() {
		return new CommercePricingClassCPDefinitionRelWrapper(
			_commercePricingClassCPDefinitionRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commercePricingClassCPDefinitionRel.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommercePricingClassCPDefinitionRelWrapper)) {
			return false;
		}

		CommercePricingClassCPDefinitionRelWrapper
			commercePricingClassCPDefinitionRelWrapper =
				(CommercePricingClassCPDefinitionRelWrapper)object;

		if (Objects.equals(
				_commercePricingClassCPDefinitionRel,
				commercePricingClassCPDefinitionRelWrapper.
					_commercePricingClassCPDefinitionRel)) {

			return true;
		}

		return false;
	}

	@Override
	public CommercePricingClassCPDefinitionRel getWrappedModel() {
		return _commercePricingClassCPDefinitionRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commercePricingClassCPDefinitionRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commercePricingClassCPDefinitionRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commercePricingClassCPDefinitionRel.resetOriginalValues();
	}

	private final CommercePricingClassCPDefinitionRel
		_commercePricingClassCPDefinitionRel;

}