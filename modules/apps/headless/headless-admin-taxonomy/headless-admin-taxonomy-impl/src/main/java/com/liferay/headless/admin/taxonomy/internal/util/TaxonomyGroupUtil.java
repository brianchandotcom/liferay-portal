/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.internal.util;

import com.liferay.headless.admin.taxonomy.dto.v1_0.AssetLibrary;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;

/**
 * @author Adolfo Pérez
 */
public class TaxonomyGroupUtil {

	public static long[] getAssetLibraryGroupIds(
		AssetLibrary[] assetLibraries, long companyId) {

		return TransformUtil.transformToLongArray(
			assetLibraries,
			assetLibrary -> {
				Group group = GroupLocalServiceUtil.fetchGroup(
					companyId, assetLibrary.getScopeKey());

				if (group != null) {
					return group.getGroupId();
				}

				return null;
			});
	}

	public static long getCMSGroupId(long companyId) throws PortalException {
		Group group = GroupLocalServiceUtil.getGroup(
			companyId, GroupConstants.CMS);

		return group.getGroupId();
	}

}