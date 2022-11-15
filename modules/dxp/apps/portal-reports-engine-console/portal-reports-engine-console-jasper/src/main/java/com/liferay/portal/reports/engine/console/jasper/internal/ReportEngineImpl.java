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

package com.liferay.portal.reports.engine.console.jasper.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.reports.engine.ReportEngine;
import com.liferay.portal.reports.engine.ReportFormat;
import com.liferay.portal.reports.engine.ReportFormatExporter;
import com.liferay.portal.reports.engine.ReportGenerationException;
import com.liferay.portal.reports.engine.ReportRequest;
import com.liferay.portal.reports.engine.ReportRequestContext;
import com.liferay.portal.reports.engine.ReportResultContainer;
import com.liferay.portal.reports.engine.console.jasper.internal.compiler.ReportCompiler;
import com.liferay.portal.reports.engine.console.jasper.internal.fill.manager.ReportFillManager;
import com.liferay.portal.reports.engine.console.jasper.internal.fill.manager.ReportFillManagerRegistry;

import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Brian Greenwald
 */
@Component(service = ReportEngine.class)
public class ReportEngineImpl implements ReportEngine {

	@Override
	public void compile(ReportRequest reportRequest)
		throws ReportGenerationException {

		try {
			_reportCompiler.compile(
				reportRequest.getReportDesignRetriever(), true);
		}
		catch (Exception exception) {
			throw new ReportGenerationException(
				"Unable to compile report: " +
					StackTraceUtil.getStackTrace(exception));
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void execute(
			ReportRequest reportRequest, ReportResultContainer resultContainer)
		throws ReportGenerationException {

		try {
			JasperReport jasperReport = _reportCompiler.compile(
				reportRequest.getReportDesignRetriever());

			ReportRequestContext reportRequestContext =
				reportRequest.getReportRequestContext();

			ReportFillManager reportFillManager =
				_reportFillManagerRegistry.getReportFillManager(
					reportRequestContext.getReportDataSourceType());

			JasperPrint jasperPrint = reportFillManager.fillReport(
				jasperReport, reportRequest);

			ReportFormatExporter reportFormatExporter =
				_reportFormatExporterServiceTrackerMap.getService(
					reportRequest.getReportFormat());

			if (reportFormatExporter == null) {
				throw new IllegalArgumentException(
					"No report format exporter found for " +
						reportRequest.getReportFormat());
			}

			reportFormatExporter.format(
				jasperPrint, reportRequest, resultContainer);
		}
		catch (Exception exception) {
			throw new ReportGenerationException(
				"Unable to execute report: " +
					StackTraceUtil.getStackTrace(exception));
		}
	}

	@Override
	public Map<String, String> getEngineParameters() {
		return _engineParameters;
	}

	@Override
	public void init(ServletContext servletContext) {
	}

	@Override
	public void setEngineParameters(Map<String, String> engineParameters) {
		_engineParameters = engineParameters;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_reportFormatExporterServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ReportFormatExporter.class, null,
				(serviceReference, emitter) -> {
					String reportFormatString = GetterUtil.getString(
						serviceReference.getProperty("reportFormat"));

					emitter.emit(ReportFormat.parse(reportFormatString));
				});
	}

	@Deactivate
	protected void deactivate() {
		_reportFormatExporterServiceTrackerMap.close();
	}

	private Map<String, String> _engineParameters;

	@Reference(
		cardinality = ReferenceCardinality.MANDATORY,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile ReportCompiler _reportCompiler;

	@Reference
	private ReportFillManagerRegistry _reportFillManagerRegistry;

	private ServiceTrackerMap<ReportFormat, ReportFormatExporter>
		_reportFormatExporterServiceTrackerMap;

}