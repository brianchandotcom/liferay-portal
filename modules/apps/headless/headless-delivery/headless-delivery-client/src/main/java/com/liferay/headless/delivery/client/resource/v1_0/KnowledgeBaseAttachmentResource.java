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

import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseAttachment;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.serdes.v1_0.KnowledgeBaseAttachmentSerDes;

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
public class KnowledgeBaseAttachmentResource {

	public Page<KnowledgeBaseAttachment>
			getKnowledgeBaseArticleKnowledgeBaseAttachmentsPage(
				Long knowledgeBaseArticleId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/knowledge-base-attachments",
			knowledgeBaseArticleId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return Page.of(
			httpResponse.getContent(), KnowledgeBaseAttachmentSerDes::toDTO);
	}

	public KnowledgeBaseAttachment
			postKnowledgeBaseArticleKnowledgeBaseAttachment(
				Long knowledgeBaseArticleId,
				KnowledgeBaseAttachment knowledgeBaseAttachment,
				Map<String, File> files)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.part(
			"knowledgeBaseAttachment",
			KnowledgeBaseAttachmentSerDes.toJSON(knowledgeBaseAttachment));

		for (Map.Entry<String, File> entry : files.entrySet()) {
			httpInvoker.part(entry.getKey(), entry.getValue());
		}

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/knowledge-base-attachments",
			knowledgeBaseArticleId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return KnowledgeBaseAttachmentSerDes.toDTO(
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

	public void deleteKnowledgeBaseAttachment(Long knowledgeBaseAttachmentId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-attachments/{knowledgeBaseAttachmentId}",
			knowledgeBaseAttachmentId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		httpInvoker.invoke();
	}

	public KnowledgeBaseAttachment getKnowledgeBaseAttachment(
			Long knowledgeBaseAttachmentId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-attachments/{knowledgeBaseAttachmentId}",
			knowledgeBaseAttachmentId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return KnowledgeBaseAttachmentSerDes.toDTO(
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
		KnowledgeBaseAttachmentResource.class.getName());

}