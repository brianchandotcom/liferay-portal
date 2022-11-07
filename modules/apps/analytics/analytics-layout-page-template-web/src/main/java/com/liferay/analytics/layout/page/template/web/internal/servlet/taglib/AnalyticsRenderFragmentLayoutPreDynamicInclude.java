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

package com.liferay.analytics.layout.page.template.web.internal.servlet.taglib;

import com.liferay.analytics.layout.page.template.web.internal.servlet.taglib.util.AnalyticsRenderFragmentLayoutUtil;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = DynamicInclude.class)
public class AnalyticsRenderFragmentLayoutPreDynamicInclude
	extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String dynamicIncludeKey)
		throws IOException {

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			(LayoutDisplayPageObjectProvider<?>)httpServletRequest.getAttribute(
				LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER);

		if (!AnalyticsRenderFragmentLayoutUtil.isTrackeable(
				_classNameLocalService, layoutDisplayPageObjectProvider)) {

			return;
		}

		try {
			ClassName className = _classNameLocalService.getClassName(
				layoutDisplayPageObjectProvider.getClassNameId());

			_printAnalyticsCloudAssetTracker(
				className.getClassName(),
				layoutDisplayPageObjectProvider.getClassPK(),
				httpServletResponse.getWriter(),
				layoutDisplayPageObjectProvider.getTitle(
					_portal.getLocale(httpServletRequest)));
		}
		catch (PortalException portalException) {
			throw new IOException(portalException);
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"com.liferay.layout,taglib#/render_fragment_layout/page.jsp#pre");
	}

	private void _printAnalyticsCloudAssetTracker(
		String className, long classPK, PrintWriter printWriter, String title) {

		String analyticsAssetType =
			AnalyticsRenderFragmentLayoutUtil.getAnalyticsAssetType(className);

		if (Validator.isNull(analyticsAssetType)) {
			return;
		}

		printWriter.print("<div data-analytics-asset-id=\"");
		printWriter.print(classPK);
		printWriter.print("\" data-analytics-asset-title=\"");
		printWriter.print(HtmlUtil.escapeAttribute(title));
		printWriter.print("\" data-analytics-asset-type=\"");
		printWriter.print(analyticsAssetType);
		printWriter.print("\" >");
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private Portal _portal;

}