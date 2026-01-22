/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.portlet.RenderRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Jesus Antonio
 */
public class AssetPublisherViewContentDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_renderRequest = Mockito.mock(RenderRequest.class);
	}

	@Test
	public void testIsAssetEntryVisible() {
		AssetPublisherViewContentDisplayContext
			assetPublisherViewContentDisplayContext =
				new AssetPublisherViewContentDisplayContext(
					_renderRequest, false);

		AssetEntry assetEntry = Mockito.mock(AssetEntry.class);

		Mockito.when(
			assetEntry.isVisible()
		).thenReturn(
			false
		);

		ReflectionTestUtil.setFieldValue(
			assetPublisherViewContentDisplayContext, "_assetEntry", assetEntry);

		Assert.assertFalse(
			assetPublisherViewContentDisplayContext.isAssetEntryVisible());
	}

	private RenderRequest _renderRequest;

}