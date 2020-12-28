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

package com.liferay.frontend.js.loader.modules.extender.internal.servlet.taglib;

import com.liferay.frontend.js.loader.modules.extender.internal.configuration.Details;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.servlet.PortalWebResourceConstants;
import com.liferay.portal.kernel.servlet.PortalWebResourcesUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	configurationPid = "com.liferay.frontend.js.loader.modules.extender.internal.configuration.Details",
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE,
	service = DynamicInclude.class
)
public class JSLoaderTopHeadDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.println("<script data-senna-track=\"temporary\" type=\"");
		printWriter.print(ContentTypes.TEXT_JAVASCRIPT);
		printWriter.print("\">window.__CONFIG__=");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		JSONObject loaderConfigJSONObject = JSONUtil.put(
			"basePath", StringPool.BLANK
		).put(
			"combine", themeDisplay.isThemeJsFastLoad()
		).put(
			"defaultURLParams", _getDefaultURLParamsJSONObject(themeDisplay)
		).put(
			"explainResolutions", _details.explainResolutions()
		).put(
			"exposeGlobal", _details.exposeGlobal()
		).put(
			"logLevel", _details.logLevel()
		).put(
			"namespace", "Liferay"
		).put(
			"reportMismatchedAnonymousModules", "warn"
		).put(
			"resolvePath", _getResolvePath(httpServletRequest)
		).put(
			"url", _getURL(httpServletRequest, themeDisplay)
		).put(
			"waitTimeout", _details.waitTimeout() * 1000
		);

		printWriter.print(loaderConfigJSONObject.toString());

		printWriter.print(";</script>");

		printWriter.println("<script data-senna-track=\"permanent\" src=\"");
		printWriter.print(_servletContext.getContextPath());
		printWriter.print("/loader.js\" type=\"");
		printWriter.print(ContentTypes.TEXT_JAVASCRIPT);
		printWriter.print("\"></script>");
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_js.jspf#resources");
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_details = ConfigurableUtil.createConfigurable(
			Details.class, properties);
	}

	private JSONObject _getDefaultURLParamsJSONObject(
		ThemeDisplay themeDisplay) {

		if (themeDisplay.isThemeJsFastLoad()) {
			return null;
		}

		return JSONUtil.put("languageId", themeDisplay.getLanguageId());
	}

	private String _getResolvePath(HttpServletRequest httpServletRequest) {
		AbsolutePortalURLBuilder absolutePortalURLBuilder =
			_absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
				httpServletRequest);

		return absolutePortalURLBuilder.forWhiteboard(
			"/js_resolve_modules"
		).build();
	}

	private String _getURL(
		HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay) {

		if (themeDisplay.isThemeJsFastLoad()) {
			String url = _portal.getStaticResourceURL(
				httpServletRequest,
				themeDisplay.getCDNDynamicResourcesHost() +
					themeDisplay.getPathContext() + "/combo/",
				"minifierType=",
				PortalWebResourcesUtil.getLastModified(
					PortalWebResourceConstants.RESOURCE_TYPE_JS));

			return url + StringPool.AMPERSAND;
		}

		return themeDisplay.getCDNBaseURL();
	}

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

	private volatile Details _details;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.js.loader.modules.extender)"
	)
	private ServletContext _servletContext;

}