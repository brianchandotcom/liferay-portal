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

package com.liferay.commerce.organization.order.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHelper;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.organization.order.web.internal.display.context.CommerceOrganizationOrderDisplayContext;
import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommercePriceCalculationLocalService;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.RenderRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + CommercePortletKeys.COMMERCE_ORGANIZATION_OPEN_ORDER,
	service = ConfigurationAction.class
)
public class CommerceOrganizationOpenOrderConfigurationAction
	extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		try {
			RenderRequest renderRequest =
				(RenderRequest)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST);

			CommerceOrganizationOrderDisplayContext
				commerceOrganizationOrderDisplayContext =
					new CommerceOrganizationOrderDisplayContext(
						_commerceAddressService, _commerceOrderHelper,
						_commerceOrderHttpHelper, _commerceOrderItemService,
						_commerceOrderLocalService, _commerceOrderNoteService,
						_commerceOrderService, _commerceOrganizationHelper,
						_commercePriceCalculationLocalService,
						_commercePriceFormatter,
						_commerceShippingEngineRegistry, _cpInstanceHelper,
						_jsonFactory, _modelResourcePermission, renderRequest);

			httpServletRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				commerceOrganizationOrderDisplayContext);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return "/open_order/configuration.jsp";
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.organization.order.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrganizationOpenOrderConfigurationAction.class);

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceOrderHelper _commerceOrderHelper;

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private CommerceOrderNoteService _commerceOrderNoteService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrganizationHelper _commerceOrganizationHelper;

	@Reference
	private CommercePriceCalculationLocalService
		_commercePriceCalculationLocalService;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CommerceShippingEngineRegistry _commerceShippingEngineRegistry;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrder)"
	)
	private ModelResourcePermission<CommerceOrder> _modelResourcePermission;

}