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
 * @author Carlos Sierra Andrés
 */
public interface PredicateFilter<T> {

	@SuppressWarnings("rawtypes")
	public static PredicateFilter ALL = new PredicateFilter<Object>() {

		@Override
		public boolean filter(Object object) {
			return true;
		}

	};

	@SuppressWarnings("rawtypes")
	public static PredicateFilter NONE = new PredicateFilter<Object>() {

		@Override
		public boolean filter(Object object) {
			return false;
		}

	};

	public boolean filter(T t);

}