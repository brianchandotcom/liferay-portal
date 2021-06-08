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

package com.liferay.commerce.product.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPQuery;
import com.liferay.commerce.product.data.source.CPDataSourceResult;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.List;

import org.frutilla.FrutillaRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luca Pellizzon
 */
@RunWith(Arquillian.class)
public class CPDefinitionHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = CompanyTestUtil.addCompany();
	}

	@Before
	public void setUp() throws Exception {
		_commerceCatalog = _commerceCatalogLocalService.addCommerceCatalog(
			null, RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			LocaleUtil.toLanguageId(LocaleUtil.US),
			ServiceContextTestUtil.getServiceContext(_company.getGroupId()));
	}

	@After
	public void tearDown() throws Exception {
		List<CPDefinition> cpDefinitions =
			_cpDefinitionLocalService.getCPDefinitions(
				_commerceCatalog.getGroupId(), WorkflowConstants.STATUS_ANY,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CPDefinition cpDefinition : cpDefinitions) {
			_cpDefinitionLocalService.deleteCPDefinition(cpDefinition);
		}

		_commerceCatalogLocalService.deleteCommerceCatalogs(
			_company.getCompanyId());
	}

	@Test
	public void testSearchAllCPDefinitions() throws PortalException {
		frutillaRule.scenario(
			"Search for CPDefinitions without filters"
		).given(
			"A collection of CPDefinitions"
		).when(
			"I search for CPDefinitions without filters"
		).then(
			"The results will contain all the products available"
		);

		CPInstance[] cpInstances = _addCPInstances(
			_commerceCatalog.getGroupId(), _CP_INSTANCES_COUNT);

		SearchContext searchContext = CPTestUtil.getSearchContext(
			null, WorkflowConstants.STATUS_APPROVED,
			_commerceCatalog.getGroup());

		CPDataSourceResult cpDataSourceResult = _cpDefinitionHelper.search(
			_commerceCatalog.getGroupId(), searchContext, new CPQuery(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		List<CPCatalogEntry> cpCatalogEntries =
			cpDataSourceResult.getCPCatalogEntries();

		List<Long> cpDefinitionIds = new ArrayList<>();

		for (CPInstance cpInstance : cpInstances) {
			CPDefinition cpDefinition = cpInstance.getCPDefinition();

			cpDefinitionIds.add(cpDefinition.getCPDefinitionId());
		}

		List<Long> actualCPDefinitionIds = new ArrayList<>();

		for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
			actualCPDefinitionIds.add(cpCatalogEntry.getCPDefinitionId());
		}

		Assert.assertTrue(actualCPDefinitionIds.containsAll(cpDefinitionIds));
	}

	@Test
	public void testSearchCPDefinitionsByCategory() throws PortalException {
		frutillaRule.scenario(
			"Search for CPDefinition by Category"
		).given(
			"A collection of CPDefinitions"
		).and(
			"A category"
		).and(
			"Some of the CPDefinitions are associated to that category"
		).when(
			"I search for CPDefinitions given the category as a search filter"
		).then(
			"The results will contain only those product linked to the category"
		);

		CPInstance[] cpInstances = _addCPInstances(
			_commerceCatalog.getGroupId(), _CP_INSTANCES_COUNT);

		int counter = 0;
		int position = 0;

		long[] cpDefinitionIds = new long[_CP_INSTANCES_COUNT / 2];

		for (CPInstance cpInstance : cpInstances) {
			if ((counter % 2) == 0) {
				CPDefinition cpDefinition = cpInstance.getCPDefinition();

				cpDefinitionIds[position] = cpDefinition.getCPDefinitionId();

				position++;
			}

			counter++;
		}

		AssetCategory assetCategory = CPTestUtil.addCategoryToCPDefinitions(
			_commerceCatalog.getGroupId(), cpDefinitionIds);

		SearchContext searchContext = CPTestUtil.getSearchContext(
			null, WorkflowConstants.STATUS_APPROVED,
			_commerceCatalog.getGroup());

		CPQuery cpQuery = new CPQuery();

		cpQuery.setAllCategoryIds(new long[] {assetCategory.getCategoryId()});

		CPDataSourceResult cpDataSourceResult = _cpDefinitionHelper.search(
			_commerceCatalog.getGroupId(), searchContext, cpQuery,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		List<CPCatalogEntry> cpCatalogEntries =
			cpDataSourceResult.getCPCatalogEntries();

		Assert.assertEquals(
			cpCatalogEntries.toString(), cpDefinitionIds.length,
			cpCatalogEntries.size());

		List<Long> actualCPDefinitionIds = new ArrayList<>();

		for (CPCatalogEntry cpCatalogEntry : cpCatalogEntries) {
			actualCPDefinitionIds.add(cpCatalogEntry.getCPDefinitionId());
		}

		Long[] cpDefinitionIdsLong = ArrayUtil.toArray(cpDefinitionIds);

		List<Long> cpDefinitionIdsList = ListUtil.fromArray(
			cpDefinitionIdsLong);

		Assert.assertTrue(
			actualCPDefinitionIds.containsAll(cpDefinitionIdsList));

		AssetCategoryLocalServiceUtil.deleteAssetCategory(
			assetCategory.getCategoryId());
	}

	@Test
	public void testSearchCPDefinitionsByName() throws PortalException {
		frutillaRule.scenario(
			"Search for CPDefinitions by name"
		).given(
			"A collection of CPDefinitions"
		).when(
			"I search for CPDefinitions given the name as a search filter"
		).then(
			"The results will contain only those product with the name equal " +
				"to the one in the search filter. In this specific case 1"
		);

		CPInstance[] cpInstances = _addCPInstances(
			_commerceCatalog.getGroupId(), _CP_INSTANCES_COUNT);

		int random = (int)(Math.random() * (_CP_INSTANCES_COUNT - 1));

		CPInstance randomCPInstance = cpInstances[random];

		CPDefinition cpDefinition = randomCPInstance.getCPDefinition();

		SearchContext searchContext = CPTestUtil.getSearchContext(
			cpDefinition.getName(), WorkflowConstants.STATUS_APPROVED,
			_commerceCatalog.getGroup());

		CPDataSourceResult cpDataSourceResult = _cpDefinitionHelper.search(
			_commerceCatalog.getGroupId(), searchContext, new CPQuery(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		List<CPCatalogEntry> cpCatalogEntries =
			cpDataSourceResult.getCPCatalogEntries();

		Assert.assertEquals(
			cpCatalogEntries.toString(), 1, cpCatalogEntries.size());

		CPCatalogEntry cpCatalogEntry = cpCatalogEntries.get(0);

		Assert.assertEquals(
			cpDefinition.getCPDefinitionId(),
			cpCatalogEntry.getCPDefinitionId());
	}

	@Rule
	public final FrutillaRule frutillaRule = new FrutillaRule();

	private CPInstance[] _addCPInstances(long groupId, int iterations)
		throws PortalException {

		CPInstance[] cpInstances = new CPInstance[iterations];

		for (int i = 0; i < iterations; i++) {
			cpInstances[i] = CPTestUtil.addCPInstanceFromCatalog(groupId);
		}

		return cpInstances;
	}

	private static final int _CP_INSTANCES_COUNT = 10;

	private static Company _company;

	private CommerceCatalog _commerceCatalog;

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Inject
	private CPDefinitionHelper _cpDefinitionHelper;

	@Inject
	private CPDefinitionLocalService _cpDefinitionLocalService;

}