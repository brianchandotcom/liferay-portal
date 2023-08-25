/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.definition.tree.Node;
import com.liferay.object.definition.tree.Tree;
import com.liferay.object.definition.tree.TreeFactory;
import com.liferay.object.exception.NoSuchObjectFieldException;
import com.liferay.object.exception.ObjectDefinitionAccountEntryRestrictedException;
import com.liferay.object.exception.ObjectDefinitionActiveException;
import com.liferay.object.exception.ObjectDefinitionEnableObjectEntryHistoryException;
import com.liferay.object.exception.ObjectDefinitionExternalReferenceCodeException;
import com.liferay.object.exception.ObjectDefinitionLabelException;
import com.liferay.object.exception.ObjectDefinitionModifiableException;
import com.liferay.object.exception.ObjectDefinitionNameException;
import com.liferay.object.exception.ObjectDefinitionPluralLabelException;
import com.liferay.object.exception.ObjectDefinitionRootObjectDefinitionIdException;
import com.liferay.object.exception.ObjectDefinitionScopeException;
import com.liferay.object.exception.ObjectDefinitionStatusException;
import com.liferay.object.exception.ObjectDefinitionVersionException;
import com.liferay.object.exception.ObjectFieldRelationshipTypeException;
import com.liferay.object.field.builder.BooleanObjectFieldBuilder;
import com.liferay.object.field.builder.DateObjectFieldBuilder;
import com.liferay.object.field.builder.LongIntegerObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectActionLocalServiceUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.service.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.service.test.util.TreeTestUtil;
import com.liferay.object.system.BaseSystemObjectDefinitionManager;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.model.UserNotificationEventTable;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.sql.Connection;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@FeatureFlags("LPS-167253")
@RunWith(Arquillian.class)
public class ObjectDefinitionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddCustomObjectDefinition() throws Exception {

		// Label is null

		AssertUtils.assertFailure(
			ObjectDefinitionLabelException.class,
			"Label is null for locale " + LocaleUtil.US.getDisplayName(),
			() -> _addCustomObjectDefinition("", "Test", "Tests"));

		// Name

		_objectDefinitionLocalService.deleteObjectDefinition(
			_addCustomObjectDefinition(" Test "));
		_objectDefinitionLocalService.deleteObjectDefinition(
			_addCustomObjectDefinition(
				"A123456789a123456789a123456789a1234567891"));

