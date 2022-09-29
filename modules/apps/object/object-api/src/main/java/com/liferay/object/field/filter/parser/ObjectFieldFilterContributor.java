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

package com.liferay.object.field.filter.parser;

import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;
import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public interface ObjectFieldFilterContributor {

	public Map<String, Object> parse(
			long listTypeDefinitionId, long objectDefinitionId, Locale locale,
			ObjectViewFilterColumn objectViewFilterColumn)
		throws PortalException;

	public String toValueSummary(
			Locale locale, ObjectField objectField,
			ObjectViewFilterColumn objectViewFilterColumn)
		throws PortalException;

	public void validate(
			long objectDefinitionId,
			ObjectViewFilterColumn objectViewFilterColumn)
		throws PortalException;

}