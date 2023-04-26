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

package com.liferay.layout.reports.web.internal.struts;

import com.liferay.layout.reports.web.internal.configuration.provider.LayoutReportsGooglePageSpeedConfigurationProvider;
import com.liferay.layout.reports.web.internal.data.provider.LayoutReportsDataProvider;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.text.Format;

import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	property = "path=/layout_reports/get_layout_reports_issues",
	service = StrutsAction.class
)
public class GetLayoutReportsIssuesStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		if (!_hasViewPermission(
				themeDisplay.getLayout(),
				themeDisplay.getPermissionChecker())) {

			_log.error("You do not have permissions to access this app");

			ServletResponseUtil.write(
				httpServletResponse,
				JSONUtil.put(
					"error",
					_language.get(locale, "an-unexpected-error-occurred")
				).toString());

			return null;
		}

		try {
			long groupId = ParamUtil.getLong(httpServletRequest, "groupId");

			Group group = _groupLocalService.fetchGroup(groupId);

			if (group == null) {
				_log.error("No site exists with site id " + groupId);

				ServletResponseUtil.write(
					httpServletResponse,
					JSONUtil.put(
						"error",
						_language.format(
							locale, "no-site-exists-with-site-id-x", groupId)
					).toString());

				return null;
			}

			ServletResponseUtil.write(
				httpServletResponse,
				_getLayoutReportIssuesResponseJSONObject(
					ParamUtil.getBoolean(httpServletRequest, "refreshCache"),
					group, resourceBundle, themeDisplay,
					ParamUtil.getString(httpServletRequest, "url")
				).toString());
		}
		catch (LayoutReportsDataProvider.LayoutReportsDataProviderException
					layoutReportsDataProviderException) {

			_log.error(layoutReportsDataProviderException);

			ServletResponseUtil.write(
				httpServletResponse,
				JSONUtil.put(
					"error", layoutReportsDataProviderException.getMessage()
				).put(
					"googlePageSpeedError",
					layoutReportsDataProviderException.
						getGooglePageSpeedErrorJSONObject()
				).toString());
		}
		catch (Exception exception) {
			_log.error(exception);

			ServletResponseUtil.write(
				httpServletResponse,
				JSONUtil.put(
					"error",
					_language.get(locale, "an-unexpected-error-occurred")
				).toString());
		}

		return null;
	}

	private JSONObject _fetchLayoutReportIssuesJSONObject(
			Group group, ResourceBundle resourceBundle,
			ThemeDisplay themeDisplay, String url)
		throws Exception {

		LayoutReportsDataProvider layoutReportsDataProvider =
			new LayoutReportsDataProvider(
				_layoutReportsGooglePageSpeedConfigurationProvider.getApiKey(
					group),
				_layoutReportsGooglePageSpeedConfigurationProvider.getStrategy(
					group));

		return JSONUtil.put(
			"issues",
			JSONUtil.toJSONArray(
				layoutReportsDataProvider.getLayoutReportsIssues(
					resourceBundle.getLocale(), url),
				layoutReportsIssue -> layoutReportsIssue.toJSONObject(
					_getConfigureLayoutSeoURL(themeDisplay),
					_getConfigurePagesSeoURL(themeDisplay), resourceBundle))
		).put(
			"timestamp", System.currentTimeMillis()
		);
	}

	private String _getCompleteURL(ThemeDisplay themeDisplay) {
		try {
			return _portal.getLayoutURL(themeDisplay);
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return _portal.getCurrentCompleteURL(themeDisplay.getRequest());
		}
	}

	private String _getConfigureLayoutSeoURL(ThemeDisplay themeDisplay) {
		Layout layout = themeDisplay.getLayout();

		try {
			if (LayoutPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), layout,
					ActionKeys.UPDATE)) {

				String completeURL = _getCompleteURL(themeDisplay);

				return HttpComponentsUtil.addParameters(
					themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
						"/layout_reports/get_layout_reports_issues",
					"redirect", completeURL, "backURL", completeURL, "groupId",
					layout.getGroupId(), "privateLayout",
					layout.isPrivateLayout(), "screenNavigationEntryKey", "seo",
					"selPlid", layout.getPlid());
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return null;
	}

	private String _getConfigurePagesSeoURL(ThemeDisplay themeDisplay) {
		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (permissionChecker.isCompanyAdmin()) {
			String configurationPid =
				"com.liferay.layout.seo.internal.configuration." +
					"LayoutSEOCompanyConfiguration";

			return HttpComponentsUtil.addParameters(
				themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
					"/layout_reports/get_layout_reports_issues",
				"redirect", _getCompleteURL(themeDisplay), "factoryPid",
				configurationPid, "pid", configurationPid);
		}

		return null;
	}

	private JSONObject _getLayoutReportIssuesResponseJSONObject(
			boolean refreshCache, Group group, ResourceBundle resourceBundle,
			ThemeDisplay themeDisplay, String url)
		throws Exception {

		String cacheKey = themeDisplay.getLocale() + "-" + url;

		if (refreshCache) {
			_layoutReportsIssuesPortalCache.put(
				cacheKey,
				_fetchLayoutReportIssuesJSONObject(
					group, resourceBundle, themeDisplay, url));
		}

		JSONObject layoutReportsIssuesJSONObject =
			_layoutReportsIssuesPortalCache.get(cacheKey);

		if (layoutReportsIssuesJSONObject != null) {
			Format format = DateFormatFactoryUtil.getSimpleDateFormat(
				"MMMM d, yyyy HH:mm a", resourceBundle.getLocale(),
				themeDisplay.getTimeZone());

			layoutReportsIssuesJSONObject.put(
				"date",
				format.format(
					new Date(
						layoutReportsIssuesJSONObject.getLong("timestamp"))));
		}

		return JSONUtil.put(
			"layoutReportsIssues", layoutReportsIssuesJSONObject);
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
		GetLayoutReportsIssuesStrutsAction.class);

	private static final PortalCache<String, JSONObject>
		_layoutReportsIssuesPortalCache = PortalCacheHelperUtil.getPortalCache(
			PortalCacheManagerNames.MULTI_VM,
			GetLayoutReportsIssuesStrutsAction.class.getName());

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private LayoutReportsGooglePageSpeedConfigurationProvider
		_layoutReportsGooglePageSpeedConfigurationProvider;

	@Reference
	private Portal _portal;

}