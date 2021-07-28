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

package com.liferay.batch.engine.internal.test;

import com.liferay.batch.engine.BaseBatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.CollectionEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Ivica Cardic
 */
public class BaseBatchEngineTaskExecutorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();

		user = UserTestUtil.addGroupAdminUser(group);

		baseDate = dateFormat.parse(dateFormat.format(new Date()));

		Bundle bundle = FrameworkUtil.getBundle(
			BatchEngineImportTaskExecutorTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_batchEngineTaskItemDelegateRegistration =
			bundleContext.registerService(
				BatchEngineTaskItemDelegate.class.getName(),
				new TestBlogPostingBatchEngineTaskItemDelegate(),
				new HashMapDictionary<String, String>());
	}

	@After
	public void tearDown() throws Exception {
		blogsEntryLocalService.deleteEntries(group.getGroupId());

		_batchEngineTaskItemDelegateRegistration.unregister();
	}

	public static class BlogPostingEntityModel implements EntityModel {

		public BlogPostingEntityModel() {
			_entityFieldsMap = EntityModel.toEntityFieldsMap(
				new CollectionEntityField(
					new StringEntityField(
						"keywords", locale -> "assetTagNames.raw")),
				new CollectionEntityField(
					new IntegerEntityField(
						"taxonomyCategoryIds", locale -> "assetCategoryIds")),
				new DateTimeEntityField(
					"dateCreated",
					locale -> Field.getSortableFieldName(Field.CREATE_DATE),
					locale -> Field.CREATE_DATE),
				new DateTimeEntityField(
					"dateModified",
					locale -> Field.getSortableFieldName(Field.MODIFIED_DATE),
					locale -> Field.MODIFIED_DATE),
				new IntegerEntityField("creatorId", locale -> Field.USER_ID),
				new StringEntityField(
					"headline",
					locale -> Field.getSortableFieldName(Field.TITLE)));
		}

		@Override
		public Map<String, EntityField> getEntityFieldsMap() {
			return _entityFieldsMap;
		}

		@Override
		public String getName() {
			return "com_liferay_batch_engine_internal_test_" +
				"BlogPostingEntityModel";
		}

		private final Map<String, EntityField> _entityFieldsMap;

	}

	public class TestBlogPostingBatchEngineTaskItemDelegate
		extends BaseBatchEngineTaskItemDelegate<BlogPosting> {

		@Override
		public void createItem(
				BlogPosting blogPosting,
				Map<String, Serializable> queryParameters)
			throws Exception {

			LocalDateTime localDateTime = _toLocalDateTime(
				blogPosting.getDatePublished());

			_blogsEntryService.addEntry(
				null, blogPosting.getHeadline(),
				blogPosting.getAlternativeHeadline(),
				blogPosting.getFriendlyUrlPath(), blogPosting.getDescription(),
				blogPosting.getArticleBody(), localDateTime.getMonthValue() - 1,
				localDateTime.getDayOfMonth(), localDateTime.getYear(),
				localDateTime.getHour(), localDateTime.getMinute(), true, true,
				new String[0], null, new ImageSelector(), null,
				_createServiceContext(blogPosting.getSiteId()));
		}

		@Override
		public void deleteItem(
				BlogPosting blogPosting,
				Map<String, Serializable> queryParameters)
			throws Exception {

			_blogsEntryService.deleteEntry(blogPosting.getId());
		}

		@Override
		public EntityModel getEntityModel(
				Map<String, List<String>> multivaluedMap)
			throws Exception {

			return new BlogPostingEntityModel();
		}

		@Override
		public Page<BlogPosting> read(
				Filter filter, Pagination pagination, Sort[] sorts,
				Map<String, Serializable> parameters, String search)
			throws Exception {

			long siteId = GetterUtil.getLong(parameters.get("siteId"));

			return _search(
				booleanQuery -> {
				},
				filter, search, pagination,
				queryConfig -> queryConfig.setSelectedFieldNames(
					Field.ENTRY_CLASS_PK),
				searchContext -> {
					searchContext.setAttribute(
						Field.STATUS, WorkflowConstants.STATUS_APPROVED);
					searchContext.setCompanyId(contextCompany.getCompanyId());
					searchContext.setGroupIds(new long[] {siteId});
				},
				sorts,
				document -> _toBlogPosting(
					_blogsEntryService.getEntry(
						GetterUtil.getLong(
							document.get(Field.ENTRY_CLASS_PK)))));
		}

		@Override
		public void updateItem(
				BlogPosting blogPosting, Map<String, Serializable> parameters)
			throws Exception {

			LocalDateTime localDateTime = _toLocalDateTime(
				blogPosting.getDatePublished());

			BlogsEntry blogsEntry = _blogsEntryService.getEntry(
				blogPosting.getId());

			_blogsEntryService.updateEntry(
				blogPosting.getId(), blogPosting.getHeadline(),
				blogPosting.getAlternativeHeadline(),
				blogPosting.getFriendlyUrlPath(), blogPosting.getDescription(),
				blogPosting.getArticleBody(), localDateTime.getMonthValue() - 1,
				localDateTime.getDayOfMonth(), localDateTime.getYear(),
				localDateTime.getHour(), localDateTime.getMinute(), true, true,
				new String[0], null, new ImageSelector(), null,
				_createServiceContext(blogsEntry.getGroupId()));
		}

		private SearchContext _createSearchContext(
				BooleanClause<?> booleanClause, String keywords,
				Pagination pagination,
				UnsafeConsumer<QueryConfig, Exception>
					queryConfigUnsafeConsumer,
				Sort[] sorts)
			throws Exception {

			SearchContext searchContext = new SearchContext();

			QueryConfig queryConfig = searchContext.getQueryConfig();

			queryConfig.setHighlightEnabled(false);
			queryConfig.setScoreEnabled(false);

			queryConfigUnsafeConsumer.accept(queryConfig);

			searchContext.setBooleanClauses(
				new BooleanClause[] {booleanClause});

			if (pagination != null) {
				searchContext.setEnd(pagination.getEndPosition());
			}

			searchContext.setKeywords(keywords);
			searchContext.setSorts(sorts);

			if (pagination != null) {
				searchContext.setStart(pagination.getStartPosition());
			}

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			searchContext.setUserId(permissionChecker.getUserId());

			return searchContext;
		}

		private ServiceContext _createServiceContext(Long groupId) {
			ServiceContext serviceContext = new ServiceContext();

			if (groupId != null) {
				serviceContext.setScopeGroupId(groupId);
			}

			return serviceContext;
		}

		private BooleanClause<?> _getBooleanClause(
				UnsafeConsumer<BooleanQuery, Exception>
					booleanQueryUnsafeConsumer,
				Filter filter)
			throws Exception {

			BooleanQuery booleanQuery = new BooleanQueryImpl();

			booleanQuery.add(new MatchAllQuery(), BooleanClauseOccur.MUST);

			BooleanFilter booleanFilter = new BooleanFilter();

			if (filter != null) {
				booleanFilter.add(filter, BooleanClauseOccur.MUST);
			}

			booleanQuery.setPreBooleanFilter(booleanFilter);

			booleanQueryUnsafeConsumer.accept(booleanQuery);

			return BooleanClauseFactoryUtil.create(
				booleanQuery, BooleanClauseOccur.MUST.getName());
		}

		private Page<BlogPosting> _search(
				UnsafeConsumer<BooleanQuery, Exception>
					booleanQueryUnsafeConsumer,
				Filter filter, String keywords, Pagination pagination,
				UnsafeConsumer<QueryConfig, Exception>
					queryConfigUnsafeConsumer,
				UnsafeConsumer<SearchContext, Exception>
					searchContextUnsafeConsumer,
				Sort[] sorts,
				UnsafeFunction<Document, BlogPosting, Exception>
					transformUnsafeFunction)
			throws Exception {

			if (sorts == null) {
				sorts = new Sort[] {
					new Sort(Field.ENTRY_CLASS_PK, Sort.LONG_TYPE, false)
				};
			}

			List<BlogPosting> items = new ArrayList<>();

			Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
				(Class<?>)BlogsEntry.class);

			SearchContext searchContext = _createSearchContext(
				_getBooleanClause(booleanQueryUnsafeConsumer, filter), keywords,
				pagination, queryConfigUnsafeConsumer, sorts);

			searchContextUnsafeConsumer.accept(searchContext);

			Hits hits = indexer.search(searchContext);

			for (Document document : hits.getDocs()) {
				BlogPosting item = transformUnsafeFunction.apply(document);

				if (item != null) {
					items.add(item);
				}
			}

			return Page.of(
				items, pagination, indexer.searchCount(searchContext));
		}

		private BlogPosting _toBlogPosting(BlogsEntry blogsEntry) {
			BlogPosting blogPosting = new BlogPosting();

			blogPosting.setAlternativeHeadline(blogsEntry.getSubtitle());
			blogPosting.setArticleBody(blogsEntry.getContent());
			blogPosting.setDateCreated(blogsEntry.getCreateDate());
			blogPosting.setDateModified(blogsEntry.getModifiedDate());
			blogPosting.setDatePublished(blogsEntry.getDisplayDate());
			blogPosting.setDescription(blogsEntry.getDescription());
			blogPosting.setEncodingFormat("text/html");
			blogPosting.setFriendlyUrlPath(blogsEntry.getUrlTitle());
			blogPosting.setHeadline(blogsEntry.getTitle());
			blogPosting.setId(blogsEntry.getEntryId());
			blogPosting.setSiteId(blogsEntry.getGroupId());

			return blogPosting;
		}

		private LocalDateTime _toLocalDateTime(Date date) {
			Instant instant = date.toInstant();

			ZonedDateTime zonedDateTime = instant.atZone(
				ZoneId.systemDefault());

			return zonedDateTime.toLocalDateTime();
		}

	}

	protected List<BlogsEntry> addBlogsEntries() throws Exception {
		List<BlogsEntry> blogsEntries = new ArrayList<>();

		for (int i = 0; i < ROWS_COUNT; i++) {
			blogsEntries.add(
				blogsEntryLocalService.addEntry(
					user.getUserId(), "headline" + i, "alternativeHeadline" + i,
					null, "articleBody" + i, new Date(baseDate.getTime()),
					false, false, null, null, null, null,
					ServiceContextTestUtil.getServiceContext(
						user.getCompanyId(), group.getGroupId(),
						user.getUserId())));
		}

		return blogsEntries;
	}

	protected static final String[] FIELD_NAMES = {
		"alternativeHeadline", "articleBody", "datePublished", "headline",
		"siteId"
	};

	protected static final int ROWS_COUNT = 18;

	protected Date baseDate;

	@Inject
	protected BlogsEntryLocalService blogsEntryLocalService;

	protected final DateFormat dateFormat = new SimpleDateFormat(
		"yyyy-MM-dd'T'HH:mm:00.000XXX");

	@DeleteAfterTestRun
	protected Group group;

	@DeleteAfterTestRun
	protected User user;

	private ServiceRegistration<?> _batchEngineTaskItemDelegateRegistration;

	@Inject
	private BlogsEntryService _blogsEntryService;

}