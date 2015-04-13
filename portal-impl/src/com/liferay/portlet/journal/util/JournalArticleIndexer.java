/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.dynamicdatamapping.StructureFieldException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.util.DDMIndexerUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;
import com.liferay.portlet.journal.service.persistence.JournalArticleActionableDynamicQuery;
import com.liferay.portlet.trash.util.TrashUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Hugo Huijser
 * @author Tibor Lipusz
 */
public class JournalArticleIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {JournalArticle.class.getName()};

	public static boolean JOURNAL_ARTICLE_INDEX_ALL_VERSIONS =
		GetterUtil.getBoolean(
			PropsUtil.get("journal.articles.index.all.versions"));

	public static final String PORTLET_ID = PortletKeys.JOURNAL;

	public JournalArticleIndexer() {
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getPortletId() {
		return PORTLET_ID;
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
			JournalArticleLocalServiceUtil.getArticlesByResourcePrimKey(
				classPK);

		for (JournalArticle article : articles) {
			if (isVisible(article.getStatus(), status)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		Long classNameId = (Long)searchContext.getAttribute(
			Field.CLASS_NAME_ID);

		if ((classNameId != null) && (classNameId.longValue() != 0)) {
			contextQuery.addRequiredTerm("classNameId", classNameId.toString());
		}

		addStatus(contextQuery, searchContext);

		addSearchClassTypeIds(contextQuery, searchContext);

		String ddmStructureFieldName = (String)searchContext.getAttribute(
			"ddmStructureFieldName");
		Serializable ddmStructureFieldValue = searchContext.getAttribute(
			"ddmStructureFieldValue");

		if (Validator.isNotNull(ddmStructureFieldName) &&
			Validator.isNotNull(ddmStructureFieldValue)) {

			String[] ddmStructureFieldNameParts = StringUtil.split(
				ddmStructureFieldName, StringPool.SLASH);

			DDMStructure structure = DDMStructureLocalServiceUtil.getStructure(
				GetterUtil.getLong(ddmStructureFieldNameParts[1]));

			String fieldName = StringUtil.replaceLast(
				ddmStructureFieldNameParts[2],
				StringPool.UNDERLINE.concat(
					LocaleUtil.toLanguageId(searchContext.getLocale())),
				StringPool.BLANK);

			try {
				ddmStructureFieldValue = DDMUtil.getIndexedFieldValue(
					ddmStructureFieldValue, structure.getFieldType(fieldName));
			}
			catch (StructureFieldException sfe) {
			}

			contextQuery.addRequiredTerm(
				ddmStructureFieldName,
				StringPool.QUOTE + ddmStructureFieldValue + StringPool.QUOTE);
		}

		String articleType = (String)searchContext.getAttribute("articleType");

		if (Validator.isNotNull(articleType)) {
			contextQuery.addRequiredTerm(Field.TYPE, articleType);
		}

		String ddmStructureKey = (String)searchContext.getAttribute(
			"ddmStructureKey");

		if (Validator.isNotNull(ddmStructureKey)) {
			contextQuery.addRequiredTerm("ddmStructureKey", ddmStructureKey);
		}

		String ddmTemplateKey = (String)searchContext.getAttribute(
			"ddmTemplateKey");

		if (Validator.isNotNull(ddmTemplateKey)) {
			contextQuery.addRequiredTerm("ddmTemplateKey", ddmTemplateKey);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.CLASS_PK, false);
		addSearchLocalizedTerm(
			searchQuery, searchContext, Field.CONTENT, false);
		addSearchLocalizedTerm(
			searchQuery, searchContext, Field.DESCRIPTION, false);
		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchLocalizedTerm(searchQuery, searchContext, Field.TITLE, false);
		addSearchTerm(searchQuery, searchContext, Field.TYPE, false);
		addSearchTerm(searchQuery, searchContext, Field.USER_NAME, false);

		addSearchTerm(searchQuery, searchContext, "articleId", false);

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params != null) {
			String expandoAttributes = (String)params.get("expandoAttributes");

			if (Validator.isNotNull(expandoAttributes)) {
				addSearchExpando(searchQuery, searchContext, expandoAttributes);
			}
		}
	}

	protected void addDDMStructureAttributes(
			Document document, JournalArticle article)
		throws Exception {

		if (Validator.isNull(article.getStructureId())) {
			return;
		}

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
			article.getGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			article.getStructureId(), true);

		if (ddmStructure == null) {
			return;
		}

		document.addKeyword(Field.CLASS_TYPE_ID, ddmStructure.getStructureId());

		Fields fields = null;

		try {
			fields = JournalConverterUtil.getDDMFields(
				ddmStructure, article.getContent());
		}
		catch (Exception e) {
			return;
		}

		if (fields != null) {
			DDMIndexerUtil.addAttributes(document, ddmStructure, fields);
		}
	}

	@Override
	protected void addSearchLocalizedTerm(
			BooleanQuery searchQuery, SearchContext searchContext, String field,
			boolean like)
		throws Exception {

		if (Validator.isNull(field)) {
			return;
		}

		String value = String.valueOf(searchContext.getAttribute(field));

		if (Validator.isNull(value)) {
			value = searchContext.getKeywords();
		}

		if (Validator.isNull(value)) {
			return;
		}

		String localizedField = DocumentImpl.getLocalizedName(
			searchContext.getLocale(), field);

		if (Validator.isNull(searchContext.getKeywords())) {
			BooleanQuery localizedQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			localizedQuery.addTerm(field, value, like);
			localizedQuery.addTerm(localizedField, value, like);

			BooleanClauseOccur booleanClauseOccur = BooleanClauseOccur.SHOULD;

			if (searchContext.isAndSearch()) {
				booleanClauseOccur = BooleanClauseOccur.MUST;
			}

			searchQuery.add(localizedQuery, booleanClauseOccur);
		}
		else {
			searchQuery.addTerm(localizedField, value, like);
		}
	}

	@Override
	protected void addStatus(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		boolean includeScheduledArticles = false;

		if (params != null) {
			includeScheduledArticles = GetterUtil.getBoolean(
				params.get("includeScheduledArticles"));
		}

		if (includeScheduledArticles) {
			BooleanQuery statusQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			BooleanQuery statusHeadQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			statusHeadQuery.addRequiredTerm("head", Boolean.TRUE);
			statusHeadQuery.addRequiredTerm(
				Field.STATUS, WorkflowConstants.STATUS_APPROVED);

			statusQuery.add(statusHeadQuery, BooleanClauseOccur.SHOULD);

			BooleanQuery statusScheduledHeadQuery =
				BooleanQueryFactoryUtil.create(searchContext);

			statusScheduledHeadQuery.addRequiredTerm(
				"scheduledHead", Boolean.TRUE);
			statusScheduledHeadQuery.addRequiredTerm(
				Field.STATUS, WorkflowConstants.STATUS_SCHEDULED);

			statusQuery.add(
				statusScheduledHeadQuery, BooleanClauseOccur.SHOULD);

			contextQuery.add(statusQuery, BooleanClauseOccur.MUST);
		}
		else {
			super.addStatus(contextQuery, searchContext);

			boolean head = GetterUtil.getBoolean(
				searchContext.getAttribute("head"), Boolean.TRUE);
			boolean relatedClassName = GetterUtil.getBoolean(
				searchContext.getAttribute("relatedClassName"));

			if (head && !relatedClassName) {
				contextQuery.addRequiredTerm("head", Boolean.TRUE);
			}
		}
	}

	protected void addStatusHeads(Document document, JournalArticle article)
		throws SystemException {

		boolean head = false;
		boolean scheduledHead = false;

		int[] statuses = new int[] {
			WorkflowConstants.STATUS_APPROVED, WorkflowConstants.STATUS_IN_TRASH
		};

		JournalArticle latestArticle =
			JournalArticleLocalServiceUtil.fetchLatestArticle(
				article.getResourcePrimKey(), statuses);

		if (latestArticle == null) {
			statuses = new int[] {WorkflowConstants.STATUS_SCHEDULED};

			latestArticle = JournalArticleLocalServiceUtil.fetchLatestArticle(
				article.getResourcePrimKey(), statuses);
		}

		if ((latestArticle != null) && latestArticle.isIndexable() &&
			(article.getId() == latestArticle.getId())) {

			if (latestArticle.getStatus() ==
					WorkflowConstants.STATUS_SCHEDULED) {

				scheduledHead = true;
			}
			else {
				head = true;
			}
		}

		document.addKeyword("head", head);
		document.addKeyword("scheduledHead", scheduledHead);
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		JournalArticle article = (JournalArticle)obj;

		long classPK = article.getId();

		if (!PropsValues.JOURNAL_ARTICLE_INDEX_ALL_VERSIONS) {
			classPK = article.getResourcePrimKey();
		}

		deleteDocument(article.getCompanyId(), classPK);

		if (!article.isApproved()) {
			return;
		}

		JournalArticle latestIndexableArticle =
			JournalArticleLocalServiceUtil.fetchLatestIndexableArticle(
				article.getResourcePrimKey());

		if ((latestIndexableArticle == null) ||
			(PropsValues.JOURNAL_ARTICLE_INDEX_ALL_VERSIONS &&
		 	(latestIndexableArticle.getVersion() > article.getVersion()))) {

			return;
		}

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), article.getCompanyId(),
			getDocument(latestIndexableArticle));
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		JournalArticle article = (JournalArticle)obj;

		Document document = getBaseModelDocument(PORTLET_ID, article);

		long classPK = article.getId();

		if (!PropsValues.JOURNAL_ARTICLE_INDEX_ALL_VERSIONS) {
			classPK = article.getResourcePrimKey();
		}

		document.addUID(PORTLET_ID, classPK);

		String articleDefaultLanguageId = LocalizationUtil.getDefaultLanguageId(
			article.getContent());

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		String[] languageIds = getLanguageIds(
			defaultLanguageId, article.getContent());

		for (String languageId : languageIds) {
			String content = extractContent(article, languageId);

			String description = article.getDescription(languageId);

			String title = article.getTitle(languageId);

			if (languageId.equals(articleDefaultLanguageId)) {
				document.addText(Field.CONTENT, content);
				document.addText(Field.DESCRIPTION, description);
				document.addText(Field.TITLE, title);
				document.addText("defaultLanguageId", languageId);
			}

			document.addText(
				Field.CONTENT.concat(StringPool.UNDERLINE).concat(languageId),
				content);
			document.addText(
				Field.DESCRIPTION.concat(StringPool.UNDERLINE).concat(
					languageId), description);
			document.addText(
				Field.TITLE.concat(StringPool.UNDERLINE).concat(languageId),
				title);
		}

		document.addKeyword(Field.FOLDER_ID, article.getFolderId());
		document.addKeyword(Field.LAYOUT_UUID, article.getLayoutUuid());
		document.addKeyword(
			Field.TREE_PATH,
			StringUtil.split(article.getTreePath(), CharPool.SLASH));
		document.addKeyword(Field.TYPE, article.getType());
		document.addKeyword(Field.VERSION, article.getVersion());

		String articleId = article.getArticleId();

		if (article.isInTrash()) {
			articleId = TrashUtil.getOriginalTitle(articleId);
		}

		document.addKeyword("articleId", articleId);
		document.addKeyword("ddmStructureKey", article.getStructureId());
		document.addKeyword("ddmTemplateKey", article.getTemplateId());
		document.addDate("displayDate", article.getDisplayDate());

		addDDMStructureAttributes(document, article);

		addStatusHeads(document, article);

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
		PortletURL portletURL) {

		Locale snippetLocale = getSnippetLocale(document, locale);

		String localizedTitleName = DocumentImpl.getLocalizedName(
			locale, Field.TITLE);

		if ((snippetLocale == null) ||
			(document.getField(localizedTitleName) == null)) {

			snippetLocale = LocaleUtil.fromLanguageId(
				document.get("defaultLanguageId"));
		}

		String title = document.get(
			snippetLocale, Field.SNIPPET + StringPool.UNDERLINE + Field.TITLE,
			Field.TITLE);

		String content = StringPool.BLANK;

		String ddmStructureKey = document.get("ddmStructureKey");

		if (Validator.isNotNull(ddmStructureKey)) {
			content = getDDMContentSummary(document, snippetLocale);
		}
		else {
			content = getBasicContentSummary(document, snippetLocale);
		}

		String groupId = document.get(Field.GROUP_ID);
		String articleId = document.get("articleId");
		String version = document.get(Field.VERSION);

		portletURL.setParameter("struts_action", "/journal/edit_article");
		portletURL.setParameter("groupId", groupId);
		portletURL.setParameter("articleId", articleId);
		portletURL.setParameter("version", version);

		return new Summary(snippetLocale, title, content, portletURL);
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		JournalArticle article = (JournalArticle)obj;

		if (PortalUtil.getClassNameId(DDMStructure.class) ==
				article.getClassNameId()) {

			Document document = getDocument(article);

			SearchEngineUtil.deleteDocument(
				getSearchEngineId(), article.getCompanyId(),
				document.get(Field.UID));

			return;
		}

		if (!PropsValues.JOURNAL_ARTICLE_INDEX_ALL_VERSIONS) {
			int status = article.getStatus();

			if ((status != WorkflowConstants.STATUS_APPROVED) &&
				(status != WorkflowConstants.STATUS_IN_TRASH) &&
				(status != WorkflowConstants.STATUS_SCHEDULED)) {

				deleteDocument(
					article.getCompanyId(), article.getResourcePrimKey());
			}
		}

		reindexArticleVersions(article);
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		JournalArticle article =
			JournalArticleLocalServiceUtil.fetchJournalArticle(classPK);

		if (article == null) {
			article =
				JournalArticleLocalServiceUtil.fetchLatestIndexableArticle(
					classPK);
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

	@Override
	protected void doReindexDDMStructures(List<Long> ddmStructureIds)
		throws Exception {

		String[] ddmStructureKeys = new String[ddmStructureIds.size()];

		for (int i = 0; i < ddmStructureIds.size(); i++) {
			long structureId = ddmStructureIds.get(i);

			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.getDDMStructure(structureId);

			ddmStructureKeys[i] = ddmStructure.getStructureKey();
		}

		List<JournalArticle> articles =
			JournalArticleLocalServiceUtil.getStructureArticles(
				ddmStructureKeys);

		for (JournalArticle article : articles) {
			doReindex(article);
		}
	}

	protected String extractBasicContent(
		JournalArticle article, String languageId) {

		String content = article.getContentByLocale(languageId);

		content = StringUtil.replace(content, "<![CDATA[", StringPool.BLANK);
		content = StringUtil.replace(content, "]]>", StringPool.BLANK);
		content = StringUtil.replace(content, "&amp;", "&");
		content = StringUtil.replace(content, "&lt;", "<");
		content = StringUtil.replace(content, "&gt;", ">");

		content = HtmlUtil.extractText(content);

		return content;
	}

	protected String extractContent(JournalArticle article, String languageId)
		throws Exception {

		if (Validator.isNotNull(article.getStructureId())) {
			return extractDDMContent(article, languageId);
		}

		return extractBasicContent(article, languageId);
	}

	protected String extractDDMContent(
			JournalArticle article, String languageId)
		throws Exception {

		if (Validator.isNull(article.getStructureId())) {
			return StringPool.BLANK;
		}

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
			article.getGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			article.getStructureId(), true);

		if (ddmStructure == null) {
			return StringPool.BLANK;
		}

		Fields fields = null;

		try {
			fields = JournalConverterUtil.getDDMFields(
				ddmStructure, article.getContent());
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}

		if (fields == null) {
			return StringPool.BLANK;
		}

		return DDMIndexerUtil.extractAttributes(
			ddmStructure, fields, LocaleUtil.fromLanguageId(languageId));
	}

	protected Collection<Document> getArticleVersions(JournalArticle article)
		throws PortalException, SystemException {

		Collection<Document> documents = new ArrayList<Document>();

		List<JournalArticle> articles = null;

		if (PropsValues.JOURNAL_ARTICLE_INDEX_ALL_VERSIONS) {
			articles =
				JournalArticleLocalServiceUtil.
					getIndexableArticlesByResourcePrimKey(
						article.getResourcePrimKey());
		}
		else {
			articles = new ArrayList<JournalArticle>();

			JournalArticle latestIndexableArticle =
				JournalArticleLocalServiceUtil.fetchLatestIndexableArticle(
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

	protected String getBasicContentSummary(
		Document document, Locale snippetLocale) {

		String prefix = Field.SNIPPET + StringPool.UNDERLINE;

		String content = document.get(
			snippetLocale, prefix + Field.DESCRIPTION, prefix + Field.CONTENT);

		if (Validator.isBlank(content)) {
			content = document.get(
				snippetLocale, Field.DESCRIPTION, Field.CONTENT);
		}

		if (content.length() > 200) {
			content = StringUtil.shorten(content, 200);
		}

		return content;
	}

	protected String getDDMContentSummary(
		Document document, Locale snippetLocale) {

		String content = StringPool.BLANK;

		try {
			long groupId = GetterUtil.getLong(document.get(Field.GROUP_ID));
			String articleId = document.get("articleId");
			double version = GetterUtil.getDouble(document.get(Field.VERSION));

			JournalArticle article =
				JournalArticleLocalServiceUtil.fetchArticle(
					groupId, articleId, version);

			if (article == null) {
				return content;
			}

			JournalArticleDisplay articleDisplay =
				JournalArticleLocalServiceUtil.getArticleDisplay(
					article, null, Constants.VIEW,
					LocaleUtil.toLanguageId(snippetLocale), 1, null, null);

			content = HtmlUtil.escape(articleDisplay.getDescription());
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

	protected String[] getLanguageIds(
		String defaultLanguageId, String content) {

		String[] languageIds = LocalizationUtil.getAvailableLanguageIds(
			content);

		if (languageIds.length == 0) {
			languageIds = new String[] {defaultLanguageId};
		}

		return languageIds;
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void reindexArticles(long companyId)
		throws PortalException, SystemException {

		ActionableDynamicQuery actionableDynamicQuery =
			new JournalArticleActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				if (PropsValues.JOURNAL_ARTICLE_INDEX_ALL_VERSIONS) {
					return;
				}

				Property statusProperty = PropertyFactoryUtil.forName("status");

				Integer[] statuses = {
					WorkflowConstants.STATUS_APPROVED,
					WorkflowConstants.STATUS_IN_TRASH,
					WorkflowConstants.STATUS_SCHEDULED
				};

				dynamicQuery.add(statusProperty.in(statuses));
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				JournalArticle article = (JournalArticle)object;

				Document document = getDocument(article);

				addDocument(document);
			}

		};

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		actionableDynamicQuery.performActions();
	}

	protected void reindexArticleVersions(JournalArticle article)
		throws PortalException, SystemException {

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), article.getCompanyId(),
			getArticleVersions(article), isCommitImmediately());
	}

	private static Log _log = LogFactoryUtil.getLog(
		JournalArticleIndexer.class);

}