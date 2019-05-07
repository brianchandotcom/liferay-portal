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
 * Provides an interface that defines how a <code>Group</code> (in
 * <code>portal-kernel</code>) associated with a servlet request is stored and
 * retrieved.
 *
 * @author Julio Camarero
 */
public interface GroupProvider {

	/**
	 * Returns the <code>Group</code> associated with the request.
	 *
	 * @param  httpServletRequest the servlet request used to retrieve the group
	 * @return the <code>Group</code> associated with the request
	 */
	public Group getGroup(HttpServletRequest httpServletRequest);

	/**
	 * Sets the <code>Group</code> to associate with the request.
	 *
	 * @param httpServletRequest the servlet request used to associate the
	 *        <code>Group</code>
	 * @param group the <code>Group</code> to associate with the request
	 */
	public void setGroup(HttpServletRequest httpServletRequest, Group group);

}