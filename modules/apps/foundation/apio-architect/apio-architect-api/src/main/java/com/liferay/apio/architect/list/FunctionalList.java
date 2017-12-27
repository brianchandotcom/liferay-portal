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

package com.liferay.apio.architect.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Defines an interface whose implementing classes behave like a list in pure
 * functional languages. Instead of splitting the list manually, you can use the
 * methods this interface defines to take different elements from the list.
 * Instances of {@code FunctionalList} should always have at least one element.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés§
 * @author Jorge Ferrer
 * @param  <E> the type of elements in this list
 */
public class FunctionalList<E> {

	public FunctionalList(FunctionalList<E> functionalList, E last) {
		if (functionalList == null) {
			_first = last;
			_tail = Collections.emptyList();
		}
		else {
			_first = functionalList.head();

			Stream<E> stream = functionalList.tailStream();

			List<E> tail = stream.collect(Collectors.toList());

			tail.add(last);

			_tail = tail;
		}
	}

	/**
	 * Returns the first element of the list.
	 *
	 * @return the first element of the list
	 */
	public E head() {
		return _first;
	}

	/**
	 * Returns all but the last element of the list.
	 *
	 * @return a {@code Stream} that contains all but the last element of the
	 *         list
	 */
	public Stream<E> initStream() {
		if (_init == null) {
			List<E> init = new ArrayList<>();

			init.add(_first);

			Stream<E> stream = middleStream();

			init.addAll(stream.collect(Collectors.toList()));

			_init = init;
		}

		return _init.stream();
	}

	/**
	 * Returns the last element of the list, if it exists.
	 *
	 * @return the last element of the list; {@code Optional.empty()} otherwise
	 */
	public Optional<E> lastOptional() {
		if (_last == null) {
			if (_tail.size() == 0) {
				return Optional.empty();
			}

			_last = _tail.get(_tail.size() - 1);
		}

		return Optional.of(_last);
	}

	/**
	 * Returns all elements of the list except the first and last.
	 *
	 * @return a {@code Stream} that contains all list elements except the first
	 *         and last
	 */
	public Stream<E> middleStream() {
		if (_middle == null) {
			if (_tail.size() == 0) {
				_middle = Collections.emptyList();
			}
			else {
				_middle = _tail.subList(0, _tail.size() - 1);
			}
		}

		return _middle.stream();
	}

	/**
	 * Returns all elements of the list except the first.
	 *
	 * @return a {@code Stream} that contains all list elements except the first
	 */
	public Stream<E> tailStream() {
		return _tail.stream();
	}

	private final E _first;
	private List<E> _init;
	private E _last;
	private List<E> _middle;
	private final List<E> _tail;

}