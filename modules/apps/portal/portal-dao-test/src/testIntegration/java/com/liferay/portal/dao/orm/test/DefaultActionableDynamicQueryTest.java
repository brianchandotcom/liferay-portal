/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class DefaultActionableDynamicQueryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testOrderByDescending() throws Exception {
		List<ClassName> expectedClassNames = new ArrayList<>(
			_classNameLocalService.getClassNames(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS));

		Assert.assertTrue(
			expectedClassNames.toString(), expectedClassNames.size() > 1);

		expectedClassNames.sort(_descClassNameIdOrderByComparator);

		List<ClassName> actualClassNames = new ArrayList<>(
			expectedClassNames.size());

		ActionableDynamicQuery actionableDynamicQuery =
			_classNameLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				ClassName className = expectedClassNames.get(0);

				Property property = PropertyFactoryUtil.forName("classNameId");

				dynamicQuery.add(property.le(className.getClassNameId()));
			});
		actionableDynamicQuery.setAddOrderCriteriaMethod(
			dynamicQuery -> OrderFactoryUtil.addOrderByComparator(
				dynamicQuery, _descClassNameIdOrderByComparator));
		actionableDynamicQuery.setInterval(1);
		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<ClassName>)
				actualClassNames::add);

		actionableDynamicQuery.performActions();

		Assert.assertEquals(expectedClassNames, actualClassNames);

		List<ClassName> sortedClassNames = new ArrayList<>(actualClassNames);

		sortedClassNames.sort(_descClassNameIdOrderByComparator);

		Assert.assertEquals(sortedClassNames, actualClassNames);
	}

	private static final OrderByComparator<ClassName>
		_descClassNameIdOrderByComparator = new OrderByComparator<ClassName>() {

			@Override
			public int compare(ClassName className1, ClassName className2) {
				return Long.compare(
					className2.getClassNameId(), className1.getClassNameId());
			}

			@Override
			public String[] getOrderByFields() {
				return new String[] {"classNameId"};
			}

			@Override
			public boolean isAscending() {
				return false;
			}

		};

	@Inject
	private ClassNameLocalService _classNameLocalService;

}