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

package com.liferay.segments.util;

/**
 * @author Pavel Savinov
 */
public class SegmentsExperiencePortletUtil {

	public static String setSegmentsExperienceId(
		String instanceId, long segmentsExperienceId) {

		int index = instanceId.indexOf(_EXPERIENCE_SEPARATOR);

		if (index == -1) {
			return instanceId + _EXPERIENCE_SEPARATOR + segmentsExperienceId;
		}

		return instanceId.substring(0, index) + _EXPERIENCE_SEPARATOR +
			segmentsExperienceId;
	}

	private static final String _EXPERIENCE_SEPARATOR = "_EXPERIENCE_";

}