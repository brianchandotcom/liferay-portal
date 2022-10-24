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

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.reports.web.internal.configuration.provider.LayoutReportsGooglePageSpeedConfigurationProvider;
import com.liferay.layout.reports.web.internal.constants.LayoutReportsPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
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
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.StringUtil;
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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 */
@Component(
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.USER,
		"product.navigation.control.menu.entry.order:Integer=550"
	},
	service = {
		LayoutReportsProductNavigationControlMenuEntry.class,
		ProductNavigationControlMenuEntry.class
	}
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

		if (isPanelStateOpen(httpServletRequest)) {
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

	public boolean isPanelStateOpen(HttpServletRequest httpServletRequest) {
		String layoutReportsPanelState = SessionClicks.get(
			httpServletRequest, _SESSION_CLICKS_KEY, "closed");

		if (Objects.equals(layoutReportsPanelState, "open")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isShow(HttpServletRequest httpServletRequest)
		throws PortalException {

		long scopeGroupId = _portal.getScopeGroupId(httpServletRequest);

		if ((scopeGroupId == 0) ||
			!_layoutReportsGooglePageSpeedConfigurationProvider.isEnabled(
				_groupLocalService.getGroup(scopeGroupId))) {

			return false;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!_isShow(themeDisplay) || !_isShowPanel(httpServletRequest)) {
			return false;
		}

		return super.isShow(httpServletRequest);
	}

	public void setPanelState(
		HttpServletRequest httpServletRequest, String panelState) {

		SessionClicks.put(httpServletRequest, _SESSION_CLICKS_KEY, panelState);
	}

	public static class ProcessBodyBottomTagBodyException
		extends RuntimeException {

		public ProcessBodyBottomTagBodyException(Throwable throwable) {
			super(throwable);
		}

	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_portletNamespace = _portal.getPortletNamespace(
			LayoutReportsPortletKeys.LAYOUT_REPORTS);
	}

	private String _getLayoutReportsDataURL(
		HttpServletRequest httpServletRequest) {

		return PortletURLBuilder.create(
			_portletURLFactory.create(
				httpServletRequest, LayoutReportsPortletKeys.LAYOUT_REPORTS,
				PortletRequest.RESOURCE_PHASE)
		).setParameter(
			"p_p_resource_id", "/layout_reports/data"
		).setParameter(
			"plid",
			() -> {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				return themeDisplay.getPlid();
			}
		).buildString();
	}

	private boolean _hasEditPermission(
			Layout layout, PermissionChecker permissionChecker)
		throws PortalException {

		if (!LayoutPermissionUtil.contains(
				permissionChecker, layout, ActionKeys.UPDATE)) {

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

	private boolean _isShow(ThemeDisplay themeDisplay) {
		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		return Optional.ofNullable(
			_layoutLocalService.fetchLayout(themeDisplay.getPlid())
		).filter(
			layout ->
				layout.isTypeAssetDisplay() || layout.isTypeContent() ||
				layout.isTypePortlet()
		).filter(
			layout -> !_isEmbeddedPersonalApplicationLayout(layout)
		).filter(
			layout -> {
				try {
					if (permissionChecker.hasPermission(
							themeDisplay.getScopeGroup(),
							BlogsEntry.class.getName(),
							BlogsEntry.class.getName(), ActionKeys.UPDATE) ||
						permissionChecker.hasPermission(
							themeDisplay.getScopeGroup(),
							DLFileEntry.class.getName(),
							DLFileEntry.class.getName(), ActionKeys.UPDATE) ||
						permissionChecker.hasPermission(
							themeDisplay.getScopeGroup(),
							JournalArticle.class.getName(),
							JournalArticle.class.getName(),
							ActionKeys.UPDATE)) {

						return true;
					}

					return _hasEditPermission(
						layout, PermissionThreadLocal.getPermissionChecker());
				}
				catch (PortalException portalException) {
					_log.error(portalException);

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

		StringBundler sb = new StringBundler(20);

		sb.append("<div class=\"");

		if (isPanelStateOpen(httpServletRequest)) {
			sb.append("lfr-has-layout-reports-panel open-admin-panel ");
		}

		sb.append("cadmin d-print-none lfr-admin-panel ");
		sb.append("lfr-product-menu-panel lfr-layout-reports-panel ");
		sb.append("sidenav-fixed sidenav-menu-slider sidenav-right\" id=\"");
		sb.append(_portletNamespace);
		sb.append("layoutReportsPanelId\"><div class=\"sidebar sidebar-light ");
		sb.append("sidenav-menu sidebar-sm\"><div class=\"sidebar-header\">");
		sb.append("<div class=\"autofit-row autofit-row-center\"><div ");
		sb.append("class=\"autofit-col autofit-col-expand\">");
		sb.append("<h1 class=\"sr-only\">");
		sb.append(_html.escape(_language.get(resourceBundle, "page-audit")));
		sb.append("</h1><span>");
		sb.append(_html.escape(_language.get(resourceBundle, "page-audit")));
		sb.append("</span></div>");
		sb.append("<div class=\"autofit-col\">");

		IconTag iconTag = new IconTag();

		iconTag.setCssClass("icon-monospaced sidenav-close");
		iconTag.setImage("times");
		iconTag.setUrl("javascript:void(0);");

		sb.append(iconTag.doTagAsString(pageContext));

		sb.append("</div></div></div><div class=\"sidebar-body\"><span ");
		sb.append("aria-hidden=\"true\" class=\"loading-animation ");
		sb.append("loading-animation-sm\"></span></div>");

		jspWriter.write(sb.toString());

		try {
			_reactRenderer.renderReact(
				new ComponentDescriptor(
					_npmResolver.resolveModuleName("layout-reports-web") +
						"/js/App"),
				HashMapBuilder.<String, Object>put(
					"isPanelStateOpen", isPanelStateOpen(httpServletRequest)
				).put(
					"layoutReportsDataURL",
					_getLayoutReportsDataURL(httpServletRequest)
				).put(
					"portletNamespace", _portletNamespace
				).build(),
				httpServletRequest, jspWriter);
		}
		catch (Exception exception) {
			throw new IOException(exception);
		}

		jspWriter.write("</div></div>");
	}

	private static final String _ICON_TMPL_CONTENT = StringUtil.read(
		LayoutReportsProductNavigationControlMenuEntry.class, "icon.tmpl");

	private static final String _SESSION_CLICKS_KEY =
		"com.liferay.layout.reports.web_layoutReportsPanelState";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutReportsProductNavigationControlMenuEntry.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Html _html;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutReportsGooglePageSpeedConfigurationProvider
		_layoutReportsGooglePageSpeedConfigurationProvider;

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