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

package com.liferay.dynamic.data.mapping.form.analytics.internal.servlet.taglib;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DynamicInclude.class)
public class DDMFormAnalyticsTopHeadJSPDynamicInclude
	extends BaseJSPDynamicInclude {

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response,
			String key)
		throws IOException {

		Map<String, String> values = new HashMap<>();

		values.put("analyticsGatewayUrl", getAnalyticsGatewayUrl());

		ScriptData scriptData = new ScriptData();

		scriptData.append(
			null,
			StringUtil.replaceToStringBundler(
				_TMPL_CONTENT, StringPool.POUND, StringPool.POUND, values),
			null, ScriptData.ModulesType.AUI);

		scriptData.writeTo(response.getWriter());
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_head.jsp#post");
	}

	protected String getAnalyticsGatewayUrl() {
		if ((_ANALYTICS_GATEWAY_PROTOCOL == null) ||
			(_ANALYTICS_GATEWAY_HOST == null) ||
			(_ANALYTICS_GATEWAY_PORT == null) ||
			(_ANALYTICS_GATEWAY_PATH == null)) {

			return "";
		}

		return String.format(
			"%s//%s:%s/%s", _ANALYTICS_GATEWAY_PROTOCOL,
			_ANALYTICS_GATEWAY_HOST, _ANALYTICS_GATEWAY_PORT,
			_ANALYTICS_GATEWAY_PATH);
	}

	@Override
	protected String getJspPath() {
		return null;
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.dynamic.data.mapping.form.analytics)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private static final String _ANALYTICS_GATEWAY_HOST = System.getProperty(
		"analytics.gateway.host");

	private static final String _ANALYTICS_GATEWAY_PATH = System.getProperty(
		"analytics.gateway.path");

	private static final String _ANALYTICS_GATEWAY_PORT = System.getProperty(
		"analytics.gateway.port");

	private static final String _ANALYTICS_GATEWAY_PROTOCOL =
		System.getProperty("analytics.gateway.protocol");

	private static final String _TMPL_CONTENT = StringUtil.read(
		DDMFormAnalyticsTopHeadJSPDynamicInclude.class,
		"/META-INF/resources/form_analytics.tmpl");

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormAnalyticsTopHeadJSPDynamicInclude.class);

}