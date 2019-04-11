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

package com.liferay.frontend.taglib.clay.servlet.taglib.data;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public interface ClayTagDataSourceAjaxInvoker {

	/**
	 * Invoke a {@link ClayTagDataSource} component and return the response as
	 * an opaque string suitable for sending to the Javascript counterpart of a
	 * table component.
	 *
	 * @param request
	 * @param dataSourceKey {@link ClayTagDataSource} identifier
	 * @param page page number
	 * @param pageSize page size
	 * @return
	 * @throws IllegalArgumentException if the given dataSourceKey is unknown
	 */
	public String getItems(
			HttpServletRequest request, String dataSourceKey, int page,
			int pageSize)
		throws IllegalArgumentException;

}