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

package com.liferay.headless.admin.address.internal.resource.v1_0;

import com.liferay.headless.admin.address.dto.v1_0.Region;
import com.liferay.headless.admin.address.resource.v1_0.RegionResource;
import com.liferay.portal.kernel.model.RegionTable;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Drew Brokke
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/region.properties",
	scope = ServiceScope.PROTOTYPE, service = RegionResource.class
)
public class RegionResourceImpl extends BaseRegionResourceImpl {

	@Override
	public Page<Region> getRegionsPage(
			Boolean active, String search, Pagination pagination, Sort[] sorts)
		throws Exception {

		BaseModelSearchResult<com.liferay.portal.kernel.model.Region>
			baseModelSearchResult = _regionService.searchRegions(
				contextCompany.getCompanyId(), active, search,
				pagination.getStartPosition(), pagination.getEndPosition(),
				_toOrderByComparator(sorts));

		return Page.of(
			transform(baseModelSearchResult.getBaseModels(), this::_toRegion),
			pagination, baseModelSearchResult.getLength());
	}

	private OrderByComparator<com.liferay.portal.kernel.model.Region>
		_toOrderByComparator(Sort[] sorts) {

		if (ArrayUtil.isEmpty(sorts)) {
			return null;
		}

		List<Object> objects = new ArrayList<>();

		for (Sort sort : sorts) {
			objects.add(sort.getFieldName());
			objects.add(!sort.isReverse());
		}

		return OrderByComparatorFactoryUtil.create(
			RegionTable.INSTANCE.getTableName(),
			objects.toArray(new Object[0]));
	}

	private Region _toRegion(
			com.liferay.portal.kernel.model.Region serviceBuilderRegion)
		throws Exception {

		return new Region() {
			{
				active = serviceBuilderRegion.getActive();
				countryId = serviceBuilderRegion.getCountryId();
				id = serviceBuilderRegion.getRegionId();
				name = serviceBuilderRegion.getName();
				regionCode = serviceBuilderRegion.getRegionCode();
				title_i18n = serviceBuilderRegion.getLanguageIdToTitleMap();
			}
		};
	}

	@Reference
	private RegionService _regionService;

}