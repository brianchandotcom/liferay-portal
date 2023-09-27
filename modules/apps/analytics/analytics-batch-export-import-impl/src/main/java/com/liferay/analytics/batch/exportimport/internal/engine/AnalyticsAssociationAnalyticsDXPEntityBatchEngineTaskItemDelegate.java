/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.batch.exportimport.internal.engine;

import com.liferay.analytics.batch.exportimport.internal.dto.v1_0.converter.constants.DTOConverterConstants;
import com.liferay.analytics.batch.exportimport.internal.odata.entity.AnalyticsDXPEntityEntityModel;
import com.liferay.analytics.dxp.entity.rest.dto.v1_0.DXPEntity;
import com.liferay.analytics.message.storage.model.AnalyticsAssociation;
import com.liferay.analytics.message.storage.service.AnalyticsAssociationLocalService;
import com.liferay.batch.engine.BaseBatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(
	property = "batch.engine.task.item.delegate.name=analytics-association-analytics-dxp-entities",
	service = BatchEngineTaskItemDelegate.class
)
public class AnalyticsAssociationAnalyticsDXPEntityBatchEngineTaskItemDelegate
	extends BaseBatchEngineTaskItemDelegate<DXPEntity> {

	@Override
	public EntityModel getEntityModel(Map<String, List<String>> multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public Page<DXPEntity> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		List<AnalyticsAssociation> analyticsAssociations = null;
		int totalCount = 0;

		Date modifiedDate = _getModifiedDate(filter);

		if (modifiedDate != null) {
			analyticsAssociations =
				_analyticsAssociationLocalService.getAnalyticsAssociations(
					contextCompany.getCompanyId(), modifiedDate,
					User.class.getName(), pagination.getStartPosition(),
					pagination.getEndPosition());
			totalCount =
				_analyticsAssociationLocalService.getAnalyticsAssociationsCount(
					contextCompany.getCompanyId(), modifiedDate,
					User.class.getName());
		}
		else {
			analyticsAssociations =
				_analyticsAssociationLocalService.getAnalyticsAssociations(
					contextCompany.getCompanyId(), User.class.getName(),
					pagination.getStartPosition(), pagination.getEndPosition());
			totalCount =
				_analyticsAssociationLocalService.getAnalyticsAssociationsCount(
					contextCompany.getCompanyId(), User.class.getName());
		}

		if (ListUtil.isEmpty(analyticsAssociations)) {
			return Page.of(Collections.emptyList());
		}

		List<DXPEntity> dxpEntities = new ArrayList<>();

		for (AnalyticsAssociation analyticsAssociation :
				analyticsAssociations) {

			User user = _userLocalService.getUser(
				analyticsAssociation.getAssociationClassPK());

			user.setModifiedDate(analyticsAssociation.getModifiedDate());

			dxpEntities.add(_dxpEntityDTOConverter.toDTO(user));
		}

		return Page.of(dxpEntities, pagination, totalCount);
	}

	private Date _getModifiedDate(Filter filter) {
		if (!(filter instanceof QueryFilter)) {
			return null;
		}

		QueryFilter queryFilter = (QueryFilter)filter;

		Query query = queryFilter.getQuery();

		if (!(query instanceof TermRangeQuery)) {
			return null;
		}

		TermRangeQuery termRangeQuery = (TermRangeQuery)query;

		if (!StringUtil.startsWith(termRangeQuery.getField(), "modified")) {
			return null;
		}

		String lowerTerm = termRangeQuery.getLowerTerm();

		return new Date(GetterUtil.getLong(lowerTerm));
	}

	private static final EntityModel _entityModel =
		new AnalyticsDXPEntityEntityModel();

	@Reference
	private AnalyticsAssociationLocalService _analyticsAssociationLocalService;

	@Reference(target = DTOConverterConstants.DXP_ENTITY_DTO_CONVERTER)
	private DTOConverter<BaseModel<?>, DXPEntity> _dxpEntityDTOConverter;

	@Reference
	private UserLocalService _userLocalService;

}