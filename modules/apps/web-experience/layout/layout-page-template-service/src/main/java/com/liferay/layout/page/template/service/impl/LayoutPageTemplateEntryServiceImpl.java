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

import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateActionKeys;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateEntryServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

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
			long[] fragmentEntryIds, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			LayoutPageTemplateActionKeys.ADD_LAYOUT_PAGE_TEMPLATE_ENTRY);

		return layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			getUserId(), groupId, layoutPageTemplateCollectionId, name,
			fragmentEntryIds, serviceContext);
	}

	@Override
	public List<LayoutPageTemplateEntry> deleteLayoutPageTemplateEntries(
			long[] layoutPageTemplateEntryIds)
		throws PortalException {

		List<LayoutPageTemplateEntry> undeletableLayoutPageTemplateEntries =
			new ArrayList<>();

		for (long layoutPageTemplateEntryId : layoutPageTemplateEntryIds) {
			try {
				_layoutPageTemplateEntryModelResourcePermission.check(
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

		_layoutPageTemplateEntryModelResourcePermission.check(
			getPermissionChecker(), layoutPageTemplateEntryId,
			ActionKeys.DELETE);

		return layoutPageTemplateEntryLocalService.
			deleteLayoutPageTemplateEntry(layoutPageTemplateEntryId);
	}

	@Override
	public LayoutPageTemplateEntry fetchDefaultLayoutPageTemplateEntry(
		long groupId, long classNameId) {

		return layoutPageTemplateEntryPersistence.fetchByG_C_D_First(
			groupId, classNameId, true, null);
	}

	@Override
	public LayoutPageTemplateEntry fetchLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId)
		throws PortalException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				layoutPageTemplateEntryId);

		if (layoutPageTemplateEntry != null) {
			_layoutPageTemplateEntryModelResourcePermission.check(
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
			long layoutPageTemplateEntryId, long[] fragmentEntryIds,
			String editableValues, ServiceContext serviceContext)
		throws PortalException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			fetchLayoutPageTemplateEntry(layoutPageTemplateEntryId);

		return layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(
				layoutPageTemplateEntryId, layoutPageTemplateEntry.getName(),
				fragmentEntryIds, editableValues, serviceContext);
	}

	@Override
	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId, String name)
		throws PortalException {

		_layoutPageTemplateEntryModelResourcePermission.check(
			getPermissionChecker(), layoutPageTemplateEntryId,
			ActionKeys.UPDATE);

		return layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(layoutPageTemplateEntryId, name);
	}

	@Override
	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId, String name,
			long[] fragmentEntryIds, ServiceContext serviceContext)
		throws PortalException {

		_layoutPageTemplateEntryModelResourcePermission.check(
			getPermissionChecker(), layoutPageTemplateEntryId,
			ActionKeys.UPDATE);

		return layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(
				layoutPageTemplateEntryId, name, fragmentEntryIds,
				StringPool.BLANK, serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateEntryServiceImpl.class);

	private static volatile ModelResourcePermission<LayoutPageTemplateEntry>
		_layoutPageTemplateEntryModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				LayoutPageTemplateEntryServiceImpl.class,
				"_layoutPageTemplateEntryModelResourcePermission",
				LayoutPageTemplateEntry.class);
	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				LayoutPageTemplateEntryServiceImpl.class,
				"_portletResourcePermission",
				LayoutPageTemplateConstants.RESOURCE_NAME);

	@ServiceReference(type = FragmentEntryService.class)
	private FragmentEntryService _fragmentEntryService;

}