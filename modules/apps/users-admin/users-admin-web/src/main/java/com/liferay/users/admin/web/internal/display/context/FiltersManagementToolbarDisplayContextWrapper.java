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

package com.liferay.users.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.ManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.ManagementToolbarDisplayContextWrapper;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.users.admin.management.toolbar.FilterContributor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Drew Brokke
 */
public class FiltersManagementToolbarDisplayContextWrapper
	extends ManagementToolbarDisplayContextWrapper {

	public FiltersManagementToolbarDisplayContextWrapper(
		FilterContributor[] filterContributors,
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		ManagementToolbarDisplayContext managementToolbarDisplayContext) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			managementToolbarDisplayContext);

		_filterContributors = filterContributors;
	}

	@Override
	public String getClearResultsURL() {
		String clearResultsURL = super.getClearResultsURL();

		for (FilterContributor filterContributor : _filterContributors) {
			clearResultsURL = HttpComponentsUtil.removeParameter(
				clearResultsURL,
				liferayPortletResponse.getNamespace() +
					filterContributor.getParameter());
		}

		return clearResultsURL;
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		DropdownItemList filterDropdownItems =
			(DropdownItemList)super.getFilterDropdownItems();

		for (FilterContributor filterContributor : _filterContributors) {
			filterDropdownItems.addGroup(
				dropdownGroupItem -> {
					Map<String, String> entriesMap = new LinkedHashMap<>();

					for (String value : filterContributor.getValues()) {
						entriesMap.put(
							filterContributor.getValueLabel(
								httpServletRequest.getLocale(), value),
							value);
					}

					dropdownGroupItem.setDropdownItems(
						getDropdownItems(
							entriesMap, getPortletURL(),
							filterContributor.getParameter(),
							_getCurrentValue(
								httpServletRequest, filterContributor)));
					dropdownGroupItem.setLabel(
						filterContributor.getLabel(
							httpServletRequest.getLocale()));
				});
		}

		return filterDropdownItems;
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		LabelItemList filterLabelItems =
			(LabelItemList)super.getFilterLabelItems();

		for (FilterContributor filterContributor : _filterContributors) {
			String currentValue = _getCurrentValue(
				httpServletRequest, filterContributor);

			if (ArrayUtil.contains(
					filterContributor.getFilterLabelValues(), currentValue)) {

				filterLabelItems.add(
					labelItem -> {
						labelItem.putData(
							"removeLabelURL",
							PortletURLBuilder.create(
								getPortletURL()
							).setParameter(
								filterContributor.getParameter(), (String)null
							).buildString());

						labelItem.setCloseable(true);
						labelItem.setLabel(
							String.format(
								"%s: %s",
								filterContributor.getShortLabel(
									httpServletRequest.getLocale()),
								filterContributor.getValueLabel(
									httpServletRequest.getLocale(),
									currentValue)));
					});
			}
		}

		return filterLabelItems;
	}

	private String _getCurrentValue(
		HttpServletRequest httpServletRequest,
		FilterContributor filterContributor) {

		return ParamUtil.getString(
			httpServletRequest, filterContributor.getParameter(),
			filterContributor.getDefaultValue());
	}

	private final FilterContributor[] _filterContributors;

}