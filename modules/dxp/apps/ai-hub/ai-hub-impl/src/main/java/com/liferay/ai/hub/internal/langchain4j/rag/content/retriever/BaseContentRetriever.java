/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.langchain4j.rag.content.retriever;

import com.liferay.ai.hub.internal.constants.AIHubDestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;

import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.query.Query;

import java.util.Date;
import java.util.List;

/**
 * @author Feliphe Marinho
 */
public abstract class BaseContentRetriever implements ContentRetriever {

	public BaseContentRetriever(long userId, long workflowInstanceId) {
		_userId = userId;
		_workflowInstanceId = workflowInstanceId;
	}

	@Override
	public List<Content> retrieve(Query query) {
		List<Content> contents = search(query);

		Message message = new Message();

		message.put("contents", contents);
		message.put("query", query.text());
		message.put("searchTarget", getSearchTarget());
		message.put("timestampDate", new Date());
		message.put("userId", _userId);
		message.put("workflowInstanceId", _workflowInstanceId);

		MessageBusUtil.sendMessage(
			AIHubDestinationNames.AI_HUB_CONTENT_RETRIEVER, message);

		return contents;
	}

	protected abstract String getSearchTarget();

	protected abstract List<Content> search(Query query);

	private final long _userId;
	private final long _workflowInstanceId;

}