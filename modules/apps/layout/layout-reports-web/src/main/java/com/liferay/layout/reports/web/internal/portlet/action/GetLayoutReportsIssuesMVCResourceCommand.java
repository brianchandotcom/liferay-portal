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

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.reports.web.internal.configuration.provider.LayoutReportsGooglePageSpeedConfigurationProvider;
import com.liferay.layout.reports.web.internal.constants.LayoutReportsPortletKeys;
import com.liferay.layout.reports.web.internal.data.provider.LayoutReportsDataProvider;
import com.liferay.layout.reports.web.internal.model.LayoutReportsIssue;
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
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.text.Format;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
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
					_language.get(locale, "an-unexpected-error-occurred")));

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
							locale, "no-site-exists-with-site-id-x", groupId)));

				return;
			}

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				_getLayoutReportIssuesResponseJSONObject(
					ParamUtil.getBoolean(resourceRequest, "refreshCache"),
					group, resourceBundle, themeDisplay,
					ParamUtil.getString(resourceRequest, "url")));
		}
		catch (LayoutReportsDataProvider.LayoutReportsDataProviderException
					layoutReportsDataProviderException) {

			_log.error(layoutReportsDataProviderException);

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error", layoutReportsDataProviderException.getMessage()
				).put(
					"googlePageSpeedError",
					layoutReportsDataProviderException.
						getGooglePageSpeedErrorJSONObject()
				));
		}
		catch (Exception exception) {
			_log.error(exception);

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					_language.get(locale, "an-unexpected-error-occurred")));
		}
	}

	private JSONObject _fetchLayoutReportIssuesJSONObject(
			Group group, ResourceBundle resourceBundle,
			ThemeDisplay themeDisplay, String url)
		throws PortalException {

		LayoutReportsDataProvider layoutReportsDataProvider =
			new LayoutReportsDataProvider(
				_layoutReportsGooglePageSpeedConfigurationProvider.getApiKey(
					group),
				_layoutReportsGooglePageSpeedConfigurationProvider.getStrategy(
					group));

		List<LayoutReportsIssue> layoutReportsIssues =
			layoutReportsDataProvider.getLayoutReportsIssues(
				resourceBundle.getLocale(), url);

		Stream<LayoutReportsIssue> stream = layoutReportsIssues.stream();

		return JSONUtil.put(
			"issues",
			JSONUtil.putAll(
				stream.map(
					layoutReportsIssue -> layoutReportsIssue.toJSONObject(
						_getConfigureLayoutSeoURL(themeDisplay),
						_getConfigurePagesSeoURL(themeDisplay), resourceBundle)
				).toArray(
					size -> new JSONObject[size]
				))
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

				return PortletURLBuilder.create(
					_portal.getControlPanelPortletURL(
						themeDisplay.getRequest(),
						LayoutAdminPortletKeys.GROUP_PAGES,
						PortletRequest.RENDER_PHASE)
				).setMVCRenderCommandName(
					"/layout_admin/edit_layout"
				).setRedirect(
					completeURL
				).setBackURL(
					completeURL
				).setPortletResource(
					() -> {
						PortletDisplay portletDisplay =
							themeDisplay.getPortletDisplay();

						return portletDisplay.getId();
					}
				).setParameter(
					"groupId", layout.getGroupId()
				).setParameter(
					"privateLayout", layout.isPrivateLayout()
				).setParameter(
					"screenNavigationEntryKey", "seo"
				).setParameter(
					"selPlid", layout.getPlid()
				).buildString();
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

			return PortletURLBuilder.create(
				_portal.getControlPanelPortletURL(
					themeDisplay.getRequest(),
					ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
					PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/configuration_admin/edit_configuration"
			).setRedirect(
				_getCompleteURL(themeDisplay)
			).setParameter(
				"factoryPid", configurationPid
			).setParameter(
				"pid", configurationPid
			).buildString();
		}

		return null;
	}

	private JSONObject _getLayoutReportIssuesResponseJSONObject(
			boolean refreshCache, Group group, ResourceBundle resourceBundle,
			ThemeDisplay themeDisplay, String url)
		throws PortalException {

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
		GetLayoutReportsIssuesMVCResourceCommand.class);

	private static final PortalCache<String, JSONObject>
		_layoutReportsIssuesPortalCache = PortalCacheHelperUtil.getPortalCache(
			PortalCacheManagerNames.MULTI_VM,
			GetLayoutReportsIssuesMVCResourceCommand.class.getName());

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