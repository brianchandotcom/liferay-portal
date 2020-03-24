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

package com.liferay.users.admin.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.IndexedFieldsFixture;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.GroupBlueprint;
import com.liferay.users.admin.test.util.search.GroupSearchFixture;
import com.liferay.users.admin.test.util.search.OrganizationBlueprint.OrganizationBlueprintBuilder;
import com.liferay.users.admin.test.util.search.OrganizationSearchFixture;
import com.liferay.users.admin.test.util.search.UserGroupSearchFixture;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Fabiano Nazar
 * @author Luan Maoski
 */
@RunWith(Arquillian.class)
public class UserIndexerIndexedFieldsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		setUpIndexedFieldsFixture();

		setUpIndexerFixture();

		setUpUserSearchFixture();
	}

	@Test
	public void testAddress() throws Exception {
		User user1 = addUser();

		userSearchFixture.addAddress(user1);

		User user2 = userLocalService.updateUser(user1);

		String searchTerm = user2.getFirstName();

		Document document = indexerFixture.searchOnlyOne(searchTerm);

		indexedFieldsFixture.postProcessDocument(document);

		Map<String, Object> map = _getExpectedFieldValues(user2);

		_populateAddressFieldValues(user2, map);

		FieldValuesAssert.assertFieldValues(map, document, searchTerm);
	}

	@Test
	public void testJobTitle() throws Exception {
		User user1 = addUser();

		user1.setJobTitle(RandomTestUtil.randomString());

		User user2 = userLocalService.updateUser(user1);

		String searchTerm = user2.getFirstName();

		Document document = indexerFixture.searchOnlyOne(searchTerm);

		indexedFieldsFixture.postProcessDocument(document);

		Map<String, Object> map = _getExpectedFieldValues(user2);

		map.put("jobTitle", user2.getJobTitle());
		map.put(
			"jobTitle_sortable", StringUtil.toLowerCase(user2.getJobTitle()));

		FieldValuesAssert.assertFieldValues(map, document, searchTerm);
	}

	@Test
	public void testOrganizationIds() throws Exception {
		Organization organization = addOrganization();

		User user = addUser();

		userLocalService.addOrganizationUser(
			organization.getOrganizationId(), user.getUserId());

		String searchTerm = user.getFirstName();

		Document document = indexerFixture.searchOnlyOne(searchTerm);

		indexedFieldsFixture.postProcessDocument(document);

		Map<String, Object> map = _getExpectedFieldValues(user);

		map.put("organizationIds", _getValues(user.getOrganizationIds()));

		FieldValuesAssert.assertFieldValues(map, document, searchTerm);
	}

	@Test
	public void testUserGroupIds() throws Exception {
		User user = addUser();

		UserGroup userGroup = userGroupSearchFixture.addUserGroup(
			UserGroupSearchFixture.getTestUserGroupBlueprintBuilder());

		userGroupLocalService.addUserUserGroup(user.getUserId(), userGroup);

		userGroupLocalService.addGroupUserGroup(group.getGroupId(), userGroup);

		String searchTerm = user.getFirstName();

		Document document = indexerFixture.searchOnlyOne(searchTerm);

		indexedFieldsFixture.postProcessDocument(document);

		Map<String, Object> map = _getExpectedFieldValues(user);

		map.put("userGroupIds", _getValues(user.getUserGroupIds()));

		FieldValuesAssert.assertFieldValues(map, document, searchTerm);
	}

	protected Organization addOrganization() {
		OrganizationBlueprintBuilder organizationBlueprintBuilder =
			OrganizationSearchFixture.getTestOrganizationBlueprintBuilder();

		return organizationSearchFixture.addOrganization(
			organizationBlueprintBuilder.build());
	}

	protected User addUser() throws Exception {
		String[] assetTagNames = {};

		return userSearchFixture.addUser(
			RandomTestUtil.randomString(), group, assetTagNames);
	}

	protected void setUpIndexedFieldsFixture() {
		indexedFieldsFixture = new IndexedFieldsFixture(
			resourcePermissionLocalService, searchEngineHelper);
	}

	protected void setUpIndexerFixture() {
		indexerFixture = new IndexerFixture<>(User.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		GroupSearchFixture groupSearchFixture = new GroupSearchFixture();

		organizationSearchFixture = new OrganizationSearchFixture(
			organizationLocalService);

		userGroupSearchFixture = new UserGroupSearchFixture(
			userGroupLocalService);

		userSearchFixture = new UserSearchFixture(
			userLocalService, groupSearchFixture, organizationSearchFixture,
			userGroupSearchFixture);

		userSearchFixture.setUp();

		_addresses = userSearchFixture.getAddresses();

		_groups = groupSearchFixture.getGroups();

		_organizations = organizationSearchFixture.getOrganizations();

		_users = userSearchFixture.getUsers();

		_userGroups = userGroupSearchFixture.getUserGroups();

		group = groupSearchFixture.addGroup(new GroupBlueprint());
	}

	protected Group group;
	protected IndexedFieldsFixture indexedFieldsFixture;
	protected IndexerFixture<User> indexerFixture;

	@Inject
	protected OrganizationLocalService organizationLocalService;

	protected OrganizationSearchFixture organizationSearchFixture;

	@Inject
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

	@Inject
	protected UserGroupLocalService userGroupLocalService;

	protected UserGroupSearchFixture userGroupSearchFixture;

	@Inject
	protected UserLocalService userLocalService;

	protected UserSearchFixture userSearchFixture;

	private Map<String, Object> _getExpectedFieldValues(User user)
		throws Exception {

		Long groupId = user.getGroupIds()[0];

		Map<String, Object> map = HashMapBuilder.put(
			Field.COMPANY_ID, (Object)user.getCompanyId()
		).put(
			Field.ENTRY_CLASS_NAME, User.class.getName()
		).put(
			Field.ENTRY_CLASS_PK, user.getUserId()
		).put(
			Field.GROUP_ID, groupId
		).put(
			Field.SCOPE_GROUP_ID, groupId
		).put(
			Field.STATUS, user.getStatus()
		).put(
			Field.USER_ID, user.getUserId()
		).put(
			Field.USER_NAME, StringUtil.toLowerCase(user.getFullName())
		).put(
			"defaultUser", user.isDefaultUser()
		).put(
			"emailAddress", user.getEmailAddress()
		).put(
			"firstName", user.getFirstName()
		).put(
			"firstName_sortable", StringUtil.toLowerCase(user.getFirstName())
		).put(
			"fullName", user.getFullName()
		).put(
			"groupIds", groupId
		).put(
			"lastName", user.getLastName()
		).put(
			"lastName_sortable", StringUtil.toLowerCase(user.getLastName())
		).put(
			"organizationCount",
			() -> {
				long[] organizationIds = user.getOrganizationIds();

				return organizationIds.length;
			}
		).put(
			"roleIds", _getValues(user.getRoleIds())
		).put(
			"screenName", user.getScreenName()
		).put(
			"screenName_sortable", StringUtil.toLowerCase(user.getScreenName())
		).build();

		indexedFieldsFixture.populateUID(
			User.class.getName(), user.getUserId(), map);

		indexedFieldsFixture.populateDate(
			Field.MODIFIED_DATE, user.getModifiedDate(), map);

		indexedFieldsFixture.populateDate("birthDate", user.getBirthday(), map);

		indexedFieldsFixture.populateRoleIdFields(
			user.getCompanyId(), User.class.getName(), user.getUserId(),
			user.getGroupId(), null, map);

		return map;
	}

	private Set<String> _getLocalizedCountryNames(Country country) {
		Set<String> countryNames = new HashSet<>();

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			String countryName = country.getName(locale);

			countryName = StringUtil.toLowerCase(countryName);

			countryNames.add(countryName);
		}

		return countryNames;
	}

	private List<Long> _getValues(long[] longValues) {
		return ListUtil.fromArray(longValues);
	}

	private void _populateAddressFieldValues(
		User user, Map<String, Object> map) {

		List<String> cities = new ArrayList<>();
		List<String> countries = new ArrayList<>();
		List<String> regions = new ArrayList<>();
		List<String> streets = new ArrayList<>();
		List<String> zips = new ArrayList<>();

		for (Address address : user.getAddresses()) {
			cities.add(StringUtil.toLowerCase(address.getCity()));

			countries.addAll(_getLocalizedCountryNames(address.getCountry()));

			Region region = address.getRegion();

			regions.add(StringUtil.toLowerCase(region.getName()));

			streets.add(StringUtil.toLowerCase(address.getStreet1()));
			streets.add(StringUtil.toLowerCase(address.getStreet2()));
			streets.add(StringUtil.toLowerCase(address.getStreet3()));

			zips.add(StringUtil.toLowerCase(address.getZip()));
		}

		map.put("city", cities);
		map.put("country", countries);
		map.put("region", regions);
		map.put("street", streets);
		map.put("zip", zips);
	}

	@DeleteAfterTestRun
	private List<Address> _addresses = new ArrayList<>();

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<Organization> _organizations;

	@DeleteAfterTestRun
	private List<UserGroup> _userGroups = new ArrayList<>();

	@DeleteAfterTestRun
	private List<User> _users;

}