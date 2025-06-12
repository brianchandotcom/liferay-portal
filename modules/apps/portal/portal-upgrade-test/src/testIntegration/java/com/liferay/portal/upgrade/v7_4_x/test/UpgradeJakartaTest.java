/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.dispatch.test.util.DispatchTriggerTestUtil;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.internal.messaging.TestDispatchTaskExecutor;
import com.liferay.portal.upgrade.v7_4_x.UpgradeJakarta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

		_upgradeProcess = new UpgradeJakarta();

		_user = TestPropsValues.getUser();
	}

	@Test
	@TestInfo("LPD-52638")
	public void testUpgradeConfiguration() throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL(
			StringBundler.concat(
				"insert into Configuration_ (configurationId, dictionary) ",
				"values ('", _JAVAX_CLASS_NAME, "', 'key=", _JAVAX_CLASS_NAME,
				"')"));

		_upgradeProcess.upgrade();

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select dictionary from Configuration_ where ",
					"configurationId = '", _JAKARTA_CLASS_NAME, "'"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet.next());

			Assert.assertEquals(
				resultSet.getString(1), "key=" + _JAKARTA_CLASS_NAME,
				resultSet.getString(1));
		}
		finally {
			db.runSQL(
				"delete from Configuration_ where configurationId = '" +
					_JAKARTA_CLASS_NAME + "'");

			db.runSQL(
				"delete from Configuration_ where configurationId = '" +
					_JAVAX_CLASS_NAME + "'");
		}
	}

	@Test
	@TestInfo("LPD-52638")
	public void testUpgradeDispatchTrigger() throws Exception {
		DispatchTrigger dispatchTrigger = null;

		try {
			dispatchTrigger = DispatchTriggerTestUtil.randomDispatchTrigger(
				_user,
				TestDispatchTaskExecutor.DISPATCH_TASK_EXECUTOR_TYPE_TEST, 1);

			dispatchTrigger.setDispatchTaskSettingsUnicodeProperties(
				new UnicodeProperties(
					HashMapBuilder.put(
						_PARAMETERS_KEY, _JAVAX_PARAMETERS
					).build(),
					false));

			dispatchTrigger = _dispatchTriggerLocalService.addDispatchTrigger(
				null, dispatchTrigger.getUserId(),
				dispatchTrigger.getDispatchTaskExecutorType(),
				dispatchTrigger.getDispatchTaskSettingsUnicodeProperties(),
				dispatchTrigger.getName(), dispatchTrigger.isSystem());

			_upgradeProcess.upgrade();

			_multiVMPool.clear();

			DispatchTrigger updatedDispatchTrigger =
				_dispatchTriggerLocalService.getDispatchTrigger(
					dispatchTrigger.getDispatchTriggerId());

			Assert.assertNotNull(updatedDispatchTrigger);

			UnicodeProperties unicodeProperties =
				updatedDispatchTrigger.
					getDispatchTaskSettingsUnicodeProperties();

			Assert.assertEquals(
				_JAKARTA_PARAMETERS,
				unicodeProperties.getProperty(_PARAMETERS_KEY));
		}
		finally {
			if (dispatchTrigger != null) {
				_dispatchTriggerLocalService.deleteDispatchTrigger(
					dispatchTrigger.getDispatchTriggerId());
			}
		}
	}

	@Test
	@TestInfo("LPD-52638")
	public void testUpgradeExportImportConfiguration() throws Exception {
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
									"className",
									new String[] {_JAVAX_CLASS_NAME}
								).build()));

			_upgradeProcess.upgrade();

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
					_JAKARTA_CLASS_NAME
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

	private static final String _JAKARTA_CLASS_NAME =
		"jakarta.portlet.test.UpgradeJakartaTest";

	private static final String _JAKARTA_PARAMETERS =
		"-Xms256M -Xmx1024M -Djakarta.xml.ws.client=xyz";

	private static final String _JAVAX_CLASS_NAME =
		"javax.portlet.test.UpgradeJakartaTest";

	private static final String _JAVAX_PARAMETERS =
		"-Xms256M -Xmx1024M -Djavax.xml.ws.client=xyz";

	private static final String _PARAMETERS_KEY = "JAVA_OPTS";

	@Inject
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	@Inject
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MultiVMPool _multiVMPool;

	private UpgradeProcess _upgradeProcess;
	private User _user;

}