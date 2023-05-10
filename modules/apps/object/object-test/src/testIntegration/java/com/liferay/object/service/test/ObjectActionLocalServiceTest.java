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

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.model.CommerceAccount;
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
import com.liferay.object.constants.ObjectActionConstants;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.exception.ObjectActionErrorMessageException;
import com.liferay.object.exception.ObjectActionLabelException;
import com.liferay.object.exception.ObjectActionNameException;
import com.liferay.object.exception.ObjectActionParametersException;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.resource.v1_0.ObjectEntryResource;
import com.liferay.object.scripting.executor.ObjectScriptingExecutor;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.test.util.ObjectDefinitionTestUtil;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

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
@FeatureFlags("LPS-173537")
@RunWith(Arquillian.class)
public class ObjectActionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_objectDefinition = ObjectDefinitionTestUtil.addObjectDefinition(
			_objectDefinitionLocalService,
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
					"First Name", "firstName", true)));
		_originalHttp = (Http)_getAndSetFieldValue(
			Http.class, "_http", ObjectActionExecutorConstants.KEY_WEBHOOK);
		_originalObjectScriptingExecutor =
			(ObjectScriptingExecutor)_getAndSetFieldValue(
				ObjectScriptingExecutor.class, "_objectScriptingExecutor",
				ObjectActionExecutorConstants.KEY_GROOVY);
		_user = UserTestUtil.addUser();
	}

	@After
	public void tearDown() {
		ReflectionTestUtil.setFieldValue(
			_objectActionExecutorRegistry.getObjectActionExecutor(
				ObjectActionExecutorConstants.KEY_WEBHOOK),
			"_http", _originalHttp);
		ReflectionTestUtil.setFieldValue(
			_objectActionExecutorRegistry.getObjectActionExecutor(
				ObjectActionExecutorConstants.KEY_GROOVY),
			"_objectScriptingExecutor", _originalObjectScriptingExecutor);
	}

	@Test
	public void testAddObjectAction() throws Exception {

		// Add object actions

		try {
			_addObjectAction(
				StringPool.BLANK, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				ObjectActionTriggerConstants.KEY_STANDALONE);

			Assert.fail();
		}
		catch (ObjectActionErrorMessageException
					objectActionErrorMessageException) {

			Assert.assertEquals(
				"Error message is null for locale " +
					LocaleUtil.US.getDisplayName(),
				objectActionErrorMessageException.getMessage());
		}

		try {
			_addObjectAction(
				StringPool.BLANK, RandomTestUtil.randomString(),
				StringPool.BLANK, RandomTestUtil.randomString(),
				ObjectActionTriggerConstants.KEY_ON_AFTER_ADD);

			Assert.fail();
		}
		catch (ObjectActionLabelException objectActionLabelException) {
			Assert.assertEquals(
				"Label is null for locale " + LocaleUtil.US.getDisplayName(),
				objectActionLabelException.getMessage());
		}

		try {
			_addObjectAction(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE);

			Assert.fail();
		}
		catch (ObjectActionNameException objectActionNameException) {
			Assert.assertEquals(
				"Name is null", objectActionNameException.getMessage());
		}

		try {
			_addObjectAction(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(42),
				ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE);

			Assert.fail();
		}
		catch (ObjectActionNameException objectActionNameException) {
			Assert.assertEquals(
				"Name must be less than 41 characters",
				objectActionNameException.getMessage());
		}

		try {
			_addObjectAction(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), "Abl e",
				ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE);

			Assert.fail();
		}
		catch (ObjectActionNameException objectActionNameException) {
			Assert.assertEquals(
				"Name must only contain letters and digits",
				objectActionNameException.getMessage());
		}

		try {
			_addObjectAction(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), "Abl-e",
				ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE);

			Assert.fail();
		}
		catch (ObjectActionNameException objectActionNameException) {
			Assert.assertEquals(
				"Name must only contain letters and digits",
				objectActionNameException.getMessage());
		}

		String name = RandomTestUtil.randomString();

		ObjectAction objectAction1 = _addObjectAction(
			name, ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"secret", "onafteradd"
			).put(
				"url", "https://onafteradd.com"
			).build());

		try {
			_addObjectAction(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), name,
				ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE);

			Assert.fail();
		}
		catch (ObjectActionNameException objectActionNameException) {
			Assert.assertEquals(
				"Duplicate name " + name,
				objectActionNameException.getMessage());
		}

		ObjectAction objectAction2 = _addObjectAction(
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
			UnicodePropertiesBuilder.put(
				"secret", "onafterdelete"
			).put(
				"url", "https://onafterdelete.com"
			).build());
		ObjectAction objectAction3 = _addObjectAction(
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE,
			UnicodePropertiesBuilder.put(
				"secret", "onafterupdate"
			).put(
				"url", "https://onafterupdate.com"
			).build());
		ObjectAction objectAction4 = _addObjectAction(
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_STANDALONE,
			UnicodePropertiesBuilder.put(
				"script", "println \"Hello World\""
			).build());

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
				)
			).toString()
		).build();

		try {
			_addObjectAction(
				RandomTestUtil.randomString(),
				ObjectActionExecutorConstants.KEY_UPDATE_OBJECT_ENTRY,
				ObjectActionTriggerConstants.KEY_STANDALONE, unicodeProperties);

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

		_publishCustomObjectDefinition();

		ObjectAction objectAction5 = _addObjectAction(
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_UPDATE_OBJECT_ENTRY,
			ObjectActionTriggerConstants.KEY_STANDALONE, unicodeProperties);

		String originalName = PrincipalThreadLocal.getName();
		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PrincipalThreadLocal.setName(_user.getUserId());
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(_user));

			// Add object entry

			Assert.assertEquals(0, _argumentsList.size());

			ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
				TestPropsValues.getUserId(), 0,
				_objectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"firstName", "John"
				).build(),
				ServiceContextTestUtil.getServiceContext());

			// On after create

			_assertWebhookObjectAction(
				"John", ObjectActionTriggerConstants.KEY_ON_AFTER_ADD, null,
				WorkflowConstants.STATUS_DRAFT);

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
				).build(),
				ServiceContextTestUtil.getServiceContext());

			// On after update

			_assertWebhookObjectAction(
				"João", ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE,
				"John", WorkflowConstants.STATUS_APPROVED);

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

			Assert.assertEquals(
				"Peter",
				MapUtil.getString(
					_objectEntryLocalService.getValues(
						objectEntry.getObjectEntryId()),
					"firstName"));

			// Delete object entry

			Assert.assertEquals(0, _argumentsList.size());

			_objectEntryLocalService.deleteObjectEntry(objectEntry);

			// On after remove

			_assertWebhookObjectAction(
				"Peter", ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
				"Peter", WorkflowConstants.STATUS_APPROVED);
		}
		finally {
			PrincipalThreadLocal.setName(originalName);
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}

		// Delete object actions

		_objectActionLocalService.deleteObjectAction(objectAction1);
		_objectActionLocalService.deleteObjectAction(objectAction2);
		_objectActionLocalService.deleteObjectAction(objectAction3);
		_objectActionLocalService.deleteObjectAction(objectAction4);
	}

	@Test
	public void testAddObjectActionWithConditionExpression() throws Exception {
		_publishCustomObjectDefinition();

		ObjectAction objectAction = _objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true,
			"equals(firstName, \"João\")", RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
			UnicodePropertiesBuilder.put(
				"script", "println \"Hello World\""
			).build());

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

		_objectActionLocalService.deleteObjectAction(objectAction);
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
			).build());

		Assert.assertEquals(0, _argumentsList.size());

		ObjectEntry objectEntry1 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "John"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_assertWebhookObjectAction(
			"John", ObjectActionTriggerConstants.KEY_ON_AFTER_ADD, null,
			WorkflowConstants.STATUS_DRAFT);

		ObjectEntry objectEntry2 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "Peter"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_assertWebhookObjectAction(
			"Peter", ObjectActionTriggerConstants.KEY_ON_AFTER_ADD, null,
			WorkflowConstants.STATUS_DRAFT);

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
			).build());

		Assert.assertEquals(0, _argumentsList.size());

		ObjectEntry objectEntry3 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "John"
			).build(),
			ServiceContextTestUtil.getServiceContext());
		ObjectEntry objectEntry4 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "Peter"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_objectEntryLocalService.deleteObjectEntry(objectEntry3);

		_assertWebhookObjectAction(
			"John", ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE, "John",
			WorkflowConstants.STATUS_APPROVED);

		_objectEntryLocalService.deleteObjectEntry(objectEntry4);

		_assertWebhookObjectAction(
			"Peter", ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE, "Peter",
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
			).build());

		ObjectEntry objectEntry5 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "Peter"
			).build(),
			ServiceContextTestUtil.getServiceContext());
		ObjectEntry objectEntry6 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "Peter"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		objectEntry5 = _objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry5.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "John"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_assertWebhookObjectAction(
			"John", ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE, "Peter",
			WorkflowConstants.STATUS_APPROVED);

		objectEntry6 = _objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry6.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "João"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_assertWebhookObjectAction(
			"João", ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE, "Peter",
			WorkflowConstants.STATUS_APPROVED);

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
			).build());

		// Add object action to create commerce order after updating order
		// status to CommerceOrderConstants#ORDER_STATUS_PROCESSING

		Group group = GroupTestUtil.addGroup();

		CommerceAccount commerceAccount = CommerceTestUtil.addAccount(
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
						"value", commerceAccount.getCommerceAccountId()
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
			).build());

		String originalName = PrincipalThreadLocal.getName();
		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

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
				commerceAccount.getCommerceAccountId(),
				commerceOrder2.getCommerceAccountId());
			Assert.assertEquals(
				commerceCurrency.getCommerceCurrencyId(),
				commerceOrder2.getCommerceCurrencyId());
			Assert.assertEquals(
				CommerceOrderConstants.ORDER_STATUS_OPEN,
				commerceOrder2.getOrderStatus());
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

		ObjectField objectField1 =
			_objectFieldLocalService.addCustomObjectField(
				null, TestPropsValues.getUserId(), 0,
				userObjectDefinition.getObjectDefinitionId(),
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING, true, true, "",
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				false, StringUtil.randomId(), false, false,
				Collections.emptyList());
		ObjectField objectField2 =
			_objectFieldLocalService.addCustomObjectField(
				null, TestPropsValues.getUserId(), 0,
				userObjectDefinition.getObjectDefinitionId(),
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING, true, true, "",
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				false, StringUtil.randomId(), false, false,
				Collections.emptyList());

		// Add object action to create user after adding an object entry

		ObjectAction objectAction3 = _addObjectAction(
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
						"name", objectField1.getName()
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
			).build());

		// Add object action to update user after adding a user

		ObjectAction objectAction4 = _objectActionLocalService.addObjectAction(
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
						"name", objectField2.getName()
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
			).build());

		_publishCustomObjectDefinition();

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

			Assert.assertEquals("John", values.get(objectField1.getName()));
			Assert.assertEquals("Peter", values.get(objectField2.getName()));

			_userLocalService.deleteUser(user);
		}
		finally {
			PrincipalThreadLocal.setName(originalName);
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}

		_objectActionLocalService.deleteObjectAction(objectAction1);
		_objectActionLocalService.deleteObjectAction(objectAction2);
		_objectActionLocalService.deleteObjectAction(objectAction3);
		_objectActionLocalService.deleteObjectAction(objectAction4);
		_objectFieldLocalService.deleteObjectField(objectField1);
		_objectFieldLocalService.deleteObjectField(objectField2);
	}

	@Test
	public void testUpdateObjectAction() throws Exception {
		String externalReferenceCode = RandomTestUtil.randomString();

		ObjectAction objectAction = _objectActionLocalService.addObjectAction(
			externalReferenceCode, TestPropsValues.getUserId(),
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
			).build());

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
			externalReferenceCode, objectAction.getObjectActionId(), false,
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
			externalReferenceCode, objectAction.getObjectActionId(), true,
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
			String name, String objectActionTriggerKey)
		throws Exception {

		_objectActionLocalService.addObjectAction(
			externalReferenceCode, TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(errorMessage),
			LocalizedMapUtil.getLocalizedMap(label), name,
			ObjectActionExecutorConstants.KEY_GROOVY, objectActionTriggerKey,
			new UnicodeProperties());
	}

	private ObjectAction _addObjectAction(
			String name, String objectActionExecutorKey,
			String objectActionTriggerKey, UnicodeProperties unicodeProperties)
		throws Exception {

		return _objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			name, objectActionExecutorKey, objectActionTriggerKey,
			unicodeProperties);
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

	private void _assertWebhookObjectAction(
			String firstName, String objectActionTriggerKey,
			String originalFirstName, int status)
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
				"JSONObject/objectEntryDTO" + _objectDefinition.getShortName(),
				"JSONObject/properties", "Object/firstName"));

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
				objectActionExecutorKey),
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
						_objectDefinition.getOSGiJaxRsName()));

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

	private void _publishCustomObjectDefinition() throws Exception {
		_objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId());
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

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	private Http _originalHttp;
	private ObjectScriptingExecutor _originalObjectScriptingExecutor;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}