		AssertUtils.assertFailure(
			ObjectDefinitionNameException.MustBeLessThan41Characters.class,
			"Name must be less than 41 characters",
			() -> _addCustomObjectDefinition(
				"A123456789a123456789a123456789a12345678912"));
		AssertUtils.assertFailure(
			ObjectDefinitionNameException.MustBeginWithUpperCaseLetter.class,
			"The first character of a name must be an upper case letter",
			() -> _addCustomObjectDefinition("test"));

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(), StringUtil.randomId())));

		objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId());

		AssertUtils.assertFailure(
			ObjectDefinitionNameException.MustNotBeDuplicate.class,
			"Duplicate name C_Test", () -> _addCustomObjectDefinition("Test"));

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);

		AssertUtils.assertFailure(
			ObjectDefinitionNameException.MustNotBeNull.class, "Name is null",
			() -> _addCustomObjectDefinition("Test", "", "Tests"));
		AssertUtils.assertFailure(
			ObjectDefinitionNameException.MustOnlyContainLettersAndDigits.class,
			"Name must only contain letters and digits",
			() -> _addCustomObjectDefinition("Tes t"));
		AssertUtils.assertFailure(
			ObjectDefinitionNameException.MustOnlyContainLettersAndDigits.class,
			"Name must only contain letters and digits",
			() -> _addCustomObjectDefinition("Tes-t"));

		// Plural label is null

		AssertUtils.assertFailure(
			ObjectDefinitionPluralLabelException.class,
			"Plural label is null for locale " + LocaleUtil.US.getDisplayName(),
			() -> _addCustomObjectDefinition("Test", "Test", ""));

		// Scope is null

		AssertUtils.assertFailure(
			ObjectDefinitionScopeException.class, "Scope is null",
			() -> _objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, "", ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(),
						StringUtil.randomId()))));

		// No object scope provider found with key

		String scope = RandomTestUtil.randomString();

		AssertUtils.assertFailure(
			ObjectDefinitionScopeException.class,
			"No object scope provider found with key " + scope,
			() -> _objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, scope, ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(),
						StringUtil.randomId()))));

		AssertUtils.assertFailure(
			ObjectDefinitionScopeException.class,
			StringBundler.concat(
				"Scope \"", ObjectDefinitionConstants.SCOPE_SITE,
				"\" cannot be associated with storage type \"",
				ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE),
			() -> _objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_SITE,
				ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(),
						StringUtil.randomId()))));

		// Name, database table, resources, and status

		objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, "Able", "able",
						false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, "Baker", "baker",
						false)));

		ObjectFieldUtil.addCustomObjectField(
			new TextObjectFieldBuilder(
			).userId(
				TestPropsValues.getUserId()
			).labelMap(
				LocalizedMapUtil.getLocalizedMap("Charlie")
			).name(
				"charlie"
			).objectDefinitionId(
				objectDefinition.getObjectDefinitionId()
			).required(
				true
			).build());

		// Custom object definition names are automatically prepended with
		// with "C_"

		Assert.assertEquals("C_Test", objectDefinition.getName());

		// Before publish, database table

		Assert.assertFalse(_hasTable(objectDefinition.getDBTableName()));
		Assert.assertFalse(
			_hasTable(objectDefinition.getExtensionDBTableName()));

		Tree tree = TreeTestUtil.createTree(
			_objectDefinitionLocalService, _objectRelationshipLocalService,
			_treeFactory);

		_assertNodeObjectDefinitions(
			tree,
			nodeObjectDefinition -> {
				Assert.assertFalse(
					_hasTable(nodeObjectDefinition.getDBTableName()));
				Assert.assertFalse(
					_hasTable(nodeObjectDefinition.getExtensionDBTableName()));
			});

		// Before publish, resources

		Assert.assertEquals(
			0,
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getClassName()));
		Assert.assertEquals(
			0,
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getPortletId()));
		Assert.assertEquals(
			0,
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getResourceName()));
		Assert.assertEquals(
			1,
			_resourcePermissionLocalService.getResourcePermissionsCount(
				objectDefinition.getCompanyId(),
				ObjectDefinition.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectDefinition.getObjectDefinitionId())));

		_assertNodeObjectDefinitions(
			tree,
			nodeObjectDefinition -> {
				Assert.assertEquals(
					0,
					_resourceActionLocalService.getResourceActionsCount(
						nodeObjectDefinition.getClassName()));
				Assert.assertEquals(
					0,
					_resourceActionLocalService.getResourceActionsCount(
						nodeObjectDefinition.getPortletId()));
				Assert.assertEquals(
					0,
					_resourceActionLocalService.getResourceActionsCount(
						nodeObjectDefinition.getResourceName()));
				Assert.assertEquals(
					1,
					_resourcePermissionLocalService.getResourcePermissionsCount(
						nodeObjectDefinition.getCompanyId(),
						ObjectDefinition.class.getName(),
						ResourceConstants.SCOPE_INDIVIDUAL,
						String.valueOf(
							nodeObjectDefinition.getObjectDefinitionId())));
			});

		// Before publish, status

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, objectDefinition.getStatus());

		_assertNodeObjectDefinitions(
			tree,
			nodeObjectDefinition -> Assert.assertEquals(
				WorkflowConstants.STATUS_DRAFT,
				nodeObjectDefinition.getStatus()));

		// Publish

		objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId());

		ObjectFieldUtil.addCustomObjectField(
			new TextObjectFieldBuilder(
			).userId(
				TestPropsValues.getUserId()
			).labelMap(
				LocalizedMapUtil.getLocalizedMap("Dog")
			).name(
				"dog"
			).objectDefinitionId(
				objectDefinition.getObjectDefinitionId()
			).required(
				true
			).build());

		_assertNodeObjectDefinitions(
			tree,
			nodeObjectDefinition -> {
				if (nodeObjectDefinition.isRootDescendantNode()) {
					AssertUtils.assertFailure(
						ObjectDefinitionStatusException.class,
						"Nonroot object definitions within a hierarchical " +
							"structure are ineligible for publication",
						() ->
							_objectDefinitionLocalService.
								publishCustomObjectDefinition(
									TestPropsValues.getUserId(),
									nodeObjectDefinition.
										getObjectDefinitionId()));
				}
			});

		ObjectDefinition rootObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				TestPropsValues.getCompanyId(), "C_A");

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			rootObjectDefinition.getObjectDefinitionId());

		// After publish, database table

		Assert.assertFalse(
			_hasColumn(objectDefinition.getDBTableName(), "able"));
		Assert.assertTrue(
			_hasColumn(objectDefinition.getDBTableName(), "able_"));
		Assert.assertFalse(
			_hasColumn(objectDefinition.getDBTableName(), "baker"));
		Assert.assertTrue(
			_hasColumn(objectDefinition.getDBTableName(), "baker_"));
		Assert.assertFalse(
			_hasColumn(objectDefinition.getDBTableName(), "charlie"));
		Assert.assertTrue(
			_hasColumn(objectDefinition.getDBTableName(), "charlie_"));
		Assert.assertFalse(
			_hasColumn(objectDefinition.getExtensionDBTableName(), "dog"));
		Assert.assertTrue(
			_hasColumn(objectDefinition.getExtensionDBTableName(), "dog_"));
		Assert.assertTrue(_hasTable(objectDefinition.getDBTableName()));
		Assert.assertTrue(
			_hasTable(objectDefinition.getExtensionDBTableName()));

		_assertNodeObjectDefinitions(
			tree,
			nodeObjectDefinition -> {
				Assert.assertFalse(
					_hasColumn(nodeObjectDefinition.getDBTableName(), "able"));
				Assert.assertTrue(
					_hasColumn(nodeObjectDefinition.getDBTableName(), "able_"));
			});

		// After publish, resources

		Assert.assertEquals(
			4,
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getClassName()));
		Assert.assertEquals(
			6,
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getPortletId()));
		Assert.assertEquals(
			2,
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getResourceName()));
		Assert.assertEquals(
			1,
			_resourcePermissionLocalService.getResourcePermissionsCount(
				objectDefinition.getCompanyId(),
				ObjectDefinition.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectDefinition.getObjectDefinitionId())));

		_assertNodeObjectDefinitions(
			tree,
			nodeObjectDefinition -> {
				Assert.assertEquals(
					4,
					_resourceActionLocalService.getResourceActionsCount(
						nodeObjectDefinition.getClassName()));
				Assert.assertEquals(
					6,
					_resourceActionLocalService.getResourceActionsCount(
						nodeObjectDefinition.getPortletId()));
				Assert.assertEquals(
					2,
					_resourceActionLocalService.getResourceActionsCount(
						nodeObjectDefinition.getResourceName()));
				Assert.assertEquals(
					1,
					_resourcePermissionLocalService.getResourcePermissionsCount(
						nodeObjectDefinition.getCompanyId(),
						ObjectDefinition.class.getName(),
						ResourceConstants.SCOPE_INDIVIDUAL,
						String.valueOf(
							nodeObjectDefinition.getObjectDefinitionId())));
			});

		// After publish, status

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, objectDefinition.getStatus());

		_assertNodeObjectDefinitions(
			tree,
			nodeObjectDefinition -> Assert.assertEquals(
				WorkflowConstants.STATUS_APPROVED,
				nodeObjectDefinition.getStatus()));

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);

		_deleteObjectDefinitionHierarchy();
	}

	@Test
	public void testAddObjectDefinition() throws Exception {
		AssertUtils.assertFailure(
			ObjectDefinitionModifiableException.MustBeModifiable.class,
			"A modifiable object definition is required",
			() -> _objectDefinitionLocalService.addObjectDefinition(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(), 0,
				false, false));
		AssertUtils.assertFailure(
			ObjectDefinitionModifiableException.MustBeModifiable.class,
			"A modifiable object definition is required",
			() -> _objectDefinitionLocalService.addObjectDefinition(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(), 0,
				false, true));

		_testAddObjectDefinition(true, false);
		_testAddObjectDefinition(true, true);
	}

	@Test
	public void testAddOrUpdateSystemObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addOrUpdateSystemObjectDefinition(
				TestPropsValues.getCompanyId(), 0,
				new BaseSystemObjectDefinitionManager() {

					@Override
					public long addBaseModel(
							User user, Map<String, Object> values)
						throws Exception {

						return 0;
					}

					@Override
					public BaseModel<?> deleteBaseModel(BaseModel<?> baseModel)
						throws PortalException {

						return null;
					}

					public BaseModel<?> fetchBaseModelByExternalReferenceCode(
						String externalReferenceCode, long companyId) {

						return null;
					}

					@Override
					public BaseModel<?> getBaseModelByExternalReferenceCode(
							String externalReferenceCode, long companyId)
						throws PortalException {

						return null;
					}

					@Override
					public String getBaseModelExternalReferenceCode(
							long primaryKey)
						throws PortalException {

						return null;
					}

					@Override
					public String getExternalReferenceCode() {
						return "L_USER_NOTIFICATION_EVENT";
					}

					@Override
					public JaxRsApplicationDescriptor
						getJaxRsApplicationDescriptor() {

						return null;
					}

					@Override
					public Map<Locale, String> getLabelMap() {
						return LocalizedMapUtil.getLocalizedMap(
							"User Notification Event");
					}

					@Override
					public Class<?> getModelClass() {
						return UserNotificationEvent.class;
					}

					@Override
					public List<ObjectAction> getObjectActions() {
						return Collections.singletonList(
							_createObjectAction("updateDeliveryType1"));
					}

					@Override
					public List<ObjectField> getObjectFields() {
						return Arrays.asList(
							new BooleanObjectFieldBuilder(
							).labelMap(
								createLabelMap("Action Required")
							).name(
								"actionRequired"
							).required(
								true
							).build(),
							new LongIntegerObjectFieldBuilder(
							).labelMap(
								createLabelMap("Delivery Type")
							).name(
								"deliveryType"
							).build(),
							new TextObjectFieldBuilder(
							).dbColumnName(
								"type_"
							).labelMap(
								createLabelMap("Type")
							).name(
								"type"
							).required(
								true
							).build());
					}

					@Override
					public Map<Locale, String> getPluralLabelMap() {
						return LocalizedMapUtil.getLocalizedMap(
							"User Notification Events");
					}

					@Override
					public Column<?, Long> getPrimaryKeyColumn() {
						return UserNotificationEventTable.INSTANCE.
							userNotificationEventId;
					}

					@Override
					public String getScope() {
						return ObjectDefinitionConstants.SCOPE_COMPANY;
					}

					@Override
					public Table getTable() {
						return UserNotificationEventTable.INSTANCE;
					}

					@Override
					public int getVersion() {
						return 1;
					}

					@Override
					public void updateBaseModel(
							long primaryKey, User user,
							Map<String, Object> values)
						throws Exception {
					}

					@Override
					public long upsertBaseModel(
						String externalReferenceCode, long companyId, User user,
						Map<String, Object> values) {

						return 0;
					}

				});

		Assert.assertEquals(
			"UserNotificationEvent", objectDefinition.getDBTableName());
		Assert.assertEquals(
			"userNotificationEventId",
			objectDefinition.getPKObjectFieldDBColumnName());
		Assert.assertEquals(
			"userNotificationEventId", objectDefinition.getPKObjectFieldName());
		Assert.assertEquals(objectDefinition.isSystem(), true);
		Assert.assertEquals(1, objectDefinition.getVersion());

		_assertObjectField(
			objectDefinition, "actionRequired", "Boolean", "actionRequired",
			true);

		try {
			_objectFieldLocalService.getObjectField(
				objectDefinition.getObjectDefinitionId(), "archived");

			Assert.fail();
		}
		catch (NoSuchObjectFieldException noSuchObjectFieldException) {
			Assert.assertNotNull(noSuchObjectFieldException);
		}

		_assertObjectField(
			objectDefinition, "deliveryType", "Long", "deliveryType", false);
		_assertObjectField(objectDefinition, "type_", "String", "type", true);

		Assert.assertNotNull(
			_objectActionLocalService.getObjectAction(
				objectDefinition.getObjectDefinitionId(), "updateDeliveryType1",
				ObjectActionTriggerConstants.KEY_ON_AFTER_ADD));

		objectDefinition =
			_objectDefinitionLocalService.addOrUpdateSystemObjectDefinition(
				TestPropsValues.getCompanyId(), 0,
				new BaseSystemObjectDefinitionManager() {

					@Override
					public long addBaseModel(
							User user, Map<String, Object> values)
						throws Exception {

						return 0;
					}

					@Override
					public BaseModel<?> deleteBaseModel(BaseModel<?> baseModel)
						throws PortalException {

						return null;
					}

					public BaseModel<?> fetchBaseModelByExternalReferenceCode(
						String externalReferenceCode, long companyId) {

						return null;
					}

					@Override
					public BaseModel<?> getBaseModelByExternalReferenceCode(
							String externalReferenceCode, long companyId)
						throws PortalException {

						return null;
					}

					@Override
					public String getBaseModelExternalReferenceCode(
							long primaryKey)
						throws PortalException {

						return null;
					}

					@Override
					public String getExternalReferenceCode() {
						return "L_USER_NOTIFICATION_EVENT";
					}

					@Override
					public JaxRsApplicationDescriptor
						getJaxRsApplicationDescriptor() {

						return null;
					}

					@Override
					public Map<Locale, String> getLabelMap() {
						return LocalizedMapUtil.getLocalizedMap(
							"User Notification Event");
					}

					@Override
					public Class<?> getModelClass() {
						return UserNotificationEvent.class;
					}

					@Override
					public List<ObjectAction> getObjectActions() {
						return Collections.singletonList(
							_createObjectAction("updateDeliveryType2"));
					}

					@Override
					public List<ObjectField> getObjectFields() {
						return Arrays.asList(
							new BooleanObjectFieldBuilder(
							).labelMap(
								createLabelMap("Archived")
							).name(
								"archived"
							).required(
								true
							).build(),
							new LongIntegerObjectFieldBuilder(
							).labelMap(
								createLabelMap("Delivery Type")
							).name(
								"deliveryType"
							).required(
								true
							).build(),
							new TextObjectFieldBuilder(
							).dbColumnName(
								"type_"
							).labelMap(
								createLabelMap("Type")
							).name(
								"type"
							).build());
					}

					@Override
					public Map<Locale, String> getPluralLabelMap() {
						return LocalizedMapUtil.getLocalizedMap(
							"User Notification Events");
					}

					@Override
					public Column<?, Long> getPrimaryKeyColumn() {
						return UserNotificationEventTable.INSTANCE.
							userNotificationEventId;
					}

					@Override
					public String getScope() {
						return ObjectDefinitionConstants.SCOPE_COMPANY;
					}

					@Override
					public Table getTable() {
						return UserNotificationEventTable.INSTANCE;
					}

					@Override
					public int getVersion() {
						return 2;
					}

					@Override
					public void updateBaseModel(
							long primaryKey, User user,
							Map<String, Object> values)
						throws Exception {
					}

					@Override
					public long upsertBaseModel(
						String externalReferenceCode, long companyId, User user,
						Map<String, Object> values) {

						return 0;
					}

				});

		Assert.assertEquals(2, objectDefinition.getVersion());

		try {
			_objectFieldLocalService.getObjectField(
				objectDefinition.getObjectDefinitionId(), "actionRequired");

			Assert.fail();
		}
		catch (NoSuchObjectFieldException noSuchObjectFieldException) {
			Assert.assertNotNull(noSuchObjectFieldException);
		}

		_assertObjectField(
			objectDefinition, "archived", "Boolean", "archived", true);
		_assertObjectField(
			objectDefinition, "deliveryType", "Long", "deliveryType", true);
		_assertObjectField(objectDefinition, "type_", "String", "type", false);

		Assert.assertNotNull(
			_objectActionLocalService.getObjectAction(
				objectDefinition.getObjectDefinitionId(), "updateDeliveryType2",
				ObjectActionTriggerConstants.KEY_ON_AFTER_ADD));

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
	}

	@Test
	public void testAddSystemObjectDefinition() throws Exception {

		// External reference code

		String liferayMode = SystemProperties.get("liferay.mode");

		SystemProperties.clear("liferay.mode");

		try {
			AssertUtils.assertFailure(
				ObjectDefinitionExternalReferenceCodeException.
					ForbiddenUnmodifiableSystemObjectDefinitionExternalReferenceCode.class,
				"Forbidden unmodifiable system object definition external " +
					"reference code INVALID_TEST",
				() ->
					ObjectDefinitionTestUtil.
						addUnmodifiableSystemObjectDefinition(
							"INVALID_TEST", TestPropsValues.getUserId(), "Test",
							null,
							LocalizedMapUtil.getLocalizedMap(
								RandomTestUtil.randomString()),
							"Test", null, null,
							LocalizedMapUtil.getLocalizedMap(
								RandomTestUtil.randomString()),
							ObjectDefinitionConstants.SCOPE_COMPANY, null, 1,
							_objectDefinitionLocalService,
							Collections.<ObjectField>emptyList()));
		}
		finally {
			SystemProperties.set("liferay.mode", liferayMode);
		}

		// Label is null

		AssertUtils.assertFailure(
			ObjectDefinitionLabelException.class,
			"Label is null for locale " + LocaleUtil.US.getDisplayName(),
			() -> _addUnmodifiableSystemObjectDefinition(
				"", "Test", RandomTestUtil.randomString()));

		// Name

		_objectDefinitionLocalService.deleteObjectDefinition(
			_addUnmodifiableSystemObjectDefinition(" Test "));
		_objectDefinitionLocalService.deleteObjectDefinition(
			_addUnmodifiableSystemObjectDefinition(
				"A123456789a123456789a123456789a1234567891"));

		AssertUtils.assertFailure(
			ObjectDefinitionNameException.
				ForbiddenModifiableSystemObjectDefinitionName.class,
			"Forbidden modifiable system object definition name Invalid Test",
			() -> ObjectDefinitionTestUtil.addModifiableSystemObjectDefinition(
				TestPropsValues.getUserId(), null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Invalid Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_SITE, null, 1,
				_objectDefinitionLocalService,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(),
						StringUtil.randomId()))));
		AssertUtils.assertFailure(
			ObjectDefinitionNameException.MustBeLessThan41Characters.class,
			"Name must be less than 41 characters",
			() -> _addUnmodifiableSystemObjectDefinition(
				"A123456789a123456789a123456789a12345678912"));
		AssertUtils.assertFailure(
			ObjectDefinitionNameException.MustBeginWithUpperCaseLetter.class,
			"The first character of a name must be an upper case letter",
			() -> _addUnmodifiableSystemObjectDefinition("test"));

		ObjectDefinition objectDefinition =
			_addUnmodifiableSystemObjectDefinition("Test");

		AssertUtils.assertFailure(
			ObjectDefinitionNameException.MustNotBeDuplicate.class,
			"Duplicate name Test",
			() -> _addUnmodifiableSystemObjectDefinition("Test"));

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);

		AssertUtils.assertFailure(
			ObjectDefinitionNameException.MustNotBeNull.class, "Name is null",
			() -> _addUnmodifiableSystemObjectDefinition(""));
		AssertUtils.assertFailure(
			ObjectDefinitionNameException.
				MustNotStartWithCAndUnderscoreForSystemObject.class,
			"System object definition names must not start with \"C_\"",
			() -> _addUnmodifiableSystemObjectDefinition("C_Test"));
		AssertUtils.assertFailure(
			ObjectDefinitionNameException.
				MustNotStartWithCAndUnderscoreForSystemObject.class,
			"System object definition names must not start with \"C_\"",
			() -> _addUnmodifiableSystemObjectDefinition("c_Test"));
		AssertUtils.assertFailure(
			ObjectDefinitionNameException.MustOnlyContainLettersAndDigits.class,
			"Name must only contain letters and digits",
			() -> _addUnmodifiableSystemObjectDefinition("Tes t"));
		AssertUtils.assertFailure(
			ObjectDefinitionNameException.MustOnlyContainLettersAndDigits.class,
			"Name must only contain letters and digits",
			() -> _addUnmodifiableSystemObjectDefinition("Tes-t"));

		// Plural label is null

		AssertUtils.assertFailure(
			ObjectDefinitionPluralLabelException.class,
			"Plural label is null for locale " + LocaleUtil.US.getDisplayName(),
			() -> _addUnmodifiableSystemObjectDefinition(
				RandomTestUtil.randomString(), "Test", ""));

		// Scope is null

		AssertUtils.assertFailure(
			ObjectDefinitionScopeException.class, "Scope is null",
			() ->
				ObjectDefinitionTestUtil.addUnmodifiableSystemObjectDefinition(
					null, TestPropsValues.getUserId(), "Test", null,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					"Test", null, null,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					"", null, 1, _objectDefinitionLocalService,
					Collections.emptyList()));

		// No object scope provider found with key

		String scope = RandomTestUtil.randomString();

		AssertUtils.assertFailure(
			ObjectDefinitionScopeException.class,
			"No object scope provider found with key " + scope,
			() ->
				ObjectDefinitionTestUtil.addUnmodifiableSystemObjectDefinition(
					null, TestPropsValues.getUserId(), "Test", null,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					"Test", null, null,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					scope, null, 1, _objectDefinitionLocalService,
					Collections.emptyList()));

		// Version must greater than 0

		AssertUtils.assertFailure(
			ObjectDefinitionVersionException.class,
			"System object definition versions must greater than 0",
			() ->
				ObjectDefinitionTestUtil.addUnmodifiableSystemObjectDefinition(
					null, TestPropsValues.getUserId(), "Test", null,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					"Test", null, null,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					ObjectDefinitionConstants.SCOPE_COMPANY, null, -1,
					_objectDefinitionLocalService,
					Collections.<ObjectField>emptyList()));

		AssertUtils.assertFailure(
			ObjectDefinitionVersionException.class,
			"System object definition versions must greater than 0",
			() ->
				ObjectDefinitionTestUtil.addUnmodifiableSystemObjectDefinition(
					null, TestPropsValues.getUserId(), "Test", null,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					"Test", null, null,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					ObjectDefinitionConstants.SCOPE_COMPANY, null, 0,
					_objectDefinitionLocalService,
					Collections.<ObjectField>emptyList()));

		// Database table, messaging, resources, and status

		objectDefinition =
			ObjectDefinitionTestUtil.addUnmodifiableSystemObjectDefinition(
				null, TestPropsValues.getUserId(), "Test", null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY, null, 1,
				_objectDefinitionLocalService,
				Collections.<ObjectField>emptyList());

		ObjectFieldUtil.addCustomObjectField(
			new TextObjectFieldBuilder(
			).userId(
				TestPropsValues.getUserId()
			).labelMap(
				LocalizedMapUtil.getLocalizedMap("Able")
			).name(
				"able"
			).objectDefinitionId(
				objectDefinition.getObjectDefinitionId()
			).required(
				true
			).build());

		// Database table

		Assert.assertFalse(
			_hasColumn(objectDefinition.getExtensionDBTableName(), "able"));
		Assert.assertTrue(
			_hasColumn(objectDefinition.getExtensionDBTableName(), "able_"));
		Assert.assertFalse(_hasTable(objectDefinition.getDBTableName()));
		Assert.assertTrue(
			_hasTable(objectDefinition.getExtensionDBTableName()));

		// Messaging

		Assert.assertNull(
			_messageBus.getDestination(objectDefinition.getDestinationName()));

		// Resources

		Assert.assertEquals(
			0,
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getClassName()));

		try {
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getPortletId());

			Assert.fail();
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
			Assert.assertNotNull(unsupportedOperationException);
		}

		try {
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getResourceName());

			Assert.fail();
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
			Assert.assertNotNull(unsupportedOperationException);
		}

		Assert.assertEquals(
			1,
			_resourcePermissionLocalService.getResourcePermissionsCount(
				objectDefinition.getCompanyId(),
				ObjectDefinition.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectDefinition.getObjectDefinitionId())));

		// Status

		Assert.assertTrue(objectDefinition.isApproved());

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);

		// Publish modifiable system object definition

		objectDefinition =
			ObjectDefinitionTestUtil.addModifiableSystemObjectDefinition(
				TestPropsValues.getUserId(), null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_SITE, null, 1,
				_objectDefinitionLocalService,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(), StringUtil.randomId())));

		objectDefinition =
			_objectDefinitionLocalService.publishSystemObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId());

		Assert.assertTrue(
			StringUtil.startsWith(
				objectDefinition.getDBTableName(),
				ObjectDefinitionConstants.
					EXTERNAL_REFERENCE_CODE_PREFIX_SYSTEM_OBJECT_DEFINITION));
		Assert.assertEquals("/test", objectDefinition.getRESTContextPath());
		Assert.assertTrue(objectDefinition.isApproved());
		Assert.assertTrue(objectDefinition.isEnableCategorization());
		Assert.assertTrue(objectDefinition.isModifiable());
		Assert.assertTrue(objectDefinition.isSystem());
		Assert.assertTrue(_hasTable(objectDefinition.getDBTableName()));
		Assert.assertTrue(
			_hasTable(objectDefinition.getExtensionDBTableName()));

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);

		objectDefinition =
			ObjectDefinitionTestUtil.addUnmodifiableSystemObjectDefinition(
				null, TestPropsValues.getUserId(), "Test", null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY, null, 1,
				_objectDefinitionLocalService,
				Collections.<ObjectField>emptyList());

		// Publish unmodifiable system object definition

		try {
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId());

			Assert.fail();
		}
		catch (ObjectDefinitionStatusException
					objectDefinitionStatusException) {

			Assert.assertNotNull(objectDefinitionStatusException);
		}

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
	}

	@Test
	public void testBindObjectDefinitions() throws Exception {

		// Bind object definitions creating a new hierarchical structure

		ObjectDefinition objectDefinitionA =
			ObjectDefinitionTestUtil.addObjectDefinition(
				"A", _objectDefinitionLocalService);
		ObjectDefinition objectDefinitionAA =
			ObjectDefinitionTestUtil.addObjectDefinition(
				"AA", _objectDefinitionLocalService);

		ObjectRelationship objectRelationshipA_AA =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, objectDefinitionA,
				objectDefinitionAA);

		TreeTestUtil.bind(
			_objectDefinitionLocalService,
			Arrays.asList(
				ObjectRelationshipTestUtil.addObjectRelationship(
					_objectRelationshipLocalService, objectDefinitionAA,
					ObjectDefinitionTestUtil.addObjectDefinition(
						"AAA", _objectDefinitionLocalService)),
				objectRelationshipA_AA));

		TreeTestUtil.assertTree(
			LinkedHashMapBuilder.put(
				"A", new String[] {"AA"}
			).put(
				"AA", new String[] {"AAA"}
			).put(
				"AAA", new String[0]
			).build(),
			_treeFactory.create(objectDefinitionA.getObjectDefinitionId()),
			_objectDefinitionLocalService);

		// Bind one object definition to an existing hierarchical structure

		TreeTestUtil.bind(
			_objectDefinitionLocalService,
			Arrays.asList(
				ObjectRelationshipTestUtil.addObjectRelationship(
					_objectRelationshipLocalService, objectDefinitionAA,
					ObjectDefinitionTestUtil.addObjectDefinition(
						"AAB", _objectDefinitionLocalService)),
				objectRelationshipA_AA));

		TreeTestUtil.assertTree(
			LinkedHashMapBuilder.put(
				"A", new String[] {"AA"}
			).put(
				"AA", new String[] {"AAA", "AAB"}
			).put(
				"AAA", new String[0]
			).put(
				"AAB", new String[0]
			).build(),
			_treeFactory.create(objectDefinitionA.getObjectDefinitionId()),
			_objectDefinitionLocalService);

		_deleteObjectDefinitionHierarchy();
	}

	@Test
	public void testDeleteObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(), StringUtil.randomId())));

		objectDefinition =
			_objectDefinitionLocalService.updateRootObjectDefinitionId(
				objectDefinition.getObjectDefinitionId(),
				objectDefinition.getObjectDefinitionId());

		ObjectDefinition finalObjectDefinition = objectDefinition;

		AssertUtils.assertFailure(
			ObjectDefinitionRootObjectDefinitionIdException.class,
			"Object definitions that belong to a hierarchical structure " +
				"cannot be deleted",
			() -> _objectDefinitionLocalService.deleteObjectDefinition(
				finalObjectDefinition));

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());

		_objectDefinitionLocalService.updateRootObjectDefinitionId(
			objectDefinition.getObjectDefinitionId(), 0);

		_objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinition.getObjectDefinitionId());

		// Database table

		Assert.assertFalse(_hasTable(objectDefinition.getDBTableName()));
		Assert.assertFalse(
			_hasTable(objectDefinition.getExtensionDBTableName()));

		// Messaging

		Assert.assertNull(
			_messageBus.getDestination(objectDefinition.getDestinationName()));

		// Resources

		Assert.assertEquals(
			0,
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getClassName()));
		Assert.assertEquals(
			0,
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getPortletId()));
		Assert.assertEquals(
			0,
			_resourceActionLocalService.getResourceActionsCount(
				objectDefinition.getResourceName()));
		Assert.assertEquals(
			0,
			_resourcePermissionLocalService.getResourcePermissionsCount(
				objectDefinition.getCompanyId(),
				ObjectDefinition.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectDefinition.getObjectDefinitionId())));
	}

	@Test
	public void testEnableAccountEntryRestrictedForNondefaultStorageType()
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false, false,
				LocalizedMapUtil.getLocalizedMap("Able"), "Able", null, null,
				LocalizedMapUtil.getLocalizedMap("Ables"), true,
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE,
				Collections.emptyList());

		objectDefinition =
			_objectDefinitionLocalService.
				enableAccountEntryRestrictedForNondefaultStorageType(
					ObjectFieldUtil.addCustomObjectField(
						new TextObjectFieldBuilder(
						).userId(
							TestPropsValues.getUserId()
						).labelMap(
							LocalizedMapUtil.getLocalizedMap(
								RandomTestUtil.randomString())
						).name(
							StringUtil.randomId()
						).objectDefinitionId(
							objectDefinition.getObjectDefinitionId()
						).required(
							true
						).build()));

		Assert.assertTrue(
			objectDefinition.getAccountEntryRestrictedObjectFieldId() > 0);
		Assert.assertEquals(
			objectDefinition.getStorageType(),
			ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE);
		Assert.assertTrue(objectDefinition.isAccountEntryRestricted());
		Assert.assertFalse(objectDefinition.isSystem());

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);

		AssertUtils.assertFailure(
			ObjectDefinitionAccountEntryRestrictedException.class,
			"Custom object definitions can only be restricted by an integer, " +
				"long integer, or text field",
			() ->
				_objectDefinitionLocalService.
					enableAccountEntryRestrictedForNondefaultStorageType(
						ObjectFieldUtil.addCustomObjectField(
							new DateObjectFieldBuilder(
							).userId(
								TestPropsValues.getUserId()
							).labelMap(
								LocalizedMapUtil.getLocalizedMap(
									RandomTestUtil.randomString())
							).name(
								StringUtil.randomId()
							).objectDefinitionId(
								_addCustomObjectDefinition(
									"Test" + RandomTestUtil.randomString()
								).getObjectDefinitionId()
							).required(
								true
							).build())));
		AssertUtils.assertFailure(
			UnsupportedOperationException.class, null,
			() ->
				_objectDefinitionLocalService.
					enableAccountEntryRestrictedForNondefaultStorageType(
						ObjectFieldUtil.addCustomObjectField(
							new TextObjectFieldBuilder(
							).userId(
								TestPropsValues.getUserId()
							).labelMap(
								LocalizedMapUtil.getLocalizedMap(
									RandomTestUtil.randomString())
							).name(
								StringUtil.randomId()
							).objectDefinitionId(
								_addCustomObjectDefinition(
									"Test" + RandomTestUtil.randomString()
								).getObjectDefinitionId()
							).required(
								true
							).build())));

		objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false, false,
				LocalizedMapUtil.getLocalizedMap("Able"), "Able", null, null,
				LocalizedMapUtil.getLocalizedMap("Ables"), true,
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE,
				Collections.emptyList());

		objectDefinition =
			_objectDefinitionLocalService.
				enableAccountEntryRestrictedForNondefaultStorageType(
					ObjectFieldUtil.addCustomObjectField(
						new TextObjectFieldBuilder(
						).userId(
							TestPropsValues.getUserId()
						).labelMap(
							LocalizedMapUtil.getLocalizedMap(
								RandomTestUtil.randomString())
						).name(
							StringUtil.randomId()
						).objectDefinitionId(
							objectDefinition.getObjectDefinitionId()
						).required(
							true
						).build()));

		Assert.assertTrue(
			objectDefinition.getAccountEntryRestrictedObjectFieldId() > 0);
		Assert.assertEquals(
			objectDefinition.getStorageType(),
			ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE);
		Assert.assertTrue(objectDefinition.isAccountEntryRestricted());
		Assert.assertFalse(objectDefinition.isSystem());

		ObjectField objectField = ObjectFieldUtil.addCustomObjectField(
			new TextObjectFieldBuilder(
			).userId(
				TestPropsValues.getUserId()
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				StringUtil.randomId()
			).objectDefinitionId(
				objectDefinition.getObjectDefinitionId()
			).required(
				true
			).build());

		objectDefinition =
			_objectDefinitionLocalService.
				enableAccountEntryRestrictedForNondefaultStorageType(
					objectField);

		Assert.assertEquals(
			objectDefinition.getAccountEntryRestrictedObjectFieldId(),
			objectField.getObjectFieldId());
		Assert.assertTrue(objectDefinition.isAccountEntryRestricted());

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
	}

	@Test
	public void testEnableAccountRestricted() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addObjectDefinition(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(), 0,
				true, false);

		objectDefinition =
			_objectDefinitionLocalService.enableAccountEntryRestricted(
				_objectRelationshipLocalService.addObjectRelationship(
					TestPropsValues.getUserId(),
					_objectDefinitionLocalService.fetchSystemObjectDefinition(
						"AccountEntry"
					).getObjectDefinitionId(),
					objectDefinition.getObjectDefinitionId(), 0,
					ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					StringUtil.randomId(),
					ObjectRelationshipConstants.TYPE_ONE_TO_MANY));

		Assert.assertTrue(
			objectDefinition.getAccountEntryRestrictedObjectFieldId() > 0);
		Assert.assertTrue(objectDefinition.isAccountEntryRestricted());
		Assert.assertFalse(objectDefinition.isSystem());

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);

		AssertUtils.assertFailure(
			ObjectDefinitionAccountEntryRestrictedException.class,
			"Custom object definitions can only be restricted by account entry",
			() -> _objectDefinitionLocalService.enableAccountEntryRestricted(
				_objectRelationshipLocalService.addObjectRelationship(
					TestPropsValues.getUserId(),
					_addCustomObjectDefinition(
						"Test" + RandomTestUtil.randomString()
					).getObjectDefinitionId(),
					_addCustomObjectDefinition(
						"Test" + RandomTestUtil.randomString()
					).getObjectDefinitionId(),
					0, ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					StringUtil.randomId(),
					ObjectRelationshipConstants.TYPE_ONE_TO_MANY)));
	}

	@Test
	public void testSystemObjectFields() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.emptyList());

		_testSystemObjectFields(objectDefinition);

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);

		objectDefinition =
			ObjectDefinitionTestUtil.addUnmodifiableSystemObjectDefinition(
				null, TestPropsValues.getUserId(), "Test", null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY, null, 1,
				_objectDefinitionLocalService,
				Collections.<ObjectField>emptyList());

		_testSystemObjectFields(objectDefinition);

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
	}

	@Test
	public void testUnbindObjectDefinition() throws Exception {

		// Unbind object definition internal node

		TreeTestUtil.assertTree(
			LinkedHashMapBuilder.put(
				"A", new String[] {"AA", "AB"}
			).put(
				"AA", new String[] {"AAA", "AAB"}
			).put(
				"AB", new String[0]
			).put(
				"AAA", new String[0]
			).put(
				"AAB", new String[0]
			).build(),
			TreeTestUtil.createTree(
				_objectDefinitionLocalService, _objectRelationshipLocalService,
				_treeFactory),
			_objectDefinitionLocalService);

		TreeTestUtil.unbind(_objectDefinitionLocalService, "C_AA");

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				TestPropsValues.getCompanyId(), "C_A");

		TreeTestUtil.assertTree(
			LinkedHashMapBuilder.put(
				"A", new String[] {"AB"}
			).put(
				"AB", new String[0]
			).build(),
			_treeFactory.create(objectDefinition.getRootObjectDefinitionId()),
			_objectDefinitionLocalService);

		// Unbind object definition leaf node

		TreeTestUtil.unbind(_objectDefinitionLocalService, "C_AB");

		TreeTestUtil.assertTree(
			LinkedHashMapBuilder.put(
				"A", new String[0]
			).build(),
			_treeFactory.create(objectDefinition.getRootObjectDefinitionId()),
			_objectDefinitionLocalService);

		// Unbind object definition root node

		TreeTestUtil.unbind(_objectDefinitionLocalService, "C_A");

		objectDefinition = _objectDefinitionLocalService.fetchObjectDefinition(
			TestPropsValues.getCompanyId(), "C_A");

		Assert.assertEquals(0, objectDefinition.getRootObjectDefinitionId());

		_deleteObjectDefinitionHierarchy();
	}

	@Test
	public void testUpdateCustomObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false, false,
				LocalizedMapUtil.getLocalizedMap("Able"), "Able", null, null,
				LocalizedMapUtil.getLocalizedMap("Ables"), true,
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE,
				Collections.emptyList());

		Assert.assertFalse(objectDefinition.isActive());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Able"),
			objectDefinition.getLabelMap());
		Assert.assertEquals("C_Able", objectDefinition.getName());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Ables"),
			objectDefinition.getPluralLabelMap());
		Assert.assertEquals(
			ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE,
			objectDefinition.getStorageType());

		long objectDefinitionId = objectDefinition.getObjectDefinitionId();
		String scope = objectDefinition.getScope();

		AssertUtils.assertFailure(
			ObjectDefinitionEnableObjectEntryHistoryException.class,
			"Enable object entry history is only allowed for object " +
				"definitions with the default storage type",
			() -> _updateObjectDefinition(
				null, objectDefinitionId, 0, 0, true,
				LocalizedMapUtil.getLocalizedMap("Able"), "Able",
				LocalizedMapUtil.getLocalizedMap("Ables"), scope));
		AssertUtils.assertFailure(
			ObjectDefinitionExternalReferenceCodeException.
				MustNotStartWithPrefix.class,
			"The prefix L_ is reserved for Liferay",
			() -> _updateObjectDefinition(
				"L_INVALID_ERC_TEST", objectDefinitionId, 0, 0, false,
				LocalizedMapUtil.getLocalizedMap("Able"), "Able",
				LocalizedMapUtil.getLocalizedMap("Ables"), scope));

		objectDefinition.setStorageType(
			ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT);

		objectDefinition = _objectDefinitionLocalService.updateObjectDefinition(
			objectDefinition);

		Assert.assertEquals(
			ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
			objectDefinition.getStorageType());

		AssertUtils.assertFailure(
			NoSuchObjectFieldException.class, null,
			() -> _updateObjectDefinition(
				null, objectDefinitionId, RandomTestUtil.randomLong(),
				RandomTestUtil.randomLong(), false,
				LocalizedMapUtil.getLocalizedMap("Able"), "Able",
				LocalizedMapUtil.getLocalizedMap("Ables"), scope));

		ObjectField objectField = ObjectFieldUtil.addCustomObjectField(
			new TextObjectFieldBuilder(
			).userId(
				TestPropsValues.getUserId()
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				StringUtil.randomId()
			).objectDefinitionId(
				objectDefinition.getObjectDefinitionId()
			).required(
				true
			).build());

		objectDefinition =
			_objectDefinitionLocalService.updateCustomObjectDefinition(
				null, objectDefinition.getObjectDefinitionId(), 0,
				objectField.getObjectFieldId(), 0,
				objectField.getObjectFieldId(), false,
				objectDefinition.isActive(), true, false, false, false, false,
				LocalizedMapUtil.getLocalizedMap("Able"), "Able", null, null,
				false, LocalizedMapUtil.getLocalizedMap("Ables"),
				objectDefinition.getScope());

		Assert.assertEquals(
			objectField.getObjectFieldId(),
			objectDefinition.getDescriptionObjectFieldId());
		Assert.assertEquals(
			objectField.getObjectFieldId(),
			objectDefinition.getTitleObjectFieldId());

		String externalReferenceCode = RandomTestUtil.randomString();

		objectDefinition =
			_objectDefinitionLocalService.updateCustomObjectDefinition(
				externalReferenceCode, objectDefinition.getObjectDefinitionId(),
				0, 0, 0, 0, false, objectDefinition.isActive(), true, false,
				false, false, false, LocalizedMapUtil.getLocalizedMap("Able"),
				"Able", null, null, false,
				LocalizedMapUtil.getLocalizedMap("Ables"),
				objectDefinition.getScope());

		Assert.assertEquals(
			externalReferenceCode, objectDefinition.getExternalReferenceCode());
		Assert.assertEquals(0, objectDefinition.getDescriptionObjectFieldId());
		Assert.assertEquals(0, objectDefinition.getTitleObjectFieldId());
		Assert.assertFalse(objectDefinition.isActive());
		Assert.assertFalse(objectDefinition.isEnableObjectEntryHistory());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Able"),
			objectDefinition.getLabelMap());
		Assert.assertEquals("C_Able", objectDefinition.getName());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Ables"),
			objectDefinition.getPluralLabelMap());

		objectDefinition =
			_objectDefinitionLocalService.updateCustomObjectDefinition(
				null, objectDefinition.getObjectDefinitionId(), 0, 0, 0, 0,
				false, objectDefinition.isActive(), true, false, false, false,
				true, LocalizedMapUtil.getLocalizedMap("Baker"), "Baker", null,
				null, false, LocalizedMapUtil.getLocalizedMap("Bakers"),
				objectDefinition.getScope());

		Assert.assertFalse(objectDefinition.isActive());
		Assert.assertTrue(objectDefinition.isEnableObjectEntryHistory());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Baker"),
			objectDefinition.getLabelMap());
		Assert.assertEquals("C_Baker", objectDefinition.getName());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Bakers"),
			objectDefinition.getPluralLabelMap());

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());

		objectDefinition =
			_objectDefinitionLocalService.updateCustomObjectDefinition(
				null, objectDefinition.getObjectDefinitionId(), 0, 0, 0, 0,
				false, true, true, false, false, false, true,
				LocalizedMapUtil.getLocalizedMap("Charlie"), "Charlie", null,
				null, false, LocalizedMapUtil.getLocalizedMap("Charlies"),
				objectDefinition.getScope());

		Assert.assertTrue(objectDefinition.isActive());
		Assert.assertTrue(objectDefinition.isEnableObjectEntryHistory());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Charlie"),
			objectDefinition.getLabelMap());
		Assert.assertEquals("C_Baker", objectDefinition.getName());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Charlies"),
			objectDefinition.getPluralLabelMap());

		_testUpdateCustomObjectDefinitionThrowsObjectFieldRelationshipTypeException(
			objectDefinition);

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
	}

	@Test
	public void testUpdateExternalReferenceCode() throws Exception {
		ObjectDefinition customObjectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false, false,
				LocalizedMapUtil.getLocalizedMap("Able"), "Able", null, null,
				LocalizedMapUtil.getLocalizedMap("Ables"), false,
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.emptyList());

		_objectDefinitionLocalService.updateExternalReferenceCode(
			customObjectDefinition.getObjectDefinitionId(), "TEST_ERC");

		_assertFailure(
			ObjectDefinitionExternalReferenceCodeException.
				MustNotStartWithPrefix.class,
			"The prefix L_ is reserved for Liferay", customObjectDefinition,
			objectDefinitionId ->
				_objectDefinitionLocalService.updateExternalReferenceCode(
					objectDefinitionId, "L_INVALID_ERC_TEST"));

		ObjectDefinition unmodifiableSystemObjectDefinition =
			_addUnmodifiableSystemObjectDefinition("Unmodifiable");

		_objectDefinitionLocalService.updateExternalReferenceCode(
			unmodifiableSystemObjectDefinition.getObjectDefinitionId(),
			"L_TEST_ERC");

		_objectDefinitionLocalService.deleteObjectDefinition(
			customObjectDefinition);
		_objectDefinitionLocalService.deleteObjectDefinition(
			unmodifiableSystemObjectDefinition);
	}

	@Test
	public void testUpdateRootObjectDefinitionId() throws Exception {
		ObjectDefinition objectDefinition1 =
			ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService);

		ObjectDefinition objectDefinition2 =
			ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService);

		AssertUtils.assertFailure(
			ObjectDefinitionRootObjectDefinitionIdException.class,
			"Object definition " + objectDefinition2.getObjectDefinitionId() +
				" is not a root object definition",
			() -> _objectDefinitionLocalService.updateRootObjectDefinitionId(
				objectDefinition1.getObjectDefinitionId(),
				objectDefinition2.getObjectDefinitionId()));

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition1);
		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition2);
	}

	@Test
	public void testUpdateSystemObjectDefinition() throws Exception {

		// Before update, assert validations criterias

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addModifiableSystemObjectDefinition(
				TestPropsValues.getUserId(), null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_SITE, null, 1,
				_objectDefinitionLocalService,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(), StringUtil.randomId())));

		// Modifiable system object definition must be published to be actived

		_assertFailure(
			ObjectDefinitionActiveException.class,
			"Object definitions must be published before being activated",
			objectDefinition,
			objectDefinitionId ->
				_objectDefinitionLocalService.updateCustomObjectDefinition(
					null, objectDefinitionId, 0, 0, 0, 0, false, true, false,
					true, false, false, false,
					LocalizedMapUtil.getLocalizedMap("Charlie"), "Charlie",
					null, null, false,
					LocalizedMapUtil.getLocalizedMap("Charlies"),
					ObjectDefinitionConstants.SCOPE_SITE));

		// Label is null

		_assertFailure(
			ObjectDefinitionLabelException.class,
			"Label is null for locale " + LocaleUtil.US.getDisplayName(),
			objectDefinition,
			objectDefinitionId ->
				_objectDefinitionLocalService.updateCustomObjectDefinition(
					null, objectDefinitionId, 0, 0, 0, 0, false, false, false,
					true, false, false, false, null, "Charlie", null, null,
					false, LocalizedMapUtil.getLocalizedMap("Charlie"),
					ObjectDefinitionConstants.SCOPE_SITE));

		// Plural label is null

		_assertFailure(
			ObjectDefinitionPluralLabelException.class,
			"Plural label is null for locale " + LocaleUtil.US.getDisplayName(),
			objectDefinition,
			objectDefinitionId ->
				_objectDefinitionLocalService.updateCustomObjectDefinition(
					null, objectDefinitionId, 0, 0, 0, 0, false, false, false,
					true, false, false, false,
					LocalizedMapUtil.getLocalizedMap("Charlie"), "Charlie",
					null, null, false, null,
					ObjectDefinitionConstants.SCOPE_SITE));

		// After Update a modifiable system object definition check its
		// properties

		objectDefinition =
			_objectDefinitionLocalService.publishSystemObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId());

		objectDefinition =
			_objectDefinitionLocalService.updateCustomObjectDefinition(
				null, objectDefinition.getObjectDefinitionId(), 0, 0, 0, 0,
				false, true, false, true, false, false, false,
				LocalizedMapUtil.getLocalizedMap("Charlie"), "Charlie", null,
				null, false, LocalizedMapUtil.getLocalizedMap("Charlies"),
				objectDefinition.getScope());

		Assert.assertFalse(objectDefinition.isEnableCategorization());
		Assert.assertTrue(objectDefinition.isEnableComments());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Charlie"),
			objectDefinition.getLabelMap());
		Assert.assertEquals("Test", objectDefinition.getName());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Charlies"),
			objectDefinition.getPluralLabelMap());

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);

		// After Update an unmodifiable system object definition check its
		// properties

		objectDefinition =
			ObjectDefinitionTestUtil.addUnmodifiableSystemObjectDefinition(
				null, TestPropsValues.getUserId(), "Test", null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY, null, 1,
				_objectDefinitionLocalService,
				Collections.<ObjectField>emptyList());

		ObjectField objectField = ObjectFieldUtil.addCustomObjectField(
			new TextObjectFieldBuilder(
			).userId(
				TestPropsValues.getUserId()
			).labelMap(
				LocalizedMapUtil.getLocalizedMap("Able")
			).name(
				"able"
			).objectDefinitionId(
				objectDefinition.getObjectDefinitionId()
			).required(
				true
			).build());

		String externalReferenceCode = RandomTestUtil.randomString();

		objectDefinition =
			_objectDefinitionLocalService.updateSystemObjectDefinition(
				externalReferenceCode, objectDefinition.getObjectDefinitionId(),
				0, objectField.getObjectFieldId());

		Assert.assertEquals(
			objectField.getObjectFieldId(),
			objectDefinition.getTitleObjectFieldId());

		Assert.assertEquals(
			externalReferenceCode, objectDefinition.getExternalReferenceCode());

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
	}

	@Test
	public void testUpdateTitleObjectFieldId() throws Exception {
		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addObjectDefinition(
				_objectDefinitionLocalService);

		try {
			objectDefinition =
				_objectDefinitionLocalService.updateTitleObjectFieldId(
					objectDefinition.getObjectDefinitionId(),
					RandomTestUtil.randomLong());

			Assert.fail();
		}
		catch (NoSuchObjectFieldException noSuchObjectFieldException) {
			Assert.assertNotNull(noSuchObjectFieldException);
		}

		ObjectField objectField = ObjectFieldUtil.addCustomObjectField(
			new TextObjectFieldBuilder(
			).userId(
				TestPropsValues.getUserId()
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				StringUtil.randomId()
			).objectDefinitionId(
				objectDefinition.getObjectDefinitionId()
			).required(
				true
			).build());

		objectDefinition =
			_objectDefinitionLocalService.updateTitleObjectFieldId(
				objectDefinition.getObjectDefinitionId(),
				objectField.getObjectFieldId());

		Assert.assertEquals(
			objectField.getObjectFieldId(),
			objectDefinition.getTitleObjectFieldId());

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
	}

	private ObjectDefinition _addCustomObjectDefinition(String name)
		throws Exception {

		return _addCustomObjectDefinition(name, name, name);
	}

	private ObjectDefinition _addCustomObjectDefinition(
			String label, String name, String pluralLabel)
		throws Exception {

		return _objectDefinitionLocalService.addCustomObjectDefinition(
			TestPropsValues.getUserId(), 0, false, false, false,
			LocalizedMapUtil.getLocalizedMap(label), name, null, null,
			LocalizedMapUtil.getLocalizedMap(pluralLabel), true,
			ObjectDefinitionConstants.SCOPE_COMPANY,
			ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING,
					RandomTestUtil.randomString(), StringUtil.randomId())));
	}

	private ObjectDefinition _addUnmodifiableSystemObjectDefinition(String name)
		throws Exception {

		return _addUnmodifiableSystemObjectDefinition(
			RandomTestUtil.randomString(), name, RandomTestUtil.randomString());
	}

	private ObjectDefinition _addUnmodifiableSystemObjectDefinition(
			String label, String name, String pluralLabel)
		throws Exception {

		return ObjectDefinitionTestUtil.addUnmodifiableSystemObjectDefinition(
			null, TestPropsValues.getUserId(), name, null,
			LocalizedMapUtil.getLocalizedMap(label), name, null, null,
			LocalizedMapUtil.getLocalizedMap(pluralLabel),
			ObjectDefinitionConstants.SCOPE_COMPANY, null, 1,
			_objectDefinitionLocalService,
			Arrays.asList(
				new TextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					StringUtil.randomId()
				).build()));
	}

	private void _assertFailure(
		Class<?> clazz, String message, ObjectDefinition objectDefinition,
		UnsafeConsumer<Long, Exception> unsafeConsumer) {

		try {
			unsafeConsumer.accept(objectDefinition.getObjectDefinitionId());

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertTrue(clazz.isInstance(exception));
			Assert.assertEquals(exception.getMessage(), message);
		}
	}

	private void _assertNodeObjectDefinitions(
			Tree tree,
			UnsafeConsumer<ObjectDefinition, Exception> unsafeConsumer)
		throws Exception {

		Iterator<Node> iterator = tree.iterator();

		while (iterator.hasNext()) {
			Node node = iterator.next();

			unsafeConsumer.accept(
				_objectDefinitionLocalService.getObjectDefinition(
					node.getObjectDefinitionId()));
		}
	}

	private void _assertObjectField(
			ObjectDefinition objectDefinition, String dbColumnName,
			String dbType, String name, boolean required)
		throws Exception {

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectDefinition.getObjectDefinitionId(), name);

		Assert.assertEquals(dbColumnName, objectField.getDBColumnName());
		Assert.assertEquals(dbType, objectField.getDBType());
		Assert.assertFalse(objectField.isIndexed());
		Assert.assertFalse(objectField.isIndexedAsKeyword());
		Assert.assertEquals("", objectField.getIndexedLanguageId());
		Assert.assertEquals(required, objectField.isRequired());
	}

	private void _assertSystemObjectFields(
		ObjectField expectedObjectField, ObjectField objectField) {

		Assert.assertEquals(
			expectedObjectField.getDBColumnName(),
			objectField.getDBColumnName());
		Assert.assertEquals(
			expectedObjectField.getDBTableName(), objectField.getDBTableName());
		Assert.assertEquals(
			expectedObjectField.getDBType(), objectField.getDBType());
		Assert.assertEquals(
			expectedObjectField.isIndexed(), objectField.isIndexed());
		Assert.assertEquals(
			expectedObjectField.isIndexedAsKeyword(),
			objectField.isIndexedAsKeyword());
		Assert.assertEquals(
			expectedObjectField.getIndexedLanguageId(),
			objectField.getIndexedLanguageId());
		Assert.assertEquals(
			expectedObjectField.getLabelMap(), objectField.getLabelMap());
		Assert.assertEquals(
			expectedObjectField.getName(), objectField.getName());
		Assert.assertEquals(
			expectedObjectField.isRequired(), objectField.isRequired());
		Assert.assertEquals(
			expectedObjectField.isState(), objectField.isState());
	}

	private ObjectAction _createObjectAction(String objectActionName) {
		ObjectAction objectAction =
			ObjectActionLocalServiceUtil.createObjectAction(0);

		objectAction.setExternalReferenceCode(objectActionName);
		objectAction.setActive(true);
		objectAction.setConditionExpression(StringPool.BLANK);
		objectAction.setDescription(RandomTestUtil.randomString());
		objectAction.setErrorMessageMap(
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()));
		objectAction.setLabelMap(
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()));
		objectAction.setName(objectActionName);
		objectAction.setObjectActionExecutorKey(
			ObjectActionExecutorConstants.KEY_UPDATE_OBJECT_ENTRY);
		objectAction.setObjectActionTriggerKey(
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD);
		objectAction.setParameters(
			UnicodePropertiesBuilder.put(
				"objectDefinitionExternalReferenceCode",
				"L_USER_NOTIFICATION_EVENT"
			).put(
				"predefinedValues",
				JSONUtil.putAll(
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", "deliveryType"
					).put(
						"value", UserNotificationDeliveryConstants.TYPE_SMS
					)
				).toString()
			).buildString());

		return objectAction;
	}

	private void _deleteObjectDefinitionHierarchy() throws Exception {
		for (String objectDefinitionName :
				new String[] {"C_A", "C_AA", "C_AAA", "C_AAB", "C_AB"}) {

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					TestPropsValues.getCompanyId(), objectDefinitionName);

			if (objectDefinition == null) {
				continue;
			}

			if (objectDefinition.getRootObjectDefinitionId() != 0) {
				TreeTestUtil.unbind(
					_objectDefinitionLocalService, objectDefinitionName);
			}

			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition.getObjectDefinitionId());
		}
	}

	private boolean _hasColumn(String tableName, String columnName)
		throws Exception {

		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			return dbInspector.hasColumn(tableName, columnName);
		}
	}

	private boolean _hasTable(String tableName) throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			return dbInspector.hasTable(tableName);
		}
	}

	private void _testAddObjectDefinition(boolean modifiable, boolean system)
		throws Exception {

		String externalReferenceCode = RandomTestUtil.randomString();
		User user = TestPropsValues.getUser();

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addObjectDefinition(
				externalReferenceCode, user.getUserId(), 0, modifiable, system);

		Assert.assertEquals(
			externalReferenceCode, objectDefinition.getExternalReferenceCode());
		Assert.assertEquals(
			TestPropsValues.getCompanyId(), objectDefinition.getCompanyId());
		Assert.assertEquals(user.getUserId(), objectDefinition.getUserId());
		Assert.assertEquals(user.getFullName(), objectDefinition.getUserName());
		Assert.assertFalse(objectDefinition.isAccountEntryRestricted());
		Assert.assertFalse(objectDefinition.isActive());
		Assert.assertEquals(
			StringPool.BLANK, objectDefinition.getDBTableName());
		Assert.assertEquals(externalReferenceCode, objectDefinition.getLabel());
		Assert.assertFalse(objectDefinition.isEnableCategorization());
		Assert.assertFalse(objectDefinition.isEnableComments());
		Assert.assertFalse(objectDefinition.isEnableLocalization());
		Assert.assertFalse(objectDefinition.isEnableObjectEntryHistory());
		Assert.assertEquals(modifiable, objectDefinition.isModifiable());
		Assert.assertEquals(externalReferenceCode, objectDefinition.getName());
		Assert.assertEquals(
			externalReferenceCode, objectDefinition.getPluralLabel());
		Assert.assertEquals(
			ObjectDefinitionConstants.SCOPE_COMPANY,
			objectDefinition.getScope());
		Assert.assertEquals(
			ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
			objectDefinition.getStorageType());
		Assert.assertEquals(system, objectDefinition.isSystem());
		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, objectDefinition.getStatus());

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
	}

	private void _testSystemObjectFields(ObjectDefinition objectDefinition)
		throws Exception {

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId());

		Assert.assertNotNull(objectFields);

		boolean system = objectDefinition.isSystem();

		Assert.assertEquals(objectFields.toString(), 6, objectFields.size());

		ListIterator<ObjectField> iterator = objectFields.listIterator();

		Assert.assertTrue(iterator.hasNext());

		String dbColumnName = null;
		String dbTableName = null;

		ObjectEntryTable objectEntryTable = ObjectEntryTable.INSTANCE;

		if (system) {
			dbColumnName = TextFormatter.format(
				objectDefinition.getShortName() + "Id", TextFormatter.I);
			dbTableName = objectDefinition.getDBTableName();
		}
		else {
			dbColumnName = objectEntryTable.objectEntryId.getName();
			dbTableName = objectEntryTable.getTableName();
		}

		_assertSystemObjectFields(
			new DateObjectFieldBuilder(
			).dbColumnName(
				objectEntryTable.createDate.getName()
			).dbTableName(
				dbTableName
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(
					LanguageUtil.get(LocaleUtil.getDefault(), "create-date"))
			).name(
				"createDate"
			).build(),
			iterator.next());

		Assert.assertTrue(iterator.hasNext());

		_assertSystemObjectFields(
			new TextObjectFieldBuilder(
			).dbColumnName(
				objectEntryTable.userName.getName()
			).dbTableName(
				dbTableName
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(
					LanguageUtil.get(LocaleUtil.getDefault(), "author"))
			).name(
				"creator"
			).build(),
			iterator.next());

		Assert.assertTrue(iterator.hasNext());

		_assertSystemObjectFields(
			new TextObjectFieldBuilder(
			).dbColumnName(
				objectEntryTable.externalReferenceCode.getName()
			).dbTableName(
				dbTableName
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(
					LanguageUtil.get(
						LocaleUtil.getDefault(), "external-reference-code"))
			).name(
				"externalReferenceCode"
			).build(),
			iterator.next());

		Assert.assertTrue(iterator.hasNext());

		_assertSystemObjectFields(
			new LongIntegerObjectFieldBuilder(
			).dbColumnName(
				dbColumnName
			).dbTableName(
				dbTableName
			).indexed(
				Boolean.TRUE
			).indexedAsKeyword(
				Boolean.TRUE
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(
					LanguageUtil.get(LocaleUtil.getDefault(), "id"))
			).name(
				"id"
			).build(),
			iterator.next());

		Assert.assertTrue(iterator.hasNext());

		_assertSystemObjectFields(
			new DateObjectFieldBuilder(
			).dbColumnName(
				objectEntryTable.modifiedDate.getName()
			).dbTableName(
				dbTableName
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(
					LanguageUtil.get(LocaleUtil.getDefault(), "modified-date"))
			).name(
				"modifiedDate"
			).build(),
			iterator.next());

		Assert.assertTrue(iterator.hasNext());

		_assertSystemObjectFields(
			new TextObjectFieldBuilder(
			).dbColumnName(
				objectEntryTable.status.getName()
			).dbTableName(
				dbTableName
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(
					LanguageUtil.get(LocaleUtil.getDefault(), "status"))
			).name(
				"status"
			).build(),
			iterator.next());

		Assert.assertFalse(iterator.hasNext());
	}

	private void
			_testUpdateCustomObjectDefinitionThrowsObjectFieldRelationshipTypeException(
				ObjectDefinition objectDefinition1)
		throws Exception {

		ObjectDefinition objectDefinition2 =
			ObjectDefinitionTestUtil.addObjectDefinition(
				false, _objectDefinitionLocalService,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(), StringUtil.randomId())));

		objectDefinition2 =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition2.getObjectDefinitionId());

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				objectDefinition1.getObjectDefinitionId(),
				objectDefinition2.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		try {
			objectDefinition2 =
				_objectDefinitionLocalService.updateCustomObjectDefinition(
					null, objectDefinition2.getObjectDefinitionId(), 0,
					objectRelationship.getObjectFieldId2(), 0, 0, false,
					objectDefinition2.isActive(), true, false, false, false,
					false, LocalizedMapUtil.getLocalizedMap("Able"), "Able",
					null, null, false,
					LocalizedMapUtil.getLocalizedMap("Ables"),
					objectDefinition2.getScope());

			Assert.fail();
		}
		catch (ObjectFieldRelationshipTypeException
					objectFieldRelationshipTypeException) {

			Assert.assertEquals(
				"Description and title object fields cannot have a " +
					"relationship type",
				objectFieldRelationshipTypeException.getMessage());
		}
		finally {

			// TODO Deleting an object definition should delete any of its
			// object relationships

			//_objectRelationshipLocalService.deleteObjectRelationship(
			//	objectRelationship);

			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition2);
		}
	}

	private ObjectDefinition _updateObjectDefinition(
			String externalReferenceCode, long objectDefinitionId,
			long descriptionObjectFieldId, long titleObjectFieldId,
			boolean enableObjectEntryHistory, Map<Locale, String> labelMap,
			String name, Map<Locale, String> pluralLabelMap, String scope)
		throws PortalException {

		return _objectDefinitionLocalService.updateCustomObjectDefinition(
			externalReferenceCode, objectDefinitionId, 0,
			descriptionObjectFieldId, 0, titleObjectFieldId, false, false,
			false, false, false, false, enableObjectEntryHistory, labelMap,
			name, null, null, false, pluralLabelMap, scope);
	}

	@Inject
	private MessageBus _messageBus;

	@Inject
	private ObjectActionLocalService _objectActionLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Inject
	private ResourceActionLocalService _resourceActionLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private TreeFactory _treeFactory;

}