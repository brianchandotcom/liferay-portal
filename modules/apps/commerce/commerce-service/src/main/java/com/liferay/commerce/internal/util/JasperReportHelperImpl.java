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

package com.liferay.commerce.internal.util;

import com.liferay.commerce.util.JasperReportHelper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.util.Collection;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	enabled = false, immediate = true, service = JasperReportHelper.class
)
public class JasperReportHelperImpl implements JasperReportHelper {

	@Override
	public byte[] exportPdf(
		Collection<?> beanCollection, Map<String, Object> parameters) {

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		try {
			JasperPrint jasperPrint = _getJasperPrint(
				beanCollection, parameters);

			JasperExportManager.exportReportToPdfStream(
				jasperPrint, byteArrayOutputStream);
		}
		catch (JRException jrException) {
			_log.error(jrException, jrException);
		}

		return byteArrayOutputStream.toByteArray();
	}

	private JasperPrint _getJasperPrint(
		Collection<?> beanCollection, Map<String, Object> parameters) {

		JasperDesign jasperDesign;
		JasperReport jasperReport;
		JasperPrint jasperPrint = null;

		ClassLoader classLoader = JasperReportHelperImpl.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				"com/liferay/commerce/internal/template/CommerceOrder.jrxml")) {

			jasperDesign = JRXmlLoader.load(inputStream);

			jasperReport = JasperCompileManager.compileReport(jasperDesign);

			JRBeanCollectionDataSource jrBeanCollectionDataSource =
				new JRBeanCollectionDataSource(beanCollection);

			jasperPrint = JasperFillManager.fillReport(
				jasperReport, parameters, jrBeanCollectionDataSource);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return jasperPrint;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JasperReportHelperImpl.class);

}