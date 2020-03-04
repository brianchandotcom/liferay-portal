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

package com.liferay.dynamic.data.mapping.uad.test;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Gabriel Ibson
 */
public class DDMFormInstanceUADTestUtil {

	public static DDMFormInstance addDDMFormInstance(
			DDMFormInstanceLocalService ddmFormInstanceLocalService,
			long userId, Group group)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		DDMForm ddmForm = new DDMForm();

		ddmForm.setDefaultLocale(LocaleUtil.US);
		ddmForm.addAvailableLocale(LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField("fieldName", "text");

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues settingsDDMFormValues =
			DDMFormValuesTestUtil.createDDMFormValues(ddmForm);

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.US, RandomTestUtil.randomString()
		).build();

		Map<Locale, String> descriptionMap = HashMapBuilder.put(
			LocaleUtil.US, RandomTestUtil.randomString()
		).build();

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(DDMFormInstance.class), group);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			ddmForm, StorageType.JSON.toString());

		return ddmFormInstanceLocalService.addFormInstance(
			userId, group.getGroupId(), ddmStructure.getStructureId(), nameMap,
			descriptionMap, settingsDDMFormValues, serviceContext);
	}

}