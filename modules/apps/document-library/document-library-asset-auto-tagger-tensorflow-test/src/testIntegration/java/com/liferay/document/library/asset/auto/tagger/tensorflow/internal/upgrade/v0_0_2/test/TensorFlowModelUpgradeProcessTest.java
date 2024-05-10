/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.asset.auto.tagger.tensorflow.internal.upgrade.v0_0_2.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class TensorFlowModelUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() {
		_store.deleteDirectory(
			0, CompanyConstants.SYSTEM,
			"com.liferay.document.library.asset.auto.tagger.tensorflow");
	}

	@Test
	public void testUpgradeProcessTensorFlowDisabled() throws Exception {
		_withTensorflowConfiguration(
			false,
			() -> {
				_runUpgradeProcess();

				Assert.assertEquals(0, _getDownloadedFilesCount());
			});
	}

	@Test
	public void testUpgradeProcessTensorFlowEnabled() throws Exception {
		_withTensorflowConfiguration(
			true,
			() -> {
				_runUpgradeProcess();

				Assert.assertEquals(2, _getDownloadedFilesCount());
			});
	}

	private int _getDownloadedFilesCount() {
		String[] fileNames = _store.getFileNames(
			0, CompanyConstants.SYSTEM,
			"com.liferay.document.library.asset.auto.tagger.tensorflow");

		return fileNames.length;
	}

	private void _runUpgradeProcess() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _UPGRADE_PROCESS_CLASS_NAME);

		upgradeProcess.upgrade();
	}

	private void _withTensorflowConfiguration(
			boolean enabled, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(), _CONFIGURATION_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"enabled", enabled
						).build())) {

			unsafeRunnable.run();
		}
	}

	private static final String _CONFIGURATION_PID =
		"com.liferay.document.library.asset.auto.tagger.tensorflow.internal." +
			"configuration." +
				"TensorFlowImageAssetAutoTagProviderCompanyConfiguration";

	private static final String _UPGRADE_PROCESS_CLASS_NAME =
		"com.liferay.document.library.asset.auto.tagger.tensorflow.internal." +
			"upgrade.v0_0_2.TensorFlowModelUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.document.library.asset.auto.tagger.tensorflow.internal.upgrade.registry.TensorFlowAssetAutoTagProviderUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject(filter = "default=true")
	private Store _store;

}