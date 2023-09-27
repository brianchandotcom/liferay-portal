/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.retriever.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.retriever.AccountOrganizationRetriever;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.comparator.OrganizationNameComparator;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class AccountOrganizationRetrieverTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_accountEntry = AccountEntryTestUtil.addAccountEntry();
	}

	@Test
	public void testSearchAccountOrganizations() throws Exception {
		_organizations.add(OrganizationTestUtil.addOrganization());
		_organizations.add(OrganizationTestUtil.addOrganization());
		_organizations.add(OrganizationTestUtil.addOrganization());

		for (Organization organization : _organizations) {
			_accountEntryOrganizationRels.add(
				_accountEntryOrganizationRelLocalService.
					addAccountEntryOrganizationRel(
						_accountEntry.getAccountEntryId(),
						organization.getOrganizationId()));
		}

		BaseModelSearchResult<Organization> baseModelSearchResult =
			_accountOrganizationRetriever.searchAccountOrganizations(
				_accountEntry.getAccountEntryId(), null, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, "organizationId", false);

		Assert.assertEquals(
			_organizations, baseModelSearchResult.getBaseModels());
	}

	@Test
	public void testSearchAccountOrganizationsByKeywords() throws Exception {
		_organizations.add(OrganizationTestUtil.addOrganization());

		String keywords = RandomTestUtil.randomString();

		Organization expectedOrganization =
			OrganizationTestUtil.addOrganization(
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
				keywords + RandomTestUtil.randomString(), false);

		_organizations.add(expectedOrganization);

		for (Organization organization : _organizations) {
			_accountEntryOrganizationRels.add(
				_accountEntryOrganizationRelLocalService.
					addAccountEntryOrganizationRel(
						_accountEntry.getAccountEntryId(),
						organization.getOrganizationId()));
		}

		BaseModelSearchResult<Organization> baseModelSearchResult =
			_accountOrganizationRetriever.searchAccountOrganizations(
				_accountEntry.getAccountEntryId(), keywords, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null, false);

		Assert.assertEquals(1, baseModelSearchResult.getLength());

		List<Organization> organizations =
			baseModelSearchResult.getBaseModels();

		Assert.assertEquals(expectedOrganization, organizations.get(0));
	}

	@Test
	public void testSearchAccountOrganizationsWithNoAccountOrganizations()
		throws Exception {

		_organizations.add(OrganizationTestUtil.addOrganization());

		BaseModelSearchResult<Organization> baseModelSearchResult =
			_accountOrganizationRetriever.searchAccountOrganizations(
				_accountEntry.getAccountEntryId(), null, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null, false);

		Assert.assertEquals(0, baseModelSearchResult.getLength());
		Assert.assertTrue(
			ListUtil.isEmpty(baseModelSearchResult.getBaseModels()));
	}

	@Test
	public void testSearchAccountOrganizationsWithPagination()
		throws Exception {

		String keywords = RandomTestUtil.randomString();

		for (int i = 1; i < 5; i++) {
			String name = keywords + i;

			Organization organization = OrganizationTestUtil.addOrganization(
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID, name,
				false);

			_organizations.add(organization);

			_accountEntryOrganizationRels.add(
				_accountEntryOrganizationRelLocalService.
					addAccountEntryOrganizationRel(
						_accountEntry.getAccountEntryId(),
						organization.getOrganizationId()));
		}

		// Test paginated search has a partial list, but full count

		BaseModelSearchResult<Organization> baseModelSearchResult =
			_accountOrganizationRetriever.searchAccountOrganizations(
				_accountEntry.getAccountEntryId(), keywords, 1, 2, "name",
				false);

		Assert.assertEquals(4, baseModelSearchResult.getLength());

		List<Organization> organizations =
			baseModelSearchResult.getBaseModels();

		Assert.assertEquals(organizations.toString(), 2, organizations.size());
		Assert.assertEquals(_organizations.get(1), organizations.get(0));

		// Test reversed sorting

		baseModelSearchResult =
			_accountOrganizationRetriever.searchAccountOrganizations(
				_accountEntry.getAccountEntryId(), keywords, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, "name", true);

		Assert.assertEquals(4, baseModelSearchResult.getLength());

		organizations = baseModelSearchResult.getBaseModels();

		Assert.assertEquals(organizations.toString(), 4, organizations.size());
		Assert.assertEquals(
			ListUtil.sort(
				_organizations, new OrganizationNameComparator(false)),
			organizations);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@DeleteAfterTestRun
	private final List<AccountEntryOrganizationRel>
		_accountEntryOrganizationRels = new ArrayList<>();

	@Inject
	private AccountOrganizationRetriever _accountOrganizationRetriever;

	@DeleteAfterTestRun
	private final List<Organization> _organizations = new ArrayList<>();

}