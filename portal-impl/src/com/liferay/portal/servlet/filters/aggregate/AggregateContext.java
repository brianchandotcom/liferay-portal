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

package com.liferay.portal.servlet.filters.aggregate;

/**
 * @author Raymond Augé
 * @author Eduardo Lundgren
 */
public interface AggregateContext {

	public String getContent(String path);

	public String getFullPath(String path);

	public String getResourcePath(String path);

	public String popPath();

	public void pushPath(String path);

	public String shiftPath();

	public void unshiftPath(String path);

}