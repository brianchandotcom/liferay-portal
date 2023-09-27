/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.batch.exportimport.internal.engine;

import com.liferay.analytics.batch.exportimport.internal.odata.entity.AnalyticsDXPEntityEntityModel;
import com.liferay.analytics.dxp.entity.rest.dto.v1_0.DXPEntity;
import com.liferay.batch.engine.BaseBatchEngineTaskItemDelegate;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Marcos Martins
 */
public abstract class BaseAnalyticsDXPEntityBatchEngineTaskItemDelegate
	<DXPEntity>
		extends BaseBatchEngineTaskItemDelegate<DXPEntity> {

	@Override
	public EntityModel getEntityModel(Map<String, List<String>> multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	protected DynamicQuery buildDynamicQuery(
		long companyId, DynamicQuery dynamicQuery, Filter filter) {

		dynamicQuery.add(RestrictionsFactoryUtil.eq("companyId", companyId));

		if (filter instanceof QueryFilter) {
			QueryFilter queryFilter = (QueryFilter)filter;

			Query query = queryFilter.getQuery();

			if (query instanceof TermRangeQuery) {
				TermRangeQuery termRangeQuery = (TermRangeQuery)query;

				if (StringUtil.startsWith(
						termRangeQuery.getField(), "modified")) {

					String lowerTerm = termRangeQuery.getLowerTerm();

					if ((lowerTerm != null) && Validator.isNumber(lowerTerm)) {
						dynamicQuery.add(
							RestrictionsFactoryUtil.gt(
								"modifiedDate",
								new Date(GetterUtil.getLong(lowerTerm))));
					}

					String upperTerm = termRangeQuery.getUpperTerm();

					if ((upperTerm != null) && Validator.isNumber(upperTerm)) {
						dynamicQuery.add(
							RestrictionsFactoryUtil.le(
								"modifiedDate",
								new Date(GetterUtil.getLong(upperTerm))));
					}
				}
			}
		}

		return dynamicQuery;
	}

	protected void getSearchContext(SearchUtil.SearchContext searchContext) {
		searchContext.setCompanyId(contextCompany.getCompanyId());
		searchContext.setGroupIds(new long[] {0});

		if (contextUser.getLocale() != null) {
			searchContext.setLocale(contextUser.getLocale());
		}

		searchContext.setUserId(0);
		searchContext.setVulcanCheckPermissions(false);
	}

	private static final EntityModel _entityModel =
		new AnalyticsDXPEntityEntityModel();

}