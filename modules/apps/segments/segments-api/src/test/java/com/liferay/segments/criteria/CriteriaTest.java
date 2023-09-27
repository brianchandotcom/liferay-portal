/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Test
	public void testMergeCriteriaWithEqualsKey() {
		Criteria criteria1 = new Criteria();

		String key = randomString();
		String filterString1 = randomString();

		criteria1.addCriterion(
			key, Criteria.Type.CONTEXT, filterString1,
			Criteria.Conjunction.AND);

		Criteria criteria2 = new Criteria();

		String filterString2 = randomString();

		criteria2.addCriterion(
			key, Criteria.Type.CONTEXT, filterString2,
			Criteria.Conjunction.AND);

		criteria1.mergeCriteria(criteria2, Criteria.Conjunction.OR);

		Criteria.Criterion criterion = criteria1.getCriterion(key);

		Assert.assertEquals(
			String.valueOf(Criteria.Conjunction.AND),
			criterion.getConjunction());
		Assert.assertEquals(
			StringBundler.concat(
				"(", filterString1, ") ", Criteria.Conjunction.OR, " (",
				filterString2, ")"),
			criterion.getFilterString());
	}

	protected String randomString() {
		return RandomStringUtils.random(5);
	}

}