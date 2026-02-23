/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.search.spi.model.index.contributor;

import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Paulo Albuquerque
 */
public class NotificationQueueEntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<NotificationQueueEntry> {

	public NotificationQueueEntryModelIndexerWriterContributor(
		NotificationQueueEntryLocalService notificationQueueEntryLocalService) {

		_notificationQueueEntryLocalService =
			notificationQueueEntryLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _notificationQueueEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	private final NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

}