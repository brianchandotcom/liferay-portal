/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				permissionChecker, CTCollectionTemplate.class.getName(),
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
				CTCollectionTemplate.class.getName(),
				ctCollectionTemplate.getCtCollectionTemplateId(),
				ctCollectionTemplate.getUserId(), actionId)) {

			return true;
		}

		Group group = _groupLocalService.fetchGroup(
			ctCollectionTemplate.getCompanyId(),
			_classNameLocalService.getClassNameId(CTCollectionTemplate.class),
			ctCollectionTemplate.getCtCollectionTemplateId());

		return permissionChecker.hasPermission(
			group, CTCollectionTemplate.class.getName(),
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