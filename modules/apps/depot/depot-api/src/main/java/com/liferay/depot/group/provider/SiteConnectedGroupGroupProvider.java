/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.group.provider;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Adolfo Pérez
 */
public interface SiteConnectedGroupGroupProvider {

	public long[] getAncestorSiteAndDepotGroupIds(long groupId)
		throws PortalException;

	public long[] getAncestorSiteAndDepotGroupIds(
			long groupId, boolean ddmStructuresAvailable)
		throws PortalException;

	public long[] getCurrentAndAncestorSiteAndDepotGroupIds(long groupId)
		throws PortalException;

	public long[] getCurrentAndAncestorSiteAndDepotGroupIds(
			long groupId, boolean ddmStructuresAvailable)
		throws PortalException;

	public long[] getCurrentAndAncestorSiteAndDepotGroupIds(long[] groupIds)
		throws PortalException;

}