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

package com.liferay.document.library.document.conversion.internal;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.DocumentFormatRegistry;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;

import com.liferay.document.library.document.conversion.internal.configuration.OpenOfficeConfiguration;
import com.liferay.document.library.kernel.document.conversion.DocumentConversion;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SortedArrayList;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Bruno Farache
 * @author Alexander Chow
 */
@Component(
	configurationPid = "com.liferay.document.library.document.conversion.internal.configuration.OpenOfficeConfiguration",
	service = DocumentConversion.class
)
public class DocumentConversionImpl implements DocumentConversion {

	@Override
	public File convert(
			String id, InputStream inputStream, String sourceExtension,
			String targetExtension)
		throws IOException {

		if (!isEnabled()) {
			return null;
		}

		sourceExtension = _fixExtension(sourceExtension);

		targetExtension = _fixExtension(targetExtension);

		_validate(targetExtension, id);

		String fileName = getFilePath(id, targetExtension);

		File file = new File(fileName);

		if (_openOfficeConfiguration.cacheEnabled() && file.exists()) {
			return file;
		}

		DocumentFormatRegistry documentFormatRegistry =
			new DefaultDocumentFormatRegistry();

		DocumentFormat inputDocumentFormat =
			documentFormatRegistry.getFormatByFileExtension(sourceExtension);
		DocumentFormat outputDocumentFormat =
			documentFormatRegistry.getFormatByFileExtension(targetExtension);

		if (inputDocumentFormat == null) {
			throw new SystemException(
				"Conversion is not supported from ." + sourceExtension);
		}
		else if (!inputDocumentFormat.isImportable()) {
			throw new SystemException(
				"Conversion is not supported from " +
					inputDocumentFormat.getName());
		}
		else if (outputDocumentFormat == null) {
			throw new SystemException(
				StringBundler.concat(
					"Conversion is not supported from ",
					inputDocumentFormat.getName(), " to .", targetExtension));
		}
		else if (!inputDocumentFormat.isExportableTo(outputDocumentFormat)) {
			throw new SystemException(
				StringBundler.concat(
					"Conversion is not supported from ",
					inputDocumentFormat.getName(), " to ",
					outputDocumentFormat.getName()));
		}

		if (sourceExtension.equals("html")) {
			DocumentHTMLProcessor documentHTMLProcessor =
				new DocumentHTMLProcessor();

			inputStream = documentHTMLProcessor.process(inputStream);
		}

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		DocumentConverter documentConverter = _getDocumentConverter();

		documentConverter.convert(
			inputStream, inputDocumentFormat, unsyncByteArrayOutputStream,
			outputDocumentFormat);

		FileUtil.write(
			file, unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
			unsyncByteArrayOutputStream.size());

		inputStream.close();

		return file;
	}

	@Override
	public void disconnect() {
		if (_openOfficeConnection != null) {
			_openOfficeConnection.disconnect();
		}
	}

	@Override
	public String[] getConversions(String extension) {
		extension = _fixExtension(extension);

		String[] conversions = ConversionsHolder.getConversions(extension);

		if (conversions == null) {
			conversions = _DEFAULT_CONVERSIONS;
		}
		else {
			if (ArrayUtil.contains(conversions, extension)) {
				List<String> conversionsList = new ArrayList<>();

				for (String conversion : conversions) {
					if (!conversion.equals(extension)) {
						conversionsList.add(conversion);
					}
				}

				conversions = conversionsList.toArray(new String[0]);
			}
		}

		return conversions;
	}

	@Override
	public String getFilePath(String id, String targetExtension) {
		StringBundler sb = new StringBundler(5);

		sb.append(SystemProperties.get(SystemProperties.TMP_DIR));
		sb.append("/liferay/document_conversion/");
		sb.append(id);
		sb.append(StringPool.PERIOD);
		sb.append(targetExtension);

		return sb.toString();
	}

