/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.service.access.policy.internal.upgrade.v3_0_2.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class SAPEntryServiceSignatureUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testUpgradeIsIdempotent() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					company.getCompanyId())) {

			String oldServiceSignature = StringBundler.concat(
				"com.liferay.object.rest.internal.resource.v1_0.",
				"ObjectEntryResourceImpl#",
				"putByExternalReferenceCodeCurrentExternalReference",
				"CodeObjectRelationshipNameRelatedExternalReferenceCode");

			String allowedServiceSignatures = StringBundler.concat(
				"com.liferay.object.rest.internal.resource.v1_0.",
				"ObjectEntryResourceImpl#getObjectEntry\n",
				oldServiceSignature);

			SAPEntry sapEntry = _sapEntryLocalService.addSAPEntry(
				company.getDefaultUser(
				).getUserId(),
				allowedServiceSignatures, false, true,
				RandomTestUtil.randomString(),
				HashMapBuilder.put(
					LocaleUtil.fromLanguageId(
						UpgradeProcessUtil.getDefaultLanguageId(
							company.getCompanyId())),
					"Test SAP Entry for Upgrade"
				).build(),
				_createServiceContext(company));

			_testSAPEntries.add(sapEntry);

			_runUpgrade();

			sapEntry = _sapEntryLocalService.getSAPEntry(
				sapEntry.getSapEntryId());

			String signaturesAfterFirstUpgrade =
				sapEntry.getAllowedServiceSignatures();

			_runUpgrade();

			sapEntry = _sapEntryLocalService.getSAPEntry(
				sapEntry.getSapEntryId());

			String signaturesAfterSecondUpgrade =
				sapEntry.getAllowedServiceSignatures();

			Assert.assertEquals(
				signaturesAfterFirstUpgrade, signaturesAfterSecondUpgrade);

			Assert.assertFalse(
				signaturesAfterSecondUpgrade.contains(oldServiceSignature));
		}
	}

	@Test
	public void testUpgradeObjectEntryRelationshipServiceSignature()
		throws Exception {

		Company company = CompanyTestUtil.addCompany();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					company.getCompanyId())) {

			String oldServiceSignature = StringBundler.concat(
				"com.liferay.object.rest.internal.resource.v1_0.",
				"ObjectEntryResourceImpl#",
				"putByExternalReferenceCodeCurrentExternalReference",
				"CodeObjectRelationshipNameRelatedExternalReferenceCode");

			String expectedNewServiceSignature = StringBundler.concat(
				"com.liferay.object.rest.internal.resource.v1_0.",
				"ObjectEntryRelatedObjectsResourceImpl#",
				"putByExternalReferenceCodeCurrentExternalReference",
				"CodeObjectRelationshipNameRelatedExternalReferenceCode");

			String allowedServiceSignatures = StringBundler.concat(
				"com.liferay.object.rest.internal.resource.v1_0.",
				"ObjectEntryResourceImpl#getByExternalReferenceCode\n",
				"com.liferay.object.rest.internal.resource.v1_0.",
				"ObjectEntryResourceImpl#getObjectEntriesPage\n",
				"com.liferay.object.rest.internal.resource.v1_0.",
				"ObjectEntryResourceImpl#getObjectEntry\n",
				"com.liferay.object.rest.internal.resource.v1_0.",
				"ObjectEntryResourceImpl#postObjectEntry\n",
				"com.liferay.object.rest.internal.resource.v1_0.",
				"ObjectEntryResourceImpl#postScopeScopeKey\n",
				oldServiceSignature);

			SAPEntry sapEntry = _sapEntryLocalService.addSAPEntry(
				company.getDefaultUser(
				).getUserId(),
				allowedServiceSignatures, false, true,
				RandomTestUtil.randomString(),
				HashMapBuilder.put(
					LocaleUtil.fromLanguageId(
						UpgradeProcessUtil.getDefaultLanguageId(
							company.getCompanyId())),
					"Test SAP Entry for Upgrade"
				).build(),
				_createServiceContext(company));

			_testSAPEntries.add(sapEntry);

			sapEntry = _sapEntryLocalService.getSAPEntry(
				sapEntry.getSapEntryId());

			String serviceSignatures = sapEntry.getAllowedServiceSignatures();

			Assert.assertTrue(serviceSignatures.contains(oldServiceSignature));
			Assert.assertFalse(
				serviceSignatures.contains(expectedNewServiceSignature));

			_runUpgrade();

			sapEntry = _sapEntryLocalService.getSAPEntry(
				sapEntry.getSapEntryId());

			serviceSignatures = sapEntry.getAllowedServiceSignatures();

			Assert.assertFalse(serviceSignatures.contains(oldServiceSignature));
			Assert.assertTrue(
				serviceSignatures.contains(expectedNewServiceSignature));
			Assert.assertTrue(
				serviceSignatures.contains(
					"com.liferay.object.rest.internal.resource.v1_0." +
						"ObjectEntryResourceImpl#getByExternalReferenceCode"));
			Assert.assertTrue(
				serviceSignatures.contains(
					"com.liferay.object.rest.internal.resource.v1_0." +
						"ObjectEntryResourceImpl#postObjectEntry"));
		}
	}

	@Test
	public void testUpgradeWithNoMatchingSignatures() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					company.getCompanyId())) {

			String allowedServiceSignatures = StringBundler.concat(
				"com.liferay.object.rest.internal.resource.v1_0.",
				"ObjectEntryResourceImpl#getByExternalReferenceCode\n",
				"com.liferay.object.rest.internal.resource.v1_0.",
				"ObjectEntryResourceImpl#getObjectEntriesPage\n",
				"com.liferay.object.rest.internal.resource.v1_0.",
				"ObjectEntryResourceImpl#postObjectEntry");

			SAPEntry sapEntry = _sapEntryLocalService.addSAPEntry(
				company.getDefaultUser(
				).getUserId(),
				allowedServiceSignatures, false, true,
				RandomTestUtil.randomString(),
				HashMapBuilder.put(
					LocaleUtil.fromLanguageId(
						UpgradeProcessUtil.getDefaultLanguageId(
							company.getCompanyId())),
					"Test SAP Entry for Upgrade"
				).build(),
				_createServiceContext(company));

			_testSAPEntries.add(sapEntry);

			String originalSignatures = sapEntry.getAllowedServiceSignatures();

			_runUpgrade();

			sapEntry = _sapEntryLocalService.getSAPEntry(
				sapEntry.getSapEntryId());

			Assert.assertEquals(
				originalSignatures, sapEntry.getAllowedServiceSignatures());
		}
	}

	private ServiceContext _createServiceContext(Company company)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(company.getCompanyId());
		serviceContext.setUserId(
			company.getDefaultUser(
			).getUserId());

		return serviceContext;
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess[] upgradeProcesses = UpgradeTestUtil.getUpgradeSteps(
			_upgradeStepRegistrator, new Version(3, 0, 2));

		for (UpgradeProcess upgradeProcess : upgradeProcesses) {
			upgradeProcess.upgrade();
		}

		CacheRegistryUtil.clear();
	}

	@Inject(
		filter = "(&(component.name=com.liferay.portal.security.service.access.policy.internal.upgrade.registry.SAPServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private SAPEntryLocalService _sapEntryLocalService;

	@DeleteAfterTestRun
	private final List<SAPEntry> _testSAPEntries = new ArrayList<>();

}