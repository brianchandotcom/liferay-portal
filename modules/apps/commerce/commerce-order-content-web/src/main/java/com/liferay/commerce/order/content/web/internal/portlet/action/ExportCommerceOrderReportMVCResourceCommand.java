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

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.report.exporter.CommerceReportExporter;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.text.Format;

import java.util.List;

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
		"mvc.command.name=/commerce_order_content/export_commerce_order_report"
	},
	service = MVCResourceCommand.class
)
public class ExportCommerceOrderReportMVCResourceCommand
	extends BaseMVCResourceCommand {

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

		CommerceAddress billingAddress = commerceOrder.getBillingAddress();
		CommerceAddress shippingAddress = commerceOrder.getShippingAddress();

		HashMapBuilder.HashMapWrapper<String, Object> hashMapWrapper =
			new HashMapBuilder.HashMapWrapper<>();

		CommerceAccount commerceAccount = commerceOrder.getCommerceAccount();

		if (billingAddress != null) {
			hashMapWrapper.put(
				"billingAddressCity", billingAddress.getCity()
			).put(
				"billingAddressCountry",
				() -> {
					Country country = billingAddress.getCountry();

					if (country == null) {
						return StringPool.BLANK;
					}

					return country.getName(themeDisplay.getLocale());
				}
			).put(
				"billingAddressName", billingAddress.getName()
			).put(
				"billingAddressPhoneNumber", billingAddress.getPhoneNumber()
			).put(
				"billingAddressRegion",
				() -> {
					Region region = billingAddress.getRegion();

					if (region == null) {
						return StringPool.BLANK;
					}

					return region.getName();
				}
			).put(
				"billingAddressStreet1", billingAddress.getStreet1()
			).put(
				"billingAddressStreet2", billingAddress.getStreet2()
			).put(
				"billingAddressStreet3", billingAddress.getStreet3()
			).put(
				"billingAddressZip", billingAddress.getZip()
			);
		}

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		hashMapWrapper.put(
			"commerceAccountName", commerceAccount.getName()
		).put(
			"commerceOrderId", commerceOrder.getCommerceOrderId()
		).put(
			"commerceOrderItemsSize", commerceOrderItems.size()
		).put(
			"companyId", commerceAccount.getCompanyId()
		).put(
			"externalReferenceCode",
			(commerceOrder.getExternalReferenceCode() != null) ?
				commerceOrder.getExternalReferenceCode() : StringPool.BLANK
		).put(
			"locale", themeDisplay.getLocale()
		).put(
			"logoURL", _getLogoURL(themeDisplay)
		).put(
			"orderDate",
			(commerceOrder.getOrderDate() == null) ? null :
				commerceOrder.getOrderDate()
		).put(
			"printedNote",
			(commerceOrder.getPrintedNote() == null) ? StringPool.BLANK :
				commerceOrder.getPrintedNote()
		).put(
			"purchaseOrderNumber", commerceOrder.getPurchaseOrderNumber()
		).put(
			"requestedDeliveryDate",
			() -> {
				if (commerceOrder.getRequestedDeliveryDate() == null) {
					return null;
				}

				Format format = FastDateFormatFactoryUtil.getDate(
					themeDisplay.getLocale(), themeDisplay.getTimeZone());

				return format.format(commerceOrder.getRequestedDeliveryDate());
			}
		);

		if (shippingAddress != null) {
			hashMapWrapper.put(
				"shippingAddressCity", shippingAddress.getCity()
			).put(
				"shippingAddressCountry",
				() -> {
					Country country = shippingAddress.getCountry();

					if (country == null) {
						return StringPool.BLANK;
					}

					return country.getName(themeDisplay.getLocale());
				}
			).put(
				"shippingAmountMoney", commerceOrder.getShippingMoney()
			).put(
				"shippingAddressName", shippingAddress.getName()
			).put(
				"shippingAddressPhoneNumber", shippingAddress.getPhoneNumber()
			).put(
				"shippingAddressRegion",
				() -> {
					Region region = shippingAddress.getRegion();

					if (region == null) {
						return StringPool.BLANK;
					}

					return region.getName();
				}
			).put(
				"shippingAddressStreet1", shippingAddress.getStreet1()
			).put(
				"shippingAddressStreet2", shippingAddress.getStreet2()
			).put(
				"shippingAddressStreet3", shippingAddress.getStreet3()
			).put(
				"shippingAddressZip", shippingAddress.getZip()
			).put(
				"shippingDiscountAmount",
				_commercePriceFormatter.format(
					commerceOrder.getCommerceCurrency(),
					commerceOrder.getShippingDiscountAmount(),
					themeDisplay.getLocale())
			);
		}

		hashMapWrapper.put(
			"taxAmount",
			_commercePriceFormatter.format(
				commerceOrder.getCommerceCurrency(),
				commerceOrder.getTaxAmount(), themeDisplay.getLocale())
		).put(
			"totalMoney", commerceOrder.getTotalMoney()
		).put(
			"totalWithTaxAmountMoney",
			commerceOrder.getTotalWithTaxAmountMoney()
		);

		FileEntry fileEntry =
			_dlAppLocalService.fetchFileEntryByExternalReferenceCode(
				commerceOrder.getGroupId(), "ORDER_PRINT_TEMPLATE");

		PortletResponseUtil.write(
			resourceResponse,
			_commerceReportExporter.export(
				commerceOrderItems, fileEntry, hashMapWrapper.build()));
	}

	private String _getLogoURL(ThemeDisplay themeDisplay) throws Exception {
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

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CommerceReportExporter _commerceReportExporter;

	@Reference
	private CompanyService _companyService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private Portal _portal;

}