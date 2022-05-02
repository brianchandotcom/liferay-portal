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
import com.liferay.portal.kernel.encryptor.Encryptor;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyInfo;
import com.liferay.portal.kernel.service.CompanyInfoLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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

	@Test
	public void testDeleteCompanyInfo() throws Exception {
		long companyId = _company.getCompanyId();

		_companyLocalService.deleteCompany(_company);

		_company = null;

		Assert.assertNull(_companyInfoLocalService.fetchCompany(companyId));
	}

	@Test
	public void testGetCompanyInfoKey() {
		CompanyInfo companyInfo = _companyInfoLocalService.fetchCompany(
			_company.getCompanyId());

		Assert.assertEquals(companyInfo.getKey(), _company.getKey());
	}

	@Test
	public void testGetCompanyInfoKeyObj() {
		CompanyInfo companyInfo = _companyInfoLocalService.fetchCompany(
			_company.getCompanyId());

		Assert.assertEquals(
			_encryptor.deserializeKey(companyInfo.getKey()),
			_company.getKeyObj());
	}

	@Test
	public void testUpdateCompanyInfoKey() {
		_company.setKey(RandomTestUtil.randomString());

		_company = _companyLocalService.updateCompany(_company);

		CompanyInfo companyInfo = _companyInfoLocalService.fetchCompany(
			_company.getCompanyId());

		Assert.assertEquals(companyInfo.getKey(), _company.getKey());
	}

	@Test
	public void testUpdateCompanyInfoKeyObj() {
		_company.setKey(RandomTestUtil.randomString());

		_company = _companyLocalService.updateCompany(_company);

		CompanyInfo companyInfo = _companyInfoLocalService.fetchCompany(
			_company.getCompanyId());

		Assert.assertEquals(
			_encryptor.deserializeKey(companyInfo.getKey()),
			_company.getKeyObj());
	}

	@Inject
	private static CompanyInfoLocalService _companyInfoLocalService;

	@Inject
	private static CompanyLocalService _companyLocalService;

	@Inject
	private static CounterLocalService _counterLocalService;

	@Inject
	private static Encryptor _encryptor;

	@DeleteAfterTestRun
	private Company _company;

}