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

import java.util.List;

/**
 * @author Juan Fernández
 */
public interface PortletDisplayTemplateHandlerRegistry {

	/**
	 * Returns the portlet display template handler associated with the class
	 * name id.
	 *
	 * @param  classNameId class name id of the PortletDisplayTemplate
	 * @return the display style handler associated with the class name
	 */
	public PortletDisplayTemplateHandler getPortletDisplayTemplateHandler(
		long classNameId);

	/**
	 * Returns the display style handler associated with the class name.
	 *
	 * @param  className class name of the Portlet Display Template
	 * @return the display style handler associated with the class name
	 */
	public PortletDisplayTemplateHandler getPortletDisplayTemplateHandler(
		String className);

	/**
	 * Returns all of the portlet display template handlers.
	 *
	 * @return the display style handlers
	 */
	public List<PortletDisplayTemplateHandler>
		getPortletDisplayTemplateHandlers();

	/**
	 * Registers the portlet display template handler.
	 *
	 * @param portletDisplayTemplateHandler the PortletDisplayTemplate to
	 * register
	 */
	public void register(
		PortletDisplayTemplateHandler portletDisplayTemplateHandler);

	/**
	 * Unregisters the portlet display template handler.
	 *
	 * @param portletDisplayTemplateHandler the display style handler to
	 * unregister
	 */
	public void unregister(
		PortletDisplayTemplateHandler portletDisplayTemplateHandler);

}