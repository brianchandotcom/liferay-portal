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

package com.liferay.notification.internal.search.spi.model.index.contributor;

import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Paulo Albuquerque
 */
@Component(
	property = "indexer.class.name=com.liferay.notification.model.NotificationQueueEntry",
	service = ModelDocumentContributor.class
)
public class NotificationQueueEntryModelDocumentContributor
	implements ModelDocumentContributor<NotificationQueueEntry> {

	@Override
	public void contribute(
		Document document, NotificationQueueEntry notificationQueueEntry) {

		document.addKeyword("fromName", notificationQueueEntry.getFromName());
		document.addText("fromName", notificationQueueEntry.getFrom());

		document.addKeyword("subject", notificationQueueEntry.getSubject());
		document.addText("subject", notificationQueueEntry.getSubject());

		document.addKeyword("toName", notificationQueueEntry.getToName());
		document.addText("toName", notificationQueueEntry.getToName());

		document.addKeyword("triggerBy", notificationQueueEntry.getToName());
		document.addText("triggerBy", notificationQueueEntry.getToName());
	}

}