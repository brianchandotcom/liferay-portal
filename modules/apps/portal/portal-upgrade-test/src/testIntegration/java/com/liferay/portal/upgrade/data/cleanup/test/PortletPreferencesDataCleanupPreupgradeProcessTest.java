/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.data.cleanup.util.OrphanReferencesDataCleanupUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.data.cleanup.PortletPreferencesDataCleanupPreupgradeProcess;

import java.sql.Connection;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@DataGuard(autoDelete = false, scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class PortletPreferencesDataCleanupPreupgradeProcessTest
	extends PortletPreferencesDataCleanupPreupgradeProcess {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpgrade() throws Exception {
		long layoutRevisionId = RandomTestUtil.nextLong();
		long plid1 = RandomTestUtil.nextLong();
		String portletId1 = "com.liferay." + RandomTestUtil.randomString();
		String portletId2 = "com.liferay." + RandomTestUtil.randomString();
		long portletPreferencesId1 = RandomTestUtil.nextLong();
		long portletPreferencesId2 = RandomTestUtil.nextLong();
		long portletPreferenceValueId1 = RandomTestUtil.nextLong();
		long portletPreferenceValueId2 = RandomTestUtil.nextLong();

		runSQL(
			StringBundler.concat(
				"insert into LayoutRevision (mvccVersion, layoutRevisionId, ",
				"companyId, plid) values (0, ", layoutRevisionId, ", ",
				CompanyThreadLocal.getCompanyId(), ", ",
				RandomTestUtil.nextLong(), ")"));

		runSQL(
			StringBundler.concat(
				"insert into PortletPreferences (mvccVersion, ctCollectionId, ",
				"portletPreferencesId, companyId, plid, portletId) values (0, ",
				"0, ", portletPreferencesId1, ", ",
				CompanyThreadLocal.getCompanyId(), ", ", plid1, ", '",
				portletId1, "')"));

		runSQL(
			StringBundler.concat(
				"insert into PortletPreferences (mvccVersion, ctCollectionId, ",
				"portletPreferencesId, companyId, plid, portletId) values (0, ",
				"0, ", portletPreferencesId2, ", ",
				CompanyThreadLocal.getCompanyId(), ", ", layoutRevisionId,
				", '", portletId2, "')"));

		runSQL(
			StringBundler.concat(
				"insert into PortletPreferenceValue (mvccVersion, ",
				"ctCollectionId, portletPreferenceValueId, companyId, ",
				"portletPreferencesId, name) values (0, 0, ",
				portletPreferenceValueId1, ", ",
				CompanyThreadLocal.getCompanyId(), ", ", portletPreferencesId1,
				", '", portletId1, "')"));

		runSQL(
			StringBundler.concat(
				"insert into PortletPreferenceValue (mvccVersion, ",
				"ctCollectionId, portletPreferenceValueId, companyId, ",
				"portletPreferencesId, name) values (0, 0, ",
				portletPreferenceValueId2, ", ",
				CompanyThreadLocal.getCompanyId(), ", ", portletPreferencesId2,
				", '", portletId2, "')"));

		try (Connection connection = DataAccess.getConnection();
			LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				OrphanReferencesDataCleanupUtil.class.getName(),
				LoggerTestUtil.INFO)) {

			upgrade();

			List<String> messages = logCapture.getMessages();

			Assert.assertEquals(messages.toString(), 2, messages.size());

			DBInspector dbInspector = new DBInspector(connection);

			Assert.assertFalse(
				messages.toString(),
				messages.contains(
					StringBundler.concat(
						"Table ",
						dbInspector.normalizeName("PortletPreferences"),
						", 1 row deleted because ",
						dbInspector.normalizeName("plid"), StringPool.SPACE,
						layoutRevisionId, " was not found in column ",
						dbInspector.normalizeName("plid"), " from table ",
						dbInspector.normalizeName("Layout"))));

			Assert.assertFalse(
				messages.toString(),
				messages.contains(
					StringBundler.concat(
						"Table ",
						dbInspector.normalizeName("PortletPreferenceValue"),
						", 1 row deleted because ",
						dbInspector.normalizeName("portletPreferencesId"),
						StringPool.SPACE, portletPreferencesId2,
						" was not found in column ",
						dbInspector.normalizeName("portletPreferencesId"),
						" from table ",
						dbInspector.normalizeName("PortletPreferences"))));

			Assert.assertTrue(
				messages.toString(),
				messages.contains(
					StringBundler.concat(
						"Table ",
						dbInspector.normalizeName("PortletPreferences"),
						", 1 row deleted because ",
						dbInspector.normalizeName("plid"), StringPool.SPACE,
						plid1, " was not found in column ",
						dbInspector.normalizeName("plid"), " from table ",
						dbInspector.normalizeName("Layout"))));

			Assert.assertTrue(
				messages.toString(),
				messages.contains(
					StringBundler.concat(
						"Table ",
						dbInspector.normalizeName("PortletPreferenceValue"),
						", 1 row deleted because ",
						dbInspector.normalizeName("portletPreferencesId"),
						StringPool.SPACE, portletPreferencesId1,
						" was not found in column ",
						dbInspector.normalizeName("portletPreferencesId"),
						" from table ",
						dbInspector.normalizeName("PortletPreferences"))));
		}
		finally {
			runSQL(
				"delete from LayoutRevision where layoutRevisionId = " +
					layoutRevisionId);
			runSQL(
				"delete from PortletPreferences where portletPreferencesId = " +
					portletPreferencesId1);
			runSQL(
				"delete from PortletPreferences where portletPreferencesId = " +
					portletPreferencesId2);
			runSQL(
				"delete from PortletPreferenceValue where " +
					"portletPreferenceValueId = " + portletPreferenceValueId1);
			runSQL(
				"delete from PortletPreferenceValue where " +
					"portletPreferenceValueId = " + portletPreferenceValueId2);
		}
	}

}