/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.portlet.action;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.content.dashboard.web.internal.constants.ContentDashboardPortletKeys;
import com.liferay.depot.service.DepotEntryGroupRelService;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(
	property = {
		"javax.portlet.name=" + ContentDashboardPortletKeys.CONTENT_DASHBOARD_ADMIN,
		"mvc.command.name=/content_dashboard/get_content_performance_info"
	},
	service = MVCResourceCommand.class
)
public class GetContentPerformanceInfoMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);
		Locale locale = _portal.getLocale(resourceRequest);
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			AnalyticsConfiguration analyticsConfiguration =
				_analyticsSettingsManager.getAnalyticsConfiguration(
					themeDisplay.getCompanyId());

			AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
				_classNameLocalService.getClassNameId(
					ParamUtil.getString(resourceRequest, "className")),
				GetterUtil.getLong(
					ParamUtil.getLong(resourceRequest, "classPK")));

			boolean connectedToAnalyticsCloud = false;

			boolean siteSyncedToAnalyticsCloud = false;

			if (ArrayUtil.contains(
					analyticsConfiguration.syncedGroupIds(),
					String.valueOf(assetEntry.getGroupId()))) {

				siteSyncedToAnalyticsCloud = true;
			}

			if (!Validator.isBlank(analyticsConfiguration.token())) {
				connectedToAnalyticsCloud = true;
			}

			boolean assetLibrary = false;

			if (Validator.isNotNull(
					_depotEntryGroupRelService.fetchGroupDepotEntry(
						assetEntry.getGroupId()))) {

				assetLibrary = true;
			}

			boolean connectedToAssetLibrary = false;

			long depotEntryGroupRelsCount =
				_depotEntryGroupRelService.getDepotEntryGroupRelsCount(
					assetEntry.getGroupId());

			if (depotEntryGroupRelsCount > 0) {
				connectedToAssetLibrary = true;
			}

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"analyticsSettingsPortletURL",
					_getAnalyticsSettingsPortletURL(httpServletRequest)
				).put(
					"assetLibrary", assetLibrary
				).put(
					"connectedToAnalyticsCloud", connectedToAnalyticsCloud
				).put(
					"connectedToAssetLibrary", connectedToAssetLibrary
				).put(
					"depotAdminPortletURL",
					_getDepotAdminPortletURL(httpServletRequest)
				).put(
					"siteSyncedToAnalyticsCloud", siteSyncedToAnalyticsCloud
				));
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

	private String _getAnalyticsSettingsPortletURL(
		HttpServletRequest httpServletRequest) {

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest,
				ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/configuration_admin/view_configuration_screen"
		).setParameter(
			"configurationScreenKey", "analytics-cloud-connection"
		).buildString();
	}

	private String _getDepotAdminPortletURL(
		HttpServletRequest httpServletRequest) {

		return PortletURLBuilder.create(
			_portletURLFactory.create(
				httpServletRequest, _DEPOT_ADMIN_PORTLET_ID,
				PortletRequest.RENDER_PHASE)
		).buildString();
	}

	private static final String _DEPOT_ADMIN_PORTLET_ID =
		"com_liferay_depot_web_portlet_DepotAdminPortlet";

	private static final Log _log = LogFactoryUtil.getLog(
		GetContentPerformanceInfoMVCResourceCommand.class);

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DepotEntryGroupRelService _depotEntryGroupRelService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

}