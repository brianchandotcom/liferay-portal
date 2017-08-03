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

package com.liferay.portal.workflow.kaleo.designer.internal.messaging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition;
import com.liferay.portal.workflow.kaleo.designer.service.KaleoDraftDefinitionLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalServiceUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
public class KaleoDefinitionMessageListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		setUpCompany();
	}

	@Test
	public void testOnCreateAddKaleoDraftDefinition() throws PortalException {
		String content = RandomTestUtil.randomString();
		String description = RandomTestUtil.randomString();
		String name = RandomTestUtil.randomString();
		ServiceContext serviceContext = _createServiceContext();
		Map<Locale, String> titleMap = RandomTestUtil.randomLocaleStringMap();
		int version = RandomTestUtil.randomInt();

		KaleoDefinitionLocalServiceUtil.addKaleoDefinition(
			name, _getLocalizedXML(titleMap), description, content, version,
			serviceContext);

		_kaleoDraftDefinitions =
			KaleoDraftDefinitionLocalServiceUtil.getKaleoDraftDefinitions(
				name, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null,
				_createServiceContext());

		Assert.assertEquals(
			_kaleoDraftDefinitions.toString(), 1,
			_kaleoDraftDefinitions.size());

		KaleoDraftDefinition kaleoDraftDefinition = _kaleoDraftDefinitions.get(
			0);

		assertKaleoDraftDefinitionValues(
			name, version, titleMap, content, kaleoDraftDefinition);
	}

	@Test
	public void testOnCreateDoesNotAddKaleoDraftDefinitionIfOneExists()
		throws PortalException {

		String content = RandomTestUtil.randomString();
		String description = RandomTestUtil.randomString();
		String name = RandomTestUtil.randomString();
		ServiceContext serviceContext = _createServiceContext();
		Map<Locale, String> titleMap = RandomTestUtil.randomLocaleStringMap();
		int version = RandomTestUtil.randomInt();

		KaleoDraftDefinition existingKaleoDraftDefinition =
			KaleoDraftDefinitionLocalServiceUtil.addKaleoDraftDefinition(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				name, titleMap, content, version, 1, serviceContext);

		KaleoDefinitionLocalServiceUtil.addKaleoDefinition(
			name, _getLocalizedXML(titleMap), description, content, version,
			serviceContext);

		_kaleoDraftDefinitions =
			KaleoDraftDefinitionLocalServiceUtil.getKaleoDraftDefinitions(
				name, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null,
				serviceContext);

		Assert.assertEquals(
			_kaleoDraftDefinitions.toString(), 1,
			_kaleoDraftDefinitions.size());

		KaleoDraftDefinition retrievedKaleoDraftDefinition =
			_kaleoDraftDefinitions.get(0);

		Assert.assertEquals(
			existingKaleoDraftDefinition.getKaleoDraftDefinitionId(),
			retrievedKaleoDraftDefinition.getKaleoDraftDefinitionId());

		assertKaleoDraftDefinitionValues(
			name, version, titleMap, content, retrievedKaleoDraftDefinition);
	}

	protected void assertKaleoDraftDefinitionValues(
		String name, int version, Map<Locale, String> titleMap, String content,
		KaleoDraftDefinition kaleoDraftDefinition) {

		Assert.assertEquals(name, kaleoDraftDefinition.getName());
		Assert.assertEquals(version, kaleoDraftDefinition.getVersion());
		Assert.assertEquals(
			_getLocalizedXML(titleMap), kaleoDraftDefinition.getTitle());
		Assert.assertEquals(content, kaleoDraftDefinition.getContent());
	}

	protected void setUpCompany() throws Exception {
		List<Company> companies = CompanyLocalServiceUtil.getCompanies();

		_company = companies.get(0);
	}

	private static String _getLocalizedXML(Map<Locale, String> map) {
		String title = StringPool.BLANK;

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		for (Locale locale : map.keySet()) {
			String languageId = LocaleUtil.toLanguageId(locale);

			String localizedTitle = map.get(locale);

			title = LocalizationUtil.updateLocalization(
				title, "Title", localizedTitle, languageId, defaultLanguageId);
		}

		return title;
	}

	private ServiceContext _createServiceContext() throws PortalException {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(_company.getCompanyId());

		User user = _company.getDefaultUser();

		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	private Company _company;

	@DeleteAfterTestRun
	private List<KaleoDraftDefinition> _kaleoDraftDefinitions;

}