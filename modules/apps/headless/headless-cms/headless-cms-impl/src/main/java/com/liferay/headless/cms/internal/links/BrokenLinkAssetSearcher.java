/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.internal.links;

import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.QueriesUtil;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.site.cms.site.initializer.constants.CMSWorkflowConstants;
import com.liferay.site.cms.site.initializer.util.CMSOutboundLinksUtil;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Jürgen Kappler
 */
public class BrokenLinkAssetSearcher {

	public static final String FIELD_NAME_OBJECT_DEFINITION_ID =
		"objectDefinitionId";

	public static final String FIELD_NAME_OBJECT_ENTRY_TITLE =
		"objectEntryTitle";

	public BrokenLinkAssetSearcher(
		ObjectEntryLocalService objectEntryLocalService, Searcher searcher,
		SearchRequestBuilderFactory searchRequestBuilderFactory) {

		_objectEntryLocalService = objectEntryLocalService;
		_searcher = searcher;
		_searchRequestBuilderFactory = searchRequestBuilderFactory;
	}

	public long getCount(
		long companyId, long[] groupIds, Set<String> outboundLinkTokens,
		long userId) {

		SearchResponse searchResponse = _searcher.search(
			_getSearchRequestBuilder(
				companyId, groupIds, outboundLinkTokens, userId
			).build());

		return searchResponse.getCount();
	}

	public Map<String, String> getExpiredAssetTitles(
			long companyId, String languageId, Long[] objectDefinitionIds)
		throws PortalException {

		Map<String, String> titles = new LinkedHashMap<>();

		for (Object[] objects :
				_getExpiredAssetObjects(companyId, objectDefinitionIds)) {

			long objectEntryId = GetterUtil.getLong(objects[1]);

			ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
				objectEntryId);

			if (objectEntry == null) {
				continue;
			}

			String title = objectEntry.getTitleValue(languageId, true);

			titles.put(
				CMSOutboundLinksUtil.getObjectEntryExternalReferenceCodeToken(
					GetterUtil.getString(objects[0])),
				title);
			titles.put(
				CMSOutboundLinksUtil.getObjectEntryIdToken(objectEntryId),
				title);
		}

		return titles;
	}

	public Set<String> getExpiredAssetTokens(
		long companyId, Long[] objectDefinitionIds) {

		Set<String> expiredAssetTokens = new LinkedHashSet<>();

		for (Object[] objects :
				_getExpiredAssetObjects(companyId, objectDefinitionIds)) {

			expiredAssetTokens.add(
				CMSOutboundLinksUtil.getObjectEntryExternalReferenceCodeToken(
					GetterUtil.getString(objects[0])));
			expiredAssetTokens.add(
				CMSOutboundLinksUtil.getObjectEntryIdToken(
					GetterUtil.getLong(objects[1])));
		}

		return expiredAssetTokens;
	}

	public SearchResponse search(
		long companyId, long[] groupIds, String languageId,
		Set<String> outboundLinkTokens, Pagination pagination, String search,
		Sort[] sorts, long userId) {

		SearchRequestBuilder searchRequestBuilder = _getSearchRequestBuilder(
			companyId, groupIds, outboundLinkTokens, userId);

		int startPosition = Math.min(
			pagination.getStartPosition(), _MAX_RESULT_WINDOW);

		searchRequestBuilder.addSelectedFieldNames(
			CMSOutboundLinksUtil.FIELD_NAME, Field.ENTRY_CLASS_PK,
			FIELD_NAME_OBJECT_DEFINITION_ID, FIELD_NAME_OBJECT_ENTRY_TITLE,
			Field.getLocalizedName(languageId, FIELD_NAME_OBJECT_ENTRY_TITLE)
		).from(
			startPosition
		).size(
			Math.min(
				pagination.getPageSize(), _MAX_RESULT_WINDOW - startPosition)
		);

		if (ArrayUtil.isNotEmpty(sorts)) {
			searchRequestBuilder.withSearchContext(
				searchContext -> searchContext.setSorts(sorts));
		}

		if (search != null) {
			searchRequestBuilder.queryString(search);
		}

		return _searcher.search(searchRequestBuilder.build());
	}

	private List<Object[]> _getExpiredAssetObjects(
		long companyId, Long[] objectDefinitionIds) {

		return _objectEntryLocalService.dslQuery(
			DSLQueryFactoryUtil.select(
				ObjectEntryTable.INSTANCE.externalReferenceCode,
				ObjectEntryTable.INSTANCE.objectEntryId
			).from(
				ObjectEntryTable.INSTANCE
			).where(
				ObjectEntryTable.INSTANCE.companyId.eq(
					companyId
				).and(
					ObjectEntryTable.INSTANCE.objectDefinitionId.in(
						objectDefinitionIds)
				).and(
					ObjectEntryTable.INSTANCE.status.eq(
						WorkflowConstants.STATUS_EXPIRED)
				)
			));
	}

	private BooleanQuery _getOutboundLinksBooleanQuery(
		Set<String> outboundLinkTokens) {

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		String[] values = outboundLinkTokens.toArray(new String[0]);

		for (int i = 0; i < values.length; i += _MAX_TERMS_COUNT) {
			booleanQuery.addShouldQueryClauses(
				_getTermsQuery(
					CMSOutboundLinksUtil.FIELD_NAME,
					ArrayUtil.subset(
						values, i,
						Math.min(i + _MAX_TERMS_COUNT, values.length))));
		}

		booleanQuery.setMinimumShouldMatch(1);

		return booleanQuery;
	}

	private SearchRequestBuilder _getSearchRequestBuilder(
		long companyId, long[] groupIds, Set<String> outboundLinkTokens,
		long userId) {

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		booleanQuery.addFilterQueryClauses(
			_getOutboundLinksBooleanQuery(outboundLinkTokens),
			_getTermsQuery("cms_section", "contents", "files"),
			_getTermsQuery(
				Field.STATUS,
				ArrayUtil.toStringArray(CMSWorkflowConstants.STATUSES)),
			QueriesUtil.term("rootDescendantNode", false));

		return _searchRequestBuilderFactory.builder(
		).companyId(
			companyId
		).emptySearchEnabled(
			true
		).groupIds(
			groupIds
		).query(
			booleanQuery
		).withSearchContext(
			searchContext -> {
				searchContext.setAttribute(
					Field.STATUS, WorkflowConstants.STATUS_ANY);
				searchContext.setUserId(userId);
			}
		);
	}

	private TermsQuery _getTermsQuery(String fieldName, String... values) {
		TermsQuery termsQuery = QueriesUtil.terms(fieldName);

		termsQuery.addValues(values);

		return termsQuery;
	}

	private static final int _MAX_RESULT_WINDOW = 10000;

	private static final int _MAX_TERMS_COUNT = 65536;

	private final ObjectEntryLocalService _objectEntryLocalService;
	private final Searcher _searcher;
	private final SearchRequestBuilderFactory _searchRequestBuilderFactory;

}