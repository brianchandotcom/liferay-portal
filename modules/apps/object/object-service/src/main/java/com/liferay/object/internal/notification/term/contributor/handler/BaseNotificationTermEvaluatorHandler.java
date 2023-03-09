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
import com.liferay.object.notification.term.evaluator.handler.NotificationTermEvaluatorHandler;
import com.liferay.object.notification.term.evaluator.handler.NotificationTermEvaluatorHandlerTracker;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
public abstract class BaseNotificationTermEvaluatorHandler
	implements NotificationTermEvaluatorHandler {

	@Override
	public String handle(
			String contextName, Map<String, Object> variables,
			ObjectDefinition objectDefinition, String termName)
		throws PortalException {

		if (isTermNameCriteriaMet(objectDefinition, termName)) {
			return evaluate(contextName, variables, objectDefinition, termName);
		}

		if (getNext() == null) {
			return termName;
		}

		NotificationTermEvaluatorHandler notificationTermEvaluatorHandler =
			notificationTermEvaluatorHandlerTracker.
				getNotificationTermEvaluatorHandler(getNext());

		return notificationTermEvaluatorHandler.handle(
			contextName, variables, objectDefinition, termName);
	}

	@Reference
	protected NotificationTermEvaluatorHandlerTracker
		notificationTermEvaluatorHandlerTracker;

}