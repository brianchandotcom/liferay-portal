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

package com.liferay.info.display.field.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.info.display.field.ExpandoInfoDisplayFieldProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.expando.util.test.ExpandoTestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pavel Savinov
 */
@RunWith(Arquillian.class)
public class ExpandoInfoDisplayFieldProviderTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule testRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_defaultCompanyId = CompanyThreadLocal.getCompanyId();

		_company = CompanyTestUtil.addCompany();

		CompanyThreadLocal.setCompanyId(_company.getCompanyId());

		_expandoTable = _expandoTableLocalService.addDefaultTable(
			_company.getCompanyId(), User.class.getName());

		_user = UserTestUtil.addUser(_company);
	}

	@After
	public void tearDown() {
		CompanyThreadLocal.setCompanyId(_defaultCompanyId);
	}

	@Test
	public void testGetGeolocationExpandoInfoDisplayFieldValue()
		throws Exception {

		ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
			_expandoTable, "test-geolocation",
			ExpandoColumnConstants.GEOLOCATION);

		JSONObject valueJSONObject = JSONUtil.put(
			"latitude", "0.5"
		).put(
			"longitude", "0.5"
		);

		_addExpandoValue(expandoColumn, valueJSONObject.toString());

		Map<String, Object> infoDisplayFieldsValues =
			_expandoInfoDisplayFieldProvider.
				getContributorExpandoInfoDisplayFieldsValues(
					User.class.getName(), _user, LocaleUtil.getDefault());

		List<InfoDisplayField> infoDisplayFields = _getInfoDisplayFields(
			expandoColumn.getName());

		Assert.assertEquals(
			infoDisplayFields.toString(), 1, infoDisplayFields.size());

		InfoDisplayField infoDisplayField = infoDisplayFields.get(0);

		Assert.assertEquals(
			valueJSONObject.getString("latitude") + StringPool.COMMA_AND_SPACE +
				valueJSONObject.getString("longitude"),
			infoDisplayFieldsValues.get(infoDisplayField.getKey()));
	}

	@Test
	public void testGetLocalizedStringArrayExpandoInfoDisplayFieldValue()
		throws Exception {

		ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
			_expandoTable, "test-localized-string-array",
			ExpandoColumnConstants.STRING_ARRAY_LOCALIZED);

		Map<Locale, String[]> values = new HashMap<>();

		values.put(
			LocaleUtil.ENGLISH, new String[] {"en-value-1", "en-value-2"});
		values.put(
			LocaleUtil.FRENCH, new String[] {"fr-value-1", "fr-value-2"});

		ExpandoValue expandoValue = _addExpandoValue(expandoColumn, values);

		List<InfoDisplayField> infoDisplayFields = _getInfoDisplayFields(
			expandoColumn.getName());

		Assert.assertEquals(
			infoDisplayFields.toString(), 1, infoDisplayFields.size());

		InfoDisplayField infoDisplayField = infoDisplayFields.get(0);

		Map<String, Object> enInfoDisplayFieldsValues =
			_expandoInfoDisplayFieldProvider.
				getContributorExpandoInfoDisplayFieldsValues(
					User.class.getName(), _user, LocaleUtil.ENGLISH);

		Map<String, Object> frInfoDisplayFieldsValues =
			_expandoInfoDisplayFieldProvider.
				getContributorExpandoInfoDisplayFieldsValues(
					User.class.getName(), _user, LocaleUtil.FRENCH);

		Assert.assertEquals(
			StringUtil.merge(
				expandoValue.getStringArray(LocaleUtil.ENGLISH),
				StringPool.COMMA_AND_SPACE),
			enInfoDisplayFieldsValues.get(infoDisplayField.getKey()));

		Assert.assertEquals(
			StringUtil.merge(
				expandoValue.getStringArray(LocaleUtil.FRENCH),
				StringPool.COMMA_AND_SPACE),
			frInfoDisplayFieldsValues.get(infoDisplayField.getKey()));
	}

	@Test
	public void testGetStringArrayExpandoInfoDisplayFieldValue()
		throws Exception {

		ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
			_expandoTable, "test-string-array",
			ExpandoColumnConstants.STRING_ARRAY);

		String[] values = {"test-value-1", "test-value-2"};

		ExpandoValue expandoValue = _addExpandoValue(expandoColumn, values);

		Map<String, Object> infoDisplayFieldsValues =
			_expandoInfoDisplayFieldProvider.
				getContributorExpandoInfoDisplayFieldsValues(
					User.class.getName(), _user, LocaleUtil.getDefault());

		List<InfoDisplayField> infoDisplayFields = _getInfoDisplayFields(
			expandoColumn.getName());

		Assert.assertEquals(
			infoDisplayFields.toString(), 1, infoDisplayFields.size());

		InfoDisplayField infoDisplayField = infoDisplayFields.get(0);

		Assert.assertEquals(
			StringUtil.merge(
				expandoValue.getStringArray(), StringPool.COMMA_AND_SPACE),
			infoDisplayFieldsValues.get(infoDisplayField.getKey()));
	}

	@Test
	public void testGetStringExpandoInfoDisplayFieldValue() throws Exception {
		ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
			_expandoTable, "test-string", ExpandoColumnConstants.STRING);

		ExpandoValue expandoValue = _addExpandoValue(
			expandoColumn, "test-value");

		Map<String, Object> infoDisplayFieldsValues =
			_expandoInfoDisplayFieldProvider.
				getContributorExpandoInfoDisplayFieldsValues(
					User.class.getName(), _user, LocaleUtil.getDefault());

		List<InfoDisplayField> infoDisplayFields = _getInfoDisplayFields(
			expandoColumn.getName());

		Assert.assertEquals(
			infoDisplayFields.toString(), 1, infoDisplayFields.size());

		InfoDisplayField infoDisplayField = infoDisplayFields.get(0);

		Assert.assertEquals(
			expandoValue.getString(),
			infoDisplayFieldsValues.get(infoDisplayField.getKey()));
	}

	private ExpandoValue _addExpandoValue(
			ExpandoColumn expandoColumn, Object data)
		throws Exception {

		return _expandoValueLocalService.addValue(
			_company.getCompanyId(),
			PortalUtil.getClassName(_expandoTable.getClassNameId()),
			_expandoTable.getName(), expandoColumn.getName(),
			_user.getPrimaryKey(), data);
	}

	private List<InfoDisplayField> _getInfoDisplayFields(
		String expandoColumnName) {

		List<InfoDisplayField> infoDisplayFields =
			_expandoInfoDisplayFieldProvider.
				getContributorExpandoInfoDisplayFields(
					User.class.getName(), LocaleUtil.getDefault());

		Stream<InfoDisplayField> stream = infoDisplayFields.stream();

		return stream.filter(
			infoDisplayField -> StringUtil.equals(
				infoDisplayField.getLabel(), expandoColumnName)
		).collect(
			Collectors.toList()
		);
	}

	@DeleteAfterTestRun
	private Company _company;

	private long _defaultCompanyId;

	@Inject
	private ExpandoInfoDisplayFieldProvider _expandoInfoDisplayFieldProvider;

	private ExpandoTable _expandoTable;

	@Inject
	private ExpandoTableLocalService _expandoTableLocalService;

	@Inject
	private ExpandoValueLocalService _expandoValueLocalService;

	private User _user;

}