/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.metadata.RawMetadataProcessor;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.File;
import java.io.InputStream;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tika.metadata.ClimateForcast;
import org.apache.tika.metadata.CreativeCommons;
import org.apache.tika.metadata.DublinCore;
import org.apache.tika.metadata.Geographic;
import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.MSOffice;
import org.apache.tika.metadata.Message;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.Property;
import org.apache.tika.metadata.TIFF;
import org.apache.tika.metadata.TikaMetadataKeys;
import org.apache.tika.metadata.TikaMimeKeys;

/**
 * @author Alexander Chow
 */
public abstract class BaseRawMetadataProcessor implements RawMetadataProcessor {

	public Map<String, Field[]> getFields() {
		return _fields;
	}

	public Map<String, Fields> getRawMetadataMap(
			String extension, String mimeType, File file)
		throws PortalException, SystemException {

		Metadata metadata = extractMetadata(extension, mimeType, file);

		return createDDMFieldsMap(metadata, getFields());
	}

	public Map<String, Fields> getRawMetadataMap(
			String extension, String mimeType, InputStream inputStream)
		throws PortalException, SystemException {

		Metadata metadata = extractMetadata(extension, mimeType, inputStream);

		return createDDMFieldsMap(metadata, getFields());
	}

	protected Fields createDDMFields(Metadata metadata, Field[] fields) {
		Fields ddmFields = new Fields();

		for (Field field : fields) {
			String value = getMetadataValue(metadata, field);

			if (value == null) {
				continue;
			}

			String fieldClassname = field.getDeclaringClass().getSimpleName();

			StringBundler sb = new StringBundler(3);

			sb.append(fieldClassname);
			sb.append(StringPool.UNDERLINE);
			sb.append(field.getName());

			com.liferay.portlet.dynamicdatamapping.storage.Field ddmField =
				new com.liferay.portlet.dynamicdatamapping.storage.Field(
					sb.toString(), value);

			ddmFields.put(ddmField);
		}

		return ddmFields;
	}

	protected Map<String, Fields> createDDMFieldsMap(
		Metadata metadata, Map<String, Field[]> fieldsMap) {

		Map<String, Fields> ddmFieldsMap = new HashMap<String, Fields>();

		if (metadata == null) {
			return ddmFieldsMap;
		}

		for (String key : fieldsMap.keySet()) {
			Field[] fields = fieldsMap.get(key);

			Fields ddmFields = createDDMFields(metadata, fields);

			Set<String> names = ddmFields.getNames();

			if (names.isEmpty()) {
				continue;
			}

			ddmFieldsMap.put(key, ddmFields);
		}

		return ddmFieldsMap;
	}

	protected abstract Metadata extractMetadata(
			String extension, String mimeType, File file)
		throws PortalException, SystemException;

	protected abstract Metadata extractMetadata(
			String extension, String mimeType, InputStream inputStream)
		throws PortalException, SystemException;

	protected Object getFieldValue(Metadata metadata, Field field) {
		Object fieldValue = null;

		try {
			fieldValue = field.get(metadata);
		}
		catch (IllegalAccessException iae) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"The property " + field.getName() +
						" will not be added to the metatada set");
			}
		}

		return fieldValue;
	}

	protected String getMetadataValue(Metadata metadata, Field field) {
		Object fieldValue = getFieldValue(metadata, field);

		if (fieldValue instanceof String) {
			return metadata.get((String)fieldValue);
		}
		else {
			Property property = (Property)fieldValue;

			return metadata.get(property.getName());
		}
	}

	private static void addFields(Class<?> clazz, List<Field> fieldsList) {
		Field[] fields = clazz.getFields();

		for (int i = 0; i < fields.length; i++) {
			fieldsList.add(fields[i]);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		BaseRawMetadataProcessor.class);

	private static Map<String, Field[]> _fields =
		new HashMap<String, Field[]>();

	private static List<Field> _fieldList = new ArrayList<Field>();

	private final static String _TIKA_RAW_METADATA = "TikaRawMetadata";

	static {
		addFields(ClimateForcast.class, _fieldList);
		addFields(CreativeCommons.class, _fieldList);
		addFields(DublinCore.class, _fieldList);
		addFields(Geographic.class, _fieldList);
		addFields(HttpHeaders.class, _fieldList);
		addFields(Message.class, _fieldList);
		addFields(MSOffice.class, _fieldList);
		addFields(TIFF.class, _fieldList);
		addFields(TikaMetadataKeys.class, _fieldList);
		addFields(TikaMimeKeys.class, _fieldList);
		addFields(XMPDM.class, _fieldList);

		_fields.put(_TIKA_RAW_METADATA,
			_fieldList.toArray(new Field[_fieldList.size()]));
	}

}