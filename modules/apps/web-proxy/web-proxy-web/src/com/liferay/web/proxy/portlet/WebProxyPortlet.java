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

package com.liferay.web.proxy.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.StrutsUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.RenderResponseImpl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.Portlet;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import org.portletbridge.portlet.PortletBridgePortlet;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.struts-path=web_proxy",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.css-class-wrapper=portlet-web-proxy",
		"javax.portlet.display-name=Web Proxy",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.authenticatorClassName=org.portletbridge.portlet.DefaultBridgeAuthenticator",
		"javax.portlet.init-param.config-template=/configuration.jsp",
		"javax.portlet.init-param.cssRegex=(?:url\\((?:'|\")?(.*?)(?:'|\")?\\))|(?:@import\\s+[^url](?:'|\")?(.*?)(?:'|\")|;|\\s+|$)",
		"javax.portlet.init-param.editStylesheet=classpath:/org/portletbridge/xsl/pages/edit.xsl",
		"javax.portlet.init-param.errorStylesheet=classpath:/org/portletbridge/xsl/pages/error.xsl",
		"javax.portlet.init-param.helpStylesheet=classpath:/org/portletbridge/xsl/pages/help.xsl",
		"javax.portlet.init-param.idParamKey=id",
		"javax.portlet.init-param.jsRegex=open\\('([^']*)'|open\\(\"([^\\\"]*)\"",
		"javax.portlet.init-param.mementoSessionKey=mementoSessionKey",
		"javax.portlet.init-param.parserClassName=org.cyberneko.html.parsers.SAXParser",
		"javax.portlet.init-param.servletName=pbhs",
		"javax.portlet.init-param.stylesheetUrl=classpath:/org/portletbridge/xsl/default.xsl",
		"javax.portlet.name=66",
		"javax.portlet.preferences=classpath:/META-INF/preferences/default-preferences.xml",
		"javax.portlet.resource-bundle=org.portletbridge.portlet.PortletBridgePortlet",
		"javax.portlet.security-role-ref=power-user",
		"javax.portlet.security-role-ref=user",
	},
	service = Portlet.class
)
public class WebProxyPortlet extends PortletBridgePortlet {

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (!_enabled) {
			printError(renderResponse);

			return;
		}

		PortletPreferences preferences = renderRequest.getPreferences();

		String initUrl = preferences.getValue("initUrl", StringPool.BLANK);

		if (Validator.isNull(initUrl)) {
			PortletContext portletContext = getPortletContext();

			PortletRequestDispatcher portletRequestDispatcher =
				portletContext.getRequestDispatcher(
					StrutsUtil.TEXT_HTML_DIR + "/portal/portlet_not_setup.jsp");

			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
		else {
			super.doView(renderRequest, renderResponse);

			RenderResponseImpl renderResponseImpl =
				(RenderResponseImpl)renderResponse;

			BufferCacheServletResponse bufferCacheServletResponse =
				(BufferCacheServletResponse)
					renderResponseImpl.getHttpServletResponse();

			String output = bufferCacheServletResponse.getString();

			output = StringUtil.replace(
				output, "//pbhs/", PortalUtil.getPathContext() + "/pbhs/");

			bufferCacheServletResponse.setString(output);
		}
	}

	@Override
	public void init() {
		try {
			super.init();

			_enabled = true;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage());
			}
		}

		if (!_enabled && ServerDetector.isWebLogic() && _log.isInfoEnabled()) {
			_log.info(
				"WebProxyPortlet will not be enabled unless Liferay's " +
					"serializer.jar and xalan.jar files are copied to the " +
						"JDK's endorsed directory");
		}
	}

	protected void printError(RenderResponse renderResponse)
		throws IOException {

		renderResponse.setContentType(ContentTypes.TEXT_HTML_UTF8);

		PrintWriter writer = renderResponse.getWriter();

		writer.print(
			"WebProxyPortlet will not be enabled unless Liferay's " +
				"serializer.jar and xalan.jar files are copied to the " +
					"JDK's endorsed directory");

		writer.close();
	}

	private static Log _log = LogFactoryUtil.getLog(WebProxyPortlet.class);

	private boolean _enabled;

}
