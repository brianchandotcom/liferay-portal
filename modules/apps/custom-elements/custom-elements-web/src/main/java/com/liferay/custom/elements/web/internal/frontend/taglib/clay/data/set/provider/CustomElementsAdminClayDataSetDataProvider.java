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

package com.liferay.custom.elements.web.internal.frontend.taglib.clay.data.set.provider;

import com.liferay.custom.elements.model.CustomElementsSource;
import com.liferay.custom.elements.service.CustomElementsSourceLocalService;
import com.liferay.custom.elements.web.internal.constants.CustomElementsWebKeys;
import com.liferay.custom.elements.web.internal.frontend.taglib.clay.data.set.CustomElementsSourceClayDataSetEntry;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.portal.kernel.search.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	immediate = true,
	property = "clay.data.provider.key=" + CustomElementsWebKeys.CUSTOM_ELEMENTS_ADMIN_DATA_SET_DISPLAY,
	service = ClayDataSetDataProvider.class
)
public class CustomElementsAdminClayDataSetDataProvider
	implements ClayDataSetDataProvider<CustomElementsSourceClayDataSetEntry> {

	@Override
	public List<CustomElementsSourceClayDataSetEntry> getItems(
		HttpServletRequest httpServletRequest, Filter filter,
		Pagination pagination, Sort sort) {

		List<CustomElementsSource> customElementsSources =
			_customElementsSourceLocalService.searchCustomElementsSources(
				filter.getKeywords(), pagination.getStartPosition(),
				pagination.getEndPosition(), sort);

		Stream<CustomElementsSource> stream = customElementsSources.stream();

		return stream.map(
			customElementsSource -> new CustomElementsSourceClayDataSetEntry(
				customElementsSource)
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public int getItemsCount(
		HttpServletRequest httpServletRequest, Filter filter) {

		return _customElementsSourceLocalService.
			searchCustomElementsSourcesCount(filter.getKeywords());
	}

	@Reference
	private CustomElementsSourceLocalService _customElementsSourceLocalService;

}