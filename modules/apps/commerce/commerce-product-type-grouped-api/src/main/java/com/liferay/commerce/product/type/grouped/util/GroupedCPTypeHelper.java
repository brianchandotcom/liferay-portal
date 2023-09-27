/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.grouped.util;

import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public interface GroupedCPTypeHelper {

	public List<CPDefinitionGroupedEntry> getCPDefinitionGroupedEntry(
			long commerceAccountId, long commerceChannelGroupId,
			long cpDefinitionId)
		throws PortalException;

}