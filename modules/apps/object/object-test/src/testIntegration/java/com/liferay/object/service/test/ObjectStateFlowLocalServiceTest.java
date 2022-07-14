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
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectState;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.persistence.ObjectStateTransitionPersistence;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Selton Guedes
 */
@RunWith(Arquillian.class)
public class ObjectStateFlowLocalServiceTest
	extends BaseObjectStateLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.object.service"));

	@Test
	public void testAddDefaultObjectStateFlow() throws Exception {
		Assert.assertNull(
			objectStateFlowLocalService.addDefaultObjectStateFlow(
				_addObjectField(0, false)));

		ObjectField objectField = _addObjectField(
			listTypeDefinition.getListTypeDefinitionId(), true);

		ObjectStateFlow objectStateFlow =
			objectStateFlowLocalService.addDefaultObjectStateFlow(objectField);

		Assert.assertEquals(
			TestPropsValues.getCompanyId(), objectStateFlow.getCompanyId());
		Assert.assertEquals(
			TestPropsValues.getUserId(), objectStateFlow.getUserId());
		Assert.assertEquals(
			objectField.getObjectFieldId(), objectStateFlow.getObjectFieldId());

		Assert.assertEquals(
			ListUtil.sort(
				ListUtil.toList(
					ListUtil.fromMapValues(listTypeEntryMap),
					ListTypeEntry::getListTypeEntryId)),
			ListUtil.toList(
				objectStateLocalService.getObjectStateFlowObjectStates(
					objectStateFlow.getObjectStateFlowId()),
				ObjectState::getListTypeEntryId));

		Map<Long, List<Long>> expectedDefaultObjectStateTransitions =
			_createExpectedDefaultObjectStateTransitions();

		for (Map.Entry<Long, List<Long>> entry :
				expectedDefaultObjectStateTransitions.entrySet()) {

			ObjectState objectState =
				objectStateLocalService.getObjectStateFlowObjectState(
					entry.getKey(), objectStateFlow.getObjectStateFlowId());

			Assert.assertEquals(
				entry.getValue(),
				ListUtil.toList(
					objectStateLocalService.getNextObjectStates(
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
	public void testDeleteObjectFieldObjectStateFlow() throws PortalException {
		ObjectField objectField = _addObjectField(
			listTypeDefinition.getListTypeDefinitionId(), true);

		ObjectStateFlow objectStateFlow =
			objectStateFlowLocalService.getObjectFieldObjectStateFlow(
				objectField.getObjectFieldId());

		Assert.assertNotNull(objectStateFlow);

		objectStateFlowLocalService.deleteObjectFieldObjectStateFlow(
			objectStateFlow.getObjectFieldId());

		Assert.assertNull(
			objectStateFlowLocalService.getObjectFieldObjectStateFlow(
				objectField.getObjectFieldId()));

		Assert.assertEquals(
			Collections.emptyList(),
			objectStateLocalService.getObjectStateFlowObjectStates(
				objectStateFlow.getObjectStateFlowId()));

		Assert.assertEquals(
			Collections.emptyList(),
			_objectStateTransitionPersistence.findByObjectStateFlowId(
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
			objectStateFlowLocalService.updateDefaultObjectStateFlow(
				objectFieldBuilder.build(), objectFieldBuilder.build()));

		ObjectField objectField1 = _addObjectField(
			listTypeDefinition.getListTypeDefinitionId(), true);

		Assert.assertNotNull(
			objectStateFlowLocalService.getObjectFieldObjectStateFlow(
				objectField1.getObjectFieldId()));

		Assert.assertNull(
			objectStateFlowLocalService.updateDefaultObjectStateFlow(
				objectFieldBuilder.build(), objectField1));

		Assert.assertNull(
			objectStateFlowLocalService.getObjectFieldObjectStateFlow(
				objectField1.getObjectFieldId()));

		ObjectField objectField2 = _addObjectField(
			listTypeDefinition.getListTypeDefinitionId(), false);

		Assert.assertNull(
			objectStateFlowLocalService.getObjectFieldObjectStateFlow(
				objectField2.getObjectFieldId()));

		Assert.assertNotNull(
			objectStateFlowLocalService.updateDefaultObjectStateFlow(
				objectFieldBuilder.state(
					true
				).listTypeDefinitionId(
					listTypeDefinition.getListTypeDefinitionId()
				).objectFieldId(
					objectField2.getObjectFieldId()
				).userId(
					TestPropsValues.getUserId()
				).build(),
				objectField2));

		ListTypeDefinition listTypeDefinition =
			listTypeDefinitionLocalService.addListTypeDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(
					RandomTestUtil.randomString()));

		for (String key : Arrays.asList("step4", "step5")) {
			listTypeEntryLocalService.addListTypeEntry(
				TestPropsValues.getUserId(),
				listTypeDefinition.getListTypeDefinitionId(), key,
				LocalizedMapUtil.getLocalizedMap(key));
		}

		ObjectField objectField3 = _addObjectField(
			listTypeDefinition.getListTypeDefinitionId(), true);

		Assert.assertNotNull(
			objectStateFlowLocalService.getObjectFieldObjectStateFlow(
				objectField3.getObjectFieldId()));

		ObjectStateFlow objectStateFlow =
			objectStateFlowLocalService.updateDefaultObjectStateFlow(
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
			objectStateLocalService.getObjectStateFlowObjectStates(
				objectStateFlow.getObjectStateFlowId());

		Assert.assertEquals(objectStates.toString(), 2, objectStates.size());
	}

	private ObjectField _addObjectField(
			long listTypeDefinitionId, boolean state)
		throws PortalException {

		return objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), listTypeDefinitionId,
			objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_PICKLIST,
			ObjectFieldConstants.DB_TYPE_STRING, null, false, true, "",
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), true, state, Collections.emptyList());
	}

	private Map<Long, List<Long>>
		_createExpectedDefaultObjectStateTransitions() {

		ListTypeEntry listTypeEntryStep1 = listTypeEntryMap.get("step1");
		ListTypeEntry listTypeEntryStep2 = listTypeEntryMap.get("step2");
		ListTypeEntry listTypeEntryStep3 = listTypeEntryMap.get("step3");

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

	@Inject
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Inject
	private ObjectStateTransitionPersistence _objectStateTransitionPersistence;

}