/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.license.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.license.util.LicenseManagerUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;

import java.util.Arrays;
import java.util.Dictionary;

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

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

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

		ConfigurationTestUtil.deleteConfiguration(_CKEDITOR_CONFIG_ID);

		if (_CKEDITOR_CONFIG_FILE.exists()) {
			_CKEDITOR_CONFIG_FILE.delete();
		}
	}

	@Test
	public void testBrokenCKEditorFile() throws Exception {
		assertPortalInvalidatedWithBrokenFile(
			getProperty("ckeditor.file.path"));
	}

	@Test
	public void testEnterpriseLicense() throws Exception {
		assertLicensePropertiesNotExisted(getPortalProductId());

		_assertCKEditorConfiguration(null, false);

		File binaryFile = deployEnterprisePortalLicense(Time.HOUR);

		ConfigurationTestUtil.updateConfiguration(
			_CKEDITOR_CONFIG_ID, this::assertPortalLicenseRegistered);

		_assertCKEditorConfiguration(_getCKEditorPrivateLicenseKey(), true);

		binaryFile.delete();

		LicenseManagerUtil.checkLicense(getPortalProductId());

		assertLicensePropertiesNotExisted(getPortalProductId());

		ConfigurationTestUtil.updateConfiguration(
			_CKEDITOR_CONFIG_ID, this::assertPortalLicenseNotRegistered);

		_assertCKEditorConfiguration(_getCKEditorPrivateLicenseKey(), true);

		deployFreeTierPortalLicense(Time.HOUR);

		ConfigurationTestUtil.updateConfiguration(
			_CKEDITOR_CONFIG_ID, this::assertPortalLicenseRegistered);

		_assertCKEditorConfiguration(null, false);
	}

	@Test
	public void testFreeTierLicense() throws Exception {
		assertLicensePropertiesNotExisted(getPortalProductId());

		_assertCKEditorConfiguration(null, false);

		ConfigurationTestUtil.updateConfiguration(
			_CKEDITOR_CONFIG_ID,
			() -> FileUtil.write(
				_CKEDITOR_CONFIG_FILE,
				"licenseKey=\"" + _getCKEditorPrivateLicenseKey() + "\""));

		_assertCKEditorConfiguration(_getCKEditorPrivateLicenseKey(), true);

		deployFreeTierPortalLicense(Time.HOUR);

		ConfigurationTestUtil.updateConfiguration(
			_CKEDITOR_CONFIG_ID, this::assertPortalLicenseRegistered);

		_assertCKEditorConfiguration(null, false);

		ConfigurationTestUtil.updateConfiguration(
			_CKEDITOR_CONFIG_ID,
			() -> FileUtil.write(
				_CKEDITOR_CONFIG_FILE,
				"licenseKey=\"" + _getCKEditorPrivateLicenseKey() + "\""));

		_assertCKEditorConfiguration(_getCKEditorPrivateLicenseKey(), true);

		resetCheckInterval();

		ConfigurationTestUtil.updateConfiguration(
			_CKEDITOR_CONFIG_ID, this::assertPortalLicenseRegistered);

		_assertCKEditorConfiguration(null, false);
	}

	@Test
	public void testFreeTierLicenseCustomKey() throws Exception {
		assertLicensePropertiesNotExisted(getPortalProductId());

		_assertCKEditorConfiguration(null, false);

		deployFreeTierPortalLicense(Time.HOUR);

		ConfigurationTestUtil.updateConfiguration(
			_CKEDITOR_CONFIG_ID, this::assertPortalLicenseRegistered);

		assertPortalLicenseRegistered();

		_assertCKEditorConfiguration(null, false);

		String customLicenseKey = RandomTestUtil.randomString();

		ConfigurationTestUtil.updateConfiguration(
			_CKEDITOR_CONFIG_ID,
			() -> {
				FileUtil.write(
					_CKEDITOR_CONFIG_FILE,
					"licenseKey=\"" + customLicenseKey + "\"");

				assertPortalLicenseRegistered();
			});

		_assertCKEditorConfiguration(customLicenseKey, true);

		resetCheckInterval();

		ConfigurationTestUtil.updateConfiguration(
			_CKEDITOR_CONFIG_ID, this::assertPortalLicenseRegistered);

		_assertCKEditorConfiguration(customLicenseKey, true);
	}

	@Test
	public void testFreeTierLicensePrivateKeyWithoutConfigurationFile()
		throws Exception {

		assertLicensePropertiesNotExisted(getPortalProductId());

		_assertCKEditorConfiguration(null, false);

		deployFreeTierPortalLicense(Time.HOUR);

		ConfigurationTestUtil.updateConfiguration(
			_CKEDITOR_CONFIG_ID, this::assertPortalLicenseRegistered);

		_assertCKEditorConfiguration(null, false);

		ConfigurationTestUtil.saveConfiguration(
			_CKEDITOR_CONFIG_ID,
			HashMapDictionaryBuilder.<String, Object>put(
				"licenseKey", _getCKEditorPrivateLicenseKey()
			).build());

		_assertCKEditorConfiguration(_getCKEditorPrivateLicenseKey(), false);

		resetCheckInterval();

		ConfigurationTestUtil.updateConfiguration(
			_CKEDITOR_CONFIG_ID, this::assertPortalLicenseRegistered);

		_assertCKEditorConfiguration(null, false);

		String customLicenseKey = RandomTestUtil.randomString();

		ConfigurationTestUtil.saveConfiguration(
			_CKEDITOR_CONFIG_ID,
			HashMapDictionaryBuilder.<String, Object>put(
				"licenseKey", customLicenseKey
			).build());

		_assertCKEditorConfiguration(customLicenseKey, false);

		resetCheckInterval();

		_assertCKEditorConfiguration(customLicenseKey, false);
	}

	private void _assertCKEditorConfiguration(
			String licenseKey, boolean fileExisted)
		throws Exception {

		Assert.assertEquals(licenseKey, _getCurrentLicenseKey());

		Assert.assertSame(fileExisted, _CKEDITOR_CONFIG_FILE.exists());

		if (fileExisted) {
			String content = FileUtil.read(_CKEDITOR_CONFIG_FILE);

			Assert.assertTrue(content.contains(licenseKey));
		}
	}

	private String _getCKEditorPrivateLicenseKey() {
		return getProperty("ckeditor.private.license.key");
	}

	private String _getCurrentLicenseKey() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.pid=" + _CKEDITOR_CONFIG_ID + ")");

		if (configurations == null) {
			return null;
		}

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);

		Configuration configuration = configurations[0];

		Dictionary<String, Object> properties = configuration.getProperties();

		return (String)properties.get("licenseKey");
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

	@Inject
	private ConfigurationAdmin _configurationAdmin;

}