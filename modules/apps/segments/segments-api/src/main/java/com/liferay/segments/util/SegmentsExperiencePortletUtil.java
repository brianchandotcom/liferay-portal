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

import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.segments.constants.SegmentsExperienceConstants;

/**
 * @author Pavel Savinov
 */
public class SegmentsExperiencePortletUtil {

	public static String decodePortletName(String portletId) {
		int index = portletId.indexOf(_SEGMENTS_EXPERIENCE_SEPARATOR);

		if (index == -1) {
			return PortletIdCodec.decodePortletName(portletId);
		}

		return PortletIdCodec.decodePortletName(portletId.substring(0, index));
	}

	public static long getSegmentsExperienceId(String portletId) {
		int index = portletId.indexOf(_SEGMENTS_EXPERIENCE_SEPARATOR);

		if (index == -1) {
			return 0L;
		}

		return GetterUtil.getLong(
			portletId.substring(
				index + _SEGMENTS_EXPERIENCE_SEPARATOR.length()),
			SegmentsExperienceConstants.ID_DEFAULT);
	}

	public static String setSegmentsExperienceId(
		String instanceId, long segmentsExperienceId) {

		if (segmentsExperienceId == SegmentsExperienceConstants.ID_DEFAULT) {
			return instanceId;
		}

		int index = instanceId.indexOf(_SEGMENTS_EXPERIENCE_SEPARATOR);

		if (index == -1) {
			return instanceId + _SEGMENTS_EXPERIENCE_SEPARATOR +
				segmentsExperienceId;
		}

		return instanceId.substring(0, index) + _SEGMENTS_EXPERIENCE_SEPARATOR +
			segmentsExperienceId;
	}

	private static final String _SEGMENTS_EXPERIENCE_SEPARATOR =
		"_SEGMENTS_EXPERIENCE_";

}