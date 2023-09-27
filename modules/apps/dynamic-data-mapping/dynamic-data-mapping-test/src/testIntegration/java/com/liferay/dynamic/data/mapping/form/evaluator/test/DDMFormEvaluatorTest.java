/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateRequest;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateResponse;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorFieldContextKey;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Leonardo Barros
 */
@RunWith(Arquillian.class)
public class DDMFormEvaluatorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testConcatFunction() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			"result", "text1", "text2", "text3");

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"result",
				new LocalizedValue() {
					{
						addString(LocaleUtil.US, null);
					}
				});

		ddmFormFieldValue.setInstanceId("instanceId");

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"text1",
				new LocalizedValue() {
					{
						addString(LocaleUtil.US, "How");
					}
				}));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"text2",
				new LocalizedValue() {
					{
						addString(LocaleUtil.US, "are");
					}
				}));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"text3",
				new LocalizedValue() {
					{
						addString(LocaleUtil.US, "you");
					}
				}));

		ddmForm.addDDMFormRule(
			new DDMFormRule() {
				{
					setActions(
						ListUtil.fromArray(
							"setValue('result',concat(getValue('text1'), " +
								"getValue('text2'),getValue('text3'),'?'))"));
					setCondition("TRUE");
					setEnabled(true);
				}
			});

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			_ddmFormEvaluator.evaluate(
				DDMFormEvaluatorEvaluateRequest.Builder.newBuilder(
					ddmForm, ddmFormValues, LocaleUtil.US
				).build());

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges =
				ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges();

		Map<String, Object> map = ddmFormFieldsPropertyChanges.get(
			new DDMFormEvaluatorFieldContextKey("result", "instanceId"));

		Assert.assertEquals("Howareyou?", map.get("value"));
	}

	@Inject
	private DDMFormEvaluator _ddmFormEvaluator;

}