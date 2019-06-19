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

package com.liferay.blogs.internal.security.permission.resource.definition;

import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.StagedModelPermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.WorkflowedModelPermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.definition.ModelResourcePermissionDefinition;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermission;
import com.liferay.sharing.security.permission.resource.SharingModelResourcePermissionConfigurator;

import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = ModelResourcePermissionDefinition.class)
public class BlogsEntryModelResourcePermissionDefinition
	implements ModelResourcePermissionDefinition<BlogsEntry> {

	@Override
	public BlogsEntry getModel(long entryId) throws PortalException {
		return _blogsEntryLocalService.getBlogsEntry(entryId);
	}

	@Override
	public Class<BlogsEntry> getModelClass() {
		return BlogsEntry.class;
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Override
	public long getPrimaryKey(BlogsEntry blogsEntry) {
		return blogsEntry.getEntryId();
	}

	@Override
	public void registerModelResourcePermissionLogics(
		ModelResourcePermission<BlogsEntry> modelResourcePermission,
		Consumer<ModelResourcePermissionLogic<BlogsEntry>>
			modelResourcePermissionLogicConsumer) {

		modelResourcePermissionLogicConsumer.accept(
			new StagedModelPermissionLogic<>(
				_stagingPermission, BlogsPortletKeys.BLOGS,
				BlogsEntry::getEntryId));
		modelResourcePermissionLogicConsumer.accept(
			new WorkflowedModelPermissionLogic<>(
				_workflowPermission, modelResourcePermission,
				_groupLocalService, BlogsEntry::getEntryId));

		if (_sharingModelResourcePermissionConfigurator != null) {
			_sharingModelResourcePermissionConfigurator.configure(
				modelResourcePermission, modelResourcePermissionLogicConsumer);
		}
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(target = "(resource.name=" + BlogsConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile SharingModelResourcePermissionConfigurator
		_sharingModelResourcePermissionConfigurator;

	@Reference
	private StagingPermission _stagingPermission;

	@Reference
	private WorkflowPermission _workflowPermission;

}