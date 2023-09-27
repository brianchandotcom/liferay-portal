/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.grouped.test.util;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class GroupedCPTypeTestUtil {

	public static CPDefinitionGroupedEntry addCPDefinitionGroupedEntry(
			long groupId, long cpDefinitionId, long entryCPDefinitionId)
		throws Exception {

		CPDefinition entryCPDefinition =
			CPDefinitionLocalServiceUtil.getCPDefinition(entryCPDefinitionId);

		return CPDefinitionGroupedEntryLocalServiceUtil.
			addCPDefinitionGroupedEntry(
				cpDefinitionId, entryCPDefinition.getCProductId(),
				RandomTestUtil.randomDouble(), RandomTestUtil.randomInt(),
				ServiceContextTestUtil.getServiceContext(groupId));
	}

}