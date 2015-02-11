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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.DLProcessorConstants;
import com.liferay.portlet.documentlibrary.util.DLProcessor;
import com.liferay.portlet.documentlibrary.util.DLProcessorRegistryUtil;
import com.liferay.portlet.documentlibrary.util.PDFProcessorImpl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class DLPDFProcessorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());
	}

	@After
	public void tearDown() {
		if (_cleanUpDLProcessor != null) {
			DLProcessorRegistryUtil.unregister(_cleanUpDLProcessor);
			DLProcessorRegistryUtil.register(_originalDLProcessor);
		}
	}

	@Test
	public void testShouldCleanUpProcessorsOnCancelCheckOut() throws Exception {
		AtomicBoolean cleanedUp = registerCleanUpDLProcessor();

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), _PDF_DATA.getBytes(), _serviceContext);

		DLAppServiceUtil.checkOutFileEntry(
			fileEntry.getFileEntryId(), _serviceContext);

		DLAppServiceUtil.cancelCheckOut(fileEntry.getFileEntryId());

		Assert.assertTrue(cleanedUp.get());
	}

	@Test
	public void testShouldCleanUpProcessorsOnDelete() throws Exception {
		AtomicBoolean cleanedUp = registerCleanUpDLProcessor();

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), _PDF_DATA.getBytes(), _serviceContext);

		DLAppServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());

		Assert.assertTrue(cleanedUp.get());
	}

	@Test
	public void testShouldCleanUpProcessorsOnUpdate() throws Exception {
		AtomicBoolean cleanedUp = registerCleanUpDLProcessor();

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), _PDF_DATA.getBytes(), _serviceContext);

		DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), StringUtil.randomString() + ".pdf",
			ContentTypes.APPLICATION_PDF, StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), true,
			_PDF_DATA.getBytes(), _serviceContext);

		Assert.assertTrue(cleanedUp.get());
	}

	@Test
	public void testShouldCleanUpProcessorsOnUpdateAndCheckIn()
		throws Exception {

		AtomicBoolean cleanedUp = registerCleanUpDLProcessor();

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), _PDF_DATA.getBytes(), _serviceContext);

		byte[] bytes = _PDF_DATA.getBytes();

		InputStream inputStream = new ByteArrayInputStream(bytes);

		DLAppServiceUtil.updateFileEntryAndCheckIn(
			fileEntry.getFileEntryId(), StringUtil.randomString() + ".pdf",
			ContentTypes.APPLICATION_PDF, StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), true,
			inputStream, bytes.length, _serviceContext);

		Assert.assertTrue(cleanedUp.get());
	}

	@Test
	public void testShouldCopyPreviousPreviewOnCheckIn() throws Exception {
		AtomicInteger count = registerDLPDFProcessorMessageListener(
			EventType.COPY_PREVIOUS);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), _PDF_DATA.getBytes(), _serviceContext);

		DLAppServiceUtil.checkInFileEntry(
			fileEntry.getFileEntryId(), true, StringUtil.randomString(),
			_serviceContext);

		Assert.assertEquals(1, count.get());
	}

	@Test
	public void testShouldCopyPreviousPreviewOnCheckOut() throws Exception {
		AtomicInteger count = registerDLPDFProcessorMessageListener(
			EventType.COPY_PREVIOUS);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), _PDF_DATA.getBytes(), _serviceContext);

		DLAppServiceUtil.checkOutFileEntry(
			fileEntry.getFileEntryId(), _serviceContext);

		Assert.assertEquals(1, count.get());
	}

	@Test
	public void testShouldCopyPreviousPreviewOnRevert() throws Exception {
		AtomicInteger count = registerDLPDFProcessorMessageListener(
			EventType.COPY_PREVIOUS);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), _PDF_DATA.getBytes(), _serviceContext);

		String previousVersion = fileEntry.getVersion();

		fileEntry = DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), StringUtil.randomString() + ".pdf",
			ContentTypes.APPLICATION_PDF, StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), true,
			_PDF_DATA.getBytes(), _serviceContext);

		Assert.assertNotEquals(previousVersion, fileEntry.getVersion());

		DLAppServiceUtil.revertFileEntry(
			fileEntry.getFileEntryId(), previousVersion, _serviceContext);

		Assert.assertEquals(1, count.get());
	}

	@Test
	public void testShouldCopyPreviousPreviewOnUpdateAndCheckInWithNoContent()
		throws Exception {

		AtomicInteger count = registerDLPDFProcessorMessageListener(
			EventType.COPY_PREVIOUS);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), _PDF_DATA.getBytes(), _serviceContext);

		DLAppServiceUtil.updateFileEntryAndCheckIn(
			fileEntry.getFileEntryId(), StringUtil.randomString() + ".pdf",
			ContentTypes.APPLICATION_PDF, StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), true, null, 0,
			_serviceContext);

		Assert.assertEquals(1, count.get());
	}

	@Test
	public void testShouldCopyPreviousPreviewOnUpdateWithNoContent()
		throws Exception {

		AtomicInteger count = registerDLPDFProcessorMessageListener(
			EventType.COPY_PREVIOUS);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), _PDF_DATA.getBytes(), _serviceContext);

		DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), StringUtil.randomString() + ".pdf",
			ContentTypes.APPLICATION_PDF, StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), true,
			new byte[0], _serviceContext);

		Assert.assertEquals(1, count.get());
	}

	@Test
	public void testShouldCreateNewPreviewOnAdd() throws Exception {
		AtomicInteger count = registerDLPDFProcessorMessageListener(
			EventType.GENERATE_NEW);

		DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), _PDF_DATA.getBytes(), _serviceContext);

		Assert.assertEquals(1, count.get());
	}

	@Test
	public void testShouldCreateNewPreviewOnCancelCheckOut() throws Exception {
		AtomicInteger count = registerDLPDFProcessorMessageListener(
			EventType.GENERATE_NEW);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), _PDF_DATA.getBytes(), _serviceContext);

		DLAppServiceUtil.checkOutFileEntry(
			fileEntry.getFileEntryId(), _serviceContext);

		DLAppServiceUtil.cancelCheckOut(fileEntry.getFileEntryId());

		Assert.assertEquals(2, count.get());
	}

	@Test
	public void testShouldCreateNewPreviewOnUpdateAndCheckInWithContent()
		throws Exception {

		AtomicInteger newCount = registerDLPDFProcessorMessageListener(
			EventType.GENERATE_NEW);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), _PDF_DATA.getBytes(), _serviceContext);

		byte[] bytes = _PDF_DATA.getBytes();

		InputStream inputStream = new ByteArrayInputStream(bytes);

		DLAppServiceUtil.updateFileEntryAndCheckIn(
			fileEntry.getFileEntryId(), StringUtil.randomString() + ".pdf",
			ContentTypes.APPLICATION_PDF, StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), true,
			inputStream, bytes.length, _serviceContext);

		Assert.assertEquals(2, newCount.get());
	}

	@Test
	public void testShouldCreateNewPreviewOnUpdateWithContent()
		throws Exception {

		AtomicInteger count = registerDLPDFProcessorMessageListener(
			EventType.GENERATE_NEW);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), _PDF_DATA.getBytes(), _serviceContext);

		DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), StringUtil.randomString() + ".pdf",
			ContentTypes.APPLICATION_PDF, StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), true,
			_PDF_DATA.getBytes(), _serviceContext);

		Assert.assertEquals(2, count.get());
	}

	protected static AtomicInteger registerDLPDFProcessorMessageListener(
		final EventType eventType) {

		final AtomicInteger counter = new AtomicInteger();

		MessageBusUtil.registerMessageListener(
			DestinationNames.DOCUMENT_LIBRARY_PDF_PROCESSOR,
			new MessageListener() {

				@Override
				public void receive(Message message) {
					Object[] payload = (Object[])message.getPayload();

					if (eventType.isMatch(payload[0])) {
						counter.incrementAndGet();
					}
				}

			});

		return counter;
	}

	protected AtomicBoolean registerCleanUpDLProcessor() {
		_originalDLProcessor = DLProcessorRegistryUtil.getDLProcessor(
			DLProcessorConstants.PDF_PROCESSOR);

		final AtomicBoolean cleanedUp = new AtomicBoolean(false);

		_cleanUpDLProcessor = new PDFProcessorImpl() {

			@Override
			public void cleanUp(FileEntry fileEntry) {
				cleanedUp.set(true);
			}

			@Override
			public void cleanUp(FileVersion fileVersion) {
				cleanedUp.set(true);
			}

			@Override
			public void copy(
				FileVersion sourceFileVersion,
				FileVersion destinationFileVersion) {
			}

			@Override
			public void exportGeneratedFiles(
					PortletDataContext portletDataContext, FileEntry fileEntry,
					Element fileEntryElement)
				throws Exception {
			}

			@Override
			public void importGeneratedFiles(
					PortletDataContext portletDataContext, FileEntry fileEntry,
					FileEntry importedFileEntry, Element fileEntryElement)
				throws Exception {
			}

			@Override
			public void trigger(
				FileVersion sourceFileVersion,
				FileVersion destinationFileVersion) {
			}

		};

		DLProcessorRegistryUtil.register(_cleanUpDLProcessor);

		return cleanedUp;
	}

	protected enum EventType {

		COPY_PREVIOUS {

			@Override
			public boolean isMatch(Object object) {
				return object != null;
			}

		},

		GENERATE_NEW {

			@Override
			public boolean isMatch(Object object) {
				return object == null;
			}

		};

		public abstract boolean isMatch(Object object);

	}

	private static final String _PDF_DATA =
		"%PDF-1.\ntrailer<</Root<</Pages<</Kids[<</MediaBox[0 0 3 3]>>]>>>>>>";

	private DLProcessor _cleanUpDLProcessor;

	@DeleteAfterTestRun
	private Group _group;

	private DLProcessor _originalDLProcessor;
	private ServiceContext _serviceContext;

}