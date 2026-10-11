/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image.internal;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.responsive.image.ImageResource;
import com.liferay.responsive.image.ResponsiveImage;
import com.liferay.responsive.image.ResponsiveImageCandidate;
import com.liferay.responsive.image.ResponsiveImageRequest;
import com.liferay.responsive.image.ResponsiveImageSource;
import com.liferay.responsive.image.internal.configuration.ResponsiveImageConfigurationHelper;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Daniel Sanz
 */
@FeatureFlag("LPD-94784")
public class ResponsiveImageMarkupRendererImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		Mockito.when(
			_imageResource.getURL()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		ReflectionTestUtil.setFieldValue(
			_responsiveImageMarkupRendererImpl,
			"_responsiveImageConfigurationHelper",
			_responsiveImageConfigurationHelper);
		ReflectionTestUtil.setFieldValue(
			_responsiveImageMarkupRendererImpl, "_responsiveImageFactory",
			_responsiveImageFactory);
	}

	@FeatureFlag(enable = false, value = "LPD-94784")
	@Test
	public void testRenderDeclinesWhenTheFeatureFlagIsDisabled()
		throws Exception {

		_mockResponsiveImage(false, _newResponsiveImageSource());

		Assert.assertNull(
			_responsiveImageMarkupRendererImpl.render(
				_ORIGINAL_IMG_TAG, _responsiveImageRequest));

		Mockito.verifyNoInteractions(_responsiveImageFactory);
	}

	@Test
	public void testRenderImgForASingleSource() throws Exception {
		_mockResponsiveImage(
			false,
			ResponsiveImageSource.builder(
			).candidates(
				Arrays.asList(
					_newResponsiveImageCandidate(
						"/documents/1/2/photo.jpg?width=320", 320),
					_newResponsiveImageCandidate())
			).query(
				null
			).sizes(
				"100vw"
			).build());

		String markup = _responsiveImageMarkupRendererImpl.render(
			_ORIGINAL_IMG_TAG, _responsiveImageRequest);

		Assert.assertTrue(
			markup,
			markup.contains("/documents/1/2/photo.jpg?width=320 320w, "));
		Assert.assertTrue(markup, markup.startsWith("<img "));
		Assert.assertFalse(markup, markup.contains("<picture>"));
		Assert.assertTrue(markup, markup.contains("alt=\"A photo\""));
		Assert.assertFalse(markup, markup.contains("loading="));
		Assert.assertTrue(markup, markup.contains("sizes=\"100vw\""));
	}

	@Test
	public void testRenderInjectsIntoTheImgTagRatherThanOneNamedAfterIt()
		throws Exception {

		_mockResponsiveImage(false, _newResponsiveImageSource());

		String markup = _responsiveImageMarkupRendererImpl.render(
			"<imgcaption>A photo</imgcaption><img " +
				"src=\"/documents/1/2/photo.jpg\" />",
			_responsiveImageRequest);

		Assert.assertTrue(markup, markup.contains("<img srcset=\""));
		Assert.assertTrue(
			markup, markup.startsWith("<imgcaption>A photo</imgcaption>"));
	}

	@Test
	public void testRenderLoadingLazyFromTheModel() throws Exception {
		_mockResponsiveImage(true, _newResponsiveImageSource());

		String markup = _responsiveImageMarkupRendererImpl.render(
			_ORIGINAL_IMG_TAG, _responsiveImageRequest);

		Assert.assertTrue(markup, markup.contains("loading=\"lazy\""));
	}

	@Test
	public void testRenderLoadingLazyWhenOtherLoadingAttributeIsPresent()
		throws Exception {

		_mockResponsiveImage(true, _newResponsiveImageSource());

		String dataAttributeName =
			"data-" + RandomTestUtil.randomString() + "-loading";

		String markup = _responsiveImageMarkupRendererImpl.render(
			"<img " + dataAttributeName +
				"=\"eager\" src=\"/documents/1/2/photo.jpg\" />",
			_responsiveImageRequest);

		Assert.assertTrue(
			markup, markup.contains(dataAttributeName + "=\"eager\""));
		Assert.assertTrue(markup, markup.contains("loading=\"lazy\""));
	}

	@Test
	public void testRenderLoadingLazyWhenTheAuthorAlreadySetLoading()
		throws Exception {

		_mockResponsiveImage(true, _newResponsiveImageSource());

		String markup = _responsiveImageMarkupRendererImpl.render(
			"<img loading=\"eager\" src=\"/documents/1/2/photo.jpg\" />",
			_responsiveImageRequest);

		Assert.assertTrue(markup, markup.contains("loading=\"eager\""));
		Assert.assertFalse(markup, markup.contains("loading=\"lazy\""));
	}

	@Test
	public void testRenderPictureForMultipleSources() throws Exception {
		_mockResponsiveImage(
			false,
			ResponsiveImageSource.builder(
			).candidates(
				Collections.singletonList(_newResponsiveImageCandidate())
			).query(
				"(max-width: 767px)"
			).sizes(
				RandomTestUtil.randomString()
			).build(),
			ResponsiveImageSource.builder(
			).candidates(
				Collections.singletonList(_newResponsiveImageCandidate())
			).query(
				"(min-width: 768px)"
			).sizes(
				"50vw"
			).build());

		String markup = _responsiveImageMarkupRendererImpl.render(
			_ORIGINAL_IMG_TAG, _responsiveImageRequest);

		Assert.assertTrue(markup, markup.endsWith("</picture>"));
		Assert.assertTrue(markup, markup.startsWith("<picture>"));
		Assert.assertTrue(
			markup, markup.contains("media=\"(max-width: 767px)\""));
		Assert.assertTrue(
			markup, markup.contains("media=\"(min-width: 768px)\""));

		Assert.assertTrue(markup, markup.contains("sizes=\"50vw\""));
	}

	@Test
	public void testRenderSizesDropsAutoOnASourceElement() throws Exception {
		_mockResponsiveImage(
			true,
			ResponsiveImageSource.builder(
			).candidates(
				Arrays.asList(
					_newResponsiveImageCandidate(),
					_newResponsiveImageCandidate())
			).query(
				"(max-width: 767px)"
			).sizes(
				"auto, 100vw"
			).build(),
			_newResponsiveImageSource(RandomTestUtil.randomString()));

		String markup = _responsiveImageMarkupRendererImpl.render(
			_ORIGINAL_IMG_TAG, _responsiveImageRequest);

		Assert.assertTrue(
			markup,
			markup.contains("<source media=\"(max-width: 767px)\" srcset=") &&
			markup.contains("sizes=\"100vw\""));
		Assert.assertFalse(markup, markup.contains("sizes=\"auto, 100vw\""));
	}

	@Test
	public void testRenderSizesDropsAutoWhenTheAuthorSetLoading()
		throws Exception {

		_mockResponsiveImage(true, _newResponsiveImageSource("auto, 100vw"));

		String markup = _responsiveImageMarkupRendererImpl.render(
			"<img loading=\"eager\" src=\"/documents/1/2/photo.jpg\" />",
			_responsiveImageRequest);

		Assert.assertTrue(markup, markup.contains("sizes=\"100vw\""));
	}

	@Test
	public void testRenderSizesDropsAutoWhenTheImageIsNotLazyLoaded()
		throws Exception {

		_mockResponsiveImage(
			false, _newResponsiveImageSource("auto, 100vw, 50vw"));

		String markup = _responsiveImageMarkupRendererImpl.render(
			_ORIGINAL_IMG_TAG, _responsiveImageRequest);

		Assert.assertTrue(markup, markup.contains("sizes=\"100vw, 50vw\""));
	}

	@Test
	public void testRenderSizesIsLeftAloneWhenTheAuthorLedWithAuto()
		throws Exception {

		_mockResponsiveImage(true, _newResponsiveImageSource("auto, 100vw"));

		String markup = _responsiveImageMarkupRendererImpl.render(
			_ORIGINAL_IMG_TAG, _responsiveImageRequest);

		Assert.assertTrue(markup, markup.contains("sizes=\"auto, 100vw\""));
	}

	@Test
	public void testRenderSizesIsLeftAloneWhenTheImageIsLazyLoaded()
		throws Exception {

		_mockResponsiveImage(true, _newResponsiveImageSource("100vw"));

		String markup = _responsiveImageMarkupRendererImpl.render(
			_ORIGINAL_IMG_TAG, _responsiveImageRequest);

		Assert.assertFalse(markup, markup.contains("auto"));
		Assert.assertTrue(markup, markup.contains("sizes=\"100vw\""));
	}

	@Test
	public void testRenderSizesIsLeftAloneWhenTheImageIsNotLazyLoaded()
		throws Exception {

		_mockResponsiveImage(false, _newResponsiveImageSource("100vw"));

		String markup = _responsiveImageMarkupRendererImpl.render(
			_ORIGINAL_IMG_TAG, _responsiveImageRequest);

		Assert.assertTrue(markup, markup.contains("sizes=\"100vw\""));
	}

	@Test
	public void testRenderSizesIsOmittedWhenAutoIsAllAndTheImageIsNotLazyLoaded()
		throws Exception {

		_mockResponsiveImage(false, _newResponsiveImageSource("auto"));

		String markup = _responsiveImageMarkupRendererImpl.render(
			_ORIGINAL_IMG_TAG, _responsiveImageRequest);

		Assert.assertFalse(markup, markup.contains("sizes="));
	}

	@Test
	public void testRenderSizesKeepsAutoWhenDataLoadingPrecedesLoading()
		throws Exception {

		_mockResponsiveImage(false, _newResponsiveImageSource("auto, 100vw"));

		String markup = _responsiveImageMarkupRendererImpl.render(
			"<img data-loading=\"eager\" loading=\"lazy\" " +
				"src=\"/documents/1/2/photo.jpg\" />",
			_responsiveImageRequest);

		Assert.assertTrue(markup, markup.contains("sizes=\"auto, 100vw\""));
	}

	@Test
	public void testRenderSizesKeepsAutoWhenTheAuthorSetLoadingLazy()
		throws Exception {

		_mockResponsiveImage(false, _newResponsiveImageSource("auto, 100vw"));

		String markup = _responsiveImageMarkupRendererImpl.render(
			"<img loading=\"lazy\" src=\"/documents/1/2/photo.jpg\" />",
			_responsiveImageRequest);

		Assert.assertTrue(markup, markup.contains("sizes=\"auto, 100vw\""));
	}

	private void _mockResponsiveImage(
			boolean lazyLoading,
			ResponsiveImageSource... responsiveImageSources)
		throws Exception {

		Mockito.when(
			_responsiveImageFactory.create(
				Mockito.any(ResponsiveImageRequest.class))
		).thenReturn(
			ResponsiveImage.builder(
			).lazyLoading(
				lazyLoading
			).sources(
				Arrays.asList(responsiveImageSources)
			).build()
		);
	}

	private ResponsiveImageCandidate _newResponsiveImageCandidate() {
		return _newResponsiveImageCandidate(
			RandomTestUtil.randomString(), RandomTestUtil.randomInt());
	}

	private ResponsiveImageCandidate _newResponsiveImageCandidate(
		String url, int width) {

		return ResponsiveImageCandidate.builder(
			url
		).width(
			width
		).build();
	}

	private ResponsiveImageSource _newResponsiveImageSource() {
		return _newResponsiveImageSource(RandomTestUtil.randomString());
	}

	private ResponsiveImageSource _newResponsiveImageSource(String sizes) {
		return ResponsiveImageSource.builder(
		).candidates(
			Arrays.asList(
				_newResponsiveImageCandidate(), _newResponsiveImageCandidate())
		).query(
			null
		).sizes(
			sizes
		).build();
	}

	private static final String _ORIGINAL_IMG_TAG =
		"<img alt=\"A photo\" src=\"/documents/1/2/photo.jpg\" />";

	private final ImageResource _imageResource = Mockito.mock(
		ImageResource.class);
	private final ResponsiveImageConfigurationHelper
		_responsiveImageConfigurationHelper = Mockito.mock(
			ResponsiveImageConfigurationHelper.class);
	private final ResponsiveImageFactory _responsiveImageFactory = Mockito.mock(
		ResponsiveImageFactory.class);
	private final ResponsiveImageMarkupRendererImpl
		_responsiveImageMarkupRendererImpl =
			new ResponsiveImageMarkupRendererImpl();
	private final ResponsiveImageRequest _responsiveImageRequest =
		ResponsiveImageRequest.of(_imageResource);

}