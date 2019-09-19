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

package com.liferay.asset.auto.tagger.opennlp.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLTrashLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.test.rule.Inject;

import java.io.ByteArrayInputStream;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class FileEntryOpenNLPDocumentAssetAutoTaggerTest
	extends BaseOpenNLPDocumentAssetAutoTaggerTestCase {

	@Test
	public void testAFileCanBeMovedToTrashAfterBeingAutoTagged()
		throws Exception {

		testWithOpenNLPDocumentAssetAutoTagProviderEnabled(
			getClassName(),
			() -> {
				AssetEntry assetEntry = getAssetEntry(getTaggableText());

				_dlTrashLocalService.moveFileEntryToTrash(
					TestPropsValues.getUserId(), group.getGroupId(),
					assetEntry.getClassPK());
			});
	}

	@Test
	public void testDoesNotAutoTagAFileEntryWithAnUnsupportedContentType()
		throws Exception {

		testWithOpenNLPDocumentAssetAutoTagProviderEnabled(
			getClassName(),
			() -> {
				AssetEntry assetEntry = _getAssetEntry(
					getTaggableText(), ContentTypes.APPLICATION_GZIP);

				Collection<String> tagNames = Arrays.asList(
					assetEntry.getTagNames());

				Assert.assertEquals(tagNames.toString(), 0, tagNames.size());
			});
	}

	@Override
	protected AssetEntry getAssetEntry(String text) throws Exception {
		return _getAssetEntry(text, ContentTypes.TEXT_PLAIN);
	}

	@Override
	protected String getClassName() {
		return DLFileEntryConstants.getClassName();
	}

	private AssetEntry _getAssetEntry(String text, String mimeType)
		throws Exception {

		byte[] bytes = text.getBytes();

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), mimeType,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			new ByteArrayInputStream(bytes), bytes.length,
			ServiceContextTestUtil.getServiceContext());

		return assetEntryLocalService.fetchEntry(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());
	}

	@Inject
	private DLTrashLocalService _dlTrashLocalService;

}