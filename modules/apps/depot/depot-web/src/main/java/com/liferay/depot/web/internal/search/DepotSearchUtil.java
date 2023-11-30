/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.search;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;

/**
 * @author Gustavo Lima
 */
public class DepotSearchUtil {

	public static void addAssetLibraryGroupIdsToSearchContext(
		SearchContext searchContext,
		DepotEntryGroupRelLocalService depotEntryGroupRelLocalService,
		DepotEntryLocalService depotEntryLocalService) {

		long[] groupIds = searchContext.getGroupIds();

		if (ArrayUtil.isEmpty(groupIds)) {
			return;
		}

		for (long groupId : groupIds) {
			searchContext.setGroupIds(
				ArrayUtil.append(
					searchContext.getGroupIds(),
					TransformUtil.transformToLongArray(
						depotEntryGroupRelLocalService.
							getSearchableDepotEntryGroupRels(
								groupId, 0,
								depotEntryGroupRelLocalService.
									getSearchableDepotEntryGroupRelsCount(
										groupId)),
						depotEntryGroupRel -> {
							DepotEntry depotEntry =
								depotEntryLocalService.fetchDepotEntry(
									depotEntryGroupRel.getDepotEntryId());

							return depotEntry.getGroupId();
						})));
		}
	}

}