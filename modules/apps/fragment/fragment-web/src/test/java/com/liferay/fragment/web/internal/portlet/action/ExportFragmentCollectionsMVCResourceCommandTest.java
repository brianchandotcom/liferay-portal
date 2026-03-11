/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.portlet.action;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.portlet.ResourceRequest;
import jakarta.portlet.ResourceResponse;

import java.io.File;
import java.io.InputStream;

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
		_timeMockedStatic.when(
			Time::getTimestamp
		).thenReturn(
			String.valueOf(RandomTestUtil.randomLong())
		);

		Mockito.when(
			_fragmentCollection.getFragmentCollectionId()
		).thenReturn(
			_FRAGMENT_COLLECTION_ID
		);

		Mockito.when(
			_fragmentCollectionService.fetchFragmentCollection(
				_FRAGMENT_COLLECTION_ID)
		).thenReturn(
			_fragmentCollection
		);

		File file = File.createTempFile("fragment-collections", ".zip");

		file.deleteOnExit();

		Mockito.when(
			_zipWriter.getFile()
		).thenReturn(
			file
		);

		Mockito.when(
			_zipWriterFactory.getZipWriter()
		).thenReturn(
			_zipWriter
		);

		_portletResponseUtilMockedStatic.when(
			() -> PortletResponseUtil.sendFile(
				Mockito.any(ResourceRequest.class),
				Mockito.any(ResourceResponse.class), Mockito.anyString(),
				Mockito.any(InputStream.class), Mockito.anyString())
		).thenAnswer(
			invocation -> null
		);

		ReflectionTestUtil.setFieldValue(
			_exportFragmentCollectionsMVCResourceCommand,
			"_fragmentCollectionService", _fragmentCollectionService);
		ReflectionTestUtil.setFieldValue(
			_exportFragmentCollectionsMVCResourceCommand, "_zipWriterFactory",
			_zipWriterFactory);
	}

	@After
	public void tearDown() {
		_portletResponseUtilMockedStatic.close();
		_timeMockedStatic.close();
	}

	@Test
	@TestInfo("LPD-82487")
	public void testServeResourceExportsCollectionWithExportableItems()
		throws Exception {

		Mockito.when(
			_fragmentCollection.hasExportableItems()
		).thenReturn(
			true
		);

		_exportFragmentCollectionsMVCResourceCommand.serveResource(
			_getMockLiferayResourceRequest(),
			new MockLiferayResourceResponse());

		Mockito.verify(
			_fragmentCollection
		).populateZipWriter(
			_zipWriter
		);
	}

	@Test
	@TestInfo("LPD-82487")
	public void testServeResourceSkipsCollectionWithoutExportableItems()
		throws Exception {

		Mockito.when(
			_fragmentCollection.hasExportableItems()
		).thenReturn(
			false
		);

		_exportFragmentCollectionsMVCResourceCommand.serveResource(
			_getMockLiferayResourceRequest(),
			new MockLiferayResourceResponse());

		Mockito.verify(
			_fragmentCollection, Mockito.never()
		).populateZipWriter(
			Mockito.any()
		);
	}

	private MockLiferayResourceRequest _getMockLiferayResourceRequest() {
		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.setParameter(
			"fragmentCollectionId", String.valueOf(_FRAGMENT_COLLECTION_ID));

		return mockLiferayResourceRequest;
	}

	private static final long _FRAGMENT_COLLECTION_ID =
		RandomTestUtil.randomLong();

	private final ExportFragmentCollectionsMVCResourceCommand
		_exportFragmentCollectionsMVCResourceCommand =
			new ExportFragmentCollectionsMVCResourceCommand();
	private final FragmentCollection _fragmentCollection = Mockito.mock(
		FragmentCollection.class);
	private final FragmentCollectionService _fragmentCollectionService =
		Mockito.mock(FragmentCollectionService.class);
	private final MockedStatic<PortletResponseUtil>
		_portletResponseUtilMockedStatic = Mockito.mockStatic(
			PortletResponseUtil.class);
	private final MockedStatic<Time> _timeMockedStatic = Mockito.mockStatic(
		Time.class);
	private final ZipWriter _zipWriter = Mockito.mock(ZipWriter.class);
	private final ZipWriterFactory _zipWriterFactory = Mockito.mock(
		ZipWriterFactory.class);

}