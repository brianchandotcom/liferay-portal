/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.batch.exportimport.internal.engine;

import com.liferay.analytics.batch.exportimport.internal.odata.entity.AnalyticsDXPEntityEntityModel;
import com.liferay.batch.engine.BaseBatchEngineTaskItemDelegate;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.odata.entity.EntityModel;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Marcos Martins
 */
public abstract class BaseAnalyticsDXPEntityBatchEngineTaskItemDelegate<T>
	extends BaseBatchEngineTaskItemDelegate<T> {

	@Override
	public EntityModel getEntityModel(Map<String, List<String>> multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	protected DynamicQuery buildDynamicQuery(
		long companyId, DynamicQuery dynamicQuery,
		Map<String, Serializable> parameters) {

		dynamicQuery.add(RestrictionsFactoryUtil.eq("companyId", companyId));

		Serializable resourceLastModifiedDate = parameters.get(
			"resourceLastModifiedDate");

		if (resourceLastModifiedDate == null) {
			return dynamicQuery;
		}

		dynamicQuery.add(
			RestrictionsFactoryUtil.gt(
				"modifiedDate", resourceLastModifiedDate));

		return dynamicQuery;
	}

	protected Predicate buildPredicate(
		BaseTable<?> baseTable, long companyId,
		Map<String, Serializable> parameters) {

		Column<?, Long> companyIdColumn = (Column<?, Long>)baseTable.getColumn(
			"companyId");

		Predicate predicate = companyIdColumn.eq(companyId);

		Serializable resourceLastModifiedDate = parameters.get(
			"resourceLastModifiedDate");

		if (resourceLastModifiedDate == null) {
			return predicate;
		}

		Column<?, Date> modifiedDateColumn =
			(Column<?, Date>)baseTable.getColumn("modifiedDate");

		return predicate.and(
			modifiedDateColumn.gt((Date)resourceLastModifiedDate));
	}

	private static final EntityModel _entityModel =
		new AnalyticsDXPEntityEntityModel();

}