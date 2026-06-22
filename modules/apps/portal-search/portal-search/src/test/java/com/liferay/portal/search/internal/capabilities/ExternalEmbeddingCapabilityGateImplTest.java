/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.capabilities;

import com.liferay.portal.kernel.license.util.LicenseManager;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.capabilities.ExternalEmbeddingEligibility;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

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
		ReflectionTestUtil.setFieldValue(
			_externalEmbeddingCapabilityGateImpl, "_licenseManager",
			_licenseManager);
		ReflectionTestUtil.setFieldValue(
			_externalEmbeddingCapabilityGateImpl, "_searchEngineInformation",
			_searchEngineInformation);

		Mockito.when(
			_searchEngineInformation.getVendorString()
		).thenReturn(
			"Elasticsearch"
		);

		Mockito.when(
			_searchEngineInformation.isInferenceAPISupported()
		).thenReturn(
			true
		);
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
			_searchEngineInformation.isInferenceAPISupported()
		).thenReturn(
			false
		);

		_assertUnavailable(
			"semantic-search.external-embedding-capability." +
				"missing-elasticsearch-license");
	}

	@Test
	public void testCheckUnsupportedSearchEngine() {
		Mockito.when(
			_searchEngineInformation.getVendorString()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		_assertUnavailable(
			"semantic-search.external-embedding-capability." +
				"unsupported-search-engine");
	}

	private void _assertUnavailable(String expectedReason) {
		ExternalEmbeddingEligibility externalEmbeddingEligibility =
			_externalEmbeddingCapabilityGateImpl.check();

		Assert.assertFalse(externalEmbeddingEligibility.isAvailable());
		Assert.assertEquals(
			expectedReason, externalEmbeddingEligibility.getReason());
	}

	private ExternalEmbeddingCapabilityGateImpl
		_externalEmbeddingCapabilityGateImpl =
			new ExternalEmbeddingCapabilityGateImpl();
	private final LicenseManager _licenseManager = Mockito.mock(
		LicenseManager.class);
	private final SearchEngineInformation _searchEngineInformation =
		Mockito.mock(SearchEngineInformation.class);

}