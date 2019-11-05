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

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.analytics.message.sender.client.AnalyticsMessageSenderClient;
import com.liferay.analytics.message.sender.model.AnalyticsMessage;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;

import java.util.Collections;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
public abstract class BaseEntityModelListener<T extends BaseModel<T>>
	extends BaseModelListener<T> {

	public abstract String getObjectType();

	protected void send(String eventType, Object object) {
		try {
			AnalyticsMessage.Builder analyticsMessageBuilder =
				AnalyticsMessage.builder("", getObjectType());

			analyticsMessageBuilder.action(eventType);
			analyticsMessageBuilder.object(serialize(object));

			_analyticsMessageSenderClient.send(
				Collections.singletonList(analyticsMessageBuilder.build()));
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to send analytics message " + serialize(object));
			}
		}
	}

	protected String serialize(Object object) {
		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		return jsonSerializer.serialize(object);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseEntityModelListener.class);

	@Reference
	private AnalyticsMessageSenderClient _analyticsMessageSenderClient;

}