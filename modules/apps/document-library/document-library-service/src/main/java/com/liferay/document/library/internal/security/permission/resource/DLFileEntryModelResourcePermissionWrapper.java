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

package com.liferay.document.library.internal.security.permission.resource;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionCheckerUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourcePermissionCheckerUtil;
import com.liferay.portal.kernel.security.permission.resource.BaseModelResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.DynamicInheritancePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.StagedModelPermissionLogic;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermission;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.constants.DLConstants;
import com.liferay.sharing.security.permission.resource.SharingModelResourcePermissionConfigurator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Preston Crary
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = ModelResourcePermission.class
)
public class DLFileEntryModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<DLFileEntry> {

	@Override
	protected ModelResourcePermission<DLFileEntry>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			DLFileEntry.class, DLFileEntry::getFileEntryId,
			_dlFileEntryLocalService::getDLFileEntry,
			_portletResourcePermission,
			(modelResourcePermission, consumer) -> {
				consumer.accept(
					new StagedModelPermissionLogic<>(
						_stagingPermission, DLPortletKeys.DOCUMENT_LIBRARY,
						DLFileEntry::getFileEntryId));
				consumer.accept(
					new DLFileEntryWorkflowedModelResourcePermissionLogic(
						modelResourcePermission));

				if (_sharingModelResourcePermissionConfigurator != null) {
					_sharingModelResourcePermissionConfigurator.configure(
						modelResourcePermission, consumer);
				}

				consumer.accept(
					(permissionChecker, name, fileEntry, actionId) -> {
						String className = fileEntry.getClassName();
						long classPK = fileEntry.getClassPK();

						if (Validator.isNull(className) || (classPK <= 0)) {
							return null;
						}

						Boolean hasResourcePermission =
							ResourcePermissionCheckerUtil.
								containsResourcePermission(
									permissionChecker, className, classPK,
									actionId);

						if ((hasResourcePermission != null) &&
							!hasResourcePermission) {

							return false;
						}

						Boolean hasBaseModelPermission =
							BaseModelPermissionCheckerUtil.
								containsBaseModelPermission(
									permissionChecker, fileEntry.getGroupId(),
									className, classPK, actionId);

						if ((hasBaseModelPermission != null) &&
							!hasBaseModelPermission) {

							return false;
						}

						return null;
					});

				if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
					consumer.accept(
						new DynamicInheritancePermissionLogic<>(
							_dlFolderModelResourcePermission,
							_getFetchParentFunction(), true));
				}
			});
	}

	private UnsafeFunction<DLFileEntry, DLFolder, PortalException>
		_getFetchParentFunction() {

		return fileEntry -> {
			long folderId = fileEntry.getFolderId();

			if (DLFolderConstants.DEFAULT_PARENT_FOLDER_ID == folderId) {
				return null;
			}

			if (fileEntry.isInTrash()) {
				return _dlFolderLocalService.fetchDLFolder(folderId);
			}

			return _dlFolderLocalService.getFolder(folderId);
		};
	}

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.document.library.kernel.model.DLFolder)"
	)
	private ModelResourcePermission<DLFolder> _dlFolderModelResourcePermission;

	@Reference(target = "(resource.name=" + DLConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private SharingModelResourcePermissionConfigurator
		_sharingModelResourcePermissionConfigurator;

	@Reference
	private StagingPermission _stagingPermission;

	@Reference
	private WorkflowPermission _workflowPermission;

	private class DLFileEntryWorkflowedModelResourcePermissionLogic
		implements ModelResourcePermissionLogic<DLFileEntry> {

		@Override
		public Boolean contains(
				PermissionChecker permissionChecker, String name,
				DLFileEntry dlFileEntry, String actionId)
			throws PortalException {

			DLFileVersion fileVersion = dlFileEntry.getFileVersion();

			if (fileVersion.isDraft() || fileVersion.isScheduled()) {
				if (actionId.equals(ActionKeys.VIEW) &&
					!_modelResourcePermission.contains(
						permissionChecker, dlFileEntry, ActionKeys.UPDATE)) {

					return false;
				}
			}
			else if (fileVersion.isPending()) {
				Boolean hasPermission = _workflowPermission.hasPermission(
					permissionChecker, fileVersion.getGroupId(), name,
					fileVersion.getFileVersionId(), actionId);

				if (hasPermission != null) {
					return hasPermission.booleanValue();
				}

				boolean hasOwnerPermission =
					permissionChecker.hasOwnerPermission(
						dlFileEntry.getCompanyId(), name,
						dlFileEntry.getFileEntryId(), dlFileEntry.getUserId(),
						actionId);

				if (!hasOwnerPermission) {
					return false;
				}
			}

			return null;
		}

		private DLFileEntryWorkflowedModelResourcePermissionLogic(
			ModelResourcePermission<DLFileEntry> modelResourcePermission) {

			_modelResourcePermission = modelResourcePermission;
		}

		private final ModelResourcePermission<DLFileEntry>
			_modelResourcePermission;

	}

}