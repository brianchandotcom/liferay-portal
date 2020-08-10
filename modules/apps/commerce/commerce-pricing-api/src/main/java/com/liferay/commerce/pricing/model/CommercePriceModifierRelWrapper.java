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
 * This class is a wrapper for {@link CommercePriceModifierRel}.
 * </p>
 *
 * @author Riccardo Alberti
 * @see CommercePriceModifierRel
 * @generated
 */
public class CommercePriceModifierRelWrapper
	implements CommercePriceModifierRel,
			   ModelWrapper<CommercePriceModifierRel> {

	public CommercePriceModifierRelWrapper(
		CommercePriceModifierRel commercePriceModifierRel) {

		_commercePriceModifierRel = commercePriceModifierRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommercePriceModifierRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommercePriceModifierRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"commercePriceModifierRelId", getCommercePriceModifierRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commercePriceModifierId", getCommercePriceModifierId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commercePriceModifierRelId = (Long)attributes.get(
			"commercePriceModifierRelId");

		if (commercePriceModifierRelId != null) {
			setCommercePriceModifierRelId(commercePriceModifierRelId);
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

		Long commercePriceModifierId = (Long)attributes.get(
			"commercePriceModifierId");

		if (commercePriceModifierId != null) {
			setCommercePriceModifierId(commercePriceModifierId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}
	}

	@Override
	public Object clone() {
		return new CommercePriceModifierRelWrapper(
			(CommercePriceModifierRel)_commercePriceModifierRel.clone());
	}

	@Override
	public int compareTo(CommercePriceModifierRel commercePriceModifierRel) {
		return _commercePriceModifierRel.compareTo(commercePriceModifierRel);
	}

	/**
	 * Returns the fully qualified class name of this commerce price modifier rel.
	 *
	 * @return the fully qualified class name of this commerce price modifier rel
	 */
	@Override
	public String getClassName() {
		return _commercePriceModifierRel.getClassName();
	}

	/**
	 * Returns the class name ID of this commerce price modifier rel.
	 *
	 * @return the class name ID of this commerce price modifier rel
	 */
	@Override
	public long getClassNameId() {
		return _commercePriceModifierRel.getClassNameId();
	}

	/**
	 * Returns the class pk of this commerce price modifier rel.
	 *
	 * @return the class pk of this commerce price modifier rel
	 */
	@Override
	public long getClassPK() {
		return _commercePriceModifierRel.getClassPK();
	}

	@Override
	public CommercePriceModifier getCommercePriceModifier()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceModifierRel.getCommercePriceModifier();
	}

	/**
	 * Returns the commerce price modifier ID of this commerce price modifier rel.
	 *
	 * @return the commerce price modifier ID of this commerce price modifier rel
	 */
	@Override
	public long getCommercePriceModifierId() {
		return _commercePriceModifierRel.getCommercePriceModifierId();
	}

	/**
	 * Returns the commerce price modifier rel ID of this commerce price modifier rel.
	 *
	 * @return the commerce price modifier rel ID of this commerce price modifier rel
	 */
	@Override
	public long getCommercePriceModifierRelId() {
		return _commercePriceModifierRel.getCommercePriceModifierRelId();
	}

	/**
	 * Returns the company ID of this commerce price modifier rel.
	 *
	 * @return the company ID of this commerce price modifier rel
	 */
	@Override
	public long getCompanyId() {
		return _commercePriceModifierRel.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce price modifier rel.
	 *
	 * @return the create date of this commerce price modifier rel
	 */
	@Override
	public Date getCreateDate() {
		return _commercePriceModifierRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commercePriceModifierRel.getExpandoBridge();
	}

	/**
	 * Returns the modified date of this commerce price modifier rel.
	 *
	 * @return the modified date of this commerce price modifier rel
	 */
	@Override
	public Date getModifiedDate() {
		return _commercePriceModifierRel.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce price modifier rel.
	 *
	 * @return the primary key of this commerce price modifier rel
	 */
	@Override
	public long getPrimaryKey() {
		return _commercePriceModifierRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commercePriceModifierRel.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this commerce price modifier rel.
	 *
	 * @return the user ID of this commerce price modifier rel
	 */
	@Override
	public long getUserId() {
		return _commercePriceModifierRel.getUserId();
	}

	/**
	 * Returns the user name of this commerce price modifier rel.
	 *
	 * @return the user name of this commerce price modifier rel
	 */
	@Override
	public String getUserName() {
		return _commercePriceModifierRel.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce price modifier rel.
	 *
	 * @return the user uuid of this commerce price modifier rel
	 */
	@Override
	public String getUserUuid() {
		return _commercePriceModifierRel.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commercePriceModifierRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commercePriceModifierRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commercePriceModifierRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commercePriceModifierRel.isNew();
	}

	@Override
	public void persist() {
		_commercePriceModifierRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commercePriceModifierRel.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(String className) {
		_commercePriceModifierRel.setClassName(className);
	}

	/**
	 * Sets the class name ID of this commerce price modifier rel.
	 *
	 * @param classNameId the class name ID of this commerce price modifier rel
	 */
	@Override
	public void setClassNameId(long classNameId) {
		_commercePriceModifierRel.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this commerce price modifier rel.
	 *
	 * @param classPK the class pk of this commerce price modifier rel
	 */
	@Override
	public void setClassPK(long classPK) {
		_commercePriceModifierRel.setClassPK(classPK);
	}

	/**
	 * Sets the commerce price modifier ID of this commerce price modifier rel.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID of this commerce price modifier rel
	 */
	@Override
	public void setCommercePriceModifierId(long commercePriceModifierId) {
		_commercePriceModifierRel.setCommercePriceModifierId(
			commercePriceModifierId);
	}

	/**
	 * Sets the commerce price modifier rel ID of this commerce price modifier rel.
	 *
	 * @param commercePriceModifierRelId the commerce price modifier rel ID of this commerce price modifier rel
	 */
	@Override
	public void setCommercePriceModifierRelId(long commercePriceModifierRelId) {
		_commercePriceModifierRel.setCommercePriceModifierRelId(
			commercePriceModifierRelId);
	}

	/**
	 * Sets the company ID of this commerce price modifier rel.
	 *
	 * @param companyId the company ID of this commerce price modifier rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commercePriceModifierRel.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce price modifier rel.
	 *
	 * @param createDate the create date of this commerce price modifier rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commercePriceModifierRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commercePriceModifierRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commercePriceModifierRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commercePriceModifierRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the modified date of this commerce price modifier rel.
	 *
	 * @param modifiedDate the modified date of this commerce price modifier rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commercePriceModifierRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commercePriceModifierRel.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce price modifier rel.
	 *
	 * @param primaryKey the primary key of this commerce price modifier rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commercePriceModifierRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commercePriceModifierRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this commerce price modifier rel.
	 *
	 * @param userId the user ID of this commerce price modifier rel
	 */
	@Override
	public void setUserId(long userId) {
		_commercePriceModifierRel.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce price modifier rel.
	 *
	 * @param userName the user name of this commerce price modifier rel
	 */
	@Override
	public void setUserName(String userName) {
		_commercePriceModifierRel.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce price modifier rel.
	 *
	 * @param userUuid the user uuid of this commerce price modifier rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commercePriceModifierRel.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommercePriceModifierRel>
		toCacheModel() {

		return _commercePriceModifierRel.toCacheModel();
	}

	@Override
	public CommercePriceModifierRel toEscapedModel() {
		return new CommercePriceModifierRelWrapper(
			_commercePriceModifierRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commercePriceModifierRel.toString();
	}

	@Override
	public CommercePriceModifierRel toUnescapedModel() {
		return new CommercePriceModifierRelWrapper(
			_commercePriceModifierRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commercePriceModifierRel.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommercePriceModifierRelWrapper)) {
			return false;
		}

		CommercePriceModifierRelWrapper commercePriceModifierRelWrapper =
			(CommercePriceModifierRelWrapper)object;

		if (Objects.equals(
				_commercePriceModifierRel,
				commercePriceModifierRelWrapper._commercePriceModifierRel)) {

			return true;
		}

		return false;
	}

	@Override
	public CommercePriceModifierRel getWrappedModel() {
		return _commercePriceModifierRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commercePriceModifierRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commercePriceModifierRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commercePriceModifierRel.resetOriginalValues();
	}

	private final CommercePriceModifierRel _commercePriceModifierRel;

}