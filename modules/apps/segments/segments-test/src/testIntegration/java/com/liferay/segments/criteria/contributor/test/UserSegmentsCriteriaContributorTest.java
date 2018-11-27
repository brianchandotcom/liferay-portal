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
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.criteria.Field;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
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
		new LiferayIntegrationTestRule();

	@Test
	public void testGetFields() {
		List<Field> fields = _segmentsCriteriaContributor.getFields(
			LocaleUtil.getDefault());

		Stream<Field> stream = fields.stream();

		Set<String> fieldNames = stream.map(
			field -> field.getName()
		).collect(
			Collectors.toSet()
		);

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		entityFieldsMap.remove("customField");

		Assert.assertTrue(fieldNames.containsAll(entityFieldsMap.keySet()));
	}

	@Inject(filter = "entity.model.name=User")
	private EntityModel _entityModel;

	@Inject(filter = "segments.criteria.contributor.key=user")
	private SegmentsCriteriaContributor _segmentsCriteriaContributor;

}