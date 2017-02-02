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

package com.liferay.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.lar.LayoutCache;
import com.liferay.exportimport.lar.PermissionExporter;
import com.liferay.exportimport.lar.PermissionImporter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.Resource;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceBlockServiceUtil;
import com.liferay.portal.kernel.service.ResourceLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.ResourcePermissionUtil;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mate Thurzo
 */
@RunWith(Arquillian.class)
public class PermissionExportImportTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());
	}

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
			final Group exportGroup, Layout exportLayout)
		throws Exception {

		final Method getCompanyIdMethod = ReflectionTestUtil.getMethod(
			PortletDataContext.class, "getCompanyId");
		final Method getGroupIdMethod = ReflectionTestUtil.getMethod(
			PortletDataContext.class, "getGroupId");

		PortletDataContext portletDataContext =
			(PortletDataContext)ProxyUtil.newProxyInstance(
				PortletDataContext.class.getClassLoader(),
				new Class<?>[] {PortletDataContext.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
							Object proxy, Method method, Object[] args)
						throws PortalException {

						if (method.equals(getCompanyIdMethod)) {
							return TestPropsValues.getCompanyId();
						}

						if (method.equals(getGroupIdMethod)) {
							return exportGroup.getGroupId();
						}

						throw new UnsupportedOperationException();
					}

				});

		Element portletElement = SAXReaderUtil.createElement("portlet");

		PermissionExporter permissionExporter =
			PermissionExporter.getInstance();

		ReflectionTestUtil.invoke(
			permissionExporter, "exportPortletPermissions",
			new Class<?>[] {
				PortletDataContext.class, String.class, Layout.class,
				Element.class
			},
			portletDataContext, _PORTLET_ID, exportLayout, portletElement);

		return portletElement;
	}

	protected void importPortletPermissions(
			Group importGroup, Layout importLayout, Element portletElement)
		throws Exception {

		PermissionImporter permissionImporter =
			PermissionImporter.getInstance();

		ReflectionTestUtil.invoke(
			permissionImporter, "importPortletPermissions",
			new Class<?>[] {
				LayoutCache.class, long.class, long.class, long.class,
				Layout.class, Element.class, String.class
			},
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
			currentIndividualActions.toString(), _ACTION_IDS.length,
			currentIndividualActions.size());

		for (String action : currentIndividualActions) {
			boolean foundActionId = false;

			for (String actionId : _ACTION_IDS) {
				if (action.equals(actionId)) {
					foundActionId = true;

					break;
				}
			}

			Assert.assertTrue("Unable to import permissions", foundActionId);
		}
	}

	private static final String[] _ACTION_IDS =
		{ActionKeys.ADD_TO_PAGE, ActionKeys.VIEW};

	private static final String _PORTLET_ID = PortletKeys.EXPORT_IMPORT;

}