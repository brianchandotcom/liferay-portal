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
package com.liferay.hello.velocity.web.portlet;

import com.liferay.hello.velocity.web.upgrade.HelloVelocityUpgrade;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portlet.VelocityPortlet;

import java.io.InputStream;

import java.util.Scanner;

import javax.portlet.Portlet;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
* @author Peter Fellwock
*/
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-hello-velocity",
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.remoteable=true", "com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.struts-path=hello_velocity",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Hello Velocity", "javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.vm",
		"javax.portlet.portlet.info.keywords=Hello Velocity",
		"javax.portlet.portlet.info.short-title=Hello Velocity",
		"javax.portlet.portlet.info.title=Hello Velocity",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class HelloVelocityPortlet extends VelocityPortlet {

	protected String getTemplateResource(String templateId) {
		InputStream stream = HelloVelocityPortlet.class.getClassLoader().
			getResourceAsStream(templateId);

		if (stream == null) {
			if (_log.isInfoEnabled()) {
				_log.info("Unable to open resource template:" + templateId +
					" from classloader");
			}
		}

		StringBuilder sb = new StringBuilder();

		Scanner scanner = null;

		try {
			scanner = new Scanner(stream);
			scanner.useDelimiter("\\n");
			while (scanner.hasNext()) {
				sb.append(scanner.next());
			}
		}catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info("Exception caught loading template:" + templateId +
					": " + e.getMessage());
			}
		}finally {
			scanner.close();
		}

		return sb.toString();
	}

	@Override
	protected void mergeTemplate(
			String templateId, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws Exception {

		templateId = _templateId;
		TemplateResource templateResource = new StringTemplateResource(
			templateId, getTemplateResource(templateId));

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_VM, templateResource, false);

		prepareTemplate(template, portletRequest, portletResponse);

		mergeTemplate(templateId, template, portletRequest, portletResponse);
	}

	@Reference(unbind = "-")
	protected void setHelloVelocityUpgrade(
		HelloVelocityUpgrade helloVelocityUpgrade) {
	}

	private static Log _log = LogFactoryUtil.getLog(HelloVelocityPortlet.class);

	private static String _templateId = "META-INF/resources/view.vm";

}