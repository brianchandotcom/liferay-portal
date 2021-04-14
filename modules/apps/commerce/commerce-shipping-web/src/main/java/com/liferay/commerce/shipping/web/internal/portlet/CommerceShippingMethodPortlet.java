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

package com.liferay.commerce.shipping.web.internal.portlet;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionService;
import com.liferay.commerce.shipping.web.internal.display.context.CommerceShippingMethodsDisplayContext;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=false",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Payment Methods",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/edit_commerce_shipping_method.jsp",
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_SHIPPING_METHODS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = {CommerceShippingMethodPortlet.class, Portlet.class}
)
public class CommerceShippingMethodPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		CommerceShippingMethodsDisplayContext
			commerceShippingMethodsDisplayContext =
				new CommerceShippingMethodsDisplayContext(
					_commerceChannelLocalService,
					_commerceShippingEngineRegistry,
					_commerceShippingFixedOptionService,
					_commerceShippingMethodService, _countryService,
					_portal.getHttpServletRequest(renderRequest));

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceShippingMethodsDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceShippingEngineRegistry _commerceShippingEngineRegistry;

	@Reference
	private CommerceShippingFixedOptionService
		_commerceShippingFixedOptionService;

	@Reference
	private CommerceShippingMethodService _commerceShippingMethodService;

	@Reference
	private CountryService _countryService;

	@Reference
	private Portal _portal;

}