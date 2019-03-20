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

package com.liferay.document.library.asset.auto.tagger.opennlp.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class OpenNLPDocumentAssetAutoTagProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), 0);
	}

	@Test
	public void testGetTagNamesWithEpubFile() throws Exception {
		String fileName =
			"Alice's Adventures in Wonderland, by Lewis Carroll.epub";

		FileEntry fileEntry = _dlAppService.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
			"application/epub+zip", RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			FileUtil.getBytes(getInputStream(fileName)), _serviceContext);

		_withOpenNLPAutoTagProviderEnabled(
			() -> {
				Collection<String> expectedTagNames = Arrays.asList("tag");

				Collection<String> actualTagNames =
					_assetAutoTagProvider.getTagNames(fileEntry);

				Assert.assertEquals(
					actualTagNames.toString(), expectedTagNames.size(),
					actualTagNames.size());
				Assert.assertTrue(actualTagNames.containsAll(expectedTagNames));
			});
	}

	@Test
	public void testGetTagNamesWithHTMLFile() throws Exception {
		String fileName =
			"Alice's Adventures in Wonderland, by Lewis Carroll.html";

		FileEntry fileEntry = _dlAppService.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
			ContentTypes.TEXT_HTML, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			FileUtil.getBytes(getInputStream(fileName)), _serviceContext);

		_withOpenNLPAutoTagProviderEnabled(
			() -> {
				Collection<String> expectedTagNames = Collections.singleton(
					"tag");

				Collection<String> actualTagNames =
					_assetAutoTagProvider.getTagNames(fileEntry);

				Assert.assertEquals(
					actualTagNames.toString(), expectedTagNames.size(),
					actualTagNames.size());
				Assert.assertTrue(
					actualTagNames.toString(),
					actualTagNames.containsAll(expectedTagNames));
			});
	}

	@Test
	public void testGetTagNamesWithJPGFile() throws Exception {
		FileEntry fileEntry = _dlAppService.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "test.jpg",
			ContentTypes.IMAGE_JPEG, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			FileUtil.getBytes(getInputStream("test.jpg")), _serviceContext);

		_withOpenNLPAutoTagProviderEnabled(
			() -> {
				Collection<String> tagNames = _assetAutoTagProvider.getTagNames(
					fileEntry);

				Assert.assertEquals(tagNames.toString(), 0, tagNames.size());
			});
	}

	@Test
	public void testGetTagNamesWithPDFFile() throws Exception {
		String fileName =
			"Alice's Adventures in Wonderland, by Lewis Carroll.pdf";

		FileEntry fileEntry = _dlAppService.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
			ContentTypes.APPLICATION_PDF, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			FileUtil.getBytes(getInputStream(fileName)), _serviceContext);

		_withOpenNLPAutoTagProviderEnabled(
			() -> {
				Collection<String> expectedTagNames = Collections.singleton(
					"tag");

				Collection<String> actualTagNames =
					_assetAutoTagProvider.getTagNames(fileEntry);

				Assert.assertEquals(
					actualTagNames.toString(), expectedTagNames.size(),
					actualTagNames.size());
				Assert.assertTrue(
					actualTagNames.toString(),
					actualTagNames.containsAll(expectedTagNames));
			});
	}

	@Test
	public void testGetTagNamesWithTemporaryTextFile() throws Exception {
		User user = TestPropsValues.getUser();

		String fileName =
			"Alice's Adventures in Wonderland, by Lewis Carroll.txt";

		FileEntry fileEntry = TempFileEntryUtil.addTempFileEntry(
			_group.getGroupId(), user.getUserId(),
			RandomTestUtil.randomString(), fileName, getInputStream(fileName),
			ContentTypes.TEXT_PLAIN);

		_withOpenNLPAutoTagProviderEnabled(
			() -> {
				Collection<String> tagNames = _assetAutoTagProvider.getTagNames(
					fileEntry);

				Assert.assertEquals(tagNames.toString(), 0, tagNames.size());
			});
	}

	@Test
	public void testGetTagNamesWithTextFile() throws Exception {
		String fileName =
			"Alice's Adventures in Wonderland, by Lewis Carroll.txt";

		FileEntry fileEntry = _dlAppService.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
			ContentTypes.TEXT, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			FileUtil.getBytes(getInputStream(fileName)), _serviceContext);

		_withOpenNLPAutoTagProviderEnabled(
			() -> {
				Collection<String> expectedTagNames = Collections.singleton(
					"tag");

				Collection<String> actualTagNames =
					_assetAutoTagProvider.getTagNames(fileEntry);

				Assert.assertEquals(
					actualTagNames.toString(), expectedTagNames.size(),
					actualTagNames.size());
				Assert.assertTrue(
					actualTagNames.toString(),
					actualTagNames.containsAll(expectedTagNames));
			});
	}

	@Test
	public void testGetTagNamesWithTextFileAndDisabledConfiguration()
		throws Exception {

		FileEntry fileEntry = _dlAppService.addFileEntry(
			_serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "test.txt",
			ContentTypes.TEXT_PLAIN, "test", RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			FileUtil.getBytes(getInputStream("test.txt")), _serviceContext);

		_withOpenNLPAutoTagProviderDisabled(
			() -> {
				Collection<String> tagNames = _assetAutoTagProvider.getTagNames(
					fileEntry);

				Assert.assertEquals(tagNames.toString(), 0, tagNames.size());
			});
	}

	protected InputStream getInputStream(String fileName) throws Exception {
		Class<?> clazz = getClass();

		return clazz.getResourceAsStream("dependencies/" + fileName);
	}

	private void _withOpenNLPAutoTagProviderDisabled(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_OPENNLP_AUTO_TAG_CONFIGURATION_CLASS_NAME,
					new HashMapDictionary<String, Object>() {
						{
							put("enabled", false);
						}
					})) {

			unsafeRunnable.run();
		}
	}

	private void _withOpenNLPAutoTagProviderEnabled(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_OPENNLP_AUTO_TAG_CONFIGURATION_CLASS_NAME,
					new HashMapDictionary<String, Object>() {
						{
							put("enabled", true);
							put("confidenceThreshold", 0.8);
						}
					})) {

			unsafeRunnable.run();
		}
	}

	private static final String _OPENNLP_AUTO_TAG_CONFIGURATION_CLASS_NAME =
		"com.liferay.document.library.asset.auto.tagger.opennlp.internal." +
			"configuration." +
				"OpenNPLDocumentAssetAutoTagProviderCompanyConfiguration";

	@Inject(
		filter = "component.name=com.liferay.document.library.asset.auto.tagger.opennlp.internal.OpenNLPDocumentAssetAutoTagProvider"
	)
	private AssetAutoTagProvider _assetAutoTagProvider;

	@Inject
	private DLAppService _dlAppService;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

}