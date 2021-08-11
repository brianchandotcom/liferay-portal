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

import com.liferay.custom.elements.model.CustomElementsPortletDescriptor;
import com.liferay.custom.elements.service.CustomElementsPortletDescriptorLocalService;
import com.liferay.custom.elements.web.internal.constants.CustomElementsClayDataSetDisplayNames;
import com.liferay.custom.elements.web.internal.frontend.taglib.clay.data.set.CustomElementsPortletDescriptorClayDataSetEntry;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

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
	property = "clay.data.provider.key=" + CustomElementsClayDataSetDisplayNames.CUSTOM_ELEMENT_PORTLET_DESCRIPTORS,
	service = ClayDataSetDataProvider.class
)
public class CustomElementsPortletDescriptorClayDataSetDataProvider
	implements ClayDataSetDataProvider
		<CustomElementsPortletDescriptorClayDataSetEntry> {

	@Override
	public List<CustomElementsPortletDescriptorClayDataSetEntry> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<CustomElementsPortletDescriptor> customElementsPortletDescriptors =
			_customElementsPortletDescriptorLocalService.search(
				themeDisplay.getCompanyId(), filter.getKeywords(),
				pagination.getStartPosition(), pagination.getEndPosition(),
				sort);

		Stream<CustomElementsPortletDescriptor> stream =
			customElementsPortletDescriptors.stream();

		return stream.map(
			customElementsPortletDescriptor ->
				new CustomElementsPortletDescriptorClayDataSetEntry(
					customElementsPortletDescriptor)
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _customElementsPortletDescriptorLocalService.searchCount(
			themeDisplay.getCompanyId(), filter.getKeywords());
	}

	@Reference
	private CustomElementsPortletDescriptorLocalService
		_customElementsPortletDescriptorLocalService;

}