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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

		for (Subevaluator subevaluator : _subevaluators) {
			String termValue = subevaluator.evaluate(
				context, termName, termValues);

			if (termValue != null) {
				return termValue;
			}
		}

		return termName;
	}

	private String _getTermValue(
			Context context, String partialObjectFieldName, User user)
		throws PortalException {

		if (Objects.equals(partialObjectFieldName, "CREATOR")) {
			if (context.equals(Context.RECIPIENT)) {
				String.valueOf(user.getUserId());
			}

			return user.getFullName(true, true);
		}
		else if (Objects.equals(partialObjectFieldName, "EMAIL_ADDRESS")) {
			return user.getEmailAddress();
		}
		else if (Objects.equals(partialObjectFieldName, "FIRST_NAME")) {
			return user.getFirstName();
		}
		else if (Objects.equals(partialObjectFieldName, "ID")) {
			return String.valueOf(user.getUserId());
		}
		else if (Objects.equals(partialObjectFieldName, "LAST_NAME")) {
			return user.getLastName();
		}
		else if (Objects.equals(partialObjectFieldName, "MIDDLE_NAME")) {
			return user.getMiddleName();
		}
		else if (Objects.equals(partialObjectFieldName, "PREFIX") ||
				 Objects.equals(partialObjectFieldName, "SUFFIX")) {

			Contact contact = user.fetchContact();

			if (contact == null) {
				return StringPool.BLANK;
			}

			long listTypeId = contact.getPrefixListTypeId();

			if (Objects.equals(partialObjectFieldName, "SUFFIX")) {
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

	private final ListTypeLocalService _listTypeLocalService;
	private final ObjectDefinition _objectDefinition;
	private final ObjectFieldLocalService _objectFieldLocalService;

	private final List<Subevaluator> _subevaluators = ListUtil.fromArray(
		new Subevaluator() {

			public String evaluate(
					Context context, String termName,
					Map<String, Object> termValues)
				throws PortalException {

				User user = null;

				String prefix = StringUtil.toUpperCase(
					_objectDefinition.getShortName());

				Set<String> creatorTermNames = SetUtil.fromArray(
					"[%" + prefix + "_CREATOR%]",
					"[%" + prefix + "_AUTHOR_EMAIL_ADDRESS%]",
					"[%" + prefix + "_AUTHOR_FIRST_NAME%]",
					"[%" + prefix + "_AUTHOR_ID%]",
					"[%" + prefix + "_AUTHOR_LAST_NAME%]",
					"[%" + prefix + "_AUTHOR_MIDDLE_NAME%]",
					"[%" + prefix + "_AUTHOR_PREFIX%]",
					"[%" + prefix + "_AUTHOR_SUFFIX%]");

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

				if (user == null) {
					return null;
				}

				return _getTermValue(
					context,
					StringUtil.removeSubstring(
						StringUtil.extractLast(termName, StringPool.UNDERLINE),
						"%]"),
					user);
			}

		},
		new Subevaluator() {

			public String evaluate(
					Context context, String termName,
					Map<String, Object> termValues)
				throws PortalException {

				if (!_termNames.contains(termName)) {
					return null;
				}

				return _getTermValue(
					context,
					StringUtil.removeSubstring(
						StringUtil.extractLast(termName, StringPool.UNDERLINE),
						"%]"),
					_userLocalService.getUser(
						GetterUtil.getLong(termValues.get("currentUserId"))));
			}

			private final Set<String> _termNames = SetUtil.fromArray(
				"[%CURRENT_USER_EMAIL_ADDRESS%]", "[%CURRENT_USER_FIRST_NAME%]",
				"[%CURRENT_USER_ID%]", "[%CURRENT_USER_LAST_NAME%]",
				"[%CURRENT_USER_MIDDLE_NAME%]", "[%CURRENT_USER_PREFIX%]",
				"[%CURRENT_USER_SUFFIX%]");

		},
		new Subevaluator() {

			public String evaluate(
					Context context, String termName,
					Map<String, Object> termValues)
				throws PortalException {

				if (termName.equals("[%OBJECT_ENTRY_CREATOR%]")) {
					return termName;
				}

				for (ObjectField objectField :
						_objectFieldLocalService.getObjectFields(
							_objectDefinition.getObjectDefinitionId())) {

					if (!Objects.equals(
							ObjectDefinitionNotificationTermUtil.
								getObjectFieldTermName(
									_objectDefinition.getShortName(),
									objectField.getName()),
							termName)) {

						continue;
					}

					String termValue = (String)termValues.get(
						objectField.getName());

					if (Validator.isNotNull(termValue)) {
						return termValue;
					}

					return (String)termValues.get(
						objectField.getDBColumnName());
				}

				return null;
			}

		});

	private final UserLocalService _userLocalService;

	private interface Subevaluator {

		public String evaluate(
				Context context, String termName,
				Map<String, Object> termValues)
			throws PortalException;

	}

}