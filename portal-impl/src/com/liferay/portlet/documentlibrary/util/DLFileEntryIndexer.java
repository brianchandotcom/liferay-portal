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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BaseRelatedEntryIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentHelper;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.RelatedEntryIndexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.dynamicdatamapping.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.DDMStructureManager;
import com.liferay.portlet.dynamicdatamapping.DDMStructureManagerUtil;
import com.liferay.portlet.dynamicdatamapping.StorageEngineManagerUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Alexander Chow
 */
@OSGiBeanProperties
public class DLFileEntryIndexer
	extends BaseIndexer<DLFileEntry> implements RelatedEntryIndexer {

	public static final String CLASS_NAME = DLFileEntry.class.getName();

	public DLFileEntryIndexer() {
		setDefaultSelectedFieldNames(
			Field.ASSET_TAG_NAMES, Field.COMPANY_ID, Field.CONTENT,
			Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK, Field.GROUP_ID,
			Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID, Field.TITLE, Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public void addRelatedClassNames(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		_relatedEntryIndexer.addRelatedClassNames(
			contextBooleanFilter, searchContext);
	}

	@Override
	public void addRelatedEntryFields(Document document, Object obj)
		throws Exception {

		Comment comment = (Comment)obj;

		FileEntry fileEntry = null;

		try {
			fileEntry = DLAppLocalServiceUtil.getFileEntry(
				comment.getClassPK());
		}
		catch (Exception e) {
			return;
		}

		if (fileEntry instanceof LiferayFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			document.addKeyword(Field.FOLDER_ID, dlFileEntry.getFolderId());
			document.addKeyword(Field.HIDDEN, dlFileEntry.isInHiddenFolder());
			document.addKeyword(
				Field.TREE_PATH,
				StringUtil.split(dlFileEntry.getTreePath(), CharPool.SLASH));
		}
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
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
	public boolean isVisible(long classPK, int status) throws Exception {
		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(classPK);

		FileVersion fileVersion = fileEntry.getFileVersion();

		return isVisible(fileVersion.getStatus(), status);
	}

	@Override
	public boolean isVisibleRelatedEntry(long classPK, int status)
		throws Exception {

		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(classPK);

		if (fileEntry instanceof LiferayFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			if (dlFileEntry.isInHiddenFolder()) {
				Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
					dlFileEntry.getClassName());

				return indexer.isVisible(dlFileEntry.getClassPK(), status);
			}
		}

		return true;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		addStatus(contextBooleanFilter, searchContext);

		if (searchContext.isIncludeAttachments()) {
			addRelatedClassNames(contextBooleanFilter, searchContext);
		}

		if (ArrayUtil.contains(
				searchContext.getFolderIds(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			contextBooleanFilter.addRequiredTerm(
				Field.HIDDEN, searchContext.isIncludeAttachments());
		}

		addSearchClassTypeIds(contextBooleanFilter, searchContext);

		String ddmStructureFieldName = (String)searchContext.getAttribute(
			"ddmStructureFieldName");
		Serializable ddmStructureFieldValue = searchContext.getAttribute(
			"ddmStructureFieldValue");

		if (Validator.isNotNull(ddmStructureFieldName) &&
			Validator.isNotNull(ddmStructureFieldValue)) {

			String[] ddmStructureFieldNameParts = StringUtil.split(
				ddmStructureFieldName,
				DDMStructureManager.STRUCTURE_INDEXER_FIELD_SEPARATOR);

			DDMStructure ddmStructure = DDMStructureManagerUtil.getStructure(
				GetterUtil.getLong(ddmStructureFieldNameParts[2]));

			String fieldName = StringUtil.replaceLast(
				ddmStructureFieldNameParts[3],
				StringPool.UNDERLINE.concat(
					LocaleUtil.toLanguageId(searchContext.getLocale())),
				StringPool.BLANK);

			try {
				ddmStructureFieldValue =
					DDMStructureManagerUtil.getIndexedFieldValue(
						ddmStructureFieldValue,
						ddmStructure.getFieldType(fieldName));
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}

			BooleanQuery booleanQuery = new BooleanQueryImpl();

			booleanQuery.addRequiredTerm(
				ddmStructureFieldName,
				StringPool.QUOTE + ddmStructureFieldValue + StringPool.QUOTE);

			contextBooleanFilter.add(
				new QueryFilter(booleanQuery), BooleanClauseOccur.MUST);
		}

		String[] mimeTypes = (String[])searchContext.getAttribute("mimeTypes");

		if (ArrayUtil.isNotEmpty(mimeTypes)) {
			BooleanFilter mimeTypesBooleanFilter = new BooleanFilter();

			for (String mimeType : mimeTypes) {
				mimeTypesBooleanFilter.addTerm(
					"mimeType",
					StringUtil.replace(
						mimeType, CharPool.FORWARD_SLASH, CharPool.UNDERLINE));
			}

			contextBooleanFilter.add(
				mimeTypesBooleanFilter, BooleanClauseOccur.MUST);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		String keywords = searchContext.getKeywords();

		if (Validator.isNull(keywords)) {
			addSearchTerm(searchQuery, searchContext, Field.DESCRIPTION, false);
			addSearchTerm(searchQuery, searchContext, Field.TITLE, false);
			addSearchTerm(searchQuery, searchContext, Field.USER_NAME, false);
		}

		addSearchTerm(searchQuery, searchContext, "ddmContent", false);
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

	@Override
	public void updateFullQuery(SearchContext searchContext) {
		if (searchContext.isIncludeAttachments()) {
			searchContext.addFullQueryEntryClassName(
				DLFileEntry.class.getName());
		}
	}

	protected void addFileEntryTypeAttributes(
			Document document, DLFileVersion dlFileVersion)
		throws PortalException {

		List<DLFileEntryMetadata> dlFileEntryMetadatas =
			DLFileEntryMetadataLocalServiceUtil.
				getFileVersionFileEntryMetadatas(
					dlFileVersion.getFileVersionId());

		for (DLFileEntryMetadata dlFileEntryMetadata : dlFileEntryMetadatas) {
			DDMFormValues ddmFormValues = null;

			try {
				ddmFormValues = StorageEngineManagerUtil.getDDMFormValues(
					dlFileEntryMetadata.getDDMStorageId());
			}
			catch (Exception e) {
			}

			if (ddmFormValues != null) {
				DDMStructureManagerUtil.addAttributes(
					dlFileEntryMetadata.getDDMStructureId(), document,
					ddmFormValues);
			}
		}
	}

	@Override
	protected void doDelete(DLFileEntry dlFileEntry) throws Exception {
		deleteDocument(
			dlFileEntry.getCompanyId(), dlFileEntry.getFileEntryId());
	}

	@Override
	protected Document doGetDocument(DLFileEntry dlFileEntry) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Indexing document " + dlFileEntry);
		}

		boolean indexContent = true;

		InputStream is = null;

		try {
			String[] ignoreExtensions = PrefsPropsUtil.getStringArray(
				PropsKeys.DL_FILE_INDEXING_IGNORE_EXTENSIONS, StringPool.COMMA);

			if (ArrayUtil.contains(
					ignoreExtensions,
					StringPool.PERIOD + dlFileEntry.getExtension())) {

				indexContent = false;
			}

			if (indexContent) {
				is = dlFileEntry.getFileVersion().getContentStream(false);
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Error retrieving document stream", e);
			}
		}

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		try {
			Document document = getBaseModelDocument(
				CLASS_NAME, dlFileEntry, dlFileVersion);

			if (indexContent) {
				if (is != null) {
					try {
						document.addFile(
							Field.CONTENT, is, dlFileEntry.getTitle(),
							PropsValues.DL_FILE_INDEXING_MAX_SIZE);
					}
					catch (IOException ioe) {
						throw new SearchException(
							"Cannot extract text from file" + dlFileEntry);
					}
				}
				else if (_log.isDebugEnabled()) {
					_log.debug(
						"Document " + dlFileEntry +
							" does not have any content");
				}
			}

			document.addKeyword(
				Field.CLASS_TYPE_ID, dlFileEntry.getFileEntryTypeId());
			document.addText(Field.DESCRIPTION, dlFileEntry.getDescription());
			document.addKeyword(Field.FOLDER_ID, dlFileEntry.getFolderId());
			document.addKeyword(Field.HIDDEN, dlFileEntry.isInHiddenFolder());
			document.addText(
				Field.PROPERTIES, dlFileEntry.getLuceneProperties());
			document.addText(Field.TITLE, dlFileEntry.getTitle());
			document.addKeyword(
				Field.TREE_PATH,
				StringUtil.split(dlFileEntry.getTreePath(), CharPool.SLASH));

			document.addKeyword(
				"dataRepositoryId", dlFileEntry.getDataRepositoryId());
			document.addText(
				"ddmContent",
				extractDDMContent(dlFileVersion, LocaleUtil.getSiteDefault()));
			document.addKeyword("extension", dlFileEntry.getExtension());
			document.addKeyword(
				"fileEntryTypeId", dlFileEntry.getFileEntryTypeId());
			document.addKeyword(
				"mimeType",
				StringUtil.replace(
					dlFileEntry.getMimeType(), CharPool.FORWARD_SLASH,
					CharPool.UNDERLINE));
			document.addKeyword("path", dlFileEntry.getTitle());
			document.addKeyword("readCount", dlFileEntry.getReadCount());
			document.addKeyword("size", dlFileEntry.getSize());

			ExpandoBridge expandoBridge =
				ExpandoBridgeFactoryUtil.getExpandoBridge(
					dlFileEntry.getCompanyId(), DLFileEntry.class.getName(),
					dlFileVersion.getFileVersionId());

			ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

			addFileEntryTypeAttributes(document, dlFileVersion);

			if (dlFileEntry.isInHiddenFolder()) {
				Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
					dlFileEntry.getClassName());

				if ((indexer != null) &&
					(indexer instanceof RelatedEntryIndexer)) {

					RelatedEntryIndexer relatedEntryIndexer =
						(RelatedEntryIndexer)indexer;

					relatedEntryIndexer.addRelatedEntryFields(
						document, new LiferayFileEntry(dlFileEntry));

					DocumentHelper documentHelper = new DocumentHelper(
						document);

					documentHelper.setAttachmentOwnerKey(
						PortalUtil.getClassNameId(dlFileEntry.getClassName()),
						dlFileEntry.getClassPK());

					document.addKeyword(Field.RELATED_ENTRY, true);
				}
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
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(document, Field.TITLE, Field.CONTENT);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(DLFileEntry dlFileEntry) throws Exception {
		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		if (!dlFileVersion.isApproved() && !dlFileEntry.isInTrash()) {
			return;
		}

		Document document = getDocument(dlFileEntry);

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), dlFileEntry.getCompanyId(), document,
			isCommitImmediately());
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
			long groupId = GetterUtil.getLong(ids[1]);
			long dataRepositoryId = GetterUtil.getLong(ids[2]);

			reindexFileEntries(companyId, groupId, dataRepositoryId);
		}
	}

	protected String extractDDMContent(
			DLFileVersion dlFileVersion, Locale locale)
		throws Exception {

		List<DLFileEntryMetadata> dlFileEntryMetadatas =
			DLFileEntryMetadataLocalServiceUtil.
				getFileVersionFileEntryMetadatas(
					dlFileVersion.getFileVersionId());

		StringBundler sb = new StringBundler(dlFileEntryMetadatas.size());

		for (DLFileEntryMetadata dlFileEntryMetadata : dlFileEntryMetadatas) {
			DDMFormValues ddmFormValues = null;

			try {
				ddmFormValues = StorageEngineManagerUtil.getDDMFormValues(
					dlFileEntryMetadata.getDDMStorageId());
			}
			catch (Exception e) {
			}

			if (ddmFormValues != null) {
				sb.append(
					DDMStructureManagerUtil.extractAttributes(
						dlFileEntryMetadata.getDDMStructureId(), ddmFormValues,
						locale));
			}
		}

		return sb.toString();
	}

	protected void reindexFileEntries(
			long companyId, final long groupId, final long dataRepositoryId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			DLFileEntryLocalServiceUtil.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property property = PropertyFactoryUtil.forName("folderId");

					long folderId = DLFolderConstants.getFolderId(
						groupId, dataRepositoryId);

					dynamicQuery.add(property.eq(folderId));
				}

			});
		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setGroupId(groupId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<DLFileEntry>() {

				@Override
				public void performAction(DLFileEntry dlFileEntry) {
					try {
						Document document = getDocument(dlFileEntry);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index document library file entry " +
									dlFileEntry.getFileEntryId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	protected void reindexFolders(final long companyId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			DLFolderLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<DLFolder>() {

				@Override
				public void performAction(DLFolder dlFolder)
					throws PortalException {

					long groupId = dlFolder.getGroupId();
					long folderId = dlFolder.getFolderId();

					String[] newIds = {
						String.valueOf(companyId), String.valueOf(groupId),
						String.valueOf(folderId)
					};

					reindex(newIds);
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void reindexRoot(final long companyId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			GroupLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Group>() {

				@Override
				public void performAction(Group group) throws PortalException {
					long groupId = group.getGroupId();
					long folderId = groupId;

					String[] newIds = {
						String.valueOf(companyId), String.valueOf(groupId),
						String.valueOf(folderId)
					};

					reindex(newIds);
				}

			});

		actionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryIndexer.class);

	private final RelatedEntryIndexer _relatedEntryIndexer =
		new BaseRelatedEntryIndexer();

}