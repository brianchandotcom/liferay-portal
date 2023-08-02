/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.exception.NoSuchObjectValidationRuleException;
import com.liferay.object.exception.ObjectValidationRuleEngineException;
import com.liferay.object.exception.ObjectValidationRuleNameException;
import com.liferay.object.exception.ObjectValidationRuleScriptException;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectValidationRule;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectValidationRuleLocalService;
import com.liferay.object.service.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcela Cunha
 */
@FeatureFlags("LPS-187846")
@RunWith(Arquillian.class)
public class ObjectValidationRuleLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_objectDefinition = ObjectDefinitionTestUtil.addObjectDefinition(
			false, _objectDefinitionLocalService,
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING,
					RandomTestUtil.randomString(), "textField")));
	}

	@Test
	public void testAddObjectValidationRule() throws Exception {
		_testAddObjectValidationRuleFailure(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			ObjectValidationRuleNameException.class,
			"Name is null for locale " + LocaleUtil.US.getDisplayName(),
			StringPool.BLANK, _VALID_DDM_SCRIPT);
		_testAddObjectValidationRuleFailure(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			ObjectValidationRuleScriptException.class, "required",
			RandomTestUtil.randomString(), StringPool.BLANK);
		_testAddObjectValidationRuleFailure(
			ObjectValidationRuleConstants.ENGINE_TYPE_GROOVY,
			ObjectValidationRuleScriptException.class, "syntax-error",
			RandomTestUtil.randomString(), "import;\ninvalidFields = false;");
		_testAddObjectValidationRuleFailure(
			StringPool.BLANK,
			ObjectValidationRuleEngineException.MustNotBeNull.class,
			"Engine is null", RandomTestUtil.randomString(), _VALID_DDM_SCRIPT);
		_testAddObjectValidationRuleFailure(
			"abcdefghijklmnopqrstuvwxyz",
			ObjectValidationRuleEngineException.NoSuchEngine.class,
			"Engine \"abcdefghijklmnopqrstuvwxyz\" does not exist",
			RandomTestUtil.randomString(), _VALID_DDM_SCRIPT);

		_testAddObjectValidationRuleSuccess(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM, _VALID_DDM_SCRIPT);
		_testAddObjectValidationRuleSuccess(
			ObjectValidationRuleConstants.ENGINE_TYPE_GROOVY,
			"import com.liferay.commerce.service.CommerceOrderLocalService;\n" +
				"invalidFields = false;");
	}

	@Test
	public void testDeleteObjectValidationRule() throws Exception {
		ObjectValidationRule objectValidationRule = _addObjectValidationRule(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM, _VALID_DDM_SCRIPT);

		objectValidationRule =
			_objectValidationRuleLocalService.fetchObjectValidationRule(
				objectValidationRule.getObjectValidationRuleId());

		Assert.assertNotNull(objectValidationRule);

		_objectValidationRuleLocalService.deleteObjectValidationRule(
			objectValidationRule.getObjectValidationRuleId());

		objectValidationRule =
			_objectValidationRuleLocalService.fetchObjectValidationRule(
				objectValidationRule.getObjectValidationRuleId());

		Assert.assertNull(objectValidationRule);
	}

	@Test
	public void testUpdateObjectValidationRule() throws Exception {
		ObjectValidationRule objectValidationRule = _addObjectValidationRule(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM, _VALID_DDM_SCRIPT);

		try {
			objectValidationRule =
				_objectValidationRuleLocalService.updateObjectValidationRule(
					RandomTestUtil.randomLong(), false,
					ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION,
					_VALID_DDM_SCRIPT, Collections.emptyList());

			Assert.fail();
		}
		catch (NoSuchObjectValidationRuleException
					noSuchObjectValidationRuleException) {

			Assert.assertNotNull(noSuchObjectValidationRuleException);
		}

		objectValidationRule =
			_objectValidationRuleLocalService.updateObjectValidationRule(
				objectValidationRule.getObjectValidationRuleId(), true,
				ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
				LocalizedMapUtil.getLocalizedMap("Field must be an URL"),
				LocalizedMapUtil.getLocalizedMap("URL Validation"),
				ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION,
				"isURL(textField)", Collections.emptyList());

		Assert.assertTrue(objectValidationRule.isActive());
		Assert.assertEquals(
			ObjectValidationRuleConstants.ENGINE_TYPE_DDM,
			objectValidationRule.getEngine());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("Field must be an URL"),
			objectValidationRule.getErrorLabelMap());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap("URL Validation"),
			objectValidationRule.getNameMap());
		Assert.assertEquals(
			"isURL(textField)", objectValidationRule.getScript());
	}

	private ObjectValidationRule _addObjectValidationRule(
			String engine, String script)
		throws Exception {

		return _objectValidationRuleLocalService.addObjectValidationRule(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true, engine,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION, script,
			Collections.emptyList());
	}

	private void _testAddObjectValidationRuleFailure(
		String engine, Class<?> expectedExceptionClass, String expectedMessage,
		String name, String script) {

		try {
			_objectValidationRuleLocalService.addObjectValidationRule(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId(), true, engine,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				LocalizedMapUtil.getLocalizedMap(name),
				ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION,
				script, Collections.emptyList());

			Assert.fail();
		}
		catch (PortalException portalException) {
			Assert.assertTrue(
				expectedExceptionClass.isInstance(portalException));

			String actualMessage = portalException.getMessage();

			if (portalException instanceof
					ObjectValidationRuleScriptException) {

				ObjectValidationRuleScriptException
					objectValidationRuleScriptException =
						(ObjectValidationRuleScriptException)portalException;

				actualMessage =
					objectValidationRuleScriptException.getMessageKey();
			}

			Assert.assertEquals(expectedMessage, actualMessage);
		}
	}

	private ObjectValidationRule _testAddObjectValidationRuleSuccess(
			String engine, String script)
		throws Exception {

		Map<Locale, String> errorLabelMap = LocalizedMapUtil.getLocalizedMap(
			RandomTestUtil.randomString());
		Map<Locale, String> nameLabelMap = LocalizedMapUtil.getLocalizedMap(
			RandomTestUtil.randomString());

		ObjectValidationRule objectValidationRule =
			_objectValidationRuleLocalService.addObjectValidationRule(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId(), true, engine,
				errorLabelMap, nameLabelMap,
				ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION,
				script, Collections.emptyList());

		Assert.assertTrue(objectValidationRule.isActive());
		Assert.assertEquals(engine, objectValidationRule.getEngine());
		Assert.assertEquals(
			errorLabelMap, objectValidationRule.getErrorLabelMap());
		Assert.assertEquals(nameLabelMap, objectValidationRule.getNameMap());
		Assert.assertEquals(script, objectValidationRule.getScript());

		return objectValidationRule;
	}

	private static final String _VALID_DDM_SCRIPT = "isEmailAddress(textField)";

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectValidationRuleLocalService _objectValidationRuleLocalService;

}