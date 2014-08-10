/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.component.samples.portlet;

import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.action-timeout=500",
		"com.liferay.portlet.action-url-redirect=true",
		"com.liferay.portlet.active=false",
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.ajaxable=false",
		"com.liferay.portlet.autopropagated-parameters=param1",
		"com.liferay.portlet.autopropagated-parameters=param2",
		"com.liferay.portlet.control-panel-entry-category=content",
		"com.liferay.portlet.control-panel-entry-weight=10.1",
		"com.liferay.portlet.css-class-wrapper=S14",
		"com.liferay.portlet.facebook-integration=fbml",
		"com.liferay.portlet.footer-portal-css=/css/a.css",
		"com.liferay.portlet.footer-portal-css=/css/b.css",
		"com.liferay.portlet.footer-portal-javascript=/js/a.js",
		"com.liferay.portlet.footer-portlet-css=/css/c.css",
		"com.liferay.portlet.footer-portlet-javascript=/js/b.js",
		"com.liferay.portlet.friendly-url-mapping=S14",
		"com.liferay.portlet.friendly-url-routes=" +
			"/META-INF/resources/routes/routes.xml",
		"com.liferay.portlet.header-portal-css=/css/d.css",
		"com.liferay.portlet.header-portal-javascript=/js/c.js",
		"com.liferay.portlet.header-portlet-css=/css/e.css",
		"com.liferay.portlet.header-portlet-javascript=/js/d.js",
		"com.liferay.portlet.icon=/images/a.png",
		"com.liferay.portlet.include=false",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.maximize-edit=true",
		"com.liferay.portlet.maximize-help=true",
		"com.liferay.portlet.parent-struts-path=blah",
		"com.liferay.portlet.pop-up-print=false",
		"com.liferay.portlet.preferences-company-wide=true",
		"com.liferay.portlet.preferences-owned-by-group=false",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.remoteable=true",
		"com.liferay.portlet.render-timeout=500",
		"com.liferay.portlet.render-weight=20",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"com.liferay.portlet.restore-current-view=false",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.show-portlet-access-denied=false",
		"com.liferay.portlet.show-portlet-inactive=false",
		"com.liferay.portlet.struts-path=S14",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=false",
		"com.liferay.portlet.user-principal-strategy=screenName",
		"com.liferay.portlet.virtual-path=/virtual",
		"javax.portlet.name=S14"
	},
	service = Portlet.class
)
public class Sample_14 extends BaseSamplePortlet {

	@Override
	public void init(PortletConfig portletConfig) throws PortletException {
		super.init(portletConfig);

		LiferayPortletConfig liferayPortletConfig =
			(LiferayPortletConfig)portletConfig;

		com.liferay.portal.model.Portlet portletModel =
			liferayPortletConfig.getPortlet();

		System.out.println(
			"action-timeout = " + portletModel.getActionTimeout());
		System.out.println(
			"action-url-redirect = " + portletModel.isActionURLRedirect());
		System.out.println(
			"active = " + portletModel.isActive());
		System.out.println(
			"add-default-resource = " + portletModel.isAddDefaultResource());
		System.out.println(
			"ajaxable = " + portletModel.isAjaxable());
		System.out.println(
			"autopropagated-parameters = " +
				StringUtil.merge(portletModel.getAutopropagatedParameters()));
		System.out.println(
			"control-panel-entry-category = " +
				portletModel.getControlPanelEntryCategory());
		System.out.println(
			"control-panel-entry-weight = " +
				portletModel.getControlPanelEntryWeight());
		System.out.println(
			"css-class-wrapper = " + portletModel.getCssClassWrapper());
		System.out.println(
			"facebook-integration = " + portletModel.getFacebookIntegration());
		System.out.println(
			"footer-portal-css = " + portletModel.getFooterPortalCss());
		System.out.println(
			"footer-portal-javascript = " +
				portletModel.getFooterPortalJavaScript());
		System.out.println(
			"footer-portlet-css = " + portletModel.getFooterPortletCss());
		System.out.println(
			"footer-portlet-javascript = " +
				portletModel.getFooterPortletJavaScript());
		System.out.println(
			"friendly-url-mapping = " + portletModel.getFriendlyURLMapping());
		System.out.println(
			"friendly-url-routes = " + portletModel.getFriendlyURLRoutes());
		System.out.println(
			"header-portal-css = " + portletModel.getHeaderPortalCss());
		System.out.println(
			"header-portal-javascript = " +
				portletModel.getHeaderPortalJavaScript());
		System.out.println(
			"header-portlet-css = " + portletModel.getHeaderPortletCss());
		System.out.println(
			"header-portlet-javascript = " +
				portletModel.getHeaderPortletJavaScript());
		System.out.println(
			"icon = " + portletModel.getIcon());
		System.out.println(
			"include = " + portletModel.isInclude());
		System.out.println(
			"instanceable = " + portletModel.isInstanceable());
		System.out.println(
			"layout-cacheable = " + portletModel.isLayoutCacheable());
		System.out.println(
			"maximize-edit = " + portletModel.isMaximizeEdit());
		System.out.println(
			"maximize-help = " + portletModel.isMaximizeHelp());
		System.out.println(
			"parent-struts-path = " + portletModel.getParentStrutsPath());
		System.out.println(
			"pop-up-print = " + portletModel.isPopUpPrint());
		System.out.println(
			"preferences-company-wide = " +
				portletModel.isPreferencesCompanyWide());
		System.out.println(
			"preferences-owned-by-group = " +
				portletModel.isPreferencesOwnedByGroup());
		System.out.println(
			"preferences-unique-per-layout = " +
				portletModel.isPreferencesUniquePerLayout());
		System.out.println(
			"private-request-attributes = " +
				portletModel.getPrivateRequestAttributes());
		System.out.println(
			"private-session-attributes = " +
				portletModel.getPrivateSessionAttributes());
		System.out.println(
			"remoteable = " + portletModel.isRemoteable());
		System.out.println(
			"render-timeout = " + portletModel.getRenderTimeout());
		System.out.println(
			"render-weight = " + portletModel.getRenderWeight());
		System.out.println(
			"requires-namespaced-parameters = " +
				portletModel.isRequiresNamespacedParameters());
		System.out.println(
			"restore-current-view = " + portletModel.isRestoreCurrentView());
		System.out.println(
			"scopeable = " + portletModel.isScopeable());
		System.out.println(
			"show-portlet-access-denied = " +
				portletModel.isShowPortletAccessDenied());
		System.out.println(
			"show-portlet-inactive = " + portletModel.isShowPortletInactive());
		System.out.println(
			"struts-path = " + portletModel.getStrutsPath());
		System.out.println(
			"system = " + portletModel.isSystem());
		System.out.println(
			"use-default-template = " + portletModel.isUseDefaultTemplate());
		System.out.println(
			"user-principal-strategy = " +
				portletModel.getUserPrincipalStrategy());
		System.out.println(
			"virtual-path = " + portletModel.getVirtualPath());
	}

}