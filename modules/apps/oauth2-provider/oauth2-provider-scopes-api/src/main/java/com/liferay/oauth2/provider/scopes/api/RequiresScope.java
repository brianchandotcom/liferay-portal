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
 * must only be executed if the incoming request is authorized for the scopes
 * given in the value of the annotation.
 *
 * @author Carlos Sierra Andrés
 * @see RequiresNoScope
 * @review
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresScope {

	/**
	 * @return whether the returned scopes in {@link RequiresScope#value()}
	 * all need to be authorized or only one of them. Defaults to
	 * <code>true</code>, which means all the specified scopes need to be
	 * authorized.
	 * @review
	 */
	boolean allNeeded() default true;

	/**
	 * @return The list of scopes that the request needs to be authorized for
	 * in order to execute this method
	 * @review
	 */
	String[] value();

}