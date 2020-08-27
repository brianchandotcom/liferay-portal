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

package com.liferay.commerce.bom.internal.permission;

import com.liferay.commerce.bom.model.CommerceBOMFolder;
import com.liferay.commerce.bom.permission.CommerceBOMFolderPermission;
import com.liferay.commerce.bom.service.CommerceBOMFolderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = CommerceBOMFolderPermission.class)
public class CommerceBOMFolderPermissionImpl
	implements CommerceBOMFolderPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceBOMFolder commerceBOMFolder, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceBOMFolder, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceBOMFolder.class.getName(),
				commerceBOMFolder.getCommerceBOMFolderId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceBOMFolderId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceBOMFolderId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceBOMFolder.class.getName(),
				commerceBOMFolderId, actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker,
		CommerceBOMFolder commerceBOMFolder, String actionId) {

		if (contains(
				permissionChecker, commerceBOMFolder.getCommerceBOMFolderId(),
				actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long commerceBOMFolderId,
		String actionId) {

		CommerceBOMFolder commerceBOMFolder =
			_commerceBOMFolderLocalService.fetchCommerceBOMFolder(
				commerceBOMFolderId);

		if (commerceBOMFolder == null) {
			return false;
		}

		return _contains(permissionChecker, commerceBOMFolder, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long[] commerceBOMFolderIds,
			String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(commerceBOMFolderIds)) {
			return false;
		}

		for (long commerceBOMFolderId : commerceBOMFolderIds) {
			if (!contains(permissionChecker, commerceBOMFolderId, actionId)) {
				return false;
			}
		}

		return true;
	}

	private boolean _contains(
		PermissionChecker permissionChecker,
		CommerceBOMFolder commerceBOMFolder, String actionId) {

		if (permissionChecker.isCompanyAdmin(
				commerceBOMFolder.getCompanyId()) ||
			permissionChecker.isOmniadmin()) {

			return true;
		}

		if (permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(),
				CommerceBOMFolder.class.getName(),
				commerceBOMFolder.getCommerceBOMFolderId(),
				permissionChecker.getUserId(), actionId) &&
			(commerceBOMFolder.getUserId() == permissionChecker.getUserId())) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, CommerceBOMFolder.class.getName(),
			commerceBOMFolder.getCommerceBOMFolderId(), actionId);
	}

	@Reference
	private CommerceBOMFolderLocalService _commerceBOMFolderLocalService;

}