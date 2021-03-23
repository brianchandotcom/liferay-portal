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

package com.liferay.layout.reports.web.internal.display.context;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.layout.reports.web.internal.configuration.LayoutReportsPageSpeedCompanyConfiguration;
import com.liferay.layout.reports.web.internal.configuration.LayoutReportsPageSpeedConfiguration;
import com.liferay.layout.reports.web.internal.data.provider.LayoutReportsDataProvider;
import com.liferay.layout.seo.kernel.LayoutSEOLink;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

/**
 * @author Cristina González
 */
public class LayoutReportsDisplayContext {

	public LayoutReportsDisplayContext(
		GroupLocalService groupLocalService,
		InfoItemServiceTracker infoItemServiceTracker,
		LayoutLocalService layoutLocalService,
		LayoutReportsDataProvider layoutReportsDataProvider,
		LayoutSEOLinkManager layoutSEOLinkManager, Language language,
		Portal portal, RenderRequest renderRequest) {

		_groupLocalService = groupLocalService;
		_infoItemServiceTracker = infoItemServiceTracker;
		_layoutLocalService = layoutLocalService;
		_layoutReportsDataProvider = layoutReportsDataProvider;
		_layoutSEOLinkManager = layoutSEOLinkManager;
		_language = language;
		_portal = portal;
		_renderRequest = renderRequest;

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getData() {
		if (_data != null) {
			return _data;
		}

		Layout layout = _layoutLocalService.fetchLayout(
			_themeDisplay.getPlid());

		_data = Collections.singletonMap(
			"context",
			HashMapBuilder.<String, Object>put(
				"assetsPath",
				_portal.getPathContext(_renderRequest) + "/assets/"
			).put(
				"canonicalURLs", _getCanonicalURLs(layout)
			).put(
				"configurePageSpeedURL",
				_getConfigurePageSpeedURL(_renderRequest)
			).put(
				"defaultLanguageId",
				LocaleUtil.toW3cLanguageId(_getDefaultLocale(layout))
			).put(
				"validConnection",
				_layoutReportsDataProvider.isValidConnection()
			).build());

		return _data;
	}

	private Map<Locale, String> _getAlternateURLs(
		String currentCompleteURL, Layout layout) {

		try {
			return _portal.getAlternateURLs(
				currentCompleteURL, _themeDisplay, layout);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return Collections.emptyMap();
	}

	private String _getCanonicalURL(String currentCompleteURL, Layout layout) {
		try {
			return _portal.getCanonicalURL(
				currentCompleteURL, _themeDisplay, layout, false, false);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return StringPool.BLANK;
	}

	private List<Map<String, Object>> _getCanonicalURLs(Layout layout) {
		Locale defaultLocale = _getDefaultLocale(layout);

		String canonicalURL = _getCanonicalURL(_getCompleteURL(), layout);

		Map<Locale, String> alternateURLs = _getAlternateURLs(
			canonicalURL, layout);

		return Optional.ofNullable(
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
				"title", _getTitle(layout, locale)
			).build()
		).collect(
			Collectors.toList()
		);
	}

	private String _getCompleteURL() {
		try {
			return _portal.getLayoutURL(_themeDisplay);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return _portal.getCurrentCompleteURL(
				_portal.getHttpServletRequest(_renderRequest));
		}
	}

	private String _getConfigurePageSpeedURL(PortletRequest portletRequest) {
		if (_isOmniAdmin()) {
			PortletURL portletURL = _portal.getControlPanelPortletURL(
				portletRequest, ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter(
				"mvcRenderCommandName",
				"/configuration_admin/edit_configuration");
			portletURL.setParameter(
				"redirect",
				_portal.getCurrentCompleteURL(
					_portal.getHttpServletRequest(portletRequest)));
			portletURL.setParameter(
				"factoryPid",
				LayoutReportsPageSpeedConfiguration.class.getName());
			portletURL.setParameter(
				"pid", LayoutReportsPageSpeedConfiguration.class.getName());

			return portletURL.toString();
		}
		else if (_isCompanyAdmin()) {
			PortletURL portletURL = _portal.getControlPanelPortletURL(
				portletRequest, ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter(
				"mvcRenderCommandName",
				"/configuration_admin/edit_configuration");
			portletURL.setParameter(
				"redirect",
				_portal.getCurrentCompleteURL(
					_portal.getHttpServletRequest(portletRequest)));
			portletURL.setParameter(
				"factoryPid",
				LayoutReportsPageSpeedCompanyConfiguration.class.getName());
			portletURL.setParameter(
				"pid",
				LayoutReportsPageSpeedCompanyConfiguration.class.getName());

			return portletURL.toString();
		}
		else if (_isSiteAdmin()) {
			try {
				PortletURL portletURL = _portal.getControlPanelPortletURL(
					portletRequest,
					_groupLocalService.getGroup(
						_themeDisplay.getScopeGroupId()),
					"com_liferay_site_admin_web_portlet_SiteSettingsPortlet", 0,
					0, PortletRequest.RENDER_PHASE);

				return portletURL.toString();
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

	private String _getTitle(Layout layout, Locale locale) {
		if (layout.isTypeAssetDisplay()) {
			return Optional.ofNullable(
				(InfoItemDetails)_renderRequest.getAttribute(
					InfoDisplayWebKeys.INFO_ITEM_DETAILS)
			).map(
				infoItemDetails ->
					_infoItemServiceTracker.getFirstInfoItemService(
						InfoItemFieldValuesProvider.class,
						infoItemDetails.getClassName())
			).map(
				infoItemFieldValuesProvider ->
					infoItemFieldValuesProvider.getInfoItemFieldValue(
						_renderRequest.getAttribute(
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

	private boolean _isSiteAdmin() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return permissionChecker.isGroupAdmin(_themeDisplay.getScopeGroupId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutReportsDisplayContext.class);

	private Map<String, Object> _data;
	private final GroupLocalService _groupLocalService;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private final Language _language;
	private final LayoutLocalService _layoutLocalService;
	private final LayoutReportsDataProvider _layoutReportsDataProvider;
	private final LayoutSEOLinkManager _layoutSEOLinkManager;
	private final Portal _portal;
	private final RenderRequest _renderRequest;
	private final ThemeDisplay _themeDisplay;

}