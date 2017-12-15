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

package com.liferay.apio.architect.response.control.internal;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.provider.Provider;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * Lets resources provide {@link Pagination} as a parameter in the methods of
 * the different routes builders.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true)
public class PaginationProvider implements Provider<Pagination> {

	@Override
	public Pagination createContext(HttpServletRequest httpServletRequest) {
		int itemsPerPage = _getAsInt(
			httpServletRequest.getParameter("per_page"),
			_ITEMS_PER_PAGE_DEFAULT);

		int pageNumber = _getAsInt(
			httpServletRequest.getParameter("page"), _PAGE_NUMBER_DEFAULT);

		return new Pagination(itemsPerPage, pageNumber);
	}

	private int _getAsInt(String parameterValue, int defaultValue) {
		Try<String> stringTry = Try.success(parameterValue);

		return stringTry.map(
			Integer::parseInt
		).filter(
			integer -> integer > 0
		).orElse(
			defaultValue
		);
	}

	private static final int _ITEMS_PER_PAGE_DEFAULT = 30;

	private static final int _PAGE_NUMBER_DEFAULT = 1;

}