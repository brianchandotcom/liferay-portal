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

package com.liferay.segments.context.contributor;

import com.liferay.segments.context.Context;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides an interface for extending the {@link Context} with information from
 * the request.
 *
 * @author Eduardo García
 * @review
 */
public interface RequestContextContributor {

	/**
	 * Contributes additional information to the context.
	 *
	 * @param  context the context
	 * @param  httpServletRequest the current request
	 * @review
	 */
	public void contribute(
		Context context, HttpServletRequest httpServletRequest);

}