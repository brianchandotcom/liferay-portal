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

import com.liferay.layout.page.template.constants.LayoutPageTemplateActionKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateCollectionServiceBaseImpl;
import com.liferay.layout.page.template.service.permission.LayoutPageTemplateCollectionPermission;
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
public class LayoutPageTemplateCollectionServiceImpl
	extends LayoutPageTemplateCollectionServiceBaseImpl {

	@Override
	public LayoutPageTemplateCollection addLayoutPageTemplateCollection(
			long groupId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutPageTemplatePermission.check(
			getPermissionChecker(), groupId,
			LayoutPageTemplateActionKeys.ADD_LAYOUT_PAGE_TEMPLATE_COLLECTION);

		return layoutPageTemplateCollectionLocalService.
			addLayoutPageTemplateCollection(
				getUserId(), groupId, name, description, serviceContext);
	}

	@Override
	public LayoutPageTemplateCollection deleteLayoutPageTemplateCollection(
			long layoutPageTemplateCollectionId)
		throws PortalException {

		LayoutPageTemplateCollectionPermission.check(
			getPermissionChecker(), layoutPageTemplateCollectionId,
			ActionKeys.DELETE);

		return layoutPageTemplateCollectionLocalService.
			deleteLayoutPageTemplateCollection(layoutPageTemplateCollectionId);
	}

	@Override
	public List<LayoutPageTemplateCollection>
			deleteLayoutPageTemplateCollections(
				long[] layoutPageTemplateCollectionIds)
		throws PortalException {

		List<LayoutPageTemplateCollection>
			undeletableLayoutPageTemplateCollections = new ArrayList<>();

		for (long layoutPageTemplateCollectionId :
				layoutPageTemplateCollectionIds) {

			try {
				LayoutPageTemplateCollectionPermission.check(
					getPermissionChecker(), layoutPageTemplateCollectionId,
					ActionKeys.DELETE);

				layoutPageTemplateCollectionLocalService.
					deleteLayoutPageTemplateCollection(
						layoutPageTemplateCollectionId);
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}

				LayoutPageTemplateCollection layoutPageTemplateCollection =
					layoutPageTemplateCollectionPersistence.fetchByPrimaryKey(
						layoutPageTemplateCollectionId);

				undeletableLayoutPageTemplateCollections.add(
					layoutPageTemplateCollection);
			}
		}

		return undeletableLayoutPageTemplateCollections;
	}

	@Override
	public LayoutPageTemplateCollection fetchLayoutPageTemplateCollection(
			long layoutPageTemplateCollectionId)
		throws PortalException {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			layoutPageTemplateCollectionLocalService.
				fetchLayoutPageTemplateCollection(
					layoutPageTemplateCollectionId);

		if (layoutPageTemplateCollection != null) {
			LayoutPageTemplateCollectionPermission.check(
				getPermissionChecker(), layoutPageTemplateCollection,
				ActionKeys.VIEW);
		}

		return layoutPageTemplateCollection;
	}

	@Override
	public List<LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
			long groupId, int start, int end)
		throws PortalException {

		return layoutPageTemplateCollectionPersistence.filterFindByGroupId(
			groupId, start, end);
	}

	@Override
	public List<LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
			long groupId, int start, int end,
			OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws PortalException {

		return layoutPageTemplateCollectionPersistence.filterFindByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
			long groupId, String name, int start, int end,
			OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws PortalException {

		return layoutPageTemplateCollectionPersistence.filterFindByG_LikeN(
			groupId, name, start, end, orderByComparator);
	}

	@Override
	public int getLayoutPageTemplateCollectionsCount(long groupId) {
		return layoutPageTemplateCollectionPersistence.filterCountByGroupId(
			groupId);
	}

	@Override
	public int getLayoutPageTemplateCollectionsCount(
		long groupId, String name) {

		return layoutPageTemplateCollectionPersistence.filterCountByG_LikeN(
			groupId, name);
	}

	@Override
	public LayoutPageTemplateCollection updateLayoutPageTemplateCollection(
			long layoutPageTemplateCollectionId, String name,
			String description)
		throws PortalException {

		LayoutPageTemplateCollectionPermission.check(
			getPermissionChecker(), layoutPageTemplateCollectionId,
			ActionKeys.UPDATE);

		return layoutPageTemplateCollectionLocalService.
			updateLayoutPageTemplateCollection(
				layoutPageTemplateCollectionId, name, description);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateCollectionServiceImpl.class);

}