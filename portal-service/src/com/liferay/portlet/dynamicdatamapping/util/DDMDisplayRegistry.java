/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.util;

import java.util.List;

/**
 * @author Eduardo Garcia
 */
public interface DDMDisplayRegistry {

	/**
	 * Returns the ddm display associated with the portlet id.
	 *
	 * @param  portletId the portlet id
	 * @return the ddm display associated with the portlet id
	 */
	public DDMDisplay getDDMDisplay(String portletId);

	/**
	 * Returns all the ddm displays.
	 *
	 * @return the ddm displays
	 */
	public List<DDMDisplay> getDDMDisplays();

	/**
	 * Returns the portlet ids of the registered ddm displays.
	 *
	 * @return the portlet ids of the registered ddm displays
	 */
	public String[] getPortletIds();

	/**
	 * Registers the ddm display.
	 *
	 * @param ddmDisplay the ddm display to register
	 */
	public void register(DDMDisplay ddmDisplay);

	/**
	 * Unregisters the ddm display.
	 *
	 * @param ddmDisplay the ddm display to unregister
	 */
	public void unregister(DDMDisplay ddmDisplay);

}