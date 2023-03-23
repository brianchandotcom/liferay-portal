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

package com.liferay.object.rest.internal.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.admin.taxonomy.client.resource.v1_0.TaxonomyCategoryResource;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.internal.resource.v1_0.test.util.HTTPTestUtil;
import com.liferay.object.rest.internal.resource.v1_0.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.rest.internal.resource.v1_0.test.util.ObjectEntryTestUtil;
import com.liferay.object.rest.internal.resource.v1_0.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;

import org.hamcrest.CoreMatchers;

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
 * @author Luis Miguel Barcos
 */
@RunWith(Arquillian.class)
public class ObjectEntryResourceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-153117", "true"
			).build());
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-154672", "true"
			).build());
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-164801", "true"
			).build());
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-176651", "true"
			).build());

		TaxonomyCategoryResource.Builder builder =
			TaxonomyCategoryResource.builder();

		_taxonomyCategoryResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();

		_assetVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(
			UserLocalServiceUtil.getDefaultUserId(
				TestPropsValues.getCompanyId()),
			TestPropsValues.getGroupId(), RandomTestUtil.randomString(),
			new ServiceContext());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-153117", "false"
			).build());
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-154672", "false"
			).build());
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-164801", "false"
			).build());
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-176651", "false"
			).build());
	}

	@Before
	public void setUp() throws Exception {
		_objectDefinition1 = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					"Text", "String", true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_1,
					false)));

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1);

		_objectDefinition2 = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					"Text", "String", true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_2,
					false)));

		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2);

		_objectDefinition3 = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					"Text", "String", true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_3,
					false)));

		_objectEntry3 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition3, _OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3);

		_siteScopedObjectDefinition1 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", true, true, null,
						RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_1,
						false)),
				ObjectDefinitionConstants.SCOPE_SITE);

		_siteScopedObjectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_siteScopedObjectDefinition1, _OBJECT_FIELD_NAME_1,
			_OBJECT_FIELD_VALUE_1);
	}

	@After
	public void tearDown() throws Exception {
		if (_objectRelationship1 != null) {
			_objectRelationshipLocalService.deleteObjectRelationship(
				_objectRelationship1);
		}

		if (_objectRelationship2 != null) {
			_objectRelationshipLocalService.deleteObjectRelationship(
				_objectRelationship2);
		}

		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition1);
		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition2);
		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition3);
		_objectDefinitionLocalService.deleteObjectDefinition(
			_siteScopedObjectDefinition1);
	}

	@Test
	public void testFilterByComparisonOperatorsObjectEntriesByRelatesObjectEntriesFields()
		throws Exception {

		// 1 to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s eq '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s ge '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s gt '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s le '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s lt '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 + 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s ne '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 - 1)),
			_objectDefinition1);

		// 1 to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id eq '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id ge '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id gt '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id le '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id lt '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() + 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id ne '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() - 1)),
			_objectDefinition1);

		// 1 to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s eq '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s ge '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s gt '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s le '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s lt '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s ne '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition2);

		// 1 to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id eq '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id ge '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id gt '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id le '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id lt '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() + 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id ne '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition2);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);

		// Many to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s eq '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s ge '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s gt '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s le '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s lt '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 + 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s ne '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 - 1)),
			_objectDefinition1);

		// Many to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id eq '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id ge '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id gt '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id le '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id lt '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() + 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id ne '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() - 1)),
			_objectDefinition1);

		// Many to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s eq '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s ge '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s gt '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s le '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s lt '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s ne '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition2);

		// Many to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id eq '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id ge '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id gt '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id le '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id lt '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() + 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id ne '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition2);
	}

	@Test
	public void testFilterByComparisonOperatorsObjectEntriesByRelatesObjectEntriesFieldsThroughMultipleRelationships()
		throws Exception {

		// 1 to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectsEntries(
			_objectDefinition1, _objectDefinition2, _objectEntry1,
			_objectEntry2, ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_objectRelationship2 = _addObjectRelationshipAndRelateObjectsEntries(
			_objectDefinition2, _objectDefinition3, _objectEntry2,
			_objectEntry3, ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s eq '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s ge '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s gt '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s le '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s lt '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 + 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s ne '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 - 1)),
			_objectDefinition1);

		// 1 to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id eq '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id ge '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id gt '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id le '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id lt '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() + 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id ne '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() - 1)),
			_objectDefinition1);

		// 1 to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s eq '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s ge '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s gt '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s le '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s lt '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s ne '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition3);

		// 1 to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id eq '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id ge '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id gt '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id le '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id lt '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() + 1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id ne '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition3);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);
		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship2);

		// Many to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectsEntries(
			_objectDefinition1, _objectDefinition2, _objectEntry1,
			_objectEntry2, ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_objectRelationship2 = _addObjectRelationshipAndRelateObjectsEntries(
			_objectDefinition2, _objectDefinition3, _objectEntry2,
			_objectEntry3, ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s eq '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s ge '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s gt '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s le '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s lt '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 + 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s ne '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 - 1)),
			_objectDefinition1);

		// Many to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id eq '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id ge '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id gt '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id le '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id lt '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() + 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id ne '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() - 1)),
			_objectDefinition1);

		// Many to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s eq '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s ge '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s gt '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s le '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s lt '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s ne '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition3);

		// Many to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id eq '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id ge '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id gt '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id le '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id lt '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() + 1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id ne '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition3);
	}

	@Test
	public void testFilterByListOperatorsObjectEntriesByRelatesObjectEntriesFields()
		throws Exception {

		// 1 to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s in ('%s', '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
					RandomTestUtil.randomInt())),
			_objectDefinition1);

		// 1 to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id in ('%s', '%s')", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId(),
					RandomTestUtil.randomInt())),
			_objectDefinition1);

		// 1 to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s in ('%s', '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
					RandomTestUtil.randomInt())),
			_objectDefinition2);

		// 1 to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id in ('%s', '%s')", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId(),
					RandomTestUtil.randomInt())),
			_objectDefinition2);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);

		// Many to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s in ('%s', '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
					RandomTestUtil.randomInt())),
			_objectDefinition1);

		// Many to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id in ('%s', '%s')", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId(),
					RandomTestUtil.randomInt())),
			_objectDefinition1);

		// Many to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s in ('%s', '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
					RandomTestUtil.randomInt())),
			_objectDefinition2);

		// Many to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id in ('%s', '%s')", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId(),
					RandomTestUtil.randomInt())),
			_objectDefinition2);
	}

	@Test
	public void testFilterByLogicalOperatorsObjectEntriesByRelatesObjectEntriesFields()
		throws Exception {

		// 1 to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s le '%s' and %s/%s gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_2,
					_OBJECT_FIELD_VALUE_2, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s le '%s' or %s/%s gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_2,
					_OBJECT_FIELD_VALUE_2, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"not (%s/%s ge '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 + 1)),
			_objectDefinition1);

		// 1 to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id le '%s' and %s/id gt '%s'",
					_objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId(),
					_objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id le '%s' or %s/id gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_VALUE_2,
					_objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"not (%s/id ge '%s')", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() + 1)),
			_objectDefinition1);

		// 1 to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s le '%s' and %s/%s gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s le '%s' or %s/%s gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"not (%s/%s ge '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition2);

		// 1 to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id le '%s' and %s/id gt '%s'",
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id le '%s' or %s/id gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_VALUE_1,
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"not (%s/id ge '%s')", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() + 1)),
			_objectDefinition2);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);

		// Many to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s le '%s' and %s/%s gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_2,
					_OBJECT_FIELD_VALUE_2, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s le '%s' or %s/%s gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_2,
					_OBJECT_FIELD_VALUE_2, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"not (%s/%s ge '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 + 1)),
			_objectDefinition1);

		// Many to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id le '%s' and %s/id gt '%s'",
					_objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId(),
					_objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id le '%s' or %s/id gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_VALUE_2,
					_objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"not (%s/id ge '%s')", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() + 1)),
			_objectDefinition1);

		// Many to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s le '%s' and %s/%s gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s le '%s' or %s/%s gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"not (%s/%s ge '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition2);

		// Many to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id le '%s' and %s/id gt '%s'",
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id le '%s' or %s/id gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_VALUE_1,
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"not (%s/id ge '%s')", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() + 1)),
			_objectDefinition2);
	}

	@Test
	public void testFilterByLogicalOperatorsObjectEntriesByRelatesObjectEntriesFieldsThroughMultipleRelationships()
		throws Exception {

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectsEntries(
			_objectDefinition1, _objectDefinition2, _objectEntry1,
			_objectEntry2, ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_objectRelationship2 = _addObjectRelationshipAndRelateObjectsEntries(
			_objectDefinition2, _objectDefinition3, _objectEntry2,
			_objectEntry3, ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		// 1 to many relationship, custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s le '%s' and %s/%s/%s gt '%s'",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3, _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 - 1)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s le '%s' or %s/%s/%s gt '%s'",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3, _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 - 1)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"not (%s/%s/%s ge '%s')", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 + 1)),
			_objectDefinition1);

		// 1 to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id le '%s' and %s/%s/id gt '%s'",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId(),
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() - 1)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id le '%s' or %s/%s/id gt '%s'",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId(),
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() - 1)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"not (%s/%s/id ge '%s')", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() + 1)),
			_objectDefinition1);

		// 1 to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s le '%s' and %s/%s/%s gt '%s'",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s le '%s' or %s/%s/%s gt '%s'",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"not (%s/%s/%s ge '%s')", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition3);

		// 1 to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id le '%s' and %s/%s/id gt '%s'",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId(),
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id le '%s' or %s/%s/id gt '%s'",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId(),
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"not (%s/%s/id ge '%s')", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() + 1)),
			_objectDefinition3);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);
		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship2);

		// Many to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectsEntries(
			_objectDefinition1, _objectDefinition2, _objectEntry1,
			_objectEntry2, ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_objectRelationship2 = _addObjectRelationshipAndRelateObjectsEntries(
			_objectDefinition2, _objectDefinition3, _objectEntry2,
			_objectEntry3, ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s le '%s' and %s/%s/%s gt '%s'",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3, _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 - 1)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s le '%s' or %s/%s/%s gt '%s'",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3, _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 - 1)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"not (%s/%s/%s ge '%s')", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 + 1)),
			_objectDefinition1);

		// Many to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id le '%s' and %s/%s/id gt '%s'",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId(),
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() - 1)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id le '%s' or %s/%s/id gt '%s'",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId(),
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() - 1)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"not (%s/%s/id ge '%s')", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() + 1)),
			_objectDefinition1);

		// Many to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s le '%s' and %s/%s/%s gt '%s'",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s le '%s' or %s/%s/%s gt '%s'",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"not (%s/%s/%s ge '%s')", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition3);

		// Many to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id le '%s' and %s/%s/id gt '%s'",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId(),
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id le '%s' or %s/%s/id gt '%s'",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId(),
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"not (%s/%s/id ge '%s')", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() + 1)),
			_objectDefinition3);
	}

	@Test
	public void testFilterByStringOperatorsObjectEntriesByRelatesObjectEntriesFields()
		throws Exception {

		// 1 to many relationship, custom object field

		String objectFieldValue1 = String.valueOf(_OBJECT_FIELD_VALUE_1);

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, objectFieldValue1);

		String objectFieldValue2 = String.valueOf(_OBJECT_FIELD_VALUE_2);

		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2, objectFieldValue2);

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"contains(%s/%s,'%s')", _objectRelationship1.getName(),
				_OBJECT_FIELD_NAME_2, objectFieldValue2.substring(1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"startswith(%s/%s,'%s')", _objectRelationship1.getName(),
				_OBJECT_FIELD_NAME_2, objectFieldValue2.substring(0, 2)),
			_objectDefinition1);

		// 1 to many relationship, system object field

		String objectEntry2ExternalReferenceCode =
			_objectEntry2.getExternalReferenceCode();

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"contains(%s/externalReferenceCode,'%s')",
				_objectRelationship1.getName(),
				objectEntry2ExternalReferenceCode.substring(1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"startswith(%s/externalReferenceCode,'%s')",
				_objectRelationship1.getName(),
				objectEntry2ExternalReferenceCode.substring(0, 2)),
			_objectDefinition1);

		// 1 to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			String.format(
				"contains(%s/%s,'%s')", _objectRelationship1.getName(),
				_OBJECT_FIELD_NAME_1, objectFieldValue1.substring(1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			String.format(
				"startswith(%s/%s,'%s')", _objectRelationship1.getName(),
				_OBJECT_FIELD_NAME_1, objectFieldValue1.substring(0, 2)),
			_objectDefinition2);

		// 1 to many relationship (other side), system object field

		String objectEntry1ExternalReferenceCode =
			_objectEntry1.getExternalReferenceCode();

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			String.format(
				"contains(%s/externalReferenceCode,'%s')",
				_objectRelationship1.getName(),
				objectEntry1ExternalReferenceCode.substring(1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			String.format(
				"startswith(%s/externalReferenceCode,'%s')",
				_objectRelationship1.getName(),
				objectEntry1ExternalReferenceCode.substring(0, 2)),
			_objectDefinition2);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);

		// Many to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"contains(%s/%s,'%s')", _objectRelationship1.getName(),
				_OBJECT_FIELD_NAME_2, objectFieldValue2.substring(1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"startswith(%s/%s,'%s')", _objectRelationship1.getName(),
				_OBJECT_FIELD_NAME_2, objectFieldValue2.substring(0, 2)),
			_objectDefinition1);

		// Many to many relationship, system object field

		objectEntry2ExternalReferenceCode =
			_objectEntry2.getExternalReferenceCode();

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"contains(%s/externalReferenceCode,'%s')",
				_objectRelationship1.getName(),
				objectEntry2ExternalReferenceCode.substring(1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"startswith(%s/externalReferenceCode,'%s')",
				_objectRelationship1.getName(),
				objectEntry2ExternalReferenceCode.substring(0, 2)),
			_objectDefinition1);

		// Many to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			String.format(
				"contains(%s/%s,'%s')", _objectRelationship1.getName(),
				_OBJECT_FIELD_NAME_1, objectFieldValue1.substring(1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			String.format(
				"startswith(%s/%s,'%s')", _objectRelationship1.getName(),
				_OBJECT_FIELD_NAME_1, objectFieldValue1.substring(0, 2)),
			_objectDefinition2);

		// Many to many relationship (other side), system object field

		objectEntry1ExternalReferenceCode =
			_objectEntry1.getExternalReferenceCode();

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			String.format(
				"contains(%s/externalReferenceCode,'%s')",
				_objectRelationship1.getName(),
				objectEntry1ExternalReferenceCode.substring(1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			String.format(
				"startswith(%s/externalReferenceCode,'%s')",
				_objectRelationship1.getName(),
				objectEntry1ExternalReferenceCode.substring(0, 2)),
			_objectDefinition2);
	}

	@Test
	public void testGetNestedFieldDetailsInOneToManyRelationships()
		throws Exception {

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_testGetNestedFieldDetailsInOneToManyRelationships(
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), "?nestedFields=r_",
				_objectRelationship1.getName(), "_",
				_objectDefinition1.getPKObjectFieldName()),
			StringBundler.concat(
				"r_", _objectRelationship1.getName(), "_",
				StringUtil.replaceLast(
					_objectDefinition1.getPKObjectFieldName(), "Id", "")));

		_testGetNestedFieldDetailsInOneToManyRelationships(
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), "?nestedFields=",
				_objectRelationship1.getName()),
			_objectRelationship1.getName());
	}

	@Test
	public void testGetObjectEntryFilteredByKeywords() throws Exception {
		_postObjectEntryWithKeywords("tag1");
		_postObjectEntryWithKeywords("tag1", "tag2");
		_postObjectEntryWithKeywords("tag1", "tag2", "tag3");

		_assertFilteredObjectEntries(3, "keywords/any(k:k eq 'tag1')");
		_assertFilteredObjectEntries(2, "keywords/any(k:k eq 'tag2')");
		_assertFilteredObjectEntries(1, "keywords/any(k:k eq 'tag3')");
		_assertFilteredObjectEntries(0, "keywords/any(k:k eq '1234')");

		_assertFilteredObjectEntries(2, "keywords/any(k:k ne 'tag1')");
		_assertFilteredObjectEntries(3, "keywords/any(k:k ne 'tag2')");
		_assertFilteredObjectEntries(3, "keywords/any(k:k ne 'tag3')");

		_assertFilteredObjectEntries(2, "keywords/any(k:k gt 'tag1')");
		_assertFilteredObjectEntries(1, "keywords/any(k:k gt 'tag2')");
		_assertFilteredObjectEntries(0, "keywords/any(k:k gt 'tag3')");

		_assertFilteredObjectEntries(3, "keywords/any(k:k ge 'tag1')");
		_assertFilteredObjectEntries(2, "keywords/any(k:k ge 'tag2')");
		_assertFilteredObjectEntries(1, "keywords/any(k:k ge 'tag3')");

		_assertFilteredObjectEntries(0, "keywords/any(k:k lt 'tag1')");
		_assertFilteredObjectEntries(3, "keywords/any(k:k lt 'tag2')");
		_assertFilteredObjectEntries(3, "keywords/any(k:k lt 'tag3')");

		_assertFilteredObjectEntries(3, "keywords/any(k:k le 'tag1')");
		_assertFilteredObjectEntries(3, "keywords/any(k:k le 'tag2')");
		_assertFilteredObjectEntries(3, "keywords/any(k:k le 'tag3')");

		_assertFilteredObjectEntries(3, "keywords/any(k:startswith(k,'t'))");
		_assertFilteredObjectEntries(3, "keywords/any(k:startswith(k,'ta'))");
		_assertFilteredObjectEntries(3, "keywords/any(k:startswith(k,'tag'))");
		_assertFilteredObjectEntries(3, "keywords/any(k:startswith(k,'tag1'))");
		_assertFilteredObjectEntries(2, "keywords/any(k:startswith(k,'tag2'))");
		_assertFilteredObjectEntries(1, "keywords/any(k:startswith(k,'tag3'))");
		_assertFilteredObjectEntries(0, "keywords/any(k:startswith(k,'1234'))");

		_assertFilteredObjectEntries(3, "keywords/any(k:contains(k,'tag'))");
		_assertFilteredObjectEntries(3, "keywords/any(k:contains(k,'ag1'))");
		_assertFilteredObjectEntries(2, "keywords/any(k:contains(k,'ag2'))");
		_assertFilteredObjectEntries(1, "keywords/any(k:contains(k,'ag3'))");
		_assertFilteredObjectEntries(0, "keywords/any(k:contains(k,'1234'))");

		_assertFilteredObjectEntries(3, "keywords/any(k:k in ('tag1','tag2'))");
		_assertFilteredObjectEntries(2, "keywords/any(k:k in ('tag2','tag3'))");
		_assertFilteredObjectEntries(0, "keywords/any(k:k in ('1234','5678'))");
	}

	@Test
	public void testGetObjectEntryFilteredByTaxonomyCategories()
		throws Exception {

		TaxonomyCategory taxonomyCategory1 = _addTaxonomyCategory();
		TaxonomyCategory taxonomyCategory2 = _addTaxonomyCategory();
		TaxonomyCategory taxonomyCategory3 = _addTaxonomyCategory();

		_postObjectEntryWithTaxonomyCategories();
		_postObjectEntryWithTaxonomyCategories(taxonomyCategory1);
		_postObjectEntryWithTaxonomyCategories(
			taxonomyCategory1, taxonomyCategory2);
		_postObjectEntryWithTaxonomyCategories(
			taxonomyCategory1, taxonomyCategory2, taxonomyCategory3);

		_assertFilteredObjectEntries(
			3,
			String.format(
				"taxonomyCategoryIds/any(k:k eq %s)",
				taxonomyCategory1.getId()));
		_assertFilteredObjectEntries(
			3,
			String.format(
				"taxonomyCategoryIds/any(k:k eq %s)",
				taxonomyCategory1.getId()));
		_assertFilteredObjectEntries(
			2,
			String.format(
				"taxonomyCategoryIds/any(k:k eq %s)",
				taxonomyCategory2.getId()));
		_assertFilteredObjectEntries(
			1,
			String.format(
				"taxonomyCategoryIds/any(k:k eq %s)",
				taxonomyCategory3.getId()));
		_assertFilteredObjectEntries(0, "taxonomyCategoryIds/any(k:k eq 1234)");

		_assertFilteredObjectEntries(
			2,
			String.format(
				"taxonomyCategoryIds/any(k:k ne %s)",
				taxonomyCategory1.getId()));
		_assertFilteredObjectEntries(
			3,
			String.format(
				"taxonomyCategoryIds/any(k:k ne %s)",
				taxonomyCategory2.getId()));
		_assertFilteredObjectEntries(
			3,
			String.format(
				"taxonomyCategoryIds/any(k:k ne %s)",
				taxonomyCategory3.getId()));

		_assertFilteredObjectEntries(
			2,
			String.format(
				"taxonomyCategoryIds/any(k:k gt %s)",
				taxonomyCategory1.getId()));
		_assertFilteredObjectEntries(
			1,
			String.format(
				"taxonomyCategoryIds/any(k:k gt %s)",
				taxonomyCategory2.getId()));
		_assertFilteredObjectEntries(
			0,
			String.format(
				"taxonomyCategoryIds/any(k:k gt %s)",
				taxonomyCategory3.getId()));

		_assertFilteredObjectEntries(
			3,
			String.format(
				"taxonomyCategoryIds/any(k:k ge %s)",
				taxonomyCategory1.getId()));
		_assertFilteredObjectEntries(
			2,
			String.format(
				"taxonomyCategoryIds/any(k:k ge %s)",
				taxonomyCategory2.getId()));
		_assertFilteredObjectEntries(
			1,
			String.format(
				"taxonomyCategoryIds/any(k:k ge %s)",
				taxonomyCategory3.getId()));

		_assertFilteredObjectEntries(
			0,
			String.format(
				"taxonomyCategoryIds/any(k:k lt %s)",
				taxonomyCategory1.getId()));
		_assertFilteredObjectEntries(
			3,
			String.format(
				"taxonomyCategoryIds/any(k:k lt %s)",
				taxonomyCategory2.getId()));
		_assertFilteredObjectEntries(
			3,
			String.format(
				"taxonomyCategoryIds/any(k:k lt %s)",
				taxonomyCategory3.getId()));

		_assertFilteredObjectEntries(
			3,
			String.format(
				"taxonomyCategoryIds/any(k:k le %s)",
				taxonomyCategory1.getId()));
		_assertFilteredObjectEntries(
			3,
			String.format(
				"taxonomyCategoryIds/any(k:k le %s)",
				taxonomyCategory2.getId()));
		_assertFilteredObjectEntries(
			3,
			String.format(
				"taxonomyCategoryIds/any(k:k le %s)",
				taxonomyCategory3.getId()));

		_assertFilteredObjectEntries(
			3,
			String.format(
				"taxonomyCategoryIds/any(k:k in (%s,%s))",
				taxonomyCategory1.getId(), taxonomyCategory2.getId()));
		_assertFilteredObjectEntries(
			2,
			String.format(
				"taxonomyCategoryIds/any(k:k in (%s,%s))",
				taxonomyCategory2.getId(), taxonomyCategory3.getId()));
		_assertFilteredObjectEntries(
			0, "taxonomyCategoryIds/any(k:k in (1234,5678))");
	}

	@Test
	public void testGetObjectEntryWithKeywords() throws Exception {
		JSONObject jsonObject = HTTPTestUtil.invoke(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, "value"
			).put(
				"keywords", JSONUtil.putAll("tag1", "tag2")
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		jsonObject = HTTPTestUtil.invoke(
			null,
			_objectDefinition1.getRESTContextPath() + StringPool.SLASH +
				jsonObject.getString("id"),
			Http.Method.GET);

		JSONArray keywordsJSONArray = jsonObject.getJSONArray("keywords");

		Assert.assertEquals("tag1", keywordsJSONArray.get(0));
		Assert.assertEquals("tag2", keywordsJSONArray.get(1));

		jsonObject = HTTPTestUtil.invoke(
			JSONUtil.put(
				"keywords", JSONUtil.putAll("tag1", "tag2", "tag3")
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		jsonObject = HTTPTestUtil.invoke(
			null,
			_objectDefinition1.getRESTContextPath() + StringPool.SLASH +
				jsonObject.getString("id"),
			Http.Method.GET);

		keywordsJSONArray = jsonObject.getJSONArray("keywords");

		Assert.assertEquals("tag1", keywordsJSONArray.get(0));
		Assert.assertEquals("tag2", keywordsJSONArray.get(1));
		Assert.assertEquals("tag3", keywordsJSONArray.get(2));
	}

	@Test
	public void testGetObjectEntryWithTaxonomyCategories() throws Exception {
		TaxonomyCategory taxonomyCategory1 = _addTaxonomyCategory();
		TaxonomyCategory taxonomyCategory2 = _addTaxonomyCategory();

		JSONObject jsonObject = HTTPTestUtil.invoke(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, "value"
			).put(
				"taxonomyCategoryIds",
				JSONUtil.putAll(
					taxonomyCategory1.getId(), taxonomyCategory2.getId())
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		jsonObject = HTTPTestUtil.invoke(
			null,
			_objectDefinition1.getRESTContextPath() + StringPool.SLASH +
				jsonObject.getString("id"),
			Http.Method.GET);

		Assert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"taxonomyCategoryId",
					Long.valueOf(taxonomyCategory1.getId())
				).put(
					"taxonomyCategoryName", taxonomyCategory1.getName()
				),
				JSONUtil.put(
					"taxonomyCategoryId",
					Long.valueOf(taxonomyCategory2.getId())
				).put(
					"taxonomyCategoryName", taxonomyCategory2.getName()
				)
			).toString(),
			jsonObject.getJSONArray(
				"taxonomyCategoryBriefs"
			).toString());
	}

	@Test
	public void testGetObjectEntryWithTaxonomyCategoriesAndEmbeddedTaxonomyCategory()
		throws Exception {

		TaxonomyCategory taxonomyCategory1 = _addTaxonomyCategory();
		TaxonomyCategory taxonomyCategory2 = _addTaxonomyCategory();

		JSONObject jsonObject = HTTPTestUtil.invoke(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, "value"
			).put(
				"taxonomyCategoryIds",
				JSONUtil.putAll(
					taxonomyCategory1.getId(), taxonomyCategory2.getId())
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				jsonObject.getString("id"),
				"?nestedFields=embeddedTaxonomyCategory"),
			Http.Method.GET);

		Assert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"embeddedTaxonomyCategory",
					_toEmbeddedTaxonomyCategoryJSONObject(taxonomyCategory1)
				).put(
					"taxonomyCategoryId",
					Long.valueOf(taxonomyCategory1.getId())
				).put(
					"taxonomyCategoryName", taxonomyCategory1.getName()
				),
				JSONUtil.put(
					"embeddedTaxonomyCategory",
					_toEmbeddedTaxonomyCategoryJSONObject(taxonomyCategory2)
				).put(
					"taxonomyCategoryId",
					Long.valueOf(taxonomyCategory2.getId())
				).put(
					"taxonomyCategoryName", taxonomyCategory2.getName()
				)
			).toString(),
			jsonObject.getJSONArray(
				"taxonomyCategoryBriefs"
			).toString());
	}

	@Test
	public void testGetObjectRelationshipERCFieldNameInOneToManyRelationship()
		throws Exception {

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null, _objectDefinition2.getRESTContextPath(), Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			itemJSONObject.getString(_objectRelationship1.getName() + "ERC"),
			_objectEntry1.getExternalReferenceCode());
	}

	@Test
	public void testGetObjectRelationshipERCFieldNameInOneToManyRelationshipFromRelatedObjectEntry()
		throws Exception {

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectsEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), "?nestedFields=",
				_objectRelationship1.getName()),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		JSONArray relationshipJSONArray = itemJSONObject.getJSONArray(
			_objectRelationship1.getName());

		Assert.assertEquals(1, relationshipJSONArray.length());

		JSONObject relatedObjectEntryJSONObject =
			relationshipJSONArray.getJSONObject(0);

		Assert.assertEquals(
			relatedObjectEntryJSONObject.getString(
				_objectRelationship1.getName() + "ERC"),
			_objectEntry1.getExternalReferenceCode());
	}

	@Test
	public void testGetScopeScopeKeyObjectEntriesPage() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.ERROR)) {

			JSONObject jsonObject = HTTPTestUtil.invoke(
				null,
				_siteScopedObjectDefinition1.getRESTContextPath() + "/scopes/" +
					RandomTestUtil.randomLong(),
				Http.Method.GET);

			Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));
		}

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			_siteScopedObjectDefinition1.getRESTContextPath() + "/scopes/" +
				TestPropsValues.getGroupId(),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			itemJSONObject.getLong("id"),
			_siteScopedObjectEntry1.getObjectEntryId());
	}

	@Test
	public void testPatchObjectEntryWithKeywords() throws Exception {
		JSONObject jsonObject = HTTPTestUtil.invoke(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, "value"
			).put(
				"keywords", JSONUtil.putAll("tag1", "tag2")
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		HTTPTestUtil.invoke(
			JSONUtil.put(
				"keywords", JSONUtil.putAll("tag1", "tag2", "tag3")
			).toString(),
			_objectDefinition1.getRESTContextPath() + StringPool.SLASH +
				jsonObject.getString("id"),
			Http.Method.PATCH);

		jsonObject = HTTPTestUtil.invoke(
			null,
			_objectDefinition1.getRESTContextPath() + StringPool.SLASH +
				jsonObject.getString("id"),
			Http.Method.GET);

		JSONArray keywordsJSONArray = jsonObject.getJSONArray("keywords");

		Assert.assertEquals("tag1", keywordsJSONArray.get(0));
		Assert.assertEquals("tag2", keywordsJSONArray.get(1));
		Assert.assertEquals("tag3", keywordsJSONArray.get(2));
	}

	@Test
	public void testPatchObjectEntryWithTaxonomyCategories() throws Exception {
		TaxonomyCategory taxonomyCategory1 = _addTaxonomyCategory();
		TaxonomyCategory taxonomyCategory2 = _addTaxonomyCategory();

		JSONObject jsonObject = HTTPTestUtil.invoke(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, "value"
			).put(
				"taxonomyCategoryIds",
				JSONUtil.putAll(
					taxonomyCategory1.getId(), taxonomyCategory2.getId())
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		TaxonomyCategory taxonomyCategory3 = _addTaxonomyCategory();

		jsonObject = HTTPTestUtil.invoke(
			JSONUtil.put(
				"taxonomyCategoryIds",
				JSONUtil.putAll(
					taxonomyCategory1.getId(), taxonomyCategory2.getId(),
					taxonomyCategory3.getId())
			).toString(),
			_objectDefinition1.getRESTContextPath() + StringPool.SLASH +
				jsonObject.getString("id"),
			Http.Method.PATCH);

		Assert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"taxonomyCategoryId",
					Long.valueOf(taxonomyCategory1.getId())
				).put(
					"taxonomyCategoryName", taxonomyCategory1.getName()
				),
				JSONUtil.put(
					"taxonomyCategoryId",
					Long.valueOf(taxonomyCategory2.getId())
				).put(
					"taxonomyCategoryName", taxonomyCategory2.getName()
				),
				JSONUtil.put(
					"taxonomyCategoryId",
					Long.valueOf(taxonomyCategory3.getId())
				).put(
					"taxonomyCategoryName", taxonomyCategory3.getName()
				)
			).toString(),
			jsonObject.getJSONArray(
				"taxonomyCategoryBriefs"
			).toString());
	}

	@Test
	public void testPatchSiteScopedObject() throws Exception {
		String newObjectFieldValue = RandomTestUtil.randomString();

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_OBJECT_FIELD_NAME_1, newObjectFieldValue);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			objectEntryJSONObject.toString(),
			StringBundler.concat(
				_siteScopedObjectDefinition1.getRESTContextPath(), "/scopes/",
				TestPropsValues.getGroupId(), "/by-external-reference-code/",
				_siteScopedObjectEntry1.getExternalReferenceCode()),
			Http.Method.PATCH);

		Assert.assertEquals(
			jsonObject.getString(_OBJECT_FIELD_NAME_1), newObjectFieldValue);
	}

	@Test
	public void testPostCustomObjectEntryWithInvalidNestedCustomObjectEntries()
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.WARN)) {

			_objectRelationship1 =
				ObjectRelationshipTestUtil.addObjectRelationship(
					_objectDefinition1, _objectDefinition2,
					TestPropsValues.getUserId(),
					ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

			_testPostCustomObjectEntryWithInvalidNestedCustomObjectEntriesInManyToManyRelationship(
				_objectDefinition1.getRESTContextPath(), _objectRelationship1);

			_objectRelationship1 =
				ObjectRelationshipTestUtil.addObjectRelationship(
					_objectDefinition1, _objectDefinition2,
					TestPropsValues.getUserId(),
					ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

			_testPostCustomObjectEntryWithInvalidNestedCustomObjectEntriesInManyToOneRelationship(
				_objectDefinition2.getRESTContextPath(), _objectRelationship1);

			_testPostCustomObjectEntryWithInvalidNestedCustomObjectEntriesInOneToManyRelationship(
				_objectDefinition1.getRESTContextPath(), _objectRelationship1);
		}
	}

	@Test
	public void testPostCustomObjectEntryWithNestedCustomObjectEntriesInManyToManyRelationship()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1, _ERC_VALUE_2}, _OBJECT_FIELD_NAME_2,
				new String[] {
					_NEW_OBJECT_FIELD_VALUE_1, _NEW_OBJECT_FIELD_VALUE_2
				}));

		JSONObject jsonObject = HTTPTestUtil.invoke(
			objectEntryJSONObject.toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		String objectEntryId = jsonObject.getString("id");

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				objectEntryId, "?nestedFields=",
				_objectRelationship1.getName()),
			Http.Method.GET);

		JSONArray nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			_objectRelationship1.getName());

		Assert.assertEquals(2, nestedObjectEntriesJSONArray.length());

		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(0),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_1);
		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(1),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_2);
	}

	@Test
	public void testPostCustomObjectEntryWithNestedCustomObjectEntriesInManyToOneRelationship()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			JSONFactoryUtil.createJSONObject(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_1
				).put(
					"externalReferenceCode", _ERC_VALUE_1
				).toString()));

		JSONObject jsonObject = HTTPTestUtil.invoke(
			objectEntryJSONObject.toString(),
			_objectDefinition2.getRESTContextPath(), Http.Method.POST);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		String objectEntryId = jsonObject.getString("id");

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				objectEntryId, "?nestedFields=",
				StringBundler.concat(
					"r_", _objectRelationship1.getName(), "_",
					StringUtil.replaceLast(
						_objectDefinition1.getPKObjectFieldName(), "Id", ""))),
			Http.Method.GET);

		_assertObjectEntryField(
			jsonObject.getJSONObject(
				StringBundler.concat(
					"r_", _objectRelationship1.getName(), "_",
					StringUtil.replaceLast(
						_objectDefinition1.getPKObjectFieldName(), "Id", ""))),
			_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_1);
	}

	@Test
	public void testPostCustomObjectEntryWithNestedCustomObjectEntriesInOneToManyRelationship()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1, _ERC_VALUE_2}, _OBJECT_FIELD_NAME_2,
				new String[] {
					_NEW_OBJECT_FIELD_VALUE_1, _NEW_OBJECT_FIELD_VALUE_2
				}));

		JSONObject jsonObject = HTTPTestUtil.invoke(
			objectEntryJSONObject.toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		String objectEntryId = jsonObject.getString("id");

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				objectEntryId, "?nestedFields=",
				_objectRelationship1.getName()),
			Http.Method.GET);

		JSONArray nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			_objectRelationship1.getName());

		Assert.assertEquals(2, nestedObjectEntriesJSONArray.length());

		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(0),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_1);
		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(1),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_2);
	}

	@Test
	public void testPutByExternalReferenceCodeManyToManyRelationship()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(),
				"/by-external-reference-code/",
				_objectEntry1.getExternalReferenceCode(), StringPool.SLASH,
				_objectRelationship1.getName(), StringPool.SLASH,
				_objectEntry2.getExternalReferenceCode()),
			Http.Method.PUT);

		Assert.assertEquals(
			_objectEntry2.getExternalReferenceCode(),
			jsonObject.getString("externalReferenceCode"));
		Assert.assertEquals(
			_OBJECT_FIELD_VALUE_2, jsonObject.getInt(_OBJECT_FIELD_NAME_2));

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(),
				"/by-external-reference-code/",
				_objectEntry2.getExternalReferenceCode(), StringPool.SLASH,
				_objectRelationship1.getName(), StringPool.SLASH,
				_objectEntry1.getExternalReferenceCode()),
			Http.Method.PUT);

		Assert.assertEquals(
			_objectEntry1.getExternalReferenceCode(),
			jsonObject.getString("externalReferenceCode"));
		Assert.assertEquals(
			_OBJECT_FIELD_VALUE_1, jsonObject.getInt(_OBJECT_FIELD_NAME_1));

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(),
				"/by-external-reference-code/",
				_objectEntry2.getExternalReferenceCode(), StringPool.SLASH,
				_objectRelationship1.getName(), StringPool.SLASH,
				RandomTestUtil.randomString()),
			Http.Method.PUT);

		Assert.assertThat(
			jsonObject.getString("title"),
			CoreMatchers.containsString("No ObjectEntry exists with the key"));
	}

	@Test
	public void testPutCustomObjectEntryWithNestedCustomObjectEntriesInManyToManyRelationship()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1, _ERC_VALUE_2}, _OBJECT_FIELD_NAME_2,
				new String[] {
					RandomTestUtil.randomString(), RandomTestUtil.randomString()
				}));

		HTTPTestUtil.invoke(
			objectEntryJSONObject.toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		JSONObject newObjectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1, _ERC_VALUE_2}, _OBJECT_FIELD_NAME_2,
				new String[] {
					_NEW_OBJECT_FIELD_VALUE_1, _NEW_OBJECT_FIELD_VALUE_2
				}));

		JSONObject jsonObject = HTTPTestUtil.invoke(
			newObjectEntryJSONObject.toString(),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey()),
			Http.Method.PUT);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), "?nestedFields=",
				_objectRelationship1.getName()),
			Http.Method.GET);

		JSONArray nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			_objectRelationship1.getName());

		Assert.assertEquals(2, nestedObjectEntriesJSONArray.length());

		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(0),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_1);
		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(1),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_2);
	}

	@Test
	public void testPutCustomObjectEntryWithNestedCustomObjectEntriesInManyToOneRelationship()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			JSONFactoryUtil.createJSONObject(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
				).put(
					"externalReferenceCode", _ERC_VALUE_1
				).toString()));

		JSONObject jsonObject = HTTPTestUtil.invoke(
			objectEntryJSONObject.toString(),
			_objectDefinition2.getRESTContextPath(), Http.Method.POST);

		String objectEntryId = jsonObject.getString("id");

		JSONObject newObjectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			JSONFactoryUtil.createJSONObject(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_1
				).put(
					"externalReferenceCode", _ERC_VALUE_1
				).toString()));

		jsonObject = HTTPTestUtil.invoke(
			newObjectEntryJSONObject.toString(),
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				objectEntryId),
			Http.Method.PUT);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				objectEntryId, "?nestedFields=",
				StringBundler.concat(
					"r_", _objectRelationship1.getName(), "_",
					StringUtil.replaceLast(
						_objectDefinition1.getPKObjectFieldName(), "Id", ""))),
			Http.Method.GET);

		_assertObjectEntryField(
			jsonObject.getJSONObject(
				StringBundler.concat(
					"r_", _objectRelationship1.getName(), "_",
					StringUtil.replaceLast(
						_objectDefinition1.getPKObjectFieldName(), "Id", ""))),
			_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_1);
	}

	@Test
	public void testPutCustomObjectEntryWithNestedCustomObjectEntriesInOneToManyRelationship()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1, _ERC_VALUE_2}, _OBJECT_FIELD_NAME_2,
				new String[] {
					RandomTestUtil.randomString(), RandomTestUtil.randomString()
				}));

		HTTPTestUtil.invoke(
			objectEntryJSONObject.toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		JSONObject newObjectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1, _ERC_VALUE_2}, _OBJECT_FIELD_NAME_2,
				new String[] {
					_NEW_OBJECT_FIELD_VALUE_1, _NEW_OBJECT_FIELD_VALUE_2
				}));

		JSONObject jsonObject = HTTPTestUtil.invoke(
			newObjectEntryJSONObject.toString(),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey()),
			Http.Method.PUT);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), "?nestedFields=",
				_objectRelationship1.getName()),
			Http.Method.GET);

		JSONArray nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			_objectRelationship1.getName());

		Assert.assertEquals(2, nestedObjectEntriesJSONArray.length());

		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(0),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_1);
		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(1),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_2);
	}

	private ObjectRelationship _addObjectRelationshipAndRelateObjectsEntries(
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2, ObjectEntry objectEntry1,
			ObjectEntry objectEntry2, String type)
		throws Exception {

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				objectDefinition1, objectDefinition2,
				TestPropsValues.getUserId(), type);

		ObjectRelationshipTestUtil.relateObjectEntries(
			objectEntry1.getPrimaryKey(), objectEntry2.getPrimaryKey(),
			objectRelationship, TestPropsValues.getUserId());

		return objectRelationship;
	}

	private ObjectRelationship _addObjectRelationshipAndRelateObjectsEntries(
			String type)
		throws Exception {

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectDefinition1, _objectDefinition2,
				TestPropsValues.getUserId(), type);

		ObjectRelationshipTestUtil.relateObjectEntries(
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			objectRelationship, TestPropsValues.getUserId());

		return objectRelationship;
	}

	private TaxonomyCategory _addTaxonomyCategory() throws Exception {
		return _taxonomyCategoryResource.postTaxonomyVocabularyTaxonomyCategory(
			_assetVocabulary.getVocabularyId(),
			new TaxonomyCategory() {
				{
					dateCreated = RandomTestUtil.nextDate();
					dateModified = RandomTestUtil.nextDate();
					description = StringUtil.toLowerCase(
						RandomTestUtil.randomString());
					externalReferenceCode = StringUtil.toLowerCase(
						RandomTestUtil.randomString());
					id = StringUtil.toLowerCase(RandomTestUtil.randomString());
					name = StringUtil.toLowerCase(
						RandomTestUtil.randomString());
					numberOfTaxonomyCategories = RandomTestUtil.randomInt();
					siteId = TestPropsValues.getGroupId();
					taxonomyCategoryUsageCount = RandomTestUtil.randomInt();
					taxonomyVocabularyId = RandomTestUtil.randomLong();
				}
			});
	}

	private void _assertFilteredObjectEntries(
			int expectedObjectEntryCount, String filterString)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			_objectDefinition1.getRESTContextPath() + "?filter=" +
				URLCodec.encodeURL(filterString),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(expectedObjectEntryCount, itemsJSONArray.length());
	}

	private void _assertFilterString(
			String expectedObjectFieldName,
			Serializable expectedObjectFieldValue, String filterString,
			ObjectDefinition objectDefinition)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			objectDefinition.getRESTContextPath() + "?filter=" + filterString,
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			String.valueOf(expectedObjectFieldValue),
			String.valueOf(itemJSONObject.get(expectedObjectFieldName)));
	}

	private void _assertObjectEntryField(
		JSONObject objectEntryJSONObject, String objectFieldName,
		String objectFieldValue) {

		int objectEntryId = objectEntryJSONObject.getInt("id");

		ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
			objectEntryId);

		Assert.assertEquals(
			MapUtil.getString(objectEntry.getValues(), objectFieldName),
			objectFieldValue);
	}

	private JSONArray _createObjectEntriesJSONArray(
			String[] externalReferenceCodeValues, String objectFieldName,
			String[] objectFieldValues)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < objectFieldValues.length; i++) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				JSONUtil.put(
					objectFieldName, objectFieldValues[i]
				).put(
					"externalReferenceCode", externalReferenceCodeValues[i]
				).toString());

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private String _escape(String string) {
		return URLCodec.encodeURL(string);
	}

	private void _postObjectEntryWithKeywords(String... keywords)
		throws Exception {

		HTTPTestUtil.invoke(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
			).put(
				"keywords", JSONUtil.putAll(keywords)
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);
	}

	private void _postObjectEntryWithTaxonomyCategories(
			TaxonomyCategory... taxonomyCategories)
		throws Exception {

		HTTPTestUtil.invoke(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
			).put(
				"taxonomyCategoryIds",
				TransformUtil.transform(
					taxonomyCategories, TaxonomyCategory::getId, String.class)
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);
	}

	private void _testGetNestedFieldDetailsInOneToManyRelationships(
			String endpoint, String expectedFieldName)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null, endpoint, Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			_OBJECT_FIELD_VALUE_2, itemJSONObject.getInt(_OBJECT_FIELD_NAME_2));

		JSONObject relatedObjectJSONObject = itemJSONObject.getJSONObject(
			expectedFieldName);

		Assert.assertEquals(
			_OBJECT_FIELD_VALUE_1,
			relatedObjectJSONObject.getInt(_OBJECT_FIELD_NAME_1));
	}

	private void
			_testPostCustomObjectEntryWithInvalidNestedCustomObjectEntriesInManyToManyRelationship(
				String objectDefinitionRESTContextPath,
				ObjectRelationship objectRelationship)
		throws Exception {

		JSONObject objectEntryJSONObject = JSONUtil.put(
			objectRelationship.getName(),
			JSONFactoryUtil.createJSONObject(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_2
				).put(
					"externalReferenceCode", _ERC_VALUE_2
				).toString()));

		JSONObject jsonObject = HTTPTestUtil.invoke(
			objectEntryJSONObject.toString(), objectDefinitionRESTContextPath,
			Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
	}

	private void
			_testPostCustomObjectEntryWithInvalidNestedCustomObjectEntriesInManyToOneRelationship(
				String objectDefinitionRESTContextPath,
				ObjectRelationship objectRelationship)
		throws Exception {

		JSONObject objectEntryJSONObject = JSONUtil.put(
			objectRelationship.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1, _ERC_VALUE_2}, _OBJECT_FIELD_NAME_1,
				new String[] {
					RandomTestUtil.randomString(), RandomTestUtil.randomString()
				}));

		JSONObject jsonObject = HTTPTestUtil.invoke(
			objectEntryJSONObject.toString(), objectDefinitionRESTContextPath,
			Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
	}

	private void
			_testPostCustomObjectEntryWithInvalidNestedCustomObjectEntriesInOneToManyRelationship(
				String objectDefinitionRESTContextPath,
				ObjectRelationship objectRelationship)
		throws Exception {

		JSONObject objectEntryJSONObject = JSONUtil.put(
			objectRelationship.getName(),
			JSONFactoryUtil.createJSONObject(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_2
				).put(
					"externalReferenceCode", _ERC_VALUE_2
				).toString()));

		JSONObject jsonObject = HTTPTestUtil.invoke(
			objectEntryJSONObject.toString(), objectDefinitionRESTContextPath,
			Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
	}

	private JSONObject _toEmbeddedTaxonomyCategoryJSONObject(
			TaxonomyCategory taxonomyCategory)
		throws Exception {

		TaxonomyCategory embeddedTaxonomyCategory = taxonomyCategory.clone();

		embeddedTaxonomyCategory.setActions(new HashMap<>());

		return JSONFactoryUtil.createJSONObject(
			embeddedTaxonomyCategory.toString());
	}

	private static final String _ERC_VALUE_1 = RandomTestUtil.randomString();

	private static final String _ERC_VALUE_2 = RandomTestUtil.randomString();

	private static final String _NEW_OBJECT_FIELD_VALUE_1 =
		RandomTestUtil.randomString();

	private static final String _NEW_OBJECT_FIELD_VALUE_2 =
		RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_1 =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_2 =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_3 =
		"x" + RandomTestUtil.randomString();

	private static final int _OBJECT_FIELD_VALUE_1 = RandomTestUtil.randomInt();

	private static final int _OBJECT_FIELD_VALUE_2 = RandomTestUtil.randomInt();

	private static final int _OBJECT_FIELD_VALUE_3 = RandomTestUtil.randomInt();

	private static AssetVocabulary _assetVocabulary;
	private static TaxonomyCategoryResource _taxonomyCategoryResource;

	private ObjectDefinition _objectDefinition1;
	private ObjectDefinition _objectDefinition2;
	private ObjectDefinition _objectDefinition3;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private ObjectEntry _objectEntry1;
	private ObjectEntry _objectEntry2;
	private ObjectEntry _objectEntry3;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	private ObjectRelationship _objectRelationship1;
	private ObjectRelationship _objectRelationship2;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	private ObjectDefinition _siteScopedObjectDefinition1;
	private ObjectEntry _siteScopedObjectEntry1;

}