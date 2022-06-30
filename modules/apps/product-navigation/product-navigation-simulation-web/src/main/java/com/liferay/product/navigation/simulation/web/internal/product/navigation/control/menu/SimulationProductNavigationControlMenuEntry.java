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

package com.liferay.product.navigation.simulation.web.internal.product.navigation.control.menu;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.control.menu.BaseProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;
import com.liferay.product.navigation.simulation.constants.ProductNavigationSimulationConstants;
import com.liferay.product.navigation.simulation.constants.ProductNavigationSimulationPortletKeys;
import com.liferay.taglib.aui.IconTag;
import com.liferay.taglib.aui.ScriptTag;
import com.liferay.taglib.ui.MessageTag;
import com.liferay.taglib.util.BodyBottomTag;

import java.io.IOException;
import java.io.Writer;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.USER,
		"product.navigation.control.menu.entry.order:Integer=300"
	},
	service = ProductNavigationControlMenuEntry.class
)
public class SimulationProductNavigationControlMenuEntry
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

		bodyBottomTag.setOutputKey("simulationMenu");

		try {
			bodyBottomTag.doBodyTag(
				httpServletRequest, httpServletResponse,
				this::_processBodyBottomTagBody);
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

		PortletURL simulationPanelURL = PortletURLBuilder.create(
			_portletURLFactory.create(
				httpServletRequest,
				ProductNavigationSimulationPortletKeys.
					PRODUCT_NAVIGATION_SIMULATION,
				PortletRequest.RENDER_PHASE)
		).setBackURL(
			_portal.getCurrentCompleteURL(httpServletRequest)
		).build();

		try {
			simulationPanelURL.setWindowState(LiferayWindowState.EXCLUSIVE);
		}
		catch (WindowStateException windowStateException) {
			ReflectionUtil.throwException(windowStateException);
		}

		Map<String, String> values = new HashMap<>();

		IconTag iconTag = new IconTag();

		iconTag.setCssClass("icon-monospaced");
		iconTag.setImage("simulation-menu-closed");
		iconTag.setMarkupView("lexicon");

		try {
			values.put(
				"iconTag",
				iconTag.doTagAsString(httpServletRequest, httpServletResponse));
		}
		catch (JspException jspException) {
			ReflectionUtil.throwException(jspException);
		}

		values.put("portletNamespace", _portletNamespace);
		values.put("simulationPanelURL", simulationPanelURL.toString());
		values.put(
			"title",
			_html.escape(_language.get(httpServletRequest, "simulation")));

		Writer writer = httpServletResponse.getWriter();

		writer.write(StringUtil.replace(_ICON_TMPL_CONTENT, "${", "}", values));

		return true;
	}

	@Override
	public boolean isShow(HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout.isTypeControlPanel() ||
			isEmbeddedPersonalApplicationLayout(layout)) {

			return false;
		}

		String layoutMode = ParamUtil.getString(
			httpServletRequest, "p_l_mode", Constants.VIEW);

		if (layoutMode.equals(Constants.EDIT)) {
			return false;
		}

		List<PanelApp> panelApps = _panelAppRegistry.getPanelApps(
			ProductNavigationSimulationConstants.SIMULATION_PANEL_CATEGORY_KEY,
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroup());

		if (panelApps.isEmpty()) {
			return false;
		}

		return super.isShow(httpServletRequest);
	}

	@Reference(
		target = "(panel.category.key=" + PanelCategoryKeys.HIDDEN + ")",
		unbind = "-"
	)
	public void setPanelCategory(PanelCategory panelCategory) {
	}

	@Activate
	protected void activate() {
		_portletNamespace = _portal.getPortletNamespace(
			ProductNavigationSimulationPortletKeys.
				PRODUCT_NAVIGATION_SIMULATION);
	}

	@Reference(unbind = "-")
	protected void setPanelAppRegistry(PanelAppRegistry panelAppRegistry) {
		_panelAppRegistry = panelAppRegistry;
	}

	private void _processBodyBottomTagBody(PageContext pageContext) {
		try {
			MessageTag messageTag = new MessageTag();

			messageTag.setKey("simulation");

			Map<String, String> values = HashMapBuilder.put(
				"portletNamespace", _portletNamespace
			).put(
				"sidebarMessage", messageTag.doTagAsString(pageContext)
			).build();

			messageTag = new MessageTag();

			messageTag.setKey("simulation-panel");

			values.put(
				"simulationPanel", messageTag.doTagAsString(pageContext));

			IconTag iconTag = new IconTag();

			iconTag.setAriaLabel(
				_language.get(
					(HttpServletRequest)pageContext.getRequest(), "close"));
			iconTag.setCssClass("close sidenav-close");
			iconTag.setImage("times");
			iconTag.setMarkupView("lexicon");
			iconTag.setUrl("javascript:void(0);");

			values.put("sidebarIcon", iconTag.doTagAsString(pageContext));

			Writer writer = pageContext.getOut();

			writer.write(
				StringUtil.replace(_BODY_TMPL_CONTENT, "${", "}", values));

			ScriptTag scriptTag = new ScriptTag();

			scriptTag.setUse("liferay-store,io-request,parse-content");

			scriptTag.doBodyTag(pageContext, this::_processScriptTagBody);
		}
		catch (Exception exception) {
			ReflectionUtil.throwException(exception);
		}
	}

	private void _processScriptTagBody(PageContext pageContext) {
		Writer writer = pageContext.getOut();

		try {
			writer.write(
				StringUtil.replace(
					_BODY_SCRIPT_TMPL_CONTENT, "${", "}",
					Collections.singletonMap(
						"portletNamespace", _portletNamespace)));
		}
		catch (IOException ioException) {
			ReflectionUtil.throwException(ioException);
		}
	}

	private static final String _BODY_SCRIPT_TMPL_CONTENT = StringUtil.read(
		SimulationProductNavigationControlMenuEntry.class, "body_script.tmpl");

	private static final String _BODY_TMPL_CONTENT = StringUtil.read(
		SimulationProductNavigationControlMenuEntry.class, "body.tmpl");

	private static final String _ICON_TMPL_CONTENT = StringUtil.read(
		SimulationProductNavigationControlMenuEntry.class, "icon.tmpl");

	@Reference
	private Html _html;

	@Reference
	private Language _language;

	private PanelAppRegistry _panelAppRegistry;

	@Reference
	private Portal _portal;

	private String _portletNamespace;

	@Reference
	private PortletURLFactory _portletURLFactory;

}