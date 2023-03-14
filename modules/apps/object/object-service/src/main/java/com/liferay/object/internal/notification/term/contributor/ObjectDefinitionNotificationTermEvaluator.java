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

package com.liferay.object.internal.notification.term.contributor;

import com.liferay.notification.term.evaluator.NotificationTermEvaluator;
import com.liferay.object.definition.notification.term.util.ObjectDefinitionNotificationTermUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.function.UnsafeTriFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Gustavo Lima
 */
public class ObjectDefinitionNotificationTermEvaluator
	implements NotificationTermEvaluator {

	public ObjectDefinitionNotificationTermEvaluator(
		ListTypeLocalService listTypeLocalService,
		ObjectDefinition objectDefinition,
		ObjectFieldLocalService objectFieldLocalService,
		UserLocalService userLocalService) {

		_listTypeLocalService = listTypeLocalService;
		_objectDefinition = objectDefinition;
		_objectFieldLocalService = objectFieldLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	public String evaluate(Context context, Object object, String termName)
		throws PortalException {

		if (!(object instanceof Map)) {
			return termName;
		}

		Map<String, Object> termValues = (Map<String, Object>)object;

		for (UnsafeTriFunction
				<Context, String, Map<String, Object>, String, PortalException>
					evaluatorFunction : _evaluatorsFunctions) {

			String termValue = evaluatorFunction.apply(
				context, termName, termValues);

			if (termValue != null) {
				return termValue;
			}
		}

		return termName;
	}

	private String _evaluateAuthor(
			Context context, String termName, Map<String, Object> termValues)
		throws PortalException {

		String prefix = StringUtil.toUpperCase(
			_objectDefinition.getShortName());

		if (!termName.equals("[%" + prefix + "_CREATOR%]") &&
			!termName.equals("[%" + prefix + "_AUTHOR_EMAIL_ADDRESS%]") &&
			!termName.equals("[%" + prefix + "_AUTHOR_FIRST_NAME%]") &&
			!termName.equals("[%" + prefix + "_AUTHOR_ID%]") &&
			!termName.equals("[%" + prefix + "_AUTHOR_LAST_NAME%]") &&
			!termName.equals("[%" + prefix + "_AUTHOR_MIDDLE_NAME%]") &&
			!termName.equals("[%" + prefix + "_AUTHOR_PREFIX%]") &&
			!termName.equals("[%" + prefix + "_AUTHOR_SUFFIX%]")) {

			return null;
		}

		User user = _userLocalService.getUser(
			GetterUtil.getLong(termValues.get("creator")));

		if (!FeatureFlagManagerUtil.isEnabled("LPS-171625")) {
			if (context.equals(Context.RECIPIENT)) {
				return String.valueOf(termValues.get("creator"));
			}

			return user.getFullName(true, true);
		}

		if (user == null) {
			return null;
		}

		String suffix = StringUtil.removeSubstring(
			termName, "[%" + prefix + "_AUTHOR_");

		if (StringUtil.equals(termName, suffix)) {
			suffix = StringUtil.removeSubstring(termName, "[%" + prefix + "_");
		}

		return _getTermValue(context, suffix, user);
	}

	private String _evaluateCurrentUser(
			Context context, String termName, Map<String, Object> termValues)
		throws PortalException {

		if (!termName.equals("[%CURRENT_USER_EMAIL_ADDRESS%]") &&
			!termName.equals("[%CURRENT_USER_FIRST_NAME%]") &&
			!termName.equals("[%CURRENT_USER_ID%]") &&
			!termName.equals("[%CURRENT_USER_LAST_NAME%]") &&
			!termName.equals("[%CURRENT_USER_MIDDLE_NAME%]") &&
			!termName.equals("[%CURRENT_USER_PREFIX%]") &&
			!termName.equals("[%CURRENT_USER_SUFFIX%]")) {

			return null;
		}

		return _getTermValue(
			context, StringUtil.removeSubstring(termName, "[%CURRENT_USER_"),
			_userLocalService.getUser(
				GetterUtil.getLong(termValues.get("currentUserId"))));
	}

	private String _evaluateObjectFields(
			Context context, String termName, Map<String, Object> termValues)
		throws PortalException {

		if (termName.equals("[%OBJECT_ENTRY_CREATOR%]")) {
			return termName;
		}

		for (ObjectField objectField :
				_objectFieldLocalService.getObjectFields(
					_objectDefinition.getObjectDefinitionId())) {

			if (!Objects.equals(
					ObjectDefinitionNotificationTermUtil.getObjectFieldTermName(
						_objectDefinition.getShortName(),
						objectField.getName()),
					termName)) {

				continue;
			}

			String termValue = (String)termValues.get(objectField.getName());

			if (Validator.isNotNull(termValue)) {
				return termValue;
			}

			return (String)termValues.get(objectField.getDBColumnName());
		}

		return null;
	}

	private String _getTermValue(Context context, String suffix, User user)
		throws PortalException {

		if (suffix.equals("CREATOR%]")) {
			if (context.equals(Context.RECIPIENT)) {
				return String.valueOf(user.getUserId());
			}

			return user.getFullName(true, true);
		}
		else if (suffix.equals("EMAIL_ADDRESS%]")) {
			return user.getEmailAddress();
		}
		else if (suffix.equals("FIRST_NAME%]")) {
			return user.getFirstName();
		}
		else if (suffix.equals("ID%]")) {
			return String.valueOf(user.getUserId());
		}
		else if (suffix.equals("LAST_NAME%]")) {
			return user.getLastName();
		}
		else if (suffix.equals("MIDDLE_NAME%]")) {
			return user.getMiddleName();
		}
		else if (suffix.equals("PREFIX%]") || suffix.equals("SUFFIX%]")) {
			Contact contact = user.fetchContact();

			if (contact == null) {
				return StringPool.BLANK;
			}

			long listTypeId = contact.getPrefixListTypeId();

			if (suffix.equals("SUFFIX%]")) {
				listTypeId = contact.getSuffixListTypeId();
			}

			if (listTypeId == 0) {
				return StringPool.BLANK;
			}

			ListType listType = _listTypeLocalService.getListType(listTypeId);

			return listType.getName();
		}

		return null;
	}

	private final List
		<UnsafeTriFunction
			<Context, String, Map<String, Object>, String, PortalException>>
				_evaluatorsFunctions = Arrays.asList(
					this::_evaluateAuthor, this::_evaluateCurrentUser,
					this::_evaluateObjectFields);
	private final ListTypeLocalService _listTypeLocalService;
	private final ObjectDefinition _objectDefinition;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final UserLocalService _userLocalService;

}