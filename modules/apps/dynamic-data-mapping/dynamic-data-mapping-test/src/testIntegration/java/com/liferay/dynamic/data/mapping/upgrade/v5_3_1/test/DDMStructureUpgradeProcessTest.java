/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.upgrade.v5_3_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseCTUpgradeProcessTestCase;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormSerializeUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Pedro Leite
 */
@RunWith(Arquillian.class)
public class DDMStructureUpgradeProcessTest
	extends BaseCTUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		_ddmStructure = DDMStructureTestUtil.addStructure(
			TestPropsValues.getGroupId(), JournalArticle.class.getName(),
			DDMFormTestUtil.createDDMForm(RandomTestUtil.randomString()));

		setDefinition(_ddmStructure);

		return _ddmStructure;
	}

	@Override
	protected CTService<?> getCTService() {
		return _ddmStructureLocalService;
	}

	@Override
	protected void runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();
	}

	protected void setDefinition(DDMStructure ddmStructure) {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				RandomTestUtil.randomString(), null,
				DDMFormFieldTypeConstants.FIELDSET, null, true, false, false));

		ddmStructure.setDefinition(
			DDMFormSerializeUtil.serialize(ddmForm, _jsonDDMFormSerializer));
	}

	@Override
	protected CTModel<?> updateCTModel(CTModel<?> ctModel) throws Exception {
		_ddmStructure = (DDMStructure)ctModel;

		setDefinition(_ddmStructure);

		return _ddmStructureLocalService.updateDDMStructure(_ddmStructure);
	}

	private static final String _CLASS_NAME =
		"com.liferay.dynamic.data.mapping.internal.upgrade.v5_3_1." +
			"DDMStructureUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.dynamic.data.mapping.internal.upgrade.registry.DDMServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@DeleteAfterTestRun
	private DDMStructure _ddmStructure;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@Inject(filter = "ddm.form.serializer.type=json")
	private DDMFormSerializer _jsonDDMFormSerializer;

}