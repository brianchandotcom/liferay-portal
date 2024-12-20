/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model;

import com.liferay.dynamic.data.mapping.test.util.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Pedro Leite
 */
public class DDMFormInstanceSettingsTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCreateDDMFormInstanceSettingsDDMForm() {
		DDMForm ddmForm = DDMFormFactory.create(DDMFormInstanceSettings.class);

		List<DDMFormRule> ddmFormRules = ddmForm.getDDMFormRules();

		Assert.assertEquals(ddmFormRules.toString(), 4, ddmFormRules.size());

		DDMFormRule ddmFormRule0 = ddmFormRules.get(0);

		Assert.assertEquals("TRUE", ddmFormRule0.getCondition());

		List<String> actions = ddmFormRule0.getActions();

		Assert.assertEquals(actions.toString(), 10, actions.size());
		Assert.assertEquals(
			"setEnabled('expirationDate', equals(getValue('neverExpire'), " +
				"FALSE))",
			actions.get(0));
		Assert.assertEquals(
			"setVisible('emailFromAddress', getValue('sendEmailNotification'))",
			actions.get(1));
		Assert.assertEquals(
			"setVisible('emailFromName', getValue('sendEmailNotification'))",
			actions.get(2));
		Assert.assertEquals(
			"setVisible('emailSubject', getValue('sendEmailNotification'))",
			actions.get(3));
		Assert.assertEquals(
			"setVisible('emailToAddress', getValue('sendEmailNotification'))",
			actions.get(4));
		Assert.assertEquals(
			"setVisible('limitToOneSubmissionPerUserBody', getValue('" +
				"limitToOneSubmissionPerUser'))",
			actions.get(5));
		Assert.assertEquals(
			"setVisible('limitToOneSubmissionPerUserHeader', getValue('" +
				"limitToOneSubmissionPerUser'))",
			actions.get(6));
		Assert.assertEquals(
			"setVisible('objectDefinitionId', contains(getValue('storageType'" +
				"), \"object\"))",
			actions.get(7));
		Assert.assertEquals("setVisible('published', FALSE)", actions.get(8));
		Assert.assertEquals(
			"setVisible('workflowDefinition', not(contains(getValue('" +
				"storageType'), \"object\")))",
			actions.get(9));

		DDMFormRule ddmFormRule1 = ddmFormRules.get(1);

		Assert.assertEquals(
			"equals(getValue('neverExpire'), TRUE)",
			ddmFormRule1.getCondition());

		actions = ddmFormRule1.getActions();

		Assert.assertEquals(actions.toString(), 1, actions.size());
		Assert.assertEquals("setValue('expirationDate', '')", actions.get(0));

		DDMFormRule ddmFormRule2 = ddmFormRules.get(2);

		Assert.assertEquals(
			"contains(getValue('storageType'), \"object\")",
			ddmFormRule2.getCondition());

		actions = ddmFormRule2.getActions();

		Assert.assertEquals(actions.toString(), 3, actions.size());
		Assert.assertEquals(
			"setEnabled('autosaveEnabled', FALSE)", actions.get(0));
		Assert.assertEquals(
			"setValue('autosaveEnabled', FALSE)", actions.get(1));
		Assert.assertEquals(
			"setValue('workflowDefinition', [\"no-workflow\"])",
			actions.get(2));

		DDMFormRule ddmFormRule3 = ddmFormRules.get(3);

		Assert.assertEquals(
			"not(contains(getValue('storageType'), \"object\"))",
			ddmFormRule3.getCondition());

		actions = ddmFormRule3.getActions();

		Assert.assertEquals(actions.toString(), 1, actions.size());
		Assert.assertEquals(
			"setEnabled('autosaveEnabled', TRUE)", actions.get(0));
	}

}