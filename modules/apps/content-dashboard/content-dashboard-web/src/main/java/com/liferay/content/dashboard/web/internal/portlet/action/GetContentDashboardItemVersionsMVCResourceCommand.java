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

package com.liferay.content.dashboard.web.internal.portlet.action;

import com.liferay.content.dashboard.item.ContentDashboardItem;
import com.liferay.content.dashboard.item.ContentDashboardItemFactory;
import com.liferay.content.dashboard.item.VersionableContentDashboardItem;
import com.liferay.content.dashboard.web.internal.constants.ContentDashboardPortletKeys;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactoryTracker;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefan Tanasie
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentDashboardPortletKeys.CONTENT_DASHBOARD_ADMIN,
		"mvc.command.name=/content_dashboard/get_content_dashboard_item_versions"
	},
	service = MVCResourceCommand.class
)
public class GetContentDashboardItemVersionsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		Locale locale = _portal.getLocale(resourceRequest);

		try {
			String className = ParamUtil.getString(
				resourceRequest, "className");

			ContentDashboardItemFactory<?> contentDashboardItemFactory =
				_contentDashboardItemFactoryTracker.
					getContentDashboardItemFactory(className);

			if (contentDashboardItemFactory == null) {
				JSONPortletResponseUtil.writeJSON(
					resourceRequest, resourceResponse,
					JSONFactoryUtil.createJSONArray());

				return;
			}

			long classPK = GetterUtil.getLong(
				ParamUtil.getLong(resourceRequest, "classPK"));

			ContentDashboardItem<?> contentDashboardItem =
				contentDashboardItemFactory.create(classPK);

			if ((contentDashboardItem == null) ||
				!(contentDashboardItem instanceof
					VersionableContentDashboardItem)) {

				JSONPortletResponseUtil.writeJSON(
					resourceRequest, resourceResponse,
					JSONFactoryUtil.createJSONArray());

				return;
			}

			JSONObject jsonObject = _getVersionsJSONObject(
				resourceRequest,
				(VersionableContentDashboardItem<?>)contentDashboardItem);

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse, jsonObject);
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info(exception);
			}

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					ResourceBundleUtil.getString(
						ResourceBundleUtil.getBundle(locale, getClass()),
						"an-unexpected-error-occurred")));
		}
	}

	private JSONArray _getVersionsJSONArray(
		int displayVersions, HttpServletRequest httpServletRequest,
		VersionableContentDashboardItem versionableContentDashboardItem) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<ContentDashboardItem.Version> versions =
			versionableContentDashboardItem.getAllVersions(themeDisplay);

		if (ListUtil.isEmpty(versions)) {
			return jsonArray;
		}

		versions = ListUtil.subList(versions, 0, displayVersions);

		for (ContentDashboardItem.Version version : versions) {
			jsonArray.put(version.toJSONObject());
		}

		return jsonArray;
	}

	private JSONObject _getVersionsJSONObject(
		ResourceRequest resourceRequest,
		VersionableContentDashboardItem<?> versionableContentDashboardItem) {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		return JSONUtil.put(
			"versions",
			() -> {
				int displayVersions = ParamUtil.getInteger(
					resourceRequest, "maxDisplayVersions",
					_DEFAULT_MAX_DISPLAY_VERSIONS);

				return _getVersionsJSONArray(
					displayVersions, httpServletRequest,
					versionableContentDashboardItem);
			}
		).put(
			"viewVersionsURL",
			versionableContentDashboardItem.getViewVersionsURL(
				httpServletRequest)
		);
	}

	private static final int _DEFAULT_MAX_DISPLAY_VERSIONS = 10;

	private static final Log _log = LogFactoryUtil.getLog(
		GetContentDashboardItemVersionsMVCResourceCommand.class);

	@Reference
	private ContentDashboardItemFactoryTracker
		_contentDashboardItemFactoryTracker;

	@Reference
	private Portal _portal;

}