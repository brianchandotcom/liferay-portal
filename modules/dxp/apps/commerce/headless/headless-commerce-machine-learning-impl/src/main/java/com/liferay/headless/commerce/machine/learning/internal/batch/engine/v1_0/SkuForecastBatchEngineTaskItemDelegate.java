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

package com.liferay.headless.commerce.machine.learning.internal.batch.engine.v1_0;

import com.liferay.batch.engine.BaseBatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.commerce.machine.learning.forecast.SkuCommerceMLForecast;
import com.liferay.commerce.machine.learning.forecast.SkuCommerceMLForecastManager;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.SkuForecast;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = "batch.engine.task.item.delegate.name=" + SkuForecastBatchEngineTaskItemDelegate.KEY,
	service = BatchEngineTaskItemDelegate.class
)
public class SkuForecastBatchEngineTaskItemDelegate
	extends BaseBatchEngineTaskItemDelegate<SkuForecast> {

	public static final String KEY = "sku-monthly-quantity-forecast";

	@Override
	public void createItem(
			SkuForecast skuForecast, Map<String, Serializable> parameters)
		throws Exception {

		SkuCommerceMLForecast skuCommerceMLForecast =
			_skuCommerceMLForecastManager.create();

		if (skuForecast.getActual() != null) {
			skuCommerceMLForecast.setActual(skuForecast.getActual());
		}

		skuCommerceMLForecast.setCompanyId(contextCompany.getCompanyId());

		if (skuForecast.getForecast() != null) {
			skuCommerceMLForecast.setForecast(skuForecast.getForecast());
			skuCommerceMLForecast.setForecastLowerBound(
				skuForecast.getForecastLowerBound());
			skuCommerceMLForecast.setForecastUpperBound(
				skuForecast.getForecastUpperBound());
		}

		skuCommerceMLForecast.setPeriod("month");
		skuCommerceMLForecast.setScope("sku");
		skuCommerceMLForecast.setSku(skuForecast.getSku());
		skuCommerceMLForecast.setTarget("quantity");
		skuCommerceMLForecast.setTimestamp(skuForecast.getTimestamp());

		_skuCommerceMLForecastManager.addSkuCommerceMLForecast(
			skuCommerceMLForecast);
	}

	@Override
	public Class<SkuForecast> getItemClass() {
		return SkuForecast.class;
	}

	@Override
	public Page<SkuForecast> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		return null;
	}

	@Reference
	private SkuCommerceMLForecastManager _skuCommerceMLForecastManager;

}