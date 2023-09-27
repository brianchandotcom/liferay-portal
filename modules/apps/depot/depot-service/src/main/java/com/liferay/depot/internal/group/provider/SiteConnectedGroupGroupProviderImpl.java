/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.group.provider;

import com.liferay.depot.group.provider.SiteConnectedGroupGroupProvider;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = SiteConnectedGroupGroupProvider.class)
public class SiteConnectedGroupGroupProviderImpl
	implements SiteConnectedGroupGroupProvider {

	@Override
	public long[] getAncestorSiteAndDepotGroupIds(long groupId)
		throws PortalException {

		return ArrayUtil.append(
			_portal.getAncestorSiteGroupIds(groupId),
			ListUtil.toLongArray(
				_depotEntryLocalService.getGroupConnectedDepotEntries(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS),
				DepotEntry::getGroupId));
	}

	@Override
	public long[] getAncestorSiteAndDepotGroupIds(
			long groupId, boolean ddmStructuresAvailable)
		throws PortalException {

		return ArrayUtil.append(
			_portal.getAncestorSiteGroupIds(groupId),
			ListUtil.toLongArray(
				_depotEntryLocalService.getGroupConnectedDepotEntries(
					groupId, ddmStructuresAvailable, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS),
				DepotEntry::getGroupId));
	}

	@Override
	public long[] getCurrentAndAncestorSiteAndDepotGroupIds(long groupId)
		throws PortalException {

		return ArrayUtil.append(
			_portal.getCurrentAndAncestorSiteGroupIds(groupId),
			ListUtil.toLongArray(
				_depotEntryLocalService.getGroupConnectedDepotEntries(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS),
				DepotEntry::getGroupId));
	}

	@Override
	public long[] getCurrentAndAncestorSiteAndDepotGroupIds(
			long groupId, boolean ddmStructuresAvailable)
		throws PortalException {

		return ArrayUtil.append(
			_portal.getCurrentAndAncestorSiteGroupIds(groupId),
			ListUtil.toLongArray(
				_depotEntryLocalService.getGroupConnectedDepotEntries(
					groupId, ddmStructuresAvailable, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS),
				DepotEntry::getGroupId));
	}

	@Override
	public long[] getCurrentAndAncestorSiteAndDepotGroupIds(long[] groupIds)
		throws PortalException {

		List<DepotEntry> depotEntries = new ArrayList<>();

		for (long groupId : groupIds) {
			depotEntries.addAll(
				_depotEntryLocalService.getGroupConnectedDepotEntries(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS));
		}

		return ArrayUtil.append(
			_portal.getCurrentAndAncestorSiteGroupIds(groupIds),
			ListUtil.toLongArray(depotEntries, DepotEntry::getGroupId));
	}

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private Portal _portal;

}