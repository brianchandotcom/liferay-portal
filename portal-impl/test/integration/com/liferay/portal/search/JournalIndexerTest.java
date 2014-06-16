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

package com.liferay.portal.search;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.SearchContextTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portal.util.test.UserTestUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMTemplateTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.portlet.journal.util.test.JournalTestUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class JournalIndexerTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddArticleApprove() throws Exception {
		addArticle(true);
	}

	@Test
	public void testAddArticleNotApprove() throws Exception {
		addArticle(false);
	}

	@Test
	public void testCopyArticle() throws Exception {
		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext.setKeywords("Architectural");

		int initialSearchCount = searchCount(
			_group.getGroupId(), searchContext);

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		JournalArticle article = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), folder.getFolderId(), "title",
			"Liferay Architectural Approach", true);

		Assert.assertEquals(
			initialSearchCount + 1,
			searchCount(_group.getGroupId(), searchContext));

		JournalArticleLocalServiceUtil.copyArticle(
			TestPropsValues.getUserId(), _group.getGroupId(),
			article.getArticleId(), StringPool.BLANK, true,
			article.getVersion());

		Assert.assertEquals(
			initialSearchCount + 2,
			searchCount(_group.getGroupId(), searchContext));
	}

	@Test
	public void testDeleteAllArticleVersion() throws Exception {
		articleVersions(true, true);
	}

	@Test
	public void testDeleteArticles() throws Exception {
		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext.setKeywords("Architectural");

		int initialSearchCount = searchCount(
			_group.getGroupId(), searchContext);

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		JournalArticle article1 = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), folder.getFolderId(), "title",
			"Liferay Architectural Approach", true);

		Assert.assertEquals(
			initialSearchCount + 1,
			searchCount(_group.getGroupId(), searchContext));

		String content = DDMStructureTestUtil.getSampleStructuredContent(
			"Architectural Approach");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		JournalTestUtil.updateArticle(
			article1, article1.getTitle(), content, false, true,
			serviceContext);

		JournalArticle article2 = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), folder.getFolderId(), "title",
			"Apple Architectural Tablet", true);

		Assert.assertEquals(
			initialSearchCount  + 2,
			searchCount(_group.getGroupId(), searchContext));

		content = DDMStructureTestUtil.getSampleStructuredContent(
			"Architectural Tablet");

		JournalTestUtil.updateArticle(
			article2, article2.getTitle(), content, false, true,
			serviceContext);

		JournalArticleLocalServiceUtil.deleteArticles(_group.getGroupId());

		Assert.assertEquals(
			initialSearchCount,
			searchCount(_group.getGroupId(), searchContext));
	}

	@Test
	public void testDeleteArticleVersion() throws Exception {
		articleVersions(true, false);
	}

	@Test
	public void testExpireAllArticleVersions() throws Exception {
		articleVersions(false, true);
	}

	@Test
	public void testExpireArticleVersion() throws Exception {
		articleVersions(false, false);
	}

	@Test
	public void testIndexVersions() throws Exception {
		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		int initialSearchCount = searchCount(
			_group.getGroupId(), searchContext);

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		String content = "Liferay Architectural Approach";

		JournalArticle article = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), folder.getFolderId(), "title", content, true);

		Assert.assertEquals(
			initialSearchCount  + 1,
			searchCount(
				_group.getGroupId(), false, WorkflowConstants.STATUS_ANY,
				searchContext));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		article = JournalTestUtil.updateArticle(
			article, article.getTitle(), article.getContent(), false, true,
			serviceContext);

		Assert.assertEquals(
			initialSearchCount + 2,
			searchCount(
				_group.getGroupId(), false, WorkflowConstants.STATUS_ANY,
				searchContext));

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		JournalTestUtil.updateArticle(
			article, article.getTitle(), article.getContent(), false, true,
			serviceContext);

		Assert.assertEquals(
			initialSearchCount + 3,
			searchCount(
				_group.getGroupId(), false, WorkflowConstants.STATUS_ANY,
				searchContext));
	}

	@Test
	public void testIndexVersionsDelete() throws Exception {
		indexVersions(true, false);
	}

	@Test
	public void testIndexVersionsDeleteAll() throws Exception {
		indexVersions(true, true);
	}

	@Test
	public void testIndexVersionsExpire() throws Exception {
		indexVersions(false, false);
	}

	@Test
	public void testIndexVersionsExpireAll() throws Exception {
		indexVersions(false, true);
	}

	@Test
	public void testMoveArticle() throws Exception {
		moveArticle(false);
	}

	@Test
	public void testMoveArticleFromTrash() throws Exception {
		moveArticle(true);
	}

	@Test
	public void testMoveArticleToTrashAndRestore() throws Exception {
		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext.setKeywords("Architectural");

		int initialSearchCount = searchCount(
			_group.getGroupId(), searchContext);

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		JournalArticle article = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), folder.getFolderId(), "title",
			"Liferay Architectural Approach", true);

		Assert.assertEquals(
			initialSearchCount + 1,
			searchCount(_group.getGroupId(), searchContext));

		JournalArticleLocalServiceUtil.moveArticleToTrash(
			TestPropsValues.getUserId(), article);

		Assert.assertEquals(
			initialSearchCount,
			searchCount(_group.getGroupId(), searchContext));
		Assert.assertEquals(
			initialSearchCount + 1,
			searchCount(
				_group.getGroupId(), true, WorkflowConstants.STATUS_IN_TRASH,
				searchContext));

		JournalArticleLocalServiceUtil.restoreArticleFromTrash(
			TestPropsValues.getUserId(), article);

		Assert.assertEquals(
			initialSearchCount + 1,
			searchCount(_group.getGroupId(), searchContext));
		Assert.assertEquals(
			initialSearchCount,
			searchCount(
				_group.getGroupId(), true, WorkflowConstants.STATUS_IN_TRASH,
				searchContext));
	}

	@Test
	public void testRemoveArticleLocale() throws Exception {
		SearchContext searchContext1 = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext1.setKeywords("Arquitectura");
		searchContext1.setLocale(LocaleUtil.SPAIN);

		int initialSearchCount1 = searchCount(
			_group.getGroupId(), searchContext1);

		SearchContext searchContext2 = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext2.setKeywords("Architectural");
		searchContext2.setLocale(LocaleUtil.SPAIN);

		int initialSearchCount2 = searchCount(
			_group.getGroupId(), searchContext2);

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		titleMap.put(LocaleUtil.GERMANY, "Titel");
		titleMap.put(LocaleUtil.SPAIN, "Titulo");
		titleMap.put(LocaleUtil.US, "Title");

		Map<Locale, String> contentMap = new HashMap<Locale, String>();

		contentMap.put(LocaleUtil.GERMANY, "Liferay Architektur Ansatz");
		contentMap.put(LocaleUtil.SPAIN, "Liferay Arquitectura Aproximacion");
		contentMap.put(LocaleUtil.US, "Liferay Architectural Approach");

		JournalArticle article = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), titleMap, titleMap, contentMap, true);

		Assert.assertEquals(
			initialSearchCount1 + 1,
			searchCount(_group.getGroupId(), searchContext1));

		JournalArticleLocalServiceUtil.removeArticleLocale(
			_group.getGroupId(), article.getArticleId(), article.getVersion(),
			LocaleUtil.toLanguageId(LocaleUtil.SPAIN));

		Assert.assertEquals(
			initialSearchCount1,
			searchCount(_group.getGroupId(), searchContext1));
		Assert.assertEquals(
			initialSearchCount2 + 1,
			searchCount(_group.getGroupId(), searchContext2));
	}

	@Test
	public void testUpdateArticleAndApprove() throws Exception {
		updateArticle(true);
	}

	@Test
	public void testUpdateArticleAndDraft() throws Exception {
		updateArticle(false);
	}

	@Test
	public void testUpdateArticleTranslation() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SearchContext searchContext1 = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext1.setKeywords("Arquitectura");
		searchContext1.setLocale(LocaleUtil.SPAIN);

		int initialSearchCount1 = searchCount(
			_group.getGroupId(), searchContext1);

		SearchContext searchContext2 = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext2.setKeywords("Apple");
		searchContext2.setLocale(LocaleUtil.SPAIN);

		int initialSearchCount2 = searchCount(
			_group.getGroupId(), searchContext2);

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		titleMap.put(LocaleUtil.GERMANY, "Titel");
		titleMap.put(LocaleUtil.SPAIN, "Titulo");
		titleMap.put(LocaleUtil.US, "Title");

		Map<Locale, String> contentMap = new HashMap<Locale, String>();

		contentMap.put(LocaleUtil.GERMANY, "Liferay Architektur Ansatz");
		contentMap.put(LocaleUtil.SPAIN, "Liferay Arquitectura Aproximacion");
		contentMap.put(LocaleUtil.US, "Liferay Architectural Approach");

		JournalArticle article = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), titleMap, titleMap, contentMap, true);

		Assert.assertEquals(
			initialSearchCount1 + 1,
			searchCount(_group.getGroupId(), searchContext1));

		contentMap.put(LocaleUtil.SPAIN, "Apple manzana tablet");

		String defaultLanguageId = LanguageUtil.getLanguageId(
			LocaleUtil.getDefault());

		String content = DDMStructureTestUtil.getSampleStructuredContent(
			contentMap, defaultLanguageId);

		article = JournalArticleLocalServiceUtil.updateArticleTranslation(
			_group.getGroupId(), article.getArticleId(), article.getVersion(),
			LocaleUtil.SPAIN, article.getTitle(LocaleUtil.SPAIN),
			article.getDescription(LocaleUtil.SPAIN), content, null,
			serviceContext);

		Assert.assertEquals(
			initialSearchCount2,
			searchCount(_group.getGroupId(), searchContext2));

		User user = UserTestUtil.addUser(_group.getGroupId(), LocaleUtil.SPAIN);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		JournalArticleLocalServiceUtil.updateArticle(
			user.getUserId(), article.getGroupId(), article.getFolderId(),
			article.getArticleId(), article.getVersion(), article.getContent(),
			serviceContext);

		Assert.assertEquals(
			initialSearchCount2 + 1,
			searchCount(_group.getGroupId(), searchContext2));
	}

	@Test
	public void testUpdateStructuredContent() throws Exception {
		updateContent();
	}

	protected void addArticle(boolean approve) throws Exception {
		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext.setKeywords("Architectural");

		int initialSearchCount = searchCount(
			_group.getGroupId(), searchContext);

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), folder.getFolderId(), "title",
			"Liferay Architectural Approach", approve);

		if (approve) {
			Assert.assertEquals(
				initialSearchCount + 1,
				searchCount(_group.getGroupId(), searchContext));
		}
		else {
			Assert.assertEquals(
				initialSearchCount,
				searchCount(_group.getGroupId(), searchContext));
		}
	}

	protected JournalArticle addJournalWithDDMStructure(
			long folderId, String keywords, ServiceContext serviceContext)
		throws Exception {

		String xsd = DDMStructureTestUtil.getSampleStructureXSD("name");

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			serviceContext.getScopeGroupId(), JournalArticle.class.getName(),
			xsd);

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			serviceContext.getScopeGroupId(), ddmStructure.getStructureId());

		String content = DDMStructureTestUtil.getSampleStructuredContent(
			"name", keywords);

		return JournalTestUtil.addArticleWithXMLContent(
			folderId, content, ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey(), serviceContext);
	}

	protected void articleVersions(boolean delete, boolean all)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SearchContext searchContext1 = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext1.setKeywords("Architectural");

		int initialSearchCount1 = searchCount(
			_group.getGroupId(), searchContext1);

		SearchContext searchContext2 = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext2.setKeywords("Apple");

		int initialSearchCount2 = searchCount(
			_group.getGroupId(), searchContext2);

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		JournalArticle article = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), folder.getFolderId(), "title",
			"Liferay Architectural Approach", true);

		Assert.assertEquals(
			initialSearchCount1 + 1,
			searchCount(_group.getGroupId(), searchContext1));

		String content = DDMStructureTestUtil.getSampleStructuredContent(
			"Apple tablet");

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		article = JournalTestUtil.updateArticle(
			article, article.getTitle(), content, false, true, serviceContext);

		Assert.assertEquals(
			initialSearchCount1,
			searchCount(_group.getGroupId(), searchContext1));
		Assert.assertEquals(
			initialSearchCount2 + 1,
			searchCount(_group.getGroupId(), searchContext2));

		if (all) {
			if (delete) {
				JournalArticleLocalServiceUtil.deleteArticle(
					_group.getGroupId(), article.getArticleId(),
					serviceContext);
			}
			else {
				JournalArticleLocalServiceUtil.expireArticle(
					TestPropsValues.getUserId(), _group.getGroupId(),
					article.getArticleId(), article.getUrlTitle(),
					serviceContext);
			}

			Assert.assertEquals(
				initialSearchCount1,
				searchCount(_group.getGroupId(), searchContext1));
		}
		else {
			if (delete) {
				JournalArticleLocalServiceUtil.deleteArticle(article);
			}
			else {
				JournalArticleLocalServiceUtil.expireArticle(
					TestPropsValues.getUserId(), _group.getGroupId(),
					article.getArticleId(), article.getVersion(),
					article.getUrlTitle(), serviceContext);
			}

			Assert.assertEquals(
				initialSearchCount1 + 1,
				searchCount(_group.getGroupId(), searchContext1));
		}

		Assert.assertEquals(
			initialSearchCount2,
			searchCount(_group.getGroupId(), searchContext2));
	}

	protected void indexVersions(boolean delete, boolean all) throws Exception {
		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		int initialSearchCount = searchCount(
			_group.getGroupId(), searchContext);

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		String content = "Liferay Architectural Approach";

		JournalArticle article = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), folder.getFolderId(), "title", content, true);

		Assert.assertEquals(
			initialSearchCount + 1,
			searchCount(_group.getGroupId(), searchContext));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		article = JournalTestUtil.updateArticle(
			article, article.getTitle(), article.getContent(), false, true,
			serviceContext);

		Assert.assertEquals(
			initialSearchCount + 2,
			searchCount(
				_group.getGroupId(), false, WorkflowConstants.STATUS_ANY,
				searchContext));

		if (delete) {
			if (all) {
				JournalArticleLocalServiceUtil.deleteArticle(
					_group.getGroupId(), article.getArticleId(),
					serviceContext);

				Assert.assertEquals(
					initialSearchCount,
					searchCount(
						_group.getGroupId(), false,
						WorkflowConstants.STATUS_ANY, searchContext));
			}
			else {
				JournalArticleLocalServiceUtil.deleteArticle(article);

				Assert.assertEquals(
					initialSearchCount + 1,
					searchCount(
						_group.getGroupId(), false,
						WorkflowConstants.STATUS_ANY, searchContext));
			}
		}
		else {
			if (all) {
				JournalArticleLocalServiceUtil.expireArticle(
					TestPropsValues.getUserId(), _group.getGroupId(),
					article.getArticleId(), article.getUrlTitle(),
					serviceContext);
			}
			else {
				JournalArticleLocalServiceUtil.expireArticle(
					TestPropsValues.getUserId(), _group.getGroupId(),
					article.getArticleId(), article.getVersion(),
					article.getUrlTitle(), serviceContext);
			}

			Assert.assertEquals(
				initialSearchCount + 2,
				searchCount(
					_group.getGroupId(), false, WorkflowConstants.STATUS_ANY,
					searchContext));
		}
	}

	protected void moveArticle(boolean moveToTrash) throws Exception {
		SearchContext searchContext1 = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext1.setKeywords("Architectural");

		JournalFolder folder1 = JournalTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		searchContext1.setFolderIds(new long[] {folder1.getFolderId()});

		int initialSearchCount1 = searchCount(
			_group.getGroupId(), searchContext1);

		SearchContext searchContext2 = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext2.setKeywords("Architectural");

		JournalFolder folder2 = JournalTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		searchContext2.setFolderIds(new long[] {folder2.getFolderId()});

		int initialSearchCount2 = searchCount(
			_group.getGroupId(), searchContext2);

		JournalArticle article = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), folder1.getFolderId(), "title",
			"Liferay Architectural Approach", true);

		Assert.assertEquals(
			initialSearchCount1 + 1,
			searchCount(_group.getGroupId(), searchContext1));

		if (moveToTrash) {
			JournalFolderLocalServiceUtil.moveFolderToTrash(
				TestPropsValues.getUserId(), folder1.getFolderId());

			Assert.assertEquals(
				initialSearchCount1,
				searchCount(_group.getGroupId(), searchContext1));

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			article = JournalArticleLocalServiceUtil.getArticle(
				article.getId());

			JournalArticleLocalServiceUtil.moveArticleFromTrash(
				TestPropsValues.getUserId(), _group.getGroupId(), article,
				folder2.getFolderId(), serviceContext);
		}
		else {
			JournalArticleLocalServiceUtil.moveArticle(
				_group.getGroupId(), article.getArticleId(),
				folder2.getFolderId());
		}

		Assert.assertEquals(
			initialSearchCount1,
			searchCount(_group.getGroupId(), searchContext1));
		Assert.assertEquals(
			initialSearchCount2 + 1,
			searchCount(_group.getGroupId(), searchContext2));
	}

	protected int searchCount(
			long groupId, boolean head, int status, SearchContext searchContext)
		throws Exception {

		Indexer indexer = IndexerRegistryUtil.getIndexer(JournalArticle.class);

		searchContext.setAttribute("head", head);
		searchContext.setAttribute("status", status);
		searchContext.setGroupIds(new long[] {groupId});

		Hits results = indexer.search(searchContext);

		return results.getLength();
	}

	protected int searchCount(long groupId, SearchContext searchContext)
		throws Exception {

		return searchCount(
			groupId, true, WorkflowConstants.STATUS_APPROVED, searchContext);
	}

	protected void updateArticle(boolean approve) throws Exception {
		SearchContext searchContext1 = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext1.setKeywords("Architectural");

		int initialSearchCount1 = searchCount(
			_group.getGroupId(), searchContext1);

		SearchContext searchContext2 = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext2.setKeywords("Apple");

		int initialSearchCount2 = searchCount(
			_group.getGroupId(), searchContext2);

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		JournalArticle article = JournalTestUtil.addArticleWithWorkflow(
			_group.getGroupId(), folder.getFolderId(), "title",
			"Liferay Architectural Approach", true);

		Assert.assertEquals(
			initialSearchCount1 + 1,
			searchCount(_group.getGroupId(), searchContext1));

		String content = DDMStructureTestUtil.getSampleStructuredContent(
			"Apple tablet");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		if (!approve) {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}
		else {
			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
		}

		JournalTestUtil.updateArticle(
			article, article.getTitle(), content, false, true, serviceContext);

		if (approve) {
			Assert.assertEquals(
				initialSearchCount1,
				searchCount(_group.getGroupId(), searchContext1));
			Assert.assertEquals(
				initialSearchCount2 + 1,
				searchCount(_group.getGroupId(), searchContext2));
		}
		else {
			Assert.assertEquals(
				initialSearchCount1 + 1,
				searchCount(_group.getGroupId(), searchContext1));
			Assert.assertEquals(
				initialSearchCount2,
				searchCount(_group.getGroupId(), searchContext2));
		}
	}

	protected void updateContent() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SearchContext searchContext1 = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext1.setKeywords("Architectural");

		int initialSearchCount1 = searchCount(
			_group.getGroupId(), searchContext1);

		SearchContext searchContext2 = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext2.setKeywords("Liferay");

		int initialSearchCount2 = searchCount(
			_group.getGroupId(), searchContext2);

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		String content = "Liferay Architectural Approach";

		JournalArticle article = addJournalWithDDMStructure(
			folder.getFolderId(), content, serviceContext);

		Assert.assertEquals(
			initialSearchCount1 + 1,
			searchCount(_group.getGroupId(), searchContext1));

		content = DDMStructureTestUtil.getSampleStructuredContent(
			"name", "Architectural Approach");

		JournalArticleLocalServiceUtil.updateContent(
			_group.getGroupId(), article.getArticleId(), article.getVersion(),
			content);

		Assert.assertEquals(
			initialSearchCount1 + 1,
			searchCount(_group.getGroupId(), searchContext1));
		Assert.assertEquals(
			initialSearchCount2,
			searchCount(_group.getGroupId(), searchContext2));
	}

	@DeleteAfterTestRun
	private Group _group;

}