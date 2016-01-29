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

import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Release;
import com.liferay.portlet.VelocityPortlet;
import com.liferay.util.ContentUtil;

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
		"com.liferay.portlet.remoteable=true",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Hello Velocity",
		"javax.portlet.expiration-cache=0",
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

	@Override
	protected String getTemplateId(String name) {
		return name;
	}

	protected TemplateResource getTemplateResource(String templateId) {
		if (templateId.indexOf(StringPool.SLASH) != 0) {
			templateId = StringPool.SLASH.concat(templateId);
		}

		String content = ContentUtil.get("META-INF/resources" + templateId);

		return new StringTemplateResource(templateId, content);
	}

	@Override
	protected void mergeTemplate(
			String templateId, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws Exception {

		TemplateResource templateResource = getTemplateResource(templateId);

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_VM, templateResource, false);

		prepareTemplate(template, portletRequest, portletResponse);

		mergeTemplate(templateId, template, portletRequest, portletResponse);
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.hello.velocity.web)(release.schema.version=1.0.0))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

}