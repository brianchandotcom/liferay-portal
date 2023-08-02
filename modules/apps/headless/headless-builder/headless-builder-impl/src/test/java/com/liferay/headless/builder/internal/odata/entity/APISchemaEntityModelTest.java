/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.odata.entity;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Carlos Correa
 */
public class APISchemaEntityModelTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetEntityFieldsMap() {
		Map<String, String> fieldMapping = HashMapBuilder.put(
			"propertyField1", "field3"
		).put(
			"propertyField2", "field4"
		).put(
			"propertyField3", "field5"
		).build();

		APISchemaEntityModel apiSchemaEntityModel = new APISchemaEntityModel(
			_getEntityModel(
				Arrays.asList("field1", "field2", "field3", "field4")),
			_getSchema(fieldMapping));

		Map<String, EntityField> entityFieldsMap =
			apiSchemaEntityModel.getEntityFieldsMap();

		Assert.assertEquals(
			entityFieldsMap.toString(), 2, entityFieldsMap.size());

		_assertEntityField(entityFieldsMap, fieldMapping, "propertyField1");
		_assertEntityField(entityFieldsMap, fieldMapping, "propertyField2");
	}

	@Test
	public void testGetEntityFieldsMapNonexistentMatch() {
		APISchemaEntityModel apiSchemaEntityModel = new APISchemaEntityModel(
			_getEntityModel(
				Arrays.asList("field1", "field2", "field3", "field4")),
			_getSchema(
				HashMapBuilder.put(
					"propertyField1", "field5"
				).put(
					"propertyField2", "field6"
				).build()));

		Map<String, EntityField> entityFieldsMap =
			apiSchemaEntityModel.getEntityFieldsMap();

		Assert.assertTrue(entityFieldsMap.isEmpty());
	}

	@Test
	public void testGetEntityFieldsMapNull() {
		APISchemaEntityModel apiSchemaEntityModel = new APISchemaEntityModel(
			_getEntityModel(null),
			_getSchema(
				HashMapBuilder.put(
					"propertyField1", "field5"
				).put(
					"propertyField2", "field6"
				).build()));

		Assert.assertNull(apiSchemaEntityModel.getEntityFieldsMap());
	}

	private void _assertEntityField(
		Map<String, EntityField> entityFieldsMap,
		Map<String, String> fieldMapping, String name) {

		APIPropertyEntityField apiPropertyEntityField =
			(APIPropertyEntityField)entityFieldsMap.get(name);

		Assert.assertEquals(
			fieldMapping.get(name), apiPropertyEntityField.getInternalName());
		Assert.assertEquals(name, apiPropertyEntityField.getName());
	}

	private EntityModel _getEntityModel(List<String> fieldNames) {
		EntityModelImpl entityModelImpl = new EntityModelImpl();

		if (fieldNames == null) {
			return entityModelImpl;
		}

		Map<String, EntityField> entityFieldsMap = new HashMap<>();

		for (String fieldName : fieldNames) {
			entityFieldsMap.put(
				fieldName,
				new EntityField(
					fieldName, EntityField.Type.STRING, locale -> null,
					locale -> null, object -> null));
		}

		entityModelImpl.setEntityFieldsMap(entityFieldsMap);

		return entityModelImpl;
	}

	private APIApplication.Schema _getSchema(Map<String, String> fieldMapping) {
		List<APIApplication.Property> properties = new ArrayList<>();

		for (Map.Entry<String, String> entry : fieldMapping.entrySet()) {
			properties.add(
				new APIApplication.Property() {

					@Override
					public String getDescription() {
						return RandomTestUtil.randomString();
					}

					@Override
					public String getExternalReferenceCode() {
						return RandomTestUtil.randomString();
					}

					@Override
					public String getName() {
						return entry.getKey();
					}

					@Override
					public List<String> getObjectRelationshipNames() {
						return Collections.emptyList();
					}

					@Override
					public String getSourceFieldName() {
						return entry.getValue();
					}

					@Override
					public Type getType() {
						return Type.TEXT;
					}

				});
		}

		return new APIApplication.Schema() {

			@Override
			public String getDescription() {
				return RandomTestUtil.randomString();
			}

			@Override
			public String getExternalReferenceCode() {
				return RandomTestUtil.randomString();
			}

			@Override
			public String getMainObjectDefinitionExternalReferenceCode() {
				return RandomTestUtil.randomString();
			}

			@Override
			public String getName() {
				return RandomTestUtil.randomString();
			}

			@Override
			public List<APIApplication.Property> getProperties() {
				return properties;
			}

		};
	}

	private static class EntityModelImpl implements EntityModel {

		@Override
		public Map<String, EntityField> getEntityFieldsMap() {
			return _entityFieldsMap;
		}

		public void setEntityFieldsMap(
			Map<String, EntityField> entityFieldsMap) {

			_entityFieldsMap = entityFieldsMap;
		}

		private Map<String, EntityField> _entityFieldsMap;

	}

}