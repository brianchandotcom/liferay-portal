/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.discovery;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Danny Situ
 */
@ProviderType
public interface CPConfigurationListDiscovery {

	public CPConfigurationList getCPConfigurationList(
			long companyId, long groupId, long accountEntryId,
			long commerceChannelId, long commerceOrderTypeId)
		throws PortalException;

	public String getCPConfigurationListDiscoveryKey();

}