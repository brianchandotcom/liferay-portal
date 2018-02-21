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

package com.liferay.oauth2.provider.scopes.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation can be used to declare that a method on a JAX-RS resource
 * does not need any scope to be authorized.
 *
 * <p>
 * If scope annotation checking is enabled and a method has no
 * {@link RequiresScope} or {@link RequiresNoScope} annotation, request won't be
 * authorized to execute that method as security precaution.
 * </p>
 *
 * @author Tomas Polesovsky
 * @see RequiresScope
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresNoScope {
}