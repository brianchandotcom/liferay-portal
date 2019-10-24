/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.reports.engine.console.model.Definition;
import com.liferay.portal.reports.engine.console.service.DefinitionLocalServiceUtil;
import com.liferay.portal.reports.engine.console.service.DefinitionServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Inácio Nery
 */
@RunWith(Arquillian.class)
public class DefinitionServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		setUpDefinition();
		setUpPermissionChecker();
	}

	@After
	public void tearDown() throws PortalException {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testGetDefinitionsAsAdminUser() throws Exception {
		List<Definition> definitions = getDefinitions(
			_adminPermissionChecker, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(definitions.toString(), 10, definitions.size());
	}

	@Test
	public void testGetDefinitionsAsGuestUser() throws Exception {
		List<Definition> definitions = getDefinitions(
			_guestPermissionChecker, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(definitions.toString(), 5, definitions.size());
	}

	@Test
	public void testGetDefinitionsCountAsAdminUser() throws Exception {
		int definitionsCount = getDefinitionsCount(_adminPermissionChecker);

		Assert.assertEquals(10, definitionsCount);
	}

	@Test
	public void testGetDefinitionsCountAsGuestUser() throws Exception {
		int definitionsCount = getDefinitionsCount(_guestPermissionChecker);

		Assert.assertEquals(5, definitionsCount);
	}

	@Test
	public void testGetDefinitionsLimitedAsAdminUser() throws Exception {
		List<Definition> definitions = getDefinitions(
			_adminPermissionChecker, 0, 5);

		Assert.assertEquals(definitions.toString(), 5, definitions.size());
	}

	@Test
	public void testGetDefinitionsLimitedAsGuestUser() throws Exception {
		List<Definition> definitions = getDefinitions(
			_guestPermissionChecker, 0, 5);

		Assert.assertEquals(definitions.toString(), 5, definitions.size());
	}

	protected List<Definition> getDefinitions(
			PermissionChecker permissionChecker, int start, int end)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		return DefinitionServiceUtil.getDefinitions(
			_group.getGroupId(), null, null, null, _TEMPLATE_NAME, false, start,
			end, null);
	}

	protected int getDefinitionsCount(PermissionChecker permissionChecker)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		return DefinitionServiceUtil.getDefinitionsCount(
			_group.getGroupId(), null, null, null, _TEMPLATE_NAME, false);
	}

	protected User getUser(String roleName) throws Exception {
		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), roleName);

		List<User> users = UserLocalServiceUtil.getRoleUsers(
			role.getRoleId(), 0, 1);

		return users.get(0);
	}

	protected void setUpDefinition() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			_DEFINITION_GROUP_PERMISSIONS, null);

		serviceContext.setModelPermissions(modelPermissions);

		for (int i = 0; i < 5; i++) {
			Map<Locale, String> nameMap = HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build();

			try (InputStream inputStream =
					DefinitionServiceTest.class.getResourceAsStream(
						"dependencies/" + _TEMPLATE_NAME + ".jrxml")) {

				DefinitionLocalServiceUtil.addDefinition(
					TestPropsValues.getUserId(), _group.getGroupId(), nameMap,
					null, 0, null, _TEMPLATE_NAME + ".jrxml", inputStream,
					serviceContext);
			}
		}

		modelPermissions = ModelPermissionsFactory.create(
			_DEFINITION_GROUP_PERMISSIONS, new String[] {"VIEW"});

		serviceContext.setModelPermissions(modelPermissions);

		for (int i = 0; i < 5; i++) {
			Map<Locale, String> nameMap = HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build();

			try (InputStream inputStream =
					DefinitionServiceTest.class.getResourceAsStream(
						"dependencies/" + _TEMPLATE_NAME + ".jrxml")) {

				DefinitionLocalServiceUtil.addDefinition(
					TestPropsValues.getUserId(), _group.getGroupId(), nameMap,
					null, 0, null, _TEMPLATE_NAME + ".jrxml", inputStream,
					serviceContext);
			}
		}
	}

	protected void setUpPermissionChecker() throws Exception {
		User adminUser = getUser(RoleConstants.ADMINISTRATOR);

		_adminPermissionChecker = PermissionCheckerFactoryUtil.create(
			adminUser);

		User guestUser = getUser(RoleConstants.GUEST);

		_guestPermissionChecker = PermissionCheckerFactoryUtil.create(
			guestUser);
	}

	private static final String[] _DEFINITION_GROUP_PERMISSIONS = {
		"ADD_REPORT", "DELETE", "PERMISSIONS", "UPDATE", "VIEW"
	};

	private static final String _TEMPLATE_NAME =
		"reports_admin_template_source_sample_list_type";

	private PermissionChecker _adminPermissionChecker;

	@DeleteAfterTestRun
	private Group _group;

	private PermissionChecker _guestPermissionChecker;
	private PermissionChecker _originalPermissionChecker;

}