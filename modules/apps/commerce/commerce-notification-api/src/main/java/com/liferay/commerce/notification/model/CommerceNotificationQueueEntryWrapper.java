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

package com.liferay.commerce.notification.model;

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
 * This class is a wrapper for {@link CommerceNotificationQueueEntry}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationQueueEntry
 * @generated
 */
public class CommerceNotificationQueueEntryWrapper
	implements CommerceNotificationQueueEntry,
			   ModelWrapper<CommerceNotificationQueueEntry> {

	public CommerceNotificationQueueEntryWrapper(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry) {

		_commerceNotificationQueueEntry = commerceNotificationQueueEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceNotificationQueueEntry.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceNotificationQueueEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"commerceNotificationQueueEntryId",
			getCommerceNotificationQueueEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put(
			"commerceNotificationTemplateId",
			getCommerceNotificationTemplateId());
		attributes.put("from", getFrom());
		attributes.put("fromName", getFromName());
		attributes.put("to", getTo());
		attributes.put("toName", getToName());
		attributes.put("cc", getCc());
		attributes.put("bcc", getBcc());
		attributes.put("subject", getSubject());
		attributes.put("body", getBody());
		attributes.put("priority", getPriority());
		attributes.put("sent", isSent());
		attributes.put("sentDate", getSentDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceNotificationQueueEntryId = (Long)attributes.get(
			"commerceNotificationQueueEntryId");

		if (commerceNotificationQueueEntryId != null) {
			setCommerceNotificationQueueEntryId(
				commerceNotificationQueueEntryId);
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

		Long commerceNotificationTemplateId = (Long)attributes.get(
			"commerceNotificationTemplateId");

		if (commerceNotificationTemplateId != null) {
			setCommerceNotificationTemplateId(commerceNotificationTemplateId);
		}

		String from = (String)attributes.get("from");

		if (from != null) {
			setFrom(from);
		}

		String fromName = (String)attributes.get("fromName");

		if (fromName != null) {
			setFromName(fromName);
		}

		String to = (String)attributes.get("to");

		if (to != null) {
			setTo(to);
		}

		String toName = (String)attributes.get("toName");

		if (toName != null) {
			setToName(toName);
		}

		String cc = (String)attributes.get("cc");

		if (cc != null) {
			setCc(cc);
		}

		String bcc = (String)attributes.get("bcc");

		if (bcc != null) {
			setBcc(bcc);
		}

		String subject = (String)attributes.get("subject");

		if (subject != null) {
			setSubject(subject);
		}

		String body = (String)attributes.get("body");

		if (body != null) {
			setBody(body);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Boolean sent = (Boolean)attributes.get("sent");

		if (sent != null) {
			setSent(sent);
		}

		Date sentDate = (Date)attributes.get("sentDate");

		if (sentDate != null) {
			setSentDate(sentDate);
		}
	}

	@Override
	public Object clone() {
		return new CommerceNotificationQueueEntryWrapper(
			(CommerceNotificationQueueEntry)
				_commerceNotificationQueueEntry.clone());
	}

	@Override
	public int compareTo(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry) {

		return _commerceNotificationQueueEntry.compareTo(
			commerceNotificationQueueEntry);
	}

	/**
	 * Returns the bcc of this commerce notification queue entry.
	 *
	 * @return the bcc of this commerce notification queue entry
	 */
	@Override
	public String getBcc() {
		return _commerceNotificationQueueEntry.getBcc();
	}

	/**
	 * Returns the body of this commerce notification queue entry.
	 *
	 * @return the body of this commerce notification queue entry
	 */
	@Override
	public String getBody() {
		return _commerceNotificationQueueEntry.getBody();
	}

	/**
	 * Returns the cc of this commerce notification queue entry.
	 *
	 * @return the cc of this commerce notification queue entry
	 */
	@Override
	public String getCc() {
		return _commerceNotificationQueueEntry.getCc();
	}

	/**
	 * Returns the fully qualified class name of this commerce notification queue entry.
	 *
	 * @return the fully qualified class name of this commerce notification queue entry
	 */
	@Override
	public String getClassName() {
		return _commerceNotificationQueueEntry.getClassName();
	}

	/**
	 * Returns the class name ID of this commerce notification queue entry.
	 *
	 * @return the class name ID of this commerce notification queue entry
	 */
	@Override
	public long getClassNameId() {
		return _commerceNotificationQueueEntry.getClassNameId();
	}

	/**
	 * Returns the class pk of this commerce notification queue entry.
	 *
	 * @return the class pk of this commerce notification queue entry
	 */
	@Override
	public long getClassPK() {
		return _commerceNotificationQueueEntry.getClassPK();
	}

	/**
	 * Returns the commerce notification queue entry ID of this commerce notification queue entry.
	 *
	 * @return the commerce notification queue entry ID of this commerce notification queue entry
	 */
	@Override
	public long getCommerceNotificationQueueEntryId() {
		return _commerceNotificationQueueEntry.
			getCommerceNotificationQueueEntryId();
	}

	/**
	 * Returns the commerce notification template ID of this commerce notification queue entry.
	 *
	 * @return the commerce notification template ID of this commerce notification queue entry
	 */
	@Override
	public long getCommerceNotificationTemplateId() {
		return _commerceNotificationQueueEntry.
			getCommerceNotificationTemplateId();
	}

	/**
	 * Returns the company ID of this commerce notification queue entry.
	 *
	 * @return the company ID of this commerce notification queue entry
	 */
	@Override
	public long getCompanyId() {
		return _commerceNotificationQueueEntry.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce notification queue entry.
	 *
	 * @return the create date of this commerce notification queue entry
	 */
	@Override
	public Date getCreateDate() {
		return _commerceNotificationQueueEntry.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceNotificationQueueEntry.getExpandoBridge();
	}

	/**
	 * Returns the from of this commerce notification queue entry.
	 *
	 * @return the from of this commerce notification queue entry
	 */
	@Override
	public String getFrom() {
		return _commerceNotificationQueueEntry.getFrom();
	}

	/**
	 * Returns the from name of this commerce notification queue entry.
	 *
	 * @return the from name of this commerce notification queue entry
	 */
	@Override
	public String getFromName() {
		return _commerceNotificationQueueEntry.getFromName();
	}

	/**
	 * Returns the group ID of this commerce notification queue entry.
	 *
	 * @return the group ID of this commerce notification queue entry
	 */
	@Override
	public long getGroupId() {
		return _commerceNotificationQueueEntry.getGroupId();
	}

	/**
	 * Returns the modified date of this commerce notification queue entry.
	 *
	 * @return the modified date of this commerce notification queue entry
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceNotificationQueueEntry.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce notification queue entry.
	 *
	 * @return the primary key of this commerce notification queue entry
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceNotificationQueueEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceNotificationQueueEntry.getPrimaryKeyObj();
	}

	/**
	 * Returns the priority of this commerce notification queue entry.
	 *
	 * @return the priority of this commerce notification queue entry
	 */
	@Override
	public double getPriority() {
		return _commerceNotificationQueueEntry.getPriority();
	}

	/**
	 * Returns the sent of this commerce notification queue entry.
	 *
	 * @return the sent of this commerce notification queue entry
	 */
	@Override
	public boolean getSent() {
		return _commerceNotificationQueueEntry.getSent();
	}

	/**
	 * Returns the sent date of this commerce notification queue entry.
	 *
	 * @return the sent date of this commerce notification queue entry
	 */
	@Override
	public Date getSentDate() {
		return _commerceNotificationQueueEntry.getSentDate();
	}

	/**
	 * Returns the subject of this commerce notification queue entry.
	 *
	 * @return the subject of this commerce notification queue entry
	 */
	@Override
	public String getSubject() {
		return _commerceNotificationQueueEntry.getSubject();
	}

	/**
	 * Returns the to of this commerce notification queue entry.
	 *
	 * @return the to of this commerce notification queue entry
	 */
	@Override
	public String getTo() {
		return _commerceNotificationQueueEntry.getTo();
	}

	/**
	 * Returns the to name of this commerce notification queue entry.
	 *
	 * @return the to name of this commerce notification queue entry
	 */
	@Override
	public String getToName() {
		return _commerceNotificationQueueEntry.getToName();
	}

	/**
	 * Returns the user ID of this commerce notification queue entry.
	 *
	 * @return the user ID of this commerce notification queue entry
	 */
	@Override
	public long getUserId() {
		return _commerceNotificationQueueEntry.getUserId();
	}

	/**
	 * Returns the user name of this commerce notification queue entry.
	 *
	 * @return the user name of this commerce notification queue entry
	 */
	@Override
	public String getUserName() {
		return _commerceNotificationQueueEntry.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce notification queue entry.
	 *
	 * @return the user uuid of this commerce notification queue entry
	 */
	@Override
	public String getUserUuid() {
		return _commerceNotificationQueueEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceNotificationQueueEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceNotificationQueueEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceNotificationQueueEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceNotificationQueueEntry.isNew();
	}

	/**
	 * Returns <code>true</code> if this commerce notification queue entry is sent.
	 *
	 * @return <code>true</code> if this commerce notification queue entry is sent; <code>false</code> otherwise
	 */
	@Override
	public boolean isSent() {
		return _commerceNotificationQueueEntry.isSent();
	}

	@Override
	public void persist() {
		_commerceNotificationQueueEntry.persist();
	}

	/**
	 * Sets the bcc of this commerce notification queue entry.
	 *
	 * @param bcc the bcc of this commerce notification queue entry
	 */
	@Override
	public void setBcc(String bcc) {
		_commerceNotificationQueueEntry.setBcc(bcc);
	}

	/**
	 * Sets the body of this commerce notification queue entry.
	 *
	 * @param body the body of this commerce notification queue entry
	 */
	@Override
	public void setBody(String body) {
		_commerceNotificationQueueEntry.setBody(body);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceNotificationQueueEntry.setCachedModel(cachedModel);
	}

	/**
	 * Sets the cc of this commerce notification queue entry.
	 *
	 * @param cc the cc of this commerce notification queue entry
	 */
	@Override
	public void setCc(String cc) {
		_commerceNotificationQueueEntry.setCc(cc);
	}

	@Override
	public void setClassName(String className) {
		_commerceNotificationQueueEntry.setClassName(className);
	}

	/**
	 * Sets the class name ID of this commerce notification queue entry.
	 *
	 * @param classNameId the class name ID of this commerce notification queue entry
	 */
	@Override
	public void setClassNameId(long classNameId) {
		_commerceNotificationQueueEntry.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this commerce notification queue entry.
	 *
	 * @param classPK the class pk of this commerce notification queue entry
	 */
	@Override
	public void setClassPK(long classPK) {
		_commerceNotificationQueueEntry.setClassPK(classPK);
	}

	/**
	 * Sets the commerce notification queue entry ID of this commerce notification queue entry.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID of this commerce notification queue entry
	 */
	@Override
	public void setCommerceNotificationQueueEntryId(
		long commerceNotificationQueueEntryId) {

		_commerceNotificationQueueEntry.setCommerceNotificationQueueEntryId(
			commerceNotificationQueueEntryId);
	}

	/**
	 * Sets the commerce notification template ID of this commerce notification queue entry.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID of this commerce notification queue entry
	 */
	@Override
	public void setCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {

		_commerceNotificationQueueEntry.setCommerceNotificationTemplateId(
			commerceNotificationTemplateId);
	}

	/**
	 * Sets the company ID of this commerce notification queue entry.
	 *
	 * @param companyId the company ID of this commerce notification queue entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceNotificationQueueEntry.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce notification queue entry.
	 *
	 * @param createDate the create date of this commerce notification queue entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceNotificationQueueEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceNotificationQueueEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceNotificationQueueEntry.setExpandoBridgeAttributes(
			expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceNotificationQueueEntry.setExpandoBridgeAttributes(
			serviceContext);
	}

	/**
	 * Sets the from of this commerce notification queue entry.
	 *
	 * @param from the from of this commerce notification queue entry
	 */
	@Override
	public void setFrom(String from) {
		_commerceNotificationQueueEntry.setFrom(from);
	}

	/**
	 * Sets the from name of this commerce notification queue entry.
	 *
	 * @param fromName the from name of this commerce notification queue entry
	 */
	@Override
	public void setFromName(String fromName) {
		_commerceNotificationQueueEntry.setFromName(fromName);
	}

	/**
	 * Sets the group ID of this commerce notification queue entry.
	 *
	 * @param groupId the group ID of this commerce notification queue entry
	 */
	@Override
	public void setGroupId(long groupId) {
		_commerceNotificationQueueEntry.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this commerce notification queue entry.
	 *
	 * @param modifiedDate the modified date of this commerce notification queue entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceNotificationQueueEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceNotificationQueueEntry.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce notification queue entry.
	 *
	 * @param primaryKey the primary key of this commerce notification queue entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceNotificationQueueEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceNotificationQueueEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the priority of this commerce notification queue entry.
	 *
	 * @param priority the priority of this commerce notification queue entry
	 */
	@Override
	public void setPriority(double priority) {
		_commerceNotificationQueueEntry.setPriority(priority);
	}

	/**
	 * Sets whether this commerce notification queue entry is sent.
	 *
	 * @param sent the sent of this commerce notification queue entry
	 */
	@Override
	public void setSent(boolean sent) {
		_commerceNotificationQueueEntry.setSent(sent);
	}

	/**
	 * Sets the sent date of this commerce notification queue entry.
	 *
	 * @param sentDate the sent date of this commerce notification queue entry
	 */
	@Override
	public void setSentDate(Date sentDate) {
		_commerceNotificationQueueEntry.setSentDate(sentDate);
	}

	/**
	 * Sets the subject of this commerce notification queue entry.
	 *
	 * @param subject the subject of this commerce notification queue entry
	 */
	@Override
	public void setSubject(String subject) {
		_commerceNotificationQueueEntry.setSubject(subject);
	}

	/**
	 * Sets the to of this commerce notification queue entry.
	 *
	 * @param to the to of this commerce notification queue entry
	 */
	@Override
	public void setTo(String to) {
		_commerceNotificationQueueEntry.setTo(to);
	}

	/**
	 * Sets the to name of this commerce notification queue entry.
	 *
	 * @param toName the to name of this commerce notification queue entry
	 */
	@Override
	public void setToName(String toName) {
		_commerceNotificationQueueEntry.setToName(toName);
	}

	/**
	 * Sets the user ID of this commerce notification queue entry.
	 *
	 * @param userId the user ID of this commerce notification queue entry
	 */
	@Override
	public void setUserId(long userId) {
		_commerceNotificationQueueEntry.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce notification queue entry.
	 *
	 * @param userName the user name of this commerce notification queue entry
	 */
	@Override
	public void setUserName(String userName) {
		_commerceNotificationQueueEntry.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce notification queue entry.
	 *
	 * @param userUuid the user uuid of this commerce notification queue entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceNotificationQueueEntry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommerceNotificationQueueEntry> toCacheModel() {

		return _commerceNotificationQueueEntry.toCacheModel();
	}

	@Override
	public CommerceNotificationQueueEntry toEscapedModel() {
		return new CommerceNotificationQueueEntryWrapper(
			_commerceNotificationQueueEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceNotificationQueueEntry.toString();
	}

	@Override
	public CommerceNotificationQueueEntry toUnescapedModel() {
		return new CommerceNotificationQueueEntryWrapper(
			_commerceNotificationQueueEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceNotificationQueueEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceNotificationQueueEntryWrapper)) {
			return false;
		}

		CommerceNotificationQueueEntryWrapper
			commerceNotificationQueueEntryWrapper =
				(CommerceNotificationQueueEntryWrapper)object;

		if (Objects.equals(
				_commerceNotificationQueueEntry,
				commerceNotificationQueueEntryWrapper.
					_commerceNotificationQueueEntry)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceNotificationQueueEntry getWrappedModel() {
		return _commerceNotificationQueueEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceNotificationQueueEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceNotificationQueueEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceNotificationQueueEntry.resetOriginalValues();
	}

	private final CommerceNotificationQueueEntry
		_commerceNotificationQueueEntry;

}