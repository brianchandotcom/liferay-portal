/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

import net.sf.jasperreports.engine.JRException;
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
@Component(service = CommerceReportExporter.class)
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

	@Override
	public boolean isValidJRXMLTemplate(InputStream inputStream) {
		try {
			JasperCompileManager.compileReport(inputStream);
		}
		catch (JRException jrException) {
			if (_log.isWarnEnabled()) {
				_log.warn(jrException);
			}

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceReportExporterImpl.class);

}