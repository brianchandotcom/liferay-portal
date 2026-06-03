/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.messaging;

import com.liferay.ai.hub.internal.audit.AuditRouterUtil;
import com.liferay.ai.hub.internal.audit.constants.AIHubEventTypes;
import com.liferay.ai.hub.internal.constants.AIHubDestinationNames;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.rag.content.Content;

import java.util.Date;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "destination.name=" + AIHubDestinationNames.AI_HUB_CONTENT_RETRIEVER,
	service = MessageListener.class
)
public class AIHubContentRetrieverMessageListener extends BaseMessageListener {

	@Activate
	protected void activate(BundleContext bundleContext) {
		Destination destination = _destinationFactory.createDestination(
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				AIHubDestinationNames.AI_HUB_CONTENT_RETRIEVER));

		_destinationServiceRegistration = bundleContext.registerService(
			Destination.class, destination,
			MapUtil.singletonDictionary(
				"destination.name", destination.getName()));
	}

	@Deactivate
	protected void deactivate() {
		_destinationServiceRegistration.unregister();
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		JSONArray jsonArray = JSONUtil.toJSONArray(
			(List<Content>)message.get("contents"),
			content -> JSONUtil.put(
				"text",
				() -> {
					TextSegment textSegment = content.textSegment();

					return textSegment.text();
				}));

		AuditRouterUtil.route(
			WorkflowInstance.class.getName(),
			message.getLong("workflowInstanceId"),
			AIHubEventTypes.AI_HUB_RAG_CONTENT_RETRIEVE,
			JSONUtil.put(
				"contents", jsonArray.toString()
			).put(
				"contentsCount", jsonArray.length()
			).put(
				"query", message.getString("query")
			).put(
				"searchTarget", message.getString("searchTarget")
			).put(
				"workflowInstanceId", message.getLong("workflowInstanceId")
			),
			(Date)message.get("timestampDate"), message.getLong("userId"));
	}

	@Reference
	private DestinationFactory _destinationFactory;

	private ServiceRegistration<Destination> _destinationServiceRegistration;

}