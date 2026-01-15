/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.resource.v1_0;

import com.liferay.ai.hub.rest.dto.v1_0.Message;
import com.liferay.ai.hub.rest.internal.resource.v1_0.util.GroupUtil;
import com.liferay.ai.hub.rest.internal.resource.v1_0.util.WorkflowContextUtil;
import com.liferay.ai.hub.rest.resource.v1_0.MessageResource;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.sse.Sse;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Feliphe Marinho
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/message.properties",
	scope = ServiceScope.PROTOTYPE, service = MessageResource.class
)
public class MessageResourceImpl extends BaseMessageResourceImpl {

	@Override
	public Message postChatByExternalReferenceCodeMessage(
			String externalReferenceCode, Message message)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-62272")) {

			throw new UnsupportedOperationException();
		}

		Map<String, Serializable> workflowContext =
			WorkflowContextUtil.toWorkflowContext(
				message.getContext(), contextHttpServletRequest, _sse,
				externalReferenceCode);

		workflowContext.put("assistantKey", "chat");
		workflowContext.put("memoryId", externalReferenceCode);
		workflowContext.put("outBoundEventName", "Chat Message Sent");
		workflowContext.put("userMessage", message.getText());
		workflowContext.put(
			"userToken",
			contextHttpServletRequest.getHeader("Liferay-AI-Hub-On-Behalf-Of"));

		_workflowInstanceManager.startWorkflowInstance(
			contextCompany.getCompanyId(),
			GroupUtil.getGroupId(
				contextCompany.getCompanyId(), _groupService,
				message.getScope()),
			contextUser.getUserId(),
			WorkflowDefinitionConstants.NAME_CHAT_MESSAGE_PIPELINE, 1, null,
			workflowContext);

		return message;
	}

	@Reference
	private GroupService _groupService;

	@Context
	private Sse _sse;

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

}