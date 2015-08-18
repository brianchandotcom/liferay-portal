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

package com.liferay.dynamic.data.lists.web.template;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldValueRendererRegistryUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;
import java.util.Locale;

/**
 * @author Leonardo Barros
 */
public class DDLDisplayTemplateHelper {

	public static List<DDLRecord> getRecords(long recordSetId)
		throws PortalException {

		return DDLRecordSetLocalServiceUtil.getDDLRecordSet(
			recordSetId).getRecords();
	}

	public static String render(DDLRecord ddlRecord, String name, Locale locale)
		throws PortalException {

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddlRecord.getDDMFormFieldValues(name);

		if ((ddmFormFieldValues == null) || ddmFormFieldValues.isEmpty()) {
			return StringPool.BLANK;
		}

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			DDMFormFieldValueRendererRegistryUtil.getDDMFormFieldValueRenderer(
				ddmFormFieldValues.get(0).getType());

		if (ddmFormFieldValues.size() > 1) {
			return ddmFormFieldValueRenderer.render(ddmFormFieldValues, locale);
		}
		else {
			return ddmFormFieldValueRenderer.render(
				ddmFormFieldValues.get(0), locale);
		}
	}

}