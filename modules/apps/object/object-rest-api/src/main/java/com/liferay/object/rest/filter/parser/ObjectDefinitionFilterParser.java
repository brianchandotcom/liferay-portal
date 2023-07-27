/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.filter.parser;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.InvalidFilterException;
import com.liferay.portal.odata.filter.expression.Expression;

/**
 * @author Carlos Correa
 */
public interface ObjectDefinitionFilterParser {

	public Expression parse(
			EntityModel entityModel, String filterString,
			ObjectDefinition objectDefinition)
		throws InvalidFilterException;

	public Expression parse(
			String filterString, ObjectDefinition objectDefinition)
		throws InvalidFilterException;

}