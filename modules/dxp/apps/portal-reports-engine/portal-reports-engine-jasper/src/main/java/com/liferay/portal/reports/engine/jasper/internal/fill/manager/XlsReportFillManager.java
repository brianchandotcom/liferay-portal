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

import com.liferay.portal.reports.engine.ReportRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRXlsDataSource;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gavin Wan
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, property = "reportDataSourceType=xls",
	service = ReportFillManager.class
)
public class XlsReportFillManager extends BaseReportFillManager {

	@Override
	protected JRDataSource getJRDataSource(ReportRequest reportRequest)
		throws Exception {

		JRXlsDataSource jrXlsDataSource = new JRXlsDataSource(
			getDataSourceByteArrayInputStream(reportRequest));

		String[] dataSourceColumnNames = getDataSourceColumnNames(
			reportRequest);

		if (dataSourceColumnNames != null) {
			jrXlsDataSource.setColumnNames(dataSourceColumnNames);
		}
		else {
			jrXlsDataSource.setUseFirstRowAsHeader(true);
		}

		return jrXlsDataSource;
	}

}