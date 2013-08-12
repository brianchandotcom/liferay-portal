/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
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
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.ResourcePermissionUtil;
import com.liferay.portal.util.TestPropsValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Mate Thurzo
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class PermissionExportImportTest extends PowerMockito {

	@Test
	public void testPortletGuestPermissionsExportImport() throws Exception {

		// Export

		LayoutSetPrototype layoutSetPrototype =
			LayoutTestUtil.addLayoutSetPrototype(
				ServiceTestUtil.randomString());

		exportGroup = layoutSetPrototype.getGroup();

		exportLayout = LayoutTestUtil.addLayout(
			exportGroup.getGroupId(), ServiceTestUtil.randomString(), true);

		portletId = PortletKeys.BOOKMARKS;

		actionIds = new String[] {ActionKeys.ADD_TO_PAGE, ActionKeys.VIEW};

		exportResourcePrimKey = PortletPermissionUtil.getPrimaryKey(
			exportLayout.getPlid(), portletId);

		role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.GUEST);

		addPortletPermissions();

		exportPortletPermissions();

		// Import

		layoutSetPrototype = LayoutTestUtil.addLayoutSetPrototype(
			ServiceTestUtil.randomString());

		importGroup = layoutSetPrototype.getGroup();

		importLayout = LayoutTestUtil.addLayout(
			importGroup.getGroupId(), ServiceTestUtil.randomString(), true);

		importResourcePrimKey = PortletPermissionUtil.getPrimaryKey(
			importLayout.getPlid(), portletId);

		importPortletPermissions();

		validateImportedPortletPermissions();
	}

	protected void addPortletPermissions() throws Exception {
		Map<Long, String[]> roleIdsToActionIds = new HashMap<Long, String[]>();

		if (ResourceBlockLocalServiceUtil.isSupported(portletId)) {
			roleIdsToActionIds.put(role.getRoleId(), actionIds);

			ResourceBlockServiceUtil.setIndividualScopePermissions(
				TestPropsValues.getCompanyId(), exportGroup.getGroupId(),
				portletId, GetterUtil.getLong(exportResourcePrimKey),
				roleIdsToActionIds);
		}
		else {
			roleIdsToActionIds.put(role.getRoleId(), actionIds);

			ResourcePermissionServiceUtil.setIndividualResourcePermissions(
				exportGroup.getGroupId(), TestPropsValues.getCompanyId(),
				portletId, exportResourcePrimKey, roleIdsToActionIds);
		}
	}

	protected void exportPortletPermissions() throws Exception {
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

		portletElement = SAXReaderUtil.createElement("portlet");

		PermissionExporter permissionExporter = new PermissionExporter();

		permissionExporter.exportPortletPermissions(
			portletDataContext, new LayoutCache(), portletId, exportLayout,
			portletElement);
	}

	protected void importPortletPermissions() throws Exception {
		PermissionImporter permissionImporter = new PermissionImporter();

		permissionImporter.importPortletPermissions(
			new LayoutCache(), TestPropsValues.getCompanyId(),
			importGroup.getGroupId(), TestPropsValues.getUserId(), importLayout,
			portletElement, portletId);
	}

	protected void validateImportedPortletPermissions() throws Exception {
		List<String> actions = ResourceActionsUtil.getResourceActions(
			portletId, null);

		Resource resource = ResourceLocalServiceUtil.getResource(
			TestPropsValues.getCompanyId(), portletId,
			ResourceConstants.SCOPE_INDIVIDUAL, importResourcePrimKey);

		List<String> currentIndividualActions = new ArrayList<String>();

		ResourcePermissionUtil.populateResourcePermissionActionIds(
			importGroup.getGroupId(), role, resource, actions,
			currentIndividualActions, new ArrayList<String>(),
			new ArrayList<String>(), new ArrayList<String>());

		Assert.assertEquals(actionIds.length, currentIndividualActions.size());

		for (String action : currentIndividualActions) {
			boolean foundActionId = false;

			for (String actionId : actionIds) {
				if (action.equals(actionId)) {
					foundActionId = true;

					break;
				}
			}

			if (!foundActionId) {
				Assert.fail("Failed to import permissions.");
			}
		}
	}

	protected String[] actionIds;
	protected Group exportGroup;
	protected Layout exportLayout;
	protected String exportResourcePrimKey;
	protected Group importGroup;
	protected Layout importLayout;
	protected String importResourcePrimKey;
	protected Element portletElement;
	protected String portletId;
	protected Role role;

}