/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.scope;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Declares that a method on a JAX-RS resource may only be executed if the
 * incoming request is authorized for the scopes given in the value of the
 * annotation. When used on JAX-RS resource class, all methods without the
 * annotation inherit the resource class annotation.
 *
 * @author Carlos Sierra Andrés
 * @see    RequiresNoScope
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresScope {

	/**
	 * Returns <code>true</code> if the returned scopes in {@link #value()} must
	 * all be authorized. This defaults to <code>true</code>.
	 *
	 * @return <code>true</code> if all specified scopes must be authorized;
	 *         <code>false</code> otherwise
	 */
	boolean allNeeded() default true;

	/**
	 * Returns the list of scopes requiring authorization to execute this
	 * method.
	 *
	 * @return the scopes
	 */
	String[] value();

}