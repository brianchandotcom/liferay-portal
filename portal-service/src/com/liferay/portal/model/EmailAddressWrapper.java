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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

import com.liferay.portlet.exportimport.lar.StagedModelType;

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
@ProviderType
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

		attributes.put("mvccVersion", getMvccVersion());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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

		Long typeId = (Long)attributes.get("typeId");

		if (typeId != null) {
			setTypeId(typeId);
		}

		Boolean primary = (Boolean)attributes.get("primary");

		if (primary != null) {
			setPrimary(primary);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new EmailAddressWrapper((EmailAddress)_emailAddress.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.EmailAddress emailAddress) {
		return _emailAddress.compareTo(emailAddress);
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
	* Returns the fully qualified class name of this email address.
	*
	* @return the fully qualified class name of this email address
	*/
	@Override
	public java.lang.String getClassName() {
		return _emailAddress.getClassName();
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
	* Returns the class p k of this email address.
	*
	* @return the class p k of this email address
	*/
	@Override
	public long getClassPK() {
		return _emailAddress.getClassPK();
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
	* Returns the create date of this email address.
	*
	* @return the create date of this email address
	*/
	@Override
	public Date getCreateDate() {
		return _emailAddress.getCreateDate();
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

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _emailAddress.getExpandoBridge();
	}

	/**
	* Returns the modified date of this email address.
	*
	* @return the modified date of this email address
	*/
	@Override
	public Date getModifiedDate() {
		return _emailAddress.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this email address.
	*
	* @return the mvcc version of this email address
	*/
	@Override
	public long getMvccVersion() {
		return _emailAddress.getMvccVersion();
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
	* Returns the primary key of this email address.
	*
	* @return the primary key of this email address
	*/
	@Override
	public long getPrimaryKey() {
		return _emailAddress.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _emailAddress.getPrimaryKeyObj();
	}

	@Override
	public com.liferay.portal.model.ListType getType()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _emailAddress.getType();
	}

	/**
	* Returns the type ID of this email address.
	*
	* @return the type ID of this email address
	*/
	@Override
	public long getTypeId() {
		return _emailAddress.getTypeId();
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
	* Returns the user name of this email address.
	*
	* @return the user name of this email address
	*/
	@Override
	public java.lang.String getUserName() {
		return _emailAddress.getUserName();
	}

	/**
	* Returns the user uuid of this email address.
	*
	* @return the user uuid of this email address
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _emailAddress.getUserUuid();
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

	@Override
	public int hashCode() {
		return _emailAddress.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _emailAddress.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _emailAddress.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _emailAddress.isNew();
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

	@Override
	public void persist() {
		_emailAddress.persist();
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

	@Override
	public void setCachedModel(boolean cachedModel) {
		_emailAddress.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_emailAddress.setClassName(className);
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
	* Sets the class p k of this email address.
	*
	* @param classPK the class p k of this email address
	*/
	@Override
	public void setClassPK(long classPK) {
		_emailAddress.setClassPK(classPK);
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
	* Sets the create date of this email address.
	*
	* @param createDate the create date of this email address
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_emailAddress.setCreateDate(createDate);
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

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
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

	/**
	* Sets the modified date of this email address.
	*
	* @param modifiedDate the modified date of this email address
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_emailAddress.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this email address.
	*
	* @param mvccVersion the mvcc version of this email address
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_emailAddress.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_emailAddress.setNew(n);
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

	/**
	* Sets the primary key of this email address.
	*
	* @param primaryKey the primary key of this email address
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_emailAddress.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_emailAddress.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the type ID of this email address.
	*
	* @param typeId the type ID of this email address
	*/
	@Override
	public void setTypeId(long typeId) {
		_emailAddress.setTypeId(typeId);
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
	* Sets the user name of this email address.
	*
	* @param userName the user name of this email address
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_emailAddress.setUserName(userName);
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
	* Sets the uuid of this email address.
	*
	* @param uuid the uuid of this email address
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_emailAddress.setUuid(uuid);
	}

	@Override
	public CacheModel<com.liferay.portal.model.EmailAddress> toCacheModel() {
		return _emailAddress.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.EmailAddress toEscapedModel() {
		return new EmailAddressWrapper(_emailAddress.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _emailAddress.toString();
	}

	@Override
	public com.liferay.portal.model.EmailAddress toUnescapedModel() {
		return new EmailAddressWrapper(_emailAddress.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _emailAddress.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof EmailAddressWrapper)) {
			return false;
		}

		EmailAddressWrapper emailAddressWrapper = (EmailAddressWrapper)obj;

		if (Validator.equals(_emailAddress, emailAddressWrapper._emailAddress)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _emailAddress.getStagedModelType();
	}

	@Override
	public EmailAddress getWrappedModel() {
		return _emailAddress;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _emailAddress.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _emailAddress.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_emailAddress.resetOriginalValues();
	}

	private final EmailAddress _emailAddress;
}