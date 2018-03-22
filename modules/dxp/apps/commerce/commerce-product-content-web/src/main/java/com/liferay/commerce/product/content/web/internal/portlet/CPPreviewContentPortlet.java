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

package com.liferay.commerce.product.content.web.internal.portlet;

import com.liferay.commerce.price.CommercePriceFormatter;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.web.internal.display.context.CPPreviewContentDisplayContext;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-commerce-product-content-preview",
		"com.liferay.portlet.display-category=commerce",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Product Preview",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/product_preview/view.jsp",
		"javax.portlet.name=" + CPPortletKeys.CP_PREVIEW_CONTENT_WEB,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {CPPreviewContentPortlet.class, Portlet.class}
)
public class CPPreviewContentPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		CPPreviewContentDisplayContext cpPreviewContentDisplayContext =
			new CPPreviewContentDisplayContext(
				_commercePriceFormatter, _cpDefinitionOptionRelService,
				_cpDefinitionService, _cpInstanceHelper, _cpInstanceService,
				renderRequest, renderResponse);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, cpPreviewContentDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CPDefinitionOptionRelService _cpDefinitionOptionRelService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private CPInstanceService _cpInstanceService;

}