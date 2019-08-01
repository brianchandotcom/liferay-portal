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
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author William Newbury
 */
@RunWith(PowerMockRunner.class)
public class UserBuilderTest extends BaseVLDAPTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpUsers();

		setUpExpando();
		setUpFastDateFormat();
		setUpGroups();
		setUpImage();
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
		when(
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
		when(
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
		when(
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
		when(
			userLocalService.search(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyInt(), Mockito.any(LinkedHashMap.class),
				Mockito.anyBoolean(), Mockito.anyInt(), Mockito.anyInt(),
				Mockito.any(UserScreenNameComparator.class))
		).thenReturn(
			_users
		);

		when(
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
		when(
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
		when(
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
		when(
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

		when(
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
		ExpandoBridge expandoBridge = mock(ExpandoBridge.class);

		when(
			expandoBridge.getAttribute(
				Mockito.eq("sambaLMPassword"), Mockito.eq(false))
		).thenReturn(
			"testLMPassword"
		);

		when(
			expandoBridge.getAttribute(
				Mockito.eq("sambaNTPassword"), Mockito.eq(false))
		).thenReturn(
			"testNTPassword"
		);

		when(
			_user.getExpandoBridge()
		).thenReturn(
			expandoBridge
		);
	}

	protected void setUpFastDateFormat() {
		FastDateFormat fastDateFormat = FastDateFormat.getInstance(
			"yyyyMMddHHmmss.SSSZ", null, LocaleUtil.getDefault());

		FastDateFormatFactory fastDateFormatFactory = mock(
			FastDateFormatFactory.class);

		when(
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
		Group group = mock(Group.class);

		when(
			group.getGroupId()
		).thenReturn(
			PRIMARY_KEY
		);

		when(
			group.getName()
		).thenReturn(
			"testGroupName"
		);

		when(
			groupLocalService.getGroup(
				Mockito.eq(PRIMARY_KEY), Mockito.eq("testGroupName"))
		).thenReturn(
			group
		);

		when(
			groupLocalService.search(
				Mockito.anyLong(), Mockito.any(long[].class),
				Mockito.anyString(), Mockito.anyString(),
				Mockito.any(LinkedHashMap.class), Mockito.anyBoolean(),
				Mockito.anyInt(), Mockito.anyInt())
		).thenReturn(
			Arrays.asList(group)
		);

		when(
			searchBase.getCommunity()
		).thenReturn(
			group
		);
	}

	protected void setUpImage() throws Exception {
		Image image = mock(Image.class);

		when(
			image.getTextObj()
		).thenReturn(
			_IMAGE_BYTES
		);

		when(
			imageService.getImage(Mockito.anyLong())
		).thenReturn(
			image
		);
	}

	protected void setUpOrganizations() throws Exception {
		Organization organization = mock(Organization.class);

		when(
			organizationLocalService.getOrganization(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			organization
		);

		when(
			organization.getName()
		).thenReturn(
			"testOrganizationName"
		);

		when(
			organization.getOrganizationId()
		).thenReturn(
			PRIMARY_KEY
		);

		when(
			_user.getOrganizations()
		).thenReturn(
			Arrays.asList(organization)
		);

		when(
			searchBase.getOrganization()
		).thenReturn(
			organization
		);
	}

	protected void setUpPasswordPolicy() throws Exception {
		PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);

		setUpPasswordPolicy(passwordPolicy);

		when(
			_user.getPasswordPolicy()
		).thenReturn(
			passwordPolicy
		);
	}

	@Override
	protected void setUpProps() {
		super.setUpProps();

		when(
			props.get(PortletPropsValues.POSIX_GROUP_ID)
		).thenReturn(
			"testGroupId"
		);
	}

	protected void setUpRoles() throws Exception {
		Role role = mock(Role.class);

		when(
			roleLocalService.getRole(Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			role
		);

		when(
			role.getName()
		).thenReturn(
			"testRoleName"
		);

		when(
			role.getRoleId()
		).thenReturn(
			PRIMARY_KEY
		);

		when(
			_user.getRoles()
		).thenReturn(
			Arrays.asList(role)
		);

		when(
			searchBase.getRole()
		).thenReturn(
			role
		);
	}

	protected void setUpUserGroups() throws Exception {
		UserGroup userGroup = mock(UserGroup.class);

		when(
			userGroupLocalService.getUserGroup(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			userGroup
		);

		when(
			userGroup.getName()
		).thenReturn(
			"testUserGroupName"
		);

		when(
			userGroup.getUserGroupId()
		).thenReturn(
			PRIMARY_KEY
		);

		when(
			_user.getUserGroups()
		).thenReturn(
			Arrays.asList(userGroup)
		);

		when(
			searchBase.getUserGroup()
		).thenReturn(
			userGroup
		);
	}

	protected void setUpUsers() {
		_user = mock(User.class);

		when(
			_user.getCompanyId()
		).thenReturn(
			PRIMARY_KEY
		);

		when(
			_user.getCreateDate()
		).thenReturn(
			null
		);

		when(
			_user.getEmailAddress()
		).thenReturn(
			"test@email"
		);

		when(
			_user.getFirstName()
		).thenReturn(
			"testFirstName"
		);

		when(
			_user.getFullName()
		).thenReturn(
			"testFullName"
		);

		when(
			_user.getLastName()
		).thenReturn(
			"testLastName"
		);

		when(
			_user.getModifiedDate()
		).thenReturn(
			null
		);

		when(
			_user.getPortraitId()
		).thenReturn(
			PRIMARY_KEY
		);

		when(
			_user.getScreenName()
		).thenReturn(
			"testScreenName"
		);

		when(
			_user.getUserId()
		).thenReturn(
			PRIMARY_KEY
		);

		when(
			_user.getUuid()
		).thenReturn(
			"testUuid"
		);

		_users.add(_user);

		when(
			userLocalService.getCompanyUsers(
				Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt())
		).thenReturn(
			_users
		);
	}

	private static final byte[] _IMAGE_BYTES =
		"Enterprise. Open Source. For Life".getBytes();

	private User _user;
	private final UserBuilder _userBuilder = new UserBuilder();
	private final List<User> _users = new ArrayList<>();

}