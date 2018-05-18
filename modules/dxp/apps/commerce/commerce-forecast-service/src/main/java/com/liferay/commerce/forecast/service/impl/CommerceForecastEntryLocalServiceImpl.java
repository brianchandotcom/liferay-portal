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
			long companyId, long userId, Date date, int period, int target,
			long customerId, String sku, BigDecimal assertivity)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		CommerceForecastEntry commerceForecastEntry =
			commerceForecastEntryPersistence.fetchByC_P_T_C_S(
				companyId, period, target, customerId, sku);

		if (commerceForecastEntry == null) {
			long commerceForecastEntryId = counterLocalService.increment();

			commerceForecastEntry = commerceForecastEntryPersistence.create(
				commerceForecastEntryId);

			commerceForecastEntry.setCompanyId(companyId);
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