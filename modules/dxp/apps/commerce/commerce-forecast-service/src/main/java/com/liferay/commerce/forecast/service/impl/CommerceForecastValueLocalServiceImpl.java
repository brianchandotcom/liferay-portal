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
import com.liferay.commerce.forecast.model.CommerceForecastValue;
import com.liferay.commerce.forecast.service.base.CommerceForecastValueLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.math.BigDecimal;

import java.util.Date;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceForecastValueLocalServiceImpl
	extends CommerceForecastValueLocalServiceBaseImpl {

	@Override
	public CommerceForecastValue addCommerceForecastValue(
			long userId, long commerceForecastEntryId, Date date,
			BigDecimal lowerValue, BigDecimal value, BigDecimal upperValue)
		throws PortalException {

		CommerceForecastValue commerceForecastValue =
			commerceForecastValuePersistence.fetchByC_D(
				commerceForecastEntryId, date);

		if (commerceForecastValue == null) {
			CommerceForecastEntry commerceForecastEntry =
				commerceForecastEntryPersistence.findByPrimaryKey(
					commerceForecastEntryId);
			User user = userLocalService.getUser(userId);

			long commerceForecastValueId = counterLocalService.increment();

			commerceForecastValue = commerceForecastValuePersistence.create(
				commerceForecastValueId);

			commerceForecastValue.setCompanyId(
				commerceForecastEntry.getCompanyId());
			commerceForecastValue.setUserId(user.getUserId());
			commerceForecastValue.setUserName(user.getFullName());
			commerceForecastValue.setCommerceForecastEntryId(
				commerceForecastEntryId);
			commerceForecastValue.setDate(date);
		}

		commerceForecastValue.setLowerValue(lowerValue);
		commerceForecastValue.setValue(value);
		commerceForecastValue.setUpperValue(upperValue);

		return commerceForecastValuePersistence.update(commerceForecastValue);
	}

	@Override
	public void deleteCommerceForecastValues(long commerceForecastEntryId) {
		commerceForecastValuePersistence.removeByCommerceForecastEntryId(
			commerceForecastEntryId);
	}

}