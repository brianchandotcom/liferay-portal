/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.taglib.servlet.taglib.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.navigation.taglib.servlet.taglib.NavigationMenuMode;
import com.liferay.site.navigation.taglib.servlet.taglib.NavigationMenuTag;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPageContext;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class NavigationMenuTagTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypePortletLayout(_group);
		_locale = _portal.getSiteDefaultLocale(_group);
	}

	@Test
	public void testNavigationMenuModePublicPagesWithUnpublishedChildrenLayout()
		throws Exception {

		Layout parentLayout = LayoutTestUtil.addTypePortletLayout(
			_group, _layout.getPlid());

		LayoutTestUtil.addTypePortletLayout(_group, parentLayout.getPlid());

		_assertNavigationMenuTagWithUnpublishedLayout(
			parentLayout.getLayoutId());
	}

	@Test
	public void testNavigationMenuModePublicPagesWithUnpublishedLayout()
		throws Exception {

		_assertNavigationMenuTagWithUnpublishedLayout(
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
	}

	private void _assertNavigationMenuTag() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest();
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		NavigationMenuTag navigationMenuTag = new NavigationMenuTag();

		navigationMenuTag.setDdmTemplateKey("LIST-MENU-FTL");
		navigationMenuTag.setDisplayDepth(0);
		navigationMenuTag.setNavigationMenuMode(
			NavigationMenuMode.PUBLIC_PAGES);
		navigationMenuTag.setPageContext(
			new MockPageContext(
				null, mockHttpServletRequest, mockHttpServletResponse));
		navigationMenuTag.setRootItemLevel(0);

		navigationMenuTag.doTag(
			mockHttpServletRequest, mockHttpServletResponse);

		String content = mockHttpServletResponse.getContentAsString();

		for (Layout layout :
				_layoutLocalService.getLayouts(_group.getGroupId(), false)) {

			if (layout.isPublished()) {
				Assert.assertTrue(content.contains(layout.getName(_locale)));
				Assert.assertTrue(
					content.contains(layout.getFriendlyURL(_locale)));

				continue;
			}

			Assert.assertFalse(content.contains(layout.getName(_locale)));
			Assert.assertFalse(
				content.contains(layout.getFriendlyURL(_locale)));
		}
	}

	private void _assertNavigationMenuTagWithUnpublishedLayout(
			long parentLayoutId)
		throws Exception {

		Layout contentLayout = _layoutLocalService.addLayout(
			null, TestPropsValues.getUserId(), _group.getGroupId(), false,
			parentLayoutId, RandomTestUtil.randomString(), StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_CONTENT, false,
			StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertFalse(contentLayout.isPublished());

		_assertNavigationMenuTag();

		ContentLayoutTestUtil.publishLayout(
			contentLayout.fetchDraftLayout(), contentLayout);

		contentLayout = _layoutLocalService.getLayout(contentLayout.getPlid());

		Assert.assertTrue(contentLayout.isPublished());

		_assertNavigationMenuTag();
	}

	private MockHttpServletRequest _getMockHttpServletRequest()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			ContentLayoutTestUtil.getMockHttpServletRequest(
				_companyLocalService.getCompany(_group.getCompanyId()), _group,
				_layout);

		mockHttpServletRequest.setAttribute(
			"ORIGINAL_HTTP_SERVLET_REQUEST", mockHttpServletRequest);
		mockHttpServletRequest.setMethod(HttpMethods.GET);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)mockHttpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		themeDisplay.setRequest(mockHttpServletRequest);

		return mockHttpServletRequest;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	private Locale _locale;

	@Inject
	private Portal _portal;

}