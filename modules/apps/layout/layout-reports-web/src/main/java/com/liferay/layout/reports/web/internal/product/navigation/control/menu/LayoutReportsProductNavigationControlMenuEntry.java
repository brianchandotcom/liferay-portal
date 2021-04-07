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

package com.liferay.layout.reports.web.internal.product.navigation.control.menu;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.react.renderer.ComponentDescriptor;
import com.liferay.portal.template.react.renderer.ReactRenderer;
import com.liferay.product.navigation.control.menu.BaseProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;
import com.liferay.taglib.aui.IconTag;
import com.liferay.taglib.util.BodyBottomTag;

import java.io.IOException;
import java.io.Writer;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 */
@Component(
	configurationPid = "com.liferay.layout.reports.web.internal.configuration.LayoutReportsGooglePageSpeedConfiguration",
	immediate = true,
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.USER,
		"product.navigation.control.menu.entry.order:Integer=550"
	},
	service = ProductNavigationControlMenuEntry.class
)
public class LayoutReportsProductNavigationControlMenuEntry
	extends BaseProductNavigationControlMenuEntry {

	@Override
	public String getLabel(Locale locale) {
		return null;
	}

	@Override
	public String getURL(HttpServletRequest httpServletRequest) {
		return null;
	}

	@Override
	public boolean includeBody(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		BodyBottomTag bodyBottomTag = new BodyBottomTag();

		bodyBottomTag.setOutputKey("layoutReportsPanel");

		try {
			bodyBottomTag.doBodyTag(
				httpServletRequest, httpServletResponse,
				pageContext -> {
					try {
						_processBodyBottomTagBody(pageContext);
					}
					catch (Exception exception) {
						throw new ProcessBodyBottomTagBodyException(exception);
					}
				});
		}
		catch (ProcessBodyBottomTagBodyException
					processBodyBottomTagBodyException) {

			throw new IOException(processBodyBottomTagBodyException);
		}
		catch (JspException jspException) {
			throw new IOException(jspException);
		}

		return true;
	}

	@Override
	public boolean includeIcon(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		Map<String, String> values = new HashMap<>();

		if (_isPanelStateOpen(httpServletRequest)) {
			values.put("cssClass", "active");
		}
		else {
			values.put("cssClass", StringPool.BLANK);
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_portal.getLocale(httpServletRequest), getClass());

		values.put(
			"title", _html.escape(_language.get(resourceBundle, "page-audit")));

		IconTag iconTag = new IconTag();

		iconTag.setCssClass("icon-monospaced");
		iconTag.setImage("info-circle");
		iconTag.setMarkupView("lexicon");

		try {
			values.put(
				"iconTag",
				iconTag.doTagAsString(httpServletRequest, httpServletResponse));
		}
		catch (JspException jspException) {
			throw new IOException(jspException);
		}

		values.put("portletNamespace", _portletNamespace);

		Writer writer = httpServletResponse.getWriter();

		writer.write(StringUtil.replace(_ICON_TMPL_CONTENT, "${", "}", values));

		return true;
	}

	@Override
	public boolean isShow(HttpServletRequest httpServletRequest)
		throws PortalException {

		if (!_layoutReportsGooglePageSpeedConfigurationProvider.isEnabled(
				_groupLocalService.getGroup(
					_portal.getScopeGroupId(httpServletRequest)))) {

			return false;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!_isShow(themeDisplay.getPlid()) ||
			!_isShowPanel(httpServletRequest)) {

			return false;
		}

		return super.isShow(httpServletRequest);
	}

	public static class ProcessBodyBottomTagBodyException
		extends RuntimeException {

		public ProcessBodyBottomTagBodyException(Throwable throwable) {
			super(throwable);
		}

	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_layoutReportsGooglePageSpeedConfigurationProvider =
			new LayoutReportsGooglePageSpeedConfigurationProvider(
				_configurationProvider,
				ConfigurableUtil.createConfigurable(
					LayoutReportsGooglePageSpeedConfiguration.class,
					properties));

		_portletNamespace = _portal.getPortletNamespace(
			LayoutReportsPortletKeys.LAYOUT_REPORTS);
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

	private List<Map<String, Object>> _getCanonicalURLs(
		HttpServletRequest httpServletRequest, Layout layout,
		ThemeDisplay themeDisplay) {

		Locale defaultLocale = _getDefaultLocale(layout);

		String canonicalURL = _getCanonicalURL(
			_getCompleteURL(httpServletRequest, themeDisplay), layout,
			themeDisplay);

		Map<Locale, String> alternateURLs = _getAlternateURLs(
			canonicalURL, layout, themeDisplay);

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
				"title", _getTitle(httpServletRequest, layout, locale)
			).build()
		).collect(
			Collectors.toList()
		);
	}

	private String _getCompleteURL(
		HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay) {

		try {
			return _portal.getLayoutURL(themeDisplay);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return _portal.getCurrentCompleteURL(httpServletRequest);
		}
	}

	private String _getConfigureGooglePageSpeedURL(
		HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay) {

		if (_isOmniAdmin()) {
			PortletURL portletURL = _portal.getControlPanelPortletURL(
				httpServletRequest,
				ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter(
				"mvcRenderCommandName",
				"/configuration_admin/edit_configuration");
			portletURL.setParameter(
				"redirect", _portal.getCurrentCompleteURL(httpServletRequest));
			portletURL.setParameter(
				"factoryPid",
				LayoutReportsGooglePageSpeedConfiguration.class.getName());
			portletURL.setParameter(
				"pid",
				LayoutReportsGooglePageSpeedConfiguration.class.getName());

			return portletURL.toString();
		}
		else if (_isCompanyAdmin()) {
			PortletURL portletURL = _portal.getControlPanelPortletURL(
				httpServletRequest,
				ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter(
				"mvcRenderCommandName",
				"/configuration_admin/edit_configuration");
			portletURL.setParameter(
				"redirect", _portal.getCurrentCompleteURL(httpServletRequest));
			portletURL.setParameter(
				"factoryPid",
				LayoutReportsGooglePageSpeedCompanyConfiguration.class.
					getName());
			portletURL.setParameter(
				"pid",
				LayoutReportsGooglePageSpeedCompanyConfiguration.class.
					getName());

			return portletURL.toString();
		}
		else if (_isSiteAdmin(themeDisplay.getScopeGroupId())) {
			try {
				PortletURL portletURL = _portal.getControlPanelPortletURL(
					httpServletRequest,
					_groupLocalService.getGroup(themeDisplay.getScopeGroupId()),
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

	private Map<String, Object> _getData(
		HttpServletRequest httpServletRequest, Layout layout,
		LayoutReportsDataProvider layoutReportsDataProvider,
		ThemeDisplay themeDisplay) {

		return Collections.singletonMap(
			"context",
			HashMapBuilder.<String, Object>put(
				"assetsPath",
				_portal.getPathContext(httpServletRequest) + "/assets/"
			).put(
				"canonicalURLs",
				_getCanonicalURLs(httpServletRequest, layout, themeDisplay)
			).put(
				"configureGooglePageSpeedURL",
				_getConfigureGooglePageSpeedURL(
					httpServletRequest, themeDisplay)
			).put(
				"defaultLanguageId",
				LocaleUtil.toW3cLanguageId(_getDefaultLocale(layout))
			).put(
				"validConnection", layoutReportsDataProvider.isValidConnection()
			).build());
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

	private String _getTitle(
		HttpServletRequest httpServletRequest, Layout layout, Locale locale) {

		if (layout.isTypeAssetDisplay()) {
			return Optional.ofNullable(
				(InfoItemDetails)httpServletRequest.getAttribute(
					InfoDisplayWebKeys.INFO_ITEM_DETAILS)
			).map(
				infoItemDetails ->
					_infoItemServiceTracker.getFirstInfoItemService(
						InfoItemFieldValuesProvider.class,
						infoItemDetails.getClassName())
			).map(
				infoItemFieldValuesProvider ->
					infoItemFieldValuesProvider.getInfoItemFieldValue(
						httpServletRequest.getAttribute(
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

	private boolean _hasViewPermission(
			Layout layout, PermissionChecker permissionChecker)
		throws PortalException {

		if (!LayoutPermissionUtil.contains(
				permissionChecker, layout, ActionKeys.VIEW)) {

			return false;
		}

		return true;
	}

	private boolean _isCompanyAdmin() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return permissionChecker.isCompanyAdmin();
	}

	private boolean _isEmbeddedPersonalApplicationLayout(Layout layout) {
		if (layout.isTypeControlPanel()) {
			return false;
		}

		String layoutFriendlyURL = layout.getFriendlyURL();

		if (layout.isSystem() &&
			layoutFriendlyURL.equals(
				PropsUtil.get(PropsKeys.CONTROL_PANEL_LAYOUT_FRIENDLY_URL))) {

			return true;
		}

		return false;
	}

	private boolean _isOmniAdmin() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return permissionChecker.isOmniadmin();
	}

	private boolean _isPanelStateOpen(HttpServletRequest httpServletRequest) {
		String layoutReportsPanelState = SessionClicks.get(
			httpServletRequest,
			"com.liferay.layout.reports.web_layoutReportsPanelState", "closed");

		if (Objects.equals(layoutReportsPanelState, "open")) {
			return true;
		}

		return false;
	}

	private boolean _isShow(long plid) {
		return Optional.ofNullable(
			_layoutLocalService.fetchLayout(plid)
		).filter(
			layout ->
				layout.isTypeAssetDisplay() || layout.isTypeContent() ||
				layout.isTypePortlet()
		).filter(
			layout -> !_isEmbeddedPersonalApplicationLayout(layout)
		).filter(
			layout -> {
				try {
					return _hasViewPermission(
						layout, PermissionThreadLocal.getPermissionChecker());
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);

					return false;
				}
			}
		).isPresent();
	}

	private boolean _isShowPanel(HttpServletRequest httpServletRequest) {
		String layoutMode = ParamUtil.getString(
			httpServletRequest, "p_l_mode", Constants.VIEW);

		if (layoutMode.equals(Constants.EDIT)) {
			return false;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				httpServletRequest);

		boolean hidePanel = GetterUtil.getBoolean(
			portalPreferences.getValue(
				LayoutReportsPortletKeys.LAYOUT_REPORTS, "hide-panel"));

		if (hidePanel) {
			return false;
		}

		return true;
	}

	private boolean _isSiteAdmin(long groupId) {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return permissionChecker.isGroupAdmin(groupId);
	}

	private void _processBodyBottomTagBody(PageContext pageContext)
		throws IOException, JspException {

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_portal.getLocale(httpServletRequest), getClass());

		pageContext.setAttribute("resourceBundle", resourceBundle);

		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<div class=\"");

		if (_isPanelStateOpen(httpServletRequest)) {
			jspWriter.write("lfr-has-layout-reports-panel open-admin-panel ");
		}

		jspWriter.write(
			StringBundler.concat(
				"d-print-none lfr-admin-panel lfr-product-menu-panel ",
				"lfr-layout-reports-panel sidenav-fixed sidenav-menu-slider ",
				"sidenav-right\" id=\""));
		jspWriter.write(_portletNamespace);
		jspWriter.write("layoutReportsPanelId\">");
		jspWriter.write(
			"<div class=\"sidebar sidebar-light sidenav-menu sidebar-sm\">");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = _layoutLocalService.fetchLayout(themeDisplay.getPlid());

		try {
			Group group = _groupLocalService.getGroup(
				themeDisplay.getScopeGroupId());

			LayoutReportsDataProvider layoutReportsDataProvider =
				new LayoutReportsDataProvider(
					_layoutReportsGooglePageSpeedConfigurationProvider.
						getApiKey(group));

			_reactRenderer.renderReact(
				new ComponentDescriptor(
					_npmResolver.resolveModuleName("layout-reports-web") +
						"/js/LayoutReportsApp"),
				_getData(
					httpServletRequest, layout, layoutReportsDataProvider,
					themeDisplay),
				httpServletRequest, jspWriter);
		}
		catch (Exception exception) {
			throw new IOException(exception);
		}

		jspWriter.write("</div></div>");
	}

	private static final String _ICON_TMPL_CONTENT = StringUtil.read(
		LayoutReportsProductNavigationControlMenuEntry.class, "icon.tmpl");

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutReportsProductNavigationControlMenuEntry.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Html _html;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	private volatile LayoutReportsGooglePageSpeedConfigurationProvider
		_layoutReportsGooglePageSpeedConfigurationProvider;

	@Reference
	private LayoutSEOLinkManager _layoutSEOLinkManager;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private Portal _portal;

	private String _portletNamespace;

	@Reference
	private PortletURLFactory _portletURLFactory;

	@Reference
	private ReactRenderer _reactRenderer;

}