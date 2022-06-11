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

package com.liferay.commerce.qualifier.metadata;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.sort.OrderByExpression;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.PersistedModelLocalService;

import java.util.Map;

/**
 * @author Riccardo Alberti
 */
public interface CommerceQualifierMetadata<T extends ClassedModel> {

	public String[][] getAllowedTargetKeysArray();

	public Predicate getFilterPredicate();

	public String getKey();

	public Column<?, String> getKeywordsColumn();

	public String getModelClassName();

	public ModelResourcePermission<T> getModelResourcePermission();

	public OrderByExpression[] getOrderByExpressions(
		Map<String, ?> targetAttributes);

	public PersistedModelLocalService getPersistedModelLocalService();

	public Column<?, Long> getPrimaryKeyColumn();

	public Table getTable();

}