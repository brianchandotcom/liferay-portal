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

import com.liferay.notification.term.contributor.DefinitionTermContributor;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Gustavo Lima
 */
public class ObjectDefinitionTermContributor
	implements DefinitionTermContributor {

	public ObjectDefinitionTermContributor(
		long objectDefinitionId,
		ObjectFieldLocalService objectFieldLocalService,
		UserLocalService userLocalService) {

		_objectDefinitionId = objectDefinitionId;
		_objectFieldLocalService = objectFieldLocalService;
		_userLocalService = userLocalService;

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(_objectDefinitionId);

		for (ObjectField objectField : objectFields) {
			_objectFieldIds.put(
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

		if (!(object instanceof ObjectEntry)) {
			return term;
		}

		ObjectEntry objectEntry = (ObjectEntry)object;

		if (term.equals("[%OBJECT_ENTRY_CREATOR%]")) {
			User user = _userLocalService.getUser(objectEntry.getUserId());

			return user.getFullName(true, true);
		}

		if (term.equals("[%OBJECT_ENTRY_ID%]")) {
			return String.valueOf(objectEntry.getObjectEntryId());
		}

		long objectEntryId = _objectFieldIds.get(term);

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			objectEntryId);

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

		long objectFieldId = _objectFieldIds.get(term);

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			objectFieldId);

		return objectField.getLabel(locale);
	}

	@Override
	public List<String> getTerms() {
		return new ArrayList<>(_objectFieldIds.keySet());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectDefinitionTermContributor.class);

	private final long _objectDefinitionId;
	private final Map<String, Long> _objectFieldIds = HashMapBuilder.put(
		"[%OBJECT_ENTRY_CREATOR%]", 0L
	).put(
		"[%OBJECT_ENTRY_ID%]", 0L
	).build();
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final UserLocalService _userLocalService;

}