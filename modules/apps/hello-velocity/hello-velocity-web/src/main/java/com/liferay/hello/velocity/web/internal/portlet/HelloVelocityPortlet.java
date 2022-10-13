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

package com.liferay.hello.velocity.web.internal.portlet;

import com.liferay.hello.velocity.web.internal.constants.HelloVelocityPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portlet.VelocityPortlet;

import java.io.IOException;
import java.io.Writer;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.MimeResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

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
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Hello Velocity",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.vm",
		"javax.portlet.name=" + HelloVelocityPortletKeys.HELLO_VELOCITY,
		"javax.portlet.portlet.info.keywords=Hello Velocity",
		"javax.portlet.portlet.info.short-title=Hello Velocity",
		"javax.portlet.portlet.info.title=Hello Velocity",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class HelloVelocityPortlet extends VelocityPortlet {

	protected void mergeTemplate(
			String templateId, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws Exception {

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_VM, _getTemplateResource(templateId),
			false);

		prepareTemplate(template, portletRequest, portletResponse);

		mergeTemplate(template, portletResponse);
	}

	protected void mergeTemplate(
			Template template, PortletResponse portletResponse)
		throws Exception {

		Writer writer = null;

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse)portletResponse;

			writer = mimeResponse.getWriter();
		}
		else {
			writer = new UnsyncStringWriter();
		}

		template.processTemplate(writer);
	}

	protected void prepareTemplate(
		Template template, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		template.put("portletConfig", getPortletConfig());
		template.put("portletContext", getPortletContext());
		template.put("preferences", portletRequest.getPreferences());
		template.put(
			"userInfo", portletRequest.getAttribute(PortletRequest.USER_INFO));

		template.put("portletRequest", portletRequest);

		if (portletRequest instanceof ActionRequest) {
			template.put("actionRequest", portletRequest);
		}
		else if (portletRequest instanceof RenderRequest) {
			template.put("renderRequest", portletRequest);
		}
		else {
			template.put("resourceRequest", portletRequest);
		}

		template.put("portletResponse", portletResponse);

		if (portletResponse instanceof ActionResponse) {
			template.put("actionResponse", portletResponse);
		}
		else if (portletRequest instanceof RenderResponse) {
			template.put("renderResponse", portletResponse);
		}
		else {
			template.put("resourceResponse", portletResponse);
		}
	}

	private TemplateResource _getTemplateResource(String templateId) {
		if (templateId.indexOf(StringPool.SLASH) != 0) {
			templateId = StringPool.SLASH.concat(templateId);
		}

		String content = null;

		try {
			content = StringUtil.read(
				HelloVelocityPortlet.class.getClassLoader(),
				"META-INF/resources" + templateId);
		}
		catch (IOException ioException) {
			_log.error(
				"Unable to read the content for META-INF/resources" +
					templateId,
				ioException);
		}

		return new StringTemplateResource(templateId, content);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HelloVelocityPortlet.class);

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.hello.velocity.web)(&(release.schema.version>=1.0.0)(!(release.schema.version>=2.0.0))))"
	)
	private Release _release;

}