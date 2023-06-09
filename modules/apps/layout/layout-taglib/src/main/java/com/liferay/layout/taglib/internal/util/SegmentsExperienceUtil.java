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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.manager.SegmentsExperienceManager;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
public class SegmentsExperienceUtil {

	public static long getSegmentsExperienceId(
		HttpServletRequest httpServletRequest) {

		long selectedSegmentsExperienceId = ParamUtil.getLong(
			httpServletRequest, "segmentsExperienceId", -1);

		if (_isValidSegmentsExperienceId(
				_getLayout(httpServletRequest), selectedSegmentsExperienceId)) {

			return selectedSegmentsExperienceId;
		}

		SegmentsExperienceManager segmentsExperienceManager =
			new SegmentsExperienceManager(
				ServletContextUtil.getSegmentsExperienceLocalService());

		return segmentsExperienceManager.getSegmentsExperienceId(
			httpServletRequest);
	}

	private static Layout _getLayout(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			return themeDisplay.getLayout();
		}

		long plid = ParamUtil.getLong(httpServletRequest, "plid");

		if (plid > 0) {
			return LayoutLocalServiceUtil.fetchLayout(plid);
		}

		return null;
	}

	private static boolean _isValidSegmentsExperienceId(
		Layout layout, long segmentsExperienceId) {

		if ((segmentsExperienceId == -1) || (layout == null)) {
			return false;
		}

		SegmentsExperience segmentsExperience =
			SegmentsExperienceLocalServiceUtil.fetchSegmentsExperience(
				segmentsExperienceId);

		if ((segmentsExperience != null) &&
			((layout.getPlid() == segmentsExperience.getPlid()) ||
			 (layout.getClassPK() == segmentsExperience.getPlid()))) {

			return true;
		}

		return false;
	}

}