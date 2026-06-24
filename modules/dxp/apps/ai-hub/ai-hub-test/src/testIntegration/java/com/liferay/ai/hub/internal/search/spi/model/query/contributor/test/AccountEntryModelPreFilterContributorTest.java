/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.search.spi.model.query.contributor.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.test.rule.SearchTestRule;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@FeatureFlag("LPD-62272")
@RunWith(Arquillian.class)
public class AccountEntryModelPreFilterContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_aiHubAccountEntry = _fetchOrAddAccountEntry("L_AI_HUB");
		_seoStudioAccountEntry = _fetchOrAddAccountEntry("L_SEO_STUDIO");
	}

	@Test
	public void testContribute() throws Exception {
		AccountEntry accountEntry = _addAccountEntry(
			RandomTestUtil.randomString());

		BaseModelSearchResult<AccountEntry> baseModelSearchResult =
			_accountEntryLocalService.searchAccountEntries(
				TestPropsValues.getCompanyId(), null, new LinkedHashMap<>(), 0,
				QueryUtil.ALL_POS, "name", false);

		List<AccountEntry> accountEntries =
			baseModelSearchResult.getBaseModels();

		Assert.assertTrue(
			accountEntries.toString(), accountEntries.contains(accountEntry));
		Assert.assertFalse(
			accountEntries.toString(),
			accountEntries.contains(_aiHubAccountEntry));
		Assert.assertFalse(
			accountEntries.toString(),
			accountEntries.contains(_seoStudioAccountEntry));
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	private AccountEntry _addAccountEntry(String externalReferenceCode)
		throws Exception {

		return _accountEntryLocalService.addAccountEntry(
			externalReferenceCode, TestPropsValues.getUserId(),
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), null, null, null, null, null,
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());
	}

	private AccountEntry _fetchOrAddAccountEntry(String externalReferenceCode)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryLocalService.fetchAccountEntryByExternalReferenceCode(
				externalReferenceCode, TestPropsValues.getCompanyId());

		if (accountEntry == null) {
			accountEntry = _addAccountEntry(externalReferenceCode);
		}

		return accountEntry;
	}

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	private AccountEntry _aiHubAccountEntry;
	private AccountEntry _seoStudioAccountEntry;

}