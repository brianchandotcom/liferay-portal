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

package com.liferay.portal.kernel.atom;

/**
 * @author Igor Spasic
 */
public interface AtomRequestContext {

	public Object getContainerAttribute(String name);

	public String getHeader(String name);

	public int getIntParameter(String name);

	public int getIntParameter(String name, int defaultValue);

	public long getLongParameter(String name);

	public long getLongParameter(String name, long defaultValue);

	public String getParameter(String name);

	public String getParameter(String name, String defaultValue);

	public Object getRequestAttribute(String name);

	public String getResolvedUri();

	public Object getSessionAttribute(String name);

	public String getTargetBasePath();

	public void setContainerAttribute(String name, Object value);

	public void setRequestAttribute(String name, Object value);

	public void setSessionAttribute(String name, Object value);

}