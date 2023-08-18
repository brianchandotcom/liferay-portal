/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.upgrade.PortalUpgradeProcess;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class UpgradeRecorderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_originalStopWatch = ReflectionTestUtil.getFieldValue(
			DBUpgrader.class, "_stopWatch");
	}

	@AfterClass
	public static void tearDownClass() {
		ReflectionTestUtil.setFieldValue(
			DBUpgrader.class, "_stopWatch", _originalStopWatch);
	}

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(DBUpgrader.class, "_stopWatch", null);
	}

	@Test
	public void testFailure() throws Exception {
		StartupHelperUtil.setUpgrading(true);

		ErrorUpgradeProcess errorUpgradeProcess = new ErrorUpgradeProcess();

		errorUpgradeProcess.doUpgrade();

		StartupHelperUtil.setUpgrading(false);

		Assert.assertEquals("failure", _getResult());

		Assert.assertEquals("no upgrade", _getType());
	}

	@Test
	public void testFailureByPendingUpgrade() throws SQLException {
		try (Connection connection = DataAccess.getConnection()) {
			Version version = PortalUpgradeProcess.getCurrentSchemaVersion(
				connection);

			PortalUpgradeProcess.updateSchemaVersion(
				connection, new Version(0, 0, 0));

			try {
				StartupHelperUtil.setUpgrading(true);

				StartupHelperUtil.setUpgrading(false);
			}
			finally {
				PortalUpgradeProcess.updateSchemaVersion(connection, version);
			}
		}

		Assert.assertEquals("unresolved", _getResult());

		Assert.assertEquals("no upgrade", _getType());
	}

	@Test
	public void testMajorUpgrade() {
		_testUpgrade("major");

		Assert.assertEquals("major", _getType());
	}

	@Test
	public void testMicroUpgrade() {
		_testUpgrade("micro");

		Assert.assertEquals("micro", _getType());
	}

	@Test
	public void testMinorUpgrade() {
		_testUpgrade("minor");

		Assert.assertEquals("minor", _getType());
	}

	@Test
	public void testQualifierUpgrade() {
		_testUpgrade("qualifier");

		Assert.assertEquals("micro", _getType());
	}

	@Test
	public void testSuccessByNoUpgrades() {
		StartupHelperUtil.setUpgrading(true);

		StartupHelperUtil.setUpgrading(false);

		Assert.assertEquals("success", _getResult());

		Assert.assertEquals("no upgrade", _getType());
	}

	@Test
	public void testWarning() throws Exception {
		StartupHelperUtil.setUpgrading(true);

		WarningUpgradeProcess warningUpgradeProcess =
			new WarningUpgradeProcess();

		warningUpgradeProcess.doUpgrade();

		StartupHelperUtil.setUpgrading(false);

		Assert.assertEquals("warning", _getResult());

		Assert.assertEquals("no upgrade", _getType());
	}

	private String _getResult() {
		return ReflectionTestUtil.getFieldValue(_upgradeRecorder, "_result");
	}

	private String _getType() {
		return ReflectionTestUtil.getFieldValue(_upgradeRecorder, "_type");
	}

	private void _testUpgrade(String type) {
		List<Release> releases = _releaseLocalService.getReleases(0, 4);

		Release majorRelease = releases.get(0);
		Release microRelease = releases.get(1);
		Release minorRelease = releases.get(2);
		Release qualifierRelease = releases.get(3);

		Version majorSchemaVersion = Version.parseVersion(
			majorRelease.getSchemaVersion());
		Version minorSchemaVersion = Version.parseVersion(
			minorRelease.getSchemaVersion());
		Version microSchemaVersion = Version.parseVersion(
			microRelease.getSchemaVersion());
		String qualifierSchemaVersion = qualifierRelease.getSchemaVersion();

		try {
			qualifierRelease.setSchemaVersion(
				qualifierSchemaVersion + ".step-2");

			qualifierRelease = _releaseLocalService.updateRelease(
				qualifierRelease);

			StartupHelperUtil.setUpgrading(true);

			if (type.equals("major")) {
				majorRelease.setSchemaVersion(
					StringBundler.concat(
						majorSchemaVersion.getMajor() + 1, StringPool.PERIOD,
						majorSchemaVersion.getMinor(), StringPool.PERIOD,
						majorSchemaVersion.getMicro()));

				majorRelease = _releaseLocalService.updateRelease(majorRelease);
			}

			if (type.equals("major") || type.equals("minor")) {
				minorRelease.setSchemaVersion(
					StringBundler.concat(
						minorSchemaVersion.getMajor(), StringPool.PERIOD,
						minorSchemaVersion.getMinor() + 1, StringPool.PERIOD,
						minorSchemaVersion.getMicro()));

				minorRelease = _releaseLocalService.updateRelease(minorRelease);
			}

			if (type.equals("major") || type.equals("minor") ||
				type.equals("micro")) {

				microRelease.setSchemaVersion(
					StringBundler.concat(
						microSchemaVersion.getMajor(), StringPool.PERIOD,
						microSchemaVersion.getMinor(), StringPool.PERIOD,
						microSchemaVersion.getMicro() + 1));

				microRelease = _releaseLocalService.updateRelease(microRelease);
			}

			qualifierRelease.setSchemaVersion(qualifierSchemaVersion);

			_releaseLocalService.updateRelease(qualifierRelease);

			StartupHelperUtil.setUpgrading(false);
		}
		finally {
			majorRelease.setSchemaVersion(majorSchemaVersion.toString());
			microRelease.setSchemaVersion(microSchemaVersion.toString());
			minorRelease.setSchemaVersion(minorSchemaVersion.toString());

			_releaseLocalService.updateRelease(majorRelease);
			_releaseLocalService.updateRelease(microRelease);
			_releaseLocalService.updateRelease(minorRelease);
		}
	}

	private static StopWatch _originalStopWatch;

	@Inject(
		filter = "component.name=com.liferay.portal.upgrade.internal.recorder.UpgradeRecorder",
		type = Inject.NoType.class
	)
	private static Object _upgradeRecorder;

	@Inject
	private ReleaseLocalService _releaseLocalService;

	private class ErrorUpgradeProcess extends UpgradeProcess {

		@Override
		protected void doUpgrade() {
			Map<String, Map<String, Integer>> errorMessages =
				ReflectionTestUtil.getFieldValue(
					_upgradeRecorder, "_errorMessages");

			errorMessages.put(
				"ErrorUpgradeProcess",
				Collections.singletonMap("Error on upgrade", 0));
		}

	}

	private class WarningUpgradeProcess extends UpgradeProcess {

		@Override
		protected void doUpgrade() {
			Map<String, Map<String, Integer>> warningMessages =
				ReflectionTestUtil.getFieldValue(
					_upgradeRecorder, "_warningMessages");

			warningMessages.put(
				"WarningUpgradeProcess",
				Collections.singletonMap("Warn on upgrade", 0));
		}

	}

}