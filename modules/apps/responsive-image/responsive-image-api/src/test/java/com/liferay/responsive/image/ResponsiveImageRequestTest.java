/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Daniel Sanz
 */
public class ResponsiveImageRequestTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test(expected = IllegalArgumentException.class)
	public void testBuilderRejectsMissingImageResource() {
		ResponsiveImageRequest.builder(null);
	}

	@Test
	public void testGetGroupId() {
		long groupId = RandomTestUtil.randomLong();

		ResponsiveImageRequest responsiveImageRequest =
			ResponsiveImageRequest.builder(
				_imageResource
			).groupId(
				groupId
			).build();

		Assert.assertEquals(groupId, responsiveImageRequest.getGroupId());

		responsiveImageRequest = ResponsiveImageRequest.of(_imageResource);

		Assert.assertEquals(0, responsiveImageRequest.getGroupId());
	}

	@Test
	public void testGetHttpServletRequest() {
		ResponsiveImageRequest responsiveImageRequest =
			ResponsiveImageRequest.builder(
				_imageResource
			).httpServletRequest(
				_httpServletRequest
			).build();

		Assert.assertSame(
			_httpServletRequest,
			responsiveImageRequest.getHttpServletRequest());

		responsiveImageRequest = ResponsiveImageRequest.of(_imageResource);

		Assert.assertNull(responsiveImageRequest.getHttpServletRequest());
	}

	@Test
	public void testGetImageResource() {
		ResponsiveImageRequest responsiveImageRequest =
			ResponsiveImageRequest.of(_imageResource);

		Assert.assertSame(
			_imageResource, responsiveImageRequest.getImageResource());
	}

	@Test
	public void testGetPresetName() {
		String presetName = RandomTestUtil.randomString();

		ResponsiveImageRequest responsiveImageRequest =
			ResponsiveImageRequest.builder(
				_imageResource
			).presetName(
				presetName
			).build();

		Assert.assertEquals(presetName, responsiveImageRequest.getPresetName());

		responsiveImageRequest = ResponsiveImageRequest.of(_imageResource);

		Assert.assertNull(responsiveImageRequest.getPresetName());
	}

	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final ImageResource _imageResource = Mockito.mock(
		ImageResource.class);

}