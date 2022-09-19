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

package com.liferay.commerce.account.internal.util;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;

/**
 * @author Crescenzo Rega
 */
public class AccountEntryUtil {

	public static long getCommerceChannelId(
			CommerceContext commerceContext, Group group)
		throws PortalException {

		if (commerceContext != null) {
			return commerceContext.getCommerceChannelId();
		}

		if (group != null) {
			CommerceChannel commerceChannel =
				CommerceChannelLocalServiceUtil.fetchCommerceChannelByGroupId(
					group.getGroupId());

			if (commerceChannel != null) {
				return commerceChannel.getCommerceChannelId();
			}

			commerceChannel =
				CommerceChannelLocalServiceUtil.
					fetchCommerceChannelBySiteGroupId(group.getGroupId());

			if (commerceChannel != null) {
				return commerceChannel.getCommerceChannelId();
			}
		}

		return 0;
	}

}