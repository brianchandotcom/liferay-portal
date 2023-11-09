/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.internal.security.permission.resource;

import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.security.permission.resource.BaseModelResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.StagedModelPermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.WorkflowedModelPermissionLogic;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.sharing.security.permission.resource.SharingModelResourcePermissionConfigurator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julius Lee
 */
@Component(
	property = "model.class.name=com.liferay.blogs.model.BlogsEntry",
	service = ModelResourcePermission.class
)
public class BlogsEntryModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<BlogsEntry> {

	@Override
	protected ModelResourcePermission<BlogsEntry>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			BlogsEntry.class, BlogsEntry::getEntryId,
			_blogsEntryLocalService::getBlogsEntry, _portletResourcePermission,
			(modelResourcePermission, consumer) -> {
				consumer.accept(
					new StagedModelPermissionLogic<>(
						_stagingPermission, BlogsPortletKeys.BLOGS,
						BlogsEntry::getEntryId));
				consumer.accept(
					new WorkflowedModelPermissionLogic<>(
						modelResourcePermission, _groupLocalService,
						BlogsEntry::getEntryId));

				_sharingModelResourcePermissionConfigurator.configure(
					modelResourcePermission, consumer);
			});
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(target = "(resource.name=" + BlogsConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private SharingModelResourcePermissionConfigurator
		_sharingModelResourcePermissionConfigurator;

	@Reference
	private StagingPermission _stagingPermission;

}