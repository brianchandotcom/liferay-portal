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

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountRoleLocalService;
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
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectFilterConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.NoSuchObjectEntryException;
import com.liferay.object.exception.ObjectRelationshipDeletionTypeException;
import com.liferay.object.exception.RequiredObjectRelationshipException;
import com.liferay.object.field.builder.AggregationObjectFieldBuilder;
import com.liferay.object.field.builder.AttachmentObjectFieldBuilder;
import com.liferay.object.field.builder.DateObjectFieldBuilder;
import com.liferay.object.field.builder.DecimalObjectFieldBuilder;
import com.liferay.object.field.builder.IntegerObjectFieldBuilder;
import com.liferay.object.field.builder.LongIntegerObjectFieldBuilder;
import com.liferay.object.field.builder.PicklistObjectFieldBuilder;
import com.liferay.object.field.builder.PrecisionDecimalObjectFieldBuilder;
import com.liferay.object.field.builder.RichTextObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.dto.v1_0.FileEntry;
import com.liferay.object.rest.dto.v1_0.Link;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.petra.sql.dsl.expression.FilterPredicateFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectFilterLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlParserUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.aggregation.Facet;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedFieldsContext;
import com.liferay.portal.vulcan.fields.NestedFieldsContextThreadLocal;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.math.BigDecimal;
import java.math.MathContext;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.hamcrest.CoreMatchers;

