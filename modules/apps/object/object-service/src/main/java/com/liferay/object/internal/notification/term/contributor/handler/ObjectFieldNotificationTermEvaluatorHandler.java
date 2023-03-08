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

import com.liferay.object.definition.notification.term.util.ObjectDefinitionNotificationTermUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.notification.term.evaluator.constants.NotificationTermEvaluatorConstants;
import com.liferay.object.notification.term.evaluator.handler.NotificationTermEvaluatorHandler;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
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
public class ObjectFieldNotificationTermEvaluatorHandler
	implements NotificationTermEvaluatorHandler {

	@Override
	public String evaluate(
			Map<String, Object> variables, ObjectDefinition objectDefinition,
			String termName)
		throws PortalException {

		Map<String, Long> objectFieldIds =
			_objectFieldIdsDCLSingleton.getSingleton(
				() -> _createObjectFieldIds(objectDefinition));

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			objectFieldIds.get(termName));

		if (objectField == null) {
			return termName;
		}

		Object termValue = variables.get(objectField.getName());

		if (Validator.isNotNull(termValue)) {
			return String.valueOf(termValue);
		}

		return String.valueOf(variables.get(objectField.getDBColumnName()));
	}

	@Override
	public NotificationTermEvaluatorHandler getNext() {
		return null;
	}

	@Override
	public boolean isTermNameCriteriaMet(
		ObjectDefinition objectDefinition, String termName) {

		Set<String> termNames = new HashSet<>();

		for (ObjectField objectField :
				_objectFieldLocalService.getObjectFields(
					objectDefinition.getObjectDefinitionId())) {

			termNames.add(
				ObjectDefinitionNotificationTermUtil.getObjectFieldTermName(
					objectDefinition.getShortName(), objectField.getName()));
		}

		return termNames.contains(termName);
	}

	private Map<String, Long> _createObjectFieldIds(
		ObjectDefinition objectDefinition) {

		Map<String, Long> objectFieldIds = HashMapBuilder.put(
			"[%OBJECT_ENTRY_CREATOR%]", 0L
		).build();

		for (ObjectField objectField :
				_objectFieldLocalService.getObjectFields(
					objectDefinition.getObjectDefinitionId())) {

			objectFieldIds.put(
				ObjectDefinitionNotificationTermUtil.getObjectFieldTermName(
					objectDefinition.getShortName(), objectField.getName()),
				objectField.getObjectFieldId());
		}

		return objectFieldIds;
	}

	private final DCLSingleton<Map<String, Long>> _objectFieldIdsDCLSingleton =
		new DCLSingleton<>();

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}