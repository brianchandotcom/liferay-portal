/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.pagination;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * @author Jorge Ferrer
 */
public class InfoPage<T> {

	public static <T> InfoPage<T> of(List<? extends T> pageItems) {
		return new InfoPage<>(pageItems);
	}

	public static <T> InfoPage<T> of(
		List<? extends T> pageItems, Pagination pagination, int totalCount) {

		return new InfoPage<>(pageItems, pagination, totalCount);
	}

	public static <T> InfoPage<T> of(
		List<? extends T> pageItems, Pagination pagination,
		Supplier<Integer> totalCountSupplier) {

		return new InfoPage<>(pageItems, pagination, totalCountSupplier);
	}

	public List<? extends T> getPageItems() {
		return _pageItems;
	}

	public int getTotalCount() {
		return _totalCountSupplier.get();
	}

	public boolean hasNext() {
		if (_pagination.getEnd() < getTotalCount()) {
			return true;
		}

		return false;
	}

	public boolean hasPrevious() {
		if (_pagination.getStart() > 1) {
			return true;
		}

		return false;
	}

	private InfoPage(List<? extends T> pageItems) {
		this(pageItems, Pagination.of(0, pageItems.size()), pageItems::size);
	}

	private InfoPage(
		List<? extends T> pageItems, Pagination pagination, int totalCount) {

		this(pageItems, pagination, () -> totalCount);
	}

	private InfoPage(
		List<? extends T> pageItems, Pagination pagination,
		Supplier<Integer> totalCountSupplier) {

		_pageItems = pageItems;
		_pagination = pagination;
		_totalCountSupplier = _getCachedSupplier(totalCountSupplier);
	}

	private Supplier<Integer> _getCachedSupplier(Supplier<Integer> supplier) {
		AtomicReference<Integer> atomicReference = new AtomicReference<>();

		return () -> {
			if (atomicReference.get() != null) {
				return atomicReference.get();
			}

			Integer result = supplier.get();

			atomicReference.set(result);

			return result;
		};
	}

	private final List<? extends T> _pageItems;
	private final Pagination _pagination;
	private final Supplier<Integer> _totalCountSupplier;

}