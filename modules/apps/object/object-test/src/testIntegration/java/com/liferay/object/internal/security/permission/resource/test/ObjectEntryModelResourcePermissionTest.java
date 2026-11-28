/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.security.permission.resource.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectDefinitionSettingConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionRegistryUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Akhash Ramprakash
 */
@RunWith(Arquillian.class)
public class ObjectEntryModelResourcePermissionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_depotEntry = _depotEntryLocalService.addDepotEntry(
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			DepotConstants.TYPE_ASSET_LIBRARY,
			ServiceContextTestUtil.getServiceContext());
		_guestPermissionChecker = PermissionCheckerFactoryUtil.create(
			_userLocalService.getGuestUser(TestPropsValues.getCompanyId()));
		_guestRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.GUEST);

		_textObjectFieldName = "a" + RandomTestUtil.randomString();

		_objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				new TextObjectFieldBuilder(
				).labelMap(
					RandomTestUtil.randomLocaleStringMap()
				).name(
					_textObjectFieldName
				).build()),
			ObjectDefinitionConstants.SCOPE_DEPOT);

		_modelResourcePermission =
			ModelResourcePermissionRegistryUtil.getModelResourcePermission(
				_objectDefinition.getClassName());

		_objectDefinitionSettingLocalService.addObjectDefinitionSetting(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(),
			ObjectDefinitionSettingConstants.NAME_ACCEPTED_GROUP_IDS,
			String.valueOf(_depotEntry.getGroupId()));

		_objectEntry = _addObjectEntry(TestPropsValues.getUserId());
	}

	@Test
	@TestInfo("LPD-94886")
	public void testContains() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(), _objectDefinition.getClassName(),
			ResourceConstants.SCOPE_GROUP, String.valueOf(group.getGroupId()),
			_guestRole.getRoleId(), new String[] {ActionKeys.VIEW});

		Assert.assertFalse(
			_modelResourcePermission.contains(
				_guestPermissionChecker, _objectEntry, ActionKeys.VIEW));

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			_depotEntry.getDepotEntryId(), group.getGroupId());

		Assert.assertTrue(
			_modelResourcePermission.contains(
				_guestPermissionChecker, _objectEntry, ActionKeys.VIEW));
	}

	@Test
	public void testContainsWithScopePermission() throws Exception {
		_depotRole = RoleTestUtil.addRole(RoleConstants.TYPE_DEPOT);
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);
		_user = UserTestUtil.addUser();

		_roleLocalService.addUserRole(_user.getUserId(), _role.getRoleId());
		_userGroupRoleLocalService.addUserGroupRoles(
			_user.getUserId(), _depotEntry.getGroupId(),
			new long[] {_depotRole.getRoleId()});

		_deleteIndividualResourcePermissions();

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(), _objectDefinition.getClassName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), _role.getRoleId(),
			new String[] {ActionKeys.VIEW});

		Assert.assertTrue(
			_modelResourcePermission.contains(
				PermissionCheckerFactoryUtil.create(_user), _objectEntry,
				ActionKeys.VIEW));

		_resourcePermissionLocalService.removeResourcePermission(
			TestPropsValues.getCompanyId(), _objectDefinition.getClassName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), _role.getRoleId(),
			ActionKeys.VIEW);
		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(), _objectDefinition.getClassName(),
			ResourceConstants.SCOPE_GROUP,
			String.valueOf(_depotEntry.getGroupId()), _role.getRoleId(),
			new String[] {ActionKeys.VIEW});

		Assert.assertTrue(
			_modelResourcePermission.contains(
				PermissionCheckerFactoryUtil.create(_user), _objectEntry,
				ActionKeys.VIEW));

		_resourcePermissionLocalService.removeResourcePermission(
			TestPropsValues.getCompanyId(), _objectDefinition.getClassName(),
			ResourceConstants.SCOPE_GROUP,
			String.valueOf(_depotEntry.getGroupId()), _role.getRoleId(),
			ActionKeys.VIEW);
		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(), _objectDefinition.getClassName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			_depotRole.getRoleId(), new String[] {ActionKeys.VIEW});

		Assert.assertTrue(
			_modelResourcePermission.contains(
				PermissionCheckerFactoryUtil.create(_user), _objectEntry,
				ActionKeys.VIEW));

		Assert.assertTrue(
			_modelResourcePermission.contains(
				PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()),
				_objectEntry, ActionKeys.VIEW));
	}

	@Test
	public void testContainsWithoutScopePermission() throws Exception {
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);
		_user = UserTestUtil.addUser();

		_roleLocalService.addUserRole(_user.getUserId(), _role.getRoleId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		Assert.assertFalse(
			_modelResourcePermission.contains(
				permissionChecker, _objectEntry, ActionKeys.VIEW));

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(), _objectDefinition.getClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_objectEntry.getObjectEntryId()), _role.getRoleId(),
			new String[] {ActionKeys.VIEW});

		Assert.assertTrue(
			_modelResourcePermission.contains(
				permissionChecker, _objectEntry, ActionKeys.VIEW));

		Assert.assertTrue(
			_modelResourcePermission.contains(
				permissionChecker, _addObjectEntry(_user.getUserId()),
				ActionKeys.VIEW));

		_deleteIndividualResourcePermissions();

		try {
			_modelResourcePermission.contains(
				permissionChecker, _objectEntry, ActionKeys.VIEW);

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
			String message = illegalArgumentException.getMessage();

			Assert.assertTrue(
				message.startsWith(
					"Someone may be trying to circumvent the permission " +
						"checker"));
		}
	}

	private ObjectEntry _addObjectEntry(long userId) throws Exception {
		return _objectEntryLocalService.addObjectEntry(
			_depotEntry.getGroupId(), userId,
			_objectDefinition.getObjectDefinitionId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			null,
			HashMapBuilder.<String, Serializable>put(
				_textObjectFieldName, RandomTestUtil.randomString()
			).build(),
			ServiceContextTestUtil.getServiceContext());
	}

	private void _deleteIndividualResourcePermissions() throws Exception {
		_resourcePermissionLocalService.deleteResourcePermissions(
			TestPropsValues.getCompanyId(), _objectDefinition.getClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_objectEntry.getObjectEntryId()));
	}

	private DepotEntry _depotEntry;

	@Inject
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@DeleteAfterTestRun
	private Role _depotRole;

	private PermissionChecker _guestPermissionChecker;
	private Role _guestRole;
	private ModelResourcePermission<ObjectEntry> _modelResourcePermission;
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionSettingLocalService
		_objectDefinitionSettingLocalService;

	private ObjectEntry _objectEntry;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@DeleteAfterTestRun
	private Role _role;

	@Inject
	private RoleLocalService _roleLocalService;

	private String _textObjectFieldName;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}