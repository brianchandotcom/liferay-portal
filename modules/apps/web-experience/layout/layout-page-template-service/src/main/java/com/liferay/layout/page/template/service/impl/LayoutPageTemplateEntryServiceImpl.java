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

package com.liferay.layout.page.template.service.impl;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.layout.page.template.constants.LayoutPageTemplateActionKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateEntryServiceBaseImpl;
import com.liferay.layout.page.template.service.permission.LayoutPageTemplateEntryPermission;
import com.liferay.layout.page.template.service.permission.LayoutPageTemplatePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jürgen Kappler
 */
public class LayoutPageTemplateEntryServiceImpl
	extends LayoutPageTemplateEntryServiceBaseImpl {

	@Override
	public LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long groupId, long layoutPageTemplateCollectionId, String name,
			List<FragmentEntry> fragmentEntries, ServiceContext serviceContext)
		throws PortalException {

		LayoutPageTemplatePermission.check(
			getPermissionChecker(), groupId,
			LayoutPageTemplateActionKeys.ADD_LAYOUT_PAGE_TEMPLATE_ENTRY);

		return layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			getUserId(), groupId, layoutPageTemplateCollectionId, name,
			fragmentEntries, serviceContext);
	}

	@Override
	public List<LayoutPageTemplateEntry> deleteLayoutPageTemplateEntries(
			long[] layoutPageTemplateEntryIds)
		throws PortalException {

		List<LayoutPageTemplateEntry> undeletableLayoutPageTemplateEntries =
			new ArrayList<>();

		for (long layoutPageTemplateEntryId : layoutPageTemplateEntryIds) {
			try {
				LayoutPageTemplateEntryPermission.check(
					getPermissionChecker(), layoutPageTemplateEntryId,
					ActionKeys.DELETE);

				layoutPageTemplateEntryLocalService.
					deleteLayoutPageTemplateEntry(layoutPageTemplateEntryId);
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}

				LayoutPageTemplateEntry layoutPageTemplateEntry =
					layoutPageTemplateEntryPersistence.fetchByPrimaryKey(
						layoutPageTemplateEntryId);

				undeletableLayoutPageTemplateEntries.add(
					layoutPageTemplateEntry);
			}
		}

		return undeletableLayoutPageTemplateEntries;
	}

	@Override
	public LayoutPageTemplateEntry deleteLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId)
		throws PortalException {

		LayoutPageTemplateEntryPermission.check(
			getPermissionChecker(), layoutPageTemplateEntryId,
			ActionKeys.DELETE);

		return layoutPageTemplateEntryLocalService.
			deleteLayoutPageTemplateEntry(layoutPageTemplateEntryId);
	}

	@Override
	public LayoutPageTemplateEntry fetchLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId)
		throws PortalException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				layoutPageTemplateEntryId);

		if (layoutPageTemplateEntry != null) {
			LayoutPageTemplateEntryPermission.check(
				getPermissionChecker(), layoutPageTemplateEntry,
				ActionKeys.VIEW);
		}

		return layoutPageTemplateEntry;
	}

	@Override
	public int getLayoutPageTemplateCollectionsCount(
		long groupId, long layoutPageTemplateCollectionId) {

		return layoutPageTemplateEntryPersistence.filterCountByG_L(
			groupId, layoutPageTemplateCollectionId);
	}

	@Override
	public int getLayoutPageTemplateCollectionsCount(
		long groupId, long layoutPageTemplateCollectionId, String name) {

		return layoutPageTemplateEntryPersistence.filterCountByG_L_LikeN(
			groupId, layoutPageTemplateCollectionId, name);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
			long groupId, long layoutPageTemplateCollectionId, int start,
			int end)
		throws PortalException {

		return layoutPageTemplateEntryPersistence.filterFindByG_L(
			groupId, layoutPageTemplateCollectionId, start, end);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
			long groupId, long layoutPageTemplateCollectionId, int start,
			int end,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws PortalException {

		return layoutPageTemplateEntryPersistence.filterFindByG_L(
			groupId, layoutPageTemplateCollectionId, start, end,
			orderByComparator);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return layoutPageTemplateEntryPersistence.filterFindByG_L_LikeN(
			groupId, layoutPageTemplateCollectionId, name, start, end,
			orderByComparator);
	}

	@Override
	public int getLayoutPageTemplateEntriesCount(
		long groupId, long layoutPageTemplateFolder) {

		return layoutPageTemplateEntryPersistence.filterCountByG_L(
			groupId, layoutPageTemplateFolder);
	}

	@Override
	public int getLayoutPageTemplateEntriesCount(
		long groupId, long layoutPageTemplateFolder, String name) {

		return layoutPageTemplateEntryPersistence.filterCountByG_L_LikeN(
			groupId, layoutPageTemplateFolder, name);
	}

	@Override
	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId, String name,
			List<FragmentEntry> fragmentEntries, ServiceContext serviceContext)
		throws PortalException {

		LayoutPageTemplateEntryPermission.check(
			getPermissionChecker(), layoutPageTemplateEntryId,
			ActionKeys.UPDATE);

		return layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(
				getUserId(), layoutPageTemplateEntryId, name, fragmentEntries,
				serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateEntryServiceImpl.class);

}