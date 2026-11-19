/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.content.web.internal.portlet.toolbar.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wulbern
 */
@RunWith(Arquillian.class)
public class JournalContentPortletToolbarContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		_layout = LayoutTestUtil.addTypePortletLayout(_group);

		_portletId = LayoutTestUtil.addPortletToLayout(
			_layout, JournalContentPortletKeys.JOURNAL_CONTENT);
	}

	@Test
	@TestInfo("LPD-116508")
	public void testGetPortletTitleMenus() throws Exception {
		URLMenuItem urlMenuItem = _getURLMenuItem(_group);

		Assert.assertEquals(
			_ddmStructure.getName(LocaleUtil.getSiteDefault()),
			urlMenuItem.getLabel());

		String url = urlMenuItem.getURL();

		Assert.assertEquals(
			String.valueOf(_ddmStructure.getStructureId()),
			_getParameter(url, "ddmStructureId"));
		Assert.assertEquals(
			String.valueOf(_group.getGroupId()), _getParameter(url, "groupId"));
		Assert.assertEquals(_portletId, _getParameter(url, "portletResource"));
		Assert.assertEquals(
			String.valueOf(_layout.getPlid()),
			_getParameter(url, "refererPlid"));

		_testGetPortletTitleMenusWithLayoutScope();
	}

	private String _getParameter(String url, String name) {
		return HttpComponentsUtil.getParameter(
			url, _portal.getPortletNamespace(JournalPortletKeys.JOURNAL) + name,
			false);
	}

	private URLMenuItem _getURLMenuItem(Group scopeGroup) throws Exception {
		ThemeDisplay themeDisplay = ContentLayoutTestUtil.getThemeDisplay(
			_companyLocalService.getCompany(_group.getCompanyId()), _group,
			_layout);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setId(_portletId);
		portletDisplay.setNamespace(_portal.getPortletNamespace(_portletId));

		themeDisplay.setScopeGroupId(scopeGroup.getGroupId());

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		List<Menu> portletTitleMenus =
			_portletToolbarContributor.getPortletTitleMenus(
				mockLiferayPortletRenderRequest,
				new MockLiferayPortletRenderResponse());

		Assert.assertEquals(
			portletTitleMenus.toString(), 1, portletTitleMenus.size());

		Menu menu = portletTitleMenus.get(0);

		List<MenuItem> menuItems = menu.getMenuItems();

		return (URLMenuItem)menuItems.get(0);
	}

	private void _testGetPortletTitleMenusWithLayoutScope() throws Exception {
		Group scopeGroup = GroupTestUtil.addGroup(
			TestPropsValues.getUserId(), _layout);

		URLMenuItem urlMenuItem = _getURLMenuItem(scopeGroup);

		Assert.assertEquals(
			_ddmStructure.getName(LocaleUtil.getSiteDefault()),
			urlMenuItem.getLabel());

		String url = urlMenuItem.getURL();

		Assert.assertEquals(
			String.valueOf(_ddmStructure.getStructureId()),
			_getParameter(url, "ddmStructureId"));
		Assert.assertEquals(
			String.valueOf(scopeGroup.getGroupId()),
			_getParameter(url, "groupId"));
		Assert.assertEquals(_portletId, _getParameter(url, "portletResource"));
		Assert.assertEquals(
			String.valueOf(_layout.getPlid()),
			_getParameter(url, "refererPlid"));
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	private DDMStructure _ddmStructure;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private Portal _portal;

	private String _portletId;

	@Inject(
		filter = "jakarta.portlet.name=" + JournalContentPortletKeys.JOURNAL_CONTENT
	)
	private PortletToolbarContributor _portletToolbarContributor;

}