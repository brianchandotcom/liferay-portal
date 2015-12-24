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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Contact}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Contact
 * @generated
 */
@ProviderType
public class ContactWrapper implements Contact, ModelWrapper<Contact> {
	public ContactWrapper(Contact contact) {
		_contact = contact;
	}

	@Override
	public Class<?> getModelClass() {
		return Contact.class;
	}

	@Override
	public String getModelClassName() {
		return Contact.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("contactId", getContactId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("accountId", getAccountId());
		attributes.put("parentContactId", getParentContactId());
		attributes.put("emailAddress", getEmailAddress());
		attributes.put("firstName", getFirstName());
		attributes.put("middleName", getMiddleName());
		attributes.put("lastName", getLastName());
		attributes.put("prefixId", getPrefixId());
		attributes.put("suffixId", getSuffixId());
		attributes.put("male", getMale());
		attributes.put("birthday", getBirthday());
		attributes.put("smsSn", getSmsSn());
		attributes.put("facebookSn", getFacebookSn());
		attributes.put("jabberSn", getJabberSn());
		attributes.put("skypeSn", getSkypeSn());
		attributes.put("twitterSn", getTwitterSn());
		attributes.put("employeeStatusId", getEmployeeStatusId());
		attributes.put("employeeNumber", getEmployeeNumber());
		attributes.put("jobTitle", getJobTitle());
		attributes.put("jobClass", getJobClass());
		attributes.put("hoursOfOperation", getHoursOfOperation());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long contactId = (Long)attributes.get("contactId");

		if (contactId != null) {
			setContactId(contactId);
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

		Long accountId = (Long)attributes.get("accountId");

		if (accountId != null) {
			setAccountId(accountId);
		}

		Long parentContactId = (Long)attributes.get("parentContactId");

		if (parentContactId != null) {
			setParentContactId(parentContactId);
		}

		String emailAddress = (String)attributes.get("emailAddress");

		if (emailAddress != null) {
			setEmailAddress(emailAddress);
		}

		String firstName = (String)attributes.get("firstName");

		if (firstName != null) {
			setFirstName(firstName);
		}

		String middleName = (String)attributes.get("middleName");

		if (middleName != null) {
			setMiddleName(middleName);
		}

		String lastName = (String)attributes.get("lastName");

		if (lastName != null) {
			setLastName(lastName);
		}

		Long prefixId = (Long)attributes.get("prefixId");

		if (prefixId != null) {
			setPrefixId(prefixId);
		}

		Long suffixId = (Long)attributes.get("suffixId");

		if (suffixId != null) {
			setSuffixId(suffixId);
		}

		Boolean male = (Boolean)attributes.get("male");

		if (male != null) {
			setMale(male);
		}

		Date birthday = (Date)attributes.get("birthday");

		if (birthday != null) {
			setBirthday(birthday);
		}

		String smsSn = (String)attributes.get("smsSn");

		if (smsSn != null) {
			setSmsSn(smsSn);
		}

		String facebookSn = (String)attributes.get("facebookSn");

		if (facebookSn != null) {
			setFacebookSn(facebookSn);
		}

		String jabberSn = (String)attributes.get("jabberSn");

		if (jabberSn != null) {
			setJabberSn(jabberSn);
		}

		String skypeSn = (String)attributes.get("skypeSn");

		if (skypeSn != null) {
			setSkypeSn(skypeSn);
		}

		String twitterSn = (String)attributes.get("twitterSn");

		if (twitterSn != null) {
			setTwitterSn(twitterSn);
		}

		String employeeStatusId = (String)attributes.get("employeeStatusId");

		if (employeeStatusId != null) {
			setEmployeeStatusId(employeeStatusId);
		}

		String employeeNumber = (String)attributes.get("employeeNumber");

		if (employeeNumber != null) {
			setEmployeeNumber(employeeNumber);
		}

		String jobTitle = (String)attributes.get("jobTitle");

		if (jobTitle != null) {
			setJobTitle(jobTitle);
		}

		String jobClass = (String)attributes.get("jobClass");

		if (jobClass != null) {
			setJobClass(jobClass);
		}

		String hoursOfOperation = (String)attributes.get("hoursOfOperation");

		if (hoursOfOperation != null) {
			setHoursOfOperation(hoursOfOperation);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new ContactWrapper((Contact)_contact.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.Contact contact) {
		return _contact.compareTo(contact);
	}

	/**
	* Returns the account ID of this contact.
	*
	* @return the account ID of this contact
	*/
	@Override
	public long getAccountId() {
		return _contact.getAccountId();
	}

	/**
	* Returns the birthday of this contact.
	*
	* @return the birthday of this contact
	*/
	@Override
	public Date getBirthday() {
		return _contact.getBirthday();
	}

	/**
	* Returns the fully qualified class name of this contact.
	*
	* @return the fully qualified class name of this contact
	*/
	@Override
	public java.lang.String getClassName() {
		return _contact.getClassName();
	}

	/**
	* Returns the class name ID of this contact.
	*
	* @return the class name ID of this contact
	*/
	@Override
	public long getClassNameId() {
		return _contact.getClassNameId();
	}

	/**
	* Returns the class p k of this contact.
	*
	* @return the class p k of this contact
	*/
	@Override
	public long getClassPK() {
		return _contact.getClassPK();
	}

	/**
	* Returns the company ID of this contact.
	*
	* @return the company ID of this contact
	*/
	@Override
	public long getCompanyId() {
		return _contact.getCompanyId();
	}

	/**
	* Returns the contact ID of this contact.
	*
	* @return the contact ID of this contact
	*/
	@Override
	public long getContactId() {
		return _contact.getContactId();
	}

	/**
	* Returns the create date of this contact.
	*
	* @return the create date of this contact
	*/
	@Override
	public Date getCreateDate() {
		return _contact.getCreateDate();
	}

	/**
	* Returns the email address of this contact.
	*
	* @return the email address of this contact
	*/
	@Override
	public java.lang.String getEmailAddress() {
		return _contact.getEmailAddress();
	}

	/**
	* Returns the employee number of this contact.
	*
	* @return the employee number of this contact
	*/
	@Override
	public java.lang.String getEmployeeNumber() {
		return _contact.getEmployeeNumber();
	}

	/**
	* Returns the employee status ID of this contact.
	*
	* @return the employee status ID of this contact
	*/
	@Override
	public java.lang.String getEmployeeStatusId() {
		return _contact.getEmployeeStatusId();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _contact.getExpandoBridge();
	}

	/**
	* Returns the facebook sn of this contact.
	*
	* @return the facebook sn of this contact
	*/
	@Override
	public java.lang.String getFacebookSn() {
		return _contact.getFacebookSn();
	}

	/**
	* Returns the first name of this contact.
	*
	* @return the first name of this contact
	*/
	@Override
	public java.lang.String getFirstName() {
		return _contact.getFirstName();
	}

	@Override
	public java.lang.String getFullName() {
		return _contact.getFullName();
	}

	/**
	* Returns the hours of operation of this contact.
	*
	* @return the hours of operation of this contact
	*/
	@Override
	public java.lang.String getHoursOfOperation() {
		return _contact.getHoursOfOperation();
	}

	/**
	* Returns the jabber sn of this contact.
	*
	* @return the jabber sn of this contact
	*/
	@Override
	public java.lang.String getJabberSn() {
		return _contact.getJabberSn();
	}

	/**
	* Returns the job class of this contact.
	*
	* @return the job class of this contact
	*/
	@Override
	public java.lang.String getJobClass() {
		return _contact.getJobClass();
	}

	/**
	* Returns the job title of this contact.
	*
	* @return the job title of this contact
	*/
	@Override
	public java.lang.String getJobTitle() {
		return _contact.getJobTitle();
	}

	/**
	* Returns the last name of this contact.
	*
	* @return the last name of this contact
	*/
	@Override
	public java.lang.String getLastName() {
		return _contact.getLastName();
	}

	/**
	* Returns the male of this contact.
	*
	* @return the male of this contact
	*/
	@Override
	public boolean getMale() {
		return _contact.getMale();
	}

	/**
	* Returns the middle name of this contact.
	*
	* @return the middle name of this contact
	*/
	@Override
	public java.lang.String getMiddleName() {
		return _contact.getMiddleName();
	}

	/**
	* Returns the modified date of this contact.
	*
	* @return the modified date of this contact
	*/
	@Override
	public Date getModifiedDate() {
		return _contact.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this contact.
	*
	* @return the mvcc version of this contact
	*/
	@Override
	public long getMvccVersion() {
		return _contact.getMvccVersion();
	}

	/**
	* Returns the parent contact ID of this contact.
	*
	* @return the parent contact ID of this contact
	*/
	@Override
	public long getParentContactId() {
		return _contact.getParentContactId();
	}

	/**
	* Returns the prefix ID of this contact.
	*
	* @return the prefix ID of this contact
	*/
	@Override
	public long getPrefixId() {
		return _contact.getPrefixId();
	}

	/**
	* Returns the primary key of this contact.
	*
	* @return the primary key of this contact
	*/
	@Override
	public long getPrimaryKey() {
		return _contact.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _contact.getPrimaryKeyObj();
	}

	/**
	* Returns the skype sn of this contact.
	*
	* @return the skype sn of this contact
	*/
	@Override
	public java.lang.String getSkypeSn() {
		return _contact.getSkypeSn();
	}

	/**
	* Returns the sms sn of this contact.
	*
	* @return the sms sn of this contact
	*/
	@Override
	public java.lang.String getSmsSn() {
		return _contact.getSmsSn();
	}

	/**
	* Returns the suffix ID of this contact.
	*
	* @return the suffix ID of this contact
	*/
	@Override
	public long getSuffixId() {
		return _contact.getSuffixId();
	}

	/**
	* Returns the twitter sn of this contact.
	*
	* @return the twitter sn of this contact
	*/
	@Override
	public java.lang.String getTwitterSn() {
		return _contact.getTwitterSn();
	}

	/**
	* Returns the user ID of this contact.
	*
	* @return the user ID of this contact
	*/
	@Override
	public long getUserId() {
		return _contact.getUserId();
	}

	/**
	* Returns the user name of this contact.
	*
	* @return the user name of this contact
	*/
	@Override
	public java.lang.String getUserName() {
		return _contact.getUserName();
	}

	/**
	* Returns the user uuid of this contact.
	*
	* @return the user uuid of this contact
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _contact.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _contact.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _contact.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _contact.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this contact is male.
	*
	* @return <code>true</code> if this contact is male; <code>false</code> otherwise
	*/
	@Override
	public boolean isMale() {
		return _contact.isMale();
	}

	@Override
	public boolean isNew() {
		return _contact.isNew();
	}

	@Override
	public boolean isUser() {
		return _contact.isUser();
	}

	@Override
	public void persist() {
		_contact.persist();
	}

	/**
	* Sets the account ID of this contact.
	*
	* @param accountId the account ID of this contact
	*/
	@Override
	public void setAccountId(long accountId) {
		_contact.setAccountId(accountId);
	}

	/**
	* Sets the birthday of this contact.
	*
	* @param birthday the birthday of this contact
	*/
	@Override
	public void setBirthday(Date birthday) {
		_contact.setBirthday(birthday);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_contact.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_contact.setClassName(className);
	}

	/**
	* Sets the class name ID of this contact.
	*
	* @param classNameId the class name ID of this contact
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_contact.setClassNameId(classNameId);
	}

	/**
	* Sets the class p k of this contact.
	*
	* @param classPK the class p k of this contact
	*/
	@Override
	public void setClassPK(long classPK) {
		_contact.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this contact.
	*
	* @param companyId the company ID of this contact
	*/
	@Override
	public void setCompanyId(long companyId) {
		_contact.setCompanyId(companyId);
	}

	/**
	* Sets the contact ID of this contact.
	*
	* @param contactId the contact ID of this contact
	*/
	@Override
	public void setContactId(long contactId) {
		_contact.setContactId(contactId);
	}

	/**
	* Sets the create date of this contact.
	*
	* @param createDate the create date of this contact
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_contact.setCreateDate(createDate);
	}

	/**
	* Sets the email address of this contact.
	*
	* @param emailAddress the email address of this contact
	*/
	@Override
	public void setEmailAddress(java.lang.String emailAddress) {
		_contact.setEmailAddress(emailAddress);
	}

	/**
	* Sets the employee number of this contact.
	*
	* @param employeeNumber the employee number of this contact
	*/
	@Override
	public void setEmployeeNumber(java.lang.String employeeNumber) {
		_contact.setEmployeeNumber(employeeNumber);
	}

	/**
	* Sets the employee status ID of this contact.
	*
	* @param employeeStatusId the employee status ID of this contact
	*/
	@Override
	public void setEmployeeStatusId(java.lang.String employeeStatusId) {
		_contact.setEmployeeStatusId(employeeStatusId);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_contact.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_contact.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_contact.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the facebook sn of this contact.
	*
	* @param facebookSn the facebook sn of this contact
	*/
	@Override
	public void setFacebookSn(java.lang.String facebookSn) {
		_contact.setFacebookSn(facebookSn);
	}

	/**
	* Sets the first name of this contact.
	*
	* @param firstName the first name of this contact
	*/
	@Override
	public void setFirstName(java.lang.String firstName) {
		_contact.setFirstName(firstName);
	}

	/**
	* Sets the hours of operation of this contact.
	*
	* @param hoursOfOperation the hours of operation of this contact
	*/
	@Override
	public void setHoursOfOperation(java.lang.String hoursOfOperation) {
		_contact.setHoursOfOperation(hoursOfOperation);
	}

	/**
	* Sets the jabber sn of this contact.
	*
	* @param jabberSn the jabber sn of this contact
	*/
	@Override
	public void setJabberSn(java.lang.String jabberSn) {
		_contact.setJabberSn(jabberSn);
	}

	/**
	* Sets the job class of this contact.
	*
	* @param jobClass the job class of this contact
	*/
	@Override
	public void setJobClass(java.lang.String jobClass) {
		_contact.setJobClass(jobClass);
	}

	/**
	* Sets the job title of this contact.
	*
	* @param jobTitle the job title of this contact
	*/
	@Override
	public void setJobTitle(java.lang.String jobTitle) {
		_contact.setJobTitle(jobTitle);
	}

	/**
	* Sets the last name of this contact.
	*
	* @param lastName the last name of this contact
	*/
	@Override
	public void setLastName(java.lang.String lastName) {
		_contact.setLastName(lastName);
	}

	/**
	* Sets whether this contact is male.
	*
	* @param male the male of this contact
	*/
	@Override
	public void setMale(boolean male) {
		_contact.setMale(male);
	}

	/**
	* Sets the middle name of this contact.
	*
	* @param middleName the middle name of this contact
	*/
	@Override
	public void setMiddleName(java.lang.String middleName) {
		_contact.setMiddleName(middleName);
	}

	/**
	* Sets the modified date of this contact.
	*
	* @param modifiedDate the modified date of this contact
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_contact.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this contact.
	*
	* @param mvccVersion the mvcc version of this contact
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_contact.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_contact.setNew(n);
	}

	/**
	* Sets the parent contact ID of this contact.
	*
	* @param parentContactId the parent contact ID of this contact
	*/
	@Override
	public void setParentContactId(long parentContactId) {
		_contact.setParentContactId(parentContactId);
	}

	/**
	* Sets the prefix ID of this contact.
	*
	* @param prefixId the prefix ID of this contact
	*/
	@Override
	public void setPrefixId(long prefixId) {
		_contact.setPrefixId(prefixId);
	}

	/**
	* Sets the primary key of this contact.
	*
	* @param primaryKey the primary key of this contact
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_contact.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_contact.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the skype sn of this contact.
	*
	* @param skypeSn the skype sn of this contact
	*/
	@Override
	public void setSkypeSn(java.lang.String skypeSn) {
		_contact.setSkypeSn(skypeSn);
	}

	/**
	* Sets the sms sn of this contact.
	*
	* @param smsSn the sms sn of this contact
	*/
	@Override
	public void setSmsSn(java.lang.String smsSn) {
		_contact.setSmsSn(smsSn);
	}

	/**
	* Sets the suffix ID of this contact.
	*
	* @param suffixId the suffix ID of this contact
	*/
	@Override
	public void setSuffixId(long suffixId) {
		_contact.setSuffixId(suffixId);
	}

	/**
	* Sets the twitter sn of this contact.
	*
	* @param twitterSn the twitter sn of this contact
	*/
	@Override
	public void setTwitterSn(java.lang.String twitterSn) {
		_contact.setTwitterSn(twitterSn);
	}

	/**
	* Sets the user ID of this contact.
	*
	* @param userId the user ID of this contact
	*/
	@Override
	public void setUserId(long userId) {
		_contact.setUserId(userId);
	}

	/**
	* Sets the user name of this contact.
	*
	* @param userName the user name of this contact
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_contact.setUserName(userName);
	}

	/**
	* Sets the user uuid of this contact.
	*
	* @param userUuid the user uuid of this contact
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_contact.setUserUuid(userUuid);
	}

	@Override
	public CacheModel<com.liferay.portal.model.Contact> toCacheModel() {
		return _contact.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.Contact toEscapedModel() {
		return new ContactWrapper(_contact.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _contact.toString();
	}

	@Override
	public com.liferay.portal.model.Contact toUnescapedModel() {
		return new ContactWrapper(_contact.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _contact.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ContactWrapper)) {
			return false;
		}

		ContactWrapper contactWrapper = (ContactWrapper)obj;

		if (Validator.equals(_contact, contactWrapper._contact)) {
			return true;
		}

		return false;
	}

	@Override
	public Contact getWrappedModel() {
		return _contact;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _contact.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _contact.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_contact.resetOriginalValues();
	}

	private final Contact _contact;
}