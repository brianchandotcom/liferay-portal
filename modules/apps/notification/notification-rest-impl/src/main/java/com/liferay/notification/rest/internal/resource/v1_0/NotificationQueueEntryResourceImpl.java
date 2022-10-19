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

package com.liferay.notification.rest.internal.resource.v1_0;

import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationQueueEntryConstants;
import com.liferay.notification.handler.NotificationHandler;
import com.liferay.notification.handler.NotificationHandlerServiceTracker;
import com.liferay.notification.rest.dto.v1_0.NotificationQueueEntry;
import com.liferay.notification.rest.resource.v1_0.NotificationQueueEntryResource;
import com.liferay.notification.service.NotificationQueueEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Paulo Albuquerque
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/notification-queue-entry.properties",
	scope = ServiceScope.PROTOTYPE,
	service = NotificationQueueEntryResource.class
)
public class NotificationQueueEntryResourceImpl
	extends BaseNotificationQueueEntryResourceImpl {

	@Override
	public void deleteNotificationQueueEntry(Long notificationQueueEntryId)
		throws Exception {

		_notificationQueueEntryService.deleteNotificationQueueEntry(
			notificationQueueEntryId);
	}

	@Override
	public Page<NotificationQueueEntry> getNotificationQueueEntriesPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.VIEW, "getNotificationQueueEntriesPage",
					NotificationConstants.RESOURCE_NAME,
					contextCompany.getCompanyId())
			).build(),
			booleanQuery -> {
			},
			filter,
			com.liferay.notification.model.NotificationQueueEntry.class.
				getName(),
			search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
			sorts,
			document -> _toNotificationQueueEntry(
				_notificationQueueEntryService.getNotificationQueueEntry(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public NotificationQueueEntry getNotificationQueueEntry(
			Long notificationQueueEntryId)
		throws Exception {

		return _toNotificationQueueEntry(
			_notificationQueueEntryService.getNotificationQueueEntry(
				notificationQueueEntryId));
	}

	@Override
	public void putNotificationQueueEntryResend(Long notificationQueueEntryId)
		throws Exception {

		_notificationQueueEntryService.resendNotificationQueueEntry(
			notificationQueueEntryId);
	}

	private NotificationQueueEntry _toNotificationQueueEntry(
			com.liferay.notification.model.NotificationQueueEntry
				serviceBuilderNotificationQueueEntry)
		throws PortalException {

		NotificationHandler notificationHandler =
			_notificationHandlerServiceTracker.
				getNotificationHandlerByClassName(
					_portal.getClassName(
						serviceBuilderNotificationQueueEntry.getClassNameId()));

		return new NotificationQueueEntry() {
			{
				actions = HashMapBuilder.put(
					"delete",
					addAction(
						ActionKeys.DELETE, "deleteNotificationQueueEntry",
						com.liferay.notification.model.NotificationQueueEntry.
							class.getName(),
						serviceBuilderNotificationQueueEntry.
							getNotificationQueueEntryId())
				).put(
					"get",
					addAction(
						ActionKeys.VIEW, "getNotificationQueueEntry",
						com.liferay.notification.model.NotificationQueueEntry.
							class.getName(),
						serviceBuilderNotificationQueueEntry.
							getNotificationQueueEntryId())
				).put(
					"update",
					() -> {
						if (serviceBuilderNotificationQueueEntry.getStatus() ==
								NotificationQueueEntryConstants.STATUS_SENT) {

							return null;
						}

						return addAction(
							ActionKeys.UPDATE,
							"putNotificationQueueEntryResend",
							com.liferay.notification.model.
								NotificationQueueEntry.class.getName(),
							serviceBuilderNotificationQueueEntry.
								getNotificationQueueEntryId());
					}
				).build();
				bcc = serviceBuilderNotificationQueueEntry.getBcc();
				body = serviceBuilderNotificationQueueEntry.getBody();
				cc = serviceBuilderNotificationQueueEntry.getCc();
				from = serviceBuilderNotificationQueueEntry.getFrom();
				fromName = serviceBuilderNotificationQueueEntry.getFromName();
				id =
					serviceBuilderNotificationQueueEntry.
						getNotificationQueueEntryId();
				priority = serviceBuilderNotificationQueueEntry.getPriority();
				sentDate = serviceBuilderNotificationQueueEntry.getSentDate();
				status = serviceBuilderNotificationQueueEntry.getStatus();
				subject = serviceBuilderNotificationQueueEntry.getSubject();
				to = serviceBuilderNotificationQueueEntry.getTo();
				toName = serviceBuilderNotificationQueueEntry.getToName();
				triggerBy = notificationHandler.getTriggerBy(
					contextAcceptLanguage.getPreferredLocale());
				type = serviceBuilderNotificationQueueEntry.getType();
			}
		};
	}

	@Reference
	private NotificationHandlerServiceTracker
		_notificationHandlerServiceTracker;

	@Reference
	private NotificationQueueEntryService _notificationQueueEntryService;

	@Reference
	private Portal _portal;

}