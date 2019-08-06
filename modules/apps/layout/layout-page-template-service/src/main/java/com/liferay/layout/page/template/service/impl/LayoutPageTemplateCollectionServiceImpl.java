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
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateCollectionServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = {
		"json.web.service.context.name=layout",
		"json.web.service.context.path=LayoutPageTemplateCollection"
	},
	service = AopService.class
)
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
	public List<LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId) {

		return layoutPageTemplateCollectionPersistence.filterFindByGroupId(
			groupId);
	}

	@Override
	public List<LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId, int start, int end) {

		return layoutPageTemplateCollectionPersistence.filterFindByGroupId(
			groupId, start, end);
	}

	@Override
	public List<LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId, int start, int end,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator) {

		return layoutPageTemplateCollectionPersistence.filterFindByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId, String name, int start, int end,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator) {

		return layoutPageTemplateCollectionPersistence.filterFindByG_LikeN(
			groupId, _customSQL.keywords(name, false, WildcardMode.SURROUND)[0],
			start, end, orderByComparator);
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
			groupId,
			_customSQL.keywords(name, false, WildcardMode.SURROUND)[0]);
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

	@Reference
	private CustomSQL _customSQL;

	@Reference(
		target = "(model.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateCollection)"
	)
	private ModelResourcePermission<LayoutPageTemplateCollection>
		_layoutPageTemplateCollectionModelResourcePermission;

	@Reference(
		target = "(resource.name=" + LayoutPageTemplateConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}