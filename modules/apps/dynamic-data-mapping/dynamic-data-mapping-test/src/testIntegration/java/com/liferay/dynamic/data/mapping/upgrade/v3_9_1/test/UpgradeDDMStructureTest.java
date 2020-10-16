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

package com.liferay.dynamic.data.mapping.upgrade.v3_9_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormInstanceTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcos Martins
 */
@RunWith(Arquillian.class)
public class UpgradeDDMStructureTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_userId = TestPropsValues.getUserId();

		setUpUpgradeDDMStructure();
	}

	@Test
	public void testUpgradeDDMFormFieldOptionReferences() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField checkboxMultipleDDMFormField =
			DDMFormTestUtil.createDDMFormField(
				"checkboxMultiple", "checkboxMultiple", "checkbox_multiple",
				"string", false, false, false);

		DDMFormFieldOptions checkboxMultipleDDMFormFieldOptions =
			checkboxMultipleDDMFormField.getDDMFormFieldOptions();

		checkboxMultipleDDMFormFieldOptions.addOptionLabel(
			"option0", LocaleUtil.US, "option0");
		checkboxMultipleDDMFormFieldOptions.addOptionLabel(
			"option1", LocaleUtil.US, "option1");

		ddmForm.addDDMFormField(checkboxMultipleDDMFormField);

		DDMFormInstance ddmFormInstance =
			DDMFormInstanceTestUtil.addDDMFormInstance(
				ddmForm, _group, _userId);

		_upgradeDDMStructure.upgrade();

		ddmForm = _getDDMFormPostUpgrade(ddmFormInstance);

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		ddmFormFields.forEach(
			ddmFormField -> _assertDDMFormFieldOptions(
				ddmFormField.getDDMFormFieldOptions()));
	}

	@Test
	public void testUpgradeDDMFormFieldReferences() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("field1");

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		ddmFormFields.forEach(
			ddmFormField -> {
				if (ddmFormField.getProperty("fieldReference") != null) {
					ddmFormField.setProperty("fieldReference", null);
				}
			});

		DDMFormInstance ddmFormInstance =
			DDMFormInstanceTestUtil.addDDMFormInstance(
				ddmForm, _group, _userId);

		_upgradeDDMStructure.upgrade();

		ddmForm = _getDDMFormPostUpgrade(ddmFormInstance);

		ddmFormFields = ddmForm.getDDMFormFields();

		ddmFormFields.forEach(
			ddmFormField -> Assert.assertEquals(
				ddmFormField.getName(),
				ddmFormField.getProperty("fieldReference")));
	}

	@Test
	public void testUpgradeGridDDMFormFieldOptionReferences() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField gridDDMFormField = DDMFormTestUtil.createDDMFormField(
			"grid", "grid", "grid", "string", false, false, false);

		DDMFormFieldOptions gridColumnDDMFormFieldOptions =
			new DDMFormFieldOptions();

		gridColumnDDMFormFieldOptions.addOptionLabel(
			"column0", LocaleUtil.US, "column0");
		gridColumnDDMFormFieldOptions.addOptionLabel(
			"column1", LocaleUtil.US, "column1");

		gridDDMFormField.setProperty("columns", gridColumnDDMFormFieldOptions);

		DDMFormFieldOptions gridRowDDMFormFieldOptions =
			new DDMFormFieldOptions();

		gridRowDDMFormFieldOptions.addOptionLabel(
			"row0", LocaleUtil.US, "row0");
		gridRowDDMFormFieldOptions.addOptionLabel(
			"row1", LocaleUtil.US, "row1");

		gridDDMFormField.setProperty("rows", gridRowDDMFormFieldOptions);

		ddmForm.addDDMFormField(gridDDMFormField);

		DDMFormInstance ddmFormInstance =
			DDMFormInstanceTestUtil.addDDMFormInstance(
				ddmForm, _group, _userId);

		_upgradeDDMStructure.upgrade();

		ddmForm = _getDDMFormPostUpgrade(ddmFormInstance);

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		ddmFormFields.forEach(
			ddmFormField -> {
				_assertDDMFormFieldOptions(
					(DDMFormFieldOptions)ddmFormField.getProperty("columns"));
				_assertDDMFormFieldOptions(
					(DDMFormFieldOptions)ddmFormField.getProperty("rows"));
			});
	}

	@Test
	public void testUpgradeNestedDDMFormFieldReference() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField textDDMFormField = DDMFormTestUtil.createTextDDMFormField(
			"field1", false, false, false);

		textDDMFormField.addNestedDDMFormField(
			DDMFormTestUtil.createTextDDMFormField(
				"field2", false, false, false));

		ddmForm.addDDMFormField(textDDMFormField);

		DDMFormInstance ddmFormInstance =
			DDMFormInstanceTestUtil.addDDMFormInstance(
				ddmForm, _group, _userId);

		_upgradeDDMStructure.upgrade();

		ddmForm = _getDDMFormPostUpgrade(ddmFormInstance);

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		ddmFormFields.forEach(
			ddmFormField -> {
				List<DDMFormField> nestedDDMFormFields =
					ddmFormField.getNestedDDMFormFields();

				nestedDDMFormFields.forEach(
					nestedDDMFormField -> Assert.assertEquals(
						nestedDDMFormField.getName(),
						nestedDDMFormField.getProperty("fieldReference")));
			});
	}

	protected void setUpUpgradeDDMStructure() {
		_upgradeStepRegistrator.register(
			new UpgradeStepRegistrator.Registry() {

				@Override
				public void register(
					String fromSchemaVersionString,
					String toSchemaVersionString, UpgradeStep... upgradeSteps) {

					for (UpgradeStep upgradeStep : upgradeSteps) {
						Class<?> clazz = upgradeStep.getClass();

						String className = clazz.getName();

						if (className.contains(_CLASS_NAME)) {
							_upgradeDDMStructure = (UpgradeProcess)upgradeStep;
						}
					}
				}

			});
	}

	private void _assertDDMFormFieldOptions(
		DDMFormFieldOptions ddmFormFieldOptions) {

		Set<String> optionsValues = ddmFormFieldOptions.getOptionsValues();

		optionsValues.forEach(
			optionValue -> Assert.assertEquals(
				optionValue,
				ddmFormFieldOptions.getOptionReference(optionValue)));
	}

	private DDMForm _getDDMFormPostUpgrade(DDMFormInstance ddmFormInstance)
		throws Exception {

		EntityCacheUtil.clearCache();

		ddmFormInstance = DDMFormInstanceLocalServiceUtil.getDDMFormInstance(
			ddmFormInstance.getFormInstanceId());

		return ddmFormInstance.getDDMForm();
	}

	private static final String _CLASS_NAME =
		"com.liferay.dynamic.data.mapping.internal.upgrade.v3_9_1." +
			"UpgradeDDMStructure";

	@Inject(
		filter = "(&(objectClass=com.liferay.dynamic.data.mapping.internal.upgrade.DDMServiceUpgrade))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@DeleteAfterTestRun
	private Group _group;

	private UpgradeProcess _upgradeDDMStructure;
	private long _userId;

}