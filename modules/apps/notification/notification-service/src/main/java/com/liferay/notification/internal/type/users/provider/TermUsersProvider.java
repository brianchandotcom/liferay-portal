/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.type.users.provider;

import com.liferay.notification.constants.NotificationRecipientConstants;
import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.term.evaluator.NotificationTermEvaluator;
import com.liferay.notification.term.evaluator.NotificationTermEvaluatorTracker;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Feliphe Marinho
 */
public class TermUsersProvider implements UsersProvider {

	public TermUsersProvider(
		PermissionCheckerFactory permissionCheckerFactory,
		NotificationTermEvaluatorTracker notificationTermEvaluatorTracker,
		UserLocalService userLocalService) {

		_permissionCheckerFactory = permissionCheckerFactory;
		_notificationTermEvaluatorTracker = notificationTermEvaluatorTracker;
		_userLocalService = userLocalService;
	}

	@Override
	public String getRecipientType() {
		return NotificationRecipientConstants.TYPE_TERM;
	}

	@Override
	public List<User> provide(NotificationContext notificationContext)
		throws PortalException {

		List<User> users = new ArrayList<>();

		List<String> screenNames = new ArrayList<>();
		List<String> terms = new ArrayList<>();

		NotificationTemplate notificationTemplate =
			notificationContext.getNotificationTemplate();

		NotificationRecipient notificationRecipient =
			notificationTemplate.getNotificationRecipient();

		for (NotificationRecipientSetting notificationRecipientSetting :
				notificationRecipient.getNotificationRecipientSettings()) {

			Matcher matcher = _pattern.matcher(
				notificationRecipientSetting.getValue());

			if (matcher.find()) {
				terms.add(notificationRecipientSetting.getValue());
			}
			else {
				screenNames.add(notificationRecipientSetting.getValue());
			}
		}

		users.addAll(
			TransformUtil.unsafeTransform(
				screenNames,
				screenName -> {
					User user = _userLocalService.getUserByScreenName(
						notificationRecipient.getCompanyId(), screenName);

					if (!ModelResourcePermissionUtil.contains(
							_permissionCheckerFactory.create(user),
							notificationContext.getGroupId(),
							notificationContext.getClassName(),
							notificationContext.getClassPK(),
							ActionKeys.VIEW)) {

						return null;
					}

					return user;
				}));

		for (NotificationTermEvaluator notificationTermEvaluator :
				_notificationTermEvaluatorTracker.getNotificationTermEvaluators(
					notificationContext.getClassName())) {

			users.addAll(
				TransformUtil.unsafeTransform(
					terms,
					term -> {
						String termValue = notificationTermEvaluator.evaluate(
							NotificationTermEvaluator.Context.RECIPIENT,
							notificationContext.getTermValues(), term);

						if (Objects.equals(term, termValue)) {
							return null;
						}

						User user = _userLocalService.getUser(
							GetterUtil.getLong(termValue));

						if (!ModelResourcePermissionUtil.contains(
								_permissionCheckerFactory.create(user),
								notificationContext.getGroupId(),
								notificationContext.getClassName(),
								notificationContext.getClassPK(),
								ActionKeys.VIEW)) {

							return null;
						}

						return user;
					}));
		}

		return users;
	}

	private static final Pattern _pattern = Pattern.compile(
		"\\[%[^\\[%]+%\\]", Pattern.CASE_INSENSITIVE);

	private final NotificationTermEvaluatorTracker
		_notificationTermEvaluatorTracker;
	private final PermissionCheckerFactory _permissionCheckerFactory;
	private final UserLocalService _userLocalService;

}