/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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