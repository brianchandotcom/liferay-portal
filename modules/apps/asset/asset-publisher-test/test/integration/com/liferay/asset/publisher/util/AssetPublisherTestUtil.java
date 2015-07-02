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

package com.liferay.asset.publisher.util;

import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.StorageType;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class AssetPublisherTestUtil {

	public static DDMStructure addDDMStructure(long groupId, String className)
		throws Exception {

		return addDDMStructure(
			groupId, className, getSampleDDMForm(), LocaleUtil.US,
			ServiceContextTestUtil.getServiceContext());
	}

	public static DDMStructure addDDMStructure(
			long groupId, String className, DDMForm ddmForm,
			Locale defaultLocale, ServiceContext serviceContext)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(defaultLocale, "Test Structure");

		DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(ddmForm);

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return DDMStructureLocalServiceUtil.addStructure(
			TestPropsValues.getUserId(), groupId, 0,
			PortalUtil.getClassNameId(className), null, nameMap, null, ddmForm,
			ddmFormLayout, StorageType.JSON.toString(),
			DDMStructureConstants.TYPE_DEFAULT, serviceContext);
	}

	public static DDMForm getSampleDDMForm() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(LocaleUtil.US);
		ddmForm.setDefaultLocale(LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField("name", "text");

		ddmFormField.setDataType("string");
		ddmFormField.setIndexType("text");
		ddmFormField.setLocalizable(true);
		ddmFormField.setRepeatable(true);

		LocalizedValue label = new LocalizedValue(LocaleUtil.US);

		label.addString(LocaleUtil.US, "Field");

		ddmFormField.setLabel(label);

		ddmForm.addDDMFormField(ddmFormField);

		return ddmForm;
	}

}