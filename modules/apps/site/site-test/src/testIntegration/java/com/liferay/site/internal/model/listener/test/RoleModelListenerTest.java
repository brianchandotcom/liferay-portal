/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.site.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.configuration.MenuAccessConfiguration;
import com.liferay.site.configuration.manager.MenuAccessConfigurationManager;

import java.util.Arrays;
import java.util.Dictionary;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class RoleModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_configurationProvider.saveGroupConfiguration(
			MenuAccessConfiguration.class, _group.getGroupId(),
			HashMapDictionaryBuilder.<String, Object>put(
				"accessToControlMenuRoleIds", new String[0]
			).put(
				"showControlMenuByRole", true
			).build());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());
	}

	@Test
	public void testAddOtherRoleTypes() throws Exception {
		_roleLocalService.addRole(
			TestPropsValues.getUserId(), null, 0, StringUtil.randomString(),
			null, null, RoleConstants.TYPE_ACCOUNT, null, _serviceContext);
		_roleLocalService.addRole(
			TestPropsValues.getUserId(), null, 0, StringUtil.randomString(),
			null, null, RoleConstants.TYPE_DEPOT, null, _serviceContext);
		_roleLocalService.addRole(
			TestPropsValues.getUserId(), null, 0, StringUtil.randomString(),
			null, null, RoleConstants.TYPE_PROVIDER, null, _serviceContext);
		_roleLocalService.addRole(
			TestPropsValues.getUserId(), null, 0, StringUtil.randomString(),
			null, null, RoleConstants.TYPE_ORGANIZATION, null, _serviceContext);
		_roleLocalService.addRole(
			TestPropsValues.getUserId(), null, 0, StringUtil.randomString(),
			null, null, RoleConstants.TYPE_PUBLICATIONS, null, _serviceContext);

		_assertConfiguration(new String[0]);
	}

	@Test
	public void testAddRole() throws Exception {
		Role role = _roleLocalService.addRole(
			TestPropsValues.getUserId(), null, 0, StringUtil.randomString(),
			null, null, RoleConstants.TYPE_SITE, null, _serviceContext);

		_assertConfiguration(new String[] {String.valueOf(role.getRoleId())});
	}

	@Test
	public void testDeleteRole() throws Exception {
		Role role1 = _roleLocalService.addRole(
			TestPropsValues.getUserId(), null, 0, StringUtil.randomString(),
			null, null, RoleConstants.TYPE_REGULAR, null, _serviceContext);
		Role role2 = _roleLocalService.addRole(
			TestPropsValues.getUserId(), null, 0, StringUtil.randomString(),
			null, null, RoleConstants.TYPE_SITE, null, _serviceContext);

		_assertConfiguration(
			new String[] {
				String.valueOf(role1.getRoleId()),
				String.valueOf(role2.getRoleId())
			});

		_roleLocalService.deleteRole(role1);

		_assertConfiguration(new String[] {String.valueOf(role2.getRoleId())});
	}

	private void _assertConfiguration(String[] expectedRolesCanSeeControlMenu)
		throws Exception {

		String filterString = StringBundler.concat(
			"(&(service.factoryPid=", MenuAccessConfiguration.class.getName(),
			".scoped)(",
			ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey(), "=",
			_group.getGroupId(), "))");

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		Assert.assertNotNull(configurations);
		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);

		Configuration configuration = configurations[0];

		Dictionary<String, Object> properties = configuration.getProperties();

		String[] accessToControlMenuRoleIds = (String[])properties.get(
			"accessToControlMenuRoleIds");

		Assert.assertArrayEquals(
			Arrays.toString(accessToControlMenuRoleIds),
			expectedRolesCanSeeControlMenu, accessToControlMenuRoleIds);

		Assert.assertTrue(
			GetterUtil.getBoolean(properties.get("showControlMenuByRole")));
	}

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	@Inject
	private ConfigurationProvider _configurationProvider;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MenuAccessConfigurationManager _menuAccessConfigurationManager;

	@Inject
	private RoleLocalService _roleLocalService;

	private ServiceContext _serviceContext;

}