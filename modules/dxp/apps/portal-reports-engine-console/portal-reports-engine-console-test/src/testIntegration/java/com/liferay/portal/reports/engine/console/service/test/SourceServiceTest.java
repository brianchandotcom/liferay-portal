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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.reports.engine.console.model.Source;
import com.liferay.portal.reports.engine.console.service.SourceLocalServiceUtil;
import com.liferay.portal.reports.engine.console.service.SourceServiceUtil;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Level;

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
public class SourceServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		setUpPermissionChecker();
		setUpSource();
	}

	@After
	public void tearDown() throws PortalException {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testGetSourcesAsAdminUser() throws Exception {
		List<Source> sources = getSources(
			_adminPermissionChecker, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(sources.toString(), 10, sources.size());
	}

	@Test
	public void testGetSourcesAsGuestUser() throws Exception {
		List<Source> sources = getSources(
			_guestPermissionChecker, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(sources.toString(), 5, sources.size());
	}

	@Test
	public void testGetSourcesCountAsAdminUser() throws Exception {
		int sourcesCount = getSourcesCount(_adminPermissionChecker);

		Assert.assertEquals(10, sourcesCount);
	}

	@Test
	public void testGetSourcesCountAsGuestUser() throws Exception {
		int sourcesCount = getSourcesCount(_guestPermissionChecker);

		Assert.assertEquals(5, sourcesCount);
	}

	@Test
	public void testGetSourcesLimitedAsAdminUser() throws Exception {
		List<Source> sources = getSources(_adminPermissionChecker, 0, 5);

		Assert.assertEquals(sources.toString(), 5, sources.size());
	}

	@Test
	public void testGetSourcesLimitedAsGuestUser() throws Exception {
		List<Source> sources = getSources(_guestPermissionChecker, 0, 5);

		Assert.assertEquals(sources.toString(), 5, sources.size());
	}

	protected List<Source> getSources(
			PermissionChecker permissionChecker, int start, int end)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		return SourceServiceUtil.getSources(
			_group.getGroupId(), null, _URL, false, start, end, null);
	}

	protected int getSourcesCount(PermissionChecker permissionChecker)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		return SourceServiceUtil.getSourcesCount(
			_group.getGroupId(), null, _URL, false);
	}

	protected User getUser(String roleName) throws Exception {
		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), roleName);

		List<User> users = UserLocalServiceUtil.getRoleUsers(
			role.getRoleId(), 0, 1);

		return users.get(0);
	}

	protected void setUpPermissionChecker() throws Exception {
		User adminUser = getUser(RoleConstants.ADMINISTRATOR);

		_adminPermissionChecker = PermissionCheckerFactoryUtil.create(
			adminUser);

		User guestUser = getUser(RoleConstants.GUEST);

		_guestPermissionChecker = PermissionCheckerFactoryUtil.create(
			guestUser);
	}

	protected void setUpSource() throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.portal.spring.hibernate.DialectDetector",
					Level.OFF)) {

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext();

			ModelPermissions modelPermissions = ModelPermissionsFactory.create(
				_SOURCE_GROUP_PERMISSIONS, null);

			serviceContext.setModelPermissions(modelPermissions);

			for (int i = 0; i < 5; i++) {
				Map<Locale, String> nameMap = HashMapBuilder.put(
					LocaleUtil.US, RandomTestUtil.randomString()
				).build();

				SourceLocalServiceUtil.addSource(
					TestPropsValues.getUserId(), _group.getGroupId(), nameMap,
					_DRIVER_CLASSNAME, _URL, _USERNAME, _PASSWORD,
					serviceContext);
			}

			modelPermissions = ModelPermissionsFactory.create(
				_SOURCE_GROUP_PERMISSIONS, new String[] {"VIEW"});

			serviceContext.setModelPermissions(modelPermissions);

			for (int i = 0; i < 5; i++) {
				Map<Locale, String> nameMap = HashMapBuilder.put(
					LocaleUtil.US, RandomTestUtil.randomString()
				).build();

				SourceLocalServiceUtil.addSource(
					TestPropsValues.getUserId(), _group.getGroupId(), nameMap,
					_DRIVER_CLASSNAME, _URL, _USERNAME, _PASSWORD,
					serviceContext);
			}
		}
	}

	private static final String _DRIVER_CLASSNAME =
		"org.hsqldb.jdbc.JDBCDriver";

	private static final String _PASSWORD = StringPool.BLANK;

	private static final String[] _SOURCE_GROUP_PERMISSIONS = {
		"DELETE", "PERMISSIONS", "UPDATE", "VIEW"
	};

	private static final String _URL = "jdbc:hsqldb:mem:testDB;shutdown=true";

	private static final String _USERNAME = "sa";

	private PermissionChecker _adminPermissionChecker;

	@DeleteAfterTestRun
	private Group _group;

	private PermissionChecker _guestPermissionChecker;
	private PermissionChecker _originalPermissionChecker;

}