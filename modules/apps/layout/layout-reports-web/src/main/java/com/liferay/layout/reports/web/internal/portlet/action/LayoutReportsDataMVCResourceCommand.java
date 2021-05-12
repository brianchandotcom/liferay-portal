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
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.layout.reports.web.internal.configuration.LayoutReportsGooglePageSpeedCompanyConfiguration;
import com.liferay.layout.reports.web.internal.configuration.LayoutReportsGooglePageSpeedConfiguration;
import com.liferay.layout.reports.web.internal.configuration.provider.LayoutReportsGooglePageSpeedConfigurationProvider;
import com.liferay.layout.reports.web.internal.constants.LayoutReportsPortletKeys;
import com.liferay.layout.reports.web.internal.data.provider.LayoutReportsDataProvider;
import com.liferay.layout.seo.kernel.LayoutSEOLink;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutReportsPortletKeys.LAYOUT_REPORTS,
		"mvc.command.name=/layout_reports/data"
	},
	service = MVCResourceCommand.class
)
public class LayoutReportsDataMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		Layout layout = _layoutLocalService.fetchLayout(
			ParamUtil.getLong(resourceRequest, "plid"));

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		LayoutReportsDataProvider layoutReportsDataProvider =
			new LayoutReportsDataProvider(
				_layoutReportsGooglePageSpeedConfigurationProvider.getApiKey(
					themeDisplay.getScopeGroup()));

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put(
				"canonicalURLs",
				_getCanonicalURLsJSONArray(
					resourceRequest, resourceResponse, layout)
			).put(
				"configureGooglePageSpeedURL",
				_getConfigureGooglePageSpeedURL(resourceRequest)
			).put(
				"defaultLanguageId",
				LocaleUtil.toW3cLanguageId(_getDefaultLocale(layout))
			).put(
				"imagesPath",
				_portal.getPathContext(resourceRequest) + "/images/"
			).put(
				"validConnection", layoutReportsDataProvider.isValidConnection()
			));
	}

	private Map<Locale, String> _getAlternateURLs(
		String currentCompleteURL, Layout layout, ThemeDisplay themeDisplay) {

		try {
			return _portal.getAlternateURLs(
				currentCompleteURL, themeDisplay, layout);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return Collections.emptyMap();
	}

	private String _getCanonicalURL(
		String currentCompleteURL, Layout layout, ThemeDisplay themeDisplay) {

		try {
			return _portal.getCanonicalURL(
				currentCompleteURL, themeDisplay, layout, false, false);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return StringPool.BLANK;
	}

	private JSONArray _getCanonicalURLsJSONArray(
		PortletRequest portletRequest, PortletResponse portletResponse,
		Layout layout) {

		Locale defaultLocale = _getDefaultLocale(layout);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String canonicalURL = _getCanonicalURL(
			_getCompleteURL(portletRequest), layout, themeDisplay);

		Map<Locale, String> alternateURLs = _getAlternateURLs(
			canonicalURL, layout, themeDisplay);

		return JSONUtil.putAll(
			Optional.ofNullable(
				_groupLocalService.fetchGroup(layout.getGroupId())
			).map(
				Group::getGroupId
			).map(
				_language::getAvailableLocales
			).orElseGet(
				Collections::emptySet
			).stream(
			).sorted(
				(locale1, locale2) -> {
					if (Objects.equals(locale1, defaultLocale)) {
						return -1;
					}

					if (Objects.equals(locale2, defaultLocale)) {
						return 1;
					}

					String languageId1 = LocaleUtil.toW3cLanguageId(locale1);
					String languageId2 = LocaleUtil.toW3cLanguageId(locale2);

					return languageId1.compareToIgnoreCase(languageId2);
				}
			).map(
				locale -> HashMapBuilder.<String, Object>put(
					"canonicalURL",
					() -> {
						LayoutSEOLink layoutSEOLink =
							_layoutSEOLinkManager.getCanonicalLayoutSEOLink(
								layout, locale, canonicalURL, alternateURLs);

						return layoutSEOLink.getHref();
					}
				).put(
					"languageId", LocaleUtil.toW3cLanguageId(locale)
				).put(
					"layoutReportsIssuesURL",
					_getResourceURL(
						layout.getGroupId(), canonicalURL, portletResponse)
				).put(
					"title", _getTitle(portletRequest, layout, locale)
				).build()
			).toArray());
	}

	private String _getCompleteURL(PortletRequest portletRequest) {
		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return _portal.getLayoutURL(themeDisplay);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return _portal.getCurrentCompleteURL(
				_portal.getHttpServletRequest(portletRequest));
		}
	}

	private String _getConfigureGooglePageSpeedURL(
		PortletRequest portletRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (_isOmniAdmin()) {
			return PortletURLBuilder.create(
				_portal.getControlPanelPortletURL(
					portletRequest,
					ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
					PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/configuration_admin/edit_configuration"
			).setRedirect(
				_getCompleteURL(portletRequest)
			).setParameter(
				"factoryPid",
				LayoutReportsGooglePageSpeedConfiguration.class.getName()
			).setParameter(
				"pid", LayoutReportsGooglePageSpeedConfiguration.class.getName()
			).buildString();
		}
		else if (_isCompanyAdmin()) {
			return PortletURLBuilder.create(
				_portal.getControlPanelPortletURL(
					portletRequest,
					ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
					PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/configuration_admin/edit_configuration"
			).setRedirect(
				_getCompleteURL(portletRequest)
			).setParameter(
				"factoryPid",
				LayoutReportsGooglePageSpeedCompanyConfiguration.class.getName()
			).setParameter(
				"pid",
				LayoutReportsGooglePageSpeedCompanyConfiguration.class.getName()
			).buildString();
		}
		else if (_isSiteAdmin(themeDisplay.getScopeGroupId())) {
			try {
				return PortletURLBuilder.create(
					_portal.getControlPanelPortletURL(
						portletRequest,
						_groupLocalService.getGroup(
							themeDisplay.getScopeGroupId()),
						"com_liferay_site_admin_web_portlet_" +
							"SiteSettingsPortlet",
						0, 0, PortletRequest.RENDER_PHASE)
				).buildString();
			}
			catch (PortalException portalException) {
				_log.error(portalException, portalException);

				return null;
			}
		}

		return null;
	}

	private Locale _getDefaultLocale(Layout layout) {
		try {
			return _portal.getSiteDefaultLocale(layout.getGroupId());
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return LocaleUtil.getSiteDefault();
		}
	}

	private String _getResourceURL(
		long groupId, String canonicalURL, PortletResponse portletResponse) {

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(portletResponse);

		ResourceURL resourceURL = liferayPortletResponse.createResourceURL();

		resourceURL.setParameter("groupId", String.valueOf(groupId));
		resourceURL.setParameter("canonicalURL", canonicalURL);
		resourceURL.setResourceID("/layout_reports/get_layout_reports_issues");

		return resourceURL.toString();
	}

	private String _getTitle(
		PortletRequest portletRequest, Layout layout, Locale locale) {

		if (layout.isTypeAssetDisplay()) {
			return Optional.ofNullable(
				(InfoItemDetails)portletRequest.getAttribute(
					InfoDisplayWebKeys.INFO_ITEM_DETAILS)
			).map(
				infoItemDetails ->
					_infoItemServiceTracker.getFirstInfoItemService(
						InfoItemFieldValuesProvider.class,
						infoItemDetails.getClassName())
			).map(
				infoItemFieldValuesProvider ->
					infoItemFieldValuesProvider.getInfoItemFieldValue(
						portletRequest.getAttribute(
							InfoDisplayWebKeys.INFO_ITEM),
						"title")
			).map(
				infoFieldValue -> (String)infoFieldValue.getValue(locale)
			).orElse(
				StringPool.BLANK
			);
		}
		else if (layout.isTypeContent() || layout.isTypePortlet()) {
			return Optional.ofNullable(
				layout.getTitle(locale)
			).filter(
				Validator::isNotNull
			).orElseGet(
				() -> layout.getName(locale)
			);
		}

		return StringPool.BLANK;
	}

	private boolean _isCompanyAdmin() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return permissionChecker.isCompanyAdmin();
	}

	private boolean _isOmniAdmin() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return permissionChecker.isOmniadmin();
	}

	private boolean _isSiteAdmin(long groupId) {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return permissionChecker.isGroupAdmin(groupId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutReportsDataMVCResourceCommand.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutReportsGooglePageSpeedConfigurationProvider
		_layoutReportsGooglePageSpeedConfigurationProvider;

	@Reference
	private LayoutSEOLinkManager _layoutSEOLinkManager;

	@Reference
	private Portal _portal;

}