/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.jasper.internal.fill.manager;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.reports.engine.ReportRequest;

import java.sql.Connection;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gavin Wan
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "reportDataSourceType=portal", service = ReportFillManager.class
)
public class PortalReportFillManager extends BaseReportFillManager {

	@Override
	protected Connection getConnection(ReportRequest reportRequest)
		throws Exception {

		return DataAccess.getConnection();
	}

}