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

package com.liferay.object.internal.notification.handler;

import com.liferay.notification.handler.NotificationHandler;
import com.liferay.object.model.ObjectDefinition;

import java.util.Locale;

/**
 * @author Feliphe Marinho
 */
public class ObjectDefinitionNotificationHandler
	implements NotificationHandler {

	public ObjectDefinitionNotificationHandler(
		ObjectDefinition objectDefinition) {

		_objectDefinition = objectDefinition;
	}

	@Override
	public String getTriggerBy(Locale locale) {
		return _objectDefinition.getShortName();
	}

	private final ObjectDefinition _objectDefinition;

}