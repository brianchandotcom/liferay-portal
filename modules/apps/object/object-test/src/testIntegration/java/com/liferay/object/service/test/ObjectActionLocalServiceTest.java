/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommerceOrderPaymentConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.object.action.engine.ObjectActionEngine;
import com.liferay.object.action.executor.ObjectActionExecutorRegistry;
import com.liferay.object.action.trigger.ObjectActionTriggerRegistry;
import com.liferay.object.action.util.ObjectActionThreadLocal;
import com.liferay.object.constants.ObjectActionConstants;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.exception.ObjectActionErrorMessageException;
import com.liferay.object.exception.ObjectActionExecutorKeyException;
import com.liferay.object.exception.ObjectActionLabelException;
import com.liferay.object.exception.ObjectActionNameException;
import com.liferay.object.exception.ObjectActionParametersException;
import com.liferay.object.exception.ObjectActionSystemException;
import com.liferay.object.exception.ObjectActionTriggerKeyException;
import com.liferay.object.field.builder.AutoIncrementObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.field.setting.builder.ObjectFieldSettingBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.resource.v1_0.ObjectEntryResource;
import com.liferay.object.scripting.executor.ObjectScriptingExecutor;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.test.util.TreeTestUtil;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.script.management.test.rule.ScriptManagementConfigurationTestRule;
import com.liferay.portal.security.script.management.test.util.ScriptManagementConfigurationTestUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Closeable;
import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

import org.hamcrest.CoreMatchers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Brian Wing Shun Chan
 */
