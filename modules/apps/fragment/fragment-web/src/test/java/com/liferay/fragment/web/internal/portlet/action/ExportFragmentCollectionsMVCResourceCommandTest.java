/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.portlet.action;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.portlet.ResourceRequest;
import jakarta.portlet.ResourceResponse;

import java.io.File;
import java.io.InputStream;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Georgel Pop
 */
public class ExportFragmentCollectionsMVCResourceCommandTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_setUpFragmentCollection();
		_mockSendFile();
		_mockExportableFragmentCollections(List.of(_fragmentCollection));
		_setUpThemeDisplay();
		_setUpTime();
		_setUpZipWriter();
		_setUpZipWriterFactory();

		_setUpExportFragmentCollectionsMVCResourceCommand();
	}

	@After
	public void tearDown() {
		_portletResponseUtilMockedStatic.close();
		_timeMockedStatic.close();
	}

	@Test
	@TestInfo("LPD-82487")
	public void testServeResource() throws Exception {
		for (List<FragmentCollection> fragmentCollections :
				List.of(
					List.<FragmentCollection>of(),
					List.of(_fragmentCollection))) {

			_mockExportableFragmentCollections(fragmentCollections);

			_exportFragmentCollectionsMVCResourceCommand.serveResource(
				_getMockLiferayResourceRequest(),
				new MockLiferayResourceResponse());

			if (fragmentCollections.isEmpty()) {
				Mockito.verify(
					_fragmentCollection, Mockito.never()
				).populateZipWriter(
					Mockito.any()
				);
			}
			else {
				Mockito.verify(
					_fragmentCollection
				).populateZipWriter(
					_zipWriter
				);
			}

			Mockito.clearInvocations(_fragmentCollection);
		}
	}

	private MockLiferayResourceRequest _getMockLiferayResourceRequest() {
		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.setParameter(
			"fragmentCollectionId", String.valueOf(_FRAGMENT_COLLECTION_ID));
		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _themeDisplay);

		return mockLiferayResourceRequest;
	}

	private void _mockExportableFragmentCollections(
		List<FragmentCollection> fragmentCollections) {

		Mockito.when(
			_fragmentCollectionService.getExportableFragmentCollections(
				new long[] {_GROUP_ID}, new long[] {_FRAGMENT_COLLECTION_ID})
		).thenReturn(
			fragmentCollections
		);
	}

	private void _mockSendFile() {
		_portletResponseUtilMockedStatic.when(
			() -> PortletResponseUtil.sendFile(
				Mockito.any(ResourceRequest.class),
				Mockito.any(ResourceResponse.class), Mockito.anyString(),
				Mockito.any(InputStream.class), Mockito.anyString())
		).thenAnswer(
			invocation -> null
		);
	}

	private void _setUpExportFragmentCollectionsMVCResourceCommand() {
		ReflectionTestUtil.setFieldValue(
			_exportFragmentCollectionsMVCResourceCommand,
			"_fragmentCollectionService", _fragmentCollectionService);
		ReflectionTestUtil.setFieldValue(
			_exportFragmentCollectionsMVCResourceCommand, "_zipWriterFactory",
			_zipWriterFactory);
	}

	private void _setUpFragmentCollection() {
		Mockito.when(
			_fragmentCollection.getFragmentCollectionId()
		).thenReturn(
			_FRAGMENT_COLLECTION_ID
		);
	}

	private void _setUpThemeDisplay() {
		Mockito.when(
			_group.isCompany()
		).thenReturn(
			false
		);

		Mockito.when(
			_themeDisplay.getScopeGroup()
		).thenReturn(
			_group
		);

		Mockito.when(
			_themeDisplay.getScopeGroupId()
		).thenReturn(
			_GROUP_ID
		);
	}

	private void _setUpTime() {
		_timeMockedStatic.when(
			Time::getTimestamp
		).thenReturn(
			String.valueOf(RandomTestUtil.randomLong())
		);
	}

	private void _setUpZipWriter() throws Exception {
		File file = File.createTempFile("fragment-collections", ".zip");

		file.deleteOnExit();

		Mockito.when(
			_zipWriter.getFile()
		).thenReturn(
			file
		);
	}

	private void _setUpZipWriterFactory() {
		Mockito.when(
			_zipWriterFactory.getZipWriter()
		).thenReturn(
			_zipWriter
		);
	}

	private static final long _FRAGMENT_COLLECTION_ID =
		RandomTestUtil.randomLong();

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	private final ExportFragmentCollectionsMVCResourceCommand
		_exportFragmentCollectionsMVCResourceCommand =
			new ExportFragmentCollectionsMVCResourceCommand();
	private final FragmentCollection _fragmentCollection = Mockito.mock(
		FragmentCollection.class);
	private final FragmentCollectionService _fragmentCollectionService =
		Mockito.mock(FragmentCollectionService.class);
	private final Group _group = Mockito.mock(Group.class);
	private final MockedStatic<PortletResponseUtil>
		_portletResponseUtilMockedStatic = Mockito.mockStatic(
			PortletResponseUtil.class);
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);
	private final MockedStatic<Time> _timeMockedStatic = Mockito.mockStatic(
		Time.class);
	private final ZipWriter _zipWriter = Mockito.mock(ZipWriter.class);
	private final ZipWriterFactory _zipWriterFactory = Mockito.mock(
		ZipWriterFactory.class);

}