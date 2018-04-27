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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CommerceAddressRestriction}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressRestriction
 * @generated
 */
@ProviderType
public class CommerceAddressRestrictionWrapper
	implements CommerceAddressRestriction,
		ModelWrapper<CommerceAddressRestriction> {
	public CommerceAddressRestrictionWrapper(
		CommerceAddressRestriction commerceAddressRestriction) {
		_commerceAddressRestriction = commerceAddressRestriction;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceAddressRestriction.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceAddressRestriction.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceAddressRestrictionId",
			getCommerceAddressRestrictionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("commerceCountryId", getCommerceCountryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceAddressRestrictionId = (Long)attributes.get(
				"commerceAddressRestrictionId");

		if (commerceAddressRestrictionId != null) {
			setCommerceAddressRestrictionId(commerceAddressRestrictionId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long commerceCountryId = (Long)attributes.get("commerceCountryId");

		if (commerceCountryId != null) {
			setCommerceCountryId(commerceCountryId);
		}
	}

	@Override
	public Object clone() {
		return new CommerceAddressRestrictionWrapper((CommerceAddressRestriction)_commerceAddressRestriction.clone());
	}

	@Override
	public int compareTo(CommerceAddressRestriction commerceAddressRestriction) {
		return _commerceAddressRestriction.compareTo(commerceAddressRestriction);
	}

	/**
	* Returns the fully qualified class name of this commerce address restriction.
	*
	* @return the fully qualified class name of this commerce address restriction
	*/
	@Override
	public String getClassName() {
		return _commerceAddressRestriction.getClassName();
	}

	/**
	* Returns the class name ID of this commerce address restriction.
	*
	* @return the class name ID of this commerce address restriction
	*/
	@Override
	public long getClassNameId() {
		return _commerceAddressRestriction.getClassNameId();
	}

	/**
	* Returns the class pk of this commerce address restriction.
	*
	* @return the class pk of this commerce address restriction
	*/
	@Override
	public long getClassPK() {
		return _commerceAddressRestriction.getClassPK();
	}

	/**
	* Returns the commerce address restriction ID of this commerce address restriction.
	*
	* @return the commerce address restriction ID of this commerce address restriction
	*/
	@Override
	public long getCommerceAddressRestrictionId() {
		return _commerceAddressRestriction.getCommerceAddressRestrictionId();
	}

	@Override
	public CommerceCountry getCommerceCountry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAddressRestriction.getCommerceCountry();
	}

	/**
	* Returns the commerce country ID of this commerce address restriction.
	*
	* @return the commerce country ID of this commerce address restriction
	*/
	@Override
	public long getCommerceCountryId() {
		return _commerceAddressRestriction.getCommerceCountryId();
	}

	/**
	* Returns the company ID of this commerce address restriction.
	*
	* @return the company ID of this commerce address restriction
	*/
	@Override
	public long getCompanyId() {
		return _commerceAddressRestriction.getCompanyId();
	}

	/**
	* Returns the create date of this commerce address restriction.
	*
	* @return the create date of this commerce address restriction
	*/
	@Override
	public Date getCreateDate() {
		return _commerceAddressRestriction.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceAddressRestriction.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce address restriction.
	*
	* @return the group ID of this commerce address restriction
	*/
	@Override
	public long getGroupId() {
		return _commerceAddressRestriction.getGroupId();
	}

	/**
	* Returns the modified date of this commerce address restriction.
	*
	* @return the modified date of this commerce address restriction
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceAddressRestriction.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce address restriction.
	*
	* @return the primary key of this commerce address restriction
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceAddressRestriction.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceAddressRestriction.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this commerce address restriction.
	*
	* @return the user ID of this commerce address restriction
	*/
	@Override
	public long getUserId() {
		return _commerceAddressRestriction.getUserId();
	}

	/**
	* Returns the user name of this commerce address restriction.
	*
	* @return the user name of this commerce address restriction
	*/
	@Override
	public String getUserName() {
		return _commerceAddressRestriction.getUserName();
	}

	/**
	* Returns the user uuid of this commerce address restriction.
	*
	* @return the user uuid of this commerce address restriction
	*/
	@Override
	public String getUserUuid() {
		return _commerceAddressRestriction.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceAddressRestriction.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceAddressRestriction.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceAddressRestriction.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceAddressRestriction.isNew();
	}

	@Override
	public void persist() {
		_commerceAddressRestriction.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceAddressRestriction.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(String className) {
		_commerceAddressRestriction.setClassName(className);
	}

	/**
	* Sets the class name ID of this commerce address restriction.
	*
	* @param classNameId the class name ID of this commerce address restriction
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_commerceAddressRestriction.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this commerce address restriction.
	*
	* @param classPK the class pk of this commerce address restriction
	*/
	@Override
	public void setClassPK(long classPK) {
		_commerceAddressRestriction.setClassPK(classPK);
	}

	/**
	* Sets the commerce address restriction ID of this commerce address restriction.
	*
	* @param commerceAddressRestrictionId the commerce address restriction ID of this commerce address restriction
	*/
	@Override
	public void setCommerceAddressRestrictionId(
		long commerceAddressRestrictionId) {
		_commerceAddressRestriction.setCommerceAddressRestrictionId(commerceAddressRestrictionId);
	}

	/**
	* Sets the commerce country ID of this commerce address restriction.
	*
	* @param commerceCountryId the commerce country ID of this commerce address restriction
	*/
	@Override
	public void setCommerceCountryId(long commerceCountryId) {
		_commerceAddressRestriction.setCommerceCountryId(commerceCountryId);
	}

	/**
	* Sets the company ID of this commerce address restriction.
	*
	* @param companyId the company ID of this commerce address restriction
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceAddressRestriction.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce address restriction.
	*
	* @param createDate the create date of this commerce address restriction
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceAddressRestriction.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceAddressRestriction.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceAddressRestriction.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceAddressRestriction.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce address restriction.
	*
	* @param groupId the group ID of this commerce address restriction
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceAddressRestriction.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce address restriction.
	*
	* @param modifiedDate the modified date of this commerce address restriction
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceAddressRestriction.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceAddressRestriction.setNew(n);
	}

	/**
	* Sets the primary key of this commerce address restriction.
	*
	* @param primaryKey the primary key of this commerce address restriction
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceAddressRestriction.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceAddressRestriction.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this commerce address restriction.
	*
	* @param userId the user ID of this commerce address restriction
	*/
	@Override
	public void setUserId(long userId) {
		_commerceAddressRestriction.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce address restriction.
	*
	* @param userName the user name of this commerce address restriction
	*/
	@Override
	public void setUserName(String userName) {
		_commerceAddressRestriction.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce address restriction.
	*
	* @param userUuid the user uuid of this commerce address restriction
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_commerceAddressRestriction.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceAddressRestriction> toCacheModel() {
		return _commerceAddressRestriction.toCacheModel();
	}

	@Override
	public CommerceAddressRestriction toEscapedModel() {
		return new CommerceAddressRestrictionWrapper(_commerceAddressRestriction.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceAddressRestriction.toString();
	}

	@Override
	public CommerceAddressRestriction toUnescapedModel() {
		return new CommerceAddressRestrictionWrapper(_commerceAddressRestriction.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceAddressRestriction.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceAddressRestrictionWrapper)) {
			return false;
		}

		CommerceAddressRestrictionWrapper commerceAddressRestrictionWrapper = (CommerceAddressRestrictionWrapper)obj;

		if (Objects.equals(_commerceAddressRestriction,
					commerceAddressRestrictionWrapper._commerceAddressRestriction)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceAddressRestriction getWrappedModel() {
		return _commerceAddressRestriction;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceAddressRestriction.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceAddressRestriction.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceAddressRestriction.resetOriginalValues();
	}

	private final CommerceAddressRestriction _commerceAddressRestriction;
}