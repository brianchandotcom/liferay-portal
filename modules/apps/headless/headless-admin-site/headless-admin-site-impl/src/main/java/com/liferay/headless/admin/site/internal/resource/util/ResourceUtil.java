/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.util;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;

/**
 * @author Lourdes Fernández Besada
 */
public class ResourceUtil {

	public static long getGroupId(
			boolean allowLiveGroup, long companyId,
			String siteExternalReferenceCode)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, companyId);

		if (group.isCompany() || group.isDepot() ||
			(!allowLiveGroup && group.hasLocalOrRemoteStagingGroup())) {

			throw new UnsupportedOperationException();
		}

		return group.getGroupId();
	}

}