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

package com.liferay.commerce.product.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueLocalService;
import com.liferay.commerce.product.service.CPOptionCategoryLocalService;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.product.type.simple.constants.SimpleCPTypeConstants;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.SimpleFacet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.util.ArrayList;
import java.util.List;

import org.frutilla.FrutillaRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luca Pellizzon
 */
@RunWith(Arquillian.class)
public class SpecificationOptionFacetsTest {

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
	public void testGetFacets() throws Exception {
		frutillaRule.scenario(
			"Add a facetable product specification"
		).given(
			"A group"
		).and(
			"A product"
		).when(
			"I add a specification option flagged as facetable"
		).then(
			"I should be able to find the facet related to that specification"
		);

		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
			_group.getGroupId(), SimpleCPTypeConstants.NAME, false, true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		CPOptionCategory cpOptionCategory =
			_cpOptionCategoryLocalService.addCPOptionCategory(
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomDouble(), RandomTestUtil.randomString(),
				serviceContext);

		CPSpecificationOption cpSpecificationOption =
			_cpSpecificationOptionLocalService.addCPSpecificationOption(
				cpOptionCategory.getCPOptionCategoryId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), true,
				RandomTestUtil.randomString(), serviceContext);

		_cpDefinitionSpecificationOptionValueLocalService.
			addCPDefinitionSpecificationOptionValue(
				cpDefinition.getCPDefinitionId(),
				cpSpecificationOption.getCPSpecificationOptionId(),
				cpOptionCategory.getCPOptionCategoryId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomDouble(), serviceContext);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		Facet facet = new SimpleFacet(searchContext);

		facet.setFieldName("specificationOptionsIds");

		searchContext.addFacet(facet);

		Indexer<CPDefinition> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPDefinition.class);

		indexer.search(searchContext);

		FacetCollector facetCollector = facet.getFacetCollector();

		List<Long> terms = new ArrayList<>();

		for (TermCollector termCollector : facetCollector.getTermCollectors()) {
			terms.add(Long.valueOf(termCollector.getTerm()));
		}

		long expectedTerm = cpSpecificationOption.getCPSpecificationOptionId();

		Assert.assertEquals(true, terms.contains(expectedTerm));
	}

	@Test
	public void testGetFacetsWithSpecificationOptionNotFacetable()
		throws Exception {

		frutillaRule.scenario(
			"Add a facetable product specification"
		).given(
			"A group"
		).and(
			"A product"
		).when(
			"I add a specification option not flagged as facetable"
		).then(
			"I should not be able to find any facet related to that " +
				"specification"
		);

		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
			_group.getGroupId(), SimpleCPTypeConstants.NAME, false, true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		CPOptionCategory cpOptionCategory =
			_cpOptionCategoryLocalService.addCPOptionCategory(
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomDouble(), RandomTestUtil.randomString(),
				serviceContext);

		CPSpecificationOption cpSpecificationOption =
			_cpSpecificationOptionLocalService.addCPSpecificationOption(
				cpOptionCategory.getCPOptionCategoryId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), false,
				RandomTestUtil.randomString(), serviceContext);

		_cpDefinitionSpecificationOptionValueLocalService.
			addCPDefinitionSpecificationOptionValue(
				cpDefinition.getCPDefinitionId(),
				cpSpecificationOption.getCPSpecificationOptionId(),
				cpOptionCategory.getCPOptionCategoryId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomDouble(), serviceContext);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		Facet facet = new SimpleFacet(searchContext);

		facet.setFieldName("specificationOptionsIds");

		searchContext.addFacet(facet);

		Indexer<CPDefinition> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPDefinition.class);

		indexer.search(searchContext);

		FacetCollector facetCollector = facet.getFacetCollector();

		List<TermCollector> termCollector = facetCollector.getTermCollectors();

		Assert.assertEquals(true, termCollector.isEmpty());
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	@Inject
	private CPDefinitionHelper _cpDefinitionHelper;

	@Inject
	private CPDefinitionSpecificationOptionValueLocalService
		_cpDefinitionSpecificationOptionValueLocalService;

	@Inject
	private CPOptionCategoryLocalService _cpOptionCategoryLocalService;

	@Inject
	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;

	@DeleteAfterTestRun
	private Group _group;

}