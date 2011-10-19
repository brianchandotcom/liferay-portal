/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.osgi;

/**
 * @author Raymond Augé
 */
public class BaseListener {

	protected String getLogMessage(String state, Object object) {
		return getLogMessage(state, String.valueOf(object));
	}

	protected String getLogMessage(String state, String message) {
		return String.format(_LOG_MESSAGE_FORMAT, state, message);
	}

	private static final String _LOG_MESSAGE_FORMAT = "%1$18s %2$s";

}