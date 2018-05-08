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

package com.liferay.apio.architect.test.util.list;

import com.liferay.apio.architect.list.FunctionalList;
import com.liferay.apio.architect.test.util.internal.list.IsAFunctionalList;

import org.hamcrest.Matcher;

/**
 * Provides {@code Matcher} objects that can be used for testing a {@link
 * FunctionalList}.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public final class FunctionalListMatchers {

	/**
	 * Returns a matcher that checks if this is a functional list that conforms
	 * with the {@code matcher} parameter.
	 *
	 * @param  matcher the matcher used to check the contents of this functional
	 *         interface
	 * @return the matcher that checks if this is a functional list that
	 *         conforms with the {@code matcher} parameter
	 */
	public static <T> Matcher<FunctionalList<T>> aFunctionalListThat(
		Matcher<Iterable<? extends T>> matcher) {

		return new IsAFunctionalList<>(matcher);
	}

	private FunctionalListMatchers() {
		throw new UnsupportedOperationException();
	}

}