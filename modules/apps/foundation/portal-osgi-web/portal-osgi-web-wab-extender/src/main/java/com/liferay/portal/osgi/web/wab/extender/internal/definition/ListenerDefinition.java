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

package com.liferay.portal.osgi.web.wab.extender.internal.definition;

import com.liferay.portal.kernel.util.Validator;

import java.util.EventListener;

/**
 * @author Raymond Augé
 */
public class ListenerDefinition {

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ListenerDefinition)) {
			return false;
		}

		ListenerDefinition listenerDefinition = (ListenerDefinition)obj;

		EventListener eventListener = listenerDefinition.getEventListener();

		Class<?> listenerClass = _eventListener.getClass();

		Class<?> objectListenerClass = eventListener.getClass();

		if (Validator.equals(listenerClass, objectListenerClass)) {
			return true;
		}

		return false;
	}

	public EventListener getEventListener() {
		return _eventListener;
	}

	public void setEventListener(EventListener eventListener) {
		_eventListener = eventListener;
	}

	private EventListener _eventListener;

}