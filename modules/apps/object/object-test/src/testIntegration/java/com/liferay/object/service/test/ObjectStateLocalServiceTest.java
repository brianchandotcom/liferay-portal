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
import com.liferay.object.exception.NoSuchObjectStateException;
import com.liferay.object.model.ObjectState;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.object.service.ObjectStateLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
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
public class ObjectStateLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-152677", "true"
			).build());

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

	@After
	public void tearDown() {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-152677", "false"
			).build());
	}

	@Test
	public void testAddObjectState() throws PortalException {
		long listTypeEntry = RandomTestUtil.randomLong();
		long objectStateFlowId = RandomTestUtil.randomLong();

		ObjectState objectState = _testAddObjectState(
			listTypeEntry, objectStateFlowId, TestPropsValues.getUserId());

		Assert.assertEquals(
			TestPropsValues.getUserId(), objectState.getUserId());
		Assert.assertEquals(
			TestPropsValues.getCompanyId(), objectState.getCompanyId());
		Assert.assertEquals(listTypeEntry, objectState.getListTypeEntryId());
		Assert.assertEquals(
			objectStateFlowId, objectState.getObjectStateFlowId());
	}

	@Test
	public void testDeleteListTypeEntryObjectStates() throws PortalException {
		ObjectState objectState = _testAddObjectState(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			TestPropsValues.getUserId());

		Assert.assertNotNull(objectState);

		_objectStateLocalService.deleteListTypeEntryObjectStates(
			objectState.getListTypeEntryId());

		try {
			_objectStateLocalService.getObjectState(
				objectState.getObjectStateId());

			Assert.fail();
		}
		catch (NoSuchObjectStateException noSuchObjectStateException) {
			Assert.assertEquals(
				"No ObjectState exists with the primary key " +
					objectState.getObjectStateId(),
				noSuchObjectStateException.getMessage());
		}
	}

	@Test
	public void testDeleteObjectStateFlowObjectStates() throws PortalException {
		ObjectStateFlow objectStateFlow = _addDefaultObjectStateFlow();

		_objectStateLocalService.deleteObjectStateFlowObjectStates(
			objectStateFlow.getObjectStateFlowId());

		for (ListTypeEntry listTypeEntry : _listTypeEntries) {
			try {
				_objectStateLocalService.getObjectStateFlowObjectState(
					listTypeEntry.getListTypeEntryId(),
					objectStateFlow.getObjectStateFlowId());

				Assert.fail();
			}
			catch (NoSuchObjectStateException noSuchObjectStateException) {
				Assert.assertEquals(
					StringBundler.concat(
						"No ObjectState exists with the key {listTypeEntryId=",
						listTypeEntry.getListTypeEntryId(),
						", objectStateFlowId=",
						objectStateFlow.getObjectStateFlowId(), "}"),
					noSuchObjectStateException.getMessage());
			}
		}
	}

	@Test
	public void testGetNextObjectStates() throws PortalException {
		ObjectStateFlow objectStateFlow = _addDefaultObjectStateFlow();

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
						objectStateFlow.getObjectStateFlowId()));
			}

			ObjectState sourceObjectState =
				_objectStateLocalService.getObjectStateFlowObjectState(
					listTypeEntry.getListTypeEntryId(),
					objectStateFlow.getObjectStateFlowId());

			Assert.assertEquals(
				nextObjectStates,
				_objectStateLocalService.getNextObjectStates(
					sourceObjectState.getObjectStateId()));
		}
	}

	@Test
	public void testGetObjectStateFlowObjectState() throws PortalException {
		long listTypeEntryId = RandomTestUtil.randomLong();
		long objectStateFlowId = RandomTestUtil.randomLong();

		ObjectState objectState = _testAddObjectState(
			listTypeEntryId, objectStateFlowId, TestPropsValues.getUserId());

		Assert.assertEquals(
			objectState,
			_objectStateLocalService.getObjectStateFlowObjectState(
				listTypeEntryId, objectStateFlowId));
	}

	@Test
	public void testGetObjectStateFlowObjectStates() throws PortalException {
		ObjectStateFlow objectStateFlow = _addDefaultObjectStateFlow();

		List<ObjectState> objectStates = new ArrayList<>();

		for (ListTypeEntry listTypeEntry : _listTypeEntries) {
			objectStates.add(
				_objectStateLocalService.getObjectStateFlowObjectState(
					listTypeEntry.getListTypeEntryId(),
					objectStateFlow.getObjectStateFlowId()));
		}

		Assert.assertEquals(
			objectStates,
			_objectStateLocalService.getObjectStateFlowObjectStates(
				objectStateFlow.getObjectStateFlowId()));
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

	private ObjectState _testAddObjectState(
			long listTypeEntryId, long objectStateFlowId, long userId)
		throws PortalException {

		return _objectStateLocalService.addObjectState(
			userId, listTypeEntryId, objectStateFlowId);
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