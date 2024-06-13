/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = CommerceNotificationHelper.class)
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
			String cc, String bcc, String subject, String body, Object object)
		throws PortalException {

		User user = _userLocalService.getGuestUser(
			CompanyThreadLocal.getCompanyId());

		_commerceNotificationQueueEntryLocalService.
			addCommerceNotificationQueueEntry(
				user.getUserId(), groupId,
				commerceNotificationType.getClassName(object),
				commerceNotificationType.getClassPK(object),
				commerceNotificationTemplate.
					getCommerceNotificationTemplateId(),
				commerceNotificationTemplate.getFrom(), fromName,
				toEmailAddress, toFullName, cc, bcc, subject, body, 0);
	}

	private void _addNotificationQueueEntry(
			long groupId, CommerceNotificationType commerceNotificationType,
			CommerceNotificationTemplate commerceNotificationTemplate,
			String fromName, User toUser, String cc, String bcc, String subject,
			String body, Object object)
		throws PortalException {

		_commerceNotificationQueueEntryLocalService.
			addCommerceNotificationQueueEntry(
				toUser.getUserId(), groupId,
				commerceNotificationType.getClassName(object),
				commerceNotificationType.getClassPK(object),
				commerceNotificationTemplate.
					getCommerceNotificationTemplateId(),
				commerceNotificationTemplate.getFrom(), fromName,
				toUser.getEmailAddress(), toUser.getFullName(), cc, bcc,
				subject, body, 0);
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

		if ((fieldType == _FIELD_BCC) || (fieldType == _FIELD_CC) ||
			(fieldType == _FIELD_TO)) {

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

		User user = _userLocalService.getUser(userId);

		String fromName = commerceNotificationTemplate.getFromName(
			user.getLanguageId());

		Locale userLocale = user.getLocale();

		String to = _formatString(
			commerceNotificationType, _FIELD_TO,
			commerceNotificationTemplate.getTo(), object, userLocale);
		String cc = _formatString(
			commerceNotificationType, _FIELD_CC,
			commerceNotificationTemplate.getCc(), object, userLocale);
		String bcc = _formatString(
			commerceNotificationType, _FIELD_BCC,
			commerceNotificationTemplate.getBcc(), object, userLocale);

		if (Validator.isNull(fromName)) {
			fromName = commerceNotificationTemplate.getFromName(
				_portal.getSiteDefaultLocale(
					commerceNotificationTemplate.getGroupId()));
		}

		String subject = _formatString(
			commerceNotificationType, _FIELD_SUBJECT,
			commerceNotificationTemplate.getSubject(userLocale), object,
			userLocale);

		Locale siteDefaultLocale = _portal.getSiteDefaultLocale(
			commerceNotificationTemplate.getGroupId());

		if (Validator.isNull(subject)) {
			subject = _formatString(
				commerceNotificationType, _FIELD_SUBJECT,
				commerceNotificationTemplate.getSubject(siteDefaultLocale),
				object, siteDefaultLocale);
		}

		String body = _formatString(
			commerceNotificationType, _FIELD_BODY,
			commerceNotificationTemplate.getBody(userLocale), object,
			userLocale);

		if (Validator.isNull(body)) {
			body = _formatString(
				commerceNotificationType, _FIELD_BODY,
				commerceNotificationTemplate.getBody(siteDefaultLocale), object,
				siteDefaultLocale);
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

					_addNotificationQueueEntry(
						commerceNotificationTemplate.getGroupId(), commerceNotificationType,
						commerceNotificationTemplate, fromName, toUserString,
						toUserString, cc, bcc, subject, body, object);
				}
				else {
					_addNotificationQueueEntry(
						commerceNotificationTemplate.getGroupId(), commerceNotificationType,
						commerceNotificationTemplate, fromName, toUser, cc, bcc,
						subject, body, object);
				}
			}
			else {
				_addNotificationQueueEntry(
					commerceNotificationTemplate.getGroupId(), commerceNotificationType,
					commerceNotificationTemplate, fromName, toUser, cc, bcc,
					subject, body, object);
			}
		}
	}

	private static final int _FIELD_BCC = 5;

	private static final int _FIELD_BODY = 2;

	private static final int _FIELD_CC = 4;

	private static final int _FIELD_SUBJECT = 1;

	private static final int _FIELD_TO = 3;

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