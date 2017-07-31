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

package com.liferay.commerce.address.model;

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
 * This class is a wrapper for {@link CommerceCountry}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCountry
 * @generated
 */
@ProviderType
public class CommerceCountryWrapper implements CommerceCountry,
	ModelWrapper<CommerceCountry> {
	public CommerceCountryWrapper(CommerceCountry commerceCountry) {
		_commerceCountry = commerceCountry;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceCountry.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceCountry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceCountryId", getCommerceCountryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("allowsBilling", getAllowsBilling());
		attributes.put("allowsShipping", getAllowsShipping());
		attributes.put("twoLettersISOCode", getTwoLettersISOCode());
		attributes.put("threeLettersISOCode", getThreeLettersISOCode());
		attributes.put("numericISOCode", getNumericISOCode());
		attributes.put("priority", getPriority());
		attributes.put("published", getPublished());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceCountryId = (Long)attributes.get("commerceCountryId");

		if (commerceCountryId != null) {
			setCommerceCountryId(commerceCountryId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Boolean allowsBilling = (Boolean)attributes.get("allowsBilling");

		if (allowsBilling != null) {
			setAllowsBilling(allowsBilling);
		}

		Boolean allowsShipping = (Boolean)attributes.get("allowsShipping");

		if (allowsShipping != null) {
			setAllowsShipping(allowsShipping);
		}

		String twoLettersISOCode = (String)attributes.get("twoLettersISOCode");

		if (twoLettersISOCode != null) {
			setTwoLettersISOCode(twoLettersISOCode);
		}

		String threeLettersISOCode = (String)attributes.get(
				"threeLettersISOCode");

		if (threeLettersISOCode != null) {
			setThreeLettersISOCode(threeLettersISOCode);
		}

		Integer numericISOCode = (Integer)attributes.get("numericISOCode");

		if (numericISOCode != null) {
			setNumericISOCode(numericISOCode);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Boolean published = (Boolean)attributes.get("published");

		if (published != null) {
			setPublished(published);
		}
	}

	@Override
	public CommerceCountry toEscapedModel() {
		return new CommerceCountryWrapper(_commerceCountry.toEscapedModel());
	}

	@Override
	public CommerceCountry toUnescapedModel() {
		return new CommerceCountryWrapper(_commerceCountry.toUnescapedModel());
	}

	/**
	* Returns the allows billing of this commerce country.
	*
	* @return the allows billing of this commerce country
	*/
	@Override
	public boolean getAllowsBilling() {
		return _commerceCountry.getAllowsBilling();
	}

	/**
	* Returns the allows shipping of this commerce country.
	*
	* @return the allows shipping of this commerce country
	*/
	@Override
	public boolean getAllowsShipping() {
		return _commerceCountry.getAllowsShipping();
	}

	/**
	* Returns the published of this commerce country.
	*
	* @return the published of this commerce country
	*/
	@Override
	public boolean getPublished() {
		return _commerceCountry.getPublished();
	}

	/**
	* Returns <code>true</code> if this commerce country is allows billing.
	*
	* @return <code>true</code> if this commerce country is allows billing; <code>false</code> otherwise
	*/
	@Override
	public boolean isAllowsBilling() {
		return _commerceCountry.isAllowsBilling();
	}

	/**
	* Returns <code>true</code> if this commerce country is allows shipping.
	*
	* @return <code>true</code> if this commerce country is allows shipping; <code>false</code> otherwise
	*/
	@Override
	public boolean isAllowsShipping() {
		return _commerceCountry.isAllowsShipping();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceCountry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceCountry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceCountry.isNew();
	}

	/**
	* Returns <code>true</code> if this commerce country is published.
	*
	* @return <code>true</code> if this commerce country is published; <code>false</code> otherwise
	*/
	@Override
	public boolean isPublished() {
		return _commerceCountry.isPublished();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceCountry.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceCountry> toCacheModel() {
		return _commerceCountry.toCacheModel();
	}

	@Override
	public int compareTo(CommerceCountry commerceCountry) {
		return _commerceCountry.compareTo(commerceCountry);
	}

	/**
	* Returns the numeric iso code of this commerce country.
	*
	* @return the numeric iso code of this commerce country
	*/
	@Override
	public int getNumericISOCode() {
		return _commerceCountry.getNumericISOCode();
	}

	/**
	* Returns the priority of this commerce country.
	*
	* @return the priority of this commerce country
	*/
	@Override
	public int getPriority() {
		return _commerceCountry.getPriority();
	}

	@Override
	public int hashCode() {
		return _commerceCountry.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceCountry.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceCountryWrapper((CommerceCountry)_commerceCountry.clone());
	}

	/**
	* Returns the name of this commerce country.
	*
	* @return the name of this commerce country
	*/
	@Override
	public java.lang.String getName() {
		return _commerceCountry.getName();
	}

	/**
	* Returns the three letters iso code of this commerce country.
	*
	* @return the three letters iso code of this commerce country
	*/
	@Override
	public java.lang.String getThreeLettersISOCode() {
		return _commerceCountry.getThreeLettersISOCode();
	}

	/**
	* Returns the two letters iso code of this commerce country.
	*
	* @return the two letters iso code of this commerce country
	*/
	@Override
	public java.lang.String getTwoLettersISOCode() {
		return _commerceCountry.getTwoLettersISOCode();
	}

	/**
	* Returns the user name of this commerce country.
	*
	* @return the user name of this commerce country
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceCountry.getUserName();
	}

	/**
	* Returns the user uuid of this commerce country.
	*
	* @return the user uuid of this commerce country
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceCountry.getUserUuid();
	}

	@Override
	public java.lang.String toString() {
		return _commerceCountry.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceCountry.toXmlString();
	}

	/**
	* Returns the create date of this commerce country.
	*
	* @return the create date of this commerce country
	*/
	@Override
	public Date getCreateDate() {
		return _commerceCountry.getCreateDate();
	}

	/**
	* Returns the modified date of this commerce country.
	*
	* @return the modified date of this commerce country
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceCountry.getModifiedDate();
	}

	/**
	* Returns the commerce country ID of this commerce country.
	*
	* @return the commerce country ID of this commerce country
	*/
	@Override
	public long getCommerceCountryId() {
		return _commerceCountry.getCommerceCountryId();
	}

	/**
	* Returns the company ID of this commerce country.
	*
	* @return the company ID of this commerce country
	*/
	@Override
	public long getCompanyId() {
		return _commerceCountry.getCompanyId();
	}

	/**
	* Returns the group ID of this commerce country.
	*
	* @return the group ID of this commerce country
	*/
	@Override
	public long getGroupId() {
		return _commerceCountry.getGroupId();
	}

	/**
	* Returns the primary key of this commerce country.
	*
	* @return the primary key of this commerce country
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceCountry.getPrimaryKey();
	}

	/**
	* Returns the user ID of this commerce country.
	*
	* @return the user ID of this commerce country
	*/
	@Override
	public long getUserId() {
		return _commerceCountry.getUserId();
	}

	@Override
	public void persist() {
		_commerceCountry.persist();
	}

	/**
	* Sets whether this commerce country is allows billing.
	*
	* @param allowsBilling the allows billing of this commerce country
	*/
	@Override
	public void setAllowsBilling(boolean allowsBilling) {
		_commerceCountry.setAllowsBilling(allowsBilling);
	}

	/**
	* Sets whether this commerce country is allows shipping.
	*
	* @param allowsShipping the allows shipping of this commerce country
	*/
	@Override
	public void setAllowsShipping(boolean allowsShipping) {
		_commerceCountry.setAllowsShipping(allowsShipping);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceCountry.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce country ID of this commerce country.
	*
	* @param commerceCountryId the commerce country ID of this commerce country
	*/
	@Override
	public void setCommerceCountryId(long commerceCountryId) {
		_commerceCountry.setCommerceCountryId(commerceCountryId);
	}

	/**
	* Sets the company ID of this commerce country.
	*
	* @param companyId the company ID of this commerce country
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceCountry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce country.
	*
	* @param createDate the create date of this commerce country
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceCountry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceCountry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceCountry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceCountry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce country.
	*
	* @param groupId the group ID of this commerce country
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceCountry.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce country.
	*
	* @param modifiedDate the modified date of this commerce country
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceCountry.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this commerce country.
	*
	* @param name the name of this commerce country
	*/
	@Override
	public void setName(java.lang.String name) {
		_commerceCountry.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_commerceCountry.setNew(n);
	}

	/**
	* Sets the numeric iso code of this commerce country.
	*
	* @param numericISOCode the numeric iso code of this commerce country
	*/
	@Override
	public void setNumericISOCode(int numericISOCode) {
		_commerceCountry.setNumericISOCode(numericISOCode);
	}

	/**
	* Sets the primary key of this commerce country.
	*
	* @param primaryKey the primary key of this commerce country
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceCountry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceCountry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this commerce country.
	*
	* @param priority the priority of this commerce country
	*/
	@Override
	public void setPriority(int priority) {
		_commerceCountry.setPriority(priority);
	}

	/**
	* Sets whether this commerce country is published.
	*
	* @param published the published of this commerce country
	*/
	@Override
	public void setPublished(boolean published) {
		_commerceCountry.setPublished(published);
	}

	/**
	* Sets the three letters iso code of this commerce country.
	*
	* @param threeLettersISOCode the three letters iso code of this commerce country
	*/
	@Override
	public void setThreeLettersISOCode(java.lang.String threeLettersISOCode) {
		_commerceCountry.setThreeLettersISOCode(threeLettersISOCode);
	}

	/**
	* Sets the two letters iso code of this commerce country.
	*
	* @param twoLettersISOCode the two letters iso code of this commerce country
	*/
	@Override
	public void setTwoLettersISOCode(java.lang.String twoLettersISOCode) {
		_commerceCountry.setTwoLettersISOCode(twoLettersISOCode);
	}

	/**
	* Sets the user ID of this commerce country.
	*
	* @param userId the user ID of this commerce country
	*/
	@Override
	public void setUserId(long userId) {
		_commerceCountry.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce country.
	*
	* @param userName the user name of this commerce country
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceCountry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce country.
	*
	* @param userUuid the user uuid of this commerce country
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceCountry.setUserUuid(userUuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceCountryWrapper)) {
			return false;
		}

		CommerceCountryWrapper commerceCountryWrapper = (CommerceCountryWrapper)obj;

		if (Objects.equals(_commerceCountry,
					commerceCountryWrapper._commerceCountry)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceCountry getWrappedModel() {
		return _commerceCountry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceCountry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceCountry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceCountry.resetOriginalValues();
	}

	private final CommerceCountry _commerceCountry;
}