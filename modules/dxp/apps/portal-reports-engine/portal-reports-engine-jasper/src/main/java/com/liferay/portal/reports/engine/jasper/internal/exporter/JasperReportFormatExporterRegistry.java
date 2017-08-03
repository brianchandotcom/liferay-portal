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