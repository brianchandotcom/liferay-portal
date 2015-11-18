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

package com.liferay.journal.search;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDMIndexerUtil;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverterUtil;
import com.liferay.journal.configuration.JournalServiceConfigurationValues;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.permission.JournalArticlePermission;
import com.liferay.journal.util.JournalContent;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.util.impl.JournalUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.DDMStructureIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.trash.util.TrashUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Hugo Huijser
 * @author Tibor Lipusz
 */
@Component(immediate = true, service = Indexer.class)
public class JournalArticleIndexer
	extends BaseIndexer<JournalArticle> implements DDMStructureIndexer {

	public static final String CLASS_NAME = JournalArticle.class.getName();

	public JournalArticleIndexer() {
		setDefaultSelectedFieldNames(
			Field.ASSET_TAG_NAMES, Field.ARTICLE_ID, Field.COMPANY_ID,
			Field.DEFAULT_LANGUAGE_ID, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK, Field.GROUP_ID, Field.MODIFIED_DATE,
			Field.SCOPE_GROUP_ID, Field.VERSION, Field.UID);
		setDefaultSelectedLocalizedFieldNames(
			Field.CONTENT, Field.DESCRIPTION, Field.TITLE);
		setFilterSearch(true);
		setPermissionAware(true);
		setSelectAllLocales(true);
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

		return JournalArticlePermission.contains(
			permissionChecker, entryClassPK, ActionKeys.VIEW);
	}

	@Override
	public boolean isVisible(long classPK, int status) throws Exception {
		List<JournalArticle> articles =
			_journalArticleLocalService.getArticlesByResourcePrimKey(classPK);

		for (JournalArticle article : articles) {
			if (isVisible(article.getStatus(), status)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		Long classNameId = (Long)searchContext.getAttribute(
			Field.CLASS_NAME_ID);

		if ((classNameId != null) && (classNameId != 0)) {
			contextBooleanFilter.addRequiredTerm(
				Field.CLASS_NAME_ID, classNameId.toString());
		}

		addStatus(contextBooleanFilter, searchContext);

		addSearchClassTypeIds(contextBooleanFilter, searchContext);

		String ddmStructureFieldName = (String)searchContext.getAttribute(
			"ddmStructureFieldName");
		Serializable ddmStructureFieldValue = searchContext.getAttribute(
			"ddmStructureFieldValue");

		if (Validator.isNotNull(ddmStructureFieldName) &&
			Validator.isNotNull(ddmStructureFieldValue)) {

			QueryFilter queryFilter =
				DDMIndexerUtil.createFieldValueQueryFilter(
					ddmStructureFieldName, ddmStructureFieldValue,
					searchContext.getLocale());

			contextBooleanFilter.add(queryFilter, BooleanClauseOccur.MUST);
		}

		String articleType = (String)searchContext.getAttribute("articleType");

		if (Validator.isNotNull(articleType)) {
			contextBooleanFilter.addRequiredTerm(Field.TYPE, articleType);
		}

		String ddmStructureKey = (String)searchContext.getAttribute(
			"ddmStructureKey");

		if (Validator.isNotNull(ddmStructureKey)) {
			contextBooleanFilter.addRequiredTerm(
				"ddmStructureKey", ddmStructureKey);
		}

		String ddmTemplateKey = (String)searchContext.getAttribute(
			"ddmTemplateKey");

		if (Validator.isNotNull(ddmTemplateKey)) {
			contextBooleanFilter.addRequiredTerm(
				"ddmTemplateKey", ddmTemplateKey);
		}

		boolean head = GetterUtil.getBoolean(
			searchContext.getAttribute("head"), Boolean.TRUE);
		boolean relatedClassName = GetterUtil.getBoolean(
			searchContext.getAttribute("relatedClassName"));
		boolean showNonindexable = GetterUtil.getBoolean(
			searchContext.getAttribute("showNonindexable"));

		if (head && !relatedClassName && !showNonindexable) {
			contextBooleanFilter.addRequiredTerm("head", Boolean.TRUE);
		}

		if (!relatedClassName && showNonindexable) {
			contextBooleanFilter.addRequiredTerm("headListable", Boolean.TRUE);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.ARTICLE_ID, false);
		addSearchTerm(searchQuery, searchContext, Field.CLASS_PK, false);
		addSearchLocalizedTerm(
			searchQuery, searchContext, Field.CONTENT, false);
		addSearchLocalizedTerm(
			searchQuery, searchContext, Field.DESCRIPTION, false);
		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchLocalizedTerm(searchQuery, searchContext, Field.TITLE, false);
		addSearchTerm(searchQuery, searchContext, Field.TYPE, false);
		addSearchTerm(searchQuery, searchContext, Field.USER_NAME, false);

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
	public void reindexDDMStructures(List<Long> ddmStructureIds)
		throws SearchException {

		if (SearchEngineUtil.isIndexReadOnly() || !isIndexerEnabled()) {
			return;
		}

		try {
			final String[] ddmStructureKeys =
				new String[ddmStructureIds.size()];

			for (int i = 0; i < ddmStructureIds.size(); i++) {
				long ddmStructureId = ddmStructureIds.get(i);

				DDMStructure ddmStructure =
					_ddmStructureLocalService.getDDMStructure(ddmStructureId);

				ddmStructureKeys[i] = ddmStructure.getStructureKey();
			}

			final Indexer<JournalArticle> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(JournalArticle.class);

			final ActionableDynamicQuery actionableDynamicQuery =
				_journalArticleLocalService.getActionableDynamicQuery();

			actionableDynamicQuery.setAddCriteriaMethod(
				new ActionableDynamicQuery.AddCriteriaMethod() {

					@Override
					public void addCriteria(DynamicQuery dynamicQuery) {
						Property ddmStructureKey = PropertyFactoryUtil.forName(
							"DDMStructureKey");

						dynamicQuery.add(ddmStructureKey.in(ddmStructureKeys));

						if (!JournalServiceConfigurationValues.
								JOURNAL_ARTICLE_INDEX_ALL_VERSIONS) {

							Property statusProperty =
								PropertyFactoryUtil.forName("status");

							Integer[] statuses = {
								WorkflowConstants.STATUS_APPROVED,
								WorkflowConstants.STATUS_IN_TRASH
							};

							dynamicQuery.add(statusProperty.in(statuses));
						}
					}

				});
			actionableDynamicQuery.setPerformActionMethod(
				new ActionableDynamicQuery.
					PerformActionMethod<JournalArticle>() {

					@Override
					public void performAction(JournalArticle article)
						throws PortalException {

						try {
							indexer.reindex(
								indexer.getClassName(),
								article.getResourcePrimKey());
						}
						catch (Exception e) {
							throw new PortalException(e);
						}
					}

				});

			actionableDynamicQuery.performActions();
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	protected void addDDMStructureAttributes(
			Document document, JournalArticle article)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			article.getGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			article.getDDMStructureKey(), true);

		if (ddmStructure == null) {
			return;
		}

		document.addKeyword(Field.CLASS_TYPE_ID, ddmStructure.getStructureId());

		DDMFormValues ddmFormValues = null;

		try {
			Fields fields = _journalConverter.getDDMFields(
				ddmStructure, article.getDocument());

			ddmFormValues = FieldsToDDMFormValuesConverterUtil.convert(
				ddmStructure, fields);
		}
		catch (Exception e) {
			return;
		}

		if (ddmFormValues != null) {
			DDMIndexerUtil.addAttributes(document, ddmStructure, ddmFormValues);
		}
	}

	@Override
	protected Map<String, Query> addSearchLocalizedTerm(
			BooleanQuery searchQuery, SearchContext searchContext, String field,
			boolean like)
		throws Exception {

		if (Validator.isNull(field)) {
			return Collections.emptyMap();
		}

		String value = String.valueOf(searchContext.getAttribute(field));

		if (Validator.isNull(value)) {
			value = searchContext.getKeywords();
		}

		if (Validator.isNull(value)) {
			return Collections.emptyMap();
		}

		String localizedField = DocumentImpl.getLocalizedName(
			searchContext.getLocale(), field);

		Map<String, Query> queries = new HashMap<>();

		if (Validator.isNull(searchContext.getKeywords())) {
			BooleanQuery localizedQuery = new BooleanQueryImpl();

			Query query = localizedQuery.addTerm(field, value, like);

			queries.put(field, query);

			Query localizedFieldQuery = localizedQuery.addTerm(
				localizedField, value, like);

			queries.put(field, localizedFieldQuery);

			BooleanClauseOccur booleanClauseOccur = BooleanClauseOccur.SHOULD;

			if (searchContext.isAndSearch()) {
				booleanClauseOccur = BooleanClauseOccur.MUST;
			}

			searchQuery.add(localizedQuery, booleanClauseOccur);
		}
		else {
			Query query = searchQuery.addTerm(localizedField, value, like);

			queries.put(field, query);
		}

		return queries;
	}

	@Override
	protected void doDelete(JournalArticle journalArticle) throws Exception {
		long classPK = journalArticle.getId();

		if (!JournalServiceConfigurationValues.
				JOURNAL_ARTICLE_INDEX_ALL_VERSIONS) {

			if (_journalArticleLocalService.getArticlesCount(
					journalArticle.getGroupId(),
					journalArticle.getArticleId()) > 0) {

				doReindex(journalArticle);

				return;
			}
			else {
				classPK = journalArticle.getResourcePrimKey();
			}
		}

		deleteDocument(journalArticle.getCompanyId(), classPK);

		if (!journalArticle.isApproved()) {
			return;
		}

		JournalArticle latestIndexableArticle =
			_journalArticleLocalService.fetchLatestIndexableArticle(
				journalArticle.getResourcePrimKey());

		if ((latestIndexableArticle == null) ||
			(latestIndexableArticle.getVersion() >
				journalArticle.getVersion())) {

			return;
		}

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), journalArticle.getCompanyId(),
			getDocument(latestIndexableArticle), isCommitImmediately());
	}

	@Override
	protected Document doGetDocument(JournalArticle journalArticle)
		throws Exception {

		Document document = getBaseModelDocument(CLASS_NAME, journalArticle);

		long classPK = journalArticle.getId();

		if (!JournalServiceConfigurationValues.
				JOURNAL_ARTICLE_INDEX_ALL_VERSIONS) {

			classPK = journalArticle.getResourcePrimKey();
		}

		document.addUID(CLASS_NAME, classPK);

		String articleDefaultLanguageId = LocalizationUtil.getDefaultLanguageId(
			journalArticle.getDocument());

		String[] languageIds = LocalizationUtil.getAvailableLanguageIds(
			journalArticle.getDocument());

		for (String languageId : languageIds) {
			String content = extractDDMContent(journalArticle, languageId);

			String description = journalArticle.getDescription(languageId);

			String title = journalArticle.getTitle(languageId);

			if (languageId.equals(articleDefaultLanguageId)) {
				document.addText(Field.CONTENT, content);
				document.addText(Field.DESCRIPTION, description);
				document.addText(Field.TITLE, title);
				document.addText("defaultLanguageId", languageId);
			}

			document.addText(
				LocalizationUtil.getLocalizedName(Field.CONTENT, languageId),
				content);
			document.addText(
				LocalizationUtil.getLocalizedName(
					Field.DESCRIPTION, languageId),
				description);
			document.addText(
				LocalizationUtil.getLocalizedName(Field.TITLE, languageId),
				title);
		}

		document.addKeyword(Field.FOLDER_ID, journalArticle.getFolderId());

		String articleId = journalArticle.getArticleId();

		if (journalArticle.isInTrash()) {
			articleId = TrashUtil.getOriginalTitle(articleId);
		}

		document.addKeyword(Field.ARTICLE_ID, articleId);

		document.addKeyword(Field.LAYOUT_UUID, journalArticle.getLayoutUuid());
		document.addKeyword(
			Field.TREE_PATH,
			StringUtil.split(journalArticle.getTreePath(), CharPool.SLASH));
		document.addKeyword(Field.VERSION, journalArticle.getVersion());

		document.addKeyword(
			"ddmStructureKey", journalArticle.getDDMStructureKey());
		document.addKeyword(
			"ddmTemplateKey", journalArticle.getDDMTemplateKey());
		document.addDate("displayDate", journalArticle.getDisplayDate());
		document.addKeyword("head", JournalUtil.isHead(journalArticle));

		boolean headListable = JournalUtil.isHeadListable(journalArticle);

		document.addKeyword("headListable", headListable);

		// Scheduled listable articles should be visible in asset browser

		if (journalArticle.isScheduled() && headListable) {
			boolean visible = GetterUtil.getBoolean(document.get("visible"));

			if (!visible) {
				document.addKeyword("visible", true);
			}
		}

		addDDMStructureAttributes(document, journalArticle);

		return document;
	}

	@Override
	protected String doGetSortField(String orderByCol) {
		if (orderByCol.equals("display-date")) {
			return "displayDate";
		}
		else if (orderByCol.equals("id")) {
			return Field.ENTRY_CLASS_PK;
		}
		else if (orderByCol.equals("modified-date")) {
			return Field.MODIFIED_DATE;
		}
		else if (orderByCol.equals("title")) {
			return Field.TITLE;
		}
		else {
			return orderByCol;
		}
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Locale defaultLocale = LocaleUtil.fromLanguageId(
			document.get("defaultLanguageId"));

		Locale snippetLocale = getSnippetLocale(document, locale);

		String localizedTitleName = DocumentImpl.getLocalizedName(
			locale, Field.TITLE);

		if ((snippetLocale == null) &&
			(document.getField(localizedTitleName) == null)) {

			snippetLocale = defaultLocale;
		}
		else {
			snippetLocale = locale;
		}

		String title = document.get(
			snippetLocale, Field.SNIPPET + StringPool.UNDERLINE + Field.TITLE,
			Field.TITLE);

		if (Validator.isNull(title) && !snippetLocale.equals(defaultLocale)) {
			title = document.get(
				defaultLocale,
				Field.SNIPPET + StringPool.UNDERLINE + Field.TITLE,
				Field.TITLE);
		}

		String content = getDDMContentSummary(
			document, snippetLocale, portletRequest, portletResponse);

		if (Validator.isNull(content) && !snippetLocale.equals(defaultLocale)) {
			content = getDDMContentSummary(
				document, defaultLocale, portletRequest, portletResponse);
		}

		Summary summary = new Summary(snippetLocale, title, content);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(JournalArticle article) throws Exception {
		if (PortalUtil.getClassNameId(DDMStructure.class) ==
				article.getClassNameId()) {

			Document document = getDocument(article);

			SearchEngineUtil.deleteDocument(
				getSearchEngineId(), article.getCompanyId(),
				document.get(Field.UID), isCommitImmediately());

			return;
		}

		reindexArticleVersions(article);
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		JournalArticle article =
			_journalArticleLocalService.fetchJournalArticle(classPK);

		if (article == null) {
			article = _journalArticleLocalService.fetchLatestArticle(classPK);
		}

		if (article != null) {
			doReindex(article);
		}
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexArticles(companyId);
	}

	protected String extractDDMContent(
			JournalArticle article, String languageId)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			article.getGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			article.getDDMStructureKey(), true);

		if (ddmStructure == null) {
			return StringPool.BLANK;
		}

		DDMFormValues ddmFormValues = null;

		try {
			Fields fields = _journalConverter.getDDMFields(
				ddmStructure, article.getDocument());

			ddmFormValues = FieldsToDDMFormValuesConverterUtil.convert(
				ddmStructure, fields);
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}

		if (ddmFormValues == null) {
			return StringPool.BLANK;
		}

		return DDMIndexerUtil.extractAttributes(
			ddmStructure, ddmFormValues, LocaleUtil.fromLanguageId(languageId));
	}

	protected JournalArticle fetchLatestIndexableArticleVersion(
		long resourcePrimKey) {

		JournalArticle latestIndexableArticle =
			_journalArticleLocalService.fetchLatestArticle(
				resourcePrimKey,
				new int[] {
					WorkflowConstants.STATUS_APPROVED,
					WorkflowConstants.STATUS_IN_TRASH
				});

		if (latestIndexableArticle == null) {
			latestIndexableArticle =
				_journalArticleLocalService.fetchLatestArticle(resourcePrimKey);
		}

		return latestIndexableArticle;
	}

	protected Collection<Document> getArticleVersions(JournalArticle article)
		throws PortalException {

		Collection<Document> documents = new ArrayList<>();

		List<JournalArticle> articles = null;

		if (JournalServiceConfigurationValues.
				JOURNAL_ARTICLE_INDEX_ALL_VERSIONS) {

			articles = _journalArticleLocalService.getArticlesByResourcePrimKey(
				article.getResourcePrimKey());
		}
		else {
			articles = new ArrayList<>();

			JournalArticle latestIndexableArticle =
				fetchLatestIndexableArticleVersion(
					article.getResourcePrimKey());

			if (latestIndexableArticle != null) {
				articles.add(latestIndexableArticle);
			}
		}

		for (JournalArticle curArticle : articles) {
			Document document = getDocument(curArticle);

			documents.add(document);
		}

		return documents;
	}

	protected String getDDMContentSummary(
		Document document, Locale snippetLocale, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		String content = StringPool.BLANK;

		if ((portletRequest == null) || (portletResponse == null)) {
			return content;
		}

		try {
			String articleId = document.get(Field.ARTICLE_ID);
			long groupId = GetterUtil.getLong(document.get(Field.GROUP_ID));
			double version = GetterUtil.getDouble(document.get(Field.VERSION));
			PortletRequestModel portletRequestModel = new PortletRequestModel(
				portletRequest, portletResponse);
			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			JournalArticleDisplay articleDisplay = _journalContent.getDisplay(
				groupId, articleId, version, null, Constants.VIEW,
				LocaleUtil.toLanguageId(snippetLocale), 1, portletRequestModel,
				themeDisplay);

			content = articleDisplay.getDescription();
			content = HtmlUtil.replaceNewLine(content);

			if (Validator.isNull(content)) {
				content = HtmlUtil.extractText(articleDisplay.getContent());
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return content;
	}

	protected void reindexArticles(long companyId) throws PortalException {
		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_journalArticleLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<JournalArticle>() {

				@Override
				public void performAction(JournalArticle article) {
					if (!JournalServiceConfigurationValues.
							JOURNAL_ARTICLE_INDEX_ALL_VERSIONS) {

						JournalArticle latestIndexableArticle =
							fetchLatestIndexableArticleVersion(
								article.getResourcePrimKey());

						if (latestIndexableArticle == null) {
							return;
						}

						article = latestIndexableArticle;
					}

					try {
						Document document = getDocument(article);

						indexableActionableDynamicQuery.addDocument(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index journal article " +
									article.getId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	protected void reindexArticleVersions(JournalArticle article)
		throws PortalException {

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), article.getCompanyId(),
			getArticleVersions(article), isCommitImmediately());
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalContent(JournalContent journalContent) {
		_journalContent = journalContent;
	}

	@Reference(unbind = "-")
	protected void setJournalConverter(JournalConverter journalConverter) {
		_journalConverter = journalConverter;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleIndexer.class);

	private DDMStructureLocalService _ddmStructureLocalService;
	private JournalArticleLocalService _journalArticleLocalService;
	private JournalContent _journalContent;
	private JournalConverter _journalConverter;

}