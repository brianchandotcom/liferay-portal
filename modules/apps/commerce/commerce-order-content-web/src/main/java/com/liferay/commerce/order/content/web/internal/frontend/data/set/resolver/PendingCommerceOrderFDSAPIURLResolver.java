/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.frontend.data.set.resolver;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.frontend.data.set.resolver.FDSAPIURLResolver;
import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gianmarco Brunialti Masera
 */
@Component(
	property = "fds.rest.application.key=/headless-commerce-delivery-cart/v1.0/Cart",
	service = FDSAPIURLResolver.class
)
public class PendingCommerceOrderFDSAPIURLResolver
	implements FDSAPIURLResolver {

	@Override
	public String getSchema() {
		return "Cart";
	}

	@Override
	public String resolve(String baseURL, HttpServletRequest httpServletRequest)
		throws PortalException {

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		AccountEntry accountEntry = commerceContext.getAccountEntry();
		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(
				commerceContext.getCommerceChannelId());
		CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

		return baseURL.replaceAll(
			"\\{accountExternalReferenceCode\\}",
			accountEntry.getExternalReferenceCode()
		).replaceAll(
			"\\{accountId\\}", String.valueOf(accountEntry.getAccountEntryId())
		).replaceAll(
			"\\{cartId\\}", String.valueOf(commerceOrder.getCommerceOrderId())
		).replaceAll(
			"\\{channelExternalReferenceCode\\}",
			String.valueOf(commerceChannel.getExternalReferenceCode())
		).replaceAll(
			"\\{channelId\\}",
			String.valueOf(commerceContext.getCommerceChannelId())
		).replaceAll(
			"\\{externalReferenceCode\\}",
			commerceOrder.getExternalReferenceCode()
		);
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

}