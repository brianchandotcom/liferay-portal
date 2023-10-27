/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.checkout.web.internal.display.context;

import com.liferay.commerce.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.payment.engine.CommercePaymentEngine;
import com.liferay.commerce.payment.integration.CommercePaymentIntegration;
import com.liferay.commerce.payment.integration.CommercePaymentIntegrationRegistry;
import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 */
public class PaymentMethodCheckoutStepDisplayContext {

	public PaymentMethodCheckoutStepDisplayContext(
		CommercePaymentEngine commercePaymentEngine,
		CommercePaymentIntegrationRegistry commercePaymentIntegrationRegistry,
		CommercePaymentMethodGroupRelLocalService
			commercePaymentMethodGroupRelLocalService,
		HttpServletRequest httpServletRequest) {

		_commercePaymentEngine = commercePaymentEngine;
		_commercePaymentIntegrationRegistry =
			commercePaymentIntegrationRegistry;
		_commercePaymentMethodGroupRelLocalService =
			commercePaymentMethodGroupRelLocalService;

		_commerceOrder = (CommerceOrder)httpServletRequest.getAttribute(
			CommerceCheckoutWebKeys.COMMERCE_ORDER);
	}

	public CommerceOrder getCommerceOrder() {
		return _commerceOrder;
	}

	public List<CommercePaymentMethodGroupRel>
			getCommercePaymentMethodGroupRels()
		throws PortalException {

		List<CommercePaymentMethodGroupRel> commercePaymentMethodGroupRels =
			new ArrayList<>();

		CommerceOrder commerceOrder = getCommerceOrder();

		long groupId = commerceOrder.getGroupId();

		List<CommercePaymentMethod> commercePaymentMethods =
			_commercePaymentEngine.getEnabledCommercePaymentMethodsForOrder(
				_commerceOrder.getGroupId(),
				_commerceOrder.getCommerceOrderId());

		for (CommercePaymentMethod commercePaymentMethod :
				commercePaymentMethods) {

			commercePaymentMethodGroupRels.add(
				_commercePaymentMethodGroupRelLocalService.
					getCommercePaymentMethodGroupRel(
						groupId, commercePaymentMethod.getKey()));
		}

		List<CommercePaymentIntegration> commercePaymentIntegrations =
			Collections.emptyList();

		if (!commerceOrder.isSubscriptionOrder()) {
			Map<String, CommercePaymentIntegration>
				commercePaymentIntegrationMaps =
					_commercePaymentIntegrationRegistry.
						getCommercePaymentIntegrations();

			commercePaymentIntegrations = ListUtil.fromCollection(
				commercePaymentIntegrationMaps.values());
		}

		for (CommercePaymentIntegration commercePaymentIntegration :
				commercePaymentIntegrations) {

			commercePaymentMethodGroupRels.add(
				_commercePaymentMethodGroupRelLocalService.
					getCommercePaymentMethodGroupRel(
						groupId, commercePaymentIntegration.getKey()));
		}

		return commercePaymentMethodGroupRels;
	}

	private final CommerceOrder _commerceOrder;
	private final CommercePaymentEngine _commercePaymentEngine;
	private final CommercePaymentIntegrationRegistry
		_commercePaymentIntegrationRegistry;
	private final CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;

}