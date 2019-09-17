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

package com.liferay.adaptive.media.image.internal.processor;

import com.liferay.adaptive.media.exception.AMRuntimeException;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.exception.DuplicateAMImageEntryException;
import com.liferay.adaptive.media.image.internal.configuration.AMImageConfigurationEntryImpl;
import com.liferay.adaptive.media.image.internal.scaler.AMImageScaledImageImpl;
import com.liferay.adaptive.media.image.model.AMImageEntry;
import com.liferay.adaptive.media.image.scaler.AMImageScaler;
import com.liferay.adaptive.media.image.scaler.AMImageScalerTracker;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalService;
import com.liferay.adaptive.media.image.validator.AMImageValidator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.image.ImageTool;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.io.InputStream;

import java.util.Collections;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class AMImageProcessorImplTest {

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_amImageProcessorImpl, "_amImageConfigurationHelper",
			_amImageConfigurationHelper);
		ReflectionTestUtil.setFieldValue(
			_amImageProcessorImpl, "_amImageEntryLocalService",
			_amImageEntryLocalService);
		ReflectionTestUtil.setFieldValue(
			_amImageProcessorImpl, "_amImageScalerTracker",
			_amImageScalerTracker);
		ReflectionTestUtil.setFieldValue(
			_amImageProcessorImpl, "_amImageValidator", _amImageValidator);

		ImageToolUtil imageToolUtil = new ImageToolUtil();

		imageToolUtil.setImageTool(_imageTool);
	}

	@Test
	public void testCleanUpFileVersion() throws Exception {
		Mockito.when(
			_amImageValidator.isValid(Mockito.any(FileVersion.class))
		).thenReturn(
			true
		);

		_amImageProcessorImpl.cleanUp(_fileVersion);

		Mockito.verify(
			_amImageEntryLocalService
		).deleteAMImageEntryFileVersion(
			_fileVersion
		);
	}

	@Test(expected = AMRuntimeException.IOException.class)
	public void testCleanUpIOException() throws Exception {
		Mockito.when(
			_amImageValidator.isValid(Mockito.any(FileVersion.class))
		).thenReturn(
			true
		);

		Mockito.doThrow(
			AMRuntimeException.IOException.class
		).when(
			_amImageEntryLocalService
		).deleteAMImageEntryFileVersion(
			Mockito.any(FileVersion.class)
		);

		_amImageProcessorImpl.cleanUp(_fileVersion);
	}

	@Test(expected = PortalException.class)
	public void testCleanUpPortalException() throws Exception {
		Mockito.when(
			_amImageValidator.isValid(Mockito.any(FileVersion.class))
		).thenReturn(
			true
		);

		Mockito.doThrow(
			PortalException.class
		).when(
			_amImageEntryLocalService
		).deleteAMImageEntryFileVersion(
			Mockito.any(FileVersion.class)
		);

		_amImageProcessorImpl.cleanUp(_fileVersion);
	}

	@Test
	public void testCleanUpWhenNotSupported() throws Exception {
		Mockito.when(
			_amImageValidator.isValid(Mockito.any(FileVersion.class))
		).thenReturn(
			false
		);

		_amImageProcessorImpl.cleanUp(_fileVersion);

		Mockito.verify(
			_amImageEntryLocalService, Mockito.never()
		).deleteAMImageEntryFileVersion(
			_fileVersion
		);
	}

	@Test
	public void testProcessConfigurationWhenAMImageEntryAlreadyExists()
		throws Exception {

		Mockito.when(
			_amImageValidator.isValid(_fileVersion)
		).thenReturn(
			true
		);

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Optional.of(
				new AMImageConfigurationEntryImpl(
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(), Collections.emptyMap()))
		);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				Mockito.anyString(), Mockito.anyLong())
		).thenReturn(
			Mockito.mock(AMImageEntry.class)
		);

		Mockito.when(
			_fileVersion.getFileEntry()
		).thenReturn(
			_fileEntry
		);

		Mockito.when(
			_fileEntry.isCheckedOut()
		).thenReturn(
			false
		);

		_amImageProcessorImpl.process(
			_fileVersion, RandomTestUtil.randomString());

		Mockito.verify(
			_amImageScaler, Mockito.never()
		).scaleImage(
			Mockito.any(FileVersion.class),
			Mockito.any(AMImageConfigurationEntry.class)
		);
	}

	@Test
	public void testProcessConfigurationWhenFileEntryIsCheckedOut()
		throws Exception {

		Mockito.when(
			_amImageValidator.isValid(_fileVersion)
		).thenReturn(
			true
		);

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Optional.of(
				new AMImageConfigurationEntryImpl(
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(), Collections.emptyMap()))
		);

		Mockito.when(
			_amImageEntryLocalService.fetchAMImageEntry(
				Mockito.anyString(), Mockito.anyLong())
		).thenReturn(
			Mockito.mock(AMImageEntry.class)
		);

		Mockito.when(
			_fileVersion.getFileEntry()
		).thenReturn(
			_fileEntry
		);

		Mockito.when(
			_fileEntry.isCheckedOut()
		).thenReturn(
			true
		);

		Mockito.when(
			_amImageScalerTracker.getAMImageScaler(Mockito.anyString())
		).thenReturn(
			_amImageScaler
		);

		Mockito.when(
			_amImageScaler.scaleImage(
				Mockito.any(FileVersion.class),
				Mockito.any(AMImageConfigurationEntry.class))
		).thenReturn(
			new AMImageScaledImageImpl(new byte[100], 100, 100)
		);

		_amImageProcessorImpl.process(
			_fileVersion, RandomTestUtil.randomString());

		Mockito.verify(
			_amImageEntryLocalService
		).deleteAMImageEntry(
			Mockito.anyLong()
		);

		Mockito.verify(
			_amImageScaler
		).scaleImage(
			Mockito.any(FileVersion.class),
			Mockito.any(AMImageConfigurationEntry.class)
		);
	}

	@Test
	public void testProcessConfigurationWhenNoAMImageScalerAvailable()
		throws Exception {

		Mockito.when(
			_amImageValidator.isValid(_fileVersion)
		).thenReturn(
			true
		);

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				Mockito.anyLong())
		).thenReturn(
			Collections.singleton(amImageConfigurationEntry)
		);

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Optional.of(amImageConfigurationEntry)
		);

		Mockito.when(
			_amImageScalerTracker.getAMImageScaler(Mockito.anyString())
		).thenReturn(
			null
		);

		_amImageProcessorImpl.process(
			_fileVersion, RandomTestUtil.randomString());

		Mockito.verify(
			_amImageEntryLocalService, Mockito.never()
		).addAMImageEntry(
			Mockito.any(AMImageConfigurationEntry.class),
			Mockito.any(FileVersion.class), Mockito.anyInt(), Mockito.anyInt(),
			Mockito.any(InputStream.class), Mockito.anyLong()
		);
	}

	@Test
	public void testProcessConfigurationWhenNoConfigurationEntry()
		throws Exception {

		Mockito.when(
			_amImageValidator.isValid(_fileVersion)
		).thenReturn(
			true
		);

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Optional.empty()
		);

		_amImageProcessorImpl.process(
			_fileVersion, RandomTestUtil.randomString());

		Mockito.verify(
			_amImageEntryLocalService, Mockito.never()
		).fetchAMImageEntry(
			Mockito.anyString(), Mockito.anyLong()
		);
	}

	@Test
	public void testProcessConfigurationWhenNotSupported() throws Exception {
		Mockito.when(
			_amImageValidator.isValid(Mockito.any(FileVersion.class))
		).thenReturn(
			false
		);

		_amImageProcessorImpl.process(
			_fileVersion, RandomTestUtil.randomString());

		Mockito.verify(
			_amImageConfigurationHelper, Mockito.never()
		).getAMImageConfigurationEntry(
			Mockito.anyLong(), Mockito.anyString()
		);
	}

	@Test(expected = DuplicateAMImageEntryException.class)
	public void testProcessDuplicateAMImageEntryExceptionInImageService()
		throws Exception {

		Mockito.when(
			_amImageValidator.isValid(_fileVersion)
		).thenReturn(
			true
		);

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				Mockito.anyLong())
		).thenReturn(
			Collections.singleton(amImageConfigurationEntry)
		);

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Optional.of(amImageConfigurationEntry)
		);

		Mockito.when(
			_amImageScalerTracker.getAMImageScaler(Mockito.anyString())
		).thenReturn(
			_amImageScaler
		);

		Mockito.when(
			_amImageScaler.scaleImage(_fileVersion, amImageConfigurationEntry)
		).thenReturn(
			new AMImageScaledImageImpl(new byte[100], 150, 200)
		);

		Mockito.doThrow(
			DuplicateAMImageEntryException.class
		).when(
			_amImageEntryLocalService
		).addAMImageEntry(
			Mockito.any(AMImageConfigurationEntry.class),
			Mockito.any(FileVersion.class), Mockito.eq(150), Mockito.eq(200),
			Mockito.any(InputStream.class), Mockito.eq(100L)
		);

		_amImageProcessorImpl.process(_fileVersion);
	}

	@Test
	public void testProcessFileVersion() throws Exception {
		Mockito.when(
			_amImageValidator.isValid(_fileVersion)
		).thenReturn(
			true
		);

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				Mockito.anyLong())
		).thenReturn(
			Collections.singleton(amImageConfigurationEntry)
		);

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Optional.of(amImageConfigurationEntry)
		);

		Mockito.when(
			_amImageScalerTracker.getAMImageScaler(Mockito.anyString())
		).thenReturn(
			_amImageScaler
		);

		Mockito.when(
			_amImageScaler.scaleImage(_fileVersion, amImageConfigurationEntry)
		).thenReturn(
			new AMImageScaledImageImpl(new byte[100], 150, 200)
		);

		_amImageProcessorImpl.process(_fileVersion);

		Mockito.verify(
			_amImageScaler
		).scaleImage(
			_fileVersion, amImageConfigurationEntry
		);

		Mockito.verify(
			_amImageEntryLocalService
		).addAMImageEntry(
			Mockito.any(AMImageConfigurationEntry.class),
			Mockito.any(FileVersion.class), Mockito.eq(150), Mockito.eq(200),
			Mockito.any(InputStream.class), Mockito.eq(100L)
		);
	}

	@Test(expected = AMRuntimeException.InvalidConfiguration.class)
	public void testProcessInvalidConfigurationException() throws Exception {
		Mockito.when(
			_amImageValidator.isValid(_fileVersion)
		).thenReturn(
			true
		);

		Mockito.doThrow(
			AMRuntimeException.InvalidConfiguration.class
		).when(
			_amImageConfigurationHelper
		).getAMImageConfigurationEntries(
			Mockito.anyLong()
		);

		_amImageProcessorImpl.process(_fileVersion);
	}

	@Test(expected = AMRuntimeException.IOException.class)
	public void testProcessIOExceptionInImageProcessor() throws Exception {
		Mockito.when(
			_amImageValidator.isValid(_fileVersion)
		).thenReturn(
			true
		);

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				Mockito.anyLong())
		).thenReturn(
			Collections.singleton(amImageConfigurationEntry)
		);

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Optional.of(amImageConfigurationEntry)
		);

		Mockito.when(
			_amImageScalerTracker.getAMImageScaler(Mockito.anyString())
		).thenReturn(
			_amImageScaler
		);

		Mockito.doThrow(
			AMRuntimeException.IOException.class
		).when(
			_amImageScaler
		).scaleImage(
			_fileVersion, amImageConfigurationEntry
		);

		_amImageProcessorImpl.process(_fileVersion);
	}

	@Test(expected = AMRuntimeException.IOException.class)
	public void testProcessIOExceptionInStorage() throws Exception {
		Mockito.when(
			_amImageValidator.isValid(_fileVersion)
		).thenReturn(
			true
		);

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				Mockito.anyLong())
		).thenReturn(
			Collections.singleton(amImageConfigurationEntry)
		);

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Optional.of(amImageConfigurationEntry)
		);

		Mockito.when(
			_amImageScalerTracker.getAMImageScaler(Mockito.anyString())
		).thenReturn(
			_amImageScaler
		);

		Mockito.when(
			_amImageScaler.scaleImage(_fileVersion, amImageConfigurationEntry)
		).thenReturn(
			new AMImageScaledImageImpl(new byte[100], 150, 200)
		);

		Mockito.doThrow(
			AMRuntimeException.IOException.class
		).when(
			_amImageEntryLocalService
		).addAMImageEntry(
			Mockito.any(AMImageConfigurationEntry.class),
			Mockito.any(FileVersion.class), Mockito.eq(150), Mockito.eq(200),
			Mockito.any(InputStream.class), Mockito.eq(100L)
		);

		_amImageProcessorImpl.process(_fileVersion);
	}

	@Test
	public void testProcessWhenNoConfigurationEntries() throws Exception {
		Mockito.when(
			_amImageValidator.isValid(Mockito.any(FileVersion.class))
		).thenReturn(
			true
		);

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				Mockito.anyLong())
		).thenReturn(
			Collections.emptyList()
		);

		_amImageProcessorImpl.process(_fileVersion);

		Mockito.verify(
			_amImageScaler, Mockito.never()
		).scaleImage(
			Mockito.any(FileVersion.class),
			Mockito.any(AMImageConfigurationEntry.class)
		);

		Mockito.verify(
			_amImageEntryLocalService, Mockito.never()
		).addAMImageEntry(
			Mockito.any(AMImageConfigurationEntry.class),
			Mockito.any(FileVersion.class), Mockito.anyInt(), Mockito.anyInt(),
			Mockito.any(InputStream.class), Mockito.anyLong()
		);
	}

	@Test
	public void testProcessWhenNotSupported() throws Exception {
		Mockito.when(
			_amImageValidator.isValid(Mockito.any(FileVersion.class))
		).thenReturn(
			false
		);

		_amImageProcessorImpl.process(_fileVersion);

		Mockito.verify(
			_amImageConfigurationHelper, Mockito.never()
		).getAMImageConfigurationEntries(
			Mockito.anyLong()
		);
	}

	private final AMImageConfigurationHelper _amImageConfigurationHelper =
		Mockito.mock(AMImageConfigurationHelper.class);
	private final AMImageEntryLocalService _amImageEntryLocalService =
		Mockito.mock(AMImageEntryLocalService.class);
	private final AMImageProcessorImpl _amImageProcessorImpl =
		new AMImageProcessorImpl();
	private final AMImageScaler _amImageScaler = Mockito.mock(
		AMImageScaler.class);
	private final AMImageScalerTracker _amImageScalerTracker = Mockito.mock(
		AMImageScalerTracker.class);
	private final AMImageValidator _amImageValidator = Mockito.mock(
		AMImageValidator.class);
	private final FileEntry _fileEntry = Mockito.mock(FileEntry.class);
	private final FileVersion _fileVersion = Mockito.mock(FileVersion.class);
	private final ImageTool _imageTool = Mockito.mock(ImageTool.class);

}