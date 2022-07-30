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

package com.liferay.commerce.tax.engine.fixed.service.impl;

import com.liferay.commerce.tax.engine.fixed.exception.DuplicateCommerceTaxFixedRateException;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate;
import com.liferay.commerce.tax.engine.fixed.service.base.CommerceTaxFixedRateLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate",
	service = AopService.class
)
public class CommerceTaxFixedRateLocalServiceImpl
	extends CommerceTaxFixedRateLocalServiceBaseImpl {

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommerceTaxFixedRate addCommerceTaxFixedRate(
			long commerceTaxMethodId, long cpTaxCategoryId, double rate,
			ServiceContext serviceContext)
		throws PortalException {

		return commerceTaxFixedRateLocalService.addCommerceTaxFixedRate(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			commerceTaxMethodId, cpTaxCategoryId, rate);
	}

	@Override
	public CommerceTaxFixedRate addCommerceTaxFixedRate(
			long userId, long groupId, long commerceTaxMethodId,
			long cpTaxCategoryId, double rate)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		validate(cpTaxCategoryId, commerceTaxMethodId);

		long commerceTaxFixedRateId = counterLocalService.increment();

		CommerceTaxFixedRate commerceTaxFixedRate =
			commerceTaxFixedRatePersistence.create(commerceTaxFixedRateId);

		commerceTaxFixedRate.setGroupId(groupId);
		commerceTaxFixedRate.setCompanyId(user.getCompanyId());
		commerceTaxFixedRate.setUserId(user.getUserId());
		commerceTaxFixedRate.setUserName(user.getFullName());
		commerceTaxFixedRate.setCPTaxCategoryId(cpTaxCategoryId);
		commerceTaxFixedRate.setCommerceTaxMethodId(commerceTaxMethodId);
		commerceTaxFixedRate.setRate(rate);

		return commerceTaxFixedRatePersistence.update(commerceTaxFixedRate);
	}

	@Override
	public void deleteCommerceTaxFixedRateByCommerceTaxMethodId(
		long commerceTaxMethodId) {

		commerceTaxFixedRatePersistence.removeByCommerceTaxMethodId(
			commerceTaxMethodId);
	}

	@Override
	public void deleteCommerceTaxFixedRateByCPTaxCategoryId(
		long cpTaxCategoryId) {

		commerceTaxFixedRatePersistence.removeByCPTaxCategoryId(
			cpTaxCategoryId);
	}

	@Override
	public CommerceTaxFixedRate fetchCommerceTaxFixedRate(
			long cpTaxCategoryId, long commerceTaxMethodId)
		throws PortalException {

		return commerceTaxFixedRatePersistence.fetchByC_C(
			cpTaxCategoryId, commerceTaxMethodId);
	}

	@Override
	public CommerceTaxFixedRate getCommerceTaxFixedRate(
			long cpTaxCategoryId, long commerceTaxMethodId)
		throws PortalException {

		return commerceTaxFixedRatePersistence.findByC_C(
			cpTaxCategoryId, commerceTaxMethodId);
	}

	@Override
	public List<CommerceTaxFixedRate> getCommerceTaxFixedRates(
		long commerceTaxMethodId, int start, int end,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator) {

		return commerceTaxFixedRatePersistence.findByCommerceTaxMethodId(
			commerceTaxMethodId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTaxFixedRatesCount(long commerceTaxMethodId) {
		return commerceTaxFixedRatePersistence.countByCommerceTaxMethodId(
			commerceTaxMethodId);
	}

	@Override
	public CommerceTaxFixedRate updateCommerceTaxFixedRate(
			long commerceTaxFixedRateId, double rate)
		throws PortalException {

		CommerceTaxFixedRate commerceTaxFixedRate =
			commerceTaxFixedRatePersistence.findByPrimaryKey(
				commerceTaxFixedRateId);

		commerceTaxFixedRate.setRate(rate);

		return commerceTaxFixedRatePersistence.update(commerceTaxFixedRate);
	}

	protected void validate(long cpTaxCategoryId, long commerceTaxMethodId)
		throws PortalException {

		int count = commerceTaxFixedRatePersistence.countByC_C(
			cpTaxCategoryId, commerceTaxMethodId);

		if (count > 0) {
			throw new DuplicateCommerceTaxFixedRateException();
		}
	}

}