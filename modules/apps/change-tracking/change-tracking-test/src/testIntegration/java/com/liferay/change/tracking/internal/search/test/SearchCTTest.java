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

package com.liferay.change.tracking.internal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.model.uid.UIDFactory;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class SearchCTTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		CTCollection ctCollection = addCTCollection();

		Group group = GroupTestUtil.addGroup();

		_ctCollection = ctCollection;
		_group = group;
	}

	@Test
	public void testCollectionVersusProduction() throws Exception {
		JournalArticle addJournalArticle = null;

		JournalArticle delJournalArticle = addJournalArticle();
		JournalArticle modJournalArticle = addJournalArticle();

		Layout addLayout = null;

		Layout delLayout = addLayout();
		Layout modLayout = addLayout();

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			addJournalArticle = addJournalArticle();
			delJournalArticle = deleteJournalArticle(delJournalArticle);
			modJournalArticle = modifyJournalArticle(modJournalArticle);

			addLayout = addLayout();
			delLayout = deleteLayout(delLayout);
			modLayout = modifyLayout(modLayout);
		}

		assertCollectionHits(
			CTConstants.CT_COLLECTION_ID_PRODUCTION, _LEGACY_INDEXER_CLASSES,
			delJournalArticle, modJournalArticle);

		assertCollectionHits(
			CTConstants.CT_COLLECTION_ID_PRODUCTION, _NEW_INDEXER_CLASSES,
			delLayout, modLayout);

		assertCollectionHits(
			CTConstants.CT_COLLECTION_ID_PRODUCTION, _ALL_INDEXER_CLASSES,
			delJournalArticle, delLayout, modJournalArticle, modLayout);

		assertCollectionHits(
			_ctCollection.getCtCollectionId(), _LEGACY_INDEXER_CLASSES,
			addJournalArticle, modJournalArticle);

		assertCollectionHits(
			_ctCollection.getCtCollectionId(), _NEW_INDEXER_CLASSES, addLayout,
			modLayout);

		assertCollectionHits(
			_ctCollection.getCtCollectionId(), _ALL_INDEXER_CLASSES,
			addJournalArticle, addLayout, modJournalArticle, modLayout);

		assertAllHits(
			_ALL_INDEXER_CLASSES,
			getUIDList(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, delJournalArticle,
				delLayout, modJournalArticle, modLayout),
			getUIDList(
				_ctCollection.getCtCollectionId(), addJournalArticle, addLayout,
				modJournalArticle, modLayout));
	}

	@Test
	public void testPublishAndUndo() throws Exception {
		Layout addLayout = null;

		Layout delLayout = addLayout();
		Layout modLayout = addLayout();

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			addLayout = addLayout();
			delLayout = deleteLayout(delLayout);
			modLayout = modifyLayout(modLayout);
		}

		assertCollectionHits(
			CTConstants.CT_COLLECTION_ID_PRODUCTION, _NEW_INDEXER_CLASSES,
			delLayout, modLayout);

		publish(_ctCollection);

		assertCollectionHits(
			CTConstants.CT_COLLECTION_ID_PRODUCTION, _NEW_INDEXER_CLASSES,
			addLayout, modLayout);

		assertAllHits(
			_NEW_INDEXER_CLASSES,
			getUIDList(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, addLayout, modLayout),
			getUIDList(
				_ctCollection.getCtCollectionId(), addLayout, modLayout));

		_undoCTCollection = undo(_ctCollection);

		assertCollectionHits(
			CTConstants.CT_COLLECTION_ID_PRODUCTION, _NEW_INDEXER_CLASSES,
			addLayout, modLayout);

		assertCollectionHits(
			_undoCTCollection.getCtCollectionId(), _NEW_INDEXER_CLASSES,
			delLayout, modLayout);

		assertAllHits(
			_NEW_INDEXER_CLASSES,
			getUIDList(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, addLayout, modLayout),
			getUIDList(_ctCollection.getCtCollectionId(), addLayout, modLayout),
			getUIDList(
				_undoCTCollection.getCtCollectionId(), delLayout, modLayout));
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected CTCollection addCTCollection() throws Exception {
		long ctCollectionId = _counterLocalService.increment(
			CTCollection.class.getName());

		CTCollection ctCollection =
			_ctCollectionLocalService.createCTCollection(ctCollectionId);

		ctCollection.setUserId(TestPropsValues.getUserId());
		ctCollection.setName(String.valueOf(ctCollectionId));
		ctCollection.setStatus(WorkflowConstants.STATUS_DRAFT);

		return _ctCollectionLocalService.updateCTCollection(ctCollection);
	}

	protected JournalArticle addJournalArticle() throws Exception {
		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		_journalArticles.add(journalArticle);

		return journalArticle;
	}

	protected Layout addLayout() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_group);

		_layouts.add(layout);

		return layout;
	}

	protected void assertAllHits(Class<?>[] classes, List<String>... uidLists) {
		SearchRequestBuilder searchRequestBuilder = getSearchRequestBuilder(
			classes);

		assertHits(
			_searcher.search(
				searchRequestBuilder.withSearchContext(
					searchContext -> searchContext.setAttribute(
						"com.liferay.change.tracking.filter.ctCollectionId",
						"ALL")
				).build()),
			ListUtil.concat(uidLists));
	}

	protected void assertCollectionHits(
		long ctCollectionId, Class<?>[] classes, CTModel... ctModels) {

		SearchRequestBuilder searchRequestBuilder = getSearchRequestBuilder(
			classes);

		assertHits(
			search(ctCollectionId, searchRequestBuilder.build()),
			getUIDList(ctCollectionId, ctModels));
	}

	protected void assertHits(
		SearchResponse searchResponse, List<String> uids) {

		DocumentsAssert.assertValuesIgnoreRelevance(
			searchResponse.getRequestString(),
			searchResponse.getDocumentsStream(), Field.UID, uids.stream());
	}

	protected JournalArticle deleteJournalArticle(JournalArticle journalArticle)
		throws Exception {

		return _journalArticleLocalService.deleteJournalArticle(journalArticle);
	}

	protected Layout deleteLayout(Layout layout) throws Exception {
		return _layoutLocalService.deleteLayout(layout);
	}

	protected String getClassName(CTModel<?> ctModel) {
		if (ctModel instanceof Layout) {
			return Layout.class.getName();
		}

		if (ctModel instanceof JournalArticle) {
			return JournalArticle.class.getName();
		}

		throw new IllegalStateException();
	}

	protected String[] getClassNames(Class<?>... classes) {
		return Stream.of(
			classes
		).map(
			Class::getName
		).toArray(
			String[]::new
		);
	}

	protected SearchRequestBuilder getSearchRequestBuilder(
		Class<?>... classes) {

		return _searchRequestBuilderFactory.builder(
		).companyId(
			_group.getCompanyId()
		).emptySearchEnabled(
			true
		).entryClassNames(
			getClassNames(classes)
		).groupIds(
			_group.getGroupId()
		).modelIndexerClasses(
			classes
		).withSearchContext(
			searchContext -> {
				searchContext.setAttribute(Field.GROUP_ID, _group.getGroupId());
				searchContext.setAttribute(
					Field.TYPE, new String[] {LayoutConstants.TYPE_PORTLET});
				searchContext.setUserId(_group.getCreatorUserId());
			}
		);
	}

	protected String getUID(long ctCollectionId, CTModel<?> ctModel) {
		return _uidFactory.getUID(
			getClassName(ctModel), ctModel.getPrimaryKey(), ctCollectionId);
	}

	protected List<String> getUIDList(
		long ctCollectionId, CTModel<?>... ctModels) {

		return Stream.of(
			ctModels
		).map(
			ctModel -> getUID(ctCollectionId, ctModel)
		).collect(
			Collectors.toList()
		);
	}

	protected JournalArticle modifyJournalArticle(
		JournalArticle journalArticle) {

		journalArticle.setTitle("testModifyJournalArticle");

		return _journalArticleLocalService.updateJournalArticle(journalArticle);
	}

	protected Layout modifyLayout(Layout layout) {
		layout.setFriendlyURL("/testModifyLayout");

		return _layoutLocalService.updateLayout(layout);
	}

	protected void publish(CTCollection ctCollection) throws Exception {
		_ctProcessLocalService.addCTProcess(
			ctCollection.getUserId(), ctCollection.getCtCollectionId());
	}

	protected SearchResponse search(
		long ctCollectionId, SearchRequest searchRequest) {

		if (ctCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			return _searcher.search(searchRequest);
		}

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			return _searcher.search(searchRequest);
		}
	}

	protected CTCollection undo(CTCollection ctCollection) throws Exception {
		return _ctCollectionLocalService.undoCTCollection(
			ctCollection.getCtCollectionId(), ctCollection.getUserId(),
			"(undo) " + ctCollection.getName(), StringPool.BLANK);
	}

	private static final Class<?>[] _ALL_INDEXER_CLASSES = {
		JournalArticle.class, Layout.class
	};

	private static final Class<?>[] _LEGACY_INDEXER_CLASSES = {
		JournalArticle.class
	};

	private static final Class<?>[] _NEW_INDEXER_CLASSES = {Layout.class};

	@Inject
	private static CounterLocalService _counterLocalService;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTEntryLocalService _ctEntryLocalService;

	@Inject
	private static CTProcessLocalService _ctProcessLocalService;

	@Inject
	private static JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private static LayoutLocalService _layoutLocalService;

	@Inject
	private static Searcher _searcher;

	@Inject
	private static SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Inject
	private static UIDFactory _uidFactory;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@DeleteAfterTestRun
	private Group _group;

	private final List<JournalArticle> _journalArticles = new ArrayList<>();
	private final List<Layout> _layouts = new ArrayList<>();

	@DeleteAfterTestRun
	private CTCollection _undoCTCollection;

}