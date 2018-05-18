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

package com.liferay.commerce.currency.internal.util;

import com.liferay.commerce.currency.util.ExchangeRateProvider;
import com.liferay.commerce.currency.util.ExchangeRateProviderRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marco Leo
 */
@Component(immediate = true)
public class ExchangeRateProviderRegistryImpl
	implements ExchangeRateProviderRegistry {

	@Override
	public ExchangeRateProvider getExchangeRateProvider(String key) {
		return _serviceTrackerMap.getService(key);
	}

	@Override
	public Iterable<String> getExchangeRateProviderKeys() {
		return _serviceTrackerMap.keySet();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ExchangeRateProvider.class,
			"commerce.exchange.provider.key");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, ExchangeRateProvider> _serviceTrackerMap;

}