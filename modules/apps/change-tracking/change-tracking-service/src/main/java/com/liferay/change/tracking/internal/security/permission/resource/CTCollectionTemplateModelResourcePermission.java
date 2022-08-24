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

package com.liferay.change.tracking.internal.security.permission.resource;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTCollectionTemplate;
import com.liferay.change.tracking.service.CTCollectionTemplateLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.change.tracking.model.CTCollectionTemplate",
	service = ModelResourcePermission.class
)
public class CTCollectionTemplateModelResourcePermission
	implements ModelResourcePermission<CTCollectionTemplate> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CTCollectionTemplate ctCollectionTemplate, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, ctCollectionTemplate, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CTCollection.class.getName(),
				ctCollectionTemplate.getCtCollectionTemplateId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long ctCollectionTemplateId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, ctCollectionTemplateId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CTCollectionTemplate.class.getName(),
				ctCollectionTemplateId, actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker,
		CTCollectionTemplate ctCollectionTemplate, String actionId) {

		if (permissionChecker.hasOwnerPermission(
				ctCollectionTemplate.getCompanyId(),
				CTCollection.class.getName(),
				ctCollectionTemplate.getCtCollectionTemplateId(),
				ctCollectionTemplate.getUserId(), actionId)) {

			return true;
		}

		Group group = _groupLocalService.fetchGroup(
			ctCollectionTemplate.getCompanyId(),
			_classNameLocalService.getClassNameId(CTCollection.class),
			ctCollectionTemplate.getCtCollectionTemplateId());

		return permissionChecker.hasPermission(
			group, CTCollection.class.getName(),
			ctCollectionTemplate.getCtCollectionTemplateId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long ctCollectionTemplateId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_ctCollectionTemplateLocalService.getCTCollectionTemplate(
				ctCollectionTemplateId),
			actionId);
	}

	@Override
	public String getModelName() {
		return CTCollection.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CTCollectionTemplateLocalService _ctCollectionTemplateLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(target = "(resource.name=" + CTConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}