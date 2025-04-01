/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.criteria.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Caio Pinheiro
 */
@RunWith(Arquillian.class)
public class UserOrganizationSegmentsCriteriaContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws InvalidSyntaxException {
		Bundle bundle = FrameworkUtil.getBundle(
			UserOrganizationSegmentsCriteriaContributorTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_entityModelServiceTracker = new ServiceTracker<>(
			bundleContext,
			bundleContext.createFilter(
				"(&(entity.model.name=Organization)(objectClass=" +
					EntityModel.class.getName() + "))"),
			null);

		_entityModelServiceTracker.open();

		_segmentsCriteriaContributorServiceTracker = new ServiceTracker<>(
			bundleContext,
			bundleContext.createFilter(
				StringBundler.concat(
					"(&(objectClass=",
					SegmentsCriteriaContributor.class.getName(),
					")(segments.criteria.contributor.key=user-",
					"organization))")),
			null);

		_segmentsCriteriaContributorServiceTracker.open();
	}

	@AfterClass
	public static void tearDownClass() {
		_entityModelServiceTracker.close();

		_segmentsCriteriaContributorServiceTracker.close();
	}

	@Before
	public void setUp() throws Exception {
		_organization = OrganizationTestUtil.addOrganization(true);
		_user = UserTestUtil.addUser();

		_organizationLocalService.addUserOrganization(
			_user.getUserId(), _organization.getOrganizationId());

		_role1 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_roleLocalService.addGroupRole(
			_organization.getGroupId(), _role1.getRoleId());

		_role2 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_userGroup = UserGroupTestUtil.addUserGroup();

		_roleLocalService.addGroupRole(
			_userGroup.getGroupId(), _role2.getRoleId());
		_userGroupLocalService.addUserUserGroup(
			_user.getUserId(), _userGroup.getUserGroupId());
	}

	@Test
	public void testGetCriteriaJSONObjectWithDateModifiedTruncated() {
		SegmentsCriteriaContributor segmentsCriteriaContributor =
			_getSegmentsCriteriaContributor();

		Criteria criteria = new Criteria();

		segmentsCriteriaContributor.contribute(
			criteria, "dateModified eq 2025-03-14T00:00:00.000Z",
			Criteria.Conjunction.AND);

		Assert.assertEquals(
			"dateModifiedTruncated eq 2025-03-14T00:00:00.000Z",
			criteria.getFilterString(Criteria.Type.MODEL));
	}

	private SegmentsCriteriaContributor _getSegmentsCriteriaContributor() {
		return _segmentsCriteriaContributorServiceTracker.getService();
	}

	private static ServiceTracker<EntityModel, EntityModel>
		_entityModelServiceTracker;
	private static ServiceTracker
		<SegmentsCriteriaContributor, SegmentsCriteriaContributor>
			_segmentsCriteriaContributorServiceTracker;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Organization _organization;

	@Inject
	private OrganizationLocalService _organizationLocalService;

	@DeleteAfterTestRun
	private Role _role1;

	@DeleteAfterTestRun
	private Role _role2;

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private User _user;

	@DeleteAfterTestRun
	private UserGroup _userGroup;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

}