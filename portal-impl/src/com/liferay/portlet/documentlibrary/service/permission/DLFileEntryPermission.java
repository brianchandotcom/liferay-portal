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

package com.liferay.portlet.documentlibrary.service.permission;

import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionCheckerUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourcePermissionCheckerUtil;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalServiceUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermissionUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Brian Wing Shun Chan
 * @author Charles May
 */
@OSGiBeanProperties(
	property = {
		"model.class.name=com.liferay.document.library.kernel.model.DLFileEntry"
	}
)
public class DLFileEntryPermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, DLFileEntry dlFileEntry,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, dlFileEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DLFileEntry.class.getName(),
				dlFileEntry.getFileEntryId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, FileEntry fileEntry,
			String actionId)
		throws PortalException {

		if (!fileEntry.containsPermission(permissionChecker, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, FileEntry.class.getName(),
				fileEntry.getFileEntryId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long fileEntryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, fileEntryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, FileEntry.class.getName(), fileEntryId,
				actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, DLFileEntry dlFileEntry,
			String actionId)
		throws PortalException {

		String portletId = PortletProviderUtil.getPortletId(
			FileEntry.class.getName(), PortletProvider.Action.EDIT);

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, dlFileEntry.getGroupId(),
			DLFileEntry.class.getName(), dlFileEntry.getFileEntryId(),
			portletId, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		DLFileVersion currentDLFileVersion = dlFileEntry.getFileVersion();

		if (currentDLFileVersion.isPending()) {
			hasPermission = WorkflowPermissionUtil.hasPermission(
				permissionChecker, dlFileEntry.getGroupId(),
				DLFileEntry.class.getName(),
				currentDLFileVersion.getFileVersionId(), actionId);

			if (hasPermission != null) {
				return hasPermission.booleanValue();
			}

			// See LPS-10500 and LPS-72547

			if (actionId.equals(ActionKeys.VIEW) &&
				_hasActiveWorkflowInstance(
					permissionChecker.getCompanyId(), dlFileEntry.getGroupId(),
					currentDLFileVersion.getFileVersionId())) {

				return false;
			}
		}

		if (permissionChecker.hasOwnerPermission(
				dlFileEntry.getCompanyId(), DLFileEntry.class.getName(),
				dlFileEntry.getFileEntryId(), dlFileEntry.getUserId(),
				actionId)) {

			return true;
		}

		String className = dlFileEntry.getClassName();
		long classPK = dlFileEntry.getClassPK();

		if (Validator.isNotNull(className) && (classPK > 0)) {
			Boolean hasResourcePermission =
				ResourcePermissionCheckerUtil.containsResourcePermission(
					permissionChecker, className, classPK, actionId);

			if ((hasResourcePermission != null) && !hasResourcePermission) {
				return false;
			}

			Boolean hasBaseModelPermission =
				BaseModelPermissionCheckerUtil.containsBaseModelPermission(
					permissionChecker, dlFileEntry.getGroupId(), className,
					classPK, actionId);

			if ((hasBaseModelPermission != null) && !hasBaseModelPermission) {
				return false;
			}
		}

		if (actionId.equals(ActionKeys.VIEW) &&
			PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {

			long dlFolderId = dlFileEntry.getFolderId();

			if (dlFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				if (!DLPermission.contains(
						permissionChecker, dlFileEntry.getGroupId(),
						actionId)) {

					return false;
				}
			}
			else {
				try {
					DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(
						dlFolderId);

					if (!DLFolderPermission.contains(
							permissionChecker, dlFolder, ActionKeys.ACCESS) &&
						!DLFolderPermission.contains(
							permissionChecker, dlFolder, ActionKeys.VIEW)) {

						return false;
					}
				}
				catch (NoSuchFolderException nsfe) {
					if (!dlFileEntry.isInTrash()) {
						throw nsfe;
					}
				}
			}
		}

		return permissionChecker.hasPermission(
			dlFileEntry.getGroupId(), DLFileEntry.class.getName(),
			dlFileEntry.getFileEntryId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, FileEntry fileEntry,
			String actionId)
		throws PortalException {

		return fileEntry.containsPermission(permissionChecker, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long fileEntryId,
			String actionId)
		throws PortalException {

		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(fileEntryId);

		return fileEntry.containsPermission(permissionChecker, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	private static boolean _hasActiveWorkflowInstance(
			long companyId, long groupId, long fileVersionId)
		throws WorkflowException {

		WorkflowInstanceLink workflowInstanceLink =
			WorkflowInstanceLinkLocalServiceUtil.fetchWorkflowInstanceLink(
				companyId, groupId, DLFileEntryConstants.getClassName(),
				fileVersionId);

		if (workflowInstanceLink == null) {
			return false;
		}

		WorkflowInstance workflowInstance =
			WorkflowInstanceManagerUtil.getWorkflowInstance(
				companyId, workflowInstanceLink.getWorkflowInstanceId());

		if (workflowInstance.isComplete()) {
			return false;
		}

		return true;
	}

}