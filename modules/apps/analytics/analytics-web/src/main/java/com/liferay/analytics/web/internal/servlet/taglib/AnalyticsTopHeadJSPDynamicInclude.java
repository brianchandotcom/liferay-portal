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

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.analytics.web.internal.constants.AnalyticsWebKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

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
@Component(service = DynamicInclude.class)
public class AnalyticsTopHeadJSPDynamicInclude extends BaseJSPDynamicInclude {

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		AnalyticsConfiguration analyticsConfiguration = null;

		try {
			analyticsConfiguration =
				_analyticsSettingsManager.getAnalyticsConfiguration(
					themeDisplay.getCompanyId());
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);
		}

		if (!_isAnalyticsTrackingEnabled(
				analyticsConfiguration, httpServletRequest, themeDisplay)) {

			return;
		}

		httpServletRequest.setAttribute(
			AnalyticsWebKeys.ANALYTICS_CLIENT_CHANNEL_ID,
			_getLiferayAnalyticsChannelId(httpServletRequest, themeDisplay));
		httpServletRequest.setAttribute(
			AnalyticsWebKeys.ANALYTICS_CLIENT_CONFIG,
			_serialize(
				HashMapBuilder.put(
					"dataSourceId",
					analyticsConfiguration.liferayAnalyticsDataSourceId()
				).put(
					"endpointUrl",
					analyticsConfiguration.liferayAnalyticsEndpointURL()
				).put(
					"projectId",
					analyticsConfiguration.liferayAnalyticsProjectId()
				).build()));
		httpServletRequest.setAttribute(
			AnalyticsWebKeys.ANALYTICS_CLIENT_GROUP_IDS,
			_serialize(
				PrefsPropsUtil.getStringArray(
					themeDisplay.getCompanyId(), "liferayAnalyticsGroupIds",
					StringPool.COMMA)));

		Layout layout = themeDisplay.getLayout();

		httpServletRequest.setAttribute(
			AnalyticsWebKeys.ANALYTICS_CLIENT_READABLE_CONTENT,
			Boolean.toString(layout.isTypeAssetDisplay()));

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

	@Override
	protected Log getLog() {
		return _log;
	}

	private String _getLiferayAnalyticsChannelId(
		HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay) {

		Layout layout = themeDisplay.getLayout();

		Group group = layout.getGroup();

		if (Objects.equals(group.getGroupKey(), "Forms")) {
			Group refererGroup = _groupLocalService.fetchGroup(
				GetterUtil.getLong(
					httpServletRequest.getAttribute("refererGroupId")));

			if (refererGroup != null) {
				return refererGroup.getTypeSettingsProperty(
					"analyticsChannelId");
			}
		}

		return group.getTypeSettingsProperty("analyticsChannelId");
	}

	private boolean _isAnalyticsTrackingEnabled(
		AnalyticsConfiguration analyticsConfiguration,
		HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay) {

		Layout layout = themeDisplay.getLayout();

		if ((analyticsConfiguration == null) || (layout == null) ||
			layout.isTypeControlPanel() ||
			Validator.isNull(
				analyticsConfiguration.liferayAnalyticsDataSourceId()) ||
			Validator.isNull(
				analyticsConfiguration.liferayAnalyticsEndpointURL()) ||
			Objects.equals(
				httpServletRequest.getRequestURI(), "/c/portal/api/jsonws")) {

			return false;
		}

		String[] syncedGroupIds = analyticsConfiguration.syncedGroupIds();

		if (_isSharedFormEnabled(
				syncedGroupIds, layout.getGroup(), httpServletRequest) ||
			analyticsConfiguration.liferayAnalyticsEnableAllGroupIds() ||
			ArrayUtil.contains(
				syncedGroupIds, String.valueOf(layout.getGroupId()))) {

			return true;
		}

		return false;
	}

	private boolean _isSharedFormEnabled(
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

	private String _serialize(Map<String, String> map) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}

		return jsonObject.toString();
	}

	private String _serialize(Object[] array) {
		JSONArray jsonArray = _jsonFactory.createJSONArray(array);

		return jsonArray.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsTopHeadJSPDynamicInclude.class);

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.analytics.web)")
	private ServletContext _servletContext;

}