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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.settings.ArchivedSettings;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsDescriptor;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletPreferences;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class UpgradePortletSettingsTest extends UpgradePortletSettings {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	public UpgradePortletSettingsTest() {
		super(new MockSettingsFactory());
	}

	@After
	public void tearDown() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("delete from PortletPreferences where portletId = '");
		sb.append(_PORTLET_ID);
		sb.append("' or portletId = '");
		sb.append(_SERVICE_NAME);
		sb.append("'");

		runSQL(sb.toString());
	}

	@Test
	public void testCopyAndNotResetServiceSettings() throws Exception {
		Map<String, String> servicePreferences = new HashMap<>();

		servicePreferences.put(_KEY, _VALUE);
		servicePreferences.put(_SERVICE_KEY, _SERVICE_VALUE);

		Map<String, String> portletPreferences = new HashMap<>();

		portletPreferences.put(_KEY, _VALUE);

		_upgradeTest(
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _PORTLET_ID, false,
			servicePreferences, portletPreferences,
			new HashMap<>(portletPreferences), true);
	}

	@Test
	public void testCopyAndResetServiceSettings() throws Exception {
		Map<String, String> servicePreferences = new HashMap<>();

		servicePreferences.put(_SERVICE_KEY, _SERVICE_VALUE);

		Map<String, String> portletPreferences = new HashMap<>();

		portletPreferences.put(_KEY, _VALUE);

		_upgradeTest(
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _PORTLET_ID, true,
			servicePreferences, portletPreferences,
			new HashMap<>(portletPreferences), true);
	}

	@Test
	public void testCopyNonLayoutPreferences() throws Exception {
		Map<String, String> servicePreferences = new HashMap<>();

		servicePreferences.put(_SERVICE_KEY, _SERVICE_VALUE);

		Map<String, String> portletPreferences = new HashMap<>();

		portletPreferences.put(_KEY, _VALUE);

		_upgradeTest(
			PortletKeys.PREFS_OWNER_TYPE_COMPANY,
			PortletKeys.PREFS_OWNER_TYPE_COMPANY, _PORTLET_ID, true,
			servicePreferences, portletPreferences,
			new HashMap<>(portletPreferences), true);
	}

	@Test
	public void testUpgradeDisplayPortlet() throws Exception {
		Map<String, String> preferences = new HashMap<>();

		preferences.put(_KEY, _VALUE);

		_upgradeTest(
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _PORTLET_ID, true, null,
			preferences, new HashMap<>(preferences), false);
	}

	@Test
	public void testUpgradeDisplayPortletOtherPortlet() throws Exception {
		Map<String, String> preferences = new HashMap<>();

		preferences.put(_KEY, _VALUE);
		preferences.put(_SERVICE_KEY, _SERVICE_VALUE);

		_upgradeTest(
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, "other_portlet_id", true, null,
			preferences, new HashMap<>(preferences), false);
	}

	@Test
	public void testUpgradeOtherOwnerType() throws Exception {
		Map<String, String> preferences = new HashMap<>();

		preferences.put(_KEY, _VALUE);
		preferences.put(_SERVICE_KEY, _SERVICE_VALUE);

		Map<String, String> archivedPreferences = new HashMap<>();

		archivedPreferences.put(_KEY, _VALUE);

		_upgradeTest(
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			PortletKeys.PREFS_OWNER_TYPE_COMPANY, _PORTLET_ID, true,
			preferences, new HashMap<>(preferences), archivedPreferences, true);
	}

	@Test
	public void testUpgradeOtherPortlet() throws Exception {
		Map<String, String> preferences = new HashMap<>();

		preferences.put(_KEY, _VALUE);
		preferences.put(_SERVICE_KEY, _SERVICE_VALUE);

		_upgradeTest(
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			PortletKeys.PREFS_OWNER_TYPE_COMPANY, "other_portlet_id", true,
			preferences, new HashMap<>(preferences), new HashMap<>(preferences),
			true);
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (_upgradeMain) {
			upgradeMainPortlet(
				_upgradePortletId, _SERVICE_NAME, _upgradeOwnerType,
				_upgradeReset);
		}
		else {
			upgradeDisplayPortlet(
				_upgradePortletId, _SERVICE_NAME, _upgradeOwnerType);
		}
	}

	private void _addPortletPreference(
			int ownerType, long plid, String portletId)
		throws Exception {

		PortletPreferences jxPortletPreferences =
			PortletPreferencesFactoryUtil.fromDefaultXML(null);

		jxPortletPreferences.setValue(_KEY, _VALUE);
		jxPortletPreferences.setValue(_SERVICE_KEY, _SERVICE_VALUE);

		StringBundler sb = new StringBundler(13);

		sb.append("insert into PortletPreferences (mvccVersion, ");
		sb.append("portletPreferencesId, ownerId, ownerType, plid, ");
		sb.append("portletId, preferences) values (0, ");
		sb.append(CounterLocalServiceUtil.increment());
		sb.append(", 0, ");
		sb.append(ownerType);
		sb.append(", ");
		sb.append(plid);
		sb.append(", '");
		sb.append(portletId);
		sb.append("', '");
		sb.append(PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));
		sb.append("')");

		runSQL(sb.toString());
	}

	private void _assertCopy(
			int copyCount, Map<String, String> preferences, int ownerType,
			long groupId, long plid)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append("select count(*) from PortletPreferences where ");
		sb.append("ownerType = ");
		sb.append(PortletKeys.PREFS_OWNER_TYPE_GROUP);
		sb.append(" and portletId = '");
		sb.append(_SERVICE_NAME);
		sb.append("'");

		try (Connection connection = DataAccess.getUpgradeOptimizedConnection();
				PreparedStatement ps = connection.prepareStatement(
					sb.toString());
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Assert.assertEquals(copyCount, rs.getInt(1));
			}
		}

		if (copyCount == 0) {
			return;
		}

		sb.setStringAt(
			"select preferences, ownerId, plid from PortletPreferences where ",
			0);

		try (Connection connection = DataAccess.getUpgradeOptimizedConnection();
				PreparedStatement ps = connection.prepareStatement(
					sb.toString());
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				_assertPreferences(rs.getString("preferences"), preferences);

				if (ownerType == PortletKeys.PREFS_OWNER_TYPE_LAYOUT) {
					Assert.assertEquals(groupId, rs.getInt("ownerId"));
					Assert.assertEquals(0, rs.getInt("plid"));
				}
				else {
					Assert.assertEquals(0, rs.getInt("ownerId"));
					Assert.assertEquals(plid, rs.getInt("plid"));
				}
			}
		}
	}

	private void _assertPreferences(
		String preferencesString, Map<String, String> preferences) {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromDefaultXML(preferencesString);

		Enumeration<String> names = portletPreferences.getNames();

		if (preferences.isEmpty()) {
			Assert.assertFalse(names.hasMoreElements());
		}
		else {
			Assert.assertTrue(names.hasMoreElements());

			while (names.hasMoreElements()) {
				String name = names.nextElement();

				Assert.assertEquals(
					preferences.remove(name),
					portletPreferences.getValue(name, "NO_VALUE"));
			}

			Assert.assertTrue(preferences.isEmpty());
		}
	}

	private void _assertReset(
			int ownerType, String portletId, Map<String, String> preferences)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append("select preferences from PortletPreferences where ");
		sb.append("ownerType = ");
		sb.append(ownerType);
		sb.append(" and portletId = '");
		sb.append(portletId);
		sb.append("'");

		try (Connection connection = DataAccess.getUpgradeOptimizedConnection();
			PreparedStatement ps = connection.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				_assertPreferences(rs.getString("preferences"), preferences);
			}
		}
	}

	private void _upgradeTest(
			int preferenceOwnerType, int upgradeOwnerType,
			String upgradePortletId, boolean upgradeReset,
			Map<String, String> servicePreferences,
			Map<String, String> portletPreferences,
			Map<String, String> archivedPreferences, boolean upgradeMain)
		throws Exception {

		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(_group);

		_addPortletPreference(
			preferenceOwnerType, _layout.getPlid(), _PORTLET_ID);

		_addPortletPreference(
			PortletKeys.PREFS_OWNER_TYPE_ARCHIVED, _layout.getPlid(),
			_PORTLET_ID);

		_upgradeMain = upgradeMain;
		_upgradeOwnerType = upgradeOwnerType;
		_upgradePortletId = upgradePortletId;
		_upgradeReset = upgradeReset;

		upgrade();

		if (upgradeMain) {
			int copyCount = 0;

			if (preferenceOwnerType == upgradeOwnerType) {
				copyCount = 1;
			}

			_assertCopy(
				copyCount, servicePreferences, preferenceOwnerType,
				_group.getGroupId(), _layout.getPlid());
		}

		_assertReset(preferenceOwnerType, _PORTLET_ID, portletPreferences);

		_assertReset(
			PortletKeys.PREFS_OWNER_TYPE_ARCHIVED, _PORTLET_ID,
			archivedPreferences);
	}

	private static final String _KEY = "test";

	private static final String _PORTLET_ID = "com_liferay_portal_upgrade_Test";

	private static final String _SERVICE_KEY = "service_test";

	private static final String _SERVICE_NAME = "com.liferay.test";

	private static final String _SERVICE_VALUE = "service_value";

	private static final String _VALUE = "value";

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Layout _layout;

	private boolean _upgradeMain;
	private int _upgradeOwnerType;
	private String _upgradePortletId;
	private boolean _upgradeReset;

	private static class MockSettingsDescriptor implements SettingsDescriptor {

		@Override
		public Set<String> getAllKeys() {
			return _keys;
		}

		@Override
		public Set<String> getMultiValuedKeys() {
			return null;
		}

		private MockSettingsDescriptor(Set<String> keys) {
			_keys = keys;
		}

		private final Set<String> _keys;

	}

	private static class MockSettingsFactory implements SettingsFactory {

		@Override
		public ArchivedSettings getPortletInstanceArchivedSettings(
				long groupId, String portletId, String name)
			throws SettingsException {

			return _settingsFactory.getPortletInstanceArchivedSettings(
				groupId, portletId, name);
		}

		@Override
		public List<ArchivedSettings> getPortletInstanceArchivedSettingsList(
			long groupId, String portletId) {

			return _settingsFactory.getPortletInstanceArchivedSettingsList(
				groupId, portletId);
		}

		@Override
		public Settings getServerSettings(String settingsId) {
			return _settingsFactory.getServerSettings(settingsId);
		}

		@Override
		public Settings getSettings(SettingsLocator settingsLocator)
			throws SettingsException {

			return _settingsFactory.getSettings(settingsLocator);
		}

		@Override
		public SettingsDescriptor getSettingsDescriptor(String settingsId) {
			if (settingsId.equals(_PORTLET_ID)) {
				return new MockSettingsDescriptor(Collections.singleton(_KEY));
			}

			if (settingsId.equals(_SERVICE_NAME)) {
				return new MockSettingsDescriptor(
					Collections.singleton(_SERVICE_KEY));
			}

			return new MockSettingsDescriptor(Collections.<String>emptySet());
		}

		@Override
		public void registerSettingsMetadata(
			Class<?> settingsClass, Object configurationBean,
			FallbackKeys fallbackKeys) {

			_settingsFactory.registerSettingsMetadata(
				settingsClass, configurationBean, fallbackKeys);
		}

		private final SettingsFactory _settingsFactory =
			SettingsFactoryUtil.getSettingsFactory();

	}

}