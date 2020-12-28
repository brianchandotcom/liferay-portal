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

import com.liferay.commerce.application.constants.CommerceApplicationActionKeys;
import com.liferay.commerce.application.model.CommerceApplicationModel;
import com.liferay.commerce.application.service.base.CommerceApplicationModelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import java.util.List;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CommerceApplicationModelServiceImpl
	extends CommerceApplicationModelServiceBaseImpl {

	@Override
	public CommerceApplicationModel addCommerceApplicationModel(
			long userId, long commerceApplicationBrandId, String name,
			String year)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceApplicationModelModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceApplicationActionKeys.ADD_COMMERCE_MODEL);

		return commerceApplicationModelLocalService.addCommerceApplicationModel(
			userId, commerceApplicationBrandId, name, year);
	}

	@Override
	public void deleteCommerceApplicationModel(long commerceApplicationModelId)
		throws PortalException {

		_commerceApplicationModelModelResourcePermission.check(
			getPermissionChecker(), commerceApplicationModelId,
			ActionKeys.DELETE);

		commerceApplicationModelLocalService.deleteCommerceApplicationModel(
			commerceApplicationModelId);
	}

	@Override
	public CommerceApplicationModel getCommerceApplicationModel(
			long commerceApplicationModelId)
		throws PortalException {

		_commerceApplicationModelModelResourcePermission.check(
			getPermissionChecker(), commerceApplicationModelId,
			ActionKeys.VIEW);

		return commerceApplicationModelLocalService.getCommerceApplicationModel(
			commerceApplicationModelId);
	}

	@Override
	public List<CommerceApplicationModel> getCommerceApplicationModels(
		long commerceApplicationBrandId, int start, int end) {

		return commerceApplicationModelPersistence.
			filterFindByCommerceApplicationBrandId(
				commerceApplicationBrandId, start, end);
	}

	@Override
	public List<CommerceApplicationModel>
		getCommerceApplicationModelsByCompanyId(
			long companyId, int start, int end) {

		return commerceApplicationModelPersistence.filterFindByCompanyId(
			companyId, start, end);
	}

	@Override
	public int getCommerceApplicationModelsCount(
		long commerceApplicationBrandId) {

		return commerceApplicationModelPersistence.
			filterCountByCommerceApplicationBrandId(commerceApplicationBrandId);
	}

	@Override
	public int getCommerceApplicationModelsCountByCompanyId(long companyId) {
		return commerceApplicationModelPersistence.filterCountByCompanyId(
			companyId);
	}

	@Override
	public CommerceApplicationModel updateCommerceApplicationModel(
			long commerceApplicationModelId, String name, String year)
		throws PortalException {

		_commerceApplicationModelModelResourcePermission.check(
			getPermissionChecker(), commerceApplicationModelId,
			ActionKeys.UPDATE);

		return commerceApplicationModelLocalService.
			updateCommerceApplicationModel(
				commerceApplicationModelId, name, year);
	}

	private static volatile ModelResourcePermission<CommerceApplicationModel>
		_commerceApplicationModelModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceApplicationModelServiceImpl.class,
				"_commerceApplicationModelModelResourcePermission",
				CommerceApplicationModel.class);

}