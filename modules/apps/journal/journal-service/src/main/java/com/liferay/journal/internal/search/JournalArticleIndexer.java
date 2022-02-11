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

package com.liferay.journal.internal.search;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.ExpandoQueryContributor;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.batch.BatchIndexingHelper;
import com.liferay.portal.search.filter.DateRangeFilterBuilder;
import com.liferay.portal.search.filter.FilterBuilders;
import com.liferay.portal.search.localization.SearchLocalizationHelper;
import com.liferay.portal.search.model.uid.UIDFactory;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import java.io.Serializable;

import java.text.Format;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
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
 * @author Hugo Huijser
 * @author Tibor Lipusz
 */
@Component(immediate = true, service = Indexer.class)
public class JournalArticleIndexer extends BaseIndexer<JournalArticle> {

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

		return _journalArticleModelResourcePermission.contains(
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

		long[] classTypeIds = searchContext.getClassTypeIds();

		if (ArrayUtil.isNotEmpty(classTypeIds)) {
			TermsFilter classTypeIdsTermsFilter = new TermsFilter(
				Field.CLASS_TYPE_ID);

			classTypeIdsTermsFilter.addValues(
				ArrayUtil.toStringArray(classTypeIds));

			contextBooleanFilter.add(
				classTypeIdsTermsFilter, BooleanClauseOccur.MUST);
		}

		String ddmStructureFieldName = (String)searchContext.getAttribute(
			"ddmStructureFieldName");
		Serializable ddmStructureFieldValue = searchContext.getAttribute(
			"ddmStructureFieldValue");

		if (Validator.isNotNull(ddmStructureFieldName) &&
			Validator.isNotNull(ddmStructureFieldValue)) {

			Locale locale = searchContext.getLocale();

			long[] groupIds = searchContext.getGroupIds();

			if (ArrayUtil.isNotEmpty(groupIds)) {
				try {
					locale = _portal.getSiteDefaultLocale(groupIds[0]);
				}
				catch (PortalException portalException) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							portalException.getMessage(), portalException);
					}
				}
			}

			try {
				QueryFilter queryFilter =
					_ddmIndexer.createFieldValueQueryFilter(
						ddmStructureFieldName, ddmStructureFieldValue, locale);

				contextBooleanFilter.add(queryFilter, BooleanClauseOccur.MUST);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception.getMessage(), exception);
				}
			}
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
		boolean latest = GetterUtil.getBoolean(
			searchContext.getAttribute("latest"));
		boolean relatedClassName = GetterUtil.getBoolean(
			searchContext.getAttribute("relatedClassName"));
		boolean showNonindexable = GetterUtil.getBoolean(
			searchContext.getAttribute("showNonindexable"));

		if (latest && !relatedClassName && !showNonindexable) {
			contextBooleanFilter.addRequiredTerm("latest", Boolean.TRUE);
		}
		else if (head && !relatedClassName && !showNonindexable) {
			contextBooleanFilter.addRequiredTerm("head", Boolean.TRUE);
		}

		if (latest && !relatedClassName && showNonindexable) {
			contextBooleanFilter.addRequiredTerm("latest", Boolean.TRUE);
		}
		else if (!relatedClassName && showNonindexable) {
			contextBooleanFilter.addRequiredTerm("headListable", Boolean.TRUE);
		}

		boolean filterExpired = GetterUtil.getBoolean(
			searchContext.getAttribute("filterExpired"));

		if (!filterExpired) {
			return;
		}

		DateRangeFilterBuilder dateRangeFilterBuilder =
			_filterBuilders.dateRangeFilterBuilder();

		dateRangeFilterBuilder.setFieldName(Field.EXPIRATION_DATE);

		String formatPattern = PropsUtil.get(
			PropsKeys.INDEX_DATE_FORMAT_PATTERN);

		dateRangeFilterBuilder.setFormat(formatPattern);

		Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(
			formatPattern);

		dateRangeFilterBuilder.setFrom(dateFormat.format(new Date()));

		dateRangeFilterBuilder.setIncludeLower(false);
		dateRangeFilterBuilder.setIncludeUpper(false);

		contextBooleanFilter.add(dateRangeFilterBuilder.build());
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		_queryHelper.addSearchTerm(
			searchQuery, searchContext, Field.ARTICLE_ID, false);
		_queryHelper.addSearchTerm(
			searchQuery, searchContext, Field.CLASS_PK, false);
		_addSearchLocalizedTerm(searchQuery, searchContext, Field.CONTENT);
		_addSearchLocalizedTerm(searchQuery, searchContext, Field.DESCRIPTION);
		_queryHelper.addSearchTerm(
			searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		_addSearchLocalizedTerm(searchQuery, searchContext, Field.TITLE);
		_queryHelper.addSearchTerm(
			searchQuery, searchContext, Field.USER_NAME, false);

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params != null) {
			String expandoAttributes = (String)params.get("expandoAttributes");

			if (Validator.isNotNull(expandoAttributes)) {
				_expandoQueryContributor.contribute(
					expandoAttributes, searchQuery,
					new String[] {JournalArticle.class.getName()},
					searchContext);
			}
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		String[] localizedFieldNames =
			_searchLocalizationHelper.getLocalizedFieldNames(
				new String[] {Field.CONTENT, Field.DESCRIPTION, Field.TITLE},
				searchContext);

		queryConfig.addHighlightFieldNames(localizedFieldNames);
	}

	@Override
	protected void doDelete(JournalArticle journalArticle) throws Exception {
		_deleteDocument(journalArticle);

		_reindexEveryVersionOfResourcePrimKey(
			journalArticle.getResourcePrimKey());
	}

	@Override
	protected Document doGetDocument(JournalArticle journalArticle)
		throws Exception {

		return null;
	}

	@Override
	protected String doGetSortField(String orderByCol) {
		if (orderByCol.equals("display-date")) {
			return Field.DISPLAY_DATE;
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

		return orderByCol;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return modelSummaryContributor.getSummary(document, locale, snippet);
	}

	@Override
	protected void doReindex(JournalArticle article) throws Exception {
		if (_portal.getClassNameId(DDMStructure.class) ==
				article.getClassNameId()) {

			_deleteDocument(article);

			return;
		}

		_reindexEveryVersionOfResourcePrimKey(article.getResourcePrimKey());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		JournalArticle journalArticle =
			_journalArticleLocalService.fetchJournalArticle(classPK);

		if (journalArticle != null) {
			_reindexEveryVersionOfResourcePrimKey(
				journalArticle.getResourcePrimKey());

			return;
		}

		long resourcePrimKey = classPK;

		_reindexEveryVersionOfResourcePrimKey(resourcePrimKey);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		_reindexArticles(companyId);
	}

	protected boolean isIndexAllArticleVersions() {
		JournalServiceConfiguration journalServiceConfiguration = null;

		try {
			journalServiceConfiguration =
				_configurationProvider.getCompanyConfiguration(
					JournalServiceConfiguration.class,
					CompanyThreadLocal.getCompanyId());

			return journalServiceConfiguration.indexAllArticleVersionsEnabled();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	@Reference(unbind = "-")
	protected void setDDMIndexer(DDMIndexer ddmIndexer) {
		_ddmIndexer = ddmIndexer;
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalArticleResourceLocalService(
		JournalArticleResourceLocalService journalArticleResourceLocalService) {

		_journalArticleResourceLocalService =
			journalArticleResourceLocalService;
	}

	@Reference(
		target = "(indexer.class.name=com.liferay.journal.model.JournalArticle)"
	)
	protected ModelSummaryContributor modelSummaryContributor;

	@Reference
	protected UIDFactory uidFactory;

	private void _addLocalizedFields(
		BooleanQuery booleanQuery, String fieldName, String value,
		SearchContext searchContext) {

		String[] localizedFieldNames =
			_searchLocalizationHelper.getLocalizedFieldNames(
				new String[] {fieldName}, searchContext);

		for (String localizedFieldName : localizedFieldNames) {
			_addTerm(booleanQuery, localizedFieldName, value);
		}
	}

	private void _addLocalizedQuery(
		BooleanQuery booleanQuery, BooleanQuery localizedQuery,
		SearchContext searchContext) {

		BooleanClauseOccur booleanClauseOccur = BooleanClauseOccur.SHOULD;

		if (searchContext.isAndSearch()) {
			booleanClauseOccur = BooleanClauseOccur.MUST;
		}

		try {
			booleanQuery.add(localizedQuery, booleanClauseOccur);
		}
		catch (ParseException parseException) {
			if (_log.isDebugEnabled()) {
				_log.debug(parseException);
			}
		}
	}

	private void _addSearchLocalizedTerm(
		BooleanQuery booleanQuery, SearchContext searchContext,
		String fieldName) {

		if (Validator.isBlank(fieldName)) {
			return;
		}

		String value = GetterUtil.getString(
			searchContext.getAttribute(fieldName));

		if (Validator.isBlank(value)) {
			value = searchContext.getKeywords();
		}

		if (Validator.isBlank(value)) {
			return;
		}

		if (Validator.isBlank(searchContext.getKeywords())) {
			BooleanQuery localizedQuery = new BooleanQueryImpl();

			_addLocalizedFields(
				localizedQuery, fieldName, value, searchContext);

			_addLocalizedQuery(booleanQuery, localizedQuery, searchContext);
		}
		else {
			_addLocalizedFields(booleanQuery, fieldName, value, searchContext);
		}
	}

	private void _addTerm(
		BooleanQuery booleanQuery, String field, String value) {

		try {
			booleanQuery.addTerm(field, value, false);
		}
		catch (ParseException parseException) {
			if (_log.isDebugEnabled()) {
				_log.debug(parseException);
			}
		}
	}

	private void _deleteDocument(JournalArticle article) throws Exception {
		if ((article.getCtCollectionId() == 0) &&
			!CTCollectionThreadLocal.isProductionMode()) {

			return;
		}

		deleteDocument(
			article.getCompanyId(), "UID=" + uidFactory.getUID(article));
	}

	private JournalArticle _fetchLatestIndexableArticleVersion(
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

	private void _reindexArticles(long companyId) throws Exception {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery;

		if (isIndexAllArticleVersions()) {
			indexableActionableDynamicQuery =
				_journalArticleLocalService.
					getIndexableActionableDynamicQuery();

			indexableActionableDynamicQuery.setAddCriteriaMethod(
				dynamicQuery -> {
					Property property = PropertyFactoryUtil.forName(
						"classNameId");

					dynamicQuery.add(
						property.ne(
							_portal.getClassNameId(DDMStructure.class)));
				});
			indexableActionableDynamicQuery.setInterval(
				_batchIndexingHelper.getBulkSize(
					JournalArticle.class.getName()));
			indexableActionableDynamicQuery.setPerformActionMethod(
				(JournalArticle article) -> {
					try {
						indexableActionableDynamicQuery.addDocuments(
							getDocument(article));
					}
					catch (PortalException portalException) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index journal article " +
									article.getId(),
								portalException);
						}
					}
				});
		}
		else {
			indexableActionableDynamicQuery =
				_journalArticleResourceLocalService.
					getIndexableActionableDynamicQuery();

			indexableActionableDynamicQuery.setInterval(
				_batchIndexingHelper.getBulkSize(
					JournalArticleResource.class.getName()));

			indexableActionableDynamicQuery.setPerformActionMethod(
				(JournalArticleResource articleResource) -> {
					JournalArticle latestIndexableArticle =
						_fetchLatestIndexableArticleVersion(
							articleResource.getResourcePrimKey());

					if (latestIndexableArticle == null) {
						return;
					}

					try {
						indexableActionableDynamicQuery.addDocuments(
							getDocument(latestIndexableArticle));
					}
					catch (PortalException portalException) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index journal article " +
									latestIndexableArticle.getId(),
								portalException);
						}
					}
				});
		}

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private void _reindexEveryVersionOfResourcePrimKey(long resourcePrimKey)
		throws Exception {

		List<JournalArticle> journalArticles =
			_journalArticleLocalService.getArticlesByResourcePrimKey(
				resourcePrimKey);

		if (ListUtil.isEmpty(journalArticles)) {
			return;
		}

		JournalArticle article = journalArticles.get(0);

		if (_portal.getClassNameId(DDMStructure.class) ==
				article.getClassNameId()) {

			_deleteDocument(article);

			return;
		}

		if (isIndexAllArticleVersions()) {
			List<Document> documents = new ArrayList<>(journalArticles.size());

			if (CTCollectionThreadLocal.isProductionMode()) {
				for (JournalArticle journalArticle : journalArticles) {
					documents.add(getDocument(journalArticle));
				}
			}
			else {
				for (JournalArticle journalArticle : journalArticles) {
					if (journalArticle.getCtCollectionId() != 0) {
						documents.add(getDocument(journalArticle));
					}
				}
			}

			_indexWriterHelper.updateDocuments(
				getSearchEngineId(), article.getCompanyId(), documents,
				isCommitImmediately());
		}
		else {
			JournalArticle latestIndexableArticle =
				_fetchLatestIndexableArticleVersion(resourcePrimKey);

			for (JournalArticle journalArticle : journalArticles) {
				if (journalArticle.getId() == latestIndexableArticle.getId()) {
					_indexWriterHelper.updateDocument(
						getSearchEngineId(), article.getCompanyId(),
						getDocument(journalArticle), isCommitImmediately());
				}
				else {
					_deleteDocument(journalArticle);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleIndexer.class);

	@Reference
	private BatchIndexingHelper _batchIndexingHelper;

	private ConfigurationProvider _configurationProvider;
	private DDMIndexer _ddmIndexer;

	@Reference
	private ExpandoQueryContributor _expandoQueryContributor;

	@Reference
	private FilterBuilders _filterBuilders;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	private JournalArticleLocalService _journalArticleLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalArticle)"
	)
	private ModelResourcePermission<JournalArticle>
		_journalArticleModelResourcePermission;

	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private QueryHelper _queryHelper;

	@Reference
	private SearchLocalizationHelper _searchLocalizationHelper;

}