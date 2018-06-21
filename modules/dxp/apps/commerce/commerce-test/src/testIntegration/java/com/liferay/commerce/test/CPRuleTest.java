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

package com.liferay.commerce.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPQuery;
import com.liferay.commerce.product.data.source.CPDataSourceResult;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPRule;
import com.liferay.commerce.product.rule.test.util.CPRuleTestUtil;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.io.Serializable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
public class CPRuleTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user1 = UserTestUtil.addUser();

		_user2 = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user1);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);
	}

	@Test
	public void testUserSegmentCategoryCPRuleDefinitionHelper()
		throws Exception {

		frutillaRule.scenario(
			"Search for CatalogEntries accessible from a given user"
		).given(
			"A user, a category with a product"
		).when(
			"I try to get the CatalogEntries that the user can view"
		).then(
			"The results will contain only accessible element from that " +
				"specific user"
		);

		CPRule cpRule = CPRuleTestUtil.addUserSegmentCPRule(
			_group.getGroupId(), _user1.getUserId());

		AssetCategory assetCategory = CPRuleTestUtil.addAssetCategoryToCPRule(
			cpRule);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		CPTestUtil.addCPInstance(_group.getGroupId());
		CPTestUtil.addCPInstance(_group.getGroupId());

		CPDefinition expectedCPDefinition = cpInstance.getCPDefinition();

		CPRuleTestUtil.addCategoryToCPDefinition(
			expectedCPDefinition, assetCategory.getCategoryId());

		SearchContext searchContext = _getSearchContext(
			null, WorkflowConstants.STATUS_APPROVED);

		CPQuery cpQuery = new CPQuery();

		CPDataSourceResult cpDataSourceResult = _cpDefinitionHelper.search(
			_group.getGroupId(), searchContext, cpQuery, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		List<CPCatalogEntry> cpCatalogEntryList =
			cpDataSourceResult.getCPCatalogEntries();

		Assert.assertEquals(
			cpCatalogEntryList.toString(), 1, cpCatalogEntryList.size());

		CPCatalogEntry actualCPCatalogEntry = cpCatalogEntryList.get(0);

		Assert.assertEquals(
			expectedCPDefinition.getCPDefinitionId(),
			actualCPCatalogEntry.getCPDefinitionId());
	}

	@Test
	public void testUserSegmentCategoryCPRuleService() throws Exception {
		frutillaRule.scenario(
			"Get a CatalogEntries accessible from a given user"
		).given(
			"A user, a category with a product"
		).when(
			"I try to get the CatalogEntrie that the user can view"
		).then(
			"The results will contain the accessible element from that " +
				"specific user"
		);

		CPRule cpRule = CPRuleTestUtil.addUserSegmentCPRule(
			_group.getGroupId(), _user1.getUserId());

		AssetCategory assetCategory = CPRuleTestUtil.addAssetCategoryToCPRule(
			cpRule);

		CPRuleTestUtil.setCPRulesThreadLocal(
			_group.getGroupId(), 0, _user1.getUserId());

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		CPDefinition expectedCPDefinition = cpInstance.getCPDefinition();

		CPRuleTestUtil.addCategoryToCPDefinition(
			expectedCPDefinition, assetCategory.getCategoryId());

		CPDefinition actualCPDefinition = _cpDefinitionService.getCPDefinition(
			expectedCPDefinition.getCPDefinitionId());

		Assert.assertEquals(
			expectedCPDefinition.getCPDefinitionId(),
			actualCPDefinition.getCPDefinitionId());
	}

	@Test
	public void testWrongUserSegmentCategoryCPRuleDefinitionHelper()
		throws Exception {

		frutillaRule.scenario(
			"Search for CatalogEntries not accessible from a given user"
		).given(
			"A user, a category with a product"
		).when(
			"I try to get the CatalogEntries that a user can view"
		).then(
			"The results will be empty since the customer cannot access any " +
				"element"
		);

		CPRule cpRule = CPRuleTestUtil.addUserSegmentCPRule(
			_group.getGroupId(), _user2.getUserId());

		AssetCategory assetCategory = CPRuleTestUtil.addAssetCategoryToCPRule(
			cpRule);

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		CPTestUtil.addCPInstance(_group.getGroupId());
		CPTestUtil.addCPInstance(_group.getGroupId());

		CPDefinition expectedCPDefinition = cpInstance.getCPDefinition();

		CPRuleTestUtil.addCategoryToCPDefinition(
			expectedCPDefinition, assetCategory.getCategoryId());

		SearchContext searchContext = _getSearchContext(
			null, WorkflowConstants.STATUS_APPROVED);

		CPQuery cpQuery = new CPQuery();

		CPDataSourceResult cpDataSourceResult = _cpDefinitionHelper.search(
			_group.getGroupId(), searchContext, cpQuery, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		List<CPCatalogEntry> cpCatalogEntryList =
			cpDataSourceResult.getCPCatalogEntries();

		Assert.assertEquals(
			cpCatalogEntryList.toString(), 0, cpCatalogEntryList.size());
	}

	@Test(expected = PrincipalException.class)
	public void testWrongUserSegmentCategoryCPRuleService() throws Exception {
		frutillaRule.scenario(
			"Try to get a CatalogEntries not accessible from a given user"
		).given(
			"A user, a category with a product"
		).when(
			"I try to get the CatalogEntrie that the user cannot view"
		).then(
			"The results will be a PrincipalException caused by the " +
				"permission checks"
		);

		CPRule cpRule = CPRuleTestUtil.addUserSegmentCPRule(
			_group.getGroupId(), _user2.getUserId());

		AssetCategory assetCategory = CPRuleTestUtil.addAssetCategoryToCPRule(
			cpRule);

		CPRuleTestUtil.setCPRulesThreadLocal(
			_group.getGroupId(), 0, _user1.getUserId());

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		CPDefinition expectedCPDefinition = cpInstance.getCPDefinition();

		CPRuleTestUtil.addCategoryToCPDefinition(
			expectedCPDefinition, assetCategory.getCategoryId());

		_cpDefinitionService.getCPDefinition(
			expectedCPDefinition.getCPDefinitionId());
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private SearchContext _getSearchContext(String keywords, int status) {
		SearchContext searchContext = new SearchContext();

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		if (Validator.isNotNull(keywords)) {
			params.put("keywords", keywords);
		}
		else {
			params.put("keywords", StringPool.STAR);
		}

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(Field.STATUS, status);
		attributes.put("params", params);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(_group.getCompanyId());
		searchContext.setGroupIds(new long[] {_group.getGroupId()});

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}
		else {
			searchContext.setKeywords(StringPool.STAR);
		}

		return searchContext;
	}

	@Inject
	private CPDefinitionHelper _cpDefinitionHelper;

	@Inject
	private CPDefinitionService _cpDefinitionService;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user1;

	@DeleteAfterTestRun
	private User _user2;

}