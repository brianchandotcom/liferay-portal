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

	public static void deleteMessageBoardAttachment(
			Long messageBoardAttachmentId)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			deleteMessageBoardAttachmentHttpResponse(messageBoardAttachmentId);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());
	}

	public static HttpInvoker.HttpResponse
			deleteMessageBoardAttachmentHttpResponse(
				Long messageBoardAttachmentId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-attachments/{messageBoardAttachmentId}",
			messageBoardAttachmentId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static MessageBoardAttachment getMessageBoardAttachment(
			Long messageBoardAttachmentId)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			getMessageBoardAttachmentHttpResponse(messageBoardAttachmentId);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		try {
			return MessageBoardAttachmentSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse
			getMessageBoardAttachmentHttpResponse(Long messageBoardAttachmentId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-attachments/{messageBoardAttachmentId}",
			messageBoardAttachmentId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static Page<MessageBoardAttachment>
			getMessageBoardMessageMessageBoardAttachmentsPage(
				Long messageBoardMessageId)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			getMessageBoardMessageMessageBoardAttachmentsPageHttpResponse(
				messageBoardMessageId);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		return Page.of(content, MessageBoardAttachmentSerDes::toDTO);
	}

	public static HttpInvoker.HttpResponse
			getMessageBoardMessageMessageBoardAttachmentsPageHttpResponse(
				Long messageBoardMessageId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/message-board-attachments",
			messageBoardMessageId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static MessageBoardAttachment
			postMessageBoardMessageMessageBoardAttachment(
				Long messageBoardMessageId,
				MessageBoardAttachment messageBoardAttachment,
				Map<String, File> multipartFiles)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			postMessageBoardMessageMessageBoardAttachmentHttpResponse(
				messageBoardMessageId, messageBoardAttachment, multipartFiles);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		try {
			return MessageBoardAttachmentSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse
			postMessageBoardMessageMessageBoardAttachmentHttpResponse(
				Long messageBoardMessageId,
				MessageBoardAttachment messageBoardAttachment,
				Map<String, File> multipartFiles)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.multipart();

		httpInvoker.part(
			"messageBoardAttachment",
			MessageBoardAttachmentSerDes.toJSON(messageBoardAttachment));

		for (Map.Entry<String, File> entry : multipartFiles.entrySet()) {
			httpInvoker.part(entry.getKey(), entry.getValue());
		}

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/message-board-attachments",
			messageBoardMessageId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static Page<MessageBoardAttachment>
			getMessageBoardThreadMessageBoardAttachmentsPage(
				Long messageBoardThreadId)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			getMessageBoardThreadMessageBoardAttachmentsPageHttpResponse(
				messageBoardThreadId);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		return Page.of(content, MessageBoardAttachmentSerDes::toDTO);
	}

	public static HttpInvoker.HttpResponse
			getMessageBoardThreadMessageBoardAttachmentsPageHttpResponse(
				Long messageBoardThreadId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-attachments",
			messageBoardThreadId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static MessageBoardAttachment
			postMessageBoardThreadMessageBoardAttachment(
				Long messageBoardThreadId,
				MessageBoardAttachment messageBoardAttachment,
				Map<String, File> multipartFiles)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			postMessageBoardThreadMessageBoardAttachmentHttpResponse(
				messageBoardThreadId, messageBoardAttachment, multipartFiles);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		try {
			return MessageBoardAttachmentSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse
			postMessageBoardThreadMessageBoardAttachmentHttpResponse(
				Long messageBoardThreadId,
				MessageBoardAttachment messageBoardAttachment,
				Map<String, File> multipartFiles)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.multipart();

		httpInvoker.part(
			"messageBoardAttachment",
			MessageBoardAttachmentSerDes.toJSON(messageBoardAttachment));

		for (Map.Entry<String, File> entry : multipartFiles.entrySet()) {
			httpInvoker.part(entry.getKey(), entry.getValue());
		}

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-attachments",
			messageBoardThreadId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	private static final Logger _logger = Logger.getLogger(
		MessageBoardAttachmentResource.class.getName());

}