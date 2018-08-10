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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.structured.content.apio.architect.util.test.PaginationTestUtil;

import java.lang.reflect.Method;

import java.util.Collection;
import java.util.HashMap;
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
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		Map<Locale, String> keywordsMap = new HashMap<>();

		String keywords = "keywords";

		keywordsMap.put(LocaleUtil.getDefault(), keywords);
		keywordsMap.put(LocaleUtil.GERMANY, keywords);
		keywordsMap.put(LocaleUtil.SPAIN, keywords);

		String articleId = "Article.Id";

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, articleId, false,
			keywordsMap, keywordsMap, keywordsMap, null,
			LocaleUtil.getDefault(), null, true, true, serviceContext);

		PageItems pageItems = _getPageItems(
			PaginationTestUtil.of(10, 1), _group.getGroupId(), null);

		Assert.assertEquals(1, pageItems.getTotalCount());

		Collection items = pageItems.getItems();

		Assert.assertTrue("Items " + items, items.contains(journalArticle));
	}

	private PageItems _getPageItems(
			Pagination pagination, long contentSpaceId,
			ThemeDisplay themeDisplay)
		throws Exception {

		Class<? extends NestedCollectionResource>
			nestedCollectionResourceClass =
				_nestedCollectionResource.getClass();

		Method getPageItems = nestedCollectionResourceClass.getDeclaredMethod(
			"_getPageItems", Pagination.class, long.class, ThemeDisplay.class);

		getPageItems.setAccessible(true);

		return (PageItems)getPageItems.invoke(
			_nestedCollectionResource, pagination, contentSpaceId,
			themeDisplay);
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject(
		filter = "component.name=com.liferay.structured.content.apio.internal.architect.resource.StructuredContentNestedCollectionResource"
	)
	private NestedCollectionResource _nestedCollectionResource;

}