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

package com.liferay.custom.elements.web.internal.servlet.taglib;

import com.liferay.custom.elements.model.CustomElementsSource;
import com.liferay.custom.elements.service.CustomElementsSourceLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = DynamicInclude.class)
public class CustomElementsSourceDynamicInclude implements DynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<CustomElementsSource> customElementsSources =
			_customElementsSourceLocalService.getCustomElementsSources(
				themeDisplay.getCompanyId());

		for (CustomElementsSource customElementsSource :
				customElementsSources) {

			String urls = customElementsSource.getURLs();

			for (String url : urls.split(StringPool.NEW_LINE)) {
				printWriter.print("<script src=\"" + url + "\"></script>");
			}
		}

		printWriter.flush();
	}

	@Override
	public void register(
		DynamicInclude.DynamicIncludeRegistry dynamicIncludeRegistry) {

		dynamicIncludeRegistry.register(
			"/html/common/themes/top_js.jspf#resources");
	}

	@Reference
	private CustomElementsSourceLocalService _customElementsSourceLocalService;

}