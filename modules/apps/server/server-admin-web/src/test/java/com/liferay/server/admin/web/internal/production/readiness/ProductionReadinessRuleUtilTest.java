/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.server.admin.web.internal.production.readiness;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Lily Chi
 */
public class ProductionReadinessRuleUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCheckBetaLanguagesFail() throws Exception {
		try (AutoCloseable closeable1 =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					PropsValues.class, "LOCALES_BETA", new String[] {"fr_FR"});
			AutoCloseable closeable2 =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					PropsValues.class, "LOCALES_ENABLED",
					new String[] {"en_US", "fr_FR"})) {

			ProductionReadinessResult productionReadinessResult =
				ReflectionTestUtil.invoke(
					ProductionReadinessRuleUtil.class, "_checkBetaLanguages",
					new Class<?>[0]);

			Assert.assertFalse(productionReadinessResult.isPass());
			Assert.assertEquals(
				"languages-beta", productionReadinessResult.getKey());
			Assert.assertEquals(
				"fr_FR", productionReadinessResult.getCurrentValue());
		}
	}

	@Test
	public void testCheckBetaLanguagesPass() throws Exception {
		try (AutoCloseable closeable1 =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					PropsValues.class, "LOCALES_BETA", new String[] {"fr_FR"});
			AutoCloseable closeable2 =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					PropsValues.class, "LOCALES_ENABLED",
					new String[] {"en_US"})) {

			ProductionReadinessResult productionReadinessResult =
				ReflectionTestUtil.invoke(
					ProductionReadinessRuleUtil.class, "_checkBetaLanguages",
					new Class<?>[0]);

			Assert.assertTrue(productionReadinessResult.isPass());
			Assert.assertEquals(
				"languages-beta", productionReadinessResult.getKey());
		}
	}

	@Test
	public void testCheckCounterIncrementFail() {
		try (MockedStatic<PropsUtil> propsUtilMockedStatic = Mockito.mockStatic(
				PropsUtil.class)) {

			propsUtilMockedStatic.when(
				() -> PropsUtil.get("counter.increment")
			).thenReturn(
				"1000"
			);

			ProductionReadinessResult productionReadinessResult =
				ReflectionTestUtil.invoke(
					ProductionReadinessRuleUtil.class, "_checkCounterIncrement",
					new Class<?>[0]);

			Assert.assertFalse(productionReadinessResult.isPass());
			Assert.assertEquals(
				"counter-increment", productionReadinessResult.getKey());
			Assert.assertEquals(
				"1000", productionReadinessResult.getCurrentValue());
		}
	}

	@Test
	public void testCheckCounterIncrementPass() {
		try (MockedStatic<PropsUtil> propsUtilMockedStatic = Mockito.mockStatic(
				PropsUtil.class)) {

			propsUtilMockedStatic.when(
				() -> PropsUtil.get("counter.increment")
			).thenReturn(
				"2000"
			);

			ProductionReadinessResult productionReadinessResult =
				ReflectionTestUtil.invoke(
					ProductionReadinessRuleUtil.class, "_checkCounterIncrement",
					new Class<?>[0]);

			Assert.assertTrue(productionReadinessResult.isPass());
			Assert.assertEquals(
				"counter-increment", productionReadinessResult.getKey());
		}
	}

	@Test
	public void testCheckDLImagePreviewDPIFail() throws Exception {
		try (AutoCloseable closeable =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					PropsValues.class, "DL_FILE_ENTRY_PREVIEW_DOCUMENT_DPI",
					100)) {

			ProductionReadinessResult productionReadinessResult =
				ReflectionTestUtil.invoke(
					ProductionReadinessRuleUtil.class,
					"_checkDLImagePreviewDPI", new Class<?>[0]);

			Assert.assertFalse(productionReadinessResult.isPass());
			Assert.assertEquals(
				"dl-image-preview-dpi", productionReadinessResult.getKey());
			Assert.assertEquals(
				"100", productionReadinessResult.getCurrentValue());
		}
	}

	@Test
	public void testCheckDLImagePreviewDPIPass() throws Exception {
		try (AutoCloseable closeable =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					PropsValues.class, "DL_FILE_ENTRY_PREVIEW_DOCUMENT_DPI",
					75)) {

			ProductionReadinessResult productionReadinessResult =
				ReflectionTestUtil.invoke(
					ProductionReadinessRuleUtil.class,
					"_checkDLImagePreviewDPI", new Class<?>[0]);

			Assert.assertTrue(productionReadinessResult.isPass());
			Assert.assertEquals(
				"dl-image-preview-dpi", productionReadinessResult.getKey());
		}
	}

	@Test
	public void testCheckDLPreviewForkingFail() throws Exception {
		try (AutoCloseable closeable =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					PropsValues.class,
					"DL_FILE_ENTRY_PREVIEW_FORK_PROCESS_ENABLED", false)) {

			ProductionReadinessResult productionReadinessResult =
				ReflectionTestUtil.invoke(
					ProductionReadinessRuleUtil.class, "_checkDLPreviewForking",
					new Class<?>[0]);

			Assert.assertFalse(productionReadinessResult.isPass());
			Assert.assertEquals(
				"dl-preview-forking", productionReadinessResult.getKey());
		}
	}

	@Test
	public void testCheckDLPreviewForkingPass() throws Exception {
		try (AutoCloseable closeable =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					PropsValues.class,
					"DL_FILE_ENTRY_PREVIEW_FORK_PROCESS_ENABLED", true)) {

			ProductionReadinessResult productionReadinessResult =
				ReflectionTestUtil.invoke(
					ProductionReadinessRuleUtil.class, "_checkDLPreviewForking",
					new Class<?>[0]);

			Assert.assertTrue(productionReadinessResult.isPass());
			Assert.assertEquals(
				"dl-preview-forking", productionReadinessResult.getKey());
		}
	}

	@Test
	public void testCheckFileStoreImplementationFail() throws Exception {
		try (AutoCloseable closeable =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					PropsValues.class, "DL_STORE_IMPL",
					"com.liferay.portal.store.db.DBStore")) {

			ProductionReadinessResult productionReadinessResult =
				ReflectionTestUtil.invoke(
					ProductionReadinessRuleUtil.class,
					"_checkFileStoreImplementation", new Class<?>[0]);

			Assert.assertFalse(productionReadinessResult.isPass());
			Assert.assertEquals(
				"file-store-implementation",
				productionReadinessResult.getKey());
		}
	}

	@Test
	public void testCheckFileStoreImplementationPass() throws Exception {
		String[] validImpls = {
			"com.liferay.portal.store.azure.AzureStore",
			"com.liferay.portal.store.file.system.AdvancedFileSystemStore",
			"com.liferay.portal.store.gcs.GCSStore",
			"com.liferay.portal.store.s3.IBMS3Store",
			"com.liferay.portal.store.s3.S3Store"
		};

		for (String impl : validImpls) {
			try (AutoCloseable closeable =
					ReflectionTestUtil.setFieldValueWithAutoCloseable(
						PropsValues.class, "DL_STORE_IMPL", impl)) {

				ProductionReadinessResult productionReadinessResult =
					ReflectionTestUtil.invoke(
						ProductionReadinessRuleUtil.class,
						"_checkFileStoreImplementation", new Class<?>[0]);

				Assert.assertTrue(
					"Expected PASS for " + impl,
					productionReadinessResult.isPass());
				Assert.assertEquals(
					impl, productionReadinessResult.getCurrentValue());
			}
		}
	}

	@Test
	public void testCheckJSPReloadingFail() {
		try (MockedStatic<PropsUtil> propsUtilMockedStatic = Mockito.mockStatic(
				PropsUtil.class)) {

			propsUtilMockedStatic.when(
				() -> PropsUtil.get("direct.servlet.context.reload")
			).thenReturn(
				"true"
			);

			ProductionReadinessResult productionReadinessResult =
				ReflectionTestUtil.invoke(
					ProductionReadinessRuleUtil.class, "_checkJSPReloading",
					new Class<?>[0]);

			Assert.assertFalse(productionReadinessResult.isPass());
			Assert.assertEquals(
				"jsp-reloading", productionReadinessResult.getKey());
		}
	}

	@Test
	public void testCheckJSPReloadingPass() {
		try (MockedStatic<PropsUtil> propsUtilMockedStatic = Mockito.mockStatic(
				PropsUtil.class)) {

			propsUtilMockedStatic.when(
				() -> PropsUtil.get("direct.servlet.context.reload")
			).thenReturn(
				"false"
			);

			ProductionReadinessResult productionReadinessResult =
				ReflectionTestUtil.invoke(
					ProductionReadinessRuleUtil.class, "_checkJSPReloading",
					new Class<?>[0]);

			Assert.assertTrue(productionReadinessResult.isPass());
			Assert.assertEquals(
				"jsp-reloading", productionReadinessResult.getKey());
		}
	}

	@Test
	public void testCheckPasswordEncryptionBCRYPTPass() {
		_assertPasswordEncryption("BCRYPT", true);
	}

	@Test
	public void testCheckPasswordEncryptionMD5Fail() {
		_assertPasswordEncryption("MD5", false);
	}

	@Test
	public void testCheckPasswordEncryptionPBKDF2StrongPass() {
		_assertPasswordEncryption("PBKDF2WithHmacSHA1/160/1300000", true);
	}

	@Test
	public void testCheckPasswordEncryptionPBKDF2WeakFail() {
		_assertPasswordEncryption("PBKDF2WithHmacSHA1/160/1000", false);
	}

	@Test
	public void testCheckPortalDeveloperPropertiesFail() {
		try (MockedStatic<PropsUtil> propsUtilMockedStatic = Mockito.mockStatic(
				PropsUtil.class)) {

			propsUtilMockedStatic.when(
				() -> PropsUtil.getArray("include-and-override")
			).thenReturn(
				new String[] {"portal-developer.properties"}
			);

			ProductionReadinessResult productionReadinessResult =
				ReflectionTestUtil.invoke(
					ProductionReadinessRuleUtil.class,
					"_checkPortalDeveloperProperties", new Class<?>[0]);

			Assert.assertFalse(productionReadinessResult.isPass());
			Assert.assertEquals(
				"portal-developer-properties",
				productionReadinessResult.getKey());
		}
	}

	@Test
	public void testCheckPortalDeveloperPropertiesPass() {
		try (MockedStatic<PropsUtil> propsUtilMockedStatic = Mockito.mockStatic(
				PropsUtil.class)) {

			propsUtilMockedStatic.when(
				() -> PropsUtil.getArray("include-and-override")
			).thenReturn(
				new String[0]
			);

			ProductionReadinessResult productionReadinessResult =
				ReflectionTestUtil.invoke(
					ProductionReadinessRuleUtil.class,
					"_checkPortalDeveloperProperties", new Class<?>[0]);

			Assert.assertTrue(productionReadinessResult.isPass());
			Assert.assertEquals(
				"portal-developer-properties",
				productionReadinessResult.getKey());
		}
	}

	@Test
	public void testCheckUnusedLanguagesFail() throws Exception {
		try (AutoCloseable closeable1 =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					PropsValues.class, "LOCALES",
					new String[] {"en_US", "de_DE"});
			AutoCloseable closeable2 =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					PropsValues.class, "LOCALES_ENABLED",
					new String[] {"en_US"})) {

			ProductionReadinessResult productionReadinessResult =
				ReflectionTestUtil.invoke(
					ProductionReadinessRuleUtil.class, "_checkUnusedLanguages",
					new Class<?>[0]);

			Assert.assertFalse(productionReadinessResult.isPass());
			Assert.assertEquals(
				"languages-unused", productionReadinessResult.getKey());
			Assert.assertEquals(
				"de_DE", productionReadinessResult.getCurrentValue());
		}
	}

	@Test
	public void testCheckUnusedLanguagesPass() throws Exception {
		try (AutoCloseable closeable1 =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					PropsValues.class, "LOCALES", new String[] {"en_US"});
			AutoCloseable closeable2 =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					PropsValues.class, "LOCALES_ENABLED",
					new String[] {"en_US"})) {

			ProductionReadinessResult productionReadinessResult =
				ReflectionTestUtil.invoke(
					ProductionReadinessRuleUtil.class, "_checkUnusedLanguages",
					new Class<?>[0]);

			Assert.assertTrue(productionReadinessResult.isPass());
			Assert.assertEquals(
				"languages-unused", productionReadinessResult.getKey());
		}
	}

	@Test
	public void testIsStrongerThanPBKDF2BCRYPT() {
		Assert.assertTrue(_invokeIsStrongerThanPBKDF2("BCRYPT"));
	}

	@Test
	public void testIsStrongerThanPBKDF2BCRYPTVariant() {
		Assert.assertTrue(_invokeIsStrongerThanPBKDF2("BCRYPT/12"));
	}

	@Test
	public void testIsStrongerThanPBKDF2MD5() {
		Assert.assertFalse(_invokeIsStrongerThanPBKDF2("MD5"));
	}

	@Test
	public void testIsStrongerThanPBKDF2Null() {
		Assert.assertFalse(_invokeIsStrongerThanPBKDF2(null));
	}

	@Test
	public void testIsStrongerThanPBKDF2SCRYPT() {
		Assert.assertTrue(_invokeIsStrongerThanPBKDF2("SCRYPT"));
	}

	@Test
	public void testIsStrongerThanPBKDF2Strong() {
		Assert.assertTrue(
			_invokeIsStrongerThanPBKDF2("PBKDF2WithHmacSHA1/160/1300000"));
	}

	@Test
	public void testIsStrongerThanPBKDF2Weak() {
		Assert.assertFalse(
			_invokeIsStrongerThanPBKDF2("PBKDF2WithHmacSHA1/160/1000"));
	}

	private ProductionReadinessResult _assertPasswordEncryption(
		String algorithm, boolean expectedPass) {

		try (MockedStatic<PropsUtil> propsUtilMockedStatic = Mockito.mockStatic(
				PropsUtil.class)) {

			propsUtilMockedStatic.when(
				() -> PropsUtil.get("passwords.encryption.algorithm")
			).thenReturn(
				algorithm
			);

			ProductionReadinessResult productionReadinessResult =
				ReflectionTestUtil.invoke(
					ProductionReadinessRuleUtil.class,
					"_checkPasswordEncryption", new Class<?>[0]);

			Assert.assertEquals(
				expectedPass, productionReadinessResult.isPass());
			Assert.assertEquals(
				"password-encryption", productionReadinessResult.getKey());

			return productionReadinessResult;
		}
	}

	private boolean _invokeIsStrongerThanPBKDF2(String algorithm) {
		return ReflectionTestUtil.invoke(
			ProductionReadinessRuleUtil.class, "_isStrongerThanPBKDF2",
			new Class<?>[] {String.class}, algorithm);
	}

}