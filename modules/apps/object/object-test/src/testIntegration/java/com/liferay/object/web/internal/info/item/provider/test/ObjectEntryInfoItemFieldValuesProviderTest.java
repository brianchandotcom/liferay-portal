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

package com.liferay.object.web.internal.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.builder.AttachmentObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class ObjectEntryInfoItemFieldValuesProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-176083", "true"
			).build());

		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-176083", "false"
			).build());
	}

	@Test
	public void testObjectEntryInfoItemFieldValuesProviderWithAttachment()
		throws Exception {

		ObjectField attachmentObjectField = _getAttachmentObjectField(
			"attachment",
			Arrays.asList(
				_createObjectFieldSetting("acceptedFileExtensions", "txt"),
				_createObjectFieldSetting("fileSource", "documentsAndMedia"),
				_createObjectFieldSetting("maximumFileSize", "100")));

		ObjectDefinition objectDefinition = null;

		try {
			objectDefinition = _addAndPublishObjectDefinition(
				attachmentObjectField);

			FileEntry fileEntry = _addFileEntry(_group, "test.txt");

			ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				objectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					attachmentObjectField.getName(), fileEntry.getFileEntryId()
				).build(),
				ServiceContextTestUtil.getServiceContext());

			InfoItemFieldValuesProvider<ObjectEntry>
				infoItemFieldValuesProvider =
					_infoItemServiceRegistry.getFirstInfoItemService(
						InfoItemFieldValuesProvider.class,
						objectDefinition.getClassName());

			InfoItemFieldValues infoItemFieldValues =
				infoItemFieldValuesProvider.getInfoItemFieldValues(objectEntry);

			Assert.assertNotNull(infoItemFieldValues);

			ObjectField persistedAttachmentObjectField =
				_objectFieldLocalService.fetchObjectField(
					objectDefinition.getObjectDefinitionId(),
					attachmentObjectField.getName());

			String expectedURL = _dlURLHelper.getDownloadURL(
				fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK);

			InfoFieldValue<Object> downloadURLInfoFieldValue =
				infoItemFieldValues.getInfoFieldValue(
					persistedAttachmentObjectField.getObjectFieldId() +
						"#downloadURL");

			Assert.assertEquals(
				HttpComponentsUtil.removeParameter(expectedURL, "t"),
				HttpComponentsUtil.removeParameter(
					String.valueOf(downloadURLInfoFieldValue.getValue()), "t"));

			_assertInfoFieldValue(
				"#fileName", infoItemFieldValues,
				persistedAttachmentObjectField, fileEntry.getFileName());
			_assertInfoFieldValue(
				"#mimeType", infoItemFieldValues,
				persistedAttachmentObjectField, fileEntry.getMimeType());
			_assertInfoFieldValue(
				"#size", infoItemFieldValues, persistedAttachmentObjectField,
				fileEntry.getSize());
		}
		finally {
			if (objectDefinition != null) {
				_objectDefinitionLocalService.deleteObjectDefinition(
					objectDefinition.getObjectDefinitionId());
			}
		}
	}

	private ObjectDefinition _addAndPublishObjectDefinition(
			ObjectField attachmentObjectField)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_SITE,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(attachmentObjectField));

		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());
	}

	private FileEntry _addFileEntry(Group group, String name) throws Exception {
		return _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, name, ContentTypes.TEXT,
			RandomTestUtil.randomBytes(), null, null,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	private void _assertInfoFieldValue(
		String infoFieldName, InfoItemFieldValues infoItemFieldValues,
		ObjectField objectField, Object expectedValue) {

		InfoFieldValue<Object> fileNameInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue(
				objectField.getObjectFieldId() + infoFieldName);

		Assert.assertEquals(expectedValue, fileNameInfoFieldValue.getValue());
	}

	private ObjectFieldSetting _createObjectFieldSetting(
		String name, String value) {

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.createObjectFieldSetting(0L);

		objectFieldSetting.setName(name);
		objectFieldSetting.setValue(value);

		return objectFieldSetting;
	}

	private ObjectField _getAttachmentObjectField(
		String name, List<ObjectFieldSetting> objectFieldSettings) {

		return new AttachmentObjectFieldBuilder(
		).labelMap(
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
		).name(
			name
		).objectFieldSettings(
			objectFieldSettings
		).build();
	}

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLURLHelper _dlURLHelper;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

}