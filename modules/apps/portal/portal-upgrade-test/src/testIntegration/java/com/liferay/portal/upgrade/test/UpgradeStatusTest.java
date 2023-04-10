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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStatus;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.tools.DBUpgrader;

import java.util.ArrayList;
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
public class UpgradeStatusTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_originalErrorMessages = ReflectionTestUtil.getFieldValue(
			_upgradeStatus, "_errorMessages");
		_originalServletSchemaVersionsMap = ReflectionTestUtil.getFieldValue(
			_upgradeStatus, "_schemaVersionsMap");
		_originalUpgradeProcessMessages = ReflectionTestUtil.getFieldValue(
			_upgradeStatus, "_upgradeProcessMessages");
		_originalState = ReflectionTestUtil.getFieldValue(
			_upgradeStatus, "_state");
		_originalType = ReflectionTestUtil.getFieldValue(
			_upgradeStatus, "_type");
		_originalWarningMessages = ReflectionTestUtil.getFieldValue(
			_upgradeStatus, "_warningMessages");

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
		_originalErrorMessages.clear();
		_originalServletSchemaVersionsMap.clear();
		_originalUpgradeProcessMessages.clear();
		_originalState = "Pending";
		_originalType = "Not calculated";
		_originalWarningMessages.clear();

		ReflectionTestUtil.setFieldValue(DBUpgrader.class, "_stopWatch", null);
	}

	@Test
	public void testErrorUpgrade() throws Exception {
		StartupHelperUtil.setUpgrading(true);

		ErrorUpgradeProcess upgradeProcess = new ErrorUpgradeProcess();

		upgradeProcess.doUpgrade();

		StartupHelperUtil.setUpgrading(false);

		Assert.assertEquals("Failure", _upgradeStatus.getState());

		Assert.assertEquals("No upgrade", _upgradeStatus.getType());
	}

	@Test
	public void testFailureUpgradesPending() {
		String bundleSymbolicName = "com.liferay.asset.service";

		Version currentSchemaVersion = _getReleaseSchemaVersion(
			bundleSymbolicName);

		_setReleaseSchemaVersion(bundleSymbolicName, "1.0.0");

		StartupHelperUtil.setUpgrading(true);

		StartupHelperUtil.setUpgrading(false);

		_setReleaseSchemaVersion(
			bundleSymbolicName, currentSchemaVersion.toString());

		Assert.assertEquals("Failure", _upgradeStatus.getState());

		Assert.assertEquals("No upgrade", _upgradeStatus.getType());
	}

	@Test
	public void testMajorUpgrade() {
		_testUpgradeType("Major");
	}

	@Test
	public void testMicroUpgrade() {
		_testUpgradeType("Micro");
	}

	@Test
	public void testMinorUpgrade() {
		_testUpgradeType("Minor");
	}

	@Test
	public void testNoUpgrades() {
		StartupHelperUtil.setUpgrading(true);

		StartupHelperUtil.setUpgrading(false);

		Assert.assertEquals("Success", _upgradeStatus.getState());

		Assert.assertEquals("No upgrade", _upgradeStatus.getType());
	}

	@Test
	public void testQualifierUpgrade() {
		_testUpgradeType("Qualifier");
	}

	@Test
	public void testWarningUpgrade() throws Exception {
		StartupHelperUtil.setUpgrading(true);

		WarningUpgradeProcess upgradeProcess = new WarningUpgradeProcess();

		upgradeProcess.doUpgrade();

		StartupHelperUtil.setUpgrading(false);

		Assert.assertEquals("Warning", _upgradeStatus.getState());

		Assert.assertEquals("No upgrade", _upgradeStatus.getType());
	}

	private Version _getReleaseSchemaVersion(String bundleSymbolicName) {
		Release release = _releaseLocalService.fetchRelease(bundleSymbolicName);

		return Version.parseVersion(release.getSchemaVersion());
	}

	private void _setReleaseSchemaVersion(
		String bundleSymbolicName, String schemaVersion) {

		Release release = _releaseLocalService.fetchRelease(bundleSymbolicName);

		release.setSchemaVersion(schemaVersion);

		_releaseLocalService.updateRelease(release);
	}

	private void _testUpgradeType(String type) {
		String majorBundleSymbolicName = "com.liferay.asset.service";
		String minorBundleSymbolicName = "com.liferay.journal.service";
		String microBundleSymbolicName = "com.liferay.object.service";
		String qualifierBundleSymbolicName = "com.liferay.calendar.service";

		Version currentMajorSchemaVersion = _getReleaseSchemaVersion(
			majorBundleSymbolicName);
		Version currentMinorSchemaVersion = _getReleaseSchemaVersion(
			minorBundleSymbolicName);
		Version currentMicroSchemaVersion = _getReleaseSchemaVersion(
			microBundleSymbolicName);

		Version currentQualifierSchemaVersion = _getReleaseSchemaVersion(
			qualifierBundleSymbolicName);

		_setReleaseSchemaVersion(
			qualifierBundleSymbolicName,
			currentQualifierSchemaVersion.toString() + ".step-2");

		StartupHelperUtil.setUpgrading(true);

		if (type.equals("Major")) {
			_setReleaseSchemaVersion(
				majorBundleSymbolicName,
				StringBundler.concat(
					String.valueOf(currentMajorSchemaVersion.getMajor() + 1),
					StringPool.PERIOD,
					String.valueOf(currentMajorSchemaVersion.getMinor()),
					StringPool.PERIOD,
					String.valueOf(currentMajorSchemaVersion.getMicro())));
		}

		if (type.equals("Minor")) {
			_setReleaseSchemaVersion(
				minorBundleSymbolicName,
				StringBundler.concat(
					String.valueOf(currentMinorSchemaVersion.getMajor()),
					StringPool.PERIOD,
					String.valueOf(currentMinorSchemaVersion.getMinor() + 1),
					StringPool.PERIOD,
					String.valueOf(currentMinorSchemaVersion.getMicro())));
		}

		if (type.equals("Micro")) {
			_setReleaseSchemaVersion(
				microBundleSymbolicName,
				StringBundler.concat(
					String.valueOf(currentMicroSchemaVersion.getMajor()),
					StringPool.PERIOD,
					String.valueOf(currentMicroSchemaVersion.getMinor()),
					StringPool.PERIOD,
					String.valueOf(currentMicroSchemaVersion.getMicro() + 1)));
		}

		_setReleaseSchemaVersion(
			qualifierBundleSymbolicName,
			currentQualifierSchemaVersion.toString());

		StartupHelperUtil.setUpgrading(false);

		_setReleaseSchemaVersion(
			majorBundleSymbolicName, currentMajorSchemaVersion.toString());
		_setReleaseSchemaVersion(
			minorBundleSymbolicName, currentMinorSchemaVersion.toString());
		_setReleaseSchemaVersion(
			microBundleSymbolicName, currentMicroSchemaVersion.toString());

		Assert.assertEquals("Success", _upgradeStatus.getState());

		if (!type.equals("Qualifier")) {
			Assert.assertEquals(type, _upgradeStatus.getType());
		}
		else {
			Assert.assertEquals("Micro", _upgradeStatus.getType());
		}
	}

	private static Map<String, Map<String, Integer>> _originalErrorMessages;
	private static Map<String, Object> _originalServletSchemaVersionsMap;
	private static String _originalState;
	private static StopWatch _originalStopWatch;
	private static String _originalType;
	private static Map<String, ArrayList<String>>
		_originalUpgradeProcessMessages;
	private static Map<String, Map<String, Integer>> _originalWarningMessages;

	@Inject
	private static UpgradeStatus _upgradeStatus;

	@Inject
	private ReleaseLocalService _releaseLocalService;

	private class ErrorUpgradeProcess extends UpgradeProcess {

		@Override
		protected void doUpgrade() throws Exception {
			_log.error("Error on upgrade");
		}

		private final Log _log = LogFactoryUtil.getLog(
			ErrorUpgradeProcess.class);

	}

	private class WarningUpgradeProcess extends UpgradeProcess {

		@Override
		protected void doUpgrade() throws Exception {
			if (_log.isWarnEnabled()) {
				_log.warn("Warn on upgrade");
			}
		}

		private final Log _log = LogFactoryUtil.getLog(
			WarningUpgradeProcess.class);

	}

}