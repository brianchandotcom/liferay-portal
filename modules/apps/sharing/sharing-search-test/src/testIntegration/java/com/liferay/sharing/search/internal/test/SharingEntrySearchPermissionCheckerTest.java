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

package com.liferay.sharing.search.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchPermissionChecker;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.DateRangeTermFilter;
import com.liferay.portal.kernel.search.filter.ExistsFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.FilterVisitor;
import com.liferay.portal.kernel.search.filter.GeoBoundingBoxFilter;
import com.liferay.portal.kernel.search.filter.GeoDistanceFilter;
import com.liferay.portal.kernel.search.filter.GeoDistanceRangeFilter;
import com.liferay.portal.kernel.search.filter.GeoPolygonFilter;
import com.liferay.portal.kernel.search.filter.MissingFilter;
import com.liferay.portal.kernel.search.filter.PrefixFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.RangeTermFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class SharingEntrySearchPermissionCheckerTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testGuestPermissionFilter() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		assertFieldValue(
			new long[] {_group.getGroupId()}, "sharedToUserId",
			String.valueOf(_user.getUserId()), true);
	}

	protected void assertFieldValue(
			long[] groupIds, String field, String value, boolean expected)
		throws Exception {

		BooleanFilter booleanFilter = getBooleanFilter(groupIds);

		TestFilterVisitor testFilterVisitor = new TestFilterVisitor(
			expected, field, value);

		booleanFilter.accept(testFilterVisitor);

		testFilterVisitor.assertField();
	}

	protected BooleanFilter getBooleanFilter(long[] groupIds) throws Exception {
		return _searchPermissionChecker.getPermissionBooleanFilter(
			TestPropsValues.getCompanyId(), groupIds, _user.getUserId(),
			DLFileEntry.class.getName(), new BooleanFilter(),
			new SearchContext());
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SearchPermissionChecker _searchPermissionChecker;

	@DeleteAfterTestRun
	private User _user;

	private static class TestFilterVisitor implements FilterVisitor<Void> {

		public TestFilterVisitor(boolean expected, String field, String value) {
			_expected = expected;
			_field = field;
			_value = value;
		}

		public void assertField() {
			Assert.assertEquals(_expected, _found);
		}

		@Override
		public Void visit(BooleanFilter booleanFilter) {
			for (BooleanClause<Filter> booleanClause :
					booleanFilter.getMustBooleanClauses()) {

				Filter filter = booleanClause.getClause();

				filter.accept(this);
			}

			for (BooleanClause<Filter> booleanClause :
					booleanFilter.getShouldBooleanClauses()) {

				Filter filter = booleanClause.getClause();

				filter.accept(this);
			}

			return null;
		}

		@Override
		public Void visit(DateRangeTermFilter dateRangeTermFilter) {
			return null;
		}

		@Override
		public Void visit(ExistsFilter existsFilter) {
			return null;
		}

		@Override
		public Void visit(GeoBoundingBoxFilter geoBoundingBoxFilter) {
			return null;
		}

		@Override
		public Void visit(GeoDistanceFilter geoDistanceFilter) {
			return null;
		}

		@Override
		public Void visit(GeoDistanceRangeFilter geoDistanceRangeFilter) {
			return null;
		}

		@Override
		public Void visit(GeoPolygonFilter geoPolygonFilter) {
			return null;
		}

		@Override
		public Void visit(MissingFilter missingFilter) {
			return null;
		}

		@Override
		public Void visit(PrefixFilter prefixFilter) {
			return null;
		}

		@Override
		public Void visit(QueryFilter queryFilter) {
			return null;
		}

		@Override
		public Void visit(RangeTermFilter rangeTermFilter) {
			return null;
		}

		@Override
		public Void visit(TermFilter termFilter) {
			return null;
		}

		@Override
		public Void visit(TermsFilter termsFilter) {
			if (_field.equals(termsFilter.getField()) &&
				ArrayUtil.contains(termsFilter.getValues(), _value)) {

				_found = true;
			}

			return null;
		}

		private final boolean _expected;
		private final String _field;
		private boolean _found;
		private final String _value;

	}

}