/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.dto.v1_0.converter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.headless.admin.user.dto.v1_0.Creator;
import com.liferay.headless.admin.user.dto.v1_0.SharedAsset;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.field.setting.builder.ObjectFieldSettingBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedFieldsContext;
import com.liferay.portal.vulcan.fields.NestedFieldsContextThreadLocal;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marco Galluzzi
 */
@RunWith(Arquillian.class)
public class SharedAssetDTOConverterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());

		_fileEntry = DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt", ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, RandomTestUtil.randomBytes(), null, null, null,
			_serviceContext);

		_user = UserTestUtil.addUser(_group.getGroupId());
	}

	@Test
	public void testToDTO() throws Exception {
		SharingEntry sharingEntry = _addSharingEntry(
			DLFileEntry.class.getName(), _fileEntry.getFileEntryId());

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				false, null, null, sharingEntry.getSharingEntryId(),
				LocaleUtil.getDefault(), null, TestPropsValues.getUser());

		SharedAsset sharedAsset = _sharedAssetDTOConverter.toDTO(
			dtoConverterContext, sharingEntry);

		Assert.assertNotNull(sharedAsset);

		Assert.assertArrayEquals(
			new String[] {"VIEW"}, sharedAsset.getActionIds());
		Assert.assertNull(sharedAsset.getActions());
		Assert.assertEquals("Document", sharedAsset.getAssetType());
		Assert.assertEquals(
			DLFileEntry.class.getName(), sharedAsset.getClassName());
		Assert.assertEquals(
			(Long)sharingEntry.getClassPK(), sharedAsset.getClassPK());

		Creator creator = sharedAsset.getCreator();

		Assert.assertNotNull(creator);

		Assert.assertEquals(_user.getFullName(), creator.getName());

		Assert.assertEquals((Long)_user.getUserId(), creator.getId());

		Assert.assertEquals(
			sharingEntry.getCreateDate(), sharedAsset.getDateCreated());
		Assert.assertEquals(
			sharingEntry.getModifiedDate(), sharedAsset.getDateModified());
		Assert.assertEquals(
			sharingEntry.getExternalReferenceCode(),
			sharedAsset.getExternalReferenceCode());
		Assert.assertNull(sharedAsset.getFile());
		Assert.assertEquals("document-text", sharedAsset.getFileTypeIcon());
		Assert.assertNotNull(sharedAsset.getFileTypeIconColor());
		Assert.assertEquals(
			(Long)sharingEntry.getSharingEntryId(), sharedAsset.getId());
		Assert.assertEquals(
			sharingEntry.isShareable(), sharedAsset.getShareable());
		Assert.assertEquals(
			_group.getName(LocaleUtil.getDefault()), sharedAsset.getSiteName());
		Assert.assertEquals(_fileEntry.getTitle(), sharedAsset.getTitle());
		Assert.assertTrue(sharedAsset.getVisible());
	}

	@Test
	public void testToDTOWithObjectEntry() throws Exception {
		ObjectField objectField = ObjectFieldUtil.createObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
			ObjectFieldConstants.DB_TYPE_LONG, true, false, null, "file",
			"file",
			Arrays.asList(
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_ACCEPTED_FILE_EXTENSIONS
				).value(
					"png"
				).build(),
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_FILE_SOURCE
				).value(
					ObjectFieldSettingConstants.VALUE_DOCS_AND_MEDIA
				).build(),
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_MAX_FILE_SIZE
				).value(
					"100"
				).build()),
			false);

		ObjectFolder objectFolder =
			_objectFolderLocalService.fetchObjectFolderByExternalReferenceCode(
				ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES,
				TestPropsValues.getCompanyId());

		if (objectFolder == null) {
			objectFolder = _objectFolderLocalService.addObjectFolder(
				ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES,
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				RandomTestUtil.randomString());
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				null, TestPropsValues.getUserId(),
				objectFolder.getObjectFolderId(), null, false, true, false,
				true, false, false, false, false, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_SITE,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.emptyList(), Arrays.asList(objectField),
				Collections.emptyList(), _serviceContext);

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".png", ContentTypes.IMAGE_PNG,
			FileUtil.getBytes(
				SharedAssetDTOConverterTest.class, "dependencies/sample.png"),
			null, null, null, _serviceContext);

		Assert.assertEquals(
			StringPool.BLANK, _dlURLHelper.getThumbnailSrc(fileEntry, null));

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			_group.getGroupId(), TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(), 0,
			LocaleUtil.toLanguageId(LocaleUtil.getDefault()),
			HashMapBuilder.<String, Serializable>put(
				"file", fileEntry.getFileEntryId()
			).build(),
			_serviceContext);

		SharingEntry sharingEntry = _addSharingEntry(
			ObjectEntry.class.getName(), objectEntry.getObjectEntryId());

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				false, new HashMap<>(), null, sharingEntry.getSharingEntryId(),
				LocaleUtil.getDefault(), null, TestPropsValues.getUser());

		SharedAsset sharedAsset = _sharedAssetDTOConverter.toDTO(
			dtoConverterContext, sharingEntry);

		NestedFieldsContextThreadLocal.setNestedFieldsContext(
			new NestedFieldsContext(
				1, null, List.of("file"), null, null, null));

		com.liferay.headless.admin.user.dto.v1_0.FileEntry file =
			sharedAsset.getFile();

		Assert.assertEquals(
			fileEntry.getExternalReferenceCode(),
			file.getExternalReferenceCode());
		Assert.assertEquals((Long)fileEntry.getFileEntryId(), file.getId());
		Assert.assertNotNull(file.getLink());
		Assert.assertEquals(fileEntry.getFileName(), file.getName());
		Assert.assertEquals(
			_dlURLHelper.getPreviewURL(
				fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK),
			file.getPreviewURL());
		Assert.assertNull(file.getThumbnailURL());
	}

	private SharingEntry _addSharingEntry(String className, long classPK)
		throws Exception {

		return _sharingEntryLocalService.addSharingEntry(
			null, _user.getUserId(), 0, TestPropsValues.getUserId(),
			_classNameLocalService.getClassNameId(className), classPK,
			_group.getGroupId(), true, Arrays.asList(SharingEntryAction.VIEW),
			null, _serviceContext);
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private DLURLHelper _dlURLHelper;

	private FileEntry _fileEntry;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFolderLocalService _objectFolderLocalService;

	private ServiceContext _serviceContext;

	@Inject(filter = "dto.class.name=com.liferay.sharing.model.SharingEntry")
	private DTOConverter<SharingEntry, SharedAsset> _sharedAssetDTOConverter;

	@Inject
	private SharingEntryLocalService _sharingEntryLocalService;

	private User _user;

}