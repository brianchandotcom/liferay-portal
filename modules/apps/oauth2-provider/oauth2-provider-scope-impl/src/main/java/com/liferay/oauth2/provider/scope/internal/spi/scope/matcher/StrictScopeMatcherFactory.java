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

package com.liferay.oauth2.provider.scope.internal.spi.scope.matcher;

import com.liferay.oauth2.provider.scope.spi.scope.matcher.ScopeMatcher;
import com.liferay.oauth2.provider.scope.spi.scope.matcher.ScopeMatcherFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tomas Polesovsky
 */
@Component(
	immediate = true, property = "type=strict",
	service = ScopeMatcherFactory.class
)
public class StrictScopeMatcherFactory implements ScopeMatcherFactory {

	@Override
	public ScopeMatcher create(String input) {
		return input::equals;
	}

}