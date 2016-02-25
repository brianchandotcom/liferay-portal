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

package com.liferay.portal.kernel.jsonwebservice;

import com.liferay.portal.kernel.util.MethodParameter;

import java.lang.reflect.Method;

/**
 * @author Igor Spasic
 */
public interface JSONWebServiceActionMapping {

	public Class<?> getActionClass();

	public Method getActionMethod();

	public Object getActionObject();

	public String getContextName();

	public String getContextPath();

	public String getMethod();

	public MethodParameter[] getMethodParameters();

	public String getPath();

	public Method getRealActionMethod();

	public String getSignature();

	public boolean isDeprecated();

}