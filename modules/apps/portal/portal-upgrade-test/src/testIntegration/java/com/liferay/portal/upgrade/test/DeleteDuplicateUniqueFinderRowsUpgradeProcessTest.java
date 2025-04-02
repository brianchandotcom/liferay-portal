/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.db.index.IndexUpdaterUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PortalPreferences;
import com.liferay.portal.kernel.model.PortletItem;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletItemLocalServiceUtil;
import com.liferay.portal.kernel.service.TicketLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.DeleteDuplicateUniqueFinderRowsUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.social.kernel.model.SocialActivitySetting;
import com.liferay.social.kernel.service.SocialActivitySettingLocalServiceUtil;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge Avalos
 */
@RunWith(Arquillian.class)
public class DeleteDuplicateUniqueFinderRowsUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_connection = DataAccess.getConnection();

		_db = DBManagerUtil.getDB();
	}

	@Test
	public void testDuplicateRemovalProcessOnPortalPreferences()
		throws IOException, PortalException, SQLException {

		_dropIndexes("PortalPreferences", "ownerType");

		PortalPreferences portalPreferences =
			PortalPreferencesLocalServiceUtil.createPortalPreferences(1);

		portalPreferences.setOwnerId(1);
		portalPreferences.setOwnerType(1);

		PortalPreferencesLocalServiceUtil.addPortalPreferences(
			portalPreferences);

		portalPreferences.setPortalPreferencesId(2);

		PortalPreferencesLocalServiceUtil.addPortalPreferences(
			portalPreferences);

		_assertCount("PortalPreferences", false, "ownerType", "ownerId");

		_runUpgrade(
			"PortalPreferences", new String[] {"ownerType", "ownerId"}, null);

		_assertCount("PortalPreferences", true, "ownerType", "ownerId");

		IndexUpdaterUtil.updateAllIndexes();

		portalPreferences.setPortalPreferencesId(3);

		try {
			PortalPreferencesLocalServiceUtil.addPortalPreferences(
				portalPreferences);
			Assert.fail();
		}
		catch (Exception exception) {
		}
		finally {
			portalPreferences.setPortalPreferencesId(1);

			Assert.assertEquals(
				portalPreferences,
				PortalPreferencesLocalServiceUtil.getPortalPreferences(1));
		}
	}

	@Test
	public void testDuplicateRemovalProcessOnPortletItem()
		throws IOException, PortalException, SQLException {

		_dropIndexes("PortletItem", "groupId");

		PortletItem portletItem = PortletItemLocalServiceUtil.createPortletItem(
			1);

		portletItem.setGroupId(1);
		portletItem.setName("1");
		portletItem.setPortletId("1");
		portletItem.setClassNameId(1);

		PortletItemLocalServiceUtil.addPortletItem(portletItem);

		portletItem.setPortletItemId(2);

		PortletItemLocalServiceUtil.addPortletItem(portletItem);

		_assertCount(
			"PortletItem", false, "groupId", "classNameId", "portletId",
			"name");

		_runUpgrade(
			"PortletItem",
			new String[] {"groupId", "classNameId", "portletId", "name"}, null);

		_assertCount(
			"PortletItem", true, "groupId", "classNameId", "portletId", "name");

		IndexUpdaterUtil.updateAllIndexes();

		portletItem.setPortletItemId(3);

		try {
			PortletItemLocalServiceUtil.addPortletItem(portletItem);
			Assert.fail();
		}
		catch (Exception exception) {
		}
		finally {
			portletItem.setPortletItemId(1);

			Assert.assertEquals(
				portletItem, PortletItemLocalServiceUtil.getPortletItem(1));
		}
	}

	@Test
	public void testDuplicateRemovalProcessOnSocialActivitySetting()
		throws IOException, PortalException, SQLException {

		_dropIndexes("SocialActivitySetting", "groupId");

		SocialActivitySetting socialActivitySetting =
			SocialActivitySettingLocalServiceUtil.createSocialActivitySetting(
				1);

		socialActivitySetting.setCtCollectionId(1);
		socialActivitySetting.setGroupId(1);
		socialActivitySetting.setClassNameId(1);
		socialActivitySetting.setActivityType(1);
		socialActivitySetting.setName("1");

		SocialActivitySettingLocalServiceUtil.addSocialActivitySetting(
			socialActivitySetting);

		socialActivitySetting.setActivitySettingId(2);

		SocialActivitySettingLocalServiceUtil.addSocialActivitySetting(
			socialActivitySetting);

		_assertCount(
			"SocialActivitySetting", false, "groupId", "classNameId",
			"activityType", "name", "ctCollectionId");

		_runUpgrade(
			"SocialActivitySetting",
			new String[] {
				"groupId", "classNameId", "activityType", "name",
				"ctCollectionId"
			},
			null);

		_assertCount(
			"SocialActivitySetting", true, "groupId", "classNameId",
			"activityType", "name", "ctCollectionId");

		IndexUpdaterUtil.updateAllIndexes();

		socialActivitySetting.setActivitySettingId(3);

		try {
			SocialActivitySettingLocalServiceUtil.addSocialActivitySetting(
				socialActivitySetting);
			Assert.fail();
		}
		catch (Exception exception) {
		}
		finally {
			socialActivitySetting.setActivitySettingId(1);

			Assert.assertEquals(
				socialActivitySetting,
				SocialActivitySettingLocalServiceUtil.getSocialActivitySetting(
					1));
		}
	}

	@Test
	public void testDuplicateRemovalProcessOnTicket()
		throws IOException, PortalException, SQLException {

		_dropIndexes("Ticket", "key_");

		Ticket ticket = TicketLocalServiceUtil.createTicket(1);

		ticket.setKey("key_");

		TicketLocalServiceUtil.addTicket(ticket);

		ticket.setTicketId(2);

		TicketLocalServiceUtil.addTicket(ticket);

		_assertCount("Ticket", false, "key_");

		_runUpgrade("Ticket", new String[] {"key_"}, "ticketId asc");

		_assertCount("Ticket", true, "key_");

		IndexUpdaterUtil.updateAllIndexes();

		ticket.setTicketId(3);

		try {
			TicketLocalServiceUtil.addTicket(ticket);
			Assert.fail();
		}
		catch (Exception exception) {
		}
		finally {
			ticket.setTicketId(2);

			Assert.assertEquals(ticket, TicketLocalServiceUtil.getTicket(2));
		}
	}

	private void _assertCount(
			String tableName, boolean duplicatesRemoved, String... columns)
		throws SQLException {

		_companyLocalService.forEachCompany(
			company -> {
				try (PreparedStatement preparedStatement =
						_connection.prepareStatement(
							StringBundler.concat(
								"select count(*) from ", tableName,
								" group by ", String.join(", ", columns),
								" having count(*) > 1"));
					ResultSet resultSet = preparedStatement.executeQuery()) {

					if (!duplicatesRemoved) {
						Assert.assertTrue(resultSet.next());

						Assert.assertEquals(2, resultSet.getInt(1));

						return;
					}

					Assert.assertFalse(resultSet.next());
				}
			});
	}

	private void _dropIndexes(String tableName, String columnName)
		throws IOException, SQLException {

		try {
			_db.dropIndexes(_connection, tableName, columnName);
		}
		catch (SQLException sqlException) {
			throw new SQLException(sqlException);
		}
		catch (IOException ioException) {
			throw new IOException(ioException);
		}
	}

	private void _runUpgrade(
			String tableName, String[] columns, String orderByClause)
		throws UpgradeException {

		DeleteDuplicateUniqueFinderRowsUpgradeProcess upgradeProcess = null;

		if (orderByClause != null) {
			upgradeProcess = new DeleteDuplicateUniqueFinderRowsUpgradeProcess(
				tableName, columns, orderByClause);
		}
		else {
			upgradeProcess = new DeleteDuplicateUniqueFinderRowsUpgradeProcess(
				tableName, columns);
		}

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.kernel.upgrade." +
					"DuplicateIndexEntriesUpgradeProcess",
				LoggerTestUtil.OFF)) {

			upgradeProcess.upgrade();
		}
		catch (UpgradeException upgradeException) {
			throw new UpgradeException(upgradeException);
		}
		finally {
			EntityCacheUtil.clearCache();
		}
	}

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static Connection _connection;
	private static DB _db;

}