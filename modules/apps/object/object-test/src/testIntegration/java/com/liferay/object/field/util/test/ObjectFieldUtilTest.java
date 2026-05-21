/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.field.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.ObjectFieldReadOnlyException;
import com.liferay.object.field.builder.ObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.field.setting.builder.ObjectFieldSettingBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectField;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Natahaly Gomes
 */
@RunWith(Arquillian.class)
public class ObjectFieldUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testValidateReadOnlyObjectFields() throws PortalException {

		// Conditional read only, custom object field

		ObjectField objectField = new TextObjectFieldBuilder(
		).labelMap(
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
		).localized(
			true
		).name(
			"textObjectField"
		).readOnly(
			ObjectFieldConstants.READ_ONLY_CONDITIONAL
		).readOnlyConditionExpression(
			"not(isEmpty(textObjectField))"
		).build();

		AssertUtils.assertFailure(
			ObjectFieldReadOnlyException.class,
			"Object field textObjectField is read only",
			() -> ObjectFieldUtil.validateReadOnlyObjectFields(
				_ddmExpressionFactory, _getValues(objectField),
				Collections.singletonList(objectField),
				_getValues(objectField)));

		Map<String, Object> values = _getValues(objectField);

		ObjectFieldUtil.validateReadOnlyObjectFields(
			_ddmExpressionFactory, values,
			Collections.singletonList(objectField),
			HashMapBuilder.<String, Object>putAll(
				values
			).put(
				objectField.getName(), RandomTestUtil.randomString()
			).build());

		// One to many relationship, custom object field

		String objectRelationshipERCObjectFieldName =
			"a" + RandomTestUtil.randomString();

		ObjectField relationshipObjectField = new ObjectFieldBuilder(
		).businessType(
			ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP
		).dbType(
			ObjectFieldConstants.DB_TYPE_LONG
		).labelMap(
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
		).name(
			"a" + RandomTestUtil.randomString()
		).objectFieldSettings(
			Collections.singletonList(
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.
						NAME_OBJECT_RELATIONSHIP_ERC_OBJECT_FIELD_NAME
				).value(
					objectRelationshipERCObjectFieldName
				).build())
		).readOnly(
			ObjectFieldConstants.READ_ONLY_TRUE
		).relationshipType(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY
		).build();

		String externalReferenceCode = RandomTestUtil.randomString();

		Map<String, Object> existingValues = HashMapBuilder.<String, Object>put(
			objectRelationshipERCObjectFieldName, externalReferenceCode
		).put(
			relationshipObjectField.getName(), RandomTestUtil.randomLong()
		).build();

		AssertUtils.assertFailure(
			ObjectFieldReadOnlyException.class,
			"Object field " + relationshipObjectField.getName() +
				" is read only",
			() -> ObjectFieldUtil.validateReadOnlyObjectFields(
				_ddmExpressionFactory, existingValues,
				Collections.singletonList(relationshipObjectField),
				Collections.singletonMap(
					objectRelationshipERCObjectFieldName, null)));

		AssertUtils.assertFailure(
			ObjectFieldReadOnlyException.class,
			"Object field " + relationshipObjectField.getName() +
				" is read only",
			() -> ObjectFieldUtil.validateReadOnlyObjectFields(
				_ddmExpressionFactory, existingValues,
				Collections.singletonList(relationshipObjectField),
				HashMapBuilder.<String, Object>put(
					objectRelationshipERCObjectFieldName,
					RandomTestUtil.randomString()
				).build()));

		AssertUtils.assertFailure(
			ObjectFieldReadOnlyException.class,
			"Object field " + relationshipObjectField.getName() +
				" is read only",
			() -> ObjectFieldUtil.validateReadOnlyObjectFields(
				_ddmExpressionFactory, new HashMap<>(),
				Collections.singletonList(relationshipObjectField),
				HashMapBuilder.<String, Object>put(
					objectRelationshipERCObjectFieldName,
					RandomTestUtil.randomString()
				).build()));

		AssertUtils.assertFailure(
			ObjectFieldReadOnlyException.class,
			"Object field " + relationshipObjectField.getName() +
				" is read only",
			() -> ObjectFieldUtil.validateReadOnlyObjectFields(
				_ddmExpressionFactory, new HashMap<>(),
				Collections.singletonList(relationshipObjectField),
				HashMapBuilder.<String, Object>put(
					relationshipObjectField.getName(),
					RandomTestUtil.randomLong()
				).build()));

		ObjectFieldUtil.validateReadOnlyObjectFields(
			_ddmExpressionFactory, existingValues,
			Collections.singletonList(relationshipObjectField),
			HashMapBuilder.<String, Object>put(
				objectRelationshipERCObjectFieldName, externalReferenceCode
			).build());

		int objectRelationshipId = RandomTestUtil.randomInt();

		ObjectFieldUtil.validateReadOnlyObjectFields(
			_ddmExpressionFactory,
			HashMapBuilder.<String, Object>put(
				objectRelationshipERCObjectFieldName, externalReferenceCode
			).put(
				relationshipObjectField.getName(),
				Long.valueOf(objectRelationshipId)
			).build(),
			Collections.singletonList(relationshipObjectField),
			HashMapBuilder.<String, Object>put(
				relationshipObjectField.getName(),
				Integer.valueOf(objectRelationshipId)
			).build());
	}

	private Map<String, Object> _getValues(ObjectField objectField) {
		return HashMapBuilder.<String, Object>put(
			objectField.getI18nObjectFieldName(),
			HashMapBuilder.put(
				"en_US", RandomTestUtil.randomString()
			).put(
				"pt_BR", RandomTestUtil.randomString()
			).build()
		).put(
			objectField.getName(), RandomTestUtil.randomString()
		).build();
	}

	@Inject
	private DDMExpressionFactory _ddmExpressionFactory;

}