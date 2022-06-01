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

import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateStructureServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionCheckerUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.util.PropsUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = {
		"json.web.service.context.name=layout",
		"json.web.service.context.path=LayoutPageTemplateStructure"
	},
	service = AopService.class
)
public class LayoutPageTemplateStructureServiceImpl
	extends LayoutPageTemplateStructureServiceBaseImpl {

	@Override
	public LayoutPageTemplateStructure updateLayoutPageTemplateStructureData(
			long groupId, long plid, long segmentsExperienceId, String data)
		throws PortalException {

		if (GetterUtil.getBoolean(
				BaseModelPermissionCheckerUtil.containsBaseModelPermission(
					getPermissionChecker(), groupId, Layout.class.getName(),
					plid, ActionKeys.UPDATE)) ||
			(GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-132571")) &&
			 _layoutPermission.containsLayoutBasicUpdatePermission(
				 getPermissionChecker(), plid))) {

			return layoutPageTemplateStructureLocalService.
				updateLayoutPageTemplateStructureData(
					groupId, plid, segmentsExperienceId, data);
		}

		throw new PrincipalException.MustHavePermission(
			getUserId(), Layout.class.getName(), plid, ActionKeys.UPDATE);
	}

	@Reference
	private LayoutPermission _layoutPermission;

	@Reference
	private Portal _portal;

}