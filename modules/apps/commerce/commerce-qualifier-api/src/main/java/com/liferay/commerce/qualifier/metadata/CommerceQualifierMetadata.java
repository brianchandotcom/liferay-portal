/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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