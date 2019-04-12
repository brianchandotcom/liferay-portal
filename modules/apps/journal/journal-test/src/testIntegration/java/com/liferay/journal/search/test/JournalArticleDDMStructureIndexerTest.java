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

package com.liferay.journal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.test.util.background.task.DDMStructureBackgroundTask;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Fabiano Nazar
 * @author Lucas Marques de Paula
 */
@RunWith(Arquillian.class)
public class JournalArticleDDMStructureIndexerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.JAPAN);

		setUpUserSearchFixture();
		setUpJournalArticleDDMStructureFixture();
		setUpJournalArticleIndexerFixture();
	}

	@After
	public void tearDown() throws Exception {
		enableJournalArticleIndexer();
	}

	@Test
	public void testDisableJournalArticleIndexer()
		throws Exception, PortalException {

		Locale locale = LocaleUtil.JAPAN;
		String searchTerm = "新規";
		String title = "新規作成";

		DDMStructure structure =
			structureFixture.createStructureWithJournalArticle(title, locale);

		Document document = journalArticleIndexer.searchOnlyOne(
			searchTerm, locale);

		journalArticleIndexer.deleteDocument(document);

		disableJournalArticleIndexer();

		runBackgroundTaskReindex(structure);

		journalArticleIndexer.searchNoOne(searchTerm, locale);
	}

	@Test
	public void testReindexJournalArticle() throws Exception, PortalException {
		Locale locale = LocaleUtil.JAPAN;
		String searchTerm = "新規";
		String title = "新規作成";

		DDMStructure structure =
			structureFixture.createStructureWithJournalArticle(title, locale);

		Document document = journalArticleIndexer.searchOnlyOne(
			searchTerm, locale);

		journalArticleIndexer.deleteDocument(document);

		runBackgroundTaskReindex(structure);

		journalArticleIndexer.searchOnlyOne(searchTerm, locale);
	}

	protected void disableJournalArticleIndexer() {
		Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
			JournalArticle.class);

		indexer.setIndexerEnabled(false);
	}

	protected void enableJournalArticleIndexer() {
		Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
			JournalArticle.class);

		indexer.setIndexerEnabled(true);
	}

	protected void runBackgroundTaskReindex(DDMStructure structure)
		throws Exception {

		DDMStructureBackgroundTask backgroundTask =
			new DDMStructureBackgroundTask(structure.getStructureId());

		backgroundTaskExecutor.execute(backgroundTask);
	}

	protected void setUpJournalArticleDDMStructureFixture() throws Exception {
		structureFixture = new JournalArticleDDMStructureFixture(
			group, journalArticleLocalService);

		ddmStructures = structureFixture.getStructures();

		ddmTemplates = structureFixture.getTemplates();
	}

	protected void setUpJournalArticleIndexerFixture() {
		journalArticleIndexer = new IndexerFixture<>(JournalArticle.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		UserSearchFixture userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		group = userSearchFixture.addGroup();

		groups = userSearchFixture.getGroups();
		users = userSearchFixture.getUsers();
	}

	@Inject
	protected static JournalArticleLocalService journalArticleLocalService;

	@Inject(
		filter = "background.task.executor.class.name=com.liferay.dynamic.data.mapping.background.task.DDMStructureIndexerBackgroundTaskExecutor"
	)
	protected BackgroundTaskExecutor backgroundTaskExecutor;

	@Inject(filter = "ddm.form.deserializer.type=json")
	protected DDMFormDeserializer ddmFormDeserializer;

	@DeleteAfterTestRun
	protected List<DDMStructure> ddmStructures;

	@DeleteAfterTestRun
	protected List<DDMTemplate> ddmTemplates;

	protected Group group;

	@DeleteAfterTestRun
	protected List<Group> groups;

	protected IndexerFixture<JournalArticle> journalArticleIndexer;
	protected JournalArticleDDMStructureFixture structureFixture;

	@DeleteAfterTestRun
	protected List<User> users;

}