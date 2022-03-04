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

package com.liferay.document.library.video.converter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.util.VideoProcessorUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.ExpectedLog;
import com.liferay.portal.test.rule.ExpectedLogs;
import com.liferay.portal.test.rule.ExpectedType;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.util.VideoProcessorImpl;

import java.util.Dictionary;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class DLVideoFFMPEGVideoConverterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), 0);
	}

	@Test
	public void testDoesNotGenerateVideoPreview() throws Exception {
		_withDLVideoFFMPEGVideoConverterConfiguration(
			false,
			() -> {
				FileEntry fileEntry = _createVideoFileEntry("video.mp4");

				Assert.assertFalse(
					VideoProcessorUtil.hasVideo(fileEntry.getFileVersion()));
			});
	}

	@ExpectedLogs(
		expectedLogs = {
			@ExpectedLog(
				expectedLog = "ffmpeg", expectedType = ExpectedType.CONTAINS
			),
			@ExpectedLog(
				expectedLog = "java.io.FileNotFoundException",
				expectedType = ExpectedType.CONTAINS
			),
			@ExpectedLog(
				expectedLog = "Unable to process",
				expectedType = ExpectedType.CONTAINS
			)
		},
		level = "ERROR", loggerClass = VideoProcessorImpl.class
	)
	@Test
	public void testDoesNotGenerateVideoPreviewIfTheVideoIsCorrupt()
		throws Exception {

		_withDLVideoFFMPEGVideoConverterConfiguration(
			true,
			() -> {
				FileEntry fileEntry = _createVideoFileEntry(
					"video_corrupt.mp4");

				Assert.assertFalse(
					VideoProcessorUtil.hasVideo(fileEntry.getFileVersion()));
			});
	}

	@Test
	public void testGeneratesVideoPreviewForOGVIfEnabled() throws Exception {
		_withDLVideoFFMPEGVideoConverterConfiguration(
			true,
			() -> {
				FileEntry fileEntry = _createVideoFileEntry("video.ogv");

				Assert.assertTrue(
					VideoProcessorUtil.hasVideo(fileEntry.getFileVersion()));

				long mp4PreviewFileSize = VideoProcessorUtil.getPreviewFileSize(
					fileEntry.getFileVersion(), "mp4");

				Assert.assertTrue(mp4PreviewFileSize > 0);

				long ogvPreviewFileSize = VideoProcessorUtil.getPreviewFileSize(
					fileEntry.getFileVersion(), "ogv");

				Assert.assertTrue(ogvPreviewFileSize > 0);
			});
	}

	@Test
	public void testGeneratesVideoPreviewIfEnabled() throws Exception {
		_withDLVideoFFMPEGVideoConverterConfiguration(
			true,
			() -> {
				FileEntry fileEntry = _createVideoFileEntry("video.mp4");

				Assert.assertTrue(
					VideoProcessorUtil.hasVideo(fileEntry.getFileVersion()));

				long mp4PreviewFileSize = VideoProcessorUtil.getPreviewFileSize(
					fileEntry.getFileVersion(), "mp4");

				Assert.assertTrue(mp4PreviewFileSize > 0);

				long ogvPreviewFileSize = VideoProcessorUtil.getPreviewFileSize(
					fileEntry.getFileVersion(), "ogv");

				Assert.assertTrue(ogvPreviewFileSize > 0);
			});
	}

	@ExpectedLogs(
		expectedLogs = {
			@ExpectedLog(
				expectedLog = "ffmpeg", expectedType = ExpectedType.CONTAINS
			),
			@ExpectedLog(
				expectedLog = "java.io.FileNotFoundException",
				expectedType = ExpectedType.CONTAINS
			),
			@ExpectedLog(
				expectedLog = "Unable to process",
				expectedType = ExpectedType.CONTAINS
			)
		},
		level = "ERROR", loggerClass = VideoProcessorImpl.class
	)
	@Test
	public void testGeneratesVideoPreviewIfTheVideoHasOnlyAudio()
		throws Exception {

		_withDLVideoFFMPEGVideoConverterConfiguration(
			true,
			() -> {
				FileEntry fileEntry = _createVideoFileEntry(
					"video_only_audio.mp4");

				Assert.assertTrue(
					VideoProcessorUtil.hasVideo(fileEntry.getFileVersion()));

				long mp4PreviewFileSize = VideoProcessorUtil.getPreviewFileSize(
					fileEntry.getFileVersion(), "mp4");

				Assert.assertTrue(mp4PreviewFileSize > 0);

				long ogvPreviewFileSize = VideoProcessorUtil.getPreviewFileSize(
					fileEntry.getFileVersion(), "ogv");

				Assert.assertTrue(ogvPreviewFileSize > 0);
			});
	}

	private FileEntry _createVideoFileEntry(String fileName) throws Exception {
		return DLAppServiceUtil.addFileEntry(
			null, _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
			MimeTypesUtil.getContentType(fileName), "video",
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), FileUtil.getBytes(getClass(), fileName),
			null, null, _serviceContext);
	}

	private void _withDLVideoFFMPEGVideoConverterConfiguration(
			boolean enabled, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				"enabled", enabled
			).build();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.video.internal." +
						"configuration." +
							"DLVideoFFMPEGVideoConverterConfiguration",
					dictionary)) {

			unsafeRunnable.run();
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

}