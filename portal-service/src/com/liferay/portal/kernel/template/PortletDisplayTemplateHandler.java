/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.template;

import java.util.Locale;

/**
 * @author Juan Fernández
 */
public interface PortletDisplayTemplateHandler {

	/**
	 * Returns the class name of the display style
	 *
	 * @return the the class name of the display style
	 */
	public String getClassName();

	/**
	 * Returns the location of the default template of the display style
	 *
	 * @return the location of the default template. This template will be shown
	 * as the default information for the user when he firstly creates a new
	 * display template
	 */
	public String getDefaultTemplateLocation();

	/**
	 * Returns the name of the display style
	 *
	 * @param  locale the locale of the display style name
	 * @return the name of the display style
	 */
	public String getName(Locale locale);

	/**
	 * Returns the name of the resource the display style is associated with.
	 * This will be used to check permissions when adding new display styles
	 *
	 * @return the name of the resource
	 */
	public String getResourceName();

}