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

package com.liferay.notification.internal.util;

import com.liferay.notification.constants.DefinitionTermConstants;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.term.contributor.DefinitionTermContributor;
import com.liferay.notification.term.contributor.DefinitionTermContributorRegistry;
import com.liferay.notification.type.NotificationType;
import com.liferay.notification.util.NotificationHelper;
import com.liferay.notification.util.NotificationTypeRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.EmailAddressValidator;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.EmailAddressValidatorFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gustavo Lima
 */
@Component(immediate = true, service = NotificationHelper.class)
public class NotificationHelperImpl implements NotificationHelper {

	@Override
	public void sendNotification(
			long userId, NotificationTemplate notificationTemplate,
			String notificationTypeKey, Object object)
		throws PortalException {

		if (Validator.isBlank(notificationTypeKey)) {
			return;
		}

		NotificationType notificationType =
			_notificationTypeRegistry.getNotificationType(notificationTypeKey);

		if (notificationType == null) {
			return;
		}

		User user = _userLocalService.getUser(userId);

		long groupId = user.getGroupId();

		Locale siteDefaultLocale = _portal.getSiteDefaultLocale(groupId);

		String body = _formatString(
			notificationTemplate.getBody(user.getLocale()), _BODYFIELD,
			user.getLocale(), notificationType, object);

		if (Validator.isNull(body)) {
			body = _formatString(
				notificationTemplate.getBody(siteDefaultLocale), _BODYFIELD,
				siteDefaultLocale, notificationType, object);
		}

		String fromName = notificationTemplate.getFromName(
			user.getLanguageId());

		if (Validator.isNull(fromName)) {
			fromName = notificationTemplate.getFromName(
				_portal.getSiteDefaultLocale(groupId));
		}

		String subject = _formatString(
			notificationTemplate.getSubject(user.getLocale()), _SUBJECTFIELD,
			user.getLocale(), notificationType, object);

		if (Validator.isNull(subject)) {
			subject = _formatString(
				notificationTemplate.getSubject(siteDefaultLocale),
				_SUBJECTFIELD, siteDefaultLocale, notificationType, object);
		}

		String to = _formatString(
			notificationTemplate.getTo(user.getLocale()), _TOFIELD,
			user.getLocale(), notificationType, object);

		if (Validator.isNull(to)) {
			to = _formatString(
				notificationTemplate.getTo(siteDefaultLocale), _TOFIELD,
				siteDefaultLocale, notificationType, object);
		}

		EmailAddressValidator emailAddressValidator =
			EmailAddressValidatorFactory.getInstance();

		String[] toUserStrings = StringUtil.split(to);

		for (String toUserString : toUserStrings) {
			User toUser = _userLocalService.fetchUser(
				GetterUtil.getLong(toUserString));

			if ((toUser == null) &&
				emailAddressValidator.validate(
					user.getCompanyId(), toUserString)) {

				toUser = _userLocalService.fetchUserByEmailAddress(
					user.getCompanyId(), toUserString);

				if (toUser == null) {
					if (_log.isInfoEnabled()) {
						_log.info("No User found with key: " + toUserString);
					}

					User defaultUser = _userLocalService.getDefaultUser(
						CompanyThreadLocal.getCompanyId());

					_notificationQueueEntryLocalService.
						addNotificationQueueEntry(
							defaultUser.getUserId(),
							notificationTemplate.getNotificationTemplateId(),
							notificationTemplate.getBcc(), body,
							notificationTemplate.getCc(),
							notificationType.getClassName(object),
							notificationType.getClassPK(object),
							notificationTemplate.getFrom(), fromName, 0,
							subject, toUserString, toUserString);

					continue;
				}
			}

			_notificationQueueEntryLocalService.addNotificationQueueEntry(
				userId, notificationTemplate.getNotificationTemplateId(),
				notificationTemplate.getBcc(), body,
				notificationTemplate.getCc(),
				notificationType.getClassName(object),
				notificationType.getClassPK(object),
				notificationTemplate.getFrom(), fromName, 0, subject,
				toUser.getEmailAddress(), notificationTemplate.getName());
		}
	}

	private String _formatString(
			String content, int fieldType, Locale locale,
			NotificationType notificationType, Object object)
		throws PortalException {

		if (Validator.isNull(content)) {
			return StringPool.BLANK;
		}

		Set<String> placeholders = new HashSet<>();

		Matcher matcher = _placeholderPattern.matcher(content);

		while (matcher.find()) {
			placeholders.add(matcher.group());
		}

		List<DefinitionTermContributor> definitionTermContributors =
			new ArrayList<>();

		if (fieldType == _TOFIELD) {
			definitionTermContributors.addAll(
				_definitionTermContributorRegistry.
					getDefinitionTermContributorsByContributorKey(
						DefinitionTermConstants.
							RECIPIENT_DEFINITION_TERMS_CONTRIBUTOR));
		}

		definitionTermContributors.addAll(
			_definitionTermContributorRegistry.
				getDefinitionTermContributorsByNotificationTypeKey(
					notificationType.getKey()));

		for (DefinitionTermContributor definitionTermContributor :
				definitionTermContributors) {

			for (String placeholder : placeholders) {
				content = StringUtil.replace(
					content, placeholder,
					definitionTermContributor.getFilledTerm(
						placeholder, object, locale));
			}
		}

		return content;
	}

	private static final int _BODYFIELD = 2;

	private static final int _SUBJECTFIELD = 1;

	private static final int _TOFIELD = 3;

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationHelperImpl.class);

	private static final Pattern _placeholderPattern = Pattern.compile(
		"\\[%[^\\[%]+%\\]", Pattern.CASE_INSENSITIVE);

	@Reference
	private DefinitionTermContributorRegistry
		_definitionTermContributorRegistry;

	@Reference
	private NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

	@Reference
	private NotificationTypeRegistry _notificationTypeRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}