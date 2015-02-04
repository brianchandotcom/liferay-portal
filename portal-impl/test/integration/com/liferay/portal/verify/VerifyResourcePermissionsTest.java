/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.verify;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.verify.test.BaseVerifyProcessTestCase;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class VerifyResourcePermissionsTest extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
	public void testVerifyMoreThanOneCompany() throws Exception {
		verify(false);
	}

	@Test
	public void testVerifyOneCompany() throws Exception {
		verify(true);
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new VerifyResourcePermissions();
	}

	protected void verify(boolean oneCompany) throws Exception {
		Company company = null;

		if (!oneCompany) {
			company = CompanyTestUtil.addCompany();
		}

		doVerify();

		if (!oneCompany) {
			if (company != null) {
				CompanyLocalServiceUtil.deleteCompany(company);
			}
		}
	}

}