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

package com.liferay.portal.reports.engine.jasper.internal.exporter;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.reports.engine.ReportFormat;
import com.liferay.portal.reports.engine.ReportFormatExporter;
import com.liferay.portal.reports.engine.ReportFormatExporterRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Brian Greenwald
 */
@Component(immediate = true, service = ReportFormatExporterRegistry.class)
public class JasperReportFormatExporterRegistry
	extends ReportFormatExporterRegistry {

	@Override
	public ReportFormatExporter getReportFormatExporter(
		ReportFormat reportFormat) {

		ReportFormatExporter reportFormatExporter = _reportFormatExporters.get(
			reportFormat);

		if (reportFormatExporter == null) {
			throw new IllegalArgumentException(
				"No report format exporter found for " + reportFormat);
		}

		return reportFormatExporter;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "unsetReportFormatExporter"
	)
	protected void setReportFormatExporter(
		ReportFormatExporter reportFormatExporter,
		Map<String, Object> properties) {

		String reportFormatString = GetterUtil.getString(
			properties.get("reportFormat"));

		ReportFormat reportFormat = ReportFormat.parse(reportFormatString);

		_reportFormatExporters.put(reportFormat, reportFormatExporter);
	}

	protected void unsetReportFormatExporter(
		ReportFormatExporter reportFormatExporter,
		Map<String, Object> properties) {

		String reportFormatString = GetterUtil.getString(
			properties.get("reportFormat"));

		ReportFormat reportFormat = ReportFormat.parse(reportFormatString);

		if (Validator.isNull(reportFormat)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No report format specified for " + reportFormatExporter);
			}

			return;
		}

		_reportFormatExporters.remove(reportFormat);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JasperReportFormatExporterRegistry.class);

	private final Map<ReportFormat, ReportFormatExporter>
		_reportFormatExporters = new ConcurrentHashMap<>();

}