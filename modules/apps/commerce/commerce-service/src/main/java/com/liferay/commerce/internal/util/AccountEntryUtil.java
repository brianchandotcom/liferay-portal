/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.util;

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
				CommerceChannelLocalServiceUtil.
					fetchCommerceChannelByGroupClassPK(group.getGroupId());

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