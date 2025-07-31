/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.DefaultAllTablesOrphanReferencesDataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.util.OrphanReferencesDataCleanupUtil;
import com.liferay.portal.test.log.LogCapture;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class DefaultAllTablesOrphanReferencesDataCleanupPreupgradeProcessTest
	extends BaseOrphanReferencesDataCleanupPreupgradeProcessTestCase {

	@Before
	public void setUp() {
		_companyId1 = RandomTestUtil.nextLong();
		_companyId2 = RandomTestUtil.nextLong();
	}

	@Override
	protected UnsafeRunnable<Exception> getInsertDataUnsafeRunnable() {
		return () -> {
			_insert(_companyId1);
			_insert(_companyId1);
			_insert(_companyId2);
		};
	}

	@Override
	protected UnsafeConsumer<LogCapture, Exception>
		getLogAssertionUnsafeConsumer() {

		return logCapture -> {
			List<String> messages = logCapture.getMessages();

			Assert.assertTrue(
				messages.contains(
					getExpectedMessage(
						2, "companyId", "Image", "companyId", "Company",
						_companyId1)));
			Assert.assertTrue(
				messages.contains(
					getExpectedMessage(
						1, "companyId", "Image", "companyId", "Company",
						_companyId2)));
			Assert.assertTrue(
				messages.contains(
					getExpectedMessage(
						2, "companyId", "Portlet", "companyId", "Company",
						_companyId1)));
			Assert.assertTrue(
				messages.contains(
					getExpectedMessage(
						1, "companyId", "Portlet", "companyId", "Company",
						_companyId2)));
		};
	}

	@Override
	protected String getLoggerClassName() {
		return OrphanReferencesDataCleanupUtil.class.getName();
	}

	@Override
	protected UpgradeProcess getUpgradeProcess() {
		return new DefaultAllTablesOrphanReferencesDataCleanupPreupgradeProcess(
			"companyId", "Company");
	}

	private void _insert(long companyId) throws Exception {
		db.runSQL(
			connection,
			StringBundler.concat(
				"insert into Image (mvccVersion, ctCollectionId, imageId, ",
				"companyId) values (0, 0, ", RandomTestUtil.nextLong(), ", ",
				companyId, ")"));

		db.runSQL(
			connection,
			StringBundler.concat(
				"insert into Portlet (mvccVersion, id_, companyId, portletId, ",
				"active_) values (0, ", RandomTestUtil.nextLong(), ", ",
				companyId, ", '", RandomTestUtil.randomString(),
				"', [$FALSE$])"));
	}

	private long _companyId1;
	private long _companyId2;

}