import org.junit.After;
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
@FeatureFlags("LPS-164801")
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
		_defaultObjectEntryManager =
			(DefaultObjectEntryManager)_objectEntryManager;
		_group = GroupTestUtil.addGroup();
		_originalName = PrincipalThreadLocal.getName();
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		_simpleDateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

		_adminUser = TestPropsValues.getUser();

		_simpleDTOConverterContext = new DefaultDTOConverterContext(
			false, Collections.emptyMap(), _dtoConverterRegistry, null,
			LocaleUtil.getDefault(), null, _adminUser);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		PrincipalThreadLocal.setName(_adminUser.getUserId());
	}

	@Before
	public void setUp() throws Exception {
		_dtoConverterContext = new DefaultDTOConverterContext(
			false, Collections.emptyMap(), _dtoConverterRegistry, null,
			LocaleUtil.getDefault(), null, _adminUser);

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

		_listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				null, _adminUser.getUserId(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				Collections.emptyList());

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
				new DateObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"dateObjectFieldName"
				).objectFieldSettings(
					Collections.emptyList()
				).build(),
				new DecimalObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"decimalObjectFieldName"
				).objectFieldSettings(
					Collections.emptyList()
				).build(),
				new IntegerObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"integerObjectFieldName"
				).objectFieldSettings(
					Collections.emptyList()
				).build(),
				new LongIntegerObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"longIntegerObjectFieldName"
				).objectFieldSettings(
					Collections.emptyList()
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
				new PrecisionDecimalObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"precisionDecimalObjectFieldName"
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

		ObjectRelationship objectRelationship1 =
			_objectRelationshipLocalService.addObjectRelationship(
				_adminUser.getUserId(),
				_objectDefinition1.getObjectDefinitionId(),
				_objectDefinition2.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"oneToManyRelationshipName",
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_addAggregationObjectField(
			"AVERAGE", "precisionDecimalObjectFieldName",
			objectRelationship1.getName());
		_addAggregationObjectField(
			"COUNT", null, objectRelationship1.getName());
		_addAggregationObjectField(
			"MAX", "integerObjectFieldName", objectRelationship1.getName());
		_addAggregationObjectField(
			"MIN", "longIntegerObjectFieldName", objectRelationship1.getName());
		_addAggregationObjectField(
			"SUM", "decimalObjectFieldName", objectRelationship1.getName());

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectRelationship1.getObjectFieldId2());

		_objectRelationshipERCObjectFieldName = ObjectFieldSettingUtil.getValue(
			ObjectFieldSettingConstants.
				NAME_OBJECT_RELATIONSHIP_ERC_OBJECT_FIELD_NAME,
			objectField);
		_objectRelationshipFieldName = objectField.getName();

		_objectDefinition3 =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				_adminUser.getUserId(), false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.emptyList());

		ObjectDefinition accountEntryObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				_companyId, "AccountEntry");

		ObjectRelationship objectRelationship2 =
			_objectRelationshipLocalService.addObjectRelationship(
				_adminUser.getUserId(),
				accountEntryObjectDefinition.getObjectDefinitionId(),
				_objectDefinition3.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"oneToManyRelationshipName",
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_objectDefinition3.setAccountEntryRestrictedObjectFieldId(
			objectRelationship2.getObjectFieldId2());

		_objectDefinition3.setAccountEntryRestricted(true);

		_objectDefinition3 =
			_objectDefinitionLocalService.updateObjectDefinition(
				_objectDefinition3);

		_objectDefinition3 =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				_adminUser.getUserId(),
				_objectDefinition3.getObjectDefinitionId());

		_accountAdministratorRole = _roleLocalService.getRole(
			_companyId,
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR);
		_accountManagerRole = _roleLocalService.getRole(
			_companyId,
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MANAGER);
		_buyerRole = _roleLocalService.getRole(_companyId, "Buyer");

		_originalNestedFieldsContext =
			NestedFieldsContextThreadLocal.getNestedFieldsContext();

		NestedFieldsContextThreadLocal.setNestedFieldsContext(
			new NestedFieldsContext(
				1,
				Arrays.asList(
					StringUtil.removeLast(
						StringUtil.removeFirst(
							_objectDefinition1.getPKObjectFieldName(), "c_"),
						"Id")),
				null, null, null, null));
	}

	@After
	public void tearDown() throws Exception {
		NestedFieldsContextThreadLocal.setNestedFieldsContext(
			_originalNestedFieldsContext);

		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	public void testAddObjectEntry() throws Exception {

		// Aggregation field with filters

		ObjectEntry parentObjectEntry1 =
			_defaultObjectEntryManager.addObjectEntry(
				_simpleDTOConverterContext, _objectDefinition1,
				new ObjectEntry() {
					{
						properties = HashMapBuilder.<String, Object>put(
							"externalReferenceCode", "newExternalReferenceCode"
						).put(
							"textObjectFieldName", RandomTestUtil.randomString()
						).build();
					}
				},
				ObjectDefinitionConstants.SCOPE_COMPANY);

		ObjectEntry childObjectEntry1 =
			_defaultObjectEntryManager.addObjectEntry(
				_dtoConverterContext, _objectDefinition2,
				new ObjectEntry() {
					{
						properties = HashMapBuilder.<String, Object>put(
							_objectRelationshipERCObjectFieldName,
							"newExternalReferenceCode"
						).put(
							"dateObjectFieldName", "2020-01-02"
						).put(
							"decimalObjectFieldName", 15.7
						).put(
							"integerObjectFieldName", 15
						).put(
							"longIntegerObjectFieldName", 100L
						).put(
							"picklistObjectFieldName", _addListTypeEntry()
						).put(
							"precisionDecimalObjectFieldName",
							new BigDecimal(
								0.9876543217654321, MathContext.DECIMAL64)
						).build();
					}
				},
				ObjectDefinitionConstants.SCOPE_COMPANY);

		String listTypeEntryKey = _addListTypeEntry();

		ObjectEntry childObjectEntry2 =
			_defaultObjectEntryManager.addObjectEntry(
				_dtoConverterContext, _objectDefinition2,
				new ObjectEntry() {
					{
						properties = HashMapBuilder.<String, Object>put(
							_objectRelationshipERCObjectFieldName,
							"newExternalReferenceCode"
						).put(
							"attachmentObjectFieldName",
							_getAttachmentObjectFieldValue()
						).put(
							"dateObjectFieldName", "2022-01-01"
						).put(
							"decimalObjectFieldName", 15.5
						).put(
							"integerObjectFieldName", 10
						).put(
							"longIntegerObjectFieldName", 50000L
						).put(
							"picklistObjectFieldName", listTypeEntryKey
						).put(
							"precisionDecimalObjectFieldName",
							new BigDecimal(
								0.1234567891234567, MathContext.DECIMAL64)
						).put(
							"richTextObjectFieldName",
							StringBundler.concat(
								"<i>", RandomTestUtil.randomString(), "</i>")
						).put(
							"textObjectFieldName", RandomTestUtil.randomString()
						).build();
					}
				},
				ObjectDefinitionConstants.SCOPE_COMPANY);

		// Aggregation field with filter (date range with date and time)

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			_objectDefinition1.getObjectDefinitionId(),
			"countAggregationObjectFieldName");

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd");

		String currentDateString = dateFormat.format(new Date());

		_objectFilterLocalService.addObjectFilter(
			_adminUser.getUserId(), objectField.getObjectFieldId(),
			"createDate", ObjectFilterConstants.TYPE_DATE_RANGE,
			StringBundler.concat(
				"{\"le\": \"", currentDateString, "\", \"ge\": \"",
				currentDateString, "\"}"));
		_objectFilterLocalService.addObjectFilter(
			_adminUser.getUserId(), objectField.getObjectFieldId(),
			"modifiedDate", ObjectFilterConstants.TYPE_DATE_RANGE,
			StringBundler.concat(
				"{\"le\": \"", currentDateString, "\", \"ge\": \"",
				currentDateString, "\"}"));

		_assertCountAggregationObjectFieldValue(2, parentObjectEntry1);

		// Aggregation field with filter (date range with date only)

		_objectFilterLocalService.addObjectFilter(
			_adminUser.getUserId(), objectField.getObjectFieldId(),
			"dateObjectFieldName", ObjectFilterConstants.TYPE_DATE_RANGE,
			"{\"le\": \"2020-01-02\", \"ge\": \"2020-01-02\"}");

		_assertCountAggregationObjectFieldValue(1, parentObjectEntry1);

		// Aggregation field with filter (equals and not equals)

		_objectFilterLocalService.addObjectFilter(
			_adminUser.getUserId(), objectField.getObjectFieldId(),
			"integerObjectFieldName", ObjectFilterConstants.TYPE_EQUALS,
			"{\"eq\": \"15\"}");

		_assertCountAggregationObjectFieldValue(1, parentObjectEntry1);

		_objectFilterLocalService.addObjectFilter(
			_adminUser.getUserId(), objectField.getObjectFieldId(),
			"integerObjectFieldName", ObjectFilterConstants.TYPE_NOT_EQUALS,
			"{\"ne\":\"15\"}");

		_assertCountAggregationObjectFieldValue(0, parentObjectEntry1);

		_objectFilterLocalService.deleteObjectFieldObjectFilter(
			objectField.getObjectFieldId());

		// Aggregation field with filter (excludes and includes with a string)

		_objectFilterLocalService.addObjectFilter(
			_adminUser.getUserId(), objectField.getObjectFieldId(),
			"picklistObjectFieldName", ObjectFilterConstants.TYPE_EXCLUDES,
			"{\"not\":{\"in\":[\"" + listTypeEntryKey + "\"]}}");

		_assertCountAggregationObjectFieldValue(1, parentObjectEntry1);

		_objectFilterLocalService.addObjectFilter(
			_adminUser.getUserId(), objectField.getObjectFieldId(),
			"picklistObjectFieldName", ObjectFilterConstants.TYPE_INCLUDES,
			"{\"in\":[\"" + listTypeEntryKey + "\"]}");

		_assertCountAggregationObjectFieldValue(0, parentObjectEntry1);

		_objectFilterLocalService.deleteObjectFieldObjectFilter(
			objectField.getObjectFieldId());

		// Aggregation field with filter (excludes and includes with an int)

		_objectFilterLocalService.addObjectFilter(
			_adminUser.getUserId(), objectField.getObjectFieldId(), "status",
			ObjectFilterConstants.TYPE_EXCLUDES,
			"{\"not\":{\"in\": [" + WorkflowConstants.STATUS_APPROVED + "]}}");

		_assertCountAggregationObjectFieldValue(0, parentObjectEntry1);

		_objectFilterLocalService.deleteObjectFieldObjectFilter(
			objectField.getObjectFieldId());

		_objectFilterLocalService.addObjectFilter(
			_adminUser.getUserId(), objectField.getObjectFieldId(), "status",
			ObjectFilterConstants.TYPE_INCLUDES,
			"{\"in\": [" + WorkflowConstants.STATUS_APPROVED + "]}");

		_assertCountAggregationObjectFieldValue(2, parentObjectEntry1);

		_objectFilterLocalService.deleteObjectFieldObjectFilter(
			objectField.getObjectFieldId());

		// Aggregation field without filters

		_assertEquals(
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"averageAggregationObjectFieldName",
						"0.55555555544444440000"
					).put(
						"countAggregationObjectFieldName", "2"
					).put(
						"maxAggregationObjectFieldName", "15"
					).put(
						"minAggregationObjectFieldName", "100"
					).put(
						"sumAggregationObjectFieldName", "31.2"
					).put(
						"textObjectFieldName",
						MapUtil.getString(
							parentObjectEntry1.getProperties(),
							"textObjectFieldName")
					).build();
				}
			},
			_defaultObjectEntryManager.getObjectEntry(
				_simpleDTOConverterContext, _objectDefinition1,
				parentObjectEntry1.getId()));

		_defaultObjectEntryManager.deleteObjectEntry(
			_objectDefinition2, childObjectEntry1.getId());

		_assertEquals(
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"averageAggregationObjectFieldName",
						"0.12345678912345670000"
					).put(
						"countAggregationObjectFieldName", "1"
					).put(
						"maxAggregationObjectFieldName", "10"
					).put(
						"minAggregationObjectFieldName", "50000"
					).put(
						"sumAggregationObjectFieldName", "15.5"
					).put(
						"textObjectFieldName",
						MapUtil.getString(
							parentObjectEntry1.getProperties(),
							"textObjectFieldName")
					).build();
				}
			},
			_defaultObjectEntryManager.getObjectEntry(
				_simpleDTOConverterContext, _objectDefinition1,
				parentObjectEntry1.getId()));

		_defaultObjectEntryManager.deleteObjectEntry(
			_objectDefinition2, childObjectEntry2.getId());

		_assertEquals(
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"averageAggregationObjectFieldName", "0"
					).put(
						"countAggregationObjectFieldName", "0"
					).put(
						"maxAggregationObjectFieldName", "0"
					).put(
						"minAggregationObjectFieldName", "0"
					).put(
						"sumAggregationObjectFieldName", "0"
					).put(
						"textObjectFieldName",
						MapUtil.getString(
							parentObjectEntry1.getProperties(),
							"textObjectFieldName")
					).build();
				}
			},
			_defaultObjectEntryManager.getObjectEntry(
				_simpleDTOConverterContext, _objectDefinition1,
				parentObjectEntry1.getId()));

		// Picklist by list entry

		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.addListTypeEntry(
				null, _adminUser.getUserId(),
				_listTypeDefinition.getListTypeDefinitionId(),
				RandomTestUtil.randomString(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()));

		ListEntry listEntry = new ListEntry() {
			{
				key = listTypeEntry.getKey();
				name = listTypeEntry.getName(LocaleUtil.US);
			}
		};

		_assertPicklistOjectField(listEntry, listEntry);

		// Picklist by list type entry key

		_assertPicklistOjectField(listEntry, listTypeEntry.getKey());

		// Picklist by map

		_assertPicklistOjectField(
			listEntry,
			HashMapBuilder.put(
				"key", listTypeEntry.getKey()
			).put(
				"name", listTypeEntry.getName(LocaleUtil.US)
			).build());
	}

	@Test
	public void testAddObjectEntryAccountEntryRestriction() throws Exception {

		// Account entry scope

		AccountEntry accountEntry1 = _addAccountEntry();
		_user = _addUser();

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry1.getAccountEntryId(), _user.getUserId());

		_assertFailure(
			StringBundler.concat(
				"User ", _user.getUserId(),
				" must have ADD_OBJECT_ENTRY permission for ",
				_objectDefinition3.getResourceName(), StringPool.SPACE),
			() -> _addObjectEntry(accountEntry1));

		_addResourcePermission("ADD_OBJECT_ENTRY", _buyerRole);

		_userGroupRoleLocalService.addUserGroupRole(
			_user.getUserId(), accountEntry1.getAccountEntryGroupId(),
			_buyerRole.getRoleId());

		Assert.assertNotNull(_addObjectEntry(accountEntry1));

		AccountEntry accountEntry2 = _addAccountEntry();

		_assertFailure(
			StringBundler.concat(
				"User ", _user.getUserId(),
				" does not have access to account entry ",
				accountEntry2.getAccountEntryId()),
			() -> _addObjectEntry(accountEntry2));

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry2.getAccountEntryId(), _user.getUserId());

		_assertFailure(
			StringBundler.concat(
				"User ", _user.getUserId(),
				" must have ADD_OBJECT_ENTRY permission for ",
				_objectDefinition3.getResourceName(), StringPool.SPACE),
			() -> _addObjectEntry(accountEntry2));

		_userGroupRoleLocalService.addUserGroupRole(
			_user.getUserId(), accountEntry2.getAccountEntryGroupId(),
			_buyerRole.getRoleId());

		Assert.assertNotNull(_addObjectEntry(accountEntry1));

		// Organization scope

		Organization organization1 = OrganizationTestUtil.addOrganization();

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry1.getAccountEntryId(),
			organization1.getOrganizationId());

		_user = _addUser();

		_assignOrganizationRole(organization1, _accountManagerRole, _user);

		_assertFailure(
			StringBundler.concat(
				"User ", _user.getUserId(),
				" must have ADD_OBJECT_ENTRY permission for ",
				_objectDefinition3.getResourceName(), StringPool.SPACE),
			() -> _addObjectEntry(accountEntry1));

		_addResourcePermission("ADD_OBJECT_ENTRY", _accountManagerRole);

		_addObjectEntry(accountEntry1);

		_assertFailure(
			StringBundler.concat(
				"User ", _user.getUserId(),
				" does not have access to account entry ",
				accountEntry2.getAccountEntryId()),
			() -> _addObjectEntry(accountEntry2));

		_removeResourcePermission("ADD_OBJECT_ENTRY", _accountManagerRole);

		// Suborganization scope

		_accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRel(
				accountEntry1.getAccountEntryId(),
				organization1.getOrganizationId());

		Organization suborganization1 = OrganizationTestUtil.addOrganization(
			organization1.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry1.getAccountEntryId(),
			suborganization1.getOrganizationId());

		Organization organization2 = OrganizationTestUtil.addOrganization();

		Organization suborganization2 = OrganizationTestUtil.addOrganization(
			organization2.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry2.getAccountEntryId(),
			suborganization2.getOrganizationId());

		_user = _addUser();

		_assignOrganizationRole(organization1, _accountManagerRole, _user);

		_assertFailure(
			StringBundler.concat(
				"User ", _user.getUserId(),
				" must have ADD_OBJECT_ENTRY permission for ",
				_objectDefinition3.getResourceName(), StringPool.SPACE),
			() -> _addObjectEntry(accountEntry1));

		_addResourcePermission("ADD_OBJECT_ENTRY", _accountManagerRole);

		_addObjectEntry(accountEntry1);

		_assertFailure(
			StringBundler.concat(
				"User ", _user.getUserId(),
				" does not have access to account entry ",
				accountEntry2.getAccountEntryId()),
			() -> _addObjectEntry(accountEntry2));
	}

	@Test
	public void testDeleteObjectEntryAccountEntryRestriction()
		throws Exception {

		// Regular roles' company scope permissions should not be restricted by
		// account entry

		AccountEntry accountEntry1 = _addAccountEntry();

		ObjectEntry objectEntry1 = _addObjectEntry(accountEntry1);

		AccountEntry accountEntry2 = _addAccountEntry();

		ObjectEntry objectEntry2 = _addObjectEntry(accountEntry2);

		_user = _addUser();

		Role role = _addRoleUser(
			new String[] {ActionKeys.DELETE, ActionKeys.VIEW},
			_objectDefinition3, _user);

		_defaultObjectEntryManager.deleteObjectEntry(
			_objectDefinition3, objectEntry1.getId());

		_resourcePermissionLocalService.removeResourcePermission(
			_companyId, _objectDefinition3.getClassName(),
			ResourceConstants.SCOPE_COMPANY, String.valueOf(_companyId),
			role.getRoleId(), ActionKeys.DELETE);

		try {
			_defaultObjectEntryManager.deleteObjectEntry(
				_objectDefinition3, objectEntry2.getId());

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertEquals(
				exception.getMessage(),
				StringBundler.concat(
					"User ", _user.getUserId(),
					" must have DELETE permission for ",
					_objectDefinition3.getClassName(), StringPool.SPACE,
					objectEntry2.getId()));
		}

		// Regular roles' individual permissions should not be restricted by
		// account entry

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		PrincipalThreadLocal.setName(_adminUser.getUserId());

		objectEntry1 = _addObjectEntry(accountEntry1);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		PrincipalThreadLocal.setName(_user.getUserId());

		_resourcePermissionLocalService.setResourcePermissions(
			_companyId, _objectDefinition3.getClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(objectEntry1.getId()), role.getRoleId(),
			new String[] {ActionKeys.DELETE});

		_defaultObjectEntryManager.deleteObjectEntry(
			_objectDefinition3, objectEntry1.getId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		PrincipalThreadLocal.setName(_adminUser.getUserId());

		objectEntry1 = _addObjectEntry(accountEntry1);

		// Account entry scope

		_user = _addUser();

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry1.getAccountEntryId(), _user.getUserId());
		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry2.getAccountEntryId(), _user.getUserId());

		_addResourcePermission(ActionKeys.DELETE, _accountAdministratorRole);
		_addResourcePermission(ActionKeys.VIEW, _accountAdministratorRole);

		_userGroupRoleLocalService.addUserGroupRole(
			_user.getUserId(), accountEntry1.getAccountEntryGroupId(),
			_accountAdministratorRole.getRoleId());

		_addResourcePermission(ActionKeys.VIEW, _buyerRole);

		_userGroupRoleLocalService.addUserGroupRole(
			_user.getUserId(), accountEntry2.getAccountEntryGroupId(),
			_buyerRole.getRoleId());

		_defaultObjectEntryManager.deleteObjectEntry(
			_objectDefinition3, objectEntry1.getId());

		try {
			_defaultObjectEntryManager.deleteObjectEntry(
				_objectDefinition3, objectEntry2.getId());

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertEquals(
				exception.getMessage(),
				StringBundler.concat(
					"User ", _user.getUserId(),
					" must have DELETE permission for ",
					_objectDefinition3.getClassName(), StringPool.SPACE,
					objectEntry2.getId()));
		}

		// Organization scope

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		PrincipalThreadLocal.setName(_adminUser.getUserId());

		objectEntry1 = _addObjectEntry(accountEntry1);

		_user = _addUser();

		Organization organization1 = OrganizationTestUtil.addOrganization();

		_addResourcePermission(ActionKeys.VIEW, _accountManagerRole);

		_assignOrganizationRole(organization1, _accountManagerRole, _user);

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry1.getAccountEntryId(),
			organization1.getOrganizationId());

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry2.getAccountEntryId(),
			organization2.getOrganizationId());

		_assertObjectEntriesSize(1);

		try {
			_defaultObjectEntryManager.deleteObjectEntry(
				_objectDefinition3, objectEntry1.getId());

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertEquals(
				exception.getMessage(),
				StringBundler.concat(
					"User ", _user.getUserId(), " must have DELETE permission ",
					"for ", _objectDefinition3.getClassName(), StringPool.SPACE,
					objectEntry1.getId()));
		}

		_assertObjectEntriesSize(1);

		_addResourcePermission(ActionKeys.DELETE, _accountManagerRole);

		_defaultObjectEntryManager.deleteObjectEntry(
			_objectDefinition3, objectEntry1.getId());

		_assertObjectEntriesSize(0);

		_accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRel(
				accountEntry1.getAccountEntryId(),
				organization1.getOrganizationId());

		_accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRel(
				accountEntry2.getAccountEntryId(),
				organization2.getOrganizationId());

		// Suborganization scope

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		PrincipalThreadLocal.setName(_adminUser.getUserId());

		objectEntry1 = _addObjectEntry(accountEntry1);

		_user = _addUser();

		_assignOrganizationRole(organization1, _accountManagerRole, _user);

		Organization suborganization1 = OrganizationTestUtil.addOrganization(
			organization1.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry1.getAccountEntryId(),
			suborganization1.getOrganizationId());

		Organization suborganization2 = OrganizationTestUtil.addOrganization(
			organization2.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry2.getAccountEntryId(),
			suborganization2.getOrganizationId());

		_assertObjectEntriesSize(1);

		_removeResourcePermission(ActionKeys.DELETE, _accountManagerRole);

		try {
			_defaultObjectEntryManager.deleteObjectEntry(
				_objectDefinition3, objectEntry1.getId());

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertEquals(
				exception.getMessage(),
				StringBundler.concat(
					"User ", _user.getUserId(), " must have DELETE permission ",
					"for ", _objectDefinition3.getClassName(), StringPool.SPACE,
					objectEntry1.getId()));
		}

		_assertObjectEntriesSize(1);

		_addResourcePermission(ActionKeys.DELETE, _accountManagerRole);

		_defaultObjectEntryManager.deleteObjectEntry(
			_objectDefinition3, objectEntry1.getId());

		_assertObjectEntriesSize(0);
	}

	@Test
	public void testDeleteObjectEntryForAllObjectRelationshipDeletionTypes()
		throws Exception {

		ObjectDefinition objectDefinition1 = _createObjectDefinition(
			Collections.singletonList(
				new TextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"textObjectFieldName"
				).objectFieldSettings(
					Collections.emptyList()
				).build()));
		ObjectDefinition objectDefinition2 = _createObjectDefinition(
			Collections.singletonList(
				new TextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"a" + RandomTestUtil.randomString()
				).objectFieldSettings(
					Collections.emptyList()
				).build()));

		// Relationship type cascade

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				_adminUser.getUserId(),
				objectDefinition1.getObjectDefinitionId(),
				objectDefinition2.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"oneToManyRelationship",
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_addRelatedObjectEntries(
			objectDefinition1, objectDefinition2, "externalReferenceCode1",
			"externalReferenceCode2", objectRelationship);

		_user = _addUser();

		Role role = _addRoleUser(
			new String[] {
				ActionKeys.DELETE, ActionKeys.PERMISSIONS, ActionKeys.UPDATE,
				ActionKeys.VIEW
			},
			objectDefinition1, _user);

		try {
			_defaultObjectEntryManager.deleteObjectEntry(
				_companyId, _simpleDTOConverterContext,
				"externalReferenceCode1", objectDefinition1, null);

			Assert.fail();
		}
		catch (ObjectRelationshipDeletionTypeException
					objectRelationshipDeletionTypeException) {

			Assert.assertThat(
				objectRelationshipDeletionTypeException.getMessage(),
				CoreMatchers.containsString(
					StringBundler.concat(
						"User ", _user.getUserId(),
						" must have DELETE permission for ",
						objectDefinition2.getClassName())));
		}

		// Relationship type disassociate

		objectRelationship =
			_objectRelationshipLocalService.updateObjectRelationship(
				objectRelationship.getObjectRelationshipId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE,
				objectRelationship.getLabelMap());

		_defaultObjectEntryManager.deleteObjectEntry(
			_companyId, _simpleDTOConverterContext, "externalReferenceCode1",
			objectDefinition1, null);

		try {
			_defaultObjectEntryManager.getObjectEntry(
				_companyId, _simpleDTOConverterContext,
				"externalReferenceCode1", objectDefinition1, null);

			Assert.fail();
		}
		catch (NoSuchObjectEntryException noSuchObjectEntryException) {
			Assert.assertNotNull(noSuchObjectEntryException);
		}

		PrincipalThreadLocal.setName(_adminUser.getUserId());
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		Assert.assertNotNull(
			_defaultObjectEntryManager.getObjectEntry(
				_companyId, _simpleDTOConverterContext,
				"externalReferenceCode2", objectDefinition2, null));

		_addRelatedObjectEntries(
			objectDefinition1, objectDefinition2, "externalReferenceCode3",
			"externalReferenceCode4", objectRelationship);

		PrincipalThreadLocal.setName(_user.getUserId());
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		// Relationshp type prevent

		objectRelationship =
			_objectRelationshipLocalService.updateObjectRelationship(
				objectRelationship.getObjectRelationshipId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				objectRelationship.getLabelMap());

		try {
			_defaultObjectEntryManager.deleteObjectEntry(
				_companyId, _simpleDTOConverterContext,
				"externalReferenceCode3", objectDefinition1, null);

			Assert.fail();
		}
		catch (RequiredObjectRelationshipException
					requiredObjectRelationshipException) {

			Assert.assertEquals(
				StringBundler.concat(
					"Object relationship ",
					objectRelationship.getObjectRelationshipId(),
					" does not allow deletes"),
				requiredObjectRelationshipException.getMessage());
		}

		_roleLocalService.deleteRole(role.getRoleId());

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship.getObjectRelationshipId());

		_objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinition1.getObjectDefinitionId());
		_objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinition2.getObjectDefinitionId());
	}

	@Test
	public void testGetObjectEntries() throws Exception {
		_testGetObjectEntries(Collections.emptyMap());

		String picklistObjectFieldValue1 = _addListTypeEntry();

		ObjectEntry parentObjectEntry1 =
			_defaultObjectEntryManager.addObjectEntry(
				_simpleDTOConverterContext, _objectDefinition1,
				new ObjectEntry() {
					{
						properties = HashMapBuilder.<String, Object>put(
							"textObjectFieldName", "Able"
						).build();
					}
				},
				ObjectDefinitionConstants.SCOPE_COMPANY);

		ObjectEntry childObjectEntry1 =
			_defaultObjectEntryManager.addObjectEntry(
				_dtoConverterContext, _objectDefinition2,
				new ObjectEntry() {
					{
						properties = HashMapBuilder.<String, Object>put(
							_objectRelationshipFieldName,
							parentObjectEntry1.getId()
						).put(
							"picklistObjectFieldName", picklistObjectFieldValue1
						).put(
							"textObjectFieldName", "aaa"
						).build();
					}
				},
				ObjectDefinitionConstants.SCOPE_COMPANY);

		ObjectEntry parentObjectEntry2 =
			_defaultObjectEntryManager.addObjectEntry(
				_simpleDTOConverterContext, _objectDefinition1,
				new ObjectEntry() {
					{
						properties = HashMapBuilder.<String, Object>put(
							"textObjectFieldName", RandomTestUtil.randomString()
						).build();
					}
				},
				ObjectDefinitionConstants.SCOPE_COMPANY);

		String picklistObjectFieldValue2 = _addListTypeEntry();

		ObjectEntry childObjectEntry2 =
			_defaultObjectEntryManager.addObjectEntry(
				_dtoConverterContext, _objectDefinition2,
				new ObjectEntry() {
					{
						properties = HashMapBuilder.<String, Object>put(
							_objectRelationshipFieldName,
							parentObjectEntry2.getId()
						).put(
							"picklistObjectFieldName", picklistObjectFieldValue2
						).put(
							"textObjectFieldName", "aab"
						).build();
					}
				},
				ObjectDefinitionConstants.SCOPE_COMPANY);

		// And/or with parentheses

		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				StringBundler.concat(
					_buildEqualsExpressionFilterString(
						"picklistObjectFieldName", picklistObjectFieldValue1),
					" and (",
					_buildEqualsExpressionFilterString(
						"picklistObjectFieldName", picklistObjectFieldValue1),
					" or ",
					_buildEqualsExpressionFilterString(
						"picklistObjectFieldName", picklistObjectFieldValue2),
					")")
			).build(),
			childObjectEntry1);

		// Contains expression

		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter", _buildContainsExpressionFilterString("id", "aaaa")
			).build());

		// Equals expression

		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildEqualsExpressionFilterString(
					"picklistObjectFieldName", picklistObjectFieldValue1)
			).put(
				"search", "aa"
			).build(),
			childObjectEntry1);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildEqualsExpressionFilterString(
					"picklistObjectFieldName", picklistObjectFieldValue2)
			).put(
				"search", "aa"
			).build(),
			childObjectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildEqualsExpressionFilterString(
					_objectRelationshipERCObjectFieldName,
					parentObjectEntry1.getExternalReferenceCode())
			).build(),
			childObjectEntry1);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildEqualsExpressionFilterString(
					_objectRelationshipERCObjectFieldName,
					parentObjectEntry2.getExternalReferenceCode())
			).build(),
			childObjectEntry2);

		// In expression

		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildInExpressionFilterString(
					"id", true, childObjectEntry1.getId())
			).build(),
			childObjectEntry1);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildInExpressionFilterString(
					"id", false, childObjectEntry1.getId())
			).build(),
			childObjectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildInExpressionFilterString(
					"picklistObjectFieldName", true, picklistObjectFieldValue1)
			).build(),
			childObjectEntry1);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildInExpressionFilterString(
					"picklistObjectFieldName", false, picklistObjectFieldValue1)
			).build(),
			childObjectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildInExpressionFilterString(
					_objectRelationshipERCObjectFieldName, true,
					parentObjectEntry1.getExternalReferenceCode())
			).build(),
			childObjectEntry1);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildInExpressionFilterString(
					_objectRelationshipERCObjectFieldName, false,
					parentObjectEntry1.getExternalReferenceCode())
			).build(),
			childObjectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildInExpressionFilterString(
					_objectRelationshipFieldName.substring(
						_objectRelationshipFieldName.lastIndexOf("_") + 1),
					true, parentObjectEntry1.getId())
			).build(),
			childObjectEntry1);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildInExpressionFilterString(
					_objectRelationshipFieldName.substring(
						_objectRelationshipFieldName.lastIndexOf("_") + 1),
					false, parentObjectEntry1.getId())
			).build(),
			childObjectEntry2);

		// Lambda expression

		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildEqualsExpressionFilterString(
					"creatorId", _adminUser.getUserId())
			).build(),
			childObjectEntry1, childObjectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildLambdaExpressionFilterString(
					"status", true, WorkflowConstants.STATUS_APPROVED)
			).build(),
			childObjectEntry1, childObjectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildLambdaExpressionFilterString(
					"status", false, WorkflowConstants.STATUS_APPROVED)
			).build());

		// Range expression

		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildRangeExpression(
					childObjectEntry1.getDateCreated(), new Date(),
					"dateCreated")
			).build(),
			childObjectEntry1, childObjectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildRangeExpression(
					childObjectEntry1.getDateModified(), new Date(),
					"dateModified")
			).build(),
			childObjectEntry1, childObjectEntry2);

		_testGetObjectEntries(
			HashMapBuilder.put(
				"search", String.valueOf(childObjectEntry1.getId())
			).build(),
			childObjectEntry1);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"search", String.valueOf(childObjectEntry2.getId())
			).build(),
			childObjectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"search", picklistObjectFieldValue1
			).build(),
			childObjectEntry1);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"search", picklistObjectFieldValue2
			).build(),
			childObjectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"search", "aa"
			).build(),
			childObjectEntry1, childObjectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"sort", "createDate:asc"
			).build(),
			childObjectEntry1, childObjectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"sort", "createDate:desc"
			).build(),
			childObjectEntry2, childObjectEntry1);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"sort", "id:asc"
			).build(),
			childObjectEntry1, childObjectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"sort", "id:desc"
			).build(),
			childObjectEntry2, childObjectEntry1);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"sort", "textObjectFieldName:asc"
			).build(),
			childObjectEntry1, childObjectEntry2);
		_testGetObjectEntries(
			HashMapBuilder.put(
				"sort", "textObjectFieldName:desc"
			).build(),
			childObjectEntry2, childObjectEntry1);
	}

	@Test
	public void testGetObjectEntriesAccountEntryRestrictions()
		throws Exception {

		// Regular roles permissions should not be restricted by account entry

		AccountEntry accountEntry1 = _addAccountEntry();

		ObjectEntry objectEntry1 = _addObjectEntry(accountEntry1);

		AccountEntry accountEntry2 = _addAccountEntry();

		_addObjectEntry(accountEntry2);

		_user = _addUser();

		_assertObjectEntriesSize(0);

		Role role = _addRoleUser(
			new String[] {ActionKeys.VIEW}, _objectDefinition3, _user);

		_assertObjectEntriesSize(2);

		_resourcePermissionLocalService.removeResourcePermission(
			_companyId, _objectDefinition3.getClassName(),
			ResourceConstants.SCOPE_COMPANY, String.valueOf(_companyId),
			role.getRoleId(), ActionKeys.VIEW);

		// Regular roles' individual permissions should not be restricted by
		// account entry

		_resourcePermissionLocalService.setResourcePermissions(
			_companyId, _objectDefinition3.getClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(objectEntry1.getId()), role.getRoleId(),
			new String[] {ActionKeys.VIEW});

		_assertObjectEntriesSize(1);

		_userLocalService.deleteRoleUser(role.getRoleId(), _user);

		_assertObjectEntriesSize(0);

		// User should be able to view object entries for account entry 1
		// because he is a member of account entry 1

		Assert.assertTrue(
			AccountRoleConstants.isSharedRole(_accountAdministratorRole));

		AccountEntryUserRel accountEntryUserRel =
			_accountEntryUserRelLocalService.addAccountEntryUserRel(
				accountEntry1.getAccountEntryId(), _user.getUserId());

		_assertObjectEntriesSize(1);

		_accountEntryUserRelLocalService.deleteAccountEntryUserRel(
			accountEntryUserRel);

		_assertObjectEntriesSize(0);

		// User should be able to view object entries for account entry 1 and
		// account entry 2 because he is a member of an organization that
		// contains account entry 1 and account entry 2.

		Organization organization1 = OrganizationTestUtil.addOrganization();

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry1.getAccountEntryId(),
			organization1.getOrganizationId());
		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry2.getAccountEntryId(),
			organization1.getOrganizationId());

		_user = _addUser();

		_organizationLocalService.addUserOrganization(
			_user.getUserId(), organization1.getOrganizationId());

		_assertObjectEntriesSize(2);

		_accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRel(
				accountEntry2.getAccountEntryId(),
				organization1.getOrganizationId());

		_assertObjectEntriesSize(1);

		_organizationLocalService.deleteUserOrganization(
			_user.getUserId(), organization1.getOrganizationId());

		_assertObjectEntriesSize(0);

		_accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRel(
				accountEntry1.getAccountEntryId(),
				organization1.getOrganizationId());

		// Check suborganizations

		Organization suborganization1 = OrganizationTestUtil.addOrganization(
			organization1.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry1.getAccountEntryId(),
			suborganization1.getOrganizationId());

		Organization organization2 = OrganizationTestUtil.addOrganization();

		Organization suborganization2 = OrganizationTestUtil.addOrganization(
			organization2.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry2.getAccountEntryId(),
			suborganization2.getOrganizationId());

		_user = _addUser();

		_organizationLocalService.addUserOrganization(
			_user.getUserId(), organization1.getOrganizationId());

		_assertObjectEntriesSize(1);

		_organizationLocalService.addUserOrganization(
			_user.getUserId(), suborganization2.getOrganizationId());

		_assertObjectEntriesSize(2);

		_organizationLocalService.deleteUserOrganization(
			_user.getUserId(), suborganization2.getOrganizationId());

		_assertObjectEntriesSize(1);

		_organizationLocalService.deleteUserOrganization(
			_user.getUserId(), organization1.getOrganizationId());

		_assertObjectEntriesSize(0);
	}

	@Test
	public void testGetObjectEntriesAggregationFacets() throws Exception {
		_defaultObjectEntryManager.addObjectEntry(
			_simpleDTOConverterContext, _objectDefinition1,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"textObjectFieldName", "Able"
					).build();
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);

		_defaultObjectEntryManager.addObjectEntry(
			_simpleDTOConverterContext, _objectDefinition1,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"textObjectFieldName", "Able"
					).build();
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);

		_user = _addUser();

		_addRoleUser(new String[] {ActionKeys.VIEW}, _objectDefinition1, _user);

		Aggregation aggregation = new Aggregation() {
			{
				setAggregationTerms(
					HashMapBuilder.put(
						"textObjectFieldName", "Able"
					).build());
			}
		};

		Page<ObjectEntry> page = _defaultObjectEntryManager.getObjectEntries(
			_companyId, _objectDefinition1, null, aggregation,
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), _dtoConverterRegistry, null,
				LocaleUtil.getDefault(), null, _user),
			StringPool.BLANK, null, null, null);

		List<Facet> facets = page.getFacets();

		Assert.assertFalse(ListUtil.isEmpty(facets));

		Facet facet = facets.get(0);

		List<Facet.FacetValue> facetValues = ListUtil.filter(
			facet.getFacetValues(),
			facetValue -> Objects.equals(facetValue.getTerm(), "Able"));

		Assert.assertFalse(ListUtil.isEmpty(facetValues));

		Facet.FacetValue facetValue = facetValues.get(0);

		Assert.assertEquals(facetValue.getNumberOfOccurrences(), (Integer)2);
	}

	@Test
	public void testUpdateObjectEntryAccountEntryRestriction()
		throws Exception {

		// Regular roles' company scope permissions should not be restricted by
		// account entry

		AccountEntry accountEntry1 = _addAccountEntry();

		ObjectEntry objectEntry1 = _addObjectEntry(accountEntry1);

		AccountEntry accountEntry2 = _addAccountEntry();

		ObjectEntry objectEntry2 = _addObjectEntry(accountEntry2);

		_user = _addUser();

		Role role = _addRoleUser(
			new String[] {ActionKeys.UPDATE, ActionKeys.VIEW},
			_objectDefinition3, _user);

		_defaultObjectEntryManager.updateObjectEntry(
			_simpleDTOConverterContext, _objectDefinition3,
			objectEntry1.getId(), objectEntry1);

		_resourcePermissionLocalService.removeResourcePermission(
			_companyId, _objectDefinition3.getClassName(),
			ResourceConstants.SCOPE_COMPANY, String.valueOf(_companyId),
			role.getRoleId(), ActionKeys.UPDATE);

		_assertFailure(
			StringBundler.concat(
				"User ", _user.getUserId(), " must have UPDATE permission for ",
				_objectDefinition3.getClassName(), StringPool.SPACE,
				objectEntry2.getId()),
			() -> _defaultObjectEntryManager.updateObjectEntry(
				_simpleDTOConverterContext, _objectDefinition3,
				objectEntry2.getId(), objectEntry2));

		// Regular roles' individual permissions should not be restricted by
		// account entry

		_resourcePermissionLocalService.setResourcePermissions(
			_companyId, _objectDefinition3.getClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(objectEntry1.getId()), role.getRoleId(),
			new String[] {ActionKeys.UPDATE});

		_defaultObjectEntryManager.updateObjectEntry(
			_simpleDTOConverterContext, _objectDefinition3,
			objectEntry1.getId(), objectEntry1);

		// Account entry scope

		_addResourcePermission(ActionKeys.UPDATE, _accountAdministratorRole);
		_addResourcePermission(ActionKeys.VIEW, _accountAdministratorRole);

		_user = _addUser();

		_assignAccountEntryRole(
			accountEntry1, _accountAdministratorRole, _user);

		_addResourcePermission(ActionKeys.VIEW, _buyerRole);

		_assignAccountEntryRole(accountEntry2, _buyerRole, _user);

		_assertObjectEntriesSize(2);

		_defaultObjectEntryManager.updateObjectEntry(
			_simpleDTOConverterContext, _objectDefinition3,
			objectEntry1.getId(), objectEntry1);

		_assertFailure(
			StringBundler.concat(
				"User ", _user.getUserId(), " must have UPDATE permission for ",
				_objectDefinition3.getClassName(), StringPool.SPACE,
				objectEntry2.getId()),
			() -> _defaultObjectEntryManager.updateObjectEntry(
				_simpleDTOConverterContext, _objectDefinition3,
				objectEntry2.getId(), objectEntry2));

		// Organization scope

		_user = _addUser();

		Organization organization1 = OrganizationTestUtil.addOrganization();

		_addResourcePermission(ActionKeys.VIEW, _accountManagerRole);

		_assignOrganizationRole(organization1, _accountManagerRole, _user);

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry1.getAccountEntryId(),
			organization1.getOrganizationId());

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry2.getAccountEntryId(),
			organization2.getOrganizationId());

		_assertFailure(
			StringBundler.concat(
				"User ", _user.getUserId(), " must have UPDATE permission for ",
				_objectDefinition3.getClassName(), StringPool.SPACE,
				objectEntry1.getId()),
			() -> _defaultObjectEntryManager.updateObjectEntry(
				_simpleDTOConverterContext, _objectDefinition3,
				objectEntry1.getId(), objectEntry1));

		_addResourcePermission(ActionKeys.UPDATE, _accountManagerRole);

		_defaultObjectEntryManager.updateObjectEntry(
			_simpleDTOConverterContext, _objectDefinition3,
			objectEntry1.getId(), objectEntry1);

		_removeResourcePermission(ActionKeys.UPDATE, _accountManagerRole);

		// Suborganization scope

		Organization suborganization1 = OrganizationTestUtil.addOrganization(
			organization1.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry1.getAccountEntryId(),
			suborganization1.getOrganizationId());

		Organization suborganization2 = OrganizationTestUtil.addOrganization(
			organization2.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry2.getAccountEntryId(),
			suborganization2.getOrganizationId());

		_user = _addUser();

		_assignOrganizationRole(organization1, _accountManagerRole, _user);

		_assertObjectEntriesSize(1);

		_assertFailure(
			StringBundler.concat(
				"User ", _user.getUserId(), " must have UPDATE permission for ",
				_objectDefinition3.getClassName(), StringPool.SPACE,
				objectEntry1.getId()),
			() -> _defaultObjectEntryManager.updateObjectEntry(
				_simpleDTOConverterContext, _objectDefinition3,
				objectEntry1.getId(), objectEntry1));

		_addResourcePermission(ActionKeys.UPDATE, _accountManagerRole);

		_defaultObjectEntryManager.updateObjectEntry(
			_simpleDTOConverterContext, _objectDefinition3,
			objectEntry1.getId(), objectEntry1);

		_assertFailure(
			"The object field r_oneToManyRelationshipName_accountEntryId is " +
				"unmodifiable because it is the account entry restrictor",
			() -> _defaultObjectEntryManager.updateObjectEntry(
				_simpleDTOConverterContext, _objectDefinition3,
				objectEntry1.getId(),
				new ObjectEntry() {
					{
						properties = HashMapBuilder.<String, Object>put(
							"r_oneToManyRelationshipName_accountEntryId",
							accountEntry2.getAccountEntryId()
						).build();
					}
				}));
	}

	private AccountEntry _addAccountEntry() throws Exception {
		return _accountEntryLocalService.addAccountEntry(
			_adminUser.getUserId(), 0L, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null, null, null,
			RandomTestUtil.randomString(),
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());
	}

	private void _addAggregationObjectField(
			String functionName, String objectFieldName,
			String objectRelationshipName)
		throws Exception {

		List<ObjectFieldSetting> objectFieldSettings = new ArrayList<>();

		objectFieldSettings.add(
			_createObjectFieldSetting("function", functionName));

		if (!Objects.equals(functionName, "COUNT")) {
			objectFieldSettings.add(
				_createObjectFieldSetting("objectFieldName", objectFieldName));
		}

		objectFieldSettings.add(
			_createObjectFieldSetting(
				"objectRelationshipName", objectRelationshipName));

		_addCustomObjectField(
			new AggregationObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				StringUtil.lowerCase(functionName) +
					"AggregationObjectFieldName"
			).objectDefinitionId(
				_objectDefinition1.getObjectDefinitionId()
			).objectFieldSettings(
				objectFieldSettings
			).build());
	}

	private void _addCustomObjectField(ObjectField objectField)
		throws Exception {

		_objectFieldService.addCustomObjectField(
			objectField.getExternalReferenceCode(),
			objectField.getListTypeDefinitionId(),
			objectField.getObjectDefinitionId(), objectField.getBusinessType(),
			objectField.getDBType(), objectField.isIndexed(),
			objectField.isIndexedAsKeyword(),
			objectField.getIndexedLanguageId(), objectField.getLabelMap(),
			objectField.isLocalized(), objectField.getName(),
			objectField.isRequired(), objectField.isState(),
			objectField.getObjectFieldSettings());
	}

	private String _addListTypeEntry() throws Exception {
		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.addListTypeEntry(
				null, _adminUser.getUserId(),
				_listTypeDefinition.getListTypeDefinitionId(),
				RandomTestUtil.randomString(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()));

		return listTypeEntry.getKey();
	}

	private ObjectEntry _addObjectEntry(AccountEntry accountEntry)
		throws Exception {

		return _defaultObjectEntryManager.addObjectEntry(
			_simpleDTOConverterContext, _objectDefinition3,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"r_oneToManyRelationshipName_accountEntryId",
						accountEntry.getAccountEntryId()
					).build();
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);
	}

	private void _addRelatedObjectEntries(
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2,
			String objectEntryExternalReferenceCode1,
			String objectEntryExternalReferenceCode2,
			ObjectRelationship objectRelationship)
		throws Exception {

		_defaultObjectEntryManager.addObjectEntry(
			_simpleDTOConverterContext, objectDefinition1,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"externalReferenceCode",
						objectEntryExternalReferenceCode1
					).put(
						"textObjectFieldName", RandomTestUtil.randomString()
					).build();
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);

		String objectRelationshipERCObjectFieldName = StringBundler.concat(
			"r_", objectRelationship.getName(), "_",
			StringUtil.replaceLast(
				objectDefinition1.getPKObjectFieldName(), "Id", "ERC"));

		_defaultObjectEntryManager.addObjectEntry(
			_simpleDTOConverterContext, objectDefinition2,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						objectRelationshipERCObjectFieldName,
						objectEntryExternalReferenceCode1
					).put(
						"externalReferenceCode",
						objectEntryExternalReferenceCode2
					).build();
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);
	}

	private void _addResourcePermission(String actionId, Role role)
		throws Exception {

		String name = _objectDefinition3.getClassName();

		if (Objects.equals(actionId, ObjectActionKeys.ADD_OBJECT_ENTRY)) {
			name = _objectDefinition3.getResourceName();
		}

		_resourcePermissionLocalService.addResourcePermission(
			_companyId, name, ResourceConstants.SCOPE_GROUP_TEMPLATE, "0",
			role.getRoleId(), actionId);
	}

	private Role _addRoleUser(
			String[] actionIds, ObjectDefinition objectDefinition, User user)
		throws Exception {

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_resourcePermissionLocalService.setResourcePermissions(
			_companyId, objectDefinition.getClassName(),
			ResourceConstants.SCOPE_COMPANY, String.valueOf(_companyId),
			role.getRoleId(), actionIds);

		_userLocalService.addRoleUser(role.getRoleId(), user);

		return role;
	}

	private User _addUser() throws Exception {
		User user = UserTestUtil.addUser();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());

		return user;
	}

	private void _assertCountAggregationObjectFieldValue(
			int expectedValue, ObjectEntry objectEntry)
		throws Exception {

		_assertEquals(
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"countAggregationObjectFieldName",
						String.valueOf(expectedValue)
					).build();
				}
			},
			_defaultObjectEntryManager.getObjectEntry(
				_simpleDTOConverterContext, _objectDefinition1,
				objectEntry.getId()));
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
				expectedObjectEntries.get(i), actualObjectEntries.get(i));
		}
	}

	private void _assertEquals(
			ObjectEntry expectedObjectEntry, ObjectEntry actualObjectEntry)
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

				String url = _dlURLHelper.getDownloadURL(
					repositoryFileEntry, repositoryFileEntry.getFileVersion(),
					null, StringPool.BLANK);

				url = HttpComponentsUtil.addParameter(
					url, "objectDefinitionExternalReferenceCode",
					_objectDefinition2.getExternalReferenceCode());
				url = HttpComponentsUtil.addParameter(
					url, "objectEntryExternalReferenceCode",
					actualObjectEntry.getExternalReferenceCode());

				Assert.assertEquals(url, link.getHref());
			}
			else if (Objects.equals(
						expectedEntry.getKey(), "dateObjectFieldName")) {

				if ((expectedEntry.getValue() == null) &&
					(actualObjectEntryProperties.get(expectedEntry.getKey()) ==
						null)) {

					continue;
				}

				Assert.assertEquals(
					expectedEntry.getKey(),
					expectedEntry.getValue() + " 00:00:00.0",
					String.valueOf(
						actualObjectEntryProperties.get(
							expectedEntry.getKey())));
			}
			else if (Objects.equals(
						expectedEntry.getKey(), "picklistObjectFieldName")) {

				ListEntry listEntry =
					(ListEntry)actualObjectEntryProperties.get(
						expectedEntry.getKey());

				if (expectedEntry.getValue() instanceof String) {
					Assert.assertEquals(
						expectedEntry.getValue(), listEntry.getKey());
				}
				else {
					Assert.assertEquals(expectedEntry.getValue(), listEntry);
				}
			}
			else if (Objects.equals(
						expectedEntry.getKey(), "richTextObjectFieldName")) {

				Assert.assertEquals(
					expectedEntry.getValue(),
					actualObjectEntryProperties.get(expectedEntry.getKey()));
			}
			else if (Objects.equals(
						expectedEntry.getKey(),
						"richTextObjectFieldNameRawText")) {

				Assert.assertEquals(
					HtmlParserUtil.extractText(
						String.valueOf(expectedEntry.getValue())),
					String.valueOf(
						actualObjectEntryProperties.get(
							expectedEntry.getKey())));
			}
			else if (Objects.equals(
						expectedEntry.getKey(),
						_objectRelationshipERCObjectFieldName)) {

				Assert.assertEquals(
					expectedEntry.getValue(),
					actualObjectEntryProperties.get(expectedEntry.getKey()));

				_assertEquals(
					_defaultObjectEntryManager.getObjectEntry(
						_objectDefinition1.getCompanyId(),
						_simpleDTOConverterContext,
						GetterUtil.getString(expectedEntry.getValue()),
						_objectDefinition1, null),
					(ObjectEntry)actualObjectEntryProperties.get(
						StringUtil.replaceLast(
							_objectRelationshipFieldName, "Id",
							StringPool.BLANK)));
			}
			else {
				Assert.assertEquals(
					expectedEntry.getKey(), expectedEntry.getValue(),
					actualObjectEntryProperties.get(expectedEntry.getKey()));
			}
		}
	}

	private void _assertFailure(
		String message, UnsafeSupplier<Object, Exception> unsafeSupplier) {

		try {
			unsafeSupplier.get();

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertEquals(exception.getMessage(), message);
		}
	}

	private void _assertObjectEntriesSize(long size) throws Exception {
		Page<ObjectEntry> page = _defaultObjectEntryManager.getObjectEntries(
			_companyId, _objectDefinition3, null, null,
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), _dtoConverterRegistry, null,
				LocaleUtil.getDefault(), null, _user),
			StringPool.BLANK, null, null, null);

		Collection<ObjectEntry> objectEntries = page.getItems();

		Assert.assertEquals(
			objectEntries.toString(), size, objectEntries.size());
	}

	private void _assertPicklistOjectField(
			ListEntry expectedListEntry, Object picklistObjectFieldValue)
		throws Exception {

		_assertEquals(
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"picklistObjectFieldName", expectedListEntry
					).build();
				}
			},
			_defaultObjectEntryManager.addObjectEntry(
				_dtoConverterContext, _objectDefinition2,
				new ObjectEntry() {
					{
						properties = HashMapBuilder.<String, Object>put(
							"picklistObjectFieldName", picklistObjectFieldValue
						).build();
					}
				},
				ObjectDefinitionConstants.SCOPE_COMPANY));
	}

	private void _assignAccountEntryRole(
			AccountEntry accountEntry, Role role, User user)
		throws Exception {

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry.getAccountEntryId(), user.getUserId());

		_userGroupRoleLocalService.addUserGroupRole(
			user.getUserId(), accountEntry.getAccountEntryGroupId(),
			role.getRoleId());
	}

	private void _assignOrganizationRole(
			Organization organization, Role role, User user)
		throws Exception {

		_organizationLocalService.addUserOrganization(
			user.getUserId(), organization.getOrganizationId());

		Group group = _groupLocalService.getOrganizationGroup(
			_companyId, organization.getOrganizationId());

		_userGroupRoleLocalService.addUserGroupRole(
			user.getUserId(), group.getGroupId(), role.getRoleId());
	}

	private String _buildContainsExpressionFilterString(
		String fieldName, String value) {

		return StringBundler.concat("contains( ", fieldName, ",'", value, "')");
	}

	private String _buildEqualsExpressionFilterString(
		String fieldName, Object value) {

		if (value instanceof String) {
			value = StringUtil.quote(String.valueOf(value));
		}

		return StringBundler.concat("( ", fieldName, " eq ", value, ")");
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
					"(x ", includes ? "eq " : "ne ", value, ")"));
		}

		return StringBundler.concat(
			"(", fieldName, "/any(x:",
			StringUtil.merge(valuesList, includes ? " or " : " and "), "))");
	}

	private String _buildRangeExpression(
		Date date1, Date date2, String fieldName) {

		return StringBundler.concat(
			"(( ", fieldName, " ge ", _simpleDateFormat.format(date1),
			") and ( ", fieldName, " le ", _simpleDateFormat.format(date2),
			"))");
	}

	private ObjectDefinition _createObjectDefinition(
			List<ObjectField> objectFields)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				_adminUser.getUserId(), false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT, objectFields);

		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			_adminUser.getUserId(), objectDefinition.getObjectDefinitionId());
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

	private void _removeResourcePermission(String actionId, Role role)
		throws Exception {

		String name = _objectDefinition3.getClassName();

		if (Objects.equals(actionId, ObjectActionKeys.ADD_OBJECT_ENTRY)) {
			name = _objectDefinition3.getResourceName();
		}

		_resourcePermissionLocalService.removeResourcePermission(
			_companyId, name, ResourceConstants.SCOPE_GROUP_TEMPLATE, "0",
			role.getRoleId(), actionId);
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

		Page<ObjectEntry> page = _defaultObjectEntryManager.getObjectEntries(
			_companyId, _objectDefinition2, null, null, _dtoConverterContext,
			context.get("filter"), null, context.get("search"), sorts);

		_assertEquals(
			ListUtil.fromArray(expectedObjectEntries),
			(List<ObjectEntry>)page.getItems());
	}

	private static User _adminUser;
	private static long _companyId;
	private static DefaultObjectEntryManager _defaultObjectEntryManager;
	private static DTOConverterContext _dtoConverterContext;

	@Inject
	private static DTOConverterRegistry _dtoConverterRegistry;

	@DeleteAfterTestRun
	private static Group _group;

	@Inject(
		filter = "object.entry.manager.storage.type=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT
	)
	private static ObjectEntryManager _objectEntryManager;

	private static String _originalName;
	private static PermissionChecker _originalPermissionChecker;
	private static DateFormat _simpleDateFormat;
	private static DTOConverterContext _simpleDTOConverterContext;

	private Role _accountAdministratorRole;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	private Role _accountManagerRole;

	@Inject
	private AccountRoleLocalService _accountRoleLocalService;

	private Role _buyerRole;

	@Inject
	private DLAppService _dlAppService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private DLURLHelper _dlURLHelper;

	@Inject
	private FilterPredicateFactory _filterPredicateFactory;

	@Inject
	private GroupLocalService _groupLocalService;

	private ListTypeDefinition _listTypeDefinition;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Inject
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition2;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition3;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectFieldService _objectFieldService;

	@Inject
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Inject
	private ObjectFilterLocalService _objectFilterLocalService;

	private String _objectRelationshipERCObjectFieldName;
	private String _objectRelationshipFieldName;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Inject
	private OrganizationLocalService _organizationLocalService;

	private NestedFieldsContext _originalNestedFieldsContext;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}