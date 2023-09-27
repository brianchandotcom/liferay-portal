/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.info.item.provider;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.content.web.internal.info.CommerceOrderInfoItemFields;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(
	property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFieldValuesProvider.class
)
public class CommerceOrderInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<CommerceOrder> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(
		CommerceOrder commerceOrder) {

		return InfoItemFieldValues.builder(
		).infoFieldValues(
			_getCommerceOrderInfoFieldValues(commerceOrder)
		).infoFieldValues(
			_expandoInfoItemFieldSetProvider.getInfoFieldValues(
				CommerceOrder.class.getName(), commerceOrder)
		).infoFieldValues(
			_templateInfoItemFieldSetProvider.getInfoFieldValues(
				CommerceOrder.class.getName(), commerceOrder)
		).infoFieldValues(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldValues(
				CommerceOrder.class.getName(), commerceOrder)
		).infoItemReference(
			new InfoItemReference(
				CommerceOrder.class.getName(),
				commerceOrder.getCommerceOrderId())
		).build();
	}

	private List<InfoFieldValue<Object>> _getCommerceOrderInfoFieldValues(
		CommerceOrder commerceOrder) {

		List<InfoFieldValue<Object>> commerceOrderInfoFieldValues =
			new ArrayList<>();

		try {
			CommerceCurrency commerceCurrency =
				commerceOrder.getCommerceCurrency();

			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.accountIdInfoField,
					commerceOrder.getCommerceAccountId()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.accountNameInfoField,
					commerceOrder.getCommerceAccountName()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.advanceStatusInfoField,
					commerceOrder.getAdvanceStatus()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.approvedInfoField,
					commerceOrder.isApproved()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						awaitingPickupOrderStatusInfoField,
					_isOrderStatus(
						commerceOrder,
						CommerceOrderConstants.ORDER_STATUS_AWAITING_PICKUP)));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.b2bInfoField,
					commerceOrder.isB2B()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.billingAddressIdInfoField,
					commerceOrder.getBillingAddressId()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.cancelledOrderStatusInfoField,
					_isOrderStatus(
						commerceOrder,
						CommerceOrderConstants.ORDER_STATUS_CANCELLED)));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.currencyIdInfoField,
					commerceOrder.getCommerceCurrencyId()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.companyIdInfoField,
					commerceOrder.getCompanyId()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.companyIdInfoField,
					_isOrderStatus(
						commerceOrder,
						CommerceOrderConstants.ORDER_STATUS_COMPLETED)));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.couponCodeInfoField,
					commerceOrder.getCouponCode()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.createDateInfoField,
					commerceOrder.getCreateDate()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.declinedOrderStatusInfoField,
					_isOrderStatus(
						commerceOrder,
						CommerceOrderConstants.ORDER_STATUS_DECLINED)));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.defaultLanguageIdInfoField,
					commerceCurrency.getDefaultLanguageId()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.defaultLanguageIdInfoField,
					commerceCurrency.getDefaultLanguageId()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						deliveryCommerceTermEntryDescriptionInfoField,
					commerceOrder.getDeliveryCommerceTermEntryDescription()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						deliveryCommerceTermEntryIdInfoField,
					commerceOrder.getDeliveryCommerceTermEntryId()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						deliveryCommerceTermEntryNameInfoField,
					commerceOrder.getDeliveryCommerceTermEntryName()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.deniedInfoField,
					commerceOrder.isDenied()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.declinedOrderStatusInfoField,
					_isOrderStatus(
						commerceOrder,
						CommerceOrderConstants.ORDER_STATUS_DISPUTED)));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.draftInfoField,
					commerceOrder.isDraft()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.emptyInfoField,
					commerceOrder.isEmpty()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.expiredInfoField,
					commerceOrder.isExpired()));

			ThemeDisplay themeDisplay = _getThemeDisplay();

			if (themeDisplay != null) {
				commerceOrderInfoFieldValues.add(
					new InfoFieldValue<>(
						CommerceOrderInfoItemFields.
							formattedDiscountAmountInfoField,
						_commercePriceFormatter.format(
							commerceCurrency,
							commerceCurrency.round(
								commerceOrder.getTotalDiscountAmount()),
							themeDisplay.getLocale())));
				commerceOrderInfoFieldValues.add(
					new InfoFieldValue<>(
						CommerceOrderInfoItemFields.
							formattedDiscountWithTaxAmountInfoField,
						_commercePriceFormatter.format(
							commerceCurrency,
							commerceCurrency.round(
								commerceOrder.getTotalDiscountWithTaxAmount()),
							themeDisplay.getLocale())));
				commerceOrderInfoFieldValues.add(
					new InfoFieldValue<>(
						CommerceOrderInfoItemFields.
							formattedShippingAmountInfoField,
						_commercePriceFormatter.format(
							commerceCurrency,
							commerceCurrency.round(
								commerceOrder.getShippingAmount()),
							themeDisplay.getLocale())));
				commerceOrderInfoFieldValues.add(
					new InfoFieldValue<>(
						CommerceOrderInfoItemFields.
							formattedShippingWithTaxAmountInfoField,
						_commercePriceFormatter.format(
							commerceCurrency,
							commerceCurrency.round(
								commerceOrder.getShippingWithTaxAmount()),
							themeDisplay.getLocale())));
				commerceOrderInfoFieldValues.add(
					new InfoFieldValue<>(
						CommerceOrderInfoItemFields.
							formattedSubtotalAmountInfoField,
						_commercePriceFormatter.format(
							commerceCurrency,
							commerceCurrency.round(commerceOrder.getSubtotal()),
							themeDisplay.getLocale())));
				commerceOrderInfoFieldValues.add(
					new InfoFieldValue<>(
						CommerceOrderInfoItemFields.
							formattedSubtotalWithTaxAmountInfoField,
						_commercePriceFormatter.format(
							commerceCurrency,
							commerceCurrency.round(
								commerceOrder.getSubtotalWithTaxAmount()),
							themeDisplay.getLocale())));
				commerceOrderInfoFieldValues.add(
					new InfoFieldValue<>(
						CommerceOrderInfoItemFields.
							formattedTotalAmountInfoField,
						_commercePriceFormatter.format(
							commerceCurrency,
							commerceCurrency.round(commerceOrder.getTotal()),
							themeDisplay.getLocale())));
				commerceOrderInfoFieldValues.add(
					new InfoFieldValue<>(
						CommerceOrderInfoItemFields.
							formattedTotalWithTaxAmountInfoField,
						_commercePriceFormatter.format(
							commerceCurrency,
							commerceCurrency.round(
								commerceOrder.getTotalWithTaxAmount()),
							themeDisplay.getLocale())));
			}

			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.groupIdInfoField,
					commerceOrder.getGroupId()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.guestOrderInfoField,
					commerceOrder.isGuestOrder()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.inactiveInfoField,
					commerceOrder.isInactive()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.incompleteInfoField,
					commerceOrder.isIncomplete()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.declinedOrderStatusInfoField,
					_isOrderStatus(
						commerceOrder,
						CommerceOrderConstants.ORDER_STATUS_IN_PROGRESS)));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.lastPriceUpdateDateInfoField,
					commerceOrder.getLastPriceUpdateDate()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.manuallyAdjustedInfoField,
					commerceOrder.isManuallyAdjusted()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.modifiedDateInfoField,
					commerceOrder.getModifiedDate()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.onHoldOrderStatusInfoField,
					_isOrderStatus(
						commerceOrder,
						CommerceOrderConstants.ORDER_STATUS_ON_HOLD)));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.openOrderStatusInfoField,
					_isOrderStatus(
						commerceOrder,
						CommerceOrderConstants.ORDER_STATUS_OPEN)));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.orderDateInfoField,
					commerceOrder.getOrderDate()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.orderIdInfoField,
					commerceOrder.getCommerceOrderId()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.orderStatusInfoField,
					commerceOrder.getOrderStatus()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.orderTypeIdInfoField,
					commerceOrder.getCommerceOrderTypeId()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						partiallyRefundedOrderStatusInfoField,
					_isOrderStatus(
						commerceOrder,
						CommerceOrderConstants.
							ORDER_STATUS_PARTIALLY_REFUNDED)));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						partiallyShippedOrderStatusInfoField,
					_isOrderStatus(
						commerceOrder,
						CommerceOrderConstants.
							ORDER_STATUS_PARTIALLY_SHIPPED)));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						paymentCommerceTermEntryDescriptionInfoField,
					commerceOrder.getPaymentCommerceTermEntryDescription()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						paymentCommerceTermEntryIdInfoField,
					commerceOrder.getPaymentCommerceTermEntryId()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						paymentCommerceTermEntryNameInfoField,
					commerceOrder.getPaymentCommerceTermEntryName()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.paymenMethodKeyInfoField,
					commerceOrder.getCommercePaymentMethodKey()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.paymentStatusInfoField,
					commerceOrder.getPaymentStatus()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.pendingInfoField,
					commerceOrder.isPending()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.pendingOrderStatusInfoField,
					_isOrderStatus(
						commerceOrder,
						CommerceOrderConstants.ORDER_STATUS_PENDING)));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.processingOrderStatusInfoField,
					_isOrderStatus(
						commerceOrder,
						CommerceOrderConstants.ORDER_STATUS_PROCESSING)));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.purchaseOrderNumberInfoField,
					commerceOrder.getPurchaseOrderNumber()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.refundedOrderStatusInfoField,
					_isOrderStatus(
						commerceOrder,
						CommerceOrderConstants.ORDER_STATUS_REFUNDED)));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.requestedDeliveryDateInfoField,
					commerceOrder.getRequestedDeliveryDate()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.scheduledInfoField,
					commerceOrder.isScheduled()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.scopeGroupIdInfoField,
					commerceOrder.getScopeGroupId()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.shippedOrderStatusInfoField,
					_isOrderStatus(
						commerceOrder,
						CommerceOrderConstants.ORDER_STATUS_SHIPPED)));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.shippingAddressIdInfoField,
					commerceOrder.getShippingAddressId()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.shippingAmountInfoField,
					commerceOrder.getShippingAmount()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.shippingDiscountAmountInfoField,
					commerceOrder.getShippingDiscountAmount()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						shippingDiscountPercentageLevel1InfoField,
					commerceOrder.getShippingDiscountPercentageLevel1()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						shippingDiscountPercentageLevel1WithTaxAmountInfoField,
					commerceOrder.
						getShippingDiscountPercentageLevel1WithTaxAmount()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						shippingDiscountPercentageLevel2InfoField,
					commerceOrder.getShippingDiscountPercentageLevel2()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						shippingDiscountPercentageLevel2WithTaxAmountInfoField,
					commerceOrder.
						getShippingDiscountPercentageLevel2WithTaxAmount()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						shippingDiscountPercentageLevel3InfoField,
					commerceOrder.getShippingDiscountPercentageLevel3()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						shippingDiscountPercentageLevel3WithTaxAmountInfoField,
					commerceOrder.
						getShippingDiscountPercentageLevel3WithTaxAmount()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						shippingDiscountPercentageLevel4InfoField,
					commerceOrder.getShippingDiscountPercentageLevel4()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						shippingDiscountPercentageLevel4WithTaxAmountInfoField,
					commerceOrder.
						getShippingDiscountPercentageLevel4WithTaxAmount()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.shippingMethodIdInfoField,
					commerceOrder.getCommerceShippingMethodId()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.shippingOptionNameInfoField,
					commerceOrder.getShippingOptionName()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.shippingWithTaxAmountInfoField,
					commerceOrder.getShippingWithTaxAmount()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.stagedModelTypeInfoField,
					commerceOrder.getStagedModelType()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.statusInfoField,
					commerceOrder.getStatus()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.statusByUserIdInfoField,
					commerceOrder.getStatusByUserId()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.statusByUserNameInfoField,
					commerceOrder.getStatusByUserName()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.statusByUserUuidInfoField,
					commerceOrder.getStatusByUserUuid()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.statusDateInfoField,
					commerceOrder.getStatusDate()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						subscriptionOrderStatusInfoField,
					_isOrderStatus(
						commerceOrder,
						CommerceOrderConstants.ORDER_STATUS_SUBSCRIPTION)));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.subscriptionOrderInfoField,
					commerceOrder.isSubscriptionOrder()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.subtotalInfoField,
					commerceOrder.getSubtotal()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.subtotalDiscountAmountInfoField,
					commerceOrder.getSubtotalDiscountAmount()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						subtotalDiscountPercentageLevel1InfoField,
					commerceOrder.getSubtotalDiscountPercentageLevel1()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						subtotalDiscountPercentageLevel1WithTaxAmountInfoField,
					commerceOrder.
						getSubtotalDiscountPercentageLevel1WithTaxAmount()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						subtotalDiscountPercentageLevel2InfoField,
					commerceOrder.getSubtotalDiscountPercentageLevel2()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						subtotalDiscountPercentageLevel2WithTaxAmountInfoField,
					commerceOrder.
						getSubtotalDiscountPercentageLevel2WithTaxAmount()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						subtotalDiscountPercentageLevel3InfoField,
					commerceOrder.getSubtotalDiscountPercentageLevel3()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						subtotalDiscountPercentageLevel3WithTaxAmountInfoField,
					commerceOrder.
						getSubtotalDiscountPercentageLevel3WithTaxAmount()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						subtotalDiscountPercentageLevel4InfoField,
					commerceOrder.getSubtotalDiscountPercentageLevel4()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						subtotalDiscountPercentageLevel4WithTaxAmountInfoField,
					commerceOrder.
						getSubtotalDiscountPercentageLevel4WithTaxAmount()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						subtotalDiscountWithTaxAmountInfoField,
					commerceOrder.getSubtotalDiscountWithTaxAmount()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.subtotalWithTaxAmountInfoField,
					commerceOrder.getSubtotalWithTaxAmount()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.taxAmountInfoField,
					commerceOrder.getTaxAmount()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.totalInfoField,
					commerceOrder.getTotal()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.totalDiscountAmountInfoField,
					commerceOrder.getTotalDiscountAmount()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						totalDiscountPercentageLevel1InfoField,
					commerceOrder.getTotalDiscountPercentageLevel1()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						totalDiscountPercentageLevel1WithTaxAmountInfoField,
					commerceOrder.
						getTotalDiscountPercentageLevel1WithTaxAmount()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						totalDiscountPercentageLevel1InfoField,
					commerceOrder.getTotalDiscountPercentageLevel2()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						totalDiscountPercentageLevel2WithTaxAmountInfoField,
					commerceOrder.
						getTotalDiscountPercentageLevel2WithTaxAmount()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						totalDiscountPercentageLevel3InfoField,
					commerceOrder.getTotalDiscountPercentageLevel3()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						totalDiscountPercentageLevel3WithTaxAmountInfoField,
					commerceOrder.
						getTotalDiscountPercentageLevel3WithTaxAmount()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						totalDiscountPercentageLevel4InfoField,
					commerceOrder.getTotalDiscountPercentageLevel4()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						totalDiscountPercentageLevel4WithTaxAmountInfoField,
					commerceOrder.
						getTotalDiscountPercentageLevel4WithTaxAmount()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.
						totalDiscountWithTaxAmountInfoField,
					commerceOrder.getTotalDiscountWithTaxAmount()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.transactionIdInfoField,
					commerceOrder.getTransactionId()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.userIdInfoField,
					commerceOrder.getUserId()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.userNameInfoField,
					commerceOrder.getUserName()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.userUuidInfoField,
					commerceOrder.getUserUuid()));
			commerceOrderInfoFieldValues.add(
				new InfoFieldValue<>(
					CommerceOrderInfoItemFields.uuidInfoField,
					commerceOrder.getUuid()));
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}

		return commerceOrderInfoFieldValues;
	}

	private ThemeDisplay _getThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getThemeDisplay();
		}

		return null;
	}

	private boolean _isOrderStatus(
		CommerceOrder commerceOrder, int commerceOrderStatus) {

		if (commerceOrder.getOrderStatus() == commerceOrderStatus) {
			return true;
		}

		return false;
	}

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private ExpandoInfoItemFieldSetProvider _expandoInfoItemFieldSetProvider;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private TemplateInfoItemFieldSetProvider _templateInfoItemFieldSetProvider;

}