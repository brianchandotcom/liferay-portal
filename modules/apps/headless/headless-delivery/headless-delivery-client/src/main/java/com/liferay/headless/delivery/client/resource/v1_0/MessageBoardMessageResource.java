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

import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardMessage;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.serdes.v1_0.MessageBoardMessageSerDes;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class MessageBoardMessageResource {

	public void deleteMessageBoardMessage(Long messageBoardMessageId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}",
			messageBoardMessageId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine("HTTP response status: " + httpResponse.getStatus());
	}

	public MessageBoardMessage getMessageBoardMessage(
			Long messageBoardMessageId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}",
			messageBoardMessageId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine("HTTP response status: " + httpResponse.getStatus());

		try {
			return MessageBoardMessageSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public MessageBoardMessage patchMessageBoardMessage(
			Long messageBoardMessageId, MessageBoardMessage messageBoardMessage)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			MessageBoardMessageSerDes.toJSON(messageBoardMessage),
			"application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.PATCH);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}",
			messageBoardMessageId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine("HTTP response status: " + httpResponse.getStatus());

		try {
			return MessageBoardMessageSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public MessageBoardMessage putMessageBoardMessage(
			Long messageBoardMessageId, MessageBoardMessage messageBoardMessage)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			MessageBoardMessageSerDes.toJSON(messageBoardMessage),
			"application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.PUT);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}",
			messageBoardMessageId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine("HTTP response status: " + httpResponse.getStatus());

		try {
			return MessageBoardMessageSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public void deleteMessageBoardMessageMyRating(Long messageBoardMessageId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/my-rating",
			messageBoardMessageId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine("HTTP response status: " + httpResponse.getStatus());
	}

	public com.liferay.headless.delivery.client.dto.v1_0.Rating
			getMessageBoardMessageMyRating(Long messageBoardMessageId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/my-rating",
			messageBoardMessageId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine("HTTP response status: " + httpResponse.getStatus());

		try {
			return com.liferay.headless.delivery.client.serdes.v1_0.
				RatingSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public com.liferay.headless.delivery.client.dto.v1_0.Rating
			postMessageBoardMessageMyRating(
				Long messageBoardMessageId,
				com.liferay.headless.delivery.client.dto.v1_0.Rating rating)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/my-rating",
			messageBoardMessageId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine("HTTP response status: " + httpResponse.getStatus());

		try {
			return com.liferay.headless.delivery.client.serdes.v1_0.
				RatingSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public com.liferay.headless.delivery.client.dto.v1_0.Rating
			putMessageBoardMessageMyRating(
				Long messageBoardMessageId,
				com.liferay.headless.delivery.client.dto.v1_0.Rating rating)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.PUT);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/my-rating",
			messageBoardMessageId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine("HTTP response status: " + httpResponse.getStatus());

		try {
			return com.liferay.headless.delivery.client.serdes.v1_0.
				RatingSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public Page<MessageBoardMessage>
			getMessageBoardMessageMessageBoardMessagesPage(
				Long parentMessageBoardMessageId, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		if (search != null) {
			httpInvoker.parameter("search", String.valueOf(search));
		}

		if (filterString != null) {
			httpInvoker.parameter("filter", filterString);
		}

		if (pagination != null) {
			httpInvoker.parameter("page", String.valueOf(pagination.getPage()));
			httpInvoker.parameter(
				"pageSize", String.valueOf(pagination.getPageSize()));
		}

		if (sortString != null) {
			httpInvoker.parameter("sort", sortString);
		}

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{parentMessageBoardMessageId}/message-board-messages",
			parentMessageBoardMessageId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine("HTTP response status: " + httpResponse.getStatus());

		return Page.of(content, MessageBoardMessageSerDes::toDTO);
	}

	public MessageBoardMessage postMessageBoardMessageMessageBoardMessage(
			Long parentMessageBoardMessageId,
			MessageBoardMessage messageBoardMessage)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			MessageBoardMessageSerDes.toJSON(messageBoardMessage),
			"application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{parentMessageBoardMessageId}/message-board-messages",
			parentMessageBoardMessageId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine("HTTP response status: " + httpResponse.getStatus());

		try {
			return MessageBoardMessageSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public Page<MessageBoardMessage>
			getMessageBoardThreadMessageBoardMessagesPage(
				Long messageBoardThreadId, String search, String filterString,
				Pagination pagination, String sortString)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		if (search != null) {
			httpInvoker.parameter("search", String.valueOf(search));
		}

		if (filterString != null) {
			httpInvoker.parameter("filter", filterString);
		}

		if (pagination != null) {
			httpInvoker.parameter("page", String.valueOf(pagination.getPage()));
			httpInvoker.parameter(
				"pageSize", String.valueOf(pagination.getPageSize()));
		}

		if (sortString != null) {
			httpInvoker.parameter("sort", sortString);
		}

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-messages",
			messageBoardThreadId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine("HTTP response status: " + httpResponse.getStatus());

		return Page.of(content, MessageBoardMessageSerDes::toDTO);
	}

	public MessageBoardMessage postMessageBoardThreadMessageBoardMessage(
			Long messageBoardThreadId, MessageBoardMessage messageBoardMessage)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			MessageBoardMessageSerDes.toJSON(messageBoardMessage),
			"application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-messages",
			messageBoardThreadId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine("HTTP response status: " + httpResponse.getStatus());

		try {
			return MessageBoardMessageSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	private static final Logger _logger = Logger.getLogger(
		MessageBoardMessageResource.class.getName());

}