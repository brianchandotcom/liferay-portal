/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.cart.internal.resource.v1_0;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.payment.engine.CommercePaymentEngine;
import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.PaymentMethod;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.PaymentMethodResource;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/payment-method.properties",
	scope = ServiceScope.PROTOTYPE, service = PaymentMethodResource.class
)
public class PaymentMethodResourceImpl extends BasePaymentMethodResourceImpl {

	@Override
	public Page<PaymentMethod> getCartPaymentMethodsPage(Long cartId)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			cartId);

		return Page.of(
			transform(
				_commercePaymentEngine.getEnabledCommercePaymentMethodsForOrder(
					commerceOrder.getGroupId(), cartId),
				this::_toPaymentMethod));
	}

	private PaymentMethod _toPaymentMethod(
		CommercePaymentMethod commercePaymentMethod) {

		Locale locale = contextAcceptLanguage.getPreferredLocale();

		return new PaymentMethod() {
			{
				description = commercePaymentMethod.getDescription(locale);
				key = commercePaymentMethod.getKey();
				name = commercePaymentMethod.getName(locale);
			}
		};
	}

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommercePaymentEngine _commercePaymentEngine;

}