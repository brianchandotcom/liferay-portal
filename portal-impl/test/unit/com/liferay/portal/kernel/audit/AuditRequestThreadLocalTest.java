/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.audit;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Christian Moura
 */
public class AuditRequestThreadLocalTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetCorrelationId() {
		Assert.assertNull(AuditRequestThreadLocal.getCorrelationId());

		String correlationId = RandomTestUtil.randomString();

		try (SafeCloseable safeCloseable =
				AuditRequestThreadLocal.setCorrelationIdWithSafeCloseable(
					correlationId)) {

			Assert.assertEquals(
				correlationId, AuditRequestThreadLocal.getCorrelationId());
		}
	}

	@Test
	public void testSetCorrelationIdWithSafeCloseable() {
		Assert.assertNull(AuditRequestThreadLocal.getCorrelationId());

		String correlationId1 = RandomTestUtil.randomString();

		try (SafeCloseable safeCloseable1 =
				AuditRequestThreadLocal.setCorrelationIdWithSafeCloseable(
					correlationId1)) {

			Assert.assertEquals(
				correlationId1, AuditRequestThreadLocal.getCorrelationId());

			String correlationId2 = RandomTestUtil.randomString();

			try (SafeCloseable safeCloseable2 =
					AuditRequestThreadLocal.setCorrelationIdWithSafeCloseable(
						correlationId2)) {

				Assert.assertEquals(
					correlationId2, AuditRequestThreadLocal.getCorrelationId());
			}

			Assert.assertEquals(
				correlationId1, AuditRequestThreadLocal.getCorrelationId());
		}

		Assert.assertNull(AuditRequestThreadLocal.getCorrelationId());
	}

}