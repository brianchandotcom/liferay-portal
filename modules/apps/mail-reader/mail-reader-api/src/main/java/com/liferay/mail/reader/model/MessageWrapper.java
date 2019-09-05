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

package com.liferay.mail.reader.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Message}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Message
 * @generated
 */
public class MessageWrapper
	extends BaseModelWrapper<Message>
	implements Message, ModelWrapper<Message> {

	public MessageWrapper(Message message) {
		super(message);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("messageId", getMessageId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("accountId", getAccountId());
		attributes.put("folderId", getFolderId());
		attributes.put("sender", getSender());
		attributes.put("to", getTo());
		attributes.put("cc", getCc());
		attributes.put("bcc", getBcc());
		attributes.put("sentDate", getSentDate());
		attributes.put("subject", getSubject());
		attributes.put("preview", getPreview());
		attributes.put("body", getBody());
		attributes.put("flags", getFlags());
		attributes.put("size", getSize());
		attributes.put("remoteMessageId", getRemoteMessageId());
		attributes.put("contentType", getContentType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long messageId = (Long)attributes.get("messageId");

		if (messageId != null) {
			setMessageId(messageId);
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

		Long accountId = (Long)attributes.get("accountId");

		if (accountId != null) {
			setAccountId(accountId);
		}

		Long folderId = (Long)attributes.get("folderId");

		if (folderId != null) {
			setFolderId(folderId);
		}

		String sender = (String)attributes.get("sender");

		if (sender != null) {
			setSender(sender);
		}

		String to = (String)attributes.get("to");

		if (to != null) {
			setTo(to);
		}

		String cc = (String)attributes.get("cc");

		if (cc != null) {
			setCc(cc);
		}

		String bcc = (String)attributes.get("bcc");

		if (bcc != null) {
			setBcc(bcc);
		}

		Date sentDate = (Date)attributes.get("sentDate");

		if (sentDate != null) {
			setSentDate(sentDate);
		}

		String subject = (String)attributes.get("subject");

		if (subject != null) {
			setSubject(subject);
		}

		String preview = (String)attributes.get("preview");

		if (preview != null) {
			setPreview(preview);
		}

		String body = (String)attributes.get("body");

		if (body != null) {
			setBody(body);
		}

		String flags = (String)attributes.get("flags");

		if (flags != null) {
			setFlags(flags);
		}

		Long size = (Long)attributes.get("size");

		if (size != null) {
			setSize(size);
		}

		Long remoteMessageId = (Long)attributes.get("remoteMessageId");

		if (remoteMessageId != null) {
			setRemoteMessageId(remoteMessageId);
		}

		String contentType = (String)attributes.get("contentType");

		if (contentType != null) {
			setContentType(contentType);
		}
	}

	/**
	 * Returns the account ID of this message.
	 *
	 * @return the account ID of this message
	 */
	@Override
	public long getAccountId() {
		return model.getAccountId();
	}

	/**
	 * Returns the bcc of this message.
	 *
	 * @return the bcc of this message
	 */
	@Override
	public String getBcc() {
		return model.getBcc();
	}

	/**
	 * Returns the body of this message.
	 *
	 * @return the body of this message
	 */
	@Override
	public String getBody() {
		return model.getBody();
	}

	/**
	 * Returns the cc of this message.
	 *
	 * @return the cc of this message
	 */
	@Override
	public String getCc() {
		return model.getCc();
	}

	/**
	 * Returns the company ID of this message.
	 *
	 * @return the company ID of this message
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the content type of this message.
	 *
	 * @return the content type of this message
	 */
	@Override
	public String getContentType() {
		return model.getContentType();
	}

	/**
	 * Returns the create date of this message.
	 *
	 * @return the create date of this message
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the flags of this message.
	 *
	 * @return the flags of this message
	 */
	@Override
	public String getFlags() {
		return model.getFlags();
	}

	/**
	 * Returns the folder ID of this message.
	 *
	 * @return the folder ID of this message
	 */
	@Override
	public long getFolderId() {
		return model.getFolderId();
	}

	@Override
	public long getGroupId()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getGroupId();
	}

	/**
	 * Returns the message ID of this message.
	 *
	 * @return the message ID of this message
	 */
	@Override
	public long getMessageId() {
		return model.getMessageId();
	}

	/**
	 * Returns the modified date of this message.
	 *
	 * @return the modified date of this message
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the preview of this message.
	 *
	 * @return the preview of this message
	 */
	@Override
	public String getPreview() {
		return model.getPreview();
	}

	/**
	 * Returns the primary key of this message.
	 *
	 * @return the primary key of this message
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the remote message ID of this message.
	 *
	 * @return the remote message ID of this message
	 */
	@Override
	public long getRemoteMessageId() {
		return model.getRemoteMessageId();
	}

	/**
	 * Returns the sender of this message.
	 *
	 * @return the sender of this message
	 */
	@Override
	public String getSender() {
		return model.getSender();
	}

	/**
	 * Returns the sent date of this message.
	 *
	 * @return the sent date of this message
	 */
	@Override
	public Date getSentDate() {
		return model.getSentDate();
	}

	/**
	 * Returns the size of this message.
	 *
	 * @return the size of this message
	 */
	@Override
	public long getSize() {
		return model.getSize();
	}

	/**
	 * Returns the subject of this message.
	 *
	 * @return the subject of this message
	 */
	@Override
	public String getSubject() {
		return model.getSubject();
	}

	/**
	 * Returns the to of this message.
	 *
	 * @return the to of this message
	 */
	@Override
	public String getTo() {
		return model.getTo();
	}

	/**
	 * Returns the user ID of this message.
	 *
	 * @return the user ID of this message
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this message.
	 *
	 * @return the user name of this message
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this message.
	 *
	 * @return the user uuid of this message
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public boolean hasAttachments() {
		return model.hasAttachments();
	}

	@Override
	public boolean hasFlag(int flag) {
		return model.hasFlag(flag);
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the account ID of this message.
	 *
	 * @param accountId the account ID of this message
	 */
	@Override
	public void setAccountId(long accountId) {
		model.setAccountId(accountId);
	}

	/**
	 * Sets the bcc of this message.
	 *
	 * @param bcc the bcc of this message
	 */
	@Override
	public void setBcc(String bcc) {
		model.setBcc(bcc);
	}

	/**
	 * Sets the body of this message.
	 *
	 * @param body the body of this message
	 */
	@Override
	public void setBody(String body) {
		model.setBody(body);
	}

	/**
	 * Sets the cc of this message.
	 *
	 * @param cc the cc of this message
	 */
	@Override
	public void setCc(String cc) {
		model.setCc(cc);
	}

	/**
	 * Sets the company ID of this message.
	 *
	 * @param companyId the company ID of this message
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the content type of this message.
	 *
	 * @param contentType the content type of this message
	 */
	@Override
	public void setContentType(String contentType) {
		model.setContentType(contentType);
	}

	/**
	 * Sets the create date of this message.
	 *
	 * @param createDate the create date of this message
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the flags of this message.
	 *
	 * @param flags the flags of this message
	 */
	@Override
	public void setFlags(String flags) {
		model.setFlags(flags);
	}

	/**
	 * Sets the folder ID of this message.
	 *
	 * @param folderId the folder ID of this message
	 */
	@Override
	public void setFolderId(long folderId) {
		model.setFolderId(folderId);
	}

	/**
	 * Sets the message ID of this message.
	 *
	 * @param messageId the message ID of this message
	 */
	@Override
	public void setMessageId(long messageId) {
		model.setMessageId(messageId);
	}

	/**
	 * Sets the modified date of this message.
	 *
	 * @param modifiedDate the modified date of this message
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the preview of this message.
	 *
	 * @param preview the preview of this message
	 */
	@Override
	public void setPreview(String preview) {
		model.setPreview(preview);
	}

	/**
	 * Sets the primary key of this message.
	 *
	 * @param primaryKey the primary key of this message
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the remote message ID of this message.
	 *
	 * @param remoteMessageId the remote message ID of this message
	 */
	@Override
	public void setRemoteMessageId(long remoteMessageId) {
		model.setRemoteMessageId(remoteMessageId);
	}

	/**
	 * Sets the sender of this message.
	 *
	 * @param sender the sender of this message
	 */
	@Override
	public void setSender(String sender) {
		model.setSender(sender);
	}

	/**
	 * Sets the sent date of this message.
	 *
	 * @param sentDate the sent date of this message
	 */
	@Override
	public void setSentDate(Date sentDate) {
		model.setSentDate(sentDate);
	}

	/**
	 * Sets the size of this message.
	 *
	 * @param size the size of this message
	 */
	@Override
	public void setSize(long size) {
		model.setSize(size);
	}

	/**
	 * Sets the subject of this message.
	 *
	 * @param subject the subject of this message
	 */
	@Override
	public void setSubject(String subject) {
		model.setSubject(subject);
	}

	/**
	 * Sets the to of this message.
	 *
	 * @param to the to of this message
	 */
	@Override
	public void setTo(String to) {
		model.setTo(to);
	}

	/**
	 * Sets the user ID of this message.
	 *
	 * @param userId the user ID of this message
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this message.
	 *
	 * @param userName the user name of this message
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this message.
	 *
	 * @param userUuid the user uuid of this message
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected MessageWrapper wrap(Message message) {
		return new MessageWrapper(message);
	}

}