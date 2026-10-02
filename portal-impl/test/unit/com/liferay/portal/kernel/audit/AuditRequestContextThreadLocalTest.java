/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.audit;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Christian Moura
 */
public class AuditRequestContextThreadLocalTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetAuditRequestContext() {
		AuditRequestContext auditRequestContext =
			AuditRequestContextThreadLocal.getAuditRequestContext();

		Assert.assertSame(
			auditRequestContext,
			AuditRequestContextThreadLocal.getAuditRequestContext());

		AuditRequestContextThreadLocal.removeAuditRequestContext();

		Assert.assertNotSame(
			auditRequestContext,
			AuditRequestContextThreadLocal.getAuditRequestContext());

		AuditRequestContextThreadLocal.removeAuditRequestContext();
	}

}