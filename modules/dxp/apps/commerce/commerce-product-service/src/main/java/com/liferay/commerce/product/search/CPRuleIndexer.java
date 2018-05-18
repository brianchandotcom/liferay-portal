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

import com.liferay.commerce.product.model.CPRule;
import com.liferay.commerce.product.model.CPRuleUserSegmentRel;
import com.liferay.commerce.product.service.CPRuleLocalService;
import com.liferay.commerce.product.service.CPRuleUserSegmentRelLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.filter.FilterBuilders;
import com.liferay.portal.search.filter.TermsSetFilterBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = Indexer.class)
public class CPRuleIndexer extends BaseIndexer<CPRule> {

	public static final String CLASS_NAME = CPRule.class.getName();

	public CPRuleIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.NAME,
			Field.SCOPE_GROUP_ID, Field.UID);
		setFilterSearch(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		long[] commerceUserSegmentEntryIds = GetterUtil.getLongValues(
			searchContext.getAttribute("commerceUserSegmentEntryIds"), null);

		if (commerceUserSegmentEntryIds != null) {
			TermsSetFilterBuilder termsSetFilterBuilder =
				_filterBuilders.termsSetFilterBuilder();

			termsSetFilterBuilder.setFieldName("commerceUserSegmentEntryIds");
			termsSetFilterBuilder.setMinimumShouldMatchField(
				"commerceUserSegmentEntryIds_required_matches");

			List<String> values = new ArrayList<>(
				commerceUserSegmentEntryIds.length);

			for (long commerceUserSegmentEntryId :
					commerceUserSegmentEntryIds) {

				values.add(String.valueOf(commerceUserSegmentEntryId));
			}

			termsSetFilterBuilder.setValues(values);

			contextBooleanFilter.add(
				termsSetFilterBuilder.build(), BooleanClauseOccur.MUST);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchTerm(searchQuery, searchContext, Field.NAME, false);
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
	protected void doDelete(CPRule cpRule) throws Exception {
		deleteDocument(cpRule.getCompanyId(), cpRule.getCPRuleId());
	}

	@Override
	protected Document doGetDocument(CPRule cpRule) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Indexing product rule " + cpRule);
		}

		Document document = getBaseModelDocument(CLASS_NAME, cpRule);

		document.addNumber(Field.ENTRY_CLASS_PK, cpRule.getCPRuleId());
		document.addText(Field.NAME, cpRule.getName());
		document.addText(Field.USER_NAME, cpRule.getUserName());

		List<CPRuleUserSegmentRel> cpRuleUserSegmentRels =
			_cpRuleUserSegmentRelLocalService.getCPRuleUserSegmentRels(
				cpRule.getCPRuleId());

		Stream<CPRuleUserSegmentRel> stream = cpRuleUserSegmentRels.stream();

		long[] commerceUserSegmentEntryIds = stream.mapToLong(
			CPRuleUserSegmentRel::getCommerceUserSegmentEntryId).toArray();

		document.addNumber(
			"commerceUserSegmentEntryIds", commerceUserSegmentEntryIds);
		document.addNumber(
			"commerceUserSegmentEntryIds_required_matches",
			commerceUserSegmentEntryIds.length);

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + cpRule + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(
			document, Field.ENTRY_CLASS_PK, Field.NAME);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(CPRule cpRule) throws Exception {
		Document document = getDocument(cpRule);

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), cpRule.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		CPRule cpRule = _cpRuleLocalService.getCPRule(classPK);

		doReindex(cpRule);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCPRules(companyId);
	}

	protected void reindexCPRules(long companyId) throws PortalException {
		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_cpRuleLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<CPRule>() {

				@Override
				public void performAction(CPRule cpRule) {
					try {
						Document document = getDocument(cpRule);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index commerce product rule " +
									cpRule.getCPRuleId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(CPRuleIndexer.class);

	@Reference
	private CPRuleLocalService _cpRuleLocalService;

	@Reference
	private CPRuleUserSegmentRelLocalService _cpRuleUserSegmentRelLocalService;

	@Reference
	private FilterBuilders _filterBuilders;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}