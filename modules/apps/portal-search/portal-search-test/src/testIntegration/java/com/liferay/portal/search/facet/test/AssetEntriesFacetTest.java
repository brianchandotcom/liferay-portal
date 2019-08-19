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

package com.liferay.portal.search.facet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.AssetEntriesFacet;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.search.test.internal.util.BaseTestFilterVisitor;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author     André de Oliveira
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
@RunWith(Arquillian.class)
public class AssetEntriesFacetTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));
	}

	@After
	public void tearDown() throws Exception {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testGetFacetFilterBooleanClause() throws Exception {
		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		AssetEntriesFacet assetEntriesFacet = new AssetEntriesFacet(
			searchContext);

		BooleanClause<Filter> booleanClause =
			assetEntriesFacet.getFacetFilterBooleanClause();

		Assert.assertTrue(
			TestFilterVisitor.find(
				booleanClause, Field.ENTRY_CLASS_NAME,
				JournalArticle.class.getName()));
	}

	@Inject
	protected PermissionCheckerFactory permissionCheckerFactory;

	private PermissionChecker _originalPermissionChecker;

	private static class TestFilterVisitor extends BaseTestFilterVisitor<Void> {

		public static boolean find(
			BooleanClause<Filter> booleanClause, String field, String value) {

			Filter filter = booleanClause.getClause();

			try {
				filter.accept(new TestFilterVisitor(field, value));
			}
			catch (FoundException fe) {
				return true;
			}

			return false;
		}

		public TestFilterVisitor(String field, String value) {
			_field = field;
			_value = value;
		}

		@Override
		public Void visit(BooleanFilter booleanFilter) {
			visit(booleanFilter.getMustBooleanClauses());
			visit(booleanFilter.getShouldBooleanClauses());

			return null;
		}

		@Override
		public Void visit(TermFilter termFilter) {
			if (_field.equals(termFilter.getField()) &&
				_value.equals(termFilter.getValue())) {

				throw new FoundException();
			}

			return null;
		}

		protected void visit(List<BooleanClause<Filter>> booleanClauses) {
			Stream<BooleanClause<Filter>> stream = booleanClauses.stream();

			stream.map(
				BooleanClause::getClause
			).forEach(
				filter -> filter.accept(this)
			);
		}

		private final String _field;
		private final String _value;

		private static class FoundException extends RuntimeException {
		}

	}

}