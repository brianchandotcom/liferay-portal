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

package com.liferay.segments.odata.retriever.test;

import com.fasterxml.jackson.databind.util.ISO8601Utils;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.expando.util.test.ExpandoTestUtil;
import com.liferay.segments.odata.retriever.ODataRetriever;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author David Arques
 */
@RunWith(Arquillian.class)
public class OrganizationODataRetrieverCustomFieldsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_expandoTable = ExpandoTestUtil.addTable(
			PortalUtil.getClassNameId(Organization.class), "CUSTOM_FIELDS");

		Bundle bundle = FrameworkUtil.getBundle(
			OrganizationODataRetrieverCustomFieldsTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTracker = new ServiceTracker<>(
			bundleContext,
			bundleContext.createFilter(
				StringBundler.concat(
					"(&(model.class.name=com.liferay.portal.kernel.model.",
					"Organization)(objectClass=",
					ODataRetriever.class.getName(), "))")),
			null);

		_serviceTracker.open();
	}

	@After
	public void tearDown() {
		_serviceTracker.close();
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndBooleanKeywordType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.BOOLEAN,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		Boolean columnValue = Boolean.TRUE;

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn),
			String.valueOf(columnValue));

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndBooleanTextType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.BOOLEAN,
			ExpandoColumnConstants.INDEX_TYPE_TEXT);

		Boolean columnValue = Boolean.TRUE;

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn),
			String.valueOf(columnValue));

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndDateKeywordType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.DATE,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		Date columnValue = new Date();

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn),
			ISO8601Utils.format(columnValue));

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndDateTextType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.DATE,
			ExpandoColumnConstants.INDEX_TYPE_TEXT);

		Date columnValue = new Date();

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn),
			ISO8601Utils.format(columnValue));

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndDoubleArrayKeywordType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.DOUBLE_ARRAY,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		double[] columnValue = {1.0};

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn),
			columnValue[0]);

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndDoubleArrayTextType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.DOUBLE_ARRAY,
			ExpandoColumnConstants.INDEX_TYPE_TEXT);

		double[] columnValue = {1.0};

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn),
			columnValue[0]);

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndDoubleKeywordType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.DOUBLE,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		double columnValue = 3.0;

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn), columnValue);

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndDoubleTextType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.DOUBLE,
			ExpandoColumnConstants.INDEX_TYPE_TEXT);

		double columnValue = 3.0;

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn), columnValue);

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndFloatArrayKeywordType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.FLOAT_ARRAY,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		float[] columnValue = {1.0F};

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn),
			columnValue[0]);

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndFloatArrayTextType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.FLOAT_ARRAY,
			ExpandoColumnConstants.INDEX_TYPE_TEXT);

		float[] columnValue = {1.0F};

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn),
			columnValue[0]);

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndFloatKeywordType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.FLOAT,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		float columnValue = 3.0F;

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn), columnValue);

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndFloatTextType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.FLOAT,
			ExpandoColumnConstants.INDEX_TYPE_TEXT);

		float columnValue = 3.0F;

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn), columnValue);

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndIntegerArrayKeywordType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.INTEGER_ARRAY,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		int[] columnValue = {1};

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn),
			columnValue[0]);

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndIntegerArrayTextType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.INTEGER_ARRAY,
			ExpandoColumnConstants.INDEX_TYPE_TEXT);

		int[] columnValue = {1};

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn),
			columnValue[0]);

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndIntegerKeywordType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.INTEGER,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		int columnValue = 3;

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn), columnValue);

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndIntegerTextType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.INTEGER,
			ExpandoColumnConstants.INDEX_TYPE_TEXT);

		int columnValue = 3;

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn), columnValue);

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndLocalizedStringKeywordType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.STRING_LOCALIZED,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		Locale esLocale = LocaleUtil.fromLanguageId("es_ES");

		Map<Locale, String> columnValueMap = HashMapBuilder.put(
			esLocale, RandomTestUtil.randomString()
		).put(
			LocaleUtil.getDefault(), RandomTestUtil.randomString()
		).build();

		Serializable columnValue = (Serializable)columnValueMap;

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq '%s')", _encodeName(expandoColumn),
			columnValueMap.get(esLocale));

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString, esLocale);

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString, esLocale, 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndLocalizedStringTextType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.STRING_LOCALIZED,
			ExpandoColumnConstants.INDEX_TYPE_TEXT);

		Locale esLocale = LocaleUtil.fromLanguageId("es_ES");

		Map<Locale, String> columnValueMap = HashMapBuilder.put(
			esLocale, "Hola Mundo!"
		).put(
			LocaleUtil.getDefault(), "Hello World!"
		).build();

		Serializable columnValue = (Serializable)columnValueMap;

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq '%s')", _encodeName(expandoColumn),
			columnValueMap.get(esLocale));

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString, esLocale);

		Assert.assertEquals(0, count);
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndLongArrayKeywordType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.LONG_ARRAY,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		long[] columnValue = {1};

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn),
			columnValue[0]);

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndLongArrayTextType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.LONG_ARRAY,
			ExpandoColumnConstants.INDEX_TYPE_TEXT);

		long[] columnValue = {1};

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn),
			columnValue[0]);

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndLongKeywordType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.LONG,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		long columnValue = 3;

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn), columnValue);

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndLongTextType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.LONG,
			ExpandoColumnConstants.INDEX_TYPE_TEXT);

		long columnValue = 3;

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn), columnValue);

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndShortArrayKeywordType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.SHORT_ARRAY,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		short[] columnValue = {1};

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn),
			columnValue[0]);

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndShortArrayTextType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.SHORT_ARRAY,
			ExpandoColumnConstants.INDEX_TYPE_TEXT);

		short[] columnValue = {1};

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn),
			columnValue[0]);

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndShortKeywordType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.SHORT,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		short columnValue = 3;

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn), columnValue);

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndShortTextType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.SHORT,
			ExpandoColumnConstants.INDEX_TYPE_TEXT);

		short columnValue = 3;

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = String.format(
			"(customField/%s eq %s)", _encodeName(expandoColumn), columnValue);

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndStringKeywordType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.STRING,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		String columnValue = RandomTestUtil.randomString();

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = StringBundler.concat(
			"(customField/", _encodeName(expandoColumn), " eq '", columnValue,
			"')");

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _getODataRetriever().getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByCustomFieldWithEqualsAndStringTextType()
		throws Exception {

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.STRING,
			ExpandoColumnConstants.INDEX_TYPE_TEXT);

		String columnValue = "Hello World!";

		Organization organization1 = _addOrganization(
			expandoColumn.getName(), columnValue);

		_organizations.add(organization1);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization2);

		String filterString = StringBundler.concat(
			"(customField/", _encodeName(expandoColumn), " eq '", columnValue,
			"')");

		int count = _getODataRetriever().getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(0, count);
	}

	private ExpandoColumn _addExpandoColumn(
			ExpandoTable expandoTable, String columnName, int columnType,
			int indexType)
		throws Exception {

		ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
			expandoTable, columnName, columnType);

		UnicodeProperties unicodeProperties =
			expandoColumn.getTypeSettingsProperties();

		unicodeProperties.setProperty(
			ExpandoColumnConstants.INDEX_TYPE, String.valueOf(indexType));

		expandoColumn.setTypeSettingsProperties(unicodeProperties);

		return _expandoColumnLocalService.updateExpandoColumn(expandoColumn);
	}

	private Organization _addOrganization(
			String columnName, Serializable columnValue)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setExpandoBridgeAttributes(
			HashMapBuilder.<String, Serializable>put(
				columnName, columnValue
			).build());

		return OrganizationLocalServiceUtil.addOrganization(
			TestPropsValues.getUserId(),
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			RandomTestUtil.randomString(),
			_organizationLocalService.getTypes()[0], 0, 0,
			ListTypeConstants.ORGANIZATION_STATUS_DEFAULT, StringPool.BLANK,
			false, serviceContext);
	}

	private String _encodeName(ExpandoColumn expandoColumn) throws Exception {
		return ReflectionTestUtil.invoke(
			_modelListener, "_encodeName", new Class<?>[] {ExpandoColumn.class},
			expandoColumn);
	}

	private ODataRetriever<Organization> _getODataRetriever() {
		return _serviceTracker.getService();
	}

	@Inject
	private static ExpandoColumnLocalService _expandoColumnLocalService;

	private static ServiceTracker
		<ODataRetriever<Organization>, ODataRetriever<Organization>>
			_serviceTracker;

	@DeleteAfterTestRun
	private ExpandoTable _expandoTable;

	@Inject(
		filter = "component.name=com.liferay.segments.internal.model.listener.OrganizationExpandoColumnModelListener"
	)
	private ModelListener<ExpandoColumn> _modelListener;

	@Inject
	private OrganizationLocalService _organizationLocalService;

	@DeleteAfterTestRun
	private final List<Organization> _organizations = new ArrayList<>();

}