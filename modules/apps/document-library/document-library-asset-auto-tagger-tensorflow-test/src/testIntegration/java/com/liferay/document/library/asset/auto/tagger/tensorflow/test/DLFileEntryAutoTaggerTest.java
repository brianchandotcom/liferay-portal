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

package com.liferay.document.library.asset.auto.tagger.tensorflow.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class DLFileEntryAutoTaggerTest {

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
	public void testAutoTagsABMPImage() throws Exception {
		_withTensorFlowAutoTagProviderEnabled(
			() -> {
				FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
					null, _serviceContext.getScopeGroupId(),
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
					"indigobunting.bmp", ContentTypes.IMAGE_BMP,
					"indigobunting", StringUtil.randomString(),
					StringUtil.randomString(), StringUtil.randomString(),
					FileUtil.getBytes(getClass(), "indigobunting.bmp"), null,
					null, _serviceContext);

				AssetEntry assetEntry = _assetEntryLocalService.getEntry(
					DLFileEntryConstants.getClassName(),
					fileEntry.getFileEntryId());

				_assertContainsTag(assetEntry, "indigo bunting");
			});
	}

	@Test
	public void testAutoTagsAJPEGImage() throws Exception {
		_withTensorFlowAutoTagProviderEnabled(
			() -> {
				FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
					null, _serviceContext.getScopeGroupId(),
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "goldfinch.jpg",
					ContentTypes.IMAGE_JPEG, "goldfinch",
					StringUtil.randomString(), StringUtil.randomString(),
					StringUtil.randomString(),
					FileUtil.getBytes(getClass(), "goldfinch.jpg"), null, null,
					_serviceContext);

				AssetEntry assetEntry = _assetEntryLocalService.getEntry(
					DLFileEntryConstants.getClassName(),
					fileEntry.getFileEntryId());

				_assertContainsTag(assetEntry, "goldfinch");
			});
	}

	@Test
	public void testAutoTagsAPNGImage() throws Exception {
		_withTensorFlowAutoTagProviderEnabled(
			() -> {
				FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
					null, _serviceContext.getScopeGroupId(),
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
					"hummingbird.png", ContentTypes.IMAGE_PNG, "hummingbird",
					StringUtil.randomString(), StringUtil.randomString(),
					StringUtil.randomString(),
					FileUtil.getBytes(getClass(), "hummingbird.png"), null,
					null, _serviceContext);

				AssetEntry assetEntry = _assetEntryLocalService.getEntry(
					DLFileEntryConstants.getClassName(),
					fileEntry.getFileEntryId());

				_assertContainsTag(assetEntry, "hummingbird");
			});
	}

	private void _assertContainsTag(AssetEntry assetEntry, String tag) {
		for (AssetTag assetTag : assetEntry.getTags()) {
			if (StringUtil.equals(assetTag.getName(), tag)) {
				return;
			}
		}

		throw new AssertionError("The asset entry was not tagged with " + tag);
	}

	private void _withTensorFlowAutoTagProviderEnabled(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				"enabled", true
			).build();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					StringBundler.concat(
						"com.liferay.document.library.asset.auto.tagger.",
						"tensorflow.internal.configuration.",
						"TensorFlowImageAssetAutoTagProviderCompany",
						"Configuration"),
					dictionary)) {

			unsafeRunnable.run();
		}
	}

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private AssetTagLocalService _assetTagLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

}