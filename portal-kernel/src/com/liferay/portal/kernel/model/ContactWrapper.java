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

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
public class ContactWrapper extends BaseModelWrapper<Contact> implements Contact,
	ModelWrapper<Contact> {
	public ContactWrapper(Contact contact) {
		super(contact);
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
		attributes.put("male", isMale());
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

	/**
	* Returns the account ID of this contact.
	*
	* @return the account ID of this contact
	*/
	@Override
	public long getAccountId() {
		return model.getAccountId();
	}

	/**
	* Returns the birthday of this contact.
	*
	* @return the birthday of this contact
	*/
	@Override
	public Date getBirthday() {
		return model.getBirthday();
	}

	/**
	* Returns the fully qualified class name of this contact.
	*
	* @return the fully qualified class name of this contact
	*/
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	* Returns the class name ID of this contact.
	*
	* @return the class name ID of this contact
	*/
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	* Returns the class pk of this contact.
	*
	* @return the class pk of this contact
	*/
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	* Returns the company ID of this contact.
	*
	* @return the company ID of this contact
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the contact ID of this contact.
	*
	* @return the contact ID of this contact
	*/
	@Override
	public long getContactId() {
		return model.getContactId();
	}

	/**
	* Returns the create date of this contact.
	*
	* @return the create date of this contact
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the email address of this contact.
	*
	* @return the email address of this contact
	*/
	@Override
	public String getEmailAddress() {
		return model.getEmailAddress();
	}

	/**
	* Returns the employee number of this contact.
	*
	* @return the employee number of this contact
	*/
	@Override
	public String getEmployeeNumber() {
		return model.getEmployeeNumber();
	}

	/**
	* Returns the employee status ID of this contact.
	*
	* @return the employee status ID of this contact
	*/
	@Override
	public String getEmployeeStatusId() {
		return model.getEmployeeStatusId();
	}

	/**
	* Returns the facebook sn of this contact.
	*
	* @return the facebook sn of this contact
	*/
	@Override
	public String getFacebookSn() {
		return model.getFacebookSn();
	}

	/**
	* Returns the first name of this contact.
	*
	* @return the first name of this contact
	*/
	@Override
	public String getFirstName() {
		return model.getFirstName();
	}

	@Override
	public String getFullName() {
		return model.getFullName();
	}

	/**
	* Returns the hours of operation of this contact.
	*
	* @return the hours of operation of this contact
	*/
	@Override
	public String getHoursOfOperation() {
		return model.getHoursOfOperation();
	}

	/**
	* Returns the jabber sn of this contact.
	*
	* @return the jabber sn of this contact
	*/
	@Override
	public String getJabberSn() {
		return model.getJabberSn();
	}

	/**
	* Returns the job class of this contact.
	*
	* @return the job class of this contact
	*/
	@Override
	public String getJobClass() {
		return model.getJobClass();
	}

	/**
	* Returns the job title of this contact.
	*
	* @return the job title of this contact
	*/
	@Override
	public String getJobTitle() {
		return model.getJobTitle();
	}

	/**
	* Returns the last name of this contact.
	*
	* @return the last name of this contact
	*/
	@Override
	public String getLastName() {
		return model.getLastName();
	}

	/**
	* Returns the male of this contact.
	*
	* @return the male of this contact
	*/
	@Override
	public boolean getMale() {
		return model.getMale();
	}

	/**
	* Returns the middle name of this contact.
	*
	* @return the middle name of this contact
	*/
	@Override
	public String getMiddleName() {
		return model.getMiddleName();
	}

	/**
	* Returns the modified date of this contact.
	*
	* @return the modified date of this contact
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this contact.
	*
	* @return the mvcc version of this contact
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the parent contact ID of this contact.
	*
	* @return the parent contact ID of this contact
	*/
	@Override
	public long getParentContactId() {
		return model.getParentContactId();
	}

	/**
	* Returns the prefix ID of this contact.
	*
	* @return the prefix ID of this contact
	*/
	@Override
	public long getPrefixId() {
		return model.getPrefixId();
	}

	/**
	* Returns the primary key of this contact.
	*
	* @return the primary key of this contact
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the skype sn of this contact.
	*
	* @return the skype sn of this contact
	*/
	@Override
	public String getSkypeSn() {
		return model.getSkypeSn();
	}

	/**
	* Returns the sms sn of this contact.
	*
	* @return the sms sn of this contact
	*/
	@Override
	public String getSmsSn() {
		return model.getSmsSn();
	}

	/**
	* Returns the suffix ID of this contact.
	*
	* @return the suffix ID of this contact
	*/
	@Override
	public long getSuffixId() {
		return model.getSuffixId();
	}

	/**
	* Returns the twitter sn of this contact.
	*
	* @return the twitter sn of this contact
	*/
	@Override
	public String getTwitterSn() {
		return model.getTwitterSn();
	}

	/**
	* Returns the user ID of this contact.
	*
	* @return the user ID of this contact
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this contact.
	*
	* @return the user name of this contact
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this contact.
	*
	* @return the user uuid of this contact
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns <code>true</code> if this contact is male.
	*
	* @return <code>true</code> if this contact is male; <code>false</code> otherwise
	*/
	@Override
	public boolean isMale() {
		return model.isMale();
	}

	@Override
	public boolean isUser() {
		return model.isUser();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the account ID of this contact.
	*
	* @param accountId the account ID of this contact
	*/
	@Override
	public void setAccountId(long accountId) {
		model.setAccountId(accountId);
	}

	/**
	* Sets the birthday of this contact.
	*
	* @param birthday the birthday of this contact
	*/
	@Override
	public void setBirthday(Date birthday) {
		model.setBirthday(birthday);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	* Sets the class name ID of this contact.
	*
	* @param classNameId the class name ID of this contact
	*/
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this contact.
	*
	* @param classPK the class pk of this contact
	*/
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this contact.
	*
	* @param companyId the company ID of this contact
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the contact ID of this contact.
	*
	* @param contactId the contact ID of this contact
	*/
	@Override
	public void setContactId(long contactId) {
		model.setContactId(contactId);
	}

	/**
	* Sets the create date of this contact.
	*
	* @param createDate the create date of this contact
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the email address of this contact.
	*
	* @param emailAddress the email address of this contact
	*/
	@Override
	public void setEmailAddress(String emailAddress) {
		model.setEmailAddress(emailAddress);
	}

	/**
	* Sets the employee number of this contact.
	*
	* @param employeeNumber the employee number of this contact
	*/
	@Override
	public void setEmployeeNumber(String employeeNumber) {
		model.setEmployeeNumber(employeeNumber);
	}

	/**
	* Sets the employee status ID of this contact.
	*
	* @param employeeStatusId the employee status ID of this contact
	*/
	@Override
	public void setEmployeeStatusId(String employeeStatusId) {
		model.setEmployeeStatusId(employeeStatusId);
	}

	/**
	* Sets the facebook sn of this contact.
	*
	* @param facebookSn the facebook sn of this contact
	*/
	@Override
	public void setFacebookSn(String facebookSn) {
		model.setFacebookSn(facebookSn);
	}

	/**
	* Sets the first name of this contact.
	*
	* @param firstName the first name of this contact
	*/
	@Override
	public void setFirstName(String firstName) {
		model.setFirstName(firstName);
	}

	/**
	* Sets the hours of operation of this contact.
	*
	* @param hoursOfOperation the hours of operation of this contact
	*/
	@Override
	public void setHoursOfOperation(String hoursOfOperation) {
		model.setHoursOfOperation(hoursOfOperation);
	}

	/**
	* Sets the jabber sn of this contact.
	*
	* @param jabberSn the jabber sn of this contact
	*/
	@Override
	public void setJabberSn(String jabberSn) {
		model.setJabberSn(jabberSn);
	}

	/**
	* Sets the job class of this contact.
	*
	* @param jobClass the job class of this contact
	*/
	@Override
	public void setJobClass(String jobClass) {
		model.setJobClass(jobClass);
	}

	/**
	* Sets the job title of this contact.
	*
	* @param jobTitle the job title of this contact
	*/
	@Override
	public void setJobTitle(String jobTitle) {
		model.setJobTitle(jobTitle);
	}

	/**
	* Sets the last name of this contact.
	*
	* @param lastName the last name of this contact
	*/
	@Override
	public void setLastName(String lastName) {
		model.setLastName(lastName);
	}

	/**
	* Sets whether this contact is male.
	*
	* @param male the male of this contact
	*/
	@Override
	public void setMale(boolean male) {
		model.setMale(male);
	}

	/**
	* Sets the middle name of this contact.
	*
	* @param middleName the middle name of this contact
	*/
	@Override
	public void setMiddleName(String middleName) {
		model.setMiddleName(middleName);
	}

	/**
	* Sets the modified date of this contact.
	*
	* @param modifiedDate the modified date of this contact
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this contact.
	*
	* @param mvccVersion the mvcc version of this contact
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the parent contact ID of this contact.
	*
	* @param parentContactId the parent contact ID of this contact
	*/
	@Override
	public void setParentContactId(long parentContactId) {
		model.setParentContactId(parentContactId);
	}

	/**
	* Sets the prefix ID of this contact.
	*
	* @param prefixId the prefix ID of this contact
	*/
	@Override
	public void setPrefixId(long prefixId) {
		model.setPrefixId(prefixId);
	}

	/**
	* Sets the primary key of this contact.
	*
	* @param primaryKey the primary key of this contact
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the skype sn of this contact.
	*
	* @param skypeSn the skype sn of this contact
	*/
	@Override
	public void setSkypeSn(String skypeSn) {
		model.setSkypeSn(skypeSn);
	}

	/**
	* Sets the sms sn of this contact.
	*
	* @param smsSn the sms sn of this contact
	*/
	@Override
	public void setSmsSn(String smsSn) {
		model.setSmsSn(smsSn);
	}

	/**
	* Sets the suffix ID of this contact.
	*
	* @param suffixId the suffix ID of this contact
	*/
	@Override
	public void setSuffixId(long suffixId) {
		model.setSuffixId(suffixId);
	}

	/**
	* Sets the twitter sn of this contact.
	*
	* @param twitterSn the twitter sn of this contact
	*/
	@Override
	public void setTwitterSn(String twitterSn) {
		model.setTwitterSn(twitterSn);
	}

	/**
	* Sets the user ID of this contact.
	*
	* @param userId the user ID of this contact
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this contact.
	*
	* @param userName the user name of this contact
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this contact.
	*
	* @param userUuid the user uuid of this contact
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected ContactWrapper wrap(Contact contact) {
		return new ContactWrapper(contact);
	}
}