/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.sample.hello;

/**
 * Provided methods to build a "hello" greeting.
 *
 * @author Andrea Di Giorgi
 */
public interface HelloBuilder {

	/**
	 * Returns the "hello" greeting.
	 *
	 * @return the "hello" greeting.
	 */
	public String getHello();

	/**
	 * Returns the name used to build a greeting.
	 *
	 * @return the name used to build a greeting.
	 */
	public String getName();

	/**
	 * Sets the name to use when building a greeting.
	 *
	 * @param name the name to use when building a greeting.
	 */
	public void setName(String name);

}