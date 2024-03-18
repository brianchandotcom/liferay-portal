/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.display.context;

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.URLCodec;

import javax.portlet.RenderRequest;

/**
 * @author Gianmarco Brunialti Masera
 */
public class CommerceReturnContentDisplayContext {

	public CommerceReturnContentDisplayContext(RenderRequest renderRequest) {
		_commerceContext = (CommerceContext)renderRequest.getAttribute(
			CommerceWebKeys.COMMERCE_CONTEXT);
	}

	public String getAPIURL() {
		long commerceChannelId = getCommerceChannelId();

		if (commerceChannelId == 0) {
			return StringPool.BLANK;
		}

		String encodedFilter = URLCodec.encodeURL(
			StringBundler.concat(
				"'channelId' eq '", commerceChannelId, StringPool.APOSTROPHE),
			true);

		return "/o/commerce-returns?filter=" + encodedFilter;
	}

	public long getCommerceChannelId() {
		try {
			return _commerceContext.getCommerceChannelId();
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return 0;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceReturnContentDisplayContext.class);

	private final CommerceContext _commerceContext;

}