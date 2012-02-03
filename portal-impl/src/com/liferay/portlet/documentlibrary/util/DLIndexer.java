/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMIndexerUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Alexander Chow
 */
public class DLIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {DLFileEntry.class.getName()};

	public static final String PORTLET_ID = PortletKeys.DOCUMENT_LIBRARY;

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long entryClassPK,
			String actionId)
		throws Exception {

		return DLFileEntryPermission.contains(
			permissionChecker, entryClassPK, ActionKeys.VIEW);
	}

	@Override
	public boolean isFilterSearch() {
		return _FILTER_SEARCH;
	}

	@Override
	public boolean isPermissionAware() {
		return _PERMISSION_AWARE;
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_APPROVED);

		if (status != WorkflowConstants.STATUS_ANY) {
			contextQuery.addRequiredTerm(Field.STATUS, status);
		}

		long[] folderIds = searchContext.getFolderIds();

		if ((folderIds != null) && (folderIds.length > 0)) {
			if (folderIds[0] == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				return;
			}

			BooleanQuery folderIdsQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			for (long folderId : folderIds) {
				try {
					DLFolderServiceUtil.getFolder(folderId);
				}
				catch (Exception e) {
					continue;
				}

				folderIdsQuery.addTerm(Field.FOLDER_ID, folderId);
			}

			contextQuery.add(folderIdsQuery, BooleanClauseOccur.MUST);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		Set<DDMStructure> ddmStructuresSet = new TreeSet<DDMStructure>();

		long[] groupIds = searchContext.getGroupIds();

		if ((groupIds != null) && (groupIds.length > 0)) {
			List<DLFileEntryType> dlFileEntryTypes =
				DLFileEntryTypeLocalServiceUtil.getFileEntryTypes(groupIds);

			for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
				ddmStructuresSet.addAll(dlFileEntryType.getDDMStructures());
			}
		}

		Group group = GroupLocalServiceUtil.getCompanyGroup(
			searchContext.getCompanyId());

		DDMStructure tikaRawMetadataStructure =
			DDMStructureLocalServiceUtil.fetchStructure(
				group.getGroupId(), "TikaRawMetadata");

		if (tikaRawMetadataStructure != null) {
			ddmStructuresSet.add(tikaRawMetadataStructure);
		}

		for (DDMStructure ddmStructure : ddmStructuresSet) {
			addSearchDDMStruture(searchQuery, searchContext, ddmStructure);
		}

		addSearchTerm(searchQuery, searchContext, Field.USER_NAME, false);

		addSearchTerm(searchQuery, searchContext, "extension", false);
		addSearchTerm(searchQuery, searchContext, "fileEntryTypeId", false);
		addSearchTerm(searchQuery, searchContext, "path", false);

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params != null) {
			String expandoAttributes = (String)params.get("expandoAttributes");

			if (Validator.isNotNull(expandoAttributes)) {
				addSearchExpando(searchQuery, searchContext, expandoAttributes);
			}
		}
	}

	protected void addFileEntryTypeAttributes(
			Document document, DLFileVersion dlFileVersion)
		throws PortalException, SystemException {

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.getFileEntryType(
				dlFileVersion.getFileEntryTypeId());

		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		Group group = GroupLocalServiceUtil.getCompanyGroup(
			dlFileVersion.getCompanyId());

		DDMStructure tikaRawMetadataStructure =
			DDMStructureLocalServiceUtil.fetchStructure(
				group.getGroupId(), "TikaRawMetadata");

		if (tikaRawMetadataStructure != null) {
			ddmStructures = ListUtil.copy(ddmStructures);

			ddmStructures.add(tikaRawMetadataStructure);
		}

		for (DDMStructure ddmStructure : ddmStructures) {
			Fields fields = null;

			try {
				DLFileEntryMetadata fileEntryMetadata =
					DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(
						ddmStructure.getStructureId(),
						dlFileVersion.getFileVersionId());

				fields = StorageEngineUtil.getFields(
					fileEntryMetadata.getDDMStorageId());
			}
			catch (Exception e) {
			}

			if (fields != null) {
				DDMIndexerUtil.addAttributes(document, ddmStructure, fields);
			}
		}
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		DLFileEntry dlFileEntry = (DLFileEntry)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, dlFileEntry.getFileEntryId());

		SearchEngineUtil.deleteDocument(
			dlFileEntry.getCompanyId(), document.get(Field.UID));
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		DLFileEntry dlFileEntry = (DLFileEntry)obj;

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing document " + dlFileEntry);
		}

		boolean indexContent = true;

		InputStream is = null;

		try {
			if (PropsValues.DL_FILE_INDEXING_MAX_SIZE == 0) {
				indexContent = false;
			}
			else if (PropsValues.DL_FILE_INDEXING_MAX_SIZE != -1) {
				if (dlFileEntry.getSize() >
						PropsValues.DL_FILE_INDEXING_MAX_SIZE) {

					indexContent = false;
				}
			}

			if (indexContent) {
				String[] ignoreExtensions = PrefsPropsUtil.getStringArray(
					PropsKeys.DL_FILE_INDEXING_IGNORE_EXTENSIONS,
					StringPool.COMMA);

				if (ArrayUtil.contains(
						ignoreExtensions,
						StringPool.PERIOD + dlFileEntry.getExtension())) {

					indexContent = false;
				}
			}

			if (indexContent) {
				is = dlFileEntry.getFileVersion().getContentStream(false);
			}
		}
		catch (Exception e) {
		}

		if (indexContent && (is == null)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Document " + dlFileEntry + " does not have any content");
			}

			return null;
		}

		try {
			Document document = new DocumentImpl();

			long fileEntryId = dlFileEntry.getFileEntryId();

			document.addUID(PORTLET_ID, fileEntryId);

			List<AssetCategory> assetCategories =
				AssetCategoryLocalServiceUtil.getCategories(
					DLFileEntry.class.getName(), fileEntryId);

			long[] assetCategoryIds = StringUtil.split(
				ListUtil.toString(
					assetCategories, AssetCategory.CATEGORY_ID_ACCESSOR),
				0L);

			document.addKeyword(Field.ASSET_CATEGORY_IDS, assetCategoryIds);

			String[] assetCategoryNames = StringUtil.split(
				ListUtil.toString(
					assetCategories, AssetCategory.NAME_ACCESSOR));

			document.addKeyword(Field.ASSET_CATEGORY_NAMES, assetCategoryNames);

			String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
				DLFileEntry.class.getName(), fileEntryId);

			document.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);

			document.addKeyword(Field.COMPANY_ID, dlFileEntry.getCompanyId());

			if (indexContent) {
				try {
					document.addFile(Field.CONTENT, is, dlFileEntry.getTitle());
				}
				catch (IOException ioe) {
					throw new SearchException(
						"Cannot extract text from file" + dlFileEntry);
				}
			}

			document.addText(Field.DESCRIPTION, dlFileEntry.getDescription());
			document.addKeyword(
				Field.ENTRY_CLASS_NAME, DLFileEntry.class.getName());
			document.addKeyword(Field.ENTRY_CLASS_PK, fileEntryId);
			document.addKeyword(Field.FOLDER_ID, dlFileEntry.getFolderId());
			document.addKeyword(
				Field.GROUP_ID, getParentGroupId(dlFileEntry.getGroupId()));
			document.addDate(
				Field.MODIFIED_DATE, dlFileEntry.getModifiedDate());
			document.addKeyword(Field.PORTLET_ID, PORTLET_ID);
			document.addText(
				Field.PROPERTIES, dlFileEntry.getLuceneProperties());
			document.addKeyword(Field.SCOPE_GROUP_ID, dlFileEntry.getGroupId());

			DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

			document.addKeyword(Field.STATUS, dlFileVersion.getStatus());
			document.addText(Field.TITLE, dlFileEntry.getTitle());

			long userId = dlFileEntry.getUserId();

			document.addKeyword(Field.USER_ID, userId);
			document.addKeyword(
				Field.USER_NAME, PortalUtil.getUserName(
					userId, dlFileEntry.getUserName()),
				true);

			document.addKeyword(
				"dataRepositoryId", dlFileEntry.getDataRepositoryId());
			document.addKeyword("extension", dlFileEntry.getExtension());
			document.addKeyword(
				"fileEntryTypeId", dlFileEntry.getFileEntryTypeId());
			document.addKeyword("path", dlFileEntry.getTitle());

			ExpandoBridge expandoBridge =
				ExpandoBridgeFactoryUtil.getExpandoBridge(
					dlFileEntry.getCompanyId(), DLFileEntry.class.getName(),
					dlFileVersion.getFileVersionId());

			ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

			addFileEntryTypeAttributes(document, dlFileVersion);

			if (_log.isDebugEnabled()) {
				_log.debug("Document " + dlFileEntry + " indexed successfully");
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

		LiferayPortletURL liferayPortletURL = (LiferayPortletURL)portletURL;

		liferayPortletURL.setLifecycle(PortletRequest.ACTION_PHASE);

		try {
			liferayPortletURL.setWindowState(LiferayWindowState.EXCLUSIVE);
		}
		catch (WindowStateException wse) {
		}

		String title = document.get(Field.TITLE);

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(document.get(Field.CONTENT), 200);
		}

		String fileEntryId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter("struts_action", "/document_library/get_file");
		portletURL.setParameter("fileEntryId", fileEntryId);

		return new Summary(title, content, portletURL);
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		DLFileEntry dlFileEntry = (DLFileEntry)obj;

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		if (!dlFileVersion.isApproved()) {
			return;
		}

		Document document = getDocument(dlFileEntry);

		if (document != null) {
			SearchEngineUtil.updateDocument(
				dlFileEntry.getCompanyId(), document);
		}
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
			classPK);

		doReindex(dlFileEntry);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		if (ids.length == 1) {
			long companyId = GetterUtil.getLong(ids[0]);

			reindexFolders(companyId);
			reindexRoot(companyId);
		}
		else {
			long companyId = GetterUtil.getLong(ids[0]);
			long groupId = GetterUtil.getLong(ids[2]);
			long dataRepositoryId = GetterUtil.getLong(ids[3]);

			reindexFileEntries(companyId, groupId, dataRepositoryId);
		}
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void reindexFileEntries(
			long companyId, long groupId, long dataRepositoryId)
		throws Exception {

		long folderId = DLFolderConstants.getFolderId(
			groupId, dataRepositoryId);

		int fileEntriesCount = DLFileEntryLocalServiceUtil.getFileEntriesCount(
			companyId, folderId);

		int fileEntriesPages = fileEntriesCount / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= fileEntriesPages; i++) {
			int fileEntriesStart = (i * Indexer.DEFAULT_INTERVAL);
			int fileEntriesEnd = fileEntriesStart + Indexer.DEFAULT_INTERVAL;

			reindexFileEntries(
				companyId, groupId, folderId, fileEntriesStart, fileEntriesEnd);
		}
	}

	protected void reindexFileEntries(
			long companyId, long groupId, long folderId, int fileEntriesStart,
			int fileEntriesEnd)
		throws Exception {

		Collection<Document> documents = new ArrayList<Document>();

		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getFileEntries(
				groupId, folderId, fileEntriesStart, fileEntriesEnd, null);

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			Document document = getDocument(dlFileEntry);

			if (document != null) {
				documents.add(document);
			}
		}

		SearchEngineUtil.updateDocuments(companyId, documents);
	}

	protected void reindexFolders(long companyId) throws Exception {
		int folderCount = DLFolderLocalServiceUtil.getCompanyFoldersCount(
			companyId);

		int folderPages = folderCount / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= folderPages; i++) {
			int folderStart = (i * Indexer.DEFAULT_INTERVAL);
			int folderEnd = folderStart + Indexer.DEFAULT_INTERVAL;

			reindexFolders(companyId, folderStart, folderEnd);
		}
	}

	protected void reindexFolders(
			long companyId, int folderStart, int folderEnd)
		throws Exception {

		List<DLFolder> dlFolders = DLFolderLocalServiceUtil.getCompanyFolders(
			companyId, folderStart, folderEnd);

		for (DLFolder dlFolder : dlFolders) {
			String portletId = PortletKeys.DOCUMENT_LIBRARY;
			long groupId = dlFolder.getGroupId();
			long folderId = dlFolder.getFolderId();

			String[] newIds = {
				String.valueOf(companyId), portletId, String.valueOf(groupId),
				String.valueOf(folderId)
			};

			reindex(newIds);
		}
	}

	protected void reindexRoot(long companyId) throws Exception {
		int groupCount = GroupLocalServiceUtil.getCompanyGroupsCount(companyId);

		int groupPages = groupCount / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= groupPages; i++) {
			int groupStart = (i * Indexer.DEFAULT_INTERVAL);
			int groupEnd = groupStart + Indexer.DEFAULT_INTERVAL;

			reindexRoot(companyId, groupStart, groupEnd);
		}
	}

	protected void reindexRoot(long companyId, int groupStart, int groupEnd)
		throws Exception {

		List<Group> groups = GroupLocalServiceUtil.getCompanyGroups(
			companyId, groupStart, groupEnd);

		for (Group group : groups) {
			String portletId = PortletKeys.DOCUMENT_LIBRARY;
			long groupId = group.getGroupId();
			long folderId = groupId;

			String[] newIds = {
				String.valueOf(companyId), portletId, String.valueOf(groupId),
				String.valueOf(folderId)
			};

			reindex(newIds);
		}
	}

	private static final boolean _FILTER_SEARCH = true;

	private static final boolean _PERMISSION_AWARE = true;

	private static Log _log = LogFactoryUtil.getLog(DLIndexer.class);

}