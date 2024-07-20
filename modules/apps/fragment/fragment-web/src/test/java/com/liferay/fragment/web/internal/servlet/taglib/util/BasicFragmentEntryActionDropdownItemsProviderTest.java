/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.servlet.taglib.util;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

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

		assertDropdownItemsInCorrectOrder(
			basicFragmentEntryActionDropdownItemsProvider.
				getActionDropdownItems(),
			"edit", "change-thumbnail", "rename", "mark-as-cacheable",
			"view-site-usages", "export", "make-a-copy", "move", "delete");
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

		assertDropdownItemsInCorrectOrder(
			basicFragmentEntryActionDropdownItemsProvider.
				getActionDropdownItems());
	}

	@Test
	public void testGetReactFragmentEntryActionDropdowns() throws Exception {
		setUpFragmentPermission(true);
		_setUpFragmentEntry(false, true);

		BasicFragmentEntryActionDropdownItemsProvider
			basicFragmentEntryActionDropdownItemsProvider =
				new BasicFragmentEntryActionDropdownItemsProvider(
					_fragmentEntry, renderRequest, renderResponse);

		assertDropdownItemsInCorrectOrder(
			basicFragmentEntryActionDropdownItemsProvider.
				getActionDropdownItems(),
			"change-thumbnail", "rename", "view-site-usages", "make-a-copy",
			"move", "delete");
	}

	@Test
	public void testGetReadonlyFragmentEntryActionDropdowns() throws Exception {
		setUpFragmentPermission(true);
		_setUpFragmentEntry(true, false);

		BasicFragmentEntryActionDropdownItemsProvider
			basicFragmentEntryActionDropdownItemsProvider =
				new BasicFragmentEntryActionDropdownItemsProvider(
					_fragmentEntry, renderRequest, renderResponse);

		assertDropdownItemsInCorrectOrder(
			basicFragmentEntryActionDropdownItemsProvider.
				getActionDropdownItems(),
			"edit", "make-a-copy");
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