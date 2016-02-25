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

import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * @author Shuyang Zhou
 */
public class MappingEnumeration<T, R> implements Enumeration<R> {

	public MappingEnumeration(Enumeration<T> enumeration, Mapper<T, R> mapper) {
		this.enumeration = enumeration;
		this.mapper = mapper;

		nextElement = nextMappedElement();
	}

	@Override
	public boolean hasMoreElements() {
		if (nextElement != null) {
			return true;
		}

		return false;
	}

	@Override
	public R nextElement() {
		if (nextElement == null) {
			throw new NoSuchElementException();
		}

		R nextElement = this.nextElement;

		this.nextElement = nextMappedElement();

		return nextElement;
	}

	public interface Mapper<T, R> {

		public R map(T t);

	}

	protected final R nextMappedElement() {
		while (enumeration.hasMoreElements()) {
			R element = mapper.map(enumeration.nextElement());

			if (element != null) {
				return element;
			}
		}

		return null;
	}

	protected final Enumeration<T> enumeration;
	protected final Mapper<T, R> mapper;
	protected R nextElement;

}