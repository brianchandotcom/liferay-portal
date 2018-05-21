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

import com.liferay.portal.reports.engine.ReportRequest;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * @author Gavin Wan
 * @author Brian Wing Shun Chan
 */
public interface ReportFillManager {

	public JasperPrint fillReport(
			JasperReport jasperReport, ReportRequest reportRequest)
		throws JRException;

}