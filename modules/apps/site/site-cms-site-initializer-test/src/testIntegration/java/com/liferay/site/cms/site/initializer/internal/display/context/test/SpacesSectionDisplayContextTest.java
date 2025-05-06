/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.headless.asset.library.dto.v1_0.AssetLibrary;
import com.liferay.headless.asset.library.resource.v1_0.AssetLibraryResource;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Roberto Díaz
 */
@FeatureFlag("LPD-17564")
@RunWith(Arquillian.class)
public class SpacesSectionDisplayContextTest
	extends BaseSectionDisplayContextTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		super.setUp();

		AssetLibraryResource.Builder builder =
			_assetLibraryResourceFactory.create();

		_assetLibraryResource = builder.user(
			UserTestUtil.getAdminUser(group.getCompanyId())
		).build();
	}

	@Test
	public void testGetPage() throws Exception {
		Page<AssetLibrary> page = ReflectionTestUtil.invoke(
			_getSpacesSectionDisplayContext(getMockHttpServletRequest()),
			"getPage", new Class<?>[0]);

		Collection<AssetLibrary> originalItems = page.getItems();
		long originalTotalCount = page.getTotalCount();

		AssetLibrary assetLibrary1 = _addAssetLibrary();
		AssetLibrary assetLibrary2 = _addAssetLibrary();

		page = ReflectionTestUtil.invoke(
			_getSpacesSectionDisplayContext(getMockHttpServletRequest()),
			"getPage", new Class<?>[0]);

		Collection<AssetLibrary> items = page.getItems();

		Assert.assertEquals(
			items.toString(), originalItems.size() + 2, items.size());

		Assert.assertEquals(originalTotalCount + 2, page.getTotalCount());

		Assert.assertTrue(items.contains(assetLibrary1));
		Assert.assertTrue(items.contains(assetLibrary2));

		_addAssetLibrary();
		_addAssetLibrary();
		_addAssetLibrary();
		_addAssetLibrary();

		page = ReflectionTestUtil.invoke(
			_getSpacesSectionDisplayContext(getMockHttpServletRequest()),
			"getPage", new Class<?>[0]);

		items = page.getItems();

		Assert.assertEquals(items.toString(), 5, items.size());

		Assert.assertEquals(originalTotalCount + 6, page.getTotalCount());

		AssetLibrary assetLibraryPin1 = _addAssetLibraryPin();
		AssetLibrary assetLibraryPin2 = _addAssetLibraryPin();

		page = ReflectionTestUtil.invoke(
			_getSpacesSectionDisplayContext(getMockHttpServletRequest()),
			"getPage", new Class<?>[0]);

		items = page.getItems();

		Assert.assertEquals(items.toString(), 5, items.size());

		Assert.assertEquals(originalTotalCount + 8, page.getTotalCount());

		Assert.assertTrue(items.contains(assetLibraryPin1));
		Assert.assertTrue(items.contains(assetLibraryPin2));

		AssetLibrary assetLibraryPin3 = _addAssetLibraryPin();
		AssetLibrary assetLibraryPin4 = _addAssetLibraryPin();
		AssetLibrary assetLibraryPin5 = _addAssetLibraryPin();

		User user = UserTestUtil.addCompanyAdminUser(
			companyLocalService.getCompany(group.getCompanyId()));

		AssetLibrary assetLibraryPinnedByOtherUser1 =
			_addAssetLibraryPinnedByOtherUser(user);
		AssetLibrary assetLibraryPinnedByOtherUser2 =
			_addAssetLibraryPinnedByOtherUser(user);
		AssetLibrary assetLibraryPinnedByOtherUser3 =
			_addAssetLibraryPinnedByOtherUser(user);
		AssetLibrary assetLibraryPinnedByOtherUser4 =
			_addAssetLibraryPinnedByOtherUser(user);
		AssetLibrary assetLibraryPinnedByOtherUser5 =
			_addAssetLibraryPinnedByOtherUser(user);

		HttpServletRequest userHttpServletRequest =
			new MockHttpServletRequest();

		userHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY,
			getUserThemeDisplay(userHttpServletRequest, user));

		Page<AssetLibrary> userPage = ReflectionTestUtil.invoke(
			_getSpacesSectionDisplayContext(userHttpServletRequest), "getPage",
			new Class<?>[0]);

		Collection<AssetLibrary> userPageItems = userPage.getItems();

		Assert.assertTrue(
			userPageItems.contains(assetLibraryPinnedByOtherUser1));
		Assert.assertTrue(
			userPageItems.contains(assetLibraryPinnedByOtherUser2));
		Assert.assertTrue(
			userPageItems.contains(assetLibraryPinnedByOtherUser3));
		Assert.assertTrue(
			userPageItems.contains(assetLibraryPinnedByOtherUser4));
		Assert.assertTrue(
			userPageItems.contains(assetLibraryPinnedByOtherUser5));

		page = ReflectionTestUtil.invoke(
			_getSpacesSectionDisplayContext(getMockHttpServletRequest()),
			"getPage", new Class<?>[0]);

		items = page.getItems();

		Assert.assertEquals(items.toString(), 5, items.size());

		Assert.assertEquals(originalTotalCount + 16, page.getTotalCount());

		Assert.assertTrue(items.contains(assetLibraryPin1));
		Assert.assertTrue(items.contains(assetLibraryPin2));
		Assert.assertTrue(items.contains(assetLibraryPin3));
		Assert.assertTrue(items.contains(assetLibraryPin4));
		Assert.assertTrue(items.contains(assetLibraryPin5));
	}

	protected ThemeDisplay getUserThemeDisplay(
			HttpServletRequest httpServletRequest, User user)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker(user, true));
		themeDisplay.setRealUser(user);
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setScopeGroupId(group.getGroupId());
		themeDisplay.setSiteGroupId(group.getGroupId());
		themeDisplay.setURLCurrent("http://localhost:8080/currentURL");
		themeDisplay.setUser(user);

		return themeDisplay;
	}

	private AssetLibrary _addAssetLibrary() throws Exception {
		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			null,
			new ServiceContext() {
				{
					setCompanyId(group.getCompanyId());
					setUserId(TestPropsValues.getUserId());
				}
			});

		return _assetLibraryResource.getAssetLibrary(depotEntry.getGroupId());
	}

	private AssetLibrary _addAssetLibraryPin() throws Exception {
		AssetLibrary assetLibrary = _addAssetLibrary();

		DepotEntry depotEntry = _depotEntryLocalService.getDepotEntry(
			assetLibrary.getId());

		return _assetLibraryResource.putAssetLibraryPin(
			depotEntry.getGroupId());
	}

	private AssetLibrary _addAssetLibraryPinnedByOtherUser(User user)
		throws Exception {

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			null,
			new ServiceContext() {
				{
					setCompanyId(group.getCompanyId());
					setUserId(user.getUserId());
				}
			});

		AssetLibraryResource.Builder builder =
			_assetLibraryResourceFactory.create();

		AssetLibraryResource userAssetLibraryResource = builder.user(
			user
		).build();

		return userAssetLibraryResource.putAssetLibraryPin(
			depotEntry.getGroupId());
	}

	private Object _getSpacesSectionDisplayContext(
			HttpServletRequest httpServletRequest)
		throws Exception {

		_fragmentRenderer.render(
			null, httpServletRequest, new MockHttpServletResponse());

		Object contentsSectionDisplayContext = httpServletRequest.getAttribute(
			"com.liferay.site.cms.site.initializer.internal.display.context." +
				"SpacesSectionDisplayContext");

		Assert.assertNotNull(contentsSectionDisplayContext);

		return contentsSectionDisplayContext;
	}

	private AssetLibraryResource _assetLibraryResource;

	@Inject
	private AssetLibraryResource.Factory _assetLibraryResourceFactory;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.site.cms.site.initializer.internal.fragment.renderer.SpacesSectionFragmentRenderer"
	)
	private FragmentRenderer _fragmentRenderer;

}