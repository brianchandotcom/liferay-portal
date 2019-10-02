/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.adaptive.media.image.internal.media.query;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.finder.AMQuery;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.finder.AMImageFinder;
import com.liferay.adaptive.media.image.finder.AMImageQueryBuilder;
import com.liferay.adaptive.media.image.internal.configuration.AMImageAttributeMapping;
import com.liferay.adaptive.media.image.internal.finder.AMImageQueryBuilderImpl;
import com.liferay.adaptive.media.image.internal.processor.AMImage;
import com.liferay.adaptive.media.image.media.query.Condition;
import com.liferay.adaptive.media.image.media.query.MediaQuery;
import com.liferay.adaptive.media.image.processor.AMImageAttribute;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;
import com.liferay.adaptive.media.image.url.AMImageURLFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.net.URI;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Alejandro Tardín
 */
@RunWith(MockitoJUnitRunner.class)
public class MediaQueryProviderImplTest {

	@Before
	public void setUp() throws PortalException {
		Mockito.when(
			_amImageFinder.getAdaptiveMediaStream(Mockito.any(Function.class))
		).thenAnswer(
			invocation -> Stream.empty()
		);

		Mockito.when(
			_fileEntry.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		Mockito.when(
			_fileEntry.getFileVersion()
		).thenReturn(
			_fileVersion
		);

		ReflectionTestUtil.setFieldValue(
			_mediaQueryProviderImpl, "_amImageConfigurationHelper",
			_amImageConfigurationHelper);
		ReflectionTestUtil.setFieldValue(
			_mediaQueryProviderImpl, "_amImageFinder", _amImageFinder);
		ReflectionTestUtil.setFieldValue(
			_mediaQueryProviderImpl, "_amImageURLFactory", _amImageURLFactory);
	}

	@Test
	public void testCreatesAMediaQuery() throws Exception {
		_addConfigs(
			_createAMImageConfigurationEntry("uuid", 800, 1989, "adaptiveURL"));

		List<MediaQuery> mediaQueries = _mediaQueryProviderImpl.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 1, mediaQueries.size());

		MediaQuery mediaQuery = mediaQueries.get(0);

		Assert.assertEquals("adaptiveURL", mediaQuery.getSrc());

		List<Condition> conditions = mediaQuery.getConditions();

		Assert.assertEquals(conditions.toString(), 1, conditions.size());

		_assertCondition(conditions.get(0), "max-width", "1989px");
	}

	@Test
	public void testCreatesSeveralMediaQueries() throws Exception {
		_addConfigs(
			_createAMImageConfigurationEntry(
				"uuid1", 800, 1986, "adaptiveURL1"),
			_createAMImageConfigurationEntry(
				"uuid2", 800, 1989, "adaptiveURL2"));

		List<MediaQuery> mediaQueries = _mediaQueryProviderImpl.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 2, mediaQueries.size());

		MediaQuery mediaQuery1 = mediaQueries.get(0);

		Assert.assertEquals("adaptiveURL1", mediaQuery1.getSrc());

		List<Condition> conditions1 = mediaQuery1.getConditions();

		Assert.assertEquals(conditions1.toString(), 1, conditions1.size());

		_assertCondition(conditions1.get(0), "max-width", "1986px");

		MediaQuery mediaQuery2 = mediaQueries.get(1);

		Assert.assertEquals("adaptiveURL2", mediaQuery2.getSrc());

		List<Condition> conditions2 = mediaQuery2.getConditions();

		Assert.assertEquals(conditions2.toString(), 2, conditions2.size());

		_assertCondition(conditions2.get(0), "max-width", "1989px");
		_assertCondition(conditions2.get(1), "min-width", "1986px");
	}

	@Test
	public void testCreatesSeveralMediaQueriesSortedByWidth() throws Exception {
		_addConfigs(
			_createAMImageConfigurationEntry(
				"uuid2", 800, 1989, "adaptiveURL2"),
			_createAMImageConfigurationEntry(
				"uuid1", 800, 1986, "adaptiveURL1"));

		List<MediaQuery> mediaQueries = _mediaQueryProviderImpl.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 2, mediaQueries.size());

		MediaQuery mediaQuery1 = mediaQueries.get(0);

		Assert.assertEquals("adaptiveURL1", mediaQuery1.getSrc());

		List<Condition> conditions1 = mediaQuery1.getConditions();

		Assert.assertEquals(conditions1.toString(), 1, conditions1.size());

		_assertCondition(conditions1.get(0), "max-width", "1986px");

		MediaQuery mediaQuery2 = mediaQueries.get(1);

		Assert.assertEquals("adaptiveURL2", mediaQuery2.getSrc());

		List<Condition> conditions2 = mediaQuery2.getConditions();

		Assert.assertEquals(conditions2.toString(), 2, conditions2.size());

		_assertCondition(conditions2.get(0), "max-width", "1989px");
		_assertCondition(conditions2.get(1), "min-width", "1986px");
	}

	@Test
	public void testFiltersOutAdaptiveMediasWithNoWidth() throws Exception {
		int auto = 0;

		_addConfigs(
			_createAMImageConfigurationEntry(
				"normal", 2048, 1024, StringPool.BLANK),
			_createAMImageConfigurationEntry(
				"wauto", 900, auto, StringPool.BLANK));

		_addAdaptiveMedias(
			_fileEntry, _createAdaptiveMedia("normal", 1334, 750, "normalURL"));

		List<MediaQuery> mediaQueries = _mediaQueryProviderImpl.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 1, mediaQueries.size());

		_assertMediaQuery(mediaQueries.get(0), "normalURL", 750);
	}

	@Test
	public void testHDMediaQueriesApplies() throws Exception {
		_addConfigs(
			_createAMImageConfigurationEntry(
				"uuid1", 450, 800, "http://small.adaptive.com"),
			_createAMImageConfigurationEntry(
				"uuid2", 900, 1600, "http://small.hd.adaptive.com"),
			_createAMImageConfigurationEntry(
				"uuid3", 1900, 2500, "http://big.adaptive.com"));

		List<MediaQuery> mediaQueries = _mediaQueryProviderImpl.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 3, mediaQueries.size());

		MediaQuery mediaQuery1 = mediaQueries.get(0);

		Assert.assertEquals(
			"http://small.adaptive.com, http://small.hd.adaptive.com 2x",
			mediaQuery1.getSrc());

		List<Condition> conditions1 = mediaQuery1.getConditions();

		Assert.assertEquals(conditions1.toString(), 1, conditions1.size());

		_assertCondition(conditions1.get(0), "max-width", "800px");

		MediaQuery mediaQuery2 = mediaQueries.get(1);

		Assert.assertEquals(
			"http://small.hd.adaptive.com", mediaQuery2.getSrc());

		List<Condition> conditions2 = mediaQuery2.getConditions();

		Assert.assertEquals(conditions2.toString(), 2, conditions2.size());

		_assertCondition(conditions2.get(0), "max-width", "1600px");
		_assertCondition(conditions2.get(1), "min-width", "800px");

		MediaQuery mediaQuery3 = mediaQueries.get(2);

		Assert.assertEquals("http://big.adaptive.com", mediaQuery3.getSrc());

		List<Condition> conditions3 = mediaQuery3.getConditions();

		Assert.assertEquals(conditions3.toString(), 2, conditions3.size());

		_assertCondition(conditions3.get(0), "max-width", "2500px");
		_assertCondition(conditions3.get(1), "min-width", "1600px");
	}

	@Test
	public void testHDMediaQueryAppliesWhenHeightHas1PXLessThanExpected()
		throws Exception {

		_addConfigs(
			_createAMImageConfigurationEntry(
				"uuid1", 450, 800, "http://small.adaptive.com"),
			_createAMImageConfigurationEntry(
				"uuid2", 899, 1600, "http://small.hd.adaptive.com"));

		List<MediaQuery> mediaQueries = _mediaQueryProviderImpl.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 2, mediaQueries.size());

		MediaQuery mediaQuery1 = mediaQueries.get(0);

		Assert.assertEquals(
			"http://small.adaptive.com, http://small.hd.adaptive.com 2x",
			mediaQuery1.getSrc());

		List<Condition> conditions1 = mediaQuery1.getConditions();

		Assert.assertEquals(conditions1.toString(), 1, conditions1.size());

		_assertCondition(conditions1.get(0), "max-width", "800px");

		MediaQuery mediaQuery2 = mediaQueries.get(1);

		Assert.assertEquals(
			"http://small.hd.adaptive.com", mediaQuery2.getSrc());

		List<Condition> conditions2 = mediaQuery2.getConditions();

		Assert.assertEquals(conditions2.toString(), 2, conditions2.size());

		_assertCondition(conditions2.get(0), "max-width", "1600px");
		_assertCondition(conditions2.get(1), "min-width", "800px");
	}

	@Test
	public void testHDMediaQueryAppliesWhenHeightHas1PXMoreThanExpected()
		throws Exception {

		_addConfigs(
			_createAMImageConfigurationEntry(
				"uuid1", 450, 800, "http://small.adaptive.com"),
			_createAMImageConfigurationEntry(
				"uuid2", 901, 1600, "http://small.hd.adaptive.com"));

		List<MediaQuery> mediaQueries = _mediaQueryProviderImpl.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 2, mediaQueries.size());

		MediaQuery mediaQuery1 = mediaQueries.get(0);

		Assert.assertEquals(
			"http://small.adaptive.com, http://small.hd.adaptive.com 2x",
			mediaQuery1.getSrc());

		List<Condition> conditions1 = mediaQuery1.getConditions();

		Assert.assertEquals(conditions1.toString(), 1, conditions1.size());

		_assertCondition(conditions1.get(0), "max-width", "800px");

		MediaQuery mediaQuery2 = mediaQueries.get(1);

		Assert.assertEquals(
			"http://small.hd.adaptive.com", mediaQuery2.getSrc());

		List<Condition> conditions2 = mediaQuery2.getConditions();

		Assert.assertEquals(conditions2.toString(), 2, conditions2.size());

		_assertCondition(conditions2.get(0), "max-width", "1600px");
		_assertCondition(conditions2.get(1), "min-width", "800px");
	}

	@Test
	public void testHDMediaQueryAppliesWhenWidthHas1PXLessThanExpected()
		throws Exception {

		_addConfigs(
			_createAMImageConfigurationEntry(
				"uuid1", 450, 800, "http://small.adaptive.com"),
			_createAMImageConfigurationEntry(
				"uuid2", 900, 1599, "http://small.hd.adaptive.com"));

		List<MediaQuery> mediaQueries = _mediaQueryProviderImpl.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 2, mediaQueries.size());

		MediaQuery mediaQuery1 = mediaQueries.get(0);

		Assert.assertEquals(
			"http://small.adaptive.com, http://small.hd.adaptive.com 2x",
			mediaQuery1.getSrc());

		List<Condition> conditions1 = mediaQuery1.getConditions();

		Assert.assertEquals(conditions1.toString(), 1, conditions1.size());

		_assertCondition(conditions1.get(0), "max-width", "800px");

		Condition condition = conditions1.get(0);

		Assert.assertEquals("max-width", condition.getAttribute());
		Assert.assertEquals("800px", condition.getValue());

		MediaQuery mediaQuery2 = mediaQueries.get(1);

		List<Condition> conditions2 = mediaQuery2.getConditions();

		Assert.assertEquals(
			"http://small.hd.adaptive.com", mediaQuery2.getSrc());

		Assert.assertEquals(conditions2.toString(), 2, conditions2.size());

		_assertCondition(conditions2.get(0), "max-width", "1599px");
		_assertCondition(conditions2.get(1), "min-width", "800px");
	}

	@Test
	public void testHDMediaQueryAppliesWhenWidthHas1PXMoreThanExpected()
		throws Exception {

		_addConfigs(
			_createAMImageConfigurationEntry(
				"uuid", 450, 800, "http://small.adaptive.com"),
			_createAMImageConfigurationEntry(
				"uuid", 900, 1601, "http://small.hd.adaptive.com"));

		List<MediaQuery> mediaQueries = _mediaQueryProviderImpl.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 2, mediaQueries.size());

		MediaQuery mediaQuery1 = mediaQueries.get(0);

		Assert.assertEquals(
			"http://small.adaptive.com, http://small.hd.adaptive.com 2x",
			mediaQuery1.getSrc());

		List<Condition> conditions1 = mediaQuery1.getConditions();

		Assert.assertEquals(conditions1.toString(), 1, conditions1.size());

		_assertCondition(conditions1.get(0), "max-width", "800px");

		MediaQuery mediaQuery2 = mediaQueries.get(1);

		Assert.assertEquals(
			"http://small.hd.adaptive.com", mediaQuery2.getSrc());

		List<Condition> conditions2 = mediaQuery2.getConditions();

		Assert.assertEquals(conditions2.toString(), 2, conditions2.size());

		_assertCondition(conditions2.get(0), "max-width", "1601px");
		_assertCondition(conditions2.get(1), "min-width", "800px");
	}

	@Test
	public void testHDMediaQueryNotAppliesWhenHeightHas2PXLessThanExpected()
		throws Exception {

		_addConfigs(
			_createAMImageConfigurationEntry(
				"uuid", 450, 800, "http://small.adaptive.com"),
			_createAMImageConfigurationEntry(
				"uuid", 898, 1600, "http://small.hd.adaptive.com"));

		List<MediaQuery> mediaQueries = _mediaQueryProviderImpl.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 2, mediaQueries.size());

		MediaQuery mediaQuery1 = mediaQueries.get(0);

		Assert.assertEquals("http://small.adaptive.com", mediaQuery1.getSrc());

		List<Condition> conditions1 = mediaQuery1.getConditions();

		Assert.assertEquals(conditions1.toString(), 1, conditions1.size());

		_assertCondition(conditions1.get(0), "max-width", "800px");

		MediaQuery mediaQuery2 = mediaQueries.get(1);

		Assert.assertEquals(
			"http://small.hd.adaptive.com", mediaQuery2.getSrc());

		List<Condition> conditions2 = mediaQuery2.getConditions();

		Assert.assertEquals(conditions2.toString(), 2, conditions2.size());

		_assertCondition(conditions2.get(0), "max-width", "1600px");
		_assertCondition(conditions2.get(1), "min-width", "800px");
	}

	@Test
	public void testHDMediaQueryNotAppliesWhenHeightHas2PXMoreThanExpected()
		throws Exception {

		_addConfigs(
			_createAMImageConfigurationEntry(
				"uuid", 450, 800, "http://small.adaptive.com"),
			_createAMImageConfigurationEntry(
				"uuid", 902, 1600, "http://small.hd.adaptive.com"));

		List<MediaQuery> mediaQueries = _mediaQueryProviderImpl.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 2, mediaQueries.size());

		MediaQuery mediaQuery1 = mediaQueries.get(0);

		Assert.assertEquals("http://small.adaptive.com", mediaQuery1.getSrc());

		List<Condition> conditions1 = mediaQuery1.getConditions();

		Assert.assertEquals(conditions1.toString(), 1, conditions1.size());

		_assertCondition(conditions1.get(0), "max-width", "800px");

		MediaQuery mediaQuery2 = mediaQueries.get(1);

		Assert.assertEquals(
			"http://small.hd.adaptive.com", mediaQuery2.getSrc());

		List<Condition> conditions2 = mediaQuery2.getConditions();

		Assert.assertEquals(conditions2.toString(), 2, conditions2.size());

		_assertCondition(conditions2.get(0), "max-width", "1600px");
		_assertCondition(conditions2.get(1), "min-width", "800px");
	}

	@Test
	public void testHDMediaQueryNotAppliesWhenWidthHas2PXLessThanExpected()
		throws Exception {

		_addConfigs(
			_createAMImageConfigurationEntry(
				"uuid", 450, 800, "http://small.adaptive.com"),
			_createAMImageConfigurationEntry(
				"uuid", 900, 1598, "http://small.hd.adaptive.com"));

		List<MediaQuery> mediaQueries = _mediaQueryProviderImpl.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 2, mediaQueries.size());

		MediaQuery mediaQuery1 = mediaQueries.get(0);

		Assert.assertEquals("http://small.adaptive.com", mediaQuery1.getSrc());

		List<Condition> conditions1 = mediaQuery1.getConditions();

		Assert.assertEquals(conditions1.toString(), 1, conditions1.size());

		_assertCondition(conditions1.get(0), "max-width", "800px");

		MediaQuery mediaQuery2 = mediaQueries.get(1);

		Assert.assertEquals(
			"http://small.hd.adaptive.com", mediaQuery2.getSrc());

		List<Condition> conditions2 = mediaQuery2.getConditions();

		Assert.assertEquals(conditions2.toString(), 2, conditions2.size());

		_assertCondition(conditions2.get(0), "max-width", "1598px");
		_assertCondition(conditions2.get(1), "min-width", "800px");
	}

	@Test
	public void testHDMediaQueryNotAppliesWhenWidthHas2PXMoreThanExpected()
		throws Exception {

		_addConfigs(
			_createAMImageConfigurationEntry(
				"uuid", 450, 800, "http://small.adaptive.com"),
			_createAMImageConfigurationEntry(
				"uuid", 900, 1602, "http://small.hd.adaptive.com"));

		List<MediaQuery> mediaQueries = _mediaQueryProviderImpl.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 2, mediaQueries.size());

		MediaQuery mediaQuery1 = mediaQueries.get(0);

		Assert.assertEquals("http://small.adaptive.com", mediaQuery1.getSrc());

		List<Condition> conditions1 = mediaQuery1.getConditions();

		Assert.assertEquals(conditions1.toString(), 1, conditions1.size());

		_assertCondition(conditions1.get(0), "max-width", "800px");

		MediaQuery mediaQuery2 = mediaQueries.get(1);

		Assert.assertEquals(
			"http://small.hd.adaptive.com", mediaQuery2.getSrc());

		List<Condition> conditions2 = mediaQuery2.getConditions();

		Assert.assertEquals(conditions2.toString(), 2, conditions2.size());

		_assertCondition(conditions2.get(0), "max-width", "1602px");
		_assertCondition(conditions2.get(1), "min-width", "800px");
	}

	@Test
	public void testReturnsNoMediaQueriesIfThereAreNoConfigs()
		throws Exception {

		_addConfigs();

		List<MediaQuery> mediaQueries = _mediaQueryProviderImpl.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 0, mediaQueries.size());
	}

	@Test
	public void testUsesTheValuesFromConfigIfNoAdaptiveMediasArePresent()
		throws Exception {

		int auto = 0;

		_addConfigs(
			_createAMImageConfigurationEntry("hauto", auto, 600, "hautoURL"),
			_createAMImageConfigurationEntry("low", 300, 300, "lowURL"),
			_createAMImageConfigurationEntry("normal", 2048, 1024, "normalURL"),
			_createAMImageConfigurationEntry("wauto", 900, auto, "wautoURL"));

		List<MediaQuery> mediaQueries = _mediaQueryProviderImpl.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 3, mediaQueries.size());

		_assertMediaQuery(mediaQueries.get(0), "lowURL", 300);
		_assertMediaQuery(mediaQueries.get(1), "hautoURL", 300, 600);
		_assertMediaQuery(mediaQueries.get(2), "normalURL", 600, 1024);
	}

	@Test
	public void testUsesTheValuesFromTheAdaptiveMediasIfPresent()
		throws Exception {

		int auto = 0;

		_addConfigs(
			_createAMImageConfigurationEntry(
				"hauto", auto, 600, StringPool.BLANK),
			_createAMImageConfigurationEntry("low", 300, 300, StringPool.BLANK),
			_createAMImageConfigurationEntry(
				"normal", 2048, 1024, StringPool.BLANK),
			_createAMImageConfigurationEntry(
				"wauto", 900, auto, StringPool.BLANK));

		_addAdaptiveMedias(
			_fileEntry, _createAdaptiveMedia("low", 300, 169, "lowURL"),
			_createAdaptiveMedia("wauto", 900, 506, "wautoURL"),
			_createAdaptiveMedia("hauto", 1067, 600, "hautoURL"),
			_createAdaptiveMedia("normal", 1334, 750, "normalURL"));

		List<MediaQuery> mediaQueries = _mediaQueryProviderImpl.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 4, mediaQueries.size());

		_assertMediaQuery(mediaQueries.get(0), "lowURL", 169);
		_assertMediaQuery(mediaQueries.get(1), "wautoURL", 169, 506);
		_assertMediaQuery(mediaQueries.get(2), "hautoURL", 506, 600);
		_assertMediaQuery(mediaQueries.get(3), "normalURL", 600, 750);
	}

	private void _addAdaptiveMedias(
			FileEntry fileEntry,
			AdaptiveMedia<AMImageProcessor>... adaptiveMedias)
		throws PortalException {

		Mockito.when(
			_amImageFinder.getAdaptiveMediaStream(Mockito.any(Function.class))
		).thenAnswer(
			invocation -> {
				Function<AMImageQueryBuilder, AMQuery>
					amImageQueryBuilderFunction = invocation.getArgumentAt(
						0, Function.class);

				AMImageQueryBuilderImpl amImageQueryBuilderImpl =
					new AMImageQueryBuilderImpl();

				AMQuery amQuery = amImageQueryBuilderFunction.apply(
					amImageQueryBuilderImpl);

				if (!AMImageQueryBuilderImpl.AM_QUERY.equals(amQuery)) {
					return Stream.empty();
				}

				for (AdaptiveMedia<AMImageProcessor> adaptiveMedia :
						adaptiveMedias) {

					Optional<String> optional = adaptiveMedia.getValueOptional(
						AMAttribute.getConfigurationUuidAMAttribute());

					String configurationUuid = optional.get();

					if (Objects.equals(
							fileEntry.getFileVersion(),
							amImageQueryBuilderImpl.getFileVersion()) &&
						configurationUuid.equals(
							amImageQueryBuilderImpl.getConfigurationUuid())) {

						return Stream.of(adaptiveMedia);
					}
				}

				return Stream.empty();
			}
		);
	}

	private void _addConfigs(
			AMImageConfigurationEntry... amImageConfigurationEntries)
		throws Exception {

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				_COMPANY_ID)
		).thenReturn(
			Arrays.asList(amImageConfigurationEntries)
		);
	}

	private void _assertCondition(
		Condition condition, String attribute, String value) {

		Assert.assertEquals(attribute, condition.getAttribute());
		Assert.assertEquals(value, condition.getValue());
	}

	private void _assertMediaQuery(
		MediaQuery mediaQuery, String url, int maxWidth) {

		Assert.assertEquals(url, mediaQuery.getSrc());

		List<Condition> conditions = mediaQuery.getConditions();

		Assert.assertEquals(conditions.toString(), 1, conditions.size());

		_assertCondition(conditions.get(0), "max-width", maxWidth + "px");
	}

	private void _assertMediaQuery(
		MediaQuery mediaQuery, String url, int minWidth, int maxWidth) {

		Assert.assertEquals(url, mediaQuery.getSrc());

		List<Condition> conditions = mediaQuery.getConditions();

		Assert.assertEquals(conditions.toString(), 2, conditions.size());

		_assertCondition(conditions.get(0), "max-width", maxWidth + "px");
		_assertCondition(conditions.get(1), "min-width", minWidth + "px");
	}

	private AdaptiveMedia<AMImageProcessor> _createAdaptiveMedia(
			String amImageConfigurationEntryUuid, int height, int width,
			String url)
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put(
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT.getName(),
			String.valueOf(height));

		properties.put(
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH.getName(),
			String.valueOf(width));

		AMAttribute amAttribute = AMAttribute.getConfigurationUuidAMAttribute();

		properties.put(amAttribute.getName(), amImageConfigurationEntryUuid);

		return new AMImage(
			() -> null, AMImageAttributeMapping.fromProperties(properties),
			URI.create(url));
	}

	private AMImageConfigurationEntry _createAMImageConfigurationEntry(
			final String uuid, final int height, final int width, String url)
		throws Exception {

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntry() {

				@Override
				public String getDescription() {
					return StringPool.BLANK;
				}

				@Override
				public String getName() {
					return uuid;
				}

				@Override
				public Map<String, String> getProperties() {
					Map<String, String> properties = new HashMap<>();

					properties.put("max-height", String.valueOf(height));
					properties.put("max-width", String.valueOf(width));

					return properties;
				}

				@Override
				public String getUUID() {
					return uuid;
				}

				@Override
				public boolean isEnabled() {
					return true;
				}

			};

		Mockito.when(
			_amImageURLFactory.createFileEntryURL(
				_fileEntry.getFileVersion(), amImageConfigurationEntry)
		).thenReturn(
			URI.create(url)
		);

		return amImageConfigurationEntry;
	}

	private static final long _COMPANY_ID = 1L;

	@Mock
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Mock
	private AMImageFinder _amImageFinder;

	@Mock
	private AMImageURLFactory _amImageURLFactory;

	@Mock
	private FileEntry _fileEntry;

	@Mock
	private FileVersion _fileVersion;

	private final MediaQueryProviderImpl _mediaQueryProviderImpl =
		new MediaQueryProviderImpl();

}