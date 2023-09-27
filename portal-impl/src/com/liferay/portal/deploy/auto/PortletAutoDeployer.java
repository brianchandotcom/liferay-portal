/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.deploy.auto;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.deploy.DeployUtil;
import com.liferay.portal.kernel.deploy.auto.AutoDeployer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Plugin;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.tools.deploy.BaseAutoDeployer;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.util.List;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
public class PortletAutoDeployer
	extends BaseAutoDeployer implements AutoDeployer {

	public PortletAutoDeployer() {
		super(Plugin.TYPE_PORTLET);

		try {
			auiTaglibDTD = DeployUtil.getResourcePath(
				tempDirPaths, "liferay-aui.tld");

			portletTaglibDTD = DeployUtil.getResourcePath(
				tempDirPaths, "liferay-portlet.tld");

			if (Validator.isNull(portletTaglibDTD)) {
				throw new IllegalArgumentException(
					"The system property deployer.portlet.taglib.dtd is not " +
						"set");
			}

			portletExtTaglibDTD = DeployUtil.getResourcePath(
				tempDirPaths, "liferay-portlet-ext.tld");
			securityTaglibDTD = DeployUtil.getResourcePath(
				tempDirPaths, "liferay-security.tld");
			themeTaglibDTD = DeployUtil.getResourcePath(
				tempDirPaths, "liferay-theme.tld");
			uiTaglibDTD = DeployUtil.getResourcePath(
				tempDirPaths, "liferay-ui.tld");
			utilTaglibDTD = DeployUtil.getResourcePath(
				tempDirPaths, "liferay-util.tld");
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	@Override
	public void copyXmls(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception {

		super.copyXmls(srcFile, displayName, pluginPackage);

		copyDependencyXml(
			"_servlet_context_include.jsp", srcFile + "/WEB-INF/jsp");
	}

	@Override
	public String getExtraContent(
			double webXmlVersion, File srcFile, String displayName)
		throws Exception {

		StringBundler sb = new StringBundler(9);

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
		sb.append(super.getExtraContent(webXmlVersion, srcFile, displayName));

		return sb.toString();
	}

	public String getServletContent(File portletXML, File webXML)
		throws Exception {

		if (!portletXML.exists()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		Document document = UnsecureSAXReaderUtil.read(portletXML);

		Element rootElement = document.getRootElement();

		List<Element> portletElements = rootElement.elements("portlet");

		for (Element portletElement : portletElements) {
			String portletName = PortalUtil.getJsSafePortletId(
				portletElement.elementText("portlet-name"));
			String portletClassName = portletElement.elementText(
				"portlet-class");

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
			sb.append(portletClassName);
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

	@Override
	public void updateDeployDirectory(File srcFile) throws Exception {
		boolean customPortletXML = PropsValues.AUTO_DEPLOY_CUSTOM_PORTLET_XML;

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

	private static final Log _log = LogFactoryUtil.getLog(
		PortletAutoDeployer.class);

}