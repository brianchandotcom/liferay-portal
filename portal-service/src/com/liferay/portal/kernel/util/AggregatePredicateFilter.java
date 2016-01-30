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
		_predicateFilter = new IdentityPredicateFilter<>(predicateFilter);
	}

	public AggregatePredicateFilter<T> and(PredicateFilter<T> predicateFilter) {
		_predicateFilter = new AndPredicateFilter<>(
			_predicateFilter, new IdentityPredicateFilter<>(predicateFilter));

		return this;
	}

	@Override
	public boolean filter(T t) {
		return _predicateFilter.filter(t);
	}

	public AggregatePredicateFilter<T> negate() {
		_predicateFilter = new NegatePredicateFilter<>(_predicateFilter);

		return this;
	}

	public AggregatePredicateFilter<T> or(PredicateFilter<T> predicateFilter) {
		_predicateFilter = new OrPredicateFilter<>(
			_predicateFilter, new IdentityPredicateFilter<>(predicateFilter));

		return this;
	}

	private PredicateFilter<T> _predicateFilter;

	private static class AndPredicateFilter<T> implements PredicateFilter<T> {

		public AndPredicateFilter(
			PredicateFilter<T> leftPredicateFilter,
			PredicateFilter<T> rightPredicateFilter) {

			_leftPredicateFilter = leftPredicateFilter;
			_rightPredicateFilter = rightPredicateFilter;
		}

		@Override
		public boolean filter(T t) {
			if (_leftPredicateFilter.filter(t) &&
				_rightPredicateFilter.filter(t)) {

				return true;
			}

			return false;
		}

		private PredicateFilter<T> _leftPredicateFilter;
		private PredicateFilter<T> _rightPredicateFilter;

	}

	private static class IdentityPredicateFilter<T>
		implements PredicateFilter<T> {

		public IdentityPredicateFilter(PredicateFilter<T> predicateFilter) {
			_predicateFilter = predicateFilter;
		}

		@Override
		public boolean filter(T t) {
			return _predicateFilter.filter(t);
		}

		private final PredicateFilter<T> _predicateFilter;

	}

	private static class NegatePredicateFilter<T>
		implements PredicateFilter<T> {

		public NegatePredicateFilter(PredicateFilter<T> predicateFilter) {
			_predicateFilter = predicateFilter;
		}

		@Override
		public boolean filter(T t) {
			return !_predicateFilter.filter(t);
		}

		private PredicateFilter<T> _predicateFilter;

	}

	private static class OrPredicateFilter<T> implements PredicateFilter<T> {

		public OrPredicateFilter(
			PredicateFilter<T> leftPredicateFilter,
			PredicateFilter<T> rightPredicateFilter) {

			_leftPredicateFilter = leftPredicateFilter;
			_rightPredicateFilter = rightPredicateFilter;
		}

		@Override
		public boolean filter(T t) {
			if (_leftPredicateFilter.filter(t) ||
				_rightPredicateFilter.filter(t)) {

				return true;
			}

			return false;
		}

		private PredicateFilter<T> _leftPredicateFilter;
		private PredicateFilter<T> _rightPredicateFilter;

	}

}