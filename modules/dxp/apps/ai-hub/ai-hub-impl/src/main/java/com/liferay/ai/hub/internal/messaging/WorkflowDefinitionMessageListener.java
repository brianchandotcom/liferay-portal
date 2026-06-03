/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.messaging;

import com.liferay.ai.hub.internal.audit.AuditRouterUtil;
import com.liferay.ai.hub.internal.audit.constants.AIHubEventTypes;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.kaleo.definition.constants.WorkflowDefinitionDestinationNames;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

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
	property = "destination.name=" + WorkflowDefinitionDestinationNames.WORKFLOW_DEFINITION,
	service = MessageListener.class
)
public class WorkflowDefinitionMessageListener extends BaseMessageListener {

	@Activate
	protected void activate(BundleContext bundleContext) {
		Destination destination = _destinationFactory.createDestination(
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				WorkflowDefinitionDestinationNames.WORKFLOW_DEFINITION));

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
		KaleoDefinition kaleoDefinition = (KaleoDefinition)message.get(
			"kaleoDefinition");

		if (!Objects.equals(
				message.getString("scope"),
				WorkflowDefinitionConstants.SCOPE_AI) ||
			kaleoDefinition.isSystem()) {

			return;
		}

		AuditRouterUtil.route(
			KaleoDefinition.class.getName(),
			kaleoDefinition.getKaleoDefinitionId(),
			(Date)message.get("createDate"),
			_eventTypes.get(message.getString("eventType")),
			JSONUtil.put(
				"content", kaleoDefinition.getContentAsXML()
			).put(
				"name", kaleoDefinition.getName()
			).put(
				"originalContent",
				() -> {
					KaleoDefinition originalKaleoDefinition =
						(KaleoDefinition)message.get("originalKaleoDefinition");

					if (originalKaleoDefinition == null) {
						return null;
					}

					return originalKaleoDefinition.getContentAsXML();
				}
			).put(
				"version", kaleoDefinition.getVersion()
			),
			kaleoDefinition.getUserId());
	}

	@Reference
	private DestinationFactory _destinationFactory;

	private ServiceRegistration<Destination> _destinationServiceRegistration;
	private final Map<String, String> _eventTypes = Map.of(
		"ADD", AIHubEventTypes.AI_HUB_WORKFLOW_DEFINITION_ADD, "DELETE",
		AIHubEventTypes.AI_HUB_WORKFLOW_DEFINITION_DELETE, "UPDATE",
		AIHubEventTypes.AI_HUB_WORKFLOW_DEFINITION_UPDATE);

}