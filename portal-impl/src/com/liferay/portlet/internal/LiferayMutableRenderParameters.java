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
	extends LiferayMutablePortletParameters, MutableRenderParameters {

	/**
	 * Returns <code>true</code> if the value of the parameter associated with
	 * the specified name has changed.
	 *
	 * @param  name the parameter's name
	 * @return <code>true</code> if the value of the parameter associated with
	 *         the specified name has changed; <code>false</code> otherwise
	 */
	public boolean isMutated(String name);

}