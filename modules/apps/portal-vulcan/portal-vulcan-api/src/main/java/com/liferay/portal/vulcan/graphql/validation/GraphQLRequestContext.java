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

package com.liferay.portal.vulcan.graphql.validation;

import java.lang.reflect.Method;

/**
 * @author Carlos Correa
 */
public interface GraphQLRequestContext {

	public String getApplicationName();

	public long getCompanyId();

	public Method getMethod();

	public String getNamespace();

	public Class<?> getResourceClass();

	public Method getResourceMethod();

	public boolean isValidationRequired();

}