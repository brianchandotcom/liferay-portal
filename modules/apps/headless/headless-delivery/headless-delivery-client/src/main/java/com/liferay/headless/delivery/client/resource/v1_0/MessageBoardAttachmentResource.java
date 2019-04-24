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

package com.liferay.headless.delivery.client.resource.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardAttachment;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.serdes.v1_0.MessageBoardAttachmentSerDes;

import java.io.File;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class MessageBoardAttachmentResource {

	public void deleteMessageBoardAttachment(Long messageBoardAttachmentId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-attachments/{messageBoardAttachmentId}",
			messageBoardAttachmentId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		httpInvoker.invoke();
	}

	public MessageBoardAttachment getMessageBoardAttachment(
			Long messageBoardAttachmentId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-attachments/{messageBoardAttachmentId}",
			messageBoardAttachmentId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return MessageBoardAttachmentSerDes.toDTO(
				httpResponse.getContent());
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response: " + httpResponse.getContent(),
				e);

			throw e;
		}
	}

	public Page<MessageBoardAttachment>
			getMessageBoardMessageMessageBoardAttachmentsPage(
				Long messageBoardMessageId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/message-board-attachments",
			messageBoardMessageId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return Page.of(
			httpResponse.getContent(), MessageBoardAttachmentSerDes::toDTO);
	}

	public MessageBoardAttachment postMessageBoardMessageMessageBoardAttachment(
			Long messageBoardMessageId,
			MessageBoardAttachment messageBoardAttachment,
			Map<String, File> files)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.part(
			"messageBoardAttachment",
			MessageBoardAttachmentSerDes.toJSON(messageBoardAttachment));

		for (Map.Entry<String, File> entry : files.entrySet()) {
			httpInvoker.part(entry.getKey(), entry.getValue());
		}

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/message-board-attachments",
			messageBoardMessageId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return MessageBoardAttachmentSerDes.toDTO(
				httpResponse.getContent());
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response: " + httpResponse.getContent(),
				e);

			throw e;
		}
	}

	public Page<MessageBoardAttachment>
			getMessageBoardThreadMessageBoardAttachmentsPage(
				Long messageBoardThreadId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-attachments",
			messageBoardThreadId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return Page.of(
			httpResponse.getContent(), MessageBoardAttachmentSerDes::toDTO);
	}

	public MessageBoardAttachment postMessageBoardThreadMessageBoardAttachment(
			Long messageBoardThreadId,
			MessageBoardAttachment messageBoardAttachment,
			Map<String, File> files)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.part(
			"messageBoardAttachment",
			MessageBoardAttachmentSerDes.toJSON(messageBoardAttachment));

		for (Map.Entry<String, File> entry : files.entrySet()) {
			httpInvoker.part(entry.getKey(), entry.getValue());
		}

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-attachments",
			messageBoardThreadId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return MessageBoardAttachmentSerDes.toDTO(
				httpResponse.getContent());
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response: " + httpResponse.getContent(),
				e);

			throw e;
		}
	}

	private static final Logger _logger = Logger.getLogger(
		MessageBoardAttachmentResource.class.getName());

}