/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link EmailAddress}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see EmailAddress
 * @generated
 */
public class EmailAddressWrapper implements EmailAddress,
	ModelWrapper<EmailAddress> {
	public EmailAddressWrapper(EmailAddress emailAddress) {
		_emailAddress = emailAddress;
	}

	@Override
	public Class<?> getModelClass() {
		return EmailAddress.class;
	}

	@Override
	public String getModelClassName() {
		return EmailAddress.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("emailAddressId", getEmailAddressId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("address", getAddress());
		attributes.put("typeId", getTypeId());
		attributes.put("primary", getPrimary());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long emailAddressId = (Long)attributes.get("emailAddressId");

		if (emailAddressId != null) {
			setEmailAddressId(emailAddressId);
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

		String address = (String)attributes.get("address");

		if (address != null) {
			setAddress(address);
		}

		Integer typeId = (Integer)attributes.get("typeId");

		if (typeId != null) {
			setTypeId(typeId);
		}

		Boolean primary = (Boolean)attributes.get("primary");

		if (primary != null) {
			setPrimary(primary);
		}
	}

	/**
	* Returns the primary key of this email address.
	*
	* @return the primary key of this email address
	*/
	@Override
	public long getPrimaryKey() {
		return _emailAddress.getPrimaryKey();
	}

	/**
	* Sets the primary key of this email address.
	*
	* @param primaryKey the primary key of this email address
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_emailAddress.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this email address.
	*
	* @return the uuid of this email address
	*/
	@Override
	public java.lang.String getUuid() {
		return _emailAddress.getUuid();
	}

	/**
	* Sets the uuid of this email address.
	*
	* @param uuid the uuid of this email address
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_emailAddress.setUuid(uuid);
	}

	/**
	* Returns the email address ID of this email address.
	*
	* @return the email address ID of this email address
	*/
	@Override
	public long getEmailAddressId() {
		return _emailAddress.getEmailAddressId();
	}

	/**
	* Sets the email address ID of this email address.
	*
	* @param emailAddressId the email address ID of this email address
	*/
	@Override
	public void setEmailAddressId(long emailAddressId) {
		_emailAddress.setEmailAddressId(emailAddressId);
	}

	/**
	* Returns the company ID of this email address.
	*
	* @return the company ID of this email address
	*/
	@Override
	public long getCompanyId() {
		return _emailAddress.getCompanyId();
	}

	/**
	* Sets the company ID of this email address.
	*
	* @param companyId the company ID of this email address
	*/
	@Override
	public void setCompanyId(long companyId) {
		_emailAddress.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this email address.
	*
	* @return the user ID of this email address
	*/
	@Override
	public long getUserId() {
		return _emailAddress.getUserId();
	}

	/**
	* Sets the user ID of this email address.
	*
	* @param userId the user ID of this email address
	*/
	@Override
	public void setUserId(long userId) {
		_emailAddress.setUserId(userId);
	}

	/**
	* Returns the user uuid of this email address.
	*
	* @return the user uuid of this email address
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _emailAddress.getUserUuid();
	}

	/**
	* Sets the user uuid of this email address.
	*
	* @param userUuid the user uuid of this email address
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_emailAddress.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this email address.
	*
	* @return the user name of this email address
	*/
	@Override
	public java.lang.String getUserName() {
		return _emailAddress.getUserName();
	}

	/**
	* Sets the user name of this email address.
	*
	* @param userName the user name of this email address
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_emailAddress.setUserName(userName);
	}

	/**
	* Returns the create date of this email address.
	*
	* @return the create date of this email address
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _emailAddress.getCreateDate();
	}

	/**
	* Sets the create date of this email address.
	*
	* @param createDate the create date of this email address
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_emailAddress.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this email address.
	*
	* @return the modified date of this email address
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _emailAddress.getModifiedDate();
	}

	/**
	* Sets the modified date of this email address.
	*
	* @param modifiedDate the modified date of this email address
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_emailAddress.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the fully qualified class name of this email address.
	*
	* @return the fully qualified class name of this email address
	*/
	@Override
	public java.lang.String getClassName() {
		return _emailAddress.getClassName();
	}

	@Override
	public void setClassName(java.lang.String className) {
		_emailAddress.setClassName(className);
	}

	/**
	* Returns the class name ID of this email address.
	*
	* @return the class name ID of this email address
	*/
	@Override
	public long getClassNameId() {
		return _emailAddress.getClassNameId();
	}

	/**
	* Sets the class name ID of this email address.
	*
	* @param classNameId the class name ID of this email address
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_emailAddress.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this email address.
	*
	* @return the class p k of this email address
	*/
	@Override
	public long getClassPK() {
		return _emailAddress.getClassPK();
	}

	/**
	* Sets the class p k of this email address.
	*
	* @param classPK the class p k of this email address
	*/
	@Override
	public void setClassPK(long classPK) {
		_emailAddress.setClassPK(classPK);
	}

	/**
	* Returns the address of this email address.
	*
	* @return the address of this email address
	*/
	@Override
	public java.lang.String getAddress() {
		return _emailAddress.getAddress();
	}

	/**
	* Sets the address of this email address.
	*
	* @param address the address of this email address
	*/
	@Override
	public void setAddress(java.lang.String address) {
		_emailAddress.setAddress(address);
	}

	/**
	* Returns the type ID of this email address.
	*
	* @return the type ID of this email address
	*/
	@Override
	public int getTypeId() {
		return _emailAddress.getTypeId();
	}

	/**
	* Sets the type ID of this email address.
	*
	* @param typeId the type ID of this email address
	*/
	@Override
	public void setTypeId(int typeId) {
		_emailAddress.setTypeId(typeId);
	}

	/**
	* Returns the primary of this email address.
	*
	* @return the primary of this email address
	*/
	@Override
	public boolean getPrimary() {
		return _emailAddress.getPrimary();
	}

	/**
	* Returns <code>true</code> if this email address is primary.
	*
	* @return <code>true</code> if this email address is primary; <code>false</code> otherwise
	*/
	@Override
	public boolean isPrimary() {
		return _emailAddress.isPrimary();
	}

	/**
	* Sets whether this email address is primary.
	*
	* @param primary the primary of this email address
	*/
	@Override
	public void setPrimary(boolean primary) {
		_emailAddress.setPrimary(primary);
	}

	@Override
	public boolean isNew() {
		return _emailAddress.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_emailAddress.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _emailAddress.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_emailAddress.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _emailAddress.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _emailAddress.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_emailAddress.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _emailAddress.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_emailAddress.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_emailAddress.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_emailAddress.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new EmailAddressWrapper((EmailAddress)_emailAddress.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.EmailAddress emailAddress) {
		return _emailAddress.compareTo(emailAddress);
	}

	@Override
	public int hashCode() {
		return _emailAddress.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.EmailAddress> toCacheModel() {
		return _emailAddress.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.EmailAddress toEscapedModel() {
		return new EmailAddressWrapper(_emailAddress.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.EmailAddress toUnescapedModel() {
		return new EmailAddressWrapper(_emailAddress.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _emailAddress.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _emailAddress.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_emailAddress.persist();
	}

	@Override
	public com.liferay.portal.model.ListType getType()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _emailAddress.getType();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public EmailAddress getWrappedEmailAddress() {
		return _emailAddress;
	}

	@Override
	public EmailAddress getWrappedModel() {
		return _emailAddress;
	}

	@Override
	public void resetOriginalValues() {
		_emailAddress.resetOriginalValues();
	}

	private EmailAddress _emailAddress;
}