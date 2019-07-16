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

package com.liferay.mail.reader.service.impl;

import com.liferay.mail.reader.constants.MailConstants;
import com.liferay.mail.reader.exception.NoSuchMessageException;
import com.liferay.mail.reader.internal.util.HtmlContentUtil;
import com.liferay.mail.reader.model.Attachment;
import com.liferay.mail.reader.model.Folder;
import com.liferay.mail.reader.model.Message;
import com.liferay.mail.reader.service.AttachmentLocalService;
import com.liferay.mail.reader.service.base.MessageLocalServiceBaseImpl;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.nio.charset.StandardCharsets;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Scott Lee
 */
@Component(
	property = "model.class.name=com.liferay.mail.reader.model.Message",
	service = AopService.class
)
public class MessageLocalServiceImpl extends MessageLocalServiceBaseImpl {

	@Override
	public Message addMessage(
			long userId, long folderId, String sender, String to, String cc,
			String bcc, Date sentDate, String subject, String body,
			String flags, long remoteMessageId, String contentType)
		throws PortalException {

		// Message

		User user = userLocalService.getUser(userId);
		Folder folder = folderPersistence.findByPrimaryKey(folderId);
		Date now = new Date();

		long messageId = counterLocalService.increment();

		Message message = messagePersistence.create(messageId);

		subject = encode(subject);

		message.setCompanyId(user.getCompanyId());
		message.setUserId(user.getUserId());
		message.setUserName(user.getFullName());
		message.setCreateDate(now);
		message.setModifiedDate(now);
		message.setAccountId(folder.getAccountId());
		message.setFolderId(folderId);
		message.setSender(sender);
		message.setTo(to);
		message.setCc(cc);
		message.setBcc(bcc);
		message.setSentDate(sentDate);
		message.setSubject(subject);
		message.setPreview(getPreview(body));
		message.setBody(getBody(body));
		message.setFlags(flags);
		message.setSize(getSize(messageId, body));
		message.setRemoteMessageId(remoteMessageId);
		message.setContentType(removeBoundaryMarker(contentType));

		messagePersistence.update(message);

		// Indexer

		Indexer<Message> indexer = _indexerRegistry.getIndexer(Message.class);

		indexer.reindex(message);

		return message;
	}

	@Override
	public Message deleteMessage(long messageId) throws PortalException {
		Message message = messagePersistence.findByPrimaryKey(messageId);

		return deleteMessage(message);
	}

	@Override
	public Message deleteMessage(Message message) throws PortalException {

		// Message

		messagePersistence.remove(message);

		// Attachments

		_attachmentLocalService.deleteAttachments(
			message.getCompanyId(), message.getMessageId());

		// Indexer

		Indexer<Message> indexer = _indexerRegistry.getIndexer(Message.class);

		indexer.delete(message);

		return message;
	}

	@Override
	public void deleteMessages(long folderId) throws PortalException {
		List<Message> messages = messagePersistence.findByFolderId(folderId);

		for (Message message : messages) {
			deleteMessage(message);
		}
	}

	@Override
	public int getAccountUnreadMessagesCount(long accountId) {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Message.class, getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("accountId", accountId));
		dynamicQuery.add(
			RestrictionsFactoryUtil.not(
				RestrictionsFactoryUtil.like(
					"flags", "%" + MailConstants.FLAG_SEEN + ",%")));

