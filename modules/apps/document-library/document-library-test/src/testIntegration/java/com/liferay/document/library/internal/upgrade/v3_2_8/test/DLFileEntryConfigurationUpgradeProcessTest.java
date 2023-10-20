/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.upgrade.v3_2_8.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.constants.DLFileEntryConfigurationConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Marco Galluzzi
 */
@RunWith(Arquillian.class)
public class DLFileEntryConfigurationUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		Configuration systemConfiguration = _getSystemConfiguration(
			_CLASS_NAME_DL_FILE_ENTRY_CONFIGURATION);

		if (systemConfiguration != null) {
			_originalDLFileEntrySystemConfigurationProperties =
				systemConfiguration.getProperties();

			systemConfiguration.delete();
		}

		systemConfiguration = _getSystemConfiguration(
			_CLASS_NAME_PDF_PREVIEW_CONFIGURATION);

		if (systemConfiguration != null) {
			_originalPDFPreviewSystemConfigurationProperties =
				systemConfiguration.getProperties();

			systemConfiguration.delete();
		}

		Configuration[] scopedConfigurations = _getScopedConfigurations(
			_CLASS_NAME_PDF_PREVIEW_CONFIGURATION);

		_originalPDFPreviewScopedConfigurationsProperties = new ArrayList<>();

		for (Configuration scopedConfiguration : scopedConfigurations) {
			if (scopedConfiguration != null) {
				_originalPDFPreviewScopedConfigurationsProperties.add(
					scopedConfiguration.getProperties());

				scopedConfiguration.delete();
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		_deleteConfigurations(_CLASS_NAME_DL_FILE_ENTRY_CONFIGURATION);
		_deleteConfigurations(_CLASS_NAME_PDF_PREVIEW_CONFIGURATION);

		if (_originalDLFileEntrySystemConfigurationProperties != null) {
			_createSystemConfiguration(
				_CLASS_NAME_DL_FILE_ENTRY_CONFIGURATION,
				_originalDLFileEntrySystemConfigurationProperties);
		}

		if (_originalPDFPreviewSystemConfigurationProperties != null) {
			_createSystemConfiguration(
				_CLASS_NAME_PDF_PREVIEW_CONFIGURATION,
				_originalPDFPreviewSystemConfigurationProperties);
		}

		for (Dictionary<String, Object> properties :
				_originalPDFPreviewScopedConfigurationsProperties) {

			_createScopedConfiguration(
				_CLASS_NAME_PDF_PREVIEW_CONFIGURATION, properties);
		}
	}

	@Test
	public void testUpgradeWithAllConfigurations() throws Exception {
		_createSystemConfiguration(
			_CLASS_NAME_DL_FILE_ENTRY_CONFIGURATION,
			_createDictionary(
				_PREVIEWABLE_PROCESSOR_MAX_SIZE_KEY,
				DLFileEntryConfigurationConstants.
					PREVIEWABLE_PROCESSOR_MAX_SIZE_DEFAULT * 2));
		_createSystemConfiguration(
			_CLASS_NAME_PDF_PREVIEW_CONFIGURATION,
			_createDictionary(_MAX_NUMBER_OF_PAGES_KEY, 10));
		_createScopedConfiguration(
			_CLASS_NAME_PDF_PREVIEW_CONFIGURATION,
			_createDictionary(_MAX_NUMBER_OF_PAGES_KEY, 8),
			ExtendedObjectClassDefinition.Scope.COMPANY);
		_createScopedConfiguration(
			_CLASS_NAME_PDF_PREVIEW_CONFIGURATION,
			_createDictionary(_MAX_NUMBER_OF_PAGES_KEY, 6),
			ExtendedObjectClassDefinition.Scope.GROUP);

		try {
			try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
					_CLASS_NAME_UPGRADE_PROCESS, LoggerTestUtil.OFF)) {

				UpgradeProcess upgradeProcess = _getUpgradeProcess();

				upgradeProcess.upgrade();
			}

			_assertConfigurationValuesEquals(
				_getDLFileEntryConfiguration(
					ExtendedObjectClassDefinition.Scope.SYSTEM),
				10,
				DLFileEntryConfigurationConstants.
					PREVIEWABLE_PROCESSOR_MAX_SIZE_DEFAULT * 2);
			_assertConfigurationValuesEquals(
				_getDLFileEntryConfiguration(
					ExtendedObjectClassDefinition.Scope.COMPANY),
				8,
				DLFileEntryConfigurationConstants.
					PREVIEWABLE_PROCESSOR_MAX_SIZE_DEFAULT * 2);
			_assertConfigurationValuesEquals(
				_getDLFileEntryConfiguration(
					ExtendedObjectClassDefinition.Scope.GROUP),
				6,
				DLFileEntryConfigurationConstants.
					PREVIEWABLE_PROCESSOR_MAX_SIZE_DEFAULT * 2);

			Assert.assertNull(
				_getConfigurations(_CLASS_NAME_PDF_PREVIEW_CONFIGURATION));
		}
		finally {
			_deleteConfigurations(_CLASS_NAME_DL_FILE_ENTRY_CONFIGURATION);
			_deleteConfigurations(_CLASS_NAME_PDF_PREVIEW_CONFIGURATION);
		}
	}

	@Test
	public void testUpgradeWithAllConfigurationsWithMultipleCompaniesAndGroups()
		throws Exception {

		_createSystemConfiguration(
			_CLASS_NAME_DL_FILE_ENTRY_CONFIGURATION,
			_createDictionary(_PREVIEWABLE_PROCESSOR_MAX_SIZE_KEY, 1000L));
		_createSystemConfiguration(
			_CLASS_NAME_PDF_PREVIEW_CONFIGURATION,
			_createDictionary(_MAX_NUMBER_OF_PAGES_KEY, 10));
		_createScopedConfiguration(
			_CLASS_NAME_PDF_PREVIEW_CONFIGURATION,
			_createDictionary(_MAX_NUMBER_OF_PAGES_KEY, 8),
			ExtendedObjectClassDefinition.Scope.COMPANY);
		_createScopedConfiguration(
			_CLASS_NAME_PDF_PREVIEW_CONFIGURATION,
			_createDictionary(_MAX_NUMBER_OF_PAGES_KEY, 6),
			ExtendedObjectClassDefinition.Scope.GROUP);

		Dictionary<String, Object> properties = _createDictionary(
			_MAX_NUMBER_OF_PAGES_KEY, 7);

		properties.put(
			ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
			77777L);

		_createScopedConfiguration(
			_CLASS_NAME_PDF_PREVIEW_CONFIGURATION, properties);

		properties = _createDictionary(_MAX_NUMBER_OF_PAGES_KEY, 5);

		properties.put(
			ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey(), 55555L);

		_createScopedConfiguration(
			_CLASS_NAME_PDF_PREVIEW_CONFIGURATION, properties);

		try {
			try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
					_CLASS_NAME_UPGRADE_PROCESS, LoggerTestUtil.OFF)) {

				UpgradeProcess upgradeProcess = _getUpgradeProcess();

				upgradeProcess.upgrade();
			}

			_assertConfigurationValuesEquals(
				_getDLFileEntryConfiguration(
					ExtendedObjectClassDefinition.Scope.SYSTEM),
				10, 1000L);
			_assertConfigurationValuesEquals(
				_getDLFileEntryConfiguration(
					ExtendedObjectClassDefinition.Scope.COMPANY),
				8,
				DLFileEntryConfigurationConstants.
					PREVIEWABLE_PROCESSOR_MAX_SIZE_DEFAULT);
			_assertConfigurationValuesEquals(
				_getDLFileEntryConfiguration(
					ExtendedObjectClassDefinition.Scope.GROUP),
				6,
				DLFileEntryConfigurationConstants.
					PREVIEWABLE_PROCESSOR_MAX_SIZE_DEFAULT);

			_assertConfigurationValuesEquals(
				_getScopedConfiguration(
					_CLASS_NAME_DL_FILE_ENTRY_CONFIGURATION,
					ExtendedObjectClassDefinition.Scope.COMPANY, 77777),
				7,
				DLFileEntryConfigurationConstants.
					PREVIEWABLE_PROCESSOR_MAX_SIZE_DEFAULT);

			_assertConfigurationValuesEquals(
				_getScopedConfiguration(
					_CLASS_NAME_DL_FILE_ENTRY_CONFIGURATION,
					ExtendedObjectClassDefinition.Scope.GROUP, 55555),
				5,
				DLFileEntryConfigurationConstants.
					PREVIEWABLE_PROCESSOR_MAX_SIZE_DEFAULT);

			Assert.assertNull(
				_getConfigurations(_CLASS_NAME_PDF_PREVIEW_CONFIGURATION));
		}
		finally {
			_deleteConfigurations(_CLASS_NAME_DL_FILE_ENTRY_CONFIGURATION);
			_deleteConfigurations(_CLASS_NAME_PDF_PREVIEW_CONFIGURATION);
		}
	}

	@Test
	public void testUpgradeWithCompanyPDFPreviewConfigurationsOnly()
		throws Exception {

		_createScopedConfiguration(
			_CLASS_NAME_PDF_PREVIEW_CONFIGURATION,
			_createDictionary(_MAX_NUMBER_OF_PAGES_KEY, 8),
			ExtendedObjectClassDefinition.Scope.COMPANY);

		try {
			try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
					_CLASS_NAME_UPGRADE_PROCESS, LoggerTestUtil.OFF)) {

				UpgradeProcess upgradeProcess = _getUpgradeProcess();

				upgradeProcess.upgrade();
			}

			Assert.assertNull(
				_getDLFileEntryConfiguration(
					ExtendedObjectClassDefinition.Scope.SYSTEM));
			_assertConfigurationValuesEquals(
				_getDLFileEntryConfiguration(
					ExtendedObjectClassDefinition.Scope.COMPANY),
				8,
				DLFileEntryConfigurationConstants.
					PREVIEWABLE_PROCESSOR_MAX_SIZE_DEFAULT);
			Assert.assertNull(
				_getDLFileEntryConfiguration(
					ExtendedObjectClassDefinition.Scope.GROUP));

			Assert.assertNull(
				_getConfigurations(_CLASS_NAME_PDF_PREVIEW_CONFIGURATION));
		}
		finally {
			_deleteConfigurations(_CLASS_NAME_DL_FILE_ENTRY_CONFIGURATION);
			_deleteConfigurations(_CLASS_NAME_PDF_PREVIEW_CONFIGURATION);
		}
	}

	@Test
	public void testUpgradeWithGroupPDFPreviewConfigurationsOnly()
		throws Exception {

		_createScopedConfiguration(
			_CLASS_NAME_PDF_PREVIEW_CONFIGURATION,
			_createDictionary(_MAX_NUMBER_OF_PAGES_KEY, 6),
			ExtendedObjectClassDefinition.Scope.GROUP);

		try {
			try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
					_CLASS_NAME_UPGRADE_PROCESS, LoggerTestUtil.OFF)) {

				UpgradeProcess upgradeProcess = _getUpgradeProcess();

				upgradeProcess.upgrade();
			}

			Assert.assertNull(
				_getDLFileEntryConfiguration(
					ExtendedObjectClassDefinition.Scope.SYSTEM));
			Assert.assertNull(
				_getDLFileEntryConfiguration(
					ExtendedObjectClassDefinition.Scope.COMPANY));
			_assertConfigurationValuesEquals(
				_getDLFileEntryConfiguration(
					ExtendedObjectClassDefinition.Scope.GROUP),
				6,
				DLFileEntryConfigurationConstants.
					PREVIEWABLE_PROCESSOR_MAX_SIZE_DEFAULT);

			Assert.assertNull(
				_getConfigurations(_CLASS_NAME_PDF_PREVIEW_CONFIGURATION));
		}
		finally {
			_deleteConfigurations(_CLASS_NAME_DL_FILE_ENTRY_CONFIGURATION);
			_deleteConfigurations(_CLASS_NAME_PDF_PREVIEW_CONFIGURATION);
		}
	}

	@Test
	public void testUpgradeWithoutAnyConfiguration() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME_UPGRADE_PROCESS, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = _getUpgradeProcess();

			upgradeProcess.upgrade();
		}

		Assert.assertNull(
			_getDLFileEntryConfiguration(
				ExtendedObjectClassDefinition.Scope.SYSTEM));
		Assert.assertNull(
			_getDLFileEntryConfiguration(
				ExtendedObjectClassDefinition.Scope.COMPANY));
		Assert.assertNull(
			_getDLFileEntryConfiguration(
				ExtendedObjectClassDefinition.Scope.GROUP));

		Assert.assertNull(
			_getConfigurations(_CLASS_NAME_PDF_PREVIEW_CONFIGURATION));
	}

	@Test
	public void testUpgradeWithSystemDLFileEntryConfigurationOnly()
		throws Exception {

		_createSystemConfiguration(
			_CLASS_NAME_DL_FILE_ENTRY_CONFIGURATION,
			_createDictionary(_PREVIEWABLE_PROCESSOR_MAX_SIZE_KEY, 1000L));

		try {
			try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
					_CLASS_NAME_UPGRADE_PROCESS, LoggerTestUtil.OFF)) {

				UpgradeProcess upgradeProcess = _getUpgradeProcess();

				upgradeProcess.upgrade();
			}

			_assertConfigurationValuesEquals(
				_getDLFileEntryConfiguration(
					ExtendedObjectClassDefinition.Scope.SYSTEM),
				DLFileEntryConfigurationConstants.MAX_NUMBER_OF_PAGES_DEFAULT,
				1000L);
			Assert.assertNull(
				_getDLFileEntryConfiguration(
					ExtendedObjectClassDefinition.Scope.COMPANY));
			Assert.assertNull(
				_getDLFileEntryConfiguration(
					ExtendedObjectClassDefinition.Scope.GROUP));

			Assert.assertNull(
				_getConfigurations(_CLASS_NAME_PDF_PREVIEW_CONFIGURATION));
		}
		finally {
			_deleteConfigurations(_CLASS_NAME_DL_FILE_ENTRY_CONFIGURATION);
		}
	}

	@Test
	public void testUpgradeWithSystemPDFPreviewConfigurationOnly()
		throws Exception {

		_createSystemConfiguration(
			_CLASS_NAME_PDF_PREVIEW_CONFIGURATION,
			_createDictionary(_MAX_NUMBER_OF_PAGES_KEY, 10));

		try {
			try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
					_CLASS_NAME_UPGRADE_PROCESS, LoggerTestUtil.OFF)) {

				UpgradeProcess upgradeProcess = _getUpgradeProcess();

				upgradeProcess.upgrade();
			}

			_assertConfigurationValuesEquals(
				_getDLFileEntryConfiguration(
					ExtendedObjectClassDefinition.Scope.SYSTEM),
				10,
				DLFileEntryConfigurationConstants.
					PREVIEWABLE_PROCESSOR_MAX_SIZE_DEFAULT);
			Assert.assertNull(
				_getDLFileEntryConfiguration(
					ExtendedObjectClassDefinition.Scope.COMPANY));
			Assert.assertNull(
				_getDLFileEntryConfiguration(
					ExtendedObjectClassDefinition.Scope.GROUP));

			Assert.assertNull(
				_getConfigurations(_CLASS_NAME_PDF_PREVIEW_CONFIGURATION));
		}
		finally {
			_deleteConfigurations(_CLASS_NAME_DL_FILE_ENTRY_CONFIGURATION);
			_deleteConfigurations(_CLASS_NAME_PDF_PREVIEW_CONFIGURATION);
		}
	}

	private void _assertConfigurationValuesEquals(
			Configuration configuration, int maxNumberOfPages,
			long previewableProcessorMaxSize)
		throws Exception {

		Assert.assertNotNull(configuration);

		Dictionary<String, Object> properties = configuration.getProperties();

		Assert.assertNotNull(properties);

		Assert.assertEquals(
			maxNumberOfPages, properties.get(_MAX_NUMBER_OF_PAGES_KEY));
		Assert.assertEquals(
			previewableProcessorMaxSize,
			properties.get(_PREVIEWABLE_PROCESSOR_MAX_SIZE_KEY));
	}

	private HashMapDictionary<String, Object> _createDictionary(
		String key, Object value) {

		return HashMapDictionaryBuilder.<String, Object>put(
			key, value
		).build();
	}

	private Configuration _createScopedConfiguration(
			String className, Dictionary<String, Object> properties)
		throws Exception {

		Configuration configuration =
			_configurationAdmin.createFactoryConfiguration(
				className + ".scoped", StringPool.QUESTION);

		configuration.update(properties);

		return configuration;
	}

	private void _createScopedConfiguration(
			String className, Dictionary<String, Object> properties,
			ExtendedObjectClassDefinition.Scope scope)
		throws Exception {

		if (scope == ExtendedObjectClassDefinition.Scope.COMPANY) {
			properties.put(
				scope.getPropertyKey(), TestPropsValues.getCompanyId());
		}
		else {
			properties.put(
				scope.getPropertyKey(), TestPropsValues.getGroupId());
		}

		_createScopedConfiguration(className, properties);
	}

	private void _createSystemConfiguration(
			String className, Dictionary<String, Object> properties)
		throws Exception {

		Configuration configuration = _configurationAdmin.getConfiguration(
			className, StringPool.QUESTION);

		configuration.update(properties);
	}

	private void _deleteConfigurations(String className) throws Exception {
		Configuration[] configurations = _getConfigurations(className);

		if (configurations == null) {
			return;
		}

		for (Configuration configuration : configurations) {
			configuration.delete();
		}
	}

	private Configuration[] _getConfigurations(String className)
		throws Exception {

		return _configurationAdmin.listConfigurations(
			String.format("(%s=%s*)", Constants.SERVICE_PID, className));
	}

	private Configuration _getDLFileEntryConfiguration(
			ExtendedObjectClassDefinition.Scope scope)
		throws Exception {

		if (scope == ExtendedObjectClassDefinition.Scope.COMPANY) {
			return _getScopedConfiguration(
				_CLASS_NAME_DL_FILE_ENTRY_CONFIGURATION, scope,
				TestPropsValues.getCompanyId());
		}
		else if (scope == ExtendedObjectClassDefinition.Scope.GROUP) {
			return _getScopedConfiguration(
				_CLASS_NAME_DL_FILE_ENTRY_CONFIGURATION, scope,
				TestPropsValues.getGroupId());
		}

		return _getSystemConfiguration(_CLASS_NAME_DL_FILE_ENTRY_CONFIGURATION);
	}

	private Configuration _getScopedConfiguration(
			String className, ExtendedObjectClassDefinition.Scope scope,
			long scopePK)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			String.format(
				"(&(%s=%s)(%s=%d))", ConfigurationAdmin.SERVICE_FACTORYPID,
				className + ".scoped", scope.getPropertyKey(), scopePK));

		if (configurations == null) {
			return null;
		}

		return configurations[0];
	}

	private Configuration[] _getScopedConfigurations(String className)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			String.format(
				"(%s=%s)", ConfigurationAdmin.SERVICE_FACTORYPID,
				className + ".scoped"));

		if (configurations == null) {
			return new Configuration[0];
		}

		return configurations;
	}

	private Configuration _getSystemConfiguration(String className)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			String.format("(%s=%s)", Constants.SERVICE_PID, className));

		if (configurations == null) {
			return null;
		}

		return configurations[0];
	}

	private UpgradeProcess _getUpgradeProcess() {
		UpgradeProcess[] upgradeProcesses = new UpgradeProcess[1];

		_upgradeStepRegistrator.register(
			(fromSchemaVersionString, toSchemaVersionString, upgradeSteps) -> {
				for (UpgradeStep upgradeStep : upgradeSteps) {
					Class<? extends UpgradeStep> clazz = upgradeStep.getClass();

					if (Objects.equals(
							clazz.getName(), _CLASS_NAME_UPGRADE_PROCESS)) {

						upgradeProcesses[0] = (UpgradeProcess)upgradeStep;

						break;
					}
				}
			});

		return upgradeProcesses[0];
	}

	private static final String _CLASS_NAME_DL_FILE_ENTRY_CONFIGURATION =
		"com.liferay.document.library.configuration.DLFileEntryConfiguration";

	private static final String _CLASS_NAME_PDF_PREVIEW_CONFIGURATION =
		"com.liferay.document.library.preview.pdf.internal.configuration." +
			"PDFPreviewConfiguration";

	private static final String _CLASS_NAME_UPGRADE_PROCESS =
		"com.liferay.document.library.internal.upgrade.v3_2_8." +
			"DLFileEntryConfigurationUpgradeProcess";

	private static final String _MAX_NUMBER_OF_PAGES_KEY = "maxNumberOfPages";

	private static final String _PREVIEWABLE_PROCESSOR_MAX_SIZE_KEY =
		"previewableProcessorMaxSize";

	@Inject(
		filter = "(&(component.name=com.liferay.document.library.internal.upgrade.registry.DLServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	private Dictionary<String, Object>
		_originalDLFileEntrySystemConfigurationProperties;
	private List<Dictionary<String, Object>>
		_originalPDFPreviewScopedConfigurationsProperties;
	private Dictionary<String, Object>
		_originalPDFPreviewSystemConfigurationProperties;

}