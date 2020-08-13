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
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RegionServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.users.admin.test.util.search.OrganizationBlueprint.OrganizationBlueprintBuilder;
import com.liferay.users.admin.test.util.search.UserBlueprintImpl.UserBlueprintBuilderImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public class UserSearchFixture {

	public UserSearchFixture() {
		this(
			UserLocalServiceUtil.getService(), new GroupSearchFixture(),
			new OrganizationSearchFixture(
				OrganizationLocalServiceUtil.getService()),
			new UserGroupSearchFixture(UserGroupLocalServiceUtil.getService()));
	}

	public UserSearchFixture(
		UserLocalService userLocalService,
		GroupSearchFixture groupSearchFixture,
		OrganizationSearchFixture organizationSearchFixture,
		UserGroupSearchFixture userGroupSearchFixture) {

		_userLocalService = userLocalService;
		_groupSearchFixture = groupSearchFixture;
		_organizationSearchFixture = organizationSearchFixture;
		_userGroupSearchFixture = userGroupSearchFixture;
	}

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

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public Group addGroup() throws Exception {
		return addGroup(new GroupBlueprint());
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public Group addGroup(GroupBlueprint groupBlueprint) throws Exception {
		return _groupSearchFixture.addGroup(groupBlueprint);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public Organization addOrganization() throws Exception {
		OrganizationBlueprintBuilder organizationBlueprintBuilder =
			OrganizationSearchFixture.getTestOrganizationBlueprintBuilder();

		return _organizationSearchFixture.addOrganization(
			organizationBlueprintBuilder.build());
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public Organization addOrganization(
			OrganizationBlueprint organizationBlueprint)
		throws Exception {

		return _organizationSearchFixture.addOrganization(
			organizationBlueprint);
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

	public User addUser(
		UserBlueprint.UserBlueprintBuilder userBlueprintBuilder) {

		User user = _addUser(userBlueprintBuilder);

		_users.add(user);

		return user;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public UserGroup addUserGroup() throws Exception {
		return _userGroupSearchFixture.addUserGroup(
			UserGroupSearchFixture.getTestUserGroupBlueprintBuilder());
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public User addUserWithAddress(
			String screenName, Group group, String... assetTagNames)
		throws Exception {

		User user = addUser(screenName, group, assetTagNames);

		addAddress(user);

		UserLocalServiceUtil.updateUser(user);

		return user;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public User addUserWithJobTitle(
			String screenName, Group group, String... assetTagNames)
		throws Exception {

		User user = addUser(screenName, group, assetTagNames);

		user.setJobTitle(RandomTestUtil.randomString());

		return UserLocalServiceUtil.updateUser(user);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public User addUserWithOrganization(
			String screenName, Group group, String... assetTagNames)
		throws Exception {

		Organization organization = addOrganization();
		User user = addUser(screenName, group, assetTagNames);

		UserLocalServiceUtil.addOrganizationUser(
			organization.getOrganizationId(), user.getUserId());

		return user;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
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

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public List<Group> getGroups() {
		return _groupSearchFixture.getGroups();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public List<Organization> getOrganizations() {
		return _organizationSearchFixture.getOrganizations();
	}

	public SearchContext getSearchContext(String keywords) throws Exception {
		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(TestPropsValues.getCompanyId());
		searchContext.setKeywords(keywords);
		searchContext.setUserId(TestPropsValues.getUserId());

		return searchContext;
	}

	public UserBlueprint.UserBlueprintBuilder getTestUserBlueprintBuilder() {
		UserBlueprint.UserBlueprintBuilder userBlueprintBuilder =
			new UserBlueprintBuilderImpl();

		long companyId = getTestCompanyId();
		long groupId = getTestGroupId();
		long userId = getTestUserId();

		ServiceContext serviceContext = new ServiceContext() {
			{
				setAddGroupPermissions(true);
				setAddGuestPermissions(true);
				setCompanyId(companyId);
				setScopeGroupId(groupId);
				setUserId(userId);
			}
		};

		return userBlueprintBuilder.autoPassword(
			true
		).birthdayMonth(
			Calendar.JANUARY
		).birthdayDay(
			1
		).birthdayYear(
			1970
		).companyId(
			companyId
		).emailAddress(
			RandomTestUtil.randomString() + RandomTestUtil.nextLong() +
				"@liferay.com"
		).userId(
			userId
		).screenName(
			RandomTestUtil.randomString(
				NumericStringRandomizerBumper.INSTANCE,
				UniqueStringRandomizerBumper.INSTANCE)
		).locale(
			LocaleUtil.getDefault()
		).firstName(
			RandomTestUtil.randomString()
		).lastName(
			RandomTestUtil.randomString()
		).serviceContext(
			serviceContext
		);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public List<UserGroup> getUserGroups() {
		return _userGroupSearchFixture.getUserGroups();
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

	protected static long getTestCompanyId() {
		try {
			return TestPropsValues.getCompanyId();
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	protected static long getTestGroupId() {
		try {
			return TestPropsValues.getGroupId();
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	protected static long getTestUserId() {
		try {
			return TestPropsValues.getUserId();
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private User _addUser(
			String screenName, String firstName, String lastName,
			long[] groupIds, Locale locale, ServiceContext serviceContext)
		throws Exception {

		return UserTestUtil.addUser(
			_companyId, TestPropsValues.getUserId(), screenName, locale,
			firstName, lastName, groupIds, serviceContext);
	}

	private User _addUser(
		UserBlueprint.UserBlueprintBuilder userBlueprintBuilder) {

		UserBlueprint userBlueprint = userBlueprintBuilder.build();

		try {
			return _userLocalService.addUserWithWorkflow(
				userBlueprint.getCreatorUserId(), userBlueprint.getCompanyId(),
				userBlueprint.isAutoPassword(), userBlueprint.getPassword1(),
				userBlueprint.getPassword2(), userBlueprint.isAutoScreenName(),
				userBlueprint.getScreenName(), userBlueprint.getEmailAddress(),
				userBlueprint.getLocale(), userBlueprint.getFirstName(),
				userBlueprint.getMiddleName(), userBlueprint.getLastName(),
				userBlueprint.getPrefixId(), userBlueprint.getSuffixId(),
				userBlueprint.isMale(), userBlueprint.getBirthdayMonth(),
				userBlueprint.getBirthdayDay(), userBlueprint.getBirthdayYear(),
				userBlueprint.getJobTitle(), userBlueprint.getGroupIds(),
				userBlueprint.getOrganizationIds(), userBlueprint.getRoleIds(),
				userBlueprint.getUserGroupIds(), userBlueprint.isSendMail(),
				userBlueprint.getServiceContext());
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private final List<Address> _addresses = new ArrayList<>();
	private final List<AssetTag> _assetTags = new ArrayList<>();
	private long _companyId;
	private final GroupSearchFixture _groupSearchFixture;
	private final OrganizationSearchFixture _organizationSearchFixture;
	private PermissionChecker _permissionChecker;
	private String _principal;
	private final UserGroupSearchFixture _userGroupSearchFixture;
	private final UserLocalService _userLocalService;
	private final List<User> _users = new ArrayList<>();

}