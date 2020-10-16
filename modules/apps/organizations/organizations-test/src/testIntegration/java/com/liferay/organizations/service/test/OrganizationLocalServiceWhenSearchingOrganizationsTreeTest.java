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

package com.liferay.organizations.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.comparator.OrganizationNameComparator;
import com.liferay.portal.search.test.util.AssertUtils;
import com.liferay.portal.search.test.util.SearchStreamUtil;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.users.admin.kernel.util.UsersAdmin;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class OrganizationLocalServiceWhenSearchingOrganizationsTreeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		Organization rootOrganization = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			StringUtil.toLowerCase(RandomTestUtil.randomString()), false);

		_organization = OrganizationTestUtil.addOrganization(
			rootOrganization.getOrganizationId(),
			StringUtil.toLowerCase(RandomTestUtil.randomString()), false);

		Organization suborganization = OrganizationTestUtil.addOrganization(
			_organization.getOrganizationId(),
			StringUtil.toLowerCase(RandomTestUtil.randomString()), false);

		_organizations.add(suborganization);

		_organizations.add(_organization);

		_organizations.add(rootOrganization);

		_user = UserTestUtil.addUser();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		userLocalService.addOrganizationUsers(
			_organization.getOrganizationId(), new long[] {_user.getUserId()});
	}

	@After
	public void tearDown() throws Exception {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testShouldIncludeSuborganizationsAsAdminUser()
		throws Exception {

		UserTestUtil.addUserGroupRole(
			_user.getUserId(), _organization.getGroupId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		_assertSearch(true);
	}

	@Test
	public void testShouldIncludeSuborganizationsWitManageSuborganizationPermission()
		throws Exception {

		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		RoleTestUtil.addResourcePermission(
			_role, Organization.class.getName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(_user.getCompanyId()),
			ActionKeys.MANAGE_SUBORGANIZATIONS);

		userLocalService.addRoleUser(_role.getRoleId(), _user);

		_assertSearch(true);
	}

	@Test
	public void testShouldNotIncludeSuborganizationsWithoutManageSuborganizationPermission()
		throws Exception {

		_assertSearch(false);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected BaseModelSearchResult<Organization> searchOrganizations(
			LinkedHashMap<String, Object> organizationParams)
		throws Exception {

		Assert.assertNotNull(
			"Indexer<Organization> must be resolved for " +
				"OrganizationLocalServiceImpl.searchOrganizations",
			indexer);
		Assert.assertNotNull(
			"IndexerRegistryUtil must be resolved for " +
				"OrganizationLocalServiceImpl.searchOrganizations",
			indexerRegistry);
		Assert.assertNotNull(
			"UsersAdminUtil must be resolved for " +
				"OrganizationLocalServiceImpl.searchOrganizations",
			usersAdmin);

		return organizationLocalService.searchOrganizations(
			_user.getCompanyId(),
			OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null,
			organizationParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new Sort("name", false));
	}

	protected String toString(Organization organization) {
		Map<String, Function<Organization, Object>> map = new LinkedHashMap<>(
			organization.getAttributeGetterFunctions());

		map.remove("createDate");
		map.remove("modifiedDate");

		Stream<Map.Entry<String, Function<Organization, Object>>> stream =
			SearchStreamUtil.stream(map.entrySet());

		return String.valueOf(
			stream.collect(
				Collectors.toMap(
					Map.Entry::getKey,
					entry -> {
						Function<Organization, Object> function =
							entry.getValue();

						return String.valueOf(function.apply(organization));
					})));
	}

	protected List<String> toStringList(List<Organization> organizations) {
		Stream<Organization> stream = organizations.stream();

		return stream.map(
			this::toString
		).collect(
			Collectors.toList()
		);
	}

	@Inject(
		filter = "indexer.class.name=com.liferay.portal.kernel.model.Organization"
	)
	protected Indexer<Organization> indexer;

	@Inject
	protected IndexerRegistry indexerRegistry;

	@Inject
	protected OrganizationLocalService organizationLocalService;

	@Inject
	protected UserLocalService userLocalService;

	@Inject
	protected UsersAdmin usersAdmin;

	private void _assertSearch(boolean includeSuborganizations)
		throws Exception {

		LinkedHashMap<String, Object> organizationParams =
			LinkedHashMapBuilder.<String, Object>put(
				"organizationsTree", _user.getOrganizations(true)
			).build();

		BaseModelSearchResult<Organization> baseModelSearchResult =
			searchOrganizations(organizationParams);

		List<Organization> expectedSearchResults = new ArrayList<>();

		expectedSearchResults.add(_organization);

		if (includeSuborganizations) {
			expectedSearchResults.addAll(_organization.getSuborganizations());
		}

		List<Organization> indexerSearchResults =
			baseModelSearchResult.getBaseModels();

		AssertUtils.assertEquals(
			String.valueOf(organizationParams),
			toStringList(
				ListUtil.sort(
					expectedSearchResults,
					(organization1, organization2) -> {
						String name1 = organization1.getName();
						String name2 = organization2.getName();

						return name1.compareToIgnoreCase(name2);
					})),
			toStringList(indexerSearchResults));

		List<Organization> finderSearchResults =
			organizationLocalService.search(
				_user.getCompanyId(),
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null, null,
				null, null, organizationParams, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new OrganizationNameComparator(true));

		AssertUtils.assertEquals(
			String.valueOf(organizationParams),
			toStringList(indexerSearchResults),
			toStringList(finderSearchResults));
	}

	private Organization _organization;

	@DeleteAfterTestRun
	private final List<Organization> _organizations = new ArrayList<>();

	private PermissionChecker _originalPermissionChecker;

	@DeleteAfterTestRun
	private Role _role;

	@DeleteAfterTestRun
	private User _user;

}