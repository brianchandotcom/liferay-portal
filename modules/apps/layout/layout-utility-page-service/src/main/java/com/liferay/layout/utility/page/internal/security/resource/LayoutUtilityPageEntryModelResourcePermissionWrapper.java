/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.internal.security.resource;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.utility.page.constants.LayoutUtilityPageConstants;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.kernel.security.permission.resource.BaseModelResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.StagedModelPermissionLogic;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = "model.class.name=com.liferay.layout.utility.page.model.LayoutUtilityPageEntry",
	service = ModelResourcePermission.class
)
public class LayoutUtilityPageEntryModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<LayoutUtilityPageEntry> {

	@Override
	protected ModelResourcePermission<LayoutUtilityPageEntry>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			LayoutUtilityPageEntry.class,
			LayoutUtilityPageEntry::getLayoutUtilityPageEntryId,
			_layoutUtilityPageEntryLocalService::getLayoutUtilityPageEntry,
			_portletResourcePermission,
			(modelResourcePermission, consumer) -> consumer.accept(
				new StagedModelPermissionLogic<>(
					_stagingPermission, LayoutAdminPortletKeys.GROUP_PAGES,
					LayoutUtilityPageEntry::getLayoutUtilityPageEntryId)));
	}

	@Reference
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Reference(
		target = "(resource.name=" + LayoutUtilityPageConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private StagingPermission _stagingPermission;

}