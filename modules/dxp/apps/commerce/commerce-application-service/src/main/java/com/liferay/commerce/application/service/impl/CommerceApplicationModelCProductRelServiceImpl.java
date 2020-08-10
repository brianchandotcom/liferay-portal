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

package com.liferay.commerce.application.service.impl;

import com.liferay.commerce.application.model.CommerceApplicationModel;
import com.liferay.commerce.application.model.CommerceApplicationModelCProductRel;
import com.liferay.commerce.application.service.base.CommerceApplicationModelCProductRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceApplicationModelCProductRelServiceImpl
	extends CommerceApplicationModelCProductRelServiceBaseImpl {

	@Override
	public CommerceApplicationModelCProductRel
			addCommerceApplicationModelCProductRel(
				long userId, long commerceApplicationModelId, long cProductId)
		throws PortalException {

		_commerceApplicationModelModelResourcePermission.check(
			getPermissionChecker(), commerceApplicationModelId,
			ActionKeys.UPDATE);

		return commerceApplicationModelCProductRelLocalService.
			addCommerceApplicationModelCProductRel(
				userId, commerceApplicationModelId, cProductId);
	}

	@Override
	public void deleteCommerceApplicationModelCProductRel(
			long commerceApplicationModelCProductRelId)
		throws PortalException {

		commerceApplicationModelCProductRelLocalService.
			deleteCommerceApplicationModelCProductRel(
				commerceApplicationModelCProductRelId);
	}

	@Override
	public List<CommerceApplicationModelCProductRel>
			getCommerceApplicationModelCProductRels(
				long commerceApplicationModelId, int start, int end)
		throws PortalException {

		_commerceApplicationModelModelResourcePermission.check(
			getPermissionChecker(), commerceApplicationModelId,
			ActionKeys.VIEW);

		return commerceApplicationModelCProductRelLocalService.
			getCommerceApplicationModelCProductRels(
				commerceApplicationModelId, start, end);
	}

	@Override
	public int getCommerceApplicationModelCProductRelsCount(
			long commerceApplicationModelId)
		throws PortalException {

		_commerceApplicationModelModelResourcePermission.check(
			getPermissionChecker(), commerceApplicationModelId,
			ActionKeys.VIEW);

		return commerceApplicationModelCProductRelLocalService.
			getCommerceApplicationModelCProductRelsCount(
				commerceApplicationModelId);
	}

	private static volatile ModelResourcePermission<CommerceApplicationModel>
		_commerceApplicationModelModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceApplicationModelCProductRelServiceImpl.class,
				"_commerceApplicationModelModelResourcePermission",
				CommerceApplicationModel.class);

}