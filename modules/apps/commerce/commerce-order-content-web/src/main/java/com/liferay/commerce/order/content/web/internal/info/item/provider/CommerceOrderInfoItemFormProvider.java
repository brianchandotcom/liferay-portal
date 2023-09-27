/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.info.item.provider;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.content.web.internal.info.CommerceOrderInfoItemFields;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.localized.bundle.ModelResourceLocalizedValue;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(
	property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFormProvider.class
)
public class CommerceOrderInfoItemFormProvider
	implements InfoItemFormProvider<CommerceOrder> {

	@Override
	public InfoForm getInfoForm() {
		return _getInfoForm();
	}

	@Override
	public InfoForm getInfoForm(CommerceOrder commerceOrder) {
		return _getInfoForm();
	}

	@Override
	public InfoForm getInfoForm(String formVariationKey, long groupId) {
		return _getInfoForm();
	}

	private InfoFieldSet _getBasicInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.accountIdInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.accountNameInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.billingAddressIdInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.couponCodeInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.
				deliveryCommerceTermEntryDescriptionInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.deliveryCommerceTermEntryIdInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.deliveryCommerceTermEntryNameInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.formattedDiscountAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.formattedDiscountWithTaxAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.formattedShippingAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.formattedShippingWithTaxAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.formattedSubtotalAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.formattedSubtotalWithTaxAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.formattedTotalAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.formattedTotalWithTaxAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.orderDateInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.orderIdInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.orderStatusInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.orderTypeIdInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.
				paymentCommerceTermEntryDescriptionInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.paymentCommerceTermEntryIdInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.paymentCommerceTermEntryNameInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.purchaseOrderNumberInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.requestedDeliveryDateInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.shippingAddressIdInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "basic-information")
		).name(
			"basic-information"
		).build();
	}

	private InfoFieldSet _getDetailedInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.advanceStatusInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.b2bInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.currencyIdInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.companyIdInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.createDateInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.defaultLanguageIdInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.emptyInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.lastPriceUpdateDateInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.manuallyAdjustedInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.modifiedDateInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.paymenMethodKeyInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.paymentStatusInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.scopeGroupIdInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.shippingAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.shippingDiscountAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.
				shippingDiscountPercentageLevel1InfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.
				shippingDiscountPercentageLevel1WithTaxAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.
				shippingDiscountPercentageLevel2InfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.
				shippingDiscountPercentageLevel2WithTaxAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.
				shippingDiscountPercentageLevel3InfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.
				shippingDiscountPercentageLevel3WithTaxAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.
				shippingDiscountPercentageLevel4InfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.
				shippingDiscountPercentageLevel4WithTaxAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.shippingMethodIdInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.shippingOptionNameInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.shippingWithTaxAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.stagedModelTypeInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.subscriptionOrderInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.subtotalInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.subtotalDiscountAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.
				subtotalDiscountPercentageLevel1InfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.
				subtotalDiscountPercentageLevel1WithTaxAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.
				subtotalDiscountPercentageLevel2InfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.
				subtotalDiscountPercentageLevel2WithTaxAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.
				subtotalDiscountPercentageLevel3InfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.
				subtotalDiscountPercentageLevel3WithTaxAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.
				subtotalDiscountPercentageLevel4InfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.
				subtotalDiscountPercentageLevel4WithTaxAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.subtotalDiscountWithTaxAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.subtotalWithTaxAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.taxAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.totalInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.totalDiscountAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.totalDiscountPercentageLevel1InfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.
				totalDiscountPercentageLevel1WithTaxAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.totalDiscountPercentageLevel2InfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.
				totalDiscountPercentageLevel2WithTaxAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.totalDiscountPercentageLevel3InfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.
				totalDiscountPercentageLevel3WithTaxAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.totalDiscountPercentageLevel4InfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.
				totalDiscountPercentageLevel4WithTaxAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.totalDiscountWithTaxAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.totalWithTaxAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.transactionIdInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.userIdInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.userNameInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.userUuidInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.uuidInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "detailed-information")
		).name(
			"detailed-information"
		).build();
	}

	private InfoForm _getInfoForm() {
		return InfoForm.builder(
		).infoFieldSetEntry(
			_getBasicInformationInfoFieldSet()
		).infoFieldSetEntry(
			_getDetailedInformationInfoFieldSet()
		).infoFieldSetEntry(
			_getOrderStatusInformationInfoFieldSet()
		).infoFieldSetEntry(
			_getWorkflowStatusInformationInfoFieldSet()
		).infoFieldSetEntry(
			_expandoInfoItemFieldSetProvider.getInfoFieldSet(
				CommerceOrder.class.getName())
		).infoFieldSetEntry(
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				CommerceOrder.class.getName())
		).infoFieldSetEntry(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
				CommerceOrder.class.getName())
		).labelInfoLocalizedValue(
			new ModelResourceLocalizedValue(CommerceOrder.class.getName())
		).name(
			CommerceOrder.class.getName()
		).build();
	}

	private InfoFieldSet _getOrderStatusInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.awaitingPickupOrderStatusInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.cancelledOrderStatusInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.completedOrderStatusInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.declinedOrderStatusInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.disputedOrderStatusInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.inProgressOrderStatusInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.onHoldOrderStatusInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.openOrderStatusInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.partiallyRefundedOrderStatusInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.partiallyShippedOrderStatusInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.pendingOrderStatusInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.processingOrderStatusInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.refundedOrderStatusInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.shippedOrderStatusInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.subscriptionOrderStatusInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "order-status-information")
		).name(
			"order-status-information"
		).build();
	}

	private InfoFieldSet _getWorkflowStatusInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.approvedInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.deniedInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.draftInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.expiredInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.inactiveInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.incompleteInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.pendingInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.scheduledInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.statusInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.statusByUserIdInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.statusByUserNameInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.statusByUserUuidInfoField
		).infoFieldSetEntry(
			CommerceOrderInfoItemFields.statusDateInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				getClass(), "workflow-status-information")
		).name(
			"workflow-status-information"
		).build();
	}

	@Reference
	private ExpandoInfoItemFieldSetProvider _expandoInfoItemFieldSetProvider;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private TemplateInfoItemFieldSetProvider _templateInfoItemFieldSetProvider;

}