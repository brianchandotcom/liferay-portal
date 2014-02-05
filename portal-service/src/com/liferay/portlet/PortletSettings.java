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

package com.liferay.portlet;

/**
 * @author Raymond Augé
 * @author Jorge Ferrer
 */
public interface PortletSettings {

	public String getValue(String key, String def);

	public String[] getValues(String key, String[] def);

	public PortletSettings setValue(String key, String value);

	public PortletSettings setValues(String key, String[] values);

}