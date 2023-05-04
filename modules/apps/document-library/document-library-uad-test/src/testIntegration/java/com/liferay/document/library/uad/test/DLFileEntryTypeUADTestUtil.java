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
import com.liferay.document.library.util.DLFileEntryTypeUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;

/**
 * @author William Newbury
 */
public class DLFileEntryTypeUADTestUtil {

	public static DLFileEntryType addDLFileEntryType(
			DLFileEntryTypeLocalService dlFileEntryTypeLocalService,
			long userId, long groupId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		DDMForm ddmForm = new DDMForm();

		ddmForm.setDefaultLocale(LocaleUtil.US);
		ddmForm.addAvailableLocale(LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField("fieldName", "text");

		ddmForm.addDDMFormField(ddmFormField);

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			groupId, "com.liferay.dynamic.data.lists.model.DDLRecordSet",
			ddmForm);

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
					DLFileEntryTypeUtil.getDDMStructures(dlFileEntryType)) {

				DDMStructureLocalServiceUtil.deleteStructure(
					ddmStructure.getStructureId());
			}
		}
	}

}