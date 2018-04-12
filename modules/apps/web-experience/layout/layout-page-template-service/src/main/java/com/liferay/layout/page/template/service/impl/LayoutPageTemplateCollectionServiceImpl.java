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
import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateCollectionServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

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

		_portletResourcePermission.check(
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

		_layoutPageTemplateCollectionModelResourcePermission.check(
			getPermissionChecker(), layoutPageTemplateCollectionId,
			ActionKeys.DELETE);

		return layoutPageTemplateCollectionLocalService.
			deleteLayoutPageTemplateCollection(layoutPageTemplateCollectionId);
	}

	@Override
	public void deleteLayoutPageTemplateCollections(
			long[] layoutPageTemplateCollectionIds)
		throws PortalException {

		for (long layoutPageTemplateCollectionId :
				layoutPageTemplateCollectionIds) {

			_layoutPageTemplateCollectionModelResourcePermission.check(
				getPermissionChecker(), layoutPageTemplateCollectionId,
				ActionKeys.DELETE);

			layoutPageTemplateCollectionLocalService.
				deleteLayoutPageTemplateCollection(
					layoutPageTemplateCollectionId);
		}
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
			_layoutPageTemplateCollectionModelResourcePermission.check(
				getPermissionChecker(), layoutPageTemplateCollection,
				ActionKeys.VIEW);
		}

		return layoutPageTemplateCollection;
	}

	@Override
	public List<LayoutPageTemplateCollection>
			getBasicLayoutPageTemplateCollections(
				long groupId, int start, int end,
				OrderByComparator<LayoutPageTemplateCollection>
					orderByComparator)
		throws PortalException {

		return layoutPageTemplateCollectionPersistence.filterFindByG_T(
			groupId, LayoutPageTemplateCollectionTypeConstants.TYPE_BASIC,
			start, end, orderByComparator);
	}

	@Override
	public List<LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
			long groupId)
		throws PortalException {

		return layoutPageTemplateCollectionPersistence.filterFindByGroupId(
			groupId);
	}

	@Override
	public List<LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
			long groupId, int type)
		throws PortalException {

		return layoutPageTemplateCollectionPersistence.filterFindByG_T(
			groupId, type);
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

		int count = layoutPageTemplateCollectionPersistence.countByG_T(
			groupId,
			LayoutPageTemplateCollectionTypeConstants.TYPE_ASSET_DISPLAY_PAGE);

		if (count <= 0) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					getUserId(), groupId, "Asset Display Pages",
					StringPool.BLANK,
					LayoutPageTemplateCollectionTypeConstants.
						TYPE_ASSET_DISPLAY_PAGE,
					serviceContext);
		}

		return layoutPageTemplateCollectionPersistence.filterFindByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
			long groupId, String name, int start, int end,
			OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws PortalException {

		return layoutPageTemplateCollectionPersistence.filterFindByG_LikeN(
			groupId, _customSQL.keywords(name, WildcardMode.SURROUND)[0], start,
			end, orderByComparator);
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
			groupId, _customSQL.keywords(name, WildcardMode.SURROUND)[0]);
	}

	@Override
	public LayoutPageTemplateCollection updateLayoutPageTemplateCollection(
			long layoutPageTemplateCollectionId, String name,
			String description)
		throws PortalException {

		_layoutPageTemplateCollectionModelResourcePermission.check(
			getPermissionChecker(), layoutPageTemplateCollectionId,
			ActionKeys.UPDATE);

		return layoutPageTemplateCollectionLocalService.
			updateLayoutPageTemplateCollection(
				layoutPageTemplateCollectionId, name, description);
	}

	private static volatile
		ModelResourcePermission<LayoutPageTemplateCollection>
			_layoutPageTemplateCollectionModelResourcePermission =
				ModelResourcePermissionFactory.getInstance(
					LayoutPageTemplateCollectionServiceImpl.class,
					"_layoutPageTemplateCollectionModelResourcePermission",
					LayoutPageTemplateCollection.class);
	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				LayoutPageTemplateCollectionServiceImpl.class,
				"_portletResourcePermission",
				LayoutPageTemplateConstants.RESOURCE_NAME);

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}