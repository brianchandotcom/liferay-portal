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

package com.liferay.commerce.product.content.web.internal.info.item.provider;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.info.exception.InfoItemPermissionException;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(enabled = false, service = InfoItemPermissionProvider.class)
public class CPDefinitionInfoItemPermissionProvider
	implements InfoItemPermissionProvider<CPDefinition> {

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, CPDefinition cpDefinition,
			String actionId)
		throws InfoItemPermissionException {

		try {
			return _cpDefinitionModelResourcePermission.contains(
				permissionChecker, cpDefinition, actionId);
		}
		catch (PortalException portalException) {
			throw new InfoItemPermissionException(
				cpDefinition.getCPDefinitionId(), portalException);
		}
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker,
			InfoItemReference infoItemReference, String actionId)
		throws InfoItemPermissionException {

		try {
			return _cpDefinitionModelResourcePermission.contains(
				permissionChecker, infoItemReference.getClassPK(), actionId);
		}
		catch (PortalException portalException) {
			throw new InfoItemPermissionException(
				infoItemReference.getClassPK(), portalException);
		}
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPDefinition)"
	)
	private ModelResourcePermission<CPDefinition>
		_cpDefinitionModelResourcePermission;

}