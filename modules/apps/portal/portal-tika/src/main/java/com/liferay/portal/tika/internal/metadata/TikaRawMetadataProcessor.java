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

package com.liferay.portal.tika.internal.metadata;

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
import com.liferay.portal.kernel.metadata.RawMetadataProcessor;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tika.internal.util.ProcessConfigUtil;
import com.liferay.portal.tika.internal.util.TikaConfigUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Field;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Miguel Pastor
 * @author Alexander Chow
 * @author Shuyang Zhou
 */
@Component(service = RawMetadataProcessor.class)
public class TikaRawMetadataProcessor implements RawMetadataProcessor {

	public TikaRawMetadataProcessor() throws Exception {
		_parser = new AutoDetectParser(TikaConfigUtil.getTikaConfig());
	}

	@Override
	public Map<String, Set<String>> getFieldNames() {
		return Collections.singletonMap(TIKA_RAW_METADATA, _fields.keySet());
	}

	@Override
	public Map<String, DDMFormValues> getRawMetadataMap(
			String mimeType, InputStream inputStream)
		throws PortalException {

		Metadata metadata = _extractMetadata(mimeType, inputStream);

		return _createDDMFormValuesMap(metadata);
	}

	private static void _addFields(Class<?> clazz, Map<String, String> fields)
		throws IllegalAccessException {

		for (Field field : clazz.getFields()) {
			Object value = field.get(null);

			if (value instanceof Property) {
				Property property = (Property)value;

				value = property.getName();
			}

			fields.put(
				StringBundler.concat(
					clazz.getSimpleName(), StringPool.UNDERLINE,
					field.getName()),
				(String)value);
		}
	}

	private DDMForm _createDDMForm(Locale defaultLocale) {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(defaultLocale);
		ddmForm.setDefaultLocale(defaultLocale);

		return ddmForm;
	}

	private DDMFormValues _createDDMFormValues(Metadata metadata) {
		Locale defaultLocale = LocaleUtil.getDefault();

		DDMForm ddmForm = _createDDMForm(defaultLocale);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(defaultLocale);
		ddmFormValues.setDefaultLocale(defaultLocale);

		for (Map.Entry<String, String> entry : _fields.entrySet()) {
			String value = metadata.get(entry.getValue());

			if (value == null) {
				continue;
			}

			String name = entry.getKey();

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
		Metadata metadata) {

		Map<String, DDMFormValues> ddmFormValuesMap = new HashMap<>();

		if (metadata == null) {
			return ddmFormValuesMap;
		}

		DDMFormValues ddmFormValues = _createDDMFormValues(metadata);

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		Set<String> names = ddmFormFieldValuesMap.keySet();

		if (!names.isEmpty()) {
			ddmFormValuesMap.put(TIKA_RAW_METADATA, ddmFormValues);
		}

		return ddmFormValuesMap;
	}

	private DDMFormField _createTextDDMFormField(String name) {
		DDMFormField ddmFormField = new DDMFormField(name, "text");

		ddmFormField.setDataType("string");

		return ddmFormField;
	}

	private Metadata _extractMetadata(
		String mimeType, InputStream inputStream) {

		boolean forkProcess = false;

		if (PropsValues.TEXT_EXTRACTION_FORK_PROCESS_ENABLED &&
			ArrayUtil.contains(
				PropsValues.TEXT_EXTRACTION_FORK_PROCESS_MIME_TYPES,
				mimeType)) {

			forkProcess = true;
		}

		if (forkProcess) {
			File file = FileUtil.createTempFile();

			try {
				FileUtil.write(file, inputStream);

				if (file.length() == 0) {
					return null;
				}

				ExtractMetadataProcessCallable extractMetadataProcessCallable =
					new ExtractMetadataProcessCallable(file, _parser);

				ProcessChannel<Metadata> processChannel =
					_processExecutor.execute(
						ProcessConfigUtil.getProcessConfig(),
						extractMetadataProcessCallable);

				Future<Metadata> future =
					processChannel.getProcessNoticeableFuture();

				return _postProcessMetadata(mimeType, future.get());
			}
			catch (Exception exception) {
				throw new SystemException(exception);
			}
			finally {
				file.delete();
			}
		}

		try {
			return _postProcessMetadata(
				mimeType,
				ExtractMetadataProcessCallable._extractMetadata(
					inputStream, _parser));
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
	}

	private Metadata _postProcessMetadata(String mimeType, Metadata metadata) {
		if (!mimeType.equals(ContentTypes.IMAGE_SVG_XML) ||
			(metadata == null)) {

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

	private static final Map<String, String> _fields;

	static {
		Map<String, String> fields = new HashMap<>();

		try {
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
		}
		catch (IllegalAccessException illegalAccessException) {
			throw new ExceptionInInitializerError(illegalAccessException);
		}

		_fields = fields;
	}

	private final Parser _parser;

	@Reference
	private ProcessExecutor _processExecutor;

	private static class ExtractMetadataProcessCallable
		implements ProcessCallable<Metadata> {

		@Override
		public Metadata call() throws ProcessException {
			Logger logger = Logger.getLogger(
				"org.apache.tika.parser.SQLite3Parser");

			logger.setLevel(Level.SEVERE);

			logger = Logger.getLogger("org.apache.tika.parsers.PDFParser");

			logger.setLevel(Level.SEVERE);

			try (InputStream inputStream = new FileInputStream(_file)) {
				return _extractMetadata(inputStream, _parser);
			}
			catch (IOException ioException) {
				throw new ProcessException(ioException);
			}
		}

		private static Metadata _extractMetadata(
				InputStream inputStream, Parser parser)
			throws IOException {

			Metadata metadata = new Metadata();

			ParseContext parseContext = new ParseContext();

			parseContext.set(Parser.class, parser);

			try {
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

		private ExtractMetadataProcessCallable(File file, Parser parser) {
			_file = file;
			_parser = parser;
		}

		private static final long serialVersionUID = 1L;

		private final File _file;
		private final Parser _parser;

	}

}