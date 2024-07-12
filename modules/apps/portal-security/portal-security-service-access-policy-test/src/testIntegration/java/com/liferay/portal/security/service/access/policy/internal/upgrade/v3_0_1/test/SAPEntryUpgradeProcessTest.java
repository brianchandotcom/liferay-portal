/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.service.access.policy.internal.upgrade.v3_0_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.security.service.access.policy.configuration.SAPConfiguration;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Christopher Kian
 */
@RunWith(Arquillian.class)
public class SAPEntryUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testUpgradeProcessSystemRestClientTemplateObjectSAPEntry()
		throws Exception {

		Company company = CompanyTestUtil.addCompany();

		SAPEntry systemRestClientTemplateObjectSAPEntry =
			_sapEntryLocalService.fetchSAPEntry(
				company.getCompanyId(),
				_sapConfiguration.systemRestClientTemplateObjectSAPEntryName());

		if (systemRestClientTemplateObjectSAPEntry != null) {
			_sapEntryLocalService.deleteSAPEntry(
				systemRestClientTemplateObjectSAPEntry);
		}

		_runUpgrade();

		Assert.assertNotNull(
			_sapEntryLocalService.fetchSAPEntry(
				company.getCompanyId(),
				_sapConfiguration.
					systemRestClientTemplateObjectSAPEntryName()));
	}

	private void _runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.portal.security.service.access.policy.internal.upgrade." +
			"v3_0_1.SAPEntryUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.portal.security.service.access.policy.internal.upgrade.registry.SAPServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private volatile SAPConfiguration _sapConfiguration =
		ConfigurableUtil.createConfigurable(
			SAPConfiguration.class, Collections.emptyMap());

	@Inject
	private SAPEntryLocalService _sapEntryLocalService;

}