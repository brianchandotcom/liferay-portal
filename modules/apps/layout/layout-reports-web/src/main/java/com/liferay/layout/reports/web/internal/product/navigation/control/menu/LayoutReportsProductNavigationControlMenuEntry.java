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

import com.liferay.layout.reports.web.internal.configuration.LayoutReportsPageSpeedCompanyConfiguration;
import com.liferay.layout.reports.web.internal.configuration.LayoutReportsPageSpeedConfiguration;
import com.liferay.layout.reports.web.internal.constants.LayoutReportsPortletKeys;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
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
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.control.menu.BaseProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;
import com.liferay.taglib.aui.IconTag;
import com.liferay.taglib.portletext.RuntimeTag;
import com.liferay.taglib.util.BodyBottomTag;

import java.io.IOException;
import java.io.Writer;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.portlet.RenderRequest;
import javax.portlet.WindowStateException;

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
	configurationPid = "com.liferay.layout.reports.web.internal.configuration.LayoutReportsPageSpeedConfiguration",
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
			values.put("layoutReportsPanelURL", StringPool.BLANK);
		}
		else {
			values.put("cssClass", StringPool.BLANK);

			try {
				values.put(
					"layoutReportsPanelURL",
					_getLayoutReportsPanelURL(
						httpServletRequest, _portal, _portletURLFactory));
			}
			catch (WindowStateException windowStateException) {
				throw new IOException(windowStateException);
			}
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

		if (!_isEnabled(
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
		_layoutReportsPageSpeedConfiguration =
			ConfigurableUtil.createConfigurable(
				LayoutReportsPageSpeedConfiguration.class, properties);

		_portletNamespace = _portal.getPortletNamespace(
			LayoutReportsPortletKeys.LAYOUT_REPORTS);
	}

	private String _getLayoutReportsPanelURL(
			HttpServletRequest httpServletRequest, Portal portal,
			PortletURLFactory portletURLFactory)
		throws WindowStateException {

		return PortletURLBuilder.create(
			portletURLFactory.create(
				httpServletRequest, LayoutReportsPortletKeys.LAYOUT_REPORTS,
				RenderRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/layout_reports/layout_reports_panel"
		).setRedirect(
			portal.getCurrentCompleteURL(httpServletRequest)
		).setWindowState(
			LiferayWindowState.EXCLUSIVE
		).buildString();
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

	private boolean _isEnabled(Group group) throws ConfigurationException {
		if (!_layoutReportsPageSpeedConfiguration.enabled()) {
			return false;
		}

		LayoutReportsPageSpeedCompanyConfiguration
			layoutReportsPageSpeedCompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					LayoutReportsPageSpeedCompanyConfiguration.class,
					group.getCompanyId());

		if (!layoutReportsPageSpeedCompanyConfiguration.enabled()) {
			return false;
		}

		return GetterUtil.get(
			group.getTypeSettingsProperty("pageSpeedEnabled"), true);
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
			layout -> layout.isTypePortlet() || layout.isTypeAssetDisplay()
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

		RuntimeTag runtimeTag = new RuntimeTag();

		runtimeTag.setPortletName(LayoutReportsPortletKeys.LAYOUT_REPORTS);

		runtimeTag.doTag(pageContext);

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
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	private volatile LayoutReportsPageSpeedConfiguration
		_layoutReportsPageSpeedConfiguration;

	@Reference
	private Portal _portal;

	private String _portletNamespace;

	@Reference
	private PortletURLFactory _portletURLFactory;

}