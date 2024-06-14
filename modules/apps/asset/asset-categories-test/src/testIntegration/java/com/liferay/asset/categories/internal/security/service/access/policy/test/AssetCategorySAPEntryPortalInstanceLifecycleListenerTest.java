/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.internal.security.service.access.policy.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class AssetCategorySAPEntryPortalInstanceLifecycleListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testPortalInstanceRegistered() throws Exception {
		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
			company.getCompanyId(), _SAP_ENTRY_NAME);

		if (sapEntry != null) {
			_sapEntryLocalService.deleteSAPEntry(sapEntry);
		}

		sapEntry = _sapEntryLocalService.fetchSAPEntry(
			company.getCompanyId(), _SAP_ENTRY_NAME);

		Assert.assertNull(sapEntry);

		_portalInstanceLifecycleListener.portalInstanceRegistered(company);

		sapEntry = _sapEntryLocalService.fetchSAPEntry(
			company.getCompanyId(), _SAP_ENTRY_NAME);

		Assert.assertNotNull(sapEntry);

		List<String> allowedServiceSignaturesList =
			sapEntry.getAllowedServiceSignaturesList();

		Assert.assertTrue(ListUtil.isNotEmpty(allowedServiceSignaturesList));
		Assert.assertEquals(
			allowedServiceSignaturesList.toString(), 1,
			allowedServiceSignaturesList.size());

		Assert.assertEquals(
			AssetCategoryService.class.getName() + "#search",
			allowedServiceSignaturesList.get(0));
	}

	private static final String _SAP_ENTRY_NAME = "ASSET_CATEGORY_DEFAULT";

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "component.name=com.liferay.asset.categories.internal.security.service.access.policy.AssetCategorySAPEntryPortalInstanceLifecycleListener"
	)
	private PortalInstanceLifecycleListener _portalInstanceLifecycleListener;

	@Inject
	private SAPEntryLocalService _sapEntryLocalService;

}