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
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class UpgradeDatabaseConnectionsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpgradeWithDatabasePartitionDisabled()
		throws UpgradeException {

		PropsTestUtil.setProps(
			HashMapBuilder.<String, Object>put(
				"database.partition.enabled", "false"
			).put(
				"database.partition.thread.pool.enabled", "true"
			).build());

		UpgradeProcess upgradeProcess = new VerifiableDummyUpgradeProcess();

		upgradeProcess.upgrade();
	}

	@Test
	public void testUpgradeWithDatabasePartitionEnabled()
		throws UpgradeException {

		PropsTestUtil.setProps(
			HashMapBuilder.<String, Object>put(
				"database.partition.enabled", "true"
			).put(
				"database.partition.thread.pool.enabled", "true"
			).build());

		UpgradeProcess upgradeProcess = new VerifiableDummyUpgradeProcess();

		upgradeProcess.upgrade();
	}

	private class VerifiableDummyUpgradeProcess extends DummyUpgradeProcess {

		@Override
		protected void process(UnsafeConsumer<Long, Exception> unsafeConsumer)
			throws Exception {

			boolean partitionConcurrencyEnabled = false;

			if (GetterUtil.getBoolean(
					PropsUtil.get("database.partition.enabled")) &&
				GetterUtil.getBoolean(
					PropsUtil.get("database.partition.thread.pool.enabled"),
					true)) {

				partitionConcurrencyEnabled = true;
			}

			if (partitionConcurrencyEnabled) {
				Assert.assertTrue(
					ProxyUtil.isProxyClass(connection.getClass()));
			}
			else {
				Assert.assertTrue(
					!ProxyUtil.isProxyClass(connection.getClass()));
			}
		}

	}

}