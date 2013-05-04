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

package com.liferay.portal.tools.deploy;

import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Plugin;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.bridges.alloy.AlloyPortlet;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 */
public class PortletDeployer extends BaseDeployer {

	public static final String JSF_STANDARD =
		"javax.portlet.faces.GenericFacesPortlet";

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		List<String> wars = new ArrayList<String>();
		List<String> jars = new ArrayList<String>();

		for (String arg : args) {
			if (arg.endsWith(".war")) {
				wars.add(arg);
			}
			else if (arg.endsWith(".jar")) {
				jars.add(arg);
			}
		}

		new PortletDeployer(wars, jars);
	}

	public PortletDeployer() {
	}

	public PortletDeployer(List<String> wars, List<String> jars) {
		super(wars, jars);
	}

	@Override
	public void checkArguments() {
		super.checkArguments();

		if (Validator.isNull(portletTaglibDTD)) {
			throw new IllegalArgumentException(
				"The system property deployer.portlet.taglib.dtd is not set");
		}
	}

	@Override
	public void copyXmls(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception {

		super.copyXmls(srcFile, displayName, pluginPackage);

		copyTomcatContextXml(srcFile);

		copyDependencyXml(
			"_servlet_context_include.jsp", srcFile + "/WEB-INF/jsp");
	}

	@Override
	public String getExtraContent(
			double webXmlVersion, File srcFile, String displayName)
		throws Exception {

		StringBundler sb = new StringBundler();

		if (ServerDetector.isWebSphere()) {
			sb.append("<context-param>");
			sb.append("<param-name>");
			sb.append("com.ibm.websphere.portletcontainer.");
			sb.append("PortletDeploymentEnabled");
			sb.append("</param-name>");
			sb.append("<param-value>false</param-value>");
			sb.append("</context-param>");
		}

		File portletXML = new File(
			srcFile + "/WEB-INF/" + Portal.PORTLET_XML_FILE_NAME_STANDARD);
		File webXML = new File(srcFile + "/WEB-INF/web.xml");

		updatePortletXML(portletXML);

		sb.append(getServletContent(portletXML, webXML));

		setupAlloy(srcFile, portletXML);

		String extraContent = super.getExtraContent(
			webXmlVersion, srcFile, displayName);

		sb.append(extraContent);

		return sb.toString();
	}

	@Override
	public String getExtraFiltersContent(double webXmlVersion, File srcFile)
		throws Exception {

		StringBundler sb = new StringBundler(4);

		String extraFiltersContent = super.getExtraFiltersContent(
			webXmlVersion, srcFile);

		sb.append(extraFiltersContent);

		// Ignore filters

		sb.append(getIgnoreFiltersContent(srcFile));

		// Speed filters

		sb.append(getSpeedFiltersContent(srcFile));

		// Servlet context include filters

		sb.append(
			getServletContextIncludeFiltersContent(webXmlVersion, srcFile));

		return sb.toString();
	}

	@Override
	public String getPluginType() {
		return Plugin.TYPE_PORTLET;
	}

	public String getServletContent(File portletXML, File webXML)
		throws Exception {

		StringBundler sb = new StringBundler();

		Document document = SAXReaderUtil.read(portletXML);

		Element rootElement = document.getRootElement();

		List<Element> portletElements = rootElement.elements("portlet");

		for (Element portletElement : portletElements) {
			String portletName = PortalUtil.getJsSafePortletId(
				portletElement.elementText("portlet-name"));
			String portletClass = portletElement.elementText("portlet-class");

			String servletName = portletName + " Servlet";

			sb.append("<servlet>");
			sb.append("<servlet-name>");
			sb.append(servletName);
			sb.append("</servlet-name>");
			sb.append("<servlet-class>");
			sb.append("com.liferay.portal.kernel.servlet.PortletServlet");
			sb.append("</servlet-class>");
			sb.append("<init-param>");
			sb.append("<param-name>portlet-class</param-name>");
			sb.append("<param-value>");
			sb.append(portletClass);
			sb.append("</param-value>");
			sb.append("</init-param>");
			sb.append("<load-on-startup>1</load-on-startup>");
			sb.append("</servlet>");

			sb.append("<servlet-mapping>");
			sb.append("<servlet-name>");
			sb.append(servletName);
			sb.append("</servlet-name>");
			sb.append("<url-pattern>/");
			sb.append(portletName);
			sb.append("/*</url-pattern>");
			sb.append("</servlet-mapping>");
		}

		return sb.toString();
	}

	public void setupAlloy(File srcFile, File portletXML) throws Exception {
		String content = FileUtil.read(portletXML);

		if (!content.contains(AlloyPortlet.class.getName())) {
			return;
		}

		String[] dirNames = FileUtil.listDirs(srcFile + "/WEB-INF/jsp");

		for (String dirName : dirNames) {
			File dir = new File(srcFile + "/WEB-INF/jsp/" + dirName + "/views");

			if (!dir.exists() || !dir.isDirectory()) {
				continue;
			}

			copyDependencyXml("touch.jsp", dir.toString());
		}
	}

	@Override
	public void updateDeployDirectory(File srcFile) throws Exception {
		boolean customPortletXML = false;

		try {
			customPortletXML = PrefsPropsUtil.getBoolean(
				PropsKeys.AUTO_DEPLOY_CUSTOM_PORTLET_XML,
				PropsValues.AUTO_DEPLOY_CUSTOM_PORTLET_XML);
		}
		catch (Exception e) {

			// This will only happen when running the deploy tool in Ant in the
			// classical way where the WAR file is actually massaged and
			// packaged.

			customPortletXML = PropsValues.AUTO_DEPLOY_CUSTOM_PORTLET_XML;
		}

		customPortletXML = GetterUtil.getBoolean(
			System.getProperty("deployer.custom.portlet.xml"),
			customPortletXML);

		if (!customPortletXML) {
			return;
		}

		File portletXML = new File(
			srcFile + "/WEB-INF/" + Portal.PORTLET_XML_FILE_NAME_STANDARD);

		if (portletXML.exists()) {
			File portletCustomXML = new File(
				srcFile + "/WEB-INF/" + Portal.PORTLET_XML_FILE_NAME_CUSTOM);

			if (portletCustomXML.exists()) {
				portletCustomXML.delete();
			}

			portletXML.renameTo(portletCustomXML);
		}
	}

	public void updatePortletXML(File portletXML) throws Exception {
		if (!portletXML.exists()) {
			return;
		}

		String content = FileUtil.read(portletXML);

		content = StringUtil.replace(
			content, "com.liferay.util.bridges.jsp.JSPPortlet",
			MVCPortlet.class.getName());

		FileUtil.write(portletXML, content);
	}

}