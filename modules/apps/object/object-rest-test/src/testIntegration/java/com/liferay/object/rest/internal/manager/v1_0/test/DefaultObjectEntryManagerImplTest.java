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
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.util.DLURLHelper;
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
import com.liferay.object.rest.dto.v1_0.Link;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.petra.sql.dsl.expression.FilterPredicateFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlParserUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.ArrayList;
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
		_companyId = TestPropsValues.getCompanyId();
		_group = GroupTestUtil.addGroup();
		_simpleDTOConverterContext = new DefaultDTOConverterContext(
			false, Collections.emptyMap(), _dtoConverterRegistry, null,
			LocaleUtil.getDefault(), null, _user);
		_user = TestPropsValues.getUser();
	}

	@Before
	public void setUp() throws Exception {
		_objectDefinition1 = _createObjectDefinition(
			Arrays.asList(
				new TextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"textObjectFieldName"
				).objectFieldSettings(
					Collections.emptyList()
				).build()));

		_setUpDTOConverterContext();

		_listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				_user.getUserId(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()));

		_objectDefinition2 = _createObjectDefinition(
			Arrays.asList(
				new AttachmentObjectFieldBuilder(
				).labelMap(
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
				new PicklistObjectFieldBuilder(
				).indexed(
					true
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).listTypeDefinitionId(
					_listTypeDefinition.getListTypeDefinitionId()
				).name(
					"picklistObjectFieldName"
				).objectFieldSettings(
					Collections.emptyList()
				).build(),
				new RichTextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"richTextObjectFieldName"
				).objectFieldSettings(
					Collections.emptyList()
				).build(),
				new TextObjectFieldBuilder(
				).indexed(
					true
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"textObjectFieldName"
				).objectFieldSettings(
					Collections.emptyList()
				).build()));

		_objectRelationshipLocalService.addObjectRelationship(
			_user.getUserId(), _objectDefinition1.getObjectDefinitionId(),
			_objectDefinition2.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			"oneToManyRelationshipName",
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
	}

	@Test
	public void testAddObjectEntry() throws Exception {
		ObjectEntry objectEntry = new ObjectEntry() {
			{
				properties = HashMapBuilder.<String, Object>put(
					"attachmentObjectFieldName",
					_getAttachmentObjectFieldValue()
				).put(
					"picklistObjectFieldName", _addListTypeEntry()
				).put(
					"r_oneToManyRelationshipName_" +
						_objectDefinition1.getPKObjectFieldName(),
					_getOneToManyRelationshipFieldValue()
				).put(
					"richTextObjectFieldName",
					StringBundler.concat(
						"<i>", RandomTestUtil.randomString(), "</i>")
				).put(
					"textObjectFieldName", RandomTestUtil.randomString()
				).build();
			}
		};

		_assertEquals(
			_objectEntryManager.addObjectEntry(
				_dtoConverterContext, _objectDefinition2, objectEntry,
				ObjectDefinitionConstants.SCOPE_COMPANY),
			objectEntry);
	}

	@Test
	public void testGetObjectEntries() throws Exception {
		_testGetObjectEntries(Collections.emptyMap());

		String oneToManyRelationshipFieldName =
			"r_oneToManyRelationshipName_" +
				_objectDefinition1.getPKObjectFieldName();
		String picklistObjectFieldValue1 = _addListTypeEntry();

		ObjectEntry objectEntry1 = _addObjectEntry(
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						oneToManyRelationshipFieldName,
						_getOneToManyRelationshipFieldValue()
					).put(
						"picklistObjectFieldName", picklistObjectFieldValue1
					).put(
						"textObjectFieldName", "aaa"
					).build();
				}
			});

		String picklistObjectFieldValue2 = _addListTypeEntry();

		ObjectEntry objectEntry2 = _addObjectEntry(
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						oneToManyRelationshipFieldName,
						_getOneToManyRelationshipFieldValue()
					).put(
						"picklistObjectFieldName", picklistObjectFieldValue2
					).put(
						"textObjectFieldName", "aab"
					).build();
				}
			});

		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildInExpressionFilterString("id", true, objectEntry1.getId())
			).build(),
			objectEntry1);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildInExpressionFilterString(
					"id", false, objectEntry1.getId())
			).build(),
			objectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildInExpressionFilterString(
					"picklistObjectFieldName", true, picklistObjectFieldValue1)
			).build(),
			objectEntry1);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildInExpressionFilterString(
					"picklistObjectFieldName", false, picklistObjectFieldValue1)
			).build(),
			objectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildLambdaExpressionFilterString(
					"status", true, WorkflowConstants.STATUS_APPROVED)
			).build(),
			objectEntry1, objectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildLambdaExpressionFilterString(
					"status", false, WorkflowConstants.STATUS_APPROVED)
			).build());
		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildInExpressionFilterString(
					oneToManyRelationshipFieldName.substring(
						oneToManyRelationshipFieldName.lastIndexOf("_") + 1),
					true,
					MapUtil.getString(
						objectEntry1.getProperties(),
						oneToManyRelationshipFieldName))
			).build(),
			objectEntry1);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildInExpressionFilterString(
					oneToManyRelationshipFieldName.substring(
						oneToManyRelationshipFieldName.lastIndexOf("_") + 1),
					false,
					MapUtil.getString(
						objectEntry1.getProperties(),
						oneToManyRelationshipFieldName))
			).build(),
			objectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildEqualsExpressionFilterString(
					"picklistObjectFieldName", picklistObjectFieldValue1)
			).put(
				"search", "aa"
			).build(),
			objectEntry1);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildEqualsExpressionFilterString(
					"picklistObjectFieldName", picklistObjectFieldValue2)
			).put(
				"search", "aa"
			).build(),
			objectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"search", String.valueOf(objectEntry1.getId())
			).build(),
			objectEntry1);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"search", String.valueOf(objectEntry2.getId())
			).build(),
			objectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"search", picklistObjectFieldValue1
			).build(),
			objectEntry1);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"search", picklistObjectFieldValue2
			).build(),
			objectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"search", "aa"
			).build(),
			objectEntry1, objectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"sort", "createDate:asc"
			).build(),
			objectEntry1, objectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"sort", "createDate:desc"
			).build(),
			objectEntry2, objectEntry1);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"sort", "id:asc"
			).build(),
			objectEntry1, objectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"sort", "id:desc"
			).build(),
			objectEntry2, objectEntry1);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"sort", "textObjectFieldName:asc"
			).build(),
			objectEntry1, objectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"sort", "textObjectFieldName:desc"
			).build(),
			objectEntry2, objectEntry1);
	}

	private String _addListTypeEntry() throws Exception {
		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.addListTypeEntry(
				_user.getUserId(),
				_listTypeDefinition.getListTypeDefinitionId(),
				RandomTestUtil.randomString(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()));

		return listTypeEntry.getKey();
	}

	private ObjectEntry _addObjectEntry(ObjectEntry objectEntry)
		throws Exception {

		return _objectEntryManager.addObjectEntry(
			_dtoConverterContext, _objectDefinition2, objectEntry,
			ObjectDefinitionConstants.SCOPE_COMPANY);
	}

	private void _assertEquals(
			List<ObjectEntry> expectedObjectEntries,
			List<ObjectEntry> actualObjectEntries)
		throws Exception {

		Assert.assertEquals(
			actualObjectEntries.toString(), expectedObjectEntries.size(),
			actualObjectEntries.size());

		for (int i = 0; i < expectedObjectEntries.size(); i++) {
			_assertEquals(
				actualObjectEntries.get(i), expectedObjectEntries.get(i));
		}
	}

	private void _assertEquals(
			ObjectEntry actualObjectEntry, ObjectEntry expectedObjectEntry)
		throws Exception {

		Map<String, Object> actualObjectEntryProperties =
			actualObjectEntry.getProperties();
		Map<String, Object> expectedObjectEntryProperties =
			expectedObjectEntry.getProperties();

		for (Map.Entry<String, Object> expectedEntry :
				expectedObjectEntryProperties.entrySet()) {

			if (Objects.equals(
					expectedEntry.getKey(), "attachmentObjectFieldName")) {

				FileEntry fileEntry =
					(FileEntry)actualObjectEntryProperties.get(
						expectedEntry.getKey());

				Assert.assertEquals(
					expectedEntry.getValue(), fileEntry.getId());

				DLFileEntry dlFileEntry = _dlFileEntryLocalService.getFileEntry(
					fileEntry.getId());

				Assert.assertEquals(
					fileEntry.getName(), dlFileEntry.getFileName());

				Link link = fileEntry.getLink();

				Assert.assertEquals(link.getLabel(), dlFileEntry.getFileName());

				com.liferay.portal.kernel.repository.model.FileEntry
					repositoryFileEntry = _dlAppService.getFileEntry(
						fileEntry.getId());

				Assert.assertEquals(
					_dlURLHelper.getDownloadURL(
						repositoryFileEntry,
						repositoryFileEntry.getFileVersion(), null,
						StringPool.BLANK),
					link.getHref());
			}
			else if (Objects.equals(
						expectedEntry.getKey(), "picklistFieldName")) {

				ListEntry listEntry =
					(ListEntry)actualObjectEntryProperties.get(
						expectedEntry.getKey());

				Assert.assertEquals(
					expectedEntry.getValue(), listEntry.getKey());
			}
			else if (Objects.equals(
						expectedEntry.getKey(), "richTextObjectFieldName")) {

				Assert.assertEquals(
					HtmlParserUtil.extractText(
						String.valueOf(expectedEntry.getValue())),
					String.valueOf(
						actualObjectEntryProperties.get(
							expectedEntry.getKey())));
			}
			else if (Objects.equals(
						expectedEntry.getKey(),
						"r_oneToManyRelationshipName_" +
							_objectDefinition1.getPKObjectFieldName())) {

				Assert.assertEquals(
					expectedEntry.getValue(),
					actualObjectEntryProperties.get(expectedEntry.getKey()));

				_assertEquals(
					(ObjectEntry)actualObjectEntryProperties.get(
						StringUtil.replaceLast(
							String.valueOf(expectedEntry.getKey()), "Id",
							StringPool.BLANK)),
					_getObjectEntry(
						_objectDefinition1,
						GetterUtil.getLong(expectedEntry.getValue())));
			}
			else if (Objects.equals("textFieldName", expectedEntry.getKey())) {
				Assert.assertEquals(
					expectedEntry.getValue(),
					actualObjectEntryProperties.get(expectedEntry.getKey()));
			}
		}
	}

	private String _buildEqualsExpressionFilterString(
		String fieldName, String value) {

		return StringBundler.concat("( ", fieldName, " eq '", value, "')");
	}

	private String _buildInExpressionFilterString(
		String fieldName, boolean includes, Object... values) {

		List<String> valuesList = new ArrayList<>();

		for (Object value : values) {
			valuesList.add(StringUtil.quote(String.valueOf(value)));
		}

		String filterString = StringBundler.concat(
			"(", fieldName, " in (",
			StringUtil.merge(valuesList, StringPool.COMMA_AND_SPACE), "))");

		if (includes) {
			return filterString;
		}

		return StringBundler.concat("(not ", filterString, ")");
	}

	private String _buildLambdaExpressionFilterString(
		String fieldName, boolean includes, int... values) {

		List<String> valuesList = new ArrayList<>();

		for (int value : values) {
			valuesList.add(
				StringBundler.concat(
					"(x ", includes ? "eq " : "ne ", String.valueOf(value),
					")"));
		}

		return StringBundler.concat(
			"(", fieldName, "/any(x:",
			StringUtil.merge(valuesList, includes ? " or " : " and "), "))");
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
			_simpleDTOConverterContext, objectDefinition, objectEntryId);
	}

	private Long _getOneToManyRelationshipFieldValue() throws Exception {
		ObjectEntry objectEntry = _objectEntryManager.addObjectEntry(
			_simpleDTOConverterContext, _objectDefinition1,
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

	private void _setUpDTOConverterContext() {
		UriInfo uriInfo = (UriInfo)ProxyUtil.newProxyInstance(
			DefaultObjectEntryManagerImplTest.class.getClassLoader(),
			new Class<?>[] {UriInfo.class},
			(proxy, method, arguments) -> {
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
					"Unsupported method " + method.getName());
			});

		_dtoConverterContext = new DefaultDTOConverterContext(
			false, Collections.emptyMap(), _dtoConverterRegistry, null,
			LocaleUtil.getDefault(), uriInfo, _user);
	}

	private void _testGetObjectEntries(
			Map<String, String> context, ObjectEntry... expectedObjectEntries)
		throws Exception {

		Sort[] sorts = null;

		if (context.containsKey("sort")) {
			String[] sort = StringUtil.split(context.get("sort"), ":");

			sorts = new Sort[] {
				SortFactoryUtil.create(sort[0], Objects.equals(sort[1], "desc"))
			};
		}

		Page<ObjectEntry> objectEntryPage =
			_objectEntryManager.getObjectEntries(
				_companyId, _objectDefinition2, null, null,
				_dtoConverterContext, null,
				_filterPredicateFactory.create(
					context.get("filter"),
					_objectDefinition2.getObjectDefinitionId()),
				context.get("search"), sorts);

		_assertEquals(
			ListUtil.fromArray(expectedObjectEntries),
			(List<ObjectEntry>)objectEntryPage.getItems());
	}

	private static long _companyId;
	private static DTOConverterContext _dtoConverterContext;

	@Inject
	private static DTOConverterRegistry _dtoConverterRegistry;

	@DeleteAfterTestRun
	private static Group _group;

	private static DTOConverterContext _simpleDTOConverterContext;
	private static User _user;

	@Inject
	private DLAppService _dlAppService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private DLURLHelper _dlURLHelper;

	@Inject
	private FilterPredicateFactory _filterPredicateFactory;

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