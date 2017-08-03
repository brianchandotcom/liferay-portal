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