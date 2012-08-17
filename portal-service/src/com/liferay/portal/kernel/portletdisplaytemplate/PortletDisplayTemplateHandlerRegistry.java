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

import java.util.List;

/**
 * @author Juan Fernández
 */
public interface PortletDisplayTemplateHandlerRegistry {

	public long[] getClassNameIds();

	/**
	 * Returns the portlet display template handler associated with the class
	 * name ID.
	 *
	 * @param  classNameId the class name ID of the portlet display template
	 * @return the portlet display template handler associated with the class
	 *         name ID
	 */
	public PortletDisplayTemplateHandler getPortletDisplayTemplateHandler(
		long classNameId);

	/**
	 * Returns the portlet display template handler associated with the class
	 * name.
	 *
	 * @param  className the class name of the portlet display template
	 * @return the portlet display template handler associated with the class
	 *         name
	 */
	public PortletDisplayTemplateHandler getPortletDisplayTemplateHandler(
		String className);

	/**
	 * Returns all the portlet display template handlers.
	 *
	 * @return the portlet display template handlers
	 */
	public List<PortletDisplayTemplateHandler>
		getPortletDisplayTemplateHandlers();

	/**
	 * Registers the portlet display template handler.
	 *
	 * @param portletDisplayTemplateHandler the portlet display template handler
	 *        to register
	 */
	public void register(
		PortletDisplayTemplateHandler portletDisplayTemplateHandler);

	/**
	 * Unregisters the portlet display template handler.
	 *
	 * @param portletDisplayTemplateHandler the portlet display template handler
	 *        to unregister
	 */
	public void unregister(
		PortletDisplayTemplateHandler portletDisplayTemplateHandler);

}