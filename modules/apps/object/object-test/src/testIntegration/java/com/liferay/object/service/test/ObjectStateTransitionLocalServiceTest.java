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
import com.liferay.object.model.ObjectState;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.model.ObjectStateTransition;
import com.liferay.object.service.persistence.ObjectStatePersistence;
import com.liferay.object.service.persistence.ObjectStateTransitionPersistence;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Selton Guedes
 */
@RunWith(Arquillian.class)
public class ObjectStateTransitionLocalServiceTest
	extends BaseObjectStateLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.object.service"));

	@Test
	public void testAddObjectStateTransition() throws PortalException {
		long objectStateFlowId = RandomTestUtil.randomLong();
		long sourceObjectStateId = RandomTestUtil.randomLong();
		long targetObjectStateId = RandomTestUtil.randomLong();

		ObjectStateTransition objectStateTransition =
			objectStateTransitionLocalService.addObjectStateTransition(
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
				objectStateFlow.getObjectStateFlowId(), null);

		List<ObjectStateTransition> objectStateTransitions =
			_objectStateTransitionPersistence.findBySourceObjectStateId(
				objectState.getObjectStateId());

		Assert.assertEquals(
			objectStateTransitions.toString(), 2,
			objectStateTransitions.size());

		objectStateTransitionLocalService.
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
		List<ObjectState> objectStates =
			objectStateLocalService.getObjectStateFlowObjectStates(
				objectStateFlow.getObjectStateFlowId());

		for (ObjectState objectState : objectStates) {
			objectState.setObjectStateTransitions(
				_objectStateTransitionPersistence.findBySourceObjectStateId(
					objectState.getObjectStateId()));
		}

		objectStateFlow.setObjectStates(objectStates);

		ObjectStateFlow newObjectStateFlow =
			(ObjectStateFlow)objectStateFlow.clone();

		List<ObjectState> newObjectStates = new ArrayList<>(objectStates);

		for (ObjectState objectState : newObjectStates) {
			objectState.setObjectStateTransitions(
				Collections.singletonList(
					_objectStateTransitionPersistence.
						findBySourceObjectStateId_First(
							objectState.getObjectStateId(), null)));
		}

		newObjectStateFlow.setObjectStates(newObjectStates);

		objectStateTransitionLocalService.updateObjectStateTransitions(
			newObjectStateFlow);

		_assertEquals(
			newObjectStates,
			objectStateLocalService.getObjectStateFlowObjectStates(
				objectStateFlow.getObjectStateFlowId()));
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

	@Inject
	private ObjectStatePersistence _objectStatePersistence;

	@Inject
	private ObjectStateTransitionPersistence _objectStateTransitionPersistence;

}