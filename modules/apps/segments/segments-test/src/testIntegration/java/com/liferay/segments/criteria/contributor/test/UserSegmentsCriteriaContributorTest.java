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

package com.liferay.segments.criteria.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.portlet.expando.util.test.ExpandoTestUtil;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;
import com.liferay.segments.criteria.Field;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;

import java.io.Serializable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
public class UserSegmentsCriteriaContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(entity.model.name=User)(objectClass=" +
				EntityModel.class.getName() + "))");

		_entityModelServiceTracker = registry.trackServices(filter);

		_entityModelServiceTracker.open();

		filter = registry.getFilter(
			"(&(objectClass=" + SegmentsCriteriaContributor.class.getName() +
				")(segments.criteria.contributor.key=user))");

		_segmentsCriteriaContributorServiceTracker = registry.trackServices(
			filter);

		_segmentsCriteriaContributorServiceTracker.open();
	}

	@AfterClass
	public static void tearDownClass() {
		_entityModelServiceTracker.close();

		_segmentsCriteriaContributorServiceTracker.close();
	}

	@Before
	public void setUp() throws Exception {
		_expandoTable = ExpandoTestUtil.addTable(
			PortalUtil.getClassNameId(User.class), "CUSTOM_FIELDS");
	}

	@Test
	public void testGetFields() throws Exception {
		_addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.STRING,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD, null);

		SegmentsCriteriaContributor segmentsCriteriaContributor =
			_getSegmentsCriteriaContributor();

		List<Field> fields = segmentsCriteriaContributor.getFields(
			LocaleUtil.getDefault());

		Stream<Field> stream = fields.stream();

		Set<String> fieldNames = stream.map(
			Field::getName
		).collect(
			Collectors.toSet()
		);

		EntityModel entityModel = _getEntityModel();

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		ComplexEntityField customFields =
			(ComplexEntityField)entityFieldsMap.remove("customField");

		Map<String, EntityField> customFieldsMap =
			customFields.getEntityFieldsMap();

		customFieldsMap.forEach(
			(s, entityField) -> entityFieldsMap.put(
				"customField/" + s, entityField));

		Assert.assertEquals(entityFieldsMap.keySet(), fieldNames);
	}

	@Test
	public void testGetFieldsWithOptions() throws Exception {
		String[] defaultValue = {"one", "two", "three"};

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.STRING_ARRAY,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD, defaultValue);

		SegmentsCriteriaContributor segmentsCriteriaContributor =
			_getSegmentsCriteriaContributor();

		List<Field> fields = segmentsCriteriaContributor.getFields(
			LocaleUtil.getDefault());

		Stream<Field> fieldStream = fields.stream();

		Optional<Field> optionalField = fieldStream.filter(
			field -> StringUtil.endsWith(
				field.getName(),
				FriendlyURLNormalizerUtil.normalize(expandoColumn.getName()))
		).findFirst();

		Assert.assertTrue(optionalField.isPresent());

		Field field = optionalField.get();

		List<Field.Option> options = field.getOptions();

		Stream<Field.Option> optionStream = options.stream();

		List<String> optionNames = optionStream.map(
			Field.Option::getName
		).collect(
			Collectors.toList()
		);

		Assert.assertEquals(Arrays.asList(defaultValue), optionNames);
	}

	private ExpandoColumn _addExpandoColumn(
			ExpandoTable expandoTable, String columnName, int columnType,
			int indexType, Serializable defaultValue)
		throws Exception {

		ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
			expandoTable, columnName, columnType);

		if (defaultValue != null) {
			ExpandoValue expandoValue = ExpandoTestUtil.addValue(
				_expandoTable, expandoColumn, defaultValue);

			expandoColumn.setDefaultData(expandoValue.getData());
		}

		UnicodeProperties unicodeProperties =
			expandoColumn.getTypeSettingsProperties();

		unicodeProperties.setProperty(
			ExpandoColumnConstants.INDEX_TYPE, String.valueOf(indexType));

		expandoColumn.setTypeSettingsProperties(unicodeProperties);

		return _expandoColumnLocalService.updateExpandoColumn(expandoColumn);
	}

	private EntityModel _getEntityModel() {
		return _entityModelServiceTracker.getService();
	}

	private SegmentsCriteriaContributor _getSegmentsCriteriaContributor() {
		return _segmentsCriteriaContributorServiceTracker.getService();
	}

	private static ServiceTracker<EntityModel, EntityModel>
		_entityModelServiceTracker;
	private static ServiceTracker
		<SegmentsCriteriaContributor, SegmentsCriteriaContributor>
			_segmentsCriteriaContributorServiceTracker;

	@Inject
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@DeleteAfterTestRun
	private ExpandoTable _expandoTable;

}