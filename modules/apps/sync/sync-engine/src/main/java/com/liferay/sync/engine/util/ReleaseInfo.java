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

package com.liferay.sync.engine.util;

/**
 * @author Shinn Lok
 */
public class ReleaseInfo {

	public static final int getBuildNumber() {
		return _BUILD_NUMBER;
	}

	public static final int getFeatureSet() {
		return _FEATURE_SET;
	}

	public static final String getVersion() {
		return _VERSION;
	}

	private static final String _BUILD = "3102";

	private static final int _BUILD_NUMBER = Integer.parseInt(_BUILD);

	private static final int _FEATURE_SET = 1;

	private static final String _VERSION = "3.1.2";

}