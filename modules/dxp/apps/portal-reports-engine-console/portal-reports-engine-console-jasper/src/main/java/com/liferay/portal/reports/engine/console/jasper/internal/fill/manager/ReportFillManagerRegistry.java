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

package com.liferay.portal.reports.engine.console.jasper.internal.fill.manager;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.reports.engine.ReportDataSourceType;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Gavin Wan
 * @author Brian Wing Shun Chan
 * @author Brian Greenwald
 */
@Component(service = ReportFillManagerRegistry.class)
public class ReportFillManagerRegistry {

	public ReportFillManager getReportFillManager(
		ReportDataSourceType reportDataSourceType) {

		ReportFillManager reportFillManager = _serviceTrackerMap.getService(
			reportDataSourceType);

		if (reportFillManager == null) {
			throw new IllegalArgumentException(
				"No report fill manager found for " + reportDataSourceType);
		}

		return reportFillManager;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ReportFillManager.class, null,
			(serviceReference, emitter) -> {
				String reportDataSourceTypeString = GetterUtil.getString(
					serviceReference.getProperty("reportDataSourceType"));

				emitter.emit(
					ReportDataSourceType.parse(reportDataSourceTypeString));
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<ReportDataSourceType, ReportFillManager>
		_serviceTrackerMap;

}