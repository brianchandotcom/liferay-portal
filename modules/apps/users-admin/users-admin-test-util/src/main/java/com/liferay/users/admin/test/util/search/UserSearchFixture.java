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

package com.liferay.users.admin.test.util.search;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;
import com.liferay.portal.kernel.service.RegionServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public class UserSearchFixture {

	public Address addAddress(User user) throws PortalException {
		List<ListType> listTypes = ListTypeServiceUtil.getListTypes(
			ListTypeConstants.CONTACT_ADDRESS);

		ListType listType = listTypes.get(0);

		long listTypeId = listType.getListTypeId();

		long contactId = user.getContactId();

		Contact contact = user.getContact();

		String modelClassName = contact.getModelClassName();

		Country country = CountryServiceUtil.getCountryByName("united-states");

		long countryId = country.getCountryId();

		List<Region> regions = RegionServiceUtil.getRegions(countryId);

		Region region = regions.get(0);

		Address address = AddressLocalServiceUtil.addAddress(
			user.getUserId(), modelClassName, contactId,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), region.getRegionId(), countryId,
			listTypeId, false, false, new ServiceContext());

		_addresses.add(address);

		return address;
	}

	public Group addGroup() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_groups.add(group);

		return group;
	}

	public Group addGroup(GroupBlueprint groupBlueprint) throws Exception {
		Group group = addGroup();

		Locale locale = groupBlueprint.getDefaultLocale();

		if (locale != null) {
			GroupTestUtil.updateDisplaySettings(
				group.getGroupId(), null, locale);
		}

		return group;
	}

	public Organization addOrganization() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		_organizations.add(organization);

		return organization;
	}

	public Organization addOrganization(
			OrganizationBlueprint organizationBlueprint)
		throws Exception {

		Organization organization = addOrganization();

		String[] assetTagNames = organizationBlueprint.getAssetTagNames();

		if (assetTagNames != null) {
			OrganizationTestUtil.updateAsset(organization, null, assetTagNames);
		}

		return organization;
	}

	public User addUser(String screenName, Group group, String... assetTagNames)
		throws Exception {

		return addUser(
			screenName, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), group,
			assetTagNames);
	}

	public User addUser(
			String screenName, String firstName, String lastName, Locale locale,
			Group group, String... assetTagNames)
		throws Exception {

		ServiceContext serviceContext = getServiceContext(group);

		serviceContext.setAssetTagNames(assetTagNames);

		User user = _addUser(
			screenName, firstName, lastName, new long[] {group.getGroupId()},
			locale, serviceContext);

		_users.add(user);

		List<AssetTag> assetTags = AssetTagLocalServiceUtil.getTags(
			user.getModelClassName(), user.getPrimaryKey());

		_assetTags.addAll(assetTags);

		return user;
	}

	public UserGroup addUserGroup() throws Exception {
		UserGroup userGroup = UserGroupTestUtil.addUserGroup();

		_userGroups.add(userGroup);

		return userGroup;
	}

	public User addUserWithAddress(
			String screenName, Group group, String... assetTagNames)
		throws Exception {

		User user = addUser(screenName, group, assetTagNames);

		addAddress(user);

		UserLocalServiceUtil.updateUser(user);

		return user;
	}

	public User addUserWithJobTitle(
			String screenName, Group group, String... assetTagNames)
		throws Exception {

		User user = addUser(screenName, group, assetTagNames);

		user.setJobTitle(RandomTestUtil.randomString());

		user = UserLocalServiceUtil.updateUser(user);

		return user;
	}

	public User addUserWithOrganization(
			String screenName, Group group, String... assetTagNames)
		throws Exception {

		Organization organization = addOrganization();
		User user = addUser(screenName, group, assetTagNames);

		UserLocalServiceUtil.addOrganizationUser(
			organization.getOrganizationId(), user.getUserId());

		return user;
	}

	public User addUserWithUserGroup(
			String screenName, Group group, String... assetTagNames)
		throws Exception {

		User user = addUser(screenName, group, assetTagNames);
		UserGroup userGroup = addUserGroup();

		UserGroupLocalServiceUtil.addUserUserGroup(user.getUserId(), userGroup);

		UserGroupLocalServiceUtil.addGroupUserGroup(
			group.getGroupId(), userGroup);

		return user;
	}

	public List<Address> getAddresses() {
		return _addresses;
	}

	public List<AssetTag> getAssetTags() {
		return _assetTags;
	}

	public List<Group> getGroups() {
		return _groups;
	}

	public List<Organization> getOrganizations() {
		return _organizations;
	}

	public SearchContext getSearchContext(String keywords) throws Exception {
		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(TestPropsValues.getCompanyId());
		searchContext.setKeywords(keywords);
		searchContext.setUserId(TestPropsValues.getUserId());

		return searchContext;
	}

	public List<UserGroup> getUserGroups() {
		return _userGroups;
	}

	public List<User> getUsers() {
		return _users;
	}

	public void setUp() throws Exception {
		_companyId = TestPropsValues.getCompanyId();

		_permissionChecker = PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			new DummyPermissionChecker() {

				@Override
				public long getCompanyId() {
					return _companyId;
				}

				@Override
				public boolean hasPermission(
					Group group, String name, long primKey, String actionId) {

					return true;
				}

				@Override
				public boolean hasPermission(
					Group group, String name, String primKey, String actionId) {

					return true;
				}

				@Override
				public boolean hasPermission(
					long groupId, String name, long primKey, String actionId) {

					return true;
				}

				@Override
				public boolean hasPermission(
					long groupId, String name, String primKey,
					String actionId) {

					return true;
				}

				@Override
				public boolean isCompanyAdmin(long companyId) {
					return true;
				}

			});

		_principal = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());
	}

	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_permissionChecker);

		PrincipalThreadLocal.setName(_principal);
	}

	public Map<String, String> toMap(List<Document> list) {
		Map<String, String> map = new HashMap<>();

		for (Document document : list) {
			String[] values = document.getValues(Field.ASSET_TAG_NAMES);

			Arrays.sort(values);

			map.put(document.get("screenName"), StringUtil.merge(values));
		}

		return map;
	}

	public Map<String, String> toMap(User user, String... tags) {
		return Collections.singletonMap(
			user.getScreenName(), toStringTags(tags));
	}

	public String toStringTags(String[] tags) {
		List<String> list = new ArrayList<>(tags.length);

		for (String tag : tags) {
			list.add(StringUtil.toLowerCase(tag));
		}

		Collections.sort(list);

		return StringUtil.merge(list);
	}

	protected static ServiceContext getServiceContext(Group group)
		throws Exception {

		return ServiceContextTestUtil.getServiceContext(
			group.getGroupId(), TestPropsValues.getUserId());
	}

	private User _addUser(
			String screenName, String firstName, String lastName,
			long[] groupIds, Locale locale, ServiceContext serviceContext)
		throws Exception {

		return UserTestUtil.addUser(
			_companyId, TestPropsValues.getUserId(), screenName, locale,
			firstName, lastName, groupIds, serviceContext);
	}

	private final List<Address> _addresses = new ArrayList<>();
	private final List<AssetTag> _assetTags = new ArrayList<>();
	private long _companyId;
	private final List<Group> _groups = new ArrayList<>();
	private final List<Organization> _organizations = new ArrayList<>();
	private PermissionChecker _permissionChecker;
	private String _principal;
	private final List<UserGroup> _userGroups = new ArrayList<>();
	private final List<User> _users = new ArrayList<>();

}