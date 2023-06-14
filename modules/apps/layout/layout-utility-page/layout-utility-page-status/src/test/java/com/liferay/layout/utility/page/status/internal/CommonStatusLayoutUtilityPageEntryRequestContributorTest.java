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

package com.liferay.layout.utility.page.status.internal;

import com.liferay.layout.utility.page.status.internal.request.contributor.CommonStatusLayoutUtilityPageEntryRequestContributor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PropsImpl;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class CommonStatusLayoutUtilityPageEntryRequestContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_originalProps = PropsUtil.getProps();

		Props props = Mockito.mock(PropsImpl.class);

		Mockito.when(
			props.get("feature.flag.LPS-165914")
		).thenReturn(
			"true"
		);

		PropsUtil.setProps(props);
	}

	@AfterClass
	public static void tearDownClass() {
		PropsUtil.setProps(_originalProps);
	}

	@Before
	public void setUp() {
		_commonStatusLayoutUtilityPageEntryRequestContributor =
			new CommonStatusLayoutUtilityPageEntryRequestContributor();
	}

	@Test
	public void testAddParametersWithDefaultVirtualHostAndWithoutCurrentURL() {
		VirtualHost virtualHost = _getMockVirtualHost(
			0, RandomTestUtil.randomString());

		_mockPortal(null, virtualHost.getHostname());

		_mockVirtualHostLocalService(virtualHost);

		_assertAttributesAndParameters(null, null, null);
	}

	@Test
	public void testAddParametersWithoutVirtualHostAndWithoutCurrentURL() {
		_mockPortal(null, null);

		_mockVirtualHostLocalService(null);

		_assertAttributesAndParameters(null, null, null);
	}

	@Test
	public void testAddParametersWithVirtualHostWithoutLayoutsAndWithoutCurrentURL()
		throws PortalException {

		VirtualHost virtualHost = _getMockVirtualHostWithoutLayouts();

		_mockPortal(null, virtualHost.getHostname());

		_assertAttributesAndParameters(null, null, null);
	}

	@Test
	public void testAddParametersWithVirtualHostWithPrivateLayoutAndWithoutCurrentURL()
		throws PortalException {

		long groupId = RandomTestUtil.randomLong();

		Layout layout = _getMockLayout();

		VirtualHost virtualHost = _getMockVirtualHostWithLayouts(
			groupId, null, layout);

		_mockPortal(null, virtualHost.getHostname());

		_assertAttributesAndParameters(
			null, String.valueOf(groupId),
			String.valueOf(layout.getLayoutId()));
	}

	@Test
	public void testAddParametersWithVirtualHostWithPublicLayoutAndWithoutCurrentURL()
		throws PortalException {

		long groupId = RandomTestUtil.randomLong();

		Layout layout = _getMockLayout();

		VirtualHost virtualHost = _getMockVirtualHostWithLayouts(
			groupId, layout, null);

		_mockPortal(null, virtualHost.getHostname());

		_assertAttributesAndParameters(
			null, String.valueOf(groupId),
			String.valueOf(layout.getLayoutId()));
	}

	private void _assertAttributesAndParameters(
		String languageId, String groupId, String layoutId) {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		DynamicServletRequest dynamicServletRequest = new DynamicServletRequest(
			mockHttpServletRequest);

		_commonStatusLayoutUtilityPageEntryRequestContributor.
			addAttributesAndParameters(dynamicServletRequest);

		Assert.assertEquals(
			groupId, dynamicServletRequest.getParameter("groupId"));
		Assert.assertEquals(
			layoutId, dynamicServletRequest.getParameter("layoutId"));
		Assert.assertEquals(
			languageId,
			dynamicServletRequest.getAttribute(WebKeys.I18N_LANGUAGE_ID));
	}

	private Group _getMockGroup(long groupId) {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getGroupId()
		).thenReturn(
			groupId
		);

		return group;
	}

	private Layout _getMockLayout() {
		Layout layout = Mockito.mock(Layout.class);

		Mockito.when(
			layout.getLayoutId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		return layout;
	}

	private LayoutSet _getMockLayoutSet(Group group) throws PortalException {
		LayoutSet layoutSet = Mockito.mock(LayoutSet.class);

		Mockito.when(
			layoutSet.getGroup()
		).thenReturn(
			group
		);

		return layoutSet;
	}

	private VirtualHost _getMockVirtualHost(long layoutSetId, String name) {
		VirtualHost virtualHost = Mockito.mock(VirtualHost.class);

		Mockito.when(
			virtualHost.getHostname()
		).thenReturn(
			name
		);

		Mockito.when(
			virtualHost.getLayoutSetId()
		).thenReturn(
			layoutSetId
		);

		return virtualHost;
	}

	private VirtualHost _getMockVirtualHostWithLayouts(
			long groupId, Layout privateLayout, Layout publicLayout)
		throws PortalException {

		VirtualHost virtualHost = _getMockVirtualHost(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString());

		Group group = _getMockGroup(groupId);

		LayoutSet layoutSet = _getMockLayoutSet(group);

		_mockLayoutLocalService(group, publicLayout, privateLayout);

		_mockLayoutSetLocalService(layoutSet, virtualHost);

		_mockVirtualHostLocalService(virtualHost);

		return virtualHost;
	}

	private VirtualHost _getMockVirtualHostWithoutLayouts()
		throws PortalException {

		Group group = _getMockGroup(RandomTestUtil.randomLong());

		LayoutSet layoutSet = _getMockLayoutSet(group);

		_mockLayoutLocalService(group, null, null);

		VirtualHost virtualHost = _getMockVirtualHost(
			0, RandomTestUtil.randomString());

		_mockLayoutSetLocalService(layoutSet, virtualHost);

		_mockVirtualHostLocalService(virtualHost);

		return virtualHost;
	}

	private void _mockLayoutLocalService(
		Group group, Layout privateLayout, Layout publicLayout) {

		LayoutLocalService layoutLocalService = Mockito.mock(
			LayoutLocalService.class);

		Mockito.when(
			layoutLocalService.fetchFirstLayout(
				group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID)
		).thenReturn(
			publicLayout
		);

		Mockito.when(
			layoutLocalService.fetchFirstLayout(
				group.getGroupId(), true,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID)
		).thenReturn(
			privateLayout
		);

		ReflectionTestUtil.setFieldValue(
			_commonStatusLayoutUtilityPageEntryRequestContributor,
			"_layoutLocalService", layoutLocalService);
	}

	private void _mockLayoutSetLocalService(
			LayoutSet layoutSet, VirtualHost virtualHost)
		throws PortalException {

		LayoutSetLocalService layoutSetLocalService = Mockito.mock(
			LayoutSetLocalService.class);

		Mockito.when(
			layoutSetLocalService.getLayoutSet(virtualHost.getLayoutSetId())
		).thenReturn(
			layoutSet
		);

		ReflectionTestUtil.setFieldValue(
			_commonStatusLayoutUtilityPageEntryRequestContributor,
			"_layoutSetLocalService", layoutSetLocalService);
	}

	private void _mockPortal(String currentURL, String host) {
		Portal portal = Mockito.mock(Portal.class);

		Mockito.when(
			portal.getHost(Mockito.any(DynamicServletRequest.class))
		).thenReturn(
			host
		);

		Mockito.when(
			portal.getCurrentURL(Mockito.any(DynamicServletRequest.class))
		).thenReturn(
			currentURL
		);

		ReflectionTestUtil.setFieldValue(
			_commonStatusLayoutUtilityPageEntryRequestContributor, "_portal",
			portal);
	}

	private void _mockVirtualHostLocalService(VirtualHost virtualHost) {
		VirtualHostLocalService virtualHostLocalService = Mockito.mock(
			VirtualHostLocalService.class);

		Mockito.when(
			virtualHostLocalService.fetchVirtualHost(Mockito.anyString())
		).thenReturn(
			virtualHost
		);

		ReflectionTestUtil.setFieldValue(
			_commonStatusLayoutUtilityPageEntryRequestContributor,
			"_virtualHostLocalService", virtualHostLocalService);
	}

	private static Props _originalProps;

	private CommonStatusLayoutUtilityPageEntryRequestContributor
		_commonStatusLayoutUtilityPageEntryRequestContributor;

}