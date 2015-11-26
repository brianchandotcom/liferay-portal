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

package com.liferay.layout.set.prototype.web.portlet;

import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.layout.set.prototype.constants.LayoutSetPrototypePortletKeys;
import com.liferay.portal.NoSuchLayoutSetPrototypeException;
import com.liferay.portal.RequiredLayoutSetPrototypeException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutSetPrototypeService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.sites.util.SitesUtil;

import java.io.IOException;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-layout-set-prototype",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.icon=/icons/layout_set_prototypes.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Site Templates",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + LayoutSetPrototypePortletKeys.LAYOUT_SET_PROTOTYPE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {Portlet.class}
)
public class LayoutSetPrototypePortlet extends MVCPortlet {

	public void deleteLayoutSetPrototypes(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] layoutSetPrototypeIds = null;

		long layoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "layoutSetPrototypeId");

		if (layoutSetPrototypeId > 0) {
			layoutSetPrototypeIds = new long[] {layoutSetPrototypeId};
		}
		else {
			layoutSetPrototypeIds = ParamUtil.getLongValues(
				actionRequest, "rowIds");
		}

		for (long curLayoutSetPrototypeId : layoutSetPrototypeIds) {
			layoutSetPrototypeService.deleteLayoutSetPrototype(
				curLayoutSetPrototypeId);
		}
	}

	public void resetMergeFailCount(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long layoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "layoutSetPrototypeId");

		LayoutSetPrototype layoutSetPrototype =
			layoutSetPrototypeService.getLayoutSetPrototype(
				layoutSetPrototypeId);

		SitesUtil.setMergeFailCount(layoutSetPrototype, 0);
	}

	public void updateLayoutSetPrototype(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long layoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "layoutSetPrototypeId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");
		boolean layoutsUpdateable = ParamUtil.getBoolean(
			actionRequest, "layoutsUpdateable");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		LayoutSetPrototype layoutSetPrototype = null;

		if (layoutSetPrototypeId <= 0) {

			// Add layout prototoype

			layoutSetPrototype =
				layoutSetPrototypeService.addLayoutSetPrototype(
					nameMap, descriptionMap, active, layoutsUpdateable,
					serviceContext);

			MultiSessionMessages.add(
				actionRequest,
				LayoutSetPrototypePortletKeys.SITE_TEMPLATE_SETTINGS +
					"requestProcessed");
		}
		else {

			// Update layout prototoype

			layoutSetPrototype =
				layoutSetPrototypeService.updateLayoutSetPrototype(
					layoutSetPrototypeId, nameMap, descriptionMap, active,
					layoutsUpdateable, serviceContext);
		}

		// Custom JSPs

		String customJspServletContextName = ParamUtil.getString(
			actionRequest, "customJspServletContextName");

		UnicodeProperties settingsProperties =
			layoutSetPrototype.getSettingsProperties();

		settingsProperties.setProperty(
			"customJspServletContextName", customJspServletContextName);

		layoutSetPrototype = layoutSetPrototypeService.updateLayoutSetPrototype(
			layoutSetPrototype.getLayoutSetPrototypeId(),
			settingsProperties.toString());

		PortletURL siteAdministrationURL = PortalUtil.getControlPanelPortletURL(
			actionRequest, layoutSetPrototype.getGroup(),
			LayoutSetPrototypePortletKeys.SITE_TEMPLATE_SETTINGS, 0, 0,
			PortletRequest.RENDER_PHASE);

		actionRequest.setAttribute(
			WebKeys.REDIRECT, siteAdministrationURL.toString());

		sendRedirect(actionRequest, actionResponse);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(
			_panelAppRegistry, _panelCategoryRegistry);

		renderRequest.setAttribute(
			ApplicationListWebKeys.PANEL_CATEGORY_HELPER, panelCategoryHelper);

		if (SessionErrors.contains(
				renderRequest, PrincipalException.getNestedClasses())) {

			include("/error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof NoSuchLayoutSetPrototypeException ||
			cause instanceof PrincipalException ||
			cause instanceof RequiredLayoutSetPrototypeException) {

			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetPrototypeService(
		LayoutSetPrototypeService layoutSetPrototypeService) {

		this.layoutSetPrototypeService = layoutSetPrototypeService;
	}

	@Reference(unbind = "-")
	protected void setPanelAppRegistry(PanelAppRegistry panelAppRegistry) {
		_panelAppRegistry = panelAppRegistry;
	}

	@Reference(unbind = "-")
	protected void setPanelCategoryRegistry(
		PanelCategoryRegistry panelCategoryRegistry) {

		_panelCategoryRegistry = panelCategoryRegistry;
	}

	protected LayoutSetPrototypeService layoutSetPrototypeService;

	private volatile PanelAppRegistry _panelAppRegistry;
	private volatile PanelCategoryRegistry _panelCategoryRegistry;

}