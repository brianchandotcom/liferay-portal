/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.internal.util;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.headless.admin.taxonomy.dto.v1_0.AssetLibrary;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class TaxonomyGroupUtil {

	public static long[] getAssetLibraryGroupIds(
			AssetLibrary[] assetLibraries, long companyId)
		throws PortalException {

		if (ArrayUtil.isEmpty(assetLibraries)) {
			return _GROUP_IDS_ALL;
		}

		List<Long> groupIds = new ArrayList<>();

		for (AssetLibrary assetLibrary : assetLibraries) {
			if ((assetLibrary == null) ||
				((assetLibrary.getId() == null) &&
				 (assetLibrary.getScopeKey() == null))) {

				continue;
			}

			Group group = null;

			if (assetLibrary.getScopeKey() != null) {
				group = GroupLocalServiceUtil.fetchGroup(
					companyId, assetLibrary.getScopeKey());

				if (group != null) {
					groupIds.add(group.getGroupId());

					continue;
				}
			}

			if (assetLibrary.getId() == GroupConstants.ANY_PARENT_GROUP_ID) {
				continue;
			}

			group = GroupLocalServiceUtil.fetchGroup(assetLibrary.getId());

			if (group == null) {
				DepotEntry depotEntry =
					DepotEntryLocalServiceUtil.getDepotEntry(
						assetLibrary.getId());

				group = depotEntry.getGroup();
			}

			if (group != null) {
				groupIds.add(group.getGroupId());
			}
		}

		if (groupIds.isEmpty()) {
			return _GROUP_IDS_ALL;
		}

		return ArrayUtil.toLongArray(groupIds);
	}

	public static long getCMSGroupId(long companyId) throws PortalException {
		Group group = GroupLocalServiceUtil.getGroup(
			companyId, GroupConstants.CMS);

		return group.getGroupId();
	}

	private static final long[] _GROUP_IDS_ALL = {-1L};

}