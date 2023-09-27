/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.order.internal.util.v1_0;

import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryRelService;
import com.liferay.commerce.product.exception.NoSuchChannelException;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleChannel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alessio Antonio Rendina
 */
public class OrderRuleChannelUtil {

	public static COREntryRel addCOREntryCommerceChannelRel(
			CommerceChannelService commerceChannelService,
			COREntryRelService corEntryRelService, COREntry corEntry,
			OrderRuleChannel orderRuleChannel)
		throws PortalException {

		CommerceChannel commerceChannel = null;

		if (Validator.isNull(
				orderRuleChannel.getChannelExternalReferenceCode())) {

			commerceChannel = commerceChannelService.getCommerceChannel(
				orderRuleChannel.getChannelId());
		}
		else {
			commerceChannel =
				commerceChannelService.fetchByExternalReferenceCode(
					orderRuleChannel.getChannelExternalReferenceCode(),
					corEntry.getCompanyId());

			if (commerceChannel == null) {
				throw new NoSuchChannelException(
					"Unable to find channel with external reference code " +
						orderRuleChannel.getChannelExternalReferenceCode());
			}
		}

		return corEntryRelService.addCOREntryRel(
			CommerceChannel.class.getName(),
			commerceChannel.getCommerceChannelId(), corEntry.getCOREntryId());
	}

}