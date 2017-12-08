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

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.html.preview.constants.HtmlPreviewConstants;
import com.liferay.html.preview.exception.InvalidHtmlPreviewEntryMimeTypeException;
import com.liferay.html.preview.model.HtmlPreviewEntry;
import com.liferay.html.preview.processor.HtmlPreviewProcessor;
import com.liferay.html.preview.processor.HtmlPreviewProcessorTracker;
import com.liferay.html.preview.service.HtmlPreviewEntryLocalService;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.File;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {"destination.name=" + HtmlPreviewConstants.DESTINATION_NAME},
	service = MessageListener.class
)
public class HtmlPreviewGeneratorMessageListener extends BaseMessageListener {

	@Activate
	protected void activate() {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				HtmlPreviewConstants.DESTINATION_NAME);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		_messageBus.addDestination(destination);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		Map<String, Object> payload = (Map<String, Object>)message.getPayload();

		long userId = GetterUtil.getLong(payload.get("userId"));
		long groupId = GetterUtil.getLong(payload.get("groupId"));
		long htmlPreviewEntryId = GetterUtil.getLong(
			payload.get("htmlPreviewEntryId"));
		String content = GetterUtil.getString(payload.get("content"));
		String mimeType = GetterUtil.getString(payload.get("mimeType"));

		HtmlPreviewProcessor htmlPreviewProcessor =
			_htmlPreviewProcessorTracker.getHtmlPreviewProcessor(mimeType);

		if (htmlPreviewProcessor == null) {
			throw new InvalidHtmlPreviewEntryMimeTypeException(
				"No HTML preview processor available for MIME type " +
					mimeType);
		}

		File file = htmlPreviewProcessor.generateHtmlPreview(content);

		FileEntry fileEntry = PortletFileRepositoryUtil.fetchPortletFileEntry(
			groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			String.valueOf(htmlPreviewEntryId));

		if (fileEntry != null) {
			PortletFileRepositoryUtil.deletePortletFileEntry(
				groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				String.valueOf(htmlPreviewEntryId));
		}

		fileEntry = PortletFileRepositoryUtil.addPortletFileEntry(
			groupId, userId, HtmlPreviewEntry.class.getName(), 0,
			HtmlPreviewEntry.class.getName(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, file,
			String.valueOf(htmlPreviewEntryId), mimeType, false);

		HtmlPreviewEntry htmlPreviewEntry =
			_htmlPreviewEntryLocalService.fetchHtmlPreviewEntry(
				htmlPreviewEntryId);

		htmlPreviewEntry.setFileEntryId(fileEntry.getFileEntryId());

		_htmlPreviewEntryLocalService.updateHtmlPreviewEntry(htmlPreviewEntry);
	}

	@Reference
	private DestinationFactory _destinationFactory;

	@Reference
	private HtmlPreviewEntryLocalService _htmlPreviewEntryLocalService;

	@Reference
	private HtmlPreviewProcessorTracker _htmlPreviewProcessorTracker;

	@Reference
	private MessageBus _messageBus;

}