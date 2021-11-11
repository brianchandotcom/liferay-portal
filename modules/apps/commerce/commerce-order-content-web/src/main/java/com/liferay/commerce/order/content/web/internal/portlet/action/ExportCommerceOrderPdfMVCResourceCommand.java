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

package com.liferay.commerce.order.content.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.util.JasperReportHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	enabled = false,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_ORDER_CONTENT,
		"mvc.command.name=/commerce_order_content/export_commerce_order_pdf"
	},
	service = MVCResourceCommand.class
)
public class ExportCommerceOrderPdfMVCResourceCommand
	extends BaseMVCResourceCommand {

	public String getLogoURL(ThemeDisplay themeDisplay) throws PortalException {
		String logoURL = StringPool.BLANK;

		Company company = themeDisplay.getCompany();

		if (company.isSiteLogo()) {
			Group group = themeDisplay.getScopeGroup();

			if (group == null) {
				return logoURL;
			}

			logoURL = group.getLogoURL(themeDisplay, false);
		}
		else {
			logoURL = themeDisplay.getCompanyLogo();
		}

		return _portal.getPortalURL(themeDisplay) + logoURL;
	}

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		resourceResponse.setContentType(ContentTypes.APPLICATION_PDF);

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long commerceOrderId = ParamUtil.getLong(
			resourceRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		CommerceAddress shippingAddress = commerceOrder.getShippingAddress();

		CommerceAddress billingAddress = commerceOrder.getBillingAddress();

		HashMapBuilder.HashMapWrapper<String, Object> parameters =
			HashMapBuilder.<String, Object>put(
				"billingAddressCity", billingAddress.getCity()
			).put(
				"billingAddressCountry",
				() -> {
					Country billingAddressCountry = billingAddress.getCountry();

					return billingAddressCountry.getName(
						themeDisplay.getLocale());
				}
			).put(
				"billingAddressName", billingAddress.getName()
			).put(
				"billingAddressPhoneNumber", billingAddress.getPhoneNumber()
			).put(
				"billingAddressRegion",
				() -> {
					Region billingAddressRegion = billingAddress.getRegion();

					return billingAddressRegion.getName();
				}
			).put(
				"billingAddressStreet1", billingAddress.getStreet1()
			).put(
				"billingAddressStreet2", billingAddress.getStreet2()
			).put(
				"billingAddressStreet3", billingAddress.getStreet3()
			).put(
				"billingAddressZip", billingAddress.getZip()
			).put(
				"commerceOrderId", commerceOrder.getCommerceOrderId()
			).put(
				"logoUrl", getLogoURL(themeDisplay)
			).put(
				"orderDate", commerceOrder.getOrderDate()
			).put(
				"printedNote", commerceOrder.getPrintedNote()
			).put(
				"shippingAddressCity", shippingAddress.getCity()
			).put(
				"shippingAddressCountry",
				() -> {
					Country shippingAddressCountry =
						shippingAddress.getCountry();

					return shippingAddressCountry.getName(
						themeDisplay.getLocale());
				}
			).put(
				"shippingAddressName", shippingAddress.getName()
			).put(
				"shippingAddressPhoneNumber", shippingAddress.getPhoneNumber()
			).put(
				"shippingAddressRegion",
				() -> {
					Region shippingAddressRegion = shippingAddress.getRegion();

					return shippingAddressRegion.getName();
				}
			).put(
				"shippingAddressStreet1", shippingAddress.getStreet1()
			).put(
				"shippingAddressStreet2", shippingAddress.getStreet2()
			).put(
				"shippingAddressStreet3", shippingAddress.getStreet3()
			).put(
				"shippingAddressZip", shippingAddress.getZip()
			);

		PortletResponseUtil.write(
			resourceResponse,
			_jasperReportHelper.exportPdf(
				commerceOrder.getCommerceOrderItems(), parameters.build()));
	}

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private JasperReportHelper _jasperReportHelper;

	@Reference
	private Portal _portal;

}