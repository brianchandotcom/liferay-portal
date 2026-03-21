/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.license.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.bytebuddy.agent.builder.ResettableClassFileTransformer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kevin Lee
 */
@RunWith(Arquillian.class)
public class CKEditorLicenseTest extends BaseLicenseTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		Assume.assumeTrue(isReleaseBundle());
	}

	@BeforeClass
	public static void setUpClass() {
		_disableKeyValidatorResettableClassFileTransformer = disableValidate();
		_setVersionResettableClassFileTransformer = setVersion("2026.Q1.0 LTS");
	}

	@AfterClass
	public static void tearDownClass() {
		resetClassFileTransformer(
			_disableKeyValidatorResettableClassFileTransformer);
		resetClassFileTransformer(_setVersionResettableClassFileTransformer);
	}

	@After
	public void tearDown() throws Exception {
		resetLicenseData();
		resetLifecycleAction();

		if (_CKEDITOR_CONFIG_FILE.exists()) {
			_CKEDITOR_CONFIG_FILE.delete();
		}
	}

	@Test
	public void testFreeTierLicense() throws Exception {
		String privateLicenseKey = _getCKEditorPrivateLicenseKey();

		_assertCKEditorConfiguration(false, privateLicenseKey);

		ConfigurationTestUtil.updateConfiguration(
			_CKEDITOR_CONFIG_ID,
			() -> {
				FileUtil.write(
					_CKEDITOR_CONFIG_FILE,
					"licenseKey=\"" + privateLicenseKey + "\"");
			});

		_assertCKEditorConfiguration(true, privateLicenseKey);

		deployFreeTierPortalLicense(Time.HOUR);

		ConfigurationTestUtil.updateConfiguration(
			_CKEDITOR_CONFIG_ID, this::assertPortalLicenseRegistered);

		_assertCKEditorConfiguration(false, privateLicenseKey);

		ConfigurationTestUtil.updateConfiguration(
			_CKEDITOR_CONFIG_ID,
			() -> {
				FileUtil.write(
					_CKEDITOR_CONFIG_FILE,
					"licenseKey=\"" + privateLicenseKey + "\"");
			});

		_assertCKEditorConfiguration(true, privateLicenseKey);

		resetCheckInterval();

		ConfigurationTestUtil.updateConfiguration(
			_CKEDITOR_CONFIG_ID, this::assertPortalLicenseRegistered);

		_assertCKEditorConfiguration(false, privateLicenseKey);
	}

	private void _assertCKEditorConfiguration(
			boolean containsLicenseKey, String licenseKey)
		throws Exception {

		if (containsLicenseKey) {
			Assert.assertTrue(_CKEDITOR_CONFIG_FILE.exists());

			String content = FileUtil.read(_CKEDITOR_CONFIG_FILE);

			Assert.assertTrue(content.contains(licenseKey));
		}
		else if (_CKEDITOR_CONFIG_FILE.exists()) {
			String content = FileUtil.read(_CKEDITOR_CONFIG_FILE);

			Assert.assertFalse(content.contains(licenseKey));
		}

		String configuration = _readConfigurationFromDatabase();

		if (containsLicenseKey) {
			Assert.assertTrue(configuration.contains(licenseKey));
		}
		else {
			Assert.assertFalse(configuration.contains(licenseKey));
		}
	}

	private String _getCKEditorPrivateLicenseKey() {
		return getProperty("ckeditor.private.license.key");
	}

	private String _readConfigurationFromDatabase() throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select dictionary from Configuration_ where configurationId " +
					"= ?")) {

			preparedStatement.setString(1, _CKEDITOR_CONFIG_ID);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getString(1);
				}
			}
		}

		return StringPool.BLANK;
	}

	private static final File _CKEDITOR_CONFIG_FILE = new File(
		PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR,
		CKEditorLicenseTest._CKEDITOR_CONFIG_ID + ".config");

	private static final String _CKEDITOR_CONFIG_ID =
		"com.liferay.frontend.editor.ckeditor.web.internal.configuration." +
			"CKEditor5Configuration";

	private static ResettableClassFileTransformer
		_disableKeyValidatorResettableClassFileTransformer;
	private static ResettableClassFileTransformer
		_setVersionResettableClassFileTransformer;

}