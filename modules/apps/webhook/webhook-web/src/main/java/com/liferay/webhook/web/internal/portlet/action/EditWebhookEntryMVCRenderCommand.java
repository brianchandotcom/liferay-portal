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

package com.liferay.webhook.web.internal.portlet.action;

import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.webhook.service.WebhookEntryLocalService;
import com.liferay.webhook.web.internal.constants.WebhookPortletKeys;
import com.liferay.webhook.web.internal.constants.WebhookWebKeys;
import com.liferay.webhook.web.internal.display.context.EditWebhookEntryDisplayContext;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WebhookPortletKeys.WEBHOOK,
		"mvc.command.name=/webhook/edit_webhook_entry"
	},
	service = MVCRenderCommand.class
)
public class EditWebhookEntryMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			renderRequest.setAttribute(
				WebhookWebKeys.EDIT_WEBHOOK_ENTRY_DISPLAY_CONTEXT,
				new EditWebhookEntryDisplayContext(
					renderRequest, renderResponse, _messageBus));

			long webhookEntryId = ParamUtil.getLong(
				renderRequest, "webhookEntryId");

			if (webhookEntryId > 0) {
				renderRequest.setAttribute(
					WebhookWebKeys.WEBHOOK_ENTRY,
					_webhookEntryLocalService.getWebhookEntry(webhookEntryId));
			}

			return "/edit_webhook_entry.jsp";
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	@Reference
	private MessageBus _messageBus;

	@Reference
	private WebhookEntryLocalService _webhookEntryLocalService;

}