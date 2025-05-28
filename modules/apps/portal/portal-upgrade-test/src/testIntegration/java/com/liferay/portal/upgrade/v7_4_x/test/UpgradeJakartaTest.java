/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.v7_4_x.UpgradeJakarta;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class UpgradeJakartaTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testUpgrade() throws Exception {
		ExportImportConfiguration exportImportConfiguration = null;

		try {
			exportImportConfiguration =
				_exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						TestPropsValues.getUserId(),
						ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
						ExportImportConfigurationSettingsMapFactoryUtil.
							buildExportLayoutSettingsMap(
								TestPropsValues.getUser(), _group.getGroupId(),
								false, new long[0],
								HashMapBuilder.put(
									"className", new String[] {_JAVAX_SETTING}
								).build()));

			UpgradeProcess upgradeProcess = new UpgradeJakarta();

			upgradeProcess.upgrade();

			_multiVMPool.clear();

			ExportImportConfiguration updatedExportImportConfiguration =
				_exportImportConfigurationLocalService.
					getExportImportConfiguration(
						exportImportConfiguration.
							getExportImportConfigurationId());

			Assert.assertNotNull(updatedExportImportConfiguration);

			Assert.assertTrue(
				updatedExportImportConfiguration.getSettings(
				).contains(
					_JAKARTA_SETTING
				));
		}
		finally {
			if (exportImportConfiguration != null) {
				_exportImportConfigurationLocalService.
					deleteExportImportConfiguration(
						exportImportConfiguration.
							getExportImportConfigurationId());
			}
		}
	}

	private static final String _JAKARTA_SETTING =
		"jakarta.portlet.test.UpgradeJakartaTest";

	private static final String _JAVAX_SETTING =
		"javax.portlet.test.UpgradeJakartaTest";

	@Inject
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MultiVMPool _multiVMPool;

}