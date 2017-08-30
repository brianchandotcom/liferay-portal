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

package com.liferay.asset.display.template.web.internal.portlet;

import com.liferay.asset.display.template.constants.AssetDisplayTemplatePortletKeys;
import com.liferay.asset.display.template.web.internal.constants.AssetDisplayTemplateWebKeys;
import com.liferay.asset.display.template.web.internal.display.context.AssetDisplayTemplateDisplayContext;
import com.liferay.dynamic.data.mapping.util.DDMDisplayRegistry;
import com.liferay.dynamic.data.mapping.util.DDMTemplateHelper;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-asset-display-template",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Asset Display Templates",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + AssetDisplayTemplatePortletKeys.ASSET_DISPLAY_TEMPLATE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {AssetDisplayTemplatePortlet.class, Portlet.class}
)
public class AssetDisplayTemplatePortlet extends MVCPortlet {

	public void changeDisplayStyle(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		hideDefaultSuccessMessage(actionRequest);

		String displayStyle = ParamUtil.getString(
			actionRequest, "displayStyle");

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(actionRequest);

		portalPreferences.setValue(
			AssetDisplayTemplatePortletKeys.ASSET_DISPLAY_TEMPLATE,
			"display-style", displayStyle);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		HttpServletRequest request = _portal.getHttpServletRequest(
			renderRequest);

		AssetDisplayTemplateDisplayContext assetDisplayTemplateDisplayContext =
			new AssetDisplayTemplateDisplayContext(
				renderRequest, renderResponse, request, _ddmDisplayRegistry,
				_ddmTemplateHelper);

		renderRequest.setAttribute(
			AssetDisplayTemplateWebKeys.DISPLAY_CONTEXT,
			assetDisplayTemplateDisplayContext);

		super.doDispatch(renderRequest, renderResponse);
	}

	@Reference
	private DDMDisplayRegistry _ddmDisplayRegistry;

	@Reference
	private DDMTemplateHelper _ddmTemplateHelper;

	@Reference
	private Portal _portal;

}