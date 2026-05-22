/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.service.access.policy.internal.upgrade.v3_0_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
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

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					company.getCompanyId())) {

			SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
				company.getCompanyId(),
				_sapConfiguration.systemRESTClientTemplateObjectSAPEntryName());

			if (sapEntry != null) {
				_sapEntryLocalService.deleteSAPEntry(sapEntry);
			}

			_runUpgrade();

			sapEntry = _sapEntryLocalService.fetchSAPEntry(
				company.getCompanyId(),
				_sapConfiguration.systemRESTClientTemplateObjectSAPEntryName());

			Assert.assertNotNull(sapEntry);

			Role role = RoleLocalServiceUtil.getRole(
				company.getCompanyId(), RoleConstants.GUEST);

			Assert.assertTrue(
				_resourcePermissionLocalService.hasResourcePermission(
					company.getCompanyId(), SAPEntry.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(sapEntry.getSapEntryId()), role.getRoleId(),
					ActionKeys.VIEW));
		}
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

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	private volatile SAPConfiguration _sapConfiguration =
		ConfigurableUtil.createConfigurable(
			SAPConfiguration.class, Collections.emptyMap());

	@Inject
	private SAPEntryLocalService _sapEntryLocalService;

	@Inject(
		filter = "(&(component.name=com.liferay.portal.security.service.access.policy.internal.upgrade.registry.SAPServiceUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}