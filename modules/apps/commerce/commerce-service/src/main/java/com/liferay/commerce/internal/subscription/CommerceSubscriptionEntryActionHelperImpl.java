/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.subscription;

import com.liferay.commerce.configuration.CommerceSubscriptionConfiguration;
import com.liferay.commerce.constants.CommerceSubscriptionEntryConstants;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.payment.engine.CommerceSubscriptionEngine;
import com.liferay.commerce.service.CommerceSubscriptionEntryLocalService;
import com.liferay.commerce.subscription.CommerceSubscriptionEntryActionHelper;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CommerceSubscriptionEntryActionHelper.class)
public class CommerceSubscriptionEntryActionHelperImpl
	implements CommerceSubscriptionEntryActionHelper {

	@Override
	public void activateCommerceSubscriptionEntry(
			long commerceSubscriptionEntryId)
		throws Exception {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			_commerceSubscriptionEntryLocalService.getCommerceSubscriptionEntry(
				commerceSubscriptionEntryId);

		if (CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_SUSPENDED !=
				commerceSubscriptionEntry.getSubscriptionStatus()) {

			return;
		}

		_commerceSubscriptionEngine.activateRecurringPayment(
			commerceSubscriptionEntryId);
	}

	@Override
	public void cancelCommerceSubscriptionEntry(
			long commerceSubscriptionEntryId)
		throws Exception {

		CommerceSubscriptionConfiguration commerceSubscriptionConfiguration =
			_configurationProvider.getSystemConfiguration(
				CommerceSubscriptionConfiguration.class);

		if (commerceSubscriptionConfiguration.
				subscriptionCancellationAllowed()) {

			_commerceSubscriptionEngine.cancelRecurringPayment(
				commerceSubscriptionEntryId);
		}
	}

	@Override
	public void suspendCommerceSubscriptionEntry(
			long commerceSubscriptionEntryId)
		throws Exception {

		CommerceSubscriptionConfiguration commerceSubscriptionConfiguration =
			_configurationProvider.getSystemConfiguration(
				CommerceSubscriptionConfiguration.class);

		if (commerceSubscriptionConfiguration.subscriptionSuspensionAllowed()) {
			_commerceSubscriptionEngine.suspendRecurringPayment(
				commerceSubscriptionEntryId);
		}
	}

	@Reference
	private CommerceSubscriptionEngine _commerceSubscriptionEngine;

	@Reference
	private CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

}