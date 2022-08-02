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
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectState;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.model.ObjectStateTransition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.object.service.ObjectStateLocalService;
import com.liferay.object.service.ObjectStateTransitionLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class ObjectStateFlowLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

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

		_listTypeEntries = new ArrayList<>();
		_listTypeEntryMap = new HashMap<>();

		for (String key : Arrays.asList("step1", "step2", "step3")) {
			ListTypeEntry listTypeEntry =
				_listTypeEntryLocalService.addListTypeEntry(
					TestPropsValues.getUserId(),
					_listTypeDefinition.getListTypeDefinitionId(), key,
					LocalizedMapUtil.getLocalizedMap(key));

			_listTypeEntries.add(listTypeEntry);

			_listTypeEntryMap.put(key, listTypeEntry);
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

		ObjectField objectField = _objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(),
			_listTypeDefinition.getListTypeDefinitionId(),
			_objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_PICKLIST,
			ObjectFieldConstants.DB_TYPE_STRING, null, false, true, "",
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), true, true, Collections.emptyList());

		_objectStateFlow =
			_objectStateFlowLocalService.getObjectFieldObjectStateFlow(
				objectField.getObjectFieldId());
	}

	@After
	public void tearDown() throws PortalException {
		_objectDefinitionLocalService.deleteObjectDefinition(_objectDefinition);
	}

	@Test
	public void testAddDefaultObjectStateFlow() throws Exception {
		Assert.assertNull(
			_objectStateFlowLocalService.addDefaultObjectStateFlow(
				_addObjectField(0, false)));

		ObjectField objectField = _addObjectField(
			_listTypeDefinition.getListTypeDefinitionId(), true);

		ObjectStateFlow objectStateFlow =
			_objectStateFlowLocalService.addDefaultObjectStateFlow(objectField);

		Assert.assertEquals(
			TestPropsValues.getCompanyId(), objectStateFlow.getCompanyId());
		Assert.assertEquals(
			TestPropsValues.getUserId(), objectStateFlow.getUserId());
		Assert.assertEquals(
			objectField.getObjectFieldId(), objectStateFlow.getObjectFieldId());

		Assert.assertEquals(
			ListUtil.sort(
				ListUtil.toList(
					ListUtil.fromMapValues(_listTypeEntryMap),
					ListTypeEntry::getListTypeEntryId)),
			ListUtil.toList(
				_objectStateLocalService.getObjectStateFlowObjectStates(
					objectStateFlow.getObjectStateFlowId()),
				ObjectState::getListTypeEntryId));

		Map<Long, List<Long>> expectedDefaultObjectStateTransitions =
			_createExpectedDefaultObjectStateTransitions();

		for (Map.Entry<Long, List<Long>> entry :
				expectedDefaultObjectStateTransitions.entrySet()) {

			ObjectState objectState =
				_objectStateLocalService.getObjectStateFlowObjectState(
					entry.getKey(), objectStateFlow.getObjectStateFlowId());

			Assert.assertEquals(
				entry.getValue(),
				ListUtil.toList(
					_objectStateLocalService.getNextObjectStates(
						objectState.getObjectStateId()),
					ObjectState::getListTypeEntryId));
		}

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectField.getObjectFieldId(),
				ObjectFieldSettingConstants.NAME_STATE_FLOW);

		Assert.assertNotNull(objectFieldSetting);
		Assert.assertEquals(
			String.valueOf(objectStateFlow.getObjectStateFlowId()),
			objectFieldSetting.getValue());
	}

	@Test
	public void testAddObjectState() throws PortalException {
		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.addListTypeEntry(
				TestPropsValues.getUserId(),
				_listTypeDefinition.getListTypeDefinitionId(), "step4",
				LocalizedMapUtil.getLocalizedMap("step4"));

		Assert.assertNotNull(
			_objectStateLocalService.getObjectStateFlowObjectState(
				listTypeEntry.getListTypeEntryId(),
				_objectStateFlow.getObjectStateFlowId()));
	}

	@Test
	public void testDeleteListTypeEntryObjectStates() throws PortalException {
		ListTypeEntry listTypeEntry = _listTypeEntries.get(0);

		_listTypeEntryLocalService.deleteListTypeEntry(
			listTypeEntry.getListTypeEntryId());

		try {
			_objectStateLocalService.getObjectStateFlowObjectState(
				listTypeEntry.getListTypeEntryId(),
				_objectStateFlow.getObjectStateFlowId());

			Assert.fail();
		}
		catch (PortalException portalException) {
			Assert.assertEquals(
				portalException.getMessage(),
				StringBundler.concat(
					"No ObjectState exists with the key {listTypeEntryId=",
					listTypeEntry.getListTypeEntryId(), ", objectStateFlowId=",
					_objectStateFlow.getObjectStateFlowId(), "}"));
		}
	}

	@Test
	public void testDeleteObjectFieldObjectStateFlow() throws PortalException {
		ObjectField objectField = _addObjectField(
			_listTypeDefinition.getListTypeDefinitionId(), true);

		ObjectStateFlow objectStateFlow =
			_objectStateFlowLocalService.getObjectFieldObjectStateFlow(
				objectField.getObjectFieldId());

		Assert.assertNotNull(objectStateFlow);

		_objectStateFlowLocalService.deleteObjectFieldObjectStateFlow(
			objectStateFlow.getObjectFieldId());

		Assert.assertNull(
			_objectStateFlowLocalService.getObjectFieldObjectStateFlow(
				objectField.getObjectFieldId()));

		Assert.assertEquals(
			Collections.emptyList(),
			_objectStateLocalService.getObjectStateFlowObjectStates(
				objectStateFlow.getObjectStateFlowId()));

		Assert.assertEquals(
			Collections.emptyList(),
			_objectStateTransitionLocalService.
				getObjectStateFlowObjectStateTransitions(
					objectStateFlow.getObjectStateFlowId()));

		Assert.assertNull(
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectStateFlow.getObjectFieldId(),
				ObjectFieldSettingConstants.NAME_STATE_FLOW));
	}

	@Test
	public void testUpdateDefaultObjectStateFlow() throws PortalException {
		ObjectFieldBuilder objectFieldBuilder = new ObjectFieldBuilder();

		Assert.assertNull(
			_objectStateFlowLocalService.updateDefaultObjectStateFlow(
				objectFieldBuilder.build(), objectFieldBuilder.build()));

		ObjectField objectField1 = _addObjectField(
			_listTypeDefinition.getListTypeDefinitionId(), true);

		Assert.assertNotNull(
			_objectStateFlowLocalService.getObjectFieldObjectStateFlow(
				objectField1.getObjectFieldId()));

		Assert.assertNull(
			_objectStateFlowLocalService.updateDefaultObjectStateFlow(
				objectFieldBuilder.build(), objectField1));

		Assert.assertNull(
			_objectStateFlowLocalService.getObjectFieldObjectStateFlow(
				objectField1.getObjectFieldId()));

		ObjectField objectField2 = _addObjectField(
			_listTypeDefinition.getListTypeDefinitionId(), false);

		Assert.assertNull(
			_objectStateFlowLocalService.getObjectFieldObjectStateFlow(
				objectField2.getObjectFieldId()));

		Assert.assertNotNull(
			_objectStateFlowLocalService.updateDefaultObjectStateFlow(
				objectFieldBuilder.state(
					true
				).listTypeDefinitionId(
					_listTypeDefinition.getListTypeDefinitionId()
				).objectFieldId(
					objectField2.getObjectFieldId()
				).userId(
					TestPropsValues.getUserId()
				).build(),
				objectField2));

		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(
					RandomTestUtil.randomString()));

		for (String key : Arrays.asList("step4", "step5")) {
			_listTypeEntryLocalService.addListTypeEntry(
				TestPropsValues.getUserId(),
				listTypeDefinition.getListTypeDefinitionId(), key,
				LocalizedMapUtil.getLocalizedMap(key));
		}

		ObjectField objectField3 = _addObjectField(
			listTypeDefinition.getListTypeDefinitionId(), true);

		Assert.assertNotNull(
			_objectStateFlowLocalService.getObjectFieldObjectStateFlow(
				objectField3.getObjectFieldId()));

		ObjectStateFlow objectStateFlow =
			_objectStateFlowLocalService.updateDefaultObjectStateFlow(
				objectFieldBuilder.state(
					true
				).listTypeDefinitionId(
					listTypeDefinition.getListTypeDefinitionId()
				).objectFieldId(
					objectField3.getObjectFieldId()
				).userId(
					TestPropsValues.getUserId()
				).build(),
				objectField2);

		Assert.assertNotNull(objectStateFlow);

		List<ObjectState> objectStates =
			_objectStateLocalService.getObjectStateFlowObjectStates(
				objectStateFlow.getObjectStateFlowId());

		Assert.assertEquals(objectStates.toString(), 2, objectStates.size());
	}

	@Test
	public void testUpdateObjectStateTransitions() throws PortalException {
		List<ObjectState> objectStates =
			_objectStateLocalService.getObjectStateFlowObjectStates(
				_objectStateFlow.getObjectStateFlowId());

		for (ObjectState objectState : objectStates) {
			objectState.setObjectStateTransitions(
				_objectStateTransitionLocalService.
					getObjectStateObjectStateTransitions(
						objectState.getObjectStateId()));
		}

		_objectStateFlow.setObjectStates(objectStates);

		ObjectStateFlow newObjectStateFlow =
			(ObjectStateFlow)_objectStateFlow.clone();

		List<ObjectState> newObjectStates = new ArrayList<>(objectStates);

		for (ObjectState objectState : newObjectStates) {
			List<ObjectStateTransition> objectStateTransitions =
				_objectStateTransitionLocalService.
					getObjectStateObjectStateTransitions(
						objectState.getObjectStateId());

			objectState.setObjectStateTransitions(
				Collections.singletonList(objectStateTransitions.get(0)));
		}

		newObjectStateFlow.setObjectStates(newObjectStates);

		_objectStateTransitionLocalService.updateObjectStateTransitions(
			newObjectStateFlow);

		_assertEquals(
			newObjectStates,
			_objectStateLocalService.getObjectStateFlowObjectStates(
				_objectStateFlow.getObjectStateFlowId()));
	}

	private ObjectField _addObjectField(
			long listTypeDefinitionId, boolean state)
		throws PortalException {

		return _objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), listTypeDefinitionId,
			_objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_PICKLIST,
			ObjectFieldConstants.DB_TYPE_STRING, null, false, true, "",
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), true, state, Collections.emptyList());
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
				_objectStateTransitionLocalService.
					getObjectStateObjectStateTransitions(
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

	private Map<Long, List<Long>>
		_createExpectedDefaultObjectStateTransitions() {

		ListTypeEntry listTypeEntryStep1 = _listTypeEntryMap.get("step1");
		ListTypeEntry listTypeEntryStep2 = _listTypeEntryMap.get("step2");
		ListTypeEntry listTypeEntryStep3 = _listTypeEntryMap.get("step3");

		return HashMapBuilder.<Long, List<Long>>put(
			listTypeEntryStep1.getListTypeEntryId(),
			Arrays.asList(
				listTypeEntryStep2.getListTypeEntryId(),
				listTypeEntryStep3.getListTypeEntryId())
		).put(
			listTypeEntryStep2.getListTypeEntryId(),
			Arrays.asList(
				listTypeEntryStep1.getListTypeEntryId(),
				listTypeEntryStep3.getListTypeEntryId())
		).put(
			listTypeEntryStep3.getListTypeEntryId(),
			Arrays.asList(
				listTypeEntryStep1.getListTypeEntryId(),
				listTypeEntryStep2.getListTypeEntryId())
		).build();
	}

	@DeleteAfterTestRun
	private ListTypeDefinition _listTypeDefinition;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	private List<ListTypeEntry> _listTypeEntries;

	@Inject
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	private Map<String, ListTypeEntry> _listTypeEntryMap;
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	private ObjectStateFlow _objectStateFlow;

	@Inject
	private ObjectStateFlowLocalService _objectStateFlowLocalService;

	@Inject
	private ObjectStateLocalService _objectStateLocalService;

	@Inject
	private ObjectStateTransitionLocalService
		_objectStateTransitionLocalService;

}