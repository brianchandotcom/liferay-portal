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

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordConstants;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSetConstants;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Daniel Kocsis
 */
public class DDLTestUtil {

	public static DDLRecord addRecord(long groupId, long recordSetId)
		throws Exception {

		DDLRecordSet recordSet = DDLRecordSetLocalServiceUtil.getRecordSet(
			recordSetId);

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		Set<String> fieldNames = ddmStructure.getFieldNames();

		Map<String, Serializable> fieldsMap =
			new HashMap<String, Serializable>();

		for (String fieldName : fieldNames) {
			if (ddmStructure.isFieldPrivate(fieldName)) {
				continue;
			}

			fieldsMap.put(fieldName, null);
		}

		return addRecord(groupId, recordSetId, fieldsMap);
	}

	public static DDLRecord addRecord(
			long groupId, long recordSetId, Map<String, Serializable> fieldsMap)
		throws Exception {

		long userId = TestPropsValues.getUserId();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		return DDLRecordLocalServiceUtil.addRecord(
			userId, groupId, recordSetId,
			DDLRecordConstants.DISPLAY_INDEX_DEFAULT, fieldsMap,
			serviceContext);
	}

	public static DDLRecordSet addRecordSet(long groupId, long structureId)
		throws Exception {

		String name = ServiceTestUtil.randomString();
		Locale defaultLocale = LocaleUtil.getSiteDefault();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		return addRecordSet(
			groupId, structureId, name, defaultLocale, serviceContext);
	}

	public static DDLRecordSet addRecordSet(
			long groupId, long structureId, String name, Locale defaultLocale,
			ServiceContext serviceContext)
		throws Exception {

		long userId = TestPropsValues.getUserId();
		String recordSetKey = null;

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(defaultLocale, name);

		Map<Locale, String> descriptionMap = null;
		int minDisplayRows = DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT;
		int scope = DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS;

		return DDLRecordSetLocalServiceUtil.addRecordSet(
			userId, groupId, structureId, recordSetKey, nameMap, descriptionMap,
			minDisplayRows, scope, serviceContext);
	}

}