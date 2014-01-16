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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Phone}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Phone
 * @generated
 */
@ProviderType
public class PhoneWrapper implements Phone, ModelWrapper<Phone> {
	public PhoneWrapper(Phone phone) {
		_phone = phone;
	}

	@Override
	public Class<?> getModelClass() {
		return Phone.class;
	}

	@Override
	public String getModelClassName() {
		return Phone.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("phoneId", getPhoneId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("number", getNumber());
		attributes.put("extension", getExtension());
		attributes.put("typeId", getTypeId());
		attributes.put("primary", getPrimary());
		attributes.put("mvccVersion", getMvccVersion());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long phoneId = (Long)attributes.get("phoneId");

		if (phoneId != null) {
			setPhoneId(phoneId);
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

		String number = (String)attributes.get("number");

		if (number != null) {
			setNumber(number);
		}

		String extension = (String)attributes.get("extension");

		if (extension != null) {
			setExtension(extension);
		}

		Integer typeId = (Integer)attributes.get("typeId");

		if (typeId != null) {
			setTypeId(typeId);
		}

		Boolean primary = (Boolean)attributes.get("primary");

		if (primary != null) {
			setPrimary(primary);
		}

		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}
	}

	/**
	* Returns the primary key of this phone.
	*
	* @return the primary key of this phone
	*/
	@Override
	public long getPrimaryKey() {
		return _phone.getPrimaryKey();
	}

	/**
	* Sets the primary key of this phone.
	*
	* @param primaryKey the primary key of this phone
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_phone.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this phone.
	*
	* @return the uuid of this phone
	*/
	@Override
	public java.lang.String getUuid() {
		return _phone.getUuid();
	}

	/**
	* Sets the uuid of this phone.
	*
	* @param uuid the uuid of this phone
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_phone.setUuid(uuid);
	}

	/**
	* Returns the phone ID of this phone.
	*
	* @return the phone ID of this phone
	*/
	@Override
	public long getPhoneId() {
		return _phone.getPhoneId();
	}

	/**
	* Sets the phone ID of this phone.
	*
	* @param phoneId the phone ID of this phone
	*/
	@Override
	public void setPhoneId(long phoneId) {
		_phone.setPhoneId(phoneId);
	}

	/**
	* Returns the company ID of this phone.
	*
	* @return the company ID of this phone
	*/
	@Override
	public long getCompanyId() {
		return _phone.getCompanyId();
	}

	/**
	* Sets the company ID of this phone.
	*
	* @param companyId the company ID of this phone
	*/
	@Override
	public void setCompanyId(long companyId) {
		_phone.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this phone.
	*
	* @return the user ID of this phone
	*/
	@Override
	public long getUserId() {
		return _phone.getUserId();
	}

	/**
	* Sets the user ID of this phone.
	*
	* @param userId the user ID of this phone
	*/
	@Override
	public void setUserId(long userId) {
		_phone.setUserId(userId);
	}

	/**
	* Returns the user uuid of this phone.
	*
	* @return the user uuid of this phone
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _phone.getUserUuid();
	}

	/**
	* Sets the user uuid of this phone.
	*
	* @param userUuid the user uuid of this phone
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_phone.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this phone.
	*
	* @return the user name of this phone
	*/
	@Override
	public java.lang.String getUserName() {
		return _phone.getUserName();
	}

	/**
	* Sets the user name of this phone.
	*
	* @param userName the user name of this phone
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_phone.setUserName(userName);
	}

	/**
	* Returns the create date of this phone.
	*
	* @return the create date of this phone
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _phone.getCreateDate();
	}

	/**
	* Sets the create date of this phone.
	*
	* @param createDate the create date of this phone
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_phone.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this phone.
	*
	* @return the modified date of this phone
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _phone.getModifiedDate();
	}

	/**
	* Sets the modified date of this phone.
	*
	* @param modifiedDate the modified date of this phone
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_phone.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the fully qualified class name of this phone.
	*
	* @return the fully qualified class name of this phone
	*/
	@Override
	public java.lang.String getClassName() {
		return _phone.getClassName();
	}

	@Override
	public void setClassName(java.lang.String className) {
		_phone.setClassName(className);
	}

	/**
	* Returns the class name ID of this phone.
	*
	* @return the class name ID of this phone
	*/
	@Override
	public long getClassNameId() {
		return _phone.getClassNameId();
	}

	/**
	* Sets the class name ID of this phone.
	*
	* @param classNameId the class name ID of this phone
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_phone.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this phone.
	*
	* @return the class p k of this phone
	*/
	@Override
	public long getClassPK() {
		return _phone.getClassPK();
	}

	/**
	* Sets the class p k of this phone.
	*
	* @param classPK the class p k of this phone
	*/
	@Override
	public void setClassPK(long classPK) {
		_phone.setClassPK(classPK);
	}

	/**
	* Returns the number of this phone.
	*
	* @return the number of this phone
	*/
	@Override
	public java.lang.String getNumber() {
		return _phone.getNumber();
	}

	/**
	* Sets the number of this phone.
	*
	* @param number the number of this phone
	*/
	@Override
	public void setNumber(java.lang.String number) {
		_phone.setNumber(number);
	}

	/**
	* Returns the extension of this phone.
	*
	* @return the extension of this phone
	*/
	@Override
	public java.lang.String getExtension() {
		return _phone.getExtension();
	}

	/**
	* Sets the extension of this phone.
	*
	* @param extension the extension of this phone
	*/
	@Override
	public void setExtension(java.lang.String extension) {
		_phone.setExtension(extension);
	}

	/**
	* Returns the type ID of this phone.
	*
	* @return the type ID of this phone
	*/
	@Override
	public int getTypeId() {
		return _phone.getTypeId();
	}

	/**
	* Sets the type ID of this phone.
	*
	* @param typeId the type ID of this phone
	*/
	@Override
	public void setTypeId(int typeId) {
		_phone.setTypeId(typeId);
	}

	/**
	* Returns the primary of this phone.
	*
	* @return the primary of this phone
	*/
	@Override
	public boolean getPrimary() {
		return _phone.getPrimary();
	}

	/**
	* Returns <code>true</code> if this phone is primary.
	*
	* @return <code>true</code> if this phone is primary; <code>false</code> otherwise
	*/
	@Override
	public boolean isPrimary() {
		return _phone.isPrimary();
	}

	/**
	* Sets whether this phone is primary.
	*
	* @param primary the primary of this phone
	*/
	@Override
	public void setPrimary(boolean primary) {
		_phone.setPrimary(primary);
	}

	/**
	* Returns the mvcc version of this phone.
	*
	* @return the mvcc version of this phone
	*/
	@Override
	public long getMvccVersion() {
		return _phone.getMvccVersion();
	}

	/**
	* Sets the mvcc version of this phone.
	*
	* @param mvccVersion the mvcc version of this phone
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_phone.setMvccVersion(mvccVersion);
	}

	@Override
	public boolean isNew() {
		return _phone.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_phone.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _phone.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_phone.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _phone.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _phone.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_phone.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _phone.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_phone.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_phone.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_phone.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new PhoneWrapper((Phone)_phone.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.Phone phone) {
		return _phone.compareTo(phone);
	}

	@Override
	public int hashCode() {
		return _phone.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.Phone> toCacheModel() {
		return _phone.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.Phone toEscapedModel() {
		return new PhoneWrapper(_phone.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.Phone toUnescapedModel() {
		return new PhoneWrapper(_phone.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _phone.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _phone.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_phone.persist();
	}

	@Override
	public com.liferay.portal.model.ListType getType()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _phone.getType();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PhoneWrapper)) {
			return false;
		}

		PhoneWrapper phoneWrapper = (PhoneWrapper)obj;

		if (Validator.equals(_phone, phoneWrapper._phone)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _phone.getStagedModelType();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public Phone getWrappedPhone() {
		return _phone;
	}

	@Override
	public Phone getWrappedModel() {
		return _phone;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _phone.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _phone.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_phone.resetOriginalValues();
	}

	private Phone _phone;
}