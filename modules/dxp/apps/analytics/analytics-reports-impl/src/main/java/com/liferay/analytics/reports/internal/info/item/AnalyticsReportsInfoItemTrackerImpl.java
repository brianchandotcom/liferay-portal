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

package com.liferay.analytics.reports.internal.info.item;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItemTracker;
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
@Component(service = AnalyticsReportsInfoItemTracker.class)
public class AnalyticsReportsInfoItemTrackerImpl
	implements AnalyticsReportsInfoItemTracker {

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