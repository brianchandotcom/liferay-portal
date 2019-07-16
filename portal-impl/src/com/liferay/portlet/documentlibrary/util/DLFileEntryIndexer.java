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

import com.liferay.document.library.kernel.exception.NoSuchFileVersionException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManager;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManagerUtil;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManagerUtil;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.expando.kernel.util.ExpandoBridgeIndexerUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BaseRelatedEntryIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentHelper;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.RelatedEntryIndexer;
import com.liferay.portal.kernel.search.RelatedEntryIndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.trash.kernel.util.TrashUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author     Brian Wing Shun Chan
 * @author     Raymond Augé
 * @author     Alexander Chow
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.document.library.internal.search.DLFileEntryIndexer}
 */
@Deprecated
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

		return _dlFileEntryModelResourcePermission.contains(
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

		try {
			FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(classPK);

			if (fileEntry instanceof LiferayFileEntry) {
				DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

				if (dlFileEntry.isInHiddenFolder()) {
					Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
						dlFileEntry.getClassName());

					return indexer.isVisible(dlFileEntry.getClassPK(), status);
				}
			}
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info("Unble to get file entry", e);
			}

			return false;
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

		if (ArrayUtil.isEmpty(searchContext.getFolderIds()) ||
			ArrayUtil.contains(
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

		if (Validator.isNull(searchContext.getKeywords())) {
			addSearchTerm(searchQuery, searchContext, Field.DESCRIPTION, false);
			addSearchTerm(searchQuery, searchContext, Field.TITLE, false);
			addSearchTerm(searchQuery, searchContext, Field.USER_NAME, false);
		}

		addSearchTerm(searchQuery, searchContext, "ddmContent", false);
		addSearchTerm(searchQuery, searchContext, "extension", false);
		addSearchTerm(searchQuery, searchContext, "fileEntryTypeId", false);
		addSearchTerm(searchQuery, searchContext, "path", false);
		addSearchLocalizedTerm(
			searchQuery, searchContext, Field.CONTENT, false);

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

	protected Summary createSummary(
		Locale locale, Document document, String titleField,
		String contentField) {

		String prefix = Field.SNIPPET + StringPool.UNDERLINE;

		String title = document.get(prefix + titleField, titleField);
		String content = document.get(
			locale, prefix + contentField, contentField);

		return new Summary(title, content);
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
				DLFileVersion fileVersion = dlFileEntry.getFileVersion();

				is = fileVersion.getContentStream(false);
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
						Locale defaultLocale = PortalUtil.getSiteDefaultLocale(
							dlFileEntry.getGroupId());

						String localizedField =
							LocalizationUtil.getLocalizedName(
								Field.CONTENT, defaultLocale.toString());

						document.addFile(
							localizedField, is, dlFileEntry.getTitle(),
							PropsValues.DL_FILE_INDEXING_MAX_SIZE);
					}
					catch (IOException ioe) {
						throw new SearchException(
							"Cannot extract text from file" + dlFileEntry, ioe);
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

			String title = dlFileEntry.getTitle();

			if (dlFileEntry.isInTrash()) {
				title = TrashUtil.getOriginalTitle(title);
			}

			document.addText(Field.TITLE, title);

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
				List<RelatedEntryIndexer> relatedEntryIndexers =
					RelatedEntryIndexerRegistryUtil.getRelatedEntryIndexers(
						dlFileEntry.getClassName());

				if (relatedEntryIndexers != null) {
					for (RelatedEntryIndexer relatedEntryIndexer :
							relatedEntryIndexers) {

						relatedEntryIndexer.addRelatedEntryFields(
							document, new LiferayFileEntry(dlFileEntry));

						DocumentHelper documentHelper = new DocumentHelper(
							document);

						documentHelper.setAttachmentOwnerKey(
							PortalUtil.getClassNameId(
								dlFileEntry.getClassName()),
							dlFileEntry.getClassPK());

						document.addKeyword(Field.RELATED_ENTRY, true);
					}
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

		Summary summary = createSummary(
			locale, document, Field.TITLE, Field.CONTENT);

		if (Validator.isNull(summary.getContent())) {
			summary = createSummary(document, Field.TITLE, Field.DESCRIPTION);
		}

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(DLFileEntry dlFileEntry) throws Exception {
		DLFileVersion dlFileVersion = null;

		try {
			dlFileVersion = dlFileEntry.getFileVersion();
		}
		catch (NoSuchFileVersionException nsfve) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get file version for file entry " +
						dlFileEntry.getFileEntryId(),
					nsfve);
			}

			return;
		}

		if (!dlFileVersion.isApproved() && !dlFileEntry.isInTrash()) {
			return;
		}

		IndexWriterHelperUtil.updateDocument(
			getSearchEngineId(), dlFileEntry.getCompanyId(),
			getDocument(dlFileEntry), isCommitImmediately());
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
			dynamicQuery -> {
				Property property = PropertyFactoryUtil.forName("folderId");

				long folderId = DLFolderConstants.getFolderId(
					groupId, dataRepositoryId);

				dynamicQuery.add(property.eq(folderId));
			});
		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setGroupId(groupId);
		indexableActionableDynamicQuery.setInterval(
			PropsValues.DL_FILE_INDEXING_INTERVAL);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(DLFileEntry dlFileEntry) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(dlFileEntry));
				}
				catch (PortalException pe) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index document library file entry " +
								dlFileEntry.getFileEntryId(),
							pe);
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
			(DLFolder dlFolder) -> {
				String[] newIds = {
					String.valueOf(companyId),
					String.valueOf(dlFolder.getGroupId()),
					String.valueOf(dlFolder.getFolderId())
				};

				reindex(newIds);
			});

		actionableDynamicQuery.performActions();
	}

	protected void reindexRoot(final long companyId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			GroupLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			(Group group) -> {
				long groupId = group.getGroupId();

				long folderId = groupId;

				String[] newIds = {
					String.valueOf(companyId), String.valueOf(groupId),
					String.valueOf(folderId)
				};

				reindex(newIds);
			});

		actionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryIndexer.class);

	private static volatile ModelResourcePermission<DLFileEntry>
		_dlFileEntryModelResourcePermission =
			ServiceProxyFactory.newServiceTrackedInstance(
				ModelResourcePermission.class, DLFileEntryIndexer.class,
				"_dlFileEntryModelResourcePermission",
				"(model.class.name=" + DLFileEntry.class.getName() + ")", true);

	private final RelatedEntryIndexer _relatedEntryIndexer =
		new BaseRelatedEntryIndexer();

}