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

package com.liferay.notifications.admin.service.impl;

import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.notifications.admin.exception.NotificationsTemplateFromException;
import com.liferay.notifications.admin.exception.NotificationsTemplateNameException;
import com.liferay.notifications.admin.model.NotificationsTemplate;
import com.liferay.notifications.admin.service.base.NotificationsTemplateLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 * @author Gustavo Lima
 */
@Component(
	property = "model.class.name=com.liferay.notifications.admin.model.NotificationsTemplate",
	service = AopService.class
)
public class NotificationsTemplateLocalServiceImpl
	extends NotificationsTemplateLocalServiceBaseImpl {

	@Override
	public NotificationsTemplate addNotificationsTemplate(
			long userId, long groupId, String name, String description,
			String from, Map<Locale, String> fromNameMap, String to, String cc,
			String bcc, boolean enabled, Map<Locale, String> subjectMap,
			Map<Locale, String> bodyMap, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		validate(name, from);

		long notificationsTemplateId = counterLocalService.increment();

		NotificationsTemplate notificationsTemplate =
			notificationsTemplatePersistence.create(notificationsTemplateId);

		notificationsTemplate.setGroupId(groupId);
		notificationsTemplate.setCompanyId(user.getCompanyId());
		notificationsTemplate.setUserId(user.getUserId());
		notificationsTemplate.setUserName(user.getFullName());
		notificationsTemplate.setName(name);
		notificationsTemplate.setDescription(description);
		notificationsTemplate.setFrom(from);
		notificationsTemplate.setFromNameMap(fromNameMap);
		notificationsTemplate.setTo(to);
		notificationsTemplate.setCc(cc);
		notificationsTemplate.setBcc(bcc);
		notificationsTemplate.setEnabled(enabled);
		notificationsTemplate.setSubjectMap(subjectMap);
		notificationsTemplate.setBodyMap(bodyMap);
		notificationsTemplate.setExpandoBridgeAttributes(serviceContext);

		return notificationsTemplatePersistence.update(notificationsTemplate);
	}

	@Override
	public NotificationsTemplate deleteNotificationsTemplate(
			long notificationsTemplateId)
		throws PortalException {

		NotificationsTemplate notificationsTemplate =
			notificationsTemplatePersistence.findByPrimaryKey(
				notificationsTemplateId);

		return notificationsTemplateLocalService.deleteNotificationsTemplate(
			notificationsTemplate);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public NotificationsTemplate deleteNotificationsTemplate(
			NotificationsTemplate notificationsTemplate)
		throws PortalException {

		notificationsTemplatePersistence.remove(notificationsTemplate);

		_expandoRowLocalService.deleteRows(
			notificationsTemplate.getNotificationsTemplateId());

		return notificationsTemplate;
	}

	@Override
	public void deleteNotificationsTemplates(long groupId)
		throws PortalException {

		List<NotificationsTemplate> notificationsTemplates =
			notificationsTemplatePersistence.findByGroupId(groupId);

		for (NotificationsTemplate notificationsTemplate :
				notificationsTemplates) {

			notificationsTemplateLocalService.deleteNotificationsTemplate(
				notificationsTemplate);
		}
	}

	@Override
	public List<NotificationsTemplate> getNotificationsTemplates(
		long groupId, boolean enabled, int start, int end,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		return notificationsTemplatePersistence.findByG_E(
			groupId, enabled, start, end, orderByComparator);
	}

	@Override
	public List<NotificationsTemplate> getNotificationsTemplates(
		long groupId, int start, int end,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		return notificationsTemplatePersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getNotificationsTemplatesCount(long groupId) {
		return notificationsTemplatePersistence.countByGroupId(groupId);
	}

	@Override
	public int getNotificationsTemplatesCount(long groupId, boolean enabled) {
		return notificationsTemplatePersistence.countByG_E(groupId, enabled);
	}

	@Override
	public NotificationsTemplate updateNotificationsTemplate(
			long notificationsTemplateId, String name, String description,
			String from, Map<Locale, String> fromNameMap, String to, String cc,
			String bcc, boolean enabled, Map<Locale, String> subjectMap,
			Map<Locale, String> bodyMap, ServiceContext serviceContext)
		throws PortalException {

		NotificationsTemplate notificationsTemplate =
			notificationsTemplatePersistence.findByPrimaryKey(
				notificationsTemplateId);

		validate(name, from);

		notificationsTemplate.setName(name);
		notificationsTemplate.setDescription(description);
		notificationsTemplate.setFrom(from);
		notificationsTemplate.setFromNameMap(fromNameMap);
		notificationsTemplate.setTo(to);
		notificationsTemplate.setCc(cc);
		notificationsTemplate.setBcc(bcc);
		notificationsTemplate.setEnabled(enabled);
		notificationsTemplate.setSubjectMap(subjectMap);
		notificationsTemplate.setBodyMap(bodyMap);
		notificationsTemplate.setExpandoBridgeAttributes(serviceContext);

		return notificationsTemplatePersistence.update(notificationsTemplate);
	}

	protected void validate(String name, String from) throws PortalException {
		if (Validator.isNull(name)) {
			throw new NotificationsTemplateNameException();
		}

		if (Validator.isNull(from)) {
			throw new NotificationsTemplateFromException();
		}
	}

	@Reference
	protected ResourceLocalService resourceLocalService;

	@Reference
	protected UserLocalService userLocalService;

	@Reference
	private ExpandoRowLocalService _expandoRowLocalService;

}