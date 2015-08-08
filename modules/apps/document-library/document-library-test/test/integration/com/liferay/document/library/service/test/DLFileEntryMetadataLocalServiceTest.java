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

package com.liferay.document.library.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMBeanCopyUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.randomizerbumpers.TikaSafeRandomizerBumper;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

import java.io.ByteArrayInputStream;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael C. Han
 */
@RunWith(Arquillian.class)
public class DLFileEntryMetadataLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		byte[] testFileBytes = FileUtil.getBytes(
			getClass(), "dependencies/ddmstructure.xml");

		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm =
			DDMFormXSDDeserializerUtil.deserialize(new String(testFileBytes));

		serviceContext.setAttribute(
			"ddmForm", DDMBeanCopyUtil.copyDDMForm(ddmForm));

		User user = TestPropsValues.getUser();

		serviceContext.setLanguageId(LocaleUtil.toLanguageId(user.getLocale()));

		_dlFileEntryType = DLFileEntryTypeLocalServiceUtil.addFileEntryType(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), StringPool.BLANK, new long[0],
			serviceContext);

		List<com.liferay.portlet.dynamicdatamapping.DDMStructure>
			ddmStructures = _dlFileEntryType.getDDMStructures();

		_ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			ddmStructures.get(0).getStructureId());

		Map<String,
			com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues>

			ddmFormValuesMap = setUpDDMFormValuesMap(
				_ddmStructure.getStructureKey(), user.getLocale());

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			RandomTestUtil.randomBytes(
				TikaSafeRandomizerBumper.TEXT_PLAIN_INSTANCE));

		_dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), null, RandomTestUtil.randomString(),
			null, null, _dlFileEntryType.getFileEntryTypeId(), ddmFormValuesMap,
			null, byteArrayInputStream, byteArrayInputStream.available(),
			serviceContext);
	}

	@Test
	public void testGetMismatchedCompanyIdFileEntryMetadatas()
		throws Exception {

		try {
			DLFileVersion dlFileVersion = _dlFileEntry.getFileVersion();

			DLFileEntryMetadata dlFileEntryMetadata =
				DLFileEntryMetadataLocalServiceUtil.fetchFileEntryMetadata(
					_ddmStructure.getStructureId(),
					dlFileVersion.getFileVersionId());

			_ddmStructure.setCompanyId(12345);

			DDMStructureLocalServiceUtil.updateDDMStructure(_ddmStructure);

			List<DLFileEntryMetadata> dlFileEntryMetadatas =
				DLFileEntryMetadataLocalServiceUtil.
					getMismatchedCompanyIdFileEntryMetadatas();

			Assert.assertEquals(1, dlFileEntryMetadatas.size());
			Assert.assertEquals(
				dlFileEntryMetadata, dlFileEntryMetadatas.get(0));
		}
		finally {
			if (_ddmStructure != null) {
				_ddmStructure.setCompanyId(_dlFileEntry.getCompanyId());

				DDMStructureLocalServiceUtil.updateDDMStructure(_ddmStructure);
			}
		}
	}

	@Test
	public void testGetNoStructuresFileEntryMetadatas() throws Exception {
		try {
			DLFileVersion dlFileVersion = _dlFileEntry.getFileVersion();

			DLFileEntryMetadata dlFileEntryMetadata =
				DLFileEntryMetadataLocalServiceUtil.fetchFileEntryMetadata(
					_ddmStructure.getStructureId(),
					dlFileVersion.getFileVersionId());

			DDMStructureLocalServiceUtil.deleteDDMStructure(_ddmStructure);

			List<DLFileEntryMetadata> dlFileEntryMetadatas =
				DLFileEntryMetadataLocalServiceUtil.
					getNoStructuresFileEntryMetadatas();

			Assert.assertEquals(1, dlFileEntryMetadatas.size());
			Assert.assertEquals(
				dlFileEntryMetadata, dlFileEntryMetadatas.get(0));
		}
		finally {
			if (_ddmStructure != null) {
				DDMStructureLocalServiceUtil.addDDMStructure(_ddmStructure);
			}
		}
	}

	protected Map<String, DDMFormValues> setUpDDMFormValuesMap(
		String ddmStructureKey, Locale currentLocale) {

		Set<Locale> availableLocales = DDMFormTestUtil.createAvailableLocales(
			currentLocale);

		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(availableLocales);
		ddmForm.setDefaultLocale(currentLocale);

		DDMFormField ddmFormField = new DDMFormField("date_an", "ddm-date");

		ddmFormField.setDataType("date");

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.setAvailableLocales(availableLocales);
		ddmFormValues.setDefaultLocale(currentLocale);

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName("date_an");
		ddmFormFieldValue.setValue(new UnlocalizedValue(""));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		Map<String, DDMFormValues> ddmFormValuesMap = new HashMap<>();

		ddmFormValuesMap.put(ddmStructureKey, ddmFormValues);

		return ddmFormValuesMap;
	}

	private DDMStructure _ddmStructure;
	private DLFileEntry _dlFileEntry;
	private DLFileEntryType _dlFileEntryType;

	@DeleteAfterTestRun
	private Group _group;

}