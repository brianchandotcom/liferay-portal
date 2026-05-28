/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.capabilities;

import com.liferay.portal.kernel.license.util.LicenseManager;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.capabilities.ElasticsearchLicenseInformation;
import com.liferay.portal.search.capabilities.ExternalEmbeddingEligibility;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Rodrigo Guedes de Souza
 */
public class ExternalEmbeddingCapabilityGateImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_externalEmbeddingCapabilityGateImpl =
			new ExternalEmbeddingCapabilityGateImpl();

		_snapshotAutoCloseable =
			ReflectionTestUtil.setFieldValueWithAutoCloseable(
				ExternalEmbeddingCapabilityGateImpl.class,
				"_elasticsearchLicenseInformationSnapshot",
				new Snapshot<ElasticsearchLicenseInformation>(
					ExternalEmbeddingCapabilityGateImpl.class,
					ElasticsearchLicenseInformation.class) {

					@Override
					public ElasticsearchLicenseInformation get() {
						return _elasticsearchLicenseInformation;
					}

				});
		ReflectionTestUtil.setFieldValue(
			_externalEmbeddingCapabilityGateImpl, "_licenseManager",
			_licenseManager);

		Mockito.when(
			_elasticsearchLicenseInformation.supportsInferenceAPI()
		).thenReturn(
			true
		);
	}

	@After
	public void tearDown() throws Exception {
		_snapshotAutoCloseable.close();
	}

	@Test
	public void testCheckAvailable() {
		ExternalEmbeddingEligibility externalEmbeddingEligibility =
			_externalEmbeddingCapabilityGateImpl.check();

		Assert.assertTrue(externalEmbeddingEligibility.isAvailable());
		Assert.assertEquals("", externalEmbeddingEligibility.getReason());
	}

	@Test
	public void testCheckMissingDXPEnterprise() {
		Mockito.when(
			_licenseManager.isFreeTier()
		).thenReturn(
			true
		);

		_assertUnavailable(
			"semantic-search.external-embedding-capability." +
				"missing-dxp-enterprise");
	}

	@Test
	public void testCheckMissingElasticsearchLicense() {
		Mockito.when(
			_elasticsearchLicenseInformation.supportsInferenceAPI()
		).thenReturn(
			false
		);

		_assertUnavailable(
			"semantic-search.external-embedding-capability." +
				"missing-elasticsearch-license");
	}

	@Test
	public void testCheckMissingElasticsearchLicenseWhenSourceUnavailable() {
		_elasticsearchLicenseInformation = null;

		_assertUnavailable(
			"semantic-search.external-embedding-capability." +
				"missing-elasticsearch-license");
	}

	private void _assertUnavailable(String expectedReason) {
		ExternalEmbeddingEligibility externalEmbeddingEligibility =
			_externalEmbeddingCapabilityGateImpl.check();

		Assert.assertFalse(externalEmbeddingEligibility.isAvailable());
		Assert.assertEquals(
			expectedReason, externalEmbeddingEligibility.getReason());
	}

	private ElasticsearchLicenseInformation _elasticsearchLicenseInformation =
		Mockito.mock(ElasticsearchLicenseInformation.class);
	private ExternalEmbeddingCapabilityGateImpl
		_externalEmbeddingCapabilityGateImpl;
	private final LicenseManager _licenseManager = Mockito.mock(
		LicenseManager.class);
	private AutoCloseable _snapshotAutoCloseable;

}