/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.setup;

import com.liferay.portal.dao.shard.ShardDataSourceTargetSource;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletFilter;
import com.liferay.portal.model.PortletURLListener;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ThemeLocalServiceUtil;
import com.liferay.portal.servlet.I18nServlet;
import com.liferay.portal.servlet.filters.i18n.I18nFilter;
import com.liferay.portal.util.ExtRegistry;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortletBagFactory;
import com.liferay.portlet.PortletConfigFactoryUtil;
import com.liferay.portlet.PortletFilterFactory;
import com.liferay.portlet.PortletURLListenerFactory;
import com.liferay.portlet.social.util.SocialConfigurationUtil;

import java.util.List;
import java.util.Set;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;

import javax.servlet.ServletContext;

/**
 * @author Manuel de la Peña
 */
public class InitPortalUtil {

	public static void initCompanies(ServletContext servletContext)
		throws Exception {

		try {
			String[] webIds = PortalInstances.getWebIds();

			for (String webId : webIds) {
				PortalInstances.initCompany(servletContext, webId);
			}
		}
		finally {
			CompanyThreadLocal.setCompanyId(
				PortalInstances.getDefaultCompanyId());

			ShardDataSourceTargetSource shardDataSourceTargetSource =
				(ShardDataSourceTargetSource)
					InfrastructureUtil.getShardDataSourceTargetSource();

			if (shardDataSourceTargetSource != null) {
				shardDataSourceTargetSource.resetDataSource();
			}
		}
	}

	public static void initExt(ServletContext servletContext) throws Exception {
		ExtRegistry.registerPortal(servletContext);
	}

	public static void initLayoutTemplates(
			PluginPackage pluginPackage, List<Portlet> portlets,
			ServletContext servletContext)
		throws Exception {

		String[] xmls = new String[] {
			HttpUtil.URLtoString(
				servletContext.getResource(
					"/WEB-INF/liferay-layout-templates.xml")),
			HttpUtil.URLtoString(
				servletContext.getResource(
					"/WEB-INF/liferay-layout-templates-ext.xml"))
		};

		LayoutTemplateLocalServiceUtil.init(
			servletContext, xmls, pluginPackage);
	}

	public static PluginPackage initPluginPackage(ServletContext servletContext)
		throws Exception {

		return PluginPackageUtil.readPluginPackageServletContext(
			servletContext);
	}

	public static void initPlugins() {
		HotDeployUtil.setCapturePrematureEvents(false);

		PortalLifecycleUtil.flushInits();
	}

	public static List<Portlet> initPortlets(
			PluginPackage pluginPackage, ServletContext servletContext)
		throws Exception {

		String[] xmls = new String[] {
			HttpUtil.URLtoString(
				servletContext.getResource(
					"/WEB-INF/" + Portal.PORTLET_XML_FILE_NAME_CUSTOM)),
			HttpUtil.URLtoString(
				servletContext.getResource("/WEB-INF/portlet-ext.xml")),
			HttpUtil.URLtoString(
				servletContext.getResource("/WEB-INF/liferay-portlet.xml")),
			HttpUtil.URLtoString(
				servletContext.getResource("/WEB-INF/liferay-portlet-ext.xml")),
			HttpUtil.URLtoString(
				servletContext.getResource("/WEB-INF/web.xml"))
		};

		PortletLocalServiceUtil.initEAR(servletContext, xmls, pluginPackage);

		PortletBagFactory portletBagFactory = new PortletBagFactory();

		portletBagFactory.setClassLoader(
			PortalClassLoaderUtil.getClassLoader());
		portletBagFactory.setServletContext(servletContext);
		portletBagFactory.setWARFile(false);

		List<Portlet> portlets = PortletLocalServiceUtil.getPortlets();

		for (int i = 0; i < portlets.size(); i++) {
			Portlet portlet = portlets.get(i);

			portletBagFactory.create(portlet);

			if (i == 0) {
				initPortletApp(portlet, servletContext);
			}
		}

		return portlets;
	}

	public static void initResourceActions(List<Portlet> portlets)
		throws Exception {

		for (Portlet portlet : portlets) {
			List<String> portletActions =
				ResourceActionsUtil.getPortletResourceActions(portlet);

			ResourceActionLocalServiceUtil.checkResourceActions(
				portlet.getPortletId(), portletActions);

			List<String> modelNames =
				ResourceActionsUtil.getPortletModelResources(
					portlet.getPortletId());

			for (String modelName : modelNames) {
				List<String> modelActions =
					ResourceActionsUtil.getModelResourceActions(modelName);

				ResourceActionLocalServiceUtil.checkResourceActions(
					modelName, modelActions);
			}
		}
	}

	public static void initServletContextPool(ServletContext servletContext)
		throws Exception {

		String contextPath = PortalUtil.getPathContext();

		ServletContextPool.put(contextPath, servletContext);
	}

	public static void initSocial(ServletContext servletContext)
		throws Exception {

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		String[] xmls = new String[] {
			HttpUtil.URLtoString(
				servletContext.getResource("/WEB-INF/liferay-social.xml")),
			HttpUtil.URLtoString(
				servletContext.getResource("/WEB-INF/liferay-social-ext.xml"))
		};

		SocialConfigurationUtil.read(classLoader, xmls);
	}

	public static void initThemes(
			PluginPackage pluginPackage, ServletContext servletContext)
		throws Exception {

		String[] xmls = new String[] {
			HttpUtil.URLtoString(
				servletContext.getResource(
					"/WEB-INF/liferay-look-and-feel.xml")),
			HttpUtil.URLtoString(
				servletContext.getResource(
					"/WEB-INF/liferay-look-and-feel-ext.xml"))
		};

		ThemeLocalServiceUtil.init(
			servletContext, null, true, xmls, pluginPackage);
	}

	public static void initWebSettings(ServletContext servletContext)
		throws Exception {

		String xml = HttpUtil.URLtoString(
			servletContext.getResource("/WEB-INF/web.xml"));

		checkWebSettings(xml);
	}

	protected static void checkWebSettings(String xml)
		throws DocumentException {

		Document doc = SAXReaderUtil.read(xml);

		Element root = doc.getRootElement();

		int timeout = PropsValues.SESSION_TIMEOUT;

		Element sessionConfig = root.element("session-config");

		if (sessionConfig != null) {
			String sessionTimeout = sessionConfig.elementText(
				"session-timeout");

			timeout = GetterUtil.getInteger(sessionTimeout, timeout);
		}

		PropsUtil.set(PropsKeys.SESSION_TIMEOUT, String.valueOf(timeout));

		PropsValues.SESSION_TIMEOUT = timeout;

		I18nServlet.setLanguageIds(root);
		I18nFilter.setLanguageIds(I18nServlet.getLanguageIds());
	}

	protected static void initPortletApp(
			Portlet portlet, ServletContext servletContext)
		throws PortletException {

		PortletApp portletApp = portlet.getPortletApp();

		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			portlet, servletContext);

		PortletContext portletContext = portletConfig.getPortletContext();

		Set<PortletFilter> portletFilters = portletApp.getPortletFilters();

		for (PortletFilter portletFilter : portletFilters) {
			PortletFilterFactory.create(portletFilter, portletContext);
		}

		Set<PortletURLListener> portletURLListeners =
			portletApp.getPortletURLListeners();

		for (PortletURLListener portletURLListener : portletURLListeners) {
			PortletURLListenerFactory.create(portletURLListener);
		}
	}

}