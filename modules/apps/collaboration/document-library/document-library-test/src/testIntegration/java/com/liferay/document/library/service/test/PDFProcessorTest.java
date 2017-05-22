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

package com.liferay.document.library.service.test;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLProcessorConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.util.DLProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.lang.reflect.Field;

import java.util.HashMap;
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
@Sync
public class PDFProcessorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		Field dlFileEntryPreviewForkProcessEnabledField =
			ReflectionUtil.getDeclaredField(
				PropsValues.class,
				"DL_FILE_ENTRY_PREVIEW_FORK_PROCESS_ENABLED");

		_dlFileEntryPreviewForkProcessEnabled =
			dlFileEntryPreviewForkProcessEnabledField.get(null);

		dlFileEntryPreviewForkProcessEnabledField.set(null, Boolean.FALSE);

		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());
	}

	@After
	public void tearDown() throws Exception {
		Field dlFileEntryPreviewForkProcessEnabledField =
			ReflectionUtil.getDeclaredField(
				PropsValues.class,
				"DL_FILE_ENTRY_PREVIEW_FORK_PROCESS_ENABLED");

		dlFileEntryPreviewForkProcessEnabledField.set(
			null, _dlFileEntryPreviewForkProcessEnabled);

		if (_dlProcessorServiceRegistration != null) {
			_dlProcessorServiceRegistration.unregister();
		}
	}

	@Test
	public void testShouldCleanUpProcessorsOnCancelCheckOut() throws Exception {
		AtomicBoolean cleanUp = registerCleanUpDLProcessor();

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(),
			FileUtil.getBytes(getClass(), "dependencies/test.pdf"),
			_serviceContext);

		DLAppServiceUtil.checkOutFileEntry(
			fileEntry.getFileEntryId(), _serviceContext);

		DLAppServiceUtil.cancelCheckOut(fileEntry.getFileEntryId());

		Assert.assertTrue(cleanUp.get());
	}

	@Test
	public void testShouldCleanUpProcessorsOnDelete() throws Exception {
		AtomicBoolean cleanUp = registerCleanUpDLProcessor();

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(),
			FileUtil.getBytes(getClass(), "dependencies/test.pdf"),
			_serviceContext);

		DLAppServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());

		Assert.assertTrue(cleanUp.get());
	}

	@Test
	public void testShouldCleanUpProcessorsOnUpdate() throws Exception {
		AtomicBoolean cleanUp = registerCleanUpDLProcessor();

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(),
			FileUtil.getBytes(getClass(), "dependencies/test.pdf"),
			_serviceContext);

		DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), StringUtil.randomString() + ".pdf",
			ContentTypes.APPLICATION_PDF, StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), true,
			FileUtil.getBytes(getClass(), "dependencies/test.pdf"),
			_serviceContext);

		Assert.assertTrue(cleanUp.get());
	}

	@Test
	public void testShouldCleanUpProcessorsOnUpdateAndCheckIn()
		throws Exception {

		AtomicBoolean cleanUp = registerCleanUpDLProcessor();

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(),
			FileUtil.getBytes(getClass(), "dependencies/test.pdf"),
			_serviceContext);

		byte[] bytes = FileUtil.getBytes(getClass(), "dependencies/test.pdf");

		InputStream inputStream = new ByteArrayInputStream(bytes);

		DLAppServiceUtil.updateFileEntryAndCheckIn(
			fileEntry.getFileEntryId(), StringUtil.randomString() + ".pdf",
			ContentTypes.APPLICATION_PDF, StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), true,
			inputStream, bytes.length, _serviceContext);

		Assert.assertTrue(cleanUp.get());
	}

	@Test
	public void testShouldCopyPreviousPreviewOnCheckIn() throws Exception {
		AtomicInteger count = registerPDFProcessorMessageListener(
			EventType.COPY_PREVIOUS);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(),
			FileUtil.getBytes(getClass(), "dependencies/test.pdf"),
			_serviceContext);

		DLAppServiceUtil.checkInFileEntry(
			fileEntry.getFileEntryId(), true, StringUtil.randomString(),
			_serviceContext);

		Assert.assertEquals(1, count.get());
	}

	@Test
	public void testShouldCopyPreviousPreviewOnCheckOut() throws Exception {
		AtomicInteger count = registerPDFProcessorMessageListener(
			EventType.COPY_PREVIOUS);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(),
			FileUtil.getBytes(getClass(), "dependencies/test.pdf"),
			_serviceContext);

		DLAppServiceUtil.checkOutFileEntry(
			fileEntry.getFileEntryId(), _serviceContext);

		Assert.assertEquals(1, count.get());
	}

	@Test
	public void testShouldCopyPreviousPreviewOnRevert() throws Exception {
		AtomicInteger count = registerPDFProcessorMessageListener(
			EventType.COPY_PREVIOUS);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(),
			FileUtil.getBytes(getClass(), "dependencies/test.pdf"),
			_serviceContext);

		String version = fileEntry.getVersion();

		fileEntry = DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), StringUtil.randomString() + ".pdf",
			ContentTypes.APPLICATION_PDF, StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), true,
			FileUtil.getBytes(getClass(), "dependencies/test.pdf"),
			_serviceContext);

		Assert.assertNotEquals(version, fileEntry.getVersion());

		DLAppServiceUtil.revertFileEntry(
			fileEntry.getFileEntryId(), version, _serviceContext);

		Assert.assertEquals(1, count.get());
	}

	@Test
	public void testShouldCopyPreviousPreviewOnUpdateAndCheckInWithNoContent()
		throws Exception {

		AtomicInteger count = registerPDFProcessorMessageListener(
			EventType.COPY_PREVIOUS);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(),
			FileUtil.getBytes(getClass(), "dependencies/test.pdf"),
			_serviceContext);

		DLAppServiceUtil.updateFileEntryAndCheckIn(
			fileEntry.getFileEntryId(), StringUtil.randomString() + ".pdf",
			ContentTypes.APPLICATION_PDF, StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), true, null, 0,
			_serviceContext);

		Assert.assertEquals(2, count.get());
	}

	@Test
	public void testShouldCopyPreviousPreviewOnUpdateWithNoContent()
		throws Exception {

		AtomicInteger count = registerPDFProcessorMessageListener(
			EventType.COPY_PREVIOUS);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(),
			FileUtil.getBytes(getClass(), "dependencies/test.pdf"),
			_serviceContext);

		DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), StringUtil.randomString() + ".pdf",
			ContentTypes.APPLICATION_PDF, StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), true,
			new byte[0], _serviceContext);

		Assert.assertEquals(1, count.get());
	}

	@Test
	public void testShouldCreateNewPreviewOnAdd() throws Exception {
		AtomicInteger count = registerPDFProcessorMessageListener(
			EventType.GENERATE_NEW);

		DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(),
			FileUtil.getBytes(getClass(), "dependencies/test.pdf"),
			_serviceContext);

		Assert.assertEquals(1, count.get());
	}

	@Test
	public void testShouldCreateNewPreviewOnCancelCheckOut() throws Exception {
		AtomicInteger count = registerPDFProcessorMessageListener(
			EventType.GENERATE_NEW);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(),
			FileUtil.getBytes(getClass(), "dependencies/test.pdf"),
			_serviceContext);

		DLAppServiceUtil.checkOutFileEntry(
			fileEntry.getFileEntryId(), _serviceContext);

		DLAppServiceUtil.cancelCheckOut(fileEntry.getFileEntryId());

		Assert.assertEquals(2, count.get());
	}

	@Test
	public void testShouldCreateNewPreviewOnUpdateAndCheckInWithContent()
		throws Exception {

		AtomicInteger count = registerPDFProcessorMessageListener(
			EventType.GENERATE_NEW);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(),
			FileUtil.getBytes(getClass(), "dependencies/test.pdf"),
			_serviceContext);

		byte[] bytes = FileUtil.getBytes(getClass(), "dependencies/test.pdf");

		InputStream inputStream = new ByteArrayInputStream(bytes);

		DLAppServiceUtil.updateFileEntryAndCheckIn(
			fileEntry.getFileEntryId(), StringUtil.randomString() + ".pdf",
			ContentTypes.APPLICATION_PDF, StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), true,
			inputStream, bytes.length, _serviceContext);

		Assert.assertEquals(2, count.get());
	}

	@Test
	public void testShouldCreateNewPreviewOnUpdateWithContent()
		throws Exception {

		AtomicInteger count = registerPDFProcessorMessageListener(
			EventType.GENERATE_NEW);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".pdf", ContentTypes.APPLICATION_PDF,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(),
			FileUtil.getBytes(getClass(), "dependencies/test.pdf"),
			_serviceContext);

		DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), StringUtil.randomString() + ".pdf",
			ContentTypes.APPLICATION_PDF, StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), true,
			FileUtil.getBytes(getClass(), "dependencies/test.pdf"),
			_serviceContext);

		Assert.assertEquals(2, count.get());
	}

	protected static AtomicInteger registerPDFProcessorMessageListener(
		final EventType eventType) {

		final AtomicInteger count = new AtomicInteger();

		MessageBusUtil.registerMessageListener(
			DestinationNames.DOCUMENT_LIBRARY_PDF_PROCESSOR,
			new MessageListener() {

				@Override
				public void receive(Message message) {
					Object[] payload = (Object[])message.getPayload();

					if (eventType.isMatch(payload[0])) {
						count.incrementAndGet();
					}
				}

			});

		return count;
	}

	protected AtomicBoolean registerCleanUpDLProcessor() {
		final AtomicBoolean cleanUp = new AtomicBoolean(false);

		DLProcessor cleanUpDLProcessor = new DLProcessor() {

			@Override
			public void afterPropertiesSet() throws Exception {
			}

			@Override
			public void cleanUp(FileEntry fileEntry) {
				cleanUp.set(true);
			}

			@Override
			public void cleanUp(FileVersion fileVersion) {
				cleanUp.set(true);
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
			public String getType() {
				return DLProcessorConstants.PDF_PROCESSOR;
			}

			@Override
			public void importGeneratedFiles(
					PortletDataContext portletDataContext, FileEntry fileEntry,
					FileEntry importedFileEntry, Element fileEntryElement)
				throws Exception {
			}

			@Override
			public boolean isSupported(FileVersion fileVersion) {
				return true;
			}

			@Override
			public boolean isSupported(String mimeType) {
				return true;
			}

			@Override
			public void trigger(
				FileVersion sourceFileVersion,
				FileVersion destinationFileVersion) {
			}

		};

		Registry registry = RegistryUtil.getRegistry();

		HashMap<String, Object> properties = new HashMap<>();

		properties.put("service.ranking", 1000);

		_dlProcessorServiceRegistration = registry.registerService(
			DLProcessor.class, cleanUpDLProcessor, properties);

		return cleanUp;
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
				if (object == null) {
					return true;
				}

				return false;
			}

		};

		public abstract boolean isMatch(Object object);

	}

	private Object _dlFileEntryPreviewForkProcessEnabled;
	private ServiceRegistration<DLProcessor> _dlProcessorServiceRegistration;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

}