/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.content.security.policy.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Jorge García Jiménez
 */
@FeatureFlag("LPS-134060")
@RunWith(Arquillian.class)
public class CSPOSGiCommandsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testResettingCSPConfigurationsViaGoGoShell() throws Exception {
		_contentSecurityPolicyConfiguration =
			_configurationAdmin.getConfiguration(
				_CLASS_NAME, StringPool.QUESTION);

		ConfigurationTestUtil.saveConfiguration(
			_contentSecurityPolicyConfiguration,
			HashMapDictionaryBuilder.<String, Object>put(
				"enabled", false
			).put(
				"excludedPaths", "/api"
			).put(
				"policy", "script-src 'unsafe-inline';"
			).build());

		_resetSystemConfiguration();

		_assertConfigurationIsDeleted(
			ExtendedObjectClassDefinition.Scope.SYSTEM, 0L, false);

		_createScopedConfiguration("companyId", _company.getCompanyId());

		_resetCompanyConfiguration(String.valueOf(_company.getCompanyId()));

		_assertConfigurationIsDeleted(
			ExtendedObjectClassDefinition.Scope.COMPANY,
			_company.getCompanyId(), true);

		_createScopedConfiguration("groupId", _group.getGroupId());

		_resetGroupConfiguration(String.valueOf(_group.getGroupId()));

		_assertConfigurationIsDeleted(
			ExtendedObjectClassDefinition.Scope.GROUP, _group.getGroupId(),
			true);
	}

	private void _assertConfigurationIsDeleted(
			ExtendedObjectClassDefinition.Scope property, long propertyId,
			boolean propertyFiltered)
		throws Exception {

		String filterString;

		if (propertyFiltered) {
			filterString = StringBundler.concat(
				"(&(service.factoryPid=", _CLASS_NAME, ".scoped)(",
				property.getPropertyKey(), "=", propertyId, "))");
		}
		else {
			filterString = StringBundler.concat(
				"(&(service.pid=", _CLASS_NAME, "))");
		}

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		Assert.assertNull(configurations);
	}

	private void _createScopedConfiguration(String property, long propertyId)
		throws Exception {

		ConfigurationTestUtil.createFactoryConfiguration(
			_CLASS_NAME + ".scoped",
			HashMapDictionaryBuilder.<String, Object>put(
				property, propertyId
			).put(
				"enabled", false
			).put(
				"excludedPaths", "/api"
			).put(
				"policy", "script-src 'unsafe-inline';"
			).build());
	}

	private void _resetCompanyConfiguration(String companyId) throws Exception {
		_runWithId("resetCompanyConfiguration", companyId);
	}

	private void _resetGroupConfiguration(String groupId) throws Exception {
		_runWithId("resetGroupConfiguration", groupId);
	}

	private void _resetSystemConfiguration() throws Exception {
		_run("resetSystemConfiguration");
	}

	private void _run(String functionName) throws Exception {
		Class<?> clazz = _cspOSGiCommands.getClass();

		Method method = clazz.getMethod(functionName);

		method.invoke(_cspOSGiCommands);
	}

	private void _runWithId(String functionName, String id) throws Exception {
		Class<?> clazz = _cspOSGiCommands.getClass();

		Method method = clazz.getMethod(functionName, String.class);

		method.invoke(_cspOSGiCommands, id);
	}

	private static final String _CLASS_NAME =
		"com.liferay.portal.security.content.security.policy.internal." +
			"configuration.ContentSecurityPolicyConfiguration";

	@Inject(filter = "osgi.command.scope=csp", type = Inject.NoType.class)
	private static Object _cspOSGiCommands;

	@DeleteAfterTestRun
	private Company _company;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	private Configuration _contentSecurityPolicyConfiguration;

	@DeleteAfterTestRun
	private Group _group;

}