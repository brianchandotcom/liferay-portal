/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.internal.info.item;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItemRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.reflect.GenericUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author David Arques
 */
@Component(service = AnalyticsReportsInfoItemRegistry.class)
public class AnalyticsReportsInfoItemRegistryImpl
	implements AnalyticsReportsInfoItemRegistry {

	@Override
	public AnalyticsReportsInfoItem<?> getAnalyticsReportsInfoItem(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		return _serviceTrackerMap.getService(key);
	}

	@Override
	public List<AnalyticsReportsInfoItem<?>> getAnalyticsReportsInfoItems() {
		return new ArrayList<>(_serviceTrackerMap.values());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext,
			(Class<AnalyticsReportsInfoItem<?>>)
				(Class<?>)AnalyticsReportsInfoItem.class,
			null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(analyticsReportsInfo, emitter) -> emitter.emit(
					GenericUtil.getGenericClassName(analyticsReportsInfo))));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, AnalyticsReportsInfoItem<?>>
		_serviceTrackerMap;

}