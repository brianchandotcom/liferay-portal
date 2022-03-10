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
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeServiceUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslatorUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@RunWith(Arquillian.class)
public class DLFileEntryTypeServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_folder = DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Folder A", "",
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_subfolder = DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_folder.getFolderId(), "SubFolder AA", "",
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_basicDocumentDLFileEntryType =
			DLFileEntryTypeLocalServiceUtil.getFileEntryType(
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);

		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), DLFileEntryMetadata.class.getName());

		_dlFileEntryType1 = DLFileEntryTypeServiceUtil.addFileEntryType(
			_group.getGroupId(), StringUtil.randomString(),
			StringUtil.randomString(),
			new long[] {ddmStructure1.getStructureId()},
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), DLFileEntryMetadata.class.getName());

		_dlFileEntryType2 = DLFileEntryTypeServiceUtil.addFileEntryType(
			_group.getGroupId(), StringUtil.randomString(),
			StringUtil.randomString(),
			new long[] {ddmStructure2.getStructureId()},
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_dlFileEntryTypes = DLFileEntryTypeLocalServiceUtil.getFileEntryTypes(
			PortalUtil.getCurrentAndAncestorSiteGroupIds(_group.getGroupId()));
	}

	@Test
	public void testAddFileEntryTypeWithEmptyDDMForm() throws Exception {
		int fileEntryTypesCount =
			DLFileEntryTypeServiceUtil.getFileEntryTypesCount(
				new long[] {_group.getGroupId()});

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		serviceContext.setAttribute(
			"ddmForm", DDMBeanTranslatorUtil.translate(new DDMForm()));

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), DLFileEntryMetadata.class.getName());

		DLFileEntryTypeServiceUtil.addFileEntryType(
			_group.getGroupId(), StringUtil.randomString(),
			StringUtil.randomString(),
			new long[] {ddmStructure.getStructureId()}, serviceContext);

		Assert.assertEquals(
			fileEntryTypesCount + 1,
			DLFileEntryTypeServiceUtil.getFileEntryTypesCount(
				new long[] {_group.getGroupId()}));
	}

	@Test
	public void testAddFileEntryTypeWithNonemptyDDMForm() throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		byte[] testFileBytes = FileUtil.getBytes(
			getClass(), _TEST_DDM_STRUCTURE);

		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
				new String(testFileBytes));

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_ddmFormDeserializer.deserialize(builder.build());

		serviceContext.setAttribute(
			"ddmForm",
			DDMBeanTranslatorUtil.translate(
				ddmFormDeserializerDeserializeResponse.getDDMForm()));

		User user = TestPropsValues.getUser();

		serviceContext.setLanguageId(LocaleUtil.toLanguageId(user.getLocale()));

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.addFileEntryType(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Test Structure", StringPool.BLANK, new long[0],
				serviceContext);

		List<com.liferay.dynamic.data.mapping.kernel.DDMStructure>
			ddmStructures = dlFileEntryType.getDDMStructures();

		Assert.assertEquals(ddmStructures.toString(), 1, ddmStructures.size());

		com.liferay.dynamic.data.mapping.kernel.DDMStructure firstDDMStructure =
			ddmStructures.get(0);

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			firstDDMStructure.getStructureId());

		Locale[] availableLocales = LocaleUtil.fromLanguageIds(
			ddmStructure.getAvailableLanguageIds());

		boolean hasDefaultLocale = ArrayUtil.contains(
			availableLocales, LocaleUtil.getSiteDefault());

		Assert.assertTrue(hasDefaultLocale);

		boolean hasHungarianLocale = ArrayUtil.contains(
			availableLocales, LocaleUtil.HUNGARY);

		Assert.assertTrue(hasHungarianLocale);

		boolean hasUserLocale = ArrayUtil.contains(
			availableLocales, user.getLocale());

		Assert.assertTrue(hasUserLocale);

		DLFileEntryTypeLocalServiceUtil.deleteFileEntryType(dlFileEntryType);
	}

	@Test
	public void testFileEntryTypeRestrictions() throws Exception {

		// Configure folder

		DLAppLocalServiceUtil.updateFolder(
			_folder.getFolderId(), _folder.getParentFolderId(),
			_folder.getName(), _folder.getDescription(),
			_getFolderServiceContext(_dlFileEntryType1, _dlFileEntryType2));

		// Add file to folder

		String name = "Test.txt";
		byte[] bytes = _CONTENT.getBytes();

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			null, _group.getGroupId(), _folder.getFolderId(), name,
			ContentTypes.TEXT_PLAIN, name, "", "", bytes, null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		assertFileEntryType(fileEntry, _dlFileEntryType1);

		// Add file to subfolder

		fileEntry = DLAppServiceUtil.addFileEntry(
			null, _group.getGroupId(), _subfolder.getFolderId(), name,
			ContentTypes.TEXT_PLAIN, name, "", "", bytes, null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		assertFileEntryType(fileEntry, _dlFileEntryType1);

		// Configure subfolder

		DLAppLocalServiceUtil.updateFolder(
			_subfolder.getFolderId(), _subfolder.getParentFolderId(),
			_subfolder.getName(), _subfolder.getDescription(),
			_getFolderServiceContext(_basicDocumentDLFileEntryType));

		assertFileEntryType(
			DLAppServiceUtil.getFileEntry(fileEntry.getFileEntryId()),
			_basicDocumentDLFileEntryType);
	}

	@Test
	public void testLocalizedSiteAddFileEntryType() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		Locale locale = LocaleThreadLocal.getSiteDefaultLocale();

		try {
			LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.SPAIN);

			String name = RandomTestUtil.randomString();
			String description = RandomTestUtil.randomString();
			DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
				DLFileEntryMetadata.class.getName(),
				new Locale[] {LocaleUtil.SPAIN}, LocaleUtil.SPAIN);

			DLFileEntryType dlFileEntryType =
				DLFileEntryTypeLocalServiceUtil.addFileEntryType(
					TestPropsValues.getUserId(), _group.getGroupId(), name,
					description, new long[] {ddmStructure.getStructureId()},
					serviceContext);

			Assert.assertEquals(
				name, dlFileEntryType.getName(LocaleUtil.US, true));
			Assert.assertEquals(
				description,
				dlFileEntryType.getDescription(LocaleUtil.US, true));
		}
		finally {
			LocaleThreadLocal.setSiteDefaultLocale(locale);
		}
	}

	@Test
	public void testLocalizedSiteUpdateFileEntryType() throws Exception {
		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		Locale locale = LocaleThreadLocal.getSiteDefaultLocale();

		try {
			LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.SPAIN);

			String name = RandomTestUtil.randomString();
			String description = RandomTestUtil.randomString();
			DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
				DLFileEntryMetadata.class.getName(),
				new Locale[] {LocaleUtil.SPAIN}, LocaleUtil.SPAIN);

			DLFileEntryType dlFileEntryType =
				DLFileEntryTypeLocalServiceUtil.addFileEntryType(
					TestPropsValues.getUserId(), group.getGroupId(), name,
					description, new long[] {ddmStructure.getStructureId()},
					serviceContext);

			name = RandomTestUtil.randomString();
			description = RandomTestUtil.randomString();

			DLFileEntryTypeLocalServiceUtil.updateFileEntryType(
				TestPropsValues.getUserId(),
				dlFileEntryType.getFileEntryTypeId(), name, description,
				new long[] {ddmStructure.getStructureId()}, serviceContext);

			dlFileEntryType = DLFileEntryTypeLocalServiceUtil.getFileEntryType(
				dlFileEntryType.getFileEntryTypeId());

			Assert.assertEquals(
				name, dlFileEntryType.getName(LocaleUtil.US, true));
			Assert.assertEquals(
				description,
				dlFileEntryType.getDescription(LocaleUtil.US, true));
		}
		finally {
			LocaleThreadLocal.setSiteDefaultLocale(locale);
		}
	}

	@Test
	public void testUpdateFileEntryTypeWithEmptyDDMForm() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), DLFileEntryMetadata.class.getName());

		DDMForm ddmForm = new DDMForm();

		ddmForm.addDDMFormField(new DDMFormField("text", "text"));
		ddmForm.setAvailableLocales(
			Collections.singleton(LocaleUtil.getDefault()));
		ddmForm.setDefaultLocale(LocaleUtil.getDefault());

		serviceContext.setAttribute(
			"ddmForm", DDMBeanTranslatorUtil.translate(ddmForm));

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeServiceUtil.addFileEntryType(
				_group.getGroupId(), ddmStructure.getStructureId(), null,
				Collections.singletonMap(
					LocaleUtil.US, StringUtil.randomString()),
				Collections.singletonMap(
					LocaleUtil.US, StringUtil.randomString()),
				serviceContext);

		serviceContext.setAttribute(
			"ddmForm", DDMBeanTranslatorUtil.translate(new DDMForm()));

		long[] ddmStructureIds = _getDDMStructureIds(dlFileEntryType);

		DLFileEntryTypeServiceUtil.updateFileEntryType(
			dlFileEntryType.getFileEntryTypeId(), StringUtil.randomString(),
			StringUtil.randomId(), ddmStructureIds, serviceContext);

		dlFileEntryType = DLFileEntryTypeServiceUtil.getFileEntryType(
			dlFileEntryType.getFileEntryTypeId());

		List<com.liferay.dynamic.data.mapping.kernel.DDMStructure>
			ddmStructures = dlFileEntryType.getDDMStructures();

		Assert.assertEquals(ddmStructures.toString(), 1, ddmStructures.size());
	}

	protected void assertFileEntryType(
		FileEntry fileEntry, DLFileEntryType dlFileEntryType) {

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		Assert.assertEquals(
			"File should be of file entry type " +
				dlFileEntryType.getFileEntryTypeId(),
			dlFileEntryType.getPrimaryKey(), dlFileEntry.getFileEntryTypeId());
	}

	private long[] _getDDMStructureIds(DLFileEntryType dlFileEntryType) {
		List<com.liferay.dynamic.data.mapping.kernel.DDMStructure>
			ddmStructures = dlFileEntryType.getDDMStructures();

		long[] ddmStructureIds = new long[ddmStructures.size()];

		int i = 0;

		for (com.liferay.dynamic.data.mapping.kernel.DDMStructure ddmStructure :
				ddmStructures) {

			ddmStructureIds[i] = ddmStructure.getStructureId();

			i++;
		}

		return ddmStructureIds;
	}

	private ServiceContext _getFolderServiceContext(
			DLFileEntryType... dlFileEntryTypes)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAttribute(
			"defaultFileEntryTypeId", dlFileEntryTypes[0].getPrimaryKey());
		serviceContext.setAttribute(
			"dlFileEntryTypesSearchContainerPrimaryKeys",
			ArrayUtil.toString(dlFileEntryTypes, "primaryKey"));
		serviceContext.setAttribute(
			"restrictionType",
			DLFolderConstants.RESTRICTION_TYPE_FILE_ENTRY_TYPES_AND_WORKFLOW);

		return serviceContext;
	}

	private static final String _CONTENT =
		"Content: Enterprise. Open Source. For Life.";

	private static final String _TEST_DDM_STRUCTURE =
		"dependencies/ddmstructure.xml";

	private DLFileEntryType _basicDocumentDLFileEntryType;

	@Inject(filter = "ddm.form.deserializer.type=xsd")
	private DDMFormDeserializer _ddmFormDeserializer;

	@DeleteAfterTestRun
	private DLFileEntryType _dlFileEntryType1;

	@DeleteAfterTestRun
	private DLFileEntryType _dlFileEntryType2;

	private List<DLFileEntryType> _dlFileEntryTypes;
	private Folder _folder;

	@DeleteAfterTestRun
	private Group _group;

	private Folder _subfolder;

}