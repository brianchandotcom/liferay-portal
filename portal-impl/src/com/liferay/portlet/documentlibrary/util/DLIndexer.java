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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
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
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.asset.DLFileEntryAssetRendererFactory;
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

	public DLIndexer() {
		setFilterSearch(true);
		setPermissionAware(true);
	}

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		return DLFileEntryPermission.contains(
			permissionChecker, entryClassPK, ActionKeys.VIEW);
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

		String keywords = searchContext.getKeywords();

		if (Validator.isNull(keywords)) {
			addSearchTerm(searchQuery, searchContext, Field.DESCRIPTION, false);
			addSearchTerm(searchQuery, searchContext, Field.TITLE, false);
			addSearchTerm(searchQuery, searchContext, Field.USER_NAME, false);
		}

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

	protected void addReindexCriteria(
		DynamicQuery dynamicQuery, long companyId) {

		Property property = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(property.eq(companyId));
	}

	protected void addReindexCriteria(
		DynamicQuery dynamicQuery, long groupId, long folderId) {

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

		dynamicQuery.add(groupIdProperty.eq(groupId));

		Property folderIdProperty = PropertyFactoryUtil.forName("folderId");

		dynamicQuery.add(folderIdProperty.eq(folderId));
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		DLFileEntry dlFileEntry = (DLFileEntry)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, dlFileEntry.getFileEntryId());

		SearchEngineUtil.deleteDocument(
			getSearchEngineId(), dlFileEntry.getCompanyId(),
			document.get(Field.UID));
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

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		try {
			Document document = getBaseModelDocument(
				PORTLET_ID, dlFileEntry, dlFileVersion);

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
			document.addKeyword(Field.FOLDER_ID, dlFileEntry.getFolderId());
			document.addText(
				Field.PROPERTIES, dlFileEntry.getLuceneProperties());
			document.addText(Field.TITLE, dlFileEntry.getTitle());

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

			if (!dlFileVersion.isInTrash() && dlFileVersion.isInTrashFolder()) {
				DLFolder trashedFolder = dlFileVersion.getTrashFolder();

				addTrashFields(
					document, DLFolder.class.getName(),
					trashedFolder.getFolderId(), null, null,
					DLFileEntryAssetRendererFactory.TYPE);

				document.addKeyword(
					Field.ROOT_ENTRY_CLASS_NAME, DLFolder.class.getName());
				document.addKeyword(
					Field.ROOT_ENTRY_CLASS_PK, trashedFolder.getFolderId());
				document.addKeyword(
					Field.STATUS, WorkflowConstants.STATUS_IN_TRASH);
			}

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

		String fileEntryId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter("struts_action", "/document_library/get_file");
		portletURL.setParameter("fileEntryId", fileEntryId);

		Summary summary = createSummary(document, Field.TITLE, Field.CONTENT);

		summary.setMaxContentLength(200);
		summary.setPortletURL(portletURL);

		return summary;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		DLFileEntry dlFileEntry = (DLFileEntry)obj;

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		if (!dlFileVersion.isApproved() && !dlFileVersion.isInTrash()) {
			return;
		}

		Document document = getDocument(dlFileEntry);

		if (document != null) {
			SearchEngineUtil.updateDocument(
				getSearchEngineId(), dlFileEntry.getCompanyId(), document);
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

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DLFileEntry.class, PACLClassLoaderUtil.getPortalClassLoader());

		Projection minFileEntryIdProjection = ProjectionFactoryUtil.min(
			"fileEntryId");
		Projection maxFileEntryIdProjection = ProjectionFactoryUtil.max(
			"fileEntryId");

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(minFileEntryIdProjection);
		projectionList.add(maxFileEntryIdProjection);

		dynamicQuery.setProjection(projectionList);

		long folderId = DLFolderConstants.getFolderId(
			groupId, dataRepositoryId);

		addReindexCriteria(dynamicQuery, groupId, folderId);

		List<Object[]> results = DLFileEntryLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		Object[] minAndMaxFileEntryIds = results.get(0);

		if ((minAndMaxFileEntryIds[0] == null) ||
			(minAndMaxFileEntryIds[1] == null)) {

			return;
		}

		long minFileEntryId = (Long)minAndMaxFileEntryIds[0];
		long maxFileEntryId = (Long)minAndMaxFileEntryIds[1];

		long startFileEntryId = minFileEntryId;
		long endFileEntryId = startFileEntryId + DEFAULT_INTERVAL;

		while (startFileEntryId <= maxFileEntryId) {
			reindexFileEntries(
				companyId, groupId, folderId, startFileEntryId, endFileEntryId);

			startFileEntryId = endFileEntryId;
			endFileEntryId += DEFAULT_INTERVAL;
		}
	}

	protected void reindexFileEntries(
			long companyId, long groupId, long folderId, long startFileEntryId,
			long endFileEntryId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DLFileEntry.class, PACLClassLoaderUtil.getPortalClassLoader());

		Property property = PropertyFactoryUtil.forName("fileEntryId");

		dynamicQuery.add(property.ge(startFileEntryId));
		dynamicQuery.add(property.lt(endFileEntryId));

		addReindexCriteria(dynamicQuery, groupId, folderId);

		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.dynamicQuery(dynamicQuery);

		if (dlFileEntries.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>(
			dlFileEntries.size());

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			Document document = getDocument(dlFileEntry);

			if (document != null) {
				documents.add(document);
			}
		}

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents);
	}

	protected void reindexFolders(long companyId) throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DLFolder.class, PACLClassLoaderUtil.getPortalClassLoader());

		Projection minFolderIdProjection = ProjectionFactoryUtil.min(
			"folderId");
		Projection maxFolderIdProjection = ProjectionFactoryUtil.max(
			"folderId");

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(minFolderIdProjection);
		projectionList.add(maxFolderIdProjection);

		dynamicQuery.setProjection(projectionList);

		addReindexCriteria(dynamicQuery, companyId);

		List<Object[]> results = BookmarksFolderLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		Object[] minAndMaxFolderIds = results.get(0);

		if ((minAndMaxFolderIds[0] == null) ||
			(minAndMaxFolderIds[1] == null)) {

			return;
		}

		long minFolderId = (Long)minAndMaxFolderIds[0];
		long maxFolderId = (Long)minAndMaxFolderIds[1];

		long startFolderId = minFolderId;
		long endFolderId = startFolderId + DEFAULT_INTERVAL;

		while (startFolderId <= maxFolderId) {
			reindexFolders(companyId, startFolderId, endFolderId);

			startFolderId = endFolderId;
			endFolderId += DEFAULT_INTERVAL;
		}
	}

	protected void reindexFolders(
			long companyId, long startFolderId, long endFolderId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DLFolder.class, PACLClassLoaderUtil.getPortalClassLoader());

		Property property = PropertyFactoryUtil.forName("folderId");

		dynamicQuery.add(property.ge(startFolderId));
		dynamicQuery.add(property.lt(endFolderId));

		addReindexCriteria(dynamicQuery, companyId);

		List<DLFolder> dlFolders = DLFolderLocalServiceUtil.dynamicQuery(
			dynamicQuery);

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
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Group.class, PACLClassLoaderUtil.getPortalClassLoader());

		Projection minGroupIdProjection = ProjectionFactoryUtil.min("groupId");
		Projection maxGroupIdProjection = ProjectionFactoryUtil.max("groupId");

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(minGroupIdProjection);
		projectionList.add(maxGroupIdProjection);

		dynamicQuery.setProjection(projectionList);

		addReindexCriteria(dynamicQuery, companyId);

		List<Object[]> results = GroupLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		Object[] minAndMaxGroupIds = results.get(0);

		if ((minAndMaxGroupIds[0] == null) || (minAndMaxGroupIds[1] == null)) {
			return;
		}

		long minGroupId = (Long)minAndMaxGroupIds[0];
		long maxGroupId = (Long)minAndMaxGroupIds[1];

		long startGroupId = minGroupId;
		long endGroupId = startGroupId + DEFAULT_INTERVAL;

		while (startGroupId <= maxGroupId) {
			reindexRoot(companyId, startGroupId, endGroupId);

			startGroupId = endGroupId;
			endGroupId += DEFAULT_INTERVAL;
		}
	}

	protected void reindexRoot(
			long companyId, long startGroupId, long endGroupId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Group.class, PACLClassLoaderUtil.getPortalClassLoader());

		Property property = PropertyFactoryUtil.forName("groupId");

		dynamicQuery.add(property.ge(startGroupId));
		dynamicQuery.add(property.lt(endGroupId));

		addReindexCriteria(dynamicQuery, companyId);

		List<Group> groups = GroupLocalServiceUtil.dynamicQuery(dynamicQuery);

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

	private static Log _log = LogFactoryUtil.getLog(DLIndexer.class);

}