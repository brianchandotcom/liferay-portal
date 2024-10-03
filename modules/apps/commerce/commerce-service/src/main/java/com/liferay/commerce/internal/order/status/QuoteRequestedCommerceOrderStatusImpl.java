/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.order.status;

import com.liferay.commerce.configuration.CommerceOrderFieldsConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItemModel;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.order.status.CommerceOrderStatus;
import com.liferay.commerce.order.status.CommerceOrderStatusRegistry;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Brian I. Kim
 */
@Component(
	property = {
		"commerce.order.status.key=" + QuoteRequestedCommerceOrderStatusImpl.KEY,
		"commerce.order.status.priority:Integer=" + QuoteRequestedCommerceOrderStatusImpl.PRIORITY
	},
	service = CommerceOrderStatus.class
)
public class QuoteRequestedCommerceOrderStatusImpl
	implements CommerceOrderStatus {

	public static final int KEY =
		CommerceOrderConstants.ORDER_STATUS_QUOTE_REQUESTED;

	public static final int PRIORITY = 20;

	@Override
	public CommerceOrder doTransition(
			CommerceOrder commerceOrder, long userId, boolean secure)
		throws PortalException {

		commerceOrder.setOrderStatus(KEY);

		if (secure) {
			commerceOrder = _commerceOrderService.updateCommerceOrder(
				commerceOrder);
		}
		else {
			commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
				commerceOrder);
		}

		return commerceOrder;
	}

	@Override
	public int getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(
			locale, CommerceOrderConstants.getOrderStatusLabel(KEY));
	}

	@Override
	public int getPriority() {
		return PRIORITY;
	}

	@Override
	public boolean isEnabled(CommerceOrder commerceOrder)
		throws PortalException {

		if (commerceOrder.isOpen() && _isRequestQuoteEnabled(commerceOrder)) {
			return true;
		}

		return commerceOrder.isQuote();
	}

	@Override
	public boolean isTransitionCriteriaMet(CommerceOrder commerceOrder)
		throws PortalException {

		CommerceOrderStatus currentCommerceOrderStatus =
			_commerceOrderStatusRegistry.getCommerceOrderStatus(
				commerceOrder.getOrderStatus());

		if ((currentCommerceOrderStatus.getKey() ==
				CommerceOrderConstants.ORDER_STATUS_IN_PROGRESS) ||
			(currentCommerceOrderStatus.getKey() ==
				CommerceOrderConstants.ORDER_STATUS_OPEN)) {

			if (!_commerceOrderValidatorRegistry.isValid(
					LocaleUtil.getSiteDefault(), commerceOrder)) {

				return false;
			}

			return _isRequestQuoteEnabled(commerceOrder);
		}

		return false;
	}

	private boolean _isRequestQuoteEnabled(CommerceOrder commerceOrder)
		throws PortalException {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
				commerceOrder.getGroupId());

		CommerceOrderFieldsConfiguration commerceOrderFieldsConfiguration =
			_configurationProvider.getConfiguration(
				CommerceOrderFieldsConfiguration.class,
				new GroupServiceSettingsLocator(
					commerceChannel.getGroupId(),
					CommerceConstants.SERVICE_NAME_COMMERCE_ORDER_FIELDS));

		if (commerceOrderFieldsConfiguration.requestQuoteEnabled()) {
			return true;
		}

		return ListUtil.exists(
			commerceOrder.getCommerceOrderItems(),
			CommerceOrderItemModel::isPriceOnApplication);
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile CommerceOrderLocalService _commerceOrderLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrderStatusRegistry _commerceOrderStatusRegistry;

	@Reference
	private CommerceOrderValidatorRegistry _commerceOrderValidatorRegistry;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Language _language;

}