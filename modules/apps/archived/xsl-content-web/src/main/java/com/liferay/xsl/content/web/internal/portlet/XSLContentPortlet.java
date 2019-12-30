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

package com.liferay.xsl.content.web.internal.portlet;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.xsl.content.web.internal.configuration.XSLContentConfiguration;
import com.liferay.xsl.content.web.internal.constants.XSLContentPortletKeys;

import java.io.IOException;

import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.xsl.content.web.internal.configuration.XSLContentConfiguration",
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-xsl-content",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.include=false",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"javax.portlet.display-name=XSL Content",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + XSLContentPortletKeys.XSL_CONTENT,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supported-public-render-parameter=tags",
		"xml.doctype.declaration.allowed=false",
		"xml.external.general.entities.allowed=false",
		"xml.external.parameter.entities.allowed=false",
		"xsl.secure.processing.enabled=true"
	},
	service = Portlet.class
)
public class XSLContentPortlet extends MVCPortlet {

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			XSLContentConfiguration.class.getName(), _xslContentConfiguration);

		super.doView(renderRequest, renderResponse);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_xslContentConfiguration = ConfigurableUtil.createConfigurable(
			XSLContentConfiguration.class, properties);
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		_xslContentConfiguration = ConfigurableUtil.createConfigurable(
			XSLContentConfiguration.class, properties);
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.xsl.content.web)(&(release.schema.version>=1.0.0)(!(release.schema.version>=2.0.0))))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	private volatile XSLContentConfiguration _xslContentConfiguration;

}