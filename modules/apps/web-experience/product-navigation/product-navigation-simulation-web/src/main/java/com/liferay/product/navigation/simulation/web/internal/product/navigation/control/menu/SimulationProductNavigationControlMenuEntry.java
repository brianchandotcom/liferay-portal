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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.control.menu.BaseProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;
import com.liferay.product.navigation.simulation.application.list.SimulationPanelCategory;
import com.liferay.product.navigation.simulation.web.constants.ProductNavigationSimulationPortletKeys;
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

	@Activate
	public void activate() {
		_portletNamespace = _portal.getPortletNamespace(
			ProductNavigationSimulationPortletKeys.
				PRODUCT_NAVIGATION_SIMULATION);
	}

	@Override
	public String getLabel(Locale locale) {
		return null;
	}

	@Override
	public String getURL(HttpServletRequest request) {
		return null;
	}

	@Override
	public boolean includeBody(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		BodyBottomTag bodyBottomTag = new BodyBottomTag();

		bodyBottomTag.setOutputKey("simulationMenu");

		try {
			bodyBottomTag.doBodyTag(
				request, response, this::_processBodyBottomTagBody);
		}
		catch (JspException je) {
			throw new IOException(je);
		}

		return true;
	}

	@Override
	public boolean includeIcon(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		PortletURL simulationPanelURL = _portletURLFactory.create(
			request,
			ProductNavigationSimulationPortletKeys.
				PRODUCT_NAVIGATION_SIMULATION,
			PortletRequest.RENDER_PHASE);

		try {
			simulationPanelURL.setWindowState(LiferayWindowState.EXCLUSIVE);
		}
		catch (WindowStateException wse) {
			ReflectionUtil.throwException(wse);
		}

		Map<String, String> values = new HashMap<>();

		IconTag iconTag = new IconTag();

		iconTag.setCssClass("icon-monospaced");
		iconTag.setImage("simulation-menu-closed");
		iconTag.setMarkupView("lexicon");

		try {
			values.put("iconTag", iconTag.doTagAsString(request, response));
		}
		catch (JspException je) {
			ReflectionUtil.throwException(je);
		}

		values.put("portletNamespace", _portletNamespace);
		values.put("simulationPanelURL", simulationPanelURL.toString());
		values.put("title", _html.escape(_language.get(request, "simulation")));

		Writer writer = response.getWriter();

		writer.write(StringUtil.replace(_ICON_TMPL_CONTENT, "${", "}", values));

		return true;
	}

	@Override
	public boolean isShow(HttpServletRequest request) throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout.isTypeControlPanel()) {
			return false;
		}

		List<PanelApp> panelApps = _panelAppRegistry.getPanelApps(
			SimulationPanelCategory.SIMULATION,
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroup());

		if (panelApps.isEmpty()) {
			return false;
		}

		return super.isShow(request);
	}

	@Reference(unbind = "-")
	public void setSimulationPanelCategory(
		SimulationPanelCategory simulationPanelCategory) {
	}

	@Reference(unbind = "-")
	protected void setPanelAppRegistry(PanelAppRegistry panelAppRegistry) {
		_panelAppRegistry = panelAppRegistry;
	}

	private void _processBodyBottomTagBody(PageContext pageContext) {
		try {
			Map<String, String> values = new HashMap<>();

			values.put("portletNamespace", _portletNamespace);

			MessageTag messageTag = new MessageTag();

			messageTag.setKey("simulation");

			values.put("sidebarMessage", messageTag.doTagAsString(pageContext));

			IconTag iconTag = new IconTag();

			iconTag.setCssClass("icon-monospaced sidenav-close");
			iconTag.setImage("times");
			iconTag.setMarkupView("lexicon");
			iconTag.setUrl("javascript:;");

			values.put("sidebarIcon", iconTag.doTagAsString(pageContext));

			Writer writer = pageContext.getOut();

			writer.write(
				StringUtil.replace(_BODY_TMPL_CONTENT, "${", "}", values));

			ScriptTag scriptTag = new ScriptTag();

			scriptTag.setUse("liferay-store,io-request,parse-content");

			scriptTag.doBodyTag(pageContext, this::_processScriptTagBody);
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
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
		catch (IOException ioe) {
			ReflectionUtil.throwException(ioe);
		}
	}

	private static final String _BODY_SCRIPT_TMPL_CONTENT = StringUtil.read(
		SimulationProductNavigationControlMenuEntry.class, "body_script.tmpl");

	private static final String _BODY_TMPL_CONTENT = StringUtil.read(
		SimulationProductNavigationControlMenuEntry.class, "body.tmpl");

	private static final String _ICON_TMPL_CONTENT = StringUtil.read(
		SimulationProductNavigationControlMenuEntry.class, "icon.tmpl");

	private static final Log _log = LogFactoryUtil.getLog(
		SimulationProductNavigationControlMenuEntry.class);

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