	@Override
	public boolean isComparableVersion(String extension) {
		boolean enabled = false;

		String periodAndExtension = StringPool.PERIOD.concat(extension);

		for (String comparableFileExtension : _COMPARABLE_FILE_EXTENSIONS) {
			if (StringPool.STAR.equals(comparableFileExtension) ||
				periodAndExtension.equals(comparableFileExtension)) {

				enabled = true;

				break;
			}
		}

		if (!enabled) {
			return false;
		}

		if (extension.equals("css") || extension.equals("htm") ||
			extension.equals("html") || extension.equals("js") ||
			extension.equals("txt") || extension.equals("xml")) {

			return true;
		}

		try {
			if (isEnabled() && isConvertBeforeCompare(extension)) {
				return true;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	@Override
	public boolean isConvertBeforeCompare(String extension) {
		if (extension.equals("txt")) {
			return false;
		}

		for (String conversion : getConversions(extension)) {
			if (conversion.equals("txt")) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isEnabled() {
		return _openOfficeConfiguration.serverEnabled();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_openOfficeConfiguration = ConfigurableUtil.createConfigurable(
			OpenOfficeConfiguration.class, properties);
	}

	private String _fixExtension(String extension) {
		if (extension.equals("htm")) {
			extension = "html";
		}

		return extension;
	}

	private DocumentConverter _getDocumentConverter() {
		if ((_openOfficeConnection != null) && (_documentConverter != null)) {
			return _documentConverter;
		}

		String host = _openOfficeConfiguration.serverHost();
		int port = _openOfficeConfiguration.serverPort();

		if (_isRemoteOpenOfficeHost(host)) {
			_openOfficeConnection = new SocketOpenOfficeConnection(host, port);

			_documentConverter = new StreamOpenOfficeDocumentConverter(
				_openOfficeConnection);
		}
		else {
			_openOfficeConnection = new SocketOpenOfficeConnection(port);

			_documentConverter = new OpenOfficeDocumentConverter(
				_openOfficeConnection);
		}

		return _documentConverter;
	}

	private boolean _isRemoteOpenOfficeHost(String host) {
		if (Validator.isNotNull(host) && !host.equals(_LOCALHOST_IP) &&
			!host.startsWith(_LOCALHOST)) {

			return true;
		}

		return false;
	}

	private void _validate(String targetExtension, String id) {
		if (!Validator.isFileExtension(targetExtension)) {
			throw new SystemException("Invalid extension: " + targetExtension);
		}

		if (!Validator.isFileName(id)) {
			throw new SystemException("Invalid file name: " + id);
		}
	}

	private static final String[] _COMPARABLE_FILE_EXTENSIONS =
		PropsValues.DL_COMPARABLE_FILE_EXTENSIONS;

	private static final String[] _DEFAULT_CONVERSIONS = new String[0];

	private static final String _LOCALHOST = "localhost";

	private static final String _LOCALHOST_IP = "127.0.0.1";

	private static final Log _log = LogFactoryUtil.getLog(
		DocumentConversionImpl.class);

	private DocumentConverter _documentConverter;
	private volatile OpenOfficeConfiguration _openOfficeConfiguration;
	private OpenOfficeConnection _openOfficeConnection;

	private static class ConversionsHolder {

		public static String[] getConversions(String extension) {
			return _conversionsMap.get(extension);
		}

		private static void _populateConversionsMap(String documentFamily) {
			Filter filter = new Filter(documentFamily);

			DocumentFormatRegistry documentFormatRegistry =
				new DefaultDocumentFormatRegistry();

			String[] sourceExtensions = PropsUtil.getArray(
				PropsKeys.OPENOFFICE_CONVERSION_SOURCE_EXTENSIONS, filter);
			String[] targetExtensions = PropsUtil.getArray(
				PropsKeys.OPENOFFICE_CONVERSION_TARGET_EXTENSIONS, filter);

			for (String sourceExtension : sourceExtensions) {
				DocumentFormat sourceDocumentFormat =
					documentFormatRegistry.getFormatByFileExtension(
						sourceExtension);

				if (sourceDocumentFormat == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Invalid source extension " + sourceExtension);
					}

					continue;
				}

				List<String> conversions = new SortedArrayList<>();

				for (String targetExtension : targetExtensions) {
					DocumentFormat targetDocumentFormat =
						documentFormatRegistry.getFormatByFileExtension(
							targetExtension);

					if (targetDocumentFormat == null) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Invalid target extension " +
									targetDocumentFormat);
						}

						continue;
					}

					if (sourceDocumentFormat.isExportableTo(
							targetDocumentFormat)) {

						conversions.add(targetExtension);
					}
				}

				if (conversions.isEmpty()) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"There are no conversions supported from " +
								sourceExtension);
					}
				}
				else {
					if (_log.isInfoEnabled()) {
						_log.info(
							StringBundler.concat(
								"Conversions supported from ", sourceExtension,
								" to ", conversions));
					}

					_conversionsMap.put(
						sourceExtension, conversions.toArray(new String[0]));
				}
			}
		}

		private static final Map<String, String[]> _conversionsMap =
			new HashMap<>();

		static {
			_populateConversionsMap("drawing");
			_populateConversionsMap("presentation");
			_populateConversionsMap("spreadsheet");
			_populateConversionsMap("text");
		}

	}

}