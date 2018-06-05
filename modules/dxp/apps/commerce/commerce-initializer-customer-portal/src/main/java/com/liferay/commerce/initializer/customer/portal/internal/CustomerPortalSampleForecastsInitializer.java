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

package com.liferay.commerce.initializer.customer.portal.internal;

import com.liferay.commerce.forecast.model.CommerceForecastEntry;
import com.liferay.commerce.forecast.model.CommerceForecastEntryConstants;
import com.liferay.commerce.forecast.service.CommerceForecastEntryLocalService;
import com.liferay.commerce.forecast.service.CommerceForecastValueLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(service = CustomerPortalSampleForecastsInitializer.class)
public class CustomerPortalSampleForecastsInitializer
	extends BaseCustomerPortalSampleInitializer {

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	protected String getName() {
		return "forecasts";
	}

	@Override
	protected void importSample(
			long now, JSONObject jsonObject, Group group,
			long[] accountOrganizationIds, Map<String, Long> cpInstanceSKUsMap)
		throws PortalException {

		long time = now + jsonObject.getLong("time");
		int period = CommerceForecastEntryConstants.getLabelPeriod(
			jsonObject.getString("period"));
		int target = CommerceForecastEntryConstants.getLabelTarget(
			jsonObject.getString("target"));
		BigDecimal assertivity = BigDecimal.valueOf(Math.random());

		long customerId = 0;

		int jsonCustomerId = jsonObject.getInt("customerId");

		if (jsonCustomerId > 0) {
			if (jsonCustomerId <= accountOrganizationIds.length) {
				customerId = accountOrganizationIds[jsonCustomerId - 1];
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(
					"Ignoring imported forecast for customer " +
						jsonCustomerId);
			}
		}

		long cpInstanceId = 0;

		String sku = jsonObject.getString("sku");

		if (Validator.isNotNull(sku)) {
			if (cpInstanceSKUsMap.containsKey(sku)) {
				cpInstanceId = cpInstanceSKUsMap.get(sku);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn("Ignoring imported forecast for SKU " + sku);
			}
		}

		CommerceForecastEntry commerceForecastEntry =
			_commerceForecastEntryLocalService.addCommerceForecastEntry(
				group.getCompanyId(), group.getCreatorUserId(), time, period,
				target, customerId, cpInstanceId, assertivity);

		JSONArray valuesJSONArray = jsonObject.getJSONArray("values");

		for (int i = 0; i < valuesJSONArray.length(); i++) {
			JSONObject valueJSONObject = valuesJSONArray.getJSONObject(i);

			long valueTime = now + valueJSONObject.getLong("time");
			BigDecimal lowerValue = getBigDecimal(
				valueJSONObject, "lowerValue");
			BigDecimal value = getBigDecimal(valueJSONObject, "value");
			BigDecimal upperValue = getBigDecimal(
				valueJSONObject, "upperValue");

			_commerceForecastValueLocalService.addCommerceForecastValue(
				group.getCreatorUserId(),
				commerceForecastEntry.getCommerceForecastEntryId(), valueTime,
				lowerValue, value, upperValue);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomerPortalSampleForecastsInitializer.class);

	@Reference
	private CommerceForecastEntryLocalService
		_commerceForecastEntryLocalService;

	@Reference
	private CommerceForecastValueLocalService
		_commerceForecastValueLocalService;

}