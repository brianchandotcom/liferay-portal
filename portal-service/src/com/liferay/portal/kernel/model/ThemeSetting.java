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

package com.liferay.portal.kernel.model;

/**
 * @author Julio Camarero
 */
public interface ThemeSetting {

	public String[] getOptions();

	public String getScript();

	public String getType();

	public String getValue();

	public boolean isConfigurable();

	public void setConfigurable(boolean configurable);

	public void setOptions(String[] options);

	public void setScript(String script);

	public void setType(String type);

	public void setValue(String value);

}