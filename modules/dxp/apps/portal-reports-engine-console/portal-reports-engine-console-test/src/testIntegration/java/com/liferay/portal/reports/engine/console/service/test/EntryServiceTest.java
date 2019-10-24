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
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
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
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.reports.engine.console.model.Definition;
import com.liferay.portal.reports.engine.console.model.Entry;
import com.liferay.portal.reports.engine.console.service.DefinitionLocalServiceUtil;
import com.liferay.portal.reports.engine.console.service.EntryLocalServiceUtil;
import com.liferay.portal.reports.engine.console.service.EntryServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Inácio Nery
 */
@RunWith(Arquillian.class)
@Sync
public class EntryServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws IOException {
		try (InputStream inputStream =
				EntryServiceTest.class.getResourceAsStream(
					"dependencies/company_logo.jpg")) {

			_file = FileUtil.createTempFile(inputStream);
		}
	}

	@AfterClass
	public static void tearDownClass() {
		_file.delete();
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		setUpDefinition();
		setUpEntries();
		setUpPermissionChecker();
	}

	@After
	public void tearDown() throws PortalException {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testGetEntriesAsAdminUser() throws Exception {
		List<Entry> entries = getEntries(
			_adminPermissionChecker, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(entries.toString(), 10, entries.size());
	}

	@Test
	public void testGetEntriesAsGuestUser() throws Exception {
		List<Entry> entries = getEntries(
			_guestPermissionChecker, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(entries.toString(), 5, entries.size());
	}

	@Test
	public void testGetEntriesCountAsAdminUser() throws Exception {
		int entriesCount = getEntriesCount(_adminPermissionChecker);

		Assert.assertEquals(10, entriesCount);
	}

	@Test
	public void testGetEntriesCountAsGuestUser() throws Exception {
		int entriesCount = getEntriesCount(_guestPermissionChecker);

		Assert.assertEquals(5, entriesCount);
	}

	@Test
	public void testGetEntriesLimitedAsAdminUser() throws Exception {
		List<Entry> entries = getEntries(_adminPermissionChecker, 0, 5);

		Assert.assertEquals(entries.toString(), 5, entries.size());
	}

	@Test
	public void testGetEntriesLimitedAsGuestUser() throws Exception {
		List<Entry> entries = getEntries(_guestPermissionChecker, 0, 5);

		Assert.assertEquals(entries.toString(), 5, entries.size());
	}

	protected List<Entry> getEntries(
			PermissionChecker permissionChecker, int start, int end)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		return EntryServiceUtil.getEntries(
			_group.getGroupId(), _definition.getName(), null, null, null, false,
			start, end, null);
	}

	protected int getEntriesCount(PermissionChecker permissionChecker)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		return EntryServiceUtil.getEntriesCount(
			_group.getGroupId(), _definition.getName(), null, null, null,
			false);
	}

	protected User getUser(String roleName) throws Exception {
		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), roleName);

		List<User> users = UserLocalServiceUtil.getRoleUsers(
			role.getRoleId(), 0, 1);

		return users.get(0);
	}

	protected void setUpDefinition() throws Exception {
		try (InputStream inputStream =
				EntryServiceTest.class.getResourceAsStream(
					"dependencies/" + _TEMPLATE_NAME + ".jrxml")) {

			Map<Locale, String> nameMap = HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build();

			String content = StringUtil.replace(
				StringUtil.read(inputStream),
				"http://www.liferay.com/image/company_logo",
				_file.getAbsolutePath());

			_definition = DefinitionLocalServiceUtil.addDefinition(
				TestPropsValues.getUserId(), _group.getGroupId(), nameMap, null,
				0, null, _TEMPLATE_NAME,
				new UnsyncByteArrayInputStream(
					content.getBytes(StringPool.DEFAULT_CHARSET_NAME)),
				ServiceContextTestUtil.getServiceContext());
		}
	}

	protected void setUpEntries() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			_ENTRY_GROUP_PERMISSIONS, null);

		serviceContext.setModelPermissions(modelPermissions);

		for (int i = 0; i < 5; i++) {
			EntryLocalServiceUtil.addEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_definition.getDefinitionId(), "txt", false, null, null, false,
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK,
				RandomTestUtil.randomString(), StringPool.BLANK,
				serviceContext);
		}

		modelPermissions = ModelPermissionsFactory.create(
			_ENTRY_GROUP_PERMISSIONS, new String[] {"VIEW"});

		serviceContext.setModelPermissions(modelPermissions);

		for (int i = 0; i < 5; i++) {
			EntryLocalServiceUtil.addEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_definition.getDefinitionId(), "txt", false, null, null, false,
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK,
				RandomTestUtil.randomString(), StringPool.BLANK,
				serviceContext);
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

	private static final String[] _ENTRY_GROUP_PERMISSIONS = {
		"DELETE", "PERMISSIONS", "VIEW"
	};

	private static final String _TEMPLATE_NAME =
		"reports_admin_template_source_sample_list_type";

	private static File _file;

	private PermissionChecker _adminPermissionChecker;
	private Definition _definition;

	@DeleteAfterTestRun
	private Group _group;

	private PermissionChecker _guestPermissionChecker;
	private PermissionChecker _originalPermissionChecker;

}