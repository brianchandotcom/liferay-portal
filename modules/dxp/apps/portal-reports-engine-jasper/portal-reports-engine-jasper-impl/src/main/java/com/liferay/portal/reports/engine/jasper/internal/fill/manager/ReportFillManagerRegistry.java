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

package com.liferay.portal.reports.engine.jasper.internal.fill.manager;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.reports.engine.ReportDataSourceType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Gavin Wan
 * @author Brian Wing Shun Chan
 * @author Brian Greenwald
 */
@Component(immediate = true, service = ReportFillManagerRegistry.class)
public class ReportFillManagerRegistry {

	public ReportFillManager getReportFillManager(
		ReportDataSourceType reportDataSourceType) {

		ReportFillManager reportFillManager = _reportFillManagers.get(
			reportDataSourceType);

		if (_reportFillManagers == null) {
			throw new IllegalArgumentException(
				"No report fill manager found for " + reportDataSourceType);
		}

		return reportFillManager;
	}

	public void unsetReportFillManager(
		ReportFillManager reportFillManager, Map<String, Object> properties) {

		String reportDataSourceTypeString = GetterUtil.getString(
			properties.get("reportDataSourceType"));

		ReportDataSourceType reportDataSourceType = ReportDataSourceType.parse(
			reportDataSourceTypeString);

		if (reportDataSourceType == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No report data source type specified for " +
						reportFillManager);
			}

			return;
		}

		_reportFillManagers.remove(reportDataSourceType);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "unsetReportFillManager"
	)
	protected void setReportFillManager(
		ReportFillManager reportFillManager, Map<String, Object> properties) {

		String reportDataSourceTypeString = GetterUtil.getString(
			properties.get("reportDataSourceType"));

		ReportDataSourceType reportDataSourceType = ReportDataSourceType.parse(
			reportDataSourceTypeString);

		_reportFillManagers.put(reportDataSourceType, reportFillManager);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReportFillManagerRegistry.class);

	private Map<ReportDataSourceType, ReportFillManager> _reportFillManagers =
		new ConcurrentHashMap<>();

}