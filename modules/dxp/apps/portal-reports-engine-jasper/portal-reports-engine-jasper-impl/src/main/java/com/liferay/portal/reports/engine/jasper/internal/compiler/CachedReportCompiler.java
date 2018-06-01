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