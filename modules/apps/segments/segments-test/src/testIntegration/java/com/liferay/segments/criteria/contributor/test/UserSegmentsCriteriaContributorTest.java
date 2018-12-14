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
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
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
import com.liferay.segments.odata.retriever.ODataRetriever;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo Garcia
 */
@RunWith(Arquillian.class)
public class UserSegmentsCriteriaContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_expandoTable = ExpandoTestUtil.addTable(
			PortalUtil.getClassNameId(User.class), "CUSTOM_FIELDS");

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(model.class.name=com.liferay.portal.kernel.model.User)" +
				"(objectClass=" + ODataRetriever.class.getName() + "))");

		_serviceTracker = registry.trackServices(filter);

		_serviceTracker.open();
	}

	@Test
	public void testGetFields() throws Exception {
		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.STRING,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

		String columnValue = RandomTestUtil.randomString();

		User user = _addUser(expandoColumn.getName(), columnValue);

		_users.add(user);

		List<Field> fields = _segmentsCriteriaContributor.getFields(
			LocaleUtil.getDefault());

		Stream<Field> stream = fields.stream();

		Set<String> fieldNames = stream.map(
			Field::getName
		).collect(
			Collectors.toSet()
		);

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		ComplexEntityField customFields =
			(ComplexEntityField)entityFieldsMap.remove("customField");

		Map<String, EntityField> customFieldsMap =
			customFields.getEntityFieldsMap();

		customFieldsMap.forEach(
			(s, entityField) -> entityFieldsMap.put(
				"customField/" + s, entityField));

		Assert.assertEquals(entityFieldsMap.keySet(), fieldNames);
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

	private User _addUser(String columnName, Serializable columnValue)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setExpandoBridgeAttributes(
			new HashMap<String, Serializable>() {
				{
					put(columnName, columnValue);
				}
			});

		return UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			serviceContext);
	}

	@Inject
	private static ExpandoColumnLocalService _expandoColumnLocalService;

	private static ServiceTracker<ODataRetriever<User>, ODataRetriever<User>>
		_serviceTracker;

	@Inject(filter = "entity.model.name=User")
	private EntityModel _entityModel;

	@DeleteAfterTestRun
	private ExpandoTable _expandoTable;

	@Inject(filter = "segments.criteria.contributor.key=user")
	private SegmentsCriteriaContributor _segmentsCriteriaContributor;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}