/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.feature.flag.test;

import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CPConfigurationEntry;
import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.commerce.product.model.CPConfigurationListRel;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CPConfigurationEntryLocalService;
import com.liferay.commerce.product.service.CPConfigurationListLocalService;
import com.liferay.commerce.product.service.CPConfigurationListRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.service.CommerceChannelRelLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.product.type.simple.constants.SimpleCPTypeConstants;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagListener;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@FeatureFlags("LPD-10889")
@RunWith(Arquillian.class)
public class CPConfigurationListEligibilityFeatureFlagListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testCPConfigurationListEligibility() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		CommerceCatalog commerceCatalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				LocaleUtil.US.getDisplayLanguage(), serviceContext);

		CommerceChannel commerceChannel1 =
			_commerceChannelLocalService.addCommerceChannel(
				null, 0, TestPropsValues.getGroupId(),
				RandomTestUtil.randomString(),
				CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
				commerceCatalog.getCommerceCurrencyCode(), serviceContext);

		CPDefinition cpDefinition1 = CPTestUtil.addCPDefinitionFromCatalog(
			commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, false,
			false);

		_commerceChannelRelLocalService.addCommerceChannelRel(
			CPDefinition.class.getName(), cpDefinition1.getCPDefinitionId(),
			commerceChannel1.getCommerceChannelId(), serviceContext);

		AccountGroup accountGroup1 = _accountGroupLocalService.addAccountGroup(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);
		CommerceChannel commerceChannel2 =
			_commerceChannelLocalService.addCommerceChannel(
				null, 0, TestPropsValues.getGroupId(),
				RandomTestUtil.randomString(),
				CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
				commerceCatalog.getCommerceCurrencyCode(), serviceContext);

		CPDefinition cpDefinition2 = CPTestUtil.addCPDefinitionFromCatalog(
			commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, false,
			false);

		_commerceChannelRelLocalService.addCommerceChannelRel(
			CPDefinition.class.getName(), cpDefinition2.getCPDefinitionId(),
			commerceChannel1.getCommerceChannelId(), serviceContext);
		_commerceChannelRelLocalService.addCommerceChannelRel(
			CPDefinition.class.getName(), cpDefinition2.getCPDefinitionId(),
			commerceChannel2.getCommerceChannelId(), serviceContext);

		CPDefinition cpDefinition3 = CPTestUtil.addCPDefinitionFromCatalog(
			commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, false,
			false);

		_accountGroupRelLocalService.addAccountGroupRel(
			accountGroup1.getAccountGroupId(), CPDefinition.class.getName(),
			cpDefinition3.getCPDefinitionId());
		_commerceChannelRelLocalService.addCommerceChannelRel(
			CPDefinition.class.getName(), cpDefinition3.getCPDefinitionId(),
			commerceChannel2.getCommerceChannelId(), serviceContext);

		AccountGroup accountGroup2 = _accountGroupLocalService.addAccountGroup(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		CPDefinition cpDefinition4 = CPTestUtil.addCPDefinitionFromCatalog(
			commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, false,
			false);

		_accountGroupRelLocalService.addAccountGroupRel(
			accountGroup2.getAccountGroupId(), CPDefinition.class.getName(),
			cpDefinition4.getCPDefinitionId());

		CPDefinition cpDefinition5 = CPTestUtil.addCPDefinitionFromCatalog(
			commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, false,
			false);

		_featureFlagListener.onValue(
			TestPropsValues.getCompanyId(), "LPD-10889", true);

		EntityCacheUtil.clearCache();

		List<CPConfigurationList> cpConfigurationLists =
			_cpConfigurationListLocalService.getCPConfigurationLists(
				commerceCatalog.getGroupId(), TestPropsValues.getCompanyId());

		Assert.assertEquals(
			cpConfigurationLists.toString(), 4, cpConfigurationLists.size());

		CPConfigurationList masterCPConfigurationList =
			cpDefinition1.getMasterCPConfigurationList();

		try {
			for (CPConfigurationList cpConfigurationList :
					cpConfigurationLists) {

				List<CommerceChannelRel> commerceChannelRels =
					_commerceChannelRelLocalService.getCommerceChannelRels(
						CPConfigurationList.class.getName(),
						cpConfigurationList.getCPConfigurationListId(),
						QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
				List<CPConfigurationEntry> cpConfigurationEntries =
					_cpConfigurationEntryLocalService.getCPConfigurationEntries(
						cpConfigurationList.getCPConfigurationListId());
				List<CPConfigurationListRel> cpConfigurationListRels =
					_cpConfigurationListRelLocalService.
						getCPConfigurationListRels(
							cpConfigurationList.getCPConfigurationListId());

				if (cpConfigurationList.isMaster()) {
					Assert.assertEquals(
						cpConfigurationEntries.toString(), 6,
						cpConfigurationEntries.size());

					CPConfigurationEntry masterCPConfigurationEntry =
						cpDefinition1.fetchMasterCPConfigurationEntry();

					Assert.assertFalse(masterCPConfigurationEntry.isVisible());

					masterCPConfigurationEntry =
						cpDefinition2.fetchMasterCPConfigurationEntry();

					Assert.assertFalse(masterCPConfigurationEntry.isVisible());

					masterCPConfigurationEntry =
						cpDefinition3.fetchMasterCPConfigurationEntry();

					Assert.assertFalse(masterCPConfigurationEntry.isVisible());

					masterCPConfigurationEntry =
						cpDefinition4.fetchMasterCPConfigurationEntry();

					Assert.assertFalse(masterCPConfigurationEntry.isVisible());

					masterCPConfigurationEntry =
						cpDefinition5.fetchMasterCPConfigurationEntry();

					Assert.assertTrue(masterCPConfigurationEntry.isVisible());
				}
				else if (Objects.equals(
							cpConfigurationList.getName(),
							masterCPConfigurationList.getName() + " 1")) {

					Assert.assertEquals(
						cpConfigurationEntries.toString(), 3,
						cpConfigurationEntries.size());
					Assert.assertTrue(
						ListUtil.exists(
							cpConfigurationEntries,
							cpConfigurationEntry ->
								(cpConfigurationEntry.getClassPK() ==
									cpDefinition1.getCPDefinitionId()) &&
								cpConfigurationEntry.isVisible()));
					Assert.assertTrue(
						ListUtil.exists(
							cpConfigurationEntries,
							cpConfigurationEntry ->
								(cpConfigurationEntry.getClassPK() ==
									cpDefinition2.getCPDefinitionId()) &&
								cpConfigurationEntry.isVisible()));
					Assert.assertTrue(
						ListUtil.exists(
							cpConfigurationEntries,
							cpConfigurationEntry ->
								(cpConfigurationEntry.getClassPK() ==
									cpDefinition3.getCPDefinitionId()) &&
								cpConfigurationEntry.isVisible()));
					Assert.assertEquals(
						commerceChannelRels.toString(), 2,
						commerceChannelRels.size());
					Assert.assertTrue(
						ListUtil.exists(
							commerceChannelRels,
							commerceChannelRel ->
								commerceChannelRel.getCommerceChannelId() ==
									commerceChannel1.getCommerceChannelId()));
					Assert.assertTrue(
						ListUtil.exists(
							commerceChannelRels,
							commerceChannelRel ->
								commerceChannelRel.getCommerceChannelId() ==
									commerceChannel2.getCommerceChannelId()));
					Assert.assertTrue(cpConfigurationListRels.isEmpty());
				}
				else if (Objects.equals(
							cpConfigurationList.getName(),
							masterCPConfigurationList.getName() + " 2")) {

					Assert.assertEquals(
						cpConfigurationEntries.toString(), 1,
						cpConfigurationEntries.size());
					Assert.assertTrue(
						ListUtil.exists(
							cpConfigurationEntries,
							cpConfigurationEntry ->
								(cpConfigurationEntry.getClassPK() ==
									cpDefinition4.getCPDefinitionId()) &&
								cpConfigurationEntry.isVisible()));
					Assert.assertTrue(commerceChannelRels.isEmpty());
					Assert.assertEquals(
						cpConfigurationListRels.toString(), 1,
						cpConfigurationListRels.size());
					Assert.assertTrue(
						ListUtil.exists(
							cpConfigurationListRels,
							cpConfigurationListRel ->
								cpConfigurationListRel.getClassPK() ==
									accountGroup2.getAccountGroupId()));
				}
				else if (Objects.equals(
							cpConfigurationList.getName(),
							masterCPConfigurationList.getName() + " 3")) {

					Assert.assertEquals(
						cpConfigurationEntries.toString(), 2,
						cpConfigurationEntries.size());
					Assert.assertTrue(
						ListUtil.exists(
							cpConfigurationEntries,
							cpConfigurationEntry ->
								(cpConfigurationEntry.getClassPK() ==
									cpDefinition2.getCPDefinitionId()) &&
								cpConfigurationEntry.isVisible()));
					Assert.assertTrue(
						ListUtil.exists(
							cpConfigurationEntries,
							cpConfigurationEntry ->
								(cpConfigurationEntry.getClassPK() ==
									cpDefinition3.getCPDefinitionId()) &&
								cpConfigurationEntry.isVisible()));
					Assert.assertEquals(
						commerceChannelRels.toString(), 1,
						commerceChannelRels.size());
					Assert.assertTrue(
						ListUtil.exists(
							commerceChannelRels,
							commerceChannelRel ->
								commerceChannelRel.getCommerceChannelId() ==
									commerceChannel2.getCommerceChannelId()));
					Assert.assertEquals(
						commerceChannelRels.toString(), 1,
						cpConfigurationListRels.size());
					Assert.assertTrue(
						ListUtil.exists(
							cpConfigurationListRels,
							cpConfigurationListRel ->
								cpConfigurationListRel.getClassPK() ==
									accountGroup1.getAccountGroupId()));
				}
				else {
					Assert.fail();
				}
			}
		}
		catch (Exception exception) {
			Assert.assertNotNull(exception);
		}

		_featureFlagListener.onValue(
			TestPropsValues.getCompanyId(), "LPD-10889", false);

		EntityCacheUtil.clearCache();

		Assert.assertFalse(
			ListUtil.exists(
				_cpConfigurationListLocalService.getCPConfigurationLists(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS),
				cpConfigurationList -> !cpConfigurationList.isMaster()));
	}

	@Inject
	private static CommerceCatalogLocalService _commerceCatalogLocalService;

	@Inject
	private static CPConfigurationEntryLocalService
		_cpConfigurationEntryLocalService;

	@Inject
	private static CPConfigurationListLocalService
		_cpConfigurationListLocalService;

	@Inject
	private static CPDefinitionLocalService _cpDefinitionLocalService;

	@Inject
	private AccountGroupLocalService _accountGroupLocalService;

	@Inject
	private AccountGroupRelLocalService _accountGroupRelLocalService;

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Inject
	private CommerceChannelRelLocalService _commerceChannelRelLocalService;

	@Inject
	private CPConfigurationListRelLocalService
		_cpConfigurationListRelLocalService;

	@Inject(
		filter = "component.name=com.liferay.commerce.internal.feature.flag.CPConfigurationListEligibilityFeatureFlagListener"
	)
	private FeatureFlagListener _featureFlagListener;

}