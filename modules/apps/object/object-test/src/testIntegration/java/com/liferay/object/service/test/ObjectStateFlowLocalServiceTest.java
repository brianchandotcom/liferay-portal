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
import com.liferay.list.type.model.ListTypeEntryModel;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.exception.NoSuchObjectStateFlowException;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectState;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.model.ObjectStateModel;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.object.service.ObjectStateLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Selton Guedes
 */
@RunWith(Arquillian.class)
public class ObjectStateFlowLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

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
	}

	@Test
	public void testAddDefaultObjectStateFlow() throws Exception {
		ObjectStateFlow objectStateFlow =
			_objectStateFlowLocalService.addDefaultObjectStateFlow(
				_addObjectField(0, false));

		Assert.assertNull(objectStateFlow);

		ObjectField objectField = _addObjectField(
			_listTypeDefinition.getListTypeDefinitionId(), true);

		objectStateFlow =
			_objectStateFlowLocalService.addDefaultObjectStateFlow(objectField);

		Assert.assertEquals(
			TestPropsValues.getCompanyId(), objectStateFlow.getCompanyId());
		Assert.assertEquals(
			TestPropsValues.getUserId(), objectStateFlow.getUserId());
		Assert.assertEquals(
			objectField.getObjectFieldId(), objectStateFlow.getObjectFieldId());

		long objectStateFlowId = objectStateFlow.getObjectStateFlowId();

		List<ObjectState> objectStates =
			_objectStateLocalService.getObjectStateFlowObjectStates(
				objectStateFlowId);

		Assert.assertEquals(
			ListUtil.toList(
				_listTypeEntries, ListTypeEntryModel::getListTypeEntryId),
			ListUtil.toList(
				objectStates, ObjectStateModel::getListTypeEntryId));

		for (ListTypeEntry listTypeEntry : _listTypeEntries) {
			List<ObjectState> nextObjectStates = new ArrayList<>();

			for (ListTypeEntry listTypeEntry1 : _listTypeEntries) {
				if (listTypeEntry1.getListTypeEntryId() ==
						listTypeEntry.getListTypeEntryId()) {

					continue;
				}

				nextObjectStates.add(
					_objectStateLocalService.getObjectStateFlowObjectState(
						listTypeEntry1.getListTypeEntryId(),
						objectStateFlowId));
			}

			ObjectState sourceObjectState =
				_objectStateLocalService.getObjectStateFlowObjectState(
					listTypeEntry.getListTypeEntryId(), objectStateFlowId);

			Assert.assertEquals(
				nextObjectStates,
				_objectStateLocalService.getNextObjectStates(
					sourceObjectState.getObjectStateId()));
		}
	}

	@Test
	public void testDeleteObjectFieldObjectStateFlow() throws PortalException {
		ObjectStateFlow objectStateFlow =
			_objectStateFlowLocalService.addDefaultObjectStateFlow(
				_addObjectField(
					_listTypeDefinition.getListTypeDefinitionId(), true));

		Assert.assertNotNull(objectStateFlow);

		_objectStateFlowLocalService.deleteObjectFieldObjectStateFlow(
			objectStateFlow.getObjectFieldId());

		long objectStateFlowId = objectStateFlow.getObjectStateFlowId();

		try {
			_objectStateFlowLocalService.getObjectStateFlow(objectStateFlowId);

			Assert.fail();
		}
		catch (NoSuchObjectStateFlowException noSuchObjectStateFlowException) {
			Assert.assertEquals(
				"No ObjectStateFlow exists with the primary key " +
					objectStateFlowId,
				noSuchObjectStateFlowException.getMessage());
		}

		Assert.assertEquals(
			Collections.emptyList(),
			_objectStateLocalService.getObjectStateFlowObjectStates(
				objectStateFlowId));
	}

	@Test
	public void testGetObjectFieldObjectStateFlow() throws PortalException {
		ObjectField objectField = _addObjectField(
			_listTypeDefinition.getListTypeDefinitionId(), true);

		ObjectStateFlow objectStateFlow1 =
			_objectStateFlowLocalService.addDefaultObjectStateFlow(objectField);

		ObjectStateFlow objectStateFlow2 =
			_objectStateFlowLocalService.getObjectFieldObjectStateFlow(
				objectField.getObjectFieldId());

		Assert.assertEquals(objectStateFlow1, objectStateFlow2);
		Assert.assertEquals(
			objectField.getObjectFieldId(),
			objectStateFlow2.getObjectFieldId());
	}

	private ObjectField _addObjectField(
			long listTypeDefinitionId, boolean state)
		throws PortalException {

		ObjectFieldBuilder objectFieldBuilder = new ObjectFieldBuilder();

		return objectFieldBuilder.userId(
			TestPropsValues.getUserId()
		).listTypeDefinitionId(
			listTypeDefinitionId
		).state(
			state
		).build();
	}

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

}