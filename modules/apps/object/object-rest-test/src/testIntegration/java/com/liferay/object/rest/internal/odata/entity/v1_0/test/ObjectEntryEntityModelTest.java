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

package com.liferay.object.rest.internal.odata.entity.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.builder.ObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.resource.v1_0.ObjectEntryResource;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.CollectionEntityField;
import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IdEntityField;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Feliphe Marinho
 */
@FeatureFlags("LPS-154672")
@RunWith(Arquillian.class)
public class ObjectEntryEntityModelTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		for (ObjectRelationship objectRelationship : _objectRelationships) {
			_objectRelationshipLocalService.deleteObjectRelationship(
				objectRelationship);
		}

		for (ObjectDefinition objectDefinition : _objectDefinitions) {
			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition);
		}

		_serviceTrackerMap.close();
	}

	@Test
	public void testGetEntityFieldsMap() throws Exception {
		String value = "A" + RandomTestUtil.randomString();

		List<ObjectField> customObjectFields = Arrays.asList(
			_createObjectField(ObjectFieldConstants.DB_TYPE_BIG_DECIMAL),
			_createObjectField(ObjectFieldConstants.DB_TYPE_BOOLEAN),
			_createObjectField(ObjectFieldConstants.DB_TYPE_CLOB),
			_createObjectField(ObjectFieldConstants.DB_TYPE_DATE),
			_createObjectField(ObjectFieldConstants.DB_TYPE_DOUBLE),
			_createObjectField(ObjectFieldConstants.DB_TYPE_INTEGER),
			_createObjectField(ObjectFieldConstants.DB_TYPE_LONG),
			_createObjectField(ObjectFieldConstants.DB_TYPE_STRING));

		ObjectDefinition objectDefinition = _publishObjectDefinition(
			value, customObjectFields);

		ObjectDefinition relatedObjectDefinition = _publishObjectDefinition(
			"A" + RandomTestUtil.randomString(), customObjectFields);

		ObjectRelationship objectRelationship = _addObjectRelationship(
			objectDefinition, relatedObjectDefinition);

		_assertEquals(
			HashMapBuilder.<String, EntityField>put(
				"creator", new StringEntityField("creator", locale -> "creator")
			).put(
				"creatorId",
				new IntegerEntityField("creatorId", locale -> Field.USER_ID)
			).put(
				"dateCreated",
				new DateTimeEntityField(
					"dateCreated", locale -> Field.CREATE_DATE,
					locale -> Field.CREATE_DATE)
			).put(
				"dateModified",
				new DateTimeEntityField(
					"dateModified", locale -> "modifiedDate",
					locale -> "modifiedDate")
			).put(
				"externalReferenceCode",
				new StringEntityField(
					"externalReferenceCode", locale -> "externalReferenceCode")
			).put(
				"id", new IdEntityField("id", locale -> "id", String::valueOf)
			).put(
				"keywords",
				new CollectionEntityField(
					new StringEntityField(
						"keywords", locale -> "assetTagNames.raw"))
			).put(
				"objectDefinitionId",
				new IntegerEntityField(
					"objectDefinitionId", locale -> "objectDefinitionId")
			).put(
				"siteId",
				new IntegerEntityField("siteId", locale -> Field.GROUP_ID)
			).put(
				"status",
				new CollectionEntityField(
					new IntegerEntityField("status", locale -> Field.STATUS))
			).put(
				"taxonomyCategoryIds",
				new CollectionEntityField(
					new IntegerEntityField(
						"taxonomyCategoryIds", locale -> "assetCategoryIds"))
			).put(
				"userId",
				new IntegerEntityField("userId", locale -> Field.USER_ID)
			).putAll(
				_getExpectedEntityFieldsMap(
					customObjectFields, objectRelationship,
					relatedObjectDefinition)
			).build(),
			_getObjectDefinitionEntityFieldsMap(objectDefinition));
	}

	private ObjectRelationship _addObjectRelationship(
			ObjectDefinition objectDefinition,
			ObjectDefinition relatedObjectDefinition)
		throws Exception {

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				relatedObjectDefinition.getObjectDefinitionId(),
				objectDefinition.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_objectRelationships.add(objectRelationship);

		return objectRelationship;
	}

	private void _assertEquals(
		Map<String, EntityField> expectedEntityFieldsMap,
		Map<String, EntityField> actualEntityFieldsMap) {

		Assert.assertEquals(
			actualEntityFieldsMap.toString(), expectedEntityFieldsMap.size(),
			actualEntityFieldsMap.size());

		for (Map.Entry<String, EntityField> entry :
				expectedEntityFieldsMap.entrySet()) {

			EntityField expectedEntityField = entry.getValue();
			EntityField actualEntityField = actualEntityFieldsMap.get(
				entry.getKey());

			Assert.assertEquals(
				actualEntityFieldsMap.toString(), expectedEntityField.getName(),
				actualEntityField.getName());
			Assert.assertEquals(
				actualEntityField.toString(), expectedEntityField.getType(),
				actualEntityField.getType());
		}
	}

	private ObjectField _createObjectField(String dbType) {
		return new ObjectFieldBuilder(
		).dbType(
			dbType
		).labelMap(
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
		).name(
			"a" + RandomTestUtil.randomString()
		).objectFieldSettings(
			Collections.emptyList()
		).build();
	}

	private Map<String, EntityField> _getExpectedEntityFieldsMap(
		List<ObjectField> customObjectFields,
		ObjectRelationship objectRelationship,
		ObjectDefinition relatedObjectDefinition) {

		Map<String, EntityField> expectedEntityFieldsMap = new HashMap<>();

		for (ObjectField customObjectField : customObjectFields) {
			EntityField entityField = _toExpectedEntityField(customObjectField);

			expectedEntityFieldsMap.put(entityField.getName(), entityField);
		}

		String pkObjectFieldName =
			relatedObjectDefinition.getPKObjectFieldName();
		String relationshipEntityFieldPrefix = StringBundler.concat(
			"r_", objectRelationship.getName(), "_");

		String expectedObjectFieldName =
			relationshipEntityFieldPrefix + pkObjectFieldName;

		expectedEntityFieldsMap.put(
			expectedObjectFieldName,
			new IdEntityField(
				expectedObjectFieldName, locale -> expectedObjectFieldName,
				String::valueOf));

		String expectedObjectRelationshipERCObjectFieldName =
			relationshipEntityFieldPrefix +
				StringUtil.replaceLast(pkObjectFieldName, "Id", "ERC");

		expectedEntityFieldsMap.put(
			expectedObjectRelationshipERCObjectFieldName,
			new StringEntityField(
				expectedObjectRelationshipERCObjectFieldName,
				locale -> expectedObjectFieldName));

		String expectedRelatedObjectDefinitionIdObjectFieldName =
			pkObjectFieldName.replaceFirst("c_", "");

		expectedEntityFieldsMap.put(
			expectedRelatedObjectDefinitionIdObjectFieldName,
			new IdEntityField(
				expectedRelatedObjectDefinitionIdObjectFieldName,
				locale -> expectedObjectFieldName, String::valueOf));

		expectedEntityFieldsMap.put(
			objectRelationship.getName(),
			new ComplexEntityField(
				objectRelationship.getName(), Collections.emptyList()));

		return expectedEntityFieldsMap;
	}

	private Map<String, EntityField> _getObjectDefinitionEntityFieldsMap(
			ObjectDefinition objectDefinition)
		throws Exception {

		Map<String, EntityField> objectEntityFieldsMap = null;

		Bundle bundle = FrameworkUtil.getBundle(
			ObjectEntryEntityModelTest.class);

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundle.getBundleContext(), ObjectEntryResource.class,
			"entity.class.name");

		ObjectEntryResource objectEntryResource = _serviceTrackerMap.getService(
			StringBundler.concat(
				ObjectEntry.class.getName(), StringPool.POUND,
				objectDefinition.getOSGiJaxRsName()));

		if (objectEntryResource instanceof EntityModelResource) {
			Class<?> clazz = objectEntryResource.getClass();

			Method method = clazz.getMethod(
				"setObjectDefinition", ObjectDefinition.class);

			method.invoke(objectEntryResource, objectDefinition);

			EntityModelResource entityModelResource =
				(EntityModelResource)objectEntryResource;

			EntityModel entityModel = entityModelResource.getEntityModel(null);

			objectEntityFieldsMap = entityModel.getEntityFieldsMap();
		}

		return objectEntityFieldsMap;
	}

	private ObjectDefinition _publishObjectDefinition(
			String objectDefinitionName, List<ObjectField> objectFields)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), false, false,
				LocalizedMapUtil.getLocalizedMap(objectDefinitionName),
				objectDefinitionName, null, null,
				LocalizedMapUtil.getLocalizedMap(objectDefinitionName),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT, objectFields);

		objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId());

		_objectDefinitions.add(objectDefinition);

		return objectDefinition;
	}

	private EntityField _toExpectedEntityField(ObjectField objectField) {
		return new EntityField(
			objectField.getName(),
			_objectFieldDBTypeEntityFieldTypeMap.get(objectField.getDBType()),
			locale -> objectField.getName(), locale -> objectField.getName(),
			String::valueOf);
	}

	private static final Map<String, EntityField.Type>
		_objectFieldDBTypeEntityFieldTypeMap = HashMapBuilder.put(
			ObjectFieldConstants.DB_TYPE_BIG_DECIMAL, EntityField.Type.DOUBLE
		).put(
			ObjectFieldConstants.DB_TYPE_BOOLEAN, EntityField.Type.BOOLEAN
		).put(
			ObjectFieldConstants.DB_TYPE_CLOB, EntityField.Type.STRING
		).put(
			ObjectFieldConstants.DB_TYPE_DATE, EntityField.Type.DATE
		).put(
			ObjectFieldConstants.DB_TYPE_DOUBLE, EntityField.Type.DOUBLE
		).put(
			ObjectFieldConstants.DB_TYPE_INTEGER, EntityField.Type.INTEGER
		).put(
			ObjectFieldConstants.DB_TYPE_LONG, EntityField.Type.INTEGER
		).put(
			ObjectFieldConstants.DB_TYPE_STRING, EntityField.Type.STRING
		).build();

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private final List<ObjectDefinition> _objectDefinitions = new ArrayList<>();

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	private final List<ObjectRelationship> _objectRelationships =
		new ArrayList<>();
	private ServiceTrackerMap<String, ObjectEntryResource> _serviceTrackerMap;

}