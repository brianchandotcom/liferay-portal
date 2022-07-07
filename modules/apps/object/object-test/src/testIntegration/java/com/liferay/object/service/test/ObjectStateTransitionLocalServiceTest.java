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
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.model.ObjectState;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.model.ObjectStateTransition;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.object.service.ObjectStateLocalService;
import com.liferay.object.service.ObjectStateTransitionLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Selton Guedes
 */
@RunWith(Arquillian.class)
public class ObjectStateTransitionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
	}

	@Before
	public void setUp() throws Exception {
		_listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(
					RandomTestUtil.randomString()));

		_listTypeEntries = new ArrayList<>();

		for (String key : Arrays.asList("closed", "open", "review")) {
			_listTypeEntries.add(
				_listTypeEntryLocalService.addListTypeEntry(
					TestPropsValues.getUserId(),
					_listTypeDefinition.getListTypeDefinitionId(), key,
					LocalizedMapUtil.getLocalizedMap(key)));
		}

		_setUser(TestPropsValues.getUser());
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testAddObjectStateTransition() throws PortalException {
		long objectStateFlowId = RandomTestUtil.randomLong();
		long sourceObjectStateId = RandomTestUtil.randomLong();
		long targetObjectStateId = RandomTestUtil.randomLong();

		ObjectStateTransition objectStateTransition =
			_objectStateTransitionLocalService.addObjectStateTransition(
				TestPropsValues.getUserId(), objectStateFlowId,
				sourceObjectStateId, targetObjectStateId);

		Assert.assertEquals(
			TestPropsValues.getUserId(), objectStateTransition.getUserId());
		Assert.assertEquals(
			TestPropsValues.getCompanyId(),
			objectStateTransition.getCompanyId());
		Assert.assertEquals(
			objectStateFlowId, objectStateTransition.getObjectStateFlowId());
		Assert.assertEquals(
			sourceObjectStateId,
			objectStateTransition.getSourceObjectStateId());
		Assert.assertEquals(
			targetObjectStateId,
			objectStateTransition.getTargetObjectStateId());
	}

	@Test
	public void testDeleteObjectStateFlowObjectStateTransitions()
		throws PortalException {

		ObjectStateFlow objectStateFlow = _addDefaultObjectStateFlow();

		List<ObjectState> objectStates =
			_objectStateLocalService.getObjectStateFlowObjectStates(
				objectStateFlow.getObjectStateFlowId());

		for (ObjectState objectState : objectStates) {
			Assert.assertNotEquals(
				Collections.emptyList(),
				_objectStateLocalService.getNextObjectStates(
					objectState.getObjectStateId()));
		}

		_objectStateTransitionLocalService.
			deleteObjectStateFlowObjectStateTransitions(
				objectStateFlow.getObjectStateFlowId());

		for (ObjectState objectState : objectStates) {
			Assert.assertEquals(
				Collections.emptyList(),
				_objectStateLocalService.getNextObjectStates(
					objectState.getObjectStateId()));
		}
	}

	@Test
	public void testDeleteObjectStateObjectStateTransitions()
		throws PortalException {

		long objectStateFlowId = RandomTestUtil.randomLong();

		ObjectState objectState1 = _objectStateLocalService.addObjectState(
			TestPropsValues.getUserId(), RandomTestUtil.randomLong(),
			objectStateFlowId);

		ObjectState objectState2 = _objectStateLocalService.addObjectState(
			TestPropsValues.getUserId(), RandomTestUtil.randomLong(),
			objectStateFlowId);

		ObjectStateTransition objectStateTransition =
			_objectStateTransitionLocalService.addObjectStateTransition(
				TestPropsValues.getUserId(), objectStateFlowId,
				objectState1.getObjectStateId(),
				objectState2.getObjectStateId());

		Assert.assertNotNull(objectStateTransition);

		List<ObjectState> objectStates =
			_objectStateLocalService.getNextObjectStates(
				objectState1.getObjectStateId());

		Assert.assertEquals(
			Collections.singletonList(objectState2), objectStates);

		_objectStateTransitionLocalService.
			deleteObjectStateObjectStateTransitions(
				objectState1.getObjectStateId());

		objectStates = _objectStateLocalService.getNextObjectStates(
			objectState1.getObjectStateId());

		Assert.assertEquals(Collections.emptyList(), objectStates);
	}

	@Test
	public void testUpdateObjectStateTransitions() throws PortalException {
		ObjectStateFlow objectStateFlow = _addDefaultObjectStateFlow();

		ObjectState objectState1 = _objectStateLocalService.addObjectState(
			TestPropsValues.getUserId(), RandomTestUtil.randomLong(),
			objectStateFlow.getObjectStateFlowId());

		ObjectState objectState2 = _objectStateLocalService.addObjectState(
			TestPropsValues.getUserId(), RandomTestUtil.randomLong(),
			objectStateFlow.getObjectStateFlowId());

		ObjectStateTransition objectStateTransition =
			_objectStateTransitionLocalService.addObjectStateTransition(
				TestPropsValues.getUserId(),
				objectStateFlow.getObjectStateFlowId(),
				objectState1.getObjectStateId(),
				objectState2.getObjectStateId());

		objectState1.setObjectStateTransitions(
			Collections.singletonList(objectStateTransition));

		objectState2.setObjectStateTransitions(Collections.emptyList());

		objectStateFlow.setObjectStates(
			Arrays.asList(objectState1, objectState2));

		_objectStateTransitionLocalService.updateObjectStateTransitions(
			objectStateFlow);

		List<ObjectState> nextObjectStates =
			_objectStateLocalService.getNextObjectStates(
				objectState1.getObjectStateId());

		Assert.assertEquals(
			Collections.singletonList(objectState2), nextObjectStates);
	}

	private ObjectStateFlow _addDefaultObjectStateFlow()
		throws PortalException {

		ObjectFieldBuilder objectFieldBuilder = new ObjectFieldBuilder();

		return _objectStateFlowLocalService.addDefaultObjectStateFlow(
			objectFieldBuilder.userId(
				TestPropsValues.getUserId()
			).listTypeDefinitionId(
				_listTypeDefinition.getListTypeDefinitionId()
			).state(
				true
			).build());
	}

	private void _setUser(User user) {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private static PermissionChecker _originalPermissionChecker;

	@DeleteAfterTestRun
	private ListTypeDefinition _listTypeDefinition;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@DeleteAfterTestRun
	private List<ListTypeEntry> _listTypeEntries;

	@Inject
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Inject
	private ObjectStateFlowLocalService _objectStateFlowLocalService;

	@Inject
	private ObjectStateLocalService _objectStateLocalService;

	@Inject
	private ObjectStateTransitionLocalService
		_objectStateTransitionLocalService;

}