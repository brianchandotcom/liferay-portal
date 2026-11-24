/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.document.library.thumbnails.internal.processor;

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.document.library.thumbnails.internal.configuration.AMSystemImagesConfiguration;
import com.liferay.adaptive.media.image.finder.AMImageFinder;
import com.liferay.adaptive.media.image.validator.AMImageValidator;
import com.liferay.adaptive.media.processor.AMAsyncProcessor;
import com.liferay.adaptive.media.processor.AMAsyncProcessorLocator;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.InputStream;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Sergio González
 */
public class AMImageEntryProcessorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_amImageEntryProcessor = new AMImageEntryProcessor();

		Mockito.doReturn(
			_amAsyncProcessor
		).when(
			_amAsyncProcessorLocator
		).locateForClass(
			FileVersion.class
		);

		ReflectionTestUtil.setFieldValue(
			_amImageEntryProcessor, "_amAsyncProcessorLocator",
			_amAsyncProcessorLocator);
		ReflectionTestUtil.setFieldValue(
			_amImageEntryProcessor, "_amImageFinder", _amImageFinder);
		ReflectionTestUtil.setFieldValue(
			_amImageEntryProcessor, "_amImageValidator", _amImageValidator);
		ReflectionTestUtil.setFieldValue(
			_amImageEntryProcessor, "_amSystemImagesConfiguration",
			_amSystemImagesConfiguration);
		ReflectionTestUtil.setFieldValue(
			PrefsPropsUtil.class, "_prefsProps", _prefsProps);
	}


	@Test
	public void testGetPreviewAsStream() throws Exception {
		Mockito.when(
			_amImageFinder.getAdaptiveMedias(Mockito.any(Function.class))
		).thenAnswer(
			invocation -> Collections.emptyList()
		);

		InputStream originalInputStream = Mockito.mock(InputStream.class);

		Mockito.when(
			_fileVersion.getContentStream(false)
		).thenReturn(
			originalInputStream
		);

		Assert.assertEquals(
			originalInputStream,
			_amImageEntryProcessor.getPreviewAsStream(_fileVersion));

		UnsafeConsumer<FileVersion, Exception> unsafeConsumer =
			fileVersion -> _amImageEntryProcessor.getPreviewAsStream(
				fileVersion);

		_testWhenAMImageExists(unsafeConsumer);
		_testWhenFileVersionIsInvalid(unsafeConsumer);
		_testWhenNoAMImageExists(unsafeConsumer);
	}

	@Test
	public void testGetPreviewFileSize() throws Exception {
		Mockito.when(
			_amImageFinder.getAdaptiveMedias(Mockito.any(Function.class))
		).thenAnswer(
			invocation -> Collections.emptyList()
		);

		long originalSize = RandomTestUtil.randomLong();

		Mockito.when(
			_fileVersion.getSize()
		).thenReturn(
			originalSize
		);

		Assert.assertEquals(
			originalSize,
			_amImageEntryProcessor.getPreviewFileSize(_fileVersion));

		UnsafeConsumer<FileVersion, Exception> unsafeConsumer =
			fileVersion -> _amImageEntryProcessor.getPreviewFileSize(
				fileVersion);

		_testWhenAMImageExists(unsafeConsumer);
		_testWhenFileVersionIsInvalid(unsafeConsumer);
		_testWhenNoAMImageExists(unsafeConsumer);
	}

	@Test
	public void testGetThumbnailAsStream() throws Exception {
		UnsafeConsumer<FileVersion, Exception> unsafeConsumer =
			fileVersion -> _amImageEntryProcessor.getThumbnailAsStream(
				fileVersion, 0);

		_testWhenAMImageExists(unsafeConsumer);
		_testWhenFileVersionIsInvalid(unsafeConsumer);
		_testWhenNoAMImageExists(unsafeConsumer);
	}

	@Test
	public void testGetThumbnailFileSize() throws Exception {
		UnsafeConsumer<FileVersion, Exception> unsafeConsumer =
			fileVersion -> _amImageEntryProcessor.getThumbnailFileSize(
				fileVersion, 0);

		_testWhenAMImageExists(unsafeConsumer);
		_testWhenFileVersionIsInvalid(unsafeConsumer);
		_testWhenNoAMImageExists(unsafeConsumer);
	}

	@Test
	public void testHasImages() throws Exception {
		UnsafeConsumer<FileVersion, Exception> unsafeConsumer =
			fileVersion -> _amImageEntryProcessor.hasImages(fileVersion);

		_testWhenAMImageExists(unsafeConsumer);
		_testWhenFileVersionIsInvalid(unsafeConsumer);
		_testWhenNoAMImageExists(unsafeConsumer);
	}

	private void _testWhenAMImageExists(
			UnsafeConsumer<FileVersion, Exception> unsafeConsumer)
		throws Exception {

		Mockito.clearInvocations(_amAsyncProcessor);

		Mockito.when(
			_amImageFinder.getAdaptiveMedias(Mockito.any(Function.class))
		).thenAnswer(
			invocation -> Arrays.asList(_adaptiveMedia)
		);

		Mockito.when(
			_amImageValidator.isValid(_fileVersion)
		).thenReturn(
			true
		);

		unsafeConsumer.accept(_fileVersion);

		Mockito.verify(
			_amAsyncProcessor, Mockito.never()
		).triggerProcess(
			Mockito.any(FileVersion.class), Mockito.anyString()
		);
	}

	private void _testWhenFileVersionIsInvalid(
			UnsafeConsumer<FileVersion, Exception> unsafeConsumer)
		throws Exception {

		Mockito.clearInvocations(_amAsyncProcessor);

		Mockito.when(
			_amImageFinder.getAdaptiveMedias(Mockito.any(Function.class))
		).thenAnswer(
			invocation -> Collections.emptyList()
		);

		Mockito.when(
			_amImageValidator.isValid(_fileVersion)
		).thenReturn(
			false
		);

		unsafeConsumer.accept(_fileVersion);

		Mockito.verify(
			_amAsyncProcessor, Mockito.never()
		).triggerProcess(
			Mockito.any(FileVersion.class), Mockito.anyString()
		);
	}

	private void _testWhenNoAMImageExists(
			UnsafeConsumer<FileVersion, Exception> unsafeConsumer)
		throws Exception {

		Mockito.clearInvocations(_amAsyncProcessor);

		Mockito.when(
			_amImageFinder.getAdaptiveMedias(Mockito.any(Function.class))
		).thenAnswer(
			invocation -> Collections.emptyList()
		);

		Mockito.when(
			_amImageValidator.isValid(_fileVersion)
		).thenReturn(
			true
		);

		unsafeConsumer.accept(_fileVersion);

		Mockito.verify(
			_amAsyncProcessor
		).triggerProcess(
			Mockito.any(FileVersion.class), Mockito.anyString()
		);
	}

	private final AdaptiveMedia<?> _adaptiveMedia = Mockito.mock(
		AdaptiveMedia.class);
	private final AMAsyncProcessor<FileVersion, ?> _amAsyncProcessor =
		Mockito.mock(AMAsyncProcessor.class);
	private final AMAsyncProcessorLocator _amAsyncProcessorLocator =
		Mockito.mock(AMAsyncProcessorLocator.class);
	private AMImageEntryProcessor _amImageEntryProcessor;
	private final AMImageFinder _amImageFinder = Mockito.mock(
		AMImageFinder.class);
	private final AMImageValidator _amImageValidator = Mockito.mock(
		AMImageValidator.class);
	private final AMSystemImagesConfiguration _amSystemImagesConfiguration =
		Mockito.mock(AMSystemImagesConfiguration.class);
	private final FileVersion _fileVersion = Mockito.mock(FileVersion.class);
	private final PrefsProps _prefsProps = Mockito.mock(PrefsProps.class);

}