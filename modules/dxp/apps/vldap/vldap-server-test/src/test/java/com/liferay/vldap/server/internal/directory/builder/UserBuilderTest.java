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

package com.liferay.vldap.server.internal.directory.builder;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.comparator.UserScreenNameComparator;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.vldap.server.internal.BaseVLDAPTestCase;
import com.liferay.vldap.server.internal.directory.FilterConstraint;
import com.liferay.vldap.server.internal.directory.ldap.Attribute;
import com.liferay.vldap.server.internal.directory.ldap.Directory;
import com.liferay.vldap.server.internal.util.PortletPropsValues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.time.FastDateFormat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author William Newbury
 */
public class UserBuilderTest extends BaseVLDAPTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpUsers();

		setUpExpando();
		setUpFastDateFormat();
		setUpGroups();
		_setUpImage();
		setUpOrganizations();
		setUpPasswordPolicy();
		setUpPortalUtil();
		setUpRoles();
		setUpUserGroups();
	}

	@Test
	public void testBuildDirectoriesCommunities() throws Exception {
		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("cn", StringPool.STAR);
		filterConstraint.addAttribute("gidNumber", StringPool.STAR);
		filterConstraint.addAttribute("givenName", "testFirstName");
		filterConstraint.addAttribute("mail", "test@email");
		filterConstraint.addAttribute(
			"member", "ou=testGroupName,ou=Communities,ou=liferay.com,cn=test");
		filterConstraint.addAttribute("sambaSID", null);
		filterConstraint.addAttribute("sn", "testLastName");
		filterConstraint.addAttribute("uidNumber", null);
		filterConstraint.addAttribute("uuid", null);

		filterConstraints.add(filterConstraint);

		doTestBuildDirectories(filterConstraints);
	}

	@Test
	public void testBuildDirectoriesGidNumber() throws Exception {
		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("gidNumber", "invalidGidNumber");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _userBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesInvalidFilter() throws Exception {
		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("test", "test");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _userBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesInvalidMember() throws Exception {
		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute(
			"member",
			"ou=testGroupName,ou=invalidGroup,ou=liferay.com,cn=test");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _userBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesInvalidSambaSID() throws Exception {
		Mockito.when(
			userLocalService.fetchUser(Mockito.anyLong())
		).thenReturn(
			_user
		);

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("cn", "testScreenName");
		filterConstraint.addAttribute("gidNumber", StringPool.STAR);
		filterConstraint.addAttribute("givenName", "testFirstName");
		filterConstraint.addAttribute("mail", "test@email");
		filterConstraint.addAttribute(
			"member", "ou=testGroupName,ou=Communities,ou=liferay.com,cn=test");
		filterConstraint.addAttribute("sambaSID", "42");
		filterConstraint.addAttribute("sn", "testLastName");
		filterConstraint.addAttribute("uidNumber", null);
		filterConstraint.addAttribute("uuid", null);

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _userBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());

		filterConstraint.addAttribute("sambaSID", "42-42");

		directories = _userBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());

		filterConstraint.addAttribute("sambaSID", "42-0-42");

		directories = _userBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesInvalidType() throws Exception {
		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("cn", StringPool.STAR);
		filterConstraint.addAttribute("gidNumber", StringPool.STAR);
		filterConstraint.addAttribute("givenName", "testFirstName");
		filterConstraint.addAttribute("mail", "test@email");
		filterConstraint.addAttribute(
			"member", "ou=testGroupName,ou=invalidType,ou=liferay.com,cn=test");
		filterConstraint.addAttribute("sambaSID", null);
		filterConstraint.addAttribute("sn", "testLastName");
		filterConstraint.addAttribute("uidNumber", null);
		filterConstraint.addAttribute("uuid", null);

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _userBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesNoCn() throws Exception {
		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("cn", null);
		filterConstraint.addAttribute("gidNumber", StringPool.STAR);
		filterConstraint.addAttribute("givenName", "testFirstName");
		filterConstraint.addAttribute("mail", "test@email");
		filterConstraint.addAttribute(
			"member", "ou=testGroupName,ou=Communities,ou=liferay.com,cn=test");
		filterConstraint.addAttribute("sambaSID", null);
		filterConstraint.addAttribute("sn", "testLastName");
		filterConstraint.addAttribute("uidNumber", null);
		filterConstraint.addAttribute("uid", "testScreenName");
		filterConstraint.addAttribute("uuid", null);

		filterConstraints.add(filterConstraint);

		doTestBuildDirectories(filterConstraints);
	}

	@Test
	public void testBuildDirectoriesNoFilter() throws Exception {
		List<Directory> directories = _userBuilder.buildDirectories(
			searchBase, new ArrayList<FilterConstraint>());

		Directory directory = directories.get(0);

		assertDirectory(directory);
	}

	@Test
	public void testBuildDirectoriesNonmatchingEmailAddress() throws Exception {
		Mockito.when(
			userLocalService.search(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyInt(), Mockito.any(LinkedHashMap.class),
				Mockito.anyBoolean(), Mockito.anyInt(), Mockito.anyInt(),
				Mockito.any(UserScreenNameComparator.class))
		).thenReturn(
			_users
		);

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("cn", "testScreenName");
		filterConstraint.addAttribute("gidNumber", StringPool.STAR);
		filterConstraint.addAttribute("givenName", "testFirstName");
		filterConstraint.addAttribute("mail", "invalid@email");
		filterConstraint.addAttribute(
			"member", "ou=testGroupName,ou=Communities,ou=liferay.com,cn=test");
		filterConstraint.addAttribute("sambaSID", null);
		filterConstraint.addAttribute("sn", "testLastName");
		filterConstraint.addAttribute("uidNumber", null);
		filterConstraint.addAttribute("uuid", null);

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _userBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesNonmatchingScreenName() throws Exception {
		Mockito.when(
			userLocalService.search(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyInt(), Mockito.any(LinkedHashMap.class),
				Mockito.anyBoolean(), Mockito.anyInt(), Mockito.anyInt(),
				Mockito.any(UserScreenNameComparator.class))
		).thenReturn(
			_users
		);

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("cn", "invalidScreenName");
		filterConstraint.addAttribute("gidNumber", StringPool.STAR);
		filterConstraint.addAttribute("givenName", "testFirstName");
		filterConstraint.addAttribute("mail", "test@email");
		filterConstraint.addAttribute(
			"member", "ou=testGroupName,ou=Communities,ou=liferay.com,cn=test");
		filterConstraint.addAttribute("sambaSID", null);
		filterConstraint.addAttribute("sn", "testLastName");
		filterConstraint.addAttribute("uidNumber", null);
		filterConstraint.addAttribute("uuid", null);

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _userBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesNoSambaSIDOrUidNumberOrUUID()
		throws Exception {

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("cn", "testScreenName");
		filterConstraint.addAttribute("gidNumber", StringPool.STAR);
		filterConstraint.addAttribute("givenName", "testFirstName");
		filterConstraint.addAttribute("mail", "test@email");
		filterConstraint.addAttribute(
			"member", "ou=testGroupName,ou=Communities,ou=liferay.com,cn=test");
		filterConstraint.addAttribute("sambaSID", null);
		filterConstraint.addAttribute("sn", "testLastName");
		filterConstraint.addAttribute("uidNumber", null);
		filterConstraint.addAttribute("uuid", null);

		filterConstraints.add(filterConstraint);

		doTestBuildDirectories(filterConstraints);
	}

	@Test
	public void testBuildDirectoriesNoScreenName() throws Exception {
		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("cn", null);
		filterConstraint.addAttribute("gidNumber", StringPool.STAR);
		filterConstraint.addAttribute("givenName", "testFirstName");
		filterConstraint.addAttribute("mail", "test@email");
		filterConstraint.addAttribute(
			"member", "ou=testGroupName,ou=Communities,ou=liferay.com,cn=test");
		filterConstraint.addAttribute("sambaSID", null);
		filterConstraint.addAttribute("sn", "testLastName");
		filterConstraint.addAttribute("uidNumber", null);
		filterConstraint.addAttribute("uuid", null);

		filterConstraints.add(filterConstraint);

		doTestBuildDirectories(filterConstraints);
	}

	@Test
	public void testBuildDirectoriesOrganizations() throws Exception {
		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("cn", StringPool.STAR);
		filterConstraint.addAttribute("gidNumber", StringPool.STAR);
		filterConstraint.addAttribute("givenName", "testFirstName");
		filterConstraint.addAttribute("mail", "test@email");
		filterConstraint.addAttribute(
			"member",
			"ou=testGroupName,ou=Organizations,ou=liferay.com,cn=test");
		filterConstraint.addAttribute("sambaSID", null);
		filterConstraint.addAttribute("sn", "testLastName");
		filterConstraint.addAttribute("uidNumber", null);
		filterConstraint.addAttribute("uuid", null);

		filterConstraints.add(filterConstraint);

		doTestBuildDirectories(filterConstraints);
	}

	@Test
	public void testBuildDirectoriesRoles() throws Exception {
		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("cn", StringPool.STAR);
		filterConstraint.addAttribute("gidNumber", StringPool.STAR);
		filterConstraint.addAttribute("givenName", "testFirstName");
		filterConstraint.addAttribute("mail", "test@email");
		filterConstraint.addAttribute(
			"member", "ou=testGroupName,ou=Roles,ou=liferay.com,cn=test");
		filterConstraint.addAttribute("sambaSID", null);
		filterConstraint.addAttribute("sn", "testLastName");
		filterConstraint.addAttribute("uidNumber", null);
		filterConstraint.addAttribute("uuid", null);

		filterConstraints.add(filterConstraint);

		doTestBuildDirectories(filterConstraints);
	}

	@Test
	public void testBuildDirectoriesSizeLimit() throws Exception {
		Mockito.when(
			userLocalService.search(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyInt(), Mockito.any(LinkedHashMap.class),
				Mockito.anyBoolean(), Mockito.anyInt(), Mockito.anyInt(),
				Mockito.any(UserScreenNameComparator.class))
		).thenReturn(
			_users
		);

		Mockito.when(
			searchBase.getSizeLimit()
		).thenReturn(
			0L
		);

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("cn", StringPool.STAR);
		filterConstraint.addAttribute("gidNumber", StringPool.STAR);
		filterConstraint.addAttribute("givenName", "testFirstName");
		filterConstraint.addAttribute("mail", "test@email");
		filterConstraint.addAttribute(
			"member", "ou=testGroupName,ou=Communities,ou=liferay.com,cn=test");
		filterConstraint.addAttribute("sambaSID", null);
		filterConstraint.addAttribute("sn", "testLastName");
		filterConstraint.addAttribute("uidNumber", null);
		filterConstraint.addAttribute("uuid", null);

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _userBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesUserGroups() throws Exception {
		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("cn", StringPool.STAR);
		filterConstraint.addAttribute("gidNumber", StringPool.STAR);
		filterConstraint.addAttribute("givenName", "testFirstName");
		filterConstraint.addAttribute("mail", "test@email");
		filterConstraint.addAttribute(
			"member", "ou=testGroupName,ou=User Groups,ou=liferay.com,cn=test");
		filterConstraint.addAttribute("sambaSID", null);
		filterConstraint.addAttribute("sn", "testLastName");
		filterConstraint.addAttribute("uidNumber", null);
		filterConstraint.addAttribute("uuid", null);

		filterConstraints.add(filterConstraint);

		doTestBuildDirectories(filterConstraints);
	}

	@Test
	public void testBuildDirectoriesValidSambaSID() throws Exception {
		Mockito.when(
			userLocalService.fetchUser(Mockito.anyLong())
		).thenReturn(
			_user
		);

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("cn", "testScreenName");
		filterConstraint.addAttribute("gidNumber", StringPool.STAR);
		filterConstraint.addAttribute("givenName", "testFirstName");
		filterConstraint.addAttribute("mail", "test@email");
		filterConstraint.addAttribute(
			"member", "ou=testGroupName,ou=Communities,ou=liferay.com,cn=test");
		filterConstraint.addAttribute("sambaSID", "42-42-42");
		filterConstraint.addAttribute("sn", "testLastName");
		filterConstraint.addAttribute("uidNumber", null);
		filterConstraint.addAttribute("uuid", null);

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _userBuilder.buildDirectories(
			searchBase, filterConstraints);

		Directory directory = directories.get(0);

		assertDirectory(directory);
	}

	@Test
	public void testBuildDirectoriesValidSambaSIDNullUser() throws Exception {
		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("cn", "testScreenName");
		filterConstraint.addAttribute("gidNumber", StringPool.STAR);
		filterConstraint.addAttribute("givenName", "testFirstName");
		filterConstraint.addAttribute("mail", "test@email");
		filterConstraint.addAttribute(
			"member", "ou=testGroupName,ou=Communities,ou=liferay.com,cn=test");
		filterConstraint.addAttribute("sambaSID", "42-42-42");
		filterConstraint.addAttribute("sn", "testLastName");
		filterConstraint.addAttribute("uidNumber", null);
		filterConstraint.addAttribute("uuid", null);

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _userBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesValidUidNumber() throws Exception {
		Mockito.when(
			userLocalService.fetchUser(Mockito.anyLong())
		).thenReturn(
			_user
		);

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("cn", "testScreenName");
		filterConstraint.addAttribute("gidNumber", StringPool.STAR);
		filterConstraint.addAttribute("givenName", "testFirstName");
		filterConstraint.addAttribute("mail", "test@email");
		filterConstraint.addAttribute(
			"member", "ou=testGroupName,ou=Communities,ou=liferay.com,cn=test");
		filterConstraint.addAttribute("sambaSID", null);
		filterConstraint.addAttribute("sn", "testLastName");
		filterConstraint.addAttribute("uidNumber", "42");
		filterConstraint.addAttribute("uuid", null);

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _userBuilder.buildDirectories(
			searchBase, filterConstraints);

		Directory directory = directories.get(0);

		List<Attribute> attributes = directory.getAttributes("jpegphoto");

		Attribute attribute = attributes.get(0);

		Assert.assertArrayEquals(_IMAGE_BYTES, attribute.getBytes());

		assertDirectory(directory);
	}

	@Test
	public void testBuildDirectoriesValidUidNumberNullUser() throws Exception {
		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("cn", "testScreenName");
		filterConstraint.addAttribute("gidNumber", StringPool.STAR);
		filterConstraint.addAttribute("givenName", "testFirstName");
		filterConstraint.addAttribute("mail", "test@email");
		filterConstraint.addAttribute(
			"member", "ou=testGroupName,ou=Communities,ou=liferay.com,cn=test");
		filterConstraint.addAttribute("sambaSID", null);
		filterConstraint.addAttribute("sn", "testLastName");
		filterConstraint.addAttribute("uidNumber", "42");
		filterConstraint.addAttribute("uuid", null);

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _userBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesValidUUID() throws Exception {
		Mockito.when(
			userLocalService.getUserByUuidAndCompanyId(
				Mockito.anyString(), Mockito.anyLong())
		).thenReturn(
			_user
		);

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("gidNumber", StringPool.STAR);
		filterConstraint.addAttribute(
			"member", "ou=testGroupName,ou=Communities,ou=liferay.com,cn=test");
		filterConstraint.addAttribute("uuid", "testUuid");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _userBuilder.buildDirectories(
			searchBase, filterConstraints);

		Directory directory = directories.get(0);

		assertDirectory(directory);
	}

	@Test
	public void testValidAttribute() {
		Assert.assertTrue(_userBuilder.isValidAttribute("cn", "test"));
		Assert.assertTrue(_userBuilder.isValidAttribute("gidNumber", "test"));
		Assert.assertTrue(_userBuilder.isValidAttribute("givenName", "test"));
		Assert.assertTrue(_userBuilder.isValidAttribute("mail", "test"));
		Assert.assertTrue(_userBuilder.isValidAttribute("member", "test"));
		Assert.assertTrue(_userBuilder.isValidAttribute("sambaSID", "test"));
		Assert.assertTrue(_userBuilder.isValidAttribute("sn", "test"));
		Assert.assertTrue(_userBuilder.isValidAttribute("status", "test"));
		Assert.assertTrue(_userBuilder.isValidAttribute("uid", "test"));
		Assert.assertTrue(_userBuilder.isValidAttribute("uidNumber", "test"));
		Assert.assertTrue(_userBuilder.isValidAttribute("uuid", "test"));
		Assert.assertTrue(
			_userBuilder.isValidAttribute("createTimestamp", "*"));
		Assert.assertTrue(_userBuilder.isValidAttribute("displayName", "*"));
		Assert.assertTrue(
			_userBuilder.isValidAttribute("modifyTimestamp", "*"));
		Assert.assertTrue(
			_userBuilder.isValidAttribute("objectclass", "groupOfNames"));
		Assert.assertTrue(
			_userBuilder.isValidAttribute("objectclass", "inetOrgPerson"));
		Assert.assertTrue(
			_userBuilder.isValidAttribute("objectclass", "liferayPerson"));
		Assert.assertTrue(
			_userBuilder.isValidAttribute("objectclass", "sambaSAMAccount"));
		Assert.assertTrue(_userBuilder.isValidAttribute("objectclass", "top"));
		Assert.assertTrue(_userBuilder.isValidAttribute("objectclass", "*"));
	}

	protected void assertDirectory(Directory directory) {
		Assert.assertTrue(directory.hasAttribute("cn", "testScreenName"));
		Assert.assertTrue(
			directory.hasAttribute(
				"member",
				"cn=testGroupName,ou=testGroupName," +
					"ou=Communities,ou=liferay.com,o=Liferay"));
		Assert.assertTrue(
			directory.hasAttribute(
				"member",
				"cn=testOrganizationName,ou=testOrganizationName," +
					"ou=Organizations,ou=liferay.com,o=Liferay"));
		Assert.assertTrue(
			directory.hasAttribute(
				"member",
				"cn=testRoleName,ou=testRoleName," +
					"ou=Roles,ou=liferay.com,o=Liferay"));
		Assert.assertTrue(
			directory.hasAttribute(
				"member",
				"cn=testUserGroupName,ou=testUserGroupName," +
					"ou=User Groups,ou=liferay.com,o=Liferay"));
		Assert.assertTrue(
			directory.hasAttribute("sambaLockoutDuration", "120"));
		Assert.assertTrue(directory.hasAttribute("sambaMaxPwdAge", "-1"));
		Assert.assertTrue(directory.hasAttribute("sn", "testLastName"));
	}

	protected void doTestBuildDirectories(
			List<FilterConstraint> filterConstraints)
		throws Exception {

		Mockito.when(
			userLocalService.search(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyInt(), Mockito.any(LinkedHashMap.class),
				Mockito.anyBoolean(), Mockito.anyInt(), Mockito.anyInt(),
				Mockito.any(UserScreenNameComparator.class))
		).thenReturn(
			_users
		);

		List<Directory> directories = _userBuilder.buildDirectories(
			searchBase, filterConstraints);

		Directory directory = directories.get(0);

		assertDirectory(directory);
	}

	protected void setUpExpando() {
		ExpandoBridge expandoBridge = Mockito.mock(ExpandoBridge.class);

		Mockito.when(
			expandoBridge.getAttribute(
				Mockito.eq("sambaLMPassword"), Mockito.eq(false))
		).thenReturn(
			"testLMPassword"
		);

		Mockito.when(
			expandoBridge.getAttribute(
				Mockito.eq("sambaNTPassword"), Mockito.eq(false))
		).thenReturn(
			"testNTPassword"
		);

		Mockito.when(
			_user.getExpandoBridge()
		).thenReturn(
			expandoBridge
		);
	}

	protected void setUpFastDateFormat() {
		FastDateFormat fastDateFormat = FastDateFormat.getInstance(
			"yyyyMMddHHmmss.SSSZ", null, LocaleUtil.getDefault());

		FastDateFormatFactory fastDateFormatFactory = Mockito.mock(
			FastDateFormatFactory.class);

		Mockito.when(
			fastDateFormatFactory.getSimpleDateFormat(Mockito.anyString())
		).thenReturn(
			fastDateFormat
		);

		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			fastDateFormatFactory);
	}

	protected void setUpGroups() throws Exception {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getGroupId()
		).thenReturn(
			PRIMARY_KEY
		);

		Mockito.when(
			group.getName()
		).thenReturn(
			"testGroupName"
		);

		Mockito.when(
			groupLocalService.getGroup(
				Mockito.eq(PRIMARY_KEY), Mockito.eq("testGroupName"))
		).thenReturn(
			group
		);

		Mockito.when(
			groupLocalService.search(
				Mockito.anyLong(), Mockito.any(long[].class),
				Mockito.anyString(), Mockito.anyString(),
				Mockito.any(LinkedHashMap.class), Mockito.anyBoolean(),
				Mockito.anyInt(), Mockito.anyInt())
		).thenReturn(
			Arrays.asList(group)
		);

		Mockito.when(
			searchBase.getCommunity()
		).thenReturn(
			group
		);
	}

	protected void setUpOrganizations() throws Exception {
		Organization organization = Mockito.mock(Organization.class);

		Mockito.when(
			organizationLocalService.getOrganization(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			organization
		);

		Mockito.when(
			organization.getName()
		).thenReturn(
			"testOrganizationName"
		);

		Mockito.when(
			organization.getOrganizationId()
		).thenReturn(
			PRIMARY_KEY
		);

		Mockito.when(
			_user.getOrganizations()
		).thenReturn(
			Arrays.asList(organization)
		);

		Mockito.when(
			searchBase.getOrganization()
		).thenReturn(
			organization
		);
	}

	protected void setUpPasswordPolicy() throws Exception {
		PasswordPolicy passwordPolicy = Mockito.mock(PasswordPolicy.class);

		setUpPasswordPolicy(passwordPolicy);

		Mockito.when(
			_user.getPasswordPolicy()
		).thenReturn(
			passwordPolicy
		);
	}

	@Override
	protected void setUpPropsUtil() {
		super.setUpPropsUtil();

		Mockito.when(
			props.get(PortletPropsValues.POSIX_GROUP_ID)
		).thenReturn(
			"testGroupId"
		);
	}

	protected void setUpRoles() throws Exception {
		Role role = Mockito.mock(Role.class);

		Mockito.when(
			roleLocalService.getRole(Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			role
		);

		Mockito.when(
			role.getName()
		).thenReturn(
			"testRoleName"
		);

		Mockito.when(
			role.getRoleId()
		).thenReturn(
			PRIMARY_KEY
		);

		Mockito.when(
			_user.getRoles()
		).thenReturn(
			Arrays.asList(role)
		);

		Mockito.when(
			searchBase.getRole()
		).thenReturn(
			role
		);
	}

	protected void setUpUserGroups() throws Exception {
		UserGroup userGroup = Mockito.mock(UserGroup.class);

		Mockito.when(
			userGroupLocalService.getUserGroup(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			userGroup
		);

		Mockito.when(
			userGroup.getName()
		).thenReturn(
			"testUserGroupName"
		);

		Mockito.when(
			userGroup.getUserGroupId()
		).thenReturn(
			PRIMARY_KEY
		);

		Mockito.when(
			_user.getUserGroups()
		).thenReturn(
			Arrays.asList(userGroup)
		);

		Mockito.when(
			searchBase.getUserGroup()
		).thenReturn(
			userGroup
		);
	}

	protected void setUpUsers() {
		_user = Mockito.mock(User.class);

		Mockito.when(
			_user.getCompanyId()
		).thenReturn(
			PRIMARY_KEY
		);

		Mockito.when(
			_user.getCreateDate()
		).thenReturn(
			null
		);

		Mockito.when(
			_user.getEmailAddress()
		).thenReturn(
			"test@email"
		);

		Mockito.when(
			_user.getFirstName()
		).thenReturn(
			"testFirstName"
		);

		Mockito.when(
			_user.getFullName()
		).thenReturn(
			"testFullName"
		);

		Mockito.when(
			_user.getLastName()
		).thenReturn(
			"testLastName"
		);

		Mockito.when(
			_user.getModifiedDate()
		).thenReturn(
			null
		);

		Mockito.when(
			_user.getPortraitId()
		).thenReturn(
			PRIMARY_KEY
		);

		Mockito.when(
			_user.getScreenName()
		).thenReturn(
			"testScreenName"
		);

		Mockito.when(
			_user.getUserId()
		).thenReturn(
			PRIMARY_KEY
		);

		Mockito.when(
			_user.getUuid()
		).thenReturn(
			"testUuid"
		);

		_users.add(_user);

		Mockito.when(
			userLocalService.getCompanyUsers(
				Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt())
		).thenReturn(
			_users
		);
	}

	private void _setUpImage() throws Exception {
		Image image = Mockito.mock(Image.class);

		Mockito.when(
			image.getTextObj()
		).thenReturn(
			_IMAGE_BYTES
		);

		Mockito.when(
			imageService.getImage(Mockito.anyLong())
		).thenReturn(
			image
		);
	}

	private static final byte[] _IMAGE_BYTES =
		"Enterprise. Open Source. For Life".getBytes();

	private User _user;
	private final UserBuilder _userBuilder = new UserBuilder();
	private final List<User> _users = new ArrayList<>();

}