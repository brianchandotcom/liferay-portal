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

package com.liferay.knowledge.base.internal.search;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.knowledge.base.service.KBFolderLocalService;
import com.liferay.knowledge.base.util.KnowledgeBaseUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = Indexer.class)
public class KBArticleIndexer extends BaseIndexer<KBArticle> {

	public static final String CLASS_NAME = KBArticle.class.getName();

	public KBArticleIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.CONTENT, Field.CREATE_DATE,
			Field.DESCRIPTION, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.MODIFIED_DATE, Field.TITLE, Field.UID, Field.USER_NAME);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws PortalException {

		return _kbArticleModelResourcePermission.contains(
			permissionChecker, entryClassPK, ActionKeys.VIEW);
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.CONTENT, true);
		addSearchTerm(searchQuery, searchContext, Field.DESCRIPTION, true);
		addSearchTerm(searchQuery, searchContext, Field.TITLE, true);
		addSearchTerm(searchQuery, searchContext, Field.USER_NAME, true);
	}

	@Override
	public Hits search(SearchContext searchContext) throws SearchException {
		Hits hits = super.search(searchContext);

		String[] queryTerms = ArrayUtil.append(
			GetterUtil.getStringValues(hits.getQueryTerms()),
			KnowledgeBaseUtil.splitKeywords(searchContext.getKeywords()));

		hits.setQueryTerms(queryTerms);

		return hits;
	}

	@Override
	protected void doDelete(KBArticle kbArticle) throws Exception {
		deleteDocument(
			kbArticle.getCompanyId(), kbArticle.getResourcePrimKey());
	}

	@Override
	protected Document doGetDocument(KBArticle kbArticle) throws Exception {
		Document document = getBaseModelDocument(CLASS_NAME, kbArticle);

		document.addText(
			Field.CONTENT, HtmlUtil.extractText(kbArticle.getContent()));
		document.addText(Field.DESCRIPTION, kbArticle.getDescription());
		document.addKeyword(Field.FOLDER_ID, kbArticle.getKbFolderId());
		document.addText(Field.TITLE, kbArticle.getTitle());
		document.addKeyword("folderNames", getKBFolderNames(kbArticle));
		document.addKeyword(
			"parentMessageId", kbArticle.getParentResourcePrimKey());
		document.addKeyword("titleKeyword", kbArticle.getTitle(), true);

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String prefix = Field.SNIPPET + StringPool.UNDERLINE;

		String title = document.get(prefix + Field.TITLE, Field.TITLE);

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = document.get(
				prefix + Field.DESCRIPTION, Field.DESCRIPTION);

			if (Validator.isNull(content)) {
				content = document.get(prefix + Field.CONTENT, Field.CONTENT);
			}
		}

		Summary summary = new Summary(title, content);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(KBArticle kbArticle) throws Exception {
		indexWriterHelper.updateDocument(
			getSearchEngineId(), kbArticle.getCompanyId(),
			getDocument(kbArticle), isCommitImmediately());

		reindexAttachments(kbArticle);
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		KBArticle kbArticle = kbArticleLocalService.getLatestKBArticle(
			classPK, WorkflowConstants.STATUS_ANY);

		reindexKBArticles(kbArticle);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexKBArticles(companyId);
	}

	protected String[] getKBFolderNames(KBArticle kbArticle)
		throws PortalException {

		long kbFolderId = kbArticle.getKbFolderId();

		Collection<String> kbFolderNames = new ArrayList<>();

		while (kbFolderId != KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			KBFolder kbFolder = kbFolderLocalService.getKBFolder(kbFolderId);

			kbFolderNames.add(kbFolder.getName());

			kbFolderId = kbFolder.getParentKBFolderId();
		}

		return kbFolderNames.toArray(new String[0]);
	}

	protected void reindexAttachments(KBArticle kbArticle)
		throws PortalException {

		Indexer<DLFileEntry> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			DLFileEntry.class);

		for (FileEntry attachmentsFileEntry :
				kbArticle.getAttachmentsFileEntries()) {

			indexer.reindex((DLFileEntry)attachmentsFileEntry.getModel());
		}
	}

	protected void reindexKBArticles(KBArticle kbArticle) throws Exception {
		List<KBArticle> kbArticles =
			kbArticleLocalService.getKBArticleAndAllDescendantKBArticles(
				kbArticle.getResourcePrimKey(),
				WorkflowConstants.STATUS_APPROVED, null);

		Collection<Document> documents = new ArrayList<>();

		for (KBArticle curKBArticle : kbArticles) {
			documents.add(getDocument(curKBArticle));
		}

		indexWriterHelper.updateDocuments(
			getSearchEngineId(), kbArticle.getCompanyId(), documents,
			isCommitImmediately());
	}

	protected void reindexKBArticles(long companyId) throws Exception {
		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			kbArticleLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property property = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(
					property.eq(WorkflowConstants.STATUS_APPROVED));
			});
		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(KBArticle kbArticle) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(kbArticle));
				}
				catch (PortalException pe) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index knowledge base article " +
								kbArticle.getKbArticleId(),
							pe);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	@Reference
	protected IndexWriterHelper indexWriterHelper;

	@Reference
	protected KBArticleLocalService kbArticleLocalService;

	@Reference
	protected KBFolderLocalService kbFolderLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		KBArticleIndexer.class);

	@Reference(
		target = "(model.class.name=com.liferay.knowledge.base.model.KBArticle)"
	)
	private ModelResourcePermission<KBArticle>
		_kbArticleModelResourcePermission;

}