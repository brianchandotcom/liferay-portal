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

import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.webhook.web.internal.search.DestinationWebhookEventRowChecker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Eduardo García
 */
public class SelectDestinationWebhookEventsDisplayContext {

	public SelectDestinationWebhookEventsDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		MessageBus messageBus) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_messageBus = messageBus;
	}

	public String getItemSelectedEventName() {
		return ParamUtil.getString(_renderRequest, "eventName");
	}

	public PortletURL getPortletURL() {
		return PortletURLUtil.getCurrent(_renderRequest, _renderResponse);
	}

	public SearchContainer<Destination.WebhookEvent> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_renderRequest, getPortletURL(), null, "no-entries-were-found");

		RowChecker rowChecker = new DestinationWebhookEventRowChecker(
			_renderResponse, getCheckedDestinationWebhookEventKeys());

		_searchContainer.setRowChecker(rowChecker);

		String destinationName = ParamUtil.getString(
			_renderRequest, "destinationName");

		Destination destination = _messageBus.getDestination(destinationName);

		List<Destination.WebhookEvent> webhookEvents = new ArrayList<>(
			destination.getWebhookEvents());

		ListUtil.sort(
			webhookEvents,
			Comparator.comparing(Destination.WebhookEvent::getName));

		_searchContainer.setTotal(webhookEvents.size());

		_searchContainer.setResults(webhookEvents);

		return _searchContainer;
	}

	protected String[] getCheckedDestinationWebhookEventKeys() {
		return ParamUtil.getStringValues(
			_renderRequest, "destinationWebHookEventKeys");
	}

	private final MessageBus _messageBus;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<Destination.WebhookEvent> _searchContainer;

}