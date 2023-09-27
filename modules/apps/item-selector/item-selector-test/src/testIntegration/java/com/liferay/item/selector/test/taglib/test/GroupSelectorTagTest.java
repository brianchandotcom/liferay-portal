/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.test.taglib.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.item.selector.taglib.servlet.taglib.GroupSelectorTag;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockPageContext;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class GroupSelectorTagTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGetGroupsCountWithoutGroupType() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, getThemeDisplay());

		GroupSelectorTag groupSelectorTag = _getGroupSelectorTag(
			mockHttpServletRequest);

		groupSelectorTag.doEndTag();

		Assert.assertEquals(
			0,
			mockHttpServletRequest.getAttribute(
				"liferay-item-selector:group-selector:groupsCount"));
	}

	@Test
	public void testGetGroupsCountWithSiteGroupType() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, getThemeDisplay());
		mockHttpServletRequest.setParameter("groupType", "site");

		GroupSelectorTag groupSelectorTag = _getGroupSelectorTag(
			mockHttpServletRequest);

		groupSelectorTag.doEndTag();

		int initialGroupsCount = (Integer)mockHttpServletRequest.getAttribute(
			"liferay-item-selector:group-selector:groupsCount");

		Group group = GroupTestUtil.addGroup();

		groupSelectorTag.doEndTag();

		int actualGroupsCount = (Integer)mockHttpServletRequest.getAttribute(
			"liferay-item-selector:group-selector:groupsCount");

		Assert.assertEquals(initialGroupsCount + 1, actualGroupsCount);

		GroupTestUtil.deleteGroup(group);
	}

	@Test
	public void testGetGroupsCountWithSiteGroupTypeAndRefererGroup()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = getThemeDisplay();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockHttpServletRequest.setParameter("groupType", "site");

		GroupSelectorTag groupSelectorTag = _getGroupSelectorTag(
			mockHttpServletRequest);

		groupSelectorTag.doEndTag();

		int initialGroupsCount = (Integer)mockHttpServletRequest.getAttribute(
			"liferay-item-selector:group-selector:groupsCount");

		Group group = GroupTestUtil.addGroup();

		try {
			themeDisplay.setRefererGroupId(group.getGroupId());

			groupSelectorTag.doEndTag();

			List<Group> groups =
				(List<Group>)mockHttpServletRequest.getAttribute(
					"liferay-item-selector:group-selector:groups");

			Assert.assertEquals(
				groups.toString(), initialGroupsCount + 1, groups.size());
		}
		finally {
			GroupTestUtil.deleteGroup(group);
		}
	}

	@Test
	public void testGetGroupsWithoutGroupType() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, getThemeDisplay());

		GroupSelectorTag groupSelectorTag = _getGroupSelectorTag(
			mockHttpServletRequest);

		groupSelectorTag.doEndTag();

		List<Group> groups = (List<Group>)mockHttpServletRequest.getAttribute(
			"liferay-item-selector:group-selector:groups");

		Assert.assertEquals(groups.toString(), 0, groups.size());
	}

	@Test
	public void testGetGroupsWithSiteGroupType() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, getThemeDisplay());
		mockHttpServletRequest.setParameter("groupType", "site");

		GroupSelectorTag groupSelectorTag = _getGroupSelectorTag(
			mockHttpServletRequest);

		Group group = GroupTestUtil.addGroup();

		try {
			groupSelectorTag.doEndTag();

			Assert.assertTrue(
				"Group " + group.getGroupId() + " was not found",
				ListUtil.exists(
					(List<Group>)mockHttpServletRequest.getAttribute(
						"liferay-item-selector:group-selector:groups"),
					currentGroup -> Objects.equals(
						group.getGroupId(), currentGroup.getGroupId())));
		}
		finally {
			GroupTestUtil.deleteGroup(group);
		}
	}

	protected ThemeDisplay getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setScopeGroupId(TestPropsValues.getGroupId());

		return themeDisplay;
	}

	private GroupSelectorTag _getGroupSelectorTag(
		HttpServletRequest httpServletRequest) {

		GroupSelectorTag groupSelectorTag = new GroupSelectorTag();

		groupSelectorTag.setPageContext(
			new MockPageContext(null, httpServletRequest));

		return groupSelectorTag;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

}