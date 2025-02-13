/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.upgrade.v5_4_4.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormSerializeUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Yuri Monteiro
 */
@RunWith(Arquillian.class)
public class DDMStructureUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpgrade() throws Exception {
		DDMForm ddmForm1 = DDMFormTestUtil.createDDMForm("textField");

		ddmForm1.setDDMFormRules(
			Collections.singletonList(
				new DDMFormRule(Arrays.asList("Action"), "Condition")));

		DDMStructure ddmStructure1 = _createDDMStructure(ddmForm1);

		DDMStructureVersion ddmStructureVersion =
			ddmStructure1.getLatestStructureVersion();

		ddmStructureVersion.setDefinition(
			DDMFormSerializeUtil.serialize(ddmForm1, _jsonDDMFormSerializer));

		DDMStructureVersionLocalServiceUtil.updateDDMStructureVersion(
			ddmStructureVersion);

		DDMForm ddmForm2 = DDMFormTestUtil.createDDMForm("textField2");

		ddmForm2.setDDMFormRules(
			Collections.singletonList(
				new DDMFormRule(
					Arrays.asList("setVisible(\"textField2\", false)"),
					"\"TRUE\"")));

		_createDDMStructure(ddmForm2);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.WARN)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"Unable to normalize form rule conditions for DDM ",
					"Structure with ID ", ddmStructure1.getStructureId()),
				logEntry.getMessage());
		}
	}

	private DDMStructure _createDDMStructure(DDMForm ddmForm) throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			DDMFormInstance.class.getName(), ddmForm);

		ddmStructure.setDDMForm(ddmForm);

		return ddmStructure;
	}

	private static final String _CLASS_NAME =
		"com.liferay.dynamic.data.mapping.internal.upgrade.v5_4_4." +
			"DDMStructureUpgradeProcess";

	@Inject(filter = "ddm.form.serializer.type=json")
	private DDMFormSerializer _jsonDDMFormSerializer;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject(
		filter = "component.name=com.liferay.dynamic.data.mapping.internal.upgrade.registry.DDMServiceUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}