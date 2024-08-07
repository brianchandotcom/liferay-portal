/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.upgrade.v5_4_1.test;

import com.liferay.change.tracking.test.util.BaseCTUpgradeProcessTestCase;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormSerializeUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Feliphe Marinho
 */
public abstract class BaseDDMFormCTUpgradeProcessTestCase
	extends BaseCTUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	public Class<?> getModelClass() {
		return null;
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		ddmStructure = DDMStructureTestUtil.addStructure(
			"com.liferay.dynamic.data.mapping.model.DDMFormInstance",
			_createDDMForm());

		if (getModelClass() == DDMStructureVersion.class) {
			return ddmStructure.getStructureVersion();
		}

		return ddmStructure;
	}

	@Override
	protected CTService<?> getCTService() {
		if (getModelClass() == DDMStructureVersion.class) {
			return _ddmStructureVersionLocalService;
		}

		return _ddmStructureLocalService;
	}

	@Override
	protected void runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.dynamic.data.mapping.internal.upgrade.v5_4_1." +
				"DDMStructureUpgradeProcess");

		upgradeProcess.upgrade();
	}

	@Override
	protected CTModel<?> updateCTModel(CTModel<?> ctModel) throws Exception {
		String definition = DDMFormSerializeUtil.serialize(
			_createDDMForm(), _jsonDDMFormSerializer);

		if (getModelClass() == DDMStructureVersion.class) {
			DDMStructureVersion ddmStructureVersion =
				(DDMStructureVersion)ctModel;

			ddmStructureVersion.setDefinition(definition);

			return _ddmStructureVersionLocalService.updateDDMStructureVersion(
				ddmStructureVersion);
		}

		DDMStructure ddmStructure = (DDMStructure)ctModel;

		ddmStructure.setDefinition(definition);

		return _ddmStructureLocalService.updateDDMStructure(ddmStructure);
	}

	@DeleteAfterTestRun
	protected DDMStructure ddmStructure;

	private DDMForm _createDDMForm() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField1 = DDMFormTestUtil.createDDMFormField(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			DDMFormFieldTypeConstants.NUMERIC, "integer", false, false, false);

		ddmFormField1.setProperty("confirmationErrorMessage", null);
		ddmFormField1.setProperty("confirmationLabel", null);
		ddmFormField1.setProperty("requireConfirmation", true);

		ddmForm.addDDMFormField(ddmFormField1);

		DDMFormField ddmFormField2 = DDMFormTestUtil.createDDMFormField(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			DDMFormFieldTypeConstants.TEXT, "string", false, false, false);

		ddmFormField2.setProperty("confirmationErrorMessage", null);
		ddmFormField2.setProperty("confirmationLabel", null);
		ddmFormField2.setProperty("requireConfirmation", true);

		ddmForm.addDDMFormField(ddmFormField2);

		return ddmForm;
	}

	@Inject(
		filter = "(&(component.name=com.liferay.dynamic.data.mapping.internal.upgrade.registry.DDMServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@Inject
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Inject(filter = "ddm.form.serializer.type=json")
	private DDMFormSerializer _jsonDDMFormSerializer;

	@Inject
	private JSONFactory _jsonFactory;

}