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

package com.liferay.user.associated.data.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.display.BaseModelUADDisplay;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class BaseModelUADDisplayTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addGroupAdminUser(_group);

		_uadDisplay = new TestLayoutUADDisplay();

		_uadDisplay.setSearchableFields("name", "title", "description");
	}

	@Test
	public void testOrder() throws Exception {
		String first = "alpha";
		String second = "beta";
		String third = "gamma";

		Layout layout1 = _addLayout(first, second, third);
		Layout layout2 = _addLayout(second, third, first);
		Layout layout3 = _addLayout(third, first, second);

		_assertOrder("name", layout1, layout2, layout3);
		_assertOrder("title", layout3, layout1, layout2);
		_assertOrder("description", layout2, layout3, layout1);

		_uadDisplay.setOrderByComparatorBiFunction(
			(orderByCol, orderByType) -> OrderByComparatorFactoryUtil.create(
				"Layout", orderByCol, !Objects.equals(orderByType, "desc")));

		_assertOrder("name", layout1, layout2, layout3);
		_assertOrder("title", layout3, layout1, layout2);
		_assertOrder("description", layout2, layout3, layout1);
	}

	@Test
	public void testSearchByGroupId() throws Exception {
		long userId = _user.getUserId();
		long groupId = _group.getGroupId();

		Group group2 = GroupTestUtil.addGroup();

		long groupId2 = group2.getGroupId();

		_groups.add(group2);

		Layout excludedGroup1Layout1 = _addLayout(
			TestPropsValues.getUserId(), groupId);
		Layout excludedGroup2Layout2 = _addLayout(
			TestPropsValues.getUserId(), group2.getGroupId());
		Layout group1Layout1 = _addLayout(userId, groupId);
		Layout group1Layout2 = _addLayout(userId, groupId);
		Layout group2Layout1 = _addLayout(userId, groupId2);
		Layout group2Layout2 = _addLayout(userId, groupId2);

		Assert.assertEquals(2, _searchCountGroupLayouts(groupId));

		List<Layout> group1Layouts = _searchGroupLayouts(groupId);

		Assert.assertFalse(group1Layouts.contains(excludedGroup1Layout1));
		Assert.assertFalse(group1Layouts.contains(excludedGroup2Layout2));
		Assert.assertTrue(group1Layouts.contains(group1Layout1));
		Assert.assertTrue(group1Layouts.contains(group1Layout2));
		Assert.assertFalse(group1Layouts.contains(group2Layout1));
		Assert.assertFalse(group1Layouts.contains(group2Layout2));

		Assert.assertEquals(2, _searchCountGroupLayouts(groupId2));

		List<Layout> group2Layouts = _searchGroupLayouts(groupId2);

		Assert.assertFalse(group2Layouts.contains(excludedGroup1Layout1));
		Assert.assertFalse(group2Layouts.contains(excludedGroup2Layout2));
		Assert.assertFalse(group2Layouts.contains(group1Layout1));
		Assert.assertFalse(group2Layouts.contains(group1Layout2));
		Assert.assertTrue(group2Layouts.contains(group2Layout1));
		Assert.assertTrue(group2Layouts.contains(group2Layout2));

		Assert.assertEquals(4, _searchCountGroupLayouts(groupId, groupId2));

		List<Layout> group1And2Layouts = _searchGroupLayouts(groupId, groupId2);

		Assert.assertFalse(group1And2Layouts.contains(excludedGroup1Layout1));
		Assert.assertFalse(group1And2Layouts.contains(excludedGroup2Layout2));
		Assert.assertTrue(group1And2Layouts.contains(group1Layout1));
		Assert.assertTrue(group1And2Layouts.contains(group1Layout2));
		Assert.assertTrue(group1And2Layouts.contains(group2Layout1));
		Assert.assertTrue(group1And2Layouts.contains(group2Layout2));

		Assert.assertEquals(4, _searchCountGroupLayouts());

		List<Layout> allUserLayouts = _searchGroupLayouts();

		Assert.assertFalse(allUserLayouts.contains(excludedGroup1Layout1));
		Assert.assertFalse(allUserLayouts.contains(excludedGroup2Layout2));
		Assert.assertTrue(allUserLayouts.contains(group1Layout1));
		Assert.assertTrue(allUserLayouts.contains(group1Layout2));
		Assert.assertTrue(allUserLayouts.contains(group2Layout1));
		Assert.assertTrue(allUserLayouts.contains(group2Layout2));
	}

	@Test
	public void testSearchByKeywords() throws Exception {
		String searchTerm = RandomTestUtil.randomString(30);
		String searchTerm2 = RandomTestUtil.randomString(30);

		Layout layout1 = _addLayout(
			searchTerm, StringPool.BLANK, StringPool.BLANK);
		Layout layout2 = _addLayout(
			StringPool.BLANK, searchTerm, StringPool.BLANK);
		Layout layout3 = _addLayout(
			StringPool.BLANK, StringPool.BLANK, searchTerm);
		Layout layout4 = _addLayout(
			searchTerm2, StringPool.BLANK, StringPool.BLANK);
		Layout layout5 = _addLayout(
			StringPool.BLANK, searchTerm2, StringPool.BLANK);
		Layout layout6 = _addLayout(
			StringPool.BLANK, StringPool.BLANK, searchTerm2);

		List<Layout> layouts = _searchLayouts(searchTerm);

		Assert.assertTrue(layouts.contains(layout1));
		Assert.assertTrue(layouts.contains(layout2));
		Assert.assertTrue(layouts.contains(layout3));
		Assert.assertFalse(layouts.contains(layout4));
		Assert.assertFalse(layouts.contains(layout5));
		Assert.assertFalse(layouts.contains(layout6));

		Assert.assertEquals(3, _searchCountLayouts(searchTerm));

		layouts = _searchLayouts(searchTerm2);

		Assert.assertFalse(layouts.contains(layout1));
		Assert.assertFalse(layouts.contains(layout2));
		Assert.assertFalse(layouts.contains(layout3));
		Assert.assertTrue(layouts.contains(layout4));
		Assert.assertTrue(layouts.contains(layout5));
		Assert.assertTrue(layouts.contains(layout6));

		Assert.assertEquals(3, _searchCountLayouts(searchTerm2));

		layouts = _searchLayouts(null);

		Assert.assertTrue(layouts.contains(layout1));
		Assert.assertTrue(layouts.contains(layout2));
		Assert.assertTrue(layouts.contains(layout3));
		Assert.assertTrue(layouts.contains(layout4));
		Assert.assertTrue(layouts.contains(layout5));
		Assert.assertTrue(layouts.contains(layout6));

		Assert.assertEquals(6, _searchCountLayouts(null));

		_uadDisplay.setSearchableFields("name", "title");

		layouts = _searchLayouts(searchTerm);

		Assert.assertTrue(layouts.contains(layout1));
		Assert.assertTrue(layouts.contains(layout2));
		Assert.assertFalse(layouts.contains(layout3));

		Assert.assertEquals(2, _searchCountLayouts(searchTerm));

		_uadDisplay.setSearchableFields("name");

		layouts = _searchLayouts(searchTerm);

		Assert.assertTrue(layouts.contains(layout1));
		Assert.assertFalse(layouts.contains(layout2));
		Assert.assertFalse(layouts.contains(layout3));

		Assert.assertEquals(1, _searchCountLayouts(searchTerm));

		_uadDisplay.setSearchableFields();

		layouts = _searchLayouts(searchTerm);

		Assert.assertTrue(layouts.contains(layout1));
		Assert.assertTrue(layouts.contains(layout2));
		Assert.assertTrue(layouts.contains(layout3));
		Assert.assertTrue(layouts.contains(layout4));
		Assert.assertTrue(layouts.contains(layout5));
		Assert.assertTrue(layouts.contains(layout6));

		Assert.assertEquals(6, _searchCountLayouts(searchTerm));

		_uadDisplay.setSearchableFields("privateLayout");

		// Checks to make sure the DyanmicQuery skips checking boolean fields

		_searchLayouts(searchTerm);
	}

	/**
	 * Ensures that the DynamicQuery field search will skip boolean fields,
	 * otherwise the search will throw an exception
	 *
	 * @throws Exception
	 */
	@Test
	public void testSkipSearchBooleanField() throws Exception {
		_addLayout("foo", "foo", "foo");

		_uadDisplay.setSearchableFields("privateLayout");

		_searchLayouts("foo");
	}

	private Layout _addLayout(long userId, long groupId) throws Exception {
		return _addLayout(userId, groupId, null, null, null);
	}

	private Layout _addLayout(
			long userId, long groupId, String name, String title,
			String description)
		throws Exception {

		if (Validator.isNull(name)) {
			name = RandomTestUtil.randomString(30);
		}

		if (Validator.isNull(title)) {
			title = RandomTestUtil.randomString(30);
		}

		if (Validator.isNull(description)) {
			description = RandomTestUtil.randomString(30);
		}

		Layout layout = _layoutLocalService.addLayout(
			userId, groupId, false, 0, name.concat("name"),
			title.concat("title"), description.concat("description"),
			LayoutConstants.TYPE_PORTLET, false, StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(groupId));

		_layouts.add(layout);

		return layout;
	}

	private Layout _addLayout(String name, String title, String description)
		throws Exception {

		return _addLayout(
			_user.getUserId(), _group.getGroupId(), name, title, description);
	}

	private void _assertOrder(String orderByCol, Layout... layouts) {
		_assertOrder(orderByCol, "asc", layouts);
		_assertOrder(orderByCol, null, layouts);

		Layout[] reversedLayouts = ArrayUtil.clone(layouts);

		ArrayUtil.reverse(reversedLayouts);

		_assertOrder(orderByCol, "desc", reversedLayouts);
	}

	private void _assertOrder(
		String orderByCol, String orderByType, Layout[] expected) {

		List<Layout> actual = _uadDisplay.search(
			_user.getUserId(), null, null, orderByCol, orderByType,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(actual.toString(), expected.length, actual.size());

		for (int i = 0; i < expected.length; i++) {
			Assert.assertEquals(expected[i], actual.get(i));
		}
	}

	private long _searchCountGroupLayouts(long... groupIds) {
		return _uadDisplay.searchCount(_user.getUserId(), groupIds, null);
	}

	private long _searchCountLayouts(String keywords) {
		return _uadDisplay.searchCount(
			_user.getUserId(), new long[] {_group.getGroupId()}, keywords);
	}

	private List<Layout> _searchGroupLayouts(long... groupIds) {
		return _uadDisplay.search(
			_user.getUserId(), groupIds, null, null, null, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	private List<Layout> _searchLayouts(String keywords) {
		return _uadDisplay.search(
			_user.getUserId(), new long[] {_group.getGroupId()}, keywords, null,
			null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private final List<Group> _groups = new ArrayList<>();

	@Inject
	private LayoutLocalService _layoutLocalService;

	@DeleteAfterTestRun
	private final List<Layout> _layouts = new ArrayList<>();

	private TestLayoutUADDisplay _uadDisplay;

	@DeleteAfterTestRun
	private User _user;

	private class TestLayoutUADDisplay extends BaseModelUADDisplay<Layout> {

		@Override
		public Layout get(Serializable primaryKey) throws Exception {
			return null;
		}

		@Override
		public String[] getDisplayFieldNames() {
			return new String[0];
		}

		@Override
		public Class<Layout> getTypeClass() {
			return Layout.class;
		}

		public void setOrderByComparatorBiFunction(
			BiFunction<String, String, OrderByComparator<Layout>>
				orderByComparatorBiFunction) {

			_orderByComparatorBiFunction = orderByComparatorBiFunction;
		}

		@Override
		protected long doCount(DynamicQuery dynamicQuery) {
			return _layoutLocalService.dynamicQueryCount(dynamicQuery);
		}

		@Override
		protected DynamicQuery doGetDynamicQuery() {
			return _layoutLocalService.dynamicQuery();
		}

		@Override
		protected List<Layout> doGetRange(
			DynamicQuery dynamicQuery, int start, int end) {

			return _layoutLocalService.dynamicQuery(dynamicQuery, start, end);
		}

		@Override
		protected String[] doGetUserIdFieldNames() {
			return new String[] {"userId"};
		}

		@Override
		protected OrderByComparator<Layout> getOrderByComparator(
			String orderByField, String orderByType) {

			if (_orderByComparatorBiFunction != null) {
				return _orderByComparatorBiFunction.apply(
					orderByField, orderByType);
			}

			return super.getOrderByComparator(orderByField, orderByType);
		}

		@Override
		protected String[] getSearchableFields() {
			return _searchableFields;
		}

		protected void setSearchableFields(String... searchableFields) {
			_searchableFields = searchableFields;
		}

		private BiFunction<String, String, OrderByComparator<Layout>>
			_orderByComparatorBiFunction;
		private String[] _searchableFields;

	}

}