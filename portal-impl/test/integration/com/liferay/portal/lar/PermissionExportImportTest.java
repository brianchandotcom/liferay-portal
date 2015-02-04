/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.lar;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.ResourcePermissionUtil;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Mate Thurzo
 */
public class PermissionExportImportTest extends PowerMockito {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
	public void testPortletGuestPermissionsExportImport() throws Exception {

		// Export

		LayoutSetPrototype exportLayoutSetPrototype =
			LayoutTestUtil.addLayoutSetPrototype(RandomTestUtil.randomString());

		Group exportGroup = exportLayoutSetPrototype.getGroup();

		Layout exportLayout = LayoutTestUtil.addLayout(exportGroup, true);

		String exportResourcePrimKey = PortletPermissionUtil.getPrimaryKey(
			exportLayout.getPlid(), _PORTLET_ID);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.GUEST);

		addPortletPermissions(exportGroup, role, exportResourcePrimKey);

		Element portletElement = exportPortletPermissions(
			exportGroup, exportLayout);

		// Import

		LayoutSetPrototype importLayoutSetPrototype =
			LayoutTestUtil.addLayoutSetPrototype(RandomTestUtil.randomString());

		Group importGroup = importLayoutSetPrototype.getGroup();

		Layout importLayout = LayoutTestUtil.addLayout(importGroup, true);

		String importResourcePrimKey = PortletPermissionUtil.getPrimaryKey(
			importLayout.getPlid(), _PORTLET_ID);

		importPortletPermissions(importGroup, importLayout, portletElement);

		validateImportedPortletPermissions(
			importGroup, role, importResourcePrimKey);

		LayoutSetPrototypeLocalServiceUtil.deleteLayoutSetPrototype(
			exportLayoutSetPrototype);
		LayoutSetPrototypeLocalServiceUtil.deleteLayoutSetPrototype(
			importLayoutSetPrototype);
	}

	protected void addPortletPermissions(
			Group exportGroup, Role role, String exportResourcePrimKey)
		throws Exception {

		Map<Long, String[]> roleIdsToActionIds = new HashMap<>();

		if (ResourceBlockLocalServiceUtil.isSupported(_PORTLET_ID)) {
			roleIdsToActionIds.put(role.getRoleId(), _ACTION_IDS);

			ResourceBlockServiceUtil.setIndividualScopePermissions(
				TestPropsValues.getCompanyId(), exportGroup.getGroupId(),
				_PORTLET_ID, GetterUtil.getLong(exportResourcePrimKey),
				roleIdsToActionIds);
		}
		else {
			roleIdsToActionIds.put(role.getRoleId(), _ACTION_IDS);

			ResourcePermissionServiceUtil.setIndividualResourcePermissions(
				exportGroup.getGroupId(), TestPropsValues.getCompanyId(),
				_PORTLET_ID, exportResourcePrimKey, roleIdsToActionIds);
		}
	}

	protected Element exportPortletPermissions(
			Group exportGroup, Layout exportLayout)
		throws Exception {

		PortletDataContext portletDataContext = mock(PortletDataContext.class);

		when(
			portletDataContext.getCompanyId()
		).thenReturn(
			TestPropsValues.getCompanyId()
		);

		when(
			portletDataContext.getGroupId()
		).thenReturn(
			exportGroup.getGroupId()
		);

		Element portletElement = SAXReaderUtil.createElement("portlet");

		PermissionExporter permissionExporter =
			PermissionExporter.getInstance();

		permissionExporter.exportPortletPermissions(
			portletDataContext, _PORTLET_ID, exportLayout, portletElement);

		return portletElement;
	}

	protected void importPortletPermissions(
			Group importGroup, Layout importLayout, Element portletElement)
		throws Exception {

		PermissionImporter permissionImporter =
			PermissionImporter.getInstance();

		permissionImporter.importPortletPermissions(
			new LayoutCache(), TestPropsValues.getCompanyId(),
			importGroup.getGroupId(), TestPropsValues.getUserId(), importLayout,
			portletElement, _PORTLET_ID);
	}

	protected void validateImportedPortletPermissions(
			Group importGroup, Role role, String importResourcePrimKey)
		throws Exception {

		List<String> actions = ResourceActionsUtil.getResourceActions(
			_PORTLET_ID, null);

		Resource resource = ResourceLocalServiceUtil.getResource(
			TestPropsValues.getCompanyId(), _PORTLET_ID,
			ResourceConstants.SCOPE_INDIVIDUAL, importResourcePrimKey);

		List<String> currentIndividualActions = new ArrayList<>();

		ResourcePermissionUtil.populateResourcePermissionActionIds(
			importGroup.getGroupId(), role, resource, actions,
			currentIndividualActions, new ArrayList<String>(),
			new ArrayList<String>(), new ArrayList<String>());

		Assert.assertEquals(
			_ACTION_IDS.length, currentIndividualActions.size());

		for (String action : currentIndividualActions) {
			boolean foundActionId = false;

			for (String actionId : _ACTION_IDS) {
				if (action.equals(actionId)) {
					foundActionId = true;

					break;
				}
			}

			if (!foundActionId) {
				Assert.fail("Unable to import permissions");
			}
		}
	}

	private static final String[] _ACTION_IDS =
		{ActionKeys.ADD_TO_PAGE, ActionKeys.VIEW};

	private static final String _PORTLET_ID = PortletKeys.LAYOUTS_ADMIN;

}