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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.FileModel;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.store.DLStoreIndexer;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

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
 */
public class DLIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {DLFileEntry.class.getName()};

	public static final String PORTLET_ID = PortletKeys.DOCUMENT_LIBRARY;

	public DLIndexer() {
		IndexerRegistryUtil.register(new DLStoreIndexer());
	}

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
			for (long groupId : searchContext.getGroupIds()) {
				List<DLFileEntryType> dlFileEntryTypes =
					DLFileEntryTypeLocalServiceUtil.getFileEntryTypes(groupId);

				for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
					ddmStructuresSet.addAll(dlFileEntryType.getDDMStructures());
				}
			}
		}

		for (DDMStructure ddmStructure : ddmStructuresSet) {
			addSearchDDMStruture(searchQuery, searchContext, ddmStructure);
		}

		addSearchTerm(searchQuery, searchContext, Field.USER_NAME, true);

		addSearchTerm(searchQuery, searchContext, "extension", true);
		addSearchTerm(searchQuery, searchContext, "fileEntryTypeId", false);
		addSearchTerm(searchQuery, searchContext, "path", true);

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params != null) {
			String expandoAttributes = (String)params.get("expandoAttributes");

			if (Validator.isNotNull(expandoAttributes)) {
				addSearchExpando(searchQuery, searchContext, expandoAttributes);
			}
		}
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		DLFileEntry dlFileEntry = (DLFileEntry)obj;

		FileModel fileModel = new FileModel();

		fileModel.setCompanyId(dlFileEntry.getCompanyId());
		fileModel.setFileName(dlFileEntry.getName());
		fileModel.setPortletId(PORTLET_ID);
		fileModel.setRepositoryId(dlFileEntry.getDataRepositoryId());

		Indexer indexer = IndexerRegistryUtil.getIndexer(FileModel.class);

		indexer.delete(fileModel);
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		DLFileEntry dlFileEntry = (DLFileEntry)obj;

		FileModel fileModel = new FileModel();

		long[] assetCategoryIds = AssetCategoryLocalServiceUtil.getCategoryIds(
			DLFileEntry.class.getName(), dlFileEntry.getFileEntryId());

		fileModel.setAssetCategoryIds(assetCategoryIds);

		String[] assetCategoryNames =
			AssetCategoryLocalServiceUtil.getCategoryNames(
				DLFileEntry.class.getName(), dlFileEntry.getFileEntryId());

		fileModel.setAssetCategoryNames(assetCategoryNames);

		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
			DLFileEntry.class.getName(), dlFileEntry.getFileEntryId());

		fileModel.setAssetTagNames(assetTagNames);

		fileModel.setCompanyId(dlFileEntry.getCompanyId());
		fileModel.setCreateDate(dlFileEntry.getCreateDate());
		fileModel.setFileEntryId(dlFileEntry.getFileEntryId());
		fileModel.setFileName(dlFileEntry.getName());
		fileModel.setGroupId(dlFileEntry.getGroupId());
		fileModel.setModifiedDate(dlFileEntry.getModifiedDate());
		fileModel.setPortletId(PORTLET_ID);
		fileModel.setProperties(dlFileEntry.getLuceneProperties());
		fileModel.setRepositoryId(dlFileEntry.getDataRepositoryId());
		fileModel.setUserId(dlFileEntry.getUserId());
		fileModel.setUserName(dlFileEntry.getUserName());
		fileModel.setUserUuid(dlFileEntry.getUserUuid());

		Indexer indexer = IndexerRegistryUtil.getIndexer(FileModel.class);

		return indexer.getDocument(fileModel);
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
		long companyId = GetterUtil.getLong(ids[0]);

		reindexFolders(companyId);
		reindexRoot(companyId);
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
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
				String.valueOf(companyId), portletId,
				String.valueOf(groupId), String.valueOf(folderId)
			};

			Indexer indexer = IndexerRegistryUtil.getIndexer(FileModel.class);

			indexer.reindex(newIds);
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
				String.valueOf(companyId), portletId,
				String.valueOf(groupId), String.valueOf(folderId)
			};

			Indexer indexer = IndexerRegistryUtil.getIndexer(FileModel.class);

			indexer.reindex(newIds);
		}
	}

	private static final boolean _FILTER_SEARCH = true;

}