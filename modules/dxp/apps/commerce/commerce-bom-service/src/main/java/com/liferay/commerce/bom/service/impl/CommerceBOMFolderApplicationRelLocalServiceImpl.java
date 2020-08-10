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

import com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel;
import com.liferay.commerce.bom.service.base.CommerceBOMFolderApplicationRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.util.List;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CommerceBOMFolderApplicationRelLocalServiceImpl
	extends CommerceBOMFolderApplicationRelLocalServiceBaseImpl {

	@Override
	public CommerceBOMFolderApplicationRel addCommerceBOMFolderApplicationRel(
			long userId, long commerceBOMFolderId,
			long commerceApplicationModelId)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long commerceBOMFolderApplicationRelId =
			counterLocalService.increment();

		CommerceBOMFolderApplicationRel commerceBOMFolderApplicationRel =
			commerceBOMFolderApplicationRelPersistence.create(
				commerceBOMFolderApplicationRelId);

		commerceBOMFolderApplicationRel.setCompanyId(user.getCompanyId());
		commerceBOMFolderApplicationRel.setUserId(user.getUserId());
		commerceBOMFolderApplicationRel.setUserName(user.getFullName());
		commerceBOMFolderApplicationRel.setCommerceBOMFolderId(
			commerceBOMFolderId);
		commerceBOMFolderApplicationRel.setCommerceApplicationModelId(
			commerceApplicationModelId);

		return commerceBOMFolderApplicationRelPersistence.update(
			commerceBOMFolderApplicationRel);
	}

	@Override
	public void deleteCommerceBOMFolderApplicationRelsByCAMId(
		long commerceApplicationModelId) {

		commerceBOMFolderApplicationRelPersistence.
			removeByCommerceApplicationModelId(commerceApplicationModelId);
	}

	@Override
	public void deleteCommerceBOMFolderApplicationRelsByCommerceBOMFolderId(
		long commerceBOMFolderId) {

		commerceBOMFolderApplicationRelPersistence.removeByCommerceBOMFolderId(
			commerceBOMFolderId);
	}

	@Override
	public List<CommerceBOMFolderApplicationRel>
		getCommerceBOMFolderApplicationRelsByCAMId(
			long commerceApplicationModelId, int start, int end) {

		return commerceBOMFolderApplicationRelPersistence.
			findByCommerceApplicationModelId(
				commerceApplicationModelId, start, end);
	}

	@Override
	public List<CommerceBOMFolderApplicationRel>
		getCommerceBOMFolderApplicationRelsByCommerceBOMFolderId(
			long commerceBOMFolderId, int start, int end) {

		return commerceBOMFolderApplicationRelPersistence.
			findByCommerceBOMFolderId(commerceBOMFolderId, start, end);
	}

	@Override
	public int getCommerceBOMFolderApplicationRelsCountByCAMId(
		long commerceApplicationModelId) {

		return commerceBOMFolderApplicationRelPersistence.
			countByCommerceApplicationModelId(commerceApplicationModelId);
	}

	@Override
	public int getCommerceBOMFolderApplicationRelsCountByCommerceBOMFolderId(
		long commerceBOMFolderId) {

		return commerceBOMFolderApplicationRelPersistence.
			countByCommerceBOMFolderId(commerceBOMFolderId);
	}

}