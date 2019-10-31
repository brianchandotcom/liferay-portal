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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Contact;
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.IndexedFieldsFixture;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

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
	public void testIndexedFields() throws Exception {
		User user = userSearchFixture.addUserWithJobTitle(
			RandomTestUtil.randomString(), group);

		String searchTerm = user.getFirstName();

		Document document = indexerFixture.searchOnlyOne(searchTerm);

		indexedFieldsFixture.postProcessDocument(document);

		FieldValuesAssert.assertFieldValues(
			_expectedValuesWithJobTitle(user), document, searchTerm);
	}

	@Test
	public void testIndexedFieldsWithAddress() throws Exception {
		User user = userSearchFixture.addUserWithAddress(
			RandomTestUtil.randomString(), group);

		String searchTerm = user.getFirstName();

		Document document = indexerFixture.searchOnlyOne(searchTerm);

		indexedFieldsFixture.postProcessDocument(document);

		FieldValuesAssert.assertFieldValues(
			_expectedValuesWithAddress(user), document, searchTerm);
	}

	@Test
	public void testIndexedFieldsWithOrganization() throws Exception {
		User user = userSearchFixture.addUserWithOrganization(
			RandomTestUtil.randomString(), group);

		String searchTerm = user.getFirstName();

		Document document = indexerFixture.searchOnlyOne(searchTerm);

		indexedFieldsFixture.postProcessDocument(document);

		FieldValuesAssert.assertFieldValues(
			_expectedValuesWithOrganization(user), document, searchTerm);
	}

	@Test
	public void testIndexedFieldsWithUserGroup() throws Exception {
		User user = userSearchFixture.addUserWithUserGroup(
			RandomTestUtil.randomString(), group);

		String searchTerm = user.getFirstName();

		Document document = indexerFixture.searchOnlyOne(searchTerm);

		indexedFieldsFixture.postProcessDocument(document);

		FieldValuesAssert.assertFieldValues(
			_expectedValuesWithUserGroup(user), document, searchTerm);
	}

	protected void setUpIndexedFieldsFixture() {
		indexedFieldsFixture = new IndexedFieldsFixture(
			resourcePermissionLocalService, searchEngineHelper);
	}

	protected void setUpIndexerFixture() {
		indexerFixture = new IndexerFixture<>(User.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_addresses = userSearchFixture.getAddresses();

		_groups = userSearchFixture.getGroups();

		_organizations = userSearchFixture.getOrganizations();

		_users = userSearchFixture.getUsers();

		_userGroups = userSearchFixture.getUserGroups();

		group = userSearchFixture.addGroup();
	}

	protected Group group;
	protected IndexedFieldsFixture indexedFieldsFixture;
	protected IndexerFixture<User> indexerFixture;

	@Inject
	protected OrganizationLocalService organizationLocalService;

	@Inject
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

	@Inject
	protected UserGroupLocalService userGroupLocalService;

	@Inject
	protected UserLocalService userLocalService;

	protected UserSearchFixture userSearchFixture;

	private Map<String, String> _expectedValues(User user) throws Exception {
		String groupId = String.valueOf(user.getGroupIds()[0]);

		Map<String, String> map = HashMapBuilder.put(
			Field.COMPANY_ID, String.valueOf(user.getCompanyId())
		).put(
			Field.ENTRY_CLASS_NAME, User.class.getName()
		).put(
			Field.ENTRY_CLASS_PK, String.valueOf(user.getUserId())
		).put(
			Field.GROUP_ID, groupId
		).put(
			Field.SCOPE_GROUP_ID, groupId
		).put(
			Field.STATUS, String.valueOf(user.getStatus())
		).put(
			Field.USER_ID, String.valueOf(user.getUserId())
		).put(
			Field.USER_NAME, StringUtil.toLowerCase(user.getFullName())
		).put(
			"defaultUser", String.valueOf(user.isDefaultUser())
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
		).build();

		long[] organizationIds = user.getOrganizationIds();

		map.put("organizationCount", String.valueOf(organizationIds.length));

		map.put("roleIds", _getValues(user.getRoleIds()));
		map.put("screenName", user.getScreenName());
		map.put(
			"screenName_sortable",
			StringUtil.toLowerCase(user.getScreenName()));

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

	private Map<String, String> _expectedValuesWithAddress(User user)
		throws Exception {

		Map<String, String> expected = _expectedValues(user);

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

		expected.put("city", _getValues(cities));
		expected.put("country", _getValues(countries));
		expected.put("region", _getValues(regions));
		expected.put("street", _getValues(streets));
		expected.put("zip", _getValues(zips));

		return expected;
	}

	private Map<String, String> _expectedValuesWithJobTitle(User user)
		throws Exception {

		Map<String, String> expected = _expectedValues(user);

		expected.put("jobTitle", user.getJobTitle());
		expected.put(
			"jobTitle_sortable", StringUtil.toLowerCase(user.getJobTitle()));

		return expected;
	}

	private Map<String, String> _expectedValuesWithOrganization(User user)
		throws Exception, PortalException {

		Map<String, String> expected = _expectedValues(user);

		expected.put("organizationIds", _getValues(user.getOrganizationIds()));

		return expected;
	}

	private Map<String, String> _expectedValuesWithUserGroup(User user)
		throws Exception {

		Map<String, String> expected = _expectedValues(user);

		expected.put("userGroupIds", _getValues(user.getUserGroupIds()));

		return expected;
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

	private String _getValues(List<String> stringValues) {
		String[] stringArray = ArrayUtil.toStringArray(stringValues);

		return _getValues(stringArray);
	}

	private String _getValues(long[] longValues) {
		String[] stringArray = ArrayUtil.toStringArray(longValues);

		return _getValues(stringArray);
	}

	private String _getValues(String[] stringArray) {
		String values = StringUtils.join(stringArray, ", ");

		if (stringArray.length > 1) {
			values = '[' + values + ']';
		}

		return values;
	}

	@DeleteAfterTestRun
	private List<Address> _addresses = new ArrayList<>();

	@DeleteAfterTestRun
	private final List<Contact> _contacts = new ArrayList<>();

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<Organization> _organizations;

	@DeleteAfterTestRun
	private List<UserGroup> _userGroups = new ArrayList<>();

	@DeleteAfterTestRun
	private List<User> _users;

}