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

package com.liferay.commerce.internal.order.status;

import com.liferay.commerce.configuration.CommerceOrderFieldsConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.status.CommerceOrderStatus;
import com.liferay.commerce.order.status.CommerceOrderStatusRegistry;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;

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

	public static final int PRIORITY = 40;

	@Override
	public CommerceOrder doTransition(CommerceOrder commerceOrder, long userId)
		throws PortalException {

		commerceOrder.setOrderStatus(KEY);

		commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
			commerceOrder);

		return _commerceOrderLocalService.updateCommerceOrder(commerceOrder);
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

		CommerceOrderFieldsConfiguration commerceOrderFieldsConfiguration =
			_getCommerceOrderFieldsConfiguration(commerceOrder);

		if (commerceOrderFieldsConfiguration.requestQuoteEnabled()) {
			return true;
		}

		return false;
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

			return true;
		}

		return false;
	}

	private CommerceOrderFieldsConfiguration
			_getCommerceOrderFieldsConfiguration(CommerceOrder commerceOrder)
		throws PortalException {

		if (_commerceOrderFieldsConfiguration != null) {
			return _commerceOrderFieldsConfiguration;
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
				commerceOrder.getGroupId());

		_commerceOrderFieldsConfiguration =
			_configurationProvider.getConfiguration(
				CommerceOrderFieldsConfiguration.class,
				new GroupServiceSettingsLocator(
					commerceChannel.getGroupId(),
					CommerceConstants.SERVICE_NAME_COMMERCE_ORDER_FIELDS));

		return _commerceOrderFieldsConfiguration;
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	private CommerceOrderFieldsConfiguration _commerceOrderFieldsConfiguration;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private CommerceOrderStatusRegistry _commerceOrderStatusRegistry;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Language _language;

}