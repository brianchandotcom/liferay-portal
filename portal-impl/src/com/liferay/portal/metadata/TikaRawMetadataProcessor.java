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

package com.liferay.portal.metadata;

import com.liferay.dynamic.data.mapping.kernel.DDMForm;
import com.liferay.dynamic.data.mapping.kernel.DDMFormField;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.UnlocalizedValue;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessChannel;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.process.ProcessExecutor;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.metadata.RawMetadataProcessor;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PortalClassPathUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.ClimateForcast;
import org.apache.tika.metadata.CreativeCommons;
import org.apache.tika.metadata.DublinCore;
import org.apache.tika.metadata.Geographic;
import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.Message;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.Office;
import org.apache.tika.metadata.OfficeOpenXMLCore;
import org.apache.tika.metadata.Property;
import org.apache.tika.metadata.TIFF;
import org.apache.tika.metadata.TikaMetadataKeys;
import org.apache.tika.metadata.TikaMimeKeys;
import org.apache.tika.metadata.XMPDM;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Miguel Pastor
 * @author Alexander Chow
 * @author Shuyang Zhou
 */
public class TikaRawMetadataProcessor implements RawMetadataProcessor {

	public TikaRawMetadataProcessor() throws Exception {
		_parser = new AutoDetectParser(new TikaConfig());
	}

	@Override
	public Map<String, Field[]> getFields() {
		return _fields;
	}

	@Override
	public Map<String, DDMFormValues> getRawMetadataMap(
			String mimeType, InputStream inputStream)
		throws PortalException {

		Metadata metadata = _extractMetadata(mimeType, inputStream);

		return _createDDMFormValuesMap(metadata, getFields());
	}

	private static void _addFields(Class<?> clazz, List<Field> fields) {
		for (Field field : clazz.getFields()) {
			fields.add(field);
		}
	}

	private DDMForm _createDDMForm(Locale defaultLocale) {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(defaultLocale);
		ddmForm.setDefaultLocale(defaultLocale);

		return ddmForm;
	}

	private DDMFormValues _createDDMFormValues(
		Metadata metadata, Field[] fields) {

		Locale defaultLocale = LocaleUtil.getDefault();

		DDMForm ddmForm = _createDDMForm(defaultLocale);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(defaultLocale);
		ddmFormValues.setDefaultLocale(defaultLocale);

		for (Field field : fields) {
			Class<?> fieldClass = field.getDeclaringClass();

			String fieldClassName = fieldClass.getSimpleName();

			String name = StringBundler.concat(
				fieldClassName, StringPool.UNDERLINE, field.getName());

			String value = _getMetadataValue(metadata, field);

			if (value == null) {
				continue;
			}

			DDMFormField ddmFormField = _createTextDDMFormField(name);

			ddmForm.addDDMFormField(ddmFormField);

			DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

			ddmFormFieldValue.setName(name);
			ddmFormFieldValue.setValue(new UnlocalizedValue(value));

			ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
		}

		return ddmFormValues;
	}

	private Map<String, DDMFormValues> _createDDMFormValuesMap(
		Metadata metadata, Map<String, Field[]> fieldsMap) {

		Map<String, DDMFormValues> ddmFormValuesMap = new HashMap<>();

		if (metadata == null) {
			return ddmFormValuesMap;
		}

		for (Map.Entry<String, Field[]> entry : fieldsMap.entrySet()) {
			Field[] fields = entry.getValue();

			DDMFormValues ddmFormValues = _createDDMFormValues(
				metadata, fields);

			Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
				ddmFormValues.getDDMFormFieldValuesMap();

			Set<String> names = ddmFormFieldValuesMap.keySet();

			if (names.isEmpty()) {
				continue;
			}

			ddmFormValuesMap.put(entry.getKey(), ddmFormValues);
		}

		return ddmFormValuesMap;
	}

	private DDMFormField _createTextDDMFormField(String name) {
		DDMFormField ddmFormField = new DDMFormField(name, "text");

		ddmFormField.setDataType("string");

		return ddmFormField;
	}

