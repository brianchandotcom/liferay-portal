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

package com.liferay.portlet.documentlibrary.util;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.DocumentFormatRegistry;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SortedArrayList;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bruno Farache
 * @author Alexander Chow
 */
public class DocumentConversionUtil {

	public static File convert(
			String id, InputStream inputStream, String sourceExtension,
			String targetExtension)
		throws IOException {

		return _instance._convert(
			id, inputStream, sourceExtension, targetExtension);
	}

	public static void disconnect() {
		_instance._disconnect();
	}

	public static String[] getConversions(String extension) {
		return _instance._getConversions(extension);
	}

	public static String getFilePath(String id, String targetExtension) {
		StringBundler sb = new StringBundler(5);

		sb.append(SystemProperties.get(SystemProperties.TMP_DIR));
		sb.append("/liferay/document_conversion/");
		sb.append(id);
		sb.append(StringPool.PERIOD);
		sb.append(targetExtension);

		return sb.toString();
	}

	public static boolean isComparableVersion(String extension) {
		boolean enabled = false;

		String periodAndExtension = StringPool.PERIOD.concat(extension);

		for (int i = 0; i < _COMPARABLE_FILE_EXTENSIONS.length; i++) {
			if (StringPool.STAR.equals(_COMPARABLE_FILE_EXTENSIONS[i]) ||
				periodAndExtension.equals(_COMPARABLE_FILE_EXTENSIONS[i])) {

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
			if (_log.isErrorEnabled()) {
				_log.error(e, e);
			}
		}

		return false;
	}

	public static boolean isConvertBeforeCompare(String extension) {
		if (extension.equals("txt")) {
			return false;
		}

		String[] conversions = getConversions(extension);

		for (int i = 0; i < conversions.length; i++) {
			if (conversions[i].equals("txt")) {
				return true;
			}
		}

		return false;
	}

	public static boolean isEnabled() {
		try {
			return PrefsPropsUtil.getBoolean(
				PropsKeys.OPENOFFICE_SERVER_ENABLED,
				PropsValues.OPENOFFICE_SERVER_ENABLED);
		}
		catch (Exception e) {
		}

		return false;
	}

	private DocumentConversionUtil() {
		_populateConversionsMap("drawing");
		_populateConversionsMap("presentation");
		_populateConversionsMap("spreadsheet");
		_populateConversionsMap("text");
	}

	private File _convert(
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

		if (PropsValues.OPENOFFICE_CACHE_ENABLED && file.exists()) {
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
				"Conversion is not supported from " +
					inputDocumentFormat.getName() + " to ." + targetExtension);
		}
		else if (!inputDocumentFormat.isExportableTo(outputDocumentFormat)) {
			throw new SystemException(
				"Conversion is not supported from " +
					inputDocumentFormat.getName() + " to " +
						outputDocumentFormat.getName());
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

		return file;
	}

	private void _disconnect() {
		if (_openOfficeConnection != null) {
			_openOfficeConnection.disconnect();
		}
	}

	private String _fixExtension(String extension) {
		if (extension.equals("htm")) {
			extension = "html";
		}

		return extension;
	}

	private String[] _getConversions(String extension) {
		extension = _fixExtension(extension);

		String[] conversions = _conversionsMap.get(extension);

		if (conversions == null) {
			conversions = _DEFAULT_CONVERSIONS;
		}
		else {
			if (ArrayUtil.contains(conversions, extension)) {
				List<String> conversionsList = new ArrayList<String>();

				for (int i = 0; i < conversions.length; i++) {
					String conversion = conversions[i];

					if (!conversion.equals(extension)) {
						conversionsList.add(conversion);
					}
				}

				conversions = conversionsList.toArray(
					new String[conversionsList.size()]);
			}
		}

		return conversions;
	}

	private DocumentConverter _getDocumentConverter() {
		if ((_openOfficeConnection != null) && (_documentConverter != null)) {
			return _documentConverter;
		}

		String host = PrefsPropsUtil.getString(
			PropsKeys.OPENOFFICE_SERVER_HOST);
		int port = PrefsPropsUtil.getInteger(
			PropsKeys.OPENOFFICE_SERVER_PORT,
			PropsValues.OPENOFFICE_SERVER_PORT);

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
		else {
			return false;
		}
	}

	private void _populateConversionsMap(String documentFamily) {
		Filter filter = new Filter(documentFamily);

		DocumentFormatRegistry documentFormatRegistry =
			new DefaultDocumentFormatRegistry();

		String[] sourceExtensions = PropsUtil.getArray(
			PropsKeys.OPENOFFICE_CONVERSION_SOURCE_EXTENSIONS, filter);
		String[] targetExtensions = PropsUtil.getArray(
			PropsKeys.OPENOFFICE_CONVERSION_TARGET_EXTENSIONS, filter);

		for (String sourceExtension : sourceExtensions) {
			List<String> conversions = new SortedArrayList<String>();

			DocumentFormat sourceDocumentFormat =
				documentFormatRegistry.getFormatByFileExtension(
					sourceExtension);

			if (sourceDocumentFormat == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("Invalid source extension " + sourceExtension);
				}

				continue;
			}

			for (String targetExtension : targetExtensions) {
				DocumentFormat targetDocumentFormat =
					documentFormatRegistry.getFormatByFileExtension(
						targetExtension);

				if (targetDocumentFormat == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Invalid target extension " + targetDocumentFormat);
					}

					continue;
				}

				if (sourceDocumentFormat.isExportableTo(targetDocumentFormat)) {
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
						"Conversions supported from " + sourceExtension +
							" to " + conversions);
				}

				_conversionsMap.put(
					sourceExtension,
					conversions.toArray(new String[conversions.size()]));
			}
		}
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

	private static Log _log = LogFactoryUtil.getLog(
		DocumentConversionUtil.class);

	private static DocumentConversionUtil _instance =
		new DocumentConversionUtil();

	private Map<String, String[]> _conversionsMap =
		new HashMap<String, String[]>();
	private DocumentConverter _documentConverter;
	private OpenOfficeConnection _openOfficeConnection;

}