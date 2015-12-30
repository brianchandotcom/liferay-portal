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

package com.liferay.portal.kernel.util;

/**
 * @author Marcellus Tavares
 */
public class AggregatePredicateFilter<T> implements PredicateFilter<T> {

	public AggregatePredicateFilter(PredicateFilter<T> predicateFilter) {
		_predicateFilter = new IdentityPredicateFilter(predicateFilter);
	}

	public AggregatePredicateFilter<T> and(PredicateFilter<T> predicateFilter) {
		_predicateFilter = new AndPredicateFilter(
			_predicateFilter, new IdentityPredicateFilter(predicateFilter));

		return this;
	}

	@Override
	public boolean filter(T t) {
		return _predicateFilter.filter(t);
	}

	public AggregatePredicateFilter<T> negate() {
		_predicateFilter = new NegatePredicateFilter(_predicateFilter);

		return this;
	}

	public AggregatePredicateFilter<T> or(PredicateFilter<T> predicateFilter) {
		_predicateFilter = new OrPredicateFilter(
			_predicateFilter, new IdentityPredicateFilter(predicateFilter));

		return this;
	}

	private PredicateFilter<T> _predicateFilter;

	private class AndPredicateFilter implements PredicateFilter<T> {

		public AndPredicateFilter(
			PredicateFilter<T> left, PredicateFilter<T> right) {

			_left = left;
			_right = right;
		}

		@Override
		public boolean filter(T t) {
			return _left.filter(t) && _right.filter(t);
		}

		private PredicateFilter<T> _left;
		private PredicateFilter<T> _right;

	}

	private class IdentityPredicateFilter implements PredicateFilter<T> {

		public IdentityPredicateFilter(PredicateFilter<T> predicateFilter) {
			_predicateFilter = predicateFilter;
		}

		@Override
		public boolean filter(T t) {
			return _predicateFilter.filter(t);
		}

		private final PredicateFilter<T> _predicateFilter;

	}

	private class NegatePredicateFilter implements PredicateFilter<T> {

		public NegatePredicateFilter(PredicateFilter<T> predicateFilter) {
			_predicateFilter = predicateFilter;
		}

		@Override
		public boolean filter(T t) {
			return !_predicateFilter.filter(t);
		}

		private PredicateFilter<T> _predicateFilter;

	}

	private class OrPredicateFilter implements PredicateFilter<T> {

		public OrPredicateFilter(
			PredicateFilter<T> left, PredicateFilter<T> right) {

			_left = left;
			_right = right;
		}

		@Override
		public boolean filter(T t) {
			return _left.filter(t) || _right.filter(t);
		}

		private PredicateFilter<T> _left;
		private PredicateFilter<T> _right;

	}

}