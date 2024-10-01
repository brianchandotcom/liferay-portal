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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;

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
		String fieldName = "x" + RandomTestUtil.randomString();

		ObjectDefinition objectDefinition1 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectDefinitionTestUtil.getRandomName(),
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
						RandomTestUtil.randomString(), fieldName, false)),
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
			_getObjectDefinitionInternalClassName(objectDefinition1.getName()),
			null);

		assertEqualsIgnoringOrder(
			ListUtil.fromArray(
				_toField(null, "externalReferenceCode", false, "string", null),
				_toField(null, "keywords", false, "array", "CSV"),
				_toField(null, "permissions", false, "array", null),
				_toField(null, "taxonomyCategoryIds", false, "array", "CSV"),
				_toField(null, fieldName, false, "string", null),
				_toField(
					null, objectRelationship.getName(), false, "array", null)),
			ListUtil.fromCollection(page.getItems()));
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

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"anyOfGroup", "name", "required", "type", "unsupportedFormats"
		};
	}

	private String _getObjectDefinitionInternalClassName(
		String objectDefinitionName) {

		return "com.liferay.object.rest.dto.v1_0.ObjectEntry%23" +
			objectDefinitionName;
	}

	private Field _toField(
		String anyOfGroup, String name, boolean required, String type,
		String unsupportedFormats) {

		Field field = new Field();

		field.setAnyOfGroup(anyOfGroup);
		field.setName(name);
		field.setRequired(required);
		field.setType(type);

		if (unsupportedFormats != null) {
			field.setUnsupportedFormats(StringUtil.split(unsupportedFormats));
		}

		return field;
	}

}