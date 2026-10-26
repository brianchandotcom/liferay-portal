/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.constants.KBConstants;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.service.KBTemplateLocalService;
import com.liferay.knowledge.base.service.KBTemplateService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class KBTemplateServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();

		_role = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);

		RoleTestUtil.addResourcePermission(
			_role, KBConstants.RESOURCE_NAME_ADMIN,
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group1.getGroupId()),
			KBActionKeys.DELETE_KB_TEMPLATES);

		_user = UserTestUtil.addUser();
		_unrecognizedPortletId =
			"TEST_PORTLET_" + RandomTestUtil.randomString();

		_siteMemberUser = UserTestUtil.addUser(_group1.getGroupId());

		_userGroupRoleLocalService.addUserGroupRoles(
			_siteMemberUser.getUserId(), _group1.getGroupId(),
			new long[] {_role.getRoleId()});
	}

	@Test
	@TestInfo("LPD-111631")
	public void testAddKBTemplate() throws Exception {
		_testAddKBTemplate(KBPortletKeys.KNOWLEDGE_BASE_ADMIN);
		_testAddKBTemplate(KBPortletKeys.KNOWLEDGE_BASE_DISPLAY);
		_testAddKBTemplateWithoutAddKBTemplatePermission();
		_testAddKBTemplateWithUnrecognizedPortletId();
	}

	@Test
	public void testDeleteKBTemplates() throws Exception {
		_testDeleteKBTemplates();
		_testDeleteKBTemplatesWithKBTemplateFromDifferentGroup();
		_testDeleteKBTemplatesWithKBTemplateFromDifferentGroupAndDeletePermission();
		_testDeleteKBTemplatesWithKBTemplatesFromDifferentGroups();
	}

	private KBTemplate _addKBTemplate(Group group) throws Exception {
		return _kbTemplateLocalService.addKBTemplate(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId()));
	}

	private KBTemplate _addKBTemplate(String portletId) throws Exception {
		return _kbTemplateService.addKBTemplate(
			portletId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_group1, _siteMemberUser.getUserId()));
	}

	private void _addPortletResourcePermissions(String actionId)
		throws Exception {

		RoleTestUtil.addResourcePermission(
			_role, KBConstants.RESOURCE_NAME_ADMIN,
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group1.getGroupId()),
			actionId);
		RoleTestUtil.addResourcePermission(
			_role, KBConstants.RESOURCE_NAME_DISPLAY,
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group1.getGroupId()),
			actionId);
	}

	private void _testAddKBTemplate(String portletId) throws Exception {
		_addPortletResourcePermissions(KBActionKeys.ADD_KB_TEMPLATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_siteMemberUser)) {

			KBTemplate kbTemplate = _addKBTemplate(portletId);

			Assert.assertEquals(
				_siteMemberUser.getUserId(), kbTemplate.getUserId());
		}
	}

	private void _testAddKBTemplateWithUnrecognizedPortletId()
		throws Exception {

		_addPortletResourcePermissions(KBActionKeys.ADD_KB_TEMPLATE);

		int kbTemplatesCount = _kbTemplateLocalService.getGroupKBTemplatesCount(
			_group1.getGroupId());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_siteMemberUser)) {

			Assert.assertThrows(
				PrincipalException.class,
				() -> _addKBTemplate(_unrecognizedPortletId));
		}

		Assert.assertEquals(
			kbTemplatesCount,
			_kbTemplateLocalService.getGroupKBTemplatesCount(
				_group1.getGroupId()));
	}

	private void _testAddKBTemplateWithoutAddKBTemplatePermission()
		throws Exception {

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user)) {

			PrincipalException.MustHavePermission mustHavePermission =
				Assert.assertThrows(
					PrincipalException.MustHavePermission.class,
					() -> _addKBTemplate(KBPortletKeys.KNOWLEDGE_BASE_ADMIN));

			Assert.assertArrayEquals(
				new String[] {KBActionKeys.ADD_KB_TEMPLATE},
				mustHavePermission.actionId);
			Assert.assertEquals(
				_group1.getGroupId(), mustHavePermission.resourceId);
			Assert.assertEquals(
				KBConstants.RESOURCE_NAME_ADMIN,
				mustHavePermission.resourceName);
		}
	}

	private void _testDeleteKBTemplates() throws Exception {
		KBTemplate kbTemplate = _addKBTemplate(_group1);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_siteMemberUser)) {

			_kbTemplateService.deleteKBTemplates(
				_group1.getGroupId(),
				new long[] {kbTemplate.getKbTemplateId()});
		}

		Assert.assertNull(
			_kbTemplateLocalService.fetchKBTemplate(
				kbTemplate.getKbTemplateId()));
	}

	private void _testDeleteKBTemplatesWithKBTemplateFromDifferentGroup()
		throws Exception {

		KBTemplate kbTemplate = _addKBTemplate(_group2);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_siteMemberUser)) {

			Assert.assertThrows(
				PrincipalException.MustHavePermission.class,
				() -> _kbTemplateService.deleteKBTemplates(
					_group1.getGroupId(),
					new long[] {kbTemplate.getKbTemplateId()}));
		}

		Assert.assertNotNull(
			_kbTemplateLocalService.fetchKBTemplate(
				kbTemplate.getKbTemplateId()));
	}

	private void _testDeleteKBTemplatesWithKBTemplateFromDifferentGroupAndDeletePermission()
		throws Exception {

		KBTemplate kbTemplate = _addKBTemplate(_group2);

		_regularRole = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_resourcePermissionLocalService.setResourcePermissions(
			_regularRole.getCompanyId(), KBTemplate.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(kbTemplate.getKbTemplateId()),
			_regularRole.getRoleId(), new String[] {KBActionKeys.DELETE});

		_userLocalService.addRoleUser(
			_regularRole.getRoleId(), _siteMemberUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_siteMemberUser)) {

			_kbTemplateService.deleteKBTemplates(
				_group1.getGroupId(),
				new long[] {kbTemplate.getKbTemplateId()});
		}

		Assert.assertNull(
			_kbTemplateLocalService.fetchKBTemplate(
				kbTemplate.getKbTemplateId()));
	}

	private void _testDeleteKBTemplatesWithKBTemplatesFromDifferentGroups()
		throws Exception {

		KBTemplate kbTemplate1 = _addKBTemplate(_group1);
		KBTemplate kbTemplate2 = _addKBTemplate(_group2);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_siteMemberUser)) {

			Assert.assertThrows(
				PrincipalException.MustHavePermission.class,
				() -> _kbTemplateService.deleteKBTemplates(
					_group1.getGroupId(),
					new long[] {
						kbTemplate1.getKbTemplateId(),
						kbTemplate2.getKbTemplateId()
					}));
		}

		Assert.assertNotNull(
			_kbTemplateLocalService.fetchKBTemplate(
				kbTemplate1.getKbTemplateId()));
		Assert.assertNotNull(
			_kbTemplateLocalService.fetchKBTemplate(
				kbTemplate2.getKbTemplateId()));
	}

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@Inject
	private KBTemplateLocalService _kbTemplateLocalService;

	@Inject
	private KBTemplateService _kbTemplateService;

	@DeleteAfterTestRun
	private Role _regularRole;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@DeleteAfterTestRun
	private Role _role;

	@DeleteAfterTestRun
	private User _siteMemberUser;

	private String _unrecognizedPortletId;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}