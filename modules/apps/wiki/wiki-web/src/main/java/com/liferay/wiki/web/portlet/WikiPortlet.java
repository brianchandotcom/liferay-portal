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

package com.liferay.wiki.web.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.model.Release;
import com.liferay.wiki.constants.WikiPortletKeys;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-wiki",
		"com.liferay.portlet.display-category=category.wiki",
		"com.liferay.portlet.header-portlet-css=/wiki/css/main.css",
		"com.liferay.portlet.icon=/wiki/icons/wiki.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.struts-path=wiki",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Wiki", "javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/wiki/view.jsp",
		"javax.portlet.name=" + WikiPortletKeys.WIKI,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supported-public-render-parameter=categoryId",
		"javax.portlet.supported-public-render-parameter=nodeId;http://www.liferay.com/public-render-parameters/wiki",
		"javax.portlet.supported-public-render-parameter=nodeName;http://www.liferay.com/public-render-parameters/wiki",
		"javax.portlet.supported-public-render-parameter=resetCur",
		"javax.portlet.supported-public-render-parameter=tag",
		"javax.portlet.supported-public-render-parameter=title;http://www.liferay.com/public-render-parameters/wiki",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class WikiPortlet extends MVCPortlet {

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.wiki.web)(release.schema.version=1.0.0))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

}