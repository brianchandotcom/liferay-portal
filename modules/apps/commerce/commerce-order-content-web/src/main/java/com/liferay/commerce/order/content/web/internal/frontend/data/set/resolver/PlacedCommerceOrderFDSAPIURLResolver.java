/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.frontend.data.set.resolver;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.frontend.data.set.resolver.FDSAPIURLResolver;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Balazs Breier
 */
@Component(
	property = "fds.rest.application.key=/headless-commerce-delivery-order/v1.0/PlacedOrder",
	service = FDSAPIURLResolver.class
)
public class PlacedCommerceOrderFDSAPIURLResolver implements FDSAPIURLResolver {

	@Override
	public String getSchema() {
		return "PlacedOrder";
	}

	@Override
	public String resolve(String baseURL, HttpServletRequest httpServletRequest)
		throws PortalException {

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		_accountEntry = commerceContext.getAccountEntry();

		if (_accountEntry == null) {
			return StringPool.BLANK;
		}

		_commerceChannel = _commerceChannelLocalService.getCommerceChannel(
			commerceContext.getCommerceChannelId());

		if (baseURL.startsWith("/v1.0/channels/by-externalReferenceCode")) {
			return _replacePlaceholders(
				baseURL, _commerceChannel.getExternalReferenceCode());
		}

		return _replacePlaceholders(baseURL, StringPool.BLANK);
	}

	private String _replacePlaceholders(
		String baseURL, String externalReferenceCode) {

		String[] placeholders = {
			"{accountExternalReferenceCode}", "{accountId}",
			"{channelExternalReferenceCode}", "{channelId}",
			"{externalReferenceCode}"
		};

		String[] replacements = {
			_accountEntry.getExternalReferenceCode(),
			String.valueOf(_accountEntry.getAccountEntryId()),
			_commerceChannel.getExternalReferenceCode(),
			String.valueOf(_commerceChannel.getCommerceChannelId()),
			externalReferenceCode
		};

		return StringUtil.replace(baseURL, placeholders, replacements);
	}

	private AccountEntry _accountEntry;
	private CommerceChannel _commerceChannel;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

}