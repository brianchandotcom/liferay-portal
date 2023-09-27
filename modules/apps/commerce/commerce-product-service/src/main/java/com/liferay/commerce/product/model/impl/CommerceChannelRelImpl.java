/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceChannelRelImpl extends CommerceChannelRelBaseImpl {

	@Override
	public CommerceChannel getCommerceChannel() throws PortalException {
		return CommerceChannelLocalServiceUtil.getCommerceChannel(
			getCommerceChannelId());
	}

}