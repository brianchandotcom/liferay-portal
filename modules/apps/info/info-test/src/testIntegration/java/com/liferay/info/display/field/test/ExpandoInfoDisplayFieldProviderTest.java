/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.display.field.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.expando.test.util.ExpandoTestUtil;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

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
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_expandoTable = _expandoTableLocalService.addDefaultTable(
			TestPropsValues.getCompanyId(), User.class.getName());
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

		Assert.assertEquals(
			valueJSONObject.getString("latitude") + StringPool.COMMA_AND_SPACE +
				valueJSONObject.getString("longitude"),
			_getValue(expandoColumn.getName(), LocaleUtil.getDefault()));
	}

	@Test
	public void testGetLocalizedStringArrayExpandoInfoDisplayFieldValue()
		throws Exception {

		ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
			_expandoTable, "test-localized-string-array",
			ExpandoColumnConstants.STRING_ARRAY_LOCALIZED);

		ExpandoValue expandoValue = _addExpandoValue(
			expandoColumn,
			HashMapBuilder.put(
				LocaleUtil.ENGLISH, new String[] {"en-value-1", "en-value-2"}
			).put(
				LocaleUtil.FRENCH, new String[] {"fr-value-1", "fr-value-2"}
			).build());

		Assert.assertEquals(
			StringUtil.merge(
				expandoValue.getStringArray(LocaleUtil.ENGLISH),
				StringPool.COMMA_AND_SPACE),
			_getValue(expandoColumn.getName(), LocaleUtil.ENGLISH));

		Assert.assertEquals(
			StringUtil.merge(
				expandoValue.getStringArray(LocaleUtil.FRENCH),
				StringPool.COMMA_AND_SPACE),
			_getValue(expandoColumn.getName(), LocaleUtil.FRENCH));
	}

	@Test
	public void testGetLocalizedStringExpandoInfoDisplayFieldValue()
		throws Exception {

		ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
			_expandoTable, "test-localized-string",
			ExpandoColumnConstants.STRING_LOCALIZED);

		ExpandoValue expandoValue = _addExpandoValue(
			expandoColumn,
			HashMapBuilder.put(
				LocaleUtil.ENGLISH, "en-value-1"
			).put(
				LocaleUtil.FRENCH, "fr-value-1"
			).build());

		Assert.assertEquals(
			expandoValue.getString(LocaleUtil.ENGLISH),
			_getValue(expandoColumn.getName(), LocaleUtil.ENGLISH));

		Assert.assertEquals(
			expandoValue.getString(LocaleUtil.FRENCH),
			_getValue(expandoColumn.getName(), LocaleUtil.FRENCH));
	}

	@Test
	public void testGetStringArrayExpandoInfoDisplayFieldValue()
		throws Exception {

		ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
			_expandoTable, "test-string-array",
			ExpandoColumnConstants.STRING_ARRAY);

		ExpandoValue expandoValue = _addExpandoValue(
			expandoColumn, new String[] {"test-value-1", "test-value-2"});

		Assert.assertEquals(
			StringUtil.merge(
				expandoValue.getStringArray(), StringPool.COMMA_AND_SPACE),
			_getValue(expandoColumn.getName(), LocaleUtil.getDefault()));
	}

	@Test
	public void testGetStringExpandoInfoDisplayFieldValue() throws Exception {
		ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
			_expandoTable, "test-string", ExpandoColumnConstants.STRING);

		ExpandoValue expandoValue = _addExpandoValue(
			expandoColumn, "test-value");

		Assert.assertEquals(
			expandoValue.getString(),
			_getValue(expandoColumn.getName(), LocaleUtil.getDefault()));
	}

	private ExpandoValue _addExpandoValue(
			ExpandoColumn expandoColumn, Object data)
		throws Exception {

		return _expandoValueLocalService.addValue(
			TestPropsValues.getCompanyId(),
			PortalUtil.getClassName(_expandoTable.getClassNameId()),
			_expandoTable.getName(), expandoColumn.getName(),
			TestPropsValues.getUserId(), data);
	}

	private String _getKey(String expandoColumnName) {
		return _CUSTOM_FIELD_PREFIX +
			expandoColumnName.replaceAll("\\W", StringPool.UNDERLINE);
	}

	private Object _getValue(String expandoColumnName, Locale locale)
		throws Exception {

		List<InfoFieldValue<Object>> infoDisplayFieldsValues =
			_expandoInfoItemFieldSetProvider.getInfoFieldValues(
				User.class.getName(), TestPropsValues.getUser());

		for (InfoFieldValue<Object> infoFieldValue : infoDisplayFieldsValues) {
			InfoField<?> infoField = infoFieldValue.getInfoField();

			if (Objects.equals(
					infoField.getName(), _getKey(expandoColumnName))) {

				return infoFieldValue.getValue(locale);
			}
		}

		return null;
	}

	private static final String _CUSTOM_FIELD_PREFIX = "_CUSTOM_FIELD_";

	@Inject
	private ExpandoInfoItemFieldSetProvider _expandoInfoItemFieldSetProvider;

	@DeleteAfterTestRun
	private ExpandoTable _expandoTable;

	@Inject
	private ExpandoTableLocalService _expandoTableLocalService;

	@Inject
	private ExpandoValueLocalService _expandoValueLocalService;

}