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

package com.liferay.portal.search.facet.faceted.searcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.search.facet.category.CategoryFacetFactory;
import com.liferay.portal.search.test.util.FacetsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.Map;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Dylan Rebelak
 */
@RunWith(Arquillian.class)
@Sync
public class CategoryFacetTest extends BaseFacetedSearcherTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_group = GroupTestUtil.addGroup();

		_assetVocabulary = assetVocabularyLocalService.addDefaultVocabulary(
			_group.getGroupId());
	}

	@Test
	public void testAggregation() throws Exception {
		String title = RandomTestUtil.randomString();

		AssetCategory assetCategory = addCategory(title);

		long categoryId = assetCategory.getCategoryId();

		addUser(_group, categoryId);

		SearchContext searchContext = getSearchContext(
			assetCategory.getTitleCurrentValue());

		searchContext.setCategoryIds(new long[] {categoryId});
		searchContext.setGroupIds(new long[] {_group.getGroupId()});

		Facet facet = categoryFacetFactory.newInstance(searchContext);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		Map<String, Integer> frequencies = Collections.singletonMap(
			String.valueOf(categoryId), 1);

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits, frequencies);
	}

	@Test
	public void testAvoidResidualDataFromDDMStructureLocalServiceTest()
		throws Exception {

		// See LPS-58543

		String title = "To Do";

		AssetCategory assetCategory = addCategory(title);

		long categoryId = assetCategory.getCategoryId();

		addUser(_group, categoryId);

		SearchContext searchContext = getSearchContext(
			assetCategory.getTitleCurrentValue());

		searchContext.setCategoryIds(new long[] {categoryId});
		searchContext.setGroupIds(new long[] {_group.getGroupId()});

		Facet facet = categoryFacetFactory.newInstance(searchContext);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		Map<String, Integer> frequencies = Collections.singletonMap(
			String.valueOf(categoryId), 1);

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits, frequencies);
	}

	protected AssetCategory addCategory(String title) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		AssetCategory assetCategory = assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(), title,
			_assetVocabulary.getVocabularyId(), serviceContext);

		_assetCategory = assetCategory;

		return assetCategory;
	}

	protected void addUser(Group group, long... categoryIds) throws Exception {
		_user = UserTestUtil.addUser(group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setAssetCategoryIds(categoryIds);

		UserTestUtil.updateUser(_user, serviceContext);
	}

	@Inject
	protected AssetCategoryLocalService assetCategoryLocalService;

	@Inject
	protected AssetVocabularyLocalService assetVocabularyLocalService;

	@Inject
	protected CategoryFacetFactory categoryFacetFactory;

	@DeleteAfterTestRun
	private AssetCategory _assetCategory;

	@DeleteAfterTestRun
	private AssetVocabulary _assetVocabulary;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}