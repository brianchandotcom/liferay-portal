/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.resource.v1_0;

import com.liferay.ai.hub.agent.Agent;
import com.liferay.ai.hub.agent.AgentContext;
import com.liferay.ai.hub.rest.dto.v1_0.Message;
import com.liferay.ai.hub.rest.resource.v1_0.MessageResource;
import com.liferay.ai.hub.rest.resource.v1_0.util.SseUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.sse.Sse;

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

		SseUtil.setSse(_sse);

		_agent.invoke(
			AgentContext.builder(
			).input(
				Map.of("message", message.getText())
			).sseEventSinkKey(
				externalReferenceCode
			).build());

		return message;
	}

	@Reference
	private Agent _agent;

	@Context
	private Sse _sse;

}