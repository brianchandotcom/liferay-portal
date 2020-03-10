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

package com.liferay.asset.tags.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.users.admin.test.util.search.OrganizationBlueprint.OrganizationBlueprintBuilder;
import com.liferay.users.admin.test.util.search.OrganizationSearchFixture;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Samuel Trong Tran
 */
@RunWith(Arquillian.class)
@Sync
public class AssetTagNamesOrganizationSearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		WorkflowThreadLocal.setEnabled(false);

		organizationSearchFixture = new OrganizationSearchFixture(
			organizationLocalService, assetTagLocalService);

		_assetTags = organizationSearchFixture.getAssetTags();
		_organizations = organizationSearchFixture.getOrganizations();
	}

	@Test
	public void testSearchOrganizationByTag() throws Exception {
		String tag = "searchtag";

		String[] newAssetTagNames = {tag};

		OrganizationBlueprintBuilder organizationBlueprintBuilder =
			OrganizationSearchFixture.getTestOrganizationBlueprintBuilder();

		Organization organization = organizationSearchFixture.addOrganization(
			organizationBlueprintBuilder.assetTagNames(
				newAssetTagNames
			).build());

		assertCount(organization.getCompanyId(), 1, tag);
	}

	protected void assertCount(
			long companyId, int expectedCount, String keywords)
		throws Exception {

		LinkedHashMap<String, Object> organizationParams =
			LinkedHashMapBuilder.<String, Object>put(
				"expandoAttributes", keywords
			).build();

		Sort sort = SortFactoryUtil.getSort(Organization.class, "name", "desc");

		BaseModelSearchResult<Organization> baseModelSearchResult =
			organizationLocalService.searchOrganizations(
				companyId, OrganizationConstants.ANY_PARENT_ORGANIZATION_ID,
				keywords, organizationParams, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, sort);

		Assert.assertEquals(
			baseModelSearchResult.toString(), expectedCount,
			baseModelSearchResult.getLength());
	}

	@Inject
	protected AssetTagLocalService assetTagLocalService;

	@Inject
	protected OrganizationLocalService organizationLocalService;

	protected OrganizationSearchFixture organizationSearchFixture;

	@DeleteAfterTestRun
	private List<AssetTag> _assetTags;

	@DeleteAfterTestRun
	private List<Organization> _organizations;

}