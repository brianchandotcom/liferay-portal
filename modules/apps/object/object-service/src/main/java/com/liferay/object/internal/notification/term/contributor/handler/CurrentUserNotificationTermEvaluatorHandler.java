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

package com.liferay.object.internal.notification.term.contributor.handler;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.notification.term.evaluator.constants.NotificationTermEvaluatorHandlerConstants;
import com.liferay.object.notification.term.evaluator.handler.NotificationTermEvaluatorHandler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ListTypeService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Paulo Albuquerque
 */
@Component(
	property = "notification.term.evaluator.handler.type=" + NotificationTermEvaluatorHandlerConstants.TYPE_CURRENT_USER,
	service = NotificationTermEvaluatorHandler.class
)
public class CurrentUserNotificationTermEvaluatorHandler
	extends BaseNotificationTermEvaluatorHandler {

	@Override
	public String evaluate(
			Map<String, Object> variables, ObjectDefinition objectDefinition,
			String termName)
		throws PortalException {

		User user = _userLocalService.getUser(
			GetterUtil.getLong(variables.get("currentUser")));

		Map<String, String> termValues = HashMapBuilder.put(
			"[%CURRENT_USER_EMAIL_ADDRESS%]", user.getEmailAddress()
		).put(
			"[%CURRENT_USER_FIRST_NAME%]", user.getFirstName()
		).put(
			"[%CURRENT_USER_ID%]", String.valueOf(user.getUserId())
		).put(
			"[%CURRENT_USER_LAST_NAME%]", user.getLastName()
		).put(
			"[%CURRENT_USER_MIDDLE_NAME%]", user.getMiddleName()
		).put(
			"[%CURRENT_USER_PREFIX%]", _getListTypeName(true, user)
		).put(
			"[%CURRENT_USER_SUFFIX%]", _getListTypeName(false, user)
		).build();

		return termValues.get(termName);
	}

	@Override
	public String getNext() {
		return NotificationTermEvaluatorHandlerConstants.TYPE_AUTHOR;
	}

	@Override
	public boolean isTermNameCriteriaMet(
		ObjectDefinition objectDefinition, String termName) {

		return _termNames.contains(termName);
	}

	private String _getListTypeName(boolean prefix, User user)
		throws PortalException {

		Contact contact = user.fetchContact();

		if (contact == null) {
			return StringPool.BLANK;
		}

		long listTypeId =
			prefix ? contact.getPrefixListTypeId() :
				contact.getSuffixListTypeId();

		if (listTypeId == 0) {
			return StringPool.BLANK;
		}

		ListType listType = _listTypeService.getListType(listTypeId);

		return listType.getName();
	}

	private static final Set<String> _termNames = Collections.unmodifiableSet(
		SetUtil.fromArray(
			"[%CURRENT_USER_EMAIL_ADDRESS%]", "[%CURRENT_USER_FIRST_NAME%]",
			"[%CURRENT_USER_ID%]", "[%CURRENT_USER_LAST_NAME%]",
			"[%CURRENT_USER_MIDDLE_NAME%]", "[%CURRENT_USER_PREFIX%]",
			"[%CURRENT_USER_SUFFIX%]"));

	@Reference
	private ListTypeService _listTypeService;

	@Reference
	private UserLocalService _userLocalService;

}