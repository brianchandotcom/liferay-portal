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

package com.liferay.portal.reports.engine.jasper.internal.exporter;

import com.liferay.portal.reports.engine.ReportFormatExporter;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, property = "reportFormat=xml",
	service = ReportFormatExporter.class
)
public class XmlReportFormatExporter extends BaseReportFormatExporter {

	@Override
	protected JRExporter getJRExporter() {
		return new JRXmlExporter();
	}

}