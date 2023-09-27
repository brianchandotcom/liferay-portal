/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.verify.model.VerifiableResourcedModel;
import com.liferay.portal.model.impl.ResourcePermissionImpl;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.VerifyResourcePermissions;
import com.liferay.portal.verify.model.GroupVerifiableResourcedModel;
import com.liferay.portal.verify.model.RoleVerifiableModel;
import com.liferay.portal.verify.test.util.BaseVerifyProcessTestCase;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class VerifyResourcePermissionsTest extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testVerifyGroupResourcedModel() throws Exception {
		_group = GroupTestUtil.addGroup();

		_testVerifyResourcedModel(
			_group.getCompanyId(), _group.getGroupId(),
			_group.getCreatorUserId(), new GroupVerifiableResourcedModel());
	}

	@Test
	public void testVerifyRoleResourcedModel() throws Exception {
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_testVerifyResourcedModel(
			_role.getCompanyId(), _role.getRoleId(), _role.getUserId(),
			new RoleVerifiableModel());
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new VerifyResourcePermissions();
	}

	private void _testVerifyResourcedModel(
			long companyId, long pk, long userId,
			VerifiableResourcedModel verifiableResourcedModel)
		throws Exception {

		Role ownerRole = _roleLocalService.getRole(
			companyId, RoleConstants.OWNER);

		ResourcePermission resourcePermission =
			_resourcePermissionLocalService.fetchResourcePermission(
				companyId, verifiableResourcedModel.getModelName(),
				ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(pk),
				ownerRole.getRoleId());

		Assert.assertNotNull(resourcePermission);

		_resourcePermissionLocalService.deleteResourcePermission(
			resourcePermission);

		VerifyResourcePermissions.verify(verifiableResourcedModel);

		_entityCache.clearCache(ResourcePermissionImpl.class);
		_finderCache.clearCache(ResourcePermissionImpl.class);

		resourcePermission =
			_resourcePermissionLocalService.fetchResourcePermission(
				companyId, verifiableResourcedModel.getModelName(),
				ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(pk),
				ownerRole.getRoleId());

		Assert.assertNotNull(resourcePermission);

		Assert.assertEquals(
			String.valueOf(pk), resourcePermission.getPrimKey());

		Assert.assertEquals(userId, resourcePermission.getOwnerId());
	}

	@Inject
	private EntityCache _entityCache;

	@Inject
	private FinderCache _finderCache;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@DeleteAfterTestRun
	private Role _role;

	@Inject
	private RoleLocalService _roleLocalService;

}