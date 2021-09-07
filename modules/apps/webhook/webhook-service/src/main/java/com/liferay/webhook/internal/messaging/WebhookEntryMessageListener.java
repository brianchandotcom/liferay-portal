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

package com.liferay.webhook.internal.messaging;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.webhook.constants.WebhookConstants;
import com.liferay.webhook.model.WebhookEntry;

import java.util.Objects;

/**
 * @author Eduardo García
 * @author Brian Wing Shun Chan
 */
public class WebhookEntryMessageListener extends BaseMessageListener {

	public WebhookEntryMessageListener(
		JSONFactory jsonFactory, WebhookEntry webhookEntry) {

		_jsonFactory = jsonFactory;
		_webhookEntry = webhookEntry;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		if (!_isRelevant(message)) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Processing message " + message);
		}

		Http.Options options = new Http.Options();

		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
		options.addHeader("X-Api-Key", _webhookEntry.getSecret());
		options.setBody(
			String.valueOf(message.getPayload()), ContentTypes.APPLICATION_JSON,
			StringPool.UTF8);
		options.setLocation(_webhookEntry.getURL());
		options.setPost(true);

		HttpUtil.URLtoString(options);
	}

	private boolean _isRelevant(Message message) {
		long companyId = message.getLong("companyId");

		if (_webhookEntry.getCompanyId() != companyId) {
			return false;
		}

		if (Objects.equals(
				_webhookEntry.getDestinationWebhookEventKeys(),
				WebhookConstants.DESTINATION_WEBHOOK_EVENT_KEYS_ALL)) {

			return true;
		}

		Object payload = message.getPayload();

		if (payload == null) {
			return false;
		}

		JSONObject payloadJSONObject = null;

		try {
			payloadJSONObject = _jsonFactory.createJSONObject(
				String.valueOf(payload));
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException, jsonException);
			}

			return false;
		}

		String webhookEventKey = payloadJSONObject.getString("webhookEventKey");

		return StringUtil.contains(
			_webhookEntry.getDestinationWebhookEventKeys(), webhookEventKey);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WebhookEntryMessageListener.class);

	private final JSONFactory _jsonFactory;
	private final WebhookEntry _webhookEntry;

}