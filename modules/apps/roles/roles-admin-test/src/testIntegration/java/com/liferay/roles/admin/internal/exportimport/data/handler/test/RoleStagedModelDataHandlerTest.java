/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.roles.admin.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Mendez Gonzalez
 */
@RunWith(Arquillian.class)
public class RoleStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testImportRoleWithUserGroup() throws Exception {
		initExport();

		UserGroup userGroup = _userGroupLocalService.addUserGroup(
			TestPropsValues.getUserId(), stagingGroup.getCompanyId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId()));

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_groupLocalService.addRoleGroup(
			role.getRoleId(), userGroup.getGroupId());

		Assert.assertEquals(
			1, _roleLocalService.getAssigneesTotal(role.getRoleId()));

		StagedModelDataHandlerUtil.exportStagedModel(portletDataContext, role);

		String originalName = PrincipalThreadLocal.getName();
		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		Company company = CompanyTestUtil.addCompany();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					company.getCompanyId())) {

			User user = UserTestUtil.getAdminUser(company.getCompanyId());

			Assert.assertNotNull(user);

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));
			PrincipalThreadLocal.setName(user.getUserId());

			initImport(stagingGroup, company.getGroup());

			Role exportedRole = (Role)readExportedStagedModel(role);

			Role importedRole = _getImportedRole(
				exportedRole, company.getGroup(), role, user);

			Assert.assertEquals(
				exportedRole.getExternalReferenceCode(),
				importedRole.getExternalReferenceCode());
			Assert.assertEquals(exportedRole.getName(), importedRole.getName());
			Assert.assertEquals(
				0,
				_roleLocalService.getAssigneesTotal(importedRole.getRoleId()));
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
			PrincipalThreadLocal.setName(originalName);

			_companyLocalService.deleteCompany(company);
		}
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		return RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return RoleLocalServiceUtil.getRoleByUuidAndCompanyId(
			uuid, group.getCompanyId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return Role.class;
	}

	@Override
	protected void initExport() throws Exception {
		super.initExport();

		Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
			portletDataContext.getCompanyId());

		rootElement.addAttribute(
			"company-group-id", String.valueOf(companyGroup.getGroupId()));

		Group userPersonalSiteGroup =
			GroupLocalServiceUtil.getUserPersonalSiteGroup(
				portletDataContext.getCompanyId());

		rootElement.addAttribute(
			"user-personal-site-group-id",
			String.valueOf(userPersonalSiteGroup.getGroupId()));
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		// super.validateImportedStagedModel(stagedModel, importedStagedModel);

		Role role = (Role)stagedModel;
		Role importedRole = (Role)importedStagedModel;

		Assert.assertEquals(role.getName(), importedRole.getName());
		Assert.assertEquals(
			role.getDescription(), importedRole.getDescription());
		Assert.assertEquals(role.getType(), importedRole.getType());
		Assert.assertEquals(role.getSubtype(), importedRole.getSubtype());
	}

	private Role _getImportedRole(
			Role exportedRole, Group group, Role importedRole, User user)
		throws Exception {

		portletDataContext.setUserIdStrategy(new TestUserIdStrategy(user));

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedRole);

		return (Role)getStagedModel(importedRole.getUuid(), group);
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

}