	private Metadata _extractMetadata(String mimeType, File file) {
		Metadata metadata = new Metadata();

		boolean forkProcess = false;

		if (PropsValues.TEXT_EXTRACTION_FORK_PROCESS_ENABLED &&
			ArrayUtil.contains(
				PropsValues.TEXT_EXTRACTION_FORK_PROCESS_MIME_TYPES,
				mimeType)) {

			forkProcess = true;
		}

		if (forkProcess) {
			ExtractMetadataProcessCallable extractMetadataProcessCallable =
				new ExtractMetadataProcessCallable(file, metadata, _parser);

			try {
				ProcessChannel<Metadata> processChannel =
					_processExecutor.execute(
						PortalClassPathUtil.getPortalProcessConfig(),
						extractMetadataProcessCallable);

				Future<Metadata> future =
					processChannel.getProcessNoticeableFuture();

				return _postProcessMetadata(mimeType, future.get());
			}
			catch (Exception exception) {
				throw new SystemException(exception);
			}
		}

		try {
			return _postProcessMetadata(
				mimeType,
				ExtractMetadataProcessCallable._extractMetadata(
					file, metadata, _parser));
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
	}

	private Metadata _extractMetadata(
		String mimeType, InputStream inputStream) {

		File file = FileUtil.createTempFile();

		try {
			FileUtil.write(file, inputStream);

			return _extractMetadata(mimeType, file);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			file.delete();
		}
	}

	private Object _getFieldValue(Metadata metadata, Field field) {
		Object fieldValue = null;

		try {
			fieldValue = field.get(metadata);
		}
		catch (IllegalAccessException illegalAccessException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"The property " + field.getName() +
						" will not be added to the metatada set",
					illegalAccessException);
			}
		}

		return fieldValue;
	}

	private String _getMetadataValue(Metadata metadata, Field field) {
		Object fieldValue = _getFieldValue(metadata, field);

		if (fieldValue instanceof String) {
			return metadata.get((String)fieldValue);
		}

		Property property = (Property)fieldValue;

		return metadata.get(property.getName());
	}

	private Metadata _postProcessMetadata(String mimeType, Metadata metadata) {
		if (!mimeType.equals(ContentTypes.IMAGE_SVG_XML)) {
			return metadata;
		}

		String contentType = metadata.get("Content-Type");

		if (contentType.startsWith(ContentTypes.TEXT_PLAIN)) {
			metadata.set(
				"Content-Type",
				StringUtil.replace(
					mimeType, ContentTypes.TEXT_PLAIN,
					ContentTypes.IMAGE_SVG_XML));
		}

		return metadata;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TikaRawMetadataProcessor.class);

	private static final Map<String, Field[]> _fields = new HashMap<>();
	private static volatile ProcessExecutor _processExecutor =
		ServiceProxyFactory.newServiceTrackedInstance(
			ProcessExecutor.class, TikaRawMetadataProcessor.class,
			"_processExecutor", true);

	static {
		List<Field> fields = new ArrayList<>();

		_addFields(ClimateForcast.class, fields);
		_addFields(CreativeCommons.class, fields);
		_addFields(DublinCore.class, fields);
		_addFields(Geographic.class, fields);
		_addFields(HttpHeaders.class, fields);
		_addFields(Message.class, fields);
		_addFields(Office.class, fields);
		_addFields(OfficeOpenXMLCore.class, fields);
		_addFields(TIFF.class, fields);
		_addFields(TikaMetadataKeys.class, fields);
		_addFields(TikaMimeKeys.class, fields);
		_addFields(XMPDM.class, fields);

		_fields.put(TIKA_RAW_METADATA, fields.toArray(new Field[0]));
	}

	private final Parser _parser;

	private static class ExtractMetadataProcessCallable
		implements ProcessCallable<Metadata> {

		@Override
		public Metadata call() throws ProcessException {
			Logger logger = Logger.getLogger(
				"org.apache.tika.parser.SQLite3Parser");

			logger.setLevel(Level.SEVERE);

			logger = Logger.getLogger("org.apache.tika.parsers.PDFParser");

			logger.setLevel(Level.SEVERE);

			try {
				return _extractMetadata(_file, _metadata, _parser);
			}
			catch (IOException ioException) {
				throw new ProcessException(ioException);
			}
		}

		private static Metadata _extractMetadata(
				File file, Metadata metadata, Parser parser)
			throws IOException {

			if (metadata == null) {
				metadata = new Metadata();
			}

			if (file.length() == 0) {
				return metadata;
			}

			ParseContext parseContext = new ParseContext();

			parseContext.set(Parser.class, parser);

			try (InputStream inputStream = new FileInputStream(file)) {
				parser.parse(
					inputStream, new DefaultHandler(), metadata, parseContext);
			}
			catch (SAXException | TikaException exception) {
				throw new IOException(exception);
			}

			// Remove potential security risks

			metadata.remove(XMPDM.ABS_PEAK_AUDIO_FILE_PATH.getName());
			metadata.remove(XMPDM.RELATIVE_PEAK_AUDIO_FILE_PATH.getName());

			return metadata;
		}

		private ExtractMetadataProcessCallable(
			File file, Metadata metadata, Parser parser) {

			_file = file;
			_metadata = metadata;
			_parser = parser;
		}

		private static final long serialVersionUID = 1L;

		private final File _file;
		private final Metadata _metadata;
		private final Parser _parser;

	}

}