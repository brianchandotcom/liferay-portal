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

package com.liferay.object.rest.petra.sql.dsl.expression;

import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.odata.entity.EntityModel;

/**
 * @author Gabriel Albuquerque
 */
public interface FilterPredicateFactory {

	public Predicate create(
		EntityModel entityModel, String filterString, long objectDefinitionId);

	public Predicate create(String filterString, long objectDefinitionId);

}