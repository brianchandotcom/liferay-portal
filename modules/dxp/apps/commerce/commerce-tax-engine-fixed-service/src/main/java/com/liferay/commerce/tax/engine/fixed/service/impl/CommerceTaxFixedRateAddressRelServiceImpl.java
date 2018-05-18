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

package com.liferay.commerce.tax.engine.fixed.service.impl;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.service.base.CommerceTaxFixedRateAddressRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxFixedRateAddressRelServiceImpl
	extends CommerceTaxFixedRateAddressRelServiceBaseImpl {

	@Override
	public CommerceTaxFixedRateAddressRel addCommerceTaxFixedRateAddressRel(
			long commerceTaxMethodId, long cpTaxCategoryId,
			long commerceCountryId, long commerceRegionId, String zip,
			double rate, ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);

		return commerceTaxFixedRateAddressRelLocalService.
			addCommerceTaxFixedRateAddressRel(
				commerceTaxMethodId, cpTaxCategoryId, commerceCountryId,
				commerceRegionId, zip, rate, serviceContext);
	}

	@Override
	public void deleteCommerceTaxFixedRateAddressRel(
			long commerceTaxFixedRateAddressRelId)
		throws PortalException {

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			commerceTaxFixedRateAddressRelLocalService.
				getCommerceTaxFixedRateAddressRel(
					commerceTaxFixedRateAddressRelId);

		CommercePermission.check(
			getPermissionChecker(), commerceTaxFixedRateAddressRel.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);

		commerceTaxFixedRateAddressRelLocalService.
			deleteCommerceTaxFixedRateAddressRel(
				commerceTaxFixedRateAddressRel);
	}

	@Override
	public CommerceTaxFixedRateAddressRel fetchCommerceTaxFixedRateAddressRel(
			long commerceTaxFixedRateAddressRelId)
		throws PortalException {

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			commerceTaxFixedRateAddressRelLocalService.
				fetchCommerceTaxFixedRateAddressRel(
					commerceTaxFixedRateAddressRelId);

		if (commerceTaxFixedRateAddressRel != null) {
			CommercePermission.check(
				getPermissionChecker(),
				commerceTaxFixedRateAddressRel.getGroupId(),
				CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);
		}

		return commerceTaxFixedRateAddressRel;
	}

	@Override
	public List<CommerceTaxFixedRateAddressRel>
			getCommerceTaxMethodFixedRateAddressRels(
				long groupId, long commerceTaxMethodId, int start, int end,
				OrderByComparator<CommerceTaxFixedRateAddressRel>
					orderByComparator)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), groupId,
			CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);

		return commerceTaxFixedRateAddressRelLocalService.
			getCommerceTaxMethodFixedRateAddressRels(
				commerceTaxMethodId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTaxMethodFixedRateAddressRelsCount(
			long groupId, long commerceTaxMethodId)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), groupId,
			CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);

		return commerceTaxFixedRateAddressRelLocalService.
			getCommerceTaxMethodFixedRateAddressRelsCount(commerceTaxMethodId);
	}

	@Override
	public CommerceTaxFixedRateAddressRel updateCommerceTaxFixedRateAddressRel(
			long commerceTaxFixedRateAddressRelId, long commerceCountryId,
			long commerceRegionId, String zip, double rate)
		throws PortalException {

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			commerceTaxFixedRateAddressRelLocalService.
				getCommerceTaxFixedRateAddressRel(
					commerceTaxFixedRateAddressRelId);

		CommercePermission.check(
			getPermissionChecker(), commerceTaxFixedRateAddressRel.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);

		return commerceTaxFixedRateAddressRelLocalService.
			updateCommerceTaxFixedRateAddressRel(
				commerceTaxFixedRateAddressRelId, commerceCountryId,
				commerceRegionId, zip, rate);
	}

}