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

package com.liferay.webhook.web.internal.display.context;

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.webhook.constants.WebhookConstants;
import com.liferay.webhook.model.WebhookEntry;
import com.liferay.webhook.web.internal.constants.WebhookWebKeys;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowStateException;

/**
 * @author Eduardo García
 */
public class EditWebhookEntryDisplayContext {

	public EditWebhookEntryDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		MessageBus messageBus) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_messageBus = messageBus;
	}

	public Collection<String> getDestinationNames() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Collection<Destination> webhookCapableDestinations =
			_messageBus.getWebhookCapableDestinations(
				themeDisplay.getCompanyId());

		Stream<Destination> webhookCapableDestinationsStream =
			webhookCapableDestinations.stream();

		return webhookCapableDestinationsStream.map(
			Destination::getName
		).sorted(
		).collect(
			Collectors.toList()
		);
	}

	public String getDestinationWebhookEventKeysSelectorUrl()
		throws WindowStateException {

		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/webhook/select_destination_webhook_events"
		).setRedirect(
			PortalUtil.getCurrentURL(_renderRequest)
		).setParameter(
			"eventName", getItemSelectedEventName()
		).setParameter(
			"webhookEntryId",
			ParamUtil.getLong(_renderRequest, "webhookEntryId")
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public String getItemSelectedEventName() {
		return _renderResponse.getNamespace() + "selectDestinationWebhookEvent";
	}

	public boolean isAllDestinationWebhookKeys() {
		WebhookEntry webhookEntry = (WebhookEntry)_renderRequest.getAttribute(
			WebhookWebKeys.WEBHOOK_ENTRY);

		if (webhookEntry == null) {
			return true;
		}

		return Objects.equals(
			webhookEntry.getDestinationWebhookEventKeys(),
			WebhookConstants.DESTINATION_WEBHOOK_EVENT_KEYS_ALL);
	}

	private final MessageBus _messageBus;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}