@FeatureFlags({"LPS-173537", "LPS-187142"})
@RunWith(Arquillian.class)
public class ObjectActionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			ScriptManagementConfigurationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_objectDefinition = ObjectDefinitionTestUtil.addCustomObjectDefinition(
			false, _objectDefinitionLocalService,
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_DATE_TIME,
					ObjectFieldConstants.DB_TYPE_DATE_TIME, true, true, null,
					"Time", "time",
					Collections.singletonList(
						new ObjectFieldSettingBuilder(
						).name(
							"timeStorage"
						).value(
							"useInputAsEntered"
						).build()),
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
					"First Name", "firstName", true),
				ObjectFieldUtil.createObjectField(
					0, ObjectFieldConstants.BUSINESS_TYPE_TEXT, null,
					ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
					"Last Name", "lastName", false, true)));
		_originalHttp = (Http)_getAndSetFieldValue(
			Http.class, "_http", ObjectActionExecutorConstants.KEY_WEBHOOK);
		_originalObjectScriptingExecutor =
			(ObjectScriptingExecutor)_getAndSetFieldValue(
				ObjectScriptingExecutor.class, "_objectScriptingExecutor",
				ObjectActionExecutorConstants.KEY_GROOVY);
		_user = UserTestUtil.addUser();
	}

	@After
	public void tearDown() throws PortalException {
		ReflectionTestUtil.setFieldValue(
			_objectActionExecutorRegistry.getObjectActionExecutor(
				0, ObjectActionExecutorConstants.KEY_WEBHOOK),
			"_http", _originalHttp);
		ReflectionTestUtil.setFieldValue(
			_objectActionExecutorRegistry.getObjectActionExecutor(
				0, ObjectActionExecutorConstants.KEY_GROOVY),
			"_objectScriptingExecutor", _originalObjectScriptingExecutor);

		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition.getObjectDefinitionId());
	}

	@Test
	public void testAddObjectAction() throws Exception {

		// Add object actions

		AssertUtils.assertFailure(
			ObjectActionErrorMessageException.class,
			"Error message is null for locale " +
				LocaleUtil.US.getDisplayName(),
			() -> _addObjectAction(
				StringPool.BLANK, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				ObjectActionTriggerConstants.KEY_STANDALONE, false));

		try (Closeable closeable =
				ScriptManagementConfigurationTestUtil.saveWithCloseable(
					false)) {

			AssertUtils.assertFailure(
				ObjectActionExecutorKeyException.class,
				"Groovy script based object actions are not allowed",
				() -> _addObjectAction(
					RandomTestUtil.randomString(),
					ObjectActionExecutorConstants.KEY_GROOVY,
					ObjectActionTriggerConstants.KEY_STANDALONE,
					UnicodePropertiesBuilder.put(
						"script", "println \"Hello World\""
					).build(),
					false));
		}

		AssertUtils.assertFailure(
			ObjectActionLabelException.class,
			"Label is null for locale " + LocaleUtil.US.getDisplayName(),
			() -> _addObjectAction(
				StringPool.BLANK, RandomTestUtil.randomString(),
				StringPool.BLANK, RandomTestUtil.randomString(),
				ObjectActionTriggerConstants.KEY_ON_AFTER_ADD, false));
		AssertUtils.assertFailure(
			ObjectActionSystemException.class, false,
			"Only allowed bundles can add system object actions",
			() -> _addObjectAction(
				StringPool.BLANK, RandomTestUtil.randomString(),
				StringPool.BLANK, RandomTestUtil.randomString(),
				ObjectActionTriggerConstants.KEY_ON_AFTER_ADD, true));

		String name = RandomTestUtil.randomString();

		ObjectAction objectAction1 = _addObjectAction(
			name, ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"secret", "onafteradd"
			).put(
				"url", "https://onafteradd.com"
			).build(),
			false);

		AssertUtils.assertFailure(
			ObjectActionNameException.class, "Duplicate name " + name,
			() -> _addObjectAction(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), name,
				ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE, false));

		AssertUtils.assertFailure(
			ObjectActionNameException.class, "Name is null",
			() -> _addObjectAction(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE, false));
		AssertUtils.assertFailure(
			ObjectActionNameException.class,
			"Name must be less than 41 characters",
			() -> _addObjectAction(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(42),
				ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE, false));
		AssertUtils.assertFailure(
			ObjectActionNameException.class,
			"Name must only contain letters and digits",
			() -> _addObjectAction(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), "Abl e",
				ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE, false));
		AssertUtils.assertFailure(
			ObjectActionNameException.class,
			"Name must only contain letters and digits",
			() -> _addObjectAction(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), "Abl-e",
				ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE, false));
		AssertUtils.assertFailure(
			ObjectActionTriggerKeyException.class,
			"The object action trigger key onAfterRootUpdate can only be " +
				"used by a root object definition",
			() -> _addObjectAction(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				ObjectActionTriggerConstants.KEY_ON_AFTER_ROOT_UPDATE, false));

		ObjectAction objectAction2 = _addObjectAction(
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
			UnicodePropertiesBuilder.put(
				"secret", "onafterdelete"
			).put(
				"url", "https://onafterdelete.com"
			).build(),
			false);
		ObjectAction objectAction3 = _addObjectAction(
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE,
			UnicodePropertiesBuilder.put(
				"secret", "onafterupdate"
			).put(
				"url", "https://onafterupdate.com"
			).build(),
			false);
		ObjectAction objectAction4 = _addObjectAction(
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_STANDALONE,
			UnicodePropertiesBuilder.put(
				"script", "println \"Hello World\""
			).build(),
			false);

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.put(
			"objectDefinitionId", _objectDefinition.getObjectDefinitionId()
		).put(
			"predefinedValues",
			JSONUtil.putAll(
				JSONUtil.put(
					"inputAsValue", true
				).put(
					"name", "firstName"
				).put(
					"value", "Peter"
				),
				JSONUtil.put(
					"inputAsValue", true
				).put(
					"name", "lastName"
				).put(
					"value", "White"
				),
				JSONUtil.put(
					"inputAsValue", true
				).put(
					"name", "time"
				).put(
					"value", "2023-06-01 06:42:08.0"
				)
			).toString()
		).build();

		try {
			_addObjectAction(
				RandomTestUtil.randomString(),
				ObjectActionExecutorConstants.KEY_ADD_OBJECT_ENTRY,
				ObjectActionTriggerConstants.KEY_STANDALONE, unicodeProperties,
				false);

			Assert.fail();
		}
		catch (ObjectActionParametersException
					objectActionParametersException) {

			Assert.assertEquals(
				"invalid",
				MapUtil.getString(
					objectActionParametersException.getMessageKeys(),
					"objectDefinitionId"));
		}

		ObjectAction objectAction5 = _addObjectAction(
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_UPDATE_OBJECT_ENTRY,
			ObjectActionTriggerConstants.KEY_STANDALONE, unicodeProperties,
			false);

		ObjectAction systemObjectAction = _addObjectAction(
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_UPDATE_OBJECT_ENTRY,
			ObjectActionTriggerConstants.KEY_STANDALONE,
			UnicodePropertiesBuilder.put(
				"objectDefinitionId", _objectDefinition.getObjectDefinitionId()
			).put(
				"predefinedValues",
				JSONUtil.putAll(
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", "firstName"
					).put(
						"value", "Jack"
					)
				).toString()
			).build(),
			true);

		_objectDefinition = _publishCustomObjectDefinition();

		// Auto increment object field should not be populated by object actions

		ObjectField autoIncrementObjectField =
			ObjectFieldUtil.addCustomObjectField(
				new AutoIncrementObjectFieldBuilder(
				).userId(
					TestPropsValues.getUserId()
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					StringUtil.randomId()
				).objectDefinitionId(
					_objectDefinition.getObjectDefinitionId()
				).objectFieldSettings(
					Arrays.asList(
						new ObjectFieldSettingBuilder(
						).name(
							ObjectFieldSettingConstants.NAME_INITIAL_VALUE
						).value(
							"0123"
						).build(),
						new ObjectFieldSettingBuilder(
						).name(
							ObjectFieldSettingConstants.NAME_PREFIX
						).value(
							"LPS-"
						).build())
				).build());

		try {
			_addObjectAction(
				RandomTestUtil.randomString(),
				ObjectActionExecutorConstants.KEY_ADD_OBJECT_ENTRY,
				ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
				UnicodePropertiesBuilder.put(
					"objectDefinitionId",
					_objectDefinition.getObjectDefinitionId()
				).put(
					"predefinedValues",
					JSONUtil.putAll(
						JSONUtil.put(
							"inputAsValue", true
						).put(
							"name", autoIncrementObjectField.getName()
						).put(
							"value", RandomTestUtil.randomString()
						)
					).toString()
				).build(),
				false);

			Assert.fail();
		}
		catch (ObjectActionParametersException
					objectActionParametersException) {

			Map<String, Object> messageKeys =
				objectActionParametersException.getMessageKeys();

			Assert.assertEquals(
				"invalid",
				MapUtil.getString(
					(Map<String, String>)messageKeys.get("predefinedValues"),
					autoIncrementObjectField.getName()));
		}

		_objectFieldLocalService.deleteObjectField(autoIncrementObjectField);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		String originalName = PrincipalThreadLocal.getName();

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(_user));
			PrincipalThreadLocal.setName(_user.getUserId());

			// Add object entry

			Assert.assertEquals(0, _argumentsList.size());

			ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
				TestPropsValues.getUserId(), 0,
				_objectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"firstName", "John"
				).put(
					"lastName", "Smith"
				).build(),
				ServiceContextTestUtil.getServiceContext());

			// On after create

			_assertWebhookObjectAction(
				"John", "Smith", ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
				_objectDefinition, null, null, WorkflowConstants.STATUS_DRAFT);

			// Execute standalone action to run a Groovy script

			ObjectEntryResource objectEntryResource = _getObjectEntryResource(
				_user);

			try {
				objectEntryResource.putObjectEntryObjectActionObjectActionName(
					objectEntry.getObjectEntryId(), objectAction4.getName());

				Assert.fail();
			}
			catch (PrincipalException.MustHavePermission principalException) {
				Assert.assertThat(
					principalException.getMessage(),
					CoreMatchers.containsString(
						StringBundler.concat(
							"User ", _user.getUserId(), " must have ",
							objectAction4.getName(), " permission for")));
			}

			_addModelResourcePermissions(
				objectAction4.getName(), objectEntry.getObjectEntryId(),
				_user.getUserId());

			objectEntryResource.putObjectEntryObjectActionObjectActionName(
				objectEntry.getObjectEntryId(), objectAction4.getName());

			_assertGroovyObjectActionExecutorArguments("John", objectEntry);

			// Update object entry

			Assert.assertEquals(0, _argumentsList.size());

			objectEntry = _objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
				HashMapBuilder.<String, Serializable>put(
					"firstName", "João"
				).put(
					"lastName", "o Discípulo Amado"
				).build(),
				ServiceContextTestUtil.getServiceContext());

			// On after update

			_assertWebhookObjectAction(
				"João", "o Discípulo Amado",
				ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE,
				_objectDefinition, "John", "Smith",
				WorkflowConstants.STATUS_APPROVED);

			// Execute standalone action to update the current object entry

			try {
				objectEntryResource.
					putByExternalReferenceCodeObjectEntryExternalReferenceCodeObjectActionObjectActionName(
						objectEntry.getExternalReferenceCode(),
						objectAction5.getName());

				Assert.fail();
			}
			catch (PrincipalException.MustHavePermission principalException) {
				Assert.assertThat(
					principalException.getMessage(),
					CoreMatchers.containsString(
						StringBundler.concat(
							"User ", _user.getUserId(), " must have ",
							objectAction5.getName(), " permission for")));
			}

			_addModelResourcePermissions(
				objectAction5.getName(), objectEntry.getObjectEntryId(),
				_user.getUserId());

			objectEntryResource.
				putByExternalReferenceCodeObjectEntryExternalReferenceCodeObjectActionObjectActionName(
					objectEntry.getExternalReferenceCode(),
					objectAction5.getName());

			Map<String, Serializable> values =
				_objectEntryLocalService.getValues(
					objectEntry.getObjectEntryId());

			Assert.assertEquals(
				"Peter", MapUtil.getString(values, "firstName"));
			Assert.assertEquals("White", MapUtil.getString(values, "lastName"));
			Assert.assertEquals(
				"2023-06-01 06:42:08.0", MapUtil.getString(values, "time"));

			// Execute standalone system action to update the current object
			// entry

			objectEntry = _objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
				HashMapBuilder.<String, Serializable>put(
					"firstName", RandomTestUtil.randomString()
				).build(),
				ServiceContextTestUtil.getServiceContext());

			_addModelResourcePermissions(
				systemObjectAction.getName(), objectEntry.getObjectEntryId(),
				_user.getUserId());

			objectEntryResource.
				putByExternalReferenceCodeObjectEntryExternalReferenceCodeObjectActionObjectActionName(
					objectEntry.getExternalReferenceCode(),
					systemObjectAction.getName());

			values = _objectEntryLocalService.getValues(
				objectEntry.getObjectEntryId());

			Assert.assertEquals("Jack", MapUtil.getString(values, "firstName"));

			// Delete object entry

			Assert.assertEquals(0, _argumentsList.size());

			_objectEntryLocalService.deleteObjectEntry(objectEntry);

			// On after remove

			_assertWebhookObjectAction(
				"Jack", "White",
				ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
				_objectDefinition, "Jack", "White",
				WorkflowConstants.STATUS_APPROVED);

			// Draft

			_objectDefinition.setEnableObjectEntryDraft(true);

			_objectDefinition =
				_objectDefinitionLocalService.updateObjectDefinition(
					_objectDefinition);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext();

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			objectEntry = _objectEntryLocalService.addObjectEntry(
				TestPropsValues.getUserId(), 0,
				_objectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"firstName", "John"
				).build(),
				serviceContext);

			_assertWebhookObjectAction(
				"John", null, ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
				_objectDefinition, null, null, WorkflowConstants.STATUS_DRAFT);

			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

			_objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
				HashMapBuilder.<String, Serializable>put(
					"firstName", "Peter"
				).build(),
				serviceContext);

			_assertWebhookObjectAction(
				"Peter", null, ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE,
				_objectDefinition, "John", null,
				WorkflowConstants.STATUS_DRAFT);

			// Hierarchy, root object entry

			ObjectDefinition objectDefinitionA =
				ObjectDefinitionTestUtil.addCustomObjectDefinition(
					false, _objectDefinitionLocalService,
					Collections.singletonList(
						ObjectFieldUtil.createObjectField(
							ObjectFieldConstants.BUSINESS_TYPE_TEXT,
							ObjectFieldConstants.DB_TYPE_STRING, "First Name",
							"firstName")));

			ObjectDefinition objectDefinitionAA =
				ObjectDefinitionTestUtil.addCustomObjectDefinition(
					ObjectDefinitionTestUtil.getRandomName(),
					_objectDefinitionLocalService);

			ObjectRelationship objectRelationshipA_AA =
				ObjectRelationshipTestUtil.addObjectRelationship(
					_objectRelationshipLocalService, objectDefinitionA,
					objectDefinitionAA);

			ObjectDefinition objectDefinitionAAA =
				ObjectDefinitionTestUtil.addCustomObjectDefinition(
					ObjectDefinitionTestUtil.getRandomName(),
					_objectDefinitionLocalService);

			ObjectRelationship objectRelationshipAA_AAA =
				ObjectRelationshipTestUtil.addObjectRelationship(
					_objectRelationshipLocalService, objectDefinitionAA,
					objectDefinitionAAA);

			TreeTestUtil.bind(
				_objectDefinitionLocalService,
				Arrays.asList(
					objectRelationshipAA_AAA, objectRelationshipA_AA));

			_objectActionLocalService.addObjectAction(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				objectDefinitionA.getObjectDefinitionId(), true,
				StringPool.BLANK, RandomTestUtil.randomString(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				RandomTestUtil.randomString(),
				ObjectActionExecutorConstants.KEY_WEBHOOK,
				ObjectActionTriggerConstants.KEY_ON_AFTER_ROOT_UPDATE,
				UnicodePropertiesBuilder.put(
					"secret", "onafterrootupdate"
				).put(
					"url", "https://onafterrootupdate.com"
				).build(),
				false);

			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinitionA.getObjectDefinitionId());

			ObjectEntry rootObjectEntry =
				_objectEntryLocalService.addObjectEntry(
					TestPropsValues.getUserId(), 0,
					objectDefinitionA.getObjectDefinitionId(),
					HashMapBuilder.<String, Serializable>put(
						"firstName", "John"
					).build(),
					ServiceContextTestUtil.getServiceContext());

			// Hierarchy, add object entry in a child node

			ObjectField relationshipObjectField =
				_objectFieldLocalService.getObjectField(
					objectRelationshipA_AA.getObjectFieldId2());

			objectEntry = _objectEntryLocalService.addObjectEntry(
				TestPropsValues.getUserId(), 0,
				objectDefinitionAA.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"able", RandomTestUtil.randomString()
				).put(
					relationshipObjectField.getName(),
					rootObjectEntry.getObjectEntryId()
				).build(),
				ServiceContextTestUtil.getServiceContext());

			// Hierarchy, on after root update

			_assertWebhookObjectAction(
				"John", null,
				ObjectActionTriggerConstants.KEY_ON_AFTER_ROOT_UPDATE,
				objectDefinitionA, null, null,
				WorkflowConstants.STATUS_APPROVED);

			// Hierarchy, add object entry in a grandchild node

			relationshipObjectField = _objectFieldLocalService.getObjectField(
				objectRelationshipAA_AAA.getObjectFieldId2());

			_objectEntryLocalService.addObjectEntry(
				TestPropsValues.getUserId(), 0,
				objectDefinitionAAA.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"able", RandomTestUtil.randomString()
				).put(
					relationshipObjectField.getName(),
					objectEntry.getObjectEntryId()
				).build(),
				ServiceContextTestUtil.getServiceContext());

			// Hierarchy, on after root update

			_assertWebhookObjectAction(
				"John", null,
				ObjectActionTriggerConstants.KEY_ON_AFTER_ROOT_UPDATE,
				objectDefinitionA, null, null,
				WorkflowConstants.STATUS_APPROVED);

			_objectEntryLocalService.deleteObjectEntry(
				rootObjectEntry.getObjectEntryId());

			_objectDefinitionLocalService.unbindObjectDefinition(
				objectDefinitionA.getObjectDefinitionId());

			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinitionA.getObjectDefinitionId());
			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinitionAA.getObjectDefinitionId());
			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinitionAAA.getObjectDefinitionId());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
			PrincipalThreadLocal.setName(originalName);
		}

		// Delete object actions

		_objectActionLocalService.deleteObjectAction(objectAction1);
		_objectActionLocalService.deleteObjectAction(objectAction2);
		_objectActionLocalService.deleteObjectAction(objectAction3);
		_objectActionLocalService.deleteObjectAction(objectAction4);
		_objectActionLocalService.deleteObjectAction(objectAction5);
		_objectActionLocalService.deleteObjectAction(systemObjectAction);
	}

	@Test
	public void testAddObjectActionWithCircularReference() throws Exception {
		_publishCustomObjectDefinition();

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.put(
			"objectDefinitionId", _objectDefinition.getObjectDefinitionId()
		).put(
			"predefinedValues",
			JSONUtil.putAll(
				JSONUtil.put(
					"inputAsValue", true
				).put(
					"name", "firstName"
				).put(
					"value", RandomTestUtil.randomString()
				)
			).toString()
		).build();

		// When you add a new object entry that belongs to an object definition,
		// update the newly added object entry

		_addObjectAction(
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_UPDATE_OBJECT_ENTRY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD, unicodeProperties,
			false);

		// When you update an object entry that belongs to an object definition,
		// add a new object entry to the object definition

		_addObjectAction(
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_ADD_OBJECT_ENTRY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE, unicodeProperties,
			false);

		// Each call to the method _testAddObjectActionWithCircularReference
		// should increase the expected objects entries count by 2. The only
		// exception is for the 4th call when we inject a broken thread local.

		_testAddObjectActionWithCircularReference(2);
		_testAddObjectActionWithCircularReference(4);
		_testAddObjectActionWithCircularReference(6);

		Object clearObjectEntryIdsMapThreadLocal =
			ReflectionTestUtil.getAndSetFieldValue(
				ObjectActionThreadLocal.class,
				"_clearObjectEntryIdsMapThreadLocal",
				new ThreadLocal<Boolean>() {

					@Override
					public Boolean get() {
						return true;
					}

				});

		try {
			_testAddObjectActionWithCircularReference(8);

			Assert.fail();
		}
		catch (StackOverflowError stackOverflowError) {
			Assert.assertNotNull(stackOverflowError);
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				ObjectActionThreadLocal.class,
				"_clearObjectEntryIdsMapThreadLocal",
				clearObjectEntryIdsMapThreadLocal);
		}
	}

	@Test
	public void testAddObjectActionWithConditionExpression() throws Exception {
		_publishCustomObjectDefinition();

		ObjectAction objectAction1 = _addObjectAction(
			"equals(firstName, \"João\")", RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
			UnicodePropertiesBuilder.put(
				"script", "println \"Hello World\""
			).build(),
			false);

		// Add object entry with unsatisfied condition

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "John"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_objectEntryLocalService.deleteObjectEntry(objectEntry);

		Assert.assertNull(_argumentsList.poll());

		// Add object entry with satisfied condition

		objectEntry = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "João"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		objectEntry = _objectEntryLocalService.deleteObjectEntry(objectEntry);

		_assertGroovyObjectActionExecutorArguments("João", objectEntry);

		_objectActionLocalService.deleteObjectAction(objectAction1);

		ObjectAction objectAction2 = _addObjectAction(
			"currentUserId == creator", RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_UPDATE_OBJECT_ENTRY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE,
			UnicodePropertiesBuilder.put(
				"objectDefinitionId", _objectDefinition.getObjectDefinitionId()
			).put(
				"predefinedValues",
				JSONUtil.putAll(
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", "firstName"
					).put(
						"value", "John"
					)
				).toString()
			).build(),
			false);
		ObjectAction objectAction3 = _addObjectAction(
			"currentUserId != creator", RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_UPDATE_OBJECT_ENTRY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE,
			UnicodePropertiesBuilder.put(
				"objectDefinitionId", _objectDefinition.getObjectDefinitionId()
			).put(
				"predefinedValues",
				JSONUtil.putAll(
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", "firstName"
					).put(
						"value", "Peter"
					)
				).toString()
			).build(),
			false);

		objectEntry = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", RandomTestUtil.randomString()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", RandomTestUtil.randomString()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			"John",
			MapUtil.getString(
				_objectEntryLocalService.getValues(
					objectEntry.getObjectEntryId()),
				"firstName"));

		_objectActionLocalService.deleteObjectAction(objectAction2);
		_objectActionLocalService.deleteObjectAction(objectAction3);
	}

	@Test
	public void testAddObjectActionWithMoreThanOneObjectEntry()
		throws Exception {

		// On after add

		_publishCustomObjectDefinition();

		ObjectAction objectAction1 = _objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"secret", "onafteradd"
			).put(
				"url", "https://onafteradd.com"
			).build(),
			false);

		Assert.assertEquals(0, _argumentsList.size());

		ObjectEntry objectEntry1 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "John"
			).put(
				"lastName", "Smith"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_assertWebhookObjectAction(
			"John", "Smith", ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			_objectDefinition, null, null, WorkflowConstants.STATUS_DRAFT);

		ObjectEntry objectEntry2 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "Peter"
			).put(
				"lastName", "White"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_assertWebhookObjectAction(
			"Peter", "White", ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			_objectDefinition, null, null, WorkflowConstants.STATUS_DRAFT);

		_objectEntryLocalService.deleteObjectEntry(objectEntry1);
		_objectEntryLocalService.deleteObjectEntry(objectEntry2);

		_objectActionLocalService.deleteObjectAction(objectAction1);

		// On after delete

		ObjectAction objectAction2 = _objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
			UnicodePropertiesBuilder.put(
				"secret", "onafterdelete"
			).put(
				"url", "https://onafterdelete.com"
			).build(),
			false);

		Assert.assertEquals(0, _argumentsList.size());

		ObjectEntry objectEntry3 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "John"
			).put(
				"lastName", "Smith"
			).build(),
			ServiceContextTestUtil.getServiceContext());
		ObjectEntry objectEntry4 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "Peter"
			).put(
				"lastName", "White"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_objectEntryLocalService.deleteObjectEntry(objectEntry3);

		_assertWebhookObjectAction(
			"John", "Smith", ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
			_objectDefinition, "John", "Smith",
			WorkflowConstants.STATUS_APPROVED);

		_objectEntryLocalService.deleteObjectEntry(objectEntry4);

		_assertWebhookObjectAction(
			"Peter", "White", ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
			_objectDefinition, "Peter", "White",
			WorkflowConstants.STATUS_APPROVED);

		_objectActionLocalService.deleteObjectAction(objectAction2);

		// On after update

		ObjectAction objectAction3 = _objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE,
			UnicodePropertiesBuilder.put(
				"secret", "onafterupdate"
			).put(
				"url", "https://onafterupdate.com"
			).build(),
			false);

		ObjectEntry objectEntry5 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "Peter"
			).put(
				"lastName", "White"
			).build(),
			ServiceContextTestUtil.getServiceContext());
		ObjectEntry objectEntry6 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "Peter"
			).put(
				"lastName", "White"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		objectEntry5 = _objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry5.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "John"
			).put(
				"lastName", "Smith"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_assertWebhookObjectAction(
			"John", "Smith", ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE,
			_objectDefinition, "Peter", "White",
			WorkflowConstants.STATUS_APPROVED);

		objectEntry6 = _objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry6.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "João"
			).put(
				"lastName", "o Discípulo Amado"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_assertWebhookObjectAction(
			"João", "o Discípulo Amado",
			ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE, _objectDefinition,
			"Peter", "White", WorkflowConstants.STATUS_APPROVED);

		_objectEntryLocalService.deleteObjectEntry(objectEntry5);
		_objectEntryLocalService.deleteObjectEntry(objectEntry6);

		_objectActionLocalService.deleteObjectAction(objectAction3);
	}

	@Test
	public void testAddObjectActionWithSystemObject() throws Exception {

		// Commerce order system object

		ObjectDefinition commerceOrderObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
				TestPropsValues.getCompanyId(), CommerceOrder.class.getName());

		Set<String> objectActionTriggerKeys = new HashSet<>();

		ListUtil.isNotEmptyForEach(
			_objectActionTriggerRegistry.getObjectActionTriggers(
				commerceOrderObjectDefinition.getClassName()),
			objectActionTrigger -> objectActionTriggerKeys.add(
				objectActionTrigger.getKey()));

		Assert.assertTrue(
			objectActionTriggerKeys.contains(
				DestinationNames.COMMERCE_ORDER_STATUS));
		Assert.assertTrue(
			objectActionTriggerKeys.contains(
				DestinationNames.COMMERCE_PAYMENT_STATUS));

		// Add object action to update commerce order status to
		// CommerceOrderConstants#ORDER_STATUS_PROCESSING after updating payment
		// status

		ObjectAction objectAction1 = _objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			commerceOrderObjectDefinition.getObjectDefinitionId(), true,
			StringPool.BLANK, RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_UPDATE_OBJECT_ENTRY,
			DestinationNames.COMMERCE_PAYMENT_STATUS,
			UnicodePropertiesBuilder.put(
				"objectDefinitionId",
				commerceOrderObjectDefinition.getObjectDefinitionId()
			).put(
				"predefinedValues",
				JSONUtil.putAll(
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", "orderStatus"
					).put(
						"value", CommerceOrderConstants.ORDER_STATUS_PROCESSING
					)
				).toString()
			).build(),
			false);

		// Add object action to create commerce order after updating order
		// status to CommerceOrderConstants#ORDER_STATUS_PROCESSING

		Group group = GroupTestUtil.addGroup();

		AccountEntry accountEntry = CommerceTestUtil.addAccount(
			group.getGroupId(), TestPropsValues.getUserId());

		CommerceCurrency commerceCurrency =
			CommerceCurrencyTestUtil.addCommerceCurrency(
				TestPropsValues.getCompanyId());

		CommerceChannel commerceChannel = CommerceTestUtil.addCommerceChannel(
			group.getGroupId(), commerceCurrency.getCode());

		ObjectAction objectAction2 = _objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			commerceOrderObjectDefinition.getObjectDefinitionId(), true,
			"orderStatus == 10", RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_ADD_OBJECT_ENTRY,
			DestinationNames.COMMERCE_ORDER_STATUS,
			UnicodePropertiesBuilder.put(
				"objectDefinitionId",
				commerceOrderObjectDefinition.getObjectDefinitionId()
			).put(
				"predefinedValues",
				JSONUtil.putAll(
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", "accountId"
					).put(
						"value", accountEntry.getAccountEntryId()
					),
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", "channelId"
					).put(
						"value", commerceChannel.getCommerceChannelId()
					),
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", "currencyCode"
					).put(
						"value", commerceCurrency.getCode()
					),
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", "externalReferenceCode"
					).put(
						"value", "newCommerceOrder"
					),
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", "orderStatus"
					).put(
						"value", CommerceOrderConstants.ORDER_STATUS_OPEN
					),
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", "shippingAmount"
					).put(
						"value", "10"
					)
				).toString()
			).build(),
			false);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		String originalName = PrincipalThreadLocal.getName();

		try {
			PrincipalThreadLocal.setName(_user.getUserId());
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(_user));

			CommerceOrder commerceOrder1 = CommerceTestUtil.addB2CCommerceOrder(
				_user.getUserId(), commerceChannel.getGroupId(),
				commerceCurrency);

			commerceOrder1 = _commerceOrderEngine.checkoutCommerceOrder(
				commerceOrder1, _user.getUserId());

			Assert.assertEquals(
				CommerceOrderConstants.ORDER_STATUS_PENDING,
				commerceOrder1.getOrderStatus());

			_commerceOrderLocalService.updatePaymentStatus(
				commerceOrder1.getUserId(), commerceOrder1.getCommerceOrderId(),
				CommerceOrderPaymentConstants.STATUS_COMPLETED);

			commerceOrder1 = _commerceOrderLocalService.getCommerceOrder(
				commerceOrder1.getCommerceOrderId());

			Assert.assertEquals(
				CommerceOrderConstants.ORDER_STATUS_PROCESSING,
				commerceOrder1.getOrderStatus());

			CommerceOrder commerceOrder2 =
				_commerceOrderLocalService.fetchByExternalReferenceCode(
					"newCommerceOrder", TestPropsValues.getCompanyId());

			Assert.assertNotNull(commerceOrder2);

			Assert.assertEquals(
				accountEntry.getAccountEntryId(),
				commerceOrder2.getCommerceAccountId());
			Assert.assertEquals(
				commerceCurrency.getCommerceCurrencyId(),
				commerceOrder2.getCommerceCurrencyId());
			Assert.assertEquals(
				CommerceOrderConstants.ORDER_STATUS_OPEN,
				commerceOrder2.getOrderStatus());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
			PrincipalThreadLocal.setName(originalName);
		}

		// Organization system object

		ObjectDefinition organizationObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
				TestPropsValues.getCompanyId(), Organization.class.getName());

		ObjectField objectField1 = ObjectFieldUtil.addCustomObjectField(
			new TextObjectFieldBuilder(
			).userId(
				TestPropsValues.getUserId()
			).indexed(
				true
			).indexedAsKeyword(
				true
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				StringUtil.randomId()
			).objectDefinitionId(
				organizationObjectDefinition.getObjectDefinitionId()
			).build());

		ObjectAction objectAction3 = _objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			organizationObjectDefinition.getObjectDefinitionId(), true,
			StringPool.BLANK, RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_ADD_OBJECT_ENTRY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"objectDefinitionId",
				organizationObjectDefinition.getObjectDefinitionId()
			).put(
				"predefinedValues",
				JSONUtil.putAll(
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", objectField1.getName()
					).put(
						"value", "Custom1"
					),
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", "comment"
					).put(
						"value", "test1"
					),
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", "name"
					).put(
						"value", "Organization1"
					)
				).toString()
			).build(),
			false);

		ObjectAction objectAction4 = _addObjectAction(
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_ADD_OBJECT_ENTRY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"objectDefinitionId",
				organizationObjectDefinition.getObjectDefinitionId()
			).put(
				"predefinedValues",
				JSONUtil.putAll(
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", objectField1.getName()
					).put(
						"value", "Custom2"
					),
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", "comment"
					).put(
						"value", "test2"
					),
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", "name"
					).put(
						"value", "Organization2"
					)
				).toString()
			).build(),
			false);

		_publishCustomObjectDefinition();

		try {
			PrincipalThreadLocal.setName(_user.getUserId());
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(_user));

			OrganizationTestUtil.addOrganization(
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
				RandomTestUtil.randomString(), false);

			_assertOrganization(
				"test1", "Organization1", organizationObjectDefinition,
				objectField1, "Custom1");

			_objectEntryLocalService.addObjectEntry(
				TestPropsValues.getUserId(), 0,
				_objectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"firstName", "John"
				).build(),
				ServiceContextTestUtil.getServiceContext());

			_assertOrganization(
				"test1", "Organization1", organizationObjectDefinition,
				objectField1, "Custom1");
			_assertOrganization(
				"test2", "Organization2", organizationObjectDefinition,
				objectField1, "Custom2");
		}
		finally {
			PrincipalThreadLocal.setName(originalName);
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}

		// User system object

		ObjectDefinition userObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
				TestPropsValues.getCompanyId(), User.class.getName());

		ObjectField objectField2 = ObjectFieldUtil.addCustomObjectField(
			new TextObjectFieldBuilder(
			).userId(
				TestPropsValues.getUserId()
			).indexed(
				true
			).indexedAsKeyword(
				true
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				StringUtil.randomId()
			).objectDefinitionId(
				userObjectDefinition.getObjectDefinitionId()
			).build());
		ObjectField objectField3 = ObjectFieldUtil.addCustomObjectField(
			new TextObjectFieldBuilder(
			).userId(
				TestPropsValues.getUserId()
			).indexed(
				true
			).indexedAsKeyword(
				true
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				StringUtil.randomId()
			).objectDefinitionId(
				userObjectDefinition.getObjectDefinitionId()
			).build());

		// Add object action to create user after adding an object entry

		ObjectAction objectAction5 = _addObjectAction(
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_ADD_OBJECT_ENTRY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"objectDefinitionId",
				userObjectDefinition.getObjectDefinitionId()
			).put(
				"predefinedValues",
				JSONUtil.putAll(
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", objectField2.getName()
					).put(
						"value", "John"
					),
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", "alternateName"
					).put(
						"value", "ScreenName"
					),
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", "emailAddress"
					).put(
						"value", "email@liferay.com"
					),
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", "familyName"
					).put(
						"value", "LastName"
					),
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", "givenName"
					).put(
						"value", "FirstName"
					)
				).toString()
			).build(),
			false);

		// Add object action to update user after adding a user

		ObjectAction objectAction6 = _objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			userObjectDefinition.getObjectDefinitionId(), true,
			StringPool.BLANK, RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_UPDATE_OBJECT_ENTRY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"objectDefinitionId",
				userObjectDefinition.getObjectDefinitionId()
			).put(
				"predefinedValues",
				JSONUtil.putAll(
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", objectField3.getName()
					).put(
						"value", "Peter"
					),
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", "additionalName"
					).put(
						"value", "MiddleName"
					)
				).toString()
			).build(),
			false);

		try {
			PrincipalThreadLocal.setName(_user.getUserId());
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(_user));

			_objectEntryLocalService.addObjectEntry(
				TestPropsValues.getUserId(), 0,
				_objectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"firstName", "John"
				).build(),
				ServiceContextTestUtil.getServiceContext());

			User user = _userLocalService.getUserByScreenName(
				TestPropsValues.getCompanyId(), "ScreenName");

			Assert.assertEquals("email@liferay.com", user.getEmailAddress());
			Assert.assertEquals("FirstName", user.getFirstName());
			Assert.assertEquals("LastName", user.getLastName());
			Assert.assertEquals("MiddleName", user.getMiddleName());

			Map<String, Serializable> values =
				_objectEntryLocalService.
					getExtensionDynamicObjectDefinitionTableValues(
						userObjectDefinition, user.getUserId());

			Assert.assertEquals("John", values.get(objectField2.getName()));
			Assert.assertEquals("Peter", values.get(objectField3.getName()));

			_userLocalService.deleteUser(user);
		}
		finally {
			PrincipalThreadLocal.setName(originalName);
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}

		_objectActionLocalService.deleteObjectAction(objectAction5);
		_objectActionLocalService.deleteObjectAction(objectAction6);

		// Add object action to execute Groovy after adding a user

		objectAction5 = _objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			userObjectDefinition.getObjectDefinitionId(), true,
			StringPool.BLANK, RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"script", "println \"Hello World 1\""
			).build(),
			false);

		// Add object action to execute Groovy after updating a user

		objectAction6 = _objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			userObjectDefinition.getObjectDefinitionId(), true,
			StringPool.BLANK, RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE,
			UnicodePropertiesBuilder.put(
				"script", "println \"Hello World 2\""
			).build(),
			false);

		// While adding a user, the user is updated and it must not trigger
		// object actions

		User user = UserTestUtil.addUser();

		Assert.assertEquals(1, _argumentsList.size());

		Object[] arguments = _argumentsList.poll();

		Map<String, Object> inputObjects = (Map<String, Object>)arguments[0];

		Assert.assertEquals(
			user.getUserId(), GetterUtil.getLong(inputObjects.get("id")));

		Assert.assertEquals(Collections.emptySet(), arguments[1]);
		Assert.assertEquals("println \"Hello World 1\"", arguments[2]);

		_objectActionLocalService.deleteObjectAction(objectAction1);
		_objectActionLocalService.deleteObjectAction(objectAction2);
		_objectActionLocalService.deleteObjectAction(objectAction3);
		_objectActionLocalService.deleteObjectAction(objectAction4);
		_objectActionLocalService.deleteObjectAction(objectAction5);
		_objectActionLocalService.deleteObjectAction(objectAction6);
		_objectFieldLocalService.deleteObjectField(objectField1);
		_objectFieldLocalService.deleteObjectField(objectField2);
		_objectFieldLocalService.deleteObjectField(objectField3);
	}

	@Test
	public void testConcurrentObjectActions() throws Exception {
		_addObjectAction(
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"script", "println \"Hello World\""
			).build(),
			false);

		_objectDefinition = _publishCustomObjectDefinition();

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		String originalName = PrincipalThreadLocal.getName();

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(_user));
			PrincipalThreadLocal.setName(_user.getUserId());

			Thread thread1 = new Thread(
				() -> {
					try {
						_objectEntryLocalService.addObjectEntry(
							TestPropsValues.getUserId(), 0,
							_objectDefinition.getObjectDefinitionId(),
							HashMapBuilder.<String, Serializable>put(
								"firstName", "John"
							).build(),
							ServiceContextTestUtil.getServiceContext());
					}
					catch (PortalException portalException) {
					}
				});

			Thread thread2 = new Thread(
				() -> {
					try {
						_objectEntryLocalService.addObjectEntry(
							TestPropsValues.getUserId(), 0,
							_objectDefinition.getObjectDefinitionId(),
							HashMapBuilder.<String, Serializable>put(
								"firstName", "Peter"
							).build(),
							ServiceContextTestUtil.getServiceContext());
					}
					catch (PortalException portalException) {
					}
				});

			thread1.start();
			thread2.start();

			thread1.join();
			thread2.join();

			Assert.assertEquals(
				2,
				_objectEntryLocalService.getObjectEntriesCount(
					0, _objectDefinition.getObjectDefinitionId()));

			Assert.assertEquals(2, _argumentsList.size());

			Object[] arguments = _argumentsList.poll();

			Set<String> firstNames = SetUtil.fromArray(
				new String[] {"John", "Peter"});

			Map<String, Object> inputObjects =
				(Map<String, Object>)arguments[0];

			Assert.assertTrue(firstNames.remove(inputObjects.get("firstName")));

			arguments = _argumentsList.poll();

			inputObjects = (Map<String, Object>)arguments[0];

			Assert.assertTrue(firstNames.remove(inputObjects.get("firstName")));
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
			PrincipalThreadLocal.setName(originalName);
		}
	}

	@Test
	public void testDeleteObjectAction() throws Exception {
		ObjectAction systemObjectAction =
			_objectActionLocalService.addObjectAction(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId(), true,
				"equals(firstName, \"John\")", "Able Description",
				LocalizedMapUtil.getLocalizedMap("Able Error Message"),
				LocalizedMapUtil.getLocalizedMap("Able Label"), "Able",
				ObjectActionExecutorConstants.KEY_WEBHOOK,
				ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
				UnicodePropertiesBuilder.put(
					"secret", "0123456789"
				).put(
					"url", "https://onafteradd.com"
				).build(),
				true);

		AssertUtils.assertFailure(
			ObjectActionSystemException.class, false,
			"Only allowed bundles can delete system object actions",
			() -> _objectActionLocalService.deleteObjectAction(
				systemObjectAction));

		_objectActionLocalService.deleteObjectAction(systemObjectAction);
	}

	/**
	 * LPS-189995
	 */
	@Test
	public void testOnAfterUpdateObjectActionWithAttachmentObjectField()
		throws Exception {

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false, _objectDefinitionLocalService,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
						ObjectFieldConstants.DB_TYPE_LONG, true, false, null,
						RandomTestUtil.randomString(), "attachment",
						Arrays.asList(
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.
									NAME_ACCEPTED_FILE_EXTENSIONS
							).value(
								"txt"
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_FILE_SOURCE
							).value(
								ObjectFieldSettingConstants.VALUE_USER_COMPUTER
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_MAX_FILE_SIZE
							).value(
								"100"
							).build()),
						false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
						ObjectFieldConstants.DB_TYPE_INTEGER, true, false, null,
						RandomTestUtil.randomString(), "integer", false)));

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());

		_objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_UPDATE_OBJECT_ENTRY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE,
			UnicodePropertiesBuilder.put(
				"objectDefinitionId", objectDefinition.getObjectDefinitionId()
			).put(
				"predefinedValues",
				JSONUtil.putAll(
					JSONUtil.put(
						"inputAsValue", true
					).put(
						"name", "integer"
					).put(
						"value", "1"
					)
				).toString()
			).build(),
			false);

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"attachment", StringPool.BLANK
			).put(
				"integer", String.valueOf(RandomTestUtil.randomInt())
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_objectEntryLocalService.addOrUpdateObjectEntry(
			objectEntry.getExternalReferenceCode(), TestPropsValues.getUserId(),
			0, objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"attachment", StringPool.BLANK
			).put(
				"integer", String.valueOf(RandomTestUtil.randomInt())
			).build(),
			ServiceContextTestUtil.getServiceContext());

		objectEntry = _objectEntryLocalService.getObjectEntry(
			objectEntry.getObjectEntryId());

		Map<String, Serializable> values = objectEntry.getValues();

		Assert.assertEquals(1, values.get("integer"));
	}

	@Test
	public void testUpdateObjectAction() throws Exception {
		String externalReferenceCode1 = RandomTestUtil.randomString();

		ObjectAction objectAction = _objectActionLocalService.addObjectAction(
			externalReferenceCode1, TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true,
			"equals(firstName, \"John\")", "Able Description",
			LocalizedMapUtil.getLocalizedMap("Able Error Message"),
			LocalizedMapUtil.getLocalizedMap("Able Label"), "Able",
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"secret", "0123456789"
			).put(
				"url", "https://onafteradd.com"
			).build(),
			false);

		_assertObjectAction(
			true, "equals(firstName, \"John\")", "Able Description",
			LocalizedMapUtil.getLocalizedMap("Able Error Message"),
			LocalizedMapUtil.getLocalizedMap("Able Label"), "Able",
			objectAction, ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"secret", "0123456789"
			).put(
				"url", "https://onafteradd.com"
			).build(),
			ObjectActionConstants.STATUS_NEVER_RAN);

		objectAction = _objectActionLocalService.updateObjectAction(
			externalReferenceCode1, objectAction.getObjectActionId(), false,
			"equals(firstName, \"João\")", "Baker Description",
			LocalizedMapUtil.getLocalizedMap("Baker Error Message"),
			LocalizedMapUtil.getLocalizedMap("Baker Label"), "Baker",
			ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
			UnicodePropertiesBuilder.put(
				"secret", "30624700"
			).put(
				"url", "https://onafterdelete.com"
			).build());

		_assertObjectAction(
			false, "equals(firstName, \"João\")", "Baker Description",
			LocalizedMapUtil.getLocalizedMap("Baker Error Message"),
			LocalizedMapUtil.getLocalizedMap("Baker Label"), "Baker",
			objectAction, ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
			UnicodePropertiesBuilder.put(
				"secret", "30624700"
			).put(
				"url", "https://onafterdelete.com"
			).build(),
			ObjectActionConstants.STATUS_NEVER_RAN);

		_publishCustomObjectDefinition();

		objectAction = _objectActionLocalService.updateObjectAction(
			externalReferenceCode1, objectAction.getObjectActionId(), true,
			"equals(firstName, \"John\")", "Charlie Description",
			LocalizedMapUtil.getLocalizedMap("Charlie Error Message"),
			LocalizedMapUtil.getLocalizedMap("Charlie Label"), "Charlie",
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE,
			UnicodePropertiesBuilder.put(
				"secret", "0123456789"
			).put(
				"url", "https://onafterdelete.com"
			).build());

		_assertObjectAction(
			true, "equals(firstName, \"John\")", "Charlie Description",
			LocalizedMapUtil.getLocalizedMap("Charlie Error Message"),
			LocalizedMapUtil.getLocalizedMap("Charlie Label"), "Baker",
			objectAction, ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
			UnicodePropertiesBuilder.put(
				"secret", "0123456789"
			).put(
				"url", "https://onafterdelete.com"
			).build(),
			ObjectActionConstants.STATUS_NEVER_RAN);

		String externalReferenceCode2 = RandomTestUtil.randomString();

		ObjectAction systemObjectAction =
			_objectActionLocalService.addObjectAction(
				externalReferenceCode2, TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId(), true,
				"equals(firstName, \"John\")", "Able Description",
				LocalizedMapUtil.getLocalizedMap("Able Error Message"),
				LocalizedMapUtil.getLocalizedMap("Able Label"), "Able",
				ObjectActionExecutorConstants.KEY_WEBHOOK,
				ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
				UnicodePropertiesBuilder.put(
					"secret", "0123456789"
				).put(
					"url", "https://onafteradd.com"
				).build(),
				true);

		systemObjectAction = _objectActionLocalService.updateObjectAction(
			externalReferenceCode2, systemObjectAction.getObjectActionId(),
			false, "equals(firstName, \"João\")", "Baker Description",
			LocalizedMapUtil.getLocalizedMap("Baker Error Message"),
			LocalizedMapUtil.getLocalizedMap("Baker Label"), "Baker",
			ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
			UnicodePropertiesBuilder.put(
				"secret", "30624700"
			).put(
				"url", "https://onafterdelete.com"
			).build());

		_assertObjectAction(
			false, "equals(firstName, \"João\")", "Baker Description",
			LocalizedMapUtil.getLocalizedMap("Baker Error Message"),
			LocalizedMapUtil.getLocalizedMap("Baker Label"), "Able",
			systemObjectAction, ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"secret", "30624700"
			).put(
				"url", "https://onafterdelete.com"
			).build(),
			ObjectActionConstants.STATUS_NEVER_RAN);

		// Requests from forbidden bundles can only update the label

		String liferayMode = SystemProperties.get("liferay.mode");

		SystemProperties.clear("liferay.mode");

		try {
			systemObjectAction = _objectActionLocalService.updateObjectAction(
				externalReferenceCode2, systemObjectAction.getObjectActionId(),
				false, "equals(firstName, \"John\")", "Charlie Description",
				LocalizedMapUtil.getLocalizedMap("Charlie Error Message"),
				LocalizedMapUtil.getLocalizedMap("Charlie Label"), "Able",
				ObjectActionExecutorConstants.KEY_WEBHOOK,
				ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
				UnicodePropertiesBuilder.put(
					"secret", "0123456789"
				).put(
					"url", "https://onafteradd.com"
				).build());
		}
		finally {
			SystemProperties.set("liferay.mode", liferayMode);
		}

		_assertObjectAction(
			false, "equals(firstName, \"João\")", "Baker Description",
			LocalizedMapUtil.getLocalizedMap("Baker Error Message"),
			LocalizedMapUtil.getLocalizedMap("Charlie Label"), "Able",
			systemObjectAction, ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"secret", "30624700"
			).put(
				"url", "https://onafterdelete.com"
			).build(),
			ObjectActionConstants.STATUS_NEVER_RAN);

		_objectActionLocalService.deleteObjectAction(objectAction);
		_objectActionLocalService.deleteObjectAction(systemObjectAction);
	}

	private void _addModelResourcePermissions(
			String objectActionName, long objectEntryId, long userId)
		throws Exception {

		_resourcePermissionLocalService.addModelResourcePermissions(
			TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
			userId, _objectDefinition.getClassName(),
			String.valueOf(objectEntryId),
			ModelPermissionsFactory.create(
				HashMapBuilder.put(
					RoleConstants.USER, new String[] {objectActionName}
				).build(),
				_objectDefinition.getClassName()));
	}

	private void _addObjectAction(
			String errorMessage, String externalReferenceCode, String label,
			String name, String objectActionTriggerKey, boolean system)
		throws Exception {

		_objectActionLocalService.addObjectAction(
			externalReferenceCode, TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(errorMessage),
			LocalizedMapUtil.getLocalizedMap(label), name,
			ObjectActionExecutorConstants.KEY_GROOVY, objectActionTriggerKey,
			new UnicodeProperties(), system);
	}

	private ObjectAction _addObjectAction(
			String conditionExpression, String name,
			String objectActionExecutorKey, String objectActionTriggerKey,
			UnicodeProperties unicodeProperties, boolean system)
		throws Exception {

		return _objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true,
			conditionExpression, RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			name, objectActionExecutorKey, objectActionTriggerKey,
			unicodeProperties, system);
	}

	private ObjectAction _addObjectAction(
			String name, String objectActionExecutorKey,
			String objectActionTriggerKey, UnicodeProperties unicodeProperties,
			boolean system)
		throws Exception {

		return _addObjectAction(
			StringPool.BLANK, name, objectActionExecutorKey,
			objectActionTriggerKey, unicodeProperties, system);
	}

	private void _assertGroovyObjectActionExecutorArguments(
		String firstName, ObjectEntry objectEntry) {

		Assert.assertEquals(1, _argumentsList.size());

		Object[] arguments = _argumentsList.poll();

		Map<String, Object> inputObjects = (Map<String, Object>)arguments[0];

		Assert.assertEquals(
			objectEntry.getExternalReferenceCode(),
			inputObjects.get("externalReferenceCode"));
		Assert.assertEquals(firstName, inputObjects.get("firstName"));
		Assert.assertEquals(
			objectEntry.getObjectEntryId(), inputObjects.get("id"));

		Assert.assertEquals(Collections.emptySet(), arguments[1]);
		Assert.assertEquals("println \"Hello World\"", arguments[2]);
	}

	private void _assertObjectAction(
		boolean active, String conditionExpression, String description,
		Map<Locale, String> errorMessageMap, Map<Locale, String> labelMap,
		String name, ObjectAction objectAction, String objectActionExecutorKey,
		String objectActionTriggerKey,
		UnicodeProperties parametersUnicodeProperties, int status) {

		Assert.assertEquals(active, objectAction.isActive());
		Assert.assertEquals(
			conditionExpression, objectAction.getConditionExpression());
		Assert.assertEquals(description, objectAction.getDescription());
		Assert.assertEquals(errorMessageMap, objectAction.getErrorMessageMap());
		Assert.assertEquals(labelMap, objectAction.getLabelMap());
		Assert.assertEquals(name, objectAction.getName());
		Assert.assertEquals(
			objectActionExecutorKey, objectAction.getObjectActionExecutorKey());
		Assert.assertEquals(
			objectActionTriggerKey, objectAction.getObjectActionTriggerKey());
		Assert.assertEquals(
			parametersUnicodeProperties,
			objectAction.getParametersUnicodeProperties());
		Assert.assertEquals(status, objectAction.getStatus());
	}

	private void _assertOrganization(
			String comments, String name, ObjectDefinition objectDefinition,
			ObjectField objectField, String objectFieldValue)
		throws Exception {

		Organization organization = _organizationLocalService.getOrganization(
			TestPropsValues.getCompanyId(), name);

		Assert.assertEquals(comments, organization.getComments());

		Map<String, Serializable> values =
			_objectEntryLocalService.
				getExtensionDynamicObjectDefinitionTableValues(
					objectDefinition, organization.getOrganizationId());

		Assert.assertEquals(
			objectFieldValue, values.get(objectField.getName()));

		_organizationLocalService.deleteOrganization(organization);
	}

	private void _assertWebhookObjectAction(
			String firstName, String lastName, String objectActionTriggerKey,
			ObjectDefinition objectDefinition, String originalFirstName,
			String originalLastName, int status)
		throws Exception {

		Assert.assertEquals(1, _argumentsList.size());

		Object[] arguments = _argumentsList.poll();

		Http.Options options = (Http.Options)arguments[0];

		Assert.assertEquals(
			StringUtil.toLowerCase(objectActionTriggerKey),
			options.getHeader("x-api-key"));
		Assert.assertEquals(
			"https://" + StringUtil.toLowerCase(objectActionTriggerKey) +
				".com",
			options.getLocation());

		Http.Body body = options.getBody();

		Assert.assertEquals(StringPool.UTF8, body.getCharset());
		Assert.assertEquals(
			ContentTypes.APPLICATION_JSON, body.getContentType());

		JSONObject payloadJSONObject = _jsonFactory.createJSONObject(
			body.getContent());

		Assert.assertEquals(
			objectActionTriggerKey,
			payloadJSONObject.getString("objectActionTriggerKey"));
		Assert.assertEquals(
			status,
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/objectEntry", "Object/status"));
		Assert.assertEquals(
			firstName,
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/objectEntry",
				"JSONObject/values", "Object/firstName"));
		Assert.assertEquals(
			firstName,
			JSONUtil.getValue(
				payloadJSONObject,
				"JSONObject/objectEntryDTO" + objectDefinition.getShortName(),
				"JSONObject/properties", "Object/firstName"));
		Assert.assertEquals(
			lastName,
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/objectEntry",
				"JSONObject/values", "Object/lastName"));

		if (StringUtil.equals(
				objectActionTriggerKey,
				ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE) ||
			StringUtil.equals(
				objectActionTriggerKey,
				ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE)) {

			Assert.assertEquals(
				originalFirstName,
				JSONUtil.getValue(
					payloadJSONObject, "JSONObject/originalObjectEntry",
					"JSONObject/values", "Object/firstName"));
			Assert.assertEquals(
				originalLastName,
				JSONUtil.getValue(
					payloadJSONObject, "JSONObject/originalObjectEntry",
					"JSONObject/values", "Object/lastName"));
		}
		else {
			Assert.assertNull(
				JSONUtil.getValue(
					payloadJSONObject, "JSONObject/originalObjectEntry"));
		}
	}

	private Object _getAndSetFieldValue(
		Class<?> clazz, String fieldName, String objectActionExecutorKey) {

		return ReflectionTestUtil.getAndSetFieldValue(
			_objectActionExecutorRegistry.getObjectActionExecutor(
				0, objectActionExecutorKey),
			fieldName,
			ProxyUtil.newProxyInstance(
				clazz.getClassLoader(), new Class<?>[] {clazz},
				(proxy, method, arguments) -> {
					_argumentsList.add(arguments);

					if (Objects.equals(
							method.getDeclaringClass(),
							ObjectScriptingExecutor.class) &&
						Objects.equals(method.getName(), "execute")) {

						return Collections.emptyMap();
					}

					return null;
				}));
	}

	private ObjectEntryResource _getObjectEntryResource(User user)
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(
			ObjectActionLocalServiceTest.class);

		try (ServiceTrackerMap<String, ObjectEntryResource> serviceTrackerMap =
				ServiceTrackerMapFactory.openSingleValueMap(
					bundle.getBundleContext(), ObjectEntryResource.class,
					"entity.class.name")) {

			ObjectEntryResource objectEntryResource =
				serviceTrackerMap.getService(
					StringBundler.concat(
						com.liferay.object.rest.dto.v1_0.ObjectEntry.class.
							getName(),
						StringPool.POUND,
						StringUtil.toLowerCase(_objectDefinition.getName())));

			objectEntryResource.setContextAcceptLanguage(
				new AcceptLanguage() {

					@Override
					public List<Locale> getLocales() {
						return Arrays.asList(LocaleUtil.getDefault());
					}

					@Override
					public String getPreferredLanguageId() {
						return LocaleUtil.toLanguageId(LocaleUtil.getDefault());
					}

					@Override
					public Locale getPreferredLocale() {
						return LocaleUtil.getDefault();
					}

				});
			objectEntryResource.setContextCompany(
				_companyLocalService.getCompany(
					_objectDefinition.getCompanyId()));
			objectEntryResource.setContextUser(user);

			Class<?> clazz = objectEntryResource.getClass();

			Method method = clazz.getMethod(
				"setObjectDefinition", ObjectDefinition.class);

			method.invoke(objectEntryResource, _objectDefinition);

			return objectEntryResource;
		}
	}

	private ObjectDefinition _publishCustomObjectDefinition() throws Exception {
		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId());
	}

	private void _testAddObjectActionWithCircularReference(
			int expectedObjectEntriesCount)
		throws Exception {

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		String originalName = PrincipalThreadLocal.getName();

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(_user));
			PrincipalThreadLocal.setName(_user.getUserId());

			_objectEntryLocalService.addObjectEntry(
				TestPropsValues.getUserId(), 0,
				_objectDefinition.getObjectDefinitionId(),
				Collections.singletonMap(
					"firstName", RandomTestUtil.randomString()),
				ServiceContextTestUtil.getServiceContext());

			Assert.assertEquals(
				expectedObjectEntriesCount,
				_objectEntryLocalService.getObjectEntriesCount(
					0, _objectDefinition.getObjectDefinitionId()));
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
			PrincipalThreadLocal.setName(originalName);
		}
	}

	private final Queue<Object[]> _argumentsList = new LinkedList<>();

	@Inject
	private CommerceOrderEngine _commerceOrderEngine;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private ObjectActionEngine _objectActionEngine;

	@Inject
	private ObjectActionExecutorRegistry _objectActionExecutorRegistry;

	@Inject
	private ObjectActionLocalService _objectActionLocalService;

	@Inject
	private ObjectActionTriggerRegistry _objectActionTriggerRegistry;

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Inject
	private OrganizationLocalService _organizationLocalService;

	private Http _originalHttp;
	private ObjectScriptingExecutor _originalObjectScriptingExecutor;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}