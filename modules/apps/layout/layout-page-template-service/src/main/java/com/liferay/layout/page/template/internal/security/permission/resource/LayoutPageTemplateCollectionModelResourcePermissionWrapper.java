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

package com.liferay.layout.page.template.internal.security.permission.resource;

import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.portal.kernel.security.permission.resource.BaseModelResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	property = "model.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateCollection",
	service = ModelResourcePermission.class
)
public class LayoutPageTemplateCollectionModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<LayoutPageTemplateCollection> {

	@Override
	protected ModelResourcePermission<LayoutPageTemplateCollection>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			LayoutPageTemplateCollection.class,
			LayoutPageTemplateCollection::getLayoutPageTemplateCollectionId,
			_layoutPageTemplateCollectionLocalService::
				getLayoutPageTemplateCollection,
			_portletResourcePermission,
			(modelResourcePermission, consumer) -> {
			});
	}

	@Reference
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Reference(
		target = "(resource.name=" + LayoutPageTemplateConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}