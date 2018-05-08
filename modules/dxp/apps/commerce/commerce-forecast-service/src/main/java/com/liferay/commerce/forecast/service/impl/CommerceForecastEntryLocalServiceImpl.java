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

package com.liferay.commerce.forecast.service.impl;

import com.liferay.commerce.forecast.model.CommerceForecastEntry;
import com.liferay.commerce.forecast.service.base.CommerceForecastEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceForecastEntryLocalServiceImpl
	extends CommerceForecastEntryLocalServiceBaseImpl {

	@Override
	public CommerceForecastEntry addCommerceForecastEntry(
			long userId, Date date, int period, int target, long customerId,
			String sku, BigDecimal assertivity)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		CommerceForecastEntry commerceForecastEntry =
			commerceForecastEntryPersistence.fetchByC_P_T_C_S(
				user.getCompanyId(), period, target, customerId, sku);

		if (commerceForecastEntry == null) {
			long commerceForecastEntryId = counterLocalService.increment();

			commerceForecastEntry = commerceForecastEntryPersistence.create(
				commerceForecastEntryId);

			commerceForecastEntry.setCompanyId(user.getCompanyId());
			commerceForecastEntry.setUserId(user.getUserId());
			commerceForecastEntry.setUserName(user.getFullName());
			commerceForecastEntry.setPeriod(period);
			commerceForecastEntry.setTarget(target);
			commerceForecastEntry.setCustomerId(customerId);
			commerceForecastEntry.setSku(sku);
		}
		else {
			commerceForecastValueLocalService.deleteCommerceForecastValues(
				commerceForecastEntry.getCommerceForecastEntryId());
		}

		commerceForecastEntry.setDate(date);
		commerceForecastEntry.setAssertivity(assertivity);

		return commerceForecastEntryPersistence.update(commerceForecastEntry);
	}

	@Override
	public void deleteCommerceForecastEntries(long companyId) {
		List<CommerceForecastEntry> commerceForecastEntries =
			commerceForecastEntryPersistence.findByCompanyId(companyId);

		for (CommerceForecastEntry commerceForecastEntry :
				commerceForecastEntries) {

			commerceForecastEntryLocalService.deleteCommerceForecastEntry(
				commerceForecastEntry);
		}
	}

	@Override
	public CommerceForecastEntry deleteCommerceForecastEntry(
		CommerceForecastEntry commerceForecastEntry) {

		// Commerce forecast values

		commerceForecastValueLocalService.deleteCommerceForecastValues(
			commerceForecastEntry.getCommerceForecastEntryId());

		// Commerce forecast entry

		commerceForecastEntryPersistence.remove(commerceForecastEntry);

		return commerceForecastEntry;
	}

	@Override
	public CommerceForecastEntry deleteCommerceForecastEntry(
			long commerceForecastEntryId)
		throws PortalException {

		CommerceForecastEntry commerceForecastEntry =
			commerceForecastEntryPersistence.findByPrimaryKey(
				commerceForecastEntryId);

		return commerceForecastEntryLocalService.deleteCommerceForecastEntry(
			commerceForecastEntry);
	}

}