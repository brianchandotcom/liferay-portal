/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * @author Carlos Sierra Andrés
 */
public class EnumerationUtil {

	public static <T> Enumeration<T> compose(
		final Enumeration<T>... enumerations) {

		return new Enumeration<T>() {

			@Override
			public boolean hasMoreElements() {
				for (int i = _index; i < enumerations.length; i++) {
					_index = i;

					if (enumerations[_index].hasMoreElements()) {
						return true;
					}
				}

				return false;
			}

			@Override
			public T nextElement() {
				for (int i = _index; i < enumerations.length; i++) {
					_index = i;

					if (enumerations[_index].hasMoreElements()) {
						return enumerations[_index].nextElement();
					}
				}

				throw new NoSuchElementException();
			}

			private int _index = 0;

		};
	}

}