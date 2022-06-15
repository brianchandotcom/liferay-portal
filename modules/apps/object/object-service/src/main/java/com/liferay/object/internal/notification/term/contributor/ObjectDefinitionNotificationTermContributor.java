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

import com.liferay.notification.term.contributor.NotificationTermContributor;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.notification.term.util.ObjectDefinitionNotificationTermUtil;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Gustavo Lima
 */
public class ObjectDefinitionNotificationTermContributor
	implements NotificationTermContributor {

	public ObjectDefinitionNotificationTermContributor(
		ObjectDefinition objectDefinition,
		ObjectFieldLocalService objectFieldLocalService,
		UserLocalService userLocalService) {

		_objectFieldLocalService = objectFieldLocalService;
		_userLocalService = userLocalService;

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId());

		for (ObjectField objectField : objectFields) {
			_objectFieldIds.put(
				ObjectDefinitionNotificationTermUtil.getObjectFieldTerm(
					objectDefinition.getShortName(), objectField.getName()),
				objectField.getObjectFieldId());
		}
	}

	@Override
	public List<String> getTermNames() {
		return new ArrayList<>(_objectFieldIds.keySet());
	}

	@Override
	public String getTermValue(Locale locale, Object object, String termName)
		throws PortalException {

		if (!(object instanceof Map)) {
			return termName;
		}

		Map<String, Object> termValues = (Map<String, Object>)object;

		if (termName.equals("[%OBJECT_ENTRY_CREATOR%]")) {
			User user = _userLocalService.getUser(
				(long)termValues.get("currentUserId"));

			return user.getFullName(true, true);
		}

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			_objectFieldIds.get(termName));

		if (objectField == null) {
			return termName;
		}

		return String.valueOf(termValues.get(objectField.getName()));
	}

	@Override
	public String getTermValue(String termName, Locale locale) {
		if (termName.equals("[%OBJECT_ENTRY_CREATOR%]")) {
			return LanguageUtil.get(locale, "creator");
		}

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			_objectFieldIds.get(termName));

		return objectField.getLabel(locale);
	}

	private final Map<String, Long> _objectFieldIds = HashMapBuilder.put(
		"[%OBJECT_ENTRY_CREATOR%]", 0L
	).build();
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final UserLocalService _userLocalService;

}