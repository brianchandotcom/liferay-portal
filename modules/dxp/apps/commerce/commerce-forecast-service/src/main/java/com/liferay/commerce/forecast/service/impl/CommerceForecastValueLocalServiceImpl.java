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
import com.liferay.commerce.forecast.model.CommerceForecastValue;
import com.liferay.commerce.forecast.service.base.CommerceForecastValueLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.math.BigDecimal;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceForecastValueLocalServiceImpl
	extends CommerceForecastValueLocalServiceBaseImpl {

	@Override
	public CommerceForecastValue addCommerceForecastValue(
			long userId, long commerceForecastEntryId, long time,
			BigDecimal lowerValue, BigDecimal value, BigDecimal upperValue)
		throws PortalException {

		CommerceForecastValue commerceForecastValue =
			commerceForecastValuePersistence.fetchByC_T(
				commerceForecastEntryId, time);

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
			commerceForecastValue.setTime(time);
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