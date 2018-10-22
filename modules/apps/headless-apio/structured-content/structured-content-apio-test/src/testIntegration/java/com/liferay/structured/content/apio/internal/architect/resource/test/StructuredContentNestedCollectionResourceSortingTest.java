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

package com.liferay.structured.content.apio.internal.architect.resource.test;

import com.liferay.apio.architect.language.AcceptLanguage;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.test.util.pagination.PaginationRequest;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.filter.Filter;
import com.liferay.portal.odata.sort.Sort;
import com.liferay.portal.odata.sort.SortParser;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Julio Camarero
 */
@RunWith(Arquillian.class)
public class StructuredContentNestedCollectionResourceSortingTest
	extends BaseStructuredContentNestedCollectionResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetPageItemsSortByDateCreatedAsc() throws Exception {
		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		Thread.sleep(1000);

		Map<Locale, String> stringMap2 = new HashMap<>();

		stringMap2.put(LocaleUtil.getDefault(), "title2");

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(),
			new Sort(_sortParser.parse("dateCreated:asc")));

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle1, journalArticles.get(0));
		Assert.assertEquals(journalArticle2, journalArticles.get(1));
	}

	@Test
	public void testGetPageItemsSortByDateCreatedDesc() throws Exception {
		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		Thread.sleep(1000);

		Map<Locale, String> stringMap2 = new HashMap<>();

		stringMap2.put(LocaleUtil.getDefault(), "title2");

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(),
			new Sort(_sortParser.parse("dateCreated:desc")));

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle1, journalArticles.get(1));
		Assert.assertEquals(journalArticle2, journalArticles.get(0));
	}

	@Test
	public void testGetPageItemsSortByDateModifiedAsc() throws Exception {
		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		Thread.sleep(1000);

		Map<Locale, String> stringMap2 = new HashMap<>();

		stringMap2.put(LocaleUtil.getDefault(), "title2");

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(),
			new Sort(_sortParser.parse("dateModified:asc")));

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle1, journalArticles.get(0));
		Assert.assertEquals(journalArticle2, journalArticles.get(1));
	}

	@Test
	public void testGetPageItemsSortByDateModifiedDesc() throws Exception {
		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		Thread.sleep(1000);

		Map<Locale, String> stringMap2 = new HashMap<>();

		stringMap2.put(LocaleUtil.getDefault(), "title2");

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(),
			new Sort(_sortParser.parse("dateModified:desc")));

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle1, journalArticles.get(1));
		Assert.assertEquals(journalArticle2, journalArticles.get(0));
	}

	@Test
	public void testGetPageItemsSortByDatePublishedAsc() throws Exception {
		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		LocalDate localDate1 = LocalDate.of(2018, 02, 20);

		ZonedDateTime zonedDateTime1 = localDate1.atStartOfDay(
			ZoneId.of("GMT"));

		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(),
			Date.from(zonedDateTime1.toInstant()), null, true, true,
			serviceContext);

		Map<Locale, String> stringMap2 = new HashMap<>();

		stringMap2.put(LocaleUtil.getDefault(), "title2");

		LocalDate localDate2 = localDate1.plusDays(4);

		ZonedDateTime zonedDateTime2 = localDate2.atStartOfDay(
			ZoneId.of("GMT"));

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(),
			Date.from(zonedDateTime2.toInstant()), null, true, true,
			serviceContext);

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(),
			new Sort(_sortParser.parse("datePublished:asc")));

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle1, journalArticles.get(0));
		Assert.assertEquals(journalArticle2, journalArticles.get(1));
	}

	@Test
	public void testGetPageItemsSortByDatePublishedDesc() throws Exception {
		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		LocalDate localDate1 = LocalDate.of(2018, 02, 20);

		ZonedDateTime zonedDateTime1 = localDate1.atStartOfDay(
			ZoneId.of("GMT"));

		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(),
			Date.from(zonedDateTime1.toInstant()), null, true, true,
			serviceContext);

		Map<Locale, String> stringMap2 = new HashMap<>();

		stringMap2.put(LocaleUtil.getDefault(), "title2");

		LocalDate localDate2 = localDate1.plusDays(4);

		ZonedDateTime zonedDateTime2 = localDate2.atStartOfDay(
			ZoneId.of("GMT"));

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(),
			Date.from(zonedDateTime2.toInstant()), null, true, true,
			serviceContext);

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(),
			new Sort(_sortParser.parse("datePublished:desc")));

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle1, journalArticles.get(1));
		Assert.assertEquals(journalArticle2, journalArticles.get(0));
	}

	@Test
	public void testGetPageItemsSortByTitleAsc() throws Exception {
		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), "title1");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		Map<Locale, String> stringMap2 = new HashMap<>();

		stringMap2.put(LocaleUtil.getDefault(), "title2");

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), new Sort(_sortParser.parse("title:asc")));

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle1, journalArticles.get(0));
		Assert.assertEquals(journalArticle2, journalArticles.get(1));
	}

	@Test
	public void testGetPageItemsSortByTitleAscWithDifferentDefaultLocale()
		throws Exception {

		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), "titleZ");
		stringMap1.put(LocaleUtil.SPAIN, "title1");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		Map<Locale, String> stringMap2 = new HashMap<>();

		stringMap2.put(LocaleUtil.SPAIN, "titleA");

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.SPAIN, null, true, true,
			serviceContext);

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), new Sort(_sortParser.parse("title:asc")));

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle2, journalArticles.get(0));
		Assert.assertEquals(journalArticle1, journalArticles.get(1));
	}

	@Test
	public void testGetPageItemsSortByTitleAscWithNonedefaultLocale()
		throws Exception {

		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), "title1");
		stringMap1.put(LocaleUtil.SPAIN, "titleZ");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		Map<Locale, String> stringMap2 = new HashMap<>();

		stringMap2.put(LocaleUtil.getDefault(), "title2");
		stringMap2.put(LocaleUtil.SPAIN, "titleA");

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(),
			() -> LocaleUtil.SPAIN, getThemeDisplay(_group, LocaleUtil.SPAIN),
			Filter.emptyFilter(), new Sort(_sortParser.parse("title:asc")));

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle2, journalArticles.get(0));
		Assert.assertEquals(journalArticle1, journalArticles.get(1));
	}

	@Test
	public void testGetPageItemsSortByTitleDefault() throws Exception {
		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), "title B");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		Map<Locale, String> stringMap2 = new HashMap<>();

		stringMap2.put(LocaleUtil.getDefault(), "title A");

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), new Sort(_sortParser.parse("title")));

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle2, journalArticles.get(0));
		Assert.assertEquals(journalArticle1, journalArticles.get(1));
	}

	@Test
	public void testGetPageItemsSortByTitleDesc() throws Exception {
		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), "title A");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		Map<Locale, String> stringMap2 = new HashMap<>();

		stringMap2.put(LocaleUtil.getDefault(), "title B");

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), new Sort(_sortParser.parse("title:desc")));

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle2, journalArticles.get(0));
		Assert.assertEquals(journalArticle1, journalArticles.get(1));
	}

	private static final AcceptLanguage _acceptLanguage =
		() -> LocaleUtil.getDefault();

	@DeleteAfterTestRun
	private Group _group;

	@Inject(filter = "entity.model.name=StructuredContent")
	private SortParser _sortParser;

}