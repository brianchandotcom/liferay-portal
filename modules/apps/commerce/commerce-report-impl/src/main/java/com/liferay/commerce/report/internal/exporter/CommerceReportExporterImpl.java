/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.report.internal.exporter;

import com.liferay.commerce.report.exporter.CommerceReportExporter;
import com.liferay.commerce.report.internal.configuration.CommerceReportExporterConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import net.sf.jasperreports.compilers.ReportClassFilter;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.SimpleJasperReportsContext;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	configurationPid = "com.liferay.commerce.report.internal.configuration.CommerceReportExporterConfiguration",
	service = CommerceReportExporter.class
)
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

			JasperExportManager jasperExportManager =
				JasperExportManager.getInstance(_jasperReportsContext);
			JasperFillManager jasperFillManager = JasperFillManager.getInstance(
				_jasperReportsContext);

			jasperExportManager.exportToPdfStream(
				jasperFillManager.fill(
					_compile(inputStream), parameters,
					new JRBeanCollectionDataSource(beanCollection)),
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
			_compile(inputStream);
		}
		catch (JRException jrException) {
			if (_log.isWarnEnabled()) {
				_log.warn(jrException);
			}

			return false;
		}

		return true;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		CommerceReportExporterConfiguration
			commerceReportExporterConfiguration =
				ConfigurableUtil.createConfigurable(
					CommerceReportExporterConfiguration.class, properties);

		SimpleJasperReportsContext simpleJasperReportsContext =
			new SimpleJasperReportsContext(
				DefaultJasperReportsContext.getInstance());

		simpleJasperReportsContext.setProperty(
			ReportClassFilter.PROPERTY_CLASS_FILTER_ENABLED, StringPool.TRUE);
		simpleJasperReportsContext.setProperty(
			ReportClassFilter.PROPERTY_PREFIX_CLASS_WHITELIST + "liferay",
			StringUtil.merge(
				ArrayUtil.append(
					_CLASS_NAMES,
					GetterUtil.getStringValues(
						commerceReportExporterConfiguration.allowedClasses())),
				StringPool.COMMA));

		_jasperReportsContext = simpleJasperReportsContext;
	}

	private JasperReport _compile(InputStream inputStream) throws JRException {
		JasperDesign jasperDesign = JRXmlLoader.load(
			_jasperReportsContext, inputStream);

		if (!Objects.equals(
				jasperDesign.getLanguage(), JRReport.LANGUAGE_JAVA)) {

			throw new JRException(
				"Unsupported report language " + jasperDesign.getLanguage());
		}

		if (Validator.isNotNull(jasperDesign.getFormatFactoryClass())) {
			throw new JRException("Format factory classes are not supported");
		}

		for (JRDataset jrDataset :
				ArrayUtil.append(
					jasperDesign.getDatasets(),
					jasperDesign.getMainDataset())) {

			if (Validator.isNotNull(jrDataset.getScriptletClass()) ||
				ArrayUtil.isNotEmpty(jrDataset.getScriptlets())) {

				throw new JRException("Scriptlets are not supported");
			}
		}

		JasperCompileManager jasperCompileManager =
			JasperCompileManager.getInstance(_jasperReportsContext);

		return jasperCompileManager.compile(jasperDesign);
	}

	private static final String[] _CLASS_NAMES = {
		"com.liferay.commerce.currency.model.CommerceMoney",
		"com.liferay.portal.kernel.language.Language"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceReportExporterImpl.class);

	private volatile JasperReportsContext _jasperReportsContext;

}