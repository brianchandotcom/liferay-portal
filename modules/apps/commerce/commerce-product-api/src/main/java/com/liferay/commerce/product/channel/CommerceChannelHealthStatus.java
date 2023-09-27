/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.channel;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

/**
 * @author Marco Leo
 */
public interface CommerceChannelHealthStatus {

	public void fixIssue(long companyId, long commerceChannelId)
		throws PortalException;

	public String getDescription(Locale locale);

	public String getKey();

	public String getName(Locale locale);

	public boolean isFixed(long companyId, long commerceChannelId)
		throws PortalException;

}