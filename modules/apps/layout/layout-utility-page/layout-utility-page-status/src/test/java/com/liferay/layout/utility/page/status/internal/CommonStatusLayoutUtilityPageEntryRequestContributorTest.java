/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.status.internal;

import com.liferay.layout.utility.page.status.internal.request.contributor.CommonStatusLayoutUtilityPageEntryRequestContributor;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.servlet.I18nServlet;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.Collections;
import java.util.List;

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
		_layoutPermissionUtilMockedStatic.close();
	}

	@Before
	public void setUp() {
		_setUpCommonStatusLayoutUtilityPageEntryRequestContributor();
		_setUpPermissionCheckerFactory();
	}

	@Test
	public void testAddParametersWithDefaultVirtualHostAndWithoutCurrentURL()
		throws PortalException {

		VirtualHost virtualHost = _mockVirtualHost(
			RandomTestUtil.randomLong(), 0, RandomTestUtil.randomString());

		_mockPortal(null, virtualHost.getHostname(), null);
		_mockVirtualHostLocalService(virtualHost);

		_assertAttributesAndParameters(
			_getDynamicServletRequest(RandomTestUtil.randomString()), null,
			null, null);
	}

	@Test
	public void testAddParametersWithoutVirtualHostAndWithoutCurrentURL()
		throws PortalException {

		_mockPortal(null, null, null);
		_mockVirtualHostLocalService(null);

		_assertAttributesAndParameters(
			_getDynamicServletRequest(RandomTestUtil.randomString()), null,
			null, null);
	}

	@Test
	public void testAddParametersWithVirtualHostAndWithContextPath()
		throws PortalException {

		Layout layout = _mockLayout(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong());

		VirtualHost virtualHost = _mockVirtualHost(
			layout.getCompanyId(), layout.getGroupId(),
			ListUtil.fromArray(layout), Collections.emptyList());

		_mockPortal(
			null, virtualHost.getHostname(), RandomTestUtil.randomString());

		_assertAttributesAndParameters(
			_getDynamicServletRequest(RandomTestUtil.randomString()),
			String.valueOf(layout.getGroupId()), null,
			String.valueOf(layout.getLayoutId()));
	}

	@Test
	public void testAddParametersWithVirtualHostAndWithCurrentURLWithoutValidGroup()
		throws PortalException {

		String languageId = LocaleUtil.toLanguageId(LocaleUtil.getDefault());

		String currentURL = StringBundler.concat(
			_PATH_PROXY, _PATH_CONTEXT, StringPool.SLASH, languageId,
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
			StringPool.SLASH, RandomTestUtil.randomString(), "/test/test");

		Layout layout = _mockLayout(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong());

		VirtualHost virtualHost = _mockVirtualHost(
			layout.getCompanyId(), layout.getGroupId(),
			ListUtil.fromArray(layout), Collections.emptyList());

		String groupFriendlyURL =
			StringPool.SLASH + RandomTestUtil.randomString();

		_mockGroupLocalService(
			virtualHost.getCompanyId(), null, groupFriendlyURL);

		_mockPortal(currentURL, virtualHost.getHostname(), _PATH_PROXY);

		_assertAttributesAndParameters(
			_getDynamicServletRequest(_PATH_CONTEXT),
			String.valueOf(layout.getGroupId()), languageId,
			String.valueOf(layout.getLayoutId()));
	}

	@Test
	public void testAddParametersWithVirtualHostAndWithCurrentURLWithValidGroupWithLayouts()
		throws PortalException {

		long companyId = RandomTestUtil.randomLong();
		long groupId = RandomTestUtil.randomLong();
		String groupFriendlyURL =
			StringPool.SLASH + RandomTestUtil.randomString();
		String languageId = LocaleUtil.toLanguageId(LocaleUtil.getDefault());

		Layout layout = _mockLayout(companyId, groupId);
		Layout virtualHostGroupLayout = _mockLayout(
			companyId, RandomTestUtil.randomLong());

		String currentURL = StringBundler.concat(
			_PATH_PROXY, _PATH_CONTEXT, StringPool.SLASH, languageId,
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
			groupFriendlyURL, "/test/test");

		Group group = _mockGroup(companyId, groupId, groupFriendlyURL);
		VirtualHost virtualHost = _mockVirtualHost(
			companyId, virtualHostGroupLayout.getGroupId(),
			ListUtil.fromArray(virtualHostGroupLayout),
			Collections.emptyList());

		_mockGroupLocalService(companyId, group, groupFriendlyURL);

		_mockLayoutLocalService(
			groupId, ListUtil.fromArray(layout), Collections.emptyList());
		_mockPortal(currentURL, virtualHost.getHostname(), _PATH_PROXY);

		_assertAttributesAndParameters(
			_getDynamicServletRequest(_PATH_CONTEXT),
			String.valueOf(group.getGroupId()), languageId,
			String.valueOf(layout.getLayoutId()));
	}

	@Test
	public void testAddParametersWithVirtualHostAndWithCurrentURLWithValidGroupWithLayoutsWithoutViewPermission()
		throws PortalException {

		String languageId = LocaleUtil.toLanguageId(LocaleUtil.getDefault());

		String groupFriendlyURL =
			StringPool.SLASH + RandomTestUtil.randomString();

		String currentURL = StringBundler.concat(
			_PATH_PROXY, _PATH_CONTEXT, StringPool.SLASH, languageId,
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
			groupFriendlyURL, "/test/test");

		Layout virtualHostGroupLayout = _mockLayout(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong());

		VirtualHost virtualHost = _mockVirtualHost(
			virtualHostGroupLayout.getCompanyId(),
			virtualHostGroupLayout.getGroupId(),
			ListUtil.fromArray(virtualHostGroupLayout),
			Collections.emptyList());

		Group group = _mockGroup(
			virtualHostGroupLayout.getCompanyId(), RandomTestUtil.randomLong(),
			groupFriendlyURL);

		_mockGroupLocalService(
			virtualHost.getCompanyId(), group, groupFriendlyURL);

		Layout groupLayout = _mockLayout(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(), false);

		_mockLayoutLocalService(
			group.getGroupId(), ListUtil.fromArray(groupLayout),
			Collections.emptyList());

		_mockPortal(currentURL, virtualHost.getHostname(), _PATH_PROXY);

		_assertAttributesAndParameters(
			_getDynamicServletRequest(_PATH_CONTEXT),
			String.valueOf(virtualHostGroupLayout.getGroupId()), languageId,
			String.valueOf(virtualHostGroupLayout.getLayoutId()));
	}

	@Test
	public void testAddParametersWithVirtualHostAndWithCurrentURLWithValidGroupWithoutLayouts()
		throws PortalException {

		String languageId = LocaleUtil.toLanguageId(LocaleUtil.getDefault());

		String groupFriendlyURL =
			StringPool.SLASH + RandomTestUtil.randomString();

		String currentURL = StringBundler.concat(
			_PATH_PROXY, _PATH_CONTEXT, StringPool.SLASH, languageId,
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
			groupFriendlyURL, "/test/test");

		Layout layout = _mockLayout(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong());

		VirtualHost virtualHost = _mockVirtualHost(
			layout.getCompanyId(), layout.getGroupId(),
			ListUtil.fromArray(layout), Collections.emptyList());

		Group group = _mockGroup(
			layout.getCompanyId(), RandomTestUtil.randomLong(),
			groupFriendlyURL);

		_mockGroupLocalService(
			virtualHost.getCompanyId(), group, groupFriendlyURL);

		_mockPortal(currentURL, virtualHost.getHostname(), _PATH_PROXY);

		_assertAttributesAndParameters(
			_getDynamicServletRequest(_PATH_CONTEXT),
			String.valueOf(layout.getGroupId()), languageId,
			String.valueOf(layout.getLayoutId()));
	}

	@Test
	public void testAddParametersWithVirtualHostAndWithInvalidCurrentURL()
		throws PortalException {

		String languageId = LocaleUtil.toLanguageId(LocaleUtil.getDefault());

		String currentURL = StringBundler.concat(
			_PATH_PROXY, _PATH_CONTEXT, StringPool.SLASH, languageId,
			StringPool.SLASH + RandomTestUtil.randomString());

		Layout layout = _mockLayout(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong());

		VirtualHost virtualHost = _mockVirtualHost(
			layout.getCompanyId(), layout.getGroupId(),
			ListUtil.fromArray(layout), Collections.emptyList());

		_mockPortal(currentURL, virtualHost.getHostname(), _PATH_PROXY);

		_assertAttributesAndParameters(
			_getDynamicServletRequest(_PATH_CONTEXT),
			String.valueOf(layout.getGroupId()), languageId,
			String.valueOf(layout.getLayoutId()));
	}

	@Test
	public void testAddParametersWithVirtualHostAndWithLanguageId()
		throws PortalException {

		String languageId = LocaleUtil.toLanguageId(LocaleUtil.getDefault());

		String currentURL = StringBundler.concat(
			_PATH_PROXY, _PATH_CONTEXT, StringPool.SLASH, languageId,
			StringPool.SLASH);

		Layout layout = _mockLayout(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong());

		VirtualHost virtualHost = _mockVirtualHost(
			layout.getCompanyId(), layout.getGroupId(),
			ListUtil.fromArray(layout), Collections.emptyList());

		_mockPortal(currentURL, virtualHost.getHostname(), _PATH_PROXY);

		_assertAttributesAndParameters(
			_getDynamicServletRequest(_PATH_CONTEXT),
			String.valueOf(layout.getGroupId()), languageId,
			String.valueOf(layout.getLayoutId()));
	}

	@Test
	public void testAddParametersWithVirtualHostWithoutLayoutsAndWithCurrentURLWithValidGroupWithoutLayouts()
		throws PortalException {

		String languageId = LocaleUtil.toLanguageId(LocaleUtil.getDefault());

		String groupFriendlyURL =
			StringPool.SLASH + RandomTestUtil.randomString();

		String currentURL = StringBundler.concat(
			_PATH_PROXY, _PATH_CONTEXT, StringPool.SLASH, languageId,
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
			groupFriendlyURL, "/test/test");

		VirtualHost virtualHost = _mockVirtualHost(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			Collections.emptyList(), Collections.emptyList());

		Group group = _mockGroup(
			virtualHost.getCompanyId(), RandomTestUtil.randomLong(),
			groupFriendlyURL);

		_mockGroupLocalService(
			virtualHost.getCompanyId(), group, groupFriendlyURL);

		_mockPortal(currentURL, virtualHost.getHostname(), _PATH_PROXY);

		_assertAttributesAndParameters(
			_getDynamicServletRequest(_PATH_CONTEXT), null, null, null);
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

		Layout layout = _mockLayout(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong());

		VirtualHost virtualHost = _mockVirtualHost(
			layout.getCompanyId(), layout.getGroupId(),
			ListUtil.fromArray(layout), Collections.emptyList());

		_mockPortal(
			null, virtualHost.getHostname(), RandomTestUtil.randomString());

		_assertAttributesAndParameters(
			_getDynamicServletRequest(null),
			String.valueOf(layout.getGroupId()), null,
			String.valueOf(layout.getLayoutId()));
	}

	@Test
	public void testAddParametersWithVirtualHostWithPrivateLayoutAndWithoutCurrentURL()
		throws PortalException {

		Layout layout = _mockLayout(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong());

		VirtualHost virtualHost = _mockVirtualHost(
			layout.getCompanyId(), layout.getGroupId(), Collections.emptyList(),
			ListUtil.fromArray(layout));

		_mockPortal(null, virtualHost.getHostname(), null);

		_assertAttributesAndParameters(
			_getDynamicServletRequest(RandomTestUtil.randomString()),
			String.valueOf(layout.getGroupId()), null,
			String.valueOf(layout.getLayoutId()));
	}

	@Test
	public void testAddParametersWithVirtualHostWithPublicLayoutAndWithoutCurrentURL()
		throws PortalException {

		Layout layout = _mockLayout(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong());

		VirtualHost virtualHost = _mockVirtualHost(
			layout.getCompanyId(), layout.getGroupId(),
			ListUtil.fromArray(layout), Collections.emptyList());

		_mockPortal(null, virtualHost.getHostname(), null);

		_assertAttributesAndParameters(
			_getDynamicServletRequest(RandomTestUtil.randomString()),
			String.valueOf(layout.getGroupId()), null,
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

	private Group _mockGroup(long companyId, long groupId, String friendlyURL) {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getCompanyId()
		).thenReturn(
			companyId
		);

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
		long companyId, Group group, String groupFriendlyURL) {

		Mockito.when(
			_groupLocalService.fetchFriendlyURLGroup(
				companyId, groupFriendlyURL)
		).thenReturn(
			group
		);
	}

	private Layout _mockLayout(long companyId, long groupId)
		throws PortalException {

		return _mockLayout(companyId, groupId, true);
	}

	private Layout _mockLayout(
			long companyId, long groupId, boolean viewPermission)
		throws PortalException {

		Layout layout = Mockito.mock(Layout.class);

		Mockito.when(
			layout.getCompanyId()
		).thenReturn(
			companyId
		);

		Mockito.when(
			layout.getGroupId()
		).thenReturn(
			groupId
		);

		Mockito.when(
			layout.getLayoutId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		_layoutPermissionUtilMockedStatic.when(
			() -> LayoutPermissionUtil.contains(
				Mockito.any(PermissionChecker.class), Mockito.eq(layout),
				Mockito.anyString())
		).thenReturn(
			viewPermission
		);

		return layout;
	}

	private void _mockLayoutLocalService(
		long groupId, List<Layout> privateLayouts, List<Layout> publicLayouts) {

		Mockito.when(
			_layoutLocalService.getLayouts(groupId, false)
		).thenReturn(
			publicLayouts
		);

		Mockito.when(
			_layoutLocalService.getLayouts(groupId, true)
		).thenReturn(
			privateLayouts
		);
	}

	private LayoutSet _mockLayoutSet(Group group) throws PortalException {
		LayoutSet layoutSet = Mockito.mock(LayoutSet.class);

		long companyId = group.getCompanyId();

		Mockito.when(
			layoutSet.getCompanyId()
		).thenReturn(
			companyId
		);

		long groupId = group.getGroupId();

		Mockito.when(
			layoutSet.getGroupId()
		).thenReturn(
			groupId
		);

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

	private void _mockPortal(String currentURL, String host, String pathProxy)
		throws PortalException {

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

		User user = Mockito.mock(User.class);

		Mockito.when(
			_portal.getUser(Mockito.any(DynamicServletRequest.class))
		).thenReturn(
			user
		);
	}

	private VirtualHost _mockVirtualHost() throws PortalException {
		VirtualHost virtualHost = _mockVirtualHost(
			RandomTestUtil.randomLong(), 0, RandomTestUtil.randomString());

		Group group = _mockGroup(
			virtualHost.getCompanyId(), RandomTestUtil.randomLong(), null);

		_mockLayoutLocalService(group.getGroupId(), null, null);
		_mockLayoutSetLocalService(_mockLayoutSet(group), virtualHost);

		_mockVirtualHostLocalService(virtualHost);

		return virtualHost;
	}

	private VirtualHost _mockVirtualHost(
			long companyId, long groupId, List<Layout> privateLayouts,
			List<Layout> publicLayouts)
		throws PortalException {

		VirtualHost virtualHost = _mockVirtualHost(
			companyId, RandomTestUtil.randomLong(),
			RandomTestUtil.randomString());

		Group group = _mockGroup(companyId, groupId, null);

		_mockLayoutLocalService(groupId, publicLayouts, privateLayouts);
		_mockLayoutSetLocalService(_mockLayoutSet(group), virtualHost);

		_mockVirtualHostLocalService(virtualHost);

		return virtualHost;
	}

	private VirtualHost _mockVirtualHost(
		long companyId, long layoutSetId, String name) {

		VirtualHost virtualHost = Mockito.mock(VirtualHost.class);

		Mockito.when(
			virtualHost.getCompanyId()
		).thenReturn(
			companyId
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

		_permissionCheckerFactory = Mockito.mock(
			PermissionCheckerFactory.class);

		ReflectionTestUtil.setFieldValue(
			_commonStatusLayoutUtilityPageEntryRequestContributor,
			"_permissionCheckerFactory", _permissionCheckerFactory);

		_portal = Mockito.mock(Portal.class);

		ReflectionTestUtil.setFieldValue(
			_commonStatusLayoutUtilityPageEntryRequestContributor, "_portal",
			_portal);

		_virtualHostLocalService = Mockito.mock(VirtualHostLocalService.class);

		ReflectionTestUtil.setFieldValue(
			_commonStatusLayoutUtilityPageEntryRequestContributor,
			"_virtualHostLocalService", _virtualHostLocalService);

		_userLocalService = Mockito.mock(UserLocalService.class);

		ReflectionTestUtil.setFieldValue(
			_commonStatusLayoutUtilityPageEntryRequestContributor,
			"_userLocalService", _userLocalService);
	}

	private void _setUpPermissionCheckerFactory() {
		PermissionChecker permissionChecker = Mockito.mock(
			PermissionChecker.class);

		Mockito.when(
			_permissionCheckerFactory.create(Mockito.any(User.class))
		).thenReturn(
			permissionChecker
		);
	}

	private static final String _PATH_CONTEXT = "/context";

	private static final String _PATH_PROXY = "/proxy";

	private static final MockedStatic<I18nServlet> _i18nServletMockedStatic =
		Mockito.mockStatic(I18nServlet.class);
	private static final MockedStatic<LayoutPermissionUtil>
		_layoutPermissionUtilMockedStatic = Mockito.mockStatic(
			LayoutPermissionUtil.class);

	private CommonStatusLayoutUtilityPageEntryRequestContributor
		_commonStatusLayoutUtilityPageEntryRequestContributor;
	private GroupLocalService _groupLocalService;
	private LayoutLocalService _layoutLocalService;
	private LayoutSetLocalService _layoutSetLocalService;
	private PermissionCheckerFactory _permissionCheckerFactory;
	private Portal _portal;
	private UserLocalService _userLocalService;
	private VirtualHostLocalService _virtualHostLocalService;

}