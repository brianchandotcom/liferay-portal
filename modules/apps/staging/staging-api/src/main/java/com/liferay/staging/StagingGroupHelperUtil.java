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

package com.liferay.staging;

/**
 * @author Máté Thurzó
 */
public class StagingGroupHelperUtil {

	public static StagingGroupHelper getStagingGroupHelper() {
		if (_stagingGroupHelper != null) {
			return _stagingGroupHelper;
		}

		throw new NullPointerException("StagingGroupHelper is null");
	}

	public static void setStagingGroupHelper(
		StagingGroupHelper stagingGroupHelper) {

		if (_stagingGroupHelper != null) {
			stagingGroupHelper = _stagingGroupHelper;

			return;
		}

		_stagingGroupHelper = stagingGroupHelper;
	}

	private static StagingGroupHelper _stagingGroupHelper;

}