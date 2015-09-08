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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.io.Serializable;

import java.util.Locale;

/**
 * @author Alexander Chow
 */
public class DDMIndexerUtil {

	public static void addAttributes(
		Document document, DDMStructure ddmStructure,
		DDMFormValues ddmFormValues) {

		getDDMIndexer().addAttributes(document, ddmStructure, ddmFormValues);
	}

	public static String encodeName(long ddmStructureId, String fieldName) {
		return getDDMIndexer().encodeName(ddmStructureId, fieldName);
	}

	public static String encodeName(
		long ddmStructureId, String fieldName, Locale locale) {

		return getDDMIndexer().encodeName(ddmStructureId, fieldName, locale);
	}

	public static String extractAttributes(
		DDMStructure ddmStructure, DDMFormValues ddmFormValues, Locale locale) {

		return getDDMIndexer().extractIndexableAttributes(
			ddmStructure, ddmFormValues, locale);
	}

	public static BooleanQuery getBooleanQuery(
		String ddmStructureFieldName, Serializable ddmStructureFieldValue,
		Locale locale) throws Exception {

		return getDDMIndexer().getBooleanQuery(
			ddmStructureFieldName, ddmStructureFieldValue, locale);
	}

	public static DDMIndexer getDDMIndexer() {
		PortalRuntimePermission.checkGetBeanProperty(DDMIndexerUtil.class);

		return _ddmIndexer;
	}

	public void setDDMIndexer(DDMIndexer ddmIndexer) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_ddmIndexer = ddmIndexer;
	}

	private static DDMIndexer _ddmIndexer;

}