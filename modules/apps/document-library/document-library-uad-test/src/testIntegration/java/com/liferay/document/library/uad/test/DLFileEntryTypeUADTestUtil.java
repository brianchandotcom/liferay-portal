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

package com.liferay.document.library.uad.test;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.kernel.DDMForm;
import com.liferay.dynamic.data.mapping.kernel.DDMFormField;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManager;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManagerUtil;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManager;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

/**
 * @author William Newbury
 */
public class DLFileEntryTypeUADTestUtil {

	public static DLFileEntryType addDLFileEntryType(
			DLFileEntryTypeLocalService dlFileEntryTypeLocalService,
			Portal portal, long userId, long groupId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		DDMForm ddmForm = new DDMForm();

		ddmForm.setDefaultLocale(LocaleUtil.US);
		ddmForm.addAvailableLocale(LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField("fieldName", "text");

		ddmForm.addDDMFormField(ddmFormField);

		DDMStructure ddmStructure = DDMStructureManagerUtil.addStructure(
			TestPropsValues.getUserId(), groupId, null,
			portal.getClassNameId(
				"com.liferay.dynamic.data.lists.model.DDLRecordSet"),
			RandomTestUtil.randomString(),
			HashMapBuilder.put(
				LocaleUtil.US, "Test Structure Name"
			).build(),
			HashMapBuilder.put(
				LocaleUtil.US, "Test Structure Description"
			).build(),
			ddmForm, StorageEngineManager.STORAGE_TYPE_DEFAULT,
			DDMStructureManager.STRUCTURE_TYPE_DEFAULT, serviceContext);

		return dlFileEntryTypeLocalService.addFileEntryType(
			userId, groupId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			new long[] {ddmStructure.getStructureId()}, serviceContext);
	}

	public static void cleanUpDependencies(
			DLFileEntryTypeLocalService dlFileEntryTypeLocalService,
			List<DLFileEntryType> dlFileEntryTypes)
		throws Exception {

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			dlFileEntryTypeLocalService.deleteFileEntryType(dlFileEntryType);

			for (DDMStructure ddmStructure :
					dlFileEntryType.getDDMStructures()) {

				DDMStructureManagerUtil.deleteStructure(
					ddmStructure.getStructureId());
			}
		}
	}

}