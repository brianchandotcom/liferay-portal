/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.servlet.taglib.util;

import com.liferay.fragment.model.FragmentComposition;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Eudaldo Alonso
 */
public class BasicFragmentCompositionActionDropdownItemsProviderTest
	extends BaseActionDropdownItemsProviderTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetActionDropdowns() throws Exception {
		setUpFragmentPermission(true);

		BasicFragmentCompositionActionDropdownItemsProvider
			basicFragmentCompositionActionDropdownItemsProvider =
				new BasicFragmentCompositionActionDropdownItemsProvider(
					Mockito.mock(FragmentComposition.class), renderRequest,
					renderResponse);

		assertDropdownItemsInCorrectOrder(
			basicFragmentCompositionActionDropdownItemsProvider.
				getActionDropdownItems(),
			"change-thumbnail", "rename", "export", "move", "delete");
	}

	@FeatureFlags("LPD-34938")
	@Test
	public void testMarketplaceFragmentCompositionGetActionDropdowns()
		throws Exception {

		setUpFragmentPermission(true);

		FragmentComposition fragmentComposition = Mockito.mock(
			FragmentComposition.class);

		Mockito.when(
			fragmentComposition.isMarketplace()
		).thenReturn(
			true
		);

		BasicFragmentCompositionActionDropdownItemsProvider
			basicFragmentCompositionActionDropdownItemsProvider =
				new BasicFragmentCompositionActionDropdownItemsProvider(
					fragmentComposition, renderRequest, renderResponse);

		assertDropdownItemsInCorrectOrder(
			basicFragmentCompositionActionDropdownItemsProvider.
				getActionDropdownItems(),
			"move", "delete");
	}

}