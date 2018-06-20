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

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(service = CustomerPortalSampleOrdersInitializer.class)
public class CustomerPortalSampleOrdersInitializer
	extends BaseCustomerPortalSampleInitializer {

	@Override
	protected void cleanUp(Group group, long[] accountOrganizationIds)
		throws PortalException {

		for (long organizationId : accountOrganizationIds) {
			try (LoggingTimer loggingTimer = new LoggingTimer(
					String.valueOf(organizationId))) {

				Organization organization =
					_organizationLocalService.getOrganization(organizationId);

				_commerceOrderLocalService.deleteCommerceOrders(
					organization.getGroupId());
			}
		}
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	protected String getName() {
		return "orders";
	}

	@Override
	protected void importSample(
			long now, JSONObject jsonObject, Group group,
			long[] accountOrganizationIds, Map<String, Long> cpInstanceSKUsMap)
		throws PortalException {

		Organization organization = null;

		int jsonOrganizationId = jsonObject.getInt("organizationId");

		if (jsonOrganizationId <= accountOrganizationIds.length) {
			organization = _organizationLocalService.getOrganization(
				accountOrganizationIds[jsonOrganizationId - 1]);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Ignoring imported order for organization " +
						jsonOrganizationId);
			}

			return;
		}

		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(
				group.getGroupId());

		long time = jsonObject.getLong("time");
		BigDecimal total = getBigDecimal(jsonObject, "total");

		Date date = new Date(now + time);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCreateDate(date);
		serviceContext.setModifiedDate(date);
		serviceContext.setScopeGroupId(organization.getGroupId());
		serviceContext.setUserId(group.getCreatorUserId());

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.addCommerceOrder(
				group.getGroupId(), organization.getOrganizationId(), 0,
				commerceCurrency.getCommerceCurrencyId(), 0, 0, 0, 0, null,
				null, total, BigDecimal.ZERO, total,
				CommerceOrderConstants.PAYMENT_STATUS_PAID,
				CommerceOrderConstants.ORDER_STATUS_TRANSMITTED,
				serviceContext);

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject itemJSONObject = jsonArray.getJSONObject(i);

			_importOrderItem(
				itemJSONObject, commerceOrder, cpInstanceSKUsMap,
				serviceContext);
		}
	}

	@Override
	protected void reindex(Group group) throws PortalException {
		Indexer<CommerceOrder> indexer = _indexerRegistry.nullSafeGetIndexer(
			CommerceOrder.class);

		indexer.reindex(new String[] {String.valueOf(group.getCompanyId())});
	}

	private void _importOrderItem(
			JSONObject jsonObject, CommerceOrder commerceOrder,
			Map<String, Long> cpInstanceSKUsMap, ServiceContext serviceContext)
		throws PortalException {

		long cpInstanceId = 0;

		String sku = jsonObject.getString("sku");

		if (Validator.isNotNull(sku)) {
			if (cpInstanceSKUsMap.containsKey(sku)) {
				cpInstanceId = cpInstanceSKUsMap.get(sku);
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn("Ignoring imported order for SKU " + sku);
				}

				return;
			}
		}

		int quantity = jsonObject.getInt("quantity");
		BigDecimal price = getBigDecimal(jsonObject, "price");

		_commerceOrderItemLocalService.addCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), cpInstanceId, quantity,
			quantity, null, price, null, serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomerPortalSampleOrdersInitializer.class);

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private OrganizationLocalService _organizationLocalService;

}