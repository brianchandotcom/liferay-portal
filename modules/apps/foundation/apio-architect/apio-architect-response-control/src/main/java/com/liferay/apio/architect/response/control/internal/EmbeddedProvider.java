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

import com.liferay.apio.architect.provider.Provider;
import com.liferay.apio.architect.response.control.Embedded;

import java.util.Arrays;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * Lets consumers use the {@code embedded} affordance in order to embed inner
 * resources in representations.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true)
public class EmbeddedProvider implements Provider<Embedded> {

	public Embedded createContext(HttpServletRequest httpServletRequest) {
		String embedded = httpServletRequest.getParameter("embedded");

		if (embedded != null) {
			return Arrays.asList(_pattern.split(embedded))::contains;
		}

		return __ -> false;
	}

	private static final Pattern _pattern = Pattern.compile("\\s*,\\s*");

}