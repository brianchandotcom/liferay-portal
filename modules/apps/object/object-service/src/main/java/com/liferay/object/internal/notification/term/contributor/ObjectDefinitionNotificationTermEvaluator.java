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
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.notification.term.evaluator.constants.NotificationTermEvaluatorHandlerConstants;
import com.liferay.object.notification.term.evaluator.handler.NotificationTermEvaluatorHandler;
import com.liferay.object.notification.term.evaluator.handler.NotificationTermEvaluatorHandlerTracker;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;

/**
 * @author Gustavo Lima
 */
public class ObjectDefinitionNotificationTermEvaluator
	implements NotificationTermEvaluator {

	public ObjectDefinitionNotificationTermEvaluator(
		NotificationTermEvaluatorHandlerTracker
			notificationTermEvaluatorHandlerTracker,
		ObjectDefinition objectDefinition) {

		_notificationTermEvaluatorHandlerTracker =
			notificationTermEvaluatorHandlerTracker;
		_objectDefinition = objectDefinition;
	}

	@Override
	public String evaluate(Context context, Object object, String termName)
		throws PortalException {

		if (!(object instanceof Map)) {
			return termName;
		}

		NotificationTermEvaluatorHandler notificationTermEvaluatorHandler =
			_notificationTermEvaluatorHandlerTracker.
				getNotificationTermEvaluatorHandler(
					NotificationTermEvaluatorHandlerConstants.
						FIRST_HANDLER_TYPE);

		return notificationTermEvaluatorHandler.handle(
			context.name(), (Map<String, Object>)object, _objectDefinition,
			termName);
	}

	private final NotificationTermEvaluatorHandlerTracker
		_notificationTermEvaluatorHandlerTracker;
	private final ObjectDefinition _objectDefinition;

}