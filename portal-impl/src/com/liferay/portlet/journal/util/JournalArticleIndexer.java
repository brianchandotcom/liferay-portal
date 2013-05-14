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
import com.liferay.portal.kernel.dao.orm.Junction;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
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
import com.liferay.portlet.dynamicdatamapping.StructureFieldException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.util.DDMIndexerUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;
import com.liferay.portlet.journal.asset.JournalArticleAssetRendererFactory;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;
import com.liferay.portlet.journal.service.persistence.JournalArticleActionableDynamicQuery;
import com.liferay.portlet.trash.util.TrashUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		Long classNameId = (Long)searchContext.getAttribute(
			Field.CLASS_NAME_ID);

		if ((classNameId != null) && (classNameId.longValue() != 0)) {
			contextQuery.addRequiredTerm("classNameId", classNameId.toString());
		}

		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_APPROVED);

		if (status != WorkflowConstants.STATUS_ANY) {
			contextQuery.addRequiredTerm(Field.STATUS, status);
		}

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

		long[] folderIds = searchContext.getFolderIds();

		if ((folderIds != null) && (folderIds.length > 0) &&
			(folderIds[0] !=
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			BooleanQuery folderIdsQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			for (long folderId : folderIds) {
				try {
					JournalFolderServiceUtil.getFolder(folderId);
				}
				catch (Exception e) {
					continue;
				}

				folderIdsQuery.addTerm(Field.FOLDER_ID, folderId);
			}

			contextQuery.add(folderIdsQuery, BooleanClauseOccur.MUST);
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

		long[] groupIds = searchContext.getGroupIds();

		if ((groupIds != null) && (groupIds.length > 0)) {
			List<DDMStructure> ddmStructures =
				DDMStructureLocalServiceUtil.getStructures(
					groupIds, PortalUtil.getClassNameId(JournalArticle.class));

			for (DDMStructure ddmStructure : ddmStructures) {
				addSearchLocalizedDDMStructure(
					searchQuery, searchContext, ddmStructure);
			}
		}

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

		field = DocumentImpl.getLocalizedName(searchContext.getLocale(), field);

		if (searchContext.isAndSearch()) {
			searchQuery.addRequiredTerm(field, value, like);
		}
		else {
			searchQuery.addTerm(field, value, like);
		}
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		JournalArticle article = (JournalArticle)obj;

		deleteDocument(
			article.getCompanyId(), article.getGroupId(),
			article.getArticleId());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		JournalArticle article = (JournalArticle)obj;

		Document document = getBaseModelDocument(PORTLET_ID, article);

		document.addUID(
			PORTLET_ID, article.getGroupId(), article.getArticleId());

		String articleDefaultLanguageId = LocalizationUtil.getDefaultLocale(
			article.getContent());

		Locale defaultLocale = LocaleUtil.getDefault();

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

		if (!article.isInTrash() && article.isInTrashContainer()) {
			JournalFolder folder = article.getTrashContainer();

			addTrashFields(
				document, JournalFolder.class.getName(), folder.getFolderId(),
				null, null, JournalArticleAssetRendererFactory.TYPE);

			document.addKeyword(
				Field.ROOT_ENTRY_CLASS_NAME, JournalFolder.class.getName());
			document.addKeyword(
				Field.ROOT_ENTRY_CLASS_PK, folder.getFolderId());
			document.addKeyword(
				Field.STATUS, WorkflowConstants.STATUS_IN_TRASH);
		}

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

		if (snippetLocale == null) {
			snippetLocale = LocaleUtil.fromLanguageId(
				document.get("defaultLanguageId"));
		}

		String prefix = Field.SNIPPET + StringPool.UNDERLINE;

		String title = document.get(
			snippetLocale, prefix + Field.TITLE, Field.TITLE);

		String content = document.get(
			snippetLocale, prefix + Field.DESCRIPTION, prefix + Field.CONTENT);

		if (Validator.isBlank(content)) {
			content = document.get(
				snippetLocale, Field.DESCRIPTION, Field.CONTENT);
		}

		if (content.length() > 200) {
			content = StringUtil.shorten(content, 200);
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

		Document document = getDocument(article);

		if (!article.isIndexable() ||
			(!article.isApproved() && !article.isInTrash() &&
			 (article.getVersion() !=
				  JournalArticleConstants.VERSION_DEFAULT))) {

			SearchEngineUtil.deleteDocument(
				getSearchEngineId(), article.getCompanyId(),
				document.get(Field.UID));

			return;
		}

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), article.getCompanyId(), document);
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		JournalArticle article =
			JournalArticleLocalServiceUtil.getLatestArticle(
				classPK, WorkflowConstants.STATUS_APPROVED);

		doReindex(article);
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

	protected String extractContent(JournalArticle article, String languageId) {
		String content = article.getContentByLocale(languageId);

		if (Validator.isNotNull(article.getStructureId())) {
			return StringPool.BLANK;
		}

		content = StringUtil.replace(content, "<![CDATA[", StringPool.BLANK);
		content = StringUtil.replace(content, "]]>", StringPool.BLANK);
		content = StringUtil.replace(content, "&amp;", "&");
		content = StringUtil.replace(content, "&lt;", "<");
		content = StringUtil.replace(content, "&gt;", ">");

		content = HtmlUtil.extractText(content);

		return content;
	}

	protected String[] getLanguageIds(
		String defaultLanguageId, String content) {

		String[] languageIds = LocalizationUtil.getAvailableLocales(content);

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

		final Collection<Document> documents = new ArrayList<Document>();

		final Set<String> latestArticleIds = new HashSet<String>();

		ActionableDynamicQuery actionableDynamicQuery =
			new JournalArticleActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				Junction junction = RestrictionsFactoryUtil.disjunction();

				Junction approvedArticlesJunction =
					RestrictionsFactoryUtil.conjunction();

				Property statusProperty = PropertyFactoryUtil.forName("status");

				approvedArticlesJunction.add(
					statusProperty.eq(WorkflowConstants.STATUS_APPROVED));

				junction.add(approvedArticlesJunction);

				Junction draftArticlesJunction =
					RestrictionsFactoryUtil.conjunction();

				Property versionProperty = PropertyFactoryUtil.forName(
					"version");

				draftArticlesJunction.add(
					versionProperty.eq(
						JournalArticleConstants.VERSION_DEFAULT));

				draftArticlesJunction.add(
					statusProperty.eq(WorkflowConstants.STATUS_DRAFT));

				junction.add(draftArticlesJunction);

				dynamicQuery.add(junction);

				Property indexableProperty = PropertyFactoryUtil.forName(
					"indexable");

				dynamicQuery.add(indexableProperty.eq(true));
			}

			@Override
			protected void performAction(Object object)
				throws PortalException, SystemException {

				JournalArticle article = (JournalArticle)object;

				if (article.isApproved()) {
					JournalArticle latestArticle =
						JournalArticleLocalServiceUtil.getLatestArticle(
							article.getResourcePrimKey(),
							WorkflowConstants.STATUS_APPROVED);

					String latestArticleId = latestArticle.getArticleId();

					if (latestArticleIds.contains(latestArticleId)) {
						return;
					}

					latestArticleIds.add(latestArticleId);

					article = latestArticle;
				}

				Document document = getDocument(article);

				documents.add(document);
			}

		};

		actionableDynamicQuery.setCompanyId(companyId);

		actionableDynamicQuery.performActions();

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents);
	}

}