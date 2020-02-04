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

package com.liferay.item.selector.test.taglib.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.item.selector.taglib.servlet.taglib.GroupSelectorTag;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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

			List<Group> groups =
				(List<Group>)mockHttpServletRequest.getAttribute(
					"liferay-item-selector:group-selector:groups");

			Stream<Group> stream = groups.stream();

			stream.filter(
				currentGroup -> Objects.equals(
					currentGroup.getGroupId(), group.getGroupId())
			).findAny(
			).orElseThrow(
				() -> new AssertionError(
					"Group " + group.getGroupId() + " was not found")
			);
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