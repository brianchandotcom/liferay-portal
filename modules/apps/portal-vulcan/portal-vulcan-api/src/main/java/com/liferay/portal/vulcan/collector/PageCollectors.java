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

package com.liferay.portal.vulcan.collector;

import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * {@code Collector} implementation that accumulates the input elements into a
 * new {@code Page}.
 *
 * <p>This class should never be directly instantiated. Use {@link #toPage}
 * methods instead.
 *
 * @author Alejandro Hernández
 * @review
 */
public class PageCollectors<T> implements Collector<T, List<T>, Page<T>> {

	/**
	 * Returns a {@code Collector} that accumulates the input elements into a
	 * new {@code Page}.
	 *
	 * @return a {@code Collector} which collects all the input elements into a
	 *         {@code Page}, in encounter order
	 * @review
	 */
	public static <T> Collector<T, ?, Page<T>> toPage() {
		return new PageCollectors<>(null, 0);
	}

	/**
	 * Returns a {@code Collector} that accumulates the input elements into a
	 * new {@code Page}.
	 *
	 * <p>The created
	 *
	 * @param  pagination the pagination information selected by the user
	 * @param  totalCount the total number of elements in the collection
	 * @return a {@code Collector} which collects all the input elements into a
	 *         {@code Page}, in encounter order
	 * @review
	 */
	public static <T> Collector<T, ?, Page<T>> toPage(
		Pagination pagination, int totalCount) {

		return new PageCollectors<>(pagination, totalCount);
	}

	@Override
	public BiConsumer<List<T>, T> accumulator() {
		return List::add;
	}

	@Override
	public Set<Characteristics> characteristics() {
		return Collections.emptySet();
	}

	@Override
	public BinaryOperator<List<T>> combiner() {
		return (list1, list2) -> {
			list1.addAll(list2);

			return list1;
		};
	}

	@Override
	public Function<List<T>, Page<T>> finisher() {
		return list -> {
			if (_pagination != null) {
				return Page.of(list, _pagination, _totalCount);
			}

			return Page.of(list);
		};
	}

	@Override
	public Supplier<List<T>> supplier() {
		return ArrayList::new;
	}

	private PageCollectors(Pagination pagination, int totalCount) {
		_pagination = pagination;
		_totalCount = totalCount;
	}

	private final Pagination _pagination;
	private final int _totalCount;

}