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

package com.liferay.commerce.notification.internal.util;

import com.liferay.commerce.constants.CommerceDefinitionTermConstants;
import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.service.CommerceNotificationQueueEntryLocalService;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateLocalService;
import com.liferay.commerce.notification.type.CommerceNotificationType;
import com.liferay.commerce.notification.type.CommerceNotificationTypeRegistry;
import com.liferay.commerce.notification.util.CommerceNotificationHelper;
import com.liferay.commerce.order.CommerceDefinitionTermContributor;
import com.liferay.commerce.order.CommerceDefinitionTermContributorRegistry;
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
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	service = CommerceNotificationHelper.class
)
public class CommerceNotificationHelperImpl
	implements CommerceNotificationHelper {

	@Override
	public void sendNotifications(
			long groupId, long userId, String key, Object object)
		throws PortalException {

		if (Validator.isBlank(key)) {
			return;
		}

		CommerceNotificationType commerceNotificationType =
			_commerceNotificationTypeRegistry.getCommerceNotificationType(key);

		if (commerceNotificationType == null) {
			return;
		}

		List<CommerceNotificationTemplate> commerceNotificationTemplates =
			_commerceNotificationTemplateLocalService.
				getCommerceNotificationTemplates(
					groupId, commerceNotificationType.getKey(), true);

		for (CommerceNotificationTemplate commerceNotificationTemplate :
				commerceNotificationTemplates) {

			_sendNotification(
				userId, commerceNotificationTemplate, commerceNotificationType,
				object);
		}
	}

	private void _addNotificationQueueEntry(
			long groupId, CommerceNotificationType commerceNotificationType,
			CommerceNotificationTemplate commerceNotificationTemplate,
			String fromName, String toEmailAddress, String toFullName,
			String subject, String body, Object object)
		throws PortalException {

		User user = _userLocalService.getDefaultUser(
			CompanyThreadLocal.getCompanyId());

		_commerceNotificationQueueEntryLocalService.
			addCommerceNotificationQueueEntry(
				user.getUserId(), groupId,
				commerceNotificationType.getClassName(object),
				commerceNotificationType.getClassPK(object),
				commerceNotificationTemplate.
					getCommerceNotificationTemplateId(),
				commerceNotificationTemplate.getFrom(), fromName,
				toEmailAddress, toFullName,
				commerceNotificationTemplate.getCc(),
				commerceNotificationTemplate.getBcc(), subject, body, 0);
	}

	private void _addNotificationQueueEntry(
			long groupId, CommerceNotificationType commerceNotificationType,
			CommerceNotificationTemplate commerceNotificationTemplate,
			String fromName, User toUser, String subject, String body,
			Object object)
		throws PortalException {

		_commerceNotificationQueueEntryLocalService.
			addCommerceNotificationQueueEntry(
				toUser.getUserId(), groupId,
				commerceNotificationType.getClassName(object),
				commerceNotificationType.getClassPK(object),
				commerceNotificationTemplate.
					getCommerceNotificationTemplateId(),
				commerceNotificationTemplate.getFrom(), fromName,
				toUser.getEmailAddress(), toUser.getFullName(),
				commerceNotificationTemplate.getCc(),
				commerceNotificationTemplate.getBcc(), subject, body, 0);
	}

	private String _formatString(
			CommerceNotificationType commerceNotificationType, int fieldType,
			String content, Object object, Locale locale)
		throws PortalException {

		if (Validator.isNull(content)) {
			return StringPool.BLANK;
		}

		Set<String> placeholders = new HashSet<>();

		Matcher matcher = _placeholderPattern.matcher(content);

		while (matcher.find()) {
			placeholders.add(matcher.group());
		}

		List<CommerceDefinitionTermContributor> definitionTermContributors =
			new ArrayList<>();

		if (fieldType == _TOFIELD) {
			definitionTermContributors.addAll(
				_commerceDefinitionTermContributorRegistry.
					getDefinitionTermContributorsByContributorKey(
						CommerceDefinitionTermConstants.
							RECIPIENT_DEFINITION_TERMS_CONTRIBUTOR));
		}

		definitionTermContributors.addAll(
			_commerceDefinitionTermContributorRegistry.
				getDefinitionTermContributorsByNotificationTypeKey(
					commerceNotificationType.getKey()));

		for (CommerceDefinitionTermContributor definitionTermContributor :
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

	private void _sendNotification(
			long userId,
			CommerceNotificationTemplate commerceNotificationTemplate,
			CommerceNotificationType commerceNotificationType, Object object)
		throws PortalException {

		long groupId = commerceNotificationTemplate.getGroupId();

		User user = _userLocalService.getUser(userId);

		Locale siteDefaultLocale = _portal.getSiteDefaultLocale(groupId);
		Locale userLocale = user.getLocale();

		String fromName = commerceNotificationTemplate.getFromName(
			user.getLanguageId());

		String subject = _formatString(
			commerceNotificationType, _SUBJECTFIELD,
			commerceNotificationTemplate.getSubject(userLocale), object,
			userLocale);
		String body = _formatString(
			commerceNotificationType, _BODYFIELD,
			commerceNotificationTemplate.getBody(userLocale), object,
			userLocale);

		if (Validator.isNull(fromName)) {
			fromName = commerceNotificationTemplate.getFromName(
				_portal.getSiteDefaultLocale(groupId));
		}

		if (Validator.isNull(subject)) {
			subject = _formatString(
				commerceNotificationType, _SUBJECTFIELD,
				commerceNotificationTemplate.getSubject(siteDefaultLocale),
				object, siteDefaultLocale);
		}

		if (Validator.isNull(body)) {
			_formatString(
				commerceNotificationType, _BODYFIELD,
				commerceNotificationTemplate.getBody(siteDefaultLocale), object,
				siteDefaultLocale);
		}

		String to = _formatString(
			commerceNotificationType, _TOFIELD,
			commerceNotificationTemplate.getTo(), object, userLocale);

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

					_addNotificationQueueEntry(
						groupId, commerceNotificationType,
						commerceNotificationTemplate, fromName, toUserString,
						toUserString, subject, body, object);
				}
				else {
					_addNotificationQueueEntry(
						groupId, commerceNotificationType,
						commerceNotificationTemplate, fromName, toUser, subject,
						body, object);
				}
			}
			else {
				_addNotificationQueueEntry(
					groupId, commerceNotificationType,
					commerceNotificationTemplate, fromName, toUser, subject,
					body, object);
			}
		}
	}

	private static final int _BODYFIELD = 2;

	private static final int _SUBJECTFIELD = 1;

	private static final int _TOFIELD = 3;

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceNotificationHelperImpl.class);

	private static final Pattern _placeholderPattern = Pattern.compile(
		"\\[%[^\\[%]+%\\]", Pattern.CASE_INSENSITIVE);

	@Reference
	private CommerceDefinitionTermContributorRegistry
		_commerceDefinitionTermContributorRegistry;

	@Reference
	private CommerceNotificationQueueEntryLocalService
		_commerceNotificationQueueEntryLocalService;

	@Reference
	private CommerceNotificationTemplateLocalService
		_commerceNotificationTemplateLocalService;

	@Reference
	private CommerceNotificationTypeRegistry _commerceNotificationTypeRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}