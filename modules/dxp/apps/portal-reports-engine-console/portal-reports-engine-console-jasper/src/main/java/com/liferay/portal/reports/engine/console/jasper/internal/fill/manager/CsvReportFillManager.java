/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.jasper.internal.fill.manager;

import com.liferay.petra.string.StringPool;
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
	property = "reportDataSourceType=csv", service = ReportFillManager.class
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