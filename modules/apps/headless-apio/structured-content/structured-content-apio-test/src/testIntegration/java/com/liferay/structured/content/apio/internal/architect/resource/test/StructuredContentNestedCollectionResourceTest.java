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
import com.liferay.journal.model.JournalArticleWrapper;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.structured.content.apio.architect.filter.Filter;
import com.liferay.structured.content.apio.architect.filter.FilterParser;
import com.liferay.structured.content.apio.architect.sort.Sort;
import com.liferay.structured.content.apio.architect.sort.SortParser;
import com.liferay.structured.content.apio.architect.util.test.PaginationTestUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.assertj.core.api.Assertions;

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
	public void testGetJournalArticleWrapper() throws Throwable {
		Map<Locale, String> stringMap = new HashMap<>();

		String title = RandomTestUtil.randomString();

		stringMap.put(LocaleUtil.getDefault(), title);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, title, false,
			stringMap, stringMap, stringMap, null, LocaleUtil.getDefault(),
			null, true, true, serviceContext);

		JournalArticleWrapper journalArticleWrapper = _getJournalArticleWrapper(
			journalArticle.getId(),
			_getThemeDisplay(_group, LocaleUtil.getDefault()));

		Assert.assertEquals(
			title, journalArticleWrapper.getTitle(LocaleUtil.getDefault()));
	}

	@Test
	public void testGetJournalArticleWrapperFilterByPermission()
		throws Exception {

		Map<Locale, String> stringMap = new HashMap<>();

		String title = RandomTestUtil.randomString();

		stringMap.put(LocaleUtil.getDefault(), title);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, title, false,
			stringMap, stringMap, stringMap, null, LocaleUtil.getDefault(),
			null, true, true, serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace =
				new ContextUserReplace(user, permissionChecker)) {

			Assertions.assertThatThrownBy(
				() -> _getJournalArticleWrapper(
					journalArticle.getId(),
					_getThemeDisplay(_group, LocaleUtil.getDefault()))
			).isInstanceOf(
				PrincipalException.MustHavePermission.class
			);
		}
		finally {
			_userLocalService.deleteUser(user);
		}
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
			_getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> items = (List<JournalArticle>)pageItems.getItems();

		Assert.assertTrue("Items " + items, items.contains(journalArticle));
	}

	@Test
	public void testGetPageItemsFilterByPartialTitle() throws Exception {
		Map<Locale, String> stringMap = new HashMap<>();

		stringMap.put(LocaleUtil.getDefault(), "hello world");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap, stringMap,
			stringMap, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		PageItems<JournalArticle> pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(),
			_getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(_filterParser.parse("(title eq 'hello')")),
			Sort.emptySort());

		Assert.assertEquals(0, pageItems.getTotalCount());
	}

	@Test
	public void testGetPageItemsFilterByPartialTitleWithSeveralWords()
		throws Exception {

		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), "hello world");

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

		stringMap2.put(LocaleUtil.getDefault(), "hello");

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		PageItems<JournalArticle> pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(),
			_getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(_filterParser.parse("(title eq 'hello world')")),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> items = (List<JournalArticle>)pageItems.getItems();

		Assert.assertTrue("Items " + items, items.contains(journalArticle1));
	}

	@Test
	public void testGetPageItemsFilterByPartialTitleWord() throws Exception {
		Map<Locale, String> stringMap = new HashMap<>();

		stringMap.put(LocaleUtil.getDefault(), "hello world");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap, stringMap,
			stringMap, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		PageItems<JournalArticle> pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(),
			_getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(_filterParser.parse("(title eq 'hel')")),
			Sort.emptySort());

		Assert.assertEquals(0, pageItems.getTotalCount());
	}

	@Test
	public void testGetPageItemsFilterByPermission() throws Exception {
		Map<Locale, String> stringMap = new HashMap<>();

		stringMap.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap, stringMap,
			stringMap, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace =
				new ContextUserReplace(user, permissionChecker)) {

			PageItems<JournalArticle> pageItems = _getPageItems(
				PaginationTestUtil.of(10, 1), _group.getGroupId(),
				_getThemeDisplay(_group, LocaleUtil.getDefault()),
				Filter.emptyFilter(), Sort.emptySort());

			Assert.assertEquals(0, pageItems.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetPageItemsFilterByTitle() throws Exception {
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

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		PageItems<JournalArticle> pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(),
			_getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(_filterParser.parse("(title eq 'title1')")),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> items = (List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle1, items.get(0));
	}

	@Test
	public void testGetPageItemsFilterByTitleInLocaleWithDifferentDefaultLocale()
		throws Exception {

		Map<Locale, String> stringMap = new HashMap<>();

		stringMap.put(LocaleUtil.SPAIN, "titulo1");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		// Default locale of article is in Spanish (no English content or title)

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap, stringMap,
			stringMap, null, LocaleUtil.SPAIN, null, true, true,
			serviceContext);

		PageItems<JournalArticle> pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(),
			_getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(_filterParser.parse("(title eq 'titulo1')")),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> items = (List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle, items.get(0));
	}

	@Test
	public void testGetPageItemsFilterByTitleInLocaleWithDifferentLocale()
		throws Exception {

		Map<Locale, String> stringMap = new HashMap<>();

		stringMap.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());
		stringMap.put(LocaleUtil.SPAIN, "titulo1");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap, stringMap,
			stringMap, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		PageItems<JournalArticle> pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(),
			_getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(_filterParser.parse("(title eq 'titulo1')")),
			Sort.emptySort());

		Assert.assertEquals(0, pageItems.getTotalCount());
	}

	@Test
	public void testGetPageItemsFilterByTitleInLocaleWithSameLocale()
		throws Exception {

		Map<Locale, String> stringMap = new HashMap<>();

		stringMap.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());
		stringMap.put(LocaleUtil.SPAIN, "titulo1");

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
			_getThemeDisplay(_group, LocaleUtil.SPAIN),
			new Filter(_filterParser.parse("(title eq 'titulo1')")),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> items = (List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle, items.get(0));
	}

	@Test
	public void testGetPageItemsFilterByTitleStartingWithUpperCase()
		throws Exception {

		Map<Locale, String> stringMap1 = new HashMap<>();

		String title1 = "great title";

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

		stringMap2.put(LocaleUtil.getDefault(), "Great Title");

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		PageItems<JournalArticle> pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(),
			_getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(_filterParser.parse("(title eq 'Great Title')")),
			Sort.emptySort());

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> items = (List<JournalArticle>)pageItems.getItems();

		Assert.assertTrue(items.contains(journalArticle1));
		Assert.assertTrue(items.contains(journalArticle2));
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
			_getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), new Sort(_sortParser.parse("title:asc")));

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> items = (List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle1, items.get(0));
		Assert.assertEquals(journalArticle2, items.get(1));
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

		PageItems<JournalArticle> pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(),
			_getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), new Sort(_sortParser.parse("title:asc")));

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> items = (List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle2, items.get(0));
		Assert.assertEquals(journalArticle1, items.get(1));
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

		PageItems<JournalArticle> pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(),
			_getThemeDisplay(_group, LocaleUtil.SPAIN), Filter.emptyFilter(),
			new Sort(_sortParser.parse("title:asc")));

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> items = (List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle2, items.get(0));
		Assert.assertEquals(journalArticle1, items.get(1));
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

		PageItems<JournalArticle> pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(),
			_getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), new Sort(_sortParser.parse("title")));

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> items = (List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle2, items.get(0));
		Assert.assertEquals(journalArticle1, items.get(1));
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

		PageItems<JournalArticle> pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(),
			_getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), new Sort(_sortParser.parse("title:desc")));

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> items = (List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle2, items.get(0));
		Assert.assertEquals(journalArticle1, items.get(1));
	}

	@Test
	public void testGetPageItemsWith2VersionsAnd1Scheduled() throws Exception {
		Map<Locale, String> stringMap = new HashMap<>();

		stringMap.put(LocaleUtil.getDefault(), "Version 1");
		stringMap.put(LocaleUtil.GERMANY, RandomTestUtil.randomString());
		stringMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap, stringMap,
			stringMap, null, LocaleUtil.getDefault(), null, null, true, true,
			serviceContext);

		// Create new version scheduled for tomorrow

		stringMap.put(LocaleUtil.getDefault(), "Version 2");

		Date displayDate = new Date(
			System.currentTimeMillis() + 24 * 60 * 60 * 1000);

		JournalTestUtil.updateArticle(
			serviceContext.getUserId(), journalArticle, stringMap,
			journalArticle.getContent(), displayDate, true, true,
			serviceContext);

		int journalArticlesCount = _journalArticleLocalService.getArticlesCount(
			journalArticle.getGroupId(), journalArticle.getArticleId());

		Assert.assertEquals(2, journalArticlesCount);

		PageItems<JournalArticle> pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(),
			_getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> items = (List<JournalArticle>)pageItems.getItems();

		Assert.assertTrue("Items " + items, items.contains(journalArticle));

		JournalArticle foundJournalArticle = items.get(0);

		Assert.assertEquals(
			"Version 1", foundJournalArticle.getTitle(LocaleUtil.getDefault()));
	}

	@Test
	public void testGetPageItemsWith2VersionsAndOnly1Approved()
		throws Exception {

		Map<Locale, String> stringMap = new HashMap<>();

		stringMap.put(LocaleUtil.getDefault(), "Version 1");
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

		// Create new version as a draft

		JournalTestUtil.updateArticle(
			journalArticle, "Version 2", journalArticle.getContent(), true,
			false, serviceContext);

		int journalArticlesCount = _journalArticleLocalService.getArticlesCount(
			journalArticle.getGroupId(), journalArticle.getArticleId());

		Assert.assertEquals(2, journalArticlesCount);

		PageItems<JournalArticle> pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(),
			_getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> items = (List<JournalArticle>)pageItems.getItems();

		Assert.assertTrue("Items " + items, items.contains(journalArticle));

		JournalArticle foundJournalArticle = items.get(0);

		Assert.assertEquals(
			"Version 1", foundJournalArticle.getTitle(LocaleUtil.getDefault()));
	}

	@Test
	public void testGetPageItemsWith2VersionsApproved() throws Exception {
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
			stringMap, null, LocaleUtil.getDefault(), null, null, true, true,
			serviceContext);

		journalArticle = JournalTestUtil.updateArticle(
			journalArticle, "Version 2", journalArticle.getContent(), true,
			true, serviceContext);

		int journalArticlesCount = _journalArticleLocalService.getArticlesCount(
			journalArticle.getGroupId(), journalArticle.getArticleId());

		Assert.assertEquals(2, journalArticlesCount);

		PageItems<JournalArticle> pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(),
			_getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> items = (List<JournalArticle>)pageItems.getItems();

		Assert.assertTrue("Items " + items, items.contains(journalArticle));

		JournalArticle foundJournalArticle = items.get(0);

		Assert.assertEquals(
			"Version 2", foundJournalArticle.getTitle(LocaleUtil.getDefault()));
	}

	@Test
	public void testGetPageItemsWithOnlyOneDraftVersion() throws Exception {
		Map<Locale, String> stringMap = new HashMap<>();

		stringMap.put(LocaleUtil.getDefault(), "Version 1");
		stringMap.put(LocaleUtil.GERMANY, RandomTestUtil.randomString());
		stringMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap, stringMap,
			stringMap, null, LocaleUtil.getDefault(), null, null, true, false,
			serviceContext);

		int journalArticlesCount = _journalArticleLocalService.getArticlesCount(
			journalArticle.getGroupId(), journalArticle.getArticleId());

		Assert.assertEquals(1, journalArticlesCount);

		PageItems<JournalArticle> pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(),
			_getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), Sort.emptySort());

		Assert.assertEquals(0, pageItems.getTotalCount());
	}

	@Test
	public void testGetPageItemsWithOnlyOneExpiredVersion() throws Exception {
		Map<Locale, String> stringMap = new HashMap<>();

		stringMap.put(LocaleUtil.getDefault(), "Version 1");
		stringMap.put(LocaleUtil.GERMANY, RandomTestUtil.randomString());
		stringMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap, stringMap,
			stringMap, null, LocaleUtil.getDefault(), null, null, true, true,
			serviceContext);

		_journalArticleLocalService.updateStatus(
			serviceContext.getUserId(), journalArticle,
			WorkflowConstants.STATUS_EXPIRED, null, serviceContext,
			new HashMap<>());

		int journalArticlesCount = _journalArticleLocalService.getArticlesCount(
			journalArticle.getGroupId(), journalArticle.getArticleId());

		Assert.assertEquals(1, journalArticlesCount);

		PageItems<JournalArticle> pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(),
			_getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), Sort.emptySort());

		Assert.assertEquals(0, pageItems.getTotalCount());
	}

	@Test
	public void testGetPageItemsWithOnlyOneSheduledVersion() throws Exception {
		Map<Locale, String> stringMap = new HashMap<>();

		stringMap.put(LocaleUtil.getDefault(), "Version 1");
		stringMap.put(LocaleUtil.GERMANY, RandomTestUtil.randomString());
		stringMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		Date displayDate = new Date(
			System.currentTimeMillis() + 24 * 60 * 60 * 1000); // Tomorrow

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap, stringMap,
			stringMap, null, LocaleUtil.getDefault(), displayDate, null, true,
			true, serviceContext);

		int journalArticlesCount = _journalArticleLocalService.getArticlesCount(
			journalArticle.getGroupId(), journalArticle.getArticleId());

		Assert.assertEquals(1, journalArticlesCount);

		PageItems<JournalArticle> pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(),
			_getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), Sort.emptySort());

		Assert.assertEquals(0, pageItems.getTotalCount());
	}

	private JournalArticleWrapper _getJournalArticleWrapper(
			long journalArticleId, ThemeDisplay themeDisplay)
		throws Throwable {

		Class<? extends NestedCollectionResource> clazz =
			_nestedCollectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_getJournalArticleWrapper", long.class, ThemeDisplay.class);

		method.setAccessible(true);

		try {
			return (JournalArticleWrapper)method.invoke(
				_nestedCollectionResource, journalArticleId, themeDisplay);
		}
		catch (InvocationTargetException ite) {
			ite.printStackTrace();

			throw ite.getCause();
		}
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

	private ThemeDisplay _getThemeDisplay(Group group, Locale locale)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = CompanyLocalServiceUtil.getCompanyById(
			group.getCompanyId());

		themeDisplay.setCompany(company);

		themeDisplay.setLocale(locale);
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

	@Inject
	private UserLocalService _userLocalService;

}