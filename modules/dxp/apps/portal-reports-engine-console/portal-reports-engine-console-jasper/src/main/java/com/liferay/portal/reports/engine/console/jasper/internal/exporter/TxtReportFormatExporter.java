/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.jasper.internal.exporter;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.reports.engine.ReportExportException;
import com.liferay.portal.reports.engine.ReportFormatExporter;
import com.liferay.portal.reports.engine.ReportRequest;
import com.liferay.portal.reports.engine.ReportResultContainer;

import java.util.Map;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
@Component(property = "reportFormat=txt", service = ReportFormatExporter.class)
public class TxtReportFormatExporter extends BaseReportFormatExporter {

	@Override
	public void format(
			Object report, ReportRequest reportRequest,
			ReportResultContainer reportResultContainer)
		throws ReportExportException {

		try {
			JRExporter jrExporter = getJRExporter();

			Map<String, String> reportParameters =
				reportRequest.getReportParameters();

			String characterEncoding = GetterUtil.getString(
				reportParameters.get(
					JRExporterParameter.CHARACTER_ENCODING.toString()),
				StringPool.UTF8);

			jrExporter.setParameter(
				JRTextExporterParameter.CHARACTER_ENCODING, characterEncoding);

			Float characterHeight = GetterUtil.getFloat(
				reportParameters.get(
					JRTextExporterParameter.CHARACTER_HEIGHT.toString()),
				11.9F);

			jrExporter.setParameter(
				JRTextExporterParameter.CHARACTER_HEIGHT, characterHeight);

			Float characterWidth = GetterUtil.getFloat(
				reportParameters.get(
					JRTextExporterParameter.CHARACTER_WIDTH.toString()),
				6.55F);

			jrExporter.setParameter(
				JRTextExporterParameter.CHARACTER_WIDTH, characterWidth);

			jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, report);
			jrExporter.setParameter(
				JRExporterParameter.OUTPUT_STREAM,
				reportResultContainer.getOutputStream());

			jrExporter.exportReport();
		}
		catch (Exception exception) {
			throw new ReportExportException(exception);
		}
	}

	@Override
	protected JRExporter getJRExporter() {
		return new JRTextExporter();
	}

}