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

package com.liferay.portal.reports.engine.jasper.internal.compiler;

import com.liferay.portal.kernel.util.LRUMap;
import com.liferay.portal.reports.engine.ReportDesignRetriever;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = ReportCompiler.class)
public class CachedReportCompiler implements ReportCompiler {

	@Override
	public JasperReport compile(ReportDesignRetriever reportDesignRetriever)
		throws JRException {

		return compile(reportDesignRetriever, false);
	}

	@Override
	public JasperReport compile(
			ReportDesignRetriever reportDesignRetriever, boolean force)
		throws JRException {

		String reportName = reportDesignRetriever.getReportName();

		Date modifiedDate = reportDesignRetriever.getModifiedDate();

		long timestamp = modifiedDate.getTime();

		CachedJasperReport cachedJasperReport = _cachedJasperReports.get(
			reportName);

		if ((cachedJasperReport == null) ||
			(cachedJasperReport.getTimestamp() != timestamp) || force) {

			cachedJasperReport = new CachedJasperReport(
				_reportCompiler.compile(reportDesignRetriever), timestamp);

			_cachedJasperReports.put(reportName, cachedJasperReport);
		}

		return cachedJasperReport.getJasperReport();
	}

	@Override
	public void flush() {
		_cachedJasperReports.clear();
	}

	private static final int _DEFAULT_MAX_SIZE = 25;

	private final Map<String, CachedJasperReport> _cachedJasperReports =
		Collections.synchronizedMap(
			new LRUMap<String, CachedJasperReport>(_DEFAULT_MAX_SIZE));

	@Reference
	private ReportCompiler _reportCompiler;

	private class CachedJasperReport {

		public CachedJasperReport(JasperReport jasperReport, long timestamp) {
			_jasperReport = jasperReport;
			_timestamp = timestamp;
		}

		public JasperReport getJasperReport() {
			return _jasperReport;
		}

		public long getTimestamp() {
			return _timestamp;
		}

		private final JasperReport _jasperReport;
		private final long _timestamp;

	}

}