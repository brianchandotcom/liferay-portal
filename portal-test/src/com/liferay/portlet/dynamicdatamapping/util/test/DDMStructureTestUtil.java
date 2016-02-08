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

package com.liferay.portlet.dynamicdatamapping.util.test;

import com.liferay.dynamic.data.mapping.kernel.DDMForm;
import com.liferay.dynamic.data.mapping.kernel.DDMFormField;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManager;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManagerUtil;
import com.liferay.dynamic.data.mapping.kernel.LocalizedValue;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManager;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 * @author Rafael Praxedes
 */
public class DDMStructureTestUtil {

	public static DDMStructure addStructure(long groupId, String className)
		throws Exception {

		return addStructure(
			groupId, className, null, getSampleDDMForm(),
			LocaleUtil.getSiteDefault(),
			ServiceContextTestUtil.getServiceContext());
	}

	public static DDMStructure addStructure(
			long groupId, String className, String parentStructureId,
			DDMForm ddmForm, Locale defaultLocale,
			ServiceContext serviceContext)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(defaultLocale, "Test Structure");

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return DDMStructureManagerUtil.addStructure(
			TestPropsValues.getUserId(), groupId, parentStructureId,
			PortalUtil.getClassNameId(className), null, nameMap, null, ddmForm,
			StorageEngineManager.STORAGE_TYPE_DEFAULT,
			DDMStructureManager.STRUCTURE_TYPE_DEFAULT, serviceContext);
	}

	public static DDMStructure addStructure(String className) throws Exception {
		return addStructure(
			TestPropsValues.getGroupId(), className, null, getSampleDDMForm(),
			LocaleUtil.getSiteDefault(),
			ServiceContextTestUtil.getServiceContext());
	}

	public static DDMForm getSampleDDMForm() {
		return getSampleDDMForm("name");
	}

	public static DDMForm getSampleDDMForm(
		Locale[] availableLocales, Locale defaultLocale) {

		return getSampleDDMForm("name", availableLocales, defaultLocale);
	}

	public static DDMForm getSampleDDMForm(String name) {
		return getSampleDDMForm(
			name, new Locale[] {LocaleUtil.US}, LocaleUtil.US);
	}

	public static DDMForm getSampleDDMForm(
		String name, Locale[] availableLocales, Locale defaultLocale) {

		return getSampleDDMForm(
			name, "string", "text", true, "text", availableLocales,
			defaultLocale);
	}

	public static DDMForm getSampleDDMForm(
		String name, String dataType, String indexType, boolean repeatable,
		String type, Locale[] availableLocales, Locale defaultLocale) {

		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(SetUtil.fromArray(availableLocales));
		ddmForm.setDefaultLocale(defaultLocale);

		DDMFormField ddmFormField = new DDMFormField(name, type);

		ddmFormField.setDataType(dataType);
		ddmFormField.setIndexType(indexType);
		ddmFormField.setLocalizable(true);
		ddmFormField.setRepeatable(repeatable);

		LocalizedValue label = new LocalizedValue(defaultLocale);

		label.addString(defaultLocale, "Field");

		ddmFormField.setLabel(label);

		ddmForm.addDDMFormField(ddmFormField);

		return ddmForm;
	}

}