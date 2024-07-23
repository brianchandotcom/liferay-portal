/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.admin.web.internal.servlet.taglib.util;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.site.admin.web.internal.display.context.SiteAdminDisplayContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
public class SiteActionDropdownItemsProviderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_groupLocalServiceUtilMockedStatic.when(
			() -> GroupLocalServiceUtil.fetchGroup(Mockito.anyLong())
		).thenReturn(
			_group
		);

		PropsUtil propsUtil = new PropsUtil();

		propsUtil.setProps(Mockito.mock(Props.class));

		_groupPermissionUtilMockedStatic = Mockito.mockStatic(
			GroupPermissionUtil.class);
	}

	@AfterClass
	public static void tearDownClass() {
		_groupLocalServiceUtilMockedStatic.close();
		_groupPermissionUtilMockedStatic.close();
		_organizationLocalServiceUtilMockedStatic.close();
		_userGroupLocalServiceUtilMockedStatic.close();
	}

	@Before
	public void setUp() throws Exception {
		Mockito.reset(GroupPermissionUtil.class, _group);

		_setUpGroup();
		_setUpLanguageUtil();
		_setUpPortalUtil();
	}

	@Test
	public void testGetActionDropdownItems() throws Exception {
		_assertDropdownItem(Collections.emptyMap());
	}

	@Test
	public void testGetActionDropdownItemsWithUpdatePermission()
		throws Exception {

		Mockito.when(
			GroupPermissionUtil.contains(null, _group, ActionKeys.UPDATE)
		).thenReturn(
			true
		);

		_assertDropdownItem(
			TreeMapBuilder.put(
				"deactivate", "/site_admin/deactivate_group"
			).put(
				"go-to-x-site-settings",
				ConfigurationAdminPortletKeys.SITE_SETTINGS
			).build());
	}

	@Test
	public void testGetActionDropdownItemsWithUpdatePermissionAndNoPublishedPrivatePages()
		throws Exception {

		Mockito.when(
			GroupPermissionUtil.contains(null, _group, ActionKeys.UPDATE)
		).thenReturn(
			true
		);

		Mockito.when(
			_group.getPrivateLayoutsPageCount()
		).thenReturn(
			RandomTestUtil.randomInt()
		);

		Mockito.when(
			_group.getDisplayURL(
				Mockito.any(ThemeDisplay.class), Mockito.anyBoolean(),
				Mockito.anyBoolean())
		).thenReturn(
			null
		);

		_assertDropdownItem(
			TreeMapBuilder.put(
				"deactivate", "/site_admin/deactivate_group"
			).put(
				"go-to-x-private-pages", "privateLayout=true"
			).put(
				"go-to-x-site-settings",
				ConfigurationAdminPortletKeys.SITE_SETTINGS
			).build());
	}

	@Test
	public void testGetActionDropdownItemsWithUpdatePermissionAndNoPublishedPublicPages()
		throws Exception {

		Mockito.when(
			GroupPermissionUtil.contains(null, _group, ActionKeys.UPDATE)
		).thenReturn(
			true
		);

		Mockito.when(
			_group.getPublicLayoutsPageCount()
		).thenReturn(
			RandomTestUtil.randomInt()
		);

		Mockito.when(
			_group.getDisplayURL(
				Mockito.any(ThemeDisplay.class), Mockito.anyBoolean(),
				Mockito.anyBoolean())
		).thenReturn(
			null
		);

		_assertDropdownItem(
			TreeMapBuilder.put(
				"deactivate", "/site_admin/deactivate_group"
			).put(
				"go-to-x-public-pages", LayoutAdminPortletKeys.GROUP_PAGES
			).put(
				"go-to-x-site-settings",
				ConfigurationAdminPortletKeys.SITE_SETTINGS
			).build());
	}

	@Test
	public void testGetActionDropdownItemsWithUpdatePermissionAndPrivatePages()
		throws Exception {

		Mockito.when(
			GroupPermissionUtil.contains(null, _group, ActionKeys.UPDATE)
		).thenReturn(
			true
		);

		Mockito.when(
			_group.getPrivateLayoutsPageCount()
		).thenReturn(
			RandomTestUtil.randomInt()
		);

		_assertDropdownItem(
			TreeMapBuilder.put(
				"deactivate", "/site_admin/deactivate_group"
			).put(
				"go-to-x-private-pages", "/group/groupKey/"
			).put(
				"go-to-x-site-settings",
				ConfigurationAdminPortletKeys.SITE_SETTINGS
			).build());
	}

	@Test
	public void testGetActionDropdownItemsWithUpdatePermissionAndPublicPages()
		throws Exception {

		Mockito.when(
			GroupPermissionUtil.contains(null, _group, ActionKeys.UPDATE)
		).thenReturn(
			true
		);

		Mockito.when(
			_group.getPublicLayoutsPageCount()
		).thenReturn(
			RandomTestUtil.randomInt()
		);

		_assertDropdownItem(
			TreeMapBuilder.put(
				"deactivate", "/site_admin/deactivate_group"
			).put(
				"go-to-x-public-pages", "/web/groupKey/"
			).put(
				"go-to-x-site-settings",
				ConfigurationAdminPortletKeys.SITE_SETTINGS
			).build());
	}

	private void _assertDropdownItem(Map<String, String> map) throws Exception {
		SiteActionDropdownItemsProvider siteActionDropdownItemsProvider =
			new SiteActionDropdownItemsProvider(
				_group, _liferayPortletRequest,
				new MockLiferayPortletActionResponse(),
				_siteAdminDisplayContext);

		List<DropdownItem> dropdownItems = new ArrayList<>();

		for (DropdownItem dropdownItem :
				siteActionDropdownItemsProvider.getActionDropdownItems()) {

			if (!StringUtil.equals((String)dropdownItem.get("type"), "group")) {
				dropdownItems.add(dropdownItem);

				continue;
			}

			dropdownItems.addAll((List<DropdownItem>)dropdownItem.get("items"));
		}

		Assert.assertEquals(
			dropdownItems.toString(), map.size(), dropdownItems.size());

		Iterator<DropdownItem> iterator = dropdownItems.iterator();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			DropdownItem dropdownItem = iterator.next();

			String href = GetterUtil.getString(dropdownItem.get("href"));

			if (Validator.isNull(href)) {
				Map<String, Object> dataMap =
					(Map<String, Object>)dropdownItem.get("data");

				href = GetterUtil.getString(
					dataMap.get(entry.getKey() + "SiteURL"));
			}

			Assert.assertEquals(entry.getKey(), dropdownItem.get("label"));

			Assert.assertTrue(
				href,
				StringUtil.contains(href, entry.getValue(), StringPool.BLANK));
		}
	}

	private void _setUpGroup() {
		Mockito.when(
			_group.isActive()
		).thenReturn(
			true
		);

		Mockito.when(
			_group.getLayoutRootNodeName(true, null)
		).thenReturn(
			"private-pages"
		);

		Mockito.when(
			_group.getLayoutRootNodeName(false, null)
		).thenReturn(
			"public-pages"
		);

		Mockito.when(
			_group.getDisplayURL(
				Mockito.any(ThemeDisplay.class), Mockito.anyBoolean(),
				Mockito.anyBoolean())
		).thenAnswer(
			(Answer<String>)invocationOnMock -> {
				if (invocationOnMock.getArgument(1, Boolean.class)) {
					return "/group/groupKey/";
				}

				return "/web/groupKey/";
			}
		);
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.get(
				Mockito.any(HttpServletRequest.class), Mockito.anyString())
		).thenAnswer(
			(Answer<String>)invocationOnMock -> invocationOnMock.getArgument(
				1, String.class)
		);

		Mockito.when(
			language.format(
				Mockito.any(HttpServletRequest.class), Mockito.anyString(),
				Mockito.any(Object.class))
		).thenAnswer(
			(Answer<String>)invocationOnMock -> StringBundler.concat(
				invocationOnMock.getArgument(1, String.class), StringPool.DASH,
				GetterUtil.getString(
					invocationOnMock.getArgument(2, Object.class)))
		);

		languageUtil.setLanguage(language);
	}

	private void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		_portal = Mockito.mock(Portal.class);

		_liferayPortletRequest = new MockLiferayPortletActionRequest();

		_httpServletRequest = new MockHttpServletRequest();
		_themeDisplay = Mockito.mock(ThemeDisplay.class);

		_httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);

		Mockito.when(
			_portal.getHttpServletRequest(_liferayPortletRequest)
		).thenReturn(
			_httpServletRequest
		);

		MockLiferayPortletURL liferayPortletURL1 = new MockLiferayPortletURL();

		liferayPortletURL1.setParameter(
			"portletId", LayoutAdminPortletKeys.GROUP_PAGES);
		liferayPortletURL1.setPortletId(LayoutAdminPortletKeys.GROUP_PAGES);

		Mockito.when(
			_portal.getControlPanelPortletURL(
				_httpServletRequest, _group, LayoutAdminPortletKeys.GROUP_PAGES,
				0, 0, PortletRequest.RENDER_PHASE)
		).thenReturn(
			liferayPortletURL1
		);

		MockLiferayPortletURL liferayPortletURL2 = new MockLiferayPortletURL();

		liferayPortletURL2.setParameter(
			"portletId", ConfigurationAdminPortletKeys.SITE_SETTINGS);
		liferayPortletURL2.setPortletId(
			ConfigurationAdminPortletKeys.SITE_SETTINGS);

		Mockito.when(
			_portal.getControlPanelPortletURL(
				_httpServletRequest, _group,
				ConfigurationAdminPortletKeys.SITE_SETTINGS, 0, 0,
				PortletRequest.RENDER_PHASE)
		).thenReturn(
			liferayPortletURL2
		);

		portalUtil.setPortal(_portal);
	}

	private static final Group _group = Mockito.mock(Group.class);
	private static final MockedStatic<GroupLocalServiceUtil>
		_groupLocalServiceUtilMockedStatic = Mockito.mockStatic(
			GroupLocalServiceUtil.class);
	private static MockedStatic<GroupPermissionUtil>
		_groupPermissionUtilMockedStatic;
	private static final MockedStatic<OrganizationLocalServiceUtil>
		_organizationLocalServiceUtilMockedStatic = Mockito.mockStatic(
			OrganizationLocalServiceUtil.class);
	private static final MockedStatic<UserGroupLocalServiceUtil>
		_userGroupLocalServiceUtilMockedStatic = Mockito.mockStatic(
			UserGroupLocalServiceUtil.class);

	private HttpServletRequest _httpServletRequest;
	private LiferayPortletRequest _liferayPortletRequest;
	private Portal _portal;
	private final SiteAdminDisplayContext _siteAdminDisplayContext =
		Mockito.mock(SiteAdminDisplayContext.class);
	private ThemeDisplay _themeDisplay;

}