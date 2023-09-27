/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.handler;

import com.liferay.adaptive.media.exception.AMRuntimeException;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.internal.configuration.AMImageConfigurationHelperImpl;
import com.liferay.adaptive.media.image.internal.util.Tuple;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class PathInterpreterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_pathInterpreter, "_amImageConfigurationHelper",
			_amImageConfigurationHelper);
		ReflectionTestUtil.setFieldValue(
			_pathInterpreter, "_dlAppService", _dlAppService);
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
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				Mockito.anyLong(), Mockito.eq("x"))
		).thenReturn(
			_amImageConfigurationEntry
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
			_amImageConfigurationEntry
		).getProperties();

		Mockito.verify(
			_amImageConfigurationEntry
		).getUUID();
	}

	@Test(expected = AMRuntimeException.class)
	public void testFileEntryPathDLAppFailure() throws Exception {
		Mockito.when(
			_dlAppService.getFileEntry(0)
		).thenThrow(
			PortalException.class
		);

		_pathInterpreter.interpretPath("/image/0/x/foo.jpg");
	}

	@Test(expected = AMRuntimeException.class)
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
	public void testFileEntryPathWithTimestamp() throws Exception {
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
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				Mockito.anyLong(), Mockito.eq("x"))
		).thenReturn(
			_amImageConfigurationEntry
		);

		_pathInterpreter.interpretPath("/image/0/x/foo.jpg?t=12345");

		Mockito.verify(
			_dlAppService
		).getFileEntry(
			0
		);

		Mockito.verify(
			_fileVersion
		).getCompanyId();

		Mockito.verify(
			_amImageConfigurationEntry
		).getProperties();

		Mockito.verify(
			_amImageConfigurationEntry
		).getUUID();
	}

	@Test
	public void testFileVersionPath() throws Exception {
		Mockito.when(
			_dlAppService.getFileVersion(1)
		).thenReturn(
			_fileVersion
		);

		Mockito.when(
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				Mockito.anyLong(), Mockito.eq("x"))
		).thenReturn(
			_amImageConfigurationEntry
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
			_amImageConfigurationEntry
		).getProperties();

		Mockito.verify(
			_amImageConfigurationEntry
		).getUUID();
	}

	@Test(expected = AMRuntimeException.class)
	public void testFileVersionPathDLAppFailure() throws Exception {
		Mockito.when(
			_dlAppService.getFileVersion(1)
		).thenThrow(
			PortalException.class
		);

		_pathInterpreter.interpretPath("/image/0/1/x/foo.jpg");
	}

	@Test
	public void testNonmatchingPathInfo() {
		Tuple<FileVersion, Map<String, String>> tuple =
			_pathInterpreter.interpretPath("/" + RandomTestUtil.randomString());

		Assert.assertNull(tuple);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullPathInfoFails() {
		_pathInterpreter.interpretPath(null);
	}

	private final AMImageConfigurationEntry _amImageConfigurationEntry =
		Mockito.mock(AMImageConfigurationEntry.class);
	private final AMImageConfigurationHelper _amImageConfigurationHelper =
		Mockito.mock(AMImageConfigurationHelperImpl.class);
	private final DLAppService _dlAppService = Mockito.mock(DLAppService.class);
	private final FileEntry _fileEntry = Mockito.mock(FileEntry.class);
	private final FileVersion _fileVersion = Mockito.mock(FileVersion.class);
	private final PathInterpreter _pathInterpreter = new PathInterpreter();

}