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

package com.liferay.dynamic.data.mapping.internal.security.permission.resource;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.security.permission.DDMPermissionSupport;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMTemplate",
	service = ModelResourcePermission.class
)
public class DDMTemplateModelResourcePermission
	implements ModelResourcePermission<DDMTemplate> {

	@Override
	public void check(
			PermissionChecker permissionChecker, DDMTemplate template,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, template, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker,
				_ddmPermissionSupport.getTemplateModelResourceName(
					template.getResourceClassName()),
				template.getTemplateId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		DDMTemplate template = _ddmTemplateLocalService.getDDMTemplate(
			primaryKey);

		check(permissionChecker, template, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, DDMTemplate template,
			String actionId)
		throws PortalException {

		String templateModelResourceName =
			_ddmPermissionSupport.getTemplateModelResourceName(
				template.getResourceClassName());

		String portletId = PortletProviderUtil.getPortletId(
			template.getResourceClassName(), PortletProvider.Action.EDIT);

		if (Validator.isNotNull(portletId)) {
			Boolean hasPermission = _stagingPermission.hasPermission(
				permissionChecker, template.getGroupId(),
				templateModelResourceName, template.getTemplateId(), portletId,
				actionId);

			if (hasPermission != null) {
				return hasPermission.booleanValue();
			}
		}

		if (permissionChecker.hasOwnerPermission(
				template.getCompanyId(), templateModelResourceName,
				template.getTemplateId(), template.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			template.getGroupId(), templateModelResourceName,
			template.getTemplateId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		DDMTemplate template = _ddmTemplateLocalService.getDDMTemplate(
			primaryKey);

		return contains(permissionChecker, template, actionId);
	}

	@Override
	public String getModelName() {
		return DDMTemplate.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	private DDMPermissionSupport _ddmPermissionSupport;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private StagingPermission _stagingPermission;

}