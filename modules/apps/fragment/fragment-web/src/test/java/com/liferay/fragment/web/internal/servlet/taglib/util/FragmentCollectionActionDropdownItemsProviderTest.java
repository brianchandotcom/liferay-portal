/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.servlet.taglib.util;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.web.internal.display.context.FragmentDisplayContext;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Eudaldo Alonso
 */
public class FragmentCollectionActionDropdownItemsProviderTest
	extends BaseActionDropdownItemsProviderTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() {
		super.setUp();

		_setUpFragmentDisplayContext();
	}

	@Test
	@TestInfo("LPD-82487")
	public void testGetActionDropdowns() {
		setUpFragmentPermission(true);

		_setUpFragmentCollection(false, true);

		FragmentCollectionActionDropdownItemsProvider
			fragmentCollectionActionDropdownItemsProvider =
				new FragmentCollectionActionDropdownItemsProvider(
					_fragmentDisplayContext, httpServletRequest,
					renderResponse);

		assertDropdownItemsInCorrectOrder(
			fragmentCollectionActionDropdownItemsProvider.
				getActionDropdownItems(),
			"edit", "export", "import", "delete");
	}

	@Test
	@TestInfo("LPD-82487")
	public void testGetActionDropdownsForFragmentCollectionWithExportableComposition() {
		setUpFragmentPermission(true);

		_setUpFragmentCollection(false, true);

		FragmentCollectionActionDropdownItemsProvider
			fragmentCollectionActionDropdownItemsProvider =
				new FragmentCollectionActionDropdownItemsProvider(
					_fragmentDisplayContext, httpServletRequest,
					renderResponse);

		assertDropdownItemsInCorrectOrder(
			fragmentCollectionActionDropdownItemsProvider.
				getActionDropdownItems(),
			"edit", "export", "import", "delete");
	}

	@Test
	@TestInfo("LPD-82487")
	public void testGetActionDropdownsForFragmentCollectionWithOnlyMarketplaceFragments() {
		setUpFragmentPermission(true);

		_setUpFragmentCollection(false, false);

		FragmentCollectionActionDropdownItemsProvider
			fragmentCollectionActionDropdownItemsProvider =
				new FragmentCollectionActionDropdownItemsProvider(
					_fragmentDisplayContext, httpServletRequest,
					renderResponse);

		assertDropdownItemsInCorrectOrder(
			fragmentCollectionActionDropdownItemsProvider.
				getActionDropdownItems(),
			"edit", "import", "delete");
	}

	@Test
	@TestInfo("LPD-82487")
	public void testGetActionDropdownsForMarketplaceFragmentCollection() {
		setUpFragmentPermission(true);

		_setUpFragmentCollection(true, false);

		FragmentCollectionActionDropdownItemsProvider
			fragmentCollectionActionDropdownItemsProvider =
				new FragmentCollectionActionDropdownItemsProvider(
					_fragmentDisplayContext, httpServletRequest,
					renderResponse);

		assertDropdownItemsInCorrectOrder(
			fragmentCollectionActionDropdownItemsProvider.
				getActionDropdownItems(),
			"edit", "import", "delete");
	}

	@Test
	@TestInfo("LPD-82487")
	public void testGetActionDropdownsForMarketplaceFragmentCollectionWithExportableFragment() {
		setUpFragmentPermission(true);

		_setUpFragmentCollection(true, true);

		FragmentCollectionActionDropdownItemsProvider
			fragmentCollectionActionDropdownItemsProvider =
				new FragmentCollectionActionDropdownItemsProvider(
					_fragmentDisplayContext, httpServletRequest,
					renderResponse);

		assertDropdownItemsInCorrectOrder(
			fragmentCollectionActionDropdownItemsProvider.
				getActionDropdownItems(),
			"edit", "export", "import", "delete");
	}

	@Test
	@TestInfo("LPD-82487")
	public void testGetActionDropdownsWithReactFragment() {
		setUpFragmentPermission(true);

		_setUpFragmentCollection(false, false);

		FragmentCollectionActionDropdownItemsProvider
			fragmentCollectionActionDropdownItemsProvider =
				new FragmentCollectionActionDropdownItemsProvider(
					_fragmentDisplayContext, httpServletRequest,
					renderResponse);

		assertDropdownItemsInCorrectOrder(
			fragmentCollectionActionDropdownItemsProvider.
				getActionDropdownItems(),
			"edit", "import", "delete");
	}

	private void _setUpFragmentCollection(
		boolean marketplace, boolean hasExportableFragments) {

		Mockito.when(
			_fragmentCollection.getFragmentCollectionId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			_fragmentDisplayContext.getFragmentCollection()
		).thenReturn(
			_fragmentCollection
		);

		Mockito.when(
			_fragmentCollection.isMarketplace()
		).thenReturn(
			marketplace
		);

		Mockito.when(
			_fragmentCollection.
				hasExportableFragmentCompositionsAndFragmentEntries()
		).thenReturn(
			hasExportableFragments
		);
	}

	private void _setUpFragmentDisplayContext() {
		Mockito.when(
			_fragmentDisplayContext.hasDeletePermission()
		).thenReturn(
			true
		);

		Mockito.when(
			_fragmentDisplayContext.hasUpdatePermission()
		).thenReturn(
			true
		);
	}

	private final FragmentCollection _fragmentCollection = Mockito.mock(
		FragmentCollection.class);
	private final FragmentDisplayContext _fragmentDisplayContext = Mockito.mock(
		FragmentDisplayContext.class);

}