/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.messaging;

import com.liferay.ai.hub.internal.audit.AuditRouterUtil;
import com.liferay.ai.hub.internal.audit.constants.AIHubEventTypes;
import com.liferay.ai.hub.internal.constants.AIHubDestinationNames;
import com.liferay.ai.hub.quota.QuotaManager;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;

import java.util.Date;
import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "destination.name=" + AIHubDestinationNames.AI_HUB_AGENT_INSTANCE,
	service = MessageListener.class
)
public class AIHubAgentInstanceMessageListener extends BaseMessageListener {

	@Activate
	protected void activate(BundleContext bundleContext) {
		Destination destination = _destinationFactory.createDestination(
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				AIHubDestinationNames.AI_HUB_AGENT_INSTANCE));

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
		AuditRouterUtil.route(
			WorkflowInstance.class.getName(),
			message.getLong("workflowInstanceId"),
			(Date)message.get("createDate"), message.getString("eventType"),
			(JSONObject)message.get("additionalInformation"),
			message.getLong("userId"));

		if (Objects.equals(
				AIHubEventTypes.AI_HUB_AGENT_INSTANCE_COMPLETE,
				message.getString("eventType")) ||
			Objects.equals(
				AIHubEventTypes.AI_HUB_AGENT_INSTANCE_COMPLETE_EXCEPTIONALLY,
				message.getString("eventType"))) {

			WorkflowInstance workflowInstance =
				_workflowInstanceManager.getWorkflowInstance(
					message.getLong("companyId"),
					message.getLong("workflowInstanceId"));

			_quotaManager.releaseAgentInstancePermit(
				MapUtil.getString(
					workflowInstance.getWorkflowContext(),
					"agentInstancePermit"));
		}
	}

	@Reference
	private DestinationFactory _destinationFactory;

	private ServiceRegistration<Destination> _destinationServiceRegistration;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private QuotaManager _quotaManager;

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

}