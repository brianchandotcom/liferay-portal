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

import com.fasterxml.jackson.databind.util.ISO8601Utils;

import com.liferay.apio.architect.language.AcceptLanguage;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.test.util.pagination.PaginationRequest;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.odata.filter.Filter;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.sort.Sort;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
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
public class StructuredContentNestedCollectionResourceFilteringTest
	extends BaseStructuredContentNestedCollectionResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		com.liferay.registry.Filter filter = registry.getFilter(
			"(&(objectClass=" + FilterParser.class.getName() +
				")(entity.model.name=StructuredContent))");

		_serviceTracker = registry.trackServices(filter);

		_serviceTracker.open();

		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() {
		_serviceTracker.close();
	}

	@Test
	public void testGetPageItemsFilterByContentStructure() throws Exception {
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

		Map<Locale, String> stringMap2 = new HashMap<>();

		stringMap2.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		FilterParser filterParser = _getFilterParser();
		DDMStructure ddmStructure = journalArticle1.getDDMStructure();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(
				filterParser.parse(
					"(contentStructure eq '/" + ddmStructure.getStructureId() +
						"')")),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle1, journalArticles.get(0));
	}

	@Test
	public void testGetPageItemsFilterByCreatorEquals() throws Exception {
		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		User user1 = UserTestUtil.addUser();

		ServiceContext serviceContext1 =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user1.getUserId());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext1);

		User user2 = UserTestUtil.addUser();

		ServiceContext serviceContext2 =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user2.getUserId());

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext2);

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(
				filterParser.parse(
					String.format(
						"(creator eq '/user-account/%s')", user1.getUserId()))),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle, journalArticles.get(0));
	}

	@Test
	public void testGetPageItemsFilterByDateCreatedEquals() throws Exception {
		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(
				filterParser.parse(
					String.format(
						"(dateCreated eq %s)",
						ISO8601Utils.format(journalArticle.getCreateDate())))),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle, journalArticles.get(0));
	}

	@Test
	public void testGetPageItemsFilterByDateCreatedGreaterOrEquals()
		throws Exception {

		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		Thread.sleep(1000);

		Date inBetween = new Date();

		Map<Locale, String> stringMap2 = new HashMap<>();

		stringMap2.put(LocaleUtil.getDefault(), "title2");

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(
				filterParser.parse(
					String.format(
						"(dateCreated ge %s)",
						ISO8601Utils.format(inBetween)))),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle2, journalArticles.get(0));
	}

	@Test
	public void testGetPageItemsFilterByDateCreatedLower() throws Exception {
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

		stringMap2.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(
				filterParser.parse(
					String.format(
						"(dateCreated lt %s)",
						ISO8601Utils.format(journalArticle2.getCreateDate())))),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle1, journalArticles.get(0));
	}

	@Test
	public void testGetPageItemsFilterByDateCreatedLowerOrEquals()
		throws Exception {

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

		Date inBetween = new Date();

		Thread.sleep(1000);

		Map<Locale, String> stringMap2 = new HashMap<>();

		stringMap2.put(LocaleUtil.getDefault(), "title2");

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(
				filterParser.parse(
					String.format(
						"(dateCreated le %s)",
						ISO8601Utils.format(inBetween)))),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle1, journalArticles.get(0));
	}

	@Test
	public void testGetPageItemsFilterByDateModifiedEquals() throws Exception {
		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(
				filterParser.parse(
					String.format(
						"(dateModified eq %s)",
						ISO8601Utils.format(
							journalArticle.getModifiedDate())))),
			Sort.emptySort());

		Assert.assertEquals(
			"Unable to find journal article with modified date: " +
				journalArticle.getModifiedDate() +
					" filtering with dateModified eq " +
						ISO8601Utils.format(journalArticle.getModifiedDate()),
			1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle, journalArticles.get(0));
	}

	@Test
	public void testGetPageItemsFilterByDateModifiedGreaterOrEquals()
		throws Exception {

		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		Thread.sleep(1000);

		Date inBetween = new Date();

		Map<Locale, String> stringMap2 = new HashMap<>();

		stringMap2.put(LocaleUtil.getDefault(), "title2");

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(
				filterParser.parse(
					String.format(
						"(dateModified ge %s)",
						ISO8601Utils.format(inBetween)))),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle2, journalArticles.get(0));
	}

	@Test
	public void testGetPageItemsFilterByDateModifiedLowerOrEquals()
		throws Exception {

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

		Date inBetween = new Date();

		Thread.sleep(1000);

		Map<Locale, String> stringMap2 = new HashMap<>();

		stringMap2.put(LocaleUtil.getDefault(), "title2");

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(
				filterParser.parse(
					String.format(
						"(dateModified le %s)",
						ISO8601Utils.format(inBetween)))),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle1, journalArticles.get(0));
	}

	@Test
	public void testGetPageItemsFilterByDateModifiedUpper() throws Exception {
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

		stringMap2.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(
				filterParser.parse(
					String.format(
						"(dateModified gt %s)",
						ISO8601Utils.format(
							journalArticle1.getModifiedDate())))),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle2, journalArticles.get(0));
	}

	@Test
	public void testGetPageItemsFilterByDatePublishedEquals() throws Exception {
		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		LocalDate localDate1 = LocalDate.of(2018, 02, 20);

		ZonedDateTime zonedDateTime1 = localDate1.atStartOfDay(
			ZoneId.of("GMT"));

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(),
			Date.from(zonedDateTime1.toInstant()), null, true, true,
			serviceContext);

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(
				filterParser.parse(
					String.format(
						"(datePublished eq %s)",
						ISO8601Utils.format(journalArticle.getDisplayDate())))),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle, journalArticles.get(0));
	}

	@Test
	public void testGetPageItemsFilterByDatePublishedGreaterOrEquals()
		throws Exception {

		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		LocalDate localDate1 = LocalDate.of(2018, 02, 20);

		ZonedDateTime zonedDateTime1 = localDate1.atStartOfDay(
			ZoneId.of("GMT"));

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(),
			Date.from(zonedDateTime1.toInstant()), null, true, true,
			serviceContext);

		Map<Locale, String> stringMap2 = new HashMap<>();

		stringMap2.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		LocalDate localDate2 = localDate1.plusDays(2);

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

		FilterParser filterParser = _getFilterParser();

		LocalDate localDateInBetween = localDate1.plusDays(1);

		ZonedDateTime zonedDateTimeInBetween = localDateInBetween.atStartOfDay(
			ZoneId.of("GMT"));

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(
				filterParser.parse(
					String.format(
						"(datePublished ge %s)",
						ISO8601Utils.format(
							Date.from(zonedDateTimeInBetween.toInstant()))))),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle2, journalArticles.get(0));
	}

	@Test
	public void testGetPageItemsFilterByDatePublishedLowerOrEquals()
		throws Exception {

		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		LocalDate localDate1 = LocalDate.of(2018, 02, 20);

		ZonedDateTime zonedDateTime1 = localDate1.atStartOfDay(
			ZoneId.of("GMT"));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(),
			Date.from(zonedDateTime1.toInstant()), null, true, true,
			serviceContext);

		Map<Locale, String> stringMap2 = new HashMap<>();

		stringMap2.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		LocalDate localDate2 = localDate1.plusDays(2);

		ZonedDateTime zonedDateTime2 = localDate2.atStartOfDay(
			ZoneId.of("GMT"));

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(),
			Date.from(zonedDateTime2.toInstant()), null, true, true,
			serviceContext);

		FilterParser filterParser = _getFilterParser();

		LocalDate localDateInBetween = localDate1.plusDays(1);

		ZonedDateTime zonedDateTimeInBetween = localDateInBetween.atStartOfDay(
			ZoneId.of("GMT"));

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(
				filterParser.parse(
					String.format(
						"(datePublished le %s)",
						ISO8601Utils.format(
							Date.from(zonedDateTimeInBetween.toInstant()))))),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle1, journalArticles.get(0));
	}

	@Test
	public void testGetPageItemsFilterByDescription() throws Exception {
		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), "description1");

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

		stringMap2.put(LocaleUtil.getDefault(), "description2");

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(filterParser.parse("(description eq 'description1')")),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle1, journalArticles.get(0));
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

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(filterParser.parse("(title eq 'hello')")),
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

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(filterParser.parse("(title eq 'hello world')")),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertTrue(
			"Items " + journalArticles,
			journalArticles.contains(journalArticle1));
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

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(filterParser.parse("(title eq 'hel')")),
			Sort.emptySort());

		Assert.assertEquals(0, pageItems.getTotalCount());
	}

	@Test
	public void testGetPageItemsFilterByStructureField() throws Exception {
		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(JournalArticle.class), _group);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(JournalArticle.class),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			deserialize(
				_ddmFormDeserializerTracker,
				read("test-journal-all-fields-structure.json")),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			TemplateConstants.LANG_TYPE_VM,
			read("test-journal-all-fields-template.xsl"), LocaleUtil.US);

		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		JournalArticle journalArticle =
			JournalArticleLocalServiceUtil.addArticle(
				TestPropsValues.getUser().getUserId(), _group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, stringMap1,
				null, read("test-journal-all-fields-content.xml"),
				ddmStructure.getStructureKey(), ddmTemplate.getTemplateKey(),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(
				filterParser.parse(
					StringBundler.concat(
						"(values/_", ddmStructure.getStructureId(),
						StringPool.UNDERLINE, "MyText eq ",
						"'TextFieldValue_us')"))),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle, journalArticles.get(0));
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

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(filterParser.parse("(title eq 'title1')")),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle1, journalArticles.get(0));
	}

	@Test
	public void testGetPageItemsFilterByTitleContains() throws Exception {
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

		Map<Locale, String> stringMap3 = new HashMap<>();

		stringMap3.put(LocaleUtil.getDefault(), "withoutContains");

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap3, stringMap3,
			stringMap3, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(filterParser.parse("contains(title,'title')")),
			Sort.emptySort());

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertTrue(journalArticles.contains(journalArticle1));
		Assert.assertTrue(journalArticles.contains(journalArticle2));
	}

	@Test
	public void testGetPageItemsFilterByTitleGreaterOrEqual() throws Exception {
		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), "title1");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		Map<Locale, String> stringMap2 = new HashMap<>();

		stringMap2.put(LocaleUtil.getDefault(), "title2");

		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		Map<Locale, String> stringMap3 = new HashMap<>();

		stringMap3.put(LocaleUtil.getDefault(), "title3");

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap3, stringMap3,
			stringMap3, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(filterParser.parse("(title ge 'title2')")),
			Sort.emptySort());

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertTrue(journalArticles.contains(journalArticle1));
		Assert.assertTrue(journalArticles.contains(journalArticle2));
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

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(filterParser.parse("(title eq 'titulo1')")),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle, journalArticles.get(0));
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

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(filterParser.parse("(title eq 'titulo1')")),
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

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(),
			() -> LocaleUtil.SPAIN, getThemeDisplay(_group, LocaleUtil.SPAIN),
			new Filter(filterParser.parse("(title eq 'titulo1')")),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle, journalArticles.get(0));
	}

	@Test
	public void testGetPageItemsFilterByTitleLower() throws Exception {
		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), "title1");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		Thread.sleep(1000);

		Map<Locale, String> stringMap2 = new HashMap<>();

		stringMap2.put(LocaleUtil.getDefault(), "title2");

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(filterParser.parse("(title lt 'title2')")),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle, journalArticles.get(0));
	}

	@Test
	public void testGetPageItemsFilterByTitleLowerOrEqual() throws Exception {
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

		Map<Locale, String> stringMap3 = new HashMap<>();

		stringMap3.put(LocaleUtil.getDefault(), "title3");

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap3, stringMap3,
			stringMap3, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(filterParser.parse("(title le 'title2')")),
			Sort.emptySort());

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertTrue(journalArticles.contains(journalArticle1));
		Assert.assertTrue(journalArticles.contains(journalArticle2));
	}

	@Test
	public void testGetPageItemsFilterByTitleLowerOrEqualAndGreaterOrEqual()
		throws Exception {

		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), "title1");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		Map<Locale, String> stringMap2 = new HashMap<>();

		stringMap2.put(LocaleUtil.getDefault(), "title2");

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		Map<Locale, String> stringMap3 = new HashMap<>();

		stringMap3.put(LocaleUtil.getDefault(), "title3");

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap3, stringMap3,
			stringMap3, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(
				filterParser.parse(
					"(title le 'title2') and (title ge 'title2')")),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertTrue(journalArticles.contains(journalArticle));
	}

	@Test
	public void testGetPageItemsFilterByTitleLowerOrEqualOrGreaterOrEqual()
		throws Exception {

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

		Map<Locale, String> stringMap3 = new HashMap<>();

		stringMap3.put(LocaleUtil.getDefault(), "title3");

		JournalArticle journalArticle3 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap3, stringMap3,
			stringMap3, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(
				filterParser.parse(
					"(title le 'title2') or (title ge 'title2')")),
			Sort.emptySort());

		Assert.assertEquals(3, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertTrue(journalArticles.contains(journalArticle1));
		Assert.assertTrue(journalArticles.contains(journalArticle2));
		Assert.assertTrue(journalArticles.contains(journalArticle3));
	}

	@Test
	public void testGetPageItemsFilterByTitleStartingWithLowerCase()
		throws Exception {

		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), "great title");

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

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(filterParser.parse("(title eq 'great title')")),
			Sort.emptySort());

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertTrue(journalArticles.contains(journalArticle1));
		Assert.assertTrue(journalArticles.contains(journalArticle2));
	}

	@Test
	public void testGetPageItemsFilterByTitleStartingWithUpperCase()
		throws Exception {

		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), "great title");

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

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(filterParser.parse("(title eq 'Great Title')")),
			Sort.emptySort());

		Assert.assertEquals(2, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertTrue(journalArticles.contains(journalArticle1));
		Assert.assertTrue(journalArticles.contains(journalArticle2));
	}

	@Test
	public void testGetPageItemsFilterByTitleUpper() throws Exception {
		Map<Locale, String> stringMap1 = new HashMap<>();

		stringMap1.put(LocaleUtil.getDefault(), "title1");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap1, stringMap1,
			stringMap1, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		Thread.sleep(1000);

		Map<Locale, String> stringMap2 = new HashMap<>();

		stringMap2.put(LocaleUtil.getDefault(), "title2");

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap2, stringMap2,
			stringMap2, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		FilterParser filterParser = _getFilterParser();

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			new Filter(filterParser.parse("(title gt 'title1')")),
			Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertEquals(journalArticle, journalArticles.get(0));
	}

	@Test
	public void testGetSearchFilterWithExistingProperty() throws Exception {
		FilterParser filterParser = _getFilterParser();

		TermFilter termQuery = (TermFilter)getSearchFilter(
			new Filter(filterParser.parse("title eq 'Title Value'")),
			LocaleUtil.US);

		Assert.assertEquals(
			"localized_title_en_US_sortable", termQuery.getField());
		Assert.assertEquals("title value", termQuery.getValue());
	}

	private FilterParser _getFilterParser() {
		return _serviceTracker.getService();
	}

	private static final AcceptLanguage _acceptLanguage =
		() -> LocaleUtil.getDefault();
	private static ServiceTracker<FilterParser, FilterParser> _serviceTracker;

	@Inject
	private DDMFormDeserializerTracker _ddmFormDeserializerTracker;

	@DeleteAfterTestRun
	private Group _group;

}