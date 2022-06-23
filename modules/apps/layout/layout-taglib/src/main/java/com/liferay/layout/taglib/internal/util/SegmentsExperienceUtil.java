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

package com.liferay.layout.taglib.internal.util;

import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.segments.manager.SegmentsExperienceManager;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
public class SegmentsExperienceUtil {

	public static long getSegmentsExperienceId(
		HttpServletRequest httpServletRequest) {

		long selectedSegmentsExperienceId = ParamUtil.getLong(
			httpServletRequest, "segmentsExperienceId", -1);

		if (selectedSegmentsExperienceId != -1) {
			return selectedSegmentsExperienceId;
		}

		SegmentsExperienceManager segmentsExperienceManager =
			new SegmentsExperienceManager(
				ServletContextUtil.getSegmentsExperienceLocalService());

		return segmentsExperienceManager.getSegmentsExperienceId(
			httpServletRequest);
	}

}