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
import com.liferay.layout.reports.web.internal.configuration.LayoutReportsGooglePageSpeedGroupConfiguration;
import com.liferay.layout.reports.web.internal.configuration.provider.LayoutReportsGooglePageSpeedConfigurationProvider;
import com.liferay.layout.reports.web.internal.constants.LayoutReportsPortletKeys;
import com.liferay.layout.reports.web.internal.data.provider.LayoutReportsDataProvider;
import com.liferay.layout.seo.canonical.url.LayoutSEOCanonicalURLProvider;
import com.liferay.layout.seo.kernel.LayoutSEOLink;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.Locale;
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

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put(
				"configureGooglePageSpeedURL",
				_getConfigureGooglePageSpeedURL(resourceRequest)
			).put(
				"defaultLanguageId",
				LocaleUtil.toW3cLanguageId(_getDefaultLocale(layout))
			).put(
				"imagesPath",
				_portal.getPathContext(resourceRequest) + "/images/"
			).put(
				"pageURLs",
				_getPageURLsJSONArray(resourceRequest, resourceResponse, layout)
			).put(
				"privateLayout", layout.isPrivateLayout()
			).put(
				"validConnection",
				() -> {
					LayoutReportsDataProvider layoutReportsDataProvider =
						new LayoutReportsDataProvider(
							_layoutReportsGooglePageSpeedConfigurationProvider.
								getApiKey(themeDisplay.getScopeGroup()),
							_layoutReportsGooglePageSpeedConfigurationProvider.
								getStrategy(themeDisplay.getScopeGroup()));

					return layoutReportsDataProvider.isValidConnection();
				}
			));
	}

	private String _getCanonicalURL(
		String canonicalURL, Layout layout, Locale locale,
		ThemeDisplay themeDisplay) {

		try {
			LayoutSEOLink layoutSEOLink =
				_layoutSEOLinkManager.getCanonicalLayoutSEOLink(
					layout, locale, canonicalURL, themeDisplay);

			return layoutSEOLink.getHref();
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return canonicalURL;
		}
	}

	private String _getCanonicalURL(
		String currentCompleteURL, Layout layout, ThemeDisplay themeDisplay) {

		try {
			return _portal.getCanonicalURL(
				currentCompleteURL, themeDisplay, layout, false, false);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return StringPool.BLANK;
	}

	private String _getCompleteURL(PortletRequest portletRequest) {
		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return _portal.getLayoutURL(themeDisplay);
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return _portal.getCurrentCompleteURL(
				_portal.getHttpServletRequest(portletRequest));
		}
	}

	private String _getConfigureGooglePageSpeedURL(
		PortletRequest portletRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!_isGroupAdmin(themeDisplay.getScopeGroupId())) {
			return null;
		}

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				portletRequest, ConfigurationAdminPortletKeys.SITE_SETTINGS,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/configuration_admin/edit_configuration"
		).setRedirect(
			_getCompleteURL(portletRequest)
		).setParameter(
			"factoryPid",
			LayoutReportsGooglePageSpeedGroupConfiguration.class.getName()
		).setParameter(
			"pid",
			LayoutReportsGooglePageSpeedGroupConfiguration.class.getName()
		).buildString();
	}

	private Locale _getDefaultLocale(Layout layout) {
		try {
			return _portal.getSiteDefaultLocale(layout.getGroupId());
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return LocaleUtil.getSiteDefault();
		}
	}

	private String _getLocaleURL(
		String canonicalURL, Locale defaultLocale, Layout layout, Locale locale,
		ThemeDisplay themeDisplay) {

		if (defaultLocale.equals(locale)) {
			return _getCanonicalURL(canonicalURL, layout, locale, themeDisplay);
		}

		try {
			return _layoutSEOCanonicalURLProvider.getCanonicalURL(
				layout, locale, canonicalURL, themeDisplay);
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return canonicalURL;
		}
	}

	private JSONArray _getPageURLsJSONArray(
		PortletRequest portletRequest, PortletResponse portletResponse,
		Layout layout) {

		Locale defaultLocale = _getDefaultLocale(layout);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String canonicalURL = _getCanonicalURL(
			_getCompleteURL(portletRequest), layout, themeDisplay);

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

					Locale locale = themeDisplay.getLocale();

					String displayLanguage1 = locale1.getDisplayLanguage(
						locale);
					String displayLanguage2 = locale2.getDisplayLanguage(
						locale);

					if (StringUtil.equalsIgnoreCase(
							displayLanguage1, displayLanguage2)) {

						return -1;
					}

					return 1;
				}
			).map(
				locale -> {
					String url = _getLocaleURL(
						canonicalURL, defaultLocale, layout, locale,
						themeDisplay);

					return HashMapBuilder.<String, Object>put(
						"languageId", LocaleUtil.toW3cLanguageId(locale)
					).put(
						"languageLabel",
						StringBundler.concat(
							locale.getDisplayLanguage(themeDisplay.getLocale()),
							StringPool.SPACE, StringPool.OPEN_PARENTHESIS,
							locale.getDisplayCountry(themeDisplay.getLocale()),
							StringPool.CLOSE_PARENTHESIS)
					).put(
						"layoutReportsIssuesURL",
						_getResourceURL(
							layout.getGroupId(), url, portletResponse)
					).put(
						"title", _getTitle(portletRequest, layout, locale)
					).put(
						"url", url
					).build();
				}
			).toArray());
	}

	private String _getResourceURL(
		long groupId, String url, PortletResponse portletResponse) {

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(portletResponse);

		ResourceURL resourceURL = liferayPortletResponse.createResourceURL();

		resourceURL.setParameter("groupId", String.valueOf(groupId));
		resourceURL.setParameter("url", url);
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
					infoItemFieldValuesProvider.getInfoFieldValue(
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

	private boolean _isGroupAdmin(long groupId) {
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
	private LayoutSEOCanonicalURLProvider _layoutSEOCanonicalURLProvider;

	@Reference
	private LayoutSEOLinkManager _layoutSEOLinkManager;

	@Reference
	private Portal _portal;

}