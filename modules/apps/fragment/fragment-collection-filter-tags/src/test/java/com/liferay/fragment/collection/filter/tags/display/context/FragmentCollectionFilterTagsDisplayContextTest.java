/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.collection.filter.tags.display.context;

import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Georgel Pop
 */
public class FragmentCollectionFilterTagsDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	@TestInfo("LPD-89494")
	public void testGetFragmentEntryLinkNamespace() {
		FragmentRendererContext fragmentRendererContext = Mockito.mock(
			FragmentRendererContext.class);

		String namespace = RandomTestUtil.randomString();

		Mockito.when(
			fragmentRendererContext.getFragmentElementId()
		).thenReturn(
			namespace
		);

		FragmentCollectionFilterTagsDisplayContext
			fragmentCollectionFilterTagsDisplayContext =
				new FragmentCollectionFilterTagsDisplayContext(
					null, Mockito.mock(FragmentEntryConfigurationParser.class),
					fragmentRendererContext,
					Mockito.mock(HttpServletRequest.class));

		Assert.assertEquals(
			namespace,
			fragmentCollectionFilterTagsDisplayContext.
				getFragmentEntryLinkNamespace());
	}

}