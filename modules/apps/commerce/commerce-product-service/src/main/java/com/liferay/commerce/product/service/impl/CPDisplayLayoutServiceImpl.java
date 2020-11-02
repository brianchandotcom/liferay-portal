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

package com.liferay.commerce.product.service.impl;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.base.CPDisplayLayoutServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDisplayLayoutServiceImpl extends CPDisplayLayoutServiceBaseImpl {

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CPDisplayLayout addCPDisplayLayout(
			Class<?> clazz, long classPK, String layoutUuid,
			ServiceContext serviceContext)
		throws PortalException {

		return cpDisplayLayoutService.addCPDisplayLayout(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), clazz,
			classPK, layoutUuid);
	}

	@Override
	public CPDisplayLayout addCPDisplayLayout(
			long userId, long groupId, Class<?> clazz, long classPK,
			String layoutUuid)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_LAYOUT);

		_checkCPDisplayLayout(clazz.getName(), classPK, ActionKeys.VIEW);

		return cpDisplayLayoutLocalService.addCPDisplayLayout(
			userId, groupId, clazz, classPK, layoutUuid);
	}

	@Override
	public void deleteCPDisplayLayout(Class<?> clazz, long classPK)
		throws PortalException {

		CPDisplayLayout cpDisplayLayout =
			cpDisplayLayoutLocalService.fetchCPDisplayLayout(clazz, classPK);

		if (cpDisplayLayout == null) {
			return;
		}

		GroupPermissionUtil.check(
			getPermissionChecker(), cpDisplayLayout.getGroupId(),
			ActionKeys.ADD_LAYOUT);

		_checkCPDisplayLayout(
			cpDisplayLayout.getClassName(), cpDisplayLayout.getClassPK(),
			ActionKeys.VIEW);

		cpDisplayLayoutLocalService.deleteCPDisplayLayout(cpDisplayLayout);
	}

	@Override
	public void deleteCPDisplayLayout(long cpDisplayLayoutId)
		throws PortalException {

		CPDisplayLayout cpDisplayLayout =
			cpDisplayLayoutLocalService.getCPDisplayLayout(cpDisplayLayoutId);

		GroupPermissionUtil.check(
			getPermissionChecker(), cpDisplayLayout.getGroupId(),
			ActionKeys.ADD_LAYOUT);

		_checkCPDisplayLayout(
			cpDisplayLayout.getClassName(), cpDisplayLayout.getClassPK(),
			ActionKeys.UPDATE);

		cpDisplayLayoutLocalService.deleteCPDisplayLayout(cpDisplayLayout);
	}

	@Override
	public CPDisplayLayout fetchCPDisplayLayout(long cpDisplayLayoutId)
		throws PortalException {

		CPDisplayLayout cpDisplayLayout =
			cpDisplayLayoutLocalService.fetchCPDisplayLayout(cpDisplayLayoutId);

		if (cpDisplayLayout != null) {
			LayoutPermissionUtil.check(
				getPermissionChecker(), _getLayout(cpDisplayLayout),
				ActionKeys.VIEW);

			_checkCPDisplayLayout(
				cpDisplayLayout.getClassName(), cpDisplayLayout.getClassPK(),
				ActionKeys.VIEW);
		}

		return cpDisplayLayout;
	}

	@Override
	public BaseModelSearchResult<CPDisplayLayout> searchCPDisplayLayout(
			long companyId, long groupId, String className, String keywords,
			int start, int end, Sort sort)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		return cpDisplayLayoutLocalService.searchCPDisplayLayout(
			companyId, groupId, className, keywords, start, end, sort);
	}

	@Override
	public CPDisplayLayout updateCPDisplayLayout(
			long cpDisplayLayoutId, String layoutUuid)
		throws PortalException {

		CPDisplayLayout cpDisplayLayout =
			cpDisplayLayoutLocalService.getCPDisplayLayout(cpDisplayLayoutId);

		LayoutPermissionUtil.check(
			getPermissionChecker(), _getLayout(cpDisplayLayout),
			ActionKeys.UPDATE);

		_checkCPDisplayLayout(
			cpDisplayLayout.getClassName(), cpDisplayLayout.getClassPK(),
			ActionKeys.VIEW);

		return cpDisplayLayoutLocalService.updateCPDisplayLayout(
			cpDisplayLayout.getCPDisplayLayoutId(), layoutUuid);
	}

	private void _checkCPDisplayLayout(
			String className, long classPK, String actionId)
		throws PortalException {

		if (className.equals(CPDefinition.class.getName())) {
			CPDefinition cpDefinition =
				cpDefinitionLocalService.fetchCPDefinition(classPK);

			if (cpDefinition == null) {
				throw new NoSuchCPDefinitionException();
			}

			CommerceCatalog commerceCatalog =
				commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
					cpDefinition.getGroupId());

			_commerceCatalogModelResourcePermission.check(
				getPermissionChecker(), commerceCatalog, actionId);
		}
		else if (className.equals(AssetCategory.class.getName())) {
			AssetCategoryPermission.check(
				getPermissionChecker(), classPK, actionId);
		}
	}

	private Layout _getLayout(CPDisplayLayout cpDisplayLayout) {
		Layout layout = layoutLocalService.fetchLayout(
			cpDisplayLayout.getLayoutUuid(), cpDisplayLayout.getGroupId(),
			false);

		if (layout != null) {
			return layout;
		}

		return layoutLocalService.fetchLayout(
			cpDisplayLayout.getLayoutUuid(), cpDisplayLayout.getGroupId(),
			true);
	}

	private static volatile ModelResourcePermission<CommerceCatalog>
		_commerceCatalogModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CPDisplayLayoutServiceImpl.class,
				"_commerceCatalogModelResourcePermission",
				CommerceCatalog.class);

}