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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
				layoutDisplayPageObjectProvider)) {

			return;
		}

		_printAnalyticsCloudAssetTracker(
			layoutDisplayPageObjectProvider.getClassName(),
			layoutDisplayPageObjectProvider.getClassPK(),
			layoutDisplayPageObjectProvider.getDisplayObject(),
			httpServletResponse.getWriter(),
			layoutDisplayPageObjectProvider.getTitle(
				_portal.getLocale(httpServletRequest)));
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"com.liferay.layout,taglib#/render_fragment_layout/page.jsp#pre");
	}

	private <T> void _printAnalyticsCloudAssetTracker(
		String className, long classPK, T displayObject,
		PrintWriter printWriter, String title) {

		AnalyticsRenderFragmentLayoutUtil.AnalyticsAssetType
			analyticsAssetType =
				AnalyticsRenderFragmentLayoutUtil.getAnalyticsAssetType(
					className);

		if (analyticsAssetType == null) {
			return;
		}

		Map<String, Function<T, String>> attributes =
			HashMapBuilder.<String, Function<T, String>>put(
				"data-analytics-asset-id", object -> String.valueOf(classPK)
			).put(
				"data-analytics-asset-title",
				object -> HtmlUtil.escapeAttribute(title)
			).put(
				"data-analytics-asset-type",
				object -> analyticsAssetType.getType()
			).putAll(
				analyticsAssetType.getAttributes()
			).build();

		List<String> keys = new ArrayList<>(attributes.keySet());

		Collections.sort(keys);

		StringBundler sb = new StringBundler((keys.size() * 3) + 2);

		sb.append("<div ");

		for (String key : keys) {
			sb.append(key);
			sb.append("=\"");

			Function<T, String> function = attributes.get(key);

			sb.append(function.apply(displayObject));

			sb.append("\"");
			sb.append(" ");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(">");

		printWriter.print(sb);
	}

	@Reference
	private Portal _portal;

}