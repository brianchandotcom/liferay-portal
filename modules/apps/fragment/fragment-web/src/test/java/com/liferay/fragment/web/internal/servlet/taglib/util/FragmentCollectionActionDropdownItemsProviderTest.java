/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.servlet.taglib.util;

import com.liferay.fragment.web.internal.display.context.FragmentDisplayContext;
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
	public void testGetActionDropdowns() {
		setUpFragmentPermission(true);

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

	private final FragmentDisplayContext _fragmentDisplayContext = Mockito.mock(
		FragmentDisplayContext.class);

}