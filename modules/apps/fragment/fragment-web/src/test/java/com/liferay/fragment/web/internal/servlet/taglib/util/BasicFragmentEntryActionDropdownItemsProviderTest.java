/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.servlet.taglib.util;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Eudaldo Alonso
 */
public class BasicFragmentEntryActionDropdownItemsProviderTest
	extends BaseActionDropdownItemsProviderTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetActionDropdownsWithManageFragmentEntries()
		throws Exception {

		setUpFragmentPermission(true);
		_setUpFragmentEntry(false, false);

		BasicFragmentEntryActionDropdownItemsProvider
			basicFragmentEntryActionDropdownItemsProvider =
				new BasicFragmentEntryActionDropdownItemsProvider(
					_fragmentEntry, renderRequest, renderResponse);

		List<DropdownItem> dropdownItems = _getActionDropdownItems(
			basicFragmentEntryActionDropdownItemsProvider.
				getActionDropdownItems());

		Assert.assertEquals(dropdownItems.toString(), 9, dropdownItems.size());
		Assert.assertNotNull(
			_findFirstDropdownItem(dropdownItems, "change-thumbnail"));
		Assert.assertNotNull(_findFirstDropdownItem(dropdownItems, "delete"));
		Assert.assertNotNull(_findFirstDropdownItem(dropdownItems, "edit"));
		Assert.assertNotNull(_findFirstDropdownItem(dropdownItems, "export"));
		Assert.assertNotNull(
			_findFirstDropdownItem(dropdownItems, "make-a-copy"));
		Assert.assertNotNull(
			_findFirstDropdownItem(dropdownItems, "mark-as-cacheable"));
		Assert.assertNotNull(_findFirstDropdownItem(dropdownItems, "move"));
		Assert.assertNotNull(_findFirstDropdownItem(dropdownItems, "rename"));
		Assert.assertNotNull(
			_findFirstDropdownItem(dropdownItems, "view-site-usages"));
	}

	@Test
	public void testGetActionDropdownsWithoutManageFragmentEntries()
		throws Exception {

		setUpFragmentPermission(false);
		_setUpFragmentEntry(false, false);

		BasicFragmentEntryActionDropdownItemsProvider
			basicFragmentEntryActionDropdownItemsProvider =
				new BasicFragmentEntryActionDropdownItemsProvider(
					_fragmentEntry, renderRequest, renderResponse);

		List<DropdownItem> dropdownItems = _getActionDropdownItems(
			basicFragmentEntryActionDropdownItemsProvider.
				getActionDropdownItems());

		Assert.assertTrue(dropdownItems.isEmpty());
	}

	@Test
	public void testGetReactFragmentEntryActionDropdowns() throws Exception {
		setUpFragmentPermission(true);
		_setUpFragmentEntry(false, true);

		BasicFragmentEntryActionDropdownItemsProvider
			basicFragmentEntryActionDropdownItemsProvider =
				new BasicFragmentEntryActionDropdownItemsProvider(
					_fragmentEntry, renderRequest, renderResponse);

		List<DropdownItem> dropdownItems = _getActionDropdownItems(
			basicFragmentEntryActionDropdownItemsProvider.
				getActionDropdownItems());

		Assert.assertEquals(dropdownItems.toString(), 6, dropdownItems.size());
		Assert.assertNotNull(
			_findFirstDropdownItem(dropdownItems, "change-thumbnail"));
		Assert.assertNotNull(_findFirstDropdownItem(dropdownItems, "delete"));
		Assert.assertNotNull(
			_findFirstDropdownItem(dropdownItems, "make-a-copy"));
		Assert.assertNotNull(_findFirstDropdownItem(dropdownItems, "move"));
		Assert.assertNotNull(_findFirstDropdownItem(dropdownItems, "rename"));
		Assert.assertNotNull(
			_findFirstDropdownItem(dropdownItems, "view-site-usages"));
	}

	@Test
	public void testGetReadonlyFragmentEntryActionDropdowns() throws Exception {
		setUpFragmentPermission(true);
		_setUpFragmentEntry(true, false);

		BasicFragmentEntryActionDropdownItemsProvider
			basicFragmentEntryActionDropdownItemsProvider =
				new BasicFragmentEntryActionDropdownItemsProvider(
					_fragmentEntry, renderRequest, renderResponse);

		List<DropdownItem> dropdownItems = _getActionDropdownItems(
			basicFragmentEntryActionDropdownItemsProvider.
				getActionDropdownItems());

		Assert.assertEquals(dropdownItems.toString(), 2, dropdownItems.size());
		Assert.assertNotNull(_findFirstDropdownItem(dropdownItems, "edit"));
		Assert.assertNotNull(
			_findFirstDropdownItem(dropdownItems, "make-a-copy"));
	}

	private DropdownItem _findFirstDropdownItem(
		List<DropdownItem> dropdownItems, String label) {

		for (DropdownItem dropdownItem : dropdownItems) {
			if (StringUtil.equals((String)dropdownItem.get("label"), label)) {
				return dropdownItem;
			}
		}

		return null;
	}

	private List<DropdownItem> _getActionDropdownItems(
		List<DropdownItem> dropdownItems) {

		List<DropdownItem> allDropdownItems = new ArrayList<>();

		for (DropdownItem dropdownItem : dropdownItems) {
			if (!StringUtil.equals((String)dropdownItem.get("type"), "group")) {
				allDropdownItems.add(dropdownItem);

				continue;
			}

			allDropdownItems.addAll(
				(List<DropdownItem>)dropdownItem.get("items"));
		}

		return allDropdownItems;
	}

	private void _setUpFragmentEntry(boolean readOnly, boolean typeReact) {
		Mockito.when(
			_fragmentEntry.getGlobalUsageCount()
		).thenReturn(
			0
		);

		Mockito.when(
			_fragmentEntry.isReadOnly()
		).thenReturn(
			readOnly
		);

		Mockito.when(
			_fragmentEntry.isTypeReact()
		).thenReturn(
			typeReact
		);
	}

	private final FragmentEntry _fragmentEntry = Mockito.mock(
		FragmentEntry.class);

}