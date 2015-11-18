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

package com.liferay.wiki.search;

import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.capabilities.RelatedModelCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BaseRelatedEntryIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.RelatedEntryIndexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalService;
import com.liferay.wiki.service.WikiNodeService;
import com.liferay.wiki.service.WikiPageLocalService;
import com.liferay.wiki.service.permission.WikiPagePermissionChecker;
import com.liferay.wiki.util.WikiUtil;

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
@Component(immediate = true, service = Indexer.class)
public class WikiPageIndexer
	extends BaseIndexer<WikiPage> implements RelatedEntryIndexer {

	public static final String CLASS_NAME = WikiPage.class.getName();

	public WikiPageIndexer() {
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

		long classPK = 0;

		if (obj instanceof Comment) {
			Comment comment = (Comment)obj;

			classPK = comment.getClassPK();
		}
		else if (obj instanceof FileEntry) {
			FileEntry fileEntry = (FileEntry)obj;

			RelatedModelCapability relatedModelCapability =
				fileEntry.getRepositoryCapability(RelatedModelCapability.class);

			classPK = relatedModelCapability.getClassPK(fileEntry);
		}

		WikiPage page = null;

		try {
			page = _wikiPageLocalService.getPage(classPK);
		}
		catch (Exception e) {
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

		WikiPage page = _wikiPageLocalService.getPage(entryClassPK);

		return WikiPagePermissionChecker.contains(
			permissionChecker, page, ActionKeys.VIEW);
	}

	@Override
	public boolean isVisible(long classPK, int status) throws Exception {
		WikiPage page = _wikiPageLocalService.getPage(classPK);

		return isVisible(page.getStatus(), status);
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
				catch (Exception e) {
					if (_log.isDebugEnabled()) {
						_log.debug("Unable to get wiki node " + nodeId, e);
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
	public void updateFullQuery(SearchContext searchContext) {
	}

	@Override
	protected void doDelete(WikiPage wikiPage) throws Exception {
		deleteDocument(wikiPage.getCompanyId(), wikiPage.getResourcePrimKey());
	}

	@Override
	protected Document doGetDocument(WikiPage wikiPage) throws Exception {
		Document document = getBaseModelDocument(CLASS_NAME, wikiPage);

		document.addUID(CLASS_NAME, wikiPage.getResourcePrimKey());

		String content = HtmlUtil.extractText(
			WikiUtil.convert(wikiPage, null, null, null));

		document.addText(Field.CONTENT, content);

		document.addKeyword(Field.NODE_ID, wikiPage.getNodeId());
		document.addText(Field.TITLE, wikiPage.getTitle());

		return document;
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
	protected void doReindex(String className, long classPK) throws Exception {
		WikiPage page = _wikiPageLocalService.fetchWikiPage(classPK);

		if (page == null) {
			page = _wikiPageLocalService.getPage(classPK, (Boolean)null);
		}

		doReindex(page);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexNodes(companyId);
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

		Document document = getDocument(wikiPage);

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), wikiPage.getCompanyId(), document,
			isCommitImmediately());
	}

	protected void reindexNodes(final long companyId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_wikiNodeLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<WikiNode>() {

				@Override
				public void performAction(WikiNode node)
					throws PortalException {

					reindexPages(
						companyId, node.getGroupId(), node.getNodeId());
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void reindexPages(long companyId, long groupId, final long nodeId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_wikiPageLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property nodeIdProperty = PropertyFactoryUtil.forName(
						"nodeId");

					dynamicQuery.add(nodeIdProperty.eq(nodeId));

					Property headProperty = PropertyFactoryUtil.forName("head");

					dynamicQuery.add(headProperty.eq(true));
				}

			});
		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setGroupId(groupId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<WikiPage>() {

				@Override
				public void performAction(WikiPage page) {
					try {
						Document document = getDocument(page);

						indexableActionableDynamicQuery.addDocument(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index wiki page " + page.getPageId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
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

	private static final Log _log = LogFactoryUtil.getLog(
		WikiPageIndexer.class);

	private final RelatedEntryIndexer _relatedEntryIndexer =
		new BaseRelatedEntryIndexer();
	private WikiNodeLocalService _wikiNodeLocalService;
	private WikiNodeService _wikiNodeService;
	private WikiPageLocalService _wikiPageLocalService;

}