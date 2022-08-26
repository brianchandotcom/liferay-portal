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
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.odata.entity.v1_0.ObjectEntryEntityModel;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.odata.entity.CollectionEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.IdEntityField;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@RunWith(Arquillian.class)
public class ObjectEntryEntityModelTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-158821", "true"
			).build());
	}

	@AfterClass
	public static void tearDownClass() {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-158821", "false"
			).build());
	}

	@Test
	public void testGetEntityFieldsMap() throws PortalException {
		String value = "A" + RandomTestUtil.randomString();

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(value), value, null, null,
				LocalizedMapUtil.getLocalizedMap(value),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.emptyList());

		List<ObjectField> customObjectFields = Arrays.asList(
			new ObjectFieldBuilder().businessType(
				ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT
			).name(
				"attachmentObjectFieldName"
			).build(),
			_createObjectField(ObjectFieldConstants.DB_TYPE_BIG_DECIMAL),
			_createObjectField(ObjectFieldConstants.DB_TYPE_BOOLEAN),
			_createObjectField(ObjectFieldConstants.DB_TYPE_CLOB),
			_createObjectField(ObjectFieldConstants.DB_TYPE_DATE),
			_createObjectField(ObjectFieldConstants.DB_TYPE_DOUBLE),
			_createObjectField(ObjectFieldConstants.DB_TYPE_INTEGER),
			_createObjectField(ObjectFieldConstants.DB_TYPE_LONG),
			new ObjectFieldBuilder().relationshipType(
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY
			).name(
				"oneToManyObjectFieldName"
			).build(),
			_createObjectField(ObjectFieldConstants.DB_TYPE_STRING));

		_expectedEntityFieldsMap.putAll(
			Stream.of(
				customObjectFields
			).flatMap(
				List::stream
			).map(
				ObjectEntryEntityModelTest::_toExpectedEntityField
			).collect(
				Collectors.toMap(EntityField::getName, Function.identity())
			));

		ObjectEntryEntityModel objectEntryEntityModel =
			new ObjectEntryEntityModel(
				ListUtil.concat(
					_objectFieldLocalService.getObjectFields(
						objectDefinition.getObjectDefinitionId()),
					customObjectFields));

		_assertEquals(
			_expectedEntityFieldsMap,
			objectEntryEntityModel.getEntityFieldsMap());
	}

	private static EntityField _toExpectedEntityField(ObjectField objectField) {
		if (Objects.equals(
				ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
				objectField.getBusinessType())) {

			return new StringEntityField(
				objectField.getName(), locale -> objectField.getName());
		}

		if (Objects.equals(
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY,
				objectField.getRelationshipType())) {

			String objectFieldName = objectField.getName();

			return new IdEntityField(
				objectFieldName.substring(
					objectFieldName.lastIndexOf(StringPool.UNDERLINE) + 1),
				locale -> objectFieldName, String::valueOf);
		}

		return new EntityField(
			objectField.getName(),
			_objectFieldDBTypeEntityFieldTypeMap.get(objectField.getDBType()),
			locale -> objectField.getName(), locale -> objectField.getName(),
			String::valueOf);
	}

	private void _assertEquals(
		Map<String, EntityField> expectedEntityFieldsMap,
		Map<String, EntityField> actualEntityFieldsMap) {

		Assert.assertEquals(
			actualEntityFieldsMap.toString(), _expectedEntityFieldsMap.size(),
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
		return new ObjectFieldBuilder().dbType(
			dbType
		).name(
			StringUtil.lowerCaseFirstLetter(dbType) + "ObjectFieldName"
		).build();
	}

	private static final Map<String, EntityField> _expectedEntityFieldsMap =
		HashMapBuilder.create(
			HashMapBuilder.<String, EntityField>put(
				"creator",
				new StringEntityField("creator", locale -> Field.USER_NAME)
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
				"id",
				new IdEntityField(
					"id",
					locale -> Field.getSortableFieldName(Field.ENTRY_CLASS_PK),
					String::valueOf)
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
				"userId",
				new IntegerEntityField("userId", locale -> Field.USER_ID)
			).build()
		).build();
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

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

}