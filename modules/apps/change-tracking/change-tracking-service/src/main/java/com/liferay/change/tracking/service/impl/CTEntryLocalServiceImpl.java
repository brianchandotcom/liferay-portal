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

package com.liferay.change.tracking.service.impl;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.exception.DuplicateCTEntryException;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.base.CTEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Kocsis
 */
@Component(
	property = "model.class.name=com.liferay.change.tracking.model.CTEntry",
	service = AopService.class
)
public class CTEntryLocalServiceImpl extends CTEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CTEntry addCTEntry(
			long userId, long modelClassNameId, long modelClassPK,
			long modelResourcePrimKey, int changeType, long ctCollectionId,
			ServiceContext serviceContext)
		throws PortalException {

		boolean force = GetterUtil.getBoolean(
			serviceContext.getAttribute("force"));

		CTEntry ctEntry = ctEntryPersistence.fetchByC_C(
			modelClassNameId, modelClassPK);

		_validate(ctEntry, changeType, ctCollectionId, force);

		User user = userLocalService.getUser(userId);

		if (ctEntry != null) {
			return _updateCTEntry(ctEntry, user, changeType, serviceContext);
		}

		return _addCTEntry(
			user, modelClassNameId, modelClassPK, modelResourcePrimKey,
			changeType, ctCollectionId, serviceContext);
	}

	@Override
	public List<CTEntry> fetchCTEntries(long modelClassNameId) {
		return ctEntryPersistence.findByModelClassNameId(modelClassNameId);
	}

	@Override
	public List<CTEntry> fetchCTEntries(
		long ctCollectionId, long modelResourcePrimKey,
		QueryDefinition<CTEntry> queryDefinition) {

		return ctEntryFinder.findByC_R(
			ctCollectionId, modelResourcePrimKey, queryDefinition);
	}

	@Override
	public List<CTEntry> fetchCTEntries(
		long ctCollectionId, QueryDefinition<CTEntry> queryDefinition) {

		return ctEntryFinder.findByC_R(ctCollectionId, 0, queryDefinition);
	}

	@Override
	public List<CTEntry> fetchCTEntries(String modelClassName) {
		return fetchCTEntries(_portal.getClassNameId(modelClassName));
	}

	@Override
	public CTEntry fetchCTEntry(long modelClassNameId, long modelClassPK) {
		return ctEntryPersistence.fetchByC_C(modelClassNameId, modelClassPK);
	}

	@Override
	public CTEntry fetchCTEntry(
		long ctCollectionId, long modelClassNameId, long modelClassPK) {

		return ctEntryFinder.findByC_C_C(
			ctCollectionId, modelClassNameId, modelClassPK);
	}

	@Override
	public List<CTEntry> getCTCollectionCTEntries(long ctCollectionId) {
		return getCTCollectionCTEntries(
			ctCollectionId, WorkflowConstants.STATUS_DRAFT, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	@Override
	public List<CTEntry> getCTCollectionCTEntries(
		long ctCollectionId, int start, int end) {

		return getCTCollectionCTEntries(
			ctCollectionId, WorkflowConstants.STATUS_DRAFT, start, end, null);
	}

	@Override
	public List<CTEntry> getCTCollectionCTEntries(
		long ctCollectionId, int status, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		if (_isProductionCTCollectionId(ctCollectionId)) {
			return super.getCTCollectionCTEntries(
				ctCollectionId, start, end, orderByComparator);
		}

		QueryDefinition<CTEntry> queryDefinition = new QueryDefinition<>();

		queryDefinition.setEnd(end);
		queryDefinition.setOrderByComparator(orderByComparator);
		queryDefinition.setStart(start);
		queryDefinition.setStatus(status);

		return ctEntryFinder.findByCTCollectionId(
			ctCollectionId, queryDefinition);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public List<CTEntry> getRelatedOwnerCTEntries(long ctEntryId) {
		return getRelatedOwnerCTEntries(
			ctEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	@Override
	public List<CTEntry> getRelatedOwnerCTEntries(
		long ctEntryId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		QueryDefinition<CTEntry> queryDefinition = new QueryDefinition<>();

		queryDefinition.setEnd(end);
		queryDefinition.setOrderByComparator(orderByComparator);
		queryDefinition.setStart(start);
		queryDefinition.setStatus(WorkflowConstants.STATUS_DRAFT);

		return ctEntryFinder.findByRelatedCTEntries(ctEntryId, queryDefinition);
	}

	@Override
	public int getRelatedOwnerCTEntriesCount(long ctEntryId) {
		QueryDefinition<CTEntry> queryDefinition = new QueryDefinition<>();

		queryDefinition.setStatus(WorkflowConstants.STATUS_DRAFT);

		return ctEntryFinder.countByRelatedCTEntries(
			ctEntryId, queryDefinition);
	}

	@Override
	public List<CTEntry> search(
		CTCollection ctCollection, long[] groupIds, long[] userIds,
		long[] classNameIds, int[] changeTypes, Boolean collision,
		QueryDefinition<CTEntry> queryDefinition) {

		Query query = _buildQuery(
			ctCollection, groupIds, userIds, classNameIds, changeTypes,
			collision, queryDefinition.getStatus(),
			queryDefinition.isExcludeStatus());

		SearchResponse searchResponse = _search(
			ctCollection.getCompanyId(), query, queryDefinition);

		return _getCTEntries(searchResponse);
	}

	@Override
	public long searchCount(
		CTCollection ctCollection, long[] groupIds, long[] userIds,
		long[] classNameIds, int[] changeTypes, Boolean collision,
		QueryDefinition<CTEntry> queryDefinition) {

		Query query = _buildQuery(
			ctCollection, groupIds, userIds, classNameIds, changeTypes,
			collision, queryDefinition.getStatus(),
			queryDefinition.isExcludeStatus());

		return _searchCount(ctCollection.getCompanyId(), query);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CTEntry updateCollision(long ctEntryId, boolean collision) {
		CTEntry ctEntry = ctEntryPersistence.fetchByPrimaryKey(ctEntryId);

		ctEntry.setCollision(collision);

		return ctEntryPersistence.update(ctEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CTEntry updateStatus(long ctEntryId, int status) {
		if ((status != WorkflowConstants.STATUS_APPROVED) &&
			(status != WorkflowConstants.STATUS_DRAFT)) {

			throw new IllegalArgumentException(
				"Change status value is invalid");
		}

		CTEntry ctEntry = ctEntryPersistence.fetchByPrimaryKey(ctEntryId);

		ctEntry.setStatus(status);

		return ctEntryPersistence.update(ctEntry);
	}

	private CTEntry _addCTEntry(
		User user, long modelClassNameId, long modelClassPK,
		long modelResourcePrimKey, int changeType, long ctCollectionId,
		ServiceContext serviceContext) {

		long ctEntryId = counterLocalService.increment();

		CTEntry ctEntry = ctEntryPersistence.create(ctEntryId);

		ctEntry.setCompanyId(user.getCompanyId());
		ctEntry.setUserId(user.getUserId());
		ctEntry.setUserName(user.getFullName());

		Date now = new Date();

		ctEntry.setCreateDate(serviceContext.getCreateDate(now));
		ctEntry.setModifiedDate(serviceContext.getModifiedDate(now));

		ctEntry.setOriginalCTCollectionId(ctCollectionId);
		ctEntry.setModelClassNameId(modelClassNameId);
		ctEntry.setModelClassPK(modelClassPK);
		ctEntry.setModelResourcePrimKey(modelResourcePrimKey);
		ctEntry.setChangeType(changeType);

		int status = WorkflowConstants.STATUS_DRAFT;

		if (_isProductionCTCollectionId(ctCollectionId)) {
			status = WorkflowConstants.STATUS_APPROVED;
		}

		ctEntry.setStatus(status);

		ctEntry = ctEntryPersistence.update(ctEntry);

		_ctCollectionLocalService.addCTEntryCTCollection(
			ctEntry.getCtEntryId(), ctCollectionId);

		return ctEntry;
	}

	private Query _buildQuery(
		CTCollection ctCollection, long[] groupIds, long[] userIds,
		long[] classNameIds, int[] changeTypes, Boolean collision, int status,
		boolean excludeStatus) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addFilterQueryClauses(
			_queries.term("ctCollectionId", ctCollection.getCtCollectionId()));
		booleanQuery.addFilterQueryClauses(
			_queries.term(Field.COMPANY_ID, ctCollection.getCompanyId()));

		if (!ArrayUtil.isEmpty(groupIds)) {
			booleanQuery.addMustQueryClauses(
				_getTermsQuery(
					Field.GROUP_ID,
					_getTermValues(ArrayUtil.toArray(groupIds))));
		}

		if (!ArrayUtil.isEmpty(userIds)) {
			booleanQuery.addMustQueryClauses(
				_getTermsQuery(
					Field.USER_ID, _getTermValues(ArrayUtil.toArray(userIds))));
		}

		if (!ArrayUtil.isEmpty(classNameIds)) {
			booleanQuery.addMustQueryClauses(
				_getTermsQuery(
					"modelClassNameId",
					_getTermValues(ArrayUtil.toArray(classNameIds))));
		}

		if (!ArrayUtil.isEmpty(changeTypes)) {
			booleanQuery.addMustQueryClauses(
				_getTermsQuery(
					"changeType",
					_getTermValues(ArrayUtil.toArray(changeTypes))));
		}

		if (collision != null) {
			booleanQuery.addFilterQueryClauses(
				_queries.term("collision", collision));
		}

		if (WorkflowConstants.STATUS_ANY != status) {
			if (excludeStatus) {
				booleanQuery.addMustNotQueryClauses(
					_queries.term(Field.STATUS, status));
			}
			else {
				booleanQuery.addMustQueryClauses(
					_queries.term(Field.STATUS, status));
			}
		}

		return booleanQuery;
	}

	private List<CTEntry> _getCTEntries(SearchResponse searchResponse) {
		Stream<Document> documentsStream = searchResponse.getDocumentsStream();

		return documentsStream.map(
			document -> document.getLong(Field.ENTRY_CLASS_PK)
		).map(
			ctEntryLocalService::fetchCTEntry
		).filter(
			Objects::nonNull
		).collect(
			Collectors.toList()
		);
	}

	private Sort[] _getSorts(QueryDefinition<CTEntry> queryDefinition) {
		if (queryDefinition == null) {
			return new Sort[0];
		}

		OrderByComparator<CTEntry> orderByComparator =
			queryDefinition.getOrderByComparator();

		if (orderByComparator == null) {
			return new Sort[0];
		}

		Stream<String> stream = Arrays.stream(
			orderByComparator.getOrderByFields());

		return stream.map(
			orderByCol -> {
				if (orderByCol.equals(Field.CREATE_DATE) ||
					orderByCol.equals(Field.MODIFIED_DATE) ||
					orderByCol.equals(Field.TITLE)) {

					orderByCol = Field.getSortableFieldName(orderByCol);
				}

				SortOrder sortOrder = SortOrder.ASC;

				if (!orderByComparator.isAscending()) {
					sortOrder = SortOrder.DESC;
				}

				return _sorts.field(orderByCol, sortOrder);
			}
		).toArray(
			Sort[]::new
		);
	}

	private TermsQuery _getTermsQuery(String field, Object[] values) {
		TermsQuery termsQuery = _queries.terms(field);

		termsQuery.addValues(values);

		return termsQuery;
	}

	private String[] _getTermValues(Number[] idsArray) {
		Stream<Number> stream = Arrays.stream(idsArray);

		return stream.map(
			String::valueOf
		).toArray(
			String[]::new
		);
	}

	private boolean _isProductionCTCollectionId(long ctCollectionId) {
		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollectionId);

		if (ctCollection == null) {
			return false;
		}

		return ctCollection.isProduction();
	}

	private SearchResponse _search(
		long companyId, Query query, QueryDefinition<CTEntry> queryDefinition) {

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.getSearchRequestBuilder(
				new SearchContext());

		SearchRequest searchRequest = searchRequestBuilder.entryClassNames(
			CTEntry.class.getName()
		).query(
			query
		).modelIndexerClasses(
			CTEntry.class
		).sorts(
			_getSorts(queryDefinition)
		).withSearchContext(
			searchContext -> {
				searchContext.setCompanyId(companyId);
				searchContext.setEnd(queryDefinition.getEnd());
				searchContext.setStart(queryDefinition.getStart());
			}
		).build();

		return _searcher.search(searchRequest);
	}

	private long _searchCount(long companyId, Query query) {
		Indexer<CTEntry> indexer = _indexerRegistry.getIndexer(CTEntry.class);

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute("search.request.query", query);
		searchContext.setCompanyId(companyId);
		searchContext.setEntryClassNames(
			new String[] {CTEntry.class.getName()});
		searchContext.setSorts();

		try {
			return indexer.searchCount(searchContext);
		}
		catch (SearchException se) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to execute change entries count search", se);
			}

			return 0L;
		}
	}

	private CTEntry _updateCTEntry(
		CTEntry ctEntry, User user, int changeType,
		ServiceContext serviceContext) {

		ctEntry.setUserId(user.getUserId());
		ctEntry.setUserName(user.getFullName());

		ctEntry.setModifiedDate(serviceContext.getModifiedDate(new Date()));
		ctEntry.setChangeType(changeType);

		return ctEntryPersistence.update(ctEntry);
	}

	private void _validate(
			CTEntry ctEntry, int changeType, long ctCollectionId, boolean force)
		throws PortalException {

		if (!force && (ctEntry != null)) {
			throw new DuplicateCTEntryException();
		}

		_ctCollectionLocalService.getCTCollection(ctCollectionId);

		if ((changeType != CTConstants.CT_CHANGE_TYPE_ADDITION) &&
			(changeType != CTConstants.CT_CHANGE_TYPE_DELETION) &&
			(changeType != CTConstants.CT_CHANGE_TYPE_MODIFICATION)) {

			throw new IllegalArgumentException("Change type value is invalid");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTEntryLocalServiceImpl.class);

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private Queries _queries;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private Sorts _sorts;

}