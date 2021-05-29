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

package com.liferay.questions.web.internal.asset.model;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.CompanyLocalService;

import javax.portlet.PortletURL;
import javax.portlet.WindowState;

/**
 * @author Javier Gamarra
 */
public class MBCategoryAssetRendererFactory
	extends BaseAssetRendererFactory<MBCategory> {

	public static final String TYPE = "category";

	public MBCategoryAssetRendererFactory(
		CompanyLocalService companyLocalService, String historyRouterBasePath,
		MBCategoryLocalService mbCategoryLocalService,
		ModelResourcePermission<MBCategory> mbCategoryModelResourcePermission) {

		_companyLocalService = companyLocalService;
		_historyRouterBasePath = historyRouterBasePath;
		_mbCategoryLocalService = mbCategoryLocalService;
		_mbCategoryModelResourcePermission = mbCategoryModelResourcePermission;

		setCategorizable(false);
		setPortletId(MBPortletKeys.MESSAGE_BOARDS);
		setSelectable(false);
	}

	@Override
	public AssetRenderer<MBCategory> getAssetRenderer(long classPK, int type)
		throws PortalException {

		MBCategory mbCategory = _mbCategoryLocalService.getMBCategory(classPK);

		Company company = _companyLocalService.getCompany(
			mbCategory.getCompanyId());

		MBCategoryAssetRenderer mbCategoryAssetRenderer =
			new MBCategoryAssetRenderer(
				company, _historyRouterBasePath, mbCategory,
				_mbCategoryModelResourcePermission);

		mbCategoryAssetRenderer.setAssetRendererType(type);

		return mbCategoryAssetRenderer;
	}

	@Override
	public String getClassName() {
		return MBCategory.class.getName();
	}

	@Override
	public String getIconCssClass() {
		return "comments";
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public PortletURL getURLView(
		LiferayPortletResponse liferayPortletResponse,
		WindowState windowState) {

		return null;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		return _mbCategoryModelResourcePermission.contains(
			permissionChecker, _mbCategoryLocalService.getMBCategory(classPK),
			actionId);
	}

	private final CompanyLocalService _companyLocalService;
	private final String _historyRouterBasePath;
	private final MBCategoryLocalService _mbCategoryLocalService;
	private final ModelResourcePermission<MBCategory>
		_mbCategoryModelResourcePermission;

}