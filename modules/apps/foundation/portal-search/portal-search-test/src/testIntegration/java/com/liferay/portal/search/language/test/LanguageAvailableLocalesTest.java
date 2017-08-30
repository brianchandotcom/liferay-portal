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

package com.liferay.portal.search.language.test;

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
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * @author Rodrigo Paulino
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
@Sync
public class LanguageAvailableLocalesTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();
	}

	@Test
	public void testCompanyThreadLocalIsDefaultWithNoArgs() throws Exception {
		long originalCompanyId = CompanyThreadLocal.getCompanyId();

		try {
			setCompanyThreadLocalWithLocales(_company, _locales);

			assertEquals(_locales, _language.getAvailableLocales());
		}
		finally {
			CompanyThreadLocal.setCompanyId(originalCompanyId);
		}
	}

	@Test
	public void testGroupWithoutLocalesInheritsFromCompany() throws Exception {
		long originalCompanyId = CompanyThreadLocal.getCompanyId();

		try {
			setCompanyThreadLocalWithLocales(_company, _locales);
		}
		finally {
			CompanyThreadLocal.setCompanyId(originalCompanyId);
		}

		assertEquals(
			_locales, _language.getAvailableLocales(getGuestGroupId()));
	}

	@Test
	public void testGroupWithSpecificLocales() throws Exception {
		long groupId = getGuestGroupId();

		GroupTestUtil.updateDisplaySettings(groupId, _locales, LocaleUtil.US);

		assertEquals(_locales, _language.getAvailableLocales(groupId));
	}

	protected void assertEquals(Collection<?> expected, Collection<?> actual) {
		Assert.assertEquals(toStringSorted(expected), toStringSorted(actual));
	}

	protected long getGuestGroupId() throws PortalException {
		Group group = _groupLocalService.getGroup(
			_company.getCompanyId(), GroupConstants.GUEST);

		return group.getGroupId();
	}

	protected void setCompanyThreadLocalWithLocales(
			Company company, List<Locale> locales)
		throws Exception {

		CompanyTestUtil.resetCompanyLocales(
			company.getCompanyId(), locales, LocaleUtil.US);
	}

	protected String toStringSorted(Collection<?> collection) {
		Stream<?> stream = collection.stream();

		return stream.map(
			Object::toString
		).sorted(
		).collect(
			Collectors.joining(StringPool.PIPE)
		);
	}

	@Inject
	private static GroupLocalService _groupLocalService;

	@Inject
	private static Language _language;

	private static final List<Locale> _locales = Arrays.asList(
		LocaleUtil.BRAZIL, LocaleUtil.HUNGARY, LocaleUtil.JAPAN, LocaleUtil.US);

	@DeleteAfterTestRun
	private Company _company;

}