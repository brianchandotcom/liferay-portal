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

package com.liferay.segments.criteria;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.apache.commons.lang3.RandomStringUtils;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Eduardo García
 */
public class CriteriaTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAddCriterion() {
		Criteria criteria = new Criteria();

		String key = randomString();
		Criteria.Type type = Criteria.Type.CONTEXT;
		String filterString = randomString();
		Criteria.Conjunction conjunction = Criteria.Conjunction.AND;

		criteria.addCriterion(key, type, filterString, conjunction);

		Criteria.Criterion criterion = criteria.getCriterion(key);

		Assert.assertEquals(filterString, criterion.getFilterString());
		Assert.assertEquals(type.getValue(), criterion.getTypeValue());
		Assert.assertEquals(conjunction.getValue(), criterion.getConjunction());
	}

	@Test
	public void testAddCriterionMultiple() {
		Criteria criteria = new Criteria();

		String key = randomString();
		Criteria.Type type = Criteria.Type.CONTEXT;
		String filterString1 = randomString();
		Criteria.Conjunction conjunction1 = Criteria.Conjunction.AND;

		criteria.addCriterion(key, type, filterString1, conjunction1);

		String filterString2 = randomString();
		Criteria.Conjunction conjunction2 = Criteria.Conjunction.OR;

		criteria.addCriterion(key, type, filterString2, conjunction2);

		Criteria.Criterion criterion = criteria.getCriterion(key);

		Assert.assertEquals(
			conjunction1.getValue(), criterion.getConjunction());
		Assert.assertEquals(
			StringBundler.concat(
				"(", filterString1, ") ", conjunction2.getValue(), " (",
				filterString2, ")"),
			criterion.getFilterString());
	}

	@Test
	public void testAddFilter() {
		Criteria criteria = new Criteria();

		Criteria.Type type = Criteria.Type.CONTEXT;
		String filterString = randomString();
		Criteria.Conjunction conjunction = Criteria.Conjunction.AND;

		criteria.addFilter(type, filterString, conjunction);

		String typeFilterString = criteria.getFilterString(type);

		Assert.assertEquals(typeFilterString, filterString);
	}

	@Test
	public void testAddFilterMultiple() {
		Criteria criteria = new Criteria();

		Criteria.Type type = Criteria.Type.CONTEXT;
		String filterString1 = randomString();
		Criteria.Conjunction conjunction1 = Criteria.Conjunction.AND;

		criteria.addFilter(type, filterString1, conjunction1);

		String filterString2 = randomString();
		Criteria.Conjunction conjunction2 = Criteria.Conjunction.AND;

		criteria.addFilter(type, filterString2, conjunction2);

		String typeFilterString = criteria.getFilterString(type);

		Assert.assertEquals(criteria.getTypeConjunction(type), conjunction1);
		Assert.assertEquals(
			StringBundler.concat(
				"(", filterString1, ") ", conjunction2.getValue(), " (",
				filterString2, ")"),
			typeFilterString);
	}

	protected String randomString() {
		return RandomStringUtils.random(5);
	}

}