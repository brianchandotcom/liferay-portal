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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.LayoutBranchPermissionUtil;
import com.liferay.portal.kernel.service.persistence.LayoutRevisionPersistence;
import com.liferay.portal.kernel.service.persistence.LayoutSetBranchPersistence;
import com.liferay.portal.service.base.LayoutBranchServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class LayoutBranchServiceImpl extends LayoutBranchServiceBaseImpl {

	@Override
	public LayoutBranch addLayoutBranch(
			long layoutRevisionId, String name, String description,
			boolean master, ServiceContext serviceContext)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		GroupPermissionUtil.check(
			permissionChecker, serviceContext.getScopeGroupId(),
			ActionKeys.ADD_LAYOUT_BRANCH);

		LayoutRevision layoutRevision =
			_layoutRevisionPersistence.findByPrimaryKey(layoutRevisionId);

		LayoutSetBranch layoutSetBranch =
			_layoutSetBranchPersistence.findByPrimaryKey(
				layoutRevision.getLayoutSetBranchId());

		GroupPermissionUtil.check(
			permissionChecker, layoutSetBranch.getGroupId(),
			ActionKeys.ADD_LAYOUT_BRANCH);

		return layoutBranchLocalService.addLayoutBranch(
			layoutRevisionId, name, description, false, serviceContext);
	}

	@Override
	public void deleteLayoutBranch(long layoutBranchId) throws PortalException {
		LayoutBranchPermissionUtil.check(
			getPermissionChecker(), layoutBranchId, ActionKeys.DELETE);

		layoutBranchLocalService.deleteLayoutBranch(layoutBranchId);
	}

	@Override
	public LayoutBranch updateLayoutBranch(
			long layoutBranchId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutBranchPermissionUtil.check(
			getPermissionChecker(), layoutBranchId, ActionKeys.UPDATE);

		return layoutBranchLocalService.updateLayoutBranch(
			layoutBranchId, name, description, serviceContext);
	}

	@BeanReference(type = LayoutRevisionPersistence.class)
	private LayoutRevisionPersistence _layoutRevisionPersistence;

	@BeanReference(type = LayoutSetBranchPersistence.class)
	private LayoutSetBranchPersistence _layoutSetBranchPersistence;

}