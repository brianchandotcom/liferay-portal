/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.dynamic.data.mapping.form.field.type.internal.multi.select.picklist;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.test.util.BaseDDMFormFieldTemplateContextContributorTestCase;
import com.liferay.object.dynamic.data.mapping.form.field.type.constants.ObjectDDMFormFieldTypeConstants;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Pedro Leite
 */
public class MultiSelectPicklistDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTemplateContextContributorTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetLocalizedObjectFieldTrue() {
		_ddmFormField.setDDMForm(getDDMForm());
		_ddmFormField.setProperty("localizedObjectField", true);

		Assert.assertTrue(
			MapUtil.getBoolean(
				_multiSelectPicklistDDMFormFieldTemplateContextContributor.
					getParameters(
						_ddmFormField, createDDMFormFieldRenderingContext()),
				"localizedObjectField"));
	}

	private final DDMFormField _ddmFormField = new DDMFormField(
		"field", ObjectDDMFormFieldTypeConstants.MULTISELECT_PICKLIST);
	private final MultiSelectPicklistDDMFormFieldTemplateContextContributor
		_multiSelectPicklistDDMFormFieldTemplateContextContributor =
			new MultiSelectPicklistDDMFormFieldTemplateContextContributor();

}