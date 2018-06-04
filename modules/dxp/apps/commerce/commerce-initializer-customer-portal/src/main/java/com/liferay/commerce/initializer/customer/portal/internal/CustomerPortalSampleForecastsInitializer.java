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
import com.liferay.commerce.organization.constants.CommerceOrganizationConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(service = CustomerPortalSampleForecastsInitializer.class)
public class CustomerPortalSampleForecastsInitializer {

	public void initialize(long groupId) throws Exception {
		Group group = _groupLocalService.getGroup(groupId);

		long[] customerIds = _getCustomerIds(group);
		Map<String, Long> cpInstanceSKUsMap = _getCPInstanceSKUsMap(group);

		if (ArrayUtil.isEmpty(customerIds) || cpInstanceSKUsMap.isEmpty()) {
			if (_log.isInfoEnabled()) {
				_log.info("Skipping import on group " + group.getGroupId());
			}

			return;
		}

		long now = System.currentTimeMillis();

		long companyId = group.getCompanyId();
		long userId = group.getCreatorUserId();

		Bundle bundle = _bundleContext.getBundle();

		Enumeration<URL> enumeration = bundle.findEntries(
			CustomerPortalGroupInitializer.DEPENDENCY_PATH + "forecasts",
			"*.json", false);

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			String name = url.getFile();

			name = name.substring(name.lastIndexOf(CharPool.SLASH) + 1);

			try (LoggingTimer loggingTimer = new LoggingTimer(name)) {
				String json = StringUtil.read(url.openStream());

				JSONArray jsonArray = _jsonFactory.createJSONArray(json);

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);

					_importForecast(
						now, jsonObject, companyId, userId, customerIds,
						cpInstanceSKUsMap);
				}
			}
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private BigDecimal _getBigDecimal(JSONObject jsonObject, String key) {
		String value = jsonObject.getString(key);

		if (Validator.isNull(value)) {
			return null;
		}

		return new BigDecimal(value);
	}

	private Map<String, Long> _getCPInstanceSKUsMap(Group group)
		throws PortalException {

		final Map<String, Long> cpInstanceSKUsMap = new HashMap<>();

		ActionableDynamicQuery actionableDynamicQuery =
			_cpInstanceLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setGroupId(group.getGroupId());
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<CPInstance>() {

				public void performAction(CPInstance cpInstance)
					throws PortalException {

					cpInstanceSKUsMap.put(
						cpInstance.getSku(), cpInstance.getCPInstanceId());
				}

			});

		actionableDynamicQuery.performActions();

		return cpInstanceSKUsMap;
	}

	private long[] _getCustomerIds(Group group) throws PortalException {
		Organization organization = _organizationLocalService.getOrganization(
			group.getOrganizationId());

		List<Organization> descendantOrganizations =
			organization.getDescendants();

		List<Long> customerIds = new ArrayList<>(
			descendantOrganizations.size());

		for (Organization descendantOrganization : descendantOrganizations) {
			if (CommerceOrganizationConstants.TYPE_ACCOUNT.equals(
					descendantOrganization.getType())) {

				customerIds.add(descendantOrganization.getOrganizationId());
			}
		}

		return ArrayUtil.toLongArray(customerIds);
	}

	private void _importForecast(
			long now, JSONObject jsonObject, long companyId, long userId,
			long[] customerIds, Map<String, Long> cpInstanceSKUsMap)
		throws PortalException {

		long time = now + jsonObject.getLong("time");
		int period = CommerceForecastEntryConstants.getLabelPeriod(
			jsonObject.getString("period"));
		int target = CommerceForecastEntryConstants.getLabelTarget(
			jsonObject.getString("target"));
		long customerId = 0;
		long cpInstanceId = 0;
		BigDecimal assertivity = BigDecimal.valueOf(Math.random());

		int jsonCustomerId = jsonObject.getInt("customerId");

		if (jsonCustomerId > 0) {
			if (jsonCustomerId <= customerIds.length) {
				customerId = customerIds[jsonCustomerId - 1];
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(
					"Ignoring imported forecast for customer " +
						jsonCustomerId);
			}
		}

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
				companyId, userId, time, period, target, customerId,
				cpInstanceId, assertivity);

		JSONArray valuesJSONArray = jsonObject.getJSONArray("values");

		for (int i = 0; i < valuesJSONArray.length(); i++) {
			JSONObject valueJSONObject = valuesJSONArray.getJSONObject(i);

			long valueTime = now + valueJSONObject.getLong("time");
			BigDecimal lowerValue = _getBigDecimal(
				valueJSONObject, "lowerValue");
			BigDecimal value = _getBigDecimal(valueJSONObject, "value");
			BigDecimal upperValue = _getBigDecimal(
				valueJSONObject, "upperValue");

			_commerceForecastValueLocalService.addCommerceForecastValue(
				userId, commerceForecastEntry.getCommerceForecastEntryId(),
				valueTime, lowerValue, value, upperValue);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomerPortalSampleForecastsInitializer.class);

	private BundleContext _bundleContext;

	@Reference
	private CommerceForecastEntryLocalService
		_commerceForecastEntryLocalService;

	@Reference
	private CommerceForecastValueLocalService
		_commerceForecastValueLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private OrganizationLocalService _organizationLocalService;

}