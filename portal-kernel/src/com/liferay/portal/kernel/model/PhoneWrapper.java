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

package com.liferay.portal.kernel.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
public class PhoneWrapper
	extends BaseModelWrapper<Phone> implements ModelWrapper<Phone>, Phone {

	public PhoneWrapper(Phone phone) {
		super(phone);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
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
		attributes.put("primary", isPrimary());

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

		Long typeId = (Long)attributes.get("typeId");

		if (typeId != null) {
			setTypeId(typeId);
		}

		Boolean primary = (Boolean)attributes.get("primary");

		if (primary != null) {
			setPrimary(primary);
		}
	}

	/**
	 * Returns the fully qualified class name of this phone.
	 *
	 * @return the fully qualified class name of this phone
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this phone.
	 *
	 * @return the class name ID of this phone
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this phone.
	 *
	 * @return the class pk of this phone
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this phone.
	 *
	 * @return the company ID of this phone
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this phone.
	 *
	 * @return the create date of this phone
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the extension of this phone.
	 *
	 * @return the extension of this phone
	 */
	@Override
	public String getExtension() {
		return model.getExtension();
	}

	/**
	 * Returns the modified date of this phone.
	 *
	 * @return the modified date of this phone
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this phone.
	 *
	 * @return the mvcc version of this phone
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the number of this phone.
	 *
	 * @return the number of this phone
	 */
	@Override
	public String getNumber() {
		return model.getNumber();
	}

	/**
	 * Returns the phone ID of this phone.
	 *
	 * @return the phone ID of this phone
	 */
	@Override
	public long getPhoneId() {
		return model.getPhoneId();
	}

	/**
	 * Returns the primary of this phone.
	 *
	 * @return the primary of this phone
	 */
	@Override
	public boolean getPrimary() {
		return model.getPrimary();
	}

	/**
	 * Returns the primary key of this phone.
	 *
	 * @return the primary key of this phone
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public ListType getType()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getType();
	}

	/**
	 * Returns the type ID of this phone.
	 *
	 * @return the type ID of this phone
	 */
	@Override
	public long getTypeId() {
		return model.getTypeId();
	}

	/**
	 * Returns the user ID of this phone.
	 *
	 * @return the user ID of this phone
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this phone.
	 *
	 * @return the user name of this phone
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this phone.
	 *
	 * @return the user uuid of this phone
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this phone.
	 *
	 * @return the uuid of this phone
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this phone is primary.
	 *
	 * @return <code>true</code> if this phone is primary; <code>false</code> otherwise
	 */
	@Override
	public boolean isPrimary() {
		return model.isPrimary();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a phone model instance should use the <code>Phone</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this phone.
	 *
	 * @param classNameId the class name ID of this phone
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this phone.
	 *
	 * @param classPK the class pk of this phone
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this phone.
	 *
	 * @param companyId the company ID of this phone
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this phone.
	 *
	 * @param createDate the create date of this phone
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the extension of this phone.
	 *
	 * @param extension the extension of this phone
	 */
	@Override
	public void setExtension(String extension) {
		model.setExtension(extension);
	}

	/**
	 * Sets the modified date of this phone.
	 *
	 * @param modifiedDate the modified date of this phone
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this phone.
	 *
	 * @param mvccVersion the mvcc version of this phone
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the number of this phone.
	 *
	 * @param number the number of this phone
	 */
	@Override
	public void setNumber(String number) {
		model.setNumber(number);
	}

	/**
	 * Sets the phone ID of this phone.
	 *
	 * @param phoneId the phone ID of this phone
	 */
	@Override
	public void setPhoneId(long phoneId) {
		model.setPhoneId(phoneId);
	}

	/**
	 * Sets whether this phone is primary.
	 *
	 * @param primary the primary of this phone
	 */
	@Override
	public void setPrimary(boolean primary) {
		model.setPrimary(primary);
	}

	/**
	 * Sets the primary key of this phone.
	 *
	 * @param primaryKey the primary key of this phone
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the type ID of this phone.
	 *
	 * @param typeId the type ID of this phone
	 */
	@Override
	public void setTypeId(long typeId) {
		model.setTypeId(typeId);
	}

	/**
	 * Sets the user ID of this phone.
	 *
	 * @param userId the user ID of this phone
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this phone.
	 *
	 * @param userName the user name of this phone
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this phone.
	 *
	 * @param userUuid the user uuid of this phone
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this phone.
	 *
	 * @param uuid the uuid of this phone
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected PhoneWrapper wrap(Phone phone) {
		return new PhoneWrapper(phone);
	}

}