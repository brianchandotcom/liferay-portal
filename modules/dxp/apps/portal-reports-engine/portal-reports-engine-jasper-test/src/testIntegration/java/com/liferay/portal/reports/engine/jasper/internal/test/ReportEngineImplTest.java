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

package com.liferay.portal.reports.engine.jasper.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.reports.engine.ByteArrayReportResultContainer;
import com.liferay.portal.reports.engine.MemoryReportDesignRetriever;
import com.liferay.portal.reports.engine.ReportDataSourceType;
import com.liferay.portal.reports.engine.ReportDesignRetriever;
import com.liferay.portal.reports.engine.ReportEngine;
import com.liferay.portal.reports.engine.ReportFormat;
import com.liferay.portal.reports.engine.ReportRequest;
import com.liferay.portal.reports.engine.ReportRequestContext;
import com.liferay.portal.reports.engine.ReportResultContainer;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

import java.util.Date;
import java.util.HashMap;

import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Michael C. Han
 * @author Brian Greenwald
 * @author Prathima Shreenath
 */
@RunWith(Arquillian.class)
@Sync
public class ReportEngineImplTest extends TestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		_bundleContext = bundle.getBundleContext();

		int counter = 0;

		do {
			_serviceReference = _bundleContext.getServiceReference(
				ReportEngine.class);

			if (_serviceReference == null) {
				Thread.sleep(500);
			}

			counter++;

			if (counter >= 5) {
				throw new IllegalStateException(
					"Unable to get reference to a report engine");
			}
		}
		while (_serviceReference == null);

		_reportEngine = _bundleContext.getService(_serviceReference);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		_bundleContext.ungetService(_serviceReference);

		_bundleContext = null;
	}

	@Test
	public void testCompileCsv() throws Exception {
		compile(
			ReportDataSourceType.CSV, "CsvDataSource.txt",
			"CsvDataSourceReport.jrxml", ReportFormat.CSV);
	}

	@Test
	public void testCompileXls() throws Exception {
		compile(
			ReportDataSourceType.XLS, "XlsDataSource.data.xls",
			"XlsDataSourceReport.jrxml", ReportFormat.CSV);
	}

	@Test
	public void testCompileXml() throws Exception {
		compile(
			ReportDataSourceType.XML, "northwind.xml", "OrdersReport.jrxml",
			ReportFormat.CSV);
	}

	@Test
	public void testExportCsv() throws Exception {
		export(ReportFormat.CSV);
	}

	@Test
	public void testExportPdf() throws Exception {
		export(ReportFormat.PDF);
	}

	@Test
	public void testExportRtf() throws Exception {
		export(ReportFormat.RTF);
	}

	@Test
	public void testExportTxt() throws Exception {
		export(ReportFormat.TXT);
	}

	@Test
	public void testExportXls() throws Exception {
		export(ReportFormat.XLS);
	}

	@Test
	public void testExportXml() throws Exception {
		export(ReportFormat.XML);
	}

	protected ReportRequest compile(
			ReportDataSourceType reportDataSourceType,
			String dataSourceFileName, String dataSourceReportFileName,
			ReportFormat reportFormat)
		throws Exception {

		ReportRequest reportRequest = getReportRequest(
			reportDataSourceType, dataSourceFileName, dataSourceReportFileName,
			reportFormat);

		_reportEngine.compile(reportRequest);

		return reportRequest;
	}

	protected void export(ReportFormat reportFormat) throws Exception {
		ReportRequest reportRequest = compile(
			ReportDataSourceType.CSV, "CsvDataSource.txt",
			"CsvDataSourceReport.jrxml", ReportFormat.CSV);

		ReportResultContainer reportResultContainer =
			new ByteArrayReportResultContainer();

		_reportEngine.execute(reportRequest, reportResultContainer);

		Assert.assertFalse(reportResultContainer.hasError());
		Assert.assertNotNull(reportResultContainer.getResults());
	}

	protected ReportRequest getReportRequest(
			ReportDataSourceType reportDataSourceType,
			String dataSourceFileName, String dataSourceReportFileName,
			ReportFormat reportFormat)
		throws Exception {

		ReportRequestContext reportRequestContext = new ReportRequestContext(
			reportDataSourceType);

		Class<?> reportEngineImplTestClass = getClass();

		ClassLoader classLoader = reportEngineImplTestClass.getClassLoader();

		InputStream dataSourceInputStream = classLoader.getResourceAsStream(
			dataSourceFileName);

		reportRequestContext.setAttribute(
			ReportRequestContext.DATA_SOURCE_BYTE_ARRAY,
			IOUtils.toByteArray(dataSourceInputStream));

		reportRequestContext.setAttribute(
			ReportRequestContext.DATA_SOURCE_COLUMN_NAMES,
			"city,id,name,address,state");

		InputStream dataSourceReportInputStream =
			classLoader.getResourceAsStream(dataSourceReportFileName);

		byte[] reportByteArray = IOUtils.toByteArray(
			dataSourceReportInputStream);

		ReportDesignRetriever reportDesignRetriever =
			new MemoryReportDesignRetriever(
				"test", new Date(), reportByteArray);

		ReportRequest reportRequest = new ReportRequest(
			reportRequestContext, reportDesignRetriever,
			new HashMap<String, String>(), reportFormat.getValue());

		return reportRequest;
	}

	private BundleContext _bundleContext;
	private ReportEngine _reportEngine;
	private ServiceReference<ReportEngine> _serviceReference;

}