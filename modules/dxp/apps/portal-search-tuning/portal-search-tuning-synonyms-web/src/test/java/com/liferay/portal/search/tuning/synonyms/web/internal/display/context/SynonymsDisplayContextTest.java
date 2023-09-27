/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adam Brandizzi
 * @author Wade Cao
 */
public class SynonymsDisplayContextTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_synonymsDisplayContext = new SynonymsDisplayContext();
	}

	@Test
	public void testGetterSetter() {
		CreationMenu creationMenu = Mockito.mock(CreationMenu.class);
		List<DropdownItem> dropdownItems = Arrays.asList(
			Mockito.mock(DropdownItem.class));

		_synonymsDisplayContext.setCreationMenu(creationMenu);
		_synonymsDisplayContext.setDisabledManagementBar(false);
		_synonymsDisplayContext.setDropdownItems(dropdownItems);
		_synonymsDisplayContext.setItemsTotal(1);
		_synonymsDisplayContext.setSearchContainer(
			Mockito.mock(SearchContainer.class));

		Assert.assertEquals(1, _synonymsDisplayContext.getItemsTotal());
		Assert.assertEquals(
			creationMenu, _synonymsDisplayContext.getCreationMenu());
		Assert.assertEquals(
			dropdownItems,
			_synonymsDisplayContext.getActionDropdownMultipleItems());

		Assert.assertNotNull(_synonymsDisplayContext.getSearchContainer());
	}

	private SynonymsDisplayContext _synonymsDisplayContext;

}