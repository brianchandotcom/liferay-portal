/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.manager.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.model.AccountRole;
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
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectFieldValidationConstants;
import com.liferay.object.constants.ObjectFilterConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.NoSuchObjectEntryException;
import com.liferay.object.exception.ObjectDefinitionAccountEntryRestrictedException;
import com.liferay.object.exception.ObjectRelationshipDeletionTypeException;
import com.liferay.object.exception.RequiredObjectRelationshipException;
import com.liferay.object.field.builder.AggregationObjectFieldBuilder;
import com.liferay.object.field.builder.AttachmentObjectFieldBuilder;
import com.liferay.object.field.builder.DateObjectFieldBuilder;
import com.liferay.object.field.builder.DateTimeObjectFieldBuilder;
import com.liferay.object.field.builder.DecimalObjectFieldBuilder;
import com.liferay.object.field.builder.IntegerObjectFieldBuilder;
import com.liferay.object.field.builder.LongIntegerObjectFieldBuilder;
import com.liferay.object.field.builder.LongTextObjectFieldBuilder;
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
import com.liferay.object.rest.test.util.BaseObjectEntryManagerImplTestCase;
import com.liferay.object.rest.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.service.ObjectFieldService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectFilterLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.hamcrest.CoreMatchers;

import org.junit.After;
import org.junit.AfterClass;
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
@FeatureFlags({"LPS-164801", "LPS-172017"})
@RunWith(Arquillian.class)
public class DefaultObjectEntryManagerImplTest
	extends BaseObjectEntryManagerImplTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		companyId = TestPropsValues.getCompanyId();
		_defaultObjectEntryManager =
			(DefaultObjectEntryManager)_objectEntryManager;
		_group = GroupTestUtil.addGroup();
		_originalName = PrincipalThreadLocal.getName();
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		_simpleDateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

		adminUser = TestPropsValues.getUser();

		_simpleDTOConverterContext = new DefaultDTOConverterContext(
			false, Collections.emptyMap(), dtoConverterRegistry, null,
			LocaleUtil.getDefault(), null, adminUser);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(adminUser));

		PrincipalThreadLocal.setName(adminUser.getUserId());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_group);

		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();

		listTypeDefinition =
			listTypeDefinitionLocalService.addListTypeDefinition(
				null, adminUser.getUserId(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				false, Collections.emptyList());
		_localizedObjectFieldI18nValues = HashMapBuilder.<String, Object>put(
			"localizedLongTextObjectFieldName_i18n",
			HashMapBuilder.put(
				"en_US", "en_US localizedLongTextObjectFieldValue"
			).put(
				"pt_BR", "pt_BR localizedLongTextObjectFieldValue"
			).build()
		).put(
			"localizedRichTextObjectFieldName_i18n",
			HashMapBuilder.put(
				"en_US", "en_US <i>localizedRichTextObjectFieldValue</i>"
			).put(
				"pt_BR", "pt_BR <i>localizedRichTextObjectFieldValue</i>"
			).build()
		).put(
			"localizedTextObjectFieldName_i18n",
			HashMapBuilder.put(
				"en_US", "en_US localizedTextObjectFieldValue"
			).put(
				"pt_BR", "pt_BR localizedTextObjectFieldValue"
			).build()
		).build();
		_objectDefinition1 = _createObjectDefinition(
			Arrays.asList(
				new TextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"textObjectFieldName"
				).build()));

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
				).build(),
				new DateTimeObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"dateTimeObjectFieldName"
				).objectFieldSettings(
					Collections.singletonList(
						_createObjectFieldSetting(
							ObjectFieldSettingConstants.NAME_TIME_STORAGE,
							ObjectFieldSettingConstants.
								VALUE_USE_INPUT_AS_ENTERED))
				).build(),
				new DateTimeObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"dateTimeUTCObjectFieldName"
				).objectFieldSettings(
					Collections.singletonList(
						_createObjectFieldSetting(
							ObjectFieldSettingConstants.NAME_TIME_STORAGE,
							ObjectFieldSettingConstants.VALUE_CONVERT_TO_UTC))
				).build(),
				new DecimalObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"decimalObjectFieldName"
				).build(),
				new IntegerObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"integerObjectFieldName"
				).build(),
				new LongIntegerObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"longIntegerObjectFieldName"
				).build(),
				new LongTextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).localized(
					true
				).name(
					"localizedLongTextObjectFieldName"
				).build(),
				new PicklistObjectFieldBuilder(
				).indexed(
					true
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).listTypeDefinitionId(
					listTypeDefinition.getListTypeDefinitionId()
				).name(
					"picklistObjectFieldName"
				).build(),
				new PrecisionDecimalObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"precisionDecimalObjectFieldName"
				).build(),
				new RichTextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"richTextObjectFieldName"
				).build(),
				new RichTextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).localized(
					true
				).name(
					"localizedRichTextObjectFieldName"
				).build(),
				new TextObjectFieldBuilder(
				).indexed(
					true
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"textObjectFieldName"
				).build(),
				new TextObjectFieldBuilder(
				).indexed(
					true
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).localized(
					true
				).name(
					"localizedTextObjectFieldName"
				).build()));

		ObjectRelationship objectRelationship1 =
			_objectRelationshipLocalService.addObjectRelationship(
				adminUser.getUserId(),
				_objectDefinition1.getObjectDefinitionId(),
				_objectDefinition2.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"oneToManyRelationshipName",
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_addAggregationObjectField(
			"precisionDecimalObjectFieldName", "AVERAGE",
			_objectDefinition1.getObjectDefinitionId(),
			"averageAggregationObjectFieldName", objectRelationship1.getName());
		_addAggregationObjectField(
			null, "COUNT", _objectDefinition1.getObjectDefinitionId(),
			"countAggregationObjectFieldName1", objectRelationship1.getName());
		_addAggregationObjectField(
			"integerObjectFieldName", "MAX",
			_objectDefinition1.getObjectDefinitionId(),
			"maxAggregationObjectFieldName", objectRelationship1.getName());
		_addAggregationObjectField(
			"longIntegerObjectFieldName", "MIN",
			_objectDefinition1.getObjectDefinitionId(),
			"minAggregationObjectFieldName", objectRelationship1.getName());
		_addAggregationObjectField(
			"decimalObjectFieldName", "SUM",
			_objectDefinition1.getObjectDefinitionId(),
			"sumAggregationObjectFieldName", objectRelationship1.getName());

		ObjectField objectField = objectFieldLocalService.getObjectField(
			objectRelationship1.getObjectFieldId2());

		_objectRelationshipERCObjectFieldName = ObjectFieldSettingUtil.getValue(
			ObjectFieldSettingConstants.
				NAME_OBJECT_RELATIONSHIP_ERC_OBJECT_FIELD_NAME,
			objectField);
		_objectRelationshipFieldName = objectField.getName();

		_objectDefinition3 =
			objectDefinitionLocalService.addCustomObjectDefinition(
				adminUser.getUserId(), 0, false, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.singletonList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"textObjectFieldName"
					).build()));

		ObjectDefinition accountEntryObjectDefinition =
			objectDefinitionLocalService.fetchObjectDefinition(
				companyId, "AccountEntry");

		ObjectRelationship objectRelationship2 =
			_objectRelationshipLocalService.addObjectRelationship(
				adminUser.getUserId(),
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
			objectDefinitionLocalService.updateObjectDefinition(
				_objectDefinition3);

		_objectDefinition3 =
			objectDefinitionLocalService.publishCustomObjectDefinition(
				adminUser.getUserId(),
				_objectDefinition3.getObjectDefinitionId());

		_accountAdministratorRole = _roleLocalService.getRole(
			companyId,
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR);
		_accountManagerRole = _roleLocalService.getRole(
			companyId, AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MANAGER);
		_buyerRole = _roleLocalService.getRole(companyId, "Buyer");

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
				dtoConverterContext, _objectDefinition2,
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
				dtoConverterContext, _objectDefinition2,
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

		ObjectField objectField = objectFieldLocalService.getObjectField(
			_objectDefinition1.getObjectDefinitionId(),
			"countAggregationObjectFieldName1");

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd");

		String currentDateString = dateFormat.format(new Date());

		_objectFilterLocalService.addObjectFilter(
			adminUser.getUserId(), objectField.getObjectFieldId(), "createDate",
			ObjectFilterConstants.TYPE_DATE_RANGE,
			StringBundler.concat(
				"{\"le\": \"", currentDateString, "\", \"ge\": \"",
				currentDateString, "\"}"));
		_objectFilterLocalService.addObjectFilter(
			adminUser.getUserId(), objectField.getObjectFieldId(),
			"modifiedDate", ObjectFilterConstants.TYPE_DATE_RANGE,
			StringBundler.concat(
				"{\"le\": \"", currentDateString, "\", \"ge\": \"",
				currentDateString, "\"}"));

		_assertCountAggregationObjectFieldValue(2, parentObjectEntry1);

		// Aggregation field with filter (date range with date only)

		_objectFilterLocalService.addObjectFilter(
			adminUser.getUserId(), objectField.getObjectFieldId(),
			"dateObjectFieldName", ObjectFilterConstants.TYPE_DATE_RANGE,
			"{\"le\": \"2020-01-02\", \"ge\": \"2020-01-02\"}");

		_assertCountAggregationObjectFieldValue(1, parentObjectEntry1);

		// Aggregation field with filter (equals and not equals)

		_objectFilterLocalService.addObjectFilter(
			adminUser.getUserId(), objectField.getObjectFieldId(),
			"integerObjectFieldName", ObjectFilterConstants.TYPE_EQUALS,
			"{\"eq\": \"15\"}");

		_assertCountAggregationObjectFieldValue(1, parentObjectEntry1);

		_objectFilterLocalService.addObjectFilter(
			adminUser.getUserId(), objectField.getObjectFieldId(),
			"integerObjectFieldName", ObjectFilterConstants.TYPE_NOT_EQUALS,
			"{\"ne\":\"15\"}");

		_assertCountAggregationObjectFieldValue(0, parentObjectEntry1);

		_objectFilterLocalService.deleteObjectFieldObjectFilter(
			objectField.getObjectFieldId());

		// Aggregation field with filter (excludes and includes with a string)

		_objectFilterLocalService.addObjectFilter(
			adminUser.getUserId(), objectField.getObjectFieldId(),
			"picklistObjectFieldName", ObjectFilterConstants.TYPE_EXCLUDES,
			"{\"not\":{\"in\":[\"" + listTypeEntryKey + "\"]}}");

		_assertCountAggregationObjectFieldValue(1, parentObjectEntry1);

		_objectFilterLocalService.addObjectFilter(
			adminUser.getUserId(), objectField.getObjectFieldId(),
			"picklistObjectFieldName", ObjectFilterConstants.TYPE_INCLUDES,
			"{\"in\":[\"" + listTypeEntryKey + "\"]}");

		_assertCountAggregationObjectFieldValue(0, parentObjectEntry1);

		_objectFilterLocalService.deleteObjectFieldObjectFilter(
			objectField.getObjectFieldId());

		// Aggregation field with filter (excludes and includes with an int)

		_objectFilterLocalService.addObjectFilter(
			adminUser.getUserId(), objectField.getObjectFieldId(), "status",
			ObjectFilterConstants.TYPE_EXCLUDES,
			"{\"not\":{\"in\": [" + WorkflowConstants.STATUS_APPROVED + "]}}");

		_assertCountAggregationObjectFieldValue(0, parentObjectEntry1);

		_objectFilterLocalService.deleteObjectFieldObjectFilter(
			objectField.getObjectFieldId());

		_objectFilterLocalService.addObjectFilter(
			adminUser.getUserId(), objectField.getObjectFieldId(), "status",
			ObjectFilterConstants.TYPE_INCLUDES,
			"{\"in\": [" + WorkflowConstants.STATUS_APPROVED + "]}");

		_assertCountAggregationObjectFieldValue(2, parentObjectEntry1);

		_objectFilterLocalService.deleteObjectFieldObjectFilter(
			objectField.getObjectFieldId());

		// Aggregation field without filters

		assertEquals(
			_defaultObjectEntryManager.getObjectEntry(
				_simpleDTOConverterContext, _objectDefinition1,
				parentObjectEntry1.getId()),
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"averageAggregationObjectFieldName",
						"0.55555555544444440000"
					).put(
						"countAggregationObjectFieldName1", "2"
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
			});

		_defaultObjectEntryManager.deleteObjectEntry(
			_objectDefinition2, childObjectEntry1.getId());

		assertEquals(
			_defaultObjectEntryManager.getObjectEntry(
				_simpleDTOConverterContext, _objectDefinition1,
				parentObjectEntry1.getId()),
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"averageAggregationObjectFieldName",
						"0.12345678912345670000"
					).put(
						"countAggregationObjectFieldName1", "1"
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
			});

		_defaultObjectEntryManager.deleteObjectEntry(
			_objectDefinition2, childObjectEntry2.getId());

		assertEquals(
			_defaultObjectEntryManager.getObjectEntry(
				_simpleDTOConverterContext, _objectDefinition1,
				parentObjectEntry1.getId()),
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"averageAggregationObjectFieldName", "0"
					).put(
						"countAggregationObjectFieldName1", "0"
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
			});

		// Aggregation field without filters (many to many self relationship)

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				adminUser.getUserId(),
				_objectDefinition1.getObjectDefinitionId(),
				_objectDefinition1.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_addAggregationObjectField(
			null, "COUNT", _objectDefinition1.getObjectDefinitionId(),
			"countAggregationObjectFieldName2", objectRelationship.getName());

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

		ObjectEntry childObjectEntry3 =
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

		ObjectRelationshipTestUtil.relateObjectEntries(
			parentObjectEntry2.getId(), childObjectEntry3.getId(),
			objectRelationship, adminUser.getUserId());

		ObjectEntry childObjectEntry4 =
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

		ObjectRelationshipTestUtil.relateObjectEntries(
			parentObjectEntry2.getId(), childObjectEntry4.getId(),
			objectRelationship, adminUser.getUserId());

		assertEquals(
			_defaultObjectEntryManager.getObjectEntry(
				_simpleDTOConverterContext, _objectDefinition1,
				parentObjectEntry2.getId()),
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"countAggregationObjectFieldName2", "2"
					).build();
				}
			});

		_defaultObjectEntryManager.deleteObjectEntry(
			_objectDefinition1, childObjectEntry3.getId());

		assertEquals(
			_defaultObjectEntryManager.getObjectEntry(
				_simpleDTOConverterContext, _objectDefinition1,
				parentObjectEntry2.getId()),
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"countAggregationObjectFieldName2", "1"
					).build();
				}
			});

		_defaultObjectEntryManager.deleteObjectEntry(
			_objectDefinition1, childObjectEntry4.getId());

		assertEquals(
			_defaultObjectEntryManager.getObjectEntry(
				_simpleDTOConverterContext, _objectDefinition1,
				parentObjectEntry2.getId()),
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"countAggregationObjectFieldName2", "0"
					).build();
				}
			});

		// Date time

		LocalDateTime localDateTime = LocalDateTime.now();

		assertEquals(
			_defaultObjectEntryManager.addObjectEntry(
				dtoConverterContext, _objectDefinition2,
				new ObjectEntry() {
					{
						properties = Collections.singletonMap(
							"dateTimeObjectFieldName", localDateTime);
					}
				},
				ObjectDefinitionConstants.SCOPE_COMPANY),
			new ObjectEntry() {
				{
					properties = Collections.singletonMap(
						"dateTimeObjectFieldName",
						localDateTime.format(
							DateTimeFormatter.ofPattern(
								"yyyy-MM-dd'T'HH:mm:ss.SSS")));
				}
			});

		User user = UserTestUtil.addOmniAdminUser();

		user.setTimeZoneId("America/Sao_Paulo");

		user = _userLocalService.updateUser(user);

		ZonedDateTime zonedDateTime = localDateTime.atZone(
			ZoneId.of(user.getTimeZoneId()));

		LocalDateTime utcLocalDateTime = LocalDateTime.from(
			zonedDateTime.withZoneSameInstant(ZoneId.of(StringPool.UTC)));

		assertEquals(
			_defaultObjectEntryManager.addObjectEntry(
				new DefaultDTOConverterContext(
					false, Collections.emptyMap(), dtoConverterRegistry, null,
					LocaleUtil.getDefault(), null, user),
				_objectDefinition2,
				new ObjectEntry() {
					{
						properties = Collections.singletonMap(
							"dateTimeUTCObjectFieldName", localDateTime);
					}
				},
				ObjectDefinitionConstants.SCOPE_COMPANY),
			new ObjectEntry() {
				{
					properties = Collections.singletonMap(
						"dateTimeUTCObjectFieldName",
						utcLocalDateTime.format(
							DateTimeFormatter.ofPattern(
								"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
				}
			});

		_userLocalService.deleteUser(user);

		// Picklist by list entry

		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.addListTypeEntry(
				null, adminUser.getUserId(),
				listTypeDefinition.getListTypeDefinitionId(),
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
	public void testAddObjectEntryWithAccountEntryRestricted()
		throws Exception {

		// Account entry restricted scope

		AccountEntry accountEntry1 = _addAccountEntry();
		_user = _addUser();

		_assignAccountEntryRole(accountEntry1, _buyerRole, _user);

		_addResourcePermission(ObjectActionKeys.ADD_OBJECT_ENTRY, _buyerRole);

		AccountEntry accountEntry2 = _addAccountEntry();

		AssertUtils.assertFailure(
			ObjectDefinitionAccountEntryRestrictedException.class,
			StringBundler.concat(
				"User ", _user.getUserId(),
				" does not have access to account entry ",
				accountEntry2.getAccountEntryId()),
			() -> _addObjectEntry(accountEntry2));

		// Account entry restricted with organization scope

		Organization organization1 = OrganizationTestUtil.addOrganization();

		_addAccountEntryOrganizationRel(accountEntry1, organization1);

		_user = _addUser();

		_assignOrganizationRole(organization1, _accountManagerRole, _user);

		_addResourcePermission(
			ObjectActionKeys.ADD_OBJECT_ENTRY, _accountManagerRole);

		AssertUtils.assertFailure(
			ObjectDefinitionAccountEntryRestrictedException.class,
			StringBundler.concat(
				"User ", _user.getUserId(),
				" does not have access to account entry ",
				accountEntry2.getAccountEntryId()),
			() -> _addObjectEntry(accountEntry2));

		_deleteAccountEntryOrganizationRel(accountEntry1, organization1);

		// Account entry restricted with suborganization scope

		Organization suborganization1 = OrganizationTestUtil.addOrganization(
			organization1.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_addAccountEntryOrganizationRel(accountEntry1, suborganization1);

		_user = _addUser();

		_assignOrganizationRole(organization1, _accountManagerRole, _user);

		AssertUtils.assertFailure(
			ObjectDefinitionAccountEntryRestrictedException.class,
			StringBundler.concat(
				"User ", _user.getUserId(),
				" does not have access to account entry ",
				accountEntry2.getAccountEntryId()),
			() -> _addObjectEntry(accountEntry2));

		_deleteAccountEntryOrganizationRel(accountEntry1, suborganization1);

		_removeResourcePermission(
			ObjectActionKeys.ADD_OBJECT_ENTRY, _accountManagerRole);

		// Check account entry permission

		_user = _addUser();

		_testAddObjectEntryAccountEntryRestriction(accountEntry1);
		_testAddObjectEntryAccountEntryRestriction(accountEntry2);

		// Check account entry permission with organization

		_addAccountEntryOrganizationRel(accountEntry1, organization1);

		_user = _addUser();

		_assignOrganizationRole(organization1, _accountManagerRole, _user);

		AssertUtils.assertFailure(
			PrincipalException.MustHavePermission.class,
			StringBundler.concat(
				"User ", _user.getUserId(),
				" must have ADD_OBJECT_ENTRY permission for ",
				_objectDefinition3.getResourceName(), StringPool.SPACE),
			() -> _addObjectEntry(accountEntry1));

		_addResourcePermission(
			ObjectActionKeys.ADD_OBJECT_ENTRY, _accountManagerRole);

		Assert.assertNotNull(_addObjectEntry(accountEntry1));

		_removeResourcePermission(
			ObjectActionKeys.ADD_OBJECT_ENTRY, _accountManagerRole);

		// Check account entry permission with suborganization

		Organization organization2 = OrganizationTestUtil.addOrganization();

		Organization suborganization2 = OrganizationTestUtil.addOrganization(
			organization2.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_addAccountEntryOrganizationRel(accountEntry2, suborganization2);

		_user = _addUser();

		_assignOrganizationRole(organization1, _accountManagerRole, _user);

		AssertUtils.assertFailure(
			PrincipalException.MustHavePermission.class,
			StringBundler.concat(
				"User ", _user.getUserId(),
				" must have ADD_OBJECT_ENTRY permission for ",
				_objectDefinition3.getResourceName(), StringPool.SPACE),
			() -> _addObjectEntry(accountEntry1));

		_addResourcePermission(
			ObjectActionKeys.ADD_OBJECT_ENTRY, _accountManagerRole);

		Assert.assertNotNull(_addObjectEntry(accountEntry1));
	}

	@Test
	public void testDeleteObjectEntry() throws Exception {
		ObjectDefinition objectDefinition1 = _createObjectDefinition(
			Collections.singletonList(
				new TextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"textObjectFieldName"
				).build()));
		ObjectDefinition objectDefinition2 = _createObjectDefinition(
			Collections.singletonList(
				new TextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"a" + RandomTestUtil.randomString()
				).build()));

		// Relationship type cascade

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				adminUser.getUserId(),
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
				companyId, _simpleDTOConverterContext, "externalReferenceCode1",
				objectDefinition1, null);

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
				ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE, false,
				objectRelationship.getLabelMap());

		_defaultObjectEntryManager.deleteObjectEntry(
			companyId, _simpleDTOConverterContext, "externalReferenceCode1",
			objectDefinition1, null);

		try {
			_defaultObjectEntryManager.getObjectEntry(
				companyId, _simpleDTOConverterContext, "externalReferenceCode1",
				objectDefinition1, null);

			Assert.fail();
		}
		catch (NoSuchObjectEntryException noSuchObjectEntryException) {
			Assert.assertNotNull(noSuchObjectEntryException);
		}

		PrincipalThreadLocal.setName(adminUser.getUserId());
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(adminUser));

		Assert.assertNotNull(
			_defaultObjectEntryManager.getObjectEntry(
				companyId, _simpleDTOConverterContext, "externalReferenceCode2",
				objectDefinition2, null));

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
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT, false,
				objectRelationship.getLabelMap());

		try {
			_defaultObjectEntryManager.deleteObjectEntry(
				companyId, _simpleDTOConverterContext, "externalReferenceCode3",
				objectDefinition1, null);

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

		objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinition1.getObjectDefinitionId());
		objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinition2.getObjectDefinitionId());
	}

	@Test
	public void testDeleteObjectEntryWithAccountEntryRestricted()
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
			companyId, _objectDefinition3.getClassName(),
			ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
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
			PermissionCheckerFactoryUtil.create(adminUser));

		PrincipalThreadLocal.setName(adminUser.getUserId());

		objectEntry1 = _addObjectEntry(accountEntry1);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		PrincipalThreadLocal.setName(_user.getUserId());

		_resourcePermissionLocalService.setResourcePermissions(
			companyId, _objectDefinition3.getClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(objectEntry1.getId()), role.getRoleId(),
			new String[] {ActionKeys.DELETE});

		_defaultObjectEntryManager.deleteObjectEntry(
			_objectDefinition3, objectEntry1.getId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(adminUser));

		PrincipalThreadLocal.setName(adminUser.getUserId());

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
			PermissionCheckerFactoryUtil.create(adminUser));

		PrincipalThreadLocal.setName(adminUser.getUserId());

		objectEntry1 = _addObjectEntry(accountEntry1);

		_user = _addUser();

		Organization organization1 = OrganizationTestUtil.addOrganization();

		_addResourcePermission(ActionKeys.VIEW, _accountManagerRole);

		_assignOrganizationRole(organization1, _accountManagerRole, _user);

		_addAccountEntryOrganizationRel(accountEntry1, organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_addAccountEntryOrganizationRel(accountEntry2, organization2);

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

		_deleteAccountEntryOrganizationRel(accountEntry1, organization1);
		_deleteAccountEntryOrganizationRel(accountEntry2, organization2);

		// Suborganization scope

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(adminUser));

		PrincipalThreadLocal.setName(adminUser.getUserId());

		objectEntry1 = _addObjectEntry(accountEntry1);

		_user = _addUser();

		_assignOrganizationRole(organization1, _accountManagerRole, _user);

		Organization suborganization1 = OrganizationTestUtil.addOrganization(
			organization1.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_addAccountEntryOrganizationRel(accountEntry1, suborganization1);

		Organization suborganization2 = OrganizationTestUtil.addOrganization(
			organization2.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_addAccountEntryOrganizationRel(accountEntry2, suborganization2);

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
	public void testGetObjectEntries() throws Exception {
		testGetObjectEntries(Collections.emptyMap());

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
				dtoConverterContext, _objectDefinition2,
				new ObjectEntry() {
					{
						properties = HashMapBuilder.<String, Object>put(
							_objectRelationshipFieldName,
							parentObjectEntry1.getId()
						).put(
							"picklistObjectFieldName", picklistObjectFieldValue1
						).put(
							"textObjectFieldName", "aaa"
						).putAll(
							_localizedObjectFieldI18nValues
						).build();
					}
				},
				ObjectDefinitionConstants.SCOPE_COMPANY);

		assertEquals(
			childObjectEntry1,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						_objectRelationshipFieldName, parentObjectEntry1.getId()
					).put(
						"localizedLongTextObjectFieldName",
						"en_US localizedLongTextObjectFieldValue"
					).put(
						"localizedRichTextObjectFieldName",
						"en_US <i>localizedRichTextObjectFieldValue</i>"
					).put(
						"localizedRichTextObjectFieldNameRawText",
						"en_US localizedRichTextObjectFieldValue"
					).put(
						"localizedTextObjectFieldName",
						"en_US localizedTextObjectFieldValue"
					).put(
						"picklistObjectFieldName", picklistObjectFieldValue1
					).put(
						"textObjectFieldName", "aaa"
					).putAll(
						_localizedObjectFieldI18nValues
					).build();
				}
			});

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
				dtoConverterContext, _objectDefinition2,
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

		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				StringBundler.concat(
					buildEqualsExpressionFilterString(
						"picklistObjectFieldName", picklistObjectFieldValue1),
					" and (",
					buildEqualsExpressionFilterString(
						"picklistObjectFieldName", picklistObjectFieldValue1),
					" or ",
					buildEqualsExpressionFilterString(
						"picklistObjectFieldName", picklistObjectFieldValue2),
					")")
			).build(),
			childObjectEntry1);

		// Contains expression

		testGetObjectEntries(
			HashMapBuilder.put(
				"filter", _buildContainsExpressionFilterString("id", "aaaa")
			).build());

		// Equals expression

		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				buildEqualsExpressionFilterString(
					"picklistObjectFieldName", picklistObjectFieldValue1)
			).put(
				"search", "aa"
			).build(),
			childObjectEntry1);
		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				buildEqualsExpressionFilterString(
					"picklistObjectFieldName", picklistObjectFieldValue2)
			).put(
				"search", "aa"
			).build(),
			childObjectEntry2);
		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				buildEqualsExpressionFilterString(
					_objectRelationshipERCObjectFieldName,
					parentObjectEntry1.getExternalReferenceCode())
			).build(),
			childObjectEntry1);
		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				buildEqualsExpressionFilterString(
					_objectRelationshipERCObjectFieldName,
					parentObjectEntry2.getExternalReferenceCode())
			).build(),
			childObjectEntry2);
		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				buildEqualsExpressionFilterString(
					"localizedLongTextObjectFieldName",
					"en_US localizedLongTextObjectFieldValue")
			).build(),
			childObjectEntry1);

		// In expression

		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildInExpressionFilterString(
					"id", true, childObjectEntry1.getId())
			).build(),
			childObjectEntry1);
		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildInExpressionFilterString(
					"id", false, childObjectEntry1.getId())
			).build(),
			childObjectEntry2);
		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildInExpressionFilterString(
					"picklistObjectFieldName", true, picklistObjectFieldValue1)
			).build(),
			childObjectEntry1);
		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildInExpressionFilterString(
					"picklistObjectFieldName", false, picklistObjectFieldValue1)
			).build(),
			childObjectEntry2);
		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildInExpressionFilterString(
					_objectRelationshipERCObjectFieldName, true,
					parentObjectEntry1.getExternalReferenceCode())
			).build(),
			childObjectEntry1);
		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildInExpressionFilterString(
					_objectRelationshipERCObjectFieldName, false,
					parentObjectEntry1.getExternalReferenceCode())
			).build(),
			childObjectEntry2);
		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildInExpressionFilterString(
					_objectRelationshipFieldName.substring(
						_objectRelationshipFieldName.lastIndexOf("_") + 1),
					true, parentObjectEntry1.getId())
			).build(),
			childObjectEntry1);
		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildInExpressionFilterString(
					_objectRelationshipFieldName.substring(
						_objectRelationshipFieldName.lastIndexOf("_") + 1),
					false, parentObjectEntry1.getId())
			).build(),
			childObjectEntry2);

		// Lambda expression

		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				buildEqualsExpressionFilterString(
					"creatorId", adminUser.getUserId())
			).build(),
			childObjectEntry1, childObjectEntry2);
		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildLambdaExpressionFilterString(
					"status", true, WorkflowConstants.STATUS_APPROVED)
			).build(),
			childObjectEntry1, childObjectEntry2);
		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildLambdaExpressionFilterString(
					"status", false, WorkflowConstants.STATUS_APPROVED)
			).build());

		// Range expression

		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildRangeExpression(
					childObjectEntry1.getDateCreated(), new Date(),
					"dateCreated")
			).build(),
			childObjectEntry1, childObjectEntry2);
		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildRangeExpression(
					childObjectEntry1.getDateModified(), new Date(),
					"dateModified")
			).build(),
			childObjectEntry1, childObjectEntry2);

		testGetObjectEntries(
			HashMapBuilder.put(
				"search", "en_US"
			).build(),
			childObjectEntry1);
		testGetObjectEntries(
			HashMapBuilder.put(
				"search", "pt_BR"
			).build());
		testGetObjectEntries(
			HashMapBuilder.put(
				"search", String.valueOf(childObjectEntry1.getId())
			).build(),
			childObjectEntry1);
		testGetObjectEntries(
			HashMapBuilder.put(
				"search", String.valueOf(childObjectEntry2.getId())
			).build(),
			childObjectEntry2);
		testGetObjectEntries(
			HashMapBuilder.put(
				"search", picklistObjectFieldValue1
			).build(),
			childObjectEntry1);
		testGetObjectEntries(
			HashMapBuilder.put(
				"search", picklistObjectFieldValue2
			).build(),
			childObjectEntry2);
		testGetObjectEntries(
			HashMapBuilder.put(
				"search", "aa"
			).build(),
			childObjectEntry1, childObjectEntry2);
		testGetObjectEntries(
			HashMapBuilder.put(
				"sort", "createDate:asc"
			).build(),
			childObjectEntry1, childObjectEntry2);
		testGetObjectEntries(
			HashMapBuilder.put(
				"sort", "createDate:desc"
			).build(),
			childObjectEntry2, childObjectEntry1);
		testGetObjectEntries(
			HashMapBuilder.put(
				"sort", "id:asc"
			).build(),
			childObjectEntry1, childObjectEntry2);
		testGetObjectEntries(
			HashMapBuilder.put(
				"sort", "id:desc"
			).build(),
			childObjectEntry2, childObjectEntry1);
		testGetObjectEntries(
			HashMapBuilder.put(
				"sort", "localizedTextObjectFieldName:asc"
			).build(),
			childObjectEntry2, childObjectEntry1);
		testGetObjectEntries(
			HashMapBuilder.put(
				"sort", "localizedTextObjectFieldName:desc"
			).build(),
			childObjectEntry1, childObjectEntry2);
		testGetObjectEntries(
			HashMapBuilder.put(
				"sort", "textObjectFieldName:asc"
			).build(),
			childObjectEntry1, childObjectEntry2);
		testGetObjectEntries(
			HashMapBuilder.put(
				"sort", "textObjectFieldName:desc"
			).build(),
			childObjectEntry2, childObjectEntry1);
	}

	@Test
	public void testGetObjectEntriesWithAccountEntryRestricted()
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
			companyId, _objectDefinition3.getClassName(),
			ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
			role.getRoleId(), ActionKeys.VIEW);

		// Regular roles' individual permissions should not be restricted by
		// account entry

		_resourcePermissionLocalService.setResourcePermissions(
			companyId, _objectDefinition3.getClassName(),
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

		_addAccountEntryOrganizationRel(accountEntry1, organization1);
		_addAccountEntryOrganizationRel(accountEntry2, organization1);

		_user = _addUser();

		_organizationLocalService.addUserOrganization(
			_user.getUserId(), organization1.getOrganizationId());

		_assertObjectEntriesSize(2);

		_deleteAccountEntryOrganizationRel(accountEntry2, organization1);

		_assertObjectEntriesSize(1);

		_organizationLocalService.deleteUserOrganization(
			_user.getUserId(), organization1.getOrganizationId());

		_assertObjectEntriesSize(0);

		_deleteAccountEntryOrganizationRel(accountEntry1, organization1);

		// Check suborganizations

		Organization suborganization1 = OrganizationTestUtil.addOrganization(
			organization1.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_addAccountEntryOrganizationRel(accountEntry1, suborganization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		Organization suborganization2 = OrganizationTestUtil.addOrganization(
			organization2.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_addAccountEntryOrganizationRel(accountEntry2, suborganization2);

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
	public void testGetObjectEntriesWithAggregationFacets() throws Exception {
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
			companyId, _objectDefinition1, null, aggregation,
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), dtoConverterRegistry, null,
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
	public void testGetObjectEntryRelatedObjectEntriesWithAccountEntryRestricted()
		throws Exception {

		ObjectDefinition childObjectDefinition = _createObjectDefinition(
			Arrays.asList(
				new TextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"textObjectFieldName"
				).build()));

		ObjectRelationship objectRelationship1 =
			_objectRelationshipLocalService.addObjectRelationship(
				adminUser.getUserId(),
				_objectDefinition3.getObjectDefinitionId(),
				childObjectDefinition.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectDefinition accountEntryObjectDefinition =
			objectDefinitionLocalService.fetchObjectDefinition(
				companyId, "AccountEntry");

		ObjectRelationship objectRelationship2 =
			_objectRelationshipLocalService.addObjectRelationship(
				adminUser.getUserId(),
				accountEntryObjectDefinition.getObjectDefinitionId(),
				childObjectDefinition.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		childObjectDefinition.setAccountEntryRestrictedObjectFieldId(
			objectRelationship2.getObjectFieldId2());

		childObjectDefinition.setAccountEntryRestricted(true);

		childObjectDefinition =
			objectDefinitionLocalService.updateObjectDefinition(
				childObjectDefinition);

		_addRelatedObjectEntries(
			_objectDefinition3, childObjectDefinition,
			"parentExternalReferenceCode", StringUtil.randomId(),
			objectRelationship1);

		AccountEntry accountEntry = _addAccountEntry();

		AccountRole accountRole = _accountRoleLocalService.addAccountRole(
			TestPropsValues.getUserId(), accountEntry.getAccountEntryId(),
			RandomTestUtil.randomString(), null, null);

		User user = _addUser();

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry.getAccountEntryId(), user.getUserId());

		_accountRoleLocalService.associateUser(
			accountEntry.getAccountEntryId(), accountRole.getAccountRoleId(),
			user.getUserId());

		Role role = accountRole.getRole();

		_addResourcePermission(ActionKeys.VIEW, role);

		_resourcePermissionLocalService.addResourcePermission(
			companyId, childObjectDefinition.getClassName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", role.getRoleId(),
			ActionKeys.VIEW);

		ObjectEntry parentObjectEntry =
			_defaultObjectEntryManager.getObjectEntry(
				companyId, _simpleDTOConverterContext,
				"parentExternalReferenceCode", _objectDefinition3, null);

		Page<ObjectEntry> page =
			_defaultObjectEntryManager.getObjectEntryRelatedObjectEntries(
				_simpleDTOConverterContext, _objectDefinition3,
				parentObjectEntry.getId(), objectRelationship1.getName(), null);

		Collection<ObjectEntry> objectEntries = page.getItems();

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship1.getObjectRelationshipId());

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship2.getObjectRelationshipId());
	}

	@Test
	public void testPartialUpdateObjectEntry() throws Exception {
		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd");

		ObjectEntry objectEntry = _defaultObjectEntryManager.addObjectEntry(
			dtoConverterContext, _objectDefinition2,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"dateObjectFieldName",
						dateFormat.format(RandomTestUtil.nextDate())
					).put(
						"decimalObjectFieldName", RandomTestUtil.randomDouble()
					).put(
						"integerObjectFieldName", RandomTestUtil.randomInt()
					).put(
						"longIntegerObjectFieldName",
						RandomTestUtil.randomLong(
							ObjectFieldValidationConstants.
								BUSINESS_TYPE_LONG_VALUE_MIN,
							ObjectFieldValidationConstants.
								BUSINESS_TYPE_LONG_VALUE_MAX)
					).put(
						"precisionDecimalObjectFieldName",
						new BigDecimal(RandomTestUtil.randomDouble())
					).put(
						"richTextObjectFieldName",
						StringBundler.concat(
							"<i>", RandomTestUtil.randomString(), "</i>")
					).put(
						"textObjectFieldName", "textObjectFieldValue"
					).build();
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);

		Map<String, Object> objectEntryProperties =
			HashMapBuilder.<String, Object>put(
				"dateObjectFieldName", "2023-08-20"
			).put(
				"decimalObjectFieldName", 2.7
			).put(
				"integerObjectFieldName", 25
			).put(
				"longIntegerObjectFieldName", 200L
			).put(
				"precisionDecimalObjectFieldName",
				new BigDecimal(0.8755445767, MathContext.DECIMAL64)
			).put(
				"richTextObjectFieldName", "<i>richTextObjectFieldNameValue</i>"
			).build();

		assertEquals(
			_defaultObjectEntryManager.partialUpdateObjectEntry(
				_simpleDTOConverterContext, _objectDefinition2,
				objectEntry.getId(),
				new ObjectEntry() {
					{
						properties = objectEntryProperties;
					}
				}),
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>putAll(
						objectEntryProperties
					).put(
						"textObjectFieldName", "textObjectFieldValue"
					).build();
				}
			});
	}

	@Test
	public void testUpdateObjectEntry() throws Exception {
		ObjectEntry objectEntry = _objectEntryManager.addObjectEntry(
			dtoConverterContext, _objectDefinition2,
			new ObjectEntry() {
				{
					properties = _localizedObjectFieldI18nValues;
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);

		Role role = _roleLocalService.getRole(
			companyId, RoleConstants.ADMINISTRATOR);

		_user = _addUser();

		_userLocalService.addRoleUser(role.getRoleId(), _user);

		_assertLocalizedValues(
			HashMapBuilder.<String, Object>put(
				"localizedLongTextObjectFieldName",
				"en_US localizedLongTextObjectFieldValue"
			).put(
				"localizedRichTextObjectFieldName",
				"en_US <i>localizedRichTextObjectFieldValue</i>"
			).put(
				"localizedRichTextObjectFieldNameRawText",
				"en_US localizedRichTextObjectFieldValue"
			).put(
				"localizedTextObjectFieldName",
				"en_US localizedTextObjectFieldValue"
			).putAll(
				_localizedObjectFieldI18nValues
			).build(),
			"en_US", objectEntry.getId());
		_assertLocalizedValues(
			HashMapBuilder.<String, Object>put(
				"localizedLongTextObjectFieldName",
				"pt_BR localizedLongTextObjectFieldValue"
			).put(
				"localizedRichTextObjectFieldName",
				"pt_BR <i>localizedRichTextObjectFieldValue</i>"
			).put(
				"localizedRichTextObjectFieldNameRawText",
				"pt_BR localizedRichTextObjectFieldValue"
			).put(
				"localizedTextObjectFieldName",
				"pt_BR localizedTextObjectFieldValue"
			).putAll(
				_localizedObjectFieldI18nValues
			).build(),
			"pt_BR", objectEntry.getId());

		_localizedObjectFieldI18nValues = HashMapBuilder.<String, Object>put(
			"localizedLongTextObjectFieldName_i18n",
			HashMapBuilder.put(
				"en_US", "en_US localizedLongTextObjectFieldValue"
			).put(
				"invalid_languageId", ""
			).build()
		).put(
			"localizedRichTextObjectFieldName_i18n",
			HashMapBuilder.put(
				"ar_SA", "ar_SA localizedRichTextObjectFieldValue"
			).build()
		).build();

		_updateLocalizedObjectEntryValues(
			_objectDefinition2, objectEntry.getId(),
			_localizedObjectFieldI18nValues);

		_localizedObjectFieldI18nValues.putAll(
			HashMapBuilder.<String, Object>put(
				"localizedLongTextObjectFieldName_i18n",
				HashMapBuilder.put(
					"en_US", "en_US localizedLongTextObjectFieldValue"
				).build()
			).put(
				"localizedTextObjectFieldName_i18n", new HashMap<>()
			).build());

		_assertLocalizedValues(
			HashMapBuilder.<String, Object>put(
				"localizedLongTextObjectFieldName", ""
			).put(
				"localizedRichTextObjectFieldName",
				"ar_SA localizedRichTextObjectFieldValue"
			).put(
				"localizedRichTextObjectFieldNameRawText",
				"ar_SA localizedRichTextObjectFieldValue"
			).put(
				"localizedTextObjectFieldName", ""
			).putAll(
				_localizedObjectFieldI18nValues
			).build(),
			"ar_SA", objectEntry.getId());
		_assertLocalizedValues(
			HashMapBuilder.<String, Object>put(
				"localizedLongTextObjectFieldName",
				"en_US localizedLongTextObjectFieldValue"
			).put(
				"localizedRichTextObjectFieldName", ""
			).put(
				"localizedRichTextObjectFieldNameRawText", ""
			).put(
				"localizedTextObjectFieldName", ""
			).putAll(
				_localizedObjectFieldI18nValues
			).build(),
			"en_US", objectEntry.getId());
		_assertLocalizedValues(
			HashMapBuilder.<String, Object>put(
				"localizedLongTextObjectFieldName", ""
			).put(
				"localizedRichTextObjectFieldName", ""
			).put(
				"localizedRichTextObjectFieldNameRawText", ""
			).put(
				"localizedTextObjectFieldName", ""
			).putAll(
				_localizedObjectFieldI18nValues
			).build(),
			"pt_BR", objectEntry.getId());

		_localizedObjectFieldI18nValues = HashMapBuilder.<String, Object>put(
			"localizedLongTextObjectFieldName_i18n",
			HashMapBuilder.put(
				"en_US", "en_US localizedLongTextObjectFieldValue"
			).put(
				"pt_BR", ""
			).build()
		).put(
			"localizedRichTextObjectFieldName_i18n",
			HashMapBuilder.put(
				"ar_SA", "ar_SA localizedRichTextObjectFieldValue"
			).put(
				"ca_ES", "ca_ES localizedRichTextObjectFieldValue"
			).put(
				"en_US", "en_US <i>localizedRichTextObjectFieldValue</i>"
			).build()
		).put(
			"localizedTextObjectFieldName_i18n",
			HashMapBuilder.put(
				"en_US", "en_US localizedTextObjectFieldValue"
			).put(
				"pt_BR", "pt_BR localizedTextObjectFieldValue"
			).build()
		).build();

		_updateLocalizedObjectEntryValues(
			_objectDefinition2, objectEntry.getId(),
			_localizedObjectFieldI18nValues);

		_localizedObjectFieldI18nValues.put(
			"localizedLongTextObjectFieldName_i18n",
			HashMapBuilder.put(
				"en_US", "en_US localizedLongTextObjectFieldValue"
			).build());

		_assertLocalizedValues(
			HashMapBuilder.<String, Object>put(
				"localizedLongTextObjectFieldName", ""
			).put(
				"localizedRichTextObjectFieldName",
				"ar_SA localizedRichTextObjectFieldValue"
			).put(
				"localizedRichTextObjectFieldNameRawText",
				"ar_SA localizedRichTextObjectFieldValue"
			).put(
				"localizedTextObjectFieldName", ""
			).putAll(
				_localizedObjectFieldI18nValues
			).build(),
			"ar_SA", objectEntry.getId());
		_assertLocalizedValues(
			HashMapBuilder.<String, Object>put(
				"localizedLongTextObjectFieldName", ""
			).put(
				"localizedRichTextObjectFieldName",
				"ca_ES localizedRichTextObjectFieldValue"
			).put(
				"localizedRichTextObjectFieldNameRawText",
				"ca_ES localizedRichTextObjectFieldValue"
			).put(
				"localizedTextObjectFieldName", ""
			).putAll(
				_localizedObjectFieldI18nValues
			).build(),
			"ca_ES", objectEntry.getId());
		_assertLocalizedValues(
			HashMapBuilder.<String, Object>put(
				"localizedLongTextObjectFieldName",
				"en_US localizedLongTextObjectFieldValue"
			).put(
				"localizedRichTextObjectFieldName",
				"en_US <i>localizedRichTextObjectFieldValue</i>"
			).put(
				"localizedRichTextObjectFieldNameRawText",
				"en_US localizedRichTextObjectFieldValue"
			).put(
				"localizedTextObjectFieldName",
				"en_US localizedTextObjectFieldValue"
			).putAll(
				_localizedObjectFieldI18nValues
			).build(),
			"en_US", objectEntry.getId());
		_assertLocalizedValues(
			HashMapBuilder.<String, Object>put(
				"localizedLongTextObjectFieldName", ""
			).put(
				"localizedRichTextObjectFieldName", ""
			).put(
				"localizedRichTextObjectFieldNameRawText", ""
			).put(
				"localizedTextObjectFieldName",
				"pt_BR localizedTextObjectFieldValue"
			).putAll(
				_localizedObjectFieldI18nValues
			).build(),
			"pt_BR", objectEntry.getId());
	}

	@Test
	public void testUpdateObjectEntryWithAccountEntryRestricted()
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

		_defaultObjectEntryManager.updateObjectEntry(
			_simpleDTOConverterContext, _objectDefinition3,
			objectEntry2.getId(), objectEntry2);

		_resourcePermissionLocalService.removeResourcePermission(
			companyId, _objectDefinition3.getClassName(),
			ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
			role.getRoleId(), ActionKeys.UPDATE);

		AssertUtils.assertFailure(
			PrincipalException.MustHavePermission.class,
			StringBundler.concat(
				"User ", _user.getUserId(), " must have UPDATE permission for ",
				_objectDefinition3.getClassName(), StringPool.SPACE,
				objectEntry1.getId()),
			() -> _defaultObjectEntryManager.updateObjectEntry(
				_simpleDTOConverterContext, _objectDefinition3,
				objectEntry1.getId(), objectEntry1));
		AssertUtils.assertFailure(
			PrincipalException.MustHavePermission.class,
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
			companyId, _objectDefinition3.getClassName(),
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

		AssertUtils.assertFailure(
			PrincipalException.MustHavePermission.class,
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

		_addAccountEntryOrganizationRel(accountEntry1, organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_addAccountEntryOrganizationRel(accountEntry2, organization2);

		AssertUtils.assertFailure(
			PrincipalException.MustHavePermission.class,
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

		_addAccountEntryOrganizationRel(accountEntry1, suborganization1);

		Organization suborganization2 = OrganizationTestUtil.addOrganization(
			organization2.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_addAccountEntryOrganizationRel(accountEntry2, suborganization2);

		_user = _addUser();

		_assignOrganizationRole(organization1, _accountManagerRole, _user);

		_assertObjectEntriesSize(1);

		AssertUtils.assertFailure(
			PrincipalException.MustHavePermission.class,
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
	}

	@Override
	protected void assertObjectEntryProperties(
			ObjectEntry actualObjectEntry,
			Map<String, Object> actualObjectEntryProperties,
			Map.Entry<String, Object> expectedEntry)
		throws Exception {

		if (Objects.equals(
				expectedEntry.getKey(), "attachmentObjectFieldName")) {

			FileEntry fileEntry = (FileEntry)actualObjectEntryProperties.get(
				expectedEntry.getKey());

			Assert.assertEquals(expectedEntry.getValue(), fileEntry.getId());

			DLFileEntry dlFileEntry = _dlFileEntryLocalService.getFileEntry(
				fileEntry.getId());

			Assert.assertEquals(fileEntry.getName(), dlFileEntry.getFileName());

			Link link = fileEntry.getLink();

			Assert.assertEquals(link.getLabel(), dlFileEntry.getFileName());

			com.liferay.portal.kernel.repository.model.FileEntry
				repositoryFileEntry = _dlAppService.getFileEntry(
					fileEntry.getId());

			String url = _dlURLHelper.getDownloadURL(
				repositoryFileEntry, repositoryFileEntry.getFileVersion(), null,
				StringPool.BLANK);

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

				return;
			}

			Assert.assertEquals(
				expectedEntry.getKey(),
				expectedEntry.getValue() + " 00:00:00.0",
				String.valueOf(
					actualObjectEntryProperties.get(expectedEntry.getKey())));
		}
		else if (Objects.equals(
					expectedEntry.getKey(), "picklistObjectFieldName")) {

			ListEntry listEntry = (ListEntry)actualObjectEntryProperties.get(
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
					expectedEntry.getKey(), "richTextObjectFieldNameRawText")) {

			Assert.assertEquals(
				HtmlParserUtil.extractText(
					String.valueOf(expectedEntry.getValue())),
				String.valueOf(
					actualObjectEntryProperties.get(expectedEntry.getKey())));
		}
		else if (Objects.equals(
					expectedEntry.getKey(),
					_objectRelationshipERCObjectFieldName)) {

			Assert.assertEquals(
				expectedEntry.getValue(),
				actualObjectEntryProperties.get(expectedEntry.getKey()));

			assertEquals(
				(ObjectEntry)actualObjectEntryProperties.get(
					StringUtil.replaceLast(
						_objectRelationshipFieldName, "Id", StringPool.BLANK)),
				_objectEntryManager.getObjectEntry(
					_objectDefinition1.getCompanyId(),
					_simpleDTOConverterContext,
					GetterUtil.getString(expectedEntry.getValue()),
					_objectDefinition1, null));
		}
		else {
			super.assertObjectEntryProperties(
				actualObjectEntry, actualObjectEntryProperties, expectedEntry);
		}
	}

	@Override
	protected Page<ObjectEntry> getObjectEntries(
			Map<String, String> context, Sort[] sorts)
		throws Exception {

		return _defaultObjectEntryManager.getObjectEntries(
			companyId, _objectDefinition2, null, null, dtoConverterContext,
			context.get("filter"), null, context.get("search"), sorts);
	}

	private AccountEntry _addAccountEntry() throws Exception {
		return _accountEntryLocalService.addAccountEntry(
			adminUser.getUserId(), 0L, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null, null, null,
			RandomTestUtil.randomString(),
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());
	}

	private void _addAccountEntryOrganizationRel(
			AccountEntry accountEntry, Organization organization)
		throws Exception {

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry.getAccountEntryId(), organization.getOrganizationId());
	}

	private void _addAggregationObjectField(
			String argumentObjectFieldName, String functionName,
			long objectDefinitionId, String objectFieldName,
			String objectRelationshipName)
		throws Exception {

		List<ObjectFieldSetting> objectFieldSettings = new ArrayList<>();

		objectFieldSettings.add(
			_createObjectFieldSetting("function", functionName));

		if (!Objects.equals(functionName, "COUNT")) {
			objectFieldSettings.add(
				_createObjectFieldSetting(
					"objectFieldName", argumentObjectFieldName));
		}

		objectFieldSettings.add(
			_createObjectFieldSetting(
				"objectRelationshipName", objectRelationshipName));

		_addCustomObjectField(
			new AggregationObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				objectFieldName
			).objectDefinitionId(
				objectDefinitionId
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
			objectField.getReadOnly(),
			objectField.getReadOnlyConditionExpression(),
			objectField.isRequired(), objectField.isState(),
			objectField.getObjectFieldSettings());
	}

	private String _addListTypeEntry() throws Exception {
		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.addListTypeEntry(
				null, adminUser.getUserId(),
				listTypeDefinition.getListTypeDefinitionId(),
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
			companyId, name, ResourceConstants.SCOPE_GROUP_TEMPLATE, "0",
			role.getRoleId(), actionId);
	}

	private Role _addRoleUser(
			String[] actionIds, ObjectDefinition objectDefinition, User user)
		throws Exception {

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_resourcePermissionLocalService.setResourcePermissions(
			companyId, objectDefinition.getClassName(),
			ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
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

		assertEquals(
			_defaultObjectEntryManager.getObjectEntry(
				_simpleDTOConverterContext, _objectDefinition1,
				objectEntry.getId()),
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"countAggregationObjectFieldName1",
						String.valueOf(expectedValue)
					).build();
				}
			});
	}

	private void _assertLocalizedValues(
			Map<String, Object> expectedLocalizedValues, String languageId,
			long objectEntryId)
		throws Exception {

		_user.setLanguageId(languageId);

		_user = _userLocalService.updateUser(_user);

		assertEquals(
			_defaultObjectEntryManager.getObjectEntry(
				new DefaultDTOConverterContext(
					false, Collections.emptyMap(), dtoConverterRegistry, null,
					LocaleUtil.getDefault(), null, _user),
				_objectDefinition2, objectEntryId),
			new ObjectEntry() {
				{
					properties = expectedLocalizedValues;
				}
			});
	}

	private void _assertObjectEntriesSize(long size) throws Exception {
		Page<ObjectEntry> page = _defaultObjectEntryManager.getObjectEntries(
			companyId, _objectDefinition3, null, null,
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), dtoConverterRegistry, null,
				LocaleUtil.getDefault(), null, _user),
			StringPool.BLANK, null, null, null);

		Collection<ObjectEntry> objectEntries = page.getItems();

		Assert.assertEquals(
			objectEntries.toString(), size, objectEntries.size());
	}

	private void _assertPicklistOjectField(
			ListEntry expectedListEntry, Object picklistObjectFieldValue)
		throws Exception {

		assertEquals(
			_defaultObjectEntryManager.addObjectEntry(
				dtoConverterContext, _objectDefinition2,
				new ObjectEntry() {
					{
						properties = HashMapBuilder.<String, Object>put(
							"picklistObjectFieldName", picklistObjectFieldValue
						).build();
					}
				},
				ObjectDefinitionConstants.SCOPE_COMPANY),
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"picklistObjectFieldName", expectedListEntry
					).build();
				}
			});
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
			companyId, organization.getOrganizationId());

		_userGroupRoleLocalService.addUserGroupRole(
			user.getUserId(), group.getGroupId(), role.getRoleId());
	}

	private String _buildContainsExpressionFilterString(
		String fieldName, String value) {

		return StringBundler.concat("contains( ", fieldName, ",'", value, "')");
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
			objectDefinitionLocalService.addCustomObjectDefinition(
				adminUser.getUserId(), 0, false, true, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT, objectFields);

		return objectDefinitionLocalService.publishCustomObjectDefinition(
			adminUser.getUserId(), objectDefinition.getObjectDefinitionId());
	}

	private ObjectFieldSetting _createObjectFieldSetting(
		String name, String value) {

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.createObjectFieldSetting(0L);

		objectFieldSetting.setName(name);
		objectFieldSetting.setValue(value);

		return objectFieldSetting;
	}

	private void _deleteAccountEntryOrganizationRel(
			AccountEntry accountEntry, Organization organization)
		throws Exception {

		_accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRel(
				accountEntry.getAccountEntryId(),
				organization.getOrganizationId());
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
			companyId, name, ResourceConstants.SCOPE_GROUP_TEMPLATE, "0",
			role.getRoleId(), actionId);
	}

	private void _testAddObjectEntryAccountEntryRestriction(
			AccountEntry accountEntry)
		throws Exception {

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry.getAccountEntryId(), _user.getUserId());

		AssertUtils.assertFailure(
			PrincipalException.MustHavePermission.class,
			StringBundler.concat(
				"User ", _user.getUserId(),
				" must have ADD_OBJECT_ENTRY permission for ",
				_objectDefinition3.getResourceName(), StringPool.SPACE),
			() -> _addObjectEntry(accountEntry));

		_addResourcePermission(ObjectActionKeys.ADD_OBJECT_ENTRY, _buyerRole);

		_userGroupRoleLocalService.addUserGroupRole(
			_user.getUserId(), accountEntry.getAccountEntryGroupId(),
			_buyerRole.getRoleId());

		Assert.assertNotNull(_addObjectEntry(accountEntry));

		_userGroupRoleLocalService.deleteUserGroupRolesByUserId(
			_user.getUserId());

		_removeResourcePermission(
			ObjectActionKeys.ADD_OBJECT_ENTRY, _buyerRole);
	}

	private void _updateLocalizedObjectEntryValues(
			ObjectDefinition objectDefinition, long objectEntryId,
			Map<String, Object> values)
		throws Exception {

		_defaultObjectEntryManager.updateObjectEntry(
			_simpleDTOConverterContext, objectDefinition, objectEntryId,
			new ObjectEntry() {
				{
					properties = values;
				}
			});
	}

	private static DefaultObjectEntryManager _defaultObjectEntryManager;
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
	private GroupLocalService _groupLocalService;

	@Inject
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	private Map<String, Object> _localizedObjectFieldI18nValues;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition2;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition3;

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