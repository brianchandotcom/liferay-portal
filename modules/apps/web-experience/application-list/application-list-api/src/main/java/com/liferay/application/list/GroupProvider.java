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

package com.liferay.application.list;

import com.liferay.portal.kernel.model.Group;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides an interface which defines how the instance of {@link Group},
 * associated with servlet request, is stored and retreived.
 *
 * @author Julio Camarero
 */
public interface GroupProvider {

	/**
	 * Returns the instance of <code>Group</code> associated with the request.
	 *
	 * @param request the servlet request to retrieve associated group
	 * @return the instance of <code>Group</code> associated with the request
	 */
	public Group getGroup(HttpServletRequest request);

	/**
	 * Sets the association of the instance of <code>Group</code> and a servlet
	 * request.
	 *
	 * @param request the servlet request to associate the <code>Group</code>
	 *        instance
	 * @param group the instance of <code>Group</code> to be associated with the
	 *        servlet request
	 */
	public void setGroup(HttpServletRequest request, Group group);

}