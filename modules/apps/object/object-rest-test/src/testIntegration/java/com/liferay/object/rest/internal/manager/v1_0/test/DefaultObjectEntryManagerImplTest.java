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

package com.liferay.object.rest.internal.manager.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.builder.AttachmentObjectFieldBuilder;
import com.liferay.object.field.builder.PicklistObjectFieldBuilder;
import com.liferay.object.field.builder.RichTextObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.rest.dto.v1_0.FileEntry;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@RunWith(Arquillian.class)
public class DefaultObjectEntryManagerImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = TestPropsValues.getUser();
	}

	@Before
	public void setUp() throws Exception {
		_objectDefinition1 = _createObjectDefinition(
			Arrays.asList(
				new TextObjectFieldBuilder().labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).objectFieldSettings(
					Collections.emptyList()
				).name(
					"textObjectFieldName"
				).build()));

		_setUpDTOConverterContext();

		_listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				_user.getUserId(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()));

		_objectDefinition2 = _createObjectDefinition(
			Arrays.asList(
				new AttachmentObjectFieldBuilder().labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"attachmentObjectFieldName"
				).objectFieldSettings(
					Arrays.asList(
						_createObjectFieldSetting(
							"acceptedFileExtensions", "txt"),
						_createObjectFieldSetting(
							"fileSource", "documentsAndMedia"),
						_createObjectFieldSetting("maximumFileSize", "100"))
				).build(),
				new PicklistObjectFieldBuilder().labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).listTypeDefinitionId(
					_listTypeDefinition.getListTypeDefinitionId()
				).objectFieldSettings(
					Collections.emptyList()
				).name(
					"picklistObjectFieldName"
				).build(),
				new RichTextObjectFieldBuilder().labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).objectFieldSettings(
					Collections.emptyList()
				).name(
					"richTextObjectFieldName"
				).build(),
				new TextObjectFieldBuilder().labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).objectFieldSettings(
					Collections.emptyList()
				).name(
					"textObjectFieldName"
				).build()));
	}

	@Test
	public void testAddObjectEntry() throws Exception {
		_testAddObjectEntry(
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"attachmentObjectFieldName",
						_getAttachmentObjectFieldValue()
					).put(
						"picklistObjectFieldName",
						_getPicklistObjectFieldValue()
					).put(
						"r_oneToManyRelationshipName_" +
							_objectDefinition1.getPKObjectFieldName(),
						_getOneToManyRelationshipFieldValue()
					).put(
						"richTextObjectFieldName", "<strong>content</strong>"
					).put(
						"textObjectFieldName", RandomTestUtil.randomString()
					).build();
				}
			});
	}

	private void _assertEquals(
			ObjectEntry objectEntry1, ObjectEntry objectEntry2)
		throws Exception {

		Map<String, Object> objectEntry1Properties =
			objectEntry1.getProperties();
		Map<String, Object> objectEntry2Properties =
			objectEntry2.getProperties();

		for (Map.Entry<String, Object> entry :
				objectEntry1Properties.entrySet()) {

			if (Objects.equals("attachmentObjectFieldName", entry.getKey())) {
				FileEntry fileEntry = (FileEntry)objectEntry2Properties.get(
					entry.getKey());

				Assert.assertEquals(entry.getValue(), fileEntry.getId());
			}
			else if (Objects.equals("picklistFieldName", entry.getKey())) {
				ListEntry listEntry = (ListEntry)objectEntry2Properties.get(
					entry.getKey());

				Assert.assertEquals(entry.getValue(), listEntry.getKey());
			}
			else if (Objects.equals(
						entry.getKey(), "richTextObjectFieldName")) {

				Assert.assertEquals(
					"content",
					String.valueOf(objectEntry2Properties.get(entry.getKey())));
			}
			else if (Objects.equals(
						entry.getKey(),
						"r_oneToManyRelationshipName_" +
							_objectDefinition1.getPKObjectFieldName())) {

				Assert.assertEquals(
					entry.getValue(),
					objectEntry2Properties.get(entry.getKey()));

				_assertEquals(
					_getObjectEntry(
						_objectDefinition1,
						GetterUtil.getLong(entry.getValue())),
					(ObjectEntry)objectEntry2Properties.get(
						StringUtil.replaceLast(
							String.valueOf(entry.getKey()), "Id",
							StringPool.BLANK)));
			}
			else if (Objects.equals("textFieldName", entry.getKey())) {
				Assert.assertEquals(
					entry.getValue(),
					objectEntry2Properties.get(entry.getKey()));
			}
		}
	}

	private ObjectDefinition _createObjectDefinition(
			List<ObjectField> objectFields)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				_user.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT, objectFields);

		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			_user.getUserId(), objectDefinition.getObjectDefinitionId());
	}

	private ObjectFieldSetting _createObjectFieldSetting(
		String name, String value) {

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.createObjectFieldSetting(0L);

		objectFieldSetting.setName(name);
		objectFieldSetting.setValue(value);

		return objectFieldSetting;
	}

	private Long _getAttachmentObjectFieldValue() throws Exception {
		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		InputStream inputStream = new ByteArrayInputStream(bytes);

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
			null, _group.getCreatorUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt",
			MimeTypesUtil.getExtensionContentType("txt"),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, inputStream, bytes.length, null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		return dlFileEntry.getFileEntryId();
	}

	private ObjectEntry _getObjectEntry(
			ObjectDefinition objectDefinition, long objectEntryId)
		throws Exception {

		return _objectEntryManager.getObjectEntry(
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), _dtoConverterRegistry, null,
				LocaleUtil.getDefault(), null, _user),
			objectDefinition, objectEntryId);
	}

	private Long _getOneToManyRelationshipFieldValue() throws Exception {
		_objectRelationshipLocalService.addObjectRelationship(
			_user.getUserId(), _objectDefinition1.getObjectDefinitionId(),
			_objectDefinition2.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			"oneToManyRelationshipName",
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectEntry objectEntry = _objectEntryManager.addObjectEntry(
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), _dtoConverterRegistry, null,
				LocaleUtil.getDefault(), null, _user),
			_objectDefinition1,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"textObjectFieldName", RandomTestUtil.randomString()
					).build();
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);

		return objectEntry.getId();
	}

	private String _getPicklistObjectFieldValue() throws Exception {
		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.addListTypeEntry(
				_user.getUserId(),
				_listTypeDefinition.getListTypeDefinitionId(),
				RandomTestUtil.randomString(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()));

		return listTypeEntry.getKey();
	}

	private void _setUpDTOConverterContext() {
		UriInfo uriInfo = (UriInfo)ProxyUtil.newProxyInstance(
			DefaultObjectEntryManagerImplTest.class.getClassLoader(),
			new Class<?>[] {UriInfo.class},
			(proxy, method, methodArgs) -> {
				if (Objects.equals(method.getName(), "getBaseUriBuilder")) {
					return UriBuilder.fromPath(RandomTestUtil.randomString());
				}
				else if (Objects.equals(method.getName(), "getMatchedURIs")) {
					return Arrays.asList(StringPool.BLANK);
				}
				else if (Objects.equals(
							method.getName(), "getPathParameters")) {

					return new MultivaluedHashMap<>();
				}
				else if (Objects.equals(
							method.getName(), "getQueryParameters")) {

					MultivaluedMap<String, String> multivaluedMap =
						new MultivaluedHashMap<>();

					multivaluedMap.add(
						"nestedFields",
						_objectDefinition1.getPKObjectFieldName());

					return multivaluedMap;
				}

				throw new UnsupportedOperationException(
					"Unsupported method: " + method.getName());
			});

		_dtoConverterContext = new DefaultDTOConverterContext(
			false, Collections.emptyMap(), _dtoConverterRegistry, null,
			LocaleUtil.getDefault(), uriInfo, _user);
	}

	private void _testAddObjectEntry(ObjectEntry newObjectEntry)
		throws Exception {

		_assertEquals(
			newObjectEntry,
			_objectEntryManager.addObjectEntry(
				_dtoConverterContext, _objectDefinition2, newObjectEntry,
				ObjectDefinitionConstants.SCOPE_COMPANY));
	}

	private static DTOConverterContext _dtoConverterContext;

	@Inject
	private static DTOConverterRegistry _dtoConverterRegistry;

	@DeleteAfterTestRun
	private static Group _group;

	private static User _user;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	private ListTypeDefinition _listTypeDefinition;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Inject
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition2;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject(
		filter = "object.entry.manager.storage.type=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT
	)
	private ObjectEntryManager _objectEntryManager;

	@Inject
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}