/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notifications.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Correa
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.kernel.model.UserNotificationEvent",
	service = ModelDocumentContributor.class
)
public class UserNotificationEventModelDocumentContributor
	implements ModelDocumentContributor<UserNotificationEvent> {

	@Override
	public void contribute(
		Document document, UserNotificationEvent userNotificationEvent) {

		document.addDate(
			Field.CREATE_DATE, new Date(userNotificationEvent.getTimestamp()));
		document.addKeyword(Field.USER_ID, userNotificationEvent.getUserId());
		document.addKeyword("archived", userNotificationEvent.isArchived());
	}

}