		return (int)dynamicQueryCount(dynamicQuery);
	}

	@Override
	public List<Message> getCompanyMessages(
		long companyId, int start, int end) {

		return messagePersistence.findByCompanyId(companyId, start, end);
	}

	@Override
	public int getCompanyMessagesCount(long companyId) {
		return messagePersistence.countByCompanyId(companyId);
	}

	@Override
	public List<Message> getFolderMessages(long folderId) {
		return messagePersistence.findByFolderId(folderId);
	}

	@Override
	public int getFolderMessagesCount(long folderId) {
		return messagePersistence.countByFolderId(folderId);
	}

	@Override
	public int getFolderUnreadMessagesCount(long folderId) {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Message.class, getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("folderId", folderId));
		dynamicQuery.add(
			RestrictionsFactoryUtil.not(
				RestrictionsFactoryUtil.like(
					"flags", "%" + MailConstants.FLAG_SEEN + ",%")));

		return (int)dynamicQueryCount(dynamicQuery);
	}

	@Override
	public Message getMessage(long folderId, long remoteMessageId)
		throws PortalException {

		return messagePersistence.findByF_R(folderId, remoteMessageId);
	}

	@Override
	public Message getRemoteMessage(long folderId, boolean oldest)
		throws PortalException {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Message.class, getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("folderId", folderId));
		dynamicQuery.add(
			RestrictionsFactoryUtil.gt("remoteMessageId", Long.valueOf(0)));

		if (oldest) {
			dynamicQuery.addOrder(OrderFactoryUtil.asc("remoteMessageId"));
		}
		else {
			dynamicQuery.addOrder(OrderFactoryUtil.desc("remoteMessageId"));
		}

		List<Message> messages = messagePersistence.findWithDynamicQuery(
			dynamicQuery, 0, 1);

		if (messages.isEmpty()) {
			throw new NoSuchMessageException();
		}

		return messages.get(0);
	}

	@Override
	public int populateMessages(
		List<Message> messages, long folderId, String keywords, int pageNumber,
		int messagesPerPage, String orderByField, String orderByType) {

		DynamicQuery countDynamicQuery = DynamicQueryFactoryUtil.forClass(
			Message.class, getClassLoader());

		countDynamicQuery.add(RestrictionsFactoryUtil.eq("folderId", folderId));

		DynamicQuery messageDynamicQuery = DynamicQueryFactoryUtil.forClass(
			Message.class, getClassLoader());

		messageDynamicQuery.add(
			RestrictionsFactoryUtil.eq("folderId", folderId));

		if (Validator.isNotNull(keywords)) {
			String value = "%" + keywords + "%";

			Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

			disjunction.add(RestrictionsFactoryUtil.ilike("subject", value));
			disjunction.add(RestrictionsFactoryUtil.ilike("body", value));

			countDynamicQuery.add(disjunction);

			messageDynamicQuery.add(disjunction);
		}

		if (orderByType.equals("desc")) {
			messageDynamicQuery.addOrder(OrderFactoryUtil.desc(orderByField));
		}
		else {
			messageDynamicQuery.addOrder(OrderFactoryUtil.asc(orderByField));
		}

		int start = messagesPerPage * (pageNumber - 1);
		int end = messagesPerPage * pageNumber;

		messages.addAll(
			messagePersistence.<Message>findWithDynamicQuery(
				messageDynamicQuery, start, end));

		return (int)dynamicQueryCount(countDynamicQuery);
	}

	@Override
	public Message updateContent(long messageId, String body, String flags)
		throws PortalException {

		Message message = messagePersistence.findByPrimaryKey(messageId);

		message.setModifiedDate(new Date());
		message.setPreview(getPreview(body));
		message.setBody(getBody(body));
		message.setFlags(flags);
		message.setSize(getSize(messageId, body));

		messagePersistence.update(message);

		return message;
	}

	@Override
	public Message updateFlag(long messageId, int flag, boolean value)
		throws PortalException {

		String flagString = String.valueOf(flag);

		Message message = messagePersistence.findByPrimaryKey(messageId);

		String flags = message.getFlags();

		if (value && !StringUtil.contains(flags, flagString)) {
			message.setFlags(StringUtil.add(flags, flagString));
		}
		else if (!value && StringUtil.contains(flags, flagString)) {
			message.setFlags(StringUtil.removeFromList(flags, flagString));
		}

		return messagePersistence.update(message);
	}

	@Override
	public Message updateMessage(
			long messageId, long folderId, String sender, String to, String cc,
			String bcc, Date sentDate, String subject, String body,
			String flags, long remoteMessageId)
		throws PortalException {

		// Message

		Message message = messagePersistence.findByPrimaryKey(messageId);

		message.setModifiedDate(new Date());
		message.setFolderId(folderId);
		message.setSender(sender);
		message.setTo(to);
		message.setCc(cc);
		message.setBcc(bcc);
		message.setSentDate(sentDate);
		message.setSubject(subject);
		message.setPreview(getPreview(body));
		message.setBody(getBody(body));
		message.setFlags(flags);
		message.setSize(getSize(messageId, body));
		message.setRemoteMessageId(remoteMessageId);

		messagePersistence.update(message);

		// Indexer

		Indexer<Message> indexer = _indexerRegistry.getIndexer(Message.class);

		indexer.reindex(message);

		return message;
	}

	protected String encode(String subject) {
		byte[] bytes = subject.getBytes(StandardCharsets.ISO_8859_1);

		return new String(bytes, StandardCharsets.UTF_8);
	}

	protected String getBody(String body) {
		if (Validator.isNull(body)) {
			return body;
		}

		return HtmlContentUtil.getInlineHtml(body);
	}

	protected String getPreview(String body) {
		if (Validator.isNull(body)) {
			return body;
		}

		return StringUtil.shorten(HtmlContentUtil.getPlainText(body), 50);
	}

	protected long getSize(long messageId, String body) {
		if (Validator.isNull(body)) {
			return 0;
		}

		byte[] bytes = body.getBytes();

		long size = bytes.length;

		List<Attachment> attachments = attachmentPersistence.findByMessageId(
			messageId);

		for (Attachment attachment : attachments) {
			size += attachment.getSize();
		}

		return size;
	}

	protected String removeBoundaryMarker(String contentType) {
		int i = contentType.indexOf(CharPool.SEMICOLON);

		if (i == -1) {
			return contentType;
		}

		return contentType.substring(0, i);
	}

	@Reference
	private AttachmentLocalService _attachmentLocalService;

	@Reference
	private IndexerRegistry _indexerRegistry;

}