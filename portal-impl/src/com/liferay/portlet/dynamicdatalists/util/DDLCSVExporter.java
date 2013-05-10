/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.util;

import com.liferay.portal.kernel.util.CSVUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.FieldConstants;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Marcellus Tavares
 * @author Manuel de la Peña
 */
public class DDLCSVExporter extends BaseDDLExporter {

	@Override
	protected byte[] doExport(
			long recordSetId, int status, int start, int end,
			OrderByComparator orderByComparator)
		throws Exception {

		DDLRecordSet recordSet = DDLRecordSetServiceUtil.getRecordSet(
			recordSetId);

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		Map<String, Map<String, String>> fieldsMap = ddmStructure.getFieldsMap(
			LocaleUtil.toLanguageId(getLocale()));

		StringBundler sb = new StringBundler();

		for (Map<String, String> fieldMap : fieldsMap.values()) {
			String label = fieldMap.get(FieldConstants.LABEL);

			sb.append(label);
			sb.append(CharPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);
		sb.append(StringPool.NEW_LINE);

		List<DDLRecord> records = DDLRecordLocalServiceUtil.getRecords(
			recordSetId, status, start, end, orderByComparator);

		for (DDLRecord record : records) {
			DDLRecordVersion recordVersion = record.getRecordVersion();

			Fields fields = StorageEngineUtil.getFields(
				recordVersion.getDDMStorageId());

			for (Map<String, String> fieldMap : fieldsMap.values()) {
				String name = fieldMap.get(FieldConstants.NAME);
				String value = StringPool.BLANK;

				if (fields.contains(name)) {
					Field field = fields.get(name);

					value = field.getRenderedValue(getLocale());
				}

				sb.append(CSVUtil.encode(value));
				sb.append(CharPool.COMMA);
			}

			sb.setIndex(sb.index() - 1);
			sb.append(StringPool.NEW_LINE);
		}

		String csv = sb.toString();

		return csv.getBytes();
	}

}