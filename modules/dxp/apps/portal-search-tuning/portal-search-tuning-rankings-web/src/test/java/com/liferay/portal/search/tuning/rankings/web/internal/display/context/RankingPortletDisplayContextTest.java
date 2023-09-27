/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class RankingPortletDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testSetterGetter() throws Exception {
		RankingPortletDisplayContext rankingPortletDisplayContext =
			new RankingPortletDisplayContext();

		List<DropdownItem> dropdownItems = Arrays.asList(
			Mockito.mock(DropdownItem.class));

		rankingPortletDisplayContext.setActionDropdownItems(dropdownItems);

		rankingPortletDisplayContext.setClearResultsURL("clearResultsURL");

		CreationMenu creationMenu = Mockito.mock(CreationMenu.class);

		rankingPortletDisplayContext.setCreationMenu(creationMenu);

		rankingPortletDisplayContext.setDisabledManagementBar(false);
		rankingPortletDisplayContext.setDisplayStyle("displayStyle");
		rankingPortletDisplayContext.setFilterItemsDropdownItems(dropdownItems);
		rankingPortletDisplayContext.setOrderByType("orderByType");
		rankingPortletDisplayContext.setSearchActionURL("searchActionURL");
		rankingPortletDisplayContext.setSortingURL("sortingURL");
		rankingPortletDisplayContext.setTotalItems(10);

		@SuppressWarnings("unchecked")
		SearchContainer<RankingEntryDisplayContext> searchContainer =
			Mockito.mock(SearchContainer.class);

		rankingPortletDisplayContext.setSearchContainer(searchContainer);

		Assert.assertEquals(10, rankingPortletDisplayContext.getTotalItems());
		Assert.assertEquals(
			"clearResultsURL",
			rankingPortletDisplayContext.getClearResultsURL());
		Assert.assertEquals(
			creationMenu, rankingPortletDisplayContext.getCreationMenu());
		Assert.assertEquals(
			"displayStyle", rankingPortletDisplayContext.getDisplayStyle());
		Assert.assertEquals(
			dropdownItems,
			rankingPortletDisplayContext.getActionDropdownItems());
		Assert.assertEquals(
			dropdownItems,
			rankingPortletDisplayContext.getFilterItemsDropdownItems());
		Assert.assertEquals(
			"orderByType", rankingPortletDisplayContext.getOrderByType());
		Assert.assertEquals(
			"searchActionURL",
			rankingPortletDisplayContext.getSearchActionURL());
		Assert.assertEquals(
			searchContainer, rankingPortletDisplayContext.getSearchContainer());
		Assert.assertEquals(
			"sortingURL", rankingPortletDisplayContext.getSortingURL());
		Assert.assertFalse(
			rankingPortletDisplayContext.isDisabledManagementBar());
	}

}