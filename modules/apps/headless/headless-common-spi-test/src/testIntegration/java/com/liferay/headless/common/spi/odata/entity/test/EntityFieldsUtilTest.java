/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.common.spi.odata.entity.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.headless.common.spi.odata.entity.EntityFieldsUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.sort.InvalidSortException;
import com.liferay.portal.search.expando.ExpandoBridgeIndexer;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Raposo
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class EntityFieldsUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_addExpandoColumn(
			"booleanArrayField", ExpandoColumnConstants.BOOLEAN_ARRAY);
		_addExpandoColumn("booleanField", ExpandoColumnConstants.BOOLEAN);
		_addExpandoColumn("dateArrayField", ExpandoColumnConstants.DATE_ARRAY);
		_addExpandoColumn("dateField", ExpandoColumnConstants.DATE);
		_addExpandoColumn(
			"doubleArrayField", ExpandoColumnConstants.DOUBLE_ARRAY);
		_addExpandoColumn("doubleField", ExpandoColumnConstants.DOUBLE);
		_addExpandoColumn(
			"floatArrayField", ExpandoColumnConstants.FLOAT_ARRAY);
		_addExpandoColumn("floatField", ExpandoColumnConstants.FLOAT);
		_addExpandoColumn(
			"geolocationField", ExpandoColumnConstants.GEOLOCATION);
		_addExpandoColumn(
			"integerArrayField", ExpandoColumnConstants.INTEGER_ARRAY);
		_addExpandoColumn("integerField", ExpandoColumnConstants.INTEGER);
		_addExpandoColumn("longArrayField", ExpandoColumnConstants.LONG_ARRAY);
		_addExpandoColumn("longField", ExpandoColumnConstants.LONG);
		_addExpandoColumn(
			"numberArrayField", ExpandoColumnConstants.NUMBER_ARRAY);
		_addExpandoColumn("numberField", ExpandoColumnConstants.NUMBER);
		_addExpandoColumn(
			"shortArrayField", ExpandoColumnConstants.SHORT_ARRAY);
		_addExpandoColumn("shortField", ExpandoColumnConstants.SHORT);
		_addExpandoColumn(
			"stringArrayField", ExpandoColumnConstants.STRING_ARRAY);
		_addExpandoColumn(
			"stringArrayLocalizedField",
			ExpandoColumnConstants.STRING_ARRAY_LOCALIZED);
		_addExpandoColumn("stringField", ExpandoColumnConstants.STRING);
		_addExpandoColumn(
			"stringLocalizedField", ExpandoColumnConstants.STRING_LOCALIZED);
	}

	@Test
	public void test() throws PortalException {
		for (EntityField entityField :
				EntityFieldsUtil.getEntityFields(
					_classNameLocalService.getClassNameId(User.class.getName()),
					TestPropsValues.getCompanyId(), _expandoBridgeIndexer,
					_expandoColumnLocalService, _expandoTableLocalService)) {

			if (Objects.equals(entityField.getName(), "dateField")) {
				Assert.assertNotNull(entityField.getSortableName(null));
			}
			else {
				Assert.assertThrows(
					InvalidSortException.class,
					() -> entityField.getSortableName(null));
			}
		}
	}

	private void _addExpandoColumn(String name, int type) throws Exception {
		ExpandoTable expandoTable = _expandoTableLocalService.fetchTable(
			TestPropsValues.getCompanyId(),
			_classNameLocalService.getClassNameId(User.class),
			ExpandoTableConstants.DEFAULT_TABLE_NAME);

		if (expandoTable == null) {
			expandoTable = _expandoTableLocalService.addTable(
				TestPropsValues.getCompanyId(),
				_classNameLocalService.getClassNameId(User.class),
				ExpandoTableConstants.DEFAULT_TABLE_NAME);
		}

		ExpandoColumn expandoColumn = _expandoColumnLocalService.addColumn(
			expandoTable.getTableId(), name, type);

		UnicodeProperties unicodeProperties =
			expandoColumn.getTypeSettingsProperties();

		unicodeProperties.setProperty(
			ExpandoColumnConstants.INDEX_TYPE,
			String.valueOf(ExpandoColumnConstants.INDEX_TYPE_KEYWORD));

		expandoColumn.setTypeSettingsProperties(unicodeProperties);

		_expandoColumnLocalService.updateExpandoColumn(expandoColumn);
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private ExpandoBridgeIndexer _expandoBridgeIndexer;

	@Inject
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Inject
	private ExpandoTableLocalService _expandoTableLocalService;

}