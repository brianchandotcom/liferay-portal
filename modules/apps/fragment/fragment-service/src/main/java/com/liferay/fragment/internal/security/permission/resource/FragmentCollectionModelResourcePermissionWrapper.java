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

package com.liferay.fragment.internal.security.permission.resource;

import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.BaseModelResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(
	property = "model.class.name=com.liferay.fragment.model.FragmentCollection",
	service = ModelResourcePermission.class
)
public class FragmentCollectionModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<FragmentCollection> {

	@Override
	protected ModelResourcePermission<FragmentCollection>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			FragmentCollection.class,
			FragmentCollection::getFragmentCollectionId,
			_fragmentCollectionLocalService::getFragmentCollection,
			_portletResourcePermission,
			(modelResourcePermission, consumer) -> consumer.accept(
				(permissionChecker, name, model, actionId) -> {
					if (actionId.equals(ActionKeys.VIEW) ||
						_portletResourcePermission.contains(
							permissionChecker, model.getGroupId(),
							FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES)) {

						return true;
					}

					return null;
				}));
	}

	@Reference
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Reference(
		target = "(resource.name=" + FragmentConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}