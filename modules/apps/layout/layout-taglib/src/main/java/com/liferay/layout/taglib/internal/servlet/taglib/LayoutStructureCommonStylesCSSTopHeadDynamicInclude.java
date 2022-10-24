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

package com.liferay.layout.taglib.internal.servlet.taglib;

import com.liferay.layout.taglib.internal.util.SegmentsExperienceUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(service = DynamicInclude.class)
public class LayoutStructureCommonStylesCSSTopHeadDynamicInclude
	extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String dynamicIncludeKey)
		throws IOException {

		if (ParamUtil.getBoolean(httpServletRequest, "disableCommonStyles") ||
			Objects.equals(
				ParamUtil.getString(
					httpServletRequest, "p_l_mode", Constants.VIEW),
				Constants.EDIT)) {

			return;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (!layout.isTypeAssetDisplay() && !layout.isTypeContent()) {
			return;
		}

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.print("<link data-senna-track=\"temporary\" href=\"");
		printWriter.print(
			_portal.getPathContext() +
				"/o/layout-common-styles/main.css?plid=");
		printWriter.print(layout.getPlid());
		printWriter.print("&segmentsExperienceId=");
		printWriter.print(
			SegmentsExperienceUtil.getSegmentsExperienceId(httpServletRequest));
		printWriter.print("&t=");

		_addModifiedDate(printWriter, layout);

		long masterLayoutPlid = layout.getMasterLayoutPlid();

		if (masterLayoutPlid > 0) {
			Layout masterLayout = _layoutLocalService.fetchLayout(
				masterLayoutPlid);

			_addModifiedDate(printWriter, masterLayout);
		}

		printWriter.print("\" rel=\"stylesheet\" type=\"text/css\">");
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_head.jsp#post");
	}

	private void _addModifiedDate(PrintWriter printWriter, Layout layout) {
		if (layout == null) {
			return;
		}

		Date modifiedDate = layout.getModifiedDate();

		if (modifiedDate != null) {
			printWriter.print(modifiedDate.getTime());
		}
		else {
			printWriter.print(System.currentTimeMillis());
		}
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}