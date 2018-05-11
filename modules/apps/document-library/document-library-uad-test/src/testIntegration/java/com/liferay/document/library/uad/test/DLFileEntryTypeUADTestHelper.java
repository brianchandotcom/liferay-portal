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
import com.liferay.dynamic.data.mapping.kernel.DDMStructureLinkManagerUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManager;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManagerUtil;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManager;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author William Newbury
 */
@Component(immediate = true, service = DLFileEntryTypeUADTestHelper.class)
public class DLFileEntryTypeUADTestHelper {

	public DLFileEntryType addDLFileEntryType(long userId) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		DDMForm ddmForm = new DDMForm();

		ddmForm.setDefaultLocale(LocaleUtil.US);
		ddmForm.addAvailableLocale(LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField("fieldName", "text");

		ddmForm.addDDMFormField(ddmFormField);

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.US, "Test Structure Name");

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.US, "Test Structure Description");

		long classNameId = portal.getClassNameId(
			"com.liferay.dynamic.data.lists.model.DDLRecordSet");

		DDMStructure ddmStructure = DDMStructureManagerUtil.addStructure(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(), null,
			classNameId, RandomTestUtil.randomString(), nameMap, descriptionMap,
			ddmForm, StorageEngineManager.STORAGE_TYPE_DEFAULT,
			DDMStructureManager.STRUCTURE_TYPE_DEFAULT, serviceContext);

		return _dlFileEntryTypeLocalService.addFileEntryType(
			userId, TestPropsValues.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			new long[] {ddmStructure.getStructureId()}, serviceContext);
	}

	public void cleanUpDependencies(List<DLFileEntryType> dlFileEntryTypes)
		throws Exception {

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			_dlFileEntryTypeLocalService.deleteFileEntryType(dlFileEntryType);

			for (DDMStructure ddmStructure :
					dlFileEntryType.getDDMStructures()) {

				long classNameId = portal.getClassNameId(
					"com.liferay.document.library.kernel.model." +
						"DLFileEntryType");

				DDMStructureLinkManagerUtil.deleteStructureLink(
					classNameId, dlFileEntryType.getFileEntryTypeId(),
					ddmStructure.getStructureId());

				DDMStructureManagerUtil.deleteStructure(
					ddmStructure.getStructureId());
			}
		}
	}

	@Reference
	protected Portal portal;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

}