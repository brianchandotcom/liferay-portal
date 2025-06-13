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
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectValidationRule;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectValidationRuleService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.security.script.management.test.util.ScriptManagementConfigurationTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.internal.messaging.TestDispatchTaskExecutor;
import com.liferay.portal.upgrade.v7_4_x.UpgradeJakarta;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Collections;

import org.junit.After;
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

		_originalName = PrincipalThreadLocal.getName();
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		_user = TestPropsValues.getUser();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		PrincipalThreadLocal.setName(_user.getUserId());

		_upgradeProcess = new UpgradeJakarta();

		ScriptManagementConfigurationTestUtil.save(true);
	}

	@After
	public void tearDown() throws Exception {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);

		ScriptManagementConfigurationTestUtil.save(false);
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

	@Test
	@TestInfo("LPD-52638")
	public void testUpgradeObjectAction() throws Exception {
		ObjectAction objectAction = null;
		ObjectDefinition objectDefinition = null;

		try {
			objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition(
				Collections.singletonList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"a" + RandomTestUtil.randomString()
					).build()));

			objectAction = _objectActionLocalService.addObjectAction(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId(), true,
				StringPool.BLANK, RandomTestUtil.randomString(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				RandomTestUtil.randomString(),
				ObjectActionExecutorConstants.KEY_GROOVY,
				ObjectActionTriggerConstants.KEY_STANDALONE,
				UnicodePropertiesBuilder.put(
					"script", _JAVAX_SCRIPT
				).build(),
				false);

			_upgradeProcess.upgrade();

			_multiVMPool.clear();

			ObjectAction updatedObjectAction =
				_objectActionLocalService.getObjectAction(
					objectAction.getObjectActionId());

			Assert.assertNotNull(updatedObjectAction);

			Assert.assertEquals(
				"script=" + _JAKARTA_SCRIPT + "\n",
				updatedObjectAction.getParameters());
		}
		finally {
			if (objectAction != null) {
				_objectActionLocalService.deleteObjectAction(objectAction);
			}

			if (objectDefinition != null) {
				_objectDefinitionLocalService.deleteObjectDefinition(
					objectDefinition);
			}
		}
	}

	@Test
	@TestInfo("LPD-52638")
	public void testUpgradeObjectValidationRule() throws Exception {
		ObjectDefinition objectDefinition = null;
		ObjectValidationRule objectValidationRule = null;

		try {
			objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition(
				Collections.singletonList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"a" + RandomTestUtil.randomString()
					).build()));

			objectValidationRule =
				_objectValidationRuleService.addObjectValidationRule(
					StringPool.BLANK, objectDefinition.getObjectDefinitionId(),
					true, ObjectValidationRuleConstants.ENGINE_TYPE_GROOVY,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION,
					_JAVAX_SCRIPT, false, Collections.emptyList());

			_upgradeProcess.upgrade();

			_multiVMPool.clear();

			ObjectValidationRule updatedObjectValidationRule =
				_objectValidationRuleService.getObjectValidationRule(
					objectValidationRule.getObjectValidationRuleId());

			Assert.assertNotNull(updatedObjectValidationRule);

			Assert.assertEquals(
				_JAKARTA_SCRIPT, updatedObjectValidationRule.getScript());
		}
		finally {
			if (objectValidationRule != null) {
				_objectValidationRuleService.deleteObjectValidationRule(
					objectValidationRule.getObjectValidationRuleId());
			}

			if (objectDefinition != null) {
				_objectDefinitionLocalService.deleteObjectDefinition(
					objectDefinition);
			}
		}
	}

	private static final String _JAKARTA_CLASS_NAME =
		"jakarta.portlet.test.UpgradeJakartaTest";

	private static final String _JAKARTA_PARAMETERS =
		"-Xms256M -Xmx1024M -Djakarta.xml.ws.client=xyz";

	private static final String _JAKARTA_SCRIPT =
		"System.out.println(\"import jakarta.servlet.GenericServlet\");";

	private static final String _JAVAX_CLASS_NAME =
		"javax.portlet.test.UpgradeJakartaTest";

	private static final String _JAVAX_PARAMETERS =
		"-Xms256M -Xmx1024M -Djavax.xml.ws.client=xyz";

	private static final String _JAVAX_SCRIPT =
		"System.out.println(\"import javax.servlet.GenericServlet\");";

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

	@Inject
	private ObjectActionLocalService _objectActionLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectValidationRuleService _objectValidationRuleService;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;
	private UpgradeProcess _upgradeProcess;
	private User _user;

}