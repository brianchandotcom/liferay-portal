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

package com.liferay.apio.architect.documentation;

import com.liferay.apio.architect.alias.RequestFunction;

import java.util.Optional;

/**
 * Represents the API's auto-documentation.
 *
 * @author Alejandro Hernández
 */
public class Documentation {

	public Documentation(
		RequestFunction<Optional<APITitle>> apiTitleRequestFunction,
		RequestFunction<Optional<APIDescription>>
			apiDescriptionRequestFunction) {

		_apiTitleRequestFunction = apiTitleRequestFunction;
		_apiDescriptionRequestFunction = apiDescriptionRequestFunction;
	}

	/**
	 * Returns the function that calculates the API's description, if present.
	 * Returns {@code Optional#empty()} otherwise.
	 *
	 * @return the API's description, if present; {@code Optional#empty()}
	 *         otherwise
	 */
	public RequestFunction<Optional<String>>
		getAPIDescriptionRequestFunction() {

		return httpServletRequest -> _apiDescriptionRequestFunction.apply(
			httpServletRequest
		).map(
			APIDescription::get
		);
	}

	/**
	 * Returns the function that calculates the API's description, if present.
	 * Returns {@code Optional#empty()} otherwise.
	 *
	 * @return the API's title, if present; {@code Optional#empty()} otherwise
	 */
	public RequestFunction<Optional<String>> getAPITitleRequestFunction() {
		return httpServletRequest -> _apiTitleRequestFunction.apply(
			httpServletRequest
		).map(
			APITitle::get
		);
	}

	private final RequestFunction<Optional<APIDescription>>
		_apiDescriptionRequestFunction;
	private final RequestFunction<Optional<APITitle>> _apiTitleRequestFunction;

}