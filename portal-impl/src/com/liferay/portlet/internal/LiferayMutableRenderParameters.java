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

package com.liferay.portlet.internal;

import javax.portlet.MutableRenderParameters;

/**
 * @author Neil Griffin
 */
public interface LiferayMutableRenderParameters
	extends MutableRenderParameters, LiferayMutablePortletParameters {

	/**
	 * Returns a flag indicating whether or not the value of the parameter
	 * associated with the specified name has changed.
	 *
	 * @param name the name of the parameter.
	 *
	 * @return A value of <code>true</code> indicates that the value of the
	 *         parameter associated with the specified name has changed,
	 *         otherwise <code>false</code> indicates that the value has not
	 *         changed.
	 */
	public boolean isMutated(String name);

}