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

package com.liferay.portlet.tck.bridge.setup;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.net.URI;
import java.net.URL;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.framework.Bundle;

/**
 * @author Vernon Singleton
 * @author Kyle Stiemann
 */
public class Setup {

	public static void setupPortletTCKSite(
			String tckDeployFilesDir, Bundle[] bundles)
		throws Exception {

		Company company = CompanyLocalServiceUtil.getCompanyByWebId(
			PropsValues.COMPANY_DEFAULT_WEB_ID);

		Group group = GroupLocalServiceUtil.fetchGroup(
			company.getCompanyId(), _TCK_SITE_GROUP_NAME);

		if (group != null) {
			return;
		}

		User user = UserLocalServiceUtil.getUserByEmailAddress(
			company.getCompanyId(),
			PropsValues.DEFAULT_ADMIN_EMAIL_ADDRESS_PREFIX + "@" +
				company.getMx());

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));
		}

		Map<Locale, String> nameMap = Collections.singletonMap(
			Locale.US, _TCK_SITE_GROUP_NAME);

		try {
			group = GroupLocalServiceUtil.addGroup(
				user.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID, null,
				0L, GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap, nameMap,
				GroupConstants.TYPE_SITE_OPEN, false,
				GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, "/portlet-tck",
				true, false, true, new ServiceContext());

			File configFile = new File(
				tckDeployFilesDir + "/pluto-portal-driver-config.xml");

			URI configFileURI = configFile.toURI();

			URL configFileURL = configFileURI.toURL();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"setupPortletTCKSite: configFileURL = " +
						configFileURL.toString());
			}

			Document document = SAXReaderUtil.read(configFileURL);

			Element rootElement = document.getRootElement();

			Element renderConfigElement = rootElement.element("render-config");

			Iterator<Element> pageElementIterator =
				renderConfigElement.elementIterator("page");

			while (pageElementIterator.hasNext()) {
				Element pageElement = pageElementIterator.next();

				Iterator<Element> portletElementIterator =
					pageElement.elementIterator("portlet");

				List<Portlet> portlets = new LinkedList<>();

				while (portletElementIterator.hasNext()) {
					Element portletElement = portletElementIterator.next();

					Attribute contextAttribute = portletElement.attribute(
						"context");

					String context = contextAttribute.getValue();

					if (context.startsWith("/tck-")) {
						portlets.add(_createPortlet(portletElement, context));
					}
				}

				if (portlets.isEmpty()) {
					continue;
				}

				Attribute pageNameAttribute = pageElement.attribute("name");

				_setupPage(
					user.getUserId(), group.getGroupId(),
					pageNameAttribute.getValue(), portlets, bundles);
			}
		}
		finally {
			if (permissionChecker == null) {
				PermissionThreadLocal.setPermissionChecker(null);
			}
		}
	}

	private static Portlet _createPortlet(Element element, String context) {
		context = context.replaceFirst("^/", "");

		context = context.replaceFirst("(-[0-9.]+)?(-SNAPSHOT)?$", "");

		Attribute nameAttribute = element.attribute("name");

		String portletName = nameAttribute.getValue();

		return new Portlet(context, portletName);
	}

	private static void _setupPage(
			long userId, long groupId, String portalPageName,
			List<Portlet> portlets, Bundle[] bundles)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);

		Layout portalPageLayout = LayoutLocalServiceUtil.addLayout(
			userId, groupId, true, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			portalPageName, portalPageName, portalPageName,
			LayoutConstants.TYPE_PORTLET, false,
			"/" + StringUtil.toLowerCase(portalPageName), serviceContext);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)portalPageLayout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(userId, "1_column", false);

		for (Portlet portlet : portlets) {
			String servletContextName = portlet.getContext();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"setupPage: searching for servetContextName = " +
						servletContextName);
			}

			long bundleId = 0L;
			int bundleState = Bundle.UNINSTALLED;

			for (Bundle bundle : bundles) {
				String symbolicName = bundle.getSymbolicName();

				if (symbolicName.startsWith(servletContextName)) {
					bundleId = bundle.getBundleId();
					bundleState = bundle.getState();
				}
			}

			String portletName = portlet.getPortletName();

			if (_log.isDebugEnabled()) {
				_log.debug("setupPage: final bundleId = " + bundleId);
			}

			if (bundleId > 0) {
				if (bundleState == Bundle.ACTIVE) {
					String noDashPortletName = portletName.replaceAll(
						"[-]", "");

					String warContext = "_WAR_" + servletContextName;

					String portletId =
						noDashPortletName + warContext.replaceAll("[.]", "");

					if (_log.isDebugEnabled()) {
						_log.debug(
							"adding portletId=[" + portletId +
								"] portletName=[" + portletName + "] ...");
					}

					layoutTypePortlet.addPortletId(
						userId, portletId, "column-1", -1);
				}
				else {
					throw new Exception(
						"Unable to add portletName=[" + portletName +
							"] since bundle =[" + servletContextName +
								"] is not active.");
				}
			}
			else {
				throw new Exception(
					"Unable to add portletName=[" + portletName +
						"] since bundleName=[" + servletContextName +
							"] is not deployed.");
			}
		}

		LayoutLocalServiceUtil.updateLayout(portalPageLayout);

		if (_log.isDebugEnabled()) {
			_log.debug("setupPage: groupId = " + groupId);
			_log.debug(
				"setupPage: portalPageLayout.getLayoutId() = " +
					portalPageLayout.getLayoutId());
			_log.debug("setupPage: themeId = portlettck_WAR_potlettcktheme");
			_log.debug(
				"setupPage: portalPageLayout.getColorSchemetId() = " +
					portalPageLayout.getColorSchemeId());
			_log.debug("setupPage: css = ");
			_log.debug(
				"setupPage: LayoutLocalServiceUtil.updateLookAndFeel ...");
		}
	}

	private static final String _TCK_SITE_GROUP_NAME = "Portlet TCK";

	private static final Log _log = LogFactoryUtil.getLog(Setup.class);

}