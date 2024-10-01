/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.planner.rest.client.dto.v1_0.Field;
import com.liferay.batch.planner.rest.client.pagination.Page;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.Collections;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Matija Petanjek
 */
@RunWith(Arquillian.class)
public class FieldResourceTest extends BaseFieldResourceTestCase {

	@Test
	public void testGetFieldsWithManyToManyRelationship() throws Exception {
		ObjectDefinition objectDefinition1 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectDefinitionTestUtil.getRandomName(),
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
						RandomTestUtil.randomString(),
						"x" + RandomTestUtil.randomString(), false)),
				ObjectDefinitionConstants.SCOPE_COMPANY);

		ObjectDefinition objectDefinition2 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectDefinitionTestUtil.getRandomName(),
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
						RandomTestUtil.randomString(),
						"x" + RandomTestUtil.randomString(), false)),
				ObjectDefinitionConstants.SCOPE_COMPANY);

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				objectDefinition1, objectDefinition2,
				TestPropsValues.getUserId(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		Page<Field> page = fieldResource.getPlanInternalClassNameKeyFieldsPage(
			_getObjectDefinitionInternalClassName(objectDefinition2.getName()),
			null);

		String objectRelationshipName = objectRelationship.getName();

		int objectRelationshipFieldsCount = 0;

		for (Field field : page.getItems()) {
			if (Objects.equals(field.getName(), objectRelationshipName)) {
				Assert.assertNull(
					field.getName() + " should not be present",
					field.getAnyOfGroup());

				objectRelationshipFieldsCount++;
			}
		}

		Assert.assertEquals(
			"Incorrect number of object relationship fields", 1,
			objectRelationshipFieldsCount);
	}

	@Test
	public void testGetFieldsWithMultipleOneToManyRelationship()
		throws Exception {

		ObjectDefinition objectDefinition1 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectDefinitionTestUtil.getRandomName(),
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
						RandomTestUtil.randomString(),
						"x" + RandomTestUtil.randomString(), false)),
				ObjectDefinitionConstants.SCOPE_COMPANY);

		ObjectDefinition objectDefinition2 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectDefinitionTestUtil.getRandomName(),
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
						RandomTestUtil.randomString(),
						"x" + RandomTestUtil.randomString(), false)),
				ObjectDefinitionConstants.SCOPE_COMPANY);

		ObjectRelationship objectRelationship1 =
			ObjectRelationshipTestUtil.addObjectRelationship(
				objectDefinition1, objectDefinition2,
				TestPropsValues.getUserId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectDefinition objectDefinition3 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectDefinitionTestUtil.getRandomName(),
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
						RandomTestUtil.randomString(),
						"x" + RandomTestUtil.randomString(), false)),
				ObjectDefinitionConstants.SCOPE_COMPANY);

		ObjectRelationship objectRelationship2 =
			ObjectRelationshipTestUtil.addObjectRelationship(
				objectDefinition3, objectDefinition2,
				TestPropsValues.getUserId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		Page<Field> page = fieldResource.getPlanInternalClassNameKeyFieldsPage(
			_getObjectDefinitionInternalClassName(objectDefinition2.getName()),
			null);

		String objectRelationship1Name = objectRelationship1.getName();

		String objectRelationship1IdFieldName = StringBundler.concat(
			"r_", objectRelationship1Name, "_",
			objectDefinition1.getPKObjectFieldName());

		String objectRelationship2Name = objectRelationship2.getName();

		String objectRelationship2IdFieldName = StringBundler.concat(
			"r_", objectRelationship2Name, "_",
			objectDefinition3.getPKObjectFieldName());

		int objectRelationshipFieldsCount = 0;

		for (Field field : page.getItems()) {
			String anyOfGroup = field.getAnyOfGroup();

			if (Objects.equals(
					field.getName(), objectRelationship1IdFieldName) ||
				Objects.equals(field.getName(), objectRelationship1Name)) {

				Assert.assertNotNull(
					field.getName() + " should not be null", anyOfGroup);

				Assert.assertEquals(objectRelationship1Name, anyOfGroup);

				objectRelationshipFieldsCount++;

				continue;
			}

			if (Objects.equals(
					field.getName(), objectRelationship2IdFieldName) ||
				Objects.equals(field.getName(), objectRelationship2Name)) {

				Assert.assertNotNull(
					field.getName() + " should not be null", anyOfGroup);

				Assert.assertEquals(objectRelationship2Name, anyOfGroup);

				objectRelationshipFieldsCount++;

				continue;
			}

			String objectRelationship1ERCFieldName =
				ObjectFieldSettingUtil.getValue(
					ObjectFieldSettingConstants.
						NAME_OBJECT_RELATIONSHIP_ERC_OBJECT_FIELD_NAME,
					ObjectFieldLocalServiceUtil.getObjectField(
						objectDefinition2.getObjectDefinitionId(),
						objectRelationship1IdFieldName));

			if (Objects.equals(
					field.getName(), objectRelationship1ERCFieldName)) {

				Assert.assertNotNull(
					field.getName() + " should not be null", anyOfGroup);

				Assert.assertEquals(objectRelationship1Name, anyOfGroup);

				objectRelationshipFieldsCount++;

				continue;
			}

			String objectRelationship2ERCFieldName =
				ObjectFieldSettingUtil.getValue(
					ObjectFieldSettingConstants.
						NAME_OBJECT_RELATIONSHIP_ERC_OBJECT_FIELD_NAME,
					ObjectFieldLocalServiceUtil.getObjectField(
						objectDefinition2.getObjectDefinitionId(),
						objectRelationship2IdFieldName));

			if (Objects.equals(
					field.getName(), objectRelationship2ERCFieldName)) {

				Assert.assertNotNull(
					field.getName() + " should not be null", anyOfGroup);

				Assert.assertEquals(objectRelationship2Name, anyOfGroup);

				objectRelationshipFieldsCount++;
			}
		}

		Assert.assertEquals(
			"Incorrect number of object relationship fields", 6,
			objectRelationshipFieldsCount);
	}

	@Test
	public void testGetFieldsWithOneToManyRelationship() throws Exception {
		ObjectDefinition objectDefinition1 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectDefinitionTestUtil.getRandomName(),
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
						RandomTestUtil.randomString(),
						"x" + RandomTestUtil.randomString(), false)),
				ObjectDefinitionConstants.SCOPE_COMPANY);

		ObjectDefinition objectDefinition2 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectDefinitionTestUtil.getRandomName(),
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
						RandomTestUtil.randomString(),
						"x" + RandomTestUtil.randomString(), false)),
				ObjectDefinitionConstants.SCOPE_COMPANY);

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				objectDefinition1, objectDefinition2,
				TestPropsValues.getUserId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		Page<Field> page = fieldResource.getPlanInternalClassNameKeyFieldsPage(
			_getObjectDefinitionInternalClassName(objectDefinition2.getName()),
			null);

		String objectRelationshipName = objectRelationship.getName();

		String objectRelationshipIdFieldName = StringBundler.concat(
			"r_", objectRelationshipName, "_",
			objectDefinition1.getPKObjectFieldName());

		int objectRelationshipFieldsCount = 0;

		for (Field field : page.getItems()) {
			String anyOfGroup = field.getAnyOfGroup();

			if (Objects.equals(
					field.getName(), objectRelationshipIdFieldName) ||
				Objects.equals(field.getName(), objectRelationshipName)) {

				Assert.assertNotNull(
					field.getName() + " should not be null", anyOfGroup);

				Assert.assertEquals(objectRelationshipName, anyOfGroup);

				objectRelationshipFieldsCount++;

				continue;
			}

			String objectRelationshipERCFieldName =
				ObjectFieldSettingUtil.getValue(
					ObjectFieldSettingConstants.
						NAME_OBJECT_RELATIONSHIP_ERC_OBJECT_FIELD_NAME,
					ObjectFieldLocalServiceUtil.getObjectField(
						objectDefinition2.getObjectDefinitionId(),
						objectRelationshipIdFieldName));

			if (Objects.equals(
					field.getName(), objectRelationshipERCFieldName)) {

				Assert.assertNotNull(
					field.getName() + " should not be null", anyOfGroup);

				Assert.assertEquals(objectRelationshipName, anyOfGroup);

				objectRelationshipFieldsCount++;
			}
		}

		Assert.assertEquals(
			"Incorrect number of object relationship fields", 3,
			objectRelationshipFieldsCount);
	}

	@Ignore
	@Override
	@Test
	public void testGetPlanInternalClassNameKeyFieldsPage() throws Exception {
		super.testGetPlanInternalClassNameKeyFieldsPage();
	}

	private String _getObjectDefinitionInternalClassName(
		String objectDefinitionName) {

		return "com.liferay.object.rest.dto.v1_0.ObjectEntry%23" +
			objectDefinitionName;
	}

}