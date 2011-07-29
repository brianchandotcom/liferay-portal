/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.FileModel;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLIndexer;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class DLStoreIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {FileModel.class.getName()};

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		FileModel fileModel = (FileModel)obj;

		Document document = new DocumentImpl();

		document.addUID(
			fileModel.getPortletId(), fileModel.getRepositoryId(),
			fileModel.getFileName());

		SearchEngineUtil.deleteDocument(
			fileModel.getCompanyId(), document.get(Field.UID));
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		FileModel fileModel = (FileModel)obj;

		long folderId = DLFolderConstants.getFolderId(
			fileModel.getGroupId(), fileModel.getRepositoryId());

		DLFileEntry fileEntry = null;

		try {
			if (fileModel.getFileEntryId() > 0) {
				fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
					fileModel.getFileEntryId());
			}
			else {
				fileEntry = DLFileEntryLocalServiceUtil.getFileEntryByName(
					fileModel.getGroupId(), folderId, fileModel.getFileName());

				fileModel.setFileEntryId(fileEntry.getFileEntryId());
			}
		}
		catch (NoSuchFileEntryException nsfe) {
			if (_log.isDebugEnabled()) {
				_log.debug("Not indexing document " + fileModel);
			}

			return null;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing document " + fileModel);
		}

		boolean indexContent = true;

		InputStream is = null;

		try {
			try {
				Store hook = StoreFactory.getInstance();

				if (PropsValues.DL_FILE_INDEXING_MAX_SIZE == 0) {
					indexContent = false;
				}
				else if (PropsValues.DL_FILE_INDEXING_MAX_SIZE != -1) {
					long size = hook.getFileSize(
						fileModel.getCompanyId(), fileModel.getRepositoryId(),
						fileModel.getFileName());

					if (size > PropsValues.DL_FILE_INDEXING_MAX_SIZE) {
						indexContent = false;
					}
				}

				if (indexContent) {
					String[] ignoreExtensions = PrefsPropsUtil.getStringArray(
						PropsKeys.DL_FILE_INDEXING_IGNORE_EXTENSIONS,
						StringPool.COMMA);

					if (ArrayUtil.contains(
							ignoreExtensions,
							StringPool.PERIOD + fileEntry.getExtension())) {

						indexContent = false;
					}
				}

				if (indexContent) {
					is = hook.getFileAsStream(
						fileModel.getCompanyId(), fileModel.getRepositoryId(),
						fileModel.getFileName());
				}
			}
			catch (Exception e) {
			}

			if (indexContent && (is == null)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Document " + fileModel + " does not have any content");
				}

				return null;
			}

			Document document = new DocumentImpl();

			document.addUID(
				fileModel.getPortletId(), fileModel.getRepositoryId(),
				fileModel.getFileName());

			long[] assetCategoryIds = fileModel.getAssetCategoryIds();

			if (assetCategoryIds == null) {
				assetCategoryIds =
					AssetCategoryLocalServiceUtil.getCategoryIds(
						DLFileEntry.class.getName(),
					fileEntry.getFileEntryId());
			}

			document.addKeyword(Field.ASSET_CATEGORY_IDS, assetCategoryIds);

			String[] assetCategoryNames = fileModel.getAssetCategoryNames();

			if (assetCategoryNames == null) {
				assetCategoryNames =
					AssetCategoryLocalServiceUtil.getCategoryNames(
						DLFileEntry.class.getName(),
					fileEntry.getFileEntryId());
			}

			document.addKeyword(Field.ASSET_CATEGORY_NAMES, assetCategoryNames);

			String[] assetTagNames = fileModel.getAssetTagNames();

			if (assetTagNames == null) {
				assetTagNames = AssetTagLocalServiceUtil.getTagNames(
					DLFileEntry.class.getName(), fileEntry.getFileEntryId());
			}

			document.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);

			document.addKeyword(Field.COMPANY_ID, fileModel.getCompanyId());

			if (indexContent) {
				try {
					document.addFile(Field.CONTENT, is, fileEntry.getTitle());
				}
				catch (IOException ioe) {
					throw new SearchException(
						"Cannot extract text from file" + fileModel);
				}
			}

			document.addText(Field.DESCRIPTION, fileEntry.getDescription());
			document.addKeyword(
				Field.ENTRY_CLASS_NAME, DLFileEntry.class.getName());
			document.addKeyword(
				Field.ENTRY_CLASS_PK, fileEntry.getFileEntryId());
			document.addKeyword(Field.FOLDER_ID, folderId);
			document.addKeyword(
				Field.GROUP_ID, getParentGroupId(fileModel.getGroupId()));

			Date modifiedDate = fileModel.getModifiedDate();

			if (modifiedDate == null) {
				modifiedDate = fileEntry.getModifiedDate();
			}

			document.addDate(Field.MODIFIED_DATE, modifiedDate);

			document.addKeyword(Field.PORTLET_ID, fileModel.getPortletId());

			String properties = fileModel.getProperties();

			if (properties == null) {
				properties = fileEntry.getLuceneProperties();
			}

			document.addText(Field.PROPERTIES, properties);

			document.addKeyword(Field.SCOPE_GROUP_ID, fileModel.getGroupId());

			DLFileVersion fileVersion = fileEntry.getFileVersion();

			document.addKeyword(Field.STATUS, fileVersion.getStatus());

			document.addText(Field.TITLE, fileEntry.getTitle());

			long userId = fileModel.getUserId();

			if (userId == 0) {
				userId = fileEntry.getUserId();
			}

			document.addKeyword(Field.USER_ID, userId);

			String userName = PortalUtil.getUserName(
				userId, fileEntry.getUserName());

			document.addKeyword(Field.USER_NAME, userName, true);

			document.addKeyword("extension", fileEntry.getExtension());
			document.addKeyword(
				"fileEntryTypeId", fileEntry.getFileEntryTypeId());
			document.addKeyword("path", fileModel.getFileName());
			document.addKeyword("repositoryId", fileModel.getRepositoryId());

			ExpandoBridge expandoBridge =
				ExpandoBridgeFactoryUtil.getExpandoBridge(
					fileModel.getCompanyId(), DLFileEntry.class.getName(),
					fileVersion.getFileVersionId());

			ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

			if (_log.isDebugEnabled()) {
				_log.debug("Document " + fileModel + " indexed successfully");
			}

			return document;
		}
		finally {
			if (is != null) {
				try {
					is.close();
				}
				catch (IOException ioe) {
				}
			}
		}
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

		return null;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		FileModel fileModel = (FileModel)obj;

		Document document = getDocument(fileModel);

		if (document != null) {
			SearchEngineUtil.updateDocument(fileModel.getCompanyId(), document);
		}
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);
		String portletId = ids[1];
		long groupId = GetterUtil.getLong(ids[2]);
		long repositoryId = GetterUtil.getLong(ids[3]);

		Collection<Document> documents = new ArrayList<Document>();

		Store hook = StoreFactory.getInstance();

		String[] fileNames = hook.getFileNames(companyId, repositoryId);

		for (String fileName : fileNames) {
			Indexer indexer = IndexerRegistryUtil.getIndexer(FileModel.class);

			FileModel fileModel = new FileModel();

			fileModel.setCompanyId(companyId);
			fileModel.setFileName(fileName);
			fileModel.setGroupId(groupId);
			fileModel.setPortletId(portletId);
			fileModel.setRepositoryId(repositoryId);

			Document document = indexer.getDocument(fileModel);

			if (document == null) {
				continue;
			}

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(companyId, documents);
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return (String)searchContext.getAttribute("portletId");
	}

	private static Log _log = LogFactoryUtil.getLog(DLIndexer.class);

}