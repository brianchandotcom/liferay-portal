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

package com.liferay.layout.page.template.headless.delivery.dto.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.headless.delivery.dto.v1_0.WidgetInstance;
import com.liferay.headless.delivery.dto.v1_0.WidgetPermission;
import com.liferay.layout.page.template.importer.PortletPreferencesPortletConfigurationImporter;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.portlet.GenericPortlet;
import javax.portlet.Portlet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class WidgetInstanceDefinitionDTOConverterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		_bundleContext = bundle.getBundleContext();

		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		_testPortletName = "TEST_PORTLET_" + RandomTestUtil.randomString();
	}

	@After
	public void tearDown() {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();

		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testToWidgetInstanceDefinition() throws Exception {
		_registerTestPortlet(_testPortletName);

		String namespace = StringUtil.randomId();

		String instanceId = StringUtil.randomId();

		JSONObject editableValueJSONObject =
			_fragmentEntryProcessorRegistry.getDefaultEditableValuesJSONObject(
				StringPool.BLANK, StringPool.BLANK);

		editableValueJSONObject.put(
			"instanceId", instanceId
		).put(
			"portletId", _testPortletName
		);

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		long defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
				0, 0, defaultSegmentsExperienceId, layout.getPlid(),
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, editableValueJSONObject.toString(), namespace,
				0, null, _serviceContext);

		String testPortletId = PortletIdCodec.encode(
			_testPortletName, instanceId);

		String configProperty1Value = RandomTestUtil.randomString();
		String configProperty2Value = RandomTestUtil.randomString();

		_portletPreferencesPortletConfigurationImporter.
			importPortletConfiguration(
				layout.getPlid(), testPortletId,
				HashMapBuilder.<String, Object>put(
					"config-property-1", configProperty1Value
				).put(
					"config-property-2", configProperty2Value
				).build());

		ResourceAction resourceAction =
			_resourceActionLocalService.addResourceAction(
				TestPortlet.class.getName(), "VIEW",
				RandomTestUtil.randomLong());

		Role role = _roleLocalService.getDefaultGroupRole(_group.getGroupId());

		Map<Long, String[]> roleIdsToActionIds = HashMapBuilder.put(
			role.getRoleId(),
			() -> {
				List<String> actionIds = new ArrayList<>();

				actionIds.add(resourceAction.getActionId());

				return actionIds.toArray(new String[0]);
			}
		).build();

		String resourcePrimKey = _portletPermission.getPrimaryKey(
			layout.getPlid(), testPortletId);

		_resourcePermissionService.setIndividualResourcePermissions(
			layout.getGroupId(), layout.getCompanyId(), _testPortletName,
			resourcePrimKey, roleIdsToActionIds);

		WidgetInstance widgetInstance = ReflectionTestUtil.invoke(
			_getService(), "getWidgetInstance",
			new Class<?>[] {FragmentEntryLink.class, String.class},
			fragmentEntryLink, testPortletId);

		_layoutLocalService.deleteLayout(layout.getPlid());
		_resourceActionLocalService.deleteResourceAction(
			resourceAction.getResourceActionId());

		Assert.assertNotNull(widgetInstance);

		Map<String, Object> widgetConfig = widgetInstance.getWidgetConfig();

		Assert.assertEquals(
			configProperty1Value, widgetConfig.get("config-property-1"));
		Assert.assertEquals(
			configProperty2Value, widgetConfig.get("config-property-2"));

		Assert.assertEquals(instanceId, widgetInstance.getWidgetInstanceId());

		Assert.assertEquals(_testPortletName, widgetInstance.getWidgetName());

		WidgetPermission[] widgetPermissions =
			widgetInstance.getWidgetPermissions();

		Assert.assertEquals(
			Arrays.toString(widgetPermissions), 1, widgetPermissions.length);

		WidgetPermission widgetPermission = widgetPermissions[0];

		Assert.assertEquals(role.getName(), widgetPermission.getRoleKey());

		String[] actionKeys = widgetPermission.getActionKeys();

		Assert.assertEquals(Arrays.toString(actionKeys), 1, actionKeys.length);

		Assert.assertEquals("VIEW", actionKeys[0]);
	}

	private Object _getService() {
		ServiceReference<?> serviceReference =
			_bundleContext.getServiceReference(
				"com.liferay.headless.delivery.internal.dto.v1_0.mapper." +
					"WidgetInstanceMapper");

		return _bundleContext.getService(serviceReference);
	}

	private void _registerTestPortlet(String portletId) throws Exception {
		_serviceRegistrations.add(
			_bundleContext.registerService(
				Portlet.class, new TestPortlet(),
				HashMapDictionaryBuilder.put(
					"com.liferay.portlet.instanceable", "true"
				).put(
					"javax.portlet.name", portletId
				).build()));
	}

	private BundleContext _bundleContext;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private PortletPermission _portletPermission;

	@Inject
	private PortletPreferencesPortletConfigurationImporter
		_portletPreferencesPortletConfigurationImporter;

	@Inject
	private ResourceActionLocalService _resourceActionLocalService;

	@Inject
	private ResourcePermissionService _resourcePermissionService;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private ServiceContext _serviceContext;
	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new CopyOnWriteArrayList<>();
	private String _testPortletName;

	private class TestPortlet extends GenericPortlet {
	}

}