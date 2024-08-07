/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.upgrade.v4_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseCTUpgradeProcessTestCase;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author David Truong
 */
@RunWith(Arquillian.class)
public class DDMStructureVersionCTUpgradeProcessTest
	extends BaseCTUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField fieldSetDDMFormField = DDMFormTestUtil.createDDMFormField(
			"fieldset", "fieldset", "fieldset", "", false, false, false);

		fieldSetDDMFormField.addNestedDDMFormField(
			DDMFormTestUtil.createTextDDMFormField(
				"field1", false, false, false));
		fieldSetDDMFormField.addNestedDDMFormField(
			DDMFormTestUtil.createTextDDMFormField(
				"field2", false, false, false));
		fieldSetDDMFormField.setProperty(
			"rows",
			JSONUtil.putAll(
				JSONUtil.put(
					"columns",
					JSONUtil.put(
						JSONUtil.put(
							"fields", JSONUtil.put("field1")
						).put(
							"size", 12
						))),
				JSONUtil.put(
					"columns",
					JSONUtil.put(
						JSONUtil.put(
							"fields", JSONUtil.put("field2")
						).put(
							"size", 12
						)))));

		_ddmForm.addDDMFormField(fieldSetDDMFormField);

		_ddmStructure = DDMStructureTestUtil.addStructure(
			JournalArticle.class.getName(), _ddmForm);
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return _ddmStructure.getStructureVersion();
	}

	@Override
	protected CTService<?> getCTService() {
		return _ddmStructureVersionLocalService;
	}

	@Override
	protected void runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();
	}

	@Override
	protected CTModel<?> updateCTModel(CTModel<?> ctModel) throws Exception {
		DDMStructureVersion ddmStructureVersion = (DDMStructureVersion)ctModel;

		JSONObject definitionJSONObject = _jsonFactory.createJSONObject(
			ddmStructureVersion.getDefinition());

		JSONArray fieldsJSONArray = definitionJSONObject.getJSONArray("fields");

		JSONObject jsonObject = fieldsJSONArray.getJSONObject(0);

		jsonObject.put("required", true);

		ddmStructureVersion.setDescription(definitionJSONObject.toString());

		return _ddmStructureVersionLocalService.updateDDMStructureVersion(
			ddmStructureVersion);
	}

	private static final String _CLASS_NAME =
		"com.liferay.dynamic.data.mapping.internal.upgrade.v4_0_0." +
			"DDMStructureUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.dynamic.data.mapping.internal.upgrade.registry.DDMServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private DDMForm _ddmForm;

	@DeleteAfterTestRun
	private DDMStructure _ddmStructure;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@Inject
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private Portal _portal;

}