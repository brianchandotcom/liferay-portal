/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.custom.field.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.expando.kernel.exception.NoSuchValueException;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.test.util.ExpandoTestUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.custom.field.CustomField;
import com.liferay.portal.vulcan.custom.field.CustomFieldsUtil;
import com.liferay.portal.vulcan.custom.field.CustomValue;
import com.liferay.portal.vulcan.custom.field.Geo;

import java.io.Serializable;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carlos Correa
 */
@RunWith(Arquillian.class)
public class CustomFieldsUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_clazz = User.class;

		_enLocale = LocaleUtil.fromLanguageId("en_US");
		_frLocale = LocaleUtil.fromLanguageId("fr_FR");
		_ptLocale = LocaleUtil.fromLanguageId("pt_BR");

		_initialExpandoColumnsCount =
			_expandoColumnLocalService.getColumnsCount(
				TestPropsValues.getCompanyId(),
				_classNameLocalService.getClassNameId(_clazz), "CUSTOM_FIELDS");

		_expandoTable = ExpandoTestUtil.addTable(
			_classNameLocalService.getClassNameId(_clazz), "CUSTOM_FIELDS");

		_user = UserTestUtil.addCompanyAdminUser(
			_companyLocalService.fetchCompany(TestPropsValues.getCompanyId()));

		_expandoColumn1 = _addExpandoColumn(
			null, null, _expandoTable, ExpandoColumnConstants.BOOLEAN);
		_expandoColumn2 = _addExpandoColumn(
			null, null, _expandoTable, ExpandoColumnConstants.BOOLEAN_ARRAY);
		_expandoColumn3 = _addExpandoColumn(
			null, null, _expandoTable, ExpandoColumnConstants.DATE);
		_expandoColumn4 = _addExpandoColumn(
			null, null, _expandoTable, ExpandoColumnConstants.DATE_ARRAY);
		_expandoColumn5 = _addExpandoColumn(
			null, null, _expandoTable, ExpandoColumnConstants.DOUBLE);

		_expandoColumn6 = _addExpandoColumn(
			new double[] {
				_DOUBLE_VALUE, RandomTestUtil.randomDouble(),
				RandomTestUtil.randomDouble()
			},
			null, _expandoTable, ExpandoColumnConstants.DOUBLE_ARRAY);
		_expandoColumn7 = _addExpandoColumn(
			new double[] {
				_DOUBLE_VALUE, RandomTestUtil.randomDouble(),
				RandomTestUtil.randomDouble()
			},
			ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_RADIO, _expandoTable,
			ExpandoColumnConstants.DOUBLE_ARRAY);
		_expandoColumn8 = _addExpandoColumn(
			new double[] {
				_DOUBLE_VALUE, RandomTestUtil.randomDouble(),
				RandomTestUtil.randomDouble()
			},
			ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_SELECTION_LIST,
			_expandoTable, ExpandoColumnConstants.DOUBLE_ARRAY);

		_expandoColumn9 = _addExpandoColumn(
			null, null, _expandoTable, ExpandoColumnConstants.FLOAT);
		_expandoColumn10 = _addExpandoColumn(
			null, null, _expandoTable, ExpandoColumnConstants.FLOAT_ARRAY);
		_expandoColumn11 = _addExpandoColumn(
			null, null, _expandoTable, ExpandoColumnConstants.GEOLOCATION);
		_expandoColumn12 = _addExpandoColumn(
			null, null, _expandoTable, ExpandoColumnConstants.INTEGER);
		_expandoColumn13 = _addExpandoColumn(
			null, null, _expandoTable, ExpandoColumnConstants.INTEGER_ARRAY);
		_expandoColumn14 = _addExpandoColumn(
			null, null, _expandoTable, ExpandoColumnConstants.LONG);

		_expandoColumn15 = _addExpandoColumn(
			new long[] {
				_LONG_VALUE, RandomTestUtil.randomLong(),
				RandomTestUtil.randomLong()
			},
			null, _expandoTable, ExpandoColumnConstants.LONG_ARRAY);
		_expandoColumn16 = _addExpandoColumn(
			new long[] {
				_LONG_VALUE, RandomTestUtil.randomLong(),
				RandomTestUtil.randomLong()
			},
			ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_RADIO, _expandoTable,
			ExpandoColumnConstants.LONG_ARRAY);
		_expandoColumn17 = _addExpandoColumn(
			new long[] {
				_LONG_VALUE, RandomTestUtil.randomLong(),
				RandomTestUtil.randomLong()
			},
			ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_SELECTION_LIST,
			_expandoTable, ExpandoColumnConstants.LONG_ARRAY);

		_expandoColumn18 = _addExpandoColumn(
			null, null, _expandoTable, ExpandoColumnConstants.NUMBER);
		_expandoColumn19 = _addExpandoColumn(
			null, null, _expandoTable, ExpandoColumnConstants.NUMBER_ARRAY);
		_expandoColumn20 = _addExpandoColumn(
			null, null, _expandoTable, ExpandoColumnConstants.SHORT);
		_expandoColumn21 = _addExpandoColumn(
			null, null, _expandoTable, ExpandoColumnConstants.SHORT_ARRAY);
		_expandoColumn22 = _addExpandoColumn(
			null, null, _expandoTable, ExpandoColumnConstants.STRING);

		_expandoColumn23 = _addExpandoColumn(
			new String[] {
				_STRING_VALUE, RandomTestUtil.randomString(),
				RandomTestUtil.randomString()
			},
			null, _expandoTable, ExpandoColumnConstants.STRING_ARRAY);
		_expandoColumn24 = _addExpandoColumn(
			new String[] {
				_STRING_VALUE, RandomTestUtil.randomString(),
				RandomTestUtil.randomString()
			},
			ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_RADIO, _expandoTable,
			ExpandoColumnConstants.STRING_ARRAY);
		_expandoColumn25 = _addExpandoColumn(
			new String[] {
				_STRING_VALUE, RandomTestUtil.randomString(),
				RandomTestUtil.randomString()
			},
			ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_SELECTION_LIST,
			_expandoTable, ExpandoColumnConstants.STRING_ARRAY);

		_expandoColumn26 = _addExpandoColumn(
			null, null, _expandoTable,
			ExpandoColumnConstants.STRING_ARRAY_LOCALIZED);
		_expandoColumn27 = _addExpandoColumn(
			null, null, _expandoTable, ExpandoColumnConstants.STRING_LOCALIZED);
	}

	@Test
	public void testToCustomFieldsCustomFieldValues() throws Exception {
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn1, _user.getPrimaryKey(), true);
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn2, _user.getPrimaryKey(),
			new boolean[] {true});

		Date randomDate = RandomTestUtil.nextDate();

		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn3, _user.getPrimaryKey(), randomDate);
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn4, _user.getPrimaryKey(),
			new Date[] {randomDate});

		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn5, _user.getPrimaryKey(),
			_DOUBLE_VALUE);
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn6, _user.getPrimaryKey(),
			new double[] {_DOUBLE_VALUE});
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn7, _user.getPrimaryKey(),
			new double[] {_DOUBLE_VALUE});
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn8, _user.getPrimaryKey(),
			new double[] {_DOUBLE_VALUE});

		float randomFloat = RandomTestUtil.randomFloat();

		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn9, _user.getPrimaryKey(), randomFloat);
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn10, _user.getPrimaryKey(),
			new float[] {randomFloat});

		double randomDouble1 = RandomTestUtil.randomDouble();
		double randomDouble2 = RandomTestUtil.randomDouble();

		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn11, _user.getPrimaryKey(),
			JSONUtil.put(
				"latitude", randomDouble1
			).put(
				"longitude", randomDouble2
			).toString());

		int randomInt = RandomTestUtil.randomInt();

		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn12, _user.getPrimaryKey(), randomInt);
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn13, _user.getPrimaryKey(),
			new int[] {randomInt});

		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn14, _user.getPrimaryKey(),
			_LONG_VALUE);
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn15, _user.getPrimaryKey(),
			new long[] {_LONG_VALUE});
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn16, _user.getPrimaryKey(),
			new long[] {_LONG_VALUE});
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn17, _user.getPrimaryKey(),
			new long[] {_LONG_VALUE});

		Number randomNumber = RandomTestUtil.randomInt();

		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn18, _user.getPrimaryKey(),
			randomNumber);
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn19, _user.getPrimaryKey(),
			new Number[] {randomNumber});

		short randomShort = (short)RandomTestUtil.randomInt(
			Short.MIN_VALUE, Short.MAX_VALUE);

		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn20, _user.getPrimaryKey(),
			randomShort);
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn21, _user.getPrimaryKey(),
			new short[] {randomShort});

		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn22, _user.getPrimaryKey(),
			_STRING_VALUE);
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn23, _user.getPrimaryKey(),
			new String[] {_STRING_VALUE});
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn24, _user.getPrimaryKey(),
			new String[] {_STRING_VALUE});
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn25, _user.getPrimaryKey(),
			new String[] {_STRING_VALUE});

		String randomString = RandomTestUtil.randomString();

		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn26, _user.getPrimaryKey(),
			HashMapBuilder.put(
				_enLocale, new String[] {_STRING_VALUE}
			).put(
				_frLocale, new String[] {randomString}
			).put(
				_ptLocale, new String[] {randomString}
			).build());
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn27, _user.getPrimaryKey(),
			HashMapBuilder.put(
				_enLocale, _STRING_VALUE
			).put(
				_frLocale, randomString
			).put(
				_ptLocale, randomString
			).build());

		CustomField[] customFields = CustomFieldsUtil.toCustomFields(
			true, _clazz.getName(), _user.getPrimaryKey(),
			TestPropsValues.getCompanyId(), LocaleUtil.getDefault());

		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = true;
						}
					};
					dataType = "";
					name = _expandoColumn1.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn1.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new boolean[] {true};
						}
					};
					dataType = "";
					name = _expandoColumn2.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn2.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = _dateFormat.format(randomDate);
						}
					};
					dataType = "";
					name = _expandoColumn3.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn3.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new Date[] {randomDate};
						}
					};
					dataType = "";
					name = _expandoColumn4.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn4.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = _DOUBLE_VALUE;
						}
					};
					dataType = "Decimal";
					name = _expandoColumn5.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn5.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new double[] {_DOUBLE_VALUE};
						}
					};
					dataType = "Decimal";
					name = _expandoColumn6.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn6.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new double[] {_DOUBLE_VALUE};
						}
					};
					dataType = "Decimal";
					name = _expandoColumn7.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn7.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new double[] {_DOUBLE_VALUE};
						}
					};
					dataType = "Decimal";
					name = _expandoColumn8.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn8.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = randomFloat;
						}
					};
					dataType = "Decimal";
					name = _expandoColumn9.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn9.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new float[] {randomFloat};
						}
					};
					dataType = "Decimal";
					name = _expandoColumn10.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn10.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							geo = new Geo() {
								{
									latitude = randomDouble1;
									longitude = randomDouble2;
								}
							};
						}
					};
					dataType = "Geolocation";
					name = _expandoColumn11.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn11.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = randomInt;
						}
					};
					dataType = "Integer";
					name = _expandoColumn12.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn12.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new int[] {randomInt};
						}
					};
					dataType = "Integer";
					name = _expandoColumn13.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn13.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = _LONG_VALUE;
						}
					};
					dataType = "Integer";
					name = _expandoColumn14.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn14.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new long[] {_LONG_VALUE};
						}
					};
					dataType = "Integer";
					name = _expandoColumn15.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn15.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new long[] {_LONG_VALUE};
						}
					};
					dataType = "Integer";
					name = _expandoColumn16.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn16.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new long[] {_LONG_VALUE};
						}
					};
					dataType = "Integer";
					name = _expandoColumn17.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn17.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new BigDecimal(randomNumber.intValue());
						}
					};
					dataType = "";
					name = _expandoColumn18.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn18.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new Number[] {
								new BigDecimal(randomNumber.intValue())
							};
						}
					};
					dataType = "";
					name = _expandoColumn19.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn19.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = randomShort;
						}
					};
					dataType = "Integer";
					name = _expandoColumn20.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn20.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new short[] {randomShort};
						}
					};
					dataType = "Integer";
					name = _expandoColumn21.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn21.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = _STRING_VALUE;
						}
					};
					dataType = "Text";
					name = _expandoColumn22.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn22.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new String[] {_STRING_VALUE};
						}
					};
					dataType = "Text";
					name = _expandoColumn23.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn23.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new String[] {_STRING_VALUE};
						}
					};
					dataType = "Text";
					name = _expandoColumn24.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn24.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new String[] {_STRING_VALUE};
						}
					};
					dataType = "Text";
					name = _expandoColumn25.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn25.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = HashMapBuilder.put(
								_enLocale, new String[] {_STRING_VALUE}
							).put(
								_frLocale, new String[] {randomString}
							).put(
								_ptLocale, new String[] {randomString}
							).build();
						}
					};
					dataType = "";
					name = _expandoColumn26.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn26.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = _STRING_VALUE;
							data_i18n = HashMapBuilder.put(
								"en-US", _STRING_VALUE
							).put(
								"fr-FR", randomString
							).put(
								"pt-BR", randomString
							).build();
						}
					};
					dataType = "Text";
					name = _expandoColumn27.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn27.getName()));
	}

	@Test
	public void testToCustomFieldsDefaultCustomFieldValues() throws Exception {
		CustomField[] customFields = CustomFieldsUtil.toCustomFields(
			true, _clazz.getName(), _user.getPrimaryKey(),
			TestPropsValues.getCompanyId(), LocaleUtil.getDefault());

		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = false;
						}
					};
					dataType = "";
					name = _expandoColumn1.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn1.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new boolean[0];
						}
					};
					dataType = "";
					name = _expandoColumn2.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn2.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = "1970-01-01T00:00:00Z";
						}
					};
					dataType = "";
					name = _expandoColumn3.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn3.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new String[0];
						}
					};
					dataType = "";
					name = _expandoColumn4.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn4.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = 0.0;
						}
					};
					dataType = "Decimal";
					name = _expandoColumn5.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn5.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new double[] {0.0};
						}
					};
					dataType = "Decimal";
					name = _expandoColumn6.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn6.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new double[] {0.0};
						}
					};
					dataType = "Decimal";
					name = _expandoColumn7.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn7.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new double[] {_DOUBLE_VALUE};
						}
					};
					dataType = "Decimal";
					name = _expandoColumn8.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn8.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = (float)0.0;
						}
					};
					dataType = "Decimal";
					name = _expandoColumn9.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn9.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new float[0];
						}
					};
					dataType = "Decimal";
					name = _expandoColumn10.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn10.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							geo = new Geo() {
								{
									latitude = Double.NaN;
									longitude = Double.NaN;
								}
							};
						}
					};
					dataType = "Geolocation";
					name = _expandoColumn11.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn11.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = 0;
						}
					};
					dataType = "Integer";
					name = _expandoColumn12.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn12.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new int[0];
						}
					};
					dataType = "Integer";
					name = _expandoColumn13.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn13.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = 0L;
						}
					};
					dataType = "Integer";
					name = _expandoColumn14.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn14.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new long[] {0L};
						}
					};
					dataType = "Integer";
					name = _expandoColumn15.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn15.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new long[] {0L};
						}
					};
					dataType = "Integer";
					name = _expandoColumn16.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn16.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new long[] {_LONG_VALUE};
						}
					};
					dataType = "Integer";
					name = _expandoColumn17.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn17.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = 0;
						}
					};
					dataType = "";
					name = _expandoColumn18.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn18.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new Number[0];
						}
					};
					dataType = "";
					name = _expandoColumn19.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn19.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = (short)0;
						}
					};
					dataType = "Integer";
					name = _expandoColumn20.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn20.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new short[0];
						}
					};
					dataType = "Integer";
					name = _expandoColumn21.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn21.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = "";
						}
					};
					dataType = "Text";
					name = _expandoColumn22.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn22.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new String[] {"false"};
						}
					};
					dataType = "Text";
					name = _expandoColumn23.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn23.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new String[] {"false"};
						}
					};
					dataType = "Text";
					name = _expandoColumn24.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn24.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new String[] {_STRING_VALUE};
						}
					};
					dataType = "Text";
					name = _expandoColumn25.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn25.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data = new HashMap<>();
						}
					};
					dataType = "";
					name = _expandoColumn26.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn26.getName()));
		_assertEquals(
			new CustomField() {
				{
					customValue = new CustomValue() {
						{
							data_i18n = new HashMap<>();
						}
					};
					dataType = "Text";
					name = _expandoColumn27.getName();
				}
			},
			_getCustomField(customFields, _expandoColumn27.getName()));
	}

	@Test
	public void testToCustomFieldsMissingCustomFields() throws Exception {

		// Random className

		Assert.assertEquals(
			new CustomField[0],
			CustomFieldsUtil.toCustomFields(
				true, RandomTestUtil.randomString(),
				RandomTestUtil.randomLong(), TestPropsValues.getCompanyId(),
				LocaleUtil.getDefault()));

		// Random primaryKey

		Assert.assertEquals(
			new CustomField[0],
			CustomFieldsUtil.toCustomFields(
				true, DLFileEntry.class.getName(), RandomTestUtil.randomLong(),
				TestPropsValues.getCompanyId(), LocaleUtil.getDefault()));
	}

	@Test
	public void testToMapCustomFieldValues() throws Exception {
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn1, _user.getPrimaryKey(), true);
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn2, _user.getPrimaryKey(),
			new boolean[] {true});

		Date randomDate = RandomTestUtil.nextDate();

		randomDate = new Date((randomDate.getTime() / 1000) * 1000);

		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn3, _user.getPrimaryKey(), randomDate);
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn4, _user.getPrimaryKey(),
			new Date[] {randomDate});

		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn5, _user.getPrimaryKey(),
			_DOUBLE_VALUE);
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn6, _user.getPrimaryKey(),
			new double[] {_DOUBLE_VALUE});
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn7, _user.getPrimaryKey(),
			new double[] {_DOUBLE_VALUE});
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn8, _user.getPrimaryKey(),
			new double[] {_DOUBLE_VALUE});

		float randomFloat = RandomTestUtil.randomFloat();

		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn9, _user.getPrimaryKey(), randomFloat);
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn10, _user.getPrimaryKey(),
			new float[] {randomFloat});

		double randomDouble1 = RandomTestUtil.randomDouble();
		double randomDouble2 = RandomTestUtil.randomDouble();

		JSONObject jsonObject = JSONUtil.put(
			"latitude", randomDouble1
		).put(
			"longitude", randomDouble2
		);

		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn11, _user.getPrimaryKey(),
			jsonObject.toString());

		int randomInt = RandomTestUtil.randomInt();

		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn12, _user.getPrimaryKey(), randomInt);
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn13, _user.getPrimaryKey(),
			new int[] {randomInt});

		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn14, _user.getPrimaryKey(),
			_LONG_VALUE);
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn15, _user.getPrimaryKey(),
			new long[] {_LONG_VALUE});
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn16, _user.getPrimaryKey(),
			new long[] {_LONG_VALUE});
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn17, _user.getPrimaryKey(),
			new long[] {_LONG_VALUE});

		Number randomNumber = RandomTestUtil.randomInt();

		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn18, _user.getPrimaryKey(),
			randomNumber);
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn19, _user.getPrimaryKey(),
			new Number[] {randomNumber});

		short randomShort = (short)RandomTestUtil.randomInt(
			Short.MIN_VALUE, Short.MAX_VALUE);

		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn20, _user.getPrimaryKey(),
			randomShort);
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn21, _user.getPrimaryKey(),
			new short[] {randomShort});

		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn22, _user.getPrimaryKey(),
			_STRING_VALUE);
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn23, _user.getPrimaryKey(),
			new String[] {_STRING_VALUE});
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn24, _user.getPrimaryKey(),
			new String[] {_STRING_VALUE});
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn25, _user.getPrimaryKey(),
			new String[] {_STRING_VALUE});

		String randomString = RandomTestUtil.randomString();

		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn26, _user.getPrimaryKey(),
			HashMapBuilder.put(
				_enLocale, new String[] {_STRING_VALUE}
			).put(
				_frLocale, new String[] {randomString}
			).put(
				_ptLocale, new String[] {randomString}
			).build());
		ExpandoTestUtil.addValue(
			_expandoTable, _expandoColumn27, _user.getPrimaryKey(),
			HashMapBuilder.put(
				_enLocale, _STRING_VALUE
			).put(
				_frLocale, randomString
			).put(
				_ptLocale, randomString
			).build());

		Map<String, Serializable> map = CustomFieldsUtil.toMap(
			_clazz.getName(), TestPropsValues.getCompanyId(),
			CustomFieldsUtil.toCustomFields(
				true, _clazz.getName(), _user.getPrimaryKey(),
				TestPropsValues.getCompanyId(), LocaleUtil.getDefault()),
			LocaleUtil.getDefault());

		Assert.assertEquals(map.toString(), _initialExpandoColumnsCount + 27, map.size());

		Assert.assertTrue((boolean)map.get(_expandoColumn1.getName()));
		Assert.assertArrayEquals(
			new boolean[] {true},
			(boolean[])map.get(_expandoColumn2.getName()));
		Assert.assertTrue(
			DateUtil.equals(
				randomDate, (Date)map.get(_expandoColumn3.getName())));
		Assert.assertArrayEquals(
			new Date[] {randomDate},
			(Date[])map.get(_expandoColumn4.getName()));
		Assert.assertEquals(_DOUBLE_VALUE, map.get(_expandoColumn5.getName()));
		Assert.assertArrayEquals(
			new double[] {_DOUBLE_VALUE},
			(double[])map.get(_expandoColumn6.getName()), 0);
		Assert.assertArrayEquals(
			new double[] {_DOUBLE_VALUE},
			(double[])map.get(_expandoColumn7.getName()), 0);
		Assert.assertArrayEquals(
			new double[] {_DOUBLE_VALUE},
			(double[])map.get(_expandoColumn8.getName()), 0);
		Assert.assertEquals(randomFloat, map.get(_expandoColumn9.getName()));
		Assert.assertArrayEquals(
			new float[] {randomFloat},
			(float[])map.get(_expandoColumn10.getName()), 0);
		Assert.assertEquals(
			jsonObject.toString(), map.get(_expandoColumn11.getName()));
		Assert.assertEquals(randomInt, map.get(_expandoColumn12.getName()));
		Assert.assertArrayEquals(
			new int[] {randomInt}, (int[])map.get(_expandoColumn13.getName()));
		Assert.assertEquals(_LONG_VALUE, map.get(_expandoColumn14.getName()));
		Assert.assertArrayEquals(
			new long[] {_LONG_VALUE},
			(long[])map.get(_expandoColumn15.getName()));
		Assert.assertArrayEquals(
			new long[] {_LONG_VALUE},
			(long[])map.get(_expandoColumn16.getName()));
		Assert.assertArrayEquals(
			new long[] {_LONG_VALUE},
			(long[])map.get(_expandoColumn17.getName()));
		Assert.assertEquals(
			new BigDecimal(randomNumber.intValue()),
			map.get(_expandoColumn18.getName()));
		Assert.assertArrayEquals(
			new Number[] {new BigDecimal(randomNumber.intValue())},
			(Number[])map.get(_expandoColumn19.getName()));
		Assert.assertEquals(randomShort, map.get(_expandoColumn20.getName()));
		Assert.assertArrayEquals(
			new short[] {randomShort},
			(short[])map.get(_expandoColumn21.getName()));
		Assert.assertEquals(_STRING_VALUE, map.get(_expandoColumn22.getName()));
		Assert.assertArrayEquals(
			new String[] {_STRING_VALUE},
			(String[])map.get(_expandoColumn23.getName()));
		Assert.assertArrayEquals(
			new String[] {_STRING_VALUE},
			(String[])map.get(_expandoColumn24.getName()));
		Assert.assertArrayEquals(
			new String[] {_STRING_VALUE},
			(String[])map.get(_expandoColumn25.getName()));
		AssertUtils.assertEquals(
			HashMapBuilder.put(
				_enLocale, new String[] {_STRING_VALUE}
			).put(
				_frLocale, new String[] {randomString}
			).put(
				_ptLocale, new String[] {randomString}
			).build(),
			(Map)map.get(_expandoColumn26.getName()));
		AssertUtils.assertEquals(
			HashMapBuilder.put(
				_enLocale, _STRING_VALUE
			).put(
				_frLocale, randomString
			).put(
				_ptLocale, randomString
			).build(),
			(Map)map.get(_expandoColumn27.getName()));
	}

	@Test
	public void testToMapDefaultCustomFieldValues() throws Exception {
		Map<String, Serializable> map = CustomFieldsUtil.toMap(
			_clazz.getName(), TestPropsValues.getCompanyId(),
			CustomFieldsUtil.toCustomFields(
				true, _clazz.getName(), RandomTestUtil.randomLong(),
				TestPropsValues.getCompanyId(), LocaleUtil.getDefault()),
			LocaleUtil.getDefault());

		Assert.assertEquals(map.toString(), _initialExpandoColumnsCount + 27, map.size());

		Assert.assertFalse((boolean)map.get(_expandoColumn1.getName()));
		Assert.assertArrayEquals(
			new boolean[0], (boolean[])map.get(_expandoColumn2.getName()));
		Assert.assertEquals(new Date(0), map.get(_expandoColumn3.getName()));
		Assert.assertArrayEquals(
			new Date[0], (Date[])map.get(_expandoColumn4.getName()));
		Assert.assertEquals(0.0, map.get(_expandoColumn5.getName()));
		Assert.assertArrayEquals(
			new double[] {0.0}, (double[])map.get(_expandoColumn6.getName()),
			0);
		Assert.assertArrayEquals(
			new double[] {0.0}, (double[])map.get(_expandoColumn7.getName()),
			0);
		Assert.assertArrayEquals(
			new double[] {_DOUBLE_VALUE},
			(double[])map.get(_expandoColumn8.getName()), 0);
		Assert.assertEquals((float)0.0, map.get(_expandoColumn9.getName()));
		Assert.assertArrayEquals(
			new float[0], (float[])map.get(_expandoColumn10.getName()), 0);
		Assert.assertEquals("{}", map.get(_expandoColumn11.getName()));
		Assert.assertEquals(0, map.get(_expandoColumn12.getName()));
		Assert.assertArrayEquals(
			new int[0], (int[])map.get(_expandoColumn13.getName()));
		Assert.assertEquals(0L, map.get(_expandoColumn14.getName()));
		Assert.assertArrayEquals(
			new long[] {0L}, (long[])map.get(_expandoColumn15.getName()));
		Assert.assertArrayEquals(
			new long[] {0L}, (long[])map.get(_expandoColumn16.getName()));
		Assert.assertArrayEquals(
			new long[] {_LONG_VALUE},
			(long[])map.get(_expandoColumn17.getName()));
		Assert.assertEquals(0, map.get(_expandoColumn18.getName()));
		Assert.assertArrayEquals(
			new Number[0], (Number[])map.get(_expandoColumn19.getName()));
		Assert.assertEquals((short)0, map.get(_expandoColumn20.getName()));
		Assert.assertArrayEquals(
			new short[0], (short[])map.get(_expandoColumn21.getName()));
		Assert.assertEquals("", map.get(_expandoColumn22.getName()));
		Assert.assertArrayEquals(
			new String[] {"false"},
			(String[])map.get(_expandoColumn23.getName()));
		Assert.assertArrayEquals(
			new String[] {"false"},
			(String[])map.get(_expandoColumn24.getName()));
		Assert.assertArrayEquals(
			new String[] {_STRING_VALUE},
			(String[])map.get(_expandoColumn25.getName()));
		Assert.assertEquals(
			new HashMap<>(), map.get(_expandoColumn26.getName()));
		Assert.assertEquals(
			new HashMap<>(), map.get(_expandoColumn27.getName()));
	}

	private ExpandoColumn _addExpandoColumn(
			Object defaultData, String displayType, ExpandoTable expandoTable,
			int type)
		throws Exception {

		ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
			expandoTable, "A" + RandomTestUtil.randomString(), type,
			defaultData);

		if (displayType != null) {
			UnicodeProperties unicodeProperties =
				expandoColumn.getTypeSettingsProperties();

			unicodeProperties.putAll(
				HashMapBuilder.put(
					ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE, displayType
				).build());

			expandoColumn.setTypeSettingsProperties(unicodeProperties);
		}

		return _expandoColumnLocalService.updateExpandoColumn(expandoColumn);
	}

	private void _assertEquals(
		CustomField customField1, CustomField customField2) {

		_assertEquals(
			customField1.getCustomValue(), customField2.getCustomValue());

		Assert.assertEquals(customField1.getName(), customField2.getName());
		Assert.assertEquals(
			customField1.getDataType(), customField2.getDataType());
	}

	private void _assertEquals(
		CustomValue customValue1, CustomValue customValue2) {

		if (customValue1 == customValue2) {
			return;
		}

		if (customValue1.getData() instanceof Map) {
			AssertUtils.assertEquals(
				(Map)customValue1.getData(), (Map)customValue2.getData());
		}
		else {
			Assert.assertTrue(
				Objects.deepEquals(
					customValue1.getData(), customValue2.getData()));
		}

		if (customValue1.getData_i18n() != customValue2.getData_i18n()) {
			AssertUtils.assertEquals(
				(Map)customValue1.getData_i18n(),
				(Map)customValue2.getData_i18n());
		}

		_assertEquals(customValue1.getGeo(), customValue2.getGeo());
	}

	private void _assertEquals(Geo geo1, Geo geo2) {
		if (geo1 == geo2) {
			return;
		}

		Assert.assertEquals(geo1.getLatitude(), geo2.getLatitude());
		Assert.assertEquals(geo1.getLongitude(), geo2.getLongitude());
	}

	private CustomField _getCustomField(CustomField[] customFields, String name)
		throws Exception {

		for (CustomField customField : customFields) {
			if (StringUtil.equals(customField.getName(), name)) {
				return customField;
			}
		}

		throw new NoSuchValueException();
	}

	private static final double _DOUBLE_VALUE = RandomTestUtil.randomDouble();

	private static final long _LONG_VALUE = RandomTestUtil.randomLong();

	private static final String _STRING_VALUE = RandomTestUtil.randomString();

	private static final DateFormat _dateFormat = new SimpleDateFormat(
		"yyyy-MM-dd'T'HH:mm:ss'Z'");

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private Class<?> _clazz;

	@Inject
	private CompanyLocalService _companyLocalService;

	private Locale _enLocale;
	private ExpandoColumn _expandoColumn1;
	private ExpandoColumn _expandoColumn2;
	private ExpandoColumn _expandoColumn3;
	private ExpandoColumn _expandoColumn4;
	private ExpandoColumn _expandoColumn5;
	private ExpandoColumn _expandoColumn6;
	private ExpandoColumn _expandoColumn7;
	private ExpandoColumn _expandoColumn8;
	private ExpandoColumn _expandoColumn9;
	private ExpandoColumn _expandoColumn10;
	private ExpandoColumn _expandoColumn11;
	private ExpandoColumn _expandoColumn12;
	private ExpandoColumn _expandoColumn13;
	private ExpandoColumn _expandoColumn14;
	private ExpandoColumn _expandoColumn15;
	private ExpandoColumn _expandoColumn16;
	private ExpandoColumn _expandoColumn17;
	private ExpandoColumn _expandoColumn18;
	private ExpandoColumn _expandoColumn19;
	private ExpandoColumn _expandoColumn20;
	private ExpandoColumn _expandoColumn21;
	private ExpandoColumn _expandoColumn22;
	private ExpandoColumn _expandoColumn23;
	private ExpandoColumn _expandoColumn24;
	private ExpandoColumn _expandoColumn25;
	private ExpandoColumn _expandoColumn26;
	private ExpandoColumn _expandoColumn27;

	@Inject
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@DeleteAfterTestRun
	private ExpandoTable _expandoTable;

	private Locale _frLocale;
	private int _initialExpandoColumnsCount;
	private Locale _ptLocale;

	@DeleteAfterTestRun
	private User _user;

}