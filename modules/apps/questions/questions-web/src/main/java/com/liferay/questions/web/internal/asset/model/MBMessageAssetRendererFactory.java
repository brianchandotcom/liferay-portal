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
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.CompanyLocalService;

import javax.portlet.PortletURL;
import javax.portlet.WindowState;

/**
 * @author Javier Gamarra
 */
public class MBMessageAssetRendererFactory
	extends BaseAssetRendererFactory<MBMessage> {

	public static final String TYPE = "message";

	public MBMessageAssetRendererFactory(
		CompanyLocalService companyLocalService, String historyRouterPath,
		MBMessageLocalService mbMessageLocalService,
		ModelResourcePermission<MBMessage> mbMessageModelResourcePermission) {

		_companyLocalService = companyLocalService;
		_historyRouterPath = historyRouterPath;
		_mbMessageLocalService = mbMessageLocalService;
		_mbMessageModelResourcePermission = mbMessageModelResourcePermission;

		setLinkable(true);
		setPortletId(MBPortletKeys.MESSAGE_BOARDS);
		setSearchable(true);
	}

	@Override
	public AssetRenderer<MBMessage> getAssetRenderer(long classPK, int type)
		throws PortalException {

		MBMessage mbMessage = _mbMessageLocalService.getMessage(classPK);

		MBMessageAssetRenderer mbMessageAssetRenderer =
			new MBMessageAssetRenderer(
				_companyLocalService.getCompany(mbMessage.getCompanyId()),
				_historyRouterPath, mbMessage,
				_mbMessageModelResourcePermission);

		mbMessageAssetRenderer.setAssetRendererType(type);

		return mbMessageAssetRenderer;
	}

	@Override
	public String getClassName() {
		return MBMessage.class.getName();
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

		return _mbMessageModelResourcePermission.contains(
			permissionChecker, classPK, actionId);
	}

	private final CompanyLocalService _companyLocalService;
	private final String _historyRouterPath;
	private final MBMessageLocalService _mbMessageLocalService;
	private final ModelResourcePermission<MBMessage>
		_mbMessageModelResourcePermission;

}