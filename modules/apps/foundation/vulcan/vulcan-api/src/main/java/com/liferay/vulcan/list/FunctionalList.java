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

package com.liferay.vulcan.list;

import aQute.bnd.annotation.ProviderType;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Instances of this class behave like a list in pure functional languages.
 * Instead of splitting the list manually, you can use the provided methods to
 * take different elements from the list, like {@code #head()}, {@code #tail()},
 * etc.
 *
 * Instances of {@code FunctionalList} should always have at least one
 * element.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 * @review
 */
@ProviderType
public interface FunctionalList<T> {

	/**
	 * Returns the first element of the list.
	 *
	 * @return the first element.
	 * @review
	 */
	public T head();

	/**
	 * Returns all elements of the list except the last one in a Java stream.
	 *
	 * @return a stream with all elements except the last one.
	 * @review
	 */
	public Stream<T> initStream();

	/**
	 * Returns the last element of the list if there is one (tail need to have
	 * at least one element). Otherwise returns {@code Optional.empty()}
	 *
	 * @return the last element if there is one, otherwise {@code
	 *         Optional.empty()}
	 * @review
	 */
	public Optional<T> lastOptional();

	/**
	 * Returns all elements of the list except the first and last in a Java
	 * stream.
	 *
	 * @return a stream with all elements except the first and last.
	 * @review
	 */
	public Stream<T> middleStream();

	/**
	 * Returns all elements of the list except the first one in a Java stream.
	 *
	 * @return a stream with all elements except the first one.
	 * @review
	 */
	public Stream<T> tailStream();

}