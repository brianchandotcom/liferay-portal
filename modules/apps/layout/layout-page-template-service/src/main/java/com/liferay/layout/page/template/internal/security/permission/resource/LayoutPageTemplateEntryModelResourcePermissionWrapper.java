/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.security.permission.resource;

import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
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
	property = "model.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateEntry",
	service = ModelResourcePermission.class
)
public class LayoutPageTemplateEntryModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<LayoutPageTemplateEntry> {

	@Override
	protected ModelResourcePermission<LayoutPageTemplateEntry>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			LayoutPageTemplateEntry.class,
			LayoutPageTemplateEntry::getLayoutPageTemplateEntryId,
			_layoutPageTemplateEntryLocalService::getLayoutPageTemplateEntry,
			_portletResourcePermission,
			(modelResourcePermission, consumer) -> {
			});
	}

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference(
		target = "(resource.name=" + LayoutPageTemplateConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}