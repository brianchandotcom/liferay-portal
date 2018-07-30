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

package com.liferay.portal.kernel.spring.orm;

/**
 * @author Shuyang Zhou
 */
public class LastSessionRecorderHelperUtil {

	public static void syncLastSessionState() {
		_lastSessionRecorderHelper.syncLastSessionState();
	}

	public void setLastSessionRecorderHelper(
		LastSessionRecorderHelper lastSessionRecorderHelper) {

		_lastSessionRecorderHelper = lastSessionRecorderHelper;
	}

	private static LastSessionRecorderHelper _lastSessionRecorderHelper;

}