/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.servlet.taglib.util;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Eudaldo Alonso
 */
public class InheritedFragmentEntryActionDropdownItemsProviderTest
	extends BaseActionDropdownItemsProviderTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetActionDropdowns() throws Exception {
		setUpFragmentPermission(true);

		InheritedFragmentEntryActionDropdownItemsProvider
			inheritedFragmentEntryActionDropdownItemsProvider =
				new InheritedFragmentEntryActionDropdownItemsProvider(
					Mockito.mock(FragmentEntry.class), renderRequest,
					renderResponse);

		List<DropdownItem> dropdownItems =
			inheritedFragmentEntryActionDropdownItemsProvider.
				getActionDropdownItems();

		Assert.assertEquals(dropdownItems.toString(), 2, dropdownItems.size());

		DropdownItem copyDropdownItem = dropdownItems.get(0);

		Assert.assertEquals("copy-to", copyDropdownItem.get("label"));

		DropdownItem exportDropdownItem = dropdownItems.get(1);

		Assert.assertEquals("export", exportDropdownItem.get("label"));
	}

}