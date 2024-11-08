/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.instance.lifecycle.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTSchemaVersion;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTSchemaVersionLocalService;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Truong
 */
@RunWith(Arquillian.class)
public class CheckCTSchemaVersionPortalInstanceLifecycleListenerTest {

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

		CTSchemaVersion ctSchemaVersion =
			_ctSchemaVersionLocalService.getLatestCTSchemaVersion(
				company.getCompanyId());

		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		Assert.assertEquals(
			ctSchemaVersion.getSchemaVersionId(),
			ctCollection.getSchemaVersionId());

		_releaseLocalService.addRelease(RandomTestUtil.randomString(), "0.0.0");

		_portalInstanceLifecycleListener.portalInstanceRegistered(company);

		CTSchemaVersion latestCTSchemaVersion =
			_ctSchemaVersionLocalService.getLatestCTSchemaVersion(
				company.getCompanyId());

		ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollection.getCtCollectionId());

		Assert.assertEquals(
			ctSchemaVersion.getSchemaVersionId(),
			ctCollection.getSchemaVersionId());
		Assert.assertEquals(
			latestCTSchemaVersion.getSchemaVersionId(),
			ctCollection.getSchemaVersionId());
	}

	@Inject
	private static ReleaseLocalService _releaseLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private CTSchemaVersionLocalService _ctSchemaVersionLocalService;

	@Inject(
		filter = "component.name=com.liferay.change.tracking.internal.instance.lifecycle.CheckCTSchemaVersionPortalInstanceLifecycleListener"
	)
	private PortalInstanceLifecycleListener _portalInstanceLifecycleListener;

}