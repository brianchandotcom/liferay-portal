/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.security.permission.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectDefinitionSettingConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.definition.setting.builder.ObjectDefinitionSettingBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class CMSWorkflowTaskPermissionContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testContains() throws Exception {
		ObjectDefinition objectDefinition1 =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMS_BASIC_WEB_CONTENT", TestPropsValues.getCompanyId());

		ObjectFolder objectFolder =
			_objectFolderLocalService.getObjectFolderByExternalReferenceCode(
				ObjectFolderConstants.
					EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES,
				TestPropsValues.getCompanyId());

		ObjectDefinition objectDefinition2 = _publishObjectDefinition(
			objectFolder.getObjectFolderId());

		ObjectDefinition objectDefinition3 = _publishObjectDefinition(0);

		DepotEntry depotEntry1 = _addDepotEntry(DepotConstants.TYPE_SPACE);
		DepotEntry depotEntry2 = _addDepotEntry(DepotConstants.TYPE_SPACE);
		DepotEntry depotEntry3 = _addDepotEntry(
			DepotConstants.TYPE_ASSET_LIBRARY);

		_updateWorkflowDefinitionLink(depotEntry1, objectDefinition1);
		_updateWorkflowDefinitionLink(depotEntry1, objectDefinition3);
		_updateWorkflowDefinitionLink(depotEntry2, objectDefinition1);
		_updateWorkflowDefinitionLink(depotEntry3, objectDefinition2);

		Map<String, Serializable> values =
			HashMapBuilder.<String, Serializable>put(
				"content_i18n",
				HashMapBuilder.put(
					"en_US", RandomTestUtil.randomString()
				).build()
			).put(
				"title_i18n",
				HashMapBuilder.put(
					"en_US", RandomTestUtil.randomString()
				).build()
			).build();

		ObjectEntry objectEntry1 = _addObjectEntry(
			depotEntry1, objectDefinition1, values);
		ObjectEntry objectEntry2 = _addObjectEntry(
			depotEntry2, objectDefinition1, values);

		ObjectEntry objectEntry3 = _addObjectEntry(
			depotEntry3, objectDefinition2, Collections.emptyMap());
		ObjectEntry objectEntry4 = _addObjectEntry(
			depotEntry1, objectDefinition3, Collections.emptyMap());

		KaleoTaskInstanceToken kaleoTaskInstanceToken1 =
			_assignKaleoTaskInstanceToken(objectDefinition1, objectEntry1);
		KaleoTaskInstanceToken kaleoTaskInstanceToken2 =
			_assignKaleoTaskInstanceToken(objectDefinition1, objectEntry2);
		KaleoTaskInstanceToken kaleoTaskInstanceToken3 =
			_assignKaleoTaskInstanceToken(objectDefinition2, objectEntry3);
		KaleoTaskInstanceToken kaleoTaskInstanceToken4 =
			_assignKaleoTaskInstanceToken(objectDefinition3, objectEntry4);

		User user1 = UserTestUtil.addUser();

		_addUserGroupRole(
			depotEntry1, DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR,
			user1);

		User user2 = UserTestUtil.addUser();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.CMS_ADMINISTRATOR);

		_userLocalService.addRoleUser(role.getRoleId(), user2.getUserId());

		User user3 = UserTestUtil.addUser();

		_userLocalService.addGroupUser(
			depotEntry1.getGroupId(), user3.getUserId());

		User user4 = UserTestUtil.addUser();

		_addUserGroupRole(
			depotEntry2, DepotRolesConstants.ASSET_LIBRARY_OWNER, user4);

		User user5 = UserTestUtil.addUser();

		_addUserGroupRole(
			depotEntry3, DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR,
			user5);

		PermissionChecker permissionChecker2 = _permissionCheckerFactory.create(
			user2);

		Assert.assertTrue(
			_kaleoTaskInstanceTokenModelResourcePermission.contains(
				permissionChecker2, kaleoTaskInstanceToken1, ActionKeys.VIEW));
		Assert.assertTrue(
			_kaleoTaskInstanceTokenModelResourcePermission.contains(
				permissionChecker2, kaleoTaskInstanceToken2, ActionKeys.VIEW));
		Assert.assertFalse(
			_kaleoTaskInstanceTokenModelResourcePermission.contains(
				permissionChecker2, kaleoTaskInstanceToken4, ActionKeys.VIEW));

		PermissionChecker permissionChecker1 = _permissionCheckerFactory.create(
			user1);

		Assert.assertTrue(
			_kaleoTaskInstanceTokenModelResourcePermission.contains(
				permissionChecker1, kaleoTaskInstanceToken1, ActionKeys.VIEW));
		Assert.assertFalse(
			_kaleoTaskInstanceTokenModelResourcePermission.contains(
				permissionChecker1, kaleoTaskInstanceToken2, ActionKeys.VIEW));
		Assert.assertFalse(
			_kaleoTaskInstanceTokenModelResourcePermission.contains(
				permissionChecker1, kaleoTaskInstanceToken4, ActionKeys.VIEW));

		Assert.assertFalse(
			_kaleoTaskInstanceTokenModelResourcePermission.contains(
				_permissionCheckerFactory.create(user3),
				kaleoTaskInstanceToken1, ActionKeys.VIEW));

		PermissionChecker permissionChecker4 = _permissionCheckerFactory.create(
			user4);

		Assert.assertFalse(
			_kaleoTaskInstanceTokenModelResourcePermission.contains(
				permissionChecker4, kaleoTaskInstanceToken1, ActionKeys.VIEW));
		Assert.assertTrue(
			_kaleoTaskInstanceTokenModelResourcePermission.contains(
				permissionChecker4, kaleoTaskInstanceToken2, ActionKeys.VIEW));

		Assert.assertFalse(
			_kaleoTaskInstanceTokenModelResourcePermission.contains(
				_permissionCheckerFactory.create(user5),
				kaleoTaskInstanceToken3, ActionKeys.VIEW));

		PermissionThreadLocal.setPermissionChecker(permissionChecker1);

		PrincipalException.MustHavePermission mustHavePermission =
			Assert.assertThrows(
				PrincipalException.MustHavePermission.class,
				() -> _workflowTaskManager.completeWorkflowTask(
					TestPropsValues.getCompanyId(), user1.getUserId(),
					kaleoTaskInstanceToken1.getKaleoTaskInstanceTokenId(),
					"approve", RandomTestUtil.randomString(), new HashMap<>()));

		Assert.assertArrayEquals(
			new String[] {ActionKeys.VIEW}, mustHavePermission.actionId);
		Assert.assertEquals(
			kaleoTaskInstanceToken1.getKaleoTaskInstanceTokenId(),
			mustHavePermission.resourceId);
		Assert.assertEquals(
			WorkflowTask.class.getName(), mustHavePermission.resourceName);
		Assert.assertEquals(user1.getUserId(), mustHavePermission.userId);

		_workflowTaskManager.assignWorkflowTaskToUser(
			TestPropsValues.getCompanyId(), user1.getUserId(),
			kaleoTaskInstanceToken1.getKaleoTaskInstanceTokenId(),
			user1.getUserId(), RandomTestUtil.randomString(), null,
			new HashMap<>());

		_workflowTaskManager.completeWorkflowTask(
			TestPropsValues.getCompanyId(), user1.getUserId(),
			kaleoTaskInstanceToken1.getKaleoTaskInstanceTokenId(), "approve",
			RandomTestUtil.randomString(), new HashMap<>());

		objectEntry1 = _objectEntryLocalService.getObjectEntry(
			objectEntry1.getObjectEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, objectEntry1.getStatus());
	}

	private DepotEntry _addDepotEntry(int type) throws Exception {
		Group group = _groupLocalService.getGroup(
			TestPropsValues.getCompanyId(), GroupConstants.CMS);

		return _depotEntryLocalService.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			null, type,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	private ObjectEntry _addObjectEntry(
			DepotEntry depotEntry, ObjectDefinition objectDefinition,
			Map<String, Serializable> values)
		throws Exception {

		return _objectEntryLocalService.addObjectEntry(
			depotEntry.getGroupId(), TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(), 0, "en_US", values,
			ServiceContextTestUtil.getServiceContext(depotEntry.getGroupId()));
	}

	private void _addUserGroupRole(
			DepotEntry depotEntry, String roleName, User user)
		throws Exception {

		_userLocalService.addGroupUser(
			depotEntry.getGroupId(), user.getUserId());

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), roleName);

		_userGroupRoleLocalService.addUserGroupRoles(
			user.getUserId(), depotEntry.getGroupId(),
			new long[] {role.getRoleId()});
	}

	private KaleoTaskInstanceToken _assignKaleoTaskInstanceToken(
			ObjectDefinition objectDefinition, ObjectEntry objectEntry)
		throws Exception {

		List<KaleoTaskInstanceToken> kaleoTaskInstanceTokens =
			_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceTokens(
				objectDefinition.getClassName(),
				objectEntry.getObjectEntryId());

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			kaleoTaskInstanceTokens.get(0);

		_workflowTaskManager.assignWorkflowTaskToUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), null,
			new HashMap<>());

		return _kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceToken(
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());
	}

	private ObjectDefinition _publishObjectDefinition(long objectFolderId)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				null, TestPropsValues.getUserId(), objectFolderId, null, null,
				true, false, true, false, true, false, false, false, false,
				null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionTestUtil.getRandomName(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_DEPOT,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.singletonList(
					new ObjectDefinitionSettingBuilder(
					).name(
						ObjectDefinitionSettingConstants.NAME_ACCEPT_ALL_GROUPS
					).value(
						StringPool.TRUE
					).build()),
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(), "text")),
				Collections.emptyList(), new ServiceContext());

		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());
	}

	private void _updateWorkflowDefinitionLink(
			DepotEntry depotEntry, ObjectDefinition objectDefinition)
		throws Exception {

		_workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			depotEntry.getGroupId(), objectDefinition.getClassName(), 0, 0,
			"Single Approver", 1);
	}

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	@Inject(
		filter = "model.class.name=com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken"
	)
	private ModelResourcePermission<KaleoTaskInstanceToken>
		_kaleoTaskInstanceTokenModelResourcePermission;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFolderLocalService _objectFolderLocalService;

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Inject
	private WorkflowTaskManager _workflowTaskManager;

}