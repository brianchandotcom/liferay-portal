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

package com.liferay.commerce.internal.order.term.contributor;

import com.liferay.commerce.order.CommerceDefinitionTermContributor;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class ObjectRecipientCommerceDefinitionTermContributor
	implements CommerceDefinitionTermContributor {

	public ObjectRecipientCommerceDefinitionTermContributor(
		long objectDefinitionId,
		ObjectFieldLocalService objectFieldLocalService,
		UserGroupLocalService userGroupLocalService,
		UserLocalService userLocalService) {

		_objectDefinitionId = objectDefinitionId;
		_objectFieldLocalService = objectFieldLocalService;
		_userGroupLocalService = userGroupLocalService;
		_userLocalService = userLocalService;

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(_objectDefinitionId);

		for (ObjectField objectField : objectFields) {
			_languageKeys.put(
				StringBundler.concat(
					"[%",
					StringUtil.toUpperCase(
						StringUtil.replace(objectField.getName(), ' ', '_')),
					"%]"),
				objectField.getObjectFieldId());
		}
	}

	@Override
	public String getFilledTerm(String term, Object object, Locale locale)
		throws PortalException {

		ObjectEntry objectEntry = null;

		if (object instanceof ObjectEntry) {
			objectEntry = (ObjectEntry)object;
		}

		if (term.equals("[%OBJECT_ENTRY_CREATOR%]")) {
			return String.valueOf(objectEntry.getUserId());
		}

		if (term.equals("[%OBJECT_ENTRY_ID%]")) {
			return String.valueOf(objectEntry.getObjectEntryId());
		}

		if (term.startsWith("[%USER_GROUP_")) {
			String[] termParts = term.split("_");

			UserGroup userGroup = _userGroupLocalService.getUserGroup(
				objectEntry.getCompanyId(),
				StringUtil.removeChars(termParts[2], '%', ']'));

			return _getUserIds(userGroup);
		}

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			objectEntry.getObjectEntryId());

		if (objectField == null) {
			return term;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing term for object field " + objectField.getName());
		}

		Map<String, Serializable> values = objectEntry.getValues();

		return String.valueOf(values.get(objectField.getName()));
	}

	@Override
	public String getLabel(String term, Locale locale) {
		if (term.equals("[%OBJECT_ENTRY_CREATOR%]")) {
			return LanguageUtil.get(locale, "creator");
		}

		if (term.equals("[%OBJECT_ENTRY_ID%]")) {
			return LanguageUtil.get(locale, "id");
		}

		if (term.equals("[%USER_GROUP_NAME%]")) {
			return LanguageUtil.get(locale, "user-group-name");
		}

		long objectFieldId = _languageKeys.get(term);

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			objectFieldId);

		return objectField.getLabel(locale);
	}

	@Override
	public List<String> getTerms() {
		return new ArrayList<>(_languageKeys.keySet());
	}

	private String _getUserIds(UserGroup userGroup) throws PortalException {
		StringBundler sb = new StringBundler();

		List<User> users = _userLocalService.getUserGroupUsers(
			userGroup.getUserGroupId());

		for (User user : users) {
			sb.append(user.getUserId());
			sb.append(",");
		}

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectRecipientCommerceDefinitionTermContributor.class);

	private final Map<String, Long> _languageKeys = HashMapBuilder.put(
		"[%OBJECT_ENTRY_CREATOR%]", 0L
	).put(
		"[%OBJECT_ENTRY_ID%]", 0L
	).put(
		"[%USER_GROUP_NAME%]", 0L
	).build();
	private final long _objectDefinitionId;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final UserGroupLocalService _userGroupLocalService;
	private final UserLocalService _userLocalService;

}