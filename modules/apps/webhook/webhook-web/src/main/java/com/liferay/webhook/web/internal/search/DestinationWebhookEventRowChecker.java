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

package com.liferay.webhook.web.internal.search;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.util.ArrayUtil;

import javax.portlet.RenderResponse;

/**
 * @author Eduardo García
 */
public class DestinationWebhookEventRowChecker extends EmptyOnClickRowChecker {

	public DestinationWebhookEventRowChecker(
		RenderResponse renderResponse,
		String[] checkedDestinationWebhookEventKeys) {

		super(renderResponse);

		_checkedDestinationWebhookEventKeys =
			checkedDestinationWebhookEventKeys;
	}

	@Override
	public boolean isChecked(Object object) {
		if (ArrayUtil.isEmpty(_checkedDestinationWebhookEventKeys)) {
			return true;
		}

		Destination.WebhookEvent webhookEvent =
			(Destination.WebhookEvent)object;

		return ArrayUtil.contains(
			_checkedDestinationWebhookEventKeys, webhookEvent.getKey());
	}

	private final String[] _checkedDestinationWebhookEventKeys;

}