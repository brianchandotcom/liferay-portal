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

package com.liferay.document.library.preview.audio.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.util.AudioProcessorUtil;
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
import com.liferay.portlet.documentlibrary.util.AudioProcessorImpl;

import java.util.Dictionary;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class DLAudioFFMPEGAudioConverterTest {

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
	public void testDoesNotGenerateAudioPreview() throws Exception {
		_withDLAudioFFMPEGAudioConverterConfiguration(
			false,
			() -> {
				FileEntry fileEntry = _createAudioFileEntry("audio.wav");

				Assert.assertFalse(
					AudioProcessorUtil.hasAudio(fileEntry.getFileVersion()));
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
		level = "ERROR", loggerClass = AudioProcessorImpl.class
	)
	@Test
	public void testDoesNotGenerateAudioPreviewIfTheAudioIsCorrupt()
		throws Exception {

		_withDLAudioFFMPEGAudioConverterConfiguration(
			true,
			() -> {
				FileEntry fileEntry = _createAudioFileEntry(
					"audio_corrupt.wav");

				Assert.assertFalse(
					AudioProcessorUtil.hasAudio(fileEntry.getFileVersion()));
			});
	}

	@Test
	public void testGeneratesAudioPreviewIfEnabled() throws Exception {
		_withDLAudioFFMPEGAudioConverterConfiguration(
			true,
			() -> {
				FileEntry fileEntry = _createAudioFileEntry("audio.wav");

				Assert.assertTrue(
					AudioProcessorUtil.hasAudio(fileEntry.getFileVersion()));

				long wavPreviewFileSize = AudioProcessorUtil.getPreviewFileSize(
					fileEntry.getFileVersion(), "mp3");

				Assert.assertTrue(wavPreviewFileSize > 0);
			});
	}

	private FileEntry _createAudioFileEntry(String fileName) throws Exception {
		return DLAppServiceUtil.addFileEntry(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			fileName, MimeTypesUtil.getContentType(fileName), "audio",
			StringUtil.randomString(), StringUtil.randomString(),
			FileUtil.getBytes(getClass(), fileName), _serviceContext);
	}

	private void _withDLAudioFFMPEGAudioConverterConfiguration(
			boolean enabled, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				"enabled", enabled
			).build();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.preview.audio.internal." +
						"configuration." +
							"DLAudioFFMPEGAudioConverterConfiguration",
					dictionary)) {

			unsafeRunnable.run();
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

}