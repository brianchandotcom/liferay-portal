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

package com.liferay.layout.reports.web.internal.portlet.action;

import com.liferay.layout.reports.web.internal.configuration.provider.LayoutReportsGooglePageSpeedConfigurationProvider;
import com.liferay.layout.reports.web.internal.constants.LayoutReportsPortletKeys;
import com.liferay.layout.reports.web.internal.data.provider.LayoutReportsDataProvider;
import com.liferay.layout.reports.web.internal.model.LayoutReportsIssue;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutReportsPortletKeys.LAYOUT_REPORTS,
		"mvc.command.name=/layout_reports/get_layout_reports_issues"
	},
	service = MVCResourceCommand.class
)
public class GetLayoutReportsIssuesMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		if (!_hasViewPermission(
				themeDisplay.getLayout(),
				themeDisplay.getPermissionChecker())) {

			_log.error("You do not have permissions to access this app");

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					_language.get(
						resourceBundle, "an-unexpected-error-occurred")));

			return;
		}

		try {
			long groupId = ParamUtil.getLong(resourceRequest, "groupId");

			Group group = _groupLocalService.fetchGroup(groupId);

			if (group == null) {
				_log.error("No site exists with site id " + groupId);

				JSONPortletResponseUtil.writeJSON(
					resourceRequest, resourceResponse,
					JSONUtil.put(
						"error",
						_language.format(
							resourceBundle, "no-site-exists-with-site-id-x",
							groupId)));

				return;
			}

			LayoutReportsDataProvider layoutReportsDataProvider =
				new LayoutReportsDataProvider(
					_layoutReportsGooglePageSpeedConfigurationProvider.
						getApiKey(group));

			String canonicalURL = ParamUtil.getString(
				resourceRequest, "canonicalURL");

			List<LayoutReportsIssue> layoutReportsIssues =
				layoutReportsDataProvider.getLayoutReportsIssues(canonicalURL);

			Stream<LayoutReportsIssue> stream = layoutReportsIssues.stream();

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"layoutReportsIssues",
					JSONUtil.putAll(
						stream.map(
							layoutReportsIssue ->
								layoutReportsIssue.toJSONObject(resourceBundle)
						).toArray(
							size -> new JSONObject[size]
						))));
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					_language.get(locale, "an-unexpected-error-occurred")));
		}
	}

	private boolean _hasViewPermission(
			Layout layout, PermissionChecker permissionChecker)
		throws Exception {

		if (!LayoutPermissionUtil.contains(
				permissionChecker, layout, ActionKeys.VIEW)) {

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetLayoutReportsIssuesMVCResourceCommand.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

	@Reference
	private Language _language;

	@Reference
	private LayoutReportsGooglePageSpeedConfigurationProvider
		_layoutReportsGooglePageSpeedConfigurationProvider;

	@Reference
	private LayoutSEOLinkManager _layoutSEOLinkManager;

	@Reference
	private Portal _portal;

}