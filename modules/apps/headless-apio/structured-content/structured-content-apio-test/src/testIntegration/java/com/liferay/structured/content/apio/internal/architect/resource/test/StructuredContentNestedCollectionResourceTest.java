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

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.structured.content.apio.architect.filter.Filter;
import com.liferay.structured.content.apio.architect.filter.FilterParser;
import com.liferay.structured.content.apio.architect.sort.Sort;
import com.liferay.structured.content.apio.architect.sort.SortParser;
import com.liferay.structured.content.apio.architect.util.test.PaginationTestUtil;

import java.lang.reflect.Method;

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
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class StructuredContentNestedCollectionResourceTest {

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
	public void testGetPageItems() throws Exception {
		Map<Locale, String> stringMap = new HashMap<>();

		stringMap.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());
		stringMap.put(LocaleUtil.GERMANY, RandomTestUtil.randomString());
		stringMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap, stringMap,
			stringMap, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		PageItems<JournalArticle> pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(),
			_getThemeDisplay(_group), Filter.emptyFilter(), Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> items = (List<JournalArticle>)pageItems.getItems();

		Assert.assertTrue("Items " + items, items.contains(journalArticle));
	}

	@Test
	public void testGetPageItemsFilterByTitle() throws Exception {
		Map<Locale, String> stringMap1 = new HashMap<>();

		String title1 = "title1";

		stringMap1.put(LocaleUtil.getDefault(), title1);

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

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		PageItems<JournalArticle> pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(),
			_getThemeDisplay(_group),
			new Filter(_filterParser.parse("(title eq 'title1')")),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> items = (List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle1, items.get(0));
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

		PageItems<JournalArticle> pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(),
			_getThemeDisplay(_group), Filter.emptyFilter(),
			new Sort(_sortParser.parse("title:asc")));

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> items = (List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle1, items.get(0));
		Assert.assertEquals(journalArticle2, items.get(1));
	}

	@Test
	public void testGetPageItemsSortByTitleDefault() throws Exception {
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

		PageItems<JournalArticle> pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(),
			_getThemeDisplay(_group), Filter.emptyFilter(),
			new Sort(_sortParser.parse("title")));

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> items = (List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle1, items.get(0));
		Assert.assertEquals(journalArticle2, items.get(1));
	}

	@Test
	public void testGetPageItemsSortByTitleDesc() throws Exception {
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

		PageItems<JournalArticle> pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(),
			_getThemeDisplay(_group), Filter.emptyFilter(),
			new Sort(_sortParser.parse("title:desc")));

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> items = (List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle2, items.get(0));
		Assert.assertEquals(journalArticle1, items.get(1));
	}

	private PageItems<JournalArticle> _getPageItems(
			Pagination pagination, long contentSpaceId,
			ThemeDisplay themeDisplay, Filter filter, Sort sort)
		throws Exception {

		Class<? extends NestedCollectionResource> clazz =
			_nestedCollectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_getPageItems", Pagination.class, long.class, ThemeDisplay.class,
			Filter.class, Sort.class);

		method.setAccessible(true);

		return (PageItems)method.invoke(
			_nestedCollectionResource, pagination, contentSpaceId, themeDisplay,
			filter, sort);
	}

	private ThemeDisplay _getThemeDisplay(Group group) throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = CompanyLocalServiceUtil.getCompanyById(
			group.getCompanyId());

		themeDisplay.setCompany(company);

		themeDisplay.setScopeGroupId(group.getGroupId());

		return themeDisplay;
	}

	@Inject
	private FilterParser _filterParser;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject(
		filter = "component.name=com.liferay.structured.content.apio.internal.architect.resource.StructuredContentNestedCollectionResource"
	)
	private NestedCollectionResource _nestedCollectionResource;

	@Inject
	private SortParser _sortParser;

}