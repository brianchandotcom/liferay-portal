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

import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel;
import com.liferay.commerce.notification.service.CommerceNotificationQueueEntryLocalService;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateLocalService;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateUserSegmentRelLocalService;
import com.liferay.commerce.notification.type.CommerceNotificationType;
import com.liferay.commerce.notification.type.CommerceNotificationTypeRegistry;
import com.liferay.commerce.notification.util.CommerceNotificationHelper;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalService;
import com.liferay.commerce.user.segment.util.CommerceUserSegmentHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true)
public class CommerceNotificationHelperImpl
	implements CommerceNotificationHelper {

	@Override
	public void sendNotifications(String key, Object object)
		throws PortalException {

		sendNotifications(key, object, null);
	}

	@Override
	public void sendNotifications(String key, Object object, long[] userIds)
		throws PortalException {

		CommerceNotificationType commerceNotificationType =
			_commerceNotificationTypeRegistry.getCommerceNotificationType(key);

		GroupedModel groupedModel = (GroupedModel)object;

		List<CommerceNotificationTemplate> commerceNotificationTemplates =
			_commerceNotificationTemplateLocalService.
				getCommerceNotificationTemplates(
					groupedModel.getGroupId(),
					commerceNotificationType.getKey(), true);

		for (CommerceNotificationTemplate commerceNotificationTemplate :
				commerceNotificationTemplates) {

			List<CommerceNotificationTemplateUserSegmentRel>
				commerceNotificationTemplateUserSegmentRels =
					_commerceNotificationTemplateUserSegmentRelLocalService.
						getCommerceNotificationTemplateUserSegmentRels(
							commerceNotificationTemplate.
								getCommerceNotificationTemplateId(),
							QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			long[] commerceUserSegmentEntryIds = ListUtil.toLongArray(
				commerceNotificationTemplateUserSegmentRels,
				CommerceNotificationTemplateUserSegmentRel.
					COMMERCE_USER_SEGMENT_ENTRY_ID_ACCESSOR);

			if (ArrayUtil.isEmpty(userIds)) {
				sendNotificationsToUserSegmentUsers(
					commerceUserSegmentEntryIds, commerceNotificationTemplate,
					commerceNotificationType, object);
			}
			else {
				sendNotificationsToUserIds(
					userIds, commerceUserSegmentEntryIds,
					commerceNotificationTemplate, commerceNotificationType,
					object);
			}
		}
	}

	protected String formatString(
			CommerceNotificationType commerceNotificationType, String content,
			Object object)
		throws PortalException {

		if (Validator.isNull(content)) {
			return StringPool.BLANK;
		}

		Set<String> placeholders = new HashSet<>();

		Matcher matcher = _placeholderPattern.matcher(content);

		while (matcher.find()) {
			placeholders.add(matcher.group());
		}

		for (String placeholder : placeholders) {
			content = content.replace(
				placeholder,
				commerceNotificationType.getFilledTerm(placeholder, object));
		}

		return content;
	}

	protected void sendNotification(
			CommerceNotificationTemplate commerceNotificationTemplate,
			CommerceNotificationType commerceNotificationType, Object object,
			long userId)
		throws PortalException {

		long groupId = commerceNotificationTemplate.getGroupId();

		User user = _userLocalService.getUser(userId);

		String subject = commerceNotificationTemplate.getSubject(
			user.getLanguageId());
		String body = commerceNotificationTemplate.getBody(
			user.getLanguageId());

		if (Validator.isNull(subject)) {
			subject = commerceNotificationTemplate.getSubject(
				_portal.getSiteDefaultLocale(groupId));
		}

		if (Validator.isNull(body)) {
			body = commerceNotificationTemplate.getBody(
				_portal.getSiteDefaultLocale(groupId));
		}

		subject = formatString(commerceNotificationType, subject, object);
		body = formatString(commerceNotificationType, body, object);

		_commerceNotificationQueueEntryLocalService.addCommerceNotificationQueueEntry(
			user.getUserId(), groupId,
			commerceNotificationTemplate.getCommerceNotificationTemplateId(),
			commerceNotificationTemplate.getFrom(),
			commerceNotificationTemplate.getFromName(), user.getEmailAddress(),
			user.getFullName(), commerceNotificationTemplate.getCc(),
			commerceNotificationTemplate.getBcc(), subject, body, 0);
	}

	protected void sendNotificationsToUserIds(
			long[] userIds, long[] commerceUserSegmentEntryIds,
			CommerceNotificationTemplate commerceNotificationTemplate,
			CommerceNotificationType commerceNotificationType, Object object)
		throws PortalException {

		for (long userId : userIds) {
			long[] userCommerceUserSegmentEntryIds =
				_commerceUserSegmentEntryLocalService.
					getCommerceUserSegmentEntryIds(
						commerceNotificationTemplate.getGroupId(),
						OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
						userId);

			if (ArrayUtil.containsAll(
					userCommerceUserSegmentEntryIds,
					commerceUserSegmentEntryIds)) {

				sendNotification(
					commerceNotificationTemplate, commerceNotificationType,
					object, userId);
			}
		}
	}

	protected void sendNotificationsToUserSegmentUsers(
			long[] commerceUserSegmentEntryIds,
			CommerceNotificationTemplate commerceNotificationTemplate,
			CommerceNotificationType commerceNotificationType, Object object)
		throws PortalException {

		long[] userIds = _commerceUserSegmentHelper.getUserIds(
			commerceNotificationTemplate.getGroupId(),
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			commerceUserSegmentEntryIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (long userId : userIds) {
			sendNotification(
				commerceNotificationTemplate, commerceNotificationType, object,
				userId);
		}
	}

	private static final Pattern _placeholderPattern = Pattern.compile(
		"\\[%[^\\[%]+%\\]", Pattern.CASE_INSENSITIVE);

	@Reference
	private CommerceNotificationQueueEntryLocalService
		_commerceNotificationQueueEntryLocalService;

	@Reference
	private CommerceNotificationTemplateLocalService
		_commerceNotificationTemplateLocalService;

	@Reference
	private CommerceNotificationTemplateUserSegmentRelLocalService
		_commerceNotificationTemplateUserSegmentRelLocalService;

	@Reference
	private CommerceNotificationTypeRegistry _commerceNotificationTypeRegistry;

	@Reference
	private CommerceUserSegmentEntryLocalService
		_commerceUserSegmentEntryLocalService;

	@Reference
	private CommerceUserSegmentHelper _commerceUserSegmentHelper;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}