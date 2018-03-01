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

package com.liferay.commerce.vat.model;

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
 * This class is a wrapper for {@link CommerceVatNumber}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVatNumber
 * @generated
 */
@ProviderType
public class CommerceVatNumberWrapper implements CommerceVatNumber,
	ModelWrapper<CommerceVatNumber> {
	public CommerceVatNumberWrapper(CommerceVatNumber commerceVatNumber) {
		_commerceVatNumber = commerceVatNumber;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceVatNumber.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceVatNumber.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceVatNumberId", getCommerceVatNumberId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("vatNumber", getVatNumber());
		attributes.put("valid", getValid());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceVatNumberId = (Long)attributes.get("commerceVatNumberId");

		if (commerceVatNumberId != null) {
			setCommerceVatNumberId(commerceVatNumberId);
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

		String vatNumber = (String)attributes.get("vatNumber");

		if (vatNumber != null) {
			setVatNumber(vatNumber);
		}

		Boolean valid = (Boolean)attributes.get("valid");

		if (valid != null) {
			setValid(valid);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceVatNumberWrapper((CommerceVatNumber)_commerceVatNumber.clone());
	}

	@Override
	public int compareTo(CommerceVatNumber commerceVatNumber) {
		return _commerceVatNumber.compareTo(commerceVatNumber);
	}

	/**
	* Returns the fully qualified class name of this commerce vat number.
	*
	* @return the fully qualified class name of this commerce vat number
	*/
	@Override
	public java.lang.String getClassName() {
		return _commerceVatNumber.getClassName();
	}

	/**
	* Returns the class name ID of this commerce vat number.
	*
	* @return the class name ID of this commerce vat number
	*/
	@Override
	public long getClassNameId() {
		return _commerceVatNumber.getClassNameId();
	}

	/**
	* Returns the class pk of this commerce vat number.
	*
	* @return the class pk of this commerce vat number
	*/
	@Override
	public long getClassPK() {
		return _commerceVatNumber.getClassPK();
	}

	/**
	* Returns the commerce vat number ID of this commerce vat number.
	*
	* @return the commerce vat number ID of this commerce vat number
	*/
	@Override
	public long getCommerceVatNumberId() {
		return _commerceVatNumber.getCommerceVatNumberId();
	}

	/**
	* Returns the company ID of this commerce vat number.
	*
	* @return the company ID of this commerce vat number
	*/
	@Override
	public long getCompanyId() {
		return _commerceVatNumber.getCompanyId();
	}

	/**
	* Returns the create date of this commerce vat number.
	*
	* @return the create date of this commerce vat number
	*/
	@Override
	public Date getCreateDate() {
		return _commerceVatNumber.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceVatNumber.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce vat number.
	*
	* @return the group ID of this commerce vat number
	*/
	@Override
	public long getGroupId() {
		return _commerceVatNumber.getGroupId();
	}

	/**
	* Returns the modified date of this commerce vat number.
	*
	* @return the modified date of this commerce vat number
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceVatNumber.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce vat number.
	*
	* @return the primary key of this commerce vat number
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceVatNumber.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceVatNumber.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this commerce vat number.
	*
	* @return the user ID of this commerce vat number
	*/
	@Override
	public long getUserId() {
		return _commerceVatNumber.getUserId();
	}

	/**
	* Returns the user name of this commerce vat number.
	*
	* @return the user name of this commerce vat number
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceVatNumber.getUserName();
	}

	/**
	* Returns the user uuid of this commerce vat number.
	*
	* @return the user uuid of this commerce vat number
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceVatNumber.getUserUuid();
	}

	/**
	* Returns the valid of this commerce vat number.
	*
	* @return the valid of this commerce vat number
	*/
	@Override
	public boolean getValid() {
		return _commerceVatNumber.getValid();
	}

	/**
	* Returns the vat number of this commerce vat number.
	*
	* @return the vat number of this commerce vat number
	*/
	@Override
	public java.lang.String getVatNumber() {
		return _commerceVatNumber.getVatNumber();
	}

	@Override
	public int hashCode() {
		return _commerceVatNumber.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceVatNumber.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceVatNumber.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceVatNumber.isNew();
	}

	/**
	* Returns <code>true</code> if this commerce vat number is valid.
	*
	* @return <code>true</code> if this commerce vat number is valid; <code>false</code> otherwise
	*/
	@Override
	public boolean isValid() {
		return _commerceVatNumber.isValid();
	}

	@Override
	public void persist() {
		_commerceVatNumber.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceVatNumber.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_commerceVatNumber.setClassName(className);
	}

	/**
	* Sets the class name ID of this commerce vat number.
	*
	* @param classNameId the class name ID of this commerce vat number
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_commerceVatNumber.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this commerce vat number.
	*
	* @param classPK the class pk of this commerce vat number
	*/
	@Override
	public void setClassPK(long classPK) {
		_commerceVatNumber.setClassPK(classPK);
	}

	/**
	* Sets the commerce vat number ID of this commerce vat number.
	*
	* @param commerceVatNumberId the commerce vat number ID of this commerce vat number
	*/
	@Override
	public void setCommerceVatNumberId(long commerceVatNumberId) {
		_commerceVatNumber.setCommerceVatNumberId(commerceVatNumberId);
	}

	/**
	* Sets the company ID of this commerce vat number.
	*
	* @param companyId the company ID of this commerce vat number
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceVatNumber.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce vat number.
	*
	* @param createDate the create date of this commerce vat number
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceVatNumber.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceVatNumber.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceVatNumber.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceVatNumber.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce vat number.
	*
	* @param groupId the group ID of this commerce vat number
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceVatNumber.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce vat number.
	*
	* @param modifiedDate the modified date of this commerce vat number
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceVatNumber.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceVatNumber.setNew(n);
	}

	/**
	* Sets the primary key of this commerce vat number.
	*
	* @param primaryKey the primary key of this commerce vat number
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceVatNumber.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceVatNumber.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this commerce vat number.
	*
	* @param userId the user ID of this commerce vat number
	*/
	@Override
	public void setUserId(long userId) {
		_commerceVatNumber.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce vat number.
	*
	* @param userName the user name of this commerce vat number
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceVatNumber.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce vat number.
	*
	* @param userUuid the user uuid of this commerce vat number
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceVatNumber.setUserUuid(userUuid);
	}

	/**
	* Sets whether this commerce vat number is valid.
	*
	* @param valid the valid of this commerce vat number
	*/
	@Override
	public void setValid(boolean valid) {
		_commerceVatNumber.setValid(valid);
	}

	/**
	* Sets the vat number of this commerce vat number.
	*
	* @param vatNumber the vat number of this commerce vat number
	*/
	@Override
	public void setVatNumber(java.lang.String vatNumber) {
		_commerceVatNumber.setVatNumber(vatNumber);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceVatNumber> toCacheModel() {
		return _commerceVatNumber.toCacheModel();
	}

	@Override
	public CommerceVatNumber toEscapedModel() {
		return new CommerceVatNumberWrapper(_commerceVatNumber.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceVatNumber.toString();
	}

	@Override
	public CommerceVatNumber toUnescapedModel() {
		return new CommerceVatNumberWrapper(_commerceVatNumber.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceVatNumber.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceVatNumberWrapper)) {
			return false;
		}

		CommerceVatNumberWrapper commerceVatNumberWrapper = (CommerceVatNumberWrapper)obj;

		if (Objects.equals(_commerceVatNumber,
					commerceVatNumberWrapper._commerceVatNumber)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceVatNumber getWrappedModel() {
		return _commerceVatNumber;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceVatNumber.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceVatNumber.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceVatNumber.resetOriginalValues();
	}

	private final CommerceVatNumber _commerceVatNumber;
}