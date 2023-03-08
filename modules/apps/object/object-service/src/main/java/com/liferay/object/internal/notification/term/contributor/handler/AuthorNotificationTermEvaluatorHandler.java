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
import com.liferay.object.notification.term.evaluator.constants.NotificationTermEvaluatorConstants;
import com.liferay.object.notification.term.evaluator.handler.NotificationTermEvaluatorHandler;
import com.liferay.object.notification.term.evaluator.handler.NotificationTermEvaluatorHandlerTracker;
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
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Paulo Albuquerque
 */
@Component(
	property = "notification.term.evaluator.handler=" + NotificationTermEvaluatorConstants.AUTHOR,
	service = NotificationTermEvaluatorHandler.class
)
public class AuthorNotificationTermEvaluatorHandler
	implements NotificationTermEvaluatorHandler {

	@Override
	public String evaluate(
			Map<String, Object> variables, ObjectDefinition objectDefinition,
			String termName)
		throws PortalException {

		User user = _userLocalService.getUser(
			GetterUtil.getLong(variables.get("currentUser")));

		String prefix = StringUtil.toUpperCase(objectDefinition.getShortName());

		Map<String, String> termValues = HashMapBuilder.put(
			"[%" + prefix + "EMAIL_ADDRESS%]", user.getEmailAddress()
		).put(
			"[%" + prefix + "FIRST_NAME%]", user.getFirstName()
		).put(
			"[%" + prefix + "ID%]", String.valueOf(user.getUserId())
		).put(
			"[%" + prefix + "LAST_NAME%]", user.getLastName()
		).put(
			"[%" + prefix + "MIDDLE_NAME%]", user.getMiddleName()
		).put(
			"[%" + prefix + "PREFIX%]", _getListTypeName(true, user)
		).put(
			"[%" + prefix + "SUFFIX%]", _getListTypeName(false, user)
		).build();

		return termValues.get(termName);
	}

	@Override
	public NotificationTermEvaluatorHandler getNext() {
		return _notificationTermEvaluatorHandlerTracker.
			getNotificationTermEvaluatorHandler(
				NotificationTermEvaluatorConstants.OBJECT_FIELD);
	}

	@Override
	public boolean isTermNameCriteriaMet(
		ObjectDefinition objectDefinition, String termName) {

		String prefix = StringUtil.toUpperCase(objectDefinition.getShortName());

		Set<String> termNames = Collections.unmodifiableSet(
			SetUtil.fromArray(
				"[%" + prefix + "_CREATOR%]",
				"[%" + prefix + "_AUTHOR_EMAIL_ADDRESS%]",
				"[%" + prefix + "_AUTHOR_FIRST_NAME%]",
				"[%" + prefix + "_AUTHOR_ID%]",
				"[%" + prefix + "_AUTHOR_LAST_NAME%]",
				"[%" + prefix + "_AUTHOR_MIDDLE_NAME%]",
				"[%" + prefix + "_AUTHOR_PREFIX%]",
				"[%" + prefix + "_AUTHOR_SUFFIX%]"));

		return termNames.contains(termName);
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

	@Reference
	private ListTypeService _listTypeService;

	@Reference
	private NotificationTermEvaluatorHandlerTracker
		_notificationTermEvaluatorHandlerTracker;

	@Reference
	private UserLocalService _userLocalService;

}