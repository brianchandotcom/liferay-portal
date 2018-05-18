/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.search;

import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPOptionCategoryLocalService;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedHashMap;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = Indexer.class)
public class CPSpecificationOptionIndexer
	extends BaseIndexer<CPSpecificationOption> {

	public static final String CLASS_NAME =
		CPSpecificationOption.class.getName();

	public static final String FIELD_CP_OPTION_CATEGORY_ID =
		"cpOptionCategoryId";

	public static final String FIELD_CP_OPTION_CATEGORY_TITLE =
		"cpOptionCategoryTitle";

	public static final String FIELD_KEY = "key";

	public CPSpecificationOptionIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID,
			Field.TITLE, Field.UID, FIELD_KEY);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(
			searchQuery, searchContext, FIELD_CP_OPTION_CATEGORY_ID, false);
		addSearchTerm(
			searchQuery, searchContext, FIELD_CP_OPTION_CATEGORY_TITLE, false);
		addSearchLocalizedTerm(
			searchQuery, searchContext, FIELD_CP_OPTION_CATEGORY_TITLE, false);
		addSearchLocalizedTerm(
			searchQuery, searchContext, Field.DESCRIPTION, false);
		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchTerm(searchQuery, searchContext, FIELD_KEY, false);
		addSearchTerm(searchQuery, searchContext, Field.TITLE, false);
		addSearchLocalizedTerm(searchQuery, searchContext, Field.TITLE, false);
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
	protected void doDelete(CPSpecificationOption cpSpecificationOption)
		throws Exception {

		deleteDocument(
			cpSpecificationOption.getCompanyId(),
			cpSpecificationOption.getCPSpecificationOptionId());
	}

	@Override
	protected Document doGetDocument(
			CPSpecificationOption cpSpecificationOption)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Indexing specification option " + cpSpecificationOption);
		}

		Document document = getBaseModelDocument(
			CLASS_NAME, cpSpecificationOption);

		CPOptionCategory cpOptionCategory =
			_cpOptionCategoryLocalService.fetchCPOptionCategory(
				cpSpecificationOption.getCPOptionCategoryId());

		String[] languageIds = LocalizationUtil.getAvailableLanguageIds(
			cpSpecificationOption.getTitle());

		for (String languageId : languageIds) {
			String description = cpSpecificationOption.getDescription(
				languageId);
			String title = cpSpecificationOption.getTitle(languageId);

			document.addText(
				LocalizationUtil.getLocalizedName(Field.TITLE, languageId),
				title);

			document.addText(
				LocalizationUtil.getLocalizedName(
					Field.DESCRIPTION, languageId),
				description);

			if (cpOptionCategory != null) {
				document.addText(
					LocalizationUtil.getLocalizedName(
						FIELD_CP_OPTION_CATEGORY_TITLE, languageId),
					cpOptionCategory.getTitle(languageId));

				document.addKeyword(
					FIELD_CP_OPTION_CATEGORY_ID,
					cpOptionCategory.getCPOptionCategoryId());
			}

			document.addText(FIELD_KEY, cpSpecificationOption.getKey());
			document.addText(Field.CONTENT, title);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + cpSpecificationOption + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(
			document, Field.TITLE, Field.DESCRIPTION);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(CPSpecificationOption cpSpecificationOption)
		throws Exception {

		Document document = getDocument(cpSpecificationOption);

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), cpSpecificationOption.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		CPSpecificationOption cpSpecificationOption =
			_cpSpecificationOptionLocalService.getCPSpecificationOption(
				classPK);

		doReindex(cpSpecificationOption);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCPSpecificationOptions(companyId);
	}

	protected void reindexCPSpecificationOptions(long companyId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_cpSpecificationOptionLocalService.
				getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.
				PerformActionMethod<CPSpecificationOption>() {

				@Override
				public void performAction(
					CPSpecificationOption cpSpecificationOption) {

					try {
						Document document = getDocument(cpSpecificationOption);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index commerce product " +
									"specification option " +
										cpSpecificationOption.
											getCPSpecificationOptionId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPSpecificationOptionIndexer.class);

	@Reference
	private CPOptionCategoryLocalService _cpOptionCategoryLocalService;

	@Reference
	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}