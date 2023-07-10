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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.servlet.I18nServlet;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PropsValues;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
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
		_i18nServletMockedStatic.when(
			I18nServlet::getLanguageIds
		).thenReturn(
			SetUtil.fromString(
				StringPool.SLASH +
					LocaleUtil.toLanguageId(LocaleUtil.getDefault()))
		);
	}

	@AfterClass
	public static void tearDownClass() {
		_i18nServletMockedStatic.close();
	}

	@Before
	public void setUp() {
		_setUpCommonStatusLayoutUtilityPageEntryRequestContributor();
	}

	@Test
	public void testAddParametersWithDefaultVirtualHostAndWithoutCurrentURL() {
		VirtualHost virtualHost = _mockVirtualHost(
			0, RandomTestUtil.randomString());

		_mockPortal(null, virtualHost.getHostname(), null);
		_mockVirtualHostLocalService(virtualHost);

		_assertAttributesAndParameters(
			_getDynamicServletRequest(RandomTestUtil.randomString()), null,
			null, null);
	}

	@Test
	public void testAddParametersWithoutVirtualHostAndWithoutCurrentURL() {
		_mockPortal(null, null, null);
		_mockVirtualHostLocalService(null);

		_assertAttributesAndParameters(
			_getDynamicServletRequest(RandomTestUtil.randomString()), null,
			null, null);
	}

	@Test
	public void testAddParametersWithVirtualHostAndWithContextPath()
		throws PortalException {

		long groupId = RandomTestUtil.randomLong();
		Layout layout = _mockLayout();

		VirtualHost virtualHost = _mockVirtualHost(groupId, layout, null);

		_mockPortal(
			null, virtualHost.getHostname(), RandomTestUtil.randomString());

		_assertAttributesAndParameters(
			_getDynamicServletRequest(RandomTestUtil.randomString()),
			String.valueOf(groupId), null,
			String.valueOf(layout.getLayoutId()));
	}

	@Test
	public void testAddParametersWithVirtualHostAndWithCurrentURLWithoutValidGroup()
		throws PortalException {

		long groupId = RandomTestUtil.randomLong();
		String groupFriendlyURL =
			StringPool.SLASH + RandomTestUtil.randomString();
		String languageId = LocaleUtil.toLanguageId(LocaleUtil.getDefault());
		Layout layout = _mockLayout();

		String currentURL = StringBundler.concat(
			_PATH_PROXY, _PATH_CONTEXT, StringPool.SLASH, languageId,
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
			StringPool.SLASH, RandomTestUtil.randomString(), "/test/test");

		VirtualHost virtualHost = _mockVirtualHost(groupId, layout, null);

		_mockGroupLocalService(
			virtualHost.getCompanyId(), groupFriendlyURL, null);
		_mockPortal(currentURL, virtualHost.getHostname(), _PATH_PROXY);

		_assertAttributesAndParameters(
			_getDynamicServletRequest(_PATH_CONTEXT), String.valueOf(groupId),
			languageId, String.valueOf(layout.getLayoutId()));
	}

	@Test
	public void testAddParametersWithVirtualHostAndWithCurrentURLWithValidGroupWithLayouts()
		throws PortalException {

		long groupId = RandomTestUtil.randomLong();
		String groupFriendlyURL =
			StringPool.SLASH + RandomTestUtil.randomString();
		String languageId = LocaleUtil.toLanguageId(LocaleUtil.getDefault());
		Layout layout = _mockLayout();
		Layout virtualHostGroupLayout = _mockLayout();

		String currentURL = StringBundler.concat(
			_PATH_PROXY, _PATH_CONTEXT, StringPool.SLASH, languageId,
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
			groupFriendlyURL, "/test/test");

		Group group = _mockGroup(RandomTestUtil.randomLong(), groupFriendlyURL);
		VirtualHost virtualHost = _mockVirtualHost(
			groupId, virtualHostGroupLayout, null);

		_mockGroupLocalService(
			virtualHost.getCompanyId(), groupFriendlyURL, group);

		_mockLayoutLocalService(group, layout, null);
		_mockPortal(currentURL, virtualHost.getHostname(), _PATH_PROXY);

		_assertAttributesAndParameters(
			_getDynamicServletRequest(_PATH_CONTEXT),
			String.valueOf(group.getGroupId()), languageId,
			String.valueOf(layout.getLayoutId()));
	}

	@Test
	public void testAddParametersWithVirtualHostAndWithCurrentURLWithValidGroupWithoutLayouts()
		throws PortalException {

		long groupId = RandomTestUtil.randomLong();
		String groupFriendlyURL =
			StringPool.SLASH + RandomTestUtil.randomString();
		String languageId = LocaleUtil.toLanguageId(LocaleUtil.getDefault());
		Layout layout = _mockLayout();

		String currentURL = StringBundler.concat(
			_PATH_PROXY, _PATH_CONTEXT, StringPool.SLASH, languageId,
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
			groupFriendlyURL, "/test/test");

		Group group = _mockGroup(RandomTestUtil.randomLong(), groupFriendlyURL);
		VirtualHost virtualHost = _mockVirtualHost(groupId, layout, null);

		_mockGroupLocalService(
			virtualHost.getCompanyId(), groupFriendlyURL, group);
		_mockPortal(currentURL, virtualHost.getHostname(), _PATH_PROXY);

		_assertAttributesAndParameters(
			_getDynamicServletRequest(_PATH_CONTEXT), null, null, null);
	}

	@Test
	public void testAddParametersWithVirtualHostAndWithInvalidCurrentURL()
		throws PortalException {

		long groupId = RandomTestUtil.randomLong();
		String languageId = LocaleUtil.toLanguageId(LocaleUtil.getDefault());
		Layout layout = _mockLayout();

		String currentURL = StringBundler.concat(
			_PATH_PROXY, _PATH_CONTEXT, StringPool.SLASH, languageId,
			StringPool.SLASH + RandomTestUtil.randomString());

		VirtualHost virtualHost = _mockVirtualHost(groupId, layout, null);

		_mockPortal(currentURL, virtualHost.getHostname(), _PATH_PROXY);

		_assertAttributesAndParameters(
			_getDynamicServletRequest(_PATH_CONTEXT), String.valueOf(groupId),
			languageId, String.valueOf(layout.getLayoutId()));
	}

	@Test
	public void testAddParametersWithVirtualHostAndWithLanguageId()
		throws PortalException {

		long groupId = RandomTestUtil.randomLong();
		String languageId = LocaleUtil.toLanguageId(LocaleUtil.getDefault());
		Layout layout = _mockLayout();

		String currentURL = StringBundler.concat(
			_PATH_PROXY, _PATH_CONTEXT, StringPool.SLASH, languageId,
			StringPool.SLASH);

		VirtualHost virtualHost = _mockVirtualHost(groupId, layout, null);

		_mockPortal(currentURL, virtualHost.getHostname(), _PATH_PROXY);

		_assertAttributesAndParameters(
			_getDynamicServletRequest(_PATH_CONTEXT), String.valueOf(groupId),
			languageId, String.valueOf(layout.getLayoutId()));
	}

	@Test
	public void testAddParametersWithVirtualHostWithoutLayoutsAndWithoutCurrentURL()
		throws PortalException {

		VirtualHost virtualHost = _mockVirtualHost();

		_mockPortal(null, virtualHost.getHostname(), null);

		_assertAttributesAndParameters(
			_getDynamicServletRequest(RandomTestUtil.randomString()), null,
			null, null);
	}

	@Test
	public void testAddParametersWithVirtualHostWithPathProxy()
		throws PortalException {

		long groupId = RandomTestUtil.randomLong();
		Layout layout = _mockLayout();

		VirtualHost virtualHost = _mockVirtualHost(groupId, layout, null);

		_mockPortal(
			null, virtualHost.getHostname(), RandomTestUtil.randomString());

		_assertAttributesAndParameters(
			_getDynamicServletRequest(null), String.valueOf(groupId), null,
			String.valueOf(layout.getLayoutId()));
	}

	@Test
	public void testAddParametersWithVirtualHostWithPrivateLayoutAndWithoutCurrentURL()
		throws PortalException {

		long groupId = RandomTestUtil.randomLong();
		Layout layout = _mockLayout();

		VirtualHost virtualHost = _mockVirtualHost(groupId, null, layout);

		_mockPortal(null, virtualHost.getHostname(), null);

		_assertAttributesAndParameters(
			_getDynamicServletRequest(RandomTestUtil.randomString()),
			String.valueOf(groupId), null,
			String.valueOf(layout.getLayoutId()));
	}

	@Test
	public void testAddParametersWithVirtualHostWithPublicLayoutAndWithoutCurrentURL()
		throws PortalException {

		long groupId = RandomTestUtil.randomLong();
		Layout layout = _mockLayout();

		VirtualHost virtualHost = _mockVirtualHost(groupId, layout, null);

		_mockPortal(null, virtualHost.getHostname(), null);

		_assertAttributesAndParameters(
			_getDynamicServletRequest(RandomTestUtil.randomString()),
			String.valueOf(groupId), null,
			String.valueOf(layout.getLayoutId()));
	}

	private void _assertAttributesAndParameters(
		DynamicServletRequest dynamicServletRequest, String groupId,
		String languageId, String layoutId) {

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

	private DynamicServletRequest _getDynamicServletRequest(
		String contextPath) {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setContextPath(contextPath);

		return new DynamicServletRequest(mockHttpServletRequest);
	}

	private Group _mockGroup(long groupId, String friendlyURL) {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getGroupId()
		).thenReturn(
			groupId
		);

		Mockito.when(
			group.getFriendlyURL()
		).thenReturn(
			friendlyURL
		);

		return group;
	}

	private void _mockGroupLocalService(
		long companyId, String groupFriendlyURL, Group group) {

		Mockito.when(
			_groupLocalService.fetchFriendlyURLGroup(
				companyId, groupFriendlyURL)
		).thenReturn(
			group
		);
	}

	private Layout _mockLayout() {
		Layout layout = Mockito.mock(Layout.class);

		Mockito.when(
			layout.getLayoutId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		return layout;
	}

	private void _mockLayoutLocalService(
		Group group, Layout privateLayout, Layout publicLayout) {

		Mockito.when(
			_layoutLocalService.fetchFirstLayout(
				group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID)
		).thenReturn(
			publicLayout
		);

		Mockito.when(
			_layoutLocalService.fetchFirstLayout(
				group.getGroupId(), true,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID)
		).thenReturn(
			privateLayout
		);
	}

	private LayoutSet _mockLayoutSet(Group group) throws PortalException {
		LayoutSet layoutSet = Mockito.mock(LayoutSet.class);

		Mockito.when(
			layoutSet.getGroup()
		).thenReturn(
			group
		);

		return layoutSet;
	}

	private void _mockLayoutSetLocalService(
			LayoutSet layoutSet, VirtualHost virtualHost)
		throws PortalException {

		Mockito.when(
			_layoutSetLocalService.getLayoutSet(virtualHost.getLayoutSetId())
		).thenReturn(
			layoutSet
		);
	}

	private void _mockPortal(String currentURL, String host, String pathProxy) {
		Mockito.when(
			_portal.getCurrentURL(Mockito.any(DynamicServletRequest.class))
		).thenReturn(
			currentURL
		);

		Mockito.when(
			_portal.getHost(Mockito.any(DynamicServletRequest.class))
		).thenReturn(
			host
		);

		Mockito.when(
			_portal.getPathProxy()
		).thenReturn(
			pathProxy
		);
	}

	private VirtualHost _mockVirtualHost() throws PortalException {
		VirtualHost virtualHost = _mockVirtualHost(
			0, RandomTestUtil.randomString());

		Group group = _mockGroup(RandomTestUtil.randomLong(), null);

		_mockLayoutLocalService(group, null, null);
		_mockLayoutSetLocalService(_mockLayoutSet(group), virtualHost);

		_mockVirtualHostLocalService(virtualHost);

		return virtualHost;
	}

	private VirtualHost _mockVirtualHost(
			long groupId, Layout privateLayout, Layout publicLayout)
		throws PortalException {

		VirtualHost virtualHost = _mockVirtualHost(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString());

		Group group = _mockGroup(groupId, null);

		_mockLayoutLocalService(group, publicLayout, privateLayout);
		_mockLayoutSetLocalService(_mockLayoutSet(group), virtualHost);

		_mockVirtualHostLocalService(virtualHost);

		return virtualHost;
	}

	private VirtualHost _mockVirtualHost(long layoutSetId, String name) {
		VirtualHost virtualHost = Mockito.mock(VirtualHost.class);

		Mockito.when(
			virtualHost.getCompanyId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

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

	private void _mockVirtualHostLocalService(VirtualHost virtualHost) {
		Mockito.when(
			_virtualHostLocalService.fetchVirtualHost(Mockito.anyString())
		).thenReturn(
			virtualHost
		);
	}

	private void _setUpCommonStatusLayoutUtilityPageEntryRequestContributor() {
		_commonStatusLayoutUtilityPageEntryRequestContributor =
			new CommonStatusLayoutUtilityPageEntryRequestContributor();

		_groupLocalService = Mockito.mock(GroupLocalService.class);

		ReflectionTestUtil.setFieldValue(
			_commonStatusLayoutUtilityPageEntryRequestContributor,
			"_groupLocalService", _groupLocalService);

		_layoutLocalService = Mockito.mock(LayoutLocalService.class);

		ReflectionTestUtil.setFieldValue(
			_commonStatusLayoutUtilityPageEntryRequestContributor,
			"_layoutLocalService", _layoutLocalService);

		_layoutSetLocalService = Mockito.mock(LayoutSetLocalService.class);

		ReflectionTestUtil.setFieldValue(
			_commonStatusLayoutUtilityPageEntryRequestContributor,
			"_layoutSetLocalService", _layoutSetLocalService);

		_portal = Mockito.mock(Portal.class);

		ReflectionTestUtil.setFieldValue(
			_commonStatusLayoutUtilityPageEntryRequestContributor, "_portal",
			_portal);

		_virtualHostLocalService = Mockito.mock(VirtualHostLocalService.class);

		ReflectionTestUtil.setFieldValue(
			_commonStatusLayoutUtilityPageEntryRequestContributor,
			"_virtualHostLocalService", _virtualHostLocalService);
	}

	private static final String _PATH_CONTEXT = "/context";

	private static final String _PATH_PROXY = "/proxy";

	private static final MockedStatic<I18nServlet> _i18nServletMockedStatic =
		Mockito.mockStatic(I18nServlet.class);

	private CommonStatusLayoutUtilityPageEntryRequestContributor
		_commonStatusLayoutUtilityPageEntryRequestContributor;
	private GroupLocalService _groupLocalService;
	private LayoutLocalService _layoutLocalService;
	private LayoutSetLocalService _layoutSetLocalService;
	private Portal _portal;
	private VirtualHostLocalService _virtualHostLocalService;

}