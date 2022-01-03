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

package com.liferay.wiki.internal.search;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.capabilities.RelatedModelCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BaseRelatedEntryIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.RelatedEntryIndexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.model.uid.UIDFactory;
import com.liferay.trash.TrashHelper;
import com.liferay.wiki.engine.WikiEngineRenderer;
import com.liferay.wiki.exception.WikiFormatException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalService;
import com.liferay.wiki.service.WikiNodeService;
import com.liferay.wiki.service.WikiPageLocalService;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = "related.entry.indexer.class.name=com.liferay.wiki.model.WikiPage",
	service = {Indexer.class, RelatedEntryIndexer.class}
)
public class WikiPageIndexer
	extends BaseIndexer<WikiPage> implements RelatedEntryIndexer {

	public static final String CLASS_NAME = WikiPage.class.getName();

	public WikiPageIndexer() {
		setDefaultSelectedFieldNames(
			Field.ASSET_TAG_NAMES, Field.COMPANY_ID, Field.CONTENT,
			Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK, Field.GROUP_ID,
			Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID, Field.TITLE, Field.UID);
		setDefaultSelectedLocalizedFieldNames(Field.CONTENT, Field.TITLE);
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
	public void addRelatedEntryFields(Document document, Object object)
		throws Exception {

		long classPK = 0;

		if (object instanceof Comment) {
			Comment comment = (Comment)object;

			classPK = comment.getClassPK();
		}
		else if (object instanceof FileEntry) {
			FileEntry fileEntry = (FileEntry)object;

			RelatedModelCapability relatedModelCapability =
				fileEntry.getRepositoryCapability(RelatedModelCapability.class);

			classPK = relatedModelCapability.getClassPK(fileEntry);
		}

		WikiPage page = null;

		try {
			page = _wikiPageLocalService.getPage(classPK);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return;
		}

		document.addKeyword(Field.NODE_ID, page.getNodeId());
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

		return _wikiPageModelResourcePermission.contains(
			permissionChecker, _wikiPageLocalService.getPage(entryClassPK),
			ActionKeys.VIEW);
	}

	@Override
	public boolean isVisible(long classPK, int status) throws Exception {
		WikiPage page = _wikiPageLocalService.getPage(classPK);

		return isVisible(page.getStatus(), status);
	}

	@Override
	public boolean isVisibleRelatedEntry(long classPK, int status) {
		return true;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		addStatus(contextBooleanFilter, searchContext);

		long[] nodeIds = searchContext.getNodeIds();

		if (ArrayUtil.isNotEmpty(nodeIds)) {
			TermsFilter nodesIdTermsFilter = new TermsFilter(Field.NODE_ID);

			for (long nodeId : nodeIds) {
				try {
					_wikiNodeService.getNode(nodeId);
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to get wiki node " + nodeId, exception);
					}

					continue;
				}

				nodesIdTermsFilter.addValue(String.valueOf(nodeId));
			}

			if (!nodesIdTermsFilter.isEmpty()) {
				contextBooleanFilter.add(
					nodesIdTermsFilter, BooleanClauseOccur.MUST);
			}
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchLocalizedTerm(
			searchQuery, searchContext, Field.CONTENT, false);
		addSearchLocalizedTerm(searchQuery, searchContext, Field.TITLE, false);
	}

	@Override
	public Hits search(SearchContext searchContext) throws SearchException {
		Hits hits = super.search(searchContext);

		hits.setQueryTerms(
			ArrayUtil.append(
				GetterUtil.getStringValues(hits.getQueryTerms()),
				StringUtil.split(
					searchContext.getKeywords(), StringPool.SPACE)));

		return hits;
	}

	@Override
	public void updateFullQuery(SearchContext searchContext) {
	}

	@Override
	protected void doDelete(WikiPage wikiPage) throws Exception {
		deleteDocument(
			wikiPage.getCompanyId(), "UID=" + uidFactory.getUID(wikiPage));
	}

	@Override
	protected Document doGetDocument(WikiPage wikiPage) throws Exception {
		Document document = getBaseModelDocument(CLASS_NAME, wikiPage);

		uidFactory.setUID(wikiPage, document);

		String content = null;

		try {
			content = HtmlUtil.extractText(
				_wikiEngineRenderer.convert(wikiPage, null, null, null));

			document.addText(Field.CONTENT, content);
		}
		catch (WikiFormatException wikiFormatException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get wiki engine for " + wikiPage.getFormat(),
					wikiFormatException);
			}
		}

		document.addKeyword(Field.NODE_ID, wikiPage.getNodeId());

		String title = wikiPage.getTitle();

		if (wikiPage.isInTrash()) {
			title = _trashHelper.getOriginalTitle(title);
		}

		document.addText(Field.TITLE, title);

		for (Locale locale :
				LanguageUtil.getAvailableLocales(wikiPage.getGroupId())) {

			String languageId = LocaleUtil.toLanguageId(locale);

			document.addText(
				LocalizationUtil.getLocalizedName(Field.CONTENT, languageId),
				content);
			document.addText(
				LocalizationUtil.getLocalizedName(Field.TITLE, languageId),
				title);
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String languageId = LocaleUtil.toLanguageId(locale);

		String content = LocalizationUtil.getLocalizedName(
			Field.CONTENT, languageId);
		String title = LocalizationUtil.getLocalizedName(
			Field.TITLE, languageId);

		Summary summary = createSummary(document, title, content);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		WikiPage wikiPage = _wikiPageLocalService.fetchWikiPage(classPK);

		if (wikiPage != null) {
			_reindexEveryVersionOfResourcePrimKey(
				wikiPage.getResourcePrimKey());

			return;
		}

		long resourcePrimKey = classPK;

		_reindexEveryVersionOfResourcePrimKey(resourcePrimKey);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		_reindexNodes(companyId);
	}

	@Override
	protected void doReindex(WikiPage wikiPage) throws Exception {
		if (!wikiPage.isHead() ||
			(!wikiPage.isApproved() && !wikiPage.isInTrash())) {

			return;
		}

		if (Validator.isNotNull(wikiPage.getRedirectTitle())) {
			return;
		}

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), wikiPage.getCompanyId(), getDocument(wikiPage),
			isCommitImmediately());

		_reindexAttachments(wikiPage);
	}

	@Reference(unbind = "-")
	protected void setWikiEngineRenderer(
		WikiEngineRenderer wikiEngineRenderer) {

		_wikiEngineRenderer = wikiEngineRenderer;
	}

	@Reference(unbind = "-")
	protected void setWikiNodeLocalService(
		WikiNodeLocalService wikiNodeLocalService) {

		_wikiNodeLocalService = wikiNodeLocalService;
	}

	@Reference(unbind = "-")
	protected void setWikiNodeService(WikiNodeService wikiNodeService) {
		_wikiNodeService = wikiNodeService;
	}

	@Reference(unbind = "-")
	protected void setWikiPageLocalService(
		WikiPageLocalService wikiPageLocalService) {

		_wikiPageLocalService = wikiPageLocalService;
	}

	@Reference
	protected UIDFactory uidFactory;

	private void _deleteDocument(WikiPage wikiPage) {
		try {
			_indexWriterHelper.deleteDocument(
				getSearchEngineId(), wikiPage.getCompanyId(),
				uidFactory.getUID(wikiPage), isCommitImmediately());
		}
		catch (SearchException searchException) {
			throw new RuntimeException(searchException);
		}
	}

	private void _reindexAttachments(WikiPage wikiPage) throws Exception {
		Indexer<DLFileEntry> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			DLFileEntry.class);

		for (FileEntry attachmentsFileEntry :
				wikiPage.getAttachmentsFileEntries()) {

			indexer.reindex((DLFileEntry)attachmentsFileEntry.getModel());
		}
	}

	private void _reindexEveryVersionOfResourcePrimKey(long resourcePrimKey)
		throws Exception {

		List<WikiPage> wikiPages =
			(List<WikiPage>)_wikiPageLocalService.getPersistedModel(
				resourcePrimKey);

		if (ListUtil.isEmpty(wikiPages)) {
			return;
		}

		WikiPage latestWikiPage = _wikiPageLocalService.getPage(
			resourcePrimKey, (Boolean)null);

		for (WikiPage wikiPage : wikiPages) {
			if (wikiPage.getPrimaryKey() == latestWikiPage.getPrimaryKey()) {
				doReindex(wikiPage);
			}
			else {
				_deleteDocument(wikiPage);
			}
		}
	}

	private void _reindexNodes(long companyId) throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			_wikiNodeLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			(WikiNode node) -> _reindexPages(
				companyId, node.getGroupId(), node.getNodeId()));

		actionableDynamicQuery.performActions();
	}

	private void _reindexPages(long companyId, long groupId, long nodeId)
		throws PortalException {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_wikiPageLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property nodeIdProperty = PropertyFactoryUtil.forName("nodeId");

				dynamicQuery.add(nodeIdProperty.eq(nodeId));

				Property headProperty = PropertyFactoryUtil.forName("head");

				dynamicQuery.add(headProperty.eq(true));
			});
		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setGroupId(groupId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(WikiPage page) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(page));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index wiki page " + page.getPageId(),
							portalException);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WikiPageIndexer.class);

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	private final RelatedEntryIndexer _relatedEntryIndexer =
		new BaseRelatedEntryIndexer();

	@Reference
	private TrashHelper _trashHelper;

	private WikiEngineRenderer _wikiEngineRenderer;
	private WikiNodeLocalService _wikiNodeLocalService;
	private WikiNodeService _wikiNodeService;
	private WikiPageLocalService _wikiPageLocalService;

	@Reference(target = "(model.class.name=com.liferay.wiki.model.WikiPage)")
	private ModelResourcePermission<WikiPage> _wikiPageModelResourcePermission;

}