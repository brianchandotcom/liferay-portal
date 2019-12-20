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

package com.liferay.company.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.encryptor.Encryptor;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyInfo;
import com.liferay.portal.kernel.service.CompanyInfoLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class CompanyInfoLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();
	}

	@After
	public void tearDown() throws Exception {
		if (_company != null) {
			_companyLocalService.deleteCompany(_company);
		}
	}

	@Test
	public void testDeleteCompanyInfo() throws Exception {
		long companyId = _company.getCompanyId();

		_companyLocalService.deleteCompany(_company);

		_company = null;

		Assert.assertNull(_companyInfoLocalService.fetchByCompanyId(companyId));
	}

	@Test
	public void testGetCompanyInfoKey() {
		CompanyInfo companyInfo = _companyInfoLocalService.fetchByCompanyId(
			_company.getCompanyId());

		Assert.assertEquals(companyInfo.getKey(), _company.getKey());
	}

	@Test
	public void testGetCompanyInfoKeyObj() {
		CompanyInfo companyInfo = _companyInfoLocalService.fetchByCompanyId(
			_company.getCompanyId());

		Assert.assertEquals(
			Encryptor.deserializeKey(companyInfo.getKey()),
			_company.getKeyObj());
	}

	@Test
	public void testUpdateCompanyInfoKey() {
		_company.setKey(RandomTestUtil.randomString());

		_companyLocalService.updateCompany(_company);

		CompanyInfo companyInfo = _companyInfoLocalService.fetchByCompanyId(
			_company.getCompanyId());

		Assert.assertEquals(companyInfo.getKey(), _company.getKey());
	}

	@Test
	public void testUpdateCompanyInfoKeyObj() {
		_company.setKey(RandomTestUtil.randomString());

		_companyLocalService.updateCompany(_company);

		CompanyInfo companyInfo = _companyInfoLocalService.fetchByCompanyId(
			_company.getCompanyId());

		Assert.assertEquals(
			Encryptor.deserializeKey(companyInfo.getKey()),
			_company.getKeyObj());
	}

	private Company _company;

	@Inject
	private CompanyInfoLocalService _companyInfoLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private CounterLocalService _counterLocalService;

}