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

import com.liferay.headless.admin.address.dto.v1_0.Country;
import com.liferay.headless.admin.address.internal.dto.v1_0.converter.CountryResourceDTOConverter;
import com.liferay.headless.admin.address.resource.v1_0.CountryResource;
import com.liferay.portal.kernel.model.CountryTable;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.odata.entity.DoubleEntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 * @author Drew Brokke
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/country.properties",
	scope = ServiceScope.PROTOTYPE, service = CountryResource.class
)
public class CountryResourceImpl extends BaseCountryResourceImpl {

	@Override
	public Page<Country> getCountriesPage(
			Boolean active, String search, Pagination pagination, Sort[] sorts)
		throws Exception {

		BaseModelSearchResult<com.liferay.portal.kernel.model.Country>
			countries = _countryService.searchCountries(
				contextCompany.getCompanyId(), active, search,
				pagination.getStartPosition(), pagination.getEndPosition(),
				_toOrderByComparator(sorts));

		return Page.of(
			transform(countries.getBaseModels(), this::_toCountry), pagination,
			countries.getLength());
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return () -> EntityModel.toEntityFieldsMap(
			new StringEntityField("name", locale -> "name"),
			new DoubleEntityField("position", locale -> "position"));
	}

	private Country _toCountry(
			com.liferay.portal.kernel.model.Country serviceBuilderCountry)
		throws Exception {

		return _countryResourceDTOConverter.toDTO(serviceBuilderCountry);
	}

	private OrderByComparator<com.liferay.portal.kernel.model.Country>
		_toOrderByComparator(Sort[] sorts) {

		if (sorts == null) {
			return null;
		}

		for (Sort sort : sorts) {
			return OrderByComparatorFactoryUtil.create(
				CountryTable.INSTANCE.getTableName(), sort.getFieldName(),
				String.valueOf(!sort.isReverse()));
		}

		return null;
	}

	@Reference
	private CountryResourceDTOConverter _countryResourceDTOConverter;

	@Reference
	private CountryService _countryService;

}