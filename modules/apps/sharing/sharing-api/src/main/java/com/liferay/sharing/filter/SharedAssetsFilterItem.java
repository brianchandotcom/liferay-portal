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

package com.liferay.sharing.filter;

import java.util.Locale;

/**
 * Filters the shared items provided by the Shared With Me portlet.
 * Implementations of this interface must be registered as OSGi components using
 * the service {@code SharedAssetsFilterItem}. The {@code navigation.item.order}
 * property defines the order in which the filter appears in the user interface.
 *
 * @author Sergio González
 */
public interface SharedAssetsFilterItem {

	/**
	 * Returns the name of the class that filters the sharing entries.
	 *
	 * @return the class name
	 */
	public String getClassName();

	/**
	 * Returns the label displayed in the user interface.
	 *
	 * @return the label
	 */
	public String getLabel(Locale locale);

}