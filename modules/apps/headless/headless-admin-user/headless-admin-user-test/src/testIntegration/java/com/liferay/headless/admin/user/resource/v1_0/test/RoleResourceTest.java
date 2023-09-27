/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.dto.v1_0.Role;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class RoleResourceTest extends BaseRoleResourceTestCase {

	@ClassRule
	@Rule
	public static final SynchronousMailTestRule synchronousMailTestRule =
		SynchronousMailTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addGroupAdminUser(testGroup);
	}

	@Override
	@Test
	public void testGetRolesPage() throws Exception {
		Page<Role> page = roleResource.getRolesPage(
			null, null, Pagination.of(1, 100));

		List<Role> roles = new ArrayList<>(page.getItems());

		Role role1 = _addRole(randomRole());

		roles.add(role1);

		Role role2 = _addRole(randomRole());

		roles.add(role2);

		page = roleResource.getRolesPage(
			null, null, Pagination.of(1, roles.size()));

		Assert.assertEquals(roles.size(), page.getTotalCount());

		assertEqualsIgnoringOrder(roles, (List<Role>)page.getItems());
		assertValid(page);

		page = roleResource.getRolesPage(
			null, role1.getName(), Pagination.of(1, roles.size()));

		roles = (List<Role>)page.getItems();

		assertEquals(role1, roles.get(0));
	}

	@Override
	@Test
	public void testGetRolesPageWithPagination() throws Exception {
		Page<Role> rolesPage = roleResource.getRolesPage(null, null, null);

		testGetRolesPage_addRole(randomRole());
		testGetRolesPage_addRole(randomRole());
		testGetRolesPage_addRole(randomRole());

		Page<Role> page1 = roleResource.getRolesPage(
			null, null, Pagination.of(1, 2));

		List<Role> roles1 = (List<Role>)page1.getItems();

		Assert.assertEquals(roles1.toString(), 2, roles1.size());

		Page<Role> page2 = roleResource.getRolesPage(
			null, null, Pagination.of(2, 2));

		Assert.assertEquals(
			rolesPage.getTotalCount() + 3, page2.getTotalCount());
	}

	@Override
	@Test
	public void testGraphQLGetRolesPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"roles",
			(HashMap)HashMapBuilder.put(
				"page", 1
			).put(
				"pageSize", 2
			).build(),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		int totalCount = JSONUtil.getValueAsInt(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/roles", "Object/totalCount");

		testGraphQLRole_addRole();
		testGraphQLRole_addRole();

		Assert.assertEquals(
			totalCount + 2,
			JSONUtil.getValueAsInt(
				invokeGraphQLQuery(graphQLField), "JSONObject/data",
				"JSONObject/roles", "Object/totalCount"));
	}

	@Override
	@Test
	public void testPostOrganizationRoleUserAccountAssociation()
		throws Exception {

		Role role = testPostOrganizationRoleUserAccountAssociation_addRole();
		Organization organization = OrganizationTestUtil.addOrganization();

		assertHttpResponseStatusCode(
			204,
			roleResource.postOrganizationRoleUserAccountAssociationHttpResponse(
				role.getId(), _user.getUserId(),
				organization.getOrganizationId()));
		assertHttpResponseStatusCode(
			404,
			roleResource.postOrganizationRoleUserAccountAssociationHttpResponse(
				0L, _user.getUserId(), organization.getOrganizationId()));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME_EXCEPTION_MAPPER, LoggerTestUtil.ERROR)) {

			assertHttpResponseStatusCode(
				500,
				roleResource.
					postOrganizationRoleUserAccountAssociationHttpResponse(
						_getRoleId(_addRole(RoleConstants.TYPE_REGULAR)),
						_user.getUserId(), organization.getOrganizationId()));
			assertHttpResponseStatusCode(
				500,
				roleResource.
					postOrganizationRoleUserAccountAssociationHttpResponse(
						_getRoleId(_addRole(RoleConstants.TYPE_SITE)),
						_user.getUserId(), organization.getOrganizationId()));
		}
	}

	@Override
	@Test
	public void testPostRoleUserAccountAssociation() throws Exception {
		Role role = testPostRoleUserAccountAssociation_addRole();

		assertHttpResponseStatusCode(
			204,
			roleResource.postRoleUserAccountAssociationHttpResponse(
				role.getId(), _user.getUserId()));

		assertHttpResponseStatusCode(
			404,
			roleResource.postRoleUserAccountAssociationHttpResponse(
				0L, _user.getUserId()));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME_EXCEPTION_MAPPER, LoggerTestUtil.ERROR)) {

			assertHttpResponseStatusCode(
				500,
				roleResource.postRoleUserAccountAssociationHttpResponse(
					_getRoleId(_addRole(RoleConstants.TYPE_ORGANIZATION)),
					_user.getUserId()));
			assertHttpResponseStatusCode(
				500,
				roleResource.postRoleUserAccountAssociationHttpResponse(
					_getRoleId(_addRole(RoleConstants.TYPE_SITE)),
					_user.getUserId()));
		}
	}

	@Override
	@Test
	public void testPostSiteRoleUserAccountAssociation() throws Exception {
		Role role = testPostSiteRoleUserAccountAssociation_addRole();

		assertHttpResponseStatusCode(
			204,
			roleResource.postSiteRoleUserAccountAssociationHttpResponse(
				role.getId(), _user.getUserId(), testGroup.getGroupId()));

		assertHttpResponseStatusCode(
			404,
			roleResource.postSiteRoleUserAccountAssociationHttpResponse(
				0L, _user.getUserId(), testGroup.getGroupId()));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME_EXCEPTION_MAPPER, LoggerTestUtil.ERROR)) {

			assertHttpResponseStatusCode(
				500,
				roleResource.postSiteRoleUserAccountAssociationHttpResponse(
					_getRoleId(_addRole(RoleConstants.TYPE_REGULAR)),
					_user.getUserId(), testGroup.getGroupId()));
			assertHttpResponseStatusCode(
				500,
				roleResource.postSiteRoleUserAccountAssociationHttpResponse(
					_getRoleId(_addRole(RoleConstants.TYPE_ORGANIZATION)),
					_user.getUserId(), testGroup.getGroupId()));
		}
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected Role randomRole() throws Exception {
		Role role = super.randomRole();

		role.setRoleType(
			RoleConstants.getTypeLabel(
				RoleConstants.TYPES_ORGANIZATION_AND_REGULAR_AND_SITE
					[RandomTestUtil.randomInt(0, 2)]));

		return role;
	}

	@Override
	protected Role testDeleteOrganizationRoleUserAccountAssociation_addRole()
		throws Exception {

		return _addRole(RoleConstants.TYPE_ORGANIZATION);
	}

	@Override
	protected Long
			testDeleteOrganizationRoleUserAccountAssociation_getOrganizationId()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		return organization.getOrganizationId();
	}

	@Override
	protected Long
			testDeleteOrganizationRoleUserAccountAssociation_getUserAccountId()
		throws Exception {

		return _user.getUserId();
	}

	@Override
	protected Role testDeleteRoleUserAccountAssociation_addRole()
		throws Exception {

		return _addRole(RoleConstants.TYPE_REGULAR);
	}

	@Override
	protected Long testDeleteRoleUserAccountAssociation_getUserAccountId()
		throws Exception {

		return _user.getUserId();
	}

	@Override
	protected Role testDeleteSiteRoleUserAccountAssociation_addRole()
		throws Exception {

		return _addRole(RoleConstants.TYPE_SITE);
	}

	@Override
	protected Long testDeleteSiteRoleUserAccountAssociation_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	@Override
	protected Long testDeleteSiteRoleUserAccountAssociation_getUserAccountId()
		throws Exception {

		return _user.getUserId();
	}

	@Override
	protected Role testGetRole_addRole() throws Exception {
		return _addRole(randomRole());
	}

	@Override
	protected Role testGetRolesPage_addRole(Role role) throws Exception {
		return _addRole(role);
	}

	@Override
	protected Role testGraphQLRole_addRole() throws Exception {
		return testGetRole_addRole();
	}

	@Override
	protected Role testPostOrganizationRoleUserAccountAssociation_addRole()
		throws Exception {

		return _addRole(RoleConstants.TYPE_ORGANIZATION);
	}

	@Override
	protected Role testPostRoleUserAccountAssociation_addRole()
		throws Exception {

		return _addRole(RoleConstants.TYPE_REGULAR);
	}

	@Override
	protected Role testPostSiteRoleUserAccountAssociation_addRole()
		throws Exception {

		return _addRole(RoleConstants.TYPE_SITE);
	}

	private Role _addRole(int type) throws Exception {
		Role role = randomRole();

		role.setRoleType(RoleConstants.getTypeLabel(type));

		return _addRole(role);
	}

	private Role _addRole(Role role) throws Exception {
		RoleLocalServiceUtil.deleteUserRole(
			_user.getUserId(),
			RoleLocalServiceUtil.getRole(
				testGroup.getCompanyId(), RoleConstants.USER));

		com.liferay.portal.kernel.model.Role serviceBuilderRole =
			RoleLocalServiceUtil.addRole(
				_user.getUserId(), null, 0, role.getName(),
				HashMapBuilder.put(
					LocaleUtil.getDefault(), role.getName()
				).build(),
				null, _toRoleType(role.getRoleType()), null,
				new ServiceContext() {
					{
						setCompanyId(testCompany.getCompanyId());
						setUserId(_user.getUserId());
					}
				});

		RoleLocalServiceUtil.addUserRole(_user.getUserId(), serviceBuilderRole);

		return _toRole(serviceBuilderRole);
	}

	private long _getRoleId(Role role) {
		return role.getId();
	}

	private Role _toRole(com.liferay.portal.kernel.model.Role role) {
		return new Role() {
			{
				description = role.getDescription();
				id = role.getRoleId();
				name = role.getName();
			}
		};
	}

	private int _toRoleType(String roleTypeLabel) {
		if (roleTypeLabel.equals(RoleConstants.TYPE_ORGANIZATION_LABEL)) {
			return RoleConstants.TYPE_ORGANIZATION;
		}
		else if (roleTypeLabel.equals(RoleConstants.TYPE_SITE_LABEL)) {
			return RoleConstants.TYPE_SITE;
		}
		else if (roleTypeLabel.equals(RoleConstants.TYPE_REGULAR_LABEL)) {
			return RoleConstants.TYPE_REGULAR;
		}

		throw new IllegalArgumentException(
			"Invalid role type label " + roleTypeLabel);
	}

	private static final String _CLASS_NAME_EXCEPTION_MAPPER =
		"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
			"ExceptionMapper";

	private User _user;

}