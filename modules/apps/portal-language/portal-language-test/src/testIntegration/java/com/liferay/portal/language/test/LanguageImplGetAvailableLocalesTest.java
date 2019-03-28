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

package com.liferay.portal.language.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class LanguageImplGetAvailableLocalesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();
	}

	@Test
	public void testCompanyThreadLocalIsDefaultWithNoArgs() throws Exception {
		long companyId = CompanyThreadLocal.getCompanyId();

		try {
			_resetCompanyLocales();

			Assert.assertEquals(_locales, _language.getAvailableLocales());
		}
		finally {
			CompanyThreadLocal.setCompanyId(companyId);
		}
	}

	@Test
	public void testGroupWithoutLocalesInheritsFromCompany() throws Exception {
		long companyId = CompanyThreadLocal.getCompanyId();

		try {
			_resetCompanyLocales();
		}
		finally {
			CompanyThreadLocal.setCompanyId(companyId);
		}

		Assert.assertEquals(
			_locales, _language.getAvailableLocales(_getGuestGroupId()));
	}

	@Test
	public void testGroupWithSpecificLocales() throws Exception {
		long groupId = _getGuestGroupId();

		GroupTestUtil.updateDisplaySettings(groupId, _locales, LocaleUtil.US);

		Assert.assertEquals(_locales, _language.getAvailableLocales(groupId));
	}

	private long _getGuestGroupId() throws PortalException {
		Group group = _groupLocalService.getGroup(
			_company.getCompanyId(), GroupConstants.GUEST);

		return group.getGroupId();
	}

	private void _resetCompanyLocales() throws Exception {
		CompanyTestUtil.resetCompanyLocales(
			_company.getCompanyId(), _locales, LocaleUtil.US);
	}

	@Inject
	private static GroupLocalService _groupLocalService;

	@Inject
	private static Language _language;

	private static final Set<Locale> _locales = new HashSet<>(
		Arrays.asList(
			LocaleUtil.BRAZIL, LocaleUtil.HUNGARY, LocaleUtil.JAPAN,
			LocaleUtil.US));

	@DeleteAfterTestRun
	private Company _company;

}