/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.server.admin.web.internal.production.readiness;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author Lily Chi
 */
public class ProductionReadinessRuleUtil {

	public static Collection<ProductionReadinessResult> check() {
		return TransformUtil.transform(
			_productionReadinessResultSuppliers, Supplier::get);
	}

	private static ProductionReadinessResult _checkBetaLanguages() {
		List<String> betaLocales = List.of(PropsValues.LOCALES_BETA);

		return _getLanguagesProductionReadinessResult(
			"languages-beta",
			ListUtil.filter(
				List.of(PropsValues.LOCALES_ENABLED), betaLocales::contains));
	}

	private static ProductionReadinessResult _checkCounterIncrement() {
		int counterIncrement = GetterUtil.getInteger(
			PropsUtil.get("counter.increment"));

		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_PORTAL_PROPERTIES_CONFIGURATION, "counter-increment"
			).currentValue(
				String.valueOf(counterIncrement)
			);

		if (counterIncrement < 2000) {
			return builder.fail();
		}

		return builder.pass();
	}

	private static ProductionReadinessResult _checkDLImagePreviewDPI() {
		int dpi = PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_DPI;

		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_PORTAL_PROPERTIES_CONFIGURATION,
				"dl-image-preview-dpi"
			).currentValue(
				String.valueOf(dpi)
			);

		if (dpi > _MAX_DL_IMAGE_PREVIEW_DPI) {
			return builder.recommendedValue(
				String.valueOf(_MAX_DL_IMAGE_PREVIEW_DPI)
			).fail();
		}

		return builder.pass();
	}

	private static ProductionReadinessResult _checkDLPreviewForking() {
		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_PORTAL_PROPERTIES_CONFIGURATION, "dl-preview-forking"
			).currentValue(
				PropsKeys.DL_FILE_ENTRY_PREVIEW_FORK_PROCESS_ENABLED + "=" +
					PropsValues.DL_FILE_ENTRY_PREVIEW_FORK_PROCESS_ENABLED
			).messageParameters(
				PropsKeys.DL_FILE_ENTRY_PREVIEW_FORK_PROCESS_ENABLED
			).recommendedValue(
				PropsKeys.DL_FILE_ENTRY_PREVIEW_FORK_PROCESS_ENABLED + "=true"
			);

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_FORK_PROCESS_ENABLED) {
			return builder.pass();
		}

		return builder.fail();
	}

	private static ProductionReadinessResult _checkFileStoreImplementation() {
		String dlStoreImpl = PropsValues.DL_STORE_IMPL;

		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_PORTAL_PROPERTIES_CONFIGURATION,
				"file-store-implementation"
			).currentValue(
				dlStoreImpl
			);

		if (_recommendedDLStoreImplClassNames.contains(dlStoreImpl)) {
			return builder.pass();
		}

		return builder.recommendedValue(
			StringUtil.merge(_recommendedDLStoreImplClassNames)
		).fail();
	}

	private static ProductionReadinessResult _checkJSPReloading() {
		boolean directServletContextReload = GetterUtil.getBoolean(
			PropsUtil.get("direct.servlet.context.reload"));

		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_PORTAL_PROPERTIES_CONFIGURATION, "jsp-reloading"
			).currentValue(
				"direct.servlet.context.reload=" + directServletContextReload
			).recommendedValue(
				"direct.servlet.context.reload=false"
			);

		if (directServletContextReload) {
			return builder.fail();
		}

		return builder.pass();
	}

	private static ProductionReadinessResult _checkPasswordEncryption() {
		String algorithm = PropsUtil.get("passwords.encryption.algorithm");

		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_PORTAL_PROPERTIES_CONFIGURATION, "password-encryption"
			).currentValue(
				algorithm
			).recommendedValue(
				"PBKDF2WithHmacSHA1/160/1300000 (or stronger)"
			);

		if (_isStrongerThanPBKDF2(algorithm)) {
			return builder.pass();
		}

		return builder.fail();
	}

	private static ProductionReadinessResult _checkPortalDeveloperProperties() {
		boolean hasDeveloperProperties = ArrayUtil.contains(
			PropsUtil.getArray("include-and-override"),
			"portal-developer.properties");

		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_PORTAL_PROPERTIES_CONFIGURATION,
				"portal-developer-properties");

		if (hasDeveloperProperties) {
			return builder.currentValue(
				"portal-developer.properties included"
			).fail();
		}

		return builder.currentValue(
			"portal-developer.properties is not included"
		).pass();
	}

	private static ProductionReadinessResult _checkUnusedLanguages() {
		List<String> enabledLocales = List.of(PropsValues.LOCALES_ENABLED);

		return _getLanguagesProductionReadinessResult(
			"languages-unused",
			ListUtil.filter(
				List.of(PropsValues.LOCALES),
				locale -> !enabledLocales.contains(locale)));
	}

	private static ProductionReadinessResult
		_getLanguagesProductionReadinessResult(
			String key, List<String> locales) {

		ProductionReadinessResult.Builder builder =
			ProductionReadinessResult.builder(
				_CATEGORY_PORTAL_PROPERTIES_CONFIGURATION, key);

		if (locales.isEmpty()) {
			return builder.pass();
		}

		String localesString = StringUtil.merge(locales);

		return builder.currentValue(
			localesString
		).messageParameters(
			localesString
		).fail();
	}

	private static boolean _isStrongerThanPBKDF2(String algorithm) {
		if (algorithm == null) {
			return false;
		}

		if (algorithm.equals("BCRYPT") || algorithm.startsWith("BCRYPT/") ||
			algorithm.equals("SCRYPT")) {

			return true;
		}

		if (algorithm.startsWith("PBKDF2WithHmacSHA1/")) {
			String[] parts = algorithm.split("/");

			if (parts.length >= 3) {
				int rounds = GetterUtil.getInteger(parts[2]);

				if (rounds >= 1300000) {
					return true;
				}
			}
		}

		return false;
	}

	private static final String _CATEGORY_PORTAL_PROPERTIES_CONFIGURATION =
		"portal-properties-configuration";

	private static final int _MAX_DL_IMAGE_PREVIEW_DPI = 75;

	private static final List<Supplier<ProductionReadinessResult>>
		_productionReadinessResultSuppliers = List.of(
			ProductionReadinessRuleUtil::_checkBetaLanguages,
			ProductionReadinessRuleUtil::_checkCounterIncrement,
			ProductionReadinessRuleUtil::_checkDLImagePreviewDPI,
			ProductionReadinessRuleUtil::_checkDLPreviewForking,
			ProductionReadinessRuleUtil::_checkFileStoreImplementation,
			ProductionReadinessRuleUtil::_checkJSPReloading,
			ProductionReadinessRuleUtil::_checkPasswordEncryption,
			ProductionReadinessRuleUtil::_checkPortalDeveloperProperties,
			ProductionReadinessRuleUtil::_checkUnusedLanguages);
	private static final List<String> _recommendedDLStoreImplClassNames =
		List.of(
			"com.liferay.portal.store.azure.AzureStore",
			"com.liferay.portal.store.file.system.AdvancedFileSystemStore",
			"com.liferay.portal.store.gcs.GCSStore",
			"com.liferay.portal.store.s3.IBMS3Store",
			"com.liferay.portal.store.s3.S3Store");

}