/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.internal.util;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.headless.admin.taxonomy.dto.v1_0.AssetLibrary;
import com.liferay.petra.function.transform.TransformUtil;
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
		AssetLibrary[] assetLibraries) {

		return TransformUtil.transformToLongArray(
			assetLibraries, TaxonomyGroupUtil::_getGroupId);
	}

	public static long getCMSGroupId(long companyId) throws PortalException {
		Group group = GroupLocalServiceUtil.getGroup(
			companyId, GroupConstants.CMS);

		return group.getGroupId();
	}

	private static long _getGroupId(AssetLibrary assetLibrary)
		throws Exception {

		long classPK = assetLibrary.getId();

		if (classPK == GroupConstants.ANY_PARENT_GROUP_ID) {
			return classPK;
		}

		Group group = GroupLocalServiceUtil.fetchGroup(classPK);

		if (group != null) {
			return group.getGroupId();
		}

		DepotEntry depotEntry = DepotEntryLocalServiceUtil.getDepotEntry(
			classPK);

		return depotEntry.getGroupId();
	}

}