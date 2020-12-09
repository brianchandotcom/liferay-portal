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
import com.liferay.change.tracking.service.CTProcessLocalService;
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.model.uid.UIDFactory;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

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
		_group = GroupTestUtil.addGroup();
		_ctCollection = _ctCollectionLocalService.addCTCollection(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			SearchCTTest.class.getName(), SearchCTTest.class.getName());
	}

	@Test
	public void testCollectionVersusProduction() throws Exception {
		JournalArticle addedJournalArticle = null;

		JournalArticle deletedJournalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		JournalArticle modifiedJournalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		JournalArticle modifiedJournalArticle2 = null;

		Layout addedLayout = null;

		Layout deletedLayout = LayoutTestUtil.addLayout(_group);
		Layout modifiedLayout = LayoutTestUtil.addLayout(_group);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			addedJournalArticle = JournalTestUtil.addArticle(
				_group.getGroupId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString());

			deletedJournalArticle = _journalArticleLocalService.deleteArticle(
				deletedJournalArticle);

			modifiedJournalArticle2 = JournalTestUtil.updateArticle(
				modifiedJournalArticle1, "testModifyJournalArticle");

			addedLayout = LayoutTestUtil.addLayout(_group);

			deletedLayout = _layoutLocalService.deleteLayout(deletedLayout);

			modifiedLayout.setFriendlyURL("/testModifyLayout");

			modifiedLayout = _layoutLocalService.updateLayout(modifiedLayout);
		}

		assertCollectionHits(
			CTConstants.CT_COLLECTION_ID_PRODUCTION, _LEGACY_INDEXER_CLASSES,
			deletedJournalArticle, modifiedJournalArticle1);

		assertCollectionHits(
			_ctCollection.getCtCollectionId(), _LEGACY_INDEXER_CLASSES,
			addedJournalArticle, modifiedJournalArticle2);

		assertCollectionHits(
			CTConstants.CT_COLLECTION_ID_PRODUCTION, _NEW_INDEXER_CLASSES,
			deletedLayout, modifiedLayout);

		assertCollectionHits(
			_ctCollection.getCtCollectionId(), _NEW_INDEXER_CLASSES,
			addedLayout, modifiedLayout);

		assertAllHits(
			_ALL_INDEXER_CLASSES,
			getUIDs(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, deletedJournalArticle,
				deletedLayout, modifiedJournalArticle1, modifiedLayout),
			getUIDs(
				_ctCollection.getCtCollectionId(), addedJournalArticle,
				addedLayout, modifiedJournalArticle2, modifiedLayout));
	}

	@Test
	public void testPublishAndUndoArticle() throws Exception {
		JournalArticle addedJournalArticle = null;

		JournalArticle deletedJournalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		JournalArticle modifiedJournalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		JournalArticle modifiedJournalArticle2 = null;

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			addedJournalArticle = JournalTestUtil.addArticle(
				_group.getGroupId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString());

			deletedJournalArticle = _journalArticleLocalService.deleteArticle(
				deletedJournalArticle);

			modifiedJournalArticle2 = JournalTestUtil.updateArticle(
				modifiedJournalArticle1, "testModifyJournalArticle");
		}

		assertCollectionHits(
			CTConstants.CT_COLLECTION_ID_PRODUCTION, _LEGACY_INDEXER_CLASSES,
			deletedJournalArticle, modifiedJournalArticle1);

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		assertCollectionHits(
			CTConstants.CT_COLLECTION_ID_PRODUCTION, _LEGACY_INDEXER_CLASSES,
			addedJournalArticle, modifiedJournalArticle2);

		_undoCTCollection = _ctCollectionLocalService.undoCTCollection(
			_ctCollection.getCtCollectionId(), _ctCollection.getUserId(),
			"(undo) " + _ctCollection.getName(), StringPool.BLANK);

		assertCollectionHits(
			CTConstants.CT_COLLECTION_ID_PRODUCTION, _LEGACY_INDEXER_CLASSES,
			addedJournalArticle, modifiedJournalArticle2);
	}

	@Test
	public void testPublishAndUndoLayout() throws Exception {
		Layout addedLayout = null;

		Layout deletedLayout = LayoutTestUtil.addLayout(_group);
		Layout modifiedLayout = LayoutTestUtil.addLayout(_group);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			addedLayout = LayoutTestUtil.addLayout(_group);
			deletedLayout = _layoutLocalService.deleteLayout(deletedLayout);
			modifiedLayout.setFriendlyURL("/testModifyLayout");

			modifiedLayout = _layoutLocalService.updateLayout(modifiedLayout);
		}

		assertCollectionHits(
			CTConstants.CT_COLLECTION_ID_PRODUCTION, _NEW_INDEXER_CLASSES,
			deletedLayout, modifiedLayout);

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		assertCollectionHits(
			CTConstants.CT_COLLECTION_ID_PRODUCTION, _NEW_INDEXER_CLASSES,
			addedLayout, modifiedLayout);

		assertAllHits(
			_NEW_INDEXER_CLASSES,
			getUIDs(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, addedLayout,
				modifiedLayout),
			getUIDs(
				_ctCollection.getCtCollectionId(), addedLayout,
				modifiedLayout));

		_undoCTCollection = _ctCollectionLocalService.undoCTCollection(
			_ctCollection.getCtCollectionId(), _ctCollection.getUserId(),
			"(undo) " + _ctCollection.getName(), StringPool.BLANK);

		assertCollectionHits(
			CTConstants.CT_COLLECTION_ID_PRODUCTION, _NEW_INDEXER_CLASSES,
			addedLayout, modifiedLayout);

		assertCollectionHits(
			_undoCTCollection.getCtCollectionId(), _NEW_INDEXER_CLASSES,
			deletedLayout, modifiedLayout);

		assertAllHits(
			_NEW_INDEXER_CLASSES,
			getUIDs(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, addedLayout,
				modifiedLayout),
			getUIDs(
				_ctCollection.getCtCollectionId(), addedLayout, modifiedLayout),
			getUIDs(
				_undoCTCollection.getCtCollectionId(), deletedLayout,
				modifiedLayout));
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void assertAllHits(Class<?>[] classes, String[]... uids) {
		assertHits(
			CTConstants.CT_COLLECTION_ID_PRODUCTION, classes,
			ArrayUtil.append(uids), true);
	}

	protected void assertCollectionHits(
		long ctCollectionId, Class<?>[] classes, CTModel<?>... ctModels) {

		assertHits(
			ctCollectionId, classes, getUIDs(ctCollectionId, ctModels), false);
	}

	protected void assertHits(
		long ctCollectionId, Class<?>[] classes, String[] uids, boolean all) {

		String[] classNames = new String[classes.length];

		for (int i = 0; i < classes.length; i++) {
			classNames[i] = classes[i].getName();
		}

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(
			).companyId(
				_group.getCompanyId()
			).emptySearchEnabled(
				true
			).entryClassNames(
				classNames
			).groupIds(
				_group.getGroupId()
			).modelIndexerClasses(
				classes
			).withSearchContext(
				searchContext -> {
					if (all) {
						searchContext.setAttribute(
							"com.liferay.change.tracking.filter.ctCollectionId",
							"ALL");
					}

					searchContext.setAttribute(
						Field.GROUP_ID, _group.getGroupId());
					searchContext.setAttribute(
						Field.TYPE,
						new String[] {LayoutConstants.TYPE_PORTLET});
					searchContext.setUserId(_group.getCreatorUserId());
				}
			);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			SearchResponse searchResponse = _searcher.search(
				searchRequestBuilder.build());

			DocumentsAssert.assertValuesIgnoreRelevance(
				searchResponse.getRequestString(),
				searchResponse.getDocumentsStream(), Field.UID,
				Stream.of(uids));
		}
	}

	protected String[] getUIDs(long ctCollectionId, CTModel<?>... ctModels) {
		String[] uids = new String[ctModels.length];

		for (int i = 0; i < ctModels.length; i++) {
			uids[i] = _uidFactory.getUID(
				ctModels[i].getModelClassName(), ctModels[i].getPrimaryKey(),
				ctCollectionId);
		}

		return uids;
	}

	private static final Class<?>[] _ALL_INDEXER_CLASSES = {
		JournalArticle.class, Layout.class
	};

	private static final Class<?>[] _LEGACY_INDEXER_CLASSES = {
		JournalArticle.class
	};

	private static final Class<?>[] _NEW_INDEXER_CLASSES = {Layout.class};

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

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

	@DeleteAfterTestRun
	private CTCollection _undoCTCollection;

}