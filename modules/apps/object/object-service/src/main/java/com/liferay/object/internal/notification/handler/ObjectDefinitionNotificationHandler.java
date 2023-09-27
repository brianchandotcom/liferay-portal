/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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