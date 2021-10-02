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

package com.liferay.object.related.models.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.related.models.ObjectRelatedModelsProvider;
import com.liferay.object.related.models.ObjectRelatedModelsProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marco Leo
 */
@RunWith(Arquillian.class)
public class ObjectRelatedModelsProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_objectDefinition1 =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Collections.<ObjectField>emptyList());

		_objectDefinition1 =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_objectDefinition1.getObjectDefinitionId());

		_objectDefinition2 =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Collections.<ObjectField>emptyList());

		_objectDefinition2 =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_objectDefinition2.getObjectDefinitionId());
	}

	@Ignore
	@Test
	public void testObjectEntry1to1ObjectRelatedModelsProviderImpl()
		throws Exception {

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				_objectDefinition1.getObjectDefinitionId(),
				_objectDefinition2.getObjectDefinitionId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_ONE);

		ObjectRelatedModelsProvider<ObjectEntry> objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				_objectDefinition2.getClassName(),
				ObjectRelationshipConstants.TYPE_ONE_TO_ONE);

		Assert.assertNotNull(objectRelatedModelsProvider);

		ObjectEntry objectEntry1 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition1.getObjectDefinitionId(),
			Collections.<String, Serializable>emptyMap(),
			ServiceContextTestUtil.getServiceContext());

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectRelationship.getObjectFieldId2());

		ObjectEntry objectEntryA = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition2.getObjectDefinitionId(),
			Collections.<String, Serializable>emptyMap(),
			ServiceContextTestUtil.getServiceContext());

		List<ObjectEntry> objectEntries =
			objectRelatedModelsProvider.getRelatedModels(
				0, objectRelationship.getObjectRelationshipId(),
				objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 0, objectEntries.size());

		ObjectEntry objectEntryB = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition2.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), objectEntry1.getObjectEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());

		try {
			_objectEntryLocalService.addObjectEntry(
				TestPropsValues.getUserId(), 0,
				_objectDefinition2.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					objectField.getName(), objectEntry1.getObjectEntryId()
				).build(),
				ServiceContextTestUtil.getServiceContext());

			Assert.fail();
		}
		catch (ObjectEntryValuesException objectEntryValuesException) {
			Assert.assertTrue(
				StringUtil.startsWith(
					objectEntryValuesException.getMessage(),
					"One to one constraint violation for "));
		}

		try {
			_objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntryA.getObjectEntryId(),
				HashMapBuilder.<String, Serializable>put(
					objectField.getName(), objectEntry1.getObjectEntryId()
				).build(),
				ServiceContextTestUtil.getServiceContext());

			Assert.fail();
		}
		catch (ObjectEntryValuesException objectEntryValuesException) {
			Assert.assertTrue(
				StringUtil.startsWith(
					objectEntryValuesException.getMessage(),
					"One to one constraint violation for "));
		}

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntryB.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), objectEntry1.getObjectEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());
		Assert.assertEquals(
			objectEntries,
			_objectEntryLocalService.getOneToManyRelatedObjectEntries(
				0, objectRelationship.getObjectRelationshipId(),
				objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS));

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntryB.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), 0
			).build(),
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 0, objectEntries.size());

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);
	}

	@Test
	public void testObjectEntry1toMObjectRelatedModelsProviderImpl()
		throws Exception {

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				_objectDefinition1.getObjectDefinitionId(),
				_objectDefinition2.getObjectDefinitionId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectRelatedModelsProvider<ObjectEntry> objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				_objectDefinition2.getClassName(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		Assert.assertNotNull(objectRelatedModelsProvider);

		ObjectEntry objectEntry1 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition1.getObjectDefinitionId(),
			Collections.<String, Serializable>emptyMap(),
			ServiceContextTestUtil.getServiceContext());

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectRelationship.getObjectFieldId2());

		ObjectEntry objectEntryA = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition2.getObjectDefinitionId(),
			Collections.<String, Serializable>emptyMap(),
			ServiceContextTestUtil.getServiceContext());

		List<ObjectEntry> objectEntries =
			objectRelatedModelsProvider.getRelatedModels(
				0, objectRelationship.getObjectRelationshipId(),
				objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 0, objectEntries.size());

		ObjectEntry objectEntryB = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition2.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), objectEntry1.getObjectEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());

		_objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition2.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), objectEntry1.getObjectEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 2, objectEntries.size());

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntryA.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), objectEntry1.getObjectEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 3, objectEntries.size());

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntryB.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				objectField.getName(), 0
			).build(),
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 2, objectEntries.size());

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);
	}

	@Test
	public void testObjectEntryMtoMObjectRelatedModelsProviderImpl()
		throws Exception {

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				_objectDefinition1.getObjectDefinitionId(),
				_objectDefinition2.getObjectDefinitionId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		ObjectRelatedModelsProvider<ObjectEntry> objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				_objectDefinition2.getClassName(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		Assert.assertNotNull(objectRelatedModelsProvider);

		ObjectEntry objectEntry1 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition1.getObjectDefinitionId(),
			Collections.<String, Serializable>emptyMap(),
			ServiceContextTestUtil.getServiceContext());
		ObjectEntry objectEntry2 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition2.getObjectDefinitionId(),
			Collections.<String, Serializable>emptyMap(),
			ServiceContextTestUtil.getServiceContext());

		List<ObjectEntry> objectEntries =
			objectRelatedModelsProvider.getRelatedModels(
				0, objectRelationship.getObjectRelationshipId(),
				objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 0, objectEntries.size());

		_objectRelationshipLocalService.addObjectRelationshipMappingTableValues(
			TestPropsValues.getUserId(),
			objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), objectEntry2.getObjectEntryId(),
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());

		objectEntry2 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition2.getObjectDefinitionId(),
			Collections.<String, Serializable>emptyMap(),
			ServiceContextTestUtil.getServiceContext());

		_objectRelationshipLocalService.addObjectRelationshipMappingTableValues(
			TestPropsValues.getUserId(),
			objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), objectEntry2.getObjectEntryId(),
			ServiceContextTestUtil.getServiceContext());

		objectEntries = objectRelatedModelsProvider.getRelatedModels(
			0, objectRelationship.getObjectRelationshipId(),
			objectEntry1.getObjectEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 2, objectEntries.size());

		// TODO deleteObjectRelationshipMappingTableValues

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);
	}

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition2;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectRelatedModelsProviderRegistry
		_objectRelatedModelsProviderRegistry;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}