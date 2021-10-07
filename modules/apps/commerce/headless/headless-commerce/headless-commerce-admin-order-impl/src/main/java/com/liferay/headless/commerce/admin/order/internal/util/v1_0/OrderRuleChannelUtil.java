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

		CommerceChannel commerceChannel;

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