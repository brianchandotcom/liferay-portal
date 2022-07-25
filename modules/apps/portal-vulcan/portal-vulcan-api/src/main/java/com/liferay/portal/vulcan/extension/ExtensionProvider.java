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

package com.liferay.portal.vulcan.extension;

import java.io.Serializable;

import java.util.Collection;
import java.util.Map;

/**
 * @author Javier de Arcos
 */
public interface ExtensionProvider {

	public Map<String, Serializable> getExtendedProperties(
			long companyId, String className, Object entity)
		throws Exception;

	public Map<String, PropertyDefinition> getExtendedPropertyDefinitions(
		long companyId, String className);

	public Collection<String> getFilteredPropertyNames(
		long companyId, Object entity);

	public boolean isApplicableExtension(long companyId, String className);

	public void setExtendedProperties(
			long companyId, long userId, String className, Object entity,
			Map<String, Serializable> extendedProperties)
		throws Exception;

}