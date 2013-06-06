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
 * This class is a wrapper for {@link Contact}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Contact
 * @generated
 */
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
		attributes.put("aimSn", getAimSn());
		attributes.put("facebookSn", getFacebookSn());
		attributes.put("icqSn", getIcqSn());
		attributes.put("jabberSn", getJabberSn());
		attributes.put("msnSn", getMsnSn());
		attributes.put("mySpaceSn", getMySpaceSn());
		attributes.put("skypeSn", getSkypeSn());
		attributes.put("twitterSn", getTwitterSn());
		attributes.put("ymSn", getYmSn());
		attributes.put("employeeStatusId", getEmployeeStatusId());
		attributes.put("employeeNumber", getEmployeeNumber());
		attributes.put("jobTitle", getJobTitle());
		attributes.put("jobClass", getJobClass());
		attributes.put("hoursOfOperation", getHoursOfOperation());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
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

		Integer prefixId = (Integer)attributes.get("prefixId");

		if (prefixId != null) {
			setPrefixId(prefixId);
		}

		Integer suffixId = (Integer)attributes.get("suffixId");

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

		String aimSn = (String)attributes.get("aimSn");

		if (aimSn != null) {
			setAimSn(aimSn);
		}

		String facebookSn = (String)attributes.get("facebookSn");

		if (facebookSn != null) {
			setFacebookSn(facebookSn);
		}

		String icqSn = (String)attributes.get("icqSn");

		if (icqSn != null) {
			setIcqSn(icqSn);
		}

		String jabberSn = (String)attributes.get("jabberSn");

		if (jabberSn != null) {
			setJabberSn(jabberSn);
		}

		String msnSn = (String)attributes.get("msnSn");

		if (msnSn != null) {
			setMsnSn(msnSn);
		}

		String mySpaceSn = (String)attributes.get("mySpaceSn");

		if (mySpaceSn != null) {
			setMySpaceSn(mySpaceSn);
		}

		String skypeSn = (String)attributes.get("skypeSn");

		if (skypeSn != null) {
			setSkypeSn(skypeSn);
		}

		String twitterSn = (String)attributes.get("twitterSn");

		if (twitterSn != null) {
			setTwitterSn(twitterSn);
		}

		String ymSn = (String)attributes.get("ymSn");

		if (ymSn != null) {
			setYmSn(ymSn);
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

	/**
	* Returns the primary key of this contact.
	*
	* @return the primary key of this contact
	*/
	@Override
	public long getPrimaryKey() {
		return _contact.getPrimaryKey();
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
	* Sets the contact ID of this contact.
	*
	* @param contactId the contact ID of this contact
	*/
	@Override
	public void setContactId(long contactId) {
		_contact.setContactId(contactId);
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
	* Sets the company ID of this contact.
	*
	* @param companyId the company ID of this contact
	*/
	@Override
	public void setCompanyId(long companyId) {
		_contact.setCompanyId(companyId);
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
	* Sets the user ID of this contact.
	*
	* @param userId the user ID of this contact
	*/
	@Override
	public void setUserId(long userId) {
		_contact.setUserId(userId);
	}

	/**
	* Returns the user uuid of this contact.
	*
	* @return the user uuid of this contact
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _contact.getUserUuid();
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
	* Sets the user name of this contact.
	*
	* @param userName the user name of this contact
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_contact.setUserName(userName);
	}

	/**
	* Returns the create date of this contact.
	*
	* @return the create date of this contact
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _contact.getCreateDate();
	}

	/**
	* Sets the create date of this contact.
	*
	* @param createDate the create date of this contact
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_contact.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this contact.
	*
	* @return the modified date of this contact
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _contact.getModifiedDate();
	}

	/**
	* Sets the modified date of this contact.
	*
	* @param modifiedDate the modified date of this contact
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_contact.setModifiedDate(modifiedDate);
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

	@Override
	public void setClassName(java.lang.String className) {
		_contact.setClassName(className);
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
	* Sets the class name ID of this contact.
	*
	* @param classNameId the class name ID of this contact
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_contact.setClassNameId(classNameId);
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
	* Sets the class p k of this contact.
	*
	* @param classPK the class p k of this contact
	*/
	@Override
	public void setClassPK(long classPK) {
		_contact.setClassPK(classPK);
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
	* Sets the account ID of this contact.
	*
	* @param accountId the account ID of this contact
	*/
	@Override
	public void setAccountId(long accountId) {
		_contact.setAccountId(accountId);
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
	* Sets the parent contact ID of this contact.
	*
	* @param parentContactId the parent contact ID of this contact
	*/
	@Override
	public void setParentContactId(long parentContactId) {
		_contact.setParentContactId(parentContactId);
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
	* Sets the email address of this contact.
	*
	* @param emailAddress the email address of this contact
	*/
	@Override
	public void setEmailAddress(java.lang.String emailAddress) {
		_contact.setEmailAddress(emailAddress);
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
	* Returns the middle name of this contact.
	*
	* @return the middle name of this contact
	*/
	@Override
	public java.lang.String getMiddleName() {
		return _contact.getMiddleName();
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
	* Returns the last name of this contact.
	*
	* @return the last name of this contact
	*/
	@Override
	public java.lang.String getLastName() {
		return _contact.getLastName();
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
	* Returns the prefix ID of this contact.
	*
	* @return the prefix ID of this contact
	*/
	@Override
	public int getPrefixId() {
		return _contact.getPrefixId();
	}

	/**
	* Sets the prefix ID of this contact.
	*
	* @param prefixId the prefix ID of this contact
	*/
	@Override
	public void setPrefixId(int prefixId) {
		_contact.setPrefixId(prefixId);
	}

	/**
	* Returns the suffix ID of this contact.
	*
	* @return the suffix ID of this contact
	*/
	@Override
	public int getSuffixId() {
		return _contact.getSuffixId();
	}

	/**
	* Sets the suffix ID of this contact.
	*
	* @param suffixId the suffix ID of this contact
	*/
	@Override
	public void setSuffixId(int suffixId) {
		_contact.setSuffixId(suffixId);
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
	* Returns <code>true</code> if this contact is male.
	*
	* @return <code>true</code> if this contact is male; <code>false</code> otherwise
	*/
	@Override
	public boolean isMale() {
		return _contact.isMale();
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
	* Returns the birthday of this contact.
	*
	* @return the birthday of this contact
	*/
	@Override
	public java.util.Date getBirthday() {
		return _contact.getBirthday();
	}

	/**
	* Sets the birthday of this contact.
	*
	* @param birthday the birthday of this contact
	*/
	@Override
	public void setBirthday(java.util.Date birthday) {
		_contact.setBirthday(birthday);
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
	* Sets the sms sn of this contact.
	*
	* @param smsSn the sms sn of this contact
	*/
	@Override
	public void setSmsSn(java.lang.String smsSn) {
		_contact.setSmsSn(smsSn);
	}

	/**
	* Returns the aim sn of this contact.
	*
	* @return the aim sn of this contact
	*/
	@Override
	public java.lang.String getAimSn() {
		return _contact.getAimSn();
	}

	/**
	* Sets the aim sn of this contact.
	*
	* @param aimSn the aim sn of this contact
	*/
	@Override
	public void setAimSn(java.lang.String aimSn) {
		_contact.setAimSn(aimSn);
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
	* Sets the facebook sn of this contact.
	*
	* @param facebookSn the facebook sn of this contact
	*/
	@Override
	public void setFacebookSn(java.lang.String facebookSn) {
		_contact.setFacebookSn(facebookSn);
	}

	/**
	* Returns the icq sn of this contact.
	*
	* @return the icq sn of this contact
	*/
	@Override
	public java.lang.String getIcqSn() {
		return _contact.getIcqSn();
	}

	/**
	* Sets the icq sn of this contact.
	*
	* @param icqSn the icq sn of this contact
	*/
	@Override
	public void setIcqSn(java.lang.String icqSn) {
		_contact.setIcqSn(icqSn);
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
	* Sets the jabber sn of this contact.
	*
	* @param jabberSn the jabber sn of this contact
	*/
	@Override
	public void setJabberSn(java.lang.String jabberSn) {
		_contact.setJabberSn(jabberSn);
	}

	/**
	* Returns the msn sn of this contact.
	*
	* @return the msn sn of this contact
	*/
	@Override
	public java.lang.String getMsnSn() {
		return _contact.getMsnSn();
	}

	/**
	* Sets the msn sn of this contact.
	*
	* @param msnSn the msn sn of this contact
	*/
	@Override
	public void setMsnSn(java.lang.String msnSn) {
		_contact.setMsnSn(msnSn);
	}

	/**
	* Returns the my space sn of this contact.
	*
	* @return the my space sn of this contact
	*/
	@Override
	public java.lang.String getMySpaceSn() {
		return _contact.getMySpaceSn();
	}

	/**
	* Sets the my space sn of this contact.
	*
	* @param mySpaceSn the my space sn of this contact
	*/
	@Override
	public void setMySpaceSn(java.lang.String mySpaceSn) {
		_contact.setMySpaceSn(mySpaceSn);
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
	* Sets the skype sn of this contact.
	*
	* @param skypeSn the skype sn of this contact
	*/
	@Override
	public void setSkypeSn(java.lang.String skypeSn) {
		_contact.setSkypeSn(skypeSn);
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
	* Sets the twitter sn of this contact.
	*
	* @param twitterSn the twitter sn of this contact
	*/
	@Override
	public void setTwitterSn(java.lang.String twitterSn) {
		_contact.setTwitterSn(twitterSn);
	}

	/**
	* Returns the ym sn of this contact.
	*
	* @return the ym sn of this contact
	*/
	@Override
	public java.lang.String getYmSn() {
		return _contact.getYmSn();
	}

	/**
	* Sets the ym sn of this contact.
	*
	* @param ymSn the ym sn of this contact
	*/
	@Override
	public void setYmSn(java.lang.String ymSn) {
		_contact.setYmSn(ymSn);
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

	/**
	* Sets the employee status ID of this contact.
	*
	* @param employeeStatusId the employee status ID of this contact
	*/
	@Override
	public void setEmployeeStatusId(java.lang.String employeeStatusId) {
		_contact.setEmployeeStatusId(employeeStatusId);
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
	* Sets the employee number of this contact.
	*
	* @param employeeNumber the employee number of this contact
	*/
	@Override
	public void setEmployeeNumber(java.lang.String employeeNumber) {
		_contact.setEmployeeNumber(employeeNumber);
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
	* Sets the job title of this contact.
	*
	* @param jobTitle the job title of this contact
	*/
	@Override
	public void setJobTitle(java.lang.String jobTitle) {
		_contact.setJobTitle(jobTitle);
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
	* Sets the job class of this contact.
	*
	* @param jobClass the job class of this contact
	*/
	@Override
	public void setJobClass(java.lang.String jobClass) {
		_contact.setJobClass(jobClass);
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
	* Sets the hours of operation of this contact.
	*
	* @param hoursOfOperation the hours of operation of this contact
	*/
	@Override
	public void setHoursOfOperation(java.lang.String hoursOfOperation) {
		_contact.setHoursOfOperation(hoursOfOperation);
	}

	@Override
	public boolean isNew() {
		return _contact.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_contact.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _contact.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_contact.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _contact.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _contact.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_contact.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _contact.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
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

	@Override
	public java.lang.Object clone() {
		return new ContactWrapper((Contact)_contact.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.Contact contact) {
		return _contact.compareTo(contact);
	}

	@Override
	public int hashCode() {
		return _contact.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.Contact> toCacheModel() {
		return _contact.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.Contact toEscapedModel() {
		return new ContactWrapper(_contact.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.Contact toUnescapedModel() {
		return new ContactWrapper(_contact.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _contact.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _contact.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_contact.persist();
	}

	@Override
	public java.lang.String getFullName() {
		return _contact.getFullName();
	}

	@Override
	public boolean isUser() {
		return _contact.isUser();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public Contact getWrappedContact() {
		return _contact;
	}

	@Override
	public Contact getWrappedModel() {
		return _contact;
	}

	@Override
	public void resetOriginalValues() {
		_contact.resetOriginalValues();
	}

	private Contact _contact;
}