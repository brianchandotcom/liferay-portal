/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Mikel Lorza
 */
@FeatureFlag("LPD-17564")
@RunWith(Arquillian.class)
@Sync
public class ViewHomeRecentAssetsSectionDisplayContextTest
	extends BaseFilesSectionDisplayContextTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGetAdditionalAPIURLParameters() throws Exception {
		_testGetAdditionalAPIURLParametersWithDepotRole(
			DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR);
		_testGetAdditionalAPIURLParametersWithDepotRole(
			DepotRolesConstants.ASSET_LIBRARY_CONTENT_REVIEWER);
		_testGetAdditionalAPIURLParametersWithDepotRole(
			DepotRolesConstants.ASSET_LIBRARY_MEMBER);
	}

	@Override
	@Test
	public void testGetCreationMenu() throws Exception {
	}

	@Override
	protected String getCMSSectionFilterString(Object displayContext) {
		return ReflectionTestUtil.invoke(
			displayContext, "getAdditionalAPIURLParameters", new Class<?>[0],
			new Object[0]);
	}

	@Override
	protected CreationMenu getCreationMenu(ObjectEntryFolder objectEntryFolder)
		throws Exception {

		return null;
	}

	@Override
	protected Map<String, String> getExpectedCreationMenuItems() {
		return new HashMap<>();
	}

	@Override
	protected String getFilterString() {
		return "cmsKind eq 'object' and (cmsSection eq 'contents' or " +
			"cmsSection eq 'files')";
	}

	@Override
	protected String getObjectFolderExternalReferenceCode() {
		if (RandomTestUtil.randomBoolean()) {
			return ObjectFolderConstants.
				EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES;
		}

		return ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES;
	}

	@Override
	protected String[] getObjectFolderExternalReferenceCodes() {
		return new String[] {
			ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES,
			ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES
		};
	}

	@Override
	protected Object getSectionDisplayContext(
			HttpServletRequest httpServletRequest)
		throws Exception {

		_fragmentRenderer.render(
			null, httpServletRequest, new MockHttpServletResponse());

		Object viewHomeRecentAssetsSectionDisplayContext =
			httpServletRequest.getAttribute(
				"com.liferay.site.cms.site.initializer.internal.display." +
					"context.ViewHomeRecentAssetsSectionDisplayContext");

		Assert.assertNotNull(viewHomeRecentAssetsSectionDisplayContext);

		return viewHomeRecentAssetsSectionDisplayContext;
	}

	private void _testGetAdditionalAPIURLParametersWithDepotRole(
			String depotRoleName)
		throws Exception {

		DepotEntry depotEntry = addDepotEntry(
			StringUtil.randomString(), TestPropsValues.getUserId());

		User user = UserTestUtil.addUser();

		groupLocalService.addUserGroup(
			user.getUserId(), depotEntry.getGroupId());

		Role depotRole = _roleLocalService.getRole(
			depotEntry.getCompanyId(), depotRoleName);

		_userGroupRoleLocalService.addUserGroupRoles(
			user.getUserId(), depotEntry.getGroupId(),
			new long[] {depotRole.getRoleId()});

		setUser(user);

		String additionalAPIURLParameters = getCMSSectionFilterString(
			getSectionDisplayContext(getMockHttpServletRequest(user)));

		Assert.assertTrue(
			additionalAPIURLParameters,
			additionalAPIURLParameters.contains(
				"filter=cmsKind eq 'object' and (cmsSection eq 'contents' or " +
					"cmsSection eq 'files')"));
		Assert.assertTrue(
			additionalAPIURLParameters,
			additionalAPIURLParameters.contains(
				"groupIds/any(g:g in (" + depotEntry.getGroupId() + "))"));

		setUser(adminUser);

		_depotEntryLocalService.deleteDepotEntry(depotEntry);

		_userLocalService.deleteUser(user);
	}

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.site.cms.site.initializer.internal.fragment.renderer.ViewHomeRecentAssetsJSPSectionFragmentRenderer"
	)
	private FragmentRenderer _fragmentRenderer;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}