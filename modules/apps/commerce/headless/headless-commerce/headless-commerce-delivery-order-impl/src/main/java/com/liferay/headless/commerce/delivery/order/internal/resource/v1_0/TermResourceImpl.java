/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.order.internal.resource.v1_0;

import com.liferay.commerce.exception.CommerceOrderStatusException;
import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.Term;
import com.liferay.headless.commerce.delivery.order.resource.v1_0.TermResource;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.GetterUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/term.properties",
	scope = ServiceScope.PROTOTYPE, service = TermResource.class
)
public class TermResourceImpl extends BaseTermResourceImpl {

	@Override
	public Term getPlacedOrderByExternalReferenceCodeDeliveryTerm(
			String externalReferenceCode)
		throws Exception {

		CommerceOrder commerceOrder =
			_commerceOrderService.fetchCommerceOrderByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrder == null) {
			throw new NoSuchOrderException(
				"Unable to find order with external reference code " +
					externalReferenceCode);
		}

		return getPlacedOrderDeliveryTerm(commerceOrder.getCommerceOrderId());
	}

	@Override
	public Term getPlacedOrderByExternalReferenceCodePaymentTerm(
			String externalReferenceCode)
		throws Exception {

		CommerceOrder commerceOrder =
			_commerceOrderService.fetchCommerceOrderByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrder == null) {
			throw new NoSuchOrderException(
				"Unable to find order with external reference code " +
					externalReferenceCode);
		}

		return getPlacedOrderPaymentTerm(commerceOrder.getCommerceOrderId());
	}

	@Override
	public Term getPlacedOrderDeliveryTerm(Long placedOrderId)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			placedOrderId);

		if (commerceOrder.isOpen()) {
			throw new CommerceOrderStatusException(
				"Unable to get delivery term of an open order");
		}

		return _toTerm(
			_commerceTermEntryLocalService.fetchCommerceTermEntry(
				GetterUtil.getLong(
					commerceOrder.getDeliveryCommerceTermEntryId())));
	}

	@Override
	public Term getPlacedOrderPaymentTerm(Long placedOrderId) throws Exception {
		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			placedOrderId);

		if (commerceOrder.isOpen()) {
			throw new CommerceOrderStatusException(
				"Unable to get payment term of an open order");
		}

		return _toTerm(
			_commerceTermEntryLocalService.fetchCommerceTermEntry(
				GetterUtil.getLong(
					commerceOrder.getPaymentCommerceTermEntryId())));
	}

	private Term _toTerm(CommerceTermEntry commerceTermEntry) {
		return new Term() {
			{
				setDescription(
					() -> commerceTermEntry.getDescription(
						_language.getLanguageId(
							contextAcceptLanguage.getPreferredLocale())));
				setExternalReferenceCode(
					commerceTermEntry::getExternalReferenceCode);
				setId(commerceTermEntry::getCommerceTermEntryId);
				setName(commerceTermEntry::getName);
			}
		};
	}

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceTermEntryLocalService _commerceTermEntryLocalService;

	@Reference
	private Language _language;

}