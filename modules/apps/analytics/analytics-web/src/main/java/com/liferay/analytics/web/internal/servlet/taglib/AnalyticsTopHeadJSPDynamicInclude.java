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

package com.liferay.analytics.web.internal.servlet.taglib;

import com.liferay.analytics.web.internal.constants.AnalyticsWebKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DynamicInclude.class)
public class AnalyticsTopHeadJSPDynamicInclude extends BaseJSPDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ThemeDisplay themeDisplay = getThemeDisplay(httpServletRequest);

		if (!isAnalyticsTrackingEnabled(httpServletRequest, themeDisplay)) {
			return;
		}

		Map<String, String> analyticsClientConfig = new HashMap<>();

		analyticsClientConfig.put(
			"dataSourceId",
			getLiferayAnalyticsDataSourceId(themeDisplay.getCompany()));
		analyticsClientConfig.put(
			"endpointUrl",
			getLiferayAnalyticsEndpointURL(themeDisplay.getCompany()));

		httpServletRequest.setAttribute(
			AnalyticsWebKeys.ANALYTICS_CLIENT_CONFIG_KEY,
			serialize(analyticsClientConfig));

		super.include(httpServletRequest, httpServletResponse, key);
	}

	@Override
	public void register(
		DynamicInclude.DynamicIncludeRegistry dynamicIncludeRegistry) {

		dynamicIncludeRegistry.register(
			"/html/common/themes/top_head.jsp#post");
	}

	@Override
	protected String getJspPath() {
		return "/dynamic_include/top_head.jsp";
	}

	protected String getLiferayAnalyticsDataSourceId(Company company) {
		return PrefsPropsUtil.getString(
			company.getCompanyId(), "liferayAnalyticsDataSourceId");
	}

	protected String getLiferayAnalyticsEndpointURL(Company company) {
		return PrefsPropsUtil.getString(
			company.getCompanyId(), "liferayAnalyticsEndpointURL");
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	protected ThemeDisplay getThemeDisplay(
		HttpServletRequest httpServletRequest) {

		return (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	protected boolean isAnalyticsTrackingEnabled(
		HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay) {

		Layout layout = themeDisplay.getLayout();

		if (layout == null) {
			return false;
		}

		if (layout.isTypeControlPanel()) {
			return false;
		}

		Company company = themeDisplay.getCompany();

		if (Validator.isNull(getLiferayAnalyticsDataSourceId(company)) ||
			Validator.isNull(getLiferayAnalyticsEndpointURL(company))) {

			return false;
		}

		if (Objects.equals(
				httpServletRequest.getRequestURI(), "/c/portal/api/jsonws")) {

			return false;
		}

		String[] liferayAnalyticsGroupIds = PrefsPropsUtil.getStringArray(
			company.getCompanyId(), "liferayAnalyticsGroupIds",
			StringPool.COMMA);

		if (isSharedFormEnabled(
				liferayAnalyticsGroupIds, layout.getGroup(),
				httpServletRequest)) {

			return true;
		}

		Group group = layout.getGroup();

		boolean liferayAnalyticsEnableAllGroupIds = PrefsPropsUtil.getBoolean(
			company.getCompanyId(), "liferayAnalyticsEnableAllGroupIds");

		if (liferayAnalyticsEnableAllGroupIds ||
			ArrayUtil.contains(
				liferayAnalyticsGroupIds, String.valueOf(group.getGroupId()))) {

			return true;
		}

		return false;
	}

	protected boolean isSharedFormEnabled(
		String[] liferayAnalyticsGroupIds, Group group,
		HttpServletRequest httpServletRequest) {

		if (Objects.equals(group.getGroupKey(), "Forms")) {
			return ArrayUtil.contains(
				liferayAnalyticsGroupIds,
				String.valueOf(
					httpServletRequest.getAttribute("refererGroupId")));
		}

		return false;
	}

	protected String serialize(Map<String, String> map) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}

		return jsonObject.toString();
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.analytics.web)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsTopHeadJSPDynamicInclude.class);

	@Reference
	private JSONFactory _jsonFactory;

}