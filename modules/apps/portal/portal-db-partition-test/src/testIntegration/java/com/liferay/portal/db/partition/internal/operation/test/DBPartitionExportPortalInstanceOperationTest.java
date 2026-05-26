/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.operation.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.module.util.BundleUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;

import java.io.Serializable;

import java.lang.reflect.Constructor;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;

/**
 * @author Mariano Álvaro Sáiz
 */
@RunWith(Arquillian.class)
public class DBPartitionExportPortalInstanceOperationTest
	extends BasePortalInstanceOperationTestCase {

	@Override
	public String getComponentName() {
		return "ExportPortalInstanceOperation";
	}

	@FeatureFlag("LPD-11342")
	@Test
	public void testDeployConfigurationFileWithFF() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.instances.internal.operation." +
					"ExportPortalInstanceOperation",
				LoggerTestUtil.ERROR)) {

			deployConfigurationFile(_PID, "exportCompanyId=L\"0\"\n");

			assertLog(
				logCapture, "Portal instance with company ID 0 does not exist");
		}

		assertConfigurationFileIsDeletedAfterDeploy(_PID);
	}

	@Test
	public void testDeployConfigurationFileWithoutFF() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.instances.internal.operation." +
					"BasePortalInstanceOperation",
				LoggerTestUtil.ERROR)) {

			deployConfigurationFile(_PID, "exportCompanyId=L\"0\"\n");

			assertLogException(
				logCapture, "Feature flag LPD-11342 is disabled");
		}

		assertConfigurationFileIsDeletedAfterDeploy(_PID);
	}

	@FeatureFlag("LPD-11342")
	@Test
	public void testDeployConfigurationWithFF() throws Exception {
		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					PortalInstancePool.getDefaultCompanyId());
			LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.instances.internal.operation." +
					"ExportPortalInstanceOperation",
				LoggerTestUtil.ERROR)) {

			deployConfiguration(
				_PID,
				HashMapDictionaryBuilder.<String, Object>put(
					"exportCompanyId", 0
				).build());

			assertLog(
				logCapture, "Portal instance with company ID 0 does not exist");

			assertConfigurationIsDeletedAfterDeploy(_PID);
		}
	}

	@Test
	public void testDeployConfigurationWithoutFF() throws Exception {
		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					PortalInstancePool.getDefaultCompanyId());
			LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.instances.internal.operation." +
					"BasePortalInstanceOperation",
				LoggerTestUtil.ERROR)) {

			deployConfiguration(
				_PID,
				HashMapDictionaryBuilder.<String, Object>put(
					"exportCompanyId", 0
				).build());

			assertLogException(
				logCapture, "Feature flag LPD-11342 is disabled");

			assertConfigurationIsDeletedAfterDeploy(_PID);
		}
	}

	@Test
	public void testIsApplicable() throws Exception {
		Bundle bundle = BundleUtil.getBundle(
			SystemBundleUtil.getBundleContext(),
			"com.liferay.portal.instances.service");

		Class<?> exportPortalInstanceOperationClass = bundle.loadClass(
			"com.liferay.portal.instances.internal.operation." +
				"ExportPortalInstanceOperation");

		Object exportPortalInstanceOperation =
			exportPortalInstanceOperationClass.newInstance();

		ReflectionTestUtil.setFieldValue(
			exportPortalInstanceOperation, "_groupLocalService",
			_groupLocalService);

		Class<?> scopedConfigurationClass =
			exportPortalInstanceOperationClass.getDeclaredClasses()[0];

		Constructor<?> scopedConfigurationConstructor =
			scopedConfigurationClass.getDeclaredConstructor(
				exportPortalInstanceOperationClass, String.class, String.class,
				Serializable.class, ExtendedObjectClassDefinition.Scope.class);

		scopedConfigurationConstructor.setAccessible(true);

		Object scopedConfiguration = scopedConfigurationConstructor.newInstance(
			exportPortalInstanceOperation, _SCOPED_CONFIGURATION_PID, "",
			GroupConstants.DEFAULT_PARENT_GROUP_ID,
			ExtendedObjectClassDefinition.Scope.GROUP);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				exportPortalInstanceOperationClass.getName(),
				LoggerTestUtil.WARN)) {

			Assert.assertFalse(
				ReflectionTestUtil.invoke(
					exportPortalInstanceOperation, "_isApplicable",
					new Class<?>[] {long.class, scopedConfigurationClass},
					PortalInstancePool.getDefaultCompanyId(),
					scopedConfiguration));

			assertLog(
				logCapture,
				"Skipping configuration " + _SCOPED_CONFIGURATION_PID +
					" because group 0 does not exist");
		}
	}

	private static final String _PID =
		"com.liferay.portal.instances.internal.configuration." +
			"ExportPortalInstanceConfiguration";

	private static final String _SCOPED_CONFIGURATION_PID =
		DBPartitionExportPortalInstanceOperationTest.class.getName() +
			"ScopedConfiguration";

	@Inject
	private GroupLocalService _groupLocalService;

}