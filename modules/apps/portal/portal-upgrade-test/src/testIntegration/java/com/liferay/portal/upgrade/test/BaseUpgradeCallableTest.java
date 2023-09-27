/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.BaseUpgradeCallable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class BaseUpgradeCallableTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testInheritCompanyThreadLocal() throws Exception {
		UpgradeProcess upgradeProcess = new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
				try (SafeCloseable safeCloseable =
						CompanyThreadLocal.setWithSafeCloseable(
							PortalUtil.getDefaultCompanyId())) {

					ExecutorService executorService =
						Executors.newFixedThreadPool(1);

					Future<Long> future = executorService.submit(
						new UpgradeCallable());

					executorService.shutdown();

					Assert.assertEquals(
						CompanyThreadLocal.getCompanyId(), future.get());
				}
			}

		};

		upgradeProcess.upgrade();
	}

	private class UpgradeCallable extends BaseUpgradeCallable<Long> {

		@Override
		protected Long doCall() throws Exception {
			return CompanyThreadLocal.getCompanyId();
		}

	}

}