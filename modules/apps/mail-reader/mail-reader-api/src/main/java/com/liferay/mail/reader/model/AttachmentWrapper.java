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

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Attachment}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Attachment
 * @generated
 */
public class AttachmentWrapper
	extends BaseModelWrapper<Attachment>
	implements Attachment, ModelWrapper<Attachment> {

	public AttachmentWrapper(Attachment attachment) {
		super(attachment);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("attachmentId", getAttachmentId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("accountId", getAccountId());
		attributes.put("folderId", getFolderId());
		attributes.put("messageId", getMessageId());
		attributes.put("contentPath", getContentPath());
		attributes.put("fileName", getFileName());
		attributes.put("size", getSize());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long attachmentId = (Long)attributes.get("attachmentId");

		if (attachmentId != null) {
			setAttachmentId(attachmentId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long accountId = (Long)attributes.get("accountId");

		if (accountId != null) {
			setAccountId(accountId);
		}

		Long folderId = (Long)attributes.get("folderId");

		if (folderId != null) {
			setFolderId(folderId);
		}

		Long messageId = (Long)attributes.get("messageId");

		if (messageId != null) {
			setMessageId(messageId);
		}

		String contentPath = (String)attributes.get("contentPath");

		if (contentPath != null) {
			setContentPath(contentPath);
		}

		String fileName = (String)attributes.get("fileName");

		if (fileName != null) {
			setFileName(fileName);
		}

		Long size = (Long)attributes.get("size");

		if (size != null) {
			setSize(size);
		}
	}

	/**
	 * Returns the account ID of this attachment.
	 *
	 * @return the account ID of this attachment
	 */
	@Override
	public long getAccountId() {
		return model.getAccountId();
	}

	/**
	 * Returns the attachment ID of this attachment.
	 *
	 * @return the attachment ID of this attachment
	 */
	@Override
	public long getAttachmentId() {
		return model.getAttachmentId();
	}

	/**
	 * Returns the company ID of this attachment.
	 *
	 * @return the company ID of this attachment
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the content path of this attachment.
	 *
	 * @return the content path of this attachment
	 */
	@Override
	public String getContentPath() {
		return model.getContentPath();
	}

	/**
	 * Returns the file name of this attachment.
	 *
	 * @return the file name of this attachment
	 */
	@Override
	public String getFileName() {
		return model.getFileName();
	}

	/**
	 * Returns the folder ID of this attachment.
	 *
	 * @return the folder ID of this attachment
	 */
	@Override
	public long getFolderId() {
		return model.getFolderId();
	}

	/**
	 * Returns the message ID of this attachment.
	 *
	 * @return the message ID of this attachment
	 */
	@Override
	public long getMessageId() {
		return model.getMessageId();
	}

	/**
	 * Returns the primary key of this attachment.
	 *
	 * @return the primary key of this attachment
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the size of this attachment.
	 *
	 * @return the size of this attachment
	 */
	@Override
	public long getSize() {
		return model.getSize();
	}

	/**
	 * Returns the user ID of this attachment.
	 *
	 * @return the user ID of this attachment
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this attachment.
	 *
	 * @return the user uuid of this attachment
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the account ID of this attachment.
	 *
	 * @param accountId the account ID of this attachment
	 */
	@Override
	public void setAccountId(long accountId) {
		model.setAccountId(accountId);
	}

	/**
	 * Sets the attachment ID of this attachment.
	 *
	 * @param attachmentId the attachment ID of this attachment
	 */
	@Override
	public void setAttachmentId(long attachmentId) {
		model.setAttachmentId(attachmentId);
	}

	/**
	 * Sets the company ID of this attachment.
	 *
	 * @param companyId the company ID of this attachment
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the content path of this attachment.
	 *
	 * @param contentPath the content path of this attachment
	 */
	@Override
	public void setContentPath(String contentPath) {
		model.setContentPath(contentPath);
	}

	/**
	 * Sets the file name of this attachment.
	 *
	 * @param fileName the file name of this attachment
	 */
	@Override
	public void setFileName(String fileName) {
		model.setFileName(fileName);
	}

	/**
	 * Sets the folder ID of this attachment.
	 *
	 * @param folderId the folder ID of this attachment
	 */
	@Override
	public void setFolderId(long folderId) {
		model.setFolderId(folderId);
	}

	/**
	 * Sets the message ID of this attachment.
	 *
	 * @param messageId the message ID of this attachment
	 */
	@Override
	public void setMessageId(long messageId) {
		model.setMessageId(messageId);
	}

	/**
	 * Sets the primary key of this attachment.
	 *
	 * @param primaryKey the primary key of this attachment
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the size of this attachment.
	 *
	 * @param size the size of this attachment
	 */
	@Override
	public void setSize(long size) {
		model.setSize(size);
	}

	/**
	 * Sets the user ID of this attachment.
	 *
	 * @param userId the user ID of this attachment
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this attachment.
	 *
	 * @param userUuid the user uuid of this attachment
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected AttachmentWrapper wrap(Attachment attachment) {
		return new AttachmentWrapper(attachment);
	}

}