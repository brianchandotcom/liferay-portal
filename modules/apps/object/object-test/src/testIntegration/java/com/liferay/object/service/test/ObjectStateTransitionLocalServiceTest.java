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
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectState;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.model.ObjectStateTransition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.object.service.ObjectStateLocalService;
import com.liferay.object.service.ObjectStateTransitionLocalService;
import com.liferay.object.service.persistence.ObjectStatePersistence;
import com.liferay.object.service.persistence.ObjectStateTransitionPersistence;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.util.PropsUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
 * @author Selton Guedes
 */
@RunWith(Arquillian.class)
public class ObjectStateTransitionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.object.service"));

	@BeforeClass
	public static void setUpClass() {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-152677", "true"
			).build());
	}

	@AfterClass
	public static void tearDownClass() {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-152677", "false"
			).build());
	}

	@Before
	public void setUp() throws Exception {
		_listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(
					RandomTestUtil.randomString()));

		for (String key : Arrays.asList("step1", "step2", "step3")) {
			_listTypeEntryLocalService.addListTypeEntry(
				TestPropsValues.getUserId(),
				_listTypeDefinition.getListTypeDefinitionId(), key,
				LocalizedMapUtil.getLocalizedMap(key));
		}

		_objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.emptyList());

		_objectStateFlow = _addDefaultObjectStateFlow();
	}

	@After
	public void tearDown() throws PortalException {
		_objectDefinitionLocalService.deleteObjectDefinition(_objectDefinition);
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
	public void testDeleteObjectStateObjectStateTransitions() {
		ObjectState objectState =
			_objectStatePersistence.fetchByObjectStateFlowId_First(
				_objectStateFlow.getObjectStateFlowId(), null);

		List<ObjectStateTransition> objectStateTransitions =
			_objectStateTransitionPersistence.findBySourceObjectStateId(
				objectState.getObjectStateId());

		Assert.assertEquals(
			objectStateTransitions.toString(), 2,
			objectStateTransitions.size());

		_objectStateTransitionLocalService.
			deleteObjectStateObjectStateTransitions(
				objectState.getObjectStateId());

		Assert.assertEquals(
			Collections.emptyList(),
			_objectStateTransitionPersistence.findBySourceObjectStateId(
				objectState.getObjectStateId()));

		Assert.assertEquals(
			Collections.emptyList(),
			_objectStateTransitionPersistence.findByTargetObjectStateId(
				objectState.getObjectStateId()));
	}

	@Test
	public void testUpdateObjectStateTransitions() throws PortalException {
		ObjectStateFlow originalObjectStateFlow = _addDefaultObjectStateFlow();

		List<ObjectState> objectStates =
			_objectStateLocalService.getObjectStateFlowObjectStates(
				originalObjectStateFlow.getObjectStateFlowId());

		for (ObjectState objectState : objectStates) {
			objectState.setObjectStateTransitions(
				_objectStateTransitionPersistence.findBySourceObjectStateId(
					objectState.getObjectStateId()));
		}

		originalObjectStateFlow.setObjectStates(objectStates);

		ObjectStateFlow newObjectStateFlow =
			(ObjectStateFlow)originalObjectStateFlow.clone();

		List<ObjectState> newObjectStates = new ArrayList<>(objectStates);

		for (ObjectState objectState : newObjectStates) {
			objectState.setObjectStateTransitions(
				Collections.singletonList(
					_objectStateTransitionPersistence.
						findBySourceObjectStateId_First(
							objectState.getObjectStateId(), null)));
		}

		newObjectStateFlow.setObjectStates(newObjectStates);

		_objectStateTransitionLocalService.updateObjectStateTransitions(
			newObjectStateFlow);

		_assertEquals(
			newObjectStates,
			_objectStateLocalService.getObjectStateFlowObjectStates(
				originalObjectStateFlow.getObjectStateFlowId()));
	}

	private ObjectStateFlow _addDefaultObjectStateFlow()
		throws PortalException {

		ObjectField objectField = _objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(),
			_listTypeDefinition.getListTypeDefinitionId(),
			_objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_PICKLIST,
			ObjectFieldConstants.DB_TYPE_STRING, null, false, true, "",
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), true, true, Collections.emptyList());

		return _objectStateFlowLocalService.getObjectFieldObjectStateFlow(
			objectField.getObjectFieldId());
	}

	private void _assertEquals(
		List<ObjectState> objectStates1, List<ObjectState> objectStates2) {

		Assert.assertEquals(
			objectStates1.toString(), objectStates1.size(),
			objectStates2.size());

		for (int i = 0; i < objectStates1.size(); i++) {
			ObjectState objectState1 = objectStates1.get(i);
			ObjectState objectState2 = objectStates2.get(i);

			Assert.assertEquals(
				objectState1.getListTypeEntryId(),
				objectState2.getListTypeEntryId());

			List<ObjectStateTransition> objectStateTransitions1 =
				objectState1.getObjectStateTransitions();
			List<ObjectStateTransition> objectStateTransitions2 =
				_objectStateTransitionPersistence.findBySourceObjectStateId(
					objectState2.getObjectStateId());

			Assert.assertEquals(
				objectStateTransitions1.toString(),
				objectStateTransitions1.size(), objectStateTransitions2.size());

			for (int j = 0; j < objectStateTransitions1.size(); j++) {
				ObjectStateTransition objectStateTransition1 =
					objectStateTransitions1.get(j);
				ObjectStateTransition objectStateTransition2 =
					objectStateTransitions2.get(j);

				Assert.assertEquals(
					objectStateTransition1.getSourceObjectStateId(),
					objectStateTransition2.getSourceObjectStateId());
				Assert.assertEquals(
					objectStateTransition1.getTargetObjectStateId(),
					objectStateTransition2.getTargetObjectStateId());
			}
		}
	}

	@DeleteAfterTestRun
	private ListTypeDefinition _listTypeDefinition;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Inject
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	private ObjectStateFlow _objectStateFlow;

	@Inject
	private ObjectStateFlowLocalService _objectStateFlowLocalService;

	@Inject
	private ObjectStateLocalService _objectStateLocalService;

	@Inject
	private ObjectStatePersistence _objectStatePersistence;

	@Inject
	private ObjectStateTransitionLocalService
		_objectStateTransitionLocalService;

	@Inject
	private ObjectStateTransitionPersistence _objectStateTransitionPersistence;

}