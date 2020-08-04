/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.journal.web.internal.util;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Adolfo Pérez
 */
public class SiteConnectedGroupUtil {

	public static long[] getCurrentAndAncestorSiteAndDepotGroupIds(long groupId)
		throws PortalException {

		return ArrayUtil.append(
			PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId),
			ListUtil.toLongArray(
				DepotEntryServiceUtil.getGroupConnectedDepotEntries(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS),
				DepotEntry::getGroupId));
	}

	public static long[] getCurrentAndAncestorSiteAndDepotGroupIds(
			long groupId, boolean ddmStructuresAvailable)
		throws PortalException {

		return ArrayUtil.append(
			PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId),
			ListUtil.toLongArray(
				DepotEntryServiceUtil.getGroupConnectedDepotEntries(
					groupId, ddmStructuresAvailable, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS),
				DepotEntry::getGroupId));
	}

}