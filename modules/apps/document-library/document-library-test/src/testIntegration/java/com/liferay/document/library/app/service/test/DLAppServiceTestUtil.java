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

package com.liferay.document.library.app.service.test;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.test.util.BaseDLAppTestCase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestDataConstants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.security.permission.DoAsUserThread;

import java.util.Dictionary;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;

/**
 * @author Alexander Chow
 */
public class DLAppServiceTestUtil {

	protected static FileEntry addFileEntry(long groupId, long folderId)
		throws Exception {

		return addFileEntry(groupId, folderId, FILE_NAME);
	}

	protected static FileEntry addFileEntry(
			long groupId, long folderId, String fileName)
		throws Exception {

		return addFileEntry(groupId, folderId, fileName, fileName, null);
	}

	protected static FileEntry addFileEntry(
			long groupId, long folderId, String fileName, String title,
			String[] assetTagNames)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setAssetTagNames(assetTagNames);

		return DLAppServiceUtil.addFileEntry(
			groupId, folderId, fileName, ContentTypes.TEXT_PLAIN, title,
			StringPool.BLANK, StringPool.BLANK,
			BaseDLAppTestCase.CONTENT.getBytes(), serviceContext);
	}

	protected static ConfigurationTemporarySwapper
			getConfigurationTemporarySwapper(String key, Object value)
		throws Exception {

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put(key, value);

		return new ConfigurationTemporarySwapper(
			DL_CONFIGURATION_PID, dictionary);
	}

	protected static AtomicInteger registerDLSyncEventProcessorMessageListener(
		final String targetEvent) {

		final AtomicInteger counter = new AtomicInteger();

		MessageBusUtil.registerMessageListener(
			DestinationNames.DOCUMENT_LIBRARY_SYNC_EVENT_PROCESSOR,
			new MessageListener() {

				@Override
				public void receive(Message message) {
					Object event = message.get("event");

					if (targetEvent.equals(event)) {
						counter.incrementAndGet();
					}
				}

			});

		return counter;
	}

	protected static int runUserThreads(DoAsUserThread[] doAsUserThreads)
		throws Exception {

		for (DoAsUserThread doAsUserThread : doAsUserThreads) {
			doAsUserThread.start();
		}

		for (DoAsUserThread doAsUserThread : doAsUserThreads) {
			doAsUserThread.join();
		}

		int successCount = 0;

		for (DoAsUserThread doAsUserThread : doAsUserThreads) {
			if (doAsUserThread.isSuccess()) {
				successCount++;
			}
		}

		return successCount;
	}

	protected static void search(
			FileEntry fileEntry, String keywords, boolean expected)
		throws Exception {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute("paginationType", "regular");
		searchContext.setCompanyId(fileEntry.getCompanyId());
		searchContext.setFolderIds(new long[] {fileEntry.getFolderId()});
		searchContext.setGroupIds(new long[] {fileEntry.getRepositoryId()});
		searchContext.setKeywords(keywords);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		Indexer<DLFileEntry> indexer = IndexerRegistryUtil.getIndexer(
			DLFileEntryConstants.getClassName());

		Hits hits = indexer.search(searchContext);

		boolean found = false;

		for (Document document : hits.getDocs()) {
			long fileEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			if (fileEntryId == fileEntry.getFileEntryId()) {
				found = true;

				break;
			}
		}

		Assert.assertEquals(hits.toString(), expected, found);
	}

	protected static void searchFile(long groupId, long folderId)
		throws Exception {

		FileEntry fileEntry = addFileEntry(groupId, folderId);

		search(fileEntry, "title", true);
		search(fileEntry, "content", true);

		DLAppServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
	}

	protected static FileEntry updateFileEntry(
			long groupId, long fileEntryId, String fileName,
			boolean majorVersion)
		throws Exception {

		return DLAppServiceUtil.updateFileEntry(
			fileEntryId, fileName, ContentTypes.TEXT_PLAIN, fileName,
			StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.fromMajorVersion(majorVersion),
			TestDataConstants.TEST_BYTE_ARRAY,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	protected static final String DL_CONFIGURATION_PID =
		"com.liferay.document.library.configuration.DLConfiguration";

	protected static final String FILE_NAME = "Title.txt";

	protected static final String STRIPPED_FILE_NAME = "Title";

}