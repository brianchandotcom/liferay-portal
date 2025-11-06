/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.search.spi.model.index.contributor;

import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationRecipientSettingTable;
import com.liferay.notification.model.NotificationRecipientTable;
import com.liferay.notification.service.NotificationRecipientLocalService;
import com.liferay.notification.util.NotificationRecipientSettingUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.ReindexCacheThreadLocal;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

		document.addDate(Field.SENT_DATE, notificationQueueEntry.getSentDate());

		String fromName = _getFromName(notificationQueueEntry);

		document.addKeyword("fromName", fromName);
		document.addText("fromName", fromName);

		document.addKeyword("subject", notificationQueueEntry.getSubject());
		document.addText("subject", notificationQueueEntry.getSubject());
	}

	private String _getFromName(NotificationQueueEntry notificationQueueEntry) {
		Map<Long, String> fromNames =
			ReindexCacheThreadLocal.getGlobalReindexCache(
				() -> _notificationRecipientLocalService.dslQueryCount(
					DSLQueryFactoryUtil.count(
					).from(
						NotificationRecipientTable.INSTANCE
					),
					false),
				NotificationQueueEntryModelDocumentContributor.class.getName(),
				count -> {
					Map<Long, String> localFromNames = new HashMap<>();

					if (count == 0) {
						return localFromNames;
					}

					DSLQuery dslQuery = DSLQueryFactoryUtil.select(
						NotificationRecipientTable.INSTANCE.classPK,
						NotificationRecipientSettingTable.INSTANCE.value
					).from(
						NotificationRecipientTable.INSTANCE
					).innerJoinON(
						NotificationRecipientSettingTable.INSTANCE,
						NotificationRecipientTable.INSTANCE.
							notificationRecipientId.eq(
								NotificationRecipientSettingTable.INSTANCE.
									notificationRecipientId)
					).where(
						NotificationRecipientSettingTable.INSTANCE.name.eq(
							"fromName")
					);

					for (Object[] values :
							_notificationRecipientLocalService.
								<List<Object[]>>dslQuery(dslQuery, false)) {

						localFromNames.put((long)values[0], (String)values[1]);
					}

					return localFromNames;
				});

		if (fromNames == null) {
			Map<String, Object> notificationRecipientSettingsMap =
				NotificationRecipientSettingUtil.
					getNotificationRecipientSettingsMap(notificationQueueEntry);

			return String.valueOf(
				notificationRecipientSettingsMap.get("fromName"));
		}

		return fromNames.get(
			notificationQueueEntry.getNotificationQueueEntryId());
	}

	@Reference
	private NotificationRecipientLocalService
		_notificationRecipientLocalService;

}