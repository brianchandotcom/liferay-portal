/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
	extends BaseModelWrapper<CommerceNotificationQueueEntry>
	implements CommerceNotificationQueueEntry,
			   ModelWrapper<CommerceNotificationQueueEntry> {

	public CommerceNotificationQueueEntryWrapper(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry) {

		super(commerceNotificationQueueEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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
	public CommerceNotificationQueueEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the bcc of this commerce notification queue entry.
	 *
	 * @return the bcc of this commerce notification queue entry
	 */
	@Override
	public String getBcc() {
		return model.getBcc();
	}

	/**
	 * Returns the body of this commerce notification queue entry.
	 *
	 * @return the body of this commerce notification queue entry
	 */
	@Override
	public String getBody() {
		return model.getBody();
	}

	/**
	 * Returns the cc of this commerce notification queue entry.
	 *
	 * @return the cc of this commerce notification queue entry
	 */
	@Override
	public String getCc() {
		return model.getCc();
	}

	/**
	 * Returns the fully qualified class name of this commerce notification queue entry.
	 *
	 * @return the fully qualified class name of this commerce notification queue entry
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this commerce notification queue entry.
	 *
	 * @return the class name ID of this commerce notification queue entry
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this commerce notification queue entry.
	 *
	 * @return the class pk of this commerce notification queue entry
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the commerce notification queue entry ID of this commerce notification queue entry.
	 *
	 * @return the commerce notification queue entry ID of this commerce notification queue entry
	 */
	@Override
	public long getCommerceNotificationQueueEntryId() {
		return model.getCommerceNotificationQueueEntryId();
	}

	/**
	 * Returns the commerce notification template ID of this commerce notification queue entry.
	 *
	 * @return the commerce notification template ID of this commerce notification queue entry
	 */
	@Override
	public long getCommerceNotificationTemplateId() {
		return model.getCommerceNotificationTemplateId();
	}

	/**
	 * Returns the company ID of this commerce notification queue entry.
	 *
	 * @return the company ID of this commerce notification queue entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce notification queue entry.
	 *
	 * @return the create date of this commerce notification queue entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the from of this commerce notification queue entry.
	 *
	 * @return the from of this commerce notification queue entry
	 */
	@Override
	public String getFrom() {
		return model.getFrom();
	}

	/**
	 * Returns the from name of this commerce notification queue entry.
	 *
	 * @return the from name of this commerce notification queue entry
	 */
	@Override
	public String getFromName() {
		return model.getFromName();
	}

	/**
	 * Returns the group ID of this commerce notification queue entry.
	 *
	 * @return the group ID of this commerce notification queue entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this commerce notification queue entry.
	 *
	 * @return the modified date of this commerce notification queue entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce notification queue entry.
	 *
	 * @return the mvcc version of this commerce notification queue entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce notification queue entry.
	 *
	 * @return the primary key of this commerce notification queue entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this commerce notification queue entry.
	 *
	 * @return the priority of this commerce notification queue entry
	 */
	@Override
	public double getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the sent of this commerce notification queue entry.
	 *
	 * @return the sent of this commerce notification queue entry
	 */
	@Override
	public boolean getSent() {
		return model.getSent();
	}

	/**
	 * Returns the sent date of this commerce notification queue entry.
	 *
	 * @return the sent date of this commerce notification queue entry
	 */
	@Override
	public Date getSentDate() {
		return model.getSentDate();
	}

	/**
	 * Returns the subject of this commerce notification queue entry.
	 *
	 * @return the subject of this commerce notification queue entry
	 */
	@Override
	public String getSubject() {
		return model.getSubject();
	}

	/**
	 * Returns the to of this commerce notification queue entry.
	 *
	 * @return the to of this commerce notification queue entry
	 */
	@Override
	public String getTo() {
		return model.getTo();
	}

	/**
	 * Returns the to name of this commerce notification queue entry.
	 *
	 * @return the to name of this commerce notification queue entry
	 */
	@Override
	public String getToName() {
		return model.getToName();
	}

	/**
	 * Returns the user ID of this commerce notification queue entry.
	 *
	 * @return the user ID of this commerce notification queue entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce notification queue entry.
	 *
	 * @return the user name of this commerce notification queue entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce notification queue entry.
	 *
	 * @return the user uuid of this commerce notification queue entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this commerce notification queue entry is sent.
	 *
	 * @return <code>true</code> if this commerce notification queue entry is sent; <code>false</code> otherwise
	 */
	@Override
	public boolean isSent() {
		return model.isSent();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the bcc of this commerce notification queue entry.
	 *
	 * @param bcc the bcc of this commerce notification queue entry
	 */
	@Override
	public void setBcc(String bcc) {
		model.setBcc(bcc);
	}

	/**
	 * Sets the body of this commerce notification queue entry.
	 *
	 * @param body the body of this commerce notification queue entry
	 */
	@Override
	public void setBody(String body) {
		model.setBody(body);
	}

	/**
	 * Sets the cc of this commerce notification queue entry.
	 *
	 * @param cc the cc of this commerce notification queue entry
	 */
	@Override
	public void setCc(String cc) {
		model.setCc(cc);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this commerce notification queue entry.
	 *
	 * @param classNameId the class name ID of this commerce notification queue entry
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this commerce notification queue entry.
	 *
	 * @param classPK the class pk of this commerce notification queue entry
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the commerce notification queue entry ID of this commerce notification queue entry.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID of this commerce notification queue entry
	 */
	@Override
	public void setCommerceNotificationQueueEntryId(
		long commerceNotificationQueueEntryId) {

		model.setCommerceNotificationQueueEntryId(
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

		model.setCommerceNotificationTemplateId(commerceNotificationTemplateId);
	}

	/**
	 * Sets the company ID of this commerce notification queue entry.
	 *
	 * @param companyId the company ID of this commerce notification queue entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce notification queue entry.
	 *
	 * @param createDate the create date of this commerce notification queue entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the from of this commerce notification queue entry.
	 *
	 * @param from the from of this commerce notification queue entry
	 */
	@Override
	public void setFrom(String from) {
		model.setFrom(from);
	}

	/**
	 * Sets the from name of this commerce notification queue entry.
	 *
	 * @param fromName the from name of this commerce notification queue entry
	 */
	@Override
	public void setFromName(String fromName) {
		model.setFromName(fromName);
	}

	/**
	 * Sets the group ID of this commerce notification queue entry.
	 *
	 * @param groupId the group ID of this commerce notification queue entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this commerce notification queue entry.
	 *
	 * @param modifiedDate the modified date of this commerce notification queue entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce notification queue entry.
	 *
	 * @param mvccVersion the mvcc version of this commerce notification queue entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce notification queue entry.
	 *
	 * @param primaryKey the primary key of this commerce notification queue entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this commerce notification queue entry.
	 *
	 * @param priority the priority of this commerce notification queue entry
	 */
	@Override
	public void setPriority(double priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets whether this commerce notification queue entry is sent.
	 *
	 * @param sent the sent of this commerce notification queue entry
	 */
	@Override
	public void setSent(boolean sent) {
		model.setSent(sent);
	}

	/**
	 * Sets the sent date of this commerce notification queue entry.
	 *
	 * @param sentDate the sent date of this commerce notification queue entry
	 */
	@Override
	public void setSentDate(Date sentDate) {
		model.setSentDate(sentDate);
	}

	/**
	 * Sets the subject of this commerce notification queue entry.
	 *
	 * @param subject the subject of this commerce notification queue entry
	 */
	@Override
	public void setSubject(String subject) {
		model.setSubject(subject);
	}

	/**
	 * Sets the to of this commerce notification queue entry.
	 *
	 * @param to the to of this commerce notification queue entry
	 */
	@Override
	public void setTo(String to) {
		model.setTo(to);
	}

	/**
	 * Sets the to name of this commerce notification queue entry.
	 *
	 * @param toName the to name of this commerce notification queue entry
	 */
	@Override
	public void setToName(String toName) {
		model.setToName(toName);
	}

	/**
	 * Sets the user ID of this commerce notification queue entry.
	 *
	 * @param userId the user ID of this commerce notification queue entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce notification queue entry.
	 *
	 * @param userName the user name of this commerce notification queue entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce notification queue entry.
	 *
	 * @param userUuid the user uuid of this commerce notification queue entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected CommerceNotificationQueueEntryWrapper wrap(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry) {

		return new CommerceNotificationQueueEntryWrapper(
			commerceNotificationQueueEntry);
	}

}