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
import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author Gustavo Lima
 */
public class ObjectDefinitionNotificationTermEvaluator
	implements NotificationTermEvaluator {

	public ObjectDefinitionNotificationTermEvaluator(
		ObjectDefinition objectDefinition,
		ObjectFieldLocalService objectFieldLocalService,
		UserLocalService userLocalService) {

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

		User user = null;

		String prefix = StringUtil.toUpperCase(
			_objectDefinition.getShortName());

		Set<String> creatorTermNames = Collections.unmodifiableSet(
			SetUtil.fromArray(
				"[%" + prefix + "_AUTHOR_EMAIL_ADDRESS%]",
				"[%" + prefix + "_AUTHOR_FIRST_NAME%]",
				"[%" + prefix + "_AUTHOR_ID%]",
				"[%" + prefix + "_AUTHOR_LAST_NAME%]",
				"[%" + prefix + "_AUTHOR_MIDDLE_NAME%]",
				"[%" + prefix + "_AUTHOR_PREFIX%]",
				"[%" + prefix + "_AUTHOR_SUFFIX%]"));

		if (creatorTermNames.contains(termName)) {
			user = _userLocalService.getUser(
				GetterUtil.getLong(termValues.get("creator")));

			if (!FeatureFlagManagerUtil.isEnabled("LPS-171625")) {
				if (context.equals(Context.RECIPIENT)) {
					return String.valueOf(termValues.get("creator"));
				}

				return user.getFullName(true, true);
			}
		}

		if (_currentUserTermName.contains(termName)) {
			user = _userLocalService.getUser(
				GetterUtil.getLong(termValues.get("currentUserId")));
		}

		if (user != null) {
			Map<String, String> userTermValuesMap = _getUserTermValuesMap(user);

			return userTermValuesMap.get(
				StringUtil.removeSubstring(
					StringUtil.extractLast(termName, StringPool.UNDERLINE),
					"%]"));
		}

		Map<String, Long> objectFieldIds =
			_objectFieldIdsDCLSingleton.getSingleton(
				this::_createObjectFieldIds);

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			objectFieldIds.get(termName));

		if (objectField == null) {
			return termName;
		}

		Object termValue = termValues.get(objectField.getName());

		if (Validator.isNotNull(termValue)) {
			return String.valueOf(termValue);
		}

		return String.valueOf(termValues.get(objectField.getDBColumnName()));
	}

	private Map<String, Long> _createObjectFieldIds() {
		Map<String, Long> objectFieldIds = HashMapBuilder.put(
			"[%OBJECT_ENTRY_CREATOR%]", 0L
		).build();

		for (ObjectField objectField :
				_objectFieldLocalService.getObjectFields(
					_objectDefinition.getObjectDefinitionId())) {

			objectFieldIds.put(
				ObjectDefinitionNotificationTermUtil.getObjectFieldTermName(
					_objectDefinition.getShortName(), objectField.getName()),
				objectField.getObjectFieldId());
		}

		return objectFieldIds;
	}

	private String _getListTypeName(boolean prefix, User user)
		throws PortalException {

		Contact contact = user.fetchContact();

		if (contact == null) {
			return StringPool.BLANK;
		}

		if (prefix) {
			if (Validator.isNull(contact.getPrefixListTypeId())) {
				return StringPool.BLANK;
			}

			ListType listType = ListTypeServiceUtil.getListType(
				contact.getPrefixListTypeId());

			return listType.getName();
		}

		if (Validator.isNull(contact.getSuffixListTypeId())) {
			return StringPool.BLANK;
		}

		ListType listType = ListTypeServiceUtil.getListType(
			contact.getSuffixListTypeId());

		return listType.getName();
	}

	private Map<String, String> _getUserTermValuesMap(User user)
		throws PortalException {

		return HashMapBuilder.put(
			"EMAIL_ADDRESS", user.getEmailAddress()
		).put(
			"FIRST_NAME", user.getFirstName()
		).put(
			"ID", String.valueOf(user.getUserId())
		).put(
			"LAST_NAME", user.getLastName()
		).put(
			"MIDDLE_NAME", user.getMiddleName()
		).put(
			"PREFIX", _getListTypeName(true, user)
		).put(
			"SUFFIX", _getListTypeName(false, user)
		).build();
	}

	private static final Set<String> _currentUserTermName =
		Collections.unmodifiableSet(
			SetUtil.fromArray(
				"[%CURRENT_USER_EMAIL_ADDRESS%]", "[%CURRENT_USER_FIRST_NAME%]",
				"[%CURRENT_USER_ID%]", "[%CURRENT_USER_LAST_NAME%]",
				"[%CURRENT_USER_MIDDLE_NAME%]", "[%CURRENT_USER_PREFIX%]",
				"[%CURRENT_USER_SUFFIX%]"));

	private final ObjectDefinition _objectDefinition;
	private final DCLSingleton<Map<String, Long>> _objectFieldIdsDCLSingleton =
		new DCLSingleton<>();
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final UserLocalService _userLocalService;

}