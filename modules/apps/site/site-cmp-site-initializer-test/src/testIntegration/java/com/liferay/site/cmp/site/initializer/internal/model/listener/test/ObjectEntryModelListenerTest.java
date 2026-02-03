/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.cmp.site.initializer.test.util.CMPTestUtil;
import com.liferay.site.cms.site.initializer.util.PermissionUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pedro Leite
 */
@FeatureFlags(
	featureFlags = {@FeatureFlag("LPD-17564"), @FeatureFlag("LPD-58677")}
)
@RunWith(Arquillian.class)
public class ObjectEntryModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		CMPTestUtil.getOrAddGroup(ObjectEntryModelListenerTest.class);
	}

	@Test
	public void testOnAfterCreate() throws Exception {
		ObjectEntry projectObjectEntry = CMPTestUtil.addProjectObjectEntry();

		Role role = PermissionUtil.getOrAddCMSAdministratorRole(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		_assertResourceActions(
			projectObjectEntry, role.getName(), ActionKeys.ADD_DISCUSSION,
			ActionKeys.DELETE, ActionKeys.DELETE_DISCUSSION,
			ActionKeys.PERMISSIONS, ActionKeys.UPDATE,
			ActionKeys.UPDATE_DISCUSSION, ActionKeys.VIEW);

		_assertResourceActions(
			projectObjectEntry, DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR,
			ActionKeys.ADD_DISCUSSION, ActionKeys.DELETE,
			ActionKeys.DELETE_DISCUSSION, ActionKeys.PERMISSIONS,
			ActionKeys.UPDATE, ActionKeys.UPDATE_DISCUSSION, ActionKeys.VIEW);
		_assertResourceActions(
			projectObjectEntry,
			DepotRolesConstants.ASSET_LIBRARY_CONTENT_REVIEWER,
			ActionKeys.ADD_DISCUSSION, ActionKeys.VIEW);
		_assertResourceActions(
			projectObjectEntry, DepotRolesConstants.ASSET_LIBRARY_MEMBER,
			ActionKeys.ADD_DISCUSSION, ActionKeys.VIEW);

		ObjectEntry taskObjectEntry = CMPTestUtil.addTaskObjectEntry(
			projectObjectEntry);

		_assertResourceActions(
			taskObjectEntry, role.getName(), ActionKeys.ADD_DISCUSSION,
			ActionKeys.DELETE, ActionKeys.DELETE_DISCUSSION,
			ActionKeys.PERMISSIONS, ActionKeys.UPDATE,
			ActionKeys.UPDATE_DISCUSSION, ActionKeys.VIEW);

		_assertResourceActions(
			taskObjectEntry, DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR,
			ActionKeys.ADD_DISCUSSION, ActionKeys.DELETE,
			ActionKeys.DELETE_DISCUSSION, ActionKeys.PERMISSIONS,
			ActionKeys.UPDATE, ActionKeys.UPDATE_DISCUSSION, ActionKeys.VIEW);
		_assertResourceActions(
			taskObjectEntry, DepotRolesConstants.ASSET_LIBRARY_CONTENT_REVIEWER,
			ActionKeys.ADD_DISCUSSION, ActionKeys.DELETE,
			ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW);
		_assertResourceActions(
			taskObjectEntry, DepotRolesConstants.ASSET_LIBRARY_MEMBER,
			ActionKeys.ADD_DISCUSSION, ActionKeys.VIEW);
	}

	private void _assertResourceActions(
			ObjectEntry objectEntry, String roleName, String... actionIds)
		throws Exception {

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), roleName);

		ResourcePermission resourcePermission =
			_resourcePermissionLocalService.getResourcePermission(
				objectEntry.getCompanyId(), objectEntry.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry.getObjectEntryId()),
				role.getRoleId());

		for (String actionId : actionIds) {
			Assert.assertTrue(resourcePermission.hasActionId(actionId));
		}
	}

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}