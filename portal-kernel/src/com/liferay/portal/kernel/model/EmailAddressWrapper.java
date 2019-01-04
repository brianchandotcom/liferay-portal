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

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
public class EmailAddressWrapper extends BaseModelWrapper<EmailAddress>
	implements EmailAddress, ModelWrapper<EmailAddress> {
	public EmailAddressWrapper(EmailAddress emailAddress) {
		super(emailAddress);
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

	/**
	* Returns the address of this email address.
	*
	* @return the address of this email address
	*/
	@Override
	public String getAddress() {
		return model.getAddress();
	}

	/**
	* Returns the fully qualified class name of this email address.
	*
	* @return the fully qualified class name of this email address
	*/
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	* Returns the class name ID of this email address.
	*
	* @return the class name ID of this email address
	*/
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	* Returns the class pk of this email address.
	*
	* @return the class pk of this email address
	*/
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	* Returns the company ID of this email address.
	*
	* @return the company ID of this email address
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this email address.
	*
	* @return the create date of this email address
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the email address ID of this email address.
	*
	* @return the email address ID of this email address
	*/
	@Override
	public long getEmailAddressId() {
		return model.getEmailAddressId();
	}

	/**
	* Returns the modified date of this email address.
	*
	* @return the modified date of this email address
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this email address.
	*
	* @return the mvcc version of this email address
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the primary of this email address.
	*
	* @return the primary of this email address
	*/
	@Override
	public boolean getPrimary() {
		return model.getPrimary();
	}

	/**
	* Returns the primary key of this email address.
	*
	* @return the primary key of this email address
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
	* Returns the type ID of this email address.
	*
	* @return the type ID of this email address
	*/
	@Override
	public long getTypeId() {
		return model.getTypeId();
	}

	/**
	* Returns the user ID of this email address.
	*
	* @return the user ID of this email address
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this email address.
	*
	* @return the user name of this email address
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this email address.
	*
	* @return the user uuid of this email address
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the uuid of this email address.
	*
	* @return the uuid of this email address
	*/
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	* Returns <code>true</code> if this email address is primary.
	*
	* @return <code>true</code> if this email address is primary; <code>false</code> otherwise
	*/
	@Override
	public boolean isPrimary() {
		return model.isPrimary();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the address of this email address.
	*
	* @param address the address of this email address
	*/
	@Override
	public void setAddress(String address) {
		model.setAddress(address);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	* Sets the class name ID of this email address.
	*
	* @param classNameId the class name ID of this email address
	*/
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this email address.
	*
	* @param classPK the class pk of this email address
	*/
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this email address.
	*
	* @param companyId the company ID of this email address
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this email address.
	*
	* @param createDate the create date of this email address
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the email address ID of this email address.
	*
	* @param emailAddressId the email address ID of this email address
	*/
	@Override
	public void setEmailAddressId(long emailAddressId) {
		model.setEmailAddressId(emailAddressId);
	}

	/**
	* Sets the modified date of this email address.
	*
	* @param modifiedDate the modified date of this email address
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this email address.
	*
	* @param mvccVersion the mvcc version of this email address
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets whether this email address is primary.
	*
	* @param primary the primary of this email address
	*/
	@Override
	public void setPrimary(boolean primary) {
		model.setPrimary(primary);
	}

	/**
	* Sets the primary key of this email address.
	*
	* @param primaryKey the primary key of this email address
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the type ID of this email address.
	*
	* @param typeId the type ID of this email address
	*/
	@Override
	public void setTypeId(long typeId) {
		model.setTypeId(typeId);
	}

	/**
	* Sets the user ID of this email address.
	*
	* @param userId the user ID of this email address
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this email address.
	*
	* @param userName the user name of this email address
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this email address.
	*
	* @param userUuid the user uuid of this email address
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this email address.
	*
	* @param uuid the uuid of this email address
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
	protected EmailAddressWrapper wrap(EmailAddress emailAddress) {
		return new EmailAddressWrapper(emailAddress);
	}
}