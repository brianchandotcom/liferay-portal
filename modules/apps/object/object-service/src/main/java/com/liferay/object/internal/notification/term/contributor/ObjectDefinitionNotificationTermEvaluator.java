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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

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

		if (termName.contains("_CREATOR")) {
			if (context.equals(Context.RECIPIENT)) {
				return String.valueOf(termValues.get("creator"));
			}

			User user = _userLocalService.getUser(
				GetterUtil.getLong(termValues.get("creator")));

			return user.getFullName(true, true);
		}

		Map<String, Long> objectFieldIds = _getObjectFieldIds();

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

	private Map<String, Long> _getObjectFieldIds() {
		Map<String, Long> objectFieldIds = _objectFieldIds;

		if (objectFieldIds != null) {
			return objectFieldIds;
		}

		synchronized (this) {
			if (_objectFieldIds != null) {
				return _objectFieldIds;
			}

			objectFieldIds = HashMapBuilder.put(
				"[%OBJECT_ENTRY_CREATOR%]", 0L
			).build();

			for (ObjectField objectField :
					_objectFieldLocalService.getObjectFields(
						_objectDefinition.getObjectDefinitionId())) {

				objectFieldIds.put(
					ObjectDefinitionNotificationTermUtil.getObjectFieldTermName(
						_objectDefinition.getShortName(),
						objectField.getName()),
					objectField.getObjectFieldId());
			}

			_objectFieldIds = objectFieldIds;
		}

		return objectFieldIds;
	}

	private final ObjectDefinition _objectDefinition;
	private volatile Map<String, Long> _objectFieldIds;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final UserLocalService _userLocalService;

}