/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			null, TestPropsValues.getUserId(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), mimeType,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, new ByteArrayInputStream(bytes), bytes.length,
			null, null, ServiceContextTestUtil.getServiceContext());

		return assetEntryLocalService.fetchEntry(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());
	}

	@Inject
	private DLTrashLocalService _dlTrashLocalService;

}