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

package com.liferay.commerce.bom.service.impl;

import com.liferay.commerce.application.model.CommerceApplicationModel;
import com.liferay.commerce.bom.model.CommerceBOMFolder;
import com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel;
import com.liferay.commerce.bom.service.base.CommerceBOMFolderApplicationRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceBOMFolderApplicationRelServiceImpl
	extends CommerceBOMFolderApplicationRelServiceBaseImpl {

	@Override
	public CommerceBOMFolderApplicationRel addCommerceBOMFolderApplicationRel(
			long userId, long commerceBOMFolderId,
			long commerceApplicationModelId)
		throws PortalException {

		_commerceBOMFolderModelResourcePermission.check(
			getPermissionChecker(), commerceBOMFolderId, ActionKeys.UPDATE);

		_commerceApplicationModelModelResourcePermission.check(
			getPermissionChecker(), commerceApplicationModelId,
			ActionKeys.UPDATE);

		return commerceBOMFolderApplicationRelLocalService.
			addCommerceBOMFolderApplicationRel(
				userId, commerceBOMFolderId, commerceApplicationModelId);
	}

	@Override
	public void deleteCommerceBOMFolderApplicationRel(
			long commerceBOMFolderApplicationRelId)
		throws PortalException {

		CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel =
			commerceBOMFolderApplicationRelLocalService.
				getCommerceBOMFolderApplicationRel(
					commerceBOMFolderApplicationRelId);

		_commerceBOMFolderModelResourcePermission.check(
			getPermissionChecker(),
			commerceBOMFolderApplicationRel.getCommerceBOMFolderId(),
			ActionKeys.UPDATE);

		_commerceApplicationModelModelResourcePermission.check(
			getPermissionChecker(),
			commerceBOMFolderApplicationRel.getCommerceApplicationModelId(),
			ActionKeys.UPDATE);

		commerceBOMFolderApplicationRelLocalService.
			deleteCommerceBOMFolderApplicationRel(
				commerceBOMFolderApplicationRel);
	}

	@Override
	public List<CommerceBOMFolderApplicationRel>
			getCommerceBOMFolderApplicationRelsByCAMId(
				long commerceApplicationModelId, int start, int end)
		throws PortalException {

		_commerceApplicationModelModelResourcePermission.check(
			getPermissionChecker(), commerceApplicationModelId,
			ActionKeys.VIEW);

		return commerceBOMFolderApplicationRelLocalService.
			getCommerceBOMFolderApplicationRelsByCAMId(
				commerceApplicationModelId, start, end);
	}

	@Override
	public List<CommerceBOMFolderApplicationRel>
			getCommerceBOMFolderApplicationRelsByCommerceBOMFolderId(
				long commerceBOMFolderId, int start, int end)
		throws PortalException {

		_commerceBOMFolderModelResourcePermission.check(
			getPermissionChecker(), commerceBOMFolderId, ActionKeys.VIEW);

		return commerceBOMFolderApplicationRelLocalService.
			getCommerceBOMFolderApplicationRelsByCommerceBOMFolderId(
				commerceBOMFolderId, start, end);
	}

	@Override
	public int getCommerceBOMFolderApplicationRelsCountByCAMId(
			long commerceApplicationModelId)
		throws PortalException {

		_commerceApplicationModelModelResourcePermission.check(
			getPermissionChecker(), commerceApplicationModelId,
			ActionKeys.VIEW);

		return commerceBOMFolderApplicationRelLocalService.
			getCommerceBOMFolderApplicationRelsCountByCAMId(
				commerceApplicationModelId);
	}

	@Override
	public int getCommerceBOMFolderApplicationRelsCountByCommerceBOMFolderId(
			long commerceBOMFolderId)
		throws PortalException {

		_commerceBOMFolderModelResourcePermission.check(
			getPermissionChecker(), commerceBOMFolderId, ActionKeys.VIEW);

		return commerceBOMFolderApplicationRelLocalService.
			getCommerceBOMFolderApplicationRelsCountByCommerceBOMFolderId(
				commerceBOMFolderId);
	}

	private static volatile ModelResourcePermission<CommerceApplicationModel>
		_commerceApplicationModelModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceBOMFolderApplicationRelServiceImpl.class,
				"_commerceApplicationModelModelResourcePermission",
				CommerceApplicationModel.class);
	private static volatile ModelResourcePermission<CommerceBOMFolder>
		_commerceBOMFolderModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceBOMFolderApplicationRelServiceImpl.class,
				"_commerceBOMFolderModelResourcePermission",
				CommerceBOMFolder.class);

}