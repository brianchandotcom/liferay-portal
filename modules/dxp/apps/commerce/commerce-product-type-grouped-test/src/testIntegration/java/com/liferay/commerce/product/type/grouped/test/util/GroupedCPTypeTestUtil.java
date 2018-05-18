/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.type.grouped.test.util;

import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

/**
 * @author Andrea Di Giorgi
 */
public class GroupedCPTypeTestUtil {

	public static CPDefinitionGroupedEntry addCPDefinitionGroupedEntry(
			long groupId, long cpDefinitionId, long entryCPDefinitionId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return CPDefinitionGroupedEntryLocalServiceUtil.
			addCPDefinitionGroupedEntry(
				cpDefinitionId, entryCPDefinitionId,
				RandomTestUtil.randomDouble(), RandomTestUtil.randomInt(),
				serviceContext);
	}

}