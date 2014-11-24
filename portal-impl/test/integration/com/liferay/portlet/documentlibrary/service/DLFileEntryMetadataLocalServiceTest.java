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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.ByteArrayInputStream;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael C. Han
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLFileEntryMetadataLocalServiceTest {

	@ClassRule
	public static final MainServletTestRule mainServletTestRule =
		MainServletTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		byte[] testFileBytes = FileUtil.getBytes(
			getClass(), _TEST_DDM_STRUCTURE);

		serviceContext.setAttribute("definition", new String(testFileBytes));

		User user = TestPropsValues.getUser();

		serviceContext.setLanguageId(LocaleUtil.toLanguageId(user.getLocale()));

		_dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.addFileEntryType(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Test Structure", StringPool.BLANK, new long[0],
				serviceContext);

		List<DDMStructure> ddmStructures = _dlFileEntryType.getDDMStructures();

		_ddmStructure = ddmStructures.get(0);

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			RandomTestUtil.randomBytes());

		Map<String, Fields> fieldsMap = new HashMap<String, Fields>();

		Fields fields = new Fields();

		Field nameField = new Field(
			_ddmStructure.getStructureId(), "date_an", new Date());

		fields.put(nameField);

		fieldsMap.put(_ddmStructure.getStructureKey(), fields);

		_dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), null, RandomTestUtil.randomString(),
			null, null, _dlFileEntryType.getFileEntryTypeId(), fieldsMap, null,
			byteArrayInputStream, byteArrayInputStream.available(),
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
	public void testGetNoStructureFileEntryMetadatas() throws Exception {
		try {
			DLFileVersion dlFileVersion = _dlFileEntry.getFileVersion();

			DLFileEntryMetadata dlFileEntryMetadata =
				DLFileEntryMetadataLocalServiceUtil.fetchFileEntryMetadata(
					_ddmStructure.getStructureId(),
					dlFileVersion.getFileVersionId());

			DDMStructureLocalServiceUtil.deleteDDMStructure(_ddmStructure);

			List<DLFileEntryMetadata> dlFileEntryMetadatas =
				DLFileEntryMetadataLocalServiceUtil.
					getNoStructureFileEntryMetadatas();

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

	private static final String _TEST_DDM_STRUCTURE =
		"dependencies/ddmstructure.xml";

	private DDMStructure _ddmStructure;
	private DLFileEntry _dlFileEntry;
	private DLFileEntryType _dlFileEntryType;

	@DeleteAfterTestRun
	private Group _group;

}