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

package com.liferay.portal.kernel.portletdisplaytemplate;

import java.util.Locale;

/**
 * @author Juan Fernández
 */
public interface PortletDisplayTemplateHandler {

	/**
	 * Returns the class name of the portlet display template.
	 *
	 * @return the class name of the portlet display template
	 */
	public String getClassName();

	/**
	 * Returns the location of the default template of the portlet display.
	 *
	 * @return the location of the default template of the portlet display. This
	 *         template will show the default information for the first portlet
	 *         display template the user creates.
	 */
	public String getDefaultTemplateLocation();

	/**
	 * Returns the name of the portlet display template.
	 *
	 * @param  locale the locale of the portlet display template name to get
	 * @return the name of the portlet display template
	 */
	public String getName(Locale locale);

	/**
	 * Returns the name of the resource the portlet display template is
	 * associated with. Permissions on the resource are checked when adding a
	 * new portlet display template.
	 *
	 * @return the name of the resource
	 */
	public String getResourceName();

}