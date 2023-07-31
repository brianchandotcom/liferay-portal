/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.finder;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.exception.AMRuntimeException;
import com.liferay.adaptive.media.finder.AMQuery;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.finder.AMImageQueryBuilder;
import com.liferay.adaptive.media.image.internal.configuration.AMImageConfigurationEntryImpl;
import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.adaptive.media.image.model.AMImageEntry;
import com.liferay.adaptive.media.image.processor.AMImageAttribute;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalService;
import com.liferay.adaptive.media.image.url.AMImageURLFactory;
import com.liferay.adaptive.media.processor.AMProcessor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.InputStream;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class AMImageFinderImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_amImageFinderImpl, "_amImageConfigurationHelper",
			_amImageConfigurationHelper);
		ReflectionTestUtil.setFieldValue(
			_amImageFinderImpl, "_amImageEntryLocalService",
			_amImageEntryLocalService);
		ReflectionTestUtil.setFieldValue(
			_amImageFinderImpl, "_amImageMimeTypeProvider",
			_amImageMimeTypeProvider);
		ReflectionTestUtil.setFieldValue(
			_amImageFinderImpl, "_amImageURLFactory", _amImageURLFactory);
	}

	@Test(expected = PortalException.class)
	public void testFileEntryGetFileVersionFails() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				new HashMap<>());

		AMImageQueryBuilder.ConfigurationStatus enabledConfigurationStatus =
			AMImageQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				_fileEntry.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
		).thenReturn(
			Collections.singleton(amImageConfigurationEntry)
		);

		Mockito.when(
			_fileEntry.getFileVersion()
		).thenThrow(
			PortalException.class
		);

		Mockito.when(
			_amImageMimeTypeProvider.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		List<AdaptiveMedia<AMProcessor<FileVersion>>> adaptiveMedias =
			_amImageFinderImpl.getAdaptiveMedias(
				amImageQueryBuilder -> amImageQueryBuilder.forFileEntry(
					_fileEntry
				).done());

		adaptiveMedias.size();
	}

	@Test
	public void testFileEntryGetMediaWithNoAttributes() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				new HashMap<>());

		AMImageQueryBuilder.ConfigurationStatus enabledConfigurationStatus =
			AMImageQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
		).thenReturn(
			Collections.singleton(amImageConfigurationEntry)
		);

		Mockito.when(
			_fileEntry.getFileVersion()
		).thenReturn(
			_fileVersion
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AMImageEntry amImageEntry = _mockImage(800, 900, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry
		);

		Mockito.when(
			_amImageMimeTypeProvider.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		List<AdaptiveMedia<AMProcessor<FileVersion>>> adaptiveMedias =
			_amImageFinderImpl.getAdaptiveMedias(
				amImageQueryBuilder -> amImageQueryBuilder.forFileEntry(
					_fileEntry
				).done());

		Assert.assertEquals(
			adaptiveMedias.toString(), 1, adaptiveMedias.size());
	}

	@Test
	public void testGetMediaAttributes() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				MapUtil.fromArray("max-height", "100", "max-width", "200"));

		AMImageQueryBuilder.ConfigurationStatus enabledConfigurationStatus =
			AMImageQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
		).thenReturn(
			Collections.singleton(amImageConfigurationEntry)
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AMImageEntry amImageEntry = _mockImage(99, 199, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry
		);

		Mockito.when(
			_amImageMimeTypeProvider.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		List<AdaptiveMedia<AMProcessor<FileVersion>>> adaptiveMedias =
			_amImageFinderImpl.getAdaptiveMedias(
				amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
					_fileVersion
				).done());

		Assert.assertEquals(
			adaptiveMedias.toString(), 1, adaptiveMedias.size());

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia =
			adaptiveMedias.get(0);

		Assert.assertEquals(
			Integer.valueOf(99),
			adaptiveMedia.getValue(AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT));
		Assert.assertEquals(
			Integer.valueOf(199),
			adaptiveMedia.getValue(AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH));
	}

	@Test
	public void testGetMediaAttributesOrderByAsc() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry1 =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				MapUtil.fromArray("max-height", "100", "max-width", "200"));
		AMImageConfigurationEntry amImageConfigurationEntry2 =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				MapUtil.fromArray("max-height", "100", "max-width", "800"));
		AMImageConfigurationEntry amImageConfigurationEntry3 =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				MapUtil.fromArray("max-height", "100", "max-width", "400"));

		List<AMImageConfigurationEntry> amImageConfigurationEntries =
			Arrays.asList(
				amImageConfigurationEntry1, amImageConfigurationEntry2,
				amImageConfigurationEntry3);

		AMImageQueryBuilder.ConfigurationStatus enabledConfigurationStatus =
			AMImageQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
		).thenReturn(
			amImageConfigurationEntries
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AMImageEntry amImageEntry1 = _mockImage(99, 199, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry1.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry1
		);

		AMImageEntry amImageEntry2 = _mockImage(99, 799, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry2.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry2
		);

		AMImageEntry amImageEntry3 = _mockImage(99, 399, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry3.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry3
		);

		Mockito.when(
			_amImageMimeTypeProvider.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		List<AdaptiveMedia<AMProcessor<FileVersion>>> adaptiveMedias =
			_amImageFinderImpl.getAdaptiveMedias(
				amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
					_fileVersion
				).orderBy(
					AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH,
					AMImageQueryBuilder.SortOrder.ASC
				).done());

		Assert.assertEquals(
			adaptiveMedias.toString(), 3, adaptiveMedias.size());

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia1 =
			adaptiveMedias.get(0);

		Assert.assertEquals(
			Integer.valueOf(199),
			adaptiveMedia1.getValue(AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH));

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia2 =
			adaptiveMedias.get(1);

		Assert.assertEquals(
			Integer.valueOf(399),
			adaptiveMedia2.getValue(AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH));

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia3 =
			adaptiveMedias.get(2);

		Assert.assertEquals(
			Integer.valueOf(799),
			adaptiveMedia3.getValue(AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH));
	}

	@Test
	public void testGetMediaAttributesOrderByDesc() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry1 =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				MapUtil.fromArray("max-height", "100", "max-width", "200"));
		AMImageConfigurationEntry amImageConfigurationEntry2 =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				MapUtil.fromArray("max-height", "100", "max-width", "800"));
		AMImageConfigurationEntry amImageConfigurationEntry3 =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				MapUtil.fromArray("max-height", "100", "max-width", "400"));

		List<AMImageConfigurationEntry> amImageConfigurationEntries =
			Arrays.asList(
				amImageConfigurationEntry1, amImageConfigurationEntry2,
				amImageConfigurationEntry3);

		AMImageQueryBuilder.ConfigurationStatus enabledConfigurationStatus =
			AMImageQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
		).thenReturn(
			amImageConfigurationEntries
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AMImageEntry amImageEntry1 = _mockImage(99, 199, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry1.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry1
		);

		AMImageEntry amImageEntry2 = _mockImage(99, 799, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry2.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry2
		);

		AMImageEntry amImageEntry3 = _mockImage(99, 399, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry3.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry3
		);

		Mockito.when(
			_amImageMimeTypeProvider.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		List<AdaptiveMedia<AMProcessor<FileVersion>>> adaptiveMedias =
			_amImageFinderImpl.getAdaptiveMedias(
				amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
					_fileVersion
				).orderBy(
					AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH,
					AMImageQueryBuilder.SortOrder.DESC
				).done());

		Assert.assertEquals(
			adaptiveMedias.toString(), 3, adaptiveMedias.size());

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia1 =
			adaptiveMedias.get(0);

		Assert.assertEquals(
			Integer.valueOf(799),
			adaptiveMedia1.getValue(AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH));

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia2 =
			adaptiveMedias.get(1);

		Assert.assertEquals(
			Integer.valueOf(399),
			adaptiveMedia2.getValue(AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH));

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia3 =
			adaptiveMedias.get(2);

		Assert.assertEquals(
			Integer.valueOf(199),
			adaptiveMedia3.getValue(AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMediaAttributesWithNonbuilderQuery() throws Exception {
		_amImageFinderImpl.getAdaptiveMedias(
			amImageQueryBuilder ->
				new AMQuery<FileVersion, AMProcessor<FileVersion>>() {
				});
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMediaAttributesWithNullQuery() throws Exception {
		_amImageFinderImpl.getAdaptiveMedias(amImageQueryBuilder -> null);
	}

	@Test(expected = AMRuntimeException.InvalidConfiguration.class)
	public void testGetMediaConfigurationError() throws Exception {
		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				Mockito.anyLong(), Mockito.any(Predicate.class))
		).thenThrow(
			AMRuntimeException.InvalidConfiguration.class
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			StringUtil.randomString()
		);

		Mockito.when(
			_amImageMimeTypeProvider.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		_amImageFinderImpl.getAdaptiveMedias(
			amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
				_fileVersion
			).done());
	}

	@Test
	public void testGetMediaInputStream() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				Collections.emptyMap());

		AMImageQueryBuilder.ConfigurationStatus enabledConfigurationStatus =
			AMImageQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
		).thenReturn(
			Collections.singleton(amImageConfigurationEntry)
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AMImageEntry amImageEntry = _mockImage(800, 900, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry
		);

		Mockito.when(
			_amImageMimeTypeProvider.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		InputStream inputStream = Mockito.mock(InputStream.class);

		Mockito.when(
			_amImageEntryLocalService.getAMImageEntryContentStream(
				amImageConfigurationEntry, _fileVersion)
		).thenReturn(
			inputStream
		);

		List<AdaptiveMedia<AMProcessor<FileVersion>>> adaptiveMedias =
			_amImageFinderImpl.getAdaptiveMedias(
				amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
					_fileVersion
				).done());

		Assert.assertEquals(
			adaptiveMedias.toString(), 1, adaptiveMedias.size());

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia =
			adaptiveMedias.get(0);

		Assert.assertSame(inputStream, adaptiveMedia.getInputStream());
	}

	@Test
	public void testGetMediaMissingAttribute() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				MapUtil.fromArray("max-height", "100"));

		AMImageQueryBuilder.ConfigurationStatus enabledConfigurationStatus =
			AMImageQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
		).thenReturn(
			Collections.singleton(amImageConfigurationEntry)
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AMImageEntry amImageEntry = _mockImage(99, 1000, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry
		);

		Mockito.when(
			_amImageMimeTypeProvider.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		List<AdaptiveMedia<AMProcessor<FileVersion>>> adaptiveMedias =
			_amImageFinderImpl.getAdaptiveMedias(
				amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
					_fileVersion
				).done());

		Assert.assertEquals(
			adaptiveMedias.toString(), 1, adaptiveMedias.size());

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia =
			adaptiveMedias.get(0);

		Assert.assertEquals(
			Integer.valueOf(99),
			adaptiveMedia.getValue(AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT));
		Assert.assertEquals(
			Integer.valueOf(1000),
			adaptiveMedia.getValue(AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH));
	}

	@Test
	public void testGetMediaQueryWith100Height() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry1 =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				MapUtil.fromArray("max-height", "100", "max-width", "200"));

		AMImageConfigurationEntry amImageConfigurationEntry2 =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				MapUtil.fromArray("max-height", "200", "max-width", "200"));

		AMImageQueryBuilder.ConfigurationStatus enabledConfigurationStatus =
			AMImageQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
		).thenReturn(
			Arrays.asList(
				amImageConfigurationEntry1, amImageConfigurationEntry2)
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AMImageEntry amImageEntry1 = _mockImage(99, 199, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry1.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry1
		);

		AMImageEntry amImageEntry2 = _mockImage(199, 199, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry2.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry2
		);

		Mockito.when(
			_amImageMimeTypeProvider.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		List<AdaptiveMedia<AMProcessor<FileVersion>>> adaptiveMedias =
			_amImageFinderImpl.getAdaptiveMedias(
				amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
					_fileVersion
				).with(
					AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT, 100
				).done());

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia0 =
			adaptiveMedias.get(0);

		Assert.assertEquals(
			Integer.valueOf(99),
			adaptiveMedia0.getValue(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT));

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia1 =
			adaptiveMedias.get(1);

		Assert.assertEquals(
			Integer.valueOf(199),
			adaptiveMedia1.getValue(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT));
	}

	@Test
	public void testGetMediaQueryWith200Height() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry1 =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				MapUtil.fromArray("max-height", "100", "max-width", "200"));

		AMImageConfigurationEntry amImageConfigurationEntry2 =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				MapUtil.fromArray("max-height", "200", "max-width", "200"));

		AMImageQueryBuilder.ConfigurationStatus enabledConfigurationStatus =
			AMImageQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
		).thenReturn(
			Arrays.asList(
				amImageConfigurationEntry1, amImageConfigurationEntry2)
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AMImageEntry amImageEntry1 = _mockImage(99, 199, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry1.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry1
		);

		AMImageEntry amImageEntry2 = _mockImage(199, 199, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry2.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry2
		);

		Mockito.when(
			_amImageMimeTypeProvider.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		List<AdaptiveMedia<AMProcessor<FileVersion>>> adaptiveMedias =
			_amImageFinderImpl.getAdaptiveMedias(
				amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
					_fileVersion
				).with(
					AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT, 200
				).done());

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia0 =
			adaptiveMedias.get(0);

		Assert.assertEquals(
			Integer.valueOf(199),
			adaptiveMedia0.getValue(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT));

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia1 =
			adaptiveMedias.get(1);

		Assert.assertEquals(
			Integer.valueOf(99),
			adaptiveMedia1.getValue(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT));
	}

	@Test
	public void testGetMediaQueryWith200HeightAspectRatio() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry1 =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				MapUtil.fromArray("max-height", "100", "max-width", "200"));

		AMImageConfigurationEntry amImageConfigurationEntry2 =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				MapUtil.fromArray("max-height", "200", "max-width", "100"));

		AMImageQueryBuilder.ConfigurationStatus enabledConfigurationStatus =
			AMImageQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
		).thenReturn(
			Arrays.asList(
				amImageConfigurationEntry1, amImageConfigurationEntry2)
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AMImageEntry amImageEntry1 = _mockImage(99, 199, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry1.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry1
		);

		AMImageEntry amImageEntry2 = _mockImage(55, 99, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry2.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry2
		);

		Mockito.when(
			_amImageMimeTypeProvider.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		List<AdaptiveMedia<AMProcessor<FileVersion>>> adaptiveMedias =
			_amImageFinderImpl.getAdaptiveMedias(
				amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
					_fileVersion
				).with(
					AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT, 200
				).done());

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia0 =
			adaptiveMedias.get(0);

		Assert.assertEquals(
			Integer.valueOf(99),
			adaptiveMedia0.getValue(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT));

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia1 =
			adaptiveMedias.get(1);

		Assert.assertEquals(
			Integer.valueOf(55),
			adaptiveMedia1.getValue(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT));
	}

	@Test
	public void testGetMediaQueryWithConfigurationAttribute() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry1 =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), "small",
				MapUtil.fromArray("max-height", "100", "max-width", "200"));

		AMImageConfigurationEntry amImageConfigurationEntry2 =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), "medium",
				MapUtil.fromArray("max-height", "200", "max-width", "200"));

		AMImageQueryBuilder.ConfigurationStatus anyConfigurationStatus =
			AMImageQueryBuilder.ConfigurationStatus.ANY;

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				_fileVersion.getCompanyId(),
				anyConfigurationStatus.getPredicate())
		).thenReturn(
			Arrays.asList(
				amImageConfigurationEntry1, amImageConfigurationEntry2)
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AMImageEntry amImageEntry1 = _mockImage(99, 199, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry1.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry1
		);

		AMImageEntry amImageEntry2 = _mockImage(199, 199, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry2.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry2
		);

		Mockito.when(
			_amImageMimeTypeProvider.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		List<AdaptiveMedia<AMProcessor<FileVersion>>> adaptiveMedias =
			_amImageFinderImpl.getAdaptiveMedias(
				amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
					_fileVersion
				).forConfiguration(
					"small"
				).done());

		Assert.assertEquals(
			adaptiveMedias.toString(), 1, adaptiveMedias.size());

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia0 =
			adaptiveMedias.get(0);

		Assert.assertEquals(
			"small",
			(String)adaptiveMedia0.getValue(
				AMAttribute.getConfigurationUuidAMAttribute()));
	}

	@Test
	public void testGetMediaQueryWithConfigurationStatusAttributeForConfiguration()
		throws Exception {

		AMImageConfigurationEntry amImageConfigurationEntry1 =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), "small",
				MapUtil.fromArray("max-height", "100", "max-width", "200"));

		AMImageConfigurationEntry amImageConfigurationEntry2 =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				"medium",
				MapUtil.fromArray("max-height", "200", "max-width", "200"),
				false);

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				_fileVersion.getCompanyId(),
				AMImageQueryBuilder.ConfigurationStatus.ANY.getPredicate())
		).thenReturn(
			Arrays.asList(
				amImageConfigurationEntry1, amImageConfigurationEntry2)
		);

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				_fileVersion.getCompanyId(),
				AMImageQueryBuilder.ConfigurationStatus.DISABLED.getPredicate())
		).thenReturn(
			Arrays.asList(amImageConfigurationEntry2)
		);

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				_fileVersion.getCompanyId(),
				AMImageQueryBuilder.ConfigurationStatus.ENABLED.getPredicate())
		).thenReturn(
			Arrays.asList(amImageConfigurationEntry1)
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AMImageEntry amImageEntry1 = _mockImage(99, 199, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry1.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry1
		);

		AMImageEntry amImageEntry2 = _mockImage(199, 199, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry2.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry2
		);

		Mockito.when(
			_amImageMimeTypeProvider.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		List<AdaptiveMedia<AMProcessor<FileVersion>>> adaptiveMedias =
			_amImageFinderImpl.getAdaptiveMedias(
				amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
					_fileVersion
				).withConfigurationStatus(
					AMImageQueryBuilder.ConfigurationStatus.ENABLED
				).forConfiguration(
					"small"
				).done());

		Assert.assertEquals(
			adaptiveMedias.toString(), 1, adaptiveMedias.size());

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia0 =
			adaptiveMedias.get(0);

		Assert.assertEquals(
			"small",
			(String)adaptiveMedia0.getValue(
				AMAttribute.getConfigurationUuidAMAttribute()));

		adaptiveMedias = _amImageFinderImpl.getAdaptiveMedias(
			amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
				_fileVersion
			).withConfigurationStatus(
				AMImageQueryBuilder.ConfigurationStatus.ANY
			).forConfiguration(
				"small"
			).done());

		Assert.assertEquals(
			adaptiveMedias.toString(), 1, adaptiveMedias.size());

		adaptiveMedia0 = adaptiveMedias.get(0);

		Assert.assertEquals(
			"small",
			(String)adaptiveMedia0.getValue(
				AMAttribute.getConfigurationUuidAMAttribute()));

		adaptiveMedias = _amImageFinderImpl.getAdaptiveMedias(
			amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
				_fileVersion
			).withConfigurationStatus(
				AMImageQueryBuilder.ConfigurationStatus.DISABLED
			).forConfiguration(
				"small"
			).done());

		Assert.assertEquals(
			adaptiveMedias.toString(), 0, adaptiveMedias.size());
	}

	@Test
	public void testGetMediaQueryWithConfigurationStatusAttributeWithWidth()
		throws Exception {

		AMImageConfigurationEntry amImageConfigurationEntry1 =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), "1",
				MapUtil.fromArray("max-height", "100"));

		AMImageConfigurationEntry amImageConfigurationEntry2 =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				"2", MapUtil.fromArray("max-height", "200"), false);

		AMImageQueryBuilder.ConfigurationStatus enabledConfigurationStatus =
			AMImageQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
		).thenReturn(
			Arrays.asList(amImageConfigurationEntry1)
		);

		AMImageQueryBuilder.ConfigurationStatus disabledConfigurationStatus =
			AMImageQueryBuilder.ConfigurationStatus.DISABLED;

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				_fileVersion.getCompanyId(),
				disabledConfigurationStatus.getPredicate())
		).thenReturn(
			Arrays.asList(amImageConfigurationEntry2)
		);

		AMImageQueryBuilder.ConfigurationStatus allConfigurationStatus =
			AMImageQueryBuilder.ConfigurationStatus.ANY;

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				_fileVersion.getCompanyId(),
				allConfigurationStatus.getPredicate())
		).thenReturn(
			Arrays.asList(
				amImageConfigurationEntry1, amImageConfigurationEntry2)
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AMImageEntry amImageEntry1 = _mockImage(100, 1000, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry1.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry1
		);

		AMImageEntry amImageEntry2 = _mockImage(200, 1000, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry2.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry2
		);

		Mockito.when(
			_amImageMimeTypeProvider.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		List<AdaptiveMedia<AMProcessor<FileVersion>>> adaptiveMedias =
			_amImageFinderImpl.getAdaptiveMedias(
				amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
					_fileVersion
				).withConfigurationStatus(
					enabledConfigurationStatus
				).with(
					AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH, 100
				).done());

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia0 =
			adaptiveMedias.get(0);

		Assert.assertEquals(
			"1",
			adaptiveMedia0.getValue(
				AMAttribute.getConfigurationUuidAMAttribute()));

		adaptiveMedias = _amImageFinderImpl.getAdaptiveMedias(
			amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
				_fileVersion
			).withConfigurationStatus(
				disabledConfigurationStatus
			).with(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH, 100
			).done());

		adaptiveMedia0 = adaptiveMedias.get(0);

		Assert.assertEquals(
			"2",
			adaptiveMedia0.getValue(
				AMAttribute.getConfigurationUuidAMAttribute()));

		adaptiveMedias = _amImageFinderImpl.getAdaptiveMedias(
			amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
				_fileVersion
			).withConfigurationStatus(
				allConfigurationStatus
			).with(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH, 100
			).done());

		adaptiveMedia0 = adaptiveMedias.get(0);

		Assert.assertEquals(
			"1",
			adaptiveMedia0.getValue(
				AMAttribute.getConfigurationUuidAMAttribute()));

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia1 =
			adaptiveMedias.get(1);

		Assert.assertEquals(
			"2",
			adaptiveMedia1.getValue(
				AMAttribute.getConfigurationUuidAMAttribute()));
	}

	@Test
	public void testGetMediaQueryWithNoMatchingAttributes() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry1 =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				MapUtil.fromArray("max-height", "100"));

		AMImageConfigurationEntry amImageConfigurationEntry2 =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				MapUtil.fromArray("max-height", "200"));

		AMImageQueryBuilder.ConfigurationStatus enabledConfigurationStatus =
			AMImageQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
		).thenReturn(
			Arrays.asList(
				amImageConfigurationEntry1, amImageConfigurationEntry2)
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AMImageEntry amImageEntry1 = _mockImage(99, 1000, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry1.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry1
		);

		AMImageEntry amImageEntry2 = _mockImage(199, 1000, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry2.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry2
		);

		Mockito.when(
			_amImageMimeTypeProvider.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		List<AdaptiveMedia<AMProcessor<FileVersion>>> adaptiveMedias =
			_amImageFinderImpl.getAdaptiveMedias(
				amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
					_fileVersion
				).with(
					AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH, 100
				).done());

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia0 =
			adaptiveMedias.get(0);

		Assert.assertEquals(
			Integer.valueOf(99),
			adaptiveMedia0.getValue(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT));

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia1 =
			adaptiveMedias.get(1);

		Assert.assertEquals(
			Integer.valueOf(199),
			adaptiveMedia1.getValue(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT));
	}

	@Test
	public void testGetMediaWhenNotSupported() throws Exception {
		Mockito.when(
			_amImageMimeTypeProvider.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			false
		);

		List<AdaptiveMedia<AMProcessor<FileVersion>>> adaptiveMedias =
			_amImageFinderImpl.getAdaptiveMedias(
				amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
					_fileVersion
				).done());

		Object[] adaptiveMediaArray = adaptiveMedias.toArray();

		Assert.assertEquals(
			Arrays.toString(adaptiveMediaArray), 0, adaptiveMediaArray.length);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMediaWithNullFunction() throws Exception {
		_amImageFinderImpl.getAdaptiveMedias(null);
	}

	@Test
	public void testGetSVGMediaInputStream() throws Exception {
		InputStream inputStream = Mockito.mock(InputStream.class);

		Mockito.when(
			_fileVersion.getContentStream(Mockito.anyBoolean())
		).thenReturn(
			inputStream
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			ContentTypes.IMAGE_SVG_XML
		);

		Mockito.when(
			_fileVersion.getSize()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			_amImageMimeTypeProvider.isMimeTypeSupported(
				ContentTypes.IMAGE_SVG_XML)
		).thenReturn(
			true
		);

		List<AdaptiveMedia<AMProcessor<FileVersion>>> adaptiveMedias =
			_amImageFinderImpl.getAdaptiveMedias(
				amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
					_fileVersion
				).done());

		Assert.assertEquals(
			adaptiveMedias.toString(), 1, adaptiveMedias.size());

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia =
			adaptiveMedias.get(0);

		Assert.assertSame(inputStream, adaptiveMedia.getInputStream());

		Assert.assertEquals(
			_fileVersion.getSize(),
			(long)adaptiveMedia.getValue(
				AMAttribute.getContentLengthAMAttribute()));
	}

	@Test
	public void testMediaLazilyDelegatesOnStorageInputStream()
		throws Exception {

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				MapUtil.fromArray("max-height", "100", "max-width", "200"));

		AMImageQueryBuilder.ConfigurationStatus enabledConfigurationStatus =
			AMImageQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
		).thenReturn(
			Collections.singleton(amImageConfigurationEntry)
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AMImageEntry amImageEntry = _mockImage(99, 99, 1000L);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				amImageConfigurationEntry.getUUID(),
				_fileVersion.getFileVersionId())
		).thenReturn(
			amImageEntry
		);

		Mockito.when(
			_amImageMimeTypeProvider.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		List<AdaptiveMedia<AMProcessor<FileVersion>>> adaptiveMedias =
			_amImageFinderImpl.getAdaptiveMedias(
				amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
					_fileVersion
				).done());

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia =
			adaptiveMedias.get(0);

		adaptiveMedia.getInputStream();

		Mockito.verify(
			_amImageEntryLocalService
		).getAMImageEntryContentStream(
			amImageConfigurationEntry, _fileVersion
		);
	}

	private AMImageEntry _mockImage(int height, int width, long size) {
		AMImageEntry amImageEntry = Mockito.mock(AMImageEntry.class);

		Mockito.when(
			amImageEntry.getHeight()
		).thenReturn(
			height
		);

		Mockito.when(
			amImageEntry.getWidth()
		).thenReturn(
			width
		);

		Mockito.when(
			amImageEntry.getSize()
		).thenReturn(
			size
		);

		return amImageEntry;
	}

	private final AMImageConfigurationHelper _amImageConfigurationHelper =
		Mockito.mock(AMImageConfigurationHelper.class);
	private final AMImageEntryLocalService _amImageEntryLocalService =
		Mockito.mock(AMImageEntryLocalService.class);
	private final AMImageFinderImpl _amImageFinderImpl =
		new AMImageFinderImpl();
	private final AMImageMimeTypeProvider _amImageMimeTypeProvider =
		Mockito.mock(AMImageMimeTypeProvider.class);
	private final AMImageURLFactory _amImageURLFactory = Mockito.mock(
		AMImageURLFactory.class);
	private final FileEntry _fileEntry = Mockito.mock(FileEntry.class);
	private final FileVersion _fileVersion = Mockito.mock(FileVersion.class);

}