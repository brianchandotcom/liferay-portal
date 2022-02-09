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

package com.liferay.commerce.report.internal.exporter;

import com.liferay.commerce.report.exporter.CommerceReportExporter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(enabled = false, service = CommerceReportExporter.class)
public class CommerceReportExporterImpl implements CommerceReportExporter {

	@Override
	public byte[] export(
			Collection<?> beanCollection, FileEntry fileEntry,
			Map<String, Object> parameters)
		throws IOException {

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		Class<?> clazz = getClass();

		InputStream inputStream = null;

		try {
			if (fileEntry == null) {
				inputStream = clazz.getResourceAsStream(
					"dependencies/commerce_order.jrxml");
			}
			else {
				inputStream = fileEntry.getContentStream();
			}

			JasperExportManager.exportReportToPdfStream(
				JasperFillManager.fillReport(
					JasperCompileManager.compileReport(
						JRXmlLoader.load(inputStream)),
					parameters, new JRBeanCollectionDataSource(beanCollection)),
				byteArrayOutputStream);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
		finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}

		return byteArrayOutputStream.toByteArray();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceReportExporterImpl.class);

}