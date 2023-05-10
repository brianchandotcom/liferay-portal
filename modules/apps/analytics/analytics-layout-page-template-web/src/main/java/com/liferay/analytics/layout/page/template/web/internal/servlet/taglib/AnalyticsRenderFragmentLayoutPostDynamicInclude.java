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
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.taglib.aui.ScriptTag;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = DynamicInclude.class)
public class AnalyticsRenderFragmentLayoutPostDynamicInclude
	extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String dynamicIncludeKey)
		throws IOException {

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			(LayoutDisplayPageObjectProvider<?>)httpServletRequest.getAttribute(
				LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER);

		if (layoutDisplayPageObjectProvider == null) {
			return;
		}

		if (!AnalyticsRenderFragmentLayoutUtil.isTrackeable(
				layoutDisplayPageObjectProvider)) {

			try {
				ScriptTag scriptTag = new ScriptTag();

				scriptTag.setPosition("inline");

				scriptTag.doBodyTag(
					httpServletRequest, httpServletResponse,
					pageContext -> _processScriptTagBody(
						layoutDisplayPageObjectProvider, pageContext));
			}
			catch (JspException jspException) {
				throw new IOException(jspException);
			}
		}
		else {
			PrintWriter printWriter = httpServletResponse.getWriter();

			printWriter.print("</div>");
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"com.liferay.layout,taglib#/render_fragment_layout/page.jsp#post");
	}

	private void _processScriptTagBody(
		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider,
		PageContext pageContext) {

		try {
			StringBundler sb = new StringBundler(9);

			sb.append("Analytics.track(\"");

			InfoItemClassDetails infoItemClassDetails =
				new InfoItemClassDetails(
					layoutDisplayPageObjectProvider.getClassName());

			ServletRequest servletRequest = pageContext.getRequest();

			Locale locale = servletRequest.getLocale();

			String label = infoItemClassDetails.getLabel(locale);

			sb.append(label);

			sb.append(" Viewed\", {'classPK': ");
			sb.append(
				String.valueOf(layoutDisplayPageObjectProvider.getClassPK()));
			sb.append(", 'title': '");
			sb.append(layoutDisplayPageObjectProvider.getTitle(locale));
			sb.append("', 'type': '");
			sb.append(label);
			sb.append("'});");

			Writer writer = pageContext.getOut();

			writer.write(sb.toString());
		}
		catch (IOException ioException) {
			ReflectionUtil.throwException(ioException);
		}
	}

	@Reference
	private Portal _portal;

}