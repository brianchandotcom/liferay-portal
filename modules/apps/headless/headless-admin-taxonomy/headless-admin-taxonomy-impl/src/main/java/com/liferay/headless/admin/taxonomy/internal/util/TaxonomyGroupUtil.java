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
import com.liferay.portal.kernel.util.Validator;

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
			if (assetLibrary == null) {
				continue;
			}

			Group group = _fetchGroup(assetLibrary, companyId);

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

	private static Group _fetchGroup(AssetLibrary assetLibrary, long companyId)
		throws PortalException {

		if (Validator.isNotNull(assetLibrary.getExternalReferenceCode())) {
			Group group =
				GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
					assetLibrary.getExternalReferenceCode(), companyId);

			if (group != null) {
				return group;
			}
		}

		if (Validator.isNotNull(assetLibrary.getScopeKey())) {
			Group group = GroupLocalServiceUtil.fetchGroup(
				companyId, assetLibrary.getScopeKey());

			if (group != null) {
				return group;
			}
		}

		if ((assetLibrary.getId() == null) ||
			(assetLibrary.getId() == GroupConstants.ANY_PARENT_GROUP_ID)) {

			return null;
		}

		Group group = GroupLocalServiceUtil.fetchGroup(assetLibrary.getId());

		if (group != null) {
			return group;
		}

		DepotEntry depotEntry = DepotEntryLocalServiceUtil.fetchDepotEntry(
			assetLibrary.getId());

		if (depotEntry != null) {
			return depotEntry.getGroup();
		}

		return null;
	}

	private static final long[] _GROUP_IDS_ALL = {-1L};

}