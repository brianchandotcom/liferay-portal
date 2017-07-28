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

package com.liferay.adaptive.media.image.internal.handler;

import com.liferay.adaptive.media.exception.AdaptiveMediaRuntimeException;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.adaptive.media.image.internal.configuration.AdaptiveMediaImageConfigurationHelperImpl;
import com.liferay.adaptive.media.image.internal.util.Tuple;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class PathInterpreterTest {

	@Before
	public void setUp() {
		_pathInterpreter.setDLAppService(_dlAppService);
		_pathInterpreter.setAdaptiveMediaImageConfigurationHelper(
			_adaptiveMediaImageConfigurationHelper);
	}

	@Test
	public void testFileEntryPath() throws Exception {
		Mockito.when(
			_dlAppService.getFileEntry(Mockito.anyLong())
		).thenReturn(
			_fileEntry
		);

		Mockito.when(
			_fileEntry.getFileVersion()
		).thenReturn(
			_fileVersion
		);

		Mockito.when(
			_adaptiveMediaImageConfigurationHelper.
				getAdaptiveMediaImageConfigurationEntry(
					Mockito.anyLong(), Mockito.eq("x"))
		).thenReturn(
			Optional.of(_configurationEntry)
		);

		_pathInterpreter.interpretPath("/image/0/x/foo.jpg");

		Mockito.verify(
			_dlAppService
		).getFileEntry(
			0
		);

		Mockito.verify(
			_fileVersion
		).getCompanyId();

		Mockito.verify(
			_configurationEntry
		).getProperties();

		Mockito.verify(
			_configurationEntry
		).getUUID();
	}

	@Test(expected = AdaptiveMediaRuntimeException.class)
	public void testFileEntryPathDLAppFailure() throws Exception {
		Mockito.when(
			_dlAppService.getFileEntry(0)
		).thenThrow(
			PortalException.class
		);

		_pathInterpreter.interpretPath("/image/0/x/foo.jpg");
	}

	@Test(expected = AdaptiveMediaRuntimeException.class)
	public void testFileEntryPathGetFileVersionFailure() throws Exception {
		Mockito.when(
			_dlAppService.getFileEntry(0)
		).thenReturn(
			_fileEntry
		);

		Mockito.when(
			_fileEntry.getFileVersion()
		).thenThrow(
			PortalException.class
		);

		_pathInterpreter.interpretPath("/image/0/x/foo.jpg");
	}

	@Test
	public void testFileVersionPath() throws Exception {
		Mockito.when(
			_dlAppService.getFileVersion(1)
		).thenReturn(
			_fileVersion
		);

		Mockito.when(
			_adaptiveMediaImageConfigurationHelper.
				getAdaptiveMediaImageConfigurationEntry(
					Mockito.anyLong(), Mockito.eq("x"))
		).thenReturn(
			Optional.of(_configurationEntry)
		);

		_pathInterpreter.interpretPath("/image/0/1/x/foo.jpg");

		Mockito.verify(
			_dlAppService
		).getFileEntry(
			0
		);

		Mockito.verify(
			_dlAppService
		).getFileVersion(
			1
		);

		Mockito.verify(
			_fileVersion
		).getCompanyId();

		Mockito.verify(
			_configurationEntry
		).getProperties();

		Mockito.verify(
			_configurationEntry
		).getUUID();
	}

	@Test(expected = AdaptiveMediaRuntimeException.class)
	public void testFileVersionPathDLAppFailure() throws Exception {
		Mockito.when(
			_dlAppService.getFileVersion(1)
		).thenThrow(
			PortalException.class
		);

		_pathInterpreter.interpretPath("/image/0/1/x/foo.jpg");
	}

	@Test
	public void testNonMatchingPathInfo() {
		Optional<Tuple<FileVersion, Map<String, String>>> resultOptional =
			_pathInterpreter.interpretPath("/" + StringUtil.randomString());

		Assert.assertFalse(resultOptional.isPresent());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullPathInfoFails() {
		_pathInterpreter.interpretPath(null);
	}

	private final AdaptiveMediaImageConfigurationHelper
		_adaptiveMediaImageConfigurationHelper = Mockito.mock(
			AdaptiveMediaImageConfigurationHelperImpl.class);
	private final AdaptiveMediaImageConfigurationEntry _configurationEntry =
		Mockito.mock(AdaptiveMediaImageConfigurationEntry.class);
	private final DLAppService _dlAppService = Mockito.mock(DLAppService.class);
	private final FileEntry _fileEntry = Mockito.mock(FileEntry.class);
	private final FileVersion _fileVersion = Mockito.mock(FileVersion.class);
	private final PathInterpreter _pathInterpreter = new PathInterpreter();

}