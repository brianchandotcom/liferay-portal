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

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeCallable;
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
public class UpgradeCallableTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testInheritCompanyThreadLocal() throws Exception {
		UpgradeProcess upgradeProcess = new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
				try (SafeClosable safeClosable =
						CompanyThreadLocal.setWithSafeClosable(
							PortalUtil.getDefaultCompanyId())) {

					ExecutorService executorService =
						Executors.newFixedThreadPool(1);

					Future<Long> future = executorService.submit(
						new UpgradeCallableTestClass());

					executorService.shutdown();

					Assert.assertEquals(
						CompanyThreadLocal.getCompanyId(), future.get());
				}
			}

		};

		upgradeProcess.upgrade();
	}

	private class UpgradeCallableTestClass extends UpgradeCallable<Long> {

		@Override
		public Long doCall() throws Exception {
			return CompanyThreadLocal.getCompanyId();
		}

	}

}