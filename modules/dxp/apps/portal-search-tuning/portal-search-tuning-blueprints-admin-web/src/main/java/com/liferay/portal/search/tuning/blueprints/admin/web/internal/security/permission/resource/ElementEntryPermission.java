/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.admin.web.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.search.tuning.blueprints.model.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = ElementEntryPermission.class)
public class ElementEntryPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, Element entry,
			String actionKey)
		throws PortalException {

		return _elementEntryModelResourcePermission.contains(
			permissionChecker, entry, actionKey);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long entryId, String actionKey)
		throws PortalException {

		return _elementEntryModelResourcePermission.contains(
			permissionChecker, entryId, actionKey);
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.search.tuning.blueprints.model.Element)",
		unbind = "-"
	)
	protected void setEntryModelPermission(
		ModelResourcePermission<Element> modelResourcePermission) {

		_elementEntryModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<Element>
		_elementEntryModelResourcePermission;

}