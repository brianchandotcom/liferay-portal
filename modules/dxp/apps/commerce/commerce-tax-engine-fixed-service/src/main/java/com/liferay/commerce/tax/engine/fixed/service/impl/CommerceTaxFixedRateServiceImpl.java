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
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate;
import com.liferay.commerce.tax.engine.fixed.service.base.CommerceTaxFixedRateServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxFixedRateServiceImpl
	extends CommerceTaxFixedRateServiceBaseImpl {

	@Override
	public CommerceTaxFixedRate addCommerceTaxFixedRate(
			long commerceTaxMethodId, long cpTaxCategoryId, double rate,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);

		return commerceTaxFixedRateLocalService.addCommerceTaxFixedRate(
			commerceTaxMethodId, cpTaxCategoryId, rate, serviceContext);
	}

	@Override
	public void deleteCommerceTaxFixedRate(long commerceTaxFixedRateId)
		throws PortalException {

		CommerceTaxFixedRate commerceTaxFixedRate =
			commerceTaxFixedRateLocalService.getCommerceTaxFixedRate(
				commerceTaxFixedRateId);

		CommercePermission.check(
			getPermissionChecker(), commerceTaxFixedRate.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);

		commerceTaxFixedRateLocalService.deleteCommerceTaxFixedRate(
			commerceTaxFixedRate);
	}

	@Override
	public CommerceTaxFixedRate fetchCommerceTaxFixedRate(
			long commerceTaxFixedRateId)
		throws PortalException {

		CommerceTaxFixedRate commerceTaxFixedRate =
			commerceTaxFixedRateLocalService.fetchCommerceTaxFixedRate(
				commerceTaxFixedRateId);

		if (commerceTaxFixedRate != null) {
			CommercePermission.check(
				getPermissionChecker(), commerceTaxFixedRate.getGroupId(),
				CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);
		}

		return commerceTaxFixedRate;
	}

	@Override
	public CommerceTaxFixedRate fetchCommerceTaxFixedRate(
			long cpTaxCategoryId, long commerceTaxMethodId)
		throws PortalException {

		CommerceTaxFixedRate commerceTaxFixedRate =
			commerceTaxFixedRateLocalService.fetchCommerceTaxFixedRate(
				cpTaxCategoryId, commerceTaxMethodId);

		if (commerceTaxFixedRate != null) {
			CommercePermission.check(
				getPermissionChecker(), commerceTaxFixedRate.getGroupId(),
				CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);
		}

		return commerceTaxFixedRate;
	}

	@Override
	public List<CommerceTaxFixedRate> getCommerceTaxFixedRates(
			long groupId, long commerceTaxMethodId, int start, int end,
			OrderByComparator<CommerceTaxFixedRate> orderByComparator)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), groupId,
			CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);

		return commerceTaxFixedRateLocalService.getCommerceTaxFixedRates(
			commerceTaxMethodId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTaxFixedRatesCount(
			long groupId, long commerceTaxMethodId)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), groupId,
			CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);

		return commerceTaxFixedRateLocalService.getCommerceTaxFixedRatesCount(
			commerceTaxMethodId);
	}

	@Override
	public CommerceTaxFixedRate updateCommerceTaxFixedRate(
			long commerceTaxFixedRateId, double rate)
		throws PortalException {

		CommerceTaxFixedRate commerceTaxFixedRate =
			commerceTaxFixedRateLocalService.getCommerceTaxFixedRate(
				commerceTaxFixedRateId);

		CommercePermission.check(
			getPermissionChecker(), commerceTaxFixedRate.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);

		return commerceTaxFixedRateLocalService.updateCommerceTaxFixedRate(
			commerceTaxFixedRateId, rate);
	}

}