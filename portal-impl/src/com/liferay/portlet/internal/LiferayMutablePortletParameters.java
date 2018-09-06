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

import javax.portlet.MutablePortletParameters;

/**
 * @author Neil Griffin
 */
public interface LiferayMutablePortletParameters
	extends MutablePortletParameters {

	/**
	 * Returns <code>true</code> if the state of the portlet parameters has
	 * changed.
	 *
	 * @return <code>true</code> if the state has changed; <code>false</code>
	 *         otherwise
	 */
	public boolean isMutated();

	/**
	 * Sets the parameter value. An <code>IllegalArgumentException</code> is
	 * thrown if the name is <code>null</code>.
	 *
	 * @param  name the parameter's name
	 * @param  value the parameter's value. If <code>null</code>, the parameter
	 *         is removed.
	 * @param  append whether the new value is appended to any existing values
	 *         for the parameter. If this is <code>false</code>, any existing
	 *         values are overwritten with the new value.
	 * @return the parameter value prior to setting
	 */
	public String setValue(String name, String value, boolean append);

	/**
	 * Sets the parameter values. An <code>IllegalArgumentException</code> is
	 * thrown if the name is <code>null</code>.
	 *
	 * @param  name the parameter's name
	 * @param  values the parameter's values. If <code>null</code>, the
	 *         parameter is removed.
	 * @param  append whether the new values are appended to any existing values
	 *         for the parameter. If this is <code>false</code>, any existing
	 *         values are overwritten with the new values.
	 * @return the parameter values prior to setting
	 */
	public String[] setValues(String name, String[] values, boolean append);

}