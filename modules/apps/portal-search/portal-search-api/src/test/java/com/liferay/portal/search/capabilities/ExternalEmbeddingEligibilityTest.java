/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.capabilities;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Rodrigo Guedes de Souza
 */
public class ExternalEmbeddingEligibilityTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAvailableReturnsSingleton() {
		Assert.assertSame(
			ExternalEmbeddingEligibility.available(),
			ExternalEmbeddingEligibility.available());
	}

	@Test
	public void testIsAvailable() {
		ExternalEmbeddingEligibility externalEmbeddingEligibility =
			ExternalEmbeddingEligibility.available();

		Assert.assertTrue(externalEmbeddingEligibility.isAvailable());
		Assert.assertEquals("", externalEmbeddingEligibility.getReason());
	}

	@Test
	public void testIsAvailableWhenUnavailable() {
		ExternalEmbeddingEligibility externalEmbeddingEligibility =
			ExternalEmbeddingEligibility.unavailable(
				_MISSING_DXP_ENTERPRISE_REASON);

		Assert.assertFalse(externalEmbeddingEligibility.isAvailable());
		Assert.assertEquals(
			_MISSING_DXP_ENTERPRISE_REASON,
			externalEmbeddingEligibility.getReason());
	}

	@Test
	public void testUnavailableWithInvalidReason() {
		try {
			ExternalEmbeddingEligibility.unavailable(null);

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
			Assert.assertEquals(
				"Reason is null or empty",
				illegalArgumentException.getMessage());
		}

		try {
			ExternalEmbeddingEligibility.unavailable("");

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
			Assert.assertEquals(
				"Reason is null or empty",
				illegalArgumentException.getMessage());
		}
	}

	private static final String _MISSING_DXP_ENTERPRISE_REASON =
		"semantic-search.external-embedding-capability.missing-dxp-enterprise";

}