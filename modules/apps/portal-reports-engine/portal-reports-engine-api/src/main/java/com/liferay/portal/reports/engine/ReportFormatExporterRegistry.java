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

package com.liferay.portal.reports.engine;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Michael C. Han
 */
public class ReportFormatExporterRegistry {

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

	protected void setReportFormatExporters(
		Map<String, ReportFormatExporter> reportFormatExporters) {

		for (Map.Entry<String, ReportFormatExporter> entry :
				reportFormatExporters.entrySet()) {

			ReportFormat reportFormat = ReportFormat.parse(entry.getKey());
			ReportFormatExporter reportFormatExporter = entry.getValue();

			_reportFormatExporters.put(reportFormat, reportFormatExporter);
		}
	}

	private final Map<ReportFormat, ReportFormatExporter>
		_reportFormatExporters = new ConcurrentHashMap<>();

}