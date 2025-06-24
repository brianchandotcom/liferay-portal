/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gislayne Vitorino
 */
@RunWith(Arquillian.class)
public class UpdatePermissionsMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testProcessAction() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			null, company.getCompanyId(), TestPropsValues.getUserId(), 0,
			RandomTestUtil.randomString(), null);

		Role ownerRole = _roleLocalService.getRole(
			company.getCompanyId(), RoleConstants.OWNER);

		for (String modelResourceOwnerDefaultAction :
				ResourceActionsUtil.getModelResourceOwnerDefaultActions(
					CTCollection.class.getName())) {

			Assert.assertTrue(
				_resourcePermissionLocalService.hasResourcePermission(
					company.getCompanyId(), CTCollection.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(ctCollection.getCtCollectionId()),
					ownerRole.getRoleId(), modelResourceOwnerDefaultAction));
		}

		Role viewerRole = _roleLocalService.getRole(
			company.getCompanyId(), RoleConstants.PUBLICATIONS_VIEWER);

		Assert.assertFalse(
			_resourcePermissionLocalService.hasResourcePermission(
				company.getCompanyId(), CTCollection.class.getName(),
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(company.getCompanyId()), viewerRole.getRoleId(),
				ActionKeys.UPDATE));
		Assert.assertTrue(
			_resourcePermissionLocalService.hasResourcePermission(
				company.getCompanyId(), CTCollection.class.getName(),
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(company.getCompanyId()), viewerRole.getRoleId(),
				ActionKeys.VIEW));

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			JavaConstants.JAKARTA_PORTLET_RESPONSE,
			new MockLiferayPortletActionResponse());
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.PORTLET_ID, CTPortletKeys.PUBLICATIONS);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(company);

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		String[] ownerRoleActionIds = {
			ActionKeys.DELETE, ActionKeys.PERMISSIONS, ActionKeys.UPDATE,
			ActionKeys.VIEW
		};

		JSONObject permissionsJSONObject = _jsonFactory.createJSONObject();

		permissionsJSONObject.put(
			String.valueOf(ownerRole.getRoleId()), ownerRoleActionIds
		).put(
			String.valueOf(viewerRole.getRoleId()),
			new String[] {ActionKeys.UPDATE}
		);

		mockLiferayPortletActionRequest.setParameter(
			"permissions", permissionsJSONObject.toString());

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		Assert.assertFalse(
			_resourcePermissionLocalService.hasResourcePermission(
				company.getCompanyId(), CTCollection.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(ctCollection.getCtCollectionId()),
				ownerRole.getRoleId(), CTActionKeys.PUBLISH));

		Assert.assertFalse(
			_resourcePermissionLocalService.hasResourcePermission(
				company.getCompanyId(), CTCollection.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(ctCollection.getCtCollectionId()),
				ownerRole.getRoleId(), CTActionKeys.INVITE_USERS));

		for (String actionId : ownerRoleActionIds) {
			Assert.assertTrue(
				_resourcePermissionLocalService.hasResourcePermission(
					company.getCompanyId(), CTCollection.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(ctCollection.getCtCollectionId()),
					ownerRole.getRoleId(), actionId));
		}

		Assert.assertFalse(
			_resourcePermissionLocalService.hasResourcePermission(
				company.getCompanyId(), CTCollection.class.getName(),
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(company.getCompanyId()), viewerRole.getRoleId(),
				ActionKeys.VIEW));

		Assert.assertTrue(
			_resourcePermissionLocalService.hasResourcePermission(
				company.getCompanyId(), CTCollection.class.getName(),
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(company.getCompanyId()), viewerRole.getRoleId(),
				ActionKeys.UPDATE));
	}

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject(filter = "mvc.command.name=/change_tracking/update_permissions")
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}