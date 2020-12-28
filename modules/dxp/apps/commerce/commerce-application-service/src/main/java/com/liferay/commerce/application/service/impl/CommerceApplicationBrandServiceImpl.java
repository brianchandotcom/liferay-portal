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
import com.liferay.commerce.application.model.CommerceApplicationBrand;
import com.liferay.commerce.application.service.base.CommerceApplicationBrandServiceBaseImpl;
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
public class CommerceApplicationBrandServiceImpl
	extends CommerceApplicationBrandServiceBaseImpl {

	@Override
	public CommerceApplicationBrand addCommerceApplicationBrand(
			long userId, String name, boolean logo, byte[] logoBytes)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceApplicationBrandModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceApplicationActionKeys.ADD_COMMERCE_BRAND);

		return commerceApplicationBrandLocalService.addCommerceApplicationBrand(
			userId, name, logo, logoBytes);
	}

	@Override
	public void deleteCommerceApplicationBrand(long commerceApplicationBrandId)
		throws PortalException {

		_commerceApplicationBrandModelResourcePermission.check(
			getPermissionChecker(), commerceApplicationBrandId,
			ActionKeys.DELETE);

		commerceApplicationBrandLocalService.deleteCommerceApplicationBrand(
			commerceApplicationBrandId);
	}

	@Override
	public CommerceApplicationBrand getCommerceApplicationBrand(
			long commerceApplicationBrandId)
		throws PortalException {

		_commerceApplicationBrandModelResourcePermission.check(
			getPermissionChecker(), commerceApplicationBrandId,
			ActionKeys.VIEW);

		return commerceApplicationBrandLocalService.getCommerceApplicationBrand(
			commerceApplicationBrandId);
	}

	@Override
	public List<CommerceApplicationBrand> getCommerceApplicationBrands(
		long companyId, int start, int end) {

		return commerceApplicationBrandPersistence.filterFindByCompanyId(
			companyId, start, end);
	}

	@Override
	public int getCommerceApplicationBrandsCount(long companyId) {
		return commerceApplicationBrandPersistence.filterCountByCompanyId(
			companyId);
	}

	@Override
	public CommerceApplicationBrand updateCommerceApplicationBrand(
			long commerceApplicationBrandId, String name, boolean logo,
			byte[] logoBytes)
		throws PortalException {

		_commerceApplicationBrandModelResourcePermission.check(
			getPermissionChecker(), commerceApplicationBrandId,
			ActionKeys.UPDATE);

		return commerceApplicationBrandLocalService.
			updateCommerceApplicationBrand(
				commerceApplicationBrandId, name, logo, logoBytes);
	}

	private static volatile ModelResourcePermission<CommerceApplicationBrand>
		_commerceApplicationBrandModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceApplicationBrandServiceImpl.class,
				"_commerceApplicationBrandModelResourcePermission",
				CommerceApplicationBrand.class);

}