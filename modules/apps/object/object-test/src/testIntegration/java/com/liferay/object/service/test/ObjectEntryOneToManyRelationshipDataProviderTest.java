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
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.data.provider.RelationshipDataProvider;
import com.liferay.object.data.provider.RelationshipDataProviderRegistry;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.object.service.ObjectRelationshipLocalServiceUtil;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marco Leo
 */
@RunWith(Arquillian.class)
public class ObjectEntryOneToManyRelationshipDataProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_objectDefinition1 =
			ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap("Left"), "Left", null, null,
				LocalizedMapUtil.getLocalizedMap("Left"),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						true, false, "Random", "random", false, "String")));

		_objectDefinition1 =
			ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_objectDefinition1.getObjectDefinitionId());

		_objectDefinition2 =
			ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap("Right"), "Right", null, null,
				LocalizedMapUtil.getLocalizedMap("Right"),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						true, false, "Random", "random", false, "String")));

		_objectDefinition2 =
			ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_objectDefinition2.getObjectDefinitionId());

		_objectRelationship =
			ObjectRelationshipLocalServiceUtil.addObjectRelationship(
				TestPropsValues.getUserId(),
				_objectDefinition1.getObjectDefinitionId(),
				_objectDefinition2.getObjectDefinitionId(),
				LocalizedMapUtil.getLocalizedMap("onetomany"), "onetomany",
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
	}

	@Test
	public void testCustomObjectsOneToManyRelationship() throws Exception {
		RelationshipDataProvider<ObjectEntry> relationshipDataProvider =
			_relationshipDataProviderRegistry.getRelationshipDataProvider(
				_objectDefinition2.getClassName(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		Assert.assertNotNull(relationshipDataProvider);

		ObjectEntry leftObjectEntry =
			ObjectEntryLocalServiceUtil.addObjectEntry(
				TestPropsValues.getUserId(), 0,
				_objectDefinition1.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"random", RandomTestUtil.randomString()
				).build(),
				ServiceContextTestUtil.getServiceContext());

		ObjectField objectField = ObjectFieldLocalServiceUtil.getObjectField(
			_objectRelationship.getObjectFieldId2());

		ObjectEntryLocalServiceUtil.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition2.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"random", RandomTestUtil.randomString()
			).put(
				objectField.getName(), leftObjectEntry.getObjectEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		List<ObjectEntry> relatedEntities =
			relationshipDataProvider.getRelatedEntities(
				0, leftObjectEntry.getObjectEntryId(),
				_objectRelationship.getObjectRelationshipId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			relatedEntities.toString(), 1, relatedEntities.size());

		ObjectEntryLocalServiceUtil.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition2.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"random", RandomTestUtil.randomString()
			).put(
				objectField.getName(), leftObjectEntry.getObjectEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		relatedEntities = relationshipDataProvider.getRelatedEntities(
			0, leftObjectEntry.getObjectEntryId(),
			_objectRelationship.getObjectRelationshipId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(
			relatedEntities.toString(), 2, relatedEntities.size());

		ObjectEntryLocalServiceUtil.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition2.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"random", RandomTestUtil.randomString()
			).put(
				objectField.getName(), 0
			).build(),
			ServiceContextTestUtil.getServiceContext());

		relatedEntities = relationshipDataProvider.getRelatedEntities(
			0, leftObjectEntry.getObjectEntryId(),
			_objectRelationship.getObjectRelationshipId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(
			relatedEntities.toString(), 2, relatedEntities.size());
	}

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition2;

	@DeleteAfterTestRun
	private ObjectRelationship _objectRelationship;

	@Inject
	private RelationshipDataProviderRegistry _relationshipDataProviderRegistry;

}