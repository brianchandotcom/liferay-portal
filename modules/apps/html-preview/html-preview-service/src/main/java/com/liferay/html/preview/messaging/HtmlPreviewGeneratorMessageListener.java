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

package com.liferay.html.preview.messaging;

import com.liferay.html.preview.constants.HtmlPreviewConstants;
import com.liferay.html.preview.service.HtmlPreviewLocalService;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"destination.name=" + HtmlPreviewConstants.HTML_PREVIEW_GENERATOR
	},
	service = MessageListener.class
)
public class HtmlPreviewGeneratorMessageListener extends BaseMessageListener {

	@Activate
	protected void activate() {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				HtmlPreviewConstants.HTML_PREVIEW_GENERATOR);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		_messageBus.addDestination(destination);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		Map<String, Object> payload = (Map<String, Object>)message.getPayload();

		long userId = GetterUtil.getLong(payload.get("userId"));
		long groupId = GetterUtil.getLong(payload.get("groupId"));
		long classNameId = GetterUtil.getLong(payload.get("classNameId"));
		long classPK = GetterUtil.getLong(payload.get("classPK"));
		String content = GetterUtil.getString(payload.get("content"));
		String mimeType = GetterUtil.getString(payload.get("mimeType"));

		ServiceContext serviceContext = new ServiceContext();

		_htmlPreviewLocalService.generateHtmlPreview(
			userId, groupId, classNameId, classPK, content, mimeType, false,
			serviceContext);
	}

	@Reference
	private DestinationFactory _destinationFactory;

	@Reference
	private HtmlPreviewLocalService _htmlPreviewLocalService;

	@Reference
	private MessageBus _messageBus;

}