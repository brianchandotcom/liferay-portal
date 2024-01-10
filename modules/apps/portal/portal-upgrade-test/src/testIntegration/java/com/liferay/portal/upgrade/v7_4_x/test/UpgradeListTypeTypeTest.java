/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.v7_4_x.UpgradeListTypeType;
import com.liferay.portal.util.PortalInstances;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge Avalos
 */
@RunWith(Arquillian.class)
public class UpgradeListTypeTypeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws PortalException {
		long companyId = PortalInstances.getDefaultCompanyId();

		_listTypeLocalService.addListType(
			companyId, _LIST_TYPE_NAME, _LIST_TYPE_TYPE);
	}

	@Test
	public void testUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = new UpgradeListTypeType();

		upgradeProcess.upgrade();
	}

	private static final String _LIST_TYPE_NAME = "intranet";

	private static final String _LIST_TYPE_TYPE =
		"com.liferay.account.model.AccountEntry.address";

	@Inject
	private ListTypeLocalService _listTypeLocalService;

}