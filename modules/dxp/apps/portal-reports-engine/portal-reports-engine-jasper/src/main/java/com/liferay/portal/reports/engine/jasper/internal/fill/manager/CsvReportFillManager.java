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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.reports.engine.ReportRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRCsvDataSource;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gavin Wan
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, property = "reportDataSourceType=csv",
	service = ReportFillManager.class
)
public class CsvReportFillManager extends BaseReportFillManager {

	@Override
	protected JRDataSource getJRDataSource(ReportRequest reportRequest)
		throws Exception {

		JRCsvDataSource jrCsvDataSource = null;

		String charsetName = getDataSourceCharSet(reportRequest);

		if (Validator.isNotNull(charsetName)) {
			jrCsvDataSource = new JRCsvDataSource(
				getDataSourceByteArrayInputStream(reportRequest), charsetName);
		}
		else {
			jrCsvDataSource = new JRCsvDataSource(
				getDataSourceByteArrayInputStream(reportRequest));
		}

		String[] dataSourceColumnNames = getDataSourceColumnNames(
			reportRequest);

		if (dataSourceColumnNames != null) {
			jrCsvDataSource.setColumnNames(dataSourceColumnNames);
		}
		else {
			jrCsvDataSource.setUseFirstRowAsHeader(true);
		}

		jrCsvDataSource.setRecordDelimiter(StringPool.NEW_LINE);

		return jrCsvDataSource